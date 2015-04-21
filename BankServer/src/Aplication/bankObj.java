/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplication;

import BankServer.bankPOA;
import Lib.ProsedeurControls;
import Lib.RandomStringGenerator;
import Lib.SendMailTLS;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

class bankObj extends bankPOA {

    private ORB orb;
    ProsedeurControls pc = new ProsedeurControls();
    String para;
    private ResultSet res;

    public void setORB(ORB orb_val) {
        orb = orb_val;
    }

    // implement add() method
    // implement shutdown() method
    public void shutdown() {
        orb.shutdown(false);
    }

    @Override
    public double getBalance(String account) {
        para = "('" + account + "')";
        res = pc.callProc("selectAccount", para);
        try {
            if (res.next()) {
                double bal = res.getDouble(4);
                return bal;
            } else {
                return -1;
            }
        } catch (SQLException ex) {
            return -1;
        }
    }

    @Override
    public String deposit(String account, String amount, String userid) {
        para = "('" + account + "')";
        res = pc.callProc("selectAccount", para);
        try {
            if (res.next()) {
                para = "('" + account + "','d'," + amount + ",'" + userid + "')";
                res = pc.callProc("doTransaction", para);
                return "Successfully do the deposit";
            } else {
                return "Your account number is wrong";
            }
        } catch (SQLException ex) {
            return "Your account number is wrong";
        }

    }

    @Override
    public String withdraw(String account, String amount, String userid) {
        double amu = Double.parseDouble(amount);
        para = "('" + account + "')";
        res = pc.callProc("selectAccount", para);
        try {
            if (res.next()) {
                if (res.getDouble("accbalance") > 500 + amu) {
                    para = "('" + account + "','w'," + amount + ",'" + userid + "')";
                    res = pc.callProc("doTransaction", para);
                    return "Successfully do the withdraw";
                } else {
                    return "Your account balence is insuffisunt";
                }

            } else {
                return "Your account number is wrong";
            }
        } catch (SQLException ex) {
            return "Your account number is wrong";
        }
    }

    @Override
    public String addAccount(String username, String accNo, String initDeposit, String accType) {

        double depo = Double.parseDouble(initDeposit);

        para = "('" + username + "')";
        res = pc.callProc("selectCustomer", para);
        try {
            if (res.next()) {
            } else {
                return "User ID does not exsist ";
            }
        } catch (SQLException ex) {
            return "User ID does not exsist ";
        }

        para = "('" + accNo + "')";
        res = pc.callProc("selectAccount", para);
        try {
            if (res.next()) {
                return "Account Number is Exsisting";
            } else {
                if (!Double.isNaN(depo)) {
                    para = "('" + accNo + "','" + username + "','" + accType + "'," + depo + ")";
                    res = pc.callProc("insertAccount", para);
                    // para = "('" + account +"','d',"+amount+ ",'" + userid + "')";
                    para = "('" + accNo + "','d'," + initDeposit + ",'adm1')";
                    res = pc.callProc("doTransaction", para);

                    return "Successfully Added to the System";
                } else {
                    return "Enter number for initial deosit";
                }
            }
        } catch (SQLException ex) {
            if (Double.isNaN(depo)) {
                para = "('" + accNo + "','" + username + "','" + accType + "'," + depo + ")";
                res = pc.callProc("insertAccount", para);
                return "Successfully Added to the System";
            } else {
                return "Enter number for initial deosit";
            }
        }

        // return "Successfully Added to the System";
    }

    @Override
    public String getAccounts(String accno) {
        try {
            para = "('" + accno + "')";
            res = pc.callProc("selectAccount", para);
            if (res.next()) {
                String cusid = res.getString(2);
                String acctype = res.getString(3);
                String accbalance = Double.toString(res.getDouble(4));

                String info = null;
                info = "Account Number \t: " + accno + "\nAccount Owner \t\t: " + cusid + "\nAccount Type \t\t: " + acctype + "\nAccount Balance \t: " + accbalance + "\n\n~~~~~~~~~~~~~~~Account Transactions~~~~~~~~~~~~~~~\n\nT.ID\tT.Type\tAmmount\tDate & Time\t\tT.Done by\n";

                para = "('" + accno + "')";
                res = pc.callProc("AccountTransactions", para);
                while (res.next()) {
                    String tid = res.getString(1);
                    String tt = res.getString(3);
                    String ttype = null;
                    if (tt.equals("d")) {
                        ttype = "Deposit";
                    } else if (tt.equals("w")) {
                        ttype = "Withdraw";
                    }
                    String amnt = Double.toString(res.getDouble(4));
                    String time = res.getString(5);
                    String other = res.getString(6);

                    info = info + "\n" + tid + "\t" + ttype + "\t" + amnt + "\t" + time + "\t" + other;
                }

                return info;
            } else {
                return "Account is does not exsist";
            }
        } catch (SQLException ex) {
            return "Account is does not exsist";
        }
    }

    @Override
    public String getInfo(String userid) {
        para = "('" + userid + "')";
        res = pc.callProc("selectonecustomer", para);

        try {
            if (res.next()) {
                String uid = res.getString(1);
                String uname = res.getString(2);
                String umobile = res.getString(3);
                String uaddres = res.getString(4);
                String uemail = res.getString(5);
                String ut = res.getString(6);
                String usertype = null;
                if ("M".equals(ut)) {
                    usertype = "Manager";
                } else if (ut.equals("C")) {
                    usertype = "Customer";
                }
                String info = "User ID\t : " + uid + "\nUser name\t : " + uname + "\nMobile Number\t : " + umobile + "\nPostel Adress\t : " + uaddres + "\nE-mail\t : " + uemail + "\nUser Type\t : " + usertype + "\n~~~~~~~~~Your Account Details~~~~~~~~~\n\nAcc Number \t Acc Type \tAcc Balance \n";

                para = "('" + userid + "')";
                res = pc.callProc("selectCustAcc", para);
                while (res.next()) {
                    String accno = res.getString(1);
                    String acctype = res.getString(3);
                    String bal = (String) Double.toString(res.getDouble(4));
                    info = info + "\n" + accno + "\t" + acctype + "\t" + bal;
                }

                return info;
            } else {
                return "User is does not exsist";
            }
        } catch (SQLException ex) {
            return "User is does not exsist";
        }
    }

    @Override
    public String addUser(String userid, String username, String mobile, String adress, String email, String usertype) {

        para = "('" + userid + "')";
        res = pc.callProc("selectCustomer", para);

        try {
            if (res.next()) {
                return "User is alredy in the system";
            } else {
                para = "('" + userid + "','" + username + "','" + mobile + "','" + adress + "','" + email + "','" + usertype + "')";
                pc.callProc("insertCustomer", para);

                RandomStringGenerator rsg = new RandomStringGenerator();
                String pass = rsg.nextSessionId();

                para = "('" + userid + "','" + pass + "')";
                pc.callProc("insertPassword", para);

                SendMailTLS sm = new SendMailTLS();
                sm.SendMail(email, "MW Bank Password ", "Your new password = " + pass);

                para = "('" + userid + "')";
                res = pc.callProc("selectCustomer", para);

                if (res.next()) {

                    return "Successfully Added";
                } else {
                    return "Error on Added";
                }
            }

            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            return "Error on Added " + ex;
        }
    }

    @Override
    public String signIn(String userid, String password) {
        try {
            para = "('" + userid + "','" + password + "')";
            res = pc.callProc("selectPassword", para);

            if (res.next()) {
                String usertype = res.getString(8);
                if ("M".equals(usertype)) {
                    return "M";
                } else {
                    return "C";
                }
            } else {
                return "I";
            }
            // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            return "I";
        }

// throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getAccountList(String userid) {
        try {
            para = "('" + userid + "')";
            res = pc.callProc("selectCustAcc", para);
            String acclist="";
            while (res.next()) {
                String accno = res.getString(1);
                acclist=acclist + accno+",";
            }
            return acclist;
        } catch (SQLException ex) {
            return "Error";
        }
    }

}

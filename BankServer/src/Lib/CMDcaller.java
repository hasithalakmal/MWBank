/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lib;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Massa
 */
public class CMDcaller {
    public boolean isCorbaRun(){
        try {
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "start orbd -ORBInitialPort 1050");
            builder.redirectErrorStream(true);
            Process p = builder.start();
            return true;
        } catch (IOException ex) {
           return  false;
        }
    }
}

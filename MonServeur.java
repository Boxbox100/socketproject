
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author pc
 */
public class MonServeur {
    public static void main(String[] args) {
        
        try {
            ServerSocket serv= new ServerSocket(1234);
            Socket bob=serv.accept();
            DataInputStream data= new DataInputStream(bob.getInputStream());
            DataOutputStream dataOut=new DataOutputStream(bob.getOutputStream());
            BufferedReader buff=new BufferedReader(new InputStreamReader(System.in));
            
            
            
            String rep="",rep2="";
            while (!rep.equals("bob")) {                
                rep=data.readUTF();
                System.out.println("client: "+rep);
                rep2=buff.readLine();
                dataOut.writeUTF(rep2);
                dataOut.flush();
            }
            dataOut.close();
            serv.close();
        } catch (IOException ex) {
            Logger.getLogger(MonServeur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

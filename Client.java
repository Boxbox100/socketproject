 
import java.io.BufferedReader;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
public class Client {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        try {
            Socket soc=new Socket("localhost",1234);
            DataOutputStream data= new DataOutputStream(soc.getOutputStream());
            DataInputStream DataInp=new DataInputStream(soc.getInputStream());
            BufferedReader buff=new BufferedReader(new InputStreamReader(System.in));
            String rep="",rep2="";
            while (!rep.equals("bob")) {                
                rep=buff.readLine();
                data.writeUTF(rep);
                data.flush();
                rep2=DataInp.readUTF();
                System.out.println("Serveur: "+rep2);
                
            }
            data.close();
            soc.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

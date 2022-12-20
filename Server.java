import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//
public class Server
{
    public static void main(String[] args) {

        Frame frame =new Frame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

//
class Frame extends JFrame implements Runnable
{


    private JTextArea textArea;

    public Frame()
    {
        setBounds(1200, 300, 280, 350);

        setTitle("Server");

        JPanel jpanel = new JPanel();

        jpanel.setLayout(new BorderLayout());

        textArea = new JTextArea();

        jpanel.add(textArea, BorderLayout.CENTER);

        add(jpanel);

        setVisible(true);

        Thread thread = new Thread(this);
        thread.start();
    }
    //
    public void run()
    {
        try
        {
            //
            ServerSocket servSoc = new ServerSocket(5555);
            System.out.println("attente de connexion");
            InfoClient c1;

            //
            while (true)
            {
                Socket socClient = servSoc.accept();
                System.out.println("connexion acceptee");

                String adresseIPClient = socClient.getInetAddress().getHostAddress();

                //
                ObjectInputStream objectInputStream = new ObjectInputStream(socClient.getInputStream());
                c1 = (InfoClient) objectInputStream.readObject();
                textArea.append(
                        c1.getNomClient() + ": " +
                        c1.getClientMessage() + "\n");


            //
                Socket soc = new Socket(adresseIPClient, 9999);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(soc.getOutputStream());
                objectOutputStream.writeObject(c1);
                System.out.println("transmition au client");

            // 
                objectOutputStream.close();
                soc.close();
                socClient.close();
            }
        }
        catch (IOException | ClassNotFoundException exception)
        {
            exception.printStackTrace();
        }
    }
}

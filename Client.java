import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

//
public class Client
{
    public static void main(String[] args) {

        ClientFrame clientFrame=new ClientFrame();

        clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

 //
class ClientFrame extends JFrame{

    ClientFrame(){

        setBounds(600,300,270,370);

        setTitle("Client");

        ClientPanel panel=new ClientPanel();

        add(panel);

        setVisible(true);
    }
}

//
class ClientPanel extends JPanel implements Runnable
{
    private JTextField messageField;
    private JTextField nomField;
    private JTextField addresseIPField;
    private JTextArea texte;

    ClientPanel()
    {
        String nomClient = JOptionPane.showInputDialog("entrez votre nom");

        //nom
        JLabel nameLabel = new JLabel("Nom:");
        nomField = new JTextField(15);
        nomField.setEditable(false);
        nomField.setText(nomClient);
        add(nameLabel);
        add(nomField);

        //addresse ip
        JLabel adresseIPLabel = new JLabel("Adresse IP serveur");
        addresseIPField = new JTextField(10);
        add(adresseIPLabel);
        add(addresseIPField);

        //Text area
        texte = new JTextArea(14,20);
        texte.setEditable(false);
        add(texte);

        //Message
        messageField = new JTextField(15);
        add(messageField);

        //Bouton
        JButton Envoyer = new JButton("Envoyer");
        Envoyer.addActionListener(new SendTextListener());
        add(Envoyer);

        Thread thread = new Thread(this);
        thread.start();
    }

    //
    @Override
    public void run()
    {
        try
        {
            ServerSocket servSoc = new ServerSocket(9999);
            Socket soc;
            InfoClient infoRecu;

            while(true)
            {
                soc = servSoc.accept();
                ObjectInputStream objectInputStream = new ObjectInputStream(soc.getInputStream());
                infoRecu = (InfoClient) objectInputStream.readObject();
                texte.append(
                        infoRecu.getNomClient() + ": " +
                        infoRecu.getClientMessage() + "\n");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    //
    class SendTextListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            try
            {
                Socket soc = new Socket(addresseIPField.getText(), 5555);

                //
                InfoClient c1 = new InfoClient();
                c1.setNomClient(nomField.getText());
                c1.setClientMessage(messageField.getText());

                //
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(soc.getOutputStream());
                objectOutputStream.writeObject(c1);

                soc.close();
            }
            catch (IOException e1)
            {
                System.out.println(e1.getMessage());
            }
        }
    }
}

//
class InfoClient implements Serializable
{
    private String nomClient, addresseIPClient, clientMessage;

    public String getNomClient()
    {
        return nomClient;
    }

    public void setNomClient(String nomClient)
    {
        this.nomClient = nomClient;
    }

    public String getAdresseIP()
    {
        return addresseIPClient;
    }

    public void setClientIpAddress(String addresseIPClient)
    {
        this.addresseIPClient = addresseIPClient;
    }

    public String getClientMessage()
    {
        return clientMessage;
    }

    public void setClientMessage(String clientMessage)
    {
        this.clientMessage = clientMessage;
    }
}
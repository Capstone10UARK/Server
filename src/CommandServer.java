import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.lang.AutoCloseable;
import org.json.JSONObject;
import org.json.JSONException;


class CommandServer implements AutoCloseable{
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    
    public CommandServer(int portNum) throws IOException{
        this.serverSocket = new ServerSocket(portNum);
        this.clientSocket = serverSocket.accept();
        this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
    }
    
    public void close() throws IOException{
        serverSocket.close();
        clientSocket.close();
        out.close();
        in.close();
    }
   
    public void run(){
        
        try{  
            while(true){
                String input = in.readLine();
                if(input != null){
                    JSONObject obj = new JSONObject(input);
                    if(obj.getString("Cmd").equals("Go")){
                        //Do stuff
                        System.out.println("Process Images");
                        JSONObject response = new JSONObject();
                        response.put("Status", "Done");
                        System.out.println(response.toString());
                        out.println(response.toString());
                    }
                    else if(obj.getString("Cmd").equals("End"))
                        break;
                    else
                        System.out.println(input+ " Was an unknown message");
                }
            }
        }
        catch(IOException e){
            System.err.println("ERROR READING COMMAND FROM PORT");
            System.err.println(e.getMessage());
        }
        catch(Exception e){
            System.err.println("ERROR WHEN CREATING JSONOBJECT");
            System.err.println(e.getMessage());
        }
    }
    
     public static void main(String[] args){
        
        try(CommandServer cmdServer = new CommandServer(4444);)
        {
            cmdServer.run();
            cmdServer.close();
        }
        catch(IOException e){
            System.err.println("ERROR CREATING COMMAND SERVER");
            System.err.println(e.getMessage());
        }
        
        
    }
    
}

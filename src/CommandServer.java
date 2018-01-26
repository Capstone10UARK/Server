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
            boolean run = true;
            while(run){
                String input = in.readLine();
                
                if(input != null){
                    JSONObject obj = new JSONObject(input);
                    
                    if(obj.has("Cmd")){
                        switch(obj.getString("Cmd")){
                            case "Go":
                                //Do stuff
                                System.out.println("Process Images");
                                //No need to create response here
                                //Client can send a ProgressReport Cmd if they are curious
                                break;
                                
                            case "ProgressReport":
                                //get progress from ImageProcessor and create response
                                JSONObject response = new JSONObject();
                                
                                response.put("Status", "Running");
                                response.put("Progress", 25);
                                out.println(response.toString());
                                break;
                                
                            case "End":
                                run = false;
                                break;
                        }
                    }
                }
            }
        }
        catch(IOException e){
            System.err.println("ERROR READING COMMAND FROM PORT");
            System.err.println(e.getMessage());
        }
        catch(JSONException e){
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

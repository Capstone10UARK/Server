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
                    
                    if(obj.has("command")){
                        JSONObject response = new JSONObject();
                        switch(obj.getString("command")){
                            case "go":
                                if(obj.has("filePath")){
                                    String filePath = obj.getString("filePath");
                                    //Do stuff
                                
                                    System.out.println("Process Images");
                                    System.out.println("Create CSV at " + filePath);
                                
                                    response.put("response","commandExecuted");
                                }
                                else{
                                    response.put("response", "error");
                                    response.put("message", "No file path provided with go command. See API.");
                                }
                                break;
                                
                            case "progressReport":
                                //get progress from ImageProcessor and create response
                                response.put("response", "progressReport");                            
                                response.put("status", "running");
                                response.put("progress", 25);
                                break;
                                
                            case "end":
                                run = false;
                                response.put("reponse", "commandExecuted");
                                break;
                        }
                        out.println(response.toString());
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

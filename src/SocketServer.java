import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;

class SocketServer{
    
    public SocketServer(){
        
    }
    public static void main(String[] args) throws IOException{
        SocketServer.run(4444);
    }
    
    public static void run(int portNum) throws IOException{
        try(
            ServerSocket serverSocket = new ServerSocket(portNum);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ){
            
            while(true){
                String input = in.readLine();
                if(input != null){
                    if(input.equals("Go")){
                        //Do stuff
                        System.out.println("Process Images");
                        out.println("Done");
                    }
                    else if(input.equals("End"))
                        break;
                    else
                    System.out.println(input+ " Was an unknown message");
                }
            }
        }
        catch(IOException error){
            System.out.println("Exception caught when trying to listen on port "
                + portNum + " or listening for a connection");
            System.err.println(error.getMessage());
        }
    }
}

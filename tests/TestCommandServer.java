import java.net.Socket;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;

class TestCommandServer{
    
    public static void main(String[] args) throws IOException{
        try(
            Socket socket = new Socket("127.0.0.1", 4444);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ){
            out.println("Go");
            System.out.println(in.readLine());
            out.println("Go");
            System.out.println(in.readLine());
            out.println("Go");
            System.out.println(in.readLine());
            out.println("Go");
            System.out.println(in.readLine());
            out.println("End");
            System.out.println(in.readLine());
        }
        catch(IOException error){
            System.out.println("Exception caught when trying to write to port 4444");
            System.err.println(error.getMessage());
        }
    }
}

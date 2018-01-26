import java.net.Socket;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONException;

class TestCommandServer{
    
    public static void main(String[] args) throws IOException{
        try(
            Socket socket = new Socket("127.0.0.1", 4444);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ){
            JSONObject obj = new JSONObject();
            obj.put("Cmd", "Go");
            out.println(obj.toString());
            JSONObject response = new JSONObject(in.readLine());
            System.out.println(response.toString());
            
            out.println(obj.toString());
            response = new JSONObject(in.readLine());
            System.out.println(response.toString());
            
            out.println(obj.toString());
            response = new JSONObject(in.readLine());
            System.out.println(response.toString());
            
            out.println(obj.toString());
            response = new JSONObject(in.readLine());
            System.out.println(response.toString());
            
            obj = new JSONObject();
            obj.put("Cmd","End");
            out.println(obj.toString());
        }
        catch(IOException e){
            System.out.println("Exception caught when trying to write to port 4444");
            System.err.println(e.getMessage());
        }
        catch(Exception e){
            System.out.println("Exception caught when trying to create JSONObject");
            System.err.println(e.getMessage());
        }
    }
}

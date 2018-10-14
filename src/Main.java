import java.io.*;

public class Main
{

    public static void main(String[] args)
    {

        GUI gui = new GUI();
        gui.show();

        String fileName = PublicMethods.getString("Enter File Name: ");

        /*String code = "";
         try (BufferedReader fileReader = new BufferedReader(new FileReader(new File("./resources/" + PublicMethods.fixFileSuffix(fileName, "txt")))))
        {
            String line;
            while ((line = fileReader.readLine()) != null)
                code += line.toLowerCase() + "\n";

            try
            {
                Interpreter interpreter = new Interpreter(code);
                interpreter.execute();
            }
             catch (InterpreterException e)
            {
                System.out.println(e.getMessage());
            }
        }
        catch (IOException e){
            System.out.println("Unable to load specified file: " + fileName);}*/


    }

}
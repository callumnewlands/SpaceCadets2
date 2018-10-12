public class Main
{

    public static void main(String[] args)
    {
        String fileName = PublicMethods.getString("Enter File Name: ");

        try
        {
            Interpreter interpreter = new Interpreter("./resources/" + PublicMethods.fixFileSuffix(fileName, "txt"));
            interpreter.execute();
        }
        catch (InterpreterException e)
        {
            System.out.println(e.getMessage());
        }

    }

}
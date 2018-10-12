import java.util.ArrayList;

public class Interpreter
{
    private ArrayList<String> _sourceCode = new ArrayList<>();

    Interpreter(String filePath)
    {
        //read in each line to LCase
    }

    void execute() throws InterpreterException
    {
        if (_sourceCode.size() <= 0)
            throw new InterpreterException("No source code to execute");

        for (String line: _sourceCode)
        {
            System.out.println(line);
        }
    }

}

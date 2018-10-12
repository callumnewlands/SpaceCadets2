public class InterpreterException extends Exception
{
    InterpreterException(String errorMessage) { super(errorMessage); }

    InterpreterException(String errorMessage, Throwable e) { super(errorMessage, e); }
}

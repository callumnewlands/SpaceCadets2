/* BareBones Language:
  Identifiers:
   - clear
   - decr
   - do
   - end
   - incr
   - not
   - while

  a '#' character introduces a comment, which continues to the end of the source line.
  Reserved words are case-insensitive.
  Identifiers must begin with an alphabetic character, and may contain alphabetic, numeric, and underscore characters.
   Identifiers are case-insensitive. Reserved words my not be used as identifiers.
   */

public class Main {

    public static void main(String[] args) {

        Interpreter interpreter = new Interpreter("<filePath>");
    }
}

import java.util.Scanner;

public class Converter
{
    //private static String postfix = "";
    public static void main(String[] args)
    {
        ArrayStack stack = new ArrayStack();
        ArrayStack answer = new ArrayStack();

        String postfix = "";

        Scanner input = new Scanner(System.in);
        System.out.println("Please enter an infix numerical expression between 3 and 20 characters: ");

        String infix = input.nextLine();

        //before the statement is checked it's set to false
        boolean valid = false;
        //while the statement is false checks keep being run
        while(!valid)
        {
            //if the statement is valid the convert method is called and the loop is exited
            if (isValid(infix))
            {
                postfix = convert(infix, stack);
                valid = true;
                System.out.println("The result of the expression is: \nInfix: " + infix + "\nPostfix: " + postfix);
            }
            //else, the statement is false and an error message is printed and the user
            //is asked to enter a new statement
            else
            {
                System.out.println("Only the following characters are valid: +,-,*,/,^,(,) \nPlease try again:");
                infix = input.nextLine();
            }
        }
        //when the statement has been checked and converted it's then solved
        solve(postfix, answer);
    }

    //method that converts the infix expression to the equivalent postfix expression
    public static String convert(String infix, ArrayStack stack)
    {

        String output = "";

        //scan the infix expression from left to right
        for(int i = 0; i < infix.length(); i++)
        {
            char current_character  = infix.charAt(i);
            //if the scanned character is a number, append it to the output string
            if(Character.isDigit(current_character))
            {
                output += current_character;
            }
            //if the current character is ')' pop the stack and append to the output
            //until a '(' is encountered and discard both parenthesis
            else if(current_character == ')')
            {
                int j = 0; //while j is equal to '0' while the top of the stack isn't '('
                //when '(' is found it is popped from the stack with out being added to
                //the output expression
                while (j == 0)
                {
                    if((char)stack.top() != '(')
                    {
                        output += stack.top();
                        stack.pop();
                    }
                    else
                    {
                        stack.pop();
                        j = 1;
                    }
                }
            }
            //if the precedence of the scanned operator is greater than the precedence
            //of the operator in the stack or the stack is empty or contains '(' or
            //the current character is '(', push it
            else if (stack.isEmpty() || precedence(current_character) > precedence((char)stack.top()) || (char)stack.top() == '(' || current_character == '(')
            {
                stack.push(current_character);
            }
            //pop all operators from the stack which are greater than or equal to in
            //precedence than that of the scanned operator and append them to the output
            //string, then push the selected character to the stack
            else
            {
                while (!stack.isEmpty() && precedence((char)stack.top()) >= precedence(current_character))
                {
                    output += stack.pop();
                }
                stack.push(current_character);
            }
        }
        //pop an append to the output any remaining content from the stack
        while(!stack.isEmpty())
        {
            output += stack.pop();
        }
        return output;
    }

    //method for checking if the string inputted by the user is a valid expression
    public static boolean isValid(String infix)
    {
        //checking if the numbers in the expression are single digit numbers
        boolean singleDigit = true;
        //a loop that goes through the inputted string, stopping at the second last
        //character so the if statement can check the current character and the next one
        for (int i = 0; i < infix.length()-1; i++)
        {
            //if the character at a certain position is a digit and the character at the
            //next position is also a digit the string is invalid
            if(Character.isDigit(infix.charAt(i)) && Character.isDigit(infix.charAt(i+1)))
            {
                singleDigit = false;
            }
        }

        boolean validChar = false; //by default the string is assumed to have invalid characters
        int correctCharacters = 0; //integer for storing the number of valid characters
        //in the inputted string
        //an array holding all the valid characters allowed in the input
        char[] validCharacters = new char[]{'0','1','2','3','4','5','6','7','8','9','+','-','*','/','^','(',')'};
        //a loop that runs through the inputted string
        for (int i = 0; i < infix.length(); i++)
        {
            //loop that runs through the array of valid characters
            for (int j = 0; j < validCharacters.length; j++)
            {
                //if the current character selected in the string matches any of the
                //characters in the array then 1 is added to the number of correct characters
                if(infix.charAt(i) == validCharacters[j])
                {
                    correctCharacters ++;
                }
            }
        }
        //if the number of correct characters matches the number of characters in the string
        //validChar is set to true
        if (correctCharacters == infix.length())
        {
            validChar = true;
        }

        if(infix.length() < 3 || infix.length() > 20)
            return false;
            //if the first character isn't a digit or '(' it's an invalid input
        else if(!Character.isDigit(infix.charAt(0)) && infix.charAt(0) != '(')
            return false;
            //if a multi-digit number is inputted it is an invalid input
        else if(!singleDigit)
            return false;
            //if the character isn't in the set of accepted character the input is invalid
        else if(!validChar)
            return false;
        else
            return true;
    }

    //method that sets the precedence of the operators
    public static int precedence(char c)
    {
        if(c == '+' || c == '-')
        {
            return 1;
        }
        else if(c == '*' || c == '/')
        {
            return 2;
        }
        else
        {
            return 3;
        }
    }

    //method for evaluation of postfix expressions
    public static void solve(String postfix, ArrayStack ans)
    {
        char postArr[] = new char[100];

        for(int i = 0; i < postfix.length(); i++)
        {
            postArr[i] = postfix.charAt(i);
        }
        //loop that iterates through the postfix expression
        for (int i = 0; i < postArr[i] ; i++)
        {
            char c = postArr[i];
            //if the element is an operand (number) push it to the stack
            if(Character.isDigit(c))
            {
                double num = Character.getNumericValue(c);
                ans.push(num);
            }
            else
            {
                //casting the objects cast from the stack to the double data type
                double op1 = (double)ans.pop();
                double op2 = (double)ans.pop();
                //switch statement to determine what operation to preform
                switch(c)
                {
                    case '+':
                        ans.push(op2 + op1);
                        break;

                    case '-':
                        ans.push(op2 - op1);
                        break;

                    case '/':
                        ans.push(op2 / op1);
                        break;

                    case '*':
                        ans.push(op2 * op1);
                        break;

                    case '^':
                        ans.push(Math.pow(op2, op1));
                        break;


                }
            }
        }
        //print the final result
        System.out.println("Result: " + ans.top());
    }
}

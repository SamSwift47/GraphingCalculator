import java.util.*;

public class Calculator
{
	public static Scanner kbd = new Scanner(System.in);

	public static Stack <Double> numSt = new Stack <Double>();
	public static Stack <String> opSt = new Stack <String>();

	public static Stack <Integer> prSt = new Stack <Integer>();
	public static int priority = 0;

	public static double preAns = 0;
	public static double ans = 0;

	public static void main(String[] args)
	{
		while(true)
		{
			String input = kbd.nextLine();
			input = prettify(input);
			//System.out.println(input);
			resolve(input);
			preAns = ans;
			ans = numSt.peek();
			System.out.println(numSt.peek());
			priority = 0;
		}
	}

	public static String prettify(String input)
	{
		input = input.replaceAll("\\s","");
		input = input.toLowerCase();
		if(input.charAt(input.length() - 1) == '=')
		{
			input = input.substring(0, input.length() - 1);
		}
		for(int i = 1; i < input.length(); i++)
		{
			if(input.charAt(i) == '(' && (input.charAt(i - 1) == ')'))
			{
				input = input.substring(0, i) + "*" + input.substring(i, input.length());
			}
			if(input.charAt(i) == '-' && (importance("" + input.charAt(i - 1)) % 2) != 0)
			{
				input = input.substring(0, i) + "n" + input.substring(i + 1, input.length());
			}
			if(input.charAt(i) == '(' && i > 0 && isNumber(input.charAt(i - 1)))
			{
				input = input.substring(0, i) + "*" + input.substring(i, input.length());
			}
			if(input.charAt(i) == ')' && i < input.length() - 1 && isNumber(input.charAt(i + 1)))
			{
				input = input.substring(0, i + 1) + "*" + input.substring(i + 1, input.length());
			}
			if(!isNumber(input.charAt(i)) && isNumber(input.charAt(i - 1)) && (importance(nextOperator(input, i)) == 15 || importance(nextOperator(input, i)) == 18))
			{
				input = input.substring(0, i) + "*" + input.substring(i, input.length());
			}
		}
		if(input.charAt(0) == '-' && numSt.size() == 0)
		{
			input = "0-" + input.substring(1, input.length());
//			System.out.println("Found Negative at the start");
		}

		return input;
	}

	public static void resolve(String input)
	{
		double num = -1;
		for(int i = 0; i < input.length(); i++)
		{
			if(isNumber(input.charAt(i)))
			{
//				System.out.println(i + " is a number.");
				i = i + nextNum(input, i).length() - 1;
			}
			else if(input.charAt(i) == '(')
			{
				priority++;
			}
			else if(input.charAt(i) == ')')
			{
				priority--;
			}
			else
			{
//				System.out.println(i + " is an operator.");
				String op = nextOperator(input, i);
				i = i + op.length() - 1;
//				System.out.println("Next operator: " + op);
				while(importance(op) > 0 && (opSt.size() != 0 && (prSt.peek() > priority || (prSt.peek() == priority && importance(opSt.peek()) >= importance(op) && !(op.equals("^") && opSt.peek().equals("^"))))))
				{
//					System.out.println("pop " + opSt.peek() + " in while loop");
//					System.out.println("Stack: " + opSt.toString().replaceAll("\\[", "").replaceAll("]", ""));
					compute(opSt.pop());
//					System.out.println("Priority: " + prSt.peek());
					prSt.pop();
				}
				opSt.push(op);
				prSt.push(priority);
//				System.out.println("Priority: " + prSt.peek());
//				System.out.println("Push " + op);
			}
		}
		while(opSt.size() > 0)
		{
//			System.out.println("pop " + opSt.peek() + " at the end");
//			System.out.println(numSt.toString());
			compute(opSt.pop());
		}
	}

	public static String nextNum(String input, int index)
	{
		String num = "";
		for(int i = index; i < input.length() && (isNumber(input.charAt(i))); i++)
		{
			num = input.substring(index, i + 1);
		}
		numSt.push(Double.parseDouble(num));
//		System.out.println(numSt.peek() + " Stored.");
//		System.out.println("Index = " + index);
		return num;
	}

	public static String nextOperator(String input, int index)
	{
		String op = "";
		for(int i = index; i < input.length(); i++)
		{
			op = input.substring(index, i + 1);
//			System.out.println(op);
			if(importance(op) > 0)
			{
				break;
			}
		}
		return op;
	}

	public static int importance(String op)
	{
		switch(op)
		{
			//Operators that need n in stead of - after them should odd
			//The rest shoud be even.

			case("("): return -1;
			case("="): return 1;
			case("+"): return 3;
			case("-"): return 5;
			case("*"): return 7;
			case("/"): return 9;
			case("n"): return 11;
			case("^"): return 13;
			case("sin"): return 15;
			case("cos"): return 15;
			case("tan"): return 15;
			case("ln"): return 15;
			case("log"): return 15;
			case("abs"): return 15;
			case("sqrt"): return 15;
			case("r"): return 17;
			case("d"): return 17;
			case("!"): return 18;
			case("pi"): return 18;
			case("e"): return 18;
		}
		return 0;
	}

	public static boolean isNumber(char num)
	{
		if((num >= '0' && num <= '9') || num == '.')
		{
			return true;
		}
		return false;
	}

	public static void compute(String input)
	{
		switch(input)
		{
			case(""):
				break;

			case("="): equals();
				break;

			case("+"): add();
				break;

			case("-"): subtract();
				break;

			case("*"): multply();
				break;

			case("/"): divide();
				break;

			case("^"): power();
				break;

			case("n"): negative();
				break;

			case("!"): factorial();
				break;

			case("pi"): numSt.push(Math.PI);
				break;

			case("sin"): numSt.push(Math.sin(numSt.pop()));
				break;

			case("cos"): numSt.push(Math.cos(numSt.pop()));
				break;

			case("tan"): numSt.push(Math.tan(numSt.pop()));
				break;

			case("ln"): numSt.push(Math.log(numSt.pop()));
				break;

			case("log"): numSt.push(Math.log10(numSt.pop()));
				break;

			case("abs"): numSt.push(abs(numSt.pop()));
				break;

			case("sqrt"): numSt.push(Math.pow(numSt.pop(),(1.0/2.0)));
				break;

			case("d"): numSt.push(Math.toRadians(numSt.pop()));
				break;

			case("e"): numSt.push(Math.E);
				break;

			case("r"):
				break;

			default: numSt.push(Double.parseDouble(input));
		}
	}

	public static void equals()
	{
		if(numSt.size() >= 2)
		{
			double a = numSt.pop();
			if(a == numSt.peek())
			{
				//System.out.println(a + " = " + a);
				System.out.println(true);
			}
			else
			{
				System.out.println(a + " != " + numSt.peek());
				System.out.println(false);
				numSt.push(a);
			}
		}
	}

	public static void add()
	{
		double a = numSt.pop();
		double b = numSt.pop();
//		System.out.println(a + " + " + b + " = " + (a + b));
		numSt.push(a + b);
	}

	public static void subtract()
	{
		double b = numSt.pop();
		double a = numSt.pop();
//		System.out.println(a + " - " + b + " = " + (a - b));
		numSt.push(a - b);
	}

	public static void multply()
	{
		double b = numSt.pop();
		double a = numSt.pop();
//		System.out.println(a + " * " + b + " = " + (a * b));
		numSt.push(a * b);
	}

	public static void divide()
	{
		double b = numSt.pop();
		double a = numSt.pop();
//		System.out.println(a + " / " + b + " = " + (a / b));
		numSt.push(a / b);
	}

	public static void power()
	{
		double b = numSt.pop();
		double a = numSt.pop();
//		System.out.print(a + " ^ " + b + " = " + Math.pow(a, b));
		numSt.push(Math.pow(a, b));
	}

	public static void negative()
	{
		double a = numSt.pop();
		a = -1 * a;
//		System.out.println("Now negative " + a);
		numSt.push(a);
//		System.out.println(numSt.peek());
	}

	public static void factorial()
	{
		double a = numSt.pop();
		double ret = 1;
		for(int i = 2; i <= abs(a); i++)
		{
			ret = ret * i;
		}
		numSt.push(ret);
	}

	public static double abs(double n)
	{
		if(n < 0)
		{
			return -n;
		}
		return n;
	}
}
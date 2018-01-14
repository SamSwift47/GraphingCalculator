import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class Graph extends Panel implements KeyListener
{
	public double width = 40; //With repersents the width that the graph will show (Changes when zooming)
	public Point center; //Centre is the center of the screen
	public Point origin = new Point(0.0, 0.0); // Origin is the reference point that is used to convert all of the numbers

	public int points = 2000; // A variable repersenting the number of points the calculator will graph

	public double round = 100000000; //This is how many decimal places the graph will round to

	public ArrayList<String> functions; //This contains a list of functions
	public double[][] yValues; //Containes a list of yValues for the respecitve functions

	public Color[] spectrum = new Color[]{Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW}; //This is a list of colours so that different graph are different colours

	double seperation; //this is the width between gridlines

	Dimension d; //this is the width and height of the pannel in pixels

	public Graph()
	{
		addKeyListener(this);
	}

	public void clear()
	{
		//This initilizes the graph
		yValues = new double[16][points + 1];
		functions = new ArrayList<String>();
		width = 40;
		center = new Point(0, 0);
	}

	public void add(String f)
	{
		//Adds a function to the graph
		if(functions.size() == 0 || !(functions.contains(f)) && functions.size() < yValues.length)
		{
			functions.add(f);
			Calc gr = new Calc(f, points, center, width);
			yValues[functions.size() - 1] = gr.yValues;
		}
	}

	public void update()
	{
		//recalculates all of the y values if the zoom or position of the graph changes
		for(int i = 0; i < functions.size(); i++)
		{
			Calc gr = new Calc(functions.get(i), points, center, width);
			yValues[i] = gr.yValues;
		}
	}

	public void paint(Graphics g)
	{
		//Paints gridlines, numbers and graphs the fucntion
		d = getSize();

		double aspectRatio = d.width / d.height;

		setBackground(Color.WHITE);
		//Gridlines
		//Fixes the width of gridlines to a range so that as you zoom there are still an apropriate number of gridlines
		seperation = 1;
		while(width / 10 < seperation )
		{
			seperation = seperation / 10;
		}
		while(width / 50 > seperation)
		{
			seperation = seperation * 10;
		}

		//System.out.println(width + " " + seperation);
		//Goes through and draws gridlines for x and y axis
		origin.convert(d, width, center);
		for(double i = center.x - width / 2 - ((center.x - width / 2) % seperation) - seperation; i < center.x + width / 2; i = i + seperation)
		{
			i = ((Math.round(i * round)) / round);
			g.setColor(Color.LIGHT_GRAY);
			if(i % (seperation * 10) == 0)
			{
				g.setColor(Color.DARK_GRAY);
			}
			if(i == 0)
			{
				g.setColor(Color.BLACK);
			}
			//System.out.println("DREW A LINE!!!!!!!!!!");
			Point top = new Point(i, center.y + width / 2);
			Point bottom = new Point(i, center.y - width / 2);
			top.convert(d, width, center);
			bottom.convert(d, width, center);
			g.drawLine(top.X, top.Y, bottom.X, bottom.Y);
			g.drawString("" + ((Math.round(top.x * round)) / round), top.X, origin.Y);
		}

		for(double i = center.y - width / 2 - ((center.y - width / 2) % seperation) - seperation; i < center.y + width / 2; i = i + seperation)
		{
			i = ((Math.round(i * round)) / round);
			g.setColor(Color.LIGHT_GRAY);
			if(i % (seperation * 10) == 0)
			{
				g.setColor(Color.DARK_GRAY);
			}
			if(i == 0)
			{
				g.setColor(Color.BLACK);
			}
			Point left = new Point(center.x - width / 2, i);
			Point right = new Point(center.x + width / 2, i);
			left.convert(d, width, center);
			right.convert(d, width, center);
			g.drawLine(left.X, left.Y, right.X, right.Y);
			g.drawString("" + ((Math.round(left.y * round)) / round), origin.X, left.Y);
		}


		//Graphs the function (Connects adjacent pointson the graph)
		for(int i = 0; i < functions.size(); i++)
		{
			g.setColor(spectrum[i % spectrum.length]);
			for(int j = 0; j < points; j++)
			{
				Point prev = new Point(center.x - width / 2 + (width / points * j), yValues[i][j]);
				Point curr = new Point(center.x - width / 2 + (width / points * (j+ 1)), yValues[i][j + 1]);
				prev.convert(d, width, center);
				curr.convert(d, width, center);
				//if(!Double.isNaN(prev.Y) || !Double.isNaN(curr.Y))
				//{
				g.drawLine(prev.X, prev.Y, curr.X, curr.Y);
				//}
			}
			//printArr(yValues[i]);
		}

	}
	//prints array only used for testing
	private void printArr(double[] arr)
	{
		for(double i = 0; i < arr.length; i++)
		{
			double X = center.x - width / 2 + (i / points * width);
			System.out.println("At x = " + X + " y = " + yValues[0][(int)i]);
		}
	}

	//scrolls horizontally
	public void scrollX(double distance)
	{
		Point temp = center;
		center.setX(center.x + distance);
		center.convert(d, width, center);
		update();
		repaint();
	}

	//Scrolls Vertically
	public void scrollY(double distance)
	{
		Point temp = center;
		center.setY(temp.y + distance);
		center.convert(d, width, temp);
		update();
		repaint();
	}

	//Zooms in and out
	public void zoom(double factor)
	{
		if(width > (10 / round) || factor < 1)//A limit so that rounding does not cause errors
		{
			width = width / factor;
			update();
			repaint();
		}
	}

	public void keyPressed(KeyEvent ke)
	{
		//System.out.println(ke.getKeyChar());
		//System.out.println(ke.getKeyCode());
		int key = ke.getKeyCode();
		switch(key)
		{
			case(37): scrollX(-seperation);
				break;

			case(38): scrollY(seperation);
				break;

			case(39): scrollX(seperation);
				break;

			case(40): scrollY(-seperation);
				break;

			case(45): zoom(1/1.25);
				break;

			case(61): zoom(1.25);
				break;
		}
	}

	public void keyReleased(KeyEvent ke)
	{
	}

	public void keyTyped(KeyEvent ke)
	{
	}
}
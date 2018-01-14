import java.awt.*;

public class Point
{
	public double x = 0;
	public double y = 0;

	public int X = 0;
	public int Y = 0;

	public Point()
	{
	}

	public Point(double a, double b)
	{
		x = a;
		y = b;
	}

	public void setX(double n)
	{
		x = n;
	}

	public void setY(double n)
	{
		y = n;
	}

	public void convert(Dimension d, double width, Point origin)
	{
		X = (int)((x - origin.x) * d.width / width) + d.width / 2;
		Y = (int) (-1 * (y - origin.y) * d.width / width) + d.height / 2;
	}

	private double abs(double n)
	{
		if(n < 1)
		{
			return -n;
		}
		return n;
	}

	private double sgn(double n)
	{
		if(n > 0)
		{
			return 1;
		}
		if(n < 0)
		{
			return -1;
		}
		return 0;
	}
}
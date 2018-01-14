import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class WorkPanel extends Panel
{
	Graph graph = new Graph();
	Control control = new Control(this);

	BorderLayout layout = new BorderLayout();

	public WorkPanel()
	{
		this.setLayout(layout);
		this.add(graph, BorderLayout.CENTER);
		this.add(control, BorderLayout.SOUTH);
		clear();
	}

	public void display(String f)
	{
		graph.add(f);
		graph.repaint();
	}

	public void clear()
	{
		graph.clear();
		graph.repaint();
	}
}
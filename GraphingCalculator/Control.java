import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class Control extends Panel implements ActionListener, KeyListener
{
	Label func;
	TextField txt;
	Button enter;
	Button clear;
	WorkPanel parent;

	public Control(WorkPanel p)
	{
		parent = p ;

		func = new Label("f(x) =");
		this.add(func);

		txt = new TextField("", 32);
		txt.addKeyListener(this);
		this.add(txt);

		enter = new Button("Graph");
		enter.addActionListener(this);
		this.add(enter);

		clear = new Button("Clear");
		clear.addActionListener(this);
		this.add(clear);

		addKeyListener(this);
	}

	public void paint(Graphics g)
	{
		setBackground(Color.BLUE);
	}

	public void actionPerformed(ActionEvent e)
	{
		Object o = e.getSource();
		String f = txt.getText();
		if(o == (Object) enter)
		{
			parent.display(f);
		}
		if(o == (Object) clear)
		{
			parent.clear();
		}
		//System.out.println(f);
	}

	public void keyPressed(KeyEvent ke)
	{
		//System.out.println(ke.getKeyChar());
		//System.out.println(ke.getKeyCode());
		String f = txt.getText();
		int key = ke.getKeyCode();
		if(key == 10)
		{
			parent.display(f);
		}
	}

	public void keyReleased(KeyEvent ke)
	{
	}

	public void keyTyped(KeyEvent ke)
	{
	}
}
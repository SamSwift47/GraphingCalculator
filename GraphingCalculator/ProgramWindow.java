import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class ProgramWindow extends Frame
{
	private String title = "Calculator" ;

	TextField txt = new TextField();

	WorkPanel panel = new WorkPanel();

	public ProgramWindow()
  	{
		setTitle(title);
  		setSize(1024, 800);
		setLocation(100, 100);
		setResizable(true);
	    add(panel);
	  	setVisible(true);
	}

}
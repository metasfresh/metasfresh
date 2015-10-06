/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.apps;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *  Applet Start
 *
 *  @author Jorg Janke
 *  @version  $Id: AStart.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public final class AStart extends JApplet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2871413592908347578L;
	boolean isStandalone = false;

	/**
	 *  Get a parameter value
	 */
	public String getParameter(String key, String def)
	{
		return isStandalone ? System.getProperty(key, def) :
			(getParameter(key) != null ? getParameter(key) : def);
	}

	/**
	 *  Construct the applet
	 */
	public AStart()
	{
	}

	/**
	 *  Initialize the applet
	 */
	public void init()
	{
		try
		{
			jbInit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 *  Component initialization
	 */
	private void jbInit() throws Exception
	{
		this.setSize(new Dimension(400,300));
	}

	/**
	 * Start the applet
	 */
	public void start()
	{
	}

	/**
	 * Stop the applet
	 */
	public void stop()
	{
	}

	/**
	 * Destroy the applet
	 */
	public void destroy()
	{
	}

	/**
	 * Get Applet information
	 */
	public String getAppletInfo()
	{
		return "Start Applet";
	}

	/**
	 *  Get parameter info
	 */
	public String[][] getParameterInfo()
	{
		return null;
	}

	public void setStandAlone(boolean ok)
	{
		isStandalone=ok;
	}
	/**
	 *  Main method
	 */
	public static void main(String[] args)
	{
		AStart applet = new AStart();
		applet.isStandalone = true;
		JFrame frame = new JFrame();
		//EXIT_ON_CLOSE == 3
		frame.setDefaultCloseOperation(3);
		frame.setTitle("Start Applet");
		frame.getContentPane().add(applet, BorderLayout.CENTER);
		applet.init();
		applet.start();
		frame.setSize(400,320);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((d.width - frame.getSize().width) / 2, (d.height - frame.getSize().height) / 2);
		frame.setVisible(true);
	}

	//static initializer for setting look & feel
	static
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			//UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		}
		catch(Exception e)
		{
		}
	}
}   //  AStert

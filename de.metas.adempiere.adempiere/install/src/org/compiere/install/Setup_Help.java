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
package org.compiere.install;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

import org.compiere.apps.OnlineHelp;
import org.compiere.swing.CPanel;

/**
 *	Setup Online Help
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: Setup_Help.java,v 1.2 2006/07/30 00:57:42 jjanke Exp $
 */
public class Setup_Help extends JDialog implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7299630217823615456L;


	/**
	 * 	Constructor
	 * 	@param parent parent frame
	 */
	public Setup_Help (Frame parent)
	{
		super (parent, true);
		init(parent);
	}	//	Setup_Help

	/**
	 * 	Constructor
	 * 	@param parent parent dialog
	 */
	public Setup_Help (Dialog parent)
	{
		super (parent, true);
		init(parent);
	}	//	Setup_Help

	/**
	 * 	Constructor init
	 * 	@param parent parent window
	 */
	private void init (Window parent)
	{
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try
		{
			jbInit();
			dynInit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		Dimension dlgSize = getPreferredSize();
		Dimension frmSize = parent.getSize();
		Point loc = parent.getLocation();
		setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
		try
		{
			pack();
			setVisible(true);	//	HTML load errors
		}
		catch (Exception ex)
		{
		}
	}	//	init


	static ResourceBundle res = ResourceBundle.getBundle("org.compiere.install.SetupRes");
	private CPanel mainPanel = new CPanel();
	private CPanel southPanel = new CPanel();
	private JButton bOK = new JButton();
	private BorderLayout mainLayout = new BorderLayout();
	private JScrollPane centerScrollPane = new JScrollPane();
	private JEditorPane editorPane = new OnlineHelp();


	/**
	 * 	Static layout
	 *  @throws Exception
	 */
	private void jbInit() throws Exception
	{
		//imageLabel.setIcon(new ImageIcon(SetupFrame_AboutBox.class.getResource("[Your Image]")));
		this.setTitle(res.getString("AdempiereServerSetup") + " " + res.getString("Help"));
		mainPanel.setLayout(mainLayout);
		bOK.setText(res.getString("Ok"));
		bOK.addActionListener(this);
		centerScrollPane.setPreferredSize(new Dimension(600, 400));
		this.getContentPane().add(mainPanel, null);
		southPanel.add(bOK, null);
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		setResizable(true);
		mainPanel.add(centerScrollPane, BorderLayout.CENTER);
		centerScrollPane.getViewport().add(editorPane, null);
	}	//	jbInit

	/**
	 * 	Set Content
	 */
	private void dynInit()
	{
		try
		{
			editorPane.setPage("http://www.adempiere.com/wiki/index.php/ServerSetupHelp");
		}
		catch (IOException ex)
		{
			editorPane.setText(res.getString("PleaseCheck")
				+ "	http://www.adempiere.org/support <p>("
				+ res.getString("UnableToConnect") + ")");
		}
	}	//	dynInit

	/**
	 * 	Close Dialog if closing
	 *  @param e event
	 */
	protected void processWindowEvent(WindowEvent e)
	{
		if (e.getID() == WindowEvent.WINDOW_CLOSING)
			dispose();
		super.processWindowEvent(e);
	}	//	processWindowEvent

	/**
	 * 	Action Listener
	 * 	@param e event
	 */
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == bOK)
			dispose();
	}	//	actionPerformed

}	//	Setup_Help

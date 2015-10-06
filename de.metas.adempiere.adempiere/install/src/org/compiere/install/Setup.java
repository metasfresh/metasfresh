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
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import org.compiere.Adempiere;
import org.compiere.apps.AEnv;
import org.compiere.swing.CFrame;
import org.compiere.swing.CMenuItem;
import org.compiere.util.CLogMgt;
import org.compiere.util.CLogger;

/**
 * Setup Dialog Frame.
 *
 * @author Jorg Janke
 * @version $Id: Setup.java,v 1.2 2006/07/30 00:57:42 jjanke Exp $
 */
public class Setup extends CFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1916571473738628986L;

	/**
	 * Constructor
	 */
	public Setup()
	{
		CLogger.get().info(Adempiere.getSummaryAscii());
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// addWindowListener(this);
		try
		{
			jbInit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		/** Init Panel **/
		AEnv.showCenterScreen(this);
		try
		{
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			configurationPanel.dynInit();
			AEnv.positionCenterScreen(this);
			setCursor(Cursor.getDefaultCursor());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}	// Setup

	// Static UI
	static ResourceBundle res = ResourceBundle.getBundle("org.compiere.install.SetupRes");
	private JPanel contentPane;
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menuFile = new JMenu();
	private CMenuItem menuFileExit = new CMenuItem();
	private JMenu menuHelp = new JMenu();
	private CMenuItem menuHelpInfo = new CMenuItem();
	private JLabel statusBar = new JLabel();
	private BorderLayout borderLayout = new BorderLayout();
	private ConfigurationPanel configurationPanel = new ConfigurationPanel(statusBar);

	/**
	 * Static Init
	 * 
	 * @throws Exception
	 */
	private void jbInit() throws Exception
	{
		this.setIconImage(Adempiere.getProductIconSmall());
		contentPane = (JPanel)this.getContentPane();
		contentPane.setLayout(borderLayout);

		this.setTitle(res.getString("AdempiereServerSetup"));
		statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
		statusBar.setText(" ");
		menuFile.setText(res.getString("File"));
		menuFileExit.setText(res.getString("Exit"));
		menuFileExit.addActionListener(this);
		menuHelp.setText(res.getString("Help"));
		menuHelpInfo.setText(res.getString("Help"));
		menuHelpInfo.addActionListener(this);
		borderLayout.setHgap(5);
		borderLayout.setVgap(5);
		menuFile.add(menuFileExit);
		menuHelp.add(menuHelpInfo);
		menuBar.add(menuFile);
		menuBar.add(menuHelp);
		this.setJMenuBar(menuBar);
		contentPane.add(statusBar, BorderLayout.SOUTH);
		contentPane.add(configurationPanel, BorderLayout.CENTER);
	}	// jbInit

	/**
	 * ActionListener
	 * 
	 * @param e event
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == menuFileExit)
			System.exit(0);
		else if (e.getSource() == menuHelpInfo)
			new Setup_Help(this);
	}	// actionPerformed

	/**
	 * Start
	 * 
	 * @param args Log Level e.g. ALL, FINE
	 */
	public static void main(String[] args)
	{
		CLogMgt.initialize(true);
		CLogMgt.getFileLogger()
				.setLogDirectory(System.getProperty("user.dir") + File.separator + "log")
				.setEnabled(true);

		// Log Level
		if (args.length > 0)
			CLogMgt.setLevel(args[0]);
		else
			CLogMgt.setLevel(Level.INFO);

		new Setup();
	}	// main

}	// Setup

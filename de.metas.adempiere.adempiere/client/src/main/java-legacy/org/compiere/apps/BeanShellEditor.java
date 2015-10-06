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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;

import org.compiere.model.MUser;
import org.compiere.model.Scriptlet;
import org.compiere.swing.CDialog;
import org.compiere.swing.CPanel;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import bsh.ParseException;

/**
 *  Bean Shell Editor
 *
 *  @author     Jorg Janke
 *  @version    $Id: ScriptEditor.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public class BeanShellEditor extends CDialog implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5134274235147001140L;
	private Frame m_owner;

	/**
	 *  Minimum Constructor
	 */
	public BeanShellEditor()
	{
		this (Msg.getMsg(Env.getCtx(), "Script"), null, 0);
	}   //  ScriptEditor

	/**
	 *  Minimum Constructor
	 */
	public BeanShellEditor(Frame owner)
	{
		this (owner, Msg.getMsg(Env.getCtx(), "Script"), null, 0);
	}   //  ScriptEditor
	
	/**
	 *  Constructor
	 *
	 *  @param title Field Name
	 *  @param script The Script
	 */
	public BeanShellEditor (String title, Scriptlet script, int WindowNo)
	{
		this(null, title, script, WindowNo);
	}
	
	/**
	 *  Constructor
	 *
	 *	@param owner Owner frame
	 *  @param title Field Name
	 *  @param script The Script
	 */
	public BeanShellEditor (Frame owner, String title, Scriptlet script, int WindowNo)
	{
		super(owner);		
		setTitle(title);
		if (owner != null)
		{
			setModal(true);
		}
		m_owner = owner;
		m_WindowNo = WindowNo;
		if (m_WindowNo == 0)
			m_WindowNo = Env.createWindowNo(this);
		log.info("Window=" + m_WindowNo);
		try
		{
			jbInit();
			setScript (script);
			dynInit();
			AEnv.showCenterScreen(this);
			toFront();
		}
		catch(Exception ex)
		{
			log.log(Level.SEVERE, "", ex);
		}
	}   //  ScriptEditor

	/** The Script      */
	private Scriptlet   m_script;
	/** WindowNo        */
	private int         m_WindowNo;
	/** Original Script */
	private String      m_origScript;
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(BeanShellEditor.class);
	//  --

	private CPanel mainPanel = new CPanel();
	private BorderLayout borderLayout1 = new BorderLayout();
	private JScrollPane editorPane = new JScrollPane();
	private JTextArea editor = new JTextArea();
	private JScrollPane variablesPane = new JScrollPane();
	private JTextPane variables = new JTextPane();
	private TitledBorder titledBorder1;
	private TitledBorder titledBorder2;
	private CPanel northPanel = new CPanel();
	private CPanel southPanel = new CPanel();
	private BorderLayout southLayout = new BorderLayout();
	private CPanel okPanel = new CPanel();
	private JButton bOK = ConfirmPanel.createOKButton(true);
	private JButton bCancel = ConfirmPanel.createCancelButton(true);
	private CPanel resultPanel = new CPanel();
	private JButton bProcess = ConfirmPanel.createProcessButton(true);
	private JButton bValidate = ConfirmPanel.createCustomizeButton("Validate");
	private JLabel lResult = new JLabel();
	private JTextField fResult = new JTextField();
	private CPanel resultVariablePanel = new CPanel();
	private BorderLayout northLayout = new BorderLayout();
	private JLabel lResultVariable = new JLabel();
	private JTextField fResultVariable = new JTextField();
	private CPanel helpPanel = new CPanel();
	private JButton bHelp = ConfirmPanel.createHelpButton(true);
	private GridBagLayout resultVariableLayout = new GridBagLayout();
	private FlowLayout okLayout = new FlowLayout();
	private GridBagLayout resultLayout = new GridBagLayout();
	private JSplitPane centerPane = new JSplitPane();

	/**
	 *  Static Layout
	 *  @throws Exception
	 */
	void jbInit() throws Exception
	{
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),
			Msg.getMsg(Env.getCtx(), "ScriptVariables"));
		titledBorder2 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),
			Msg.getMsg(Env.getCtx(), "ScriptEditor"));
		mainPanel.setLayout(borderLayout1);
		editor.setLineWrap(true);
		editor.setTabSize(4);
		editor.setWrapStyleWord(true);
		bOK.addActionListener(this);
		bCancel.addActionListener(this);
		bHelp.addActionListener(this);
		bProcess.addActionListener(this);
		bValidate.addActionListener(this);
		variables.setBackground(Color.lightGray);
		variables.setEditable(false);
		variables.setContentType("text/html");
		variablesPane.setBorder(titledBorder1);
		editorPane.setBorder(titledBorder2);
		centerPane.setPreferredSize(new Dimension(500, 500));
		centerPane.setLeftComponent(editorPane);
		centerPane.setRightComponent(variablesPane);
		southPanel.setLayout(southLayout);
		resultPanel.setLayout(resultLayout);
		lResult.setText(Msg.getMsg(Env.getCtx(), "ScriptResult"));
		fResult.setBackground(Color.lightGray);
		fResult.setEditable(false);
		fResult.setText("");
		northPanel.setLayout(northLayout);
		lResultVariable.setText(Msg.getMsg(Env.getCtx(), "ScriptResultVariable"));
		fResultVariable.setBackground(Color.lightGray);
		fResultVariable.setEditable(false);
		resultVariablePanel.setLayout(resultVariableLayout);
		okPanel.setLayout(okLayout);
		northPanel.add(resultVariablePanel, BorderLayout.CENTER);
		getContentPane().add(mainPanel);
		editorPane.getViewport().add(editor, null);
		variablesPane.getViewport().add(variables, null);
		mainPanel.add(northPanel, BorderLayout.NORTH);
		mainPanel.add(southPanel,  BorderLayout.SOUTH);
		southPanel.add(okPanel,  BorderLayout.EAST);
		okPanel.add(bCancel, null);
		okPanel.add(bOK, null);
		southPanel.add(resultPanel,  BorderLayout.CENTER);
		resultPanel.add(bValidate,   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
				,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		resultPanel.add(bProcess,   new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		resultPanel.add(lResult,   new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		resultPanel.add(fResult,   new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		mainPanel.add(centerPane,  BorderLayout.CENTER);
		resultVariablePanel.add(lResultVariable,   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		resultVariablePanel.add(fResultVariable,   new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
			,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		northPanel.add(helpPanel,  BorderLayout.EAST);
		helpPanel.add(bHelp, null);
		centerPane.setDividerLocation(350);
	}   //  jbInit

	/**
	 *  Set Script
	 *  @param script The Script
	 */
	public void setScript (Scriptlet script)
	{
		if (script == null)
			m_script = new Scriptlet (Scriptlet.VARIABLE, ";", Env.getCtx(), m_WindowNo);
		else
			m_script = script;
		//
		fResultVariable.setText(m_script.getVariable());
		m_origScript = m_script.getScript();
		editor.setText(m_script.getScript());
		//
		StringBuffer sb = new StringBuffer("<HTML><BODY>");
		HashMap<String,Object> ctx = m_script.getEnvironment();
		String[] pp = new String[ctx.size()];
		ctx.keySet().toArray(pp);
		Arrays.sort(pp);
		for (int i = 0; i < pp.length; i++)
		{
			String key = pp[i].toString();
			Object value = ctx.get(key);
			sb.append("<font color=").append('"').append(getColor(value)).append('"').append(">")
				.append(key)
				.append(" (")
				.append(value)
				.append(")</font><br>");
		}
		sb.append("</BODY></HTML>");
		variables.setText(sb.toString());
		variables.setCaretPosition(0);
	}   //  setScript

	/**
	 *  Get Color Code
	 *  @param value the object
	 *  @return HTML color code
	 */
	private String getColor (Object value)
	{
		if (value instanceof String)
			return "#009900";   //  "green";
		else if (value instanceof Integer)
			return "#0000FF";   //  "blue";
		else if (value instanceof Double)
			return "#00FFFF";   //  "cyan";
		else if (value instanceof Timestamp)
			return "#FF00FF";   //  "magenta";
		else if (value instanceof Boolean)
			return "#FF9900";   //  "orange";
		return     "#FF0000";   //  "red";
	}   //  getColor

	/**
	 *  Dynamic Init
	 */
	private void dynInit()
	{

	}   //  dynInit


	/**
	 *  Action Listener
	 *  @param e
	 */
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == bOK)
		{
			m_script.setScript(editor.getText());
			dispose();
		}
		else if (e.getSource() == bCancel)
		{
			m_script.setScript(m_origScript);
			dispose();
		}

		else if (e.getSource() == bProcess)
			actionProcess();
		else if (e.getSource() == bValidate)
			actionValidate();
		else if (e.getSource() == bHelp)
		{
			Help h = new Help (this ,
				Msg.getMsg(Env.getCtx(), "ScriptHelp"),
				getClass().getResource("Script.html"));
			h.setVisible(true);
		}
	}	//  actionPerformed

	/**
	 *  Process Script
	 */
	private void actionProcess()
	{
		/** Example:
		import org.compiere.util.DB;
		import java.sql.*;
		PreparedStatement pstmt =DB.prepareStatement("select Name, Password from AD_User where Name like 'Super%'");
		ResultSet rs = pstmt.executeQuery();
		if (rs.next())
		{
		result = rs.getString("Name") + "; password= " + rs.getString("Password");
		}
		**/
		
		MUser user = MUser.get(Env.getCtx());
		if (!user.isAdministrator())
		{
			fResult.setText("Not Administrator");
			return;
		}
		//
		m_script.setScript(editor.getText());
		Exception e = m_script.execute();
		if (e != null)
			ADialog.error(m_WindowNo, this, "ScriptError", e.toString());
		Object result = m_script.getResult(false);
		fResult.setText(result == null ? "" : result.toString());
	}   //  actionProcess

	/**
	 *  Validate Script
	 */
	private void actionValidate()
	{
		/** Example:
		import org.compiere.util.DB;
		import java.sql.*;
		PreparedStatement pstmt =DB.prepareStatement("select Name, Password from AD_User where Name like 'Super%'");
		ResultSet rs = pstmt.executeQuery();
		if (rs.next())
		{
		result = rs.getString("Name") + "; password= " + rs.getString("Password");
		}
		**/
		
		MUser user = MUser.get(Env.getCtx());
		if (!user.isAdministrator())
		{
			fResult.setText("Not Administrator");
			return;
		}
		//
		m_script.setScript(editor.getText());
		Exception e = null;
		try {
			m_script.validate();
		} catch (ParseException e1) {
			e = e1;
		}
		if (e != null)
		{
			ADialog.error(m_WindowNo, this, "ScriptError", e.toString());
			fResult.setText("Syntax errors detected.");
		}
		else
			fResult.setText("No syntax errors detected.");
	}   //  actionValidate
	
	/*************************************************************************/
}   //  ScriptEditor

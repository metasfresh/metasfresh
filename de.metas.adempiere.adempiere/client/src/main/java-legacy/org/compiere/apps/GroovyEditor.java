/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2008 Low Heng Sin. All Rights Reserved.                	  *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.compiere.apps;

import groovy.lang.GroovyShell;
import groovy.ui.ConsoleTextEditor;

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
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.compiere.model.MUser;
import org.compiere.swing.CDialog;
import org.compiere.swing.CPanel;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Msg;

/**
 *  Groovy script Editor
 *
 *  @author Low Heng Sin
 */
public class GroovyEditor extends CDialog implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2941209813417292930L;
	private Frame m_owner;

	/**
	 *  Minimum Constructor
	 */
	public GroovyEditor()
	{
		this (Msg.getMsg(Env.getCtx(), "Script"), null, 0);
	}   //  ScriptEditor

	/**
	 *  Minimum Constructor
	 */
	public GroovyEditor(Frame owner)
	{
		this (owner, Msg.getMsg(Env.getCtx(), "Script"), null, 0);
	}   //  ScriptEditor
	
	/**
	 *  Constructor
	 *
	 *  @param title Field Name
	 *  @param script The Script
	 */
	public GroovyEditor (String title, String script, int WindowNo)
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
	public GroovyEditor (Frame owner, String title, String script, int WindowNo)
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
	private String		m_script;
	/** WindowNo        */
	private int         m_WindowNo;
	/** Original Script */
	private String      m_origScript;
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(GroovyEditor.class);
	//  --

	private CPanel mainPanel = new CPanel();
	private BorderLayout borderLayout1 = new BorderLayout();
	private JScrollPane editorPane = new JScrollPane();
	private ConsoleTextEditor editor = new ConsoleTextEditor();
	private TitledBorder titledBorder2;
	private CPanel southPanel = new CPanel();
	private BorderLayout southLayout = new BorderLayout();
	private CPanel okPanel = new CPanel();
	private JButton bOK = ConfirmPanel.createOKButton(true);
	private JButton bCancel = ConfirmPanel.createCancelButton(true);
	private CPanel resultPanel = new CPanel();
	private JButton bProcess = ConfirmPanel.createProcessButton(true);
	private JButton bHelp = ConfirmPanel.createHelpButton(true);
	private JLabel lResult = new JLabel();
	private JTextField fResult = new JTextField();
	private FlowLayout okLayout = new FlowLayout();
	private GridBagLayout resultLayout = new GridBagLayout();
	
	/**
	 *  Static Layout
	 *  @throws Exception
	 */
	void jbInit() throws Exception
	{
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		titledBorder2 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140)),
			Msg.getMsg(Env.getCtx(), "ScriptEditor"));
		mainPanel.setLayout(borderLayout1);
		bOK.addActionListener(this);
		bCancel.addActionListener(this);
		bProcess.addActionListener(this);
		bHelp.addActionListener(this);
		editorPane.setBorder(titledBorder2);
		editorPane.setPreferredSize(new Dimension(500, 500));
		southPanel.setLayout(southLayout);
		resultPanel.setLayout(resultLayout);
		lResult.setText(Msg.getMsg(Env.getCtx(), "ScriptResult"));
		fResult.setBackground(Color.lightGray);
		fResult.setEditable(false);
		fResult.setText("");
		okPanel.setLayout(okLayout);
		getContentPane().add(mainPanel);
		editorPane.getViewport().add(editor, null);
		mainPanel.add(southPanel,  BorderLayout.SOUTH);
		southPanel.add(okPanel,  BorderLayout.EAST);
		okPanel.add(bCancel, null);
		okPanel.add(bOK, null);
		southPanel.add(resultPanel,  BorderLayout.CENTER);
		resultPanel.add(bProcess,   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		resultPanel.add(lResult,   new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		resultPanel.add(fResult,   new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		resultPanel.add(bHelp,   new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
				,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		mainPanel.add(editorPane,  BorderLayout.CENTER);
	}   //  jbInit

	/**
	 *  Set Script
	 *  @param script The Script
	 */
	public void setScript (String script)
	{
		if (script == null)
			m_script = "";
		else
			m_script = script;
		//
		m_origScript = m_script;
		editor.getTextEditor().setText(m_script);
	}   //  setScript

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
			m_script = editor.getTextEditor().getText();
			dispose();
		}
		else if (e.getSource() == bCancel)
		{
			m_script = m_origScript;
			dispose();
		}

		else if (e.getSource() == bProcess)
			actionProcess();
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
		MUser user = MUser.get(Env.getCtx());
		if (!user.isAdministrator())
		{
			fResult.setText("Not Administrator");
			return;
		}
		//
		GroovyShell sh = new GroovyShell();		
		Exception e = null;
		try {
			sh.parse(editor.getTextEditor().getText());
		} catch (Exception e1) {
			e = e1;
		}
		if (e != null)
		{
			ADialog.error(m_WindowNo, this, "ScriptError", e.toString());
			fResult.setText("Syntax errors detected.");
		}
		else
			fResult.setText("No syntax errors detected.");
	}   //  actionProcess

	/*************************************************************************/

	/**
	 *  Start ScriptEditor
	 *
	 *  @param owner
	 *  @param header   Title
	 *  @param script   ScriptCode
	 *  @param editable
	 *  @return updated Script
	 */
	public static String start (Frame owner, String header, String script, boolean editable, int WindowNo)
	{
		GroovyEditor se = new GroovyEditor (owner, header, script, WindowNo);
		return se.getScript();
	}   //  start

	/***
	 * 
	 * @return string
	 */
	public String getScript() {
		return m_script;
	}

}   //  ScriptEditor

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
package org.compiere.grid.ed;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import org.slf4j.Logger;

import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.apps.ADialog;
import org.compiere.apps.AEnv;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.StatusBar;
import org.compiere.model.GridField;
import org.compiere.swing.CDialog;
import org.compiere.swing.CMenuItem;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextArea;
import org.compiere.swing.CTextPane;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.Env;

/**
 *  Editor for Text (textArea) with HTML (textPane) View
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: Editor.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class Editor extends CDialog
	implements ChangeListener, ActionListener, KeyListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8314686583942866712L;

	/**
	 *	Factory: Start Editor
	 *	@param jc container to get parent frame
	 *	@param header heading
	 *	@param text initial text
	 *	@param editable if false = r/o
	 *	@param maxSize max size
	 *	@return edited string
	 */
	public static String startEditor(Container jc, String header, String text, boolean editable, int maxSize)
	{
		//	Find frame
		JFrame frame = Env.getFrame(jc);
		String hdr = header;
		if (Check.isEmpty(hdr, true))
			hdr = Services.get(IMsgBL.class).getMsg(Env.getCtx(), "Editor");
		//	Start it
		Editor ed = new Editor(frame, hdr, text, editable, maxSize, getGridField(jc)); // metas: added last parameter: GridField
		AEnv.showCenterWindow(frame, ed);
		String s = ed.getText();
		ed = null;
		return s;
	}	//	startEditor

	/**
	 *	Minimum constructor
	 * 	@param frame parent
	 */
	public Editor(Frame frame)
	{
		this (frame, Services.get(IMsgBL.class).getMsg(Env.getCtx(), "Editor"), "", true, 0);
	}   //  Editor

	/**
	 *	Standard constructor
	 *	@param frame parent
	 *	@param header heading
	 *	@param text initial text
	 *	@param editable if false = r/o
	 *	@param maxSize max size; 0 = ignore
	 */
	public Editor(Frame frame, String header, String text, boolean editable, int maxSize)
	{
		super (frame, header, frame != null);
		try
		{
			m_maxSize = maxSize;
			jbInit();
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		}
		catch(Exception ex)
		{
			log.error("", ex);
		}
		//	Set Text
		m_text = text;
		textArea.setText(m_text);
		textArea.setEditable(editable);
		if (editable)
			textArea.setBackground(AdempierePLAF.getFieldBackground_Normal());
		else
			textArea.setBackground(AdempierePLAF.getFieldBackground_Inactive());
		textPane.setBackground(AdempierePLAF.getFieldBackground_Inactive());
		updateStatusBar();
	}	//	Editor
	
	// services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private static Logger log = LogManager.getLogger(Editor.class);

	/** The Text			*/
	private String 		m_text;
	/** Maximum Size		*/
	private int			m_maxSize = 0; 

	private CPanel panel = new CPanel();
	private BorderLayout panelLayout = new BorderLayout();
	private JTabbedPane tabbedPane = new JTabbedPane();
	private CTextArea textArea = new CTextArea();
	private CTextPane textPane = new CTextPane();
	private JMenuBar menuBar = new JMenuBar();
	private JMenu mFile = new JMenu();
	private CMenuItem mImport = new CMenuItem();
	private CMenuItem mExport = new CMenuItem();
	private ConfirmPanel confirmPanel = ConfirmPanel.builder()
			.withoutText()
			.build();
	private StatusBar statusBar = new StatusBar(false);

	/**
	 *	Static Init
	 * 	@throws Exception
	 */
	private void jbInit() throws Exception
	{
		panel.setLayout(panelLayout);
		this.setJMenuBar(menuBar);
		//	Text Tab
		textArea.setPreferredSize(new Dimension(300, 300));
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		tabbedPane.add(textArea, "Text");
		textArea.addKeyListener(this);
		//	HTML Tab
		textPane.setContentType("text/html");
		textPane.setEditable(false);
		tabbedPane.add(textPane, "HTML");
		//
		mFile.setText("File");
		mImport.setText("Import");
		mImport.addActionListener(this);
		mExport.setText("Export");
		mExport.addActionListener(this);
		tabbedPane.addChangeListener(this);
		panel.add(tabbedPane, BorderLayout.CENTER);
		confirmPanel.setActionListener(this);
		panel.add(confirmPanel, BorderLayout.SOUTH);
		this.getContentPane().add(panel, BorderLayout.CENTER);
		this.getContentPane().add(statusBar, BorderLayout.SOUTH);
		//
		menuBar.add(mFile);
		mFile.add(mImport);
		mFile.add(mExport);
		updateStatusBar();
	}	//	jbInit

	/**
	 *	ActionListener
	 * 	@param e event
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			m_text = textArea.getText();
			log.debug("OK - length=" + m_text.length());
			dispose();
		}
		else if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			dispose();
		}
		
		else if (e.getSource() == mImport)
		{
			importText();
		}
		else if (e.getSource() == mExport)
		{
			exportText();
		}
	}	//	actionPerformed

	/**
	 * 	Update Status Bar
	 */
	private void updateStatusBar()
	{
		String str = textArea.getText();
		int length = str.length();
		statusBar.setStatusDB(length);
		int size = length;
		try
		{
			size = str.getBytes("UTF-8").length;
		}
		catch (UnsupportedEncodingException e)
		{
			log.error(str, e);
		}
		
		if (m_maxSize == 0)
			statusBar.setStatusLine(String.valueOf(size));
		else if (size < m_maxSize)
			statusBar.setStatusLine(size + " < " + m_maxSize, false);
		else if (size == m_maxSize)
			statusBar.setStatusLine(size + " = " + m_maxSize, false);
		else
		{
			statusBar.setStatusLine(size + " > " + m_maxSize, true);
			ADialog.beep();
		}
	}	//	updateStatusBar
	
	/**
	 *	Get Text
	 * 	@return edited text
	 */
	public String getText()
	{
		return m_text;
	}	//	getText

	/**
	 *	Import Text from File
	 */
	private void importText()
	{
		JFileChooser jc = new JFileChooser();
		jc.setDialogTitle(msgBL.getMsg(Env.getCtx(), "ImportText"));
		jc.setDialogType(JFileChooser.OPEN_DIALOG);
		jc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		//
		if (jc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
			return;

		StringBuffer sb = new StringBuffer();
		try
		{
			InputStreamReader in = new InputStreamReader (new FileInputStream (jc.getSelectedFile()));
			char[] cbuf = new char[1024];
			int count;
			while ((count = in.read(cbuf)) > 0)
				sb.append(cbuf, 0, count);
			in.close();
		}
		catch (Exception e)
		{
			log.warn(e.getMessage());
			return;
		}
		textArea.setText(sb.toString());
		updateStatusBar();
	}	//	importText

	/**
	 *	Export Text to File
	 */
	private void exportText()
	{
		JFileChooser jc = new JFileChooser();
		jc.setDialogTitle(msgBL.getMsg(Env.getCtx(), "ExportText"));
		jc.setDialogType(JFileChooser.SAVE_DIALOG);
		jc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		//
		if (jc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
			return;

		try
		{
			BufferedWriter bout = new BufferedWriter (new OutputStreamWriter (new FileOutputStream (jc.getSelectedFile())));
			bout.write(textArea.getText());
			bout.flush();
			bout.close();
		}
		catch (Exception e)
		{
			log.warn(e.getMessage());
		}
	}	//	exportText

	/**
	 *	ChangeListener for TabbedPane
	 * 	@param e event
	 */
	@Override
	public void stateChanged(ChangeEvent e)
	{
		if (tabbedPane.getSelectedIndex() == 1)		//	switch to HTML
			textPane.setText(textArea.getText());
	}	//	stateChanged

	@Override
	public void keyTyped (KeyEvent e)
	{
	}

	@Override
	public void keyPressed (KeyEvent e)
	{
	}

	@Override
	public void keyReleased (KeyEvent e)
	{
		updateStatusBar();
	}

	// metas: begin
	public Editor(Frame frame, String header, String text, boolean editable, int maxSize, GridField gridField)
	{
		this(frame, header, text, editable, maxSize);
		BoilerPlateMenu.createFieldMenu(textArea, null, gridField);
	}
	private static final GridField getGridField(Container c)
	{
		if (c == null)
		{
			return null;
		}
		
		GridField field = null;
		if (c instanceof VEditor)
		{
			VEditor editor = (VEditor)c;
			field = editor.getField();
		}
		else
		{
			try
			{
				field = (GridField)c.getClass().getMethod("getField").invoke(c);
			}
			catch (Exception e)
			{
				final AdempiereException ex = new AdempiereException("Cannot get GridField from " + c, e);
				log.warn(ex.getLocalizedMessage(), ex);
			}
		}
		return field;
	}
	// metas: end
}	//	Editor

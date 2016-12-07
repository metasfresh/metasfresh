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

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

import org.compiere.apps.ADialog;
import org.compiere.apps.StatusBar;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;
import org.compiere.swing.CPanel;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.compiere.util.ValueNamePair;

/**
 *	Translation Dialog Import + Export.
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: TranslationDialog.java,v 1.3 2006/07/30 00:51:28 jjanke Exp $
 */
public class VTranslationDialog extends TranslationController
	implements FormPanel, ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5072470836657762574L;
	
	private CPanel panel = new CPanel();


	/**
	 *	TranslationDialog Constructor.
	 * 	(Initiated via init())
	 */
	public VTranslationDialog()
	{
	}	//	TranslationDialog

	/**	FormFrame			*/
	private FormFrame 		m_frame;
	/**	Logger			*/
	private static Logger log = LogManager.getLogger(VTranslationDialog.class);
	//
	private GridBagLayout mainLayout = new GridBagLayout();
	private JComboBox cbLanguage = new JComboBox();
	private JLabel lLanguage = new JLabel();
	private JLabel lTable = new JLabel();
	private JComboBox cbTable = new JComboBox();
	private JButton bExport = new JButton();
	private JButton bImport = new JButton();
	//
	private StatusBar statusBar = new StatusBar();
	private JLabel lClient = new JLabel();
	private JComboBox cbClient = new JComboBox();


	/**
	 * 	Static Init
	 * 	@throws Exception
	 */
	private void jbInit() throws Exception
	{
		panel.setLayout(mainLayout);
		lClient.setText(Msg.translate(Env.getCtx(), "AD_Client_ID"));
		lLanguage.setText(Msg.translate(Env.getCtx(), "AD_Language"));
		lLanguage.setToolTipText(Msg.translate(Env.getCtx(), "IsSystemLanguage"));
		lTable.setText(Msg.translate(Env.getCtx(), "AD_Table_ID"));
		//
		bExport.setText(Msg.getMsg(Env.getCtx(), "Export"));
		bExport.addActionListener(this);
		bImport.setText(Msg.getMsg(Env.getCtx(), "Import"));
		bImport.addActionListener(this);
		//
		panel.add(cbLanguage,     new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		panel.add(lLanguage,    new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		panel.add(lTable,   new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		panel.add(cbTable,     new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		panel.add(bExport,   new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		panel.add(bImport,   new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		panel.add(lClient,   new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		panel.add(cbClient,   new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
	}	//	jbInit

	/**
	 * 	Dynamic Init.
	 * 	- fill Language & Table
	 */
	private void dynInit()
	{
		//	Fill Client
		ArrayList<KeyNamePair> clients = getClientList();
		for(KeyNamePair client: clients)
			cbClient.addItem(client);
		
		//	Fill Language
		ArrayList<ValueNamePair> languages = getLanguageList();
		for(ValueNamePair language: languages)
			cbLanguage.addItem(language);
		
		//	Fill Table
		ArrayList<ValueNamePair> tables = getTableList();
		for(ValueNamePair table: tables)
			cbTable.addItem(table);

		//	Info
		setStatusBar(statusBar);
	}	//	dynInit

	/**
	 *	Initialize Panel
	 *  @param WindowNo window
	 *  @param frame frame
	 */
	public void init (int WindowNo, FormFrame frame)
	{
		log.info("");
		m_WindowNo = WindowNo;
		m_frame = frame;
		Env.setContext(Env.getCtx(), m_WindowNo, "IsSOTrx", "Y");
		try
		{
			jbInit();
			dynInit();
			frame.getContentPane().add(panel, BorderLayout.CENTER);
			frame.getContentPane().add(statusBar, BorderLayout.SOUTH);
		}
		catch(Exception ex)
		{
			log.error("", ex);
		}
	}	//	init

	/**
	 * 	Dispose
	 */
	public void dispose()
	{
		m_frame.dispose();
	}	//	dispose

	/**************************************************************************
	 * 	Action Listener
	 * 	@param e event
	 */
	public void actionPerformed(ActionEvent e)
	{
		ValueNamePair AD_Language = (ValueNamePair)cbLanguage.getSelectedItem();
		if (AD_Language == null)
		{
			statusBar.setStatusLine(Msg.getMsg(Env.getCtx(), "LanguageSetupError"), true);
			return;
		}
		ValueNamePair AD_Table = (ValueNamePair)cbTable.getSelectedItem();
		if (AD_Table == null)
			return;
		boolean imp = (e.getSource() == bImport);
		KeyNamePair AD_Client = (KeyNamePair)cbClient.getSelectedItem();
		int AD_Client_ID = -1;
		if (AD_Client != null)
			AD_Client_ID = AD_Client.getKey();

		String startDir = Ini.getMetasfreshHome() + File.separator + "data";
		JFileChooser chooser = new JFileChooser(startDir);
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = imp ? chooser.showOpenDialog(panel) : chooser.showSaveDialog(panel);
		if (returnVal != JFileChooser.APPROVE_OPTION)
			return;
		String directory = chooser.getSelectedFile().getAbsolutePath();
		//
		statusBar.setStatusLine(directory);
		panel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		Translation t = new Translation(Env.getCtx());
		String msg = t.validateLanguage(AD_Language.getValue());
		if (msg.length() > 0)
		{
			ADialog.error(m_WindowNo, panel, "LanguageSetupError", msg);
			return;
		}

		//	All Tables
		if (AD_Table.getValue().equals(""))
		{
			msg = null;
			
			for (int i = 1; i < cbTable.getItemCount(); i++)
			{
				AD_Table = (ValueNamePair)cbTable.getItemAt(i);
				msg = imp
					? t.importTrl (directory, AD_Client_ID, AD_Language.getValue(), AD_Table.getValue())
					: t.exportTrl (directory, AD_Client_ID, AD_Language.getValue(), AD_Table.getValue());
			}
			
			if(msg == null || msg.length() == 0)
				msg = (imp ? "Import" : "Export") + " Successful. [" + directory + "]";
			
			statusBar.setStatusLine(directory);
		}
		else	//	single table
		{
			msg = null;
			msg = imp
				? t.importTrl (directory, AD_Client_ID, AD_Language.getValue(), AD_Table.getValue())
				: t.exportTrl (directory, AD_Client_ID, AD_Language.getValue(), AD_Table.getValue());
				
			if(msg == null || msg.length() == 0)
				msg = (imp ? "Import" : "Export") + " Successful. [" + directory + "]";
				
			statusBar.setStatusLine(msg);
		}
		//
		panel.setCursor(Cursor.getDefaultCursor());
	}	//	actionPerformed

}	//	Translation

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

/**
 * 2007, Modified by Posterita Ltd.
 */

package org.adempiere.webui.apps.form;

import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;

import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.Checkbox;
import org.adempiere.webui.component.ConfirmPanel;
import org.adempiere.webui.component.Datebox;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListItem;
import org.adempiere.webui.component.Listbox;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.component.Tab;
import org.adempiere.webui.component.Tabbox;
import org.adempiere.webui.component.Tabpanel;
import org.adempiere.webui.component.Tabpanels;
import org.adempiere.webui.component.Tabs;
import org.adempiere.webui.component.Textbox;
import org.adempiere.webui.editor.WSearchEditor;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.panel.CustomForm;
import org.adempiere.webui.panel.IFormController;
import org.adempiere.webui.session.SessionManager;
import org.compiere.apps.form.Archive;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MUser;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Iframe;

/**
 * 	Archive Viewer
 * 
 * @author	Niraj Sohun
 * @date	September 28, 2007
*/

public class WArchiveViewer extends Archive implements IFormController, EventListener
{
	private CustomForm form = new CustomForm();	
	
//	private Vbox queryPanel = new Vbox();
	private Checkbox reportField = new Checkbox();
	private Label processLabel = new Label(Msg.translate(Env.getCtx(), "AD_Process_ID"));
	private Listbox processField = new Listbox();
	private Label tableLabel = new Label(Msg.translate(Env.getCtx(), "AD_Table_ID"));
	private Listbox tableField = new Listbox();
	private Label bPartnerLabel = new Label(Msg.translate(Env.getCtx(), "C_BPartner_ID"));
	private WSearchEditor bPartnerField = null;
	private Label nameQLabel = new Label(Msg.translate(Env.getCtx(), "Name"));
	private Textbox nameQField = new Textbox();
	private Label descriptionQLabel = new Label(Msg.translate(Env.getCtx(), "Description"));
	private Textbox descriptionQField = new Textbox();
	private Label helpQLabel = new Label(Msg.translate(Env.getCtx(), "Help"));
	private Textbox helpQField = new Textbox();
	private Label createdByQLabel = new Label(Msg.translate(Env.getCtx(), "CreatedBy"));
	private Listbox createdByQField = new Listbox();
	private Label createdQLabel = new Label(Msg.translate(Env.getCtx(), "Created"));
	private Datebox createdQFrom = new Datebox();
	private Datebox createdQTo = new Datebox();

//	private Vbox viewEnterPanel = new Vbox();
	private Button bBack = new Button();
	private Button bNext = new Button();
	private Label positionInfo = new Label(".");
	private Label createdByLabel = new Label(Msg.translate(Env.getCtx(), "CreatedBy"));
	private Textbox createdByField = new Textbox();
	private Datebox createdField = new Datebox();
	
	private Label nameLabel = new Label(Msg.translate(Env.getCtx(), "Name"));
	private Textbox nameField = new Textbox();
	private Label descriptionLabel = new Label(Msg.translate(Env.getCtx(), "Description"));
	private Textbox descriptionField = new Textbox();
	private Label helpLabel = new Label(Msg.translate(Env.getCtx(), "Help"));
	private Textbox helpField = new Textbox();
	private ConfirmPanel confirmPanel = new ConfirmPanel(true);
	private Button updateArchive = new Button(); 
		
	private Tabbox tabbox = new Tabbox();
	private Tabs tabs = new Tabs();
	private Tabpanels tabpanels = new Tabpanels(); 
	
	private Iframe iframe = new Iframe();
	private Button bRefresh = new Button();
	
	public WArchiveViewer()
	{
		log.info("");

		try
		{
			dynInit();
			jbInit();
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "init", e);
		}
	}
	
	/**
	 *  Dynamic Init
	 */
	
	private void dynInit()
	{
		processField = new Listbox();
		KeyNamePair[] keyNamePair = getProcessData();		
		for (int i = 0; i < keyNamePair.length; i++)
			processField.appendItem(keyNamePair[i].getName(), keyNamePair[i]);
		
		tableField = new Listbox();		
		keyNamePair = getTableData();
		for (int i = 0; i < keyNamePair.length; i++)
			tableField.appendItem(keyNamePair[i].getName(), keyNamePair[i]);
		
		createdByQField = new Listbox();		
		keyNamePair = getUserData();
		for (int i = 0; i < keyNamePair.length; i++)
			createdByQField.appendItem(keyNamePair[i].getName(), keyNamePair[i]);
		
		MLookup lookup = MLookupFactory.get(Env.getCtx(), m_WindowNo, 0, 2762, DisplayType.Search);

		bPartnerField = new WSearchEditor(lookup, Msg.translate(
				Env.getCtx(), "C_BPartner_ID"), "", true, false, true);
	}	//	dynInit

	private void reportViewer(byte[] data)
	{
		AMedia media = new AMedia("Archive Viewer", "pdf", "application/pdf", data);
		iframe.setContent(media);
		iframe.invalidate();
	}
	
	/**
	 *  Static Init
	 *  @throws Exception
	 */
	
	private void jbInit() throws Exception
	{
		tabbox.setWidth("100%");
		tabbox.setHeight("90%");
		tabbox.appendChild(tabs);
		tabbox.appendChild(tabpanels);
		tabbox.addEventListener(Events.ON_SELECT, this);
		
		processField.setMold("select");
		processField.setRows(1);
		
		tableField.setMold("select");
		tableField.setRows(1);
		
		createdByQField.setMold("select");
		createdByQField.setRows(1);
		
		updateArchive.setImage("/images/Ok24.png");
		updateArchive.setTooltiptext("Save Archive");
		updateArchive.addEventListener(Events.ON_CLICK, this);
		
		bRefresh.setImage("/images/Refresh24.png");
		bRefresh.setTooltiptext("Refresh");
		bRefresh.addEventListener(Events.ON_CLICK, this);
		
		bBack.setImage("/images/Parent24.png");
		bBack.setTooltiptext("Back");
		bBack.addEventListener(Events.ON_CLICK, this);
		
		bNext.setImage("/images/Detail24.png");
		bNext.setTooltiptext("Next");
		bNext.addEventListener(Events.ON_CLICK, this);
		
		nameField.addEventListener(Events.ON_CHANGE, this);
		descriptionField.addEventListener(Events.ON_CHANGE, this);
		helpField.addEventListener(Events.ON_CHANGE, this);
		
		reportField.setLabel(Msg.translate(Env.getCtx(), "IsReport"));
		reportField.addEventListener(Events.ON_CHECK, this);
		
		Grid gridQuery = new Grid();
		gridQuery.setWidth("500px");
		gridQuery.setStyle("margin:0; padding:0;");
		gridQuery.makeNoStrip();
		gridQuery.setOddRowSclass("even");
        
		Rows rows = new Rows();
		gridQuery.appendChild(rows);
		
		Row row = new Row();
		rows.appendChild(row);
		row.setSpans("3");
		row.setAlign("right");
		row.appendChild(reportField);

		row = new Row();
		rows.appendChild(row);
		row.setSpans("1, 2");
		Div div = new Div();
		div.setAlign("right");
		div.appendChild(processLabel);
		row.appendChild(div);
		row.appendChild(processField);
		processField.setWidth("100%");
		
		row = new Row();
		rows.appendChild(row);
		row.setSpans("1, 2");
		div = new Div();
		div.setAlign("right");
		div.appendChild(bPartnerLabel);
		row.appendChild(div);
		row.appendChild(bPartnerField.getComponent());
		
		row = new Row();
		rows.appendChild(row);
		row.setSpans("1, 2");
		div = new Div();
		div.setAlign("right");
		div.appendChild(tableLabel);
		row.appendChild(div);
		row.appendChild(tableField);
		tableField.setWidth("100%");
		
		row = new Row();
		rows.appendChild(row);
		row.setSpans("1, 2");
		div = new Div();
		div.setAlign("right");
		div.appendChild(nameQLabel);
		row.appendChild(div);
		row.appendChild(nameQField);
		nameQField.setWidth("100%");
		
		row = new Row();
		rows.appendChild(row);
		row.setSpans("1, 2");
		div = new Div();
		div.setAlign("right");
		div.appendChild(descriptionQLabel);
		row.appendChild(div);
		row.appendChild(descriptionQField);
		descriptionQField.setWidth("100%");
		
		row = new Row();
		rows.appendChild(row);
		row.setSpans("1, 2");
		div = new Div();
		div.setAlign("right");
		div.appendChild(helpQLabel);
		row.appendChild(div);
		row.appendChild(helpQField);
		helpQField.setWidth("100%");
		
		row = new Row();
		rows.appendChild(row);
		row.setSpans("1, 2");
		div = new Div();
		div.setAlign("right");
		div.appendChild(createdByQLabel);
		row.appendChild(div);
		row.appendChild(createdByQField);
		createdByQField.setWidth("100%");
		
		row = new Row();
		rows.appendChild(row);
		div = new Div();
		div.setAlign("right");
		div.appendChild(createdQLabel);
		row.appendChild(div);
		row.appendChild(createdQFrom);
		createdQFrom.setWidth("100%");
		row.appendChild(createdQTo);
		createdQTo.setWidth("100%");
		
		div = new Div();
		div.setAlign("center");
		div.appendChild(gridQuery);
		
		Tabpanel tabQueryPanel = new Tabpanel();
		tabQueryPanel.appendChild(div);

		Tab tabQuery = new Tab("Query");

		tabpanels.appendChild(tabQueryPanel);
		tabs.appendChild(tabQuery);
		
		Grid gridView = new Grid();
		gridView.setStyle("margin:0; padding:0;");
		gridView.makeNoStrip();
		gridView.setOddRowSclass("even");
        
		rows = new Rows();
		gridView.appendChild(rows);
		
		row = new Row();
		rows.appendChild(row);
		row.setSpans("1, 2, 1");
		div = new Div();
		div.setAlign("left");
		div.appendChild(bBack);
		row.appendChild(div);
		div = new Div();
		div.setAlign("center");
		div.appendChild(positionInfo);
		row.appendChild(div);
		div = new Div();
		div.setAlign("right");
		div.appendChild(bNext);
		row.appendChild(div);

		row = new Row();
		rows.appendChild(row);
		row.setSpans("4");
		row.appendChild(createdByLabel);
		createdByLabel.setWidth("100%");
		
		row = new Row();
		rows.appendChild(row);
		row.setSpans("4");
		row.appendChild(createdByField);
		createdByField.setWidth("100%");
		
		row = new Row();
		rows.appendChild(row);
		row.setSpans("4");
		div = new Div();
		div.setAlign("right");
		div.appendChild(createdField);
		row.appendChild(div);
		
		row = new Row();
		rows.appendChild(row);
		row.setSpans("4");
		row.appendChild(nameLabel);
		nameLabel.setWidth("100%");
		
		row = new Row();
		rows.appendChild(row);
		row.setSpans("4");
		row.appendChild(nameField);
		nameField.setWidth("100%");
		
		row = new Row();
		rows.appendChild(row);
		row.setSpans("4");
		row.appendChild(descriptionLabel);
		descriptionLabel.setWidth("100%");
		
		row = new Row();
		rows.appendChild(row);
		row.setSpans("4");
		row.appendChild(descriptionField);
		descriptionField.setRows(3);
		descriptionField.setWidth("100%");
		
		row = new Row();
		rows.appendChild(row);
		row.setSpans("4");
		row.appendChild(helpLabel);
		helpLabel.setWidth("100%");
		
		row = new Row();
		rows.appendChild(row);
		row.setSpans("4");
		row.appendChild(helpField);
		helpField.setRows(3);
		helpField.setWidth("100%");
		
		row = new Row();
		rows.appendChild(row);
		row.setSpans("4");
		div = new Div();
		div.setAlign("right");
		div.appendChild(bRefresh);
		div.appendChild(updateArchive);
		row.appendChild(div);
				
		createdByField.setEnabled(false);
		createdField.setEnabled(false);
		
		Tab tabView = new Tab("View");
		
		Tabpanel tabViewPanel = new Tabpanel();
		Hbox boxViewSeparator = new Hbox();
		boxViewSeparator.setWidth("100%");
		boxViewSeparator.setHeight("100%");	
		boxViewSeparator.setWidths("70%, 30%");		
		boxViewSeparator.appendChild(iframe);
		boxViewSeparator.appendChild(gridView);
		tabViewPanel.appendChild(boxViewSeparator);

		tabs.appendChild(tabView);
		tabpanels.appendChild(tabViewPanel);
		
		confirmPanel.addActionListener(this);
		updateQDisplay();

		iframe.setId("reportFrame");
		int height = Double.valueOf(SessionManager.getAppDesktop().getClientInfo().desktopHeight * 0.8).intValue();
		height = height - 50;
		iframe.setHeight(height + "px");
		iframe.setWidth("100%");
		iframe.setAutohide(true);
		
		form.setWidth("100%");
		form.setHeight("100%");
		form.appendChild(tabbox);
		form.appendChild(confirmPanel);
	}
	
	public void onEvent(Event e) throws Exception 
	{
		log.info(e.getName());
		
		if (e.getTarget() == updateArchive)
			cmd_updateArchive();
		else if (e.getTarget().getId().equals(ConfirmPanel.A_CANCEL))
			SessionManager.getAppDesktop().closeActiveWindow();
		else if (e.getTarget().getId().equals(ConfirmPanel.A_OK))
		{
			if (tabbox.getSelectedIndex() == 1)
				SessionManager.getAppDesktop().closeActiveWindow();
			else
				cmd_query();
		}
		else if (e.getTarget() == reportField)
			updateQDisplay();
		else if (e.getTarget() == bBack)
			updateVDisplay(false);
		else if (e.getTarget() == bNext)
			updateVDisplay(true);
		else if (e.getTarget() == bRefresh)
			iframe.invalidate();
		else if (e.getTarget() instanceof Tab)
		{
			if(tabbox.getSelectedIndex() == 1)
				iframe.invalidate();
		}
		
		if(e.getName().equals(Events.ON_CHANGE))
		{
			if (m_archives.length > 0)
				updateArchive.setEnabled(true);
		}
	}
	
/*	public void valueChange(ValueChangeEvent evt) 
	{
		if (m_archives.length > 0)
			updateArchive.setEnabled(true);
	}
*/	
	/**
	 * 	Update Query Display
	 */
	
	private void updateQDisplay()
	{
		boolean reports = reportField.isChecked();
		log.config("Reports=" + reports);

		//	Show
		processLabel.setVisible(reports);
		processField.setVisible(reports);
		
		//	Hide
		bPartnerLabel.setVisible(!reports);
		bPartnerField.setVisible(!reports);
	}	//	updateQDisplay

	/**
	 * 	Update View Display
	 * 	@param next show next Archive
	 */
	
	private void updateVDisplay (boolean next)
	{
		if (m_archives == null)
			m_archives = new I_AD_Archive[0];
	
		if (next)
			m_index++;
		else
			m_index--;
		
		if (m_index >= m_archives.length-1)
			m_index = m_archives.length-1;
		
		if (m_index < 0)
			m_index = 0;
		
		bBack.setEnabled(m_index > 0);
		bNext.setEnabled(m_index < m_archives.length-1);
		updateArchive.setEnabled(false);
		
		log.info("Index=" + m_index + ", Length=" + m_archives.length);
		
		if (m_archives.length == 0)
		{
			positionInfo.setValue("No Record Found");
			createdByField.setText("");
			createdField.setValue(null);
			nameField.setText("");
			descriptionField.setText("");
			helpField.setText("");
			iframe.getChildren().clear();
			return;
		}
		
		positionInfo.setValue(m_index+1 + " of " + m_archives.length);
		I_AD_Archive ar = m_archives[m_index];
		createdByField.setText(MUser.getNameOfUser(ar.getCreatedBy()));
		createdField.setValue(ar.getCreated());
		nameField.setText(ar.getName());
		descriptionField.setText(ar.getDescription());
		helpField.setText(ar.getHelp());
		
		try
		{
			final byte[] data = Services.get(IArchiveBL.class).getBinaryData(ar);
			//pdfViewer.setScale(reportField.isSelected() ? 50 : 75);
			if (data != null && data.length > 0)
				reportViewer(data);//pdfViewer.loadPDF(in);
			else
				iframe.getChildren().clear();//pdfViewer.clearDocument();
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "pdf", e);
			iframe.getChildren().clear();//pdfViewer.clearDocument();
		}
	}	//	updateVDisplay

	/**
	 * 	Update Archive Info
	 */
	
	private void cmd_updateArchive()
	{
		final I_AD_Archive ar = m_archives[m_index];
		boolean update = false;
		
		if (!isSame(nameField.getText(), ar.getName()))
		{
			String newText = nameField.getText();
			if (newText != null && newText.length() > 0)
			{
				ar.setName(newText);
				update = true;
			}
		}
		
		if (!isSame(descriptionField.getText(), ar.getDescription()))
		{
			ar.setDescription(descriptionField.getText());
			update = true;
		}
		
		if (!isSame(helpField.getText(), ar.getHelp()))
		{
			ar.setHelp(helpField.getText());
			update = true;
		}
		
		log.info("Update=" + update);
		
		if (update)
		{
			InterfaceWrapperHelper.save(ar);
		}
		
		m_index++;
		
		updateVDisplay(false);
	}	//	cmd_updateArchive
	
	/**
	 * 	Query Directly
	 *	@param isReport report
	 *	@param AD_Table_ID table
	 *	@param Record_ID tecord
	 */
	
	public void query (boolean isReport, int AD_Table_ID, int Record_ID)
	{
		log.config("Report=" + isReport + ", AD_Table_ID=" + AD_Table_ID + ",Record_ID=" + Record_ID);
		reportField.setChecked(isReport);
		m_AD_Table_ID = AD_Table_ID;
		m_Record_ID = Record_ID;
		cmd_query();
	}	//	query	
	
	/**************************************************************************
	 * 	Create Query
	 */
	
	private void cmd_query()
	{
		boolean reports = reportField.isChecked();
		
		ListItem listitem = processField.getSelectedItem();
		KeyNamePair process = null;
		if (listitem != null)
			process = (KeyNamePair)listitem.getValue();
		
		listitem = tableField.getSelectedItem();
		KeyNamePair table = null;
		if (listitem != null)
			table = (KeyNamePair)listitem.getValue();
		
		Integer C_BPartner_ID = (Integer)bPartnerField.getValue();
		String name = nameQField.getText();
		String description = descriptionQField.getText();
		String help = helpQField.getText();
		
		listitem = createdByQField.getSelectedItem();
		KeyNamePair createdBy = null;
		if (listitem != null)
			createdBy = (KeyNamePair)listitem.getValue();
		
		Date date = null;
		Timestamp createdFrom = null;
		if (createdQFrom.getValue() != null)
		{
			date = createdQFrom.getValue();
			createdFrom = new Timestamp(date.getTime());
		}
		
		Timestamp createdTo = null;
		if (createdQTo.getValue() != null)
		{
			date = createdQTo.getValue();
			createdTo = new Timestamp(date.getTime());
		}
		
		cmd_query(reports, process, table, C_BPartner_ID, name, description, help, 
				createdBy, createdFrom, createdTo);
		
		//	Display
		tabbox.setSelectedIndex(1);
		
		m_index = 1;
		updateVDisplay(false);
	}	//	cmd_query
	
	public ADForm getForm() {
		return form;
	}
}
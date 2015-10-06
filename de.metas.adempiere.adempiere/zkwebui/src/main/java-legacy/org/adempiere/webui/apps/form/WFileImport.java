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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.ConfirmPanel;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListItem;
import org.adempiere.webui.component.Listbox;
import org.adempiere.webui.component.Textbox;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.session.SessionManager;
import org.adempiere.webui.util.ReaderInputStream;
import org.adempiere.webui.window.FDialog;
import org.compiere.impexp.ImpFormat;
import org.compiere.impexp.ImpFormatRow;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.Msg;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.North;
import org.zkoss.zkex.zul.South;
import org.zkoss.zul.Div;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Separator;

/**
 * 	Fixed length file import
 *
 *  @author 	Niraj Sohun
 *  			Aug 16, 2007
 *  
 */

public class WFileImport extends ADForm implements EventListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5779187375101512112L;
	private static final int MAX_LOADED_LINES = 100;
	private static final int MAX_SHOWN_LINES = 10;
	
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(WFileImport.class);
	
	private int	m_record = -1;
	
	private Listbox pickFormat = new Listbox();
	private Listbox fCharset = new Listbox();
	
	private ArrayList<String> m_data = new ArrayList<String>();
	private static final String s_none = "----";	//	no format indicator

	private ImpFormat m_format;
	
	private ConfirmPanel confirmPanel = new ConfirmPanel(true);

	private Button bFile = new Button();
	private Button bNext = new Button();
	private Button bPrevious = new Button();

	private InputStream m_file_istream;
	
	private Textbox rawData = new Textbox();
	private Textbox[] m_fields;
	
	private Label info = new Label();
	private Label[] m_labels;
	private Label record = new Label();
	private Label labelFormat = new Label();

	private Div previewPanel = new Div();

	private Hbox northPanel = new Hbox();

	private Div centerPanel = new Div();

	public WFileImport()
	{
	}
	
	/**
	 *	Initialize Panel
	 *  @param WindowNo window
	 */
	protected void initForm()
	{
		log.info("");
		try
		{
			jbInit();
			dynInit();
			
			this.setWidth("100%");
			this.setClosable(true);
			this.setTitle("Import File Loader");
			this.setBorder("normal");
			
			Borderlayout layout = new Borderlayout();
			layout.setHeight("100%");
			layout.setWidth("100%");
			this.appendChild(layout);
			North north = new North();
			layout.appendChild(north);
			north.appendChild(northPanel);
			Center center = new Center();
			center.setFlex(true);
			layout.appendChild(center);
			center.appendChild(centerPanel);
			South south = new South();
			layout.appendChild(south);
			south.appendChild(confirmPanel);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "init", e);
		}
	}	//	init

	/**
	 *	Static Init
	 *  @throws Exception
	 */
	
	private void jbInit() throws Exception
	{
		Charset[] charsets = Ini.getAvailableCharsets();
		
		for (int i = 0; i < charsets.length; i++)
			fCharset.appendItem(charsets[i].displayName(), charsets[i]);
		
		bFile.setLabel(Msg.getMsg(Env.getCtx(), "FileImportFile"));
		bFile.setTooltiptext(Msg.getMsg(Env.getCtx(), "FileImportFileInfo"));
		bFile.addEventListener(Events.ON_CLICK, this);
		
		fCharset.setMold("select");
		fCharset.setRows(0);
		fCharset.setTooltiptext(Msg.getMsg(Env.getCtx(), "Charset", false));
		
		info.setValue("   ");
		
		labelFormat.setValue(Msg.translate(Env.getCtx(), "AD_ImpFormat_ID"));
		
		pickFormat.setMold("select");
		pickFormat.setRows(0);
		
		bNext.setTooltiptext(Msg.getMsg(Env.getCtx(), "Next"));
		bNext.setLabel(">");
		bNext.addEventListener(Events.ON_CLICK, this);
		
		record.setValue("------");
		
		bPrevious.setTooltiptext(Msg.getMsg(Env.getCtx(), "Previous"));
		bPrevious.setLabel("<");
		bPrevious.addEventListener(Events.ON_CLICK, this);
		
		northPanel.appendChild(bFile);
		northPanel.appendChild(fCharset);
		northPanel.appendChild(info);
		northPanel.appendChild(labelFormat);
		northPanel.appendChild(pickFormat);
		northPanel.appendChild(bPrevious);
		northPanel.appendChild(record);
		northPanel.appendChild(bNext);
		
		rawData.setWidth("100%");
		rawData.setCols(80);
		rawData.setRows(MAX_SHOWN_LINES);
		rawData.setHeight("40%");
		
		previewPanel.setWidth("100%");
		previewPanel.setHeight("58%");
		previewPanel.setStyle("overflow: auto");
		
		centerPanel.setWidth("100%"); // Elaine 2008/11/07 - fix text area is not expanded in IE7
		centerPanel.setHeight("100%");
		centerPanel.appendChild(rawData);
		centerPanel.appendChild(new Separator());
		centerPanel.appendChild(previewPanel);
		
		confirmPanel.addActionListener(Events.ON_CLICK, this);
	}
	
	/**
	 *	Dynamic Init
	 */
	
	private void dynInit()
	{
		//	Load Formats
		pickFormat.appendItem(s_none, s_none);
		
		String sql = Env.getUserRolePermissions().addAccessSQL("SELECT Name FROM AD_ImpFormat", "AD_ImpFormat",
				IUserRolePermissions.SQL_FULLYQUALIFIED, IUserRolePermissions.SQL_RO);
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			ResultSet rs = pstmt.executeQuery();
		
			while (rs.next())
				pickFormat.appendItem(rs.getString(1), rs.getString(1));
			
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		
		pickFormat.setSelectedIndex(0);
		pickFormat.addEventListener(Events.ON_SELECT, this);

		Charset charset = Ini.getCharset();
		
		for (int i = 0; i < fCharset.getItemCount(); i++)
		{
			ListItem listitem = fCharset.getItemAtIndex(i);
			Charset compare = (Charset)listitem.getValue();
			
			if (charset == compare)
			{
				fCharset.setSelectedIndex(i);
				break;
			}
		}
		
		fCharset.addEventListener(Events.ON_SELECT, this);
		
		confirmPanel.setEnabled("Ok", false);
	}	//	dynInit

	
	public void onEvent(Event e) throws Exception 
	{
		if (e.getTarget() == bFile)
		{
			cmd_loadFile();
			invalidate();
		}
		else if (e.getTarget() == fCharset) 
		{
			int record = m_record;
			cmd_reloadFile();
			m_record = record - 1;
			cmd_applyFormat(true);
		}
		else if (e.getTarget() == pickFormat)
		{
			cmd_loadFormat();
			invalidate();
		}
		else if (e.getTarget() == bNext )
			cmd_applyFormat(true);
		else if (e.getTarget() == bPrevious )
			cmd_applyFormat(false);
		
		else if (e.getTarget() == confirmPanel.getButton("Ok"))
		{
			confirmPanel.setEnabled("Ok", false);

			cmd_process();			
		}
		else if (e.getTarget() == confirmPanel.getButton("Cancel"))
		{
			SessionManager.getAppDesktop().closeActiveWindow();
			return;			
		}
		
		if (m_data != null && m_data.size()	> 0					//	file loaded
			&& m_format != null && m_format.getRowCount() > 0)	//	format loaded
			confirmPanel.getButton("Ok").setEnabled(true);
		else
			confirmPanel.getButton("Ok").setEnabled(false);
	}
	
	/**************************************************************************
	 *	Load File
	 */
	
	private void cmd_loadFile()
	{
		Media media = null;
		
		try 
		{
			media = Fileupload.get();
		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	
		if (media == null)
			return;
		
		if (media.isBinary()) {
			m_file_istream = media.getStreamData();
		}
		else {
			ListItem listitem = fCharset.getSelectedItem();
			if (listitem == null) {
				m_file_istream = new ReaderInputStream(media.getReaderData());
			} else {
				Charset charset = (Charset)listitem.getValue();
				m_file_istream = new ReaderInputStream(media.getReaderData(), charset.name());
			}
		}
		
		log.config(media.getName());
		bFile.setLabel(media.getName());
	
		cmd_reloadFile();
	}
	
	/**
	 * Reload/Load file
	 */
	
	private void cmd_reloadFile()
	{
		if (m_file_istream == null)
			return;
		
		m_data.clear();
		rawData .setText("");
		
		try
		{
			//  see NaturalAccountMap
			
			ListItem listitem = fCharset.getSelectedItem();
			Charset charset = null;
			
			if (listitem == null)
				return;
			
			charset = (Charset)listitem.getValue();
			BufferedReader in = new BufferedReader(new InputStreamReader(m_file_istream, charset), 10240);
						
			//	not safe see p108 Network pgm
			String s = null;
			String concat = "";
			
			while ((s = in.readLine()) != null)
			{
				m_data.add(s);
				
				concat += s;
				concat += "\n";
				
				if (m_data.size() < MAX_LOADED_LINES)
				{
					rawData.setValue(concat);
				}
			}
			in.close();
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "", e);
			bFile.setLabel(Msg.getMsg(Env.getCtx(), "FileImportFile"));
		}
		
		int index = 1;	//	second line as first may be heading
		
		if (m_data.size() == 1)
			index = 0;
		
		int length = 0;
		
		if (m_data.size() > 0)
			length = m_data.get(index).toString().length();
		
		info.setValue(Msg.getMsg(Env.getCtx(), "Records") + "=" + m_data.size()
			+ ", " + Msg.getMsg(Env.getCtx(), "Length") + "=" + length + "   ");
		
		//setCursor (Cursor.getDefaultCursor());
		
		log.config("Records=" + m_data.size() + ", Length=" + length);
	}	//	cmd_loadFile

	/**
	 *	Load Format
	 */
	
	private void cmd_loadFormat()
	{
		//	clear panel
		previewPanel.getChildren().clear();
		
		ListItem listitem = pickFormat.getSelectedItem();
		
		String formatName = (String)listitem.getValue();
		
		if (formatName.equals(s_none))
			return;
		
		m_format = ImpFormat.load (formatName);
		
		if (m_format == null)
		{
			FDialog.error(m_WindowNo, this, formatName);
			return;
		}

		//	pointers
		
		int size = m_format.getRowCount();
		m_labels = new Label[size];
		m_fields = new Textbox[size];
		
		for (int i = 0; i < size; i++)
		{
			ImpFormatRow row = m_format.getRow(i);
			
			m_labels[i] = new Label(row.getColumnName());
			
			Hbox hbox = new Hbox();
			hbox.setWidth("100%");
			hbox.setWidths("30%, 70%");
			hbox.setStyle("padding-bottom: 3px");
			
			hbox.appendChild(m_labels[i].rightAlign());
			
			int length = row.getEndNo() - row.getStartNo();
			
			if (length <= 5)
				length = 5;
			else if (length > 20)
				length = 20;
			
			m_fields[i] = new Textbox();
			m_fields[i].setStyle("margin-left: 2px");
			
			hbox.appendChild(m_fields[i]);
			
			previewPanel.appendChild(hbox);
		}
		m_record = -1;
		record.setValue("------");
		previewPanel.invalidate();
	}	//	cmd_format

	/**
	 *	Apply Current Pattern
	 *  @param next next
	 */
	
	private void cmd_applyFormat (boolean next)
	{
		if (m_format == null || m_data.size() == 0)
			return;

		//	set position
		if (next)
			m_record++;
		else
			m_record--;
	
		if (m_record < 0)
			m_record = 0;
		else if (m_record >= m_data.size())
			m_record = m_data.size() - 1;
		
		record.setValue(" " + String.valueOf(m_record+1) + " ");

		//	Line Info
		
		String[] lInfo = m_format.parseLine(m_data.get(m_record).toString(), false, true, false);	//	no label, trace, no ignore
		
		int size = m_format.getRowCount();
		
		if (lInfo.length != size)
			log.log(Level.SEVERE, "FormatElements=" + size + " != Fields=" + lInfo.length);
		
		for (int i = 0; i < size; i++)
		{
			m_fields[i].setText(lInfo[i]);
			//m_fields[i].setCaretPosition(0);
		}
	}	//	cmd_applyFormat

	/**************************************************************************
	 *	Process File
	 */
	
	private void cmd_process()
	{
		if (m_format == null)
		{
			FDialog.error(m_WindowNo, this, "FileImportNoFormat");
			return;
		}
		
		log.config(m_format.getName());

		//	For all rows - update/insert DB table
		
		int row = 0;
		int imported = 0;
		
		for (row = 0; row < m_data.size(); row++)
			if (m_format.updateDB(Env.getCtx(), m_data.get(row).toString(), null))
				imported++;
		
		FDialog.info(m_WindowNo, this, "FileImportR/I", row + " / " + imported + "#");
		
		SessionManager.getAppDesktop().closeActiveWindow();
	}	//	cmd_process
}

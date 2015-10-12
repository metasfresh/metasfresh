/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2007 Adempiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *
 * Copyright (C) 2007 Low Heng Sin hengsin@avantz.com
 * _____________________________________________
 *****************************************************************************/
package org.adempiere.webui.window;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.asp.IASPFiltersFactory;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.apps.WReport;
import org.adempiere.webui.component.Checkbox;
import org.adempiere.webui.component.ConfirmPanel;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListItem;
import org.adempiere.webui.component.Listbox;
import org.adempiere.webui.component.Window;
import org.adempiere.webui.event.DrillEvent;
import org.adempiere.webui.event.ZoomEvent;
import org.adempiere.webui.panel.AbstractADWindowPanel;
import org.adempiere.webui.panel.StatusBarPanel;
import org.adempiere.webui.report.HTMLExtension;
import org.compiere.model.GridField;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.MQuery;
import org.compiere.model.MSysConfig;
import org.compiere.model.MUser;
import org.compiere.print.AReport;
import org.compiere.print.ArchiveEngine;
import org.compiere.print.MPrintFormat;
import org.compiere.print.ReportEngine;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.North;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vbox;

import de.metas.adempiere.form.IClientUI;


/**
 *	Print View Frame
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: Viewer.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 * globalqss: integrate phib contribution from 
 *   http://sourceforge.net/tracker/index.php?func=detail&aid=1566335&group_id=176962&atid=879334
 * globalqss: integrate Teo Sarca bug fixing
 * Colin Rooney 2007/03/20 RFE#1670185 & BUG#1684142
 *                         Extend security to Info queries
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 				<li>FR [ 1762466 ] Add "Window" menu to report viewer.
 * 				<li>FR [ 1894640 ] Report Engine: Excel Export support
 * 
 * @author Low Heng Sin
 */
public class ZkReportViewer extends Window implements EventListener {
	
	private static final long serialVersionUID = 4640088641140012438L;
	/** Window No					*/
	private int                 m_WindowNo;
	/**	Print Context				*/
	private Properties			m_ctx;
	/**	Setting Values				*/
	private boolean				m_setting = false;
	/**	Report Engine				*/
	private ReportEngine 		m_reportEngine;
	/** Table ID					*/
	private int					m_AD_Table_ID = 0;
	private boolean				m_isCanExport;
	
	private MQuery 		m_ddQ = null;
	private MQuery 		m_daQ = null;
	private Menuitem 	m_ddM = null;
	private Menuitem 	m_daM = null;

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ZkReportViewer.class);

	//
	private StatusBarPanel statusBar = new StatusBarPanel();
	private Toolbar toolBar = new Toolbar();
	private Toolbarbutton bSendMail = new Toolbarbutton();
	private Toolbarbutton bArchive = new Toolbarbutton();
	private Toolbarbutton bCustomize = new Toolbarbutton();
	private Toolbarbutton bFind = new Toolbarbutton();
	private Toolbarbutton bExport = new Toolbarbutton();
	private Listbox comboReport = new Listbox();
	private Label labelDrill = new Label();
	private Listbox comboDrill = new Listbox();
	private Listbox previewType = new Listbox();
	
	private Toolbarbutton bRefresh = new Toolbarbutton();
	private Iframe iframe;
	
	private Window winExportFile = null;
	private ConfirmPanel confirmPanel = new ConfirmPanel(true);
	private Listbox cboType = new Listbox();
	private Checkbox summary = new Checkbox();
	
	/**
	 * 	Static Layout
	 * 	@throws Exception
	 */
	public ZkReportViewer(ReportEngine re, String title) {		
		super();
		
		log.info("");
		m_reportEngine = re;
		m_AD_Table_ID = re.getPrintFormat().getAD_Table_ID();
		if (!Env.getUserRolePermissions().isCanReport(m_AD_Table_ID))
		{
			FDialog.error(m_WindowNo, this, "AccessCannotReport", m_reportEngine.getName());
			this.onClose();
		}
		m_isCanExport = Env.getUserRolePermissions().isCanExport(m_AD_Table_ID);
		try
		{
			m_ctx = m_reportEngine.getCtx();
			init();
			dynInit();
			
			if (!ArchiveEngine.isValid(m_reportEngine.getLayout()))
				log.warning("Cannot archive Document");
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "", e);
			FDialog.error(m_WindowNo, this, "LoadError", e.getLocalizedMessage());
			this.onClose();
		}
	}

	private void init() {
		Borderlayout layout = new Borderlayout();
		layout.setStyle("position: absolute; height: 99%; width: 99%");
		this.appendChild(layout);
		this.setStyle("width: 100%; height: 100%; position: absolute");

		toolBar.setHeight("26px");
		
		previewType.setMold("select");
		previewType.appendItem("PDF", "PDF");
		previewType.appendItem("HTML", "HTML");
		previewType.appendItem("Excel", "XLS");
		toolBar.appendChild(previewType);		
		previewType.addEventListener(Events.ON_SELECT, this);
		toolBar.appendChild(new Separator("vertical"));
		
		//set default type
		String type = m_reportEngine.getPrintFormat().isForm()
				? MSysConfig.getValue("ZK_REPORT_FORM_OUTPUT_TYPE")
				: MSysConfig.getValue("ZK_REPORT_TABLE_OUTPUT_TYPE");

		if ("PDF".equals(type))
			previewType.setSelectedIndex(0);
		else if ("HTML".equals(type))
			previewType.setSelectedIndex(1);
		else if ("XLS".equals(type))
			previewType.setSelectedIndex(2);
		else
			previewType.setSelectedIndex(0); //fallback to PDF
			
		
		labelDrill.setValue(Msg.getMsg(m_ctx, "Drill") + ": ");
		toolBar.appendChild(labelDrill);
		
		comboDrill.setMold("select");
		comboDrill.setTooltiptext(Msg.getMsg(m_ctx, "Drill"));
		toolBar.appendChild(comboDrill);
		
		toolBar.appendChild(new Separator("vertical"));
		
		comboReport.setMold("select");
		comboReport.setTooltiptext(Msg.translate(m_ctx, "AD_PrintFormat_ID"));
		toolBar.appendChild(comboReport);
		
		summary.setText(Msg.getMsg(m_ctx, "Summary"));
		toolBar.appendChild(summary);
		
		bCustomize.setImage("/images/Preference24.png");
		bCustomize.setTooltiptext("Customize Report");
		toolBar.appendChild(bCustomize);
		bCustomize.addEventListener(Events.ON_CLICK, this);
		
		bFind.setImage("/images/Find24.png");
		bFind.setTooltiptext("Lookup Record");
		toolBar.appendChild(bFind);
		bFind.addEventListener(Events.ON_CLICK, this);
		
		toolBar.appendChild(new Separator("vertical"));
		
		bSendMail.setImage("/images/SendMail24.png");
		bSendMail.setTooltiptext("Send Mail");
		toolBar.appendChild(bSendMail);
		bSendMail.addEventListener(Events.ON_CLICK, this);
		
		bArchive.setImage("/images/Archive24.png");
		bArchive.setTooltiptext("Archived Documents/Reports");
		toolBar.appendChild(bArchive);
		bArchive.addEventListener(Events.ON_CLICK, this);
		
		if (m_isCanExport)
		{
			bExport.setImage("/images/ExportX24.png");
			bExport.setTooltiptext("Export");
			toolBar.appendChild(bExport);
			bExport.addEventListener(Events.ON_CLICK, this);
		}
		
		toolBar.appendChild(new Separator("vertical"));
		
		bRefresh.setImage("/images/Refresh24.png");
		bRefresh.setTooltiptext("Refresh");
		toolBar.appendChild(bRefresh);
		bRefresh.addEventListener(Events.ON_CLICK, this);

		North north = new North();
		layout.appendChild(north);
		north.appendChild(toolBar);

		Center center = new Center();
		center.setFlex(true);
		layout.appendChild(center);
		iframe = new Iframe();
		iframe.setId("reportFrame");
		iframe.setHeight("100%");
		iframe.setWidth("100%");
		iframe.addEventListener(Events.ON_CLICK, this);
		iframe.addEventListener(Events.ON_RIGHT_CLICK, this);
		center.appendChild(iframe);

		try {
			renderReport();
		} catch (Exception e) {
			throw new AdempiereException("Failed to render report", e);
		}
				
		iframe.setAutohide(true);

		this.setBorder("normal");
		
		this.addEventListener("onZoom", new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				if (event instanceof ZoomEvent) {
					ZoomEvent ze = (ZoomEvent) event;
					if (ze.getData() != null && ze.getData() instanceof MQuery) {
						AEnv.zoom((MQuery) ze.getData());
					}
				}
				
			}
		});
		
		this.addEventListener(DrillEvent.ON_DRILL_ACROSS, new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				if (event instanceof DrillEvent) {
					DrillEvent de = (DrillEvent) event;
					if (de.getData() != null && de.getData() instanceof MQuery) {
						MQuery query = (MQuery) de.getData();
						Listitem item = comboDrill.getSelectedItem();
						if (item != null && item.getValue() != null && item.toString().trim().length() > 0)
						{
							query.setTableName(item.getValue().toString());
							executeDrill(query, event.getTarget());
						}
					}
				}
				
			}
		});
		
		this.addEventListener(DrillEvent.ON_DRILL_DOWN, new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {
				if (event instanceof DrillEvent) {
					DrillEvent de = (DrillEvent) event;
					if (de.getData() != null && de.getData() instanceof MQuery) {
						MQuery query = (MQuery) de.getData();
						executeDrill(query, event.getTarget());
					}
				}
				
			}
		});
	}

	private void renderReport() throws Exception {
		AMedia media = null;
		Listitem selected = previewType.getSelectedItem();
		if (selected == null || "PDF".equals(selected.getValue())) {
			String path = System.getProperty("java.io.tmpdir");
			String prefix = makePrefix(m_reportEngine.getName());
			if (log.isLoggable(Level.FINE))
			{
				log.log(Level.FINE, "Path="+path + " Prefix="+prefix);
			}
			File file = File.createTempFile(prefix, ".pdf", new File(path));
			m_reportEngine.createPDF(file);
			media = new AMedia(getTitle(), "pdf", "application/pdf", file, true);
		} else if ("HTML".equals(previewType.getSelectedItem().getValue())) {
			String path = System.getProperty("java.io.tmpdir");
			String prefix = makePrefix(m_reportEngine.getName());
			if (log.isLoggable(Level.FINE))
			{
				log.log(Level.FINE, "Path="+path + " Prefix="+prefix);
			}
			File file = File.createTempFile(prefix, ".html", new File(path));
			m_reportEngine.createHTML(file, false, AEnv.getLanguage(Env.getCtx()), new HTMLExtension(Executions.getCurrent().getContextPath(), "rp", this.getUuid()));
			media = new AMedia(getTitle(), "html", "text/html", file, false);
		} else if ("XLS".equals(previewType.getSelectedItem().getValue())) {
			String path = System.getProperty("java.io.tmpdir");
			String prefix = makePrefix(m_reportEngine.getName());
			if (log.isLoggable(Level.FINE))
			{
				log.log(Level.FINE, "Path="+path + " Prefix="+prefix);
			}
			File file = File.createTempFile(prefix, ".xls", new File(path));
			m_reportEngine.createXLS(file, AEnv.getLanguage(Env.getCtx()));
			// metas: use name="null" instead of title because title is not set yet, and by using null, filename will be used instead
			media = new AMedia(null, "xls", "application/vnd.ms-excel", file, true);
		}
		
		iframe.setContent(media);
	}

	private String makePrefix(String name) {
		StringBuffer prefix = new StringBuffer();
		char[] nameArray = name.toCharArray();
		for (char ch : nameArray) {
			if (Character.isLetterOrDigit(ch)) {
				prefix.append(ch);
			} else {
				prefix.append("_");
			}
		}
		return prefix.toString();
	}
	
	/**
	 * 	Dynamic Init
	 */
	private void dynInit()
	{
		summary.addActionListener(this);
		
		fillComboReport(m_reportEngine.getPrintFormat().get_ID());

		//	fill Drill Options (Name, TableName)
		comboDrill.appendItem("", null);
		String sql = "SELECT t.AD_Table_ID, t.TableName, e.PrintName, NULLIF(e.PO_PrintName,e.PrintName) "
			+ "FROM AD_Column c "
			+ " INNER JOIN AD_Column used ON (c.ColumnName=used.ColumnName)"
			+ " INNER JOIN AD_Table t ON (used.AD_Table_ID=t.AD_Table_ID AND t.IsView='N' AND t.AD_Table_ID <> c.AD_Table_ID)"
			+ " INNER JOIN AD_Column cKey ON (t.AD_Table_ID=cKey.AD_Table_ID AND cKey.IsKey='Y')"
			+ " INNER JOIN AD_Element e ON (cKey.ColumnName=e.ColumnName) "
			+ "WHERE c.AD_Table_ID=? AND c.IsKey='Y' "
			+ "ORDER BY 3";
		boolean trl = !Env.isBaseLanguage(Env.getCtx(), "AD_Element");
		if (trl)
			sql = "SELECT t.AD_Table_ID, t.TableName, et.PrintName, NULLIF(et.PO_PrintName,et.PrintName) "
				+ "FROM AD_Column c"
				+ " INNER JOIN AD_Column used ON (c.ColumnName=used.ColumnName)"
				+ " INNER JOIN AD_Table t ON (used.AD_Table_ID=t.AD_Table_ID AND t.IsView='N' AND t.AD_Table_ID <> c.AD_Table_ID)"
				+ " INNER JOIN AD_Column cKey ON (t.AD_Table_ID=cKey.AD_Table_ID AND cKey.IsKey='Y')"
				+ " INNER JOIN AD_Element e ON (cKey.ColumnName=e.ColumnName)"
				+ " INNER JOIN AD_Element_Trl et ON (e.AD_Element_ID=et.AD_Element_ID) "
				+ "WHERE c.AD_Table_ID=? AND c.IsKey='Y'"
				+ " AND et.AD_Language=? "
				+ "ORDER BY 3";
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, m_reportEngine.getPrintFormat().getAD_Table_ID());
			if (trl)
				pstmt.setString(2, Env.getAD_Language(Env.getCtx()));
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				String tableName = rs.getString(2);
				String name = rs.getString(3);
				String poName = rs.getString(4);
				if (poName != null)
					name += "/" + poName;
				comboDrill.appendItem(name, tableName);
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		if (comboDrill.getItemCount() == 1)
		{
			labelDrill.setVisible(false);
			comboDrill.setVisible(false);
		}
		else
			comboDrill.addEventListener(Events.ON_SELECT, this);

		revalidate();
	}	//	dynInit
	
	/**
	 * 	Fill ComboBox comboReport (report options)
	 *  @param AD_PrintFormat_ID item to be selected
	 */
	private void fillComboReport(int AD_PrintFormat_ID)
	{
		comboReport.removeEventListener(Events.ON_SELECT, this);
		comboReport.getItems().clear();
		KeyNamePair selectValue = null;
		//	fill Report Options
		String sql = Env.getUserRolePermissions().addAccessSQL(
			"SELECT AD_PrintFormat_ID, Name, Description "
				+ "FROM AD_PrintFormat "
				+ "WHERE AD_Table_ID=? "
				//Added Lines by Armen
				+ "AND IsActive='Y' "
				//End of Added Lines
				+ "ORDER BY Name",
			"AD_PrintFormat", IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO);
		int AD_Table_ID = m_reportEngine.getPrintFormat().getAD_Table_ID();
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, AD_Table_ID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				KeyNamePair pp = new KeyNamePair(rs.getInt(1), rs.getString(2));
				Listitem li = comboReport.appendItem(pp.getName(), pp.getKey());
				if (rs.getInt(1) == AD_PrintFormat_ID)
				{
					selectValue = pp;
					if(selectValue != null)
						comboReport.setSelectedItem(li);
				}
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		StringBuffer sb = new StringBuffer("** ").append(Msg.getMsg(m_ctx, "NewReport")).append(" **");
		KeyNamePair pp = new KeyNamePair(-1, sb.toString());
		comboReport.appendItem(pp.getName(), pp.getKey());
		comboReport.addEventListener(Events.ON_SELECT, this);
	}	//	fillComboReport

	/**
	 * 	Revalidate settings after change of environment
	 */
	private void revalidate()
	{
		//	Report Info
		setTitle(Msg.getMsg(m_ctx, "Report") + ": " + m_reportEngine.getName() + "  " + Env.getHeader(m_ctx, 0));
		StringBuffer sb = new StringBuffer ();
		sb.append(Msg.getMsg(m_ctx, "DataCols")).append("=")
			.append(m_reportEngine.getColumnCount())
			.append(", ").append(Msg.getMsg(m_ctx, "DataRows")).append("=")
			.append(m_reportEngine.getRowCount());
		statusBar.setStatusLine(sb.toString());
		//
	}	//	revalidate

	/**
	 * 	Dispose
	 */
	@Override
	public void onClose()
	{
		Env.clearWinContext(m_WindowNo);
		m_reportEngine = null;
		m_ctx = null;
		super.onClose();
	}	//	dispose

	@Override
	public void onEvent(Event event) throws Exception {
		
		if(event.getTarget().getId().equals(ConfirmPanel.A_CANCEL))
			winExportFile.onClose();
		else if(event.getTarget().getId().equals(ConfirmPanel.A_OK))			
			exportFile();
		else if(event.getName().equals(Events.ON_CLICK) || event.getName().equals(Events.ON_SELECT)) 
			actionPerformed(event);
		else if (event.getTarget() == summary) 
		{
			m_reportEngine.setSummary(summary.isSelected());
			cmd_report();
		}
	}

	/**************************************************************************
	 * 	Action Listener
	 * 	@param e event
	 */
	public void actionPerformed (Event e)
	{
		if (m_setting)
			return;
		if (e.getTarget() == comboReport)
			cmd_report();
		else if (e.getTarget() == bFind)
			cmd_find();
		else if (e.getTarget() == bExport)
			cmd_export();
		else if (e.getTarget() == previewType)
			cmd_render();
		else if (e.getTarget() == bSendMail)
			cmd_sendMail();
		else if (e.getTarget() == bArchive)
			cmd_archive();
		else if (e.getTarget() == bCustomize)
			cmd_customize();
		else if (e.getTarget() == bRefresh)
			iframe.invalidate();
		//
		else if (e.getTarget() == m_ddM)
			cmd_window(m_ddQ);
		else if (e.getTarget() == m_daM)
			cmd_window(m_daQ);
	}	//	actionPerformed
	
	private void cmd_render() {
		try { 
			renderReport();
		} catch (Exception e) {
			throw new AdempiereException("Failed to render report", e);
		}
	}

	/**
	 * 	Execute Drill to Query
	 * 	@param query query
	 *  @param component
	 */
	private void executeDrill (MQuery query, Component component)
	{
		int AD_Table_ID = AReport.getAD_Table_ID(query.getTableName());
		if (!Env.getUserRolePermissions().isCanReport(AD_Table_ID))
		{
			FDialog.error(m_WindowNo, this, "AccessCannotReport", query.getTableName());
			return;
		}
		if (AD_Table_ID != 0)
			new WReport (AD_Table_ID, query, component, 0);
		else
			log.warning("No Table found for " + query.getWhereClause(true));
	}	//	executeDrill
	
	/**
	 * 	Open Window
	 *	@param query query
	 */
	private void cmd_window (MQuery query)
	{
		if (query == null)
			return;
		AEnv.zoom(query);
	}	//	cmd_window
	
	/**
	 * 	Send Mail
	 */
	private void cmd_sendMail()
	{
		String to = "";
		MUser from = MUser.get(Env.getCtx(), Env.getAD_User_ID(Env.getCtx()));
		String subject = m_reportEngine.getName();
		String message = "";
		File attachment = null;
		
		try
		{
			attachment = File.createTempFile("mail", ".pdf");
			m_reportEngine.getPDF(attachment);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "", e);
		}

		new WEMailDialog (this,
			Msg.getMsg(Env.getCtx(), "SendMail"),
			from, to, subject, message, attachment);		
	}	//	cmd_sendMail

	/**
	 * 	Archive Report directly
	 */
	private void cmd_archive ()
	{
		try
		{
			final I_AD_Archive archive = Services.get(IArchiveBL.class).archive(
					m_reportEngine.getLayout(),
					m_reportEngine.getPrintInfo(),
					true, // force
					ITrx.TRXNAME_None);
			if (archive != null)
			{
				Services.get(IClientUI.class).info(m_WindowNo, "Archived");
			}
			else
			{
				Services.get(IClientUI.class).error(m_WindowNo, "ArchiveError");
			}

		}
		catch (Exception e)
		{
			log.log(Level.WARNING, e.getLocalizedMessage(), e);
			Services.get(IClientUI.class).error(m_WindowNo, "ArchiveError", e.getLocalizedMessage());
		}
	}	//	cmd_archive

	/**
	 * 	Export
	 */
	private void cmd_export()
	{		
		log.config("");
		if (!m_isCanExport)
		{
			FDialog.error(m_WindowNo, this, "AccessCannotExport", getTitle());
			return;
		}
		
		if(winExportFile == null)
		{
			winExportFile = new Window();
			winExportFile.setTitle(Msg.getMsg(m_ctx, "Export") + ": " + getTitle());
			winExportFile.setWidth("450px");
			winExportFile.setClosable(true);
			winExportFile.setBorder("normal");
			winExportFile.setStyle("position:absolute");

			cboType.setMold("select");
			
			cboType.getItems().clear();			
			cboType.appendItem("ps" + " - " + Msg.getMsg(m_ctx, "FilePS"), "ps");
			cboType.appendItem("xml" + " - " + Msg.getMsg(m_ctx, "FileXML"), "xml");
			ListItem li = cboType.appendItem("pdf" + " - " + Msg.getMsg(m_ctx, "FilePDF"), "pdf");
			cboType.appendItem("html" + " - " + Msg.getMsg(m_ctx, "FileHTML"), "html");
			cboType.appendItem("txt" + " - " + Msg.getMsg(m_ctx, "FileTXT"), "txt");
			cboType.appendItem("ssv" + " - " + Msg.getMsg(m_ctx, "FileSSV"), "ssv");
			cboType.appendItem("csv" + " - " + Msg.getMsg(m_ctx, "FileCSV"), "csv");
			cboType.appendItem("xls" + " - " + Msg.getMsg(m_ctx, "FileXLS"), "xls");
			cboType.setSelectedItem(li);
			
			Hbox hb = new Hbox();
			Div div = new Div();
			div.setAlign("right");
			div.appendChild(new Label("Files of Type: "));
			hb.appendChild(div);
			hb.appendChild(cboType);
			cboType.setWidth("100%");

			Vbox vb = new Vbox();
			vb.setWidth("390px");
			winExportFile.appendChild(vb);
			vb.appendChild(hb);
			vb.appendChild(confirmPanel);	
			confirmPanel.addActionListener(this);
		}
		
		AEnv.showCenterScreen(winExportFile);
		
	}	//	cmd_export
		
	private void exportFile()
	{
		try
		{
			ListItem li = cboType.getSelectedItem();
			if(li == null || li.getValue() == null)
			{
				FDialog.error(m_WindowNo, winExportFile, "FileInvalidExtension");
				return;
			}
			
			String ext = li.getValue().toString();
			
			byte[] data = null;
			File inputFile = null;
									
			if (ext.equals("pdf"))
			{
				data = m_reportEngine.createPDFData();
			}
			else if (ext.equals("ps"))
			{
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				m_reportEngine.createPS(baos);
				data = baos.toByteArray();
			}
			else if (ext.equals("xml"))
			{
				StringWriter sw = new StringWriter();							
				m_reportEngine.createXML(sw);
				data = sw.getBuffer().toString().getBytes();
			}
			else if (ext.equals("csv") || ext.equals("ssv"))
			{
				StringWriter sw = new StringWriter();							
				m_reportEngine.createCSV(sw, ',', m_reportEngine.getPrintFormat().getLanguage());
				data = sw.getBuffer().toString().getBytes();
			}
			else if (ext.equals("txt"))
			{
				StringWriter sw = new StringWriter();							
				m_reportEngine.createCSV(sw, '\t', m_reportEngine.getPrintFormat().getLanguage());
				data = sw.getBuffer().toString().getBytes();							
			}
			else if (ext.equals("html") || ext.equals("htm"))
			{
				StringWriter sw = new StringWriter();							
				m_reportEngine.createHTML(sw, false, m_reportEngine.getPrintFormat().getLanguage());
				data = sw.getBuffer().toString().getBytes();	
			}
			else if (ext.equals("xls"))
			{
				inputFile = File.createTempFile("Export", ".xls");							
				m_reportEngine.createXLS(inputFile, m_reportEngine.getPrintFormat().getLanguage());
			}
			else
			{
				FDialog.error(m_WindowNo, winExportFile, "FileInvalidExtension");
				return;
			}

			winExportFile.onClose();
			AMedia media = null;
			if (data != null)
				media = new AMedia(m_reportEngine.getPrintFormat().getName() + "." + ext, null, "application/octet-stream", data);
			else
				media = new AMedia(m_reportEngine.getPrintFormat().getName() + "." + ext, null, "application/octet-stream", inputFile, true);
			Filedownload.save(media, m_reportEngine.getPrintFormat().getName() + "." + ext);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "Failed to export content.", e);
		}
	}
	
	/**
	 * 	Report Combo - Start other Report or create new one
	 */
	private void cmd_report()
	{
		ListItem li = comboReport.getSelectedItem();
		if(li == null || li.getValue() == null) return;
		
		Object pp = li.getValue();
		if (pp == null)
			return;
		//
		MPrintFormat pf = null;
		int AD_PrintFormat_ID = Integer.valueOf(pp.toString());

		//	create new
		if (AD_PrintFormat_ID == -1)
		{
			int AD_ReportView_ID = m_reportEngine.getPrintFormat().getAD_ReportView_ID();
			if (AD_ReportView_ID != 0)
			{
				String name = m_reportEngine.getName();
				int index = name.lastIndexOf('_');
				if (index != -1)
					name = name.substring(0,index);
				pf = MPrintFormat.createFromReportView(m_ctx, AD_ReportView_ID, name);
			}
			else
			{
				int AD_Table_ID = m_reportEngine.getPrintFormat().getAD_Table_ID();
				pf = MPrintFormat.createFromTable(m_ctx, AD_Table_ID);
			}
			if (pf != null)
				fillComboReport(pf.get_ID());
			else
				return;
		}
		else
			pf = MPrintFormat.get (Env.getCtx(), AD_PrintFormat_ID, true);
		
		//	Get Language from previous - thanks Gunther Hoppe 
		if (m_reportEngine.getPrintFormat() != null)
		{
			pf.setLanguage(m_reportEngine.getPrintFormat().getLanguage());		//	needs to be re-set - otherwise viewer will be blank
			pf.setTranslationLanguage(m_reportEngine.getPrintFormat().getLanguage());
		}
		m_reportEngine.setPrintFormat(pf);
		
		try {
			renderReport();
		} catch (Exception e) {
			throw new AdempiereException("Failed to render report", e);
		}

		revalidate();

	}	//	cmd_report

	/**
	 * 	Query Report
	 */
	private void cmd_find()
	{
		int AD_Table_ID = m_reportEngine.getPrintFormat().getAD_Table_ID();
		
		String title = null; 
		String tableName = null;

		//	Get Find Tab Info
		String sql = "SELECT t.AD_Tab_ID "
			//	,w.Name, t.Name, w.IsDefault, t.SeqNo, ABS (tt.AD_Window_ID-t.AD_Window_ID)
			+ "FROM AD_Tab t"
			+ " INNER JOIN AD_Window w ON (t.AD_Window_ID=w.AD_Window_ID)"
			+ " INNER JOIN AD_Table tt ON (t.AD_Table_ID=tt.AD_Table_ID) "
			+ "WHERE tt.AD_Table_ID=? "
			+ "ORDER BY w.IsDefault DESC, t.SeqNo, ABS (tt.AD_Window_ID-t.AD_Window_ID)";
		final int AD_Tab_ID = DB.getSQLValue(ITrx.TRXNAME_None, sql, AD_Table_ID);

		// ASP
		final String ASPFilter = Services.get(IASPFiltersFactory.class)
				.getASPFiltersForClient(Env.getAD_Client_ID(Env.getCtx()))
				.getSQLWhereClause(I_AD_Tab.class);

		//
		sql = "SELECT Name, TableName FROM AD_Tab_v WHERE AD_Tab_ID=? " + ASPFilter;
		if (!Env.isBaseLanguage(Env.getCtx(), "AD_Tab"))
			sql = "SELECT Name, TableName FROM AD_Tab_vt WHERE AD_Tab_ID=?"
				+ " AND AD_Language='" + Env.getAD_Language(Env.getCtx()) + "' " + ASPFilter;
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, AD_Tab_ID);
			ResultSet rs = pstmt.executeQuery();
			//
			if (rs.next())
			{
				title = rs.getString(1);				
				tableName = rs.getString(2);
			}
			//
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
		}

		GridField[] findFields = null;
		if (tableName != null)
			findFields = GridField.createSearchFields(m_ctx, m_WindowNo, 0, AD_Tab_ID);
		
		if (findFields == null)		//	No Tab for Table exists
			bFind.setVisible(false);
		else
		{
            FindWindow find = new FindWindow(m_WindowNo, title, AD_Table_ID, tableName,
            		"", // whereClause
            		m_reportEngine.getQuery(), // metas
            		findFields, 1, AD_Tab_ID);
            if (!find.isCancel())
            {
            	m_reportEngine.setQuery(find.getQuery());
            	try {
            		renderReport();
            	} catch (Exception e) {
        			throw new AdempiereException("Failed to render report", e);
        		}
            	revalidate();
            }
            find = null;
		}
	}	//	cmd_find

	/**
	 * 	Call Customize
	 */
	private void cmd_customize()
	{
		int AD_Window_ID = 240;		//	hardcoded
		int AD_PrintFormat_ID = m_reportEngine.getPrintFormat().get_ID();
		final ADWindow win = AEnv.zoom(AD_Window_ID, MQuery.getEqualQuery("AD_PrintFormat_ID", AD_PrintFormat_ID));
		if (win != null)
		{
			win.getADWindowPanel().addEventListener(AbstractADWindowPanel.EVENT_OnWindowClose, new EventListener()
			{
				
				@Override
				public void onEvent(Event event) throws Exception
				{
					InterfaceWrapperHelper.refresh(m_reportEngine.getPrintFormat());
					m_reportEngine.setPrintFormat(m_reportEngine.getPrintFormat());
					renderReport();
				}
			});
		}
	}	//	cmd_customize
}

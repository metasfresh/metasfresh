package org.adempiere.webui.apps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.Window;
import org.adempiere.webui.desktop.IDesktop;
import org.adempiere.webui.process.WProcessInfo;
import org.adempiere.webui.session.SessionManager;
import org.adempiere.webui.window.FDialog;
import org.adempiere.webui.window.SimplePDFViewer;
import org.compiere.apps.ProcessCtl;
import org.compiere.print.ReportEngine;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoUtil;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.slf4j.Logger;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;
import org.zkoss.zk.au.out.AuEcho;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.North;
import org.zkoss.zkex.zul.South;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Html;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2007 Low Heng Sin											  *
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
 *****************************************************************************/



/**
 *	Dialog to Start process or report.
 *	Displays information about the process
 *		and lets the user decide to start it
 *  	and displays results (optionally print them).
 *  Calls ProcessCtl to execute.
 *  @author 	Low Heng Sin
 *  @author     arboleda - globalqss
 *  - Implement ShowHelp option on processes and reports
 */
public class ProcessDialog extends Window implements EventListener//, ASyncProcess
{
	/**
	 * generate serial version ID
	 */
	private static final long serialVersionUID = 5545731871518761455L;
	private static final String MESSAGE_DIV_STYLE = "max-height: 150pt; overflow: auto";	
	private Div messageDiv;
	private Center center;
	private North north;


	/**
	 * Dialog to start a process/report
	 * @param ctx
	 * @param parent
	 * @param title
	 * @param aProcess
	 * @param WindowNo
	 * @param AD_Process_ID
	 * @param tableId
	 * @param recordId
	 * @param autoStart
	 */
	public ProcessDialog (int AD_Process_ID, boolean isSOTrx)
	{
		
		log.info("Process=" + AD_Process_ID );
		m_ctx = Env.getCtx();
		m_WindowNo = SessionManager.getAppDesktop().registerWindow(this);
		this.setAttribute(IDesktop.WINDOWNO_ATTRIBUTE, m_WindowNo);
		m_AD_Process_ID = AD_Process_ID;
		Env.setContext(Env.getCtx(), m_WindowNo, "IsSOTrx", isSOTrx ? "Y" : "N");
		try
		{
			initComponents();
			init();
		}
		catch(Exception ex)
		{
			// metas: 03577: Just logging the error is not sufficient.
			// Dispose the window, because is not usable/available anyway and throw the exception up 
			dispose();
			log.error(ex.getLocalizedMessage(), ex);
			throw new AdempiereException(ex.getLocalizedMessage(), ex);
		}
	}	//	ProcessDialog

	private void initComponents() {
		this.setStyle("position: absolute; width: 100%; height: 100%");
		Borderlayout layout = new Borderlayout();
		layout.setStyle("position: absolute; width: 100%; height: 100%; border: none;");
		messageDiv = new Div();
		message = new Html();
		messageDiv.appendChild(message);
		messageDiv.setStyle(MESSAGE_DIV_STYLE);
		
		north = new North();
		north.appendChild(messageDiv);
		layout.appendChild(north);
		north.setAutoscroll(true);
		north.setStyle("border: none;");
		
		centerPanel = new Panel();
		center = new Center();
		layout.appendChild(center);
		center.appendChild(centerPanel);
		center.setFlex(true);
		center.setAutoscroll(true);
		center.setStyle("border: none");
		
		Div div = new Div();
		div.setAlign("center");
		Hbox hbox = new Hbox();
		String label = Msg.getMsg(Env.getCtx(), "Start");
		bOK = new Button(label.replaceAll("&", ""));
		bOK.setImage("/images/Ok16.png");
		bOK.setId("Ok");
		bOK.addEventListener(Events.ON_CLICK, this);
		bOK.setSclass("action-button");
		hbox.appendChild(bOK);
		
		label = Msg.getMsg(Env.getCtx(), "Cancel");
		Button btn = new Button(label.replaceAll("&", ""));
		btn.setImage("/images/Cancel16.png");
		btn.setId("Cancel");
		btn.addEventListener(Events.ON_CLICK, this);
		btn.setSclass("action-button");
		hbox.appendChild(btn);		
		div.appendChild(hbox);
		div.setStyle("padding: 10px");
		
		South south = new South();
		layout.appendChild(south);
		south.appendChild(div);		
		this.appendChild(layout);
	}

	private int m_WindowNo;
	private Properties m_ctx;
	private int 		    m_AD_Process_ID;
	private String		    m_Name = null;
	
	private boolean		    m_IsReport = false;
	private int[]		    m_ids = null;
	private StringBuffer	m_messageText = new StringBuffer();
	private String          m_ShowHelp = null; // Determine if a Help Process Window is shown
	
	private Panel centerPanel = null;
	private Html message = null;
	private Button bOK = null;
	
	private boolean valid = true;
	
	/**	Logger			*/
	private static Logger log = LogManager.getLogger(ProcessDialog.class);
	//
	private ProcessParameterPanel parameterPanel = null;
	
	private ProcessInfo m_pi = null;
	private boolean m_isLocked = false;
	private String initialMessage;
	private BusyDialog progressWindow;

	
	/**
	 * 	Set Visible 
	 * 	(set focus to OK if visible)
	 * 	@param visible true if visible
	 */
	@Override
	public boolean setVisible (boolean visible)
	{
		return super.setVisible(visible);
	}	//	setVisible

	/**
	 *	Dispose
	 */
	@Override
	public void dispose()
	{
		SessionManager.getAppDesktop().closeWindow(m_WindowNo);
		valid = false;
	}//	dispose

	/**
	 *	Dynamic Init
	 *  @return true, if there is something to process (start from menu)
	 */
	public boolean init()
	{
		log.info("");
		//
		boolean trl = !Env.isBaseLanguage(m_ctx, "AD_Process");
		String sql = "SELECT Name, Description, Help, IsReport, ShowHelp "
				+ "FROM AD_Process "
				+ "WHERE AD_Process_ID=?";
		if (trl)
			sql = "SELECT t.Name, t.Description, t.Help, p.IsReport, p.ShowHelp "
				+ "FROM AD_Process p, AD_Process_Trl t "
				+ "WHERE p.AD_Process_ID=t.AD_Process_ID"
				+ " AND p.AD_Process_ID=? AND t.AD_Language=?";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, m_AD_Process_ID);
			if (trl)
				pstmt.setString(2, Env.getAD_Language(m_ctx));
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				m_Name = rs.getString(1);
				m_IsReport = rs.getString(4).equals("Y");
				m_ShowHelp = rs.getString(5);
				//
				m_messageText.append("<b>");
				String s = rs.getString(2);		//	Description
				if (rs.wasNull())
					m_messageText.append(Msg.getMsg(m_ctx, "StartProcess?"));
				else
					m_messageText.append(s);
				m_messageText.append("</b>");
				
				s = rs.getString(3);			//	Help
				if (!rs.wasNull())
					m_messageText.append("<p>").append(s).append("</p>");
			}
		}
		catch (SQLException e)
		{
			// metas: 03577: Just logging the error is not sufficient.
			//log.error(sql, e);
			//return false;
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		if (m_Name == null)
			return false;
		//
		this.setTitle(m_Name);
		initialMessage = m_messageText.toString();
		message.setContent(initialMessage);
		bOK.setLabel(Msg.getMsg(Env.getCtx(), "Start"));

		//	Move from APanel.actionButton
		m_pi = new WProcessInfo(m_Name, m_AD_Process_ID);
		m_pi.setAD_User_ID (Env.getAD_User_ID(Env.getCtx()));
		m_pi.setAD_Client_ID(Env.getAD_Client_ID(Env.getCtx()));
		parameterPanel = new ProcessParameterPanel(m_WindowNo, m_pi, "70%");
		centerPanel.getChildren().clear();
		if ( parameterPanel.init() ) {
			centerPanel.appendChild(parameterPanel);
		} else {
			if (m_ShowHelp != null && m_ShowHelp.equals("N")) {
				startProcess();
			}
		}
		
		// Check if the process is a silent one
		if(m_ShowHelp != null && m_ShowHelp.equals("S"))
		{
			startProcess();
		}
		return true;
	}	//	init

	public void startProcess()
	{
		m_pi.setPrintPreview(true);

		this.lockUI(m_pi);
		Clients.response(new AuEcho(this, "runProcess", null));
	}

	public void runProcess() {
		try {
			ProcessCtl.process(null, m_WindowNo, parameterPanel, m_pi, ITrx.TRX_None);
		} finally {
			unlockUI(m_pi);
		}
	}
	
	@Override
	public void onEvent(Event event) {
		Component component = event.getTarget(); 
		if (component instanceof Button) {
			Button element = (Button)component;
			if ("Ok".equalsIgnoreCase(element.getId())) {
				if (!parameterPanel.validateParameters())
					return;
				if (element.getLabel().length() > 0)
					this.startProcess();
				else
					this.dispose();
			} else if ("Cancel".equalsIgnoreCase(element.getId())) {
				this.dispose();
			}
		}
		
	}

	public void lockUI(ProcessInfo pi) {
		if (m_isLocked) return;
		
		m_isLocked = true;
		
		showBusyDialog();
	}

	private void showBusyDialog() {
		progressWindow = new BusyDialog();
		progressWindow.setPage(this.getPage());
		progressWindow.doHighlighted();
	}

	public void unlockUI(ProcessInfo pi) {
		if (!m_isLocked) return;
		
		m_isLocked = false;
		hideBusyDialog();
		updateUI(pi);
	}

	private void hideBusyDialog() {
		if (progressWindow != null) {
			progressWindow.dispose();
			progressWindow = null;
		}
	}

	private void updateUI(ProcessInfo pi)
	{
		// Show process logs if any
		if (pi.isShowProcessLogs())
		{
			ProcessInfoUtil.setLogFromDB(pi);
			m_messageText.append("<p><font color=\"").append(pi.isError() ? "#FF0000" : "#0000FF").append("\">** ")
				.append(pi.getSummary())
				.append("</font></p>");
			m_messageText.append(pi.getLogInfo(true));
		}
		message.setContent(m_messageText.toString());
		
		bOK.setLabel("");		
		m_ids = pi.getIDs();
		
		//move message div to center to give more space to display potentially very long log info
		centerPanel.detach();
		messageDiv.detach();
		messageDiv.setStyle("");
		north.setVisible(false);
		center.appendChild(messageDiv);
		invalidate();
		
		Clients.response(new AuEcho(this, "onAfterProcess", null));
	}
	
	public void onAfterProcess() 
	{
		//
		afterProcessTask();

		// Close automatically
		if (m_IsReport && !m_pi.isError())
			this.dispose();

		// If the process is a silent one and no errors occured, close the dialog
		if(m_ShowHelp != null && m_ShowHelp.equals("S"))
			this.dispose();	
	}
	
	/**************************************************************************
	 *	Optional Processing Task
	 */
	private void afterProcessTask()
	{
		//  something to do?
		if (m_ids != null && m_ids.length > 0)
		{
			log.info("");
			//	Print invoices
			if (m_AD_Process_ID == 119)
				printInvoices();
			else if (m_AD_Process_ID == 118)
				printShipments();
		}

	}	//	afterProcessTask
	
	/**************************************************************************
	 *	Print Shipments
	 */
	private void printShipments()
	{		
		if (m_ids == null)
			return;
		if (!FDialog.ask(m_WindowNo, this, "PrintShipments"))
			return;
				
		m_messageText.append("<p>").append(Msg.getMsg(Env.getCtx(), "PrintShipments")).append("</p>");
		message.setContent(m_messageText.toString());
		showBusyDialog();
		Clients.response(new AuEcho(this, "onPrintShipments", null));
	}	//	printInvoices
	
	public void onPrintShipments() 
	{		
		//	Loop through all items
		List<File> pdfList = new ArrayList<File>();
		for (int i = 0; i < m_ids.length; i++)
		{
			int M_InOut_ID = m_ids[i];
			ReportEngine re = ReportEngine.get (Env.getCtx(), ReportEngine.SHIPMENT, M_InOut_ID);
			pdfList.add(re.getPDF());				
		}
		
		if (pdfList.size() > 1) {
			try {
				File outFile = File.createTempFile("PrintShipments", ".pdf");					
				Document document = null;
				PdfWriter copy = null;					
				for (File f : pdfList) 
				{
					String fileName = f.getAbsolutePath();
					PdfReader reader = new PdfReader(fileName);
					reader.consolidateNamedDestinations();
					if (document == null)
					{
						document = new Document(reader.getPageSizeWithRotation(1));
						copy = PdfWriter.getInstance(document, new FileOutputStream(outFile));
						document.open();
					}
					int pages = reader.getNumberOfPages();
					PdfContentByte cb = copy.getDirectContent();
					for (int i = 1; i <= pages; i++) {
						document.newPage();
						PdfImportedPage page = copy.getImportedPage(reader, i);
						cb.addTemplate(page, 0, 0);
					}
				}
				document.close();

				hideBusyDialog();
				Window win = new SimplePDFViewer(this.getTitle(), new FileInputStream(outFile));
				SessionManager.getAppDesktop().showWindow(win, "center");
			} catch (Exception e) {
				log.error(e.getLocalizedMessage(), e);
			}
		} else if (pdfList.size() > 0) {
			hideBusyDialog();
			try {
				Window win = new SimplePDFViewer(this.getTitle(), new FileInputStream(pdfList.get(0)));
				SessionManager.getAppDesktop().showWindow(win, "center");
			} catch (Exception e)
			{
				log.error(e.getLocalizedMessage(), e);
			}
		}
	}

	/**
	 *	Print Invoices
	 */
	private void printInvoices()
	{
		if (m_ids == null)
			return;
		if (!FDialog.ask(m_WindowNo, this, "PrintInvoices"))
			return;
		m_messageText.append("<p>").append(Msg.getMsg(Env.getCtx(), "PrintInvoices")).append("</p>");
		message.setContent(m_messageText.toString());
		showBusyDialog();
		Clients.response(new AuEcho(this, "onPrintInvoices", null));				
	}	//	printInvoices
	
	public void onPrintInvoices()
	{
		//	Loop through all items
		List<File> pdfList = new ArrayList<File>();
		for (int i = 0; i < m_ids.length; i++)
		{
			int C_Invoice_ID = m_ids[i];
			ReportEngine re = ReportEngine.get (Env.getCtx(), ReportEngine.INVOICE, C_Invoice_ID);
			pdfList.add(re.getPDF());				
		}
		
		if (pdfList.size() > 1) {
			try {
				File outFile = File.createTempFile("PrintInvoices", ".pdf");					
				Document document = null;
				PdfWriter copy = null;					
				for (File f : pdfList) 
				{
					PdfReader reader = new PdfReader(f.getAbsolutePath());
					if (document == null)
					{
						document = new Document(reader.getPageSizeWithRotation(1));
						copy = PdfWriter.getInstance(document, new FileOutputStream(outFile));
						document.open();						
					}
					PdfContentByte cb = copy.getDirectContent(); // Holds the PDF
					int pages = reader.getNumberOfPages();
					for (int i = 1; i <= pages; i++) {
						document.newPage();
						PdfImportedPage page = copy.getImportedPage(reader, i);
						cb.addTemplate(page, 0, 0);
					}
				}
				document.close();

				hideBusyDialog();
				Window win = new SimplePDFViewer(this.getTitle(), new FileInputStream(outFile));
				SessionManager.getAppDesktop().showWindow(win, "center");
			} catch (Exception e) {
				log.error(e.getLocalizedMessage(), e);
			}
		} else if (pdfList.size() > 0) {
			try {
				Window win = new SimplePDFViewer(this.getTitle(), new FileInputStream(pdfList.get(0)));
				SessionManager.getAppDesktop().showWindow(win, "center");
			} catch (Exception e)
			{
				log.error(e.getLocalizedMessage(), e);
			}
		}
	}

	public boolean isValid() {
		return valid;
	}

	public void executeASync(ProcessInfo pi) {
	}

	public boolean isUILocked() {
		return m_isLocked;
	}
}	//	ProcessDialog

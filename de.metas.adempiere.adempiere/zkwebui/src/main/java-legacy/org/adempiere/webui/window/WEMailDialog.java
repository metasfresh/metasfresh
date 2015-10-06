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
 * Contributor(s):                                                            *
 *****************************************************************************/
package org.adempiere.webui.window;

import java.beans.PropertyVetoException;
import java.io.File;
import java.util.StringTokenizer;
import java.util.logging.Level;

import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.ConfirmPanel;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.component.Textbox;
import org.adempiere.webui.component.Window;
import org.adempiere.webui.editor.WSearchEditor;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.adempiere.webui.panel.StatusBarPanel;
import org.compiere.model.Lookup;
import org.compiere.model.MClient;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MUser;
import org.compiere.model.MUserMail;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.EMail;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.South;
import org.zkoss.zul.Div;
import org.zkoss.zul.Separator;

/**
 *	EMail Dialog
 *
 *  @author 	Jorg Janke
 *  @version 	$Id: EMailDialog.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 *  
 *  globalqss: integrate phib fixing bug reported here
 *     http://sourceforge.net/tracker/index.php?func=detail&aid=1568765&group_id=176962&atid=879332
 * 
 *  phib - fixing bug [ 1568765 ] Close email dialog button broken
 *  
 *  globalqss - Carlos Ruiz - implement CC - FR [ 1754879 ] Enhancements on sending e-mail
 *
 */
public class WEMailDialog extends Window implements EventListener, ValueChangeListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2960343329714019910L;

	/**
	 * 	EMail Dialog
	 *	@param owner calling window
	 *	@param title title
	 *	@param from from
	 *	@param to to 
	 *	@param subject subject
	 *	@param message message
	 *	@param attachment optional attachment
	 */
	public WEMailDialog (Window owner, String title, MUser from, String to, 
		String subject, String message, File attachment)
	{
		super();
        this.setTitle(title);
		this.setWidth("500px");
		this.setHeight("500px");
		this.setClosable(true);
		this.setBorder("normal");
        this.setStyle("position:absolute");
		        
		commonInit(from, to, subject, message, attachment);				
	}	//	EmailDialog

	/**
	 * 	Common Init
	 *	@param from from
	 *	@param to to 
	 *	@param subject subject
	 *	@param message message
	 *	@param attachment optional attachment
	 */
	private void commonInit (MUser from, String to, 
		String subject, String message, File attachment)
	{
		m_client = MClient.get(Env.getCtx());
		try
		{
			int WindowNo = 0;
			int AD_Column_ID = 0;
			Lookup lookup = MLookupFactory.get (Env.getCtx(), WindowNo, 
				AD_Column_ID, DisplayType.Search,
				Env.getLanguage(Env.getCtx()), "AD_User_ID", 0, false,
				"EMail IS NOT NULL");
			
			fUser = new WSearchEditor(lookup, "AD_User_ID", "", false, false, true);
			fUser.addValueChangeListener(this);
			fCcUser = new WSearchEditor(lookup, "AD_User_ID", "", false, false, true);
			fCcUser.addValueChangeListener(this);
			jbInit();
		}
		catch(Exception ex)
		{
			log.log(Level.SEVERE, "EMailDialog", ex);
		}
		set(from, to, subject, message);
		setAttachment(attachment);
		AEnv.showCenterScreen(this);
	}	//	commonInit


	/**	Client				*/
	private MClient	m_client = null;
	/** Sender				*/
	private MUser	m_from = null;
	/** Primary Recipient	*/
	private MUser	m_user = null;
	/** Cc Recipient	*/
	private MUser	m_ccuser = null;
	//
	private String  m_to;
	private String  m_cc;
	private String  m_subject;
	private String  m_message;
	/**	File to be optionally attached	*/
	private File	m_attachFile;
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(WEMailDialog.class);

//	private CPanel mainPanel = new CPanel();
//	private BorderLayout mainLayout = new BorderLayout();
//	private CPanel headerPanel = new CPanel();
//	private GridBagLayout headerLayout = new GridBagLayout();
	private Textbox fFrom = new Textbox();//20);
	private Textbox fTo = new Textbox();//20);
	private Textbox fCc = new Textbox();//20);
	private WSearchEditor fUser = null;
	private WSearchEditor fCcUser = null;
	private Textbox fSubject = new Textbox();//40);
	private	Label lFrom = new Label();
	private Label lTo = new Label();
	private Label lCc = new Label();
	private Label lSubject = new Label();
	private Label lAttachment = new Label();
	private Textbox fAttachment = new Textbox();//40);
	private Textbox fMessage = new Textbox();
	private ConfirmPanel confirmPanel = new ConfirmPanel(true);
	private StatusBarPanel statusBar = new StatusBarPanel();

	/**
	 *	Static Init
	 */
	void jbInit() throws Exception
	{
		lFrom.setValue(Msg.getMsg(Env.getCtx(), "From") + ":");
		lTo.setValue(Msg.getMsg(Env.getCtx(), "To") + ":");
		lCc.setValue(Msg.getMsg(Env.getCtx(), "Cc") + ":");
		lSubject.setValue(Msg.getMsg(Env.getCtx(), "Subject") + ":");
		lAttachment.setValue(Msg.getMsg(Env.getCtx(), "Attachment") + ":");
		fFrom.setReadonly(true);
		statusBar.setStatusDB(null);
		//
				
		Grid grid = new Grid();
		grid.setWidth("480px");
        grid.setStyle("margin:0; padding:0; position: absolute; align: center; valign: center;");
        grid.makeNoStrip();
        grid.setOddRowSclass("even");
        
		Rows rows = new Rows();
		grid.appendChild(rows);
		
		Row row = new Row();
		rows.appendChild(row);
		Div div = new Div();
		div.setAlign("right");
		div.appendChild(lFrom);
		row.appendChild(div);
		row.appendChild(fFrom);
		fFrom.setWidth("100%");
		
		row = new Row();
		rows.appendChild(row);
		div = new Div();
		div.setAlign("right");
		div.appendChild(lTo);
		row.appendChild(div);
		row.appendChild(fUser.getComponent());
		fUser.getComponent().setWidth("100%");
		
		row = new Row();
		rows.appendChild(row);
		row.appendChild(new Label(""));
		row.appendChild(fTo);
		fTo.setWidth("100%");
		
		row = new Row();
		rows.appendChild(row);
		div = new Div();
		div.setAlign("right");
		div.appendChild(lCc);
		row.appendChild(div);
		row.appendChild(fCcUser.getComponent());
		fCcUser.getComponent().setWidth("100%");
		
		row = new Row();
		rows.appendChild(row);
		row.appendChild(new Label(""));
		row.appendChild(fCc);
		fCc.setWidth("100%");
		
		row = new Row();
		rows.appendChild(row);
		row.setSpans("2");
		row.appendChild(new Separator());
		
		row = new Row();
		rows.appendChild(row);
		div = new Div();
		div.setAlign("right");
		div.appendChild(lSubject);
		row.appendChild(div);
		row.appendChild(fSubject);
		fSubject.setWidth("100%");
		
		row = new Row();
		rows.appendChild(row);
		row.setSpans("2");
		row.appendChild(new Separator());
		
		row = new Row();
		rows.appendChild(row);
		div = new Div();
		div.setAlign("right");
		div.appendChild(lAttachment);
		row.appendChild(div);
		row.appendChild(fAttachment);
		fAttachment.setWidth("100%");
		
		row = new Row();
		rows.appendChild(row);
		row.setSpans("2");
		row.appendChild(fMessage);
		fMessage.setWidth("100%");
		fMessage.setRows(10);
		
		row = new Row();
		rows.appendChild(row);
		row.setSpans("2");
		row.appendChild(confirmPanel);
		confirmPanel.addActionListener(this);
		
		Borderlayout layout = new Borderlayout();
		layout.setWidth("490px");
		layout.setHeight("470px");
		layout.setStyle("background-color: white; position: absolute;");
		
		Center center = new Center();
		center.appendChild(grid);
		layout.appendChild(center);
		center.setStyle("background-color: white");
		
		South south = new South();
		south.appendChild(statusBar);
		layout.appendChild(south);
		south.setStyle("background-color: white");
		
		this.appendChild(layout);		
	}	//	jbInit

	/**
	 *	Set all properties
	 */
	public void set (MUser from, String to, String subject, String message)
	{
		//	Content
		setFrom(from);
		setTo(to);
		setSubject(subject);
		setMessage(message);
		//
		statusBar.setStatusLine(m_client.getSMTPHost());
	}	//	set

	/**
	 *  Set Address
	 */
	public void setTo(String newTo)
	{
		m_to = newTo;
		fTo.setText(m_to);
	}	//	setTo

	/**
	 *  Set CC Address
	 */
	public void setCc(String newCc)
	{
		m_cc = newCc;
		fCc.setText(m_cc);
	}	//	setCc

	/**
	 *  Get Address
	 */
	public String getTo()
	{
		m_to = fTo.getText();
		return m_to;
	}	//	getTo

	/**
	 *  Get CC Address
	 */
	public String getCc()
	{
		m_cc = fCc.getText();
		return m_cc;
	}	//	getCc

	/**
	 *  Set Sender
	 */
	public void setFrom(MUser newFrom)
	{
		m_from = newFrom;
		if (newFrom == null 
			|| !newFrom.isEMailValid() 
			|| !newFrom.isCanSendEMail())
		{
//			confirmPanel.getOKButton().setEnabled(false);
			fFrom.setText("**Invalid**");
		}
		else
			fFrom.setText(m_from.getEMail());
	}	//	setFrom

	/**
	 *  Get Sender
	 */
	public MUser getFrom()
	{
		return m_from;
	}	//	getFrom

	/**
	 *  Set Subject
	 */
	public void setSubject(String newSubject)
	{
		m_subject = newSubject;
		fSubject.setText(m_subject);
	}	//	setSubject

	/**
	 *  Get Subject
	 */
	public String getSubject()
	{
		m_subject = fSubject.getText();
		return m_subject;
	}	//	getSubject

	/**
	 *  Set Message
	 */
	public void setMessage(String newMessage)
	{
		m_message = newMessage;
		fMessage.setText(m_message);
//		fMessage.setCaretPosition(0);
	}   //  setMessage

	/**
	 *  Get Message
	 */
	public String getMessage()
	{
		m_message = fMessage.getText();
		return m_message;
	}   //  getMessage

	/**
	 *  Set Attachment
	 */
	public void setAttachment (File attachment)
	{
		m_attachFile = attachment;
		if (attachment == null)
		{
			lAttachment.setVisible(false);
			fAttachment.setVisible(false);
		}
		else
		{
			lAttachment.setVisible(true);
			fAttachment.setVisible(true);
			fAttachment.setText(attachment.getName());
			fAttachment.setReadonly(true);
		}
	}	//	setAttachment

	/**
	 *  Get Attachment
	 */
	public File getAttachment()
	{
		return m_attachFile;
	}	//	getAttachment

	/**************************************************************************
	 * 	Action Listener - Send email
	 */
	public void onEvent(Event event) throws Exception {		
		if (event.getTarget().getId().equals(ConfirmPanel.A_CANCEL))
			onClose();
		
		if (getTo() == null || getTo().length() == 0)
		{
			return;
		}
		//	Send
		if (event.getTarget().getId().equals(ConfirmPanel.A_OK))
		{

			StringTokenizer st = new StringTokenizer(getTo(), " ,;", false);
			String to = st.nextToken();
			EMail email = m_client.createEMail(getFrom(), to, getSubject(), getMessage());
			String status = "Check Setup";
			if (email != null)
			{
				while (st.hasMoreTokens())
					email.addTo(st.nextToken());
				// cc
				StringTokenizer stcc = new StringTokenizer(getCc(), " ,;", false);
				while (stcc.hasMoreTokens())
				{
					String cc = stcc.nextToken();
					if (cc != null && cc.length() > 0)
                        email.addCc(cc);
				}
				//	Attachment
				if (m_attachFile != null && m_attachFile.exists())
					email.addAttachment(m_attachFile);
				status = email.send();
				//
				if (m_user != null)
					new MUserMail(m_user, m_user.getAD_User_ID(), email).save();
				if (email.isSentOK())
				{
					FDialog.info(0, this, "MessageSent");
					onClose();
				}
				else
					FDialog.error(0, this, "MessageNotSent", status);
			}
			else
				FDialog.error(0, this, "MessageNotSent", status);
			//
//			confirmPanel.getOKButton().setEnabled(false);
//			setCursor(Cursor.getDefaultCursor());
		}
		else if (event.getTarget().getId().equals(ConfirmPanel.A_CANCEL))
			onClose();
	}

	/**
	 * 	Vetoable Change - User selected 
	 *	@param evt
	 *	@throws PropertyVetoException
	 */
	public void valueChange(ValueChangeEvent evt) {
		WSearchEditor source = (WSearchEditor) evt.getSource();
        Object value = evt.getNewValue();

		log.info("Value=" + value);

        if (value == null)
        {
            return;
        }
        
		if (source.equals(fUser)) {
			// fUser			
			if (value == null)
				fTo.setText("");
			if (value instanceof Integer)
			{
				int AD_User_ID = ((Integer)value).intValue();
				m_user = MUser.get(Env.getCtx(), AD_User_ID);
				fTo.setValue(m_user.getEMail());
			}
		} else {
			// fCcUser
			if (value == null)
				fCc.setText("");
			if (value instanceof Integer)
			{
				int AD_User_ID = ((Integer)value).intValue();
				m_ccuser = MUser.get(Env.getCtx(), AD_User_ID);
				fCc.setValue(m_ccuser.getEMail());
			}
		}

        return;
	}

}	//	VEMailDialog
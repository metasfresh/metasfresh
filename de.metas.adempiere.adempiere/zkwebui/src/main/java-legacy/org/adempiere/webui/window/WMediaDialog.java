/******************************************************************************
 * Copyright (C) 2008 Low Heng Sin  All Rights Reserved.                      *
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
 * Posterita Ltd., 3, Draper Avenue, Quatre Bornes, Mauritius                 *
 * or via info@posterita.org or http://www.posterita.org/                     *
 *****************************************************************************/

package org.adempiere.webui.window;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;

import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.Window;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.slf4j.Logger;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.North;
import org.zkoss.zkex.zul.South;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Iframe;

/**
 * 
 * @author Low Heng Sin
 *
 */
public class WMediaDialog extends Window implements EventListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -329919930778203892L;

	private static Logger log = LogManager.getLogger(WMediaDialog.class);

	/** data	*/
	private Object m_data;
	
	/** Change					*/
	private boolean m_change = false;

	private Iframe preview = new Iframe();

	private Button bDelete = new Button();
	private Button bSave = new Button();
	private Button bLoad = new Button();
	private Button bCancel = new Button();
	private Button bOk = new Button();

	private Panel previewPanel = new Panel();
	
	private Borderlayout mainPanel = new Borderlayout();

	private Hbox toolBar = new Hbox();	
	
	private Hbox confirmPanel = new Hbox();

	private boolean m_cancel;

	/**
	 *	Constructor.
	 *  @param title
	 *  @param data
	 */
	
	public WMediaDialog(String title, Object data)
	{
		super();
		this.setTitle(title);
		
		try
		{
			staticInit();
			m_data = data;
			displayData();
		}
		catch (Exception ex)
		{
			log.error("", ex);
		}
		
		try
		{
			AEnv.showWindow(this);
		}
		catch (Exception e)
		{
		}		
	} // WAttachment

	/**
	 *	Static setup.
	 *  <pre>
	 *  - northPanel
	 *      - toolBar
	 *      - title
	 *  - centerPane [split]
	 * 		- graphPanel (left)
	 *		  	- gifScroll - gifPanel
	 *			- pdfViewer
	 *  	- text (right)
	 *  - confirmPanel
	 *  </pre>
	 *  @throws Exception
	 */
	
	void staticInit() throws Exception
	{
		this.setWidth("500px");
		this.setHeight("600px");
		this.setClosable(true);
		this.setBorder("normal");
		this.appendChild(mainPanel);
		mainPanel.setHeight("100%");
		mainPanel.setWidth("100%");
		
		
		North northPanel = new North();
		northPanel.setCollapsible(false);
		northPanel.setSplittable(false);
		
		toolBar.appendChild(bLoad);
		toolBar.appendChild(bDelete);
		toolBar.appendChild(bSave);
		
		mainPanel.appendChild(northPanel);
		northPanel.appendChild(toolBar);
		

		bSave.setEnabled(false);
		bSave.setImage("/images/Export24.png");
		bSave.setTooltiptext(Msg.getMsg(Env.getCtx(), "AttachmentSave"));
		bSave.addEventListener(Events.ON_CLICK, this);

		bLoad.setImage("/images/Import24.png");
		bLoad.setTooltiptext(Msg.getMsg(Env.getCtx(), "Load"));
		bLoad.addEventListener(Events.ON_CLICK, this);

		bDelete.setImage("/images/Delete24.png");
		bDelete.setTooltiptext(Msg.getMsg(Env.getCtx(), "Delete"));
		bDelete.addEventListener(Events.ON_CLICK, this);

		previewPanel.appendChild(preview);
		preview.setHeight("100%");
		preview.setWidth("100%");
			
		Center centerPane = new Center();
		centerPane.setAutoscroll(true);
		centerPane.setFlex(true);
		mainPanel.appendChild(centerPane);
		centerPane.appendChild(previewPanel);
		
		South southPane = new South();
		mainPanel.appendChild(southPane);
		southPane.appendChild(confirmPanel);
		southPane.setHeight("30px");
		
		bCancel.setImage("/images/Cancel24.png");
		bCancel.addEventListener(Events.ON_CLICK, this);

		bOk.setImage("/images/Ok24.png");
		bOk.addEventListener(Events.ON_CLICK, this);
		
		confirmPanel.appendChild(bCancel);
		confirmPanel.appendChild(bOk);
	}
	
	/**
	 * 	Dispose
	 */
	
	public void dispose ()
	{
		preview = null;
		this.detach();
	} // dispose
	
	/**
	 *  Display gif or jpg in gifPanel
	 * 	@param index index
	 */
	
	private void displayData ()
	{
		//	Reset UI		
		preview.setVisible(false);

		bDelete.setEnabled(false);
		bSave.setEnabled(false);

		if (m_data != null)
		{
			bSave.setEnabled(true);
			bDelete.setEnabled(true);
			
			try
			{
				AMedia media = createMedia();
				
				preview.setContent(media);
				preview.setVisible(true);
			}
			catch (Exception e)
			{
				log.error("Failed to preview content", e);
			}
		}		
	}   //  displayData

	private AMedia createMedia() throws SQLException {
		AMedia media;
		String contentType = null;
		if (m_data instanceof byte[])
		{
			media = new AMedia(this.getTitle(), null, contentType, (byte[])m_data);
		}
		else if (m_data instanceof Blob)
		{
			media = new AMedia(this.getTitle(), null, contentType, ((Blob)m_data).getBinaryStream());
		}
		else if (m_data instanceof Clob)
		{
			Clob clob = (Clob)m_data;
			long length = clob.length() > 100 ? 100 : clob.length();
			String data = ((Clob)m_data).getSubString(1, new Long(length).intValue());
			if (data.toUpperCase().indexOf("<html>") >= 0)
			{
				contentType = "text/html";
			}
			else
			{
				contentType = "text/plain";
			}
			media = new AMedia(this.getTitle(), null, contentType, ((Clob)m_data).getCharacterStream());
		}
		else
		{
			contentType = "text/plain";
			media = new AMedia(this.getTitle(), null, contentType, m_data.toString());
		}
		return media;
	}
	
	/**
	 *	Action Listener
	 *  @param e event
	 */
	
	public void onEvent(Event e)
	{
		//	log.info(e.getActionCommand());
		//	Save and Close
		
		if (e.getTarget() == bOk)
		{
			dispose();
		}
	
		//	Cancel
		
		else if (e.getTarget() == bCancel)
		{
			m_cancel = true;
			dispose();
		}
		
		//	clear data
		
		else if (e.getTarget() == bDelete)
		{
			m_data = null;
			m_change = true;
			displayData();
		}
		
		//	Load Attachment
		
		else if (e.getTarget() == bLoad)
			loadFile();
		
		//	Open Attachment
		
		else if (e.getTarget() == bSave)
		{
			save();
		}
	}	//	onEvent
	
	/**************************************************************************
	 *	Load file 
	 */
	
	private void loadFile()
	{
		log.info("");
		
		Media media = null;
		
		try 
		{
			media = Fileupload.get(); 
			
			if (media == null)
				return;
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	
		String fileName = media.getName(); 
		log.info(fileName);
		//update		
		m_change = true;
		m_data = Util.readBytes(media.getStreamData());
		displayData();
		
	}	//	getFileName

	/**
	 *	download
	 */
	
	private void save()
	{
		if (m_data == null)
			return;

		try
		{
			AMedia media = createMedia();
			Filedownload.save(media);
		}
		catch (Exception e)
		{
			log.error("Failed to export content.", e);
		}
	}	//	saveAttachmentToFile
	
	public boolean isCancel() {
		return m_cancel;
	}
	
	public boolean isChange() {
		return m_change;
	}
	
	public Object getData() {
		return m_data;
	}
	
}

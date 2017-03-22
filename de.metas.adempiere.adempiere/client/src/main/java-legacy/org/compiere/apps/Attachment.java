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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.images.Images;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pdf.Document;
import org.adempiere.pdf.viewer.PDFViewerBean;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Attachment;
import org.compiere.model.MAttachment;
import org.compiere.model.MAttachmentEntry;
import org.compiere.model.MSysConfig;
import org.compiere.swing.CButton;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CDialog;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextArea;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.slf4j.Logger;
import org.slf4j.Logger;

import de.metas.adempiere.Constants;
import de.metas.adempiere.form.IClientUI;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;

/**
 *  Attachment Viewer
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: Attachment.java,v 1.4 2006/08/10 01:00:27 jjanke Exp $
 */
public final class Attachment extends CDialog
	implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2497487523050526742L;

	/**
	 *	Constructor.
	 *	loads Attachment, if ID <> 0
	 *  @param frame frame
	 *  @param WindowNo window no
	 *  @param AD_Attachment_ID attachment
	 *  @param AD_Table_ID table
	 *  @param Record_ID record key
	 *  @param trxName transaction
	 */
	public Attachment(Frame frame, int WindowNo, int AD_Attachment_ID, int AD_Table_ID, int Record_ID)
	{
		super (frame, Msg.getMsg(Env.getCtx(), "Attachment"), true);
		//	needs to be modal otherwise APanel does not recognize change.
		log.info("ID=" + AD_Attachment_ID
			+ ", Table=" + AD_Table_ID + ", Record=" + Record_ID);
		//
		m_WindowNo = WindowNo;
		//
		try
		{
			staticInit();
		}
		catch (Exception ex)
		{
			log.error("", ex);
		}
		
		//	Load/Create Model
		if (AD_Attachment_ID <= 0)
		{
			m_attachment = new MAttachment (Env.getCtx(), AD_Table_ID, Record_ID, ITrx.TRXNAME_None);
		}
		else
		{
			m_attachment = new MAttachment (Env.getCtx(), AD_Attachment_ID, ITrx.TRXNAME_None);
		}
		loadAttachments();
	}	//	Attachment
	
	/**	Window No				*/
	private int				m_WindowNo;
	/** Attachment				*/
	private MAttachment		m_attachment;
	/** Change					*/
	private boolean			m_change = false;
	/**	Logger			*/
	private static Logger log = LogManager.getLogger(Attachment.class);
	
	//
	private CPanel mainPanel = new CPanel();
	private CTextArea text = new CTextArea();
	private CButton bOpen = new CButton();
	private CButton bSave = new CButton();
	private CButton bLoad = new CButton();
	private ConfirmPanel confirmPanel = ConfirmPanel.newWithOKAndCancel();
	private CPanel toolBar = new CPanel(new FlowLayout(FlowLayout.LEADING, 5,5));
	private CButton bDelete = new CButton();
	private CButton bDeleteAll = null;
	private CComboBox<String> cbContent = new CComboBox<>();
	private JSplitPane centerPane = new JSplitPane();
	//
	private CPanel graphPanel = new CPanel(new BorderLayout());
	private GImage gifPanel = new GImage();
	private JScrollPane gifScroll = new JScrollPane (gifPanel);
	
	// metas: Document.getViewer() may burn a lot of time (Document's static
	// initializer), so only use it when we have to.
	private PDFViewerBean pdfViewer = null;	//Document.getViewer();
	private CTextArea info = new CTextArea();

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
		final BorderLayout mainLayout = new BorderLayout();
		mainPanel.setLayout(mainLayout);
		mainLayout.setHgap(5);
		mainLayout.setVgap(5);
		this.getContentPane().add(mainPanel);
		
		final CPanel northPanel = new CPanel();
		northPanel.setLayout(new BorderLayout());
		northPanel.add(toolBar, BorderLayout.CENTER);
		toolBar.add(bLoad);
		toolBar.add(bDelete);
		toolBar.add(bSave);
		toolBar.add(bOpen);
		toolBar.add(cbContent);
		mainPanel.add(northPanel, BorderLayout.NORTH);

		//
		bOpen.setEnabled(false);
		bOpen.setIcon(Images.getImageIcon2("Editor24"));
		bOpen.setMargin(new Insets(0, 2, 0, 2));
		bOpen.setToolTipText(Msg.getMsg(Env.getCtx(), "Open"));
		bOpen.addActionListener(this);
		//
		bSave.setEnabled(false);
		bSave.setIcon(Images.getImageIcon2("Export24"));
		bSave.setMargin(new Insets(0, 2, 0, 2));
		bSave.setToolTipText(Msg.getMsg(Env.getCtx(), "AttachmentSave"));
		bSave.addActionListener(this);
		//
		bLoad.setIcon(Images.getImageIcon2("Import24"));
		bLoad.setMargin(new Insets(0, 2, 0, 2));
		bLoad.setToolTipText(Msg.getMsg(Env.getCtx(), "Load"));
		bLoad.addActionListener(this);
		//
		bDelete.setIcon(Images.getImageIcon2("Delete24"));
		bDelete.setMargin(new Insets(0, 2, 0, 2));
		bDelete.setToolTipText(Msg.getMsg(Env.getCtx(), "Delete"));
		bDelete.addActionListener(this);
		//
		Dimension size = cbContent.getPreferredSize();
		size.width = 200;
		cbContent.setPreferredSize(size);
	//	cbContent.setToolTipText(text);
		cbContent.addActionListener(this);
		cbContent.setLightWeightPopupEnabled(false);	//	Acrobat Panel is heavy
		//
		text.setBackground(AdempierePLAF.getInfoBackground());
		text.setPreferredSize(new Dimension(200, 200));
		//
		mainPanel.add(confirmPanel, BorderLayout.SOUTH);
		confirmPanel.setActionListener(this);
		bDeleteAll = ConfirmPanel.createDeleteButton(true);
		if (!MSysConfig.getBooleanValue(Constants.SYSCONFIG_Attachment_HideDeleteAll, false, Env.getAD_Client_ID(Env.getCtx()))) // metas: tsa: 01819
			confirmPanel.addButton(bDeleteAll);
		bDeleteAll.addActionListener(this);
		//
		info.setText("-");
		info.setReadWrite(false);
		graphPanel.add(info, BorderLayout.CENTER);
		//
		mainPanel.add(centerPane, BorderLayout.CENTER);
		centerPane.add(graphPanel, JSplitPane.LEFT);
		centerPane.add(text, JSplitPane.RIGHT);
		centerPane.setResizeWeight(.75);	//	more to graph
	}	//	jbInit

	
	/**
	 * 	Dispose
	 */
	@Override
	public void dispose()
	{
		pdfViewer = null;
		super.dispose ();
	}	//	dispose
	
	
	@Override
	public void requestFocus()
	{
		if(text != null)
		{
			text.requestFocus();
		}
	}
	
	/**
	 *	Load Attachments
	 */
	private void loadAttachments()
	{
		log.info("");
		//	Set Text/Description
		String sText = m_attachment.getTextMsg();
		if (sText == null)
			text.setText("");
		else
			text.setText(sText);

		//	Set Combo
		int size = m_attachment.getEntryCount();
		for (int i = 0; i < size; i++)
			cbContent.addItem(m_attachment.getEntryName(i));
		if (size > 0)
			cbContent.setSelectedIndex(0);
		else
			displayData(0);
	}	//	loadAttachment

	/**
	 *  Display gif or jpg in gifPanel
	 * 	@param index index
	 */
	private void displayData (final int index)
	{
		MAttachmentEntry entry = m_attachment.getEntry(index); 
		log.info("Index=" + index + " - " + entry);
		
		//	Reset UI
		gifPanel.setImage(null);
		graphPanel.removeAll();
		//
		bDelete.setEnabled(false);
		bOpen.setEnabled(false);
		bSave.setEnabled(false);

		//	no attachment
		if (entry == null || entry.getData() == null)
		{
			info.setText("-");
		}
		else
		{
			bOpen.setEnabled(true);
			bSave.setEnabled(true);
			bDelete.setEnabled(true);
			log.info(entry.toStringX());
			//
			info.setText(entry.toStringX());
			if (entry.isPDF()) {
				
				// metas: get the viewer when we need it
				if (pdfViewer == null) {
					pdfViewer = Document.getViewer();
				}
				if (pdfViewer != null) {

					try {
						pdfViewer.loadPDF(entry.getInputStream());
						pdfViewer.setScale(50);
						//
						graphPanel.add(pdfViewer, BorderLayout.CENTER);
						
						pdfViewer.invalidate();
						pdfViewer.revalidate();
						pdfViewer.repaint(50L);
						
					} catch (Exception e) {
						log.error("(pdf)", e);
					}
				}
			}
			else if (entry.isGraphic())
			{
				//  Can we display it
				final Image image = Toolkit.getDefaultToolkit().createImage(entry.getData());
				if (image != null)
				{
					gifPanel.setImage(image);

					final Dimension size = gifPanel.getPreferredSize();
					if (size.width == -1 && size.height == -1)
					{
						log.error("Invalid Image");
					}
					else
					{
						//	size.width += 40;
						//	size.height += 40;
						gifPanel.invalidate();
						graphPanel.add(gifScroll, BorderLayout.CENTER);
						
						graphPanel.invalidate();
						graphPanel.revalidate();
						graphPanel.repaint(50L);
					}
				}
				else
					log.error("Could not create image");
			}
		}
		if (graphPanel.getComponentCount() == 0)
		{
			graphPanel.add(info, BorderLayout.CENTER);
		}
		
		graphPanel.repaint(50L); // timeout before update (millis)
	}   //  displayData


	/**
	 * 	Get File Name with index
	 *	@param index index
	 *	@return file name or null
	 */
	private String getFileName (int index)
	{
		String fileName = null;
		if (cbContent.getItemCount() > index)
			fileName = cbContent.getItemAt(index);
		return fileName;
	}	//	getFileName

	/**
	 *	Action Listener
	 *  @param event event
	 */
	@Override
	public void actionPerformed(ActionEvent event)
	{
		try
		{
			actionPerformed0(event);
		}
		catch (Exception ex)
		{
			Services.get(IClientUI.class).error(m_WindowNo, ex);
		}
	}
	
	private void actionPerformed0(final ActionEvent event)
	{
		//	Save and Close
		if (event.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			final String newText = Util.coalesce(text.getText(), "");
			final String oldText = Util.coalesce(m_attachment.getTextMsg(), "");
			if (!m_change)
				m_change = !newText.equals(oldText);
			if (newText.length() > 0 || m_attachment.getEntryCount() > 0)
			{
				if (m_change)
				{
					m_attachment.markColumnChanged(I_AD_Attachment.COLUMNNAME_TextMsg); // force saving
					m_attachment.setTextMsg(text.getText());
					InterfaceWrapperHelper.save(m_attachment);;
				}
			}
			else
			{
				InterfaceWrapperHelper.delete(m_attachment);
			}
			
			dispose();
		}
		//	Cancel
		else if (event.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			dispose();
		}
		//	Delete Attachment
		else if (event.getSource() == bDeleteAll)
		{
			if (deleteAttachment()) // metas: tsa:  US1115: dispose only if the user confirmed deletion
				dispose();
		}
		//	Delete individual entry and Return
		else if (event.getSource() == bDelete)
			deleteAttachmentEntry();
		//	Show Data
		else if (event.getSource() == cbContent)
			displayData (cbContent.getSelectedIndex());
		//	Load Attachment
		else if (event.getSource() == bLoad)
			loadFile();
		//	Open Attachment
		else if (event.getSource() == bSave)
			saveAttachmentToFile();
		//	Open Attachment
		else if (event.getSource() == bOpen)
		{
			if (!openAttachment())
				saveAttachmentToFile();
		}
	}	//	actionPerformed

	
	/**************************************************************************
	 *	Load file for attachment
	 */
	private void loadFile()
	{
		log.info("");
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setDialogTitle(Msg.getMsg(Env.getCtx(), "AttachmentNew"));
		chooser.setMultiSelectionEnabled(true);
		int returnVal = chooser.showOpenDialog(this);
		if (returnVal != JFileChooser.APPROVE_OPTION)
			return;
		//
		File[] files = chooser.getSelectedFiles();
		for (File file : files) {
			int cnt = m_attachment.getEntryCount();
			
			//update
			boolean add = true;
			for (int i = 0; i < cnt; i++) 
			{
				if (m_attachment.getEntryName(i).equals(file.getName()))
				{
					if (m_attachment.updateEntry(i, file)) 
					{
						cbContent.setSelectedItem(file.getName());
						m_change = true;
					}
					add = false;
					break;
				}
			}

			if (add)
			{
				//new
				if (m_attachment.addEntry(file))
				{
					cbContent.addItem(file.getName());
					cbContent.setSelectedIndex(cbContent.getItemCount()-1);
					m_change = true;
				}
			}
		}
	}	//	getFileName

	/**
	 *	Delete entire Attachment
	 * @return true if attachment was deleted (i.e. user confirmed)
	 */
	// metas: tsa:  US1115: changed the return type from void to boolean; true if user confirmed the deletion
	private boolean deleteAttachment()
	{
		log.info("");
		if (ADialog.ask(m_WindowNo, this, "AttachmentDelete?"))
		{
			m_attachment.delete(true);
			return true;
		}
		return false;
	}	//	deleteAttachment

	/**
	 *	Delete Attachment Entry
	 */
	private void deleteAttachmentEntry()
	{
		log.info("");
		int index = cbContent.getSelectedIndex();
		String fileName = getFileName(index);
		if (fileName == null)
			return;
		//
		if (ADialog.ask(m_WindowNo, this, "AttachmentDeleteEntry?", fileName))
		{
			if (m_attachment.deleteEntry(index))
				cbContent.removeItemAt(index);
			m_change = true;
		}
	}	//	deleteAttachment


	/**
	 *	Save Attachment to File
	 */
	private void saveAttachmentToFile()
	{
		int index = cbContent.getSelectedIndex();
		log.info("index=" + index);
		if (m_attachment.getEntryCount() < index)
			return;

		String fileName = getFileName(index);
		String ext = fileName.substring (fileName.lastIndexOf('.'));
		log.info( "Ext=" + ext);

		JFileChooser chooser = new JFileChooser();
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setDialogTitle(Msg.getMsg(Env.getCtx(), "AttachmentSave"));
		File f = new File(fileName);
		chooser.setSelectedFile(f);
		//	Show dialog
		int returnVal = chooser.showSaveDialog(this);
		if (returnVal != JFileChooser.APPROVE_OPTION)
			return;
		File saveFile = chooser.getSelectedFile();
		if (saveFile == null)
			return;

		log.info("Save to " + saveFile.getAbsolutePath());
		m_attachment.getEntryFile(index, saveFile);
	}	//	saveAttachmentToFile

	/**
	 *	Open the temporary file with the application associated with the extension in the file name
	 *	@return true if file was opened with third party application
	 */
	private boolean openAttachment ()
    {
        int index = cbContent.getSelectedIndex();
        byte[] data = m_attachment.getEntryData(index);
        if (data == null)
            return false;
        
        try 
        {
            String fileName = System.getProperty("java.io.tmpdir") +
					System.getProperty("file.separator") +
					m_attachment.getEntryName(index);
            File tempFile = new File(fileName);
            m_attachment.getEntryFile(index, tempFile);
        
            if (Env.isWindows())
            {
            //	Runtime.getRuntime().exec ("rundll32 url.dll,FileProtocolHandler " + url);
                Process p = Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL \"" + tempFile + "\"");
            //	p.waitFor();
                return true;
            }
            else if (Env.isMac())
            {
            	String [] cmdArray = new String [] {"open", tempFile.getAbsolutePath()};
            	Process p = Runtime.getRuntime ().exec (cmdArray);
            //	p.waitFor();
                return true;
            }
            else	//	other OS
            {
            }
        } 
        catch (Exception e) 
        {
        	log.error("", e);
        }
        return false;
    }    //    openFile

	
	/**************************************************************************
	 *  Graphic Image Panel
	 */
	class GImage extends JPanel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 4991225210651641722L;

		/**
		 *  Graphic Image
		 */
		public GImage()
		{
			super();
		}   //  GImage

		/** The Image           */
		private Image 			m_image = null;

		/**
		 *  Set Image
		 *  @param image image
		 */
		public void setImage (Image image)
		{
			m_image = image;
			if (m_image == null)
				return;

			MediaTracker mt = new MediaTracker(this);
			mt.addImage(m_image, 0);
			try {
				mt.waitForID(0);
			} catch (Exception e) {}
			Dimension dim = new Dimension(m_image.getWidth(this), m_image.getHeight(this));
			this.setPreferredSize(dim);
		}   //  setImage

		/**
		 *  Paint
		 *  @param g graphics
		 */
		@Override
		public void paint (Graphics g)
		{
			Insets in = getInsets();
			if (m_image != null)
				g.drawImage(m_image, in.left, in.top, this);
		}   //  paint

		/**
		 *  Update
		 *  @param g graphics
		 */
		@Override
		public void update (Graphics g)
		{
			paint(g);
		}   //  update
	}	//	GImage

	
	/**
	 * 	Test PDF Viewer
	 *	@param args ignored
	 */
	public static void main (String[] args)
	{
	}	//	main
	
}	//	Attachment

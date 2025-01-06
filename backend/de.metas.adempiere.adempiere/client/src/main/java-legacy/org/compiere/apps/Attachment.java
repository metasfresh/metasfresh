/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.apps;

import com.google.common.io.Files;
import de.metas.adempiere.Constants;
import de.metas.adempiere.form.IClientUI;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryId;
import de.metas.attachments.AttachmentEntryService;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.Msg;
import de.metas.logging.LogManager;
import de.metas.util.FileUtil;
import de.metas.util.Services;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.images.Images;
import org.adempiere.pdf.Document;
import org.adempiere.pdf.viewer.PDFViewerBean;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.swing.CButton;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CDialog;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextArea;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Attachment Viewer
 *
 * @author Jorg Janke
 * @version $Id: Attachment.java,v 1.4 2006/08/10 01:00:27 jjanke Exp $
 */
public final class Attachment extends CDialog implements ActionListener
{
	private static final long serialVersionUID = -5525529223484103376L;

	// services
	private static final Logger log = LogManager.getLogger(Attachment.class);

	private final int m_WindowNo;
	private final TableRecordReference _recordRef;

	//
	private CPanel mainPanel = new CPanel();
	private CButton bOpen = new CButton();
	private CButton bSave = new CButton();
	private CButton bLoad = new CButton();
	private ConfirmPanel confirmPanel = ConfirmPanel.newWithOKAndCancel();
	private CPanel toolBar = new CPanel(new FlowLayout(FlowLayout.LEADING, 5, 5));
	private CButton bDelete = new CButton();
	private CButton bDeleteAll = null;
	private CComboBox<AttachmentEntryItem> cbAttachmentEntries = new CComboBox<>();
	private JSplitPane centerPane = new JSplitPane();
	//
	private CPanel graphPanel = new CPanel(new BorderLayout());
	private GImage gifPanel = new GImage();
	private JScrollPane gifScroll = new JScrollPane(gifPanel);

	// metas: Document.getViewer() may burn a lot of time (Document's static
	// initializer), so only use it when we have to.
	private PDFViewerBean pdfViewer = null;	// Document.getViewer();
	private CTextArea info = new CTextArea();

	/**
	 * Constructor.
	 * loads Attachment, if ID <> 0
	 */
	public Attachment(final Frame frame, final int WindowNo, final int AD_Attachment_ID_NOTUSED, final int AD_Table_ID, final int Record_ID)
	{
		super(
				frame, // parent frame
				Services.get(IMsgBL.class).getMsg(Env.getCtx(), "Attachment"), // title
				true // needs to be modal otherwise APanel does not recognize change.
		);

		//
		m_WindowNo = WindowNo;
		_recordRef = TableRecordReference.of(AD_Table_ID, Record_ID);

		staticInit();
		loadAttachments();
	}

	private TableRecordReference getRecord()
	{
		return _recordRef;
	}

	/**
	 * Static setup.
	 *
	 * <pre>
	 * -northPanel
	 * 		- toolBar
	 * 		- title
	 * 		- centerPane[split]
	 * 		- graphPanel(left)
	 * 		- gifScroll - gifPanel
	 * 		- pdfViewer
	 * 		- text(right)
	 * 		- confirmPanel
	 * </pre>
	 */
	void staticInit()
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
		toolBar.add(cbAttachmentEntries);
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
		Dimension size = cbAttachmentEntries.getPreferredSize();
		size.width = 200;
		cbAttachmentEntries.setPreferredSize(size);
		// cbAttachmentEntries.setToolTipText(text);
		cbAttachmentEntries.addActionListener(this);
		cbAttachmentEntries.setLightWeightPopupEnabled(false);	// Acrobat Panel is heavy

		//
		mainPanel.add(confirmPanel, BorderLayout.SOUTH);
		confirmPanel.setActionListener(this);
		bDeleteAll = ConfirmPanel.createDeleteButton(true);
		if (!Services.get(ISysConfigBL.class).getBooleanValue(Constants.SYSCONFIG_Attachment_HideDeleteAll, false, Env.getAD_Client_ID(Env.getCtx())))
		{
			confirmPanel.addButton(bDeleteAll);
		}
		bDeleteAll.addActionListener(this);
		//
		info.setText("-");
		info.setReadWrite(false);
		graphPanel.add(info, BorderLayout.CENTER);
		//
		mainPanel.add(centerPane, BorderLayout.CENTER);
		centerPane.add(graphPanel, JSplitPane.LEFT);
		centerPane.setResizeWeight(.75);	// more to graph
	}	// jbInit

	/**
	 * Dispose
	 */
	@Override
	public void dispose()
	{
		pdfViewer = null;
		super.dispose();
	}	// dispose

	/**
	 * Load Attachments
	 */
	private void loadAttachments()
	{
		final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);

		// Set attachment entries combo
		final List<AttachmentEntry> attachmentEntries = attachmentEntryService.getByReferencedRecord(getRecord());
		for (AttachmentEntry entry : attachmentEntries)
		{
			cbAttachmentEntries.addItem(AttachmentEntryItem.of(entry));
		}
		if (!attachmentEntries.isEmpty())
		{
			cbAttachmentEntries.setSelectedIndex(0);
		}
		else
		{
			displayData(null);
		}
	}

	/**
	 * Display gif or jpg in gifPanel
	 *
	 * @param entryItem
	 */
	private void displayData(@Nullable final AttachmentEntryItem entryItem)
	{
		// Reset UI
		gifPanel.setImage(null);
		graphPanel.removeAll();
		//
		bDelete.setEnabled(false);
		bOpen.setEnabled(false);
		bSave.setEnabled(false);

		final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);

		final AttachmentEntry entry = entryItem != null ? attachmentEntryService.getById(entryItem.getAttachmentEntryId()) : null;

		// no attachment
		final byte[] data = entry == null ? null : attachmentEntryService.retrieveData(entry.getId());
		if (data == null || data.length == 0)
		{
			info.setText("-");
		}
		else
		{
			bOpen.setEnabled(true);
			bSave.setEnabled(true);
			bDelete.setEnabled(true);
			//
			info.setText(entry.toStringX());
			if (entry.isPDF())
			{

				// metas: get the viewer when we need it
				if (pdfViewer == null)
				{
					pdfViewer = Document.getViewer();
				}
				if (pdfViewer != null)
				{

					try
					{
						pdfViewer.loadPDF(data);
						pdfViewer.setScale(50);
						//
						graphPanel.add(pdfViewer, BorderLayout.CENTER);

						pdfViewer.invalidate();
						pdfViewer.revalidate();
						pdfViewer.repaint(50L);

					}
					catch (Exception e)
					{
						log.error("(pdf)", e);
					}
				}
			}
			else if (entry.isGraphic())
			{
				// Can we display it
				final Image image = Toolkit.getDefaultToolkit().createImage(data);
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
						// size.width += 40;
						// size.height += 40;
						gifPanel.invalidate();
						graphPanel.add(gifScroll, BorderLayout.CENTER);

						graphPanel.invalidate();
						graphPanel.revalidate();
						graphPanel.repaint(50L);
					}
				}
				else
				{
					log.error("Could not create image");
				}
			}
		}
		if (graphPanel.getComponentCount() == 0)
		{
			graphPanel.add(info, BorderLayout.CENTER);
		}

		graphPanel.repaint(50L); // timeout before update (millis)
	}   // displayData

	/**
	 * Action Listener
	 *
	 * @param event event
	 */
	@Override
	public void actionPerformed(final ActionEvent event)
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
		// close (attachment entries were already saved directly)
		if (event.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			dispose();
		}
		// Cancel
		else if (event.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			dispose();
		}
		// Delete Attachment
		else if (event.getSource() == bDeleteAll)
		{
			if (deleteAttachment())
			{
				dispose();
			}
		}
		// Delete individual entry and Return
		else if (event.getSource() == bDelete)
		{
			deleteAttachmentEntry();
		}
		else if (event.getSource() == cbAttachmentEntries)
		{
			displayData(cbAttachmentEntries.getSelectedItem());
		}
		else if (event.getSource() == bLoad)
		{
			loadFile();
		}
		else if (event.getSource() == bSave)
		{
			saveAttachmentToFile();
		}
		else if (event.getSource() == bOpen)
		{
			if (!openAttachment())
			{
				saveAttachmentToFile();
			}
		}
	}	// actionPerformed

	/**************************************************************************
	 * Load file for attachment
	 */
	private void loadFile()
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setDialogTitle(Msg.getMsg(Env.getCtx(), "AttachmentNew"));
		chooser.setMultiSelectionEnabled(true);
		int returnVal = chooser.showOpenDialog(this);
		if (returnVal != JFileChooser.APPROVE_OPTION)
		{
			return;
		}

		final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);
		//
		final File[] files = chooser.getSelectedFiles();
		for (final File file : files)
		{
			final AttachmentEntry existingEntry = attachmentEntryService.getByFilenameOrNull(getRecord(), file.getName());
			if (existingEntry != null)
			{
				final byte[] data = Util.readBytes(file);
				attachmentEntryService.updateData(existingEntry.getId(), data);
			}
			else
			{
				final AttachmentEntry newEntry = attachmentEntryService.createNewAttachment(getRecord(), file);
				// m_change = true; // not needed because it's already saved

				final AttachmentEntryItem newEntryItem = AttachmentEntryItem.of(newEntry);
				cbAttachmentEntries.addItem(newEntryItem);
				cbAttachmentEntries.setSelectedItem(newEntryItem);
			}
		}
	}	// getFileName

	/**
	 * Delete entire Attachment
	 *
	 * @return true if attachment was deleted (i.e. user confirmed)
	 */
	// metas: tsa: US1115: changed the return type from void to boolean; true if user confirmed the deletion
	private boolean deleteAttachment()
	{
		if (ADialog.ask(m_WindowNo, this, "AttachmentDelete?"))
		{
			final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);
			final List<AttachmentEntry> entries = attachmentEntryService.getByReferencedRecord(getRecord());
			for (final AttachmentEntry entry : entries)
			{
				attachmentEntryService.unattach(getRecord(), entry);
			}

			return true;
		}
		return false;
	}	// deleteAttachment

	/**
	 * Delete Attachment Entry
	 */
	private void deleteAttachmentEntry()
	{
		final AttachmentEntryItem entryItem = cbAttachmentEntries.getSelectedItem();
		if (entryItem == null)
		{
			return;
		}
		//
		if (ADialog.ask(m_WindowNo, this, "AttachmentDeleteEntry?", entryItem.getDisplayName()))
		{
			final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);
			final AttachmentEntry entry = attachmentEntryService.getById(entryItem.getAttachmentEntryId());
			attachmentEntryService.unattach(getRecord(), entry);

			cbAttachmentEntries.removeItem(entryItem);
		}
	}

	/**
	 * Save Attachment to File
	 */
	private void saveAttachmentToFile()
	{
		final AttachmentEntryItem entryItem = cbAttachmentEntries.getSelectedItem();
		if (entryItem == null)
		{
			return;
		}

		final JFileChooser chooser = new JFileChooser();
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setDialogTitle(Msg.getMsg(Env.getCtx(), "AttachmentSave"));
		final File file = new File(entryItem.getFilename());
		chooser.setSelectedFile(file);
		// Show dialog
		if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
		{
			return;
		}

		final File saveFile = chooser.getSelectedFile();
		if (saveFile == null)
		{
			return;
		}

		final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);
		final byte[] data = attachmentEntryService.retrieveData(entryItem.getAttachmentEntryId());
		try
		{
			Files.write(data, saveFile);
		}
		catch (IOException ex)
		{
			throw new AdempiereException("Failed saving file: " + saveFile, ex);
		}
	}	// saveAttachmentToFile

	/**
	 * Open the temporary file with the application associated with the extension in the file name
	 *
	 * @return true if file was opened with third party application
	 */
	private boolean openAttachment()
	{
		final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);

		final AttachmentEntryItem entryItem = cbAttachmentEntries.getSelectedItem();
		final byte[] data = attachmentEntryService.retrieveData(entryItem.getAttachmentEntryId());
		if (data == null)
		{
			return false;
		}

		try
		{
			final String fileName = FileUtil.getTempDir() +
					System.getProperty("file.separator") +
					entryItem.getFilename();
			final File tempFile = new File(fileName);

			Files.write(data, tempFile);

			if (Env.isWindows())
			{
				// Runtime.getRuntime().exec ("rundll32 url.dll,FileProtocolHandler " + url);
				Process p = Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL \"" + tempFile + "\"");
				// p.waitFor();
				return true;
			}
			else if (Env.isMac())
			{
				String[] cmdArray = new String[] { "open", tempFile.getAbsolutePath() };
				Process p = Runtime.getRuntime().exec(cmdArray);
				// p.waitFor();
				return true;
			}
			else	// other OS
			{
			}
		}
		catch (Exception e)
		{
			log.error("", e);
		}
		return false;
	}    // openFile

	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	@Getter
	private static final class AttachmentEntryItem
	{
		public static AttachmentEntryItem of(final AttachmentEntry entry)
		{
			return new AttachmentEntryItem(entry.getId(), entry.getName(), entry.getFilename());
		}

		private final AttachmentEntryId attachmentEntryId;
		private final String displayName;
		private final String filename;

		@Override
		public String toString()
		{
			return displayName;
		}

		@Override
		public int hashCode()
		{
			return attachmentEntryId.hashCode();
		}

		@Override
		public boolean equals(final Object obj)
		{
			if (this == obj)
			{
				return true;
			}
			if (obj instanceof AttachmentEntryItem)
			{
				final AttachmentEntryItem other = (AttachmentEntryItem)obj;
				return attachmentEntryId == other.attachmentEntryId;
			}
			else
			{
				return false;
			}
		}
	}

	/**************************************************************************
	 * Graphic Image Panel
	 */
	class GImage extends JPanel
	{
		/**
		 *
		 */
		private static final long serialVersionUID = 4991225210651641722L;

		/**
		 * Graphic Image
		 */
		public GImage()
		{
			super();
		}   // GImage

		/** The Image */
		private Image m_image = null;

		/**
		 * Set Image
		 *
		 * @param image image
		 */
		public void setImage(final Image image)
		{
			m_image = image;
			if (m_image == null)
			{
				return;
			}

			MediaTracker mt = new MediaTracker(this);
			mt.addImage(m_image, 0);
			try
			{
				mt.waitForID(0);
			}
			catch (Exception e)
			{
			}
			Dimension dim = new Dimension(m_image.getWidth(this), m_image.getHeight(this));
			this.setPreferredSize(dim);
		}   // setImage

		/**
		 * Paint
		 *
		 * @param g graphics
		 */
		@Override
		public void paint(final Graphics g)
		{
			Insets in = getInsets();
			if (m_image != null)
			{
				g.drawImage(m_image, in.left, in.top, this);
			}
		}   // paint

		/**
		 * Update
		 *
		 * @param g graphics
		 */
		@Override
		public void update(final Graphics g)
		{
			paint(g);
		}   // update
	}	// GImage
}	// Attachment

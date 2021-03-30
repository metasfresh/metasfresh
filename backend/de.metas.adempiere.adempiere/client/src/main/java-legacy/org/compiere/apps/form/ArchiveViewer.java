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
package org.compiere.apps.form;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.InputStream;
import java.sql.Timestamp;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JSplitPane;

import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.images.Images;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pdf.Document;
import org.adempiere.pdf.viewer.PDFViewerBean;
import org.compiere.apps.ConfirmPanel;
import org.compiere.grid.ed.VDate;
import org.compiere.grid.ed.VLookup;
import org.compiere.grid.ed.VString;
import org.compiere.grid.ed.VText;
import org.compiere.model.I_AD_Archive;
import org.compiere.swing.CButton;
import org.compiere.swing.CCheckBox;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTabbedPane;
import org.compiere.swing.CTextField;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Util;

import de.metas.i18n.Msg;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;

/**
 * Arvhive Viewer
 * <p>
 * Change log
 * <ul>
 * <li>2007-03-01 - teo_sarca - [ 1671899 ] Archive Viewer: table, process are not translated
 * <li>2007-03-01 - teo_sarca - [ 1671900 ] Archive Viewer: second tab has no split pane
 * </ul>
 *
 * @author Jorg Janke
 * @version $Id: ArchiveViewer.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class ArchiveViewer extends Archive
		implements FormPanel, ActionListener, VetoableChangeListener
{

	// /**
	// *
	// */
	// private static final long serialVersionUID = 876677286190292132L;

	private CTabbedPane panel = new CTabbedPane();

	/**
	 * Initialize Panel
	 * 
	 * @param WindowNo window
	 * @param frame parent frame
	 */
	@Override
	public void init(int WindowNo, FormFrame frame)
	{
		log.info("");
		m_WindowNo = WindowNo;
		m_frame = frame;
		try
		{
			dynInit();
			jbInit();
			
			frame.setMaximize(true);
			frame.getContentPane().add(panel, BorderLayout.CENTER);
			
			frame.getContentPane().add(confirmPanel, BorderLayout.SOUTH);
			//
			m_frame.setIconImage(Images.getImage2("Archive16"));
		}
		catch (Exception e)
		{
			log.error("init", e);
		}
	}	// init

	/** FormFrame */
	private FormFrame m_frame;

	private CPanel queryPanel = new CPanel(new GridBagLayout());
	private CCheckBox reportField = new CCheckBox(Msg.translate(Env.getCtx(), "IsReport"));
	private CLabel processLabel = new CLabel(Msg.translate(Env.getCtx(), "AD_Process_ID"));
	private CComboBox processField = null;
	private CLabel tableLabel = new CLabel(Msg.translate(Env.getCtx(), "AD_Table_ID"));
	private CComboBox tableField = null;
	private CLabel bPartnerLabel = new CLabel(Msg.translate(Env.getCtx(), "C_BPartner_ID"));
	private VLookup bPartnerField = null;
	private CLabel nameQLabel = new CLabel(Msg.translate(Env.getCtx(), "Name"));
	private CTextField nameQField = new CTextField(15);
	private CLabel descriptionQLabel = new CLabel(Msg.translate(Env.getCtx(), "Description"));
	private CTextField descriptionQField = new CTextField(15);
	private CLabel helpQLabel = new CLabel(Msg.translate(Env.getCtx(), "Help"));
	private CTextField helpQField = new CTextField(15);
	private CLabel createdByQLabel = new CLabel(Msg.translate(Env.getCtx(), "CreatedBy"));
	private CComboBox createdByQField = null;
	private CLabel createdQLabel = new CLabel(Msg.translate(Env.getCtx(), "Created"));
	private VDate createdQFrom = new VDate();
	private VDate createdQTo = new VDate();
	//
	private CPanel viewPanel = new CPanel(new BorderLayout(5, 5));
	private JSplitPane viewPanelSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	private PDFViewerBean pdfViewer = Document.getViewer();
	private JLabel imageViewer = new JLabel();
	private CPanel viewEnterPanel = new CPanel(new GridBagLayout());
	private CButton bBack = new CButton(Images.getImageIcon2("wfBack24"));
	private CButton bNext = new CButton(Images.getImageIcon2("wfNext24"));
	private CLabel positionInfo = new CLabel(".");
	private CLabel createdByLabel = new CLabel(Msg.translate(Env.getCtx(), "CreatedBy"));
	private CTextField createdByField = new CTextField(20);
	// private CLabel createdLabel = new CLabel(Msg.translate(Env.getCtx(), "Created"));
	private VDate createdField = new VDate();
	//
	private CLabel nameLabel = new CLabel(Msg.translate(Env.getCtx(), "Name"));
	private VString nameField = new VString("Name", true, false, true, 20, 60, null, null);
	private CLabel descriptionLabel = new CLabel(Msg.translate(Env.getCtx(), "Description"));
	private VText descriptionField = new VText("Description", false, false, true, 20, 255);
	private CLabel helpLabel = new CLabel(Msg.translate(Env.getCtx(), "Help"));
	private VText helpField = new VText("Help", false, false, true, 20, 2000);
	private CButton updateArchive = ConfirmPanel.createOKButton(Msg.getMsg(Env.getCtx(), "Update"));
	private ConfirmPanel confirmPanel = ConfirmPanel.newWithOKAndCancel();

	/**
	 * Dynamic Init
	 */
	private void dynInit()
	{
		processField = new CComboBox(getProcessData());
		tableField = new CComboBox(getTableData());
		createdByQField = new CComboBox(getUserData());
		//
		bPartnerField = VLookup.createBPartner(m_WindowNo);
	}	// dynInit

	/**
	 * Static Init
	 * 
	 * @throws Exception
	 */
	private void jbInit() throws Exception
	{
		int line = 0;
		queryPanel.add(reportField, new GridBagConstraints(0, line,
				3, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		reportField.addActionListener(this);
		//
		queryPanel.add(processLabel, new GridBagConstraints(0, ++line,
				1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
		queryPanel.add(processField, new GridBagConstraints(1, line,
				2, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0), 0, 0));
		queryPanel.add(bPartnerLabel, new GridBagConstraints(0, ++line,
				1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
		queryPanel.add(bPartnerField, new GridBagConstraints(1, line,
				2, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0), 0, 0));
		queryPanel.add(tableLabel, new GridBagConstraints(0, ++line,
				1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
		queryPanel.add(tableField, new GridBagConstraints(1, line,
				2, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0), 0, 0));
		//
		queryPanel.add(nameQLabel, new GridBagConstraints(0, ++line,
				1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 0, 0, 5), 0, 0));
		queryPanel.add(nameQField, new GridBagConstraints(1, line,
				2, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 0, 0), 0, 0));
		queryPanel.add(descriptionQLabel, new GridBagConstraints(0, ++line,
				1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
		queryPanel.add(descriptionQField, new GridBagConstraints(1, line,
				2, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0), 0, 0));
		queryPanel.add(helpQLabel, new GridBagConstraints(0, ++line,
				1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
		queryPanel.add(helpQField, new GridBagConstraints(1, line,
				2, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0), 0, 0));
		//
		queryPanel.add(createdByQLabel, new GridBagConstraints(0, ++line,
				1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 0, 0, 5), 0, 0));
		queryPanel.add(createdByQField, new GridBagConstraints(1, line,
				2, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(10, 0, 0, 0), 0, 0));
		queryPanel.add(createdQLabel, new GridBagConstraints(0, ++line,
				1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
		queryPanel.add(createdQFrom, new GridBagConstraints(1, line,
				1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
		queryPanel.add(createdQTo, new GridBagConstraints(2, line,
				1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));

		panel.add(queryPanel, "Query");
		//
		//
		line = 0;
		viewPanel.add(viewPanelSplit, BorderLayout.CENTER);
		
		pdfViewer.setMinimumSize(new Dimension(1350, 300));
		imageViewer.setMinimumSize(new Dimension(500, 300));
		viewPanelSplit.setLeftComponent(pdfViewer);
		//
		bBack.addActionListener(this);
		bNext.addActionListener(this);
		positionInfo.setFontBold(true);
		positionInfo.setHorizontalAlignment(CLabel.CENTER);
		viewEnterPanel.add(bBack, new GridBagConstraints(0, line,
				1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		viewEnterPanel.add(positionInfo, new GridBagConstraints(1, line,
				1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 0, 0));
		viewEnterPanel.add(bNext, new GridBagConstraints(2, line,
				1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
		//
		createdByField.setReadWrite(false);
		createdField.setReadWrite(false);
		nameField.addVetoableChangeListener(this);
		descriptionField.addVetoableChangeListener(this);
		helpField.addVetoableChangeListener(this);
		viewEnterPanel.add(createdByLabel, new GridBagConstraints(0, ++line,
				3, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
		viewEnterPanel.add(createdByField, new GridBagConstraints(0, ++line,
				3, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 0, 0, 5), 0, 0));
		// viewEnterPanel.add(createdLabel, new ALayoutConstraint(line++,0));
		viewEnterPanel.add(createdField, new GridBagConstraints(0, ++line,
				3, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(2, 0, 0, 5), 0, 0));
		//
		viewEnterPanel.add(nameLabel, new GridBagConstraints(0, ++line,
				3, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
		viewEnterPanel.add(nameField, new GridBagConstraints(0, ++line,
				3, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 0, 0, 5), 0, 0));
		//
		viewEnterPanel.add(descriptionLabel, new GridBagConstraints(0, ++line,
				3, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
		viewEnterPanel.add(descriptionField, new GridBagConstraints(0, ++line,
				3, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 0, 0, 5), 0, 0));
		//
		viewEnterPanel.add(helpLabel, new GridBagConstraints(0, ++line,
				3, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
		viewEnterPanel.add(helpField, new GridBagConstraints(0, ++line,
				3, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 0, 0, 5), 0, 0));
		//
		viewEnterPanel.add(updateArchive, new GridBagConstraints(0, ++line,
				3, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
		//
		viewEnterPanel.setPreferredSize(new Dimension(220, 500));
		viewEnterPanel.setMinimumSize(new Dimension(220, 500));
		updateArchive.addActionListener(this);
		viewPanelSplit.setRightComponent(viewEnterPanel);
		panel.add(viewPanel, "View");
		//
		confirmPanel.setActionListener(this);
		updateQDisplay();
		
		// open the panel directly maximized
		panel.setPreferredSize(new Dimension(0, 0));
	}	// jbInit

	/**
	 * Dispose
	 */
	@Override
	public void dispose()
	{
		if (m_frame != null)
			m_frame.dispose();
		m_frame = null;
	}	// dispose

	/**
	 * Action Listener
	 * 
	 * @param e event
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		log.info(e.getActionCommand());
		//
		if (e.getSource() == updateArchive)
			cmd_updateArchive();
		else if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
			dispose();
		else if (e.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			if (panel.getSelectedIndex() == 1)
				dispose();
			else
				cmd_query();
		}
		else if (e.getSource() == reportField)
			updateQDisplay();
		else if (e.getSource() == bBack)
			updateVDisplay(false);
		else if (e.getSource() == bNext)
			updateVDisplay(true);
	}	// actionPerformed

	/**
	 * Field Listener
	 *
	 * @param evt event
	 * @throws PropertyVetoException
	 */
	@Override
	public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException
	{
		if (m_archives.length > 0)
			updateArchive.setEnabled(true);
	}	// vetableChange

	/**
	 * Update Query Display
	 */
	private void updateQDisplay()
	{
		boolean reports = reportField.isSelected();
		log.info("Reports=" + reports);
		// Show
		processLabel.setVisible(reports);
		processField.setVisible(reports);
		// Hide
		bPartnerLabel.setVisible(!reports);
		bPartnerField.setVisible(!reports);
	}	// updateQDisplay

	/**
	 * Update View Display
	 * 
	 * @param next show next Archive
	 */
	private void updateVDisplay(boolean next)
	{
		if (m_archives == null)
			m_archives = new I_AD_Archive[0];
		if (next)
			m_index++;
		else
			m_index--;
		if (m_index >= m_archives.length - 1)
			m_index = m_archives.length - 1;
		if (m_index < 0)
			m_index = 0;
		bBack.setEnabled(m_index > 0);
		bNext.setEnabled(m_index < m_archives.length - 1);
		updateArchive.setEnabled(false);
		//
		log.info("Index=" + m_index + ", Length=" + m_archives.length);
		if (m_archives.length == 0)
		{
			positionInfo.setText("No Record Found");
			createdByField.setText("");
			createdField.setValue(null);
			nameField.setText("");
			descriptionField.setText("");
			helpField.setText("");
			pdfViewer.clearDocument();
			imageViewer.setIcon(null);
			return;
		}
		//
		positionInfo.setText(m_index + 1 + " of " + m_archives.length);
		I_AD_Archive ar = m_archives[m_index];
		createdByField.setText(Services.get(IUserDAO.class).retrieveUserFullName(ar.getCreatedBy()));
		createdField.setValue(ar.getCreated());
		nameField.setText(ar.getName());
		descriptionField.setText(ar.getDescription());
		helpField.setText(ar.getHelp());

		//
		// Load PDF
		try
		{
			viewPanelSplit.setLeftComponent(pdfViewer); // make sure is displayed

			// zoom directly to 150
			pdfViewer.setScale(150);

			boolean loaded = false;
			final InputStream dataStream = Services.get(IArchiveBL.class).getBinaryDataAsStream(ar);
			if (dataStream != null)
			{
				loaded = pdfViewer.loadPDF(dataStream);
			}
			
			if (!loaded)
			{
				pdfViewer.clearDocument();
			}
			else
			{
				return;
			}
		}
		catch (Exception e)
		{
			log.info("Failed loading PDF", e);
			clearViewers();
		}

		//
		// Load Image/PNG
		try
		{
			viewPanelSplit.setLeftComponent(imageViewer);

			final InputStream dataStream = Services.get(IArchiveBL.class).getBinaryDataAsStream(ar);
			final byte[] imageData = Util.readBytes(dataStream);
			final ImageIcon image = new ImageIcon(imageData);
			imageViewer.setIcon(image);
		}
		catch (Exception e)
		{
			log.info("Failed loading image", e);
			clearViewers();
		}
	}	// updateVDisplay

	private void clearViewers()
	{
		pdfViewer.clearDocument();
		imageViewer.setIcon((Icon)null);
	}

	/**
	 * Update Archive Info
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
		//
		m_index++;
		updateVDisplay(false);
	}	// cmd_updateArchive

	/**
	 * Query Directly
	 *
	 * @param isReport report
	 * @param AD_Table_ID table
	 * @param Record_ID tecord
	 */
	public void query(boolean isReport, int AD_Table_ID, int Record_ID)
	{
		log.info("Report=" + isReport + ", AD_Table_ID=" + AD_Table_ID + ",Record_ID=" + Record_ID);
		reportField.setSelected(isReport);
		m_AD_Table_ID = AD_Table_ID;
		m_Record_ID = Record_ID;
		cmd_query();
	}	// query

	/**************************************************************************
	 * Create Query
	 */
	private void cmd_query()
	{
		boolean reports = reportField.isSelected();
		KeyNamePair process = (KeyNamePair)processField.getSelectedItem();
		KeyNamePair table = (KeyNamePair)tableField.getSelectedItem();
		Integer C_BPartner_ID = (Integer)bPartnerField.getValue();
		String name = nameQField.getText();
		String description = descriptionQField.getText();
		String help = helpQField.getText();
		KeyNamePair createdBy = (KeyNamePair)createdByQField.getSelectedItem();
		Timestamp createdFrom = createdQFrom.getTimestamp();
		Timestamp createdTo = createdQTo.getTimestamp();

		cmd_query(reports, process, table, C_BPartner_ID, name, description, help,
				createdBy, createdFrom, createdTo);

		// Display
		panel.setSelectedIndex(1);
		m_index = 1;
		updateVDisplay(false);
	}	// cmd_query

}	// ArchiveViewer

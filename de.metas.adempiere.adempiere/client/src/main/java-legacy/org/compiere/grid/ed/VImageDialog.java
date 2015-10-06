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
package org.compiere.grid.ed;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;

import org.compiere.apps.AEnv;
import org.compiere.apps.ConfirmPanel;
import org.compiere.model.MImage;
import org.compiere.swing.CButton;
import org.compiere.swing.CDialog;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Msg;

/**
 *  Image Dialog
 *
 *  @author   Jorg Janke
 *  @version  $Id: VImageDialog.java,v 1.4 2006/07/30 00:51:28 jjanke Exp $
 */
public class VImageDialog extends CDialog
	implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2630060904081520523L;

	/**
	 *  Constructor
	 *  @param owner
	 *  @param mImage
	 */
	public VImageDialog (Frame owner, MImage mImage)
	{
		super (owner, Msg.translate(Env.getCtx(), "AD_Image_ID"), true);
		log.info("MImage=" + mImage);
		m_mImage = mImage;
		try
		{
			jbInit();
		}
		catch(Exception ex)
		{
			log.log(Level.SEVERE, "", ex);
		}
		//  load data
		if (m_mImage == null)
			m_mImage = MImage.get (Env.getCtx(), 0);
		fileButton.setText(m_mImage.getName());
		imageLabel.setIcon(m_mImage.getIcon());
		AEnv.positionCenterWindow(owner, this);
	}   //  VImageDialog

	/**  Image Model            */
	private MImage      m_mImage = null;
	/**	Logger					*/
	private static CLogger log = CLogger.getCLogger(VImageDialog.class);

	/** */
	private CPanel mainPanel = new CPanel();
	private BorderLayout mainLayout = new BorderLayout();
	private CPanel parameterPanel = new CPanel();
	private CLabel fileLabel = new CLabel();
	private CButton fileButton = new CButton();
	private CLabel imageLabel = new CLabel();
	private ConfirmPanel confirmPanel = new ConfirmPanel(true);

	/**
	 *  Static Init
	 *  @throws Exception
	 */
	void jbInit() throws Exception
	{
		mainPanel.setLayout(mainLayout);
		fileLabel.setText(Msg.getMsg(Env.getCtx(), "SelectFile"));
		fileButton.setText("-");
		imageLabel.setBackground(Color.white);
		imageLabel.setBorder(BorderFactory.createRaisedBevelBorder());
	//	imageLabel.setPreferredSize(new Dimension(50, 50));
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(mainPanel);
		mainPanel.add(parameterPanel, BorderLayout.NORTH);
		parameterPanel.add(fileLabel, null);
		parameterPanel.add(fileButton, null);
		mainPanel.add(imageLabel, BorderLayout.CENTER);
		mainPanel.add(confirmPanel, BorderLayout.SOUTH);
		//
		fileButton.addActionListener(this);
		confirmPanel.addActionListener(this);
	}   //  jbInit

	/**
	 *  ActionListener
	 *  @param e
	 */
	@Override
	public void actionPerformed (ActionEvent e)
	{
		if (e.getSource() == fileButton)
			cmd_file();

		else if (e.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			if (m_mImage.save())
				dispose();
			else
				setCursor(Cursor.getDefaultCursor());
		}

		else if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			m_mImage = null;	//	reset
			dispose();
		}
	}   //  actionPerformed

	/**
	 *  Load file & display
	 */
	private void cmd_file()
	{
		//  Show File Open Dialog
		JFileChooser jfc = new JFileChooser();
		jfc.setMultiSelectionEnabled(false);
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfc.showOpenDialog(this);

		//  Get File Name
		File imageFile = jfc.getSelectedFile();
		if (imageFile == null || imageFile.isDirectory() || !imageFile.exists())
			return;

		String fileName = imageFile.getAbsolutePath();
		byte[] data = null;
		
		//  See if we can load & display it
		try
		{
			FileInputStream fis = new FileInputStream(imageFile);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024*8];   //  8kB
			int length = -1;
			while ((length = fis.read(buffer)) != -1)
				os.write(buffer, 0, length);
			fis.close();
			data = os.toByteArray();
			os.close();
			//
			ImageIcon image = new ImageIcon (data, fileName);
			imageLabel.setIcon(image);
		}
		catch (Exception e)
		{
			log.log(Level.WARNING, "load image", e);
			return;
		}

		//  OK
		fileButton.setText(imageFile.getAbsolutePath());
		pack();

		//  Save info
		m_mImage.setName(fileName);
		m_mImage.setImageURL(fileName);
		m_mImage.setBinaryData(data);
	}   //  cmd_file

	/**
	 * 	Get Image ID
	 *	@return ID or 0
	 */
	public int getAD_Image_ID()
	{
		if (m_mImage != null)
			return m_mImage.getAD_Image_ID();
		return 0;
	}	//	getAD_Image_ID
	
}   //  VImageDialog

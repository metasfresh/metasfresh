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
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.apps.AEnv;
import org.compiere.apps.ConfirmPanel;
import org.compiere.model.MImage;
import org.compiere.swing.CButton;
import org.compiere.swing.CDialog;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.util.Env;
import org.compiere.util.Util;

import de.metas.adempiere.form.IClientUI;
import de.metas.i18n.IMsgBL;

/**
 * Image Dialog
 *
 * @author Jorg Janke
 * @version $Id: VImageDialog.java,v 1.4 2006/07/30 00:51:28 jjanke Exp $
 */
public class VImageDialog extends CDialog
		implements ActionListener
{
	private static final long serialVersionUID = -2630060904081520523L;

	// services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient IClientUI clientUI = Services.get(IClientUI.class);

	private final int windowNo;
	/** Image Model */
	private MImage m_mImage = null;
	private boolean canceled = true;

	//
	// UI
	private final CButton fileButton = new CButton();
	private final CLabel imagePreviewLabel = new CLabel();
	private final ConfirmPanel confirmPanel = ConfirmPanel.builder()
			.withResetButton(true)
			.build();

	/**
	 * Constructor
	 *
	 * @param owner
	 * @param mImage
	 */
	public VImageDialog(final Frame owner, final int windowNo, final MImage mImage)
	{
		super(owner, "", true);

		this.windowNo = windowNo;

		jbInit();

		//
		// Load data
		m_mImage = new MImage(Env.getCtx(), 0, ITrx.TRXNAME_None);
		if (mImage != null)
		{
			// Copy the image in a new object because AD_Image objects shall be handled as value objects
			InterfaceWrapperHelper.copyValues(mImage, m_mImage);
			m_mImage = new MImage(Env.getCtx(), 0, ITrx.TRXNAME_None);
		}

		fileButton.setText(m_mImage.getName());
		imagePreviewLabel.setIcon(m_mImage.getIcon());

		//
		// Show window
		AEnv.positionCenterWindow(owner, this);
	}   // VImageDialog

	/**
	 * Static Init
	 */
	void jbInit()
	{
		setTitle(msgBL.translate(Env.getCtx(), "AD_Image_ID"));

		final CPanel mainPanel = new CPanel();
		mainPanel.setLayout(new BorderLayout());
		setContentPane(mainPanel);

		//
		// Top: File label & button
		{
			final CLabel fileLabel = new CLabel();
			fileLabel.setText(msgBL.getMsg(Env.getCtx(), "SelectFile"));
			fileButton.setText("-");
			fileButton.addActionListener(this);

			imagePreviewLabel.setBackground(Color.white);
			imagePreviewLabel.setBorder(BorderFactory.createRaisedBevelBorder());
			imagePreviewLabel.setHorizontalAlignment(SwingConstants.CENTER);

			final CPanel parameterPanel = new CPanel();
			parameterPanel.add(fileLabel, null);
			parameterPanel.add(fileButton, null);
			mainPanel.add(parameterPanel, BorderLayout.NORTH);
		}

		//
		// Center: image preview
		{
			mainPanel.add(imagePreviewLabel, BorderLayout.CENTER);
		}

		// Bottom
		{
			confirmPanel.getOKButton().setEnabled(false);
			mainPanel.add(confirmPanel, BorderLayout.SOUTH);
			confirmPanel.setActionListener(this);
		}
	}   // jbInit

	private int getWindowNo()
	{
		return windowNo;
	}

	/**
	 * ActionListener
	 *
	 * @param e
	 */
	@Override
	public void actionPerformed(final ActionEvent e)
	{
		if (e.getSource() == fileButton)
		{
			cmd_file();
		}
		//
		// OK: caller shall take the new image
		else if (ConfirmPanel.A_OK.equals(e.getActionCommand()))
		{
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			try
			{
				InterfaceWrapperHelper.save(m_mImage);
				canceled = false;
				dispose();
			}
			catch (final Exception ex)
			{
				clientUI.error(getWindowNo(), ex);
			}
			finally
			{
				setCursor(Cursor.getDefaultCursor());
			}
		}
		//
		// Reset: caller shall reset the field value
		else if (ConfirmPanel.A_RESET.equals(e.getActionCommand()))
		{
			m_mImage = null;	// reset
			canceled = false;
			dispose();
		}
	}   // actionPerformed

	/**
	 * Load file & display
	 */
	private void cmd_file()
	{
		// Show File Open Dialog
		final JFileChooser jfc = new JFileChooser();
		jfc.setMultiSelectionEnabled(false);
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfc.showOpenDialog(this);

		// Get File Name
		final File imageFile = jfc.getSelectedFile();
		if (imageFile == null || imageFile.isDirectory() || !imageFile.exists())
		{
			return;
		}
		final String fileName = imageFile.getAbsolutePath();

		//
		// See if we can load & display it
		final byte[] data;
		final ImageIcon image;
		try
		{
			data = Util.readBytes(imageFile);
			image = new ImageIcon(data, fileName);
		}
		catch (final Exception e)
		{
			clientUI.error(getWindowNo(), e);
			return;
		}

		//
		// Update UI
		fileButton.setText(imageFile.getAbsolutePath());
		imagePreviewLabel.setIcon(image);
		confirmPanel.getOKButton().setEnabled(true);
		pack();

		// Save info
		m_mImage.setName(fileName);
		m_mImage.setImageURL(fileName);
		m_mImage.setBinaryData(data);
	}   // cmd_file

	/**
	 * Get Image ID
	 *
	 * @return ID or -1
	 */
	public int getAD_Image_ID()
	{
		if (m_mImage != null && m_mImage.getAD_Image_ID() > 0)
		{
			return m_mImage.getAD_Image_ID();
		}
		return -1;
	}

	/**
	 * @return true if the window was canceled and the caller shall ignore the result
	 */
	public boolean isCanceled()
	{
		return canceled;
	}

}   // VImageDialog

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
package org.compiere.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.plaf.AdempierePLAF;
import org.compiere.Adempiere;
import org.compiere.swing.CDialog;
import org.compiere.swing.CFrame;
import org.compiere.swing.CPanel;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.util.Check;

/**
 * Init Dialog
 *
 * @author Jorg Janke
 * @version $Id: IniDialog.java,v 1.3 2006/10/12 00:58:32 jjanke Exp $
 */
public final class IniDialog extends CDialog implements ActionListener
{
	private static final long serialVersionUID = -2629728001114463544L;

	static
	{
		// NOTE: we need to set the L&F here because if the ini file is missing, this is the first window which is opened,
		// even before the PLAF is set.
		try
		{
			AdempierePLAF.setPLAF();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * Display License and exit if rejected.
	 * 
	 * @return true if accepted
	 */
	public static final boolean accept()
	{
		IniDialog id = new IniDialog();
		
		if (id.isAccepted())
		{
			log.info("License Accepted");
			return true;
		}
		System.exit(10);
		return false;       // never executed.
	}
	
	/**
	 * Constructor
	 * @param parenta 
	 */
	private IniDialog()
	{
		super(createDummyParentFrame());
		try
		{
			jbInit();
			final URL url = getLicenseURL();

			//
			licensePane.setPage(url);
			AdempierePLAF.showCenterScreen(this);
		}
		catch (Exception ex)
		{
			log.error("init", ex);
			cmd_reject();
		}
	}   // IniDialog
	
	/**
	 * Creates a dummy parent for this dialog. We need it because we want to have an icon in the task bar (FRESH-320).
	 * 
	 * @see http://stackoverflow.com/questions/8006502/show-jdialog-on-taskbar
	 */
	private static final CFrame createDummyParentFrame()
	{
		final CFrame dummyFrame = new CFrame();
		dummyFrame.setUndecorated(true);
		dummyFrame.setVisible(true);
		dummyFrame.setLocationRelativeTo(null);
		dummyFrame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		return dummyFrame;
	}

	private static final URL getLicenseURL()
	{
		final ClassLoader classLoader = IniDialog.class.getClassLoader();

		//
		// Try loading the license from local resource (if any)
		final String licenseResourceName = Adempiere.getProductLicenseResourceName();
		if (!Check.isEmpty(licenseResourceName, true))
		{
			final URL url = classLoader.getResource(licenseResourceName);
			if (url != null)
			{
				return url;
			}
		}

		//
		// Try using the online license URL (if any)
		final String licenseOnlineURL = Adempiere.getProductLicenseURL();
		if (!Check.isEmpty(licenseOnlineURL, true))
		{
			log.warn("No license in resource. Using online license.");
			try
			{
				return new URL(licenseOnlineURL);
			}
			catch (MalformedURLException e)
			{
				log.warn("Invalid online license URL: " + licenseOnlineURL + ". Ignored.", e);
			}
		}

		// Fail if there is no license URL
		throw new AdempiereException("No license URL found");
	}

	/** Translation */
	static ResourceBundle s_res = ResourceBundle.getBundle("org.compiere.util.IniRes");
	private boolean m_accept = false;
	/** Logger */
	private static final Logger log = LogManager.getLogger(IniDialog.class.getName());

	private CPanel mainPanel = new CPanel();
	private BorderLayout mainLayout = new BorderLayout();
	private JScrollPane scrollPane = new JScrollPane();
	private CPanel southPanel = new CPanel();
	private JButton bReject = AdempierePLAF.getCancelButton();
	private JButton bAccept = AdempierePLAF.getOKButton();
	private FlowLayout southLayout = new FlowLayout();
	private JLabel southLabel = new JLabel();
	private JEditorPane licensePane = new JEditorPane();

	/**
	 * Static Layout
	 * 
	 * @throws Exception
	 */
	private void jbInit() throws Exception
	{
		final String title = Adempiere.getName() + " - " + s_res.getString("Adempiere_License");

		final JFrame dummyParentFrame = getOwner();
		dummyParentFrame.setTitle(title);
		dummyParentFrame.setIconImages(getIconImages());
		
		setTitle(title);
		southLabel.setText(s_res.getString("Do_you_accept"));
		bReject.setText(s_res.getString("No"));
		bAccept.setText(s_res.getString("Yes_I_Understand"));
		//
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		//
		mainPanel.setLayout(mainLayout);
		bReject.setForeground(Color.red);
		bReject.addActionListener(this);
		bAccept.addActionListener(this);
		southPanel.setLayout(southLayout);
		southLayout.setAlignment(FlowLayout.RIGHT);
		licensePane.setEditable(false);
		licensePane.setContentType("text/html");
		scrollPane.setPreferredSize(new Dimension(700, 400));
		southPanel.add(southLabel, null);
		getContentPane().add(mainPanel);
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		scrollPane.getViewport().add(licensePane, null);
		mainPanel.add(southPanel, BorderLayout.SOUTH);
		southPanel.add(bReject, null);
		southPanel.add(bAccept, null);
	}   // jbInit

	/**
	 * ActionListener
	 * 
	 * @param e event
	 */
	@Override
	public final void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == bAccept)
			m_accept = true;
		dispose();
	}   // actionPerformed

	/**
	 * Dispose
	 */
	@Override
	public final void dispose()
	{
		if(_disposed)
		{
			return;
		}
		_disposed = true;
		
		super.dispose();
		
		if (!m_accept)
		{
			cmd_reject();
		}
		
		final JFrame dummyParentFrame = getOwner();
		dummyParentFrame.dispose();
	}
	
	private boolean _disposed = false;

	/**
	 * @return our dummy parent frame
	 * @see #createDummyParentFrame()
	 */
	@Override
	public JFrame getOwner()
	{
		return (JFrame) super.getOwner();
	}

	/**
	 * Is Accepted
	 * 
	 * @return true if accepted
	 */
	public final boolean isAccepted()
	{
		return m_accept;
	}   // isAccepted

	/**
	 * Reject License
	 */
	public final void cmd_reject()
	{
		String info = "License rejected or expired";
		try
		{
			info = s_res.getString("License_rejected");
		}
		catch (Exception e)
		{
		}
		log.error(info);
		System.exit(10);
	}   // cmd_reject
}   // IniDialog

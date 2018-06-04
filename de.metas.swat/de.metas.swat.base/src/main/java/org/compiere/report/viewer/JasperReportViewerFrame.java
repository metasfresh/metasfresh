package org.compiere.report.viewer;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import org.compiere.apps.AEnv;

import de.metas.process.ProcessInfo;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

/**
 * Jasper Viewer Frame
 */
public class JasperReportViewerFrame extends javax.swing.JFrame
{

	private static final long serialVersionUID = 1192807883081180999L;

	private final ProcessInfo processInfo;
	private final String m_title;

	private javax.swing.JPanel pnlMain;

	/** Creates new form JasperViewer */
	/**
	 * @param jasperPrint
	 *            report to display
	 * @param frameTitle
	 *            Title to be displayed
	 * @param pi
	 *            ProcessInfo that contains additional info. Used to find
	 *            original document in order to find the business partner's mail
	 *            address.
	 * @throws JRException
	 */
	public JasperReportViewerFrame(final JasperPrint jasperPrint, final String frameTitle, final ProcessInfo pi) throws JRException
	{
		this.m_title = frameTitle;
		this.processInfo = pi;

		initComponents();
		//JasperReportViewerPanel viewer = new JasperReportViewerPanel(this, jasperPrint);
		final JRViewer viewer = new JRViewer(jasperPrint);
		this.pnlMain.add(viewer, BorderLayout.CENTER);

		// Add this frame to metasfresh window manager.
		// In this way we also make sure the frame is closed when user logs out.
		AEnv.addToWindowManager(this);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	private void initComponents()
	{
		pnlMain = new javax.swing.JPanel();

		setTitle(m_title);
		addWindowListener(new java.awt.event.WindowAdapter()
		{
			@Override
			public void windowClosing(java.awt.event.WindowEvent evt)
			{
				dispose();
			}
		});
		addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent event)
			{
				if (event.getKeyCode() == KeyEvent.VK_ESCAPE)
				{
					dispose();
				}
			}
		});

		pnlMain.setLayout(new java.awt.BorderLayout());

		getContentPane().add(pnlMain, java.awt.BorderLayout.CENTER);

		pack();
		final java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setSize(new java.awt.Dimension(750, 550));
		setLocation((screenSize.width - 750) / 2, (screenSize.height - 550) / 2);
	}

	@Override
	public void dispose()
	{
		setVisible(false);
		super.dispose();
	}

	public final ProcessInfo getProcessInfo()
	{
		return processInfo;
	}
}

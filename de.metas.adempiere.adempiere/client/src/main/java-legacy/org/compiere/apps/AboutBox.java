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
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.adempiere.plaf.MetasFreshTheme;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.swing.CDialog;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextArea;
import org.compiere.util.Env;
import org.compiere.util.SupportInfo;

import de.metas.adempiere.form.IClientUI;
import de.metas.i18n.IMsgBL;

/**
 *	About Dialog
 *
 *  @author 	Jorg Janke
 *  @version 	$Id: AboutBox.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public final class AboutBox extends CDialog implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5126987147443121045L;

	/**
	 *	Constructor for modal about dialog
	 *  @param parent parent
	 */
	public AboutBox(JFrame parent)
	{
		super (parent, true);
		try
		{
			jbInit();
		}
		catch(Exception e)
		{
			// shall not happen
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		//
		labelVersion.setText(Adempiere.getVersion());
		labelCopyright.setText(Adempiere.getCopyright());
		infoArea.setText(SupportInfo.getInfo());
		//  create 5 pt border
		Dimension d = imageControl.getPreferredSize();
		imageControl.setPreferredSize(new Dimension(d.width+10, d.height+10));
		//
		setMinimumSize(new Dimension(800, 500)); // make sure width is big enough
		AEnv.positionCenterWindow(parent, this);
	}	//	AWindow_AboutBox

	private CPanel panel = new CPanel();
	private CPanel mainPanel = new CPanel();
	private JLabel imageControl = new JLabel();
	private JLabel labelHeading = new JLabel();
	private JLabel labelVersion = new JLabel();
	private JLabel labelCopyright = new JLabel();
	private BorderLayout panelLayout = new BorderLayout();
	private BorderLayout mainLayout = new BorderLayout();
	private CPanel northPanel = new CPanel();
	private CPanel headerPanel = new CPanel();
	private GridLayout headerLayout = new GridLayout();
	private CTextArea infoArea = new CTextArea();
	private BorderLayout northLayout = new BorderLayout();
	private ConfirmPanel confirmPanel = ConfirmPanel.builder()
			.withCancelButton(false)
			.build();

	/**
	 *	Init
	 *  @throws Exception
	 */
	private void jbInit() throws Exception
	{
		this.setTitle(Services.get(IMsgBL.class).translate(Env.getCtx(), "About"));
		//
		setResizable(false);
		labelHeading.setFont(new java.awt.Font("Dialog", 1, 14));
		labelHeading.setHorizontalAlignment(SwingConstants.CENTER);
		labelHeading.setHorizontalTextPosition(SwingConstants.CENTER);
		labelHeading.setText(Adempiere.getSubtitle());
		labelVersion.setHorizontalAlignment(SwingConstants.CENTER);
		labelVersion.setHorizontalTextPosition(SwingConstants.CENTER);
		labelVersion.setText(".");
		labelCopyright.setHorizontalAlignment(SwingConstants.CENTER);
		labelCopyright.setHorizontalTextPosition(SwingConstants.CENTER);
		labelCopyright.setText(".");
		final JLabel labelURL = createURLLabel(Adempiere.getURL());
		//
		imageControl.setFont(UIManager.getFont(MetasFreshTheme.KEY_Logo_TextFontSmall));
		imageControl.setForeground(UIManager.getColor(MetasFreshTheme.KEY_Logo_TextColor));
		imageControl.setAlignmentX((float) 0.5);
		imageControl.setHorizontalAlignment(SwingConstants.CENTER);
		imageControl.setHorizontalTextPosition(SwingConstants.CENTER);
		final Image productLogo = Adempiere.getProductLogoLarge();
		if (productLogo != null)
		{
			imageControl.setIcon(new ImageIcon(productLogo));
		}
		imageControl.setText(Adempiere.getSubtitle());
		imageControl.setVerticalTextPosition(SwingConstants.BOTTOM);
		//
		mainPanel.setLayout(mainLayout);
		mainLayout.setHgap(10);
		mainLayout.setVgap(10);
		northPanel.setLayout(northLayout);
		northLayout.setHgap(10);
		northLayout.setVgap(10);
		panel.setLayout(panelLayout);
		panelLayout.setHgap(10);
		panelLayout.setVgap(10);
		headerPanel.setLayout(headerLayout);
		headerLayout.setColumns(1);
		headerLayout.setRows(4);
		
		//
		// Configure info text area:
		infoArea.setReadWrite(false); // readonly
		infoArea.setLineWrap(false); // don't wrap lines, we will use horizontal scroll bars
		infoArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		infoArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		this.getContentPane().add(panel, null);
		panel.add(northPanel, BorderLayout.NORTH);
		northPanel.add(imageControl, BorderLayout.WEST);
		northPanel.add(headerPanel, BorderLayout.CENTER);
		headerPanel.add(labelHeading, null);
		headerPanel.add(labelCopyright, null);
		headerPanel.add(labelVersion, null);
		headerPanel.add(labelURL, null);
		panel.add(mainPanel, BorderLayout.CENTER);
		mainPanel.add(infoArea, BorderLayout.CENTER);
		mainPanel.add(confirmPanel, BorderLayout.SOUTH);
		confirmPanel.setActionListener(this);
	}   //  jbInit
	
	private static final JLabel createURLLabel(final String urlStr)
	{
		final JLabel labelURL = new JLabel();
		labelURL.setForeground(Color.blue);
		labelURL.setHorizontalAlignment(SwingConstants.CENTER);
		labelURL.setHorizontalTextPosition(SwingConstants.CENTER);
		labelURL.setCursor(new Cursor(Cursor.HAND_CURSOR));
		labelURL.setText(urlStr);
		labelURL.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(final MouseEvent e)
			{
				Services.get(IClientUI.class).showURL(urlStr);
			}
		});
		
		return labelURL;
	}

	
	/**
	 *	ActionListener
	 *  @param e event
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals(ConfirmPanel.A_OK))
			dispose();
	}   //  actionPerformed
}	//	AboutBox

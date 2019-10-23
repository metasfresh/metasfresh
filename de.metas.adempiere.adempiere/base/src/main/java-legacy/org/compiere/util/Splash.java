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
import java.awt.Frame;
import java.awt.Image;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.adempiere.plaf.MetasFreshTheme;
import org.compiere.Adempiere;

/**
 * Splash Screen.
 * - don't use environment as not set up yet - <code>
 * 		Splash splash = new Splash("Processing");
 * 		.. do something here
 * 		splash.dispose();
 * 		splash = null;
 *  </code>
 *
 * @author Jorg Janke
 * @version $Id: Splash.java,v 1.3 2006/07/30 00:54:36 jjanke Exp $
 */
public class Splash extends Frame
{
	/**
	 *
	 */
	private static final long serialVersionUID = 7671371032359729541L;

	/**
	 * Get Splash Screen and display it.
	 *
	 * @return Splash Screen
	 */
	public static Splash getSplash()
	{
		String msg;
		if (Locale.getDefault().getLanguage().equals("es"))
		{
			msg = new String("Cargando...");
		}
		else
		{
			// Default english
			msg = new String("Loading...");
		}
		return getSplash(msg);
	}   // getSplash

	/**
	 * Get Splash Screen
	 *
	 * @param text splash text
	 * @return Splash Screen
	 */
	public static Splash getSplash(final String text)
	{
		if (s_splash == null)
		{
			s_splash = new Splash(text);
		}
		else
		{
			s_splash.setText(text);
		}
		return s_splash;
	}   // getSplash

	private static Splash s_splash = null;

	/**************************************************************************
	 * Standard constructor
	 *
	 * @param text clear text
	 */
	public Splash(final String text)
	{
		super(Adempiere.getName());
		message.setText(text);
		try
		{
			jbInit();
		}
		catch (final Exception e)
		{
			System.out.println("Splash");
			e.printStackTrace();
		}
		display();
	}	// Splash

	private final Label message = new Label();

	/**
	 * Static Init
	 *
	 * @throws Exception
	 */
	private void jbInit() throws Exception
	{
		final Color background = UIManager.getColor(MetasFreshTheme.KEY_Logo_BackgroundColor);

		setBackground(background);
		setName("splash");
		setUndecorated(true);

		final JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		contentPanel.setBackground(background);
		// NOTE: set some border to not have the logo near the margin
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.add(contentPanel);

		//
		message.setFont(UIManager.getFont(MetasFreshTheme.KEY_Logo_TextFont));
		message.setForeground(UIManager.getColor(MetasFreshTheme.KEY_Logo_TextColor));
		message.setAlignment(Label.CENTER);

		//
		// North: Product logo (if any)
		final JLabel productLogo = new JLabel();
		final Image productLogoImage = Adempiere.getProductLogoLarge();
		if (productLogoImage != null)
		{
			productLogo.setIcon(new ImageIcon(productLogoImage));
			contentPanel.add(productLogo, BorderLayout.NORTH);
		}

		//
		// South: message
		contentPanel.add(message, BorderLayout.SOUTH);
	}	// jbInit

	/**
	 * Set Text (20 pt) and display the splash screen.
	 *
	 * @param text translated text to display
	 */
	public void setText(final String text)
	{
		message.setText(text);
		display();
	}	// setText

	/**
	 * Show Window
	 *
	 * @param visible true if visible
	 */
	@Override
	public void setVisible(final boolean visible)
	{
		super.setVisible(visible);
		if (visible)
		{
			toFront();
		}
	}   // setVisible

	/**
	 * Calculate size and display
	 */
	private void display()
	{
		pack();
		final Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
		final Rectangle bounds = getBounds();
		setBounds((ss.width - bounds.width) / 2, (ss.height - bounds.height) / 2,
				bounds.width, bounds.height);
		setVisible(true);
	}   // display

	/**
	 * Dispose Splash
	 */
	@Override
	public void dispose()
	{
		super.dispose();
		s_splash = null;
	}   // dispose
}	// Splash

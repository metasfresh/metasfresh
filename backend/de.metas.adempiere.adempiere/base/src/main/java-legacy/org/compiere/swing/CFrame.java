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
package org.compiere.swing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.annotation.Nullable;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.adempiere.ad.element.api.AdWindowId;

/**
 * 	Adempiere Frame
 *	
 *  @author Jorg Janke
 *  @version $Id: CFrame.java,v 1.2 2006/07/30 00:52:24 jjanke Exp $
 */
public class CFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6282268921005682543L;

	/**
	 * 	CFrame
	 *	@throws HeadlessException
	 */
	public CFrame () throws HeadlessException
	{
		super ();
	}	//	CFrame

	/**
	 * 	CFrame
	 *	@param gc
	 */
	public CFrame (GraphicsConfiguration gc)
	{
		super (gc);
	}	//	CFrame

	/**
	 * 	CFrame
	 *	@param title
	 *	@throws HeadlessException
	 */
	public CFrame (String title) throws HeadlessException
	{
		super (cleanup(title));
	}	//	CFrame

	/**
	 * 	CFrame
	 *	@param title
	 *	@param gc
	 */
	public CFrame (String title, GraphicsConfiguration gc)
	{
		super (cleanup(title), gc);
	}	//	CFrame

	/** Window ID			*/
	private AdWindowId adWindowId;
	
	/**
	 * 	Frame Init.
	 * 	Install ALT-Pause
	 */
	@Override
	protected void frameInit ()
	{
		super.frameInit ();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//
		Container c = getContentPane();
		if (c instanceof JPanel)
		{
			JPanel panel = (JPanel)c;
			panel.getActionMap().put(CDialog.ACTION_DISPOSE, CDialog.s_dialogAction);
			panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(CDialog.s_disposeKeyStroke, CDialog.ACTION_DISPOSE);
		}
		
		//
		// Window Header Notice
		// see http://dewiki908/mediawiki/index.php/05730_Use_different_Theme_colour_on_UAT_system
		final WindowHeaderNotice windowHeaderNotice = new WindowHeaderNotice();
		c.add(windowHeaderNotice, BorderLayout.NORTH);
		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowOpened(WindowEvent e)
			{
				// NOTE: we need to do this for AMenu which is created before login
				windowHeaderNotice.load();
			}
		});
	}	//	frameInit
	
	/**
	 * 	Cleanedup Title
	 *	@param title title
	 *	@return title w/o mn
	 */
	private static String cleanup (String title)
	{
		if (title != null)
		{
			int pos = title.indexOf('&');
			if (pos != -1 && title.length() > pos)	//	We have a mnemonic
			{
				int mnemonic = title.toUpperCase().charAt(pos+1);
				if (mnemonic != ' ')
				{
					title = title.substring(0, pos) + title.substring(pos+1);
				}
			}
		}
		return title;
	}	//	getTitle

	/**
	 * 	Set Title
	 *	@param title title
	 */
	@Override
	public void setTitle(String title)
	{
		super.setTitle(cleanup(title));
	}	//	setTitle

	public AdWindowId getAdWindowId()
	{
		return adWindowId;
	}	//	getAD_Window_ID

	public void setAdWindowId(@Nullable final AdWindowId adWindowId)
	{
		this.adWindowId = adWindowId;
	}
}	//	CFrame

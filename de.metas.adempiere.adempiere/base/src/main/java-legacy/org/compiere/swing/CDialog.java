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

import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import org.adempiere.plaf.AdempierePLAF;
import org.compiere.Adempiere;

/**
 * Conveniance Dialog Class.
 * Adempiere Background + Dispose on Close
 * Implementing empty Action and Mouse Listener
 *
 * @author Jorg Janke
 * @version $Id: CDialog.java,v 1.3 2006/07/30 00:52:24 jjanke Exp $
 */
public class CDialog extends JDialog
		implements ActionListener, MouseListener
{
	/**
	 *
	 */
	private static final long serialVersionUID = -1698027199524570661L;

	/**
	 * CDialog
	 *
	 * @throws HeadlessException
	 */
	public CDialog() throws HeadlessException
	{
		this((Frame)null, false);
	}

	/**
	 * CDialog
	 *
	 * @param owner
	 * @throws HeadlessException
	 */
	public CDialog(final Frame owner) throws HeadlessException
	{
		this(owner, false);
	}

	/**
	 * CDialog
	 *
	 * @param owner
	 * @param modal
	 * @throws HeadlessException
	 */
	public CDialog(final Frame owner, final boolean modal) throws HeadlessException
	{
		this(owner, null, modal);
	}

	/**
	 * CDialog
	 *
	 * @param owner
	 * @param title
	 * @throws HeadlessException
	 */
	public CDialog(final Frame owner, final String title) throws HeadlessException
	{
		this(owner, title, false);
	}

	/**
	 * CDialog
	 *
	 * @param owner
	 * @param title
	 * @param modal
	 * @throws HeadlessException
	 */
	public CDialog(final Frame owner, final String title, final boolean modal) throws HeadlessException
	{
		super(owner, title, modal);
	}

	/**
	 * CDialog
	 *
	 * @param owner
	 * @param title
	 * @param modal
	 * @param gc
	 */
	public CDialog(final Frame owner, final String title, final boolean modal, final GraphicsConfiguration gc)
	{
		super(owner, title, modal, gc);
	}

	/**
	 * CDialog
	 *
	 * @param owner
	 * @throws HeadlessException
	 */
	public CDialog(final Dialog owner) throws HeadlessException
	{
		this(owner, false);
	}

	/**
	 * CDialog
	 *
	 * @param owner
	 * @param modal
	 * @throws HeadlessException
	 */
	public CDialog(final Dialog owner, final boolean modal) throws HeadlessException
	{
		this(owner, null, modal);
	}

	/**
	 * CDialog
	 *
	 * @param owner
	 * @param title
	 * @throws HeadlessException
	 */
	public CDialog(final Dialog owner, final String title) throws HeadlessException
	{
		this(owner, title, false);
	}

	/**
	 * CDialog
	 *
	 * @param owner
	 * @param title
	 * @param modal
	 * @throws HeadlessException
	 */
	public CDialog(final Dialog owner, final String title, final boolean modal) throws HeadlessException
	{
		super(owner, title, modal);
	}

	/**
	 * CDialog
	 *
	 * @param owner
	 * @param title
	 * @param modal
	 * @param gc
	 * @throws HeadlessException
	 */
	public CDialog(final Dialog owner, final String title, final boolean modal, final GraphicsConfiguration gc) throws HeadlessException
	{
		super(owner, title, modal, gc);
	}

	/**
	 * CDialog
	 *
	 * @param owner
	 * @param title
	 * @param modal
	 * @param gc
	 */
	public CDialog(final Window owner, final String title, final boolean modal)
	{
		super(owner, title, modal ? ModalityType.APPLICATION_MODAL : ModalityType.MODELESS);
	}
	
	/**
	 * Initialize.
	 * Install ALT-Pause
	 */
	@Override
	protected void dialogInit()
	{
		super.dialogInit();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(getTitle());	// remove Mn
		//
		final Container c = getContentPane();
		if (c instanceof JPanel)
		{
			final JPanel panel = (JPanel)c;
			panel.getActionMap().put(ACTION_DISPOSE, s_dialogAction);
			panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(s_disposeKeyStroke, ACTION_DISPOSE);
		}
		
		// By default all dialog shall have our product icon.
		// Later, the caller can change it.
		setIconImage(Adempiere.getProductIconSmall());
		
		setBackground(AdempierePLAF.getFormBackground());
	}

	/**************************************************************************
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * @param e
	 */
	@Override
	public void actionPerformed(final ActionEvent e)
	{
	}

	/**
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 * @param e
	 */
	@Override
	public void mouseClicked(final MouseEvent e)
	{
	}

	/**
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 * @param e
	 */
	@Override
	public void mouseEntered(final MouseEvent e)
	{
	}

	/**
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 * @param e
	 */
	@Override
	public void mouseExited(final MouseEvent e)
	{
	}

	/**
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 * @param e
	 */
	@Override
	public void mousePressed(final MouseEvent e)
	{
	}

	/**
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 * @param e
	 */
	@Override
	public void mouseReleased(final MouseEvent e)
	{
	}

	/**
	 * Set Title
	 *
	 * @param title title
	 */
	@Override
	public void setTitle(String title)
	{
		if (title != null)
		{
			final int pos = title.indexOf('&');
			if (pos != -1 && title.length() > pos)	// We have a nemonic
			{
				final int mnemonic = title.toUpperCase().charAt(pos + 1);
				if (mnemonic != ' ')
				{
					title = title.substring(0, pos) + title.substring(pos + 1);
				}
			}
		}
		super.setTitle(title);
	}	// setTitle

	/** Dispose Action Name */
	protected static String ACTION_DISPOSE = "CDialogDispose";
	/** Action */
	protected static DialogAction s_dialogAction = new DialogAction(ACTION_DISPOSE);
	/** ALT-EXCAPE */
	protected static KeyStroke s_disposeKeyStroke =
			KeyStroke.getKeyStroke(KeyEvent.VK_PAUSE, InputEvent.ALT_MASK);

	/**
	 * Adempiere Dialog Action
	 *
	 * @author Jorg Janke
	 * @version $Id: CDialog.java,v 1.3 2006/07/30 00:52:24 jjanke Exp $
	 */
	static class DialogAction extends AbstractAction
	{
		/**
		 *
		 */
		private static final long serialVersionUID = -1502992970807699945L;

		DialogAction(final String actionName)
		{
			super(actionName);
			putValue(Action.ACTION_COMMAND_KEY, actionName);
		}	// DialogAction

		/**
		 * Action Listener
		 *
		 * @param e event
		 */
		@Override
		public void actionPerformed(final ActionEvent e)
		{
			if (ACTION_DISPOSE.equals(e.getActionCommand()))
			{
				Object source = e.getSource();
				while (source != null)
				{
					if (source instanceof Window)
					{
						((Window)source).dispose();
						return;
					}
					if (source instanceof Container)
					{
						source = ((Container)source).getParent();
					}
					else
					{
						source = null;
					}
				}
			}
			else
			{
				System.out.println("Action: " + e);
			}
		}	// actionPerformed
	}	// DialogAction

}	// CDialog

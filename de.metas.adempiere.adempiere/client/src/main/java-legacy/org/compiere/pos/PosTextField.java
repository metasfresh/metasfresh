/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved.               *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.compiere.pos;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.Format;

import javax.swing.JFormattedTextField;

/**
 * Formatted Text field with on-screen keyboard support
 *
 * @author Paul Bowden (Adaxa Pty Ltd)
 *
 */
public class PosTextField extends JFormattedTextField implements MouseListener
{
	/**
	 *
	 */
	private static final long serialVersionUID = -2453719110038264481L;

	// private final DefaultFormatterFactory formatFactory = new DefaultFormatterFactory();

	private final String title;

	private PosBasePanel pos = null;
	private int keyLayoutId = 0;

	public PosTextField(final String title, final PosBasePanel pos, final int posKeyLayout_ID, final Format format)
	{
		super(format);

		if (posKeyLayout_ID > 0)
		{
			addMouseListener(this);
		}

		keyLayoutId = posKeyLayout_ID;
		this.pos = pos;
		this.title = title;

	}

	public PosTextField(final String title, final PosBasePanel pos, final int posKeyLayout_ID, final AbstractFormatter formatter)
	{
		super(formatter);

		if (posKeyLayout_ID > 0)
		{
			addMouseListener(this);
		}

		keyLayoutId = posKeyLayout_ID;
		this.pos = pos;
		this.title = title;

	}

	public PosTextField(final String title, final PosBasePanel pos, final int posKeyLayout_ID)
	{
		super();

		if (posKeyLayout_ID > 0)
		{
			addMouseListener(this);
		}

		keyLayoutId = posKeyLayout_ID;
		this.pos = pos;
		this.title = title;

	}

	@Override
	public void mouseReleased(final MouseEvent arg0)
	{
		// nothing at this level
	}

	@Override
	public void mousePressed(final MouseEvent arg0)
	{
		// nothing at this level
	}

	@Override
	public void mouseExited(final MouseEvent arg0)
	{
		// nothing at this level
	}

	@Override
	public void mouseEntered(final MouseEvent arg0)
	{
		// nothing at this level
	}

	@Override
	public void mouseClicked(final MouseEvent arg0)
	{
		if (!isEnabled() || !isEditable())
		{
			return;
		}

		final POSKeyboard keyboard = pos.getKeyboard(keyLayoutId);
		keyboard.setTitle(title);
		keyboard.setPosTextField(this);
		keyboard.setVisible(true);
		fireActionPerformed();
	}
}

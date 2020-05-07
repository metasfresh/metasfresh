/**
 * 
 */
package de.metas.picking.terminal;

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


import java.awt.Color;

import javax.swing.tree.DefaultMutableTreeNode;

import org.adempiere.model.I_M_PackagingContainer;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalKeyStatus;
import de.metas.adempiere.form.terminal.TerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.model.I_C_POSKey;
import de.metas.picking.terminal.Utils.PackingStates;

/**
 * @author cg
 * 
 */
public class BoxKey extends TerminalKey
{
	private I_C_POSKey key;
	private KeyNamePair value;
	private String tableName;
	private int boxNo;
	private I_M_PackagingContainer container;
	private DefaultMutableTreeNode node;
	private boolean ready = false;

	public boolean isReady()
	{
		return ready;
	}

	public void setReady(boolean ready)
	{
		this.ready = ready;
	}

	public DefaultMutableTreeNode getNode()
	{
		return node;
	}

	public void setNode(DefaultMutableTreeNode node)
	{
		this.node = node;
	}

	public I_M_PackagingContainer getContainer()
	{
		return container;
	}

	public void setContainer(I_M_PackagingContainer container)
	{
		this.container = container;
	}

	//
	public BoxStatus updateBoxStatus(PackingStates status)
	{
		BoxStatus s = (BoxStatus)getStatus();
		// if the status is closed, don't change
		if (s.packStatus == PackingStates.closed )
		{
			return s;
		}
		//Status Open can be changed only to be put into closed
		if (s.packStatus == PackingStates.open && status != null && status != PackingStates.closed)
		{	
			return s;
		}
		
		if ((s.packStatus != PackingStates.ready && status != null)  || (s.getPackStatus() != status && status != null))
		{
			return new BoxStatus(status);
		}
		return s;
	}

	protected BoxKey(final ITerminalContext terminalContext, I_C_POSKey key, String tableName, int idValue)
	{
		this(terminalContext, key, tableName, new KeyNamePair(idValue, key.getName()));
	}

	public BoxKey(final ITerminalContext terminalContext, I_C_POSKey key, String tableName, KeyNamePair value)
	{
		super(terminalContext);
		
		this.key = key;
		this.tableName = tableName;
		this.value = value;
		setStatus(new BoxStatus());
	}

	public void setBoxNo(int no)
	{
		this.boxNo = no;
	}

	public int getBoxNo()
	{
		return this.boxNo;
	}

	@Override
	public String getId()
	{
		return "" + key.getC_POSKey_ID()
				+ (value != null ? "#" + value.getKey() : "" + boxNo);
	}

	public Object getName()
	{
		return value.getName() + "#" + boxNo;
	}

	public String getTableName()
	{
		return this.tableName;
	}

	public KeyNamePair getValue()
	{
		return value;
	}

	public String getText()
	{
		return key.getText();
	}

	public int getSpanX()
	{
		return key.getSpanX();
	}

	public int getSpanY()
	{
		return key.getSpanY();
	}

	public boolean isActive()
	{
		return key.isActive();
	}

	@Override
	public IKeyLayout getSubKeyLayout()
	{
		return null;
	}

	private class BoxStatus implements ITerminalKeyStatus
	{
		private Color color;
		private PackingStates packStatus;

		public PackingStates getPackStatus()
		{
			return packStatus;
		}

		public BoxStatus()
		{
			packStatus = PackingStates.unpacked;
			setColor(packStatus.getColor());
		}

		public BoxStatus(PackingStates state)
		{
			this();
			packStatus = state;
			setColor(packStatus.getColor());
		}

		@Override
		public int getAD_Image_ID()
		{
			return key.getAD_Image_ID();
		}

		@Override
		public int getAD_PrintColor_ID()
		{
			return key.getAD_PrintColor_ID();
		}

		@Override
		public int getAD_PrintFont_ID()
		{
			return key.getAD_PrintFont_ID();
		}

		@Override
		public String getName()
		{
			return getPackStatus().name();
		}

		@Override
		public Color getColor()
		{
			return color;
		}

		public void setColor(Color color)
		{
			this.color = color;
		}
	}

}

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
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.LegacyPackingItem;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalKeyStatus;
import de.metas.adempiere.form.terminal.TerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.model.I_C_POSKey;

/**
 * @author cg
 * 
 */
public class NewKartonKey extends TerminalKey
{

	public I_C_POSKey key;
	private KeyNamePair value;
	private String tableName;
	private DefaultMutableTreeNode node;

	public DefaultMutableTreeNode getNode()
	{
		return node;
	}

	public void setNode(DefaultMutableTreeNode node)
	{
		this.node = node;
	}

	protected NewKartonKey(final ITerminalContext terminalContext, I_C_POSKey key, String tableName, int idValue)
	{
		this(terminalContext, key, tableName, new KeyNamePair(idValue, key.getName()));
	}

	protected NewKartonKey(final ITerminalContext terminalContext, I_C_POSKey key, String tableName, KeyNamePair value)
	{
		super(terminalContext);
		
		this.key = key;
		this.tableName = tableName;
		this.value = value;
		this.status = new NewKartonStatus();
	}

	@Override
	public String getId()
	{
		return "" + key.getC_POSKey_ID()
				+ (value != null ? "#" + value.getKey() : "");
	}

	public Object getName()
	{
		return value.getName();
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
		// nothing to do
		return null;
	}

	public List<LegacyPackingItem> getPackItems()
	{
		// nothing to do
		return null;
	}

	@Override
	public ITerminalKeyStatus getStatus()
	{
		return status;
	}

	private ITerminalKeyStatus status;

	private class NewKartonStatus implements ITerminalKeyStatus
	{

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
			return "default";
		}

		@Override
		public Color getColor()
		{
			return null;
		}
	}

}

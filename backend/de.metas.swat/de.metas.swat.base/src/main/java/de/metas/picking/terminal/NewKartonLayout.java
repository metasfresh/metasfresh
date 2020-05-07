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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.I_M_PackagingContainer;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.KeyLayout;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.picking.legacy.form.AvailableBins;
import de.metas.picking.terminal.form.swing.AbstractPackageTerminalPanel;

/**
 * @author cg
 * 
 */
public class NewKartonLayout extends KeyLayout
{

	public static final Color DEFAULT_Color = Color.RED;

	public NewKartonLayout(ITerminalContext tc)
	{
		super(tc);
		setColumns(3);
		setDefaultColor(DEFAULT_Color);
	}

	@Override
	public String getId()
	{
		return "NewKartonLayout#" + 103;
	}

	@Override
	protected List<ITerminalKey> createKeys()
	{
		List<DefaultMutableTreeNode> results = ((AbstractPackageTerminalPanel)getBasePanel()).getAvailBoxes();
		List<ITerminalKey> list = new ArrayList<ITerminalKey>();
		if (((AvailableBins)results.get(0).getUserObject()).getQtyAvail() > 0)
		{
			for (int i = 0; i < results.size(); i++)
			{
				I_M_PackagingContainer pc = ((AvailableBins)results.get(i).getUserObject()).getPc();
				de.metas.adempiere.model.I_C_POSKey key = InterfaceWrapperHelper.create(getCtx(), de.metas.adempiere.model.I_C_POSKey.class, ITrx.TRXNAME_None);
				if (!key.isActive())
					continue;

				String tableName = I_M_PackagingContainer.Table_Name;
				int idValue = pc.getM_Product_ID();
				key.setName(pc.getName());
			
				NewKartonKey tk = new NewKartonKey(getTerminalContext(), key, tableName, idValue);
				tk.setNode(results.get(i));
				list.add(tk);
			}
		}
		return Collections.unmodifiableList(list);
	}

	@Override
	public boolean isNumeric()
	{
		return false;
	}

	@Override
	public boolean isText()
	{
		return false;
	}
}

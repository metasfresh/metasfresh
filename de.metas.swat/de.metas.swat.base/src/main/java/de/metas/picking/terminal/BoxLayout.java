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
import java.awt.Font;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.I_M_PackagingContainer;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.adempiere.form.IPackingItem;
import de.metas.adempiere.form.PackingItemsMap;
import de.metas.adempiere.form.UsedBin;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.KeyLayout;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.picking.terminal.Utils.PackingStates;
import de.metas.picking.terminal.form.swing.AbstractPackageTerminalPanel;

/**
 * @author cg
 * 
 */
public class BoxLayout extends KeyLayout
{

	public static final Color DEFAULT_Color = Color.green;

	BigDecimal volumeSumContainer = BigDecimal.ZERO;

	BigDecimal weightSumContainer = BigDecimal.ZERO;

	private int BoxesNo = 0;

	public int getBoxesNo()
	{
		return BoxesNo;
	}

	public void setBoxesNo(int count)
	{
		this.BoxesNo = count;
	}

	// FIXME: please use the keys from super class
	private List<ITerminalKey> keys = null;

	@Override
	public void resetKeys()
	{
		super.resetKeys();
		keys = null;
	}

	public BoxLayout(ITerminalContext tc)
	{
		super(tc);
		setColumns(3);
	}

	@Override
	public Color getDefaultColor(ITerminalKey key)
	{
		return DEFAULT_Color;
	}

	@Override
	public Font getDefaultFont()
	{
		return getTerminalContext().getTerminalFactory().getDefaultFieldFont();
	}

	@Override
	public String getId()
	{
		return "BoxLayout#" + 101;
	}

	@Override
	public void addKey(ITerminalKey key)
	{
		List<ITerminalKey> list = new ArrayList<ITerminalKey>(keys);
		Collections.copy(list, keys);
		((BoxKey)key).setBoxNo(getBoxesNo() + 1);
		list.add(key);
		BoxesNo = list.size();
		keys = null;
		keys = Collections.unmodifiableList(list);
	}

	public void removeKey(ITerminalKey key)
	{
		List<ITerminalKey> list = new ArrayList<ITerminalKey>(keys);
		Collections.copy(list, keys);
		list.remove(key);
		int i = 1;
		for (ITerminalKey tk : list)
		{
			((BoxKey)tk).setBoxNo(i);
			i++;
		}
		BoxesNo = list.size();
		keys = null;
		keys = Collections.unmodifiableList(list);
	}

	@Override
	protected List<ITerminalKey> createKeys()
	{
		final Map<Integer, DefaultMutableTreeNode> results = ((AbstractPackageTerminalPanel)getBasePanel()).getBoxes();

		List<ITerminalKey> list = new ArrayList<ITerminalKey>();
		for (int i = 0; i < results.size(); i++)
		{
			final UsedBin usedBin = (UsedBin)results.get(i + 1).getUserObject();
			final I_M_PackagingContainer container = usedBin.getPackagingContainer();

			final de.metas.adempiere.model.I_C_POSKey key = InterfaceWrapperHelper.create(getCtx(), de.metas.adempiere.model.I_C_POSKey.class, ITrx.TRXNAME_None);
			if (!key.isActive())
			{
				continue;
			}

			final String tableName = I_M_PackagingContainer.Table_Name;
			final int idValue = Integer.parseInt(container.getM_Product_ID() + "" + (i + 1));
			key.setName(container.getName());
			
			final BoxKey tk = new BoxKey(getTerminalContext(), key, tableName, idValue);
			if (usedBin.isReady())
			{
				tk.setStatus(tk.updateBoxStatus(PackingStates.ready));
			}
			else
			{
				tk.setStatus(tk.updateBoxStatus(getContainerState(container, i + 1)));
			}
			if (usedBin.isMarkedForPacking())
			{
				tk.setStatus(tk.updateBoxStatus(PackingStates.closed));
			}
			if (usedBin.getM_Package_ID() > 0)
			{
				tk.setStatus(tk.updateBoxStatus(PackingStates.open));
			}
			tk.setContainer(container);
			tk.setNode(results.get(i + 1));
			tk.setBoxNo(i + 1);
			list.add(tk);
		}
		BoxesNo = list.size();
		return Collections.unmodifiableList(list);
	}

	public BoxKey getBoxKey(DefaultMutableTreeNode node)
	{
		for (ITerminalKey key : getKeys())
		{
			BoxKey boxKey = (BoxKey)key;
			if (boxKey.getNode() == node)
				return boxKey;
		}
		return null;
	}

	
	public BigDecimal getVolumeSumContainer(I_M_PackagingContainer container, int boxNo)
	{
		final PackingItemsMap packItems = getPackageItems().getPackItems();
		List<IPackingItem> products = packItems.get(boxNo);
		if (products == null || products.size() == 0)
			return null;

		volumeSumContainer = BigDecimal.ZERO;
		for (IPackingItem pi : products)
		{
			final BigDecimal volSingle = pi.retrieveVolumeSingle(null);
			volumeSumContainer = volumeSumContainer.add(pi.getQtySum().multiply(volSingle));
		}

		return volumeSumContainer;
	}

	public AbstractPackageTerminalPanel getPackageItems()
	{
		final AbstractPackageTerminalPanel packageItem = (AbstractPackageTerminalPanel)getBasePanel();
		return packageItem;
	}
	
	public BigDecimal getWeightSumContainer(I_M_PackagingContainer container, int boxNo)
	{
		final PackingItemsMap packItems = getPackageItems().getPackItems();
		List<IPackingItem> products = packItems.get(boxNo);
		if (products == null || products.size() == 0)
			return null;

		weightSumContainer = BigDecimal.ZERO;

		for (IPackingItem pi : products)
		{
			final BigDecimal weightSingle = pi.retrieveWeightSingle(null);
			weightSumContainer = weightSumContainer.add(pi.getQtySum().multiply(weightSingle));
		}

		return weightSumContainer;
	}

	public PackingStates getContainerState(I_M_PackagingContainer container, int boxNo)
	{
		final PackingItemsMap packItems = getPackageItems().getPackItems();
		List<IPackingItem> products = packItems.get(boxNo);
		if (products == null || products.size() == 0)
			return null;
		
		if (products.size() > 0)
			return PackingStates.partiallypacked;
			
		/// don't care anymore about volume and weight

		BigDecimal volumeMin = BigDecimal.ZERO;
		BigDecimal volumeMax = BigDecimal.ZERO;
		BigDecimal weightMin = BigDecimal.ZERO;
		BigDecimal weightMax = BigDecimal.ZERO;
		weightSumContainer = BigDecimal.ZERO;
		volumeSumContainer = BigDecimal.ZERO;

		for (IPackingItem pi : products)
		{
			final BigDecimal volSingle = pi.retrieveVolumeSingle(null);
			final BigDecimal weightSingle = pi.retrieveWeightSingle(null);
			weightSumContainer = weightSumContainer.add(pi.getQtySum().multiply(weightSingle));
			volumeSumContainer = volumeSumContainer.add(pi.getQtySum().multiply(volSingle));

			if (volumeMax.compareTo(volSingle) < 0)
			{
				volumeMax = volSingle;
			}
			if (weightMax.compareTo(weightSingle) < 0)
			{
				weightMax = weightSingle;
			}

			if (volumeMin.compareTo(volSingle) > 0)
			{
				volumeMin = volSingle;
			}
			if (weightMin.compareTo(weightSingle) > 0)
			{
				weightMin = weightSingle;
			}
		}

		PackingStates state = PackingStates.packed;

		if ((container.getMaxVolume().compareTo(volumeMax.add(volumeSumContainer)) >= 0 
				|| container.getMaxVolume().compareTo(volumeMin.add(volumeSumContainer)) >= 0)
				&& (container.getMaxWeight().compareTo(weightMax.add(weightSumContainer)) >= 0 
				|| container.getMaxWeight().compareTo(weightMin.add(weightSumContainer)) >= 0))
		{
			if (weightSumContainer.compareTo(BigDecimal.ZERO) == 0)
				state = PackingStates.unpacked;
			else if ((container.getMaxVolume().compareTo(volumeMin.add(volumeSumContainer)) > 0)
					&& (container.getMaxWeight().compareTo(weightMin.add(weightSumContainer)) > 0))
				state = PackingStates.partiallypacked;
		}
		if (container.getMaxVolume().compareTo(volumeSumContainer) < 0
				|| container.getMaxWeight().compareTo(weightSumContainer) < 0)
		{
			state = PackingStates.overlapped;
		}

		return state;
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

	public void checkKeyState()
	{
		List<ITerminalKey> list = getKeys();
		for (ITerminalKey key : list)
		{
			BoxKey bk = (BoxKey)key;
			PackingStates state = null;
			if (bk.getStatus().getName().equals(PackingStates.ready.name()))
			{
				state = PackingStates.ready;
			}
			else if (bk.getStatus().getName().equals(PackingStates.closed.name()))
			{
				state = PackingStates.closed;
			}
			else
			{
				state = getContainerState(bk.getContainer(), bk.getBoxNo());
			}
			if (state == null)
				continue;
			bk.setStatus(bk.updateBoxStatus(state));
		}
	}
}

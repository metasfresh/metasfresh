package org.adempiere.model.tree;

/*
 * #%L
 * ADempiere ERP - Base
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


import org.adempiere.util.WeakList;
import org.compiere.model.PO;

/**
 * 
 * @author Teo Sarca
 */
public class TreeListenerSupport implements ITreeListener
{
	private final WeakList<ITreeListener> listeners = new WeakList<ITreeListener>();
	
	public void addTreeListener(ITreeListener listener, boolean isWeak)
	{
		if (!listeners.contains(listener))
			listeners.add(listener, isWeak);
	}
	
	public void removeTreeListener(ITreeListener listener)
	{
		listeners.remove(listener);
	}

	@Override
	public void onNodeInserted(PO po)
	{
		for (ITreeListener listener : listeners)
		{
			listener.onNodeInserted(po);
		}
	}

	@Override
	public void onNodeDeleted(PO po)
	{
		for (ITreeListener listener : listeners)
		{
			listener.onNodeDeleted(po);
		}
	}

	@Override
	public void onParentChanged(int AD_Table_ID, int nodeId, int newParentId, int oldParentId, String trxName)
	{
		if (newParentId == oldParentId)
			return;
		for (ITreeListener listener : listeners)
		{
			listener.onParentChanged(AD_Table_ID, nodeId, newParentId, oldParentId, trxName);
		}
	}


}

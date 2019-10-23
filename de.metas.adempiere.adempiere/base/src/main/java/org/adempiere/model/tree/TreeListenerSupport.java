package org.adempiere.model.tree;

import org.compiere.model.PO;

import de.metas.util.WeakList;

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

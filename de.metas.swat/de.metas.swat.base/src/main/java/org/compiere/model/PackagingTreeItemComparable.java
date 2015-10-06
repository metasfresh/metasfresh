package org.compiere.model;

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


import java.sql.ResultSet;
import java.util.Properties;


public class PackagingTreeItemComparable extends X_M_PackagingTreeItem implements Comparable<X_M_PackagingTreeItem>
{

	private boolean freezedItem = false;
	
	
	public boolean isFreezedItem()
	{
		return freezedItem;
	}

	public void setFreezedItem(boolean freezedItem)
	{
		this.freezedItem = freezedItem;
	}

	public PackagingTreeItemComparable(Properties ctx, int M_PackagingTreeItem_ID, String trxName)
	{
		super(ctx, M_PackagingTreeItem_ID, trxName);
	}

	public PackagingTreeItemComparable(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -163531299356934148L;

	@Override
	public int compareTo(X_M_PackagingTreeItem o)
	{
		return getM_PackageTree_ID() - o.getM_PackageTree_ID();
	}

	@Override 
	public int hashCode()
	{
		if (X_M_PackagingTreeItem.TYPE_Box.equals(getType()))
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + getM_PackageTree_ID();
			return result;
		}
		else
			return super.hashCode();
		
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		
		if (X_M_PackagingTreeItem.TYPE_Box.equals(getType()))
		{
			
			if (getClass() != obj.getClass())
			{
				return false;
			}
			
			if (getM_PackageTree_ID() != ((X_M_PackagingTreeItem)obj).getM_PackageTree_ID())
			{
				return false;
			}
			return true;
		}
		else if (X_M_PackagingTreeItem.TYPE_PackedItem.equals(getType()))
		{
			
			if (getClass() != obj.getClass())
			{
				return false;
			}
			
			if (getM_PackageTree_ID() != ((X_M_PackagingTreeItem)obj).getM_PackageTree_ID() || getM_Product_ID() != ((X_M_PackagingTreeItem)obj).getM_Product_ID() && getQty() != ((X_M_PackagingTreeItem)obj).getQty())
			{
				return false;
			}
			return true;
		}
		else 
		{
			return super.equals(obj);
		}
			
		
	}

	
}
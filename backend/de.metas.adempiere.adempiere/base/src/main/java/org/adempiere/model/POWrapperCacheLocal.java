package org.adempiere.model;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.Properties;

import lombok.NonNull;
import org.compiere.model.AbstractPOCacheLocal;

/**
 * Same as {@link org.compiere.model.POCacheLocal}, but for a {@link POWrapper} parent.
 * <p>
 * The parent is referenced strongly on purpose. An instance lives only in its parent's {@code POWrapper.poCacheLocals} map
 * (or in a running call on it), so this reference adds no retention: parent and cache-local are collected together.
 * A weak reference could be cleared by the GC while a getter on the parent was still running, which failed the call with "POWrapper reference expired".
 */
/*package*/class POWrapperCacheLocal extends AbstractPOCacheLocal
{
	@NonNull private final POWrapper parentPOWrapper;
	private final int parentColumnIndex;

	public POWrapperCacheLocal(@NonNull final POWrapper parentPOWrapper, final String parentColumnName, final String refTableName)
	{
		super(parentColumnName, refTableName);
		this.parentPOWrapper = parentPOWrapper;
		this.parentColumnIndex = parentPOWrapper.getColumnIndex(parentColumnName);
	}

	private POWrapper getParentPOWrapper()
	{
		return parentPOWrapper;
	}

	@Override
	protected Properties getParentCtx()
	{
		return getParentPOWrapper().getCtx();
	}

	@Override
	protected String getParentTrxName()
	{
		return getParentPOWrapper().getTrxName();
	}

	@Override
	protected int getId()
	{
		return getParentPOWrapper().getValueAsInt(parentColumnIndex);
	}

	@Override
	protected boolean setId(int id)
	{
		getParentPOWrapper().setValue(getParentColumnName(), id);
		return true;
	}

}

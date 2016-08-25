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

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.WeakReference;
import org.compiere.model.AbstractPOCacheLocal;

/*package*/class POWrapperCacheLocal extends AbstractPOCacheLocal
{
	private final WeakReference<POWrapper> parentPOWrapperRef;
	private final int parentColumnIndex;

	public POWrapperCacheLocal(final POWrapper parentPOWrapper, final String parentColumnName, final String refTableName)
	{
		super(parentColumnName, refTableName);
		this.parentPOWrapperRef = new WeakReference<>(parentPOWrapper);
		this.parentColumnIndex = parentPOWrapper.getColumnIndex(parentColumnName);
	}

	private final POWrapper getParentPOWrapper()
	{
		final POWrapper poWrapper = parentPOWrapperRef.getValue();
		if (poWrapper == null)
		{
			throw new AdempiereException("POWrapper reference expired");
		}
		return poWrapper;
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

package org.adempiere.mm.attributes.api.impl;

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


import java.util.List;

import org.adempiere.ad.modelvalidator.AbstractModelInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.mm.attributes.api.IModelAttributeSetInstanceListener;
import org.adempiere.model.InterfaceWrapperHelper;

import lombok.NonNull;

/*package*/final class ModelAttributeSetInstanceListenerInterceptor extends AbstractModelInterceptor
{
	private final IModelAttributeSetInstanceListener listener;

	public ModelAttributeSetInstanceListenerInterceptor(@NonNull final IModelAttributeSetInstanceListener listener)
	{
		this.listener = listener;
	}

	@Override
	protected void onInit(final IModelValidationEngine engine, final org.compiere.model.I_AD_Client client)
	{
		final String tableName = listener.getSourceTableName();
		engine.addModelChange(tableName, this);
	}

	@Override
	public void onModelChange(final Object model, final ModelChangeType changeType) throws Exception
	{
		// Skip updating the ASI if automatic ASI updating is disabled (08091)
		if (IModelAttributeSetInstanceListener.DYNATTR_DisableASIUpdateOnModelChange.getValue(model, false))
		{
			return;
		}

		//
		// Fire listener on BEFORE_NEW
		if (changeType == ModelChangeType.BEFORE_NEW)
		{
			listener.modelChanged(model);
		}
		//
		// Fire listener on BEFORE CHANGE, ONLY if one of the source columns were changed
		else if (changeType == ModelChangeType.BEFORE_CHANGE && isValueChanged(model, listener.getSourceColumnNames()))
		{
			listener.modelChanged(model);
		}
	}

	/**
	 * @param model
	 * @param columnNames
	 * @return true if at least one of the given column names were changed.
	 */
	private final boolean isValueChanged(final Object model, final List<String> columnNames)
	{
		if (columnNames == IModelAttributeSetInstanceListener.ANY_SOURCE_COLUMN)
		{
			return true;
		}

		if (columnNames == null || columnNames.isEmpty())
		{
			return false;
		}

		for (final String columnName : columnNames)
		{
			if (InterfaceWrapperHelper.isValueChanged(model, columnName))
			{
				return true;
			}
		}

		return false;
	}
}

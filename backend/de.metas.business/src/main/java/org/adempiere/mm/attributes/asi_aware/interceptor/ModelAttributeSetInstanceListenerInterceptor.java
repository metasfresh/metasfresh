package org.adempiere.mm.attributes.asi_aware.interceptor;

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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.AbstractModelInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.mm.attributes.asi_aware.listener.IModelAttributeSetInstanceListener;
import org.adempiere.model.InterfaceWrapperHelper;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ModelAttributeSetInstanceListenerInterceptor extends AbstractModelInterceptor
{
	private static final Logger logger = LogManager.getLogger(ModelAttributeSetInstanceListenerInterceptor.class);
	private final ImmutableListMultimap<String, IModelAttributeSetInstanceListener> listenersBySourceTableName;

	public ModelAttributeSetInstanceListenerInterceptor(@NonNull final Optional<List<IModelAttributeSetInstanceListener>> listeners)
	{
		final List<IModelAttributeSetInstanceListener> listenersList = listeners.orElseGet(ImmutableList::of);

		listenersBySourceTableName = Multimaps.index(listenersList, IModelAttributeSetInstanceListener::getSourceTableName);
		logger.info("Registered {} listeners: {}", listenersList.size(), listenersBySourceTableName);
	}

	@Override
	protected void onInit(final IModelValidationEngine engine, final org.compiere.model.I_AD_Client client)
	{
		listenersBySourceTableName.keySet()
				.forEach(tableName -> engine.addModelChange(tableName, this));
	}

	@Override
	public void onModelChange(final Object model, final ModelChangeType changeType)
	{
		if (changeType != ModelChangeType.BEFORE_NEW && changeType != ModelChangeType.BEFORE_CHANGE)
		{
			return;
		}

		// Skip updating the ASI if automatic ASI updating is disabled (08091)
		if (IModelAttributeSetInstanceListener.DYNATTR_DisableASIUpdateOnModelChange.getValue(model, false))
		{
			return;
		}

		final String tableName = InterfaceWrapperHelper.getModelTableName(model);
		for (final IModelAttributeSetInstanceListener listener : listenersBySourceTableName.get(tableName))
		{
			if (changeType.isNew() || isValueChanged(model, listener.getSourceColumnNames()))
			{
				listener.modelChanged(model);
			}
		}
	}

	/**
	 * @return true if at least one of the given column names were changed.
	 */
	private static boolean isValueChanged(final Object model, final List<String> columnNames)
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

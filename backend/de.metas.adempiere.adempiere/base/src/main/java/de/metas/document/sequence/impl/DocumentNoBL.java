package de.metas.document.sequence.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.document.sequence.IDocumentNoBL;
import de.metas.document.sequence.spi.IDocumentNoAware;
import de.metas.document.sequence.spi.IDocumentNoListener;
import de.metas.util.Check;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class DocumentNoBL implements IDocumentNoBL
{
	private final Map<String, IDocumentNoListener> listeners = new HashMap<>();

	@Override
	public void registerDocumentNoListener(final IDocumentNoListener listener)
	{
		Check.assumeNotNull(listener, "Param 'listener' is not null");
		listeners.put(listener.getTableName(), listener);
	}

	@Override
	public void fireDocumentNoChange(final Object model, final String newDocumentNo)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableNameOrNull(model);
		if (Check.isEmpty(tableName))
		{
			return;
		}

		final IDocumentNoListener documentNoListener = listeners.get(tableName);
		if (documentNoListener == null)
		{
			return;
		}

		final Optional<IDocumentNoAware> documentNoAware = asDocumentNoAware(model);
		if (!documentNoAware.isPresent())
		{
			return;
		}

		documentNoListener.onDocumentNoChange(documentNoAware.get(), newDocumentNo);
	}

	@Override
	public Optional<IDocumentNoAware> asDocumentNoAware(final Object model)
	{
		final IDocumentNoAware documentNoAware = InterfaceWrapperHelper.asColumnReferenceAwareOrNull(model, IDocumentNoAware.class);

		return Optional.of(documentNoAware);
	}

}

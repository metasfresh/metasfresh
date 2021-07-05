/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.document.location.adapter;

import de.metas.logging.LogManager;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentLocationAdaptersRegistry
{
	private static final Logger logger = LogManager.getLogger(DocumentLocationAdaptersRegistry.class);

	private final CompositeDocumentLocationAdapterFactory factories;

	public DocumentLocationAdaptersRegistry(final List<DocumentLocationAdapterFactory> factories)
	{
		this.factories = new CompositeDocumentLocationAdapterFactory(factories);
		logger.info("Factories: {}", this.factories);
	}

	public Optional<IDocumentLocationAdapter> getDocumentLocationAdapterIfHandled(@NonNull final Object record)
	{
		return factories.getDocumentLocationAdapterIfHandled(record);
	}

	public Optional<IDocumentBillLocationAdapter> getDocumentBillLocationAdapterIfHandled(@NonNull final Object record)
	{
		return factories.getDocumentBillLocationAdapterIfHandled(record);
	}

	public Optional<IDocumentDeliveryLocationAdapter> getDocumentDeliveryLocationAdapter(@NonNull final Object record)
	{
		return factories.getDocumentDeliveryLocationAdapter(record);
	}

	public Optional<IDocumentHandOverLocationAdapter> getDocumentHandOverLocationAdapter(@NonNull final Object record)
	{
		return factories.getDocumentHandOverLocationAdapter(record);
	}
}

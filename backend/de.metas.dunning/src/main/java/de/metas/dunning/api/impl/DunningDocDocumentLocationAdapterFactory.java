/*
 * #%L
 * de.metas.dunning
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

package de.metas.dunning.api.impl;

import de.metas.document.location.adapter.DocumentLocationAdapterFactory;
import de.metas.document.location.adapter.IDocumentBillLocationAdapter;
import de.metas.document.location.adapter.IDocumentDeliveryLocationAdapter;
import de.metas.document.location.adapter.IDocumentHandOverLocationAdapter;
import de.metas.document.location.adapter.IDocumentLocationAdapter;
import de.metas.dunning.model.I_C_DunningDoc;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DunningDocDocumentLocationAdapterFactory implements DocumentLocationAdapterFactory
{
	public static DunningDocDocumentLocationAdapter locationAdapter(@NonNull final I_C_DunningDoc delegate)
	{
		return new DunningDocDocumentLocationAdapter(delegate);
	}

	@Override
	public Optional<IDocumentLocationAdapter> getDocumentLocationAdapterIfHandled(final Object record)
	{
		return toDunningDoc(record).map(DunningDocDocumentLocationAdapterFactory::locationAdapter);
	}

	@Override
	public Optional<IDocumentBillLocationAdapter> getDocumentBillLocationAdapterIfHandled(final Object record)
	{
		return Optional.empty();
	}

	@Override
	public Optional<IDocumentDeliveryLocationAdapter> getDocumentDeliveryLocationAdapter(final Object record)
	{
		return Optional.empty();
	}

	@Override
	public Optional<IDocumentHandOverLocationAdapter> getDocumentHandOverLocationAdapter(final Object record)
	{
		return Optional.empty();
	}

	private static Optional<I_C_DunningDoc> toDunningDoc(final Object record)
	{
		return InterfaceWrapperHelper.isInstanceOf(record, I_C_DunningDoc.class)
				? Optional.of(InterfaceWrapperHelper.create(record, I_C_DunningDoc.class))
				: Optional.empty();
	}

}

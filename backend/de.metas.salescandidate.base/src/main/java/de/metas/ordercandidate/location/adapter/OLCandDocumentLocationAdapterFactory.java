/*
 * #%L
 * de.metas.salescandidate.base
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

package de.metas.ordercandidate.location.adapter;

import de.metas.document.location.adapter.DocumentLocationAdapterFactory;
import de.metas.document.location.adapter.IDocumentBillLocationAdapter;
import de.metas.document.location.adapter.IDocumentDeliveryLocationAdapter;
import de.metas.document.location.adapter.IDocumentHandOverLocationAdapter;
import de.metas.document.location.adapter.IDocumentLocationAdapter;
import de.metas.ordercandidate.model.I_C_OLCand;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OLCandDocumentLocationAdapterFactory implements DocumentLocationAdapterFactory
{
	@Override
	public Optional<IDocumentLocationAdapter> getDocumentLocationAdapterIfHandled(final Object record)
	{
		return toOLCand(record).map(OLCandDocumentLocationAdapterFactory::bpartnerLocationAdapter);
	}

	@Override
	public Optional<IDocumentBillLocationAdapter> getDocumentBillLocationAdapterIfHandled(final Object record)
	{
		return toOLCand(record).map(OLCandDocumentLocationAdapterFactory::billLocationAdapter);
	}

	@Override
	public Optional<IDocumentDeliveryLocationAdapter> getDocumentDeliveryLocationAdapter(final Object record)
	{
		return toOLCand(record).map(OLCandDocumentLocationAdapterFactory::dropShipAdapter);
	}

	@Override
	public Optional<IDocumentHandOverLocationAdapter> getDocumentHandOverLocationAdapter(final Object record)
	{
		return toOLCand(record).map(OLCandDocumentLocationAdapterFactory::handOverLocationAdapter);
	}

	private static Optional<I_C_OLCand> toOLCand(final Object record)
	{
		return InterfaceWrapperHelper.isInstanceOf(record, I_C_OLCand.class)
				? Optional.of(InterfaceWrapperHelper.create(record, I_C_OLCand.class))
				: Optional.empty();
	}

	public static BPartnerLocationAdapter bpartnerLocationAdapter(@NonNull final I_C_OLCand delegate)
	{
		return new BPartnerLocationAdapter(delegate);
	}

	public static BPartnerOverrideLocationAdapter bpartnerLocationOverrideAdapter(@NonNull final I_C_OLCand delegate)
	{
		return new BPartnerOverrideLocationAdapter(delegate);
	}

	public static DocumentBillLocationAdapter billLocationAdapter(@NonNull final I_C_OLCand delegate)
	{
		return new DocumentBillLocationAdapter(delegate);
	}

	public static DropShipLocationAdapter dropShipAdapter(@NonNull final I_C_OLCand delegate)
	{
		return new DropShipLocationAdapter(delegate);
	}

	public static DropShipOverrideLocationAdapter dropShipOverrideAdapter(@NonNull final I_C_OLCand delegate)
	{
		return new DropShipOverrideLocationAdapter(delegate);
	}

	public static HandOverLocationAdapter handOverLocationAdapter(@NonNull final I_C_OLCand delegate)
	{
		return new HandOverLocationAdapter(delegate);
	}

	public static HandOverOverrideLocationAdapter handOverOverrideLocationAdapter(@NonNull final I_C_OLCand delegate)
	{
		return new HandOverOverrideLocationAdapter(delegate);
	}
}

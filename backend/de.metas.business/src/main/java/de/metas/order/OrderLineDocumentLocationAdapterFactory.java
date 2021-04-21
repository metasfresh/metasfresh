/*
 * #%L
 * de.metas.business
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

package de.metas.order;

import de.metas.document.location.adapter.DocumentLocationAdapterFactory;
import de.metas.document.location.adapter.IDocumentBillLocationAdapter;
import de.metas.document.location.adapter.IDocumentDeliveryLocationAdapter;
import de.metas.document.location.adapter.IDocumentHandOverLocationAdapter;
import de.metas.document.location.adapter.IDocumentLocationAdapter;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderLineDocumentLocationAdapterFactory implements DocumentLocationAdapterFactory
{
	public static OrderLineDocumentLocationAdapter locationAdapter(@NonNull final I_C_OrderLine delegate)
	{
		return new OrderLineDocumentLocationAdapter(delegate);
	}

	@Override
	public Optional<IDocumentLocationAdapter> getDocumentLocationAdapterIfHandled(final Object record)
	{
		return toOrderLine(record).map(OrderLineDocumentLocationAdapterFactory::locationAdapter);
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

	private static Optional<I_C_OrderLine> toOrderLine(final Object record)
	{
		return InterfaceWrapperHelper.isInstanceOf(record, I_C_OrderLine.class)
				? Optional.of(InterfaceWrapperHelper.create(record, I_C_OrderLine.class))
				: Optional.empty();
	}

	public static class OrderLineDocumentLocationAdapter implements IDocumentLocationAdapter
	{
		private final I_C_OrderLine delegate;

		private OrderLineDocumentLocationAdapter(@NonNull final I_C_OrderLine delegate)
		{
			this.delegate = delegate;
		}

		@Override
		public int getC_BPartner_ID()
		{
			return delegate.getC_BPartner_ID();
		}

		@Override
		public int getC_BPartner_Location_ID()
		{
			return delegate.getC_BPartner_Location_ID();
		}

		@Override
		public void setC_BPartner_Location_ID(final int C_BPartner_Location_ID)
		{
			delegate.setC_BPartner_Location_ID(C_BPartner_Location_ID);
		}

		@Override
		public int getC_BPartner_Location_Value_ID()
		{
			return -1;
		}

		@Override
		public void setC_BPartner_Location_Value_ID(final int C_BPartner_Location_Value_ID)
		{
		}

		@Override
		public int getAD_User_ID()
		{
			return delegate.getAD_User_ID();
		}

		@Override
		public String getBPartnerAddress()
		{
			return delegate.getBPartnerAddress();
		}

		@Override
		public void setBPartnerAddress(String address)
		{
			delegate.setBPartnerAddress(address);
		}
	}
}

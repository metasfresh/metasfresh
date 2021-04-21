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

package de.metas.inout;

import de.metas.document.location.adapter.DocumentLocationAdapterFactory;
import de.metas.document.location.adapter.IDocumentBillLocationAdapter;
import de.metas.document.location.adapter.IDocumentDeliveryLocationAdapter;
import de.metas.document.location.adapter.IDocumentHandOverLocationAdapter;
import de.metas.document.location.adapter.IDocumentLocationAdapter;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.checkerframework.checker.units.qual.C;
import org.compiere.model.I_M_InOut;

import java.sql.DriverPropertyInfo;
import java.util.Optional;

public class InOutDocumentLocationAdapterFactory implements DocumentLocationAdapterFactory
{
	public static DocumentLocationAdapter locationAdapter(@NonNull final I_M_InOut delegate)
	{
		return new DocumentLocationAdapter(delegate);
	}

	public static DocumentDeliveryLocationAdapter deliveryLocationAdapter(@NonNull final I_M_InOut delegate)
	{
		return new DocumentDeliveryLocationAdapter(delegate);
	}

	@Override
	public Optional<IDocumentLocationAdapter> getDocumentLocationAdapterIfHandled(final Object record)
	{
		return toInOut(record).map(InOutDocumentLocationAdapterFactory::locationAdapter);
	}

	@Override
	public Optional<IDocumentBillLocationAdapter> getDocumentBillLocationAdapterIfHandled(final Object record)
	{
		return Optional.empty();
	}

	@Override
	public Optional<IDocumentDeliveryLocationAdapter> getDocumentDeliveryLocationAdapter(final Object record)
	{
		return toInOut(record).map(InOutDocumentLocationAdapterFactory::deliveryLocationAdapter);
	}

	@Override
	public Optional<IDocumentHandOverLocationAdapter> getDocumentHandOverLocationAdapter(final Object record)
	{
		return Optional.empty();
	}

	private static Optional<I_M_InOut> toInOut(final Object record)
	{
		return InterfaceWrapperHelper.isInstanceOf(record, I_M_InOut.class)
				? Optional.of(InterfaceWrapperHelper.create(record, I_M_InOut.class))
				: Optional.empty();
	}

	public static class DocumentLocationAdapter implements IDocumentLocationAdapter
	{
		private final I_M_InOut delegate;

		private DocumentLocationAdapter(@NonNull final I_M_InOut delegate)
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
			return delegate.getC_BPartner_Location_Value_ID();
		}

		@Override
		public void setC_BPartner_Location_Value_ID(final int C_BPartner_Location_Value_ID)
		{
			delegate.setC_BPartner_Location_Value_ID(C_BPartner_Location_Value_ID);
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

	public static class DocumentDeliveryLocationAdapter implements IDocumentDeliveryLocationAdapter
	{
		private final I_M_InOut delegate;

		private DocumentDeliveryLocationAdapter(@NonNull final I_M_InOut delegate)
		{
			this.delegate = delegate;
		}

		@Override
		public int getDropShip_BPartner_ID()
		{
			return delegate.getDropShip_BPartner_ID();
		}

		@Override
		public int getDropShip_Location_ID()
		{
			return delegate.getDropShip_Location_ID();
		}

		@Override
		public void setDropShip_Location_ID(final int DropShip_Location_ID)
		{
			delegate.setDropShip_Location_ID(DropShip_Location_ID);
		}

		@Override
		public int getDropShip_Location_Value_ID()
		{
			return delegate.getDropShip_Location_Value_ID();
		}

		@Override
		public void setDropShip_Location_Value_ID(final int DropShip_Location_Value_ID)
		{
			delegate.setDropShip_Location_Value_ID(DropShip_Location_Value_ID);
		}

		@Override
		public int getDropShip_User_ID()
		{
			return delegate.getDropShip_User_ID();
		}

		@Override
		public int getM_Warehouse_ID()
		{
			return delegate.getM_Warehouse_ID();
		}

		@Override
		public boolean isDropShip()
		{
			return delegate.isDropShip();
		}

		@Override
		public String getDeliveryToAddress()
		{
			return delegate.getDeliveryToAddress();
		}

		@Override
		public void setDeliveryToAddress(final String address)
		{
			delegate.setDeliveryToAddress(address);
		}
	}

}

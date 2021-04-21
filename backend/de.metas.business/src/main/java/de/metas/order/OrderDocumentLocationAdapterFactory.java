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

import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.document.location.adapter.DocumentLocationAdapterFactory;
import de.metas.document.location.adapter.IDocumentBillLocationAdapter;
import de.metas.document.location.adapter.IDocumentDeliveryLocationAdapter;
import de.metas.document.location.adapter.IDocumentHandOverLocationAdapter;
import de.metas.document.location.adapter.IDocumentLocationAdapter;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Optional;

@Component
public class OrderDocumentLocationAdapterFactory implements DocumentLocationAdapterFactory
{
	@Override
	public Optional<IDocumentLocationAdapter> getDocumentLocationAdapterIfHandled(final Object record)
	{
		return toOrder(record).map(OrderDocumentLocationAdapterFactory::locationAdapter);
	}

	@Override
	public Optional<IDocumentBillLocationAdapter> getDocumentBillLocationAdapterIfHandled(final Object record)
	{
		return toOrder(record).map(OrderDocumentLocationAdapterFactory::billLocationAdapter);
	}

	@Override
	public Optional<IDocumentDeliveryLocationAdapter> getDocumentDeliveryLocationAdapter(final Object record)
	{
		return toOrder(record).map(OrderDocumentLocationAdapterFactory::deliveryLocationAdapter);
	}

	@Override
	public Optional<IDocumentHandOverLocationAdapter> getDocumentHandOverLocationAdapter(final Object record)
	{
		return toOrder(record).map(OrderDocumentLocationAdapterFactory::handOverLocationAdapter);
	}

	private static Optional<I_C_Order> toOrder(final Object record)
	{
		return InterfaceWrapperHelper.isInstanceOf(record, I_C_Order.class)
				? Optional.of(InterfaceWrapperHelper.create(record, I_C_Order.class))
				: Optional.empty();
	}

	public static OrderDocumentLocationAdapter locationAdapter(@NonNull final I_C_Order delegate)
	{
		return new OrderDocumentLocationAdapter(delegate);
	}

	public static OrderDocumentBillLocationAdapter billLocationAdapter(@NonNull final I_C_Order delegate)
	{
		return new OrderDocumentBillLocationAdapter(delegate);
	}

	public static OrderDocumentDeliveryLocationAdapter deliveryLocationAdapter(@NonNull final I_C_Order delegate)
	{
		return new OrderDocumentDeliveryLocationAdapter(delegate);
	}

	public static OrderDocumentHandOverLocationAdapter handOverLocationAdapter(@NonNull final I_C_Order delegate)
	{
		return new OrderDocumentHandOverLocationAdapter(delegate);
	}

	public static class OrderDocumentLocationAdapter implements IDocumentLocationAdapter
	{
		private final I_C_Order delegate;

		private OrderDocumentLocationAdapter(@NonNull final I_C_Order delegate)
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

	public static class OrderDocumentBillLocationAdapter implements IDocumentBillLocationAdapter
	{
		private final I_C_Order delegate;

		private OrderDocumentBillLocationAdapter(@NonNull final I_C_Order delegate)
		{
			this.delegate = delegate;
		}

		@Override
		public int getBill_BPartner_ID()
		{
			return delegate.getBill_BPartner_ID();
		}

		@Override
		public int getBill_Location_ID()
		{
			return delegate.getBill_Location_ID();
		}

		@Override
		public void setBill_Location_ID(final int Bill_Location_ID)
		{
			delegate.setBill_Location_ID(Bill_Location_ID);
		}

		@Override
		public int getBill_Location_Value_ID()
		{
			return delegate.getBill_Location_Value_ID();
		}

		@Override
		public void setBill_Location_Value_ID(final int Bill_Location_Value_ID)
		{
			delegate.setBill_Location_Value_ID(Bill_Location_Value_ID);
		}

		@Override
		public int getBill_User_ID()
		{
			return delegate.getBill_User_ID();
		}

		@Override
		public String getBillToAddress()
		{
			return delegate.getBillToAddress();
		}

		@Override
		public void setBillToAddress(final String address)
		{
			delegate.setBillToAddress(address);
		}
	}

	public static class OrderDocumentDeliveryLocationAdapter implements IDocumentDeliveryLocationAdapter
	{
		private final I_C_Order delegate;

		private OrderDocumentDeliveryLocationAdapter(@NonNull final I_C_Order delegate)
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

	public static class OrderDocumentHandOverLocationAdapter implements IDocumentHandOverLocationAdapter
	{
		private final I_C_Order delegate;

		private OrderDocumentHandOverLocationAdapter(@NonNull final I_C_Order delegate)
		{
			this.delegate = delegate;
		}

		@Override
		public boolean isUseHandOver_Location()
		{
			return delegate.isUseHandOver_Location();
		}

		@Override
		public int getHandOver_Partner_ID()
		{
			return delegate.getHandOver_Partner_ID();
		}

		@Override
		public int getHandOver_Location_ID()
		{
			return delegate.getHandOver_Location_ID();
		}

		@Override
		public void setHandOver_Location_ID(final int HandOver_Location_ID)
		{
			delegate.setHandOver_Location_ID(HandOver_Location_ID);
		}

		@Override
		public int getHandOver_Location_Value_ID()
		{
			return delegate.getHandOver_Location_Value_ID();
		}

		@Override
		public void setHandOver_Location_Value_ID(final int HandOver_Location_Value_ID)
		{
			delegate.setHandOver_Location_Value_ID(HandOver_Location_Value_ID);
		}

		@Override
		public int getHandOver_User_ID()
		{
			return delegate.getHandOver_User_ID();
		}

		@Override
		public String getHandOverAddress()
		{
			return delegate.getHandOverAddress();
		}

		@Override
		public void setHandOverAddress(final String address)
		{
			delegate.setHandOverAddress(address);
		}
	}
}

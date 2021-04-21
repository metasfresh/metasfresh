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

package de.metas.order.location.adapter;

import de.metas.document.location.adapter.IDocumentHandOverLocationAdapter;
import lombok.NonNull;
import org.compiere.model.I_C_Order;

public class OrderHandOverLocationAdapter implements IDocumentHandOverLocationAdapter
{
	private final I_C_Order delegate;

	OrderHandOverLocationAdapter(@NonNull final I_C_Order delegate)
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

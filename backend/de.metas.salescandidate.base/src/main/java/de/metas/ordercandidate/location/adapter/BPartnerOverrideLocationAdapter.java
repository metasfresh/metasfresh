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

import de.metas.document.location.adapter.IDocumentLocationAdapter;
import de.metas.ordercandidate.model.I_C_OLCand;
import lombok.NonNull;

public class BPartnerOverrideLocationAdapter implements IDocumentLocationAdapter
{
	private final I_C_OLCand delegate;
	private String address;

	BPartnerOverrideLocationAdapter(@NonNull final I_C_OLCand delegate)
	{
		this.delegate = delegate;
	}

	@Override
	public int getC_BPartner_ID()
	{
		return delegate.getC_BPartner_Override_ID();
	}

	@Override
	public int getC_BPartner_Location_ID()
	{
		return delegate.getC_BP_Location_Override_ID();
	}

	@Override
	public int getC_BPartner_Location_Value_ID()
	{
		return delegate.getC_BP_Location_Override_Value_ID();
	}

	@Override
	public void setC_BPartner_Location_Value_ID(final int C_BPartner_Location_Value_ID)
	{
		delegate.setC_BP_Location_Override_Value_ID(C_BPartner_Location_Value_ID);
	}

	@Override
	public int getAD_User_ID()
	{
		return -1;
	}

	@Override
	public String getBPartnerAddress()
	{
		return address;
	}

	@Override
	public void setBPartnerAddress(String address)
	{
		this.address = address;
	}
}

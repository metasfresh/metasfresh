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

import de.metas.document.location.adapter.IDocumentBillLocationAdapter;
import de.metas.ordercandidate.model.I_C_OLCand;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class DocumentBillLocationAdapter implements IDocumentBillLocationAdapter
{
	private final I_C_OLCand delegate;

	@Getter
	@Setter
	private String billToAddress;

	DocumentBillLocationAdapter(@NonNull final I_C_OLCand delegate)
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
}

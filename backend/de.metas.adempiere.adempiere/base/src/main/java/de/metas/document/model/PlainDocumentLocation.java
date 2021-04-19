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

package de.metas.document.model;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.model.IDocumentLocation;
import lombok.Builder;
import lombok.Data;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

/**
 * Plain implementation of {@link IDocumentLocation} which will load dependent objects (bpartner, location, contact) on demand, using given context and trxName.
 *
 * @author tsa
 */
@Data
public final class PlainDocumentLocation implements IDocumentLocation
{
	private final BPartnerId bpartnerId;
	private final BPartnerLocationId bpartnerLocationId;
	private final BPartnerContactId contactId;

	private String bpartnerAddress;

	@Builder
	private PlainDocumentLocation(
			@Nullable final BPartnerId bpartnerId,
			@Nullable final BPartnerLocationId bpartnerLocationId,
			@Nullable final BPartnerContactId contactId,
			@Nullable final String bpartnerAddress)
	{
		if (bpartnerLocationId != null && !bpartnerLocationId.getBpartnerId().equals(bpartnerId))
		{
			throw new AdempiereException("" + bpartnerId + " and " + bpartnerLocationId + " shall match");
		}
		if (contactId != null && !contactId.getBpartnerId().equals(bpartnerId))
		{
			throw new AdempiereException("" + bpartnerId + " and " + contactId + " shall match");
		}

		this.bpartnerId = bpartnerId;
		this.bpartnerLocationId = bpartnerLocationId;
		this.contactId = contactId;
		this.bpartnerAddress = bpartnerAddress;
	}

	@Override
	public int getC_BPartner_ID()
	{
		return BPartnerId.toRepoId(bpartnerId);
	}

	@Override
	public int getC_BPartner_Location_ID()
	{
		return BPartnerLocationId.toRepoId(bpartnerLocationId);
	}

	@Override
	public int getAD_User_ID()
	{
		return BPartnerContactId.toRepoId(contactId);
	}

	@Override
	public String getBPartnerAddress()
	{
		return bpartnerAddress;
	}

	@Override
	public void setBPartnerAddress(@Nullable final String bpartnerAddress)
	{
		this.bpartnerAddress = bpartnerAddress;
	}

}

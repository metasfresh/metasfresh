package de.metas.document.model.impl;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.model.IDocumentLocation;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.ToString;

/**
 * Plain implementation of {@link IDocumentLocation} which will load dependent objects (bpartner, location, contact) on demand, using given context and trxName.
 *
 * @author tsa
 *
 */
@ToString
public final class PlainDocumentLocation implements IDocumentLocation
{
	private final BPartnerId bpartnerId;
	private final BPartnerLocationId bpartnerLocationId;
	private final UserId contactId;

	private String bpartnerAddress;

	@Builder
	private PlainDocumentLocation(
			final BPartnerId bpartnerId,
			final BPartnerLocationId bpartnerLocationId,
			final UserId contactId)
	{
		if (bpartnerLocationId != null && !bpartnerLocationId.getBpartnerId().equals(bpartnerId))
		{
			throw new AdempiereException(String.valueOf(bpartnerId) + " and " + bpartnerLocationId + " shall match");
		}

		this.bpartnerId = bpartnerId;
		this.bpartnerLocationId = bpartnerLocationId;
		this.contactId = contactId;
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
		return UserId.toRepoId(contactId);
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

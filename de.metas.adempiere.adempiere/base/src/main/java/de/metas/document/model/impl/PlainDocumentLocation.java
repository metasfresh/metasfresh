package de.metas.document.model.impl;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.model.IDocumentLocation;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
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
	private I_C_BPartner bpartner; // lazy

	private final BPartnerLocationId bpartnerLocationId;
	private I_C_BPartner_Location bpartnerLocation; // lazy

	private final UserId contactId;
	private I_AD_User contact; // lazy

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
	public I_C_BPartner getC_BPartner()
	{
		if (bpartnerId == null)
		{
			return null;
		}

		if (bpartner == null)
		{
			bpartner = Services.get(IBPartnerDAO.class).getById(bpartnerId);
		}
		return bpartner;
	}

	@Override
	public int getC_BPartner_Location_ID()
	{
		return BPartnerLocationId.toRepoId(bpartnerLocationId);
	}

	@Override
	public I_C_BPartner_Location getC_BPartner_Location()
	{
		if (bpartnerLocationId == null)
		{
			return null;
		}

		if (bpartnerLocation == null)
		{
			bpartnerLocation = Services.get(IBPartnerDAO.class).getBPartnerLocationById(bpartnerLocationId);
		}
		return bpartnerLocation;
	}

	@Override
	public int getAD_User_ID()
	{
		return UserId.toRepoId(contactId);
	}

	@Override
	public I_AD_User getAD_User()
	{
		if (contactId == null)
		{
			return null;
		}

		if (contact == null)
		{
			contact = Services.get(IUserDAO.class).getById(contactId);
		}
		return contact;
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

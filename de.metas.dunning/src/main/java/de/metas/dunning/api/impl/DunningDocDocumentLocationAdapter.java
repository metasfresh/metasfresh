package de.metas.dunning.api.impl;

import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;

import de.metas.document.model.IDocumentLocation;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.util.Check;

public class DunningDocDocumentLocationAdapter implements IDocumentLocation
{
	private final I_C_DunningDoc delegate;

	public DunningDocDocumentLocationAdapter(I_C_DunningDoc delegate)
	{
		Check.assume(delegate != null, "delegate not null");
		this.delegate = delegate;
	}

	@Override
	public int getC_BPartner_ID()
	{
		return delegate.getC_BPartner_ID();
	}

	@Override
	public I_C_BPartner getC_BPartner()
	{
		return delegate.getC_BPartner();
	}

	@Override
	public int getC_BPartner_Location_ID()
	{
		return delegate.getC_BPartner_Location_ID();
	}

	@Override
	public I_C_BPartner_Location getC_BPartner_Location()
	{
		return delegate.getC_BPartner_Location();
	}

	@Override
	public int getAD_User_ID()
	{
		return delegate.getC_Dunning_Contact_ID();
	}

	@Override
	public I_AD_User getAD_User()
	{
		return delegate.getC_Dunning_Contact();
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

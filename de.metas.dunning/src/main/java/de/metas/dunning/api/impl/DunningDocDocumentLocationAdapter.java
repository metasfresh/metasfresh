package de.metas.dunning.api.impl;

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
	public int getC_BPartner_Location_ID()
	{
		return delegate.getC_BPartner_Location_ID();
	}

	@Override
	public int getAD_User_ID()
	{
		return delegate.getC_Dunning_Contact_ID();
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

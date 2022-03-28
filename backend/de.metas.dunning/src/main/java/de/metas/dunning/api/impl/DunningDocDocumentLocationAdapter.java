package de.metas.dunning.api.impl;

import de.metas.document.location.adapter.IDocumentLocationAdapter;
import de.metas.dunning.model.I_C_DunningDoc;
import lombok.NonNull;
import lombok.ToString;

@ToString
class DunningDocDocumentLocationAdapter implements IDocumentLocationAdapter
{
	private final I_C_DunningDoc delegate;

	public DunningDocDocumentLocationAdapter(@NonNull final I_C_DunningDoc delegate)
	{
		this.delegate = delegate;
	}

	@Override
	public int getC_BPartner_ID()
	{
		return delegate.getC_BPartner_ID();
	}

	@Override
	public void setC_BPartner_ID(final int C_BPartner_ID)
	{
		delegate.setC_BPartner_ID(C_BPartner_ID);
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
		return -1;
	}

	@Override
	public void setC_BPartner_Location_Value_ID(final int C_BPartner_Location_Value_ID)
	{
	}

	@Override
	public int getAD_User_ID()
	{
		return delegate.getC_Dunning_Contact_ID();
	}

	@Override
	public void setAD_User_ID(final int AD_User_ID)
	{
		delegate.setC_Dunning_Contact_ID(AD_User_ID);
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

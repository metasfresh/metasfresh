package de.metas.letters.model;

import de.metas.document.location.adapter.IDocumentLocationAdapter;
import lombok.NonNull;

public class LetterDocumentLocationAdapter implements IDocumentLocationAdapter
{
	private final I_C_Letter delegate;

	public LetterDocumentLocationAdapter(@NonNull final I_C_Letter delegate)
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
		return -1;
	}

	@Override
	public void setC_BPartner_Location_Value_ID(final int C_BPartner_Location_Value_ID)
	{
	}

	@Override
	public int getAD_User_ID()
	{
		return delegate.getC_BP_Contact_ID();
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

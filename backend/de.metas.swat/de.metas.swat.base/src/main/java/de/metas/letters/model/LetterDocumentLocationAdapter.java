package de.metas.letters.model;

import de.metas.document.model.IDocumentLocation;
import lombok.NonNull;

public class LetterDocumentLocationAdapter implements IDocumentLocation
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

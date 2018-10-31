package de.metas.edi.api;

import org.compiere.model.I_C_BPartner;

import de.metas.util.ISingletonService;

public interface IEDIInvoiceCandDAO extends ISingletonService
{

	/**
	 * <li>Update the invoice candidates of the given bPartner by setting their IsEDIRecipient flag with the given value.
	 * 
	 * <li>The update will be performed only for not processed invoice candidates that don't already have this value set correctly.
	 * 
	 * @param bPartner
	 * @param isEDIRecipient
	 */
	void updateEdiRecipientInvoiceCandidates(I_C_BPartner bPartner, boolean isEDIRecipient);

}

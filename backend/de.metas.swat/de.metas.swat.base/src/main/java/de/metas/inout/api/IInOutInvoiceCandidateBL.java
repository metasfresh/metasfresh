package de.metas.inout.api;

import de.metas.inout.model.I_M_InOut;
import de.metas.util.ISingletonService;

public interface IInOutInvoiceCandidateBL extends ISingletonService
{

	/**
	 * Check if the inout is ready for invoicing (it is Active, completed or closed);
	 * 
	 * @param inOut
	 * @return true if the inout can be approved for invoicing
	 */
	boolean isApproveInOutForInvoicing(de.metas.inout.model.I_M_InOut inOut);

	/**
	 * This method sets the flag IsInOutApprovedForInvoicing in the invoice candidates that are linked with the inout given as parameter
	 * It also sets the editable flag ApprovedForInvoicing on true in case the IsInOutApprovedForInvoicing is true.
	 * 
	 * In case the invoice candidates are linked with other inouts that are not yet approved for invoicing, the flag will also not be set in the invoice candidates
	 * 
	 * @param inOut
	 */
	void approveForInvoicingLinkedInvoiceCandidates(I_M_InOut inOut);
}

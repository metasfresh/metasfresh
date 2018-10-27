package de.metas.document;

import org.compiere.model.I_C_DocType;

import de.metas.util.ISingletonService;

public interface IDocTypeBL extends ISingletonService
{
	/**
	 * 	Is this a Quotation (Binding)
	 *	@return true if Quotation
	 */
	boolean isQuotation(I_C_DocType dt);

	/**
	 * 	Is this a Proposal (Not binding)
	 *	@return true if proposal
	 */
	boolean isProposal(I_C_DocType dt);

	/**
	 * 	Is this a Proposal or Quotation
	 *	@return true if proposal or quotation
	 */
	boolean isOffer(I_C_DocType dt);

	boolean isSOTrx(String docBaseType);

	boolean isPrepay(I_C_DocType dt);
}

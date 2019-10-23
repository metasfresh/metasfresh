package de.metas.document;

import org.compiere.model.I_C_DocType;

import de.metas.util.ISingletonService;

public interface IDocTypeBL extends ISingletonService
{
	/**
	 * @return true if it's a sales quotation (Binding)
	 */
	boolean isSalesQuotation(DocTypeId docTypeId);

	/**
	 * @return true if it's a sales quotation (Binding)
	 */
	boolean isSalesQuotation(I_C_DocType dt);

	/**
	 * @return true if it's a sales proposal (Not binding)
	 */
	boolean isSalesProposal(DocTypeId docTypeId);

	/**
	 * @return true if it's a sales proposal (Not binding)
	 */
	boolean isSalesProposal(I_C_DocType dt);

	/**
	 * @return true if it's a sales proposal or quotation
	 */
	boolean isSalesProposalOrQuotation(DocTypeId docTypeId);

	/**
	 * @return true if it's a sales proposal or quotation
	 */
	boolean isSalesProposalOrQuotation(I_C_DocType dt);

	boolean isSOTrx(String docBaseType);

	boolean isPrepay(DocTypeId docTypeId);

	boolean isPrepay(I_C_DocType dt);

}

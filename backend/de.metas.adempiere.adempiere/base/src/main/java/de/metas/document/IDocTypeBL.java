package de.metas.document;

import de.metas.i18n.ITranslatableString;
import de.metas.util.ISingletonService;
import org.compiere.model.I_C_DocType;

public interface IDocTypeBL extends ISingletonService
{
	I_C_DocType getById(DocTypeId docTypeId);

	DocTypeId getDocTypeIdOrNull(DocTypeQuery docTypeQuery);

	ITranslatableString getNameById(DocTypeId docTypeId);

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

	boolean hasRequestType(DocTypeId docTypeId);
}

package de.metas.document;

import com.google.common.collect.ImmutableSet;
import de.metas.document.invoicingpool.DocTypeInvoicingPoolId;
import de.metas.i18n.ITranslatableString;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_C_DocType;

public interface IDocTypeBL extends ISingletonService
{
	@NonNull
	I_C_DocType getById(@NonNull DocTypeId docTypeId);

	@NonNull
	I_C_DocType getByIdInTrx(@NonNull DocTypeId docTypeId);

	DocTypeId getDocTypeIdOrNull(DocTypeQuery docTypeQuery);

	@NonNull
	DocTypeId getDocTypeId(DocTypeQuery docTypeQuery);

	@NonNull
	ImmutableSet<DocTypeId> getDocTypeIdsByInvoicingPoolId(@NonNull DocTypeInvoicingPoolId docTypeInvoicingPoolId);

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
	 * @return true if it's a sales cost estimate
	 */
	boolean isSalesCostEstimate(I_C_DocType dt);

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

	boolean isPrepay(DocTypeId docTypeId);

	boolean isPrepay(I_C_DocType dt);

	boolean hasRequestType(DocTypeId docTypeId);

	boolean isRequisition(DocTypeId docTypeId);

	boolean isMediated(DocTypeId docTypeId);

	boolean isCallOrder(DocTypeId docTypeId);

	boolean isInternalVendorInvoice(DocTypeId docTypeId);

	boolean isProFormaSO(DocTypeId docTypeId);

	boolean isDownPayment(DocTypeId docTypeId);

	boolean isFinalInvoiceOrFinalCreditMemo(@NonNull DocTypeId docTypeId);

	boolean isDefinitiveInvoiceOrDefinitiveCreditMemo(@NonNull DocTypeId docTypeId);

	void save(I_C_DocType dt);

	boolean isModularManufacturingOrder(@NonNull DocTypeId docTypeId);
}

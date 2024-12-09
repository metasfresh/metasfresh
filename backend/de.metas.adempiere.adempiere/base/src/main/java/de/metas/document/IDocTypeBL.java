package de.metas.document;

<<<<<<< HEAD
import de.metas.i18n.ITranslatableString;
import de.metas.util.ISingletonService;
=======
import com.google.common.collect.ImmutableSet;
import de.metas.document.invoicingpool.DocTypeInvoicingPoolId;
import de.metas.i18n.ITranslatableString;
import de.metas.util.ISingletonService;
import lombok.NonNull;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import org.compiere.model.I_C_DocType;

public interface IDocTypeBL extends ISingletonService
{
<<<<<<< HEAD
	I_C_DocType getById(DocTypeId docTypeId);

	DocTypeId getDocTypeIdOrNull(DocTypeQuery docTypeQuery);

=======
	@NonNull
	I_C_DocType getById(@NonNull DocTypeId docTypeId);

	@NonNull
	I_C_DocType getByIdInTrx(@NonNull DocTypeId docTypeId);


	DocTypeId getDocTypeIdOrNull(DocTypeQuery docTypeQuery);

	@NonNull
	ImmutableSet<DocTypeId> getDocTypeIdsByInvoicingPoolId(@NonNull DocTypeInvoicingPoolId docTypeInvoicingPoolId);

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

	boolean isSOTrx(String docBaseType);

	boolean isPrepay(DocTypeId docTypeId);

	boolean isPrepay(I_C_DocType dt);

	boolean hasRequestType(DocTypeId docTypeId);

	boolean isRequisition(DocTypeId docTypeId);

	boolean isMediated(DocTypeId docTypeId);

	boolean isCallOrder(DocTypeId docTypeId);
<<<<<<< HEAD
=======

	void save(I_C_DocType dt);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}

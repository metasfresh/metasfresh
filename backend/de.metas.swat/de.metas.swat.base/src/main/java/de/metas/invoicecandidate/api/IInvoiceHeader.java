package de.metas.invoicecandidate.api;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.document.DocTypeId;
import de.metas.document.dimension.Dimension;
import de.metas.document.invoicingpool.DocTypeInvoicingPoolId;
import de.metas.forex.ForexContractRef;
import de.metas.impex.InputDataSourceId;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import de.metas.sectionCode.SectionCodeId;
import de.metas.user.UserId;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IInvoiceHeader
{
	InvoiceDocBaseType getDocBaseType();

	String getPOReference();

	String getEMail();

	LocalDate getDateInvoiced();

	/**
	 * Task 08437
	 */
	LocalDate getDateAcct();

	/**
	 * Note: when creating an C_Invoice, this value take precedence over the org of the order specified by {@link #getC_Order_ID()} (if >0).
	 */
	OrgId getOrgId();

	int getC_Order_ID();

	int getM_PriceList_ID();

	BPartnerInfo getBillTo();

	BPartnerId getSalesPartnerId();

	UserId getSalesRepId();

	// 03805 : add getter for C_Currency_ID
	CurrencyId getCurrencyId();

	/**
	 * Returns a mapping from invoice candidates to the invoice line predecessor(s) into which the respective invoice candidate has been aggregated.
	 */
	List<IInvoiceCandAggregate> getLines();

	List<I_C_Invoice_Candidate> getAllInvoiceCandidates();

	// 04258: add header and footer
	String getDescription();

	String getDescriptionBottom();

	boolean isSOTrx();

	boolean isTakeDocTypeFromPool();

	int getM_InOut_ID();

	Optional<DocTypeId> getDocTypeInvoiceId();

	void setDocTypeInvoiceId(DocTypeId docTypeInvoiceId);

	@NonNull
	Optional<DocTypeInvoicingPoolId> getDocTypeInvoicingPoolId();

	void setDocTypeInvoicingPoolId(@Nullable DocTypeInvoicingPoolId docTypeInvoicingPoolId);

	boolean isTaxIncluded();

	@Nullable
	PaymentTermId getPaymentTermId();

	String getExternalId();

	int getC_Async_Batch_ID();

	int getC_Incoterms_ID();

	String getIncotermLocation();

	String getPaymentRule();

	@Nullable
	InputDataSourceId getAD_InputDataSource_ID();

	@Nullable
	SectionCodeId getM_SectionCode_ID();

	@Nullable
	ProjectId getProjectId();

	@Nullable
	ActivityId getActivityId();

	@Nullable
	ForexContractRef getForexContractRef();

	@Nullable
	String getInvoiceAdditionalText();

	boolean isNotShowOriginCountry();

	@Nullable
	LocalDate getOverrideDueDate();

	void setC_PaymentInstruction_ID(int C_PaymentInstruction_ID);

	int getC_PaymentInstruction_ID();

	@NonNull
	Dimension getDimension();

	@Nullable
	CountryId getC_Tax_Departure_Country_ID();
}

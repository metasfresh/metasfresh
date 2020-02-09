package de.metas.invoicecandidate.api;

import java.time.LocalDate;
import java.util.List;

import org.compiere.model.I_C_DocType;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;

/**
 * Invoice predecessor returned by {@link IAggregationBL#aggregate()}.
 *
 * @author tsa
 *
 */
public interface IInvoiceHeader
{
	String getDocBaseType();

	String getPOReference();

	LocalDate getDateInvoiced();

	/**
	 * @task 08437
	 */
	LocalDate getDateAcct();

	/**
	 * Note: when creating an C_Invoice, this value take precedence over the org of the order specified by {@link #getC_Order_ID()} (if >0).
	 */
	OrgId getOrgId();

	int getC_Order_ID();

	int getM_PriceList_ID();

	int getBill_Location_ID();

	int getBill_BPartner_ID();

	int getBill_User_ID();

	// 03805 : add getter for C_Currency_ID
	CurrencyId getCurrencyId();

	/**
	 * Returns a mapping from invoice candidates to the invoice line predecessor(s) into which the respective invoice candidate has been aggregated.
	 *
	 * @return
	 */
	List<IInvoiceCandAggregate> getLines();

	List<I_C_Invoice_Candidate> getAllInvoiceCandidates();

	// 04258: add header and footer
	String getDescription();

	String getDescriptionBottom();

	boolean isSOTrx();

	int getM_InOut_ID();

	I_C_DocType getC_DocTypeInvoice();

	boolean isTaxIncluded();

	int getC_PaymentTerm_ID();
}

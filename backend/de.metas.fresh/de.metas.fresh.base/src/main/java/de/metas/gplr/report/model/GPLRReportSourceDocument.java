package de.metas.gplr.report.model;

import de.metas.invoice.InvoiceId;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class GPLRReportSourceDocument
{
	@NonNull InvoiceId invoiceId;
	@NonNull OrgId orgId;

	// 001 - Department - Display department “Name” from department master for the section code as of document date
	@Nullable String departmentName;

	// 002 - Section - Section code and section name
	@Nullable GPLRSectionCodeRenderedString sectionCode;

	// 003 - Document No - Metasfresh invoice number
	@NonNull String documentNo;

	// 004 - Document Type
	@NonNull String invoiceDocTypeName;

	// 005 - Created By - Invoice creator user name (family name, first name)
	@NonNull String createdByName;

	// 006 - Document Date - Invoice date
	@NonNull LocalDateAndOrgId documentDate;

	// 007 - Date Created
	@NonNull LocalDateAndOrgId created;

	// 008 - Commodity (Product Hierarchy) - Product hierarchy from material master
	// e.g. 327 55
	// TODO need to clarify what it is
	@Nullable String product;

	// 009 - Terms of Payment - (1) Terms of payment code, (2) payment term description, (3) payment method text
	// e.g. B075 75 days after BL
	@NonNull GPLRPaymentTermRenderedString paymentTerm;

	// 010 - Due Date - Due Date from invoice
	@Nullable LocalDateAndOrgId dueDate;

	// 011 - Currency & Rate FEC No. - Currency & Rate used in invoice, All FECs related to order
	// e.g.USD / 1.0123
	@NonNull GPLRCurrencyInfo currencyInfo;
}

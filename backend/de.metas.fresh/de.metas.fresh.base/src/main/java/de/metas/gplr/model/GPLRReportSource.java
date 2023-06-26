package de.metas.gplr.model;

import de.metas.organization.LocalDateAndOrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class GPLRReportSource
{
	// 001 - Department - Display department “Name” from department master for the section code as of document date
	@Nullable String departmentName;

	// 002 - Section - Section code and section name
	@Nullable GPLRSectionCode sectionCode;

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
	@NonNull String product;

	// 009 - Terms of Payment - (1) Terms of payment code, (2) payment term description, (3) payment method text
	// e.g. B075 75 days after BL
	@NonNull GPLRPaymentTermName paymentTerm;

	// 010 - Due Date - Due Date from invoice
	@Nullable LocalDateAndOrgId dueDate;

	// 011 - Currency & Rate FEC No. - Currency & Rate used in invoice, All FECs related to order
	// e.g.USD / 1.0123
	@Nullable GPLRForexInfo forexInfo;
}

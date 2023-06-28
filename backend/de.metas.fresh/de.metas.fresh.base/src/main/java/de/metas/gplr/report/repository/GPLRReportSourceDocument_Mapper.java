package de.metas.gplr.report.repository;

import com.google.common.annotations.VisibleForTesting;
import de.metas.currency.CurrencyCode;
import de.metas.gplr.report.model.GPLRCurrencyInfo;
import de.metas.gplr.report.model.GPLRPaymentTermRenderedString;
import de.metas.gplr.report.model.GPLRReportSourceDocument;
import de.metas.gplr.report.model.GPLRSectionCodeRenderedString;
import de.metas.invoice.InvoiceId;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.model.I_GPLR_Report;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.function.Function;

@UtilityClass
@VisibleForTesting
class GPLRReportSourceDocument_Mapper
{
	static void updateRecord(
			@NonNull final I_GPLR_Report record,
			@NonNull GPLRReportSourceDocument from,
			@NonNull final Function<OrgId, ZoneId> orgId2timeZoneMapper)
	{
		record.setAD_Org_ID(from.getOrgId().getRepoId());
		record.setC_Invoice_ID(from.getInvoiceId().getRepoId());
		record.setDepartmentName(from.getDepartmentName());
		record.setSectionCodeAndName(from.getSectionCode() != null ? from.getSectionCode().toRenderedString() : null);
		record.setInvoiceDocumentNo(from.getDocumentNo());
		record.setDocTypeName(from.getInvoiceDocTypeName());
		record.setCreatedByName(from.getCreatedByName());
		record.setDateDoc(from.getDocumentDate().toTimestamp(orgId2timeZoneMapper));
		record.setDocCreateDate(from.getCreated().toTimestamp(orgId2timeZoneMapper));
		record.setSAP_ProductHierarchy(from.getSapProductHierarchy());
		record.setPaymentTermInfo(from.getPaymentTerm().toRenderedString());
		record.setDueDate(from.getDueDate() != null ? from.getDueDate().toTimestamp(orgId2timeZoneMapper) : null);
		updateRecord_CurrencyInfo(record, from.getCurrencyInfo());
	}

	private static void updateRecord_CurrencyInfo(
			final @NonNull I_GPLR_Report record,
			final @NonNull GPLRCurrencyInfo currencyInfo)
	{
		record.setCurrencyRate(currencyInfo.getCurrencyRate());
		record.setForeignCurrencyCode(currencyInfo.getForeignCurrency().toThreeLetterCode());
		record.setFEC_DocumentNo(currencyInfo.getFecDocumentNo());
	}

	@VisibleForTesting
	static GPLRReportSourceDocument fromRecord(
			@NonNull final I_GPLR_Report record,
			@NonNull final Function<OrgId, ZoneId> orgId2timeZoneMapper)
	{
		final OrgId orgId = OrgId.ofRepoId(record.getAD_Org_ID());
		final Function<Timestamp, LocalDateAndOrgId> toLocalDateAndOrgId = timestamp -> LocalDateAndOrgId.ofTimestamp(timestamp, orgId, orgId2timeZoneMapper);

		return GPLRReportSourceDocument.builder()
				.invoiceId(InvoiceId.ofRepoId(record.getC_Invoice_ID()))
				.orgId(orgId)
				.departmentName(record.getDepartmentName())
				.sectionCode(GPLRSectionCodeRenderedString.ofNullableRenderedString(record.getSectionCodeAndName()))
				.documentNo(record.getInvoiceDocumentNo())
				.invoiceDocTypeName(record.getDocTypeName())
				.createdByName(record.getCreatedByName())
				.documentDate(toLocalDateAndOrgId.apply(record.getDateDoc()))
				.created(toLocalDateAndOrgId.apply(record.getDocCreateDate()))
				.sapProductHierarchy(StringUtils.trimBlankToNull(record.getSAP_ProductHierarchy()))
				.paymentTerm(GPLRPaymentTermRenderedString.ofRenderedString(record.getPaymentTermInfo()))
				.dueDate(toLocalDateAndOrgId.apply(record.getDueDate()))
				.currencyInfo(extractCurrencyInfo(record))
				.build();
	}

	private static GPLRCurrencyInfo extractCurrencyInfo(final @NonNull I_GPLR_Report record)
	{
		return GPLRCurrencyInfo.builder()
				.currencyRate(record.getCurrencyRate())
				.foreignCurrency(CurrencyCode.ofThreeLetterCode(record.getForeignCurrencyCode()))
				.fecDocumentNo(record.getFEC_DocumentNo())
				.build();
	}
}

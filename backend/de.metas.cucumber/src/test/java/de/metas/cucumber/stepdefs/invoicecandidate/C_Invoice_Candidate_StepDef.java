/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.cucumber.stepdefs.invoicecandidate;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.AD_User_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.docType.C_DocType_StepDefData;
import de.metas.cucumber.stepdefs.iinvoicecandidate.I_Invoice_Candidate_StepDefData;
import de.metas.cucumber.stepdefs.org.AD_Org_StepDefData;
import de.metas.cucumber.stepdefs.uom.C_UOM_StepDefData;
import de.metas.invoice.InvoiceService;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_I_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.logging.LogbackLoggable;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_InvoiceRule_Override;
import static de.metas.invoicecandidate.model.I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class C_Invoice_Candidate_StepDef
{
	private final static transient Logger logger = LogManager.getLogger(C_Invoice_Candidate_StepDef.class);

	private final InvoiceService invoiceService = SpringContextHolder.instance.getBean(InvoiceService.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final C_Invoice_Candidate_StepDefData invoiceCandTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;
	private final M_Product_StepDefData productTable;
	private final C_Order_StepDefData orderTable;
	private final C_OrderLine_StepDefData orderLineTable;
	private final I_Invoice_Candidate_StepDefData iInvoiceCandidatetable;
	private final AD_User_StepDefData contactTable;
	private final C_DocType_StepDefData docTypeTable;
	private final C_UOM_StepDefData uomTable;
	private final AD_Org_StepDefData orgTable;

	public C_Invoice_Candidate_StepDef(
			@NonNull final C_Invoice_Candidate_StepDefData invoiceCandTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final C_BPartner_Location_StepDefData bPartnerLocationTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable,
			@NonNull final I_Invoice_Candidate_StepDefData iInvoiceCandidateTable,
			@NonNull final AD_User_StepDefData contactTable,
			@NonNull final C_DocType_StepDefData docTypeTable,
			@NonNull final C_UOM_StepDefData uomTable,
			@NonNull final AD_Org_StepDefData orgTable)
	{
		this.invoiceCandTable = invoiceCandTable;
		this.bPartnerTable = bPartnerTable;
		this.bPartnerLocationTable = bPartnerLocationTable;
		this.productTable = productTable;
		this.orderTable = orderTable;
		this.orderLineTable = orderLineTable;
		this.iInvoiceCandidatetable = iInvoiceCandidateTable;
		this.contactTable = contactTable;
		this.docTypeTable = docTypeTable;
		this.uomTable = uomTable;
		this.orgTable = orgTable;
	}

	@And("update C_Invoice_Candidate:")
	public void update_C_Invoice_Candidate(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String invoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Invoice_Candidate invoiceCandidate = invoiceCandTable.get(invoiceCandIdentifier);

			final BigDecimal qtyToInvoiceOverride = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice_Override);
			if (qtyToInvoiceOverride != null)
			{
				invoiceCandidate.setQtyToInvoice_Override(qtyToInvoiceOverride);
			}

			final String invoiceRuleOverride = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_InvoiceRule_Override);
			if (Check.isNotBlank(invoiceRuleOverride))
			{
				invoiceCandidate.setInvoiceRule_Override(invoiceRuleOverride);
			}

			InterfaceWrapperHelper.saveRecord(invoiceCandidate);
			invoiceCandTable.putOrReplace(invoiceCandIdentifier, invoiceCandidate);
		}
	}

	@And("process invoice candidates")
	public void process_invoice_cand(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);

		for (final Map<String, String> row : tableRows)
		{
			try (final IAutoCloseable ignore = Loggables.temporarySetLoggable(new LogbackLoggable(logger, Level.INFO)))
			{
				final String invoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
				final I_C_Invoice_Candidate invoiceCandidate = invoiceCandTable.get(invoiceCandIdentifier);

				final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(invoiceCandidate.getC_Invoice_Candidate_ID());
				invoiceService.generateInvoicesFromInvoiceCandidateIds(ImmutableSet.of(invoiceCandidateId));
			}
		}
	}

	@And("validate invoice candidates by record reference:")
	public void locate_invoice_candidate_by_record_id(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			loadAndValidateInvoiceCandidateByRecordId(row);
		}
	}

	@And("^after not more than (.*)s C_Invoice_Candidate matches:$")
	public void wait_for_candidate_to_match(final int maxSecondsToWait, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final I_C_Invoice_Candidate invoiceCandidate = StepDefUtil.tryAndWaitForItem(maxSecondsToWait, 1000, () -> getInvoiceCandidateIfMatches(row));

			assertThat(invoiceCandidate).isNotNull();
		}
	}

	private void validate_C_invoice_Candidate(@NonNull final I_C_Invoice_Candidate invoiceCandidate, @NonNull final Map<String, String> row)
	{
		final BigDecimal qtyToInvoice = DataTableUtil.extractBigDecimalOrNullForColumnName(row, I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice);
		if (qtyToInvoice != null)
		{
			assertThat(invoiceCandidate.getQtyToInvoice()).isEqualTo(qtyToInvoice);
		}

		final BigDecimal qtyOrdered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyOrdered);
		if (qtyOrdered != null)
		{
			assertThat(invoiceCandidate.getQtyOrdered()).isEqualTo(qtyOrdered);
		}

		final BigDecimal qtyDelivered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyDelivered);
		if (qtyDelivered != null)
		{
			assertThat(invoiceCandidate.getQtyDelivered()).isEqualTo(qtyDelivered);
		}

		final BigDecimal qtyToInvoiceOverride = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice_Override);
		if (qtyToInvoiceOverride != null)
		{
			assertThat(invoiceCandidate.getQtyToInvoice_Override()).isEqualTo(qtyToInvoiceOverride);
		}

		final String orderIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(orderIdentifier))
		{
			final I_C_Order order = orderTable.get(orderIdentifier);
			assertThat(invoiceCandidate.getC_Order_ID()).isEqualTo(order.getC_Order_ID());
		}

		final String orderLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(orderLineIdentifier))
		{
			final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);
			assertThat(invoiceCandidate.getC_OrderLine_ID()).isEqualTo(orderLine.getC_OrderLine_ID());
		}

		final String paymentRule = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Invoice_Candidate.COLUMNNAME_PaymentRule);

		if (Check.isNotBlank(paymentRule))
		{
			assertThat(invoiceCandidate.getPaymentRule()).isEqualTo(paymentRule);
		}

		final String billBPartnerContactIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_Bill_User_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(billBPartnerContactIdentifier))
		{
			final I_AD_User contact = contactTable.get(billBPartnerContactIdentifier);
			assertThat(contact).isNotNull();
			assertThat(invoiceCandidate.getBill_User_ID()).isEqualTo(contact.getAD_User_ID());
		}

		final String productIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(productIdentifier))
		{
			final I_M_Product product = productTable.get(productIdentifier);
			assertThat(product).isNotNull();
			assertThat(invoiceCandidate.getM_Product_ID()).isEqualTo(product.getM_Product_ID());
		}

		final ZoneId zoneId = orgDAO.getTimeZone(OrgId.ofRepoId(invoiceCandidate.getAD_Org_ID()));
		final Timestamp dateOrdered = DataTableUtil.extractDateTimestampForColumnNameOrNull(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_DateOrdered);
		if (dateOrdered != null)
		{
			assertThat(TimeUtil.asLocalDate(invoiceCandidate.getDateOrdered(), zoneId)).isEqualTo(TimeUtil.asLocalDate(dateOrdered, zoneId));
		}

		final Timestamp presetDateInvoiced = DataTableUtil.extractDateTimestampForColumnNameOrNull(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_PresetDateInvoiced);
		if (presetDateInvoiced != null)
		{
			assertThat(TimeUtil.asLocalDate(invoiceCandidate.getPresetDateInvoiced(), zoneId)).isEqualTo(TimeUtil.asLocalDate(presetDateInvoiced, zoneId));
		}

		final String poReference = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_POReference);
		if (Check.isNotBlank(poReference))
		{
			assertThat(invoiceCandidate.getPOReference()).isEqualTo(poReference);
		}

		final String description = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_Description);
		if (Check.isNotBlank(description))
		{
			assertThat(invoiceCandidate.getDescription()).isEqualTo(description);
		}

		final String docTypeIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_C_DocType_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(docTypeIdentifier))
		{
			final I_C_DocType docType = docTypeTable.get(docTypeIdentifier);
			assertThat(docType).isNotNull();
			assertThat(invoiceCandidate.getC_DocTypeInvoice_ID()).isEqualTo(docType.getC_DocType_ID());
		}

		final String uomIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_C_UOM_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(uomIdentifier))
		{
			final I_C_UOM uom = uomTable.get(uomIdentifier);
			assertThat(uom).isNotNull();
			assertThat(invoiceCandidate.getC_UOM_ID()).isEqualTo(uom.getC_UOM_ID());
		}
	}

	private void validate_C_Invoice_Candidate_mandatory_fields(@NonNull final I_C_Invoice_Candidate invoiceCandidate, @NonNull final Map<String, String> row)
	{
		final String billBPartnerIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner billBPartner = bPartnerTable.get(billBPartnerIdentifier);
		assertThat(billBPartner).isNotNull();
		assertThat(invoiceCandidate.getBill_BPartner_ID()).isEqualTo(billBPartner.getC_BPartner_ID());

		final String billBPartnerLocationIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_Bill_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner_Location bPartnerLocation = bPartnerLocationTable.get(billBPartnerLocationIdentifier);
		assertThat(bPartnerLocation).isNotNull();
		assertThat(invoiceCandidate.getBill_Location_ID()).isEqualTo(bPartnerLocation.getC_BPartner_Location_ID());

		final String orgIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_AD_Org_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_AD_Org org = orgTable.get(orgIdentifier);
		assertThat(org).isNotNull();
		assertThat(invoiceCandidate.getAD_Org_ID()).isEqualTo(org.getAD_Org_ID());

		final String invoiceRule = DataTableUtil.extractStringOrNullForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_InvoiceRule);
		assertThat(invoiceCandidate.getInvoiceRule()).isEqualTo(invoiceRule);

		final boolean isSOTrx = DataTableUtil.extractBooleanForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_IsSOTrx);
		assertThat(invoiceCandidate.isSOTrx()).isEqualTo(isSOTrx);
	}

	private void loadAndValidateInvoiceCandidateByRecordId(@NonNull final Map<String, String> row)
	{
		final String tableName = DataTableUtil.extractStringForColumnName(row, "TableName");

		if (tableName.equals(I_I_Invoice_Candidate.Table_Name))
		{
			final String iInvoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_I_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_I_Invoice_Candidate iIInvoiceCandidate = iInvoiceCandidatetable.get(iInvoiceCandIdentifier);
			assertThat(iIInvoiceCandidate).isNotNull();

			final int tableId = tableDAO.retrieveTableId(tableName);
			final TableRecordReference recordReference = TableRecordReference.of(tableId, iIInvoiceCandidate.getI_Invoice_Candidate_ID());

			final List<I_C_Invoice_Candidate> invoiceCandidateRecords = invoiceCandDAO.retrieveReferencing(recordReference);

			validate_C_invoice_Candidate(invoiceCandidateRecords.get(0), row);
			validate_C_Invoice_Candidate_mandatory_fields(invoiceCandidateRecords.get(0), row);

			final String invoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
			invoiceCandTable.putOrReplace(invoiceCandIdentifier, invoiceCandidateRecords.get(0));
		}
	}

	@NonNull
	private ItemProvider.ProviderResult<I_C_Invoice_Candidate> getInvoiceCandidateIfMatches(@NonNull final Map<String, String> row)
	{
		final String invoiceCandidateIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Invoice_Candidate_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_Invoice_Candidate invoiceCandidateRecord = invoiceCandTable.get(invoiceCandidateIdentifier);
		InterfaceWrapperHelper.refresh(invoiceCandidateRecord);

		final BigDecimal qtyToInvoice = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_QtyToInvoice);

		if (qtyToInvoice == null)
		{
			return ItemProvider.ProviderResult.resultWasFound(invoiceCandidateRecord);
		}
		else if (qtyToInvoice.compareTo(invoiceCandidateRecord.getQtyToInvoice()) == 0)
		{
			return ItemProvider.ProviderResult.resultWasFound(invoiceCandidateRecord);
		}

		return ItemProvider.ProviderResult.resultWasNotFound("C_Invoice_Candidate_ID=" + invoiceCandidateRecord.getC_Invoice_Candidate_ID() + " is not matching criteria yet");
	}
}

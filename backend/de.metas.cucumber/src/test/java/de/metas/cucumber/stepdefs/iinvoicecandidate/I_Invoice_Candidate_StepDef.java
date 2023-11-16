/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cucumber.stepdefs.iinvoicecandidate;

import de.metas.cucumber.stepdefs.AD_User_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.activity.C_Activity_StepDefData;
import de.metas.cucumber.stepdefs.docType.C_DocType_StepDefData;
import de.metas.cucumber.stepdefs.org.AD_Org_StepDefData;
import de.metas.cucumber.stepdefs.uom.C_UOM_StepDefData;
import de.metas.invoicecandidate.model.I_I_Invoice_Candidate;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class I_Invoice_Candidate_StepDef
{
	private final I_Invoice_Candidate_StepDefData iInvoiceCandidateTable;
	private final C_BPartner_StepDefData bpartnerTable;
	private final M_Product_StepDefData productTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;
	private final AD_User_StepDefData contactTable;
	private final C_DocType_StepDefData docTypeTable;
	private final C_UOM_StepDefData uomTable;
	private final AD_Org_StepDefData orgTable;
	private final C_Activity_StepDefData activityTable;

	private final I_Invoice_Candidate_List_StepDefData iInvoiceCandidateListTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	public I_Invoice_Candidate_StepDef(
			@NonNull final I_Invoice_Candidate_StepDefData iInvoiceCandidateTable,
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_BPartner_Location_StepDefData bPartnerLocationTable,
			@NonNull final AD_User_StepDefData contactTable,
			@NonNull final C_DocType_StepDefData docTypeTable,
			@NonNull final C_UOM_StepDefData uomTable,
			@NonNull final AD_Org_StepDefData orgTable,
			@NonNull final C_Activity_StepDefData activityTable,
			@NonNull final I_Invoice_Candidate_List_StepDefData iInvoiceCandidateListTable)
	{
		this.iInvoiceCandidateTable = iInvoiceCandidateTable;
		this.bpartnerTable = bpartnerTable;
		this.productTable = productTable;
		this.bPartnerLocationTable = bPartnerLocationTable;
		this.contactTable = contactTable;
		this.docTypeTable = docTypeTable;
		this.uomTable = uomTable;
		this.orgTable = orgTable;
		this.activityTable = activityTable;
		this.iInvoiceCandidateListTable = iInvoiceCandidateListTable;
	}

	@And("I_Invoice_Candidate is found: searching by product value")
	public void validate_I_Invoice_Candidate(@NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			validateCreatedIInvoiceCandidate(row);
		}
	}

	@And("locate I_Invoice_Candidate list searching by bill partner value")
	public void loadImportInvoiceCandidatesByPartnerValue(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String billBPartnerValue = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_Bill_BPartner_Value);

			final List<I_I_Invoice_Candidate> importInvoiceCandidates = queryBL.createQueryBuilder(I_I_Invoice_Candidate.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_I_Invoice_Candidate.COLUMNNAME_Bill_BPartner_Value, billBPartnerValue)
					.orderByDescending(I_I_Invoice_Candidate.COLUMNNAME_Created)
					.create()
					.list(I_I_Invoice_Candidate.class);

			final Integer numberOfCandidates = DataTableUtil.extractIntegerOrNullForColumnName(row, "OPT.ExpectedCount");
			if (numberOfCandidates != null)
			{
				assertThat(numberOfCandidates).as("ExpectedCount").isEqualTo(importInvoiceCandidates.size());
			}

			final String iInvoiceCandidateIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_I_Invoice_Candidate_ID + "_List." + TABLECOLUMN_IDENTIFIER);
			iInvoiceCandidateListTable.putOrReplace(iInvoiceCandidateIdentifier, importInvoiceCandidates);
		}
	}

	private void validateCreatedIInvoiceCandidate(@NonNull final Map<String, String> row) throws InterruptedException
	{
		final String isImported = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_I_IsImported);
		final String productValue = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_M_Product_Value);

		final I_I_Invoice_Candidate invoiceCandidate = queryBL.createQueryBuilder(I_I_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_I_Invoice_Candidate.COLUMNNAME_I_IsImported, isImported)
				.addEqualsFilter(I_I_Invoice_Candidate.COLUMNNAME_M_Product_Value, productValue)
				.orderByDescending(I_I_Invoice_Candidate.COLUMNNAME_Created)
				.create()
				.firstOptional(I_I_Invoice_Candidate.class)
				.orElse(null);

		assertThat(invoiceCandidate).isNotNull();

		final SoftAssertions softly = new SoftAssertions();

		final String errorMsg = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_I_ErrorMsg);
		if (Check.isNotBlank(errorMsg))
		{
			softly.assertThat(invoiceCandidate.getI_ErrorMsg().trim()).as("I_ErrorMsg").isEqualTo(errorMsg.trim());

			final String iInvoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_I_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
			iInvoiceCandidateTable.putOrReplace(iInvoiceCandIdentifier, invoiceCandidate);
			return;
		}

		final String billBPartnerIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner billBPartner = bpartnerTable.get(billBPartnerIdentifier);
		softly.assertThat(invoiceCandidate.getBill_BPartner_ID()).as("Bill_Partner_ID").isEqualTo(billBPartner.getC_BPartner_ID());
		if (invoiceCandidate.getBill_BPartner_Value() != null)
		{
			softly.assertThat(invoiceCandidate.getBill_BPartner_Value()).as("Bill_Partner_Value").isEqualTo(billBPartner.getValue());
		}
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Product product = productTable.get(productIdentifier);
		softly.assertThat(invoiceCandidate.getM_Product_ID()).as("M_Product_ID").isEqualTo(product.getM_Product_ID());

		final String billBPartnerLocationIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_Bill_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner_Location bPartnerLocation = bPartnerLocationTable.get(billBPartnerLocationIdentifier);
		softly.assertThat(invoiceCandidate.getBill_Location_ID()).as("C_BPartnerLocation_ID").isEqualTo(bPartnerLocation.getC_BPartner_Location_ID());

		final String billBPartnerContactIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_Bill_User_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_AD_User contact = billBPartnerContactIdentifier == null ? null : contactTable.get(billBPartnerContactIdentifier);
		softly.assertThat(invoiceCandidate.getBill_User_ID()).as("invoiceCandidate.Bill_User_ID").isEqualTo(contact == null ? 0 : contact.getAD_User_ID());

		final String orgIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_AD_Org_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_AD_Org org = orgTable.get(orgIdentifier);
		softly.assertThat(invoiceCandidate.getAD_Org_ID()).as("AD_Orge_ID").isEqualTo(org.getAD_Org_ID());

		final BigDecimal qtyOrdered = DataTableUtil.extractBigDecimalForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_QtyOrdered);
		final BigDecimal qtyDelivered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_QtyDelivered);

		softly.assertThat(invoiceCandidate.getM_Product_ID()).as("M_Product_ID").isNotNull();
		softly.assertThat(invoiceCandidate.getQtyOrdered()).as("QtyOrdered").isNotNull().isEqualTo(qtyOrdered);
		softly.assertThat(invoiceCandidate.getQtyDelivered()).as("QtyDelivered").isEqualTo(qtyDelivered);

		final ZoneId zoneId = orgDAO.getTimeZone(OrgId.ofRepoId(invoiceCandidate.getAD_Org_ID()));
		final Timestamp dateOrdered = DataTableUtil.extractDateTimestampForColumnNameOrNull(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_DateOrdered);
		if (dateOrdered != null)
		{
			softly.assertThat(TimeUtil.asLocalDate(invoiceCandidate.getDateOrdered(), zoneId))
					.as("DateOrdered")
					.isEqualTo(TimeUtil.asLocalDate(dateOrdered, zoneId));
		}

		final Timestamp presetDateInvoiced = DataTableUtil.extractDateTimestampForColumnNameOrNull(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_PresetDateInvoiced);
		if (presetDateInvoiced != null)
		{
			softly.assertThat(TimeUtil.asLocalDate(invoiceCandidate.getPresetDateInvoiced(), zoneId))
					.as("PresetDeviceInvoiced")
					.isEqualTo(TimeUtil.asLocalDate(presetDateInvoiced, zoneId));
		}

		final String description = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_Description);
		final String invoiceRule = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_InvoiceRule);
		final boolean isSOTrx = DataTableUtil.extractBooleanForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_IsSOTrx);
		final String poReference = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_POReference);

		softly.assertThat(invoiceCandidate.getPOReference()).as("POReference").isEqualTo(poReference);
		softly.assertThat(invoiceCandidate.getDescription()).as("Description").isEqualTo(description);
		softly.assertThat(invoiceCandidate.isSOTrx()).as("IsSOTrx").isEqualTo(isSOTrx);
		//dev-note: param isImported is passed as string from feature file as it has value 'E' when an error occurs
		softly.assertThat(invoiceCandidate.isI_IsImported()).as("IsImported").isEqualTo(isImported.equals("Y"));

		if (Check.isNotBlank(invoiceRule))
		{
			softly.assertThat(invoiceCandidate.getInvoiceRule()).as("InvoiceRule").isEqualTo(invoiceRule);
		}
		else
		{
			softly.assertThat(invoiceCandidate.getInvoiceRule()).as("InvoiceRule").isEqualTo(billBPartner.getInvoiceRule());
		}

		final String docTypeIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_C_DocType_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(docTypeIdentifier))
		{
			final I_C_DocType docType = docTypeTable.get(docTypeIdentifier);
			softly.assertThat(invoiceCandidate.getC_DocType_ID()).as("C_DocType_ID").isEqualTo(docType.getC_DocType_ID());
		}

		final String uomIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_C_UOM_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(uomIdentifier))
		{
			final I_C_UOM uom = uomTable.get(uomIdentifier);
			softly.assertThat(invoiceCandidate.getC_UOM_ID()).as("UOM").isEqualTo(uom.getC_UOM_ID());
		}
		else
		{
			softly.assertThat(invoiceCandidate.getC_UOM_ID()).as("UOM").isEqualTo(product.getC_UOM_ID());
		}

		final String activityIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_C_Activity_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(activityIdentifier))
		{
			final I_C_Activity activity = activityTable.get(activityIdentifier);
			softly.assertThat(invoiceCandidate.getC_Activity_ID()).as("C_Activity_ID").isEqualTo(activity.getC_Activity_ID());
		}

		softly.assertAll();

		final String iInvoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_I_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
		iInvoiceCandidateTable.putOrReplace(iInvoiceCandIdentifier, invoiceCandidate);
	}
}

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

package de.metas.cucumber.stepdefs.iinvoicecandidate;

import de.metas.cucumber.stepdefs.AD_User_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
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
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

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
			@NonNull final AD_Org_StepDefData orgTable)
	{
		this.iInvoiceCandidateTable = iInvoiceCandidateTable;
		this.bpartnerTable = bpartnerTable;
		this.productTable = productTable;
		this.bPartnerLocationTable = bPartnerLocationTable;
		this.contactTable = contactTable;
		this.docTypeTable = docTypeTable;
		this.uomTable = uomTable;
		this.orgTable = orgTable;
	}

	@And("I_Invoice_Candidate is found: searching by product value")
	public void validate_I_Invoice_Candidate(@NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			validateCreatedIInvoiceCandidate(row);
		}
	}

	@And("metasfresh initially has no I_Invoice_Candidate data")
	public void delete_I_Invoice_Candidate_data()
	{
		DB.executeUpdateEx("DELETE FROM I_Invoice_Candidate cascade", ITrx.TRXNAME_None);
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

		final String errorMsg = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_I_ErrorMsg);
		if (Check.isNotBlank(errorMsg))
		{
			assertThat(invoiceCandidate.getI_ErrorMsg().trim()).isEqualTo(errorMsg.trim());

			final String iInvoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_I_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
			iInvoiceCandidateTable.putOrReplace(iInvoiceCandIdentifier, invoiceCandidate);
			return;
		}

		final String billBPartnerIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner billBPartner = bpartnerTable.get(billBPartnerIdentifier);
		assertThat(billBPartner).isNotNull();
		assertThat(invoiceCandidate.getBill_BPartner_ID()).isEqualTo(billBPartner.getC_BPartner_ID());
		assertThat(invoiceCandidate.getBill_BPartner_Value()).isEqualTo(billBPartner.getValue());

		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Product product = productTable.get(productIdentifier);
		assertThat(product).isNotNull();
		assertThat(invoiceCandidate.getM_Product_ID()).isEqualTo(product.getM_Product_ID());

		final String billBPartnerLocationIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_Bill_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner_Location bPartnerLocation = bPartnerLocationTable.get(billBPartnerLocationIdentifier);
		assertThat(bPartnerLocation).isNotNull();
		assertThat(invoiceCandidate.getBill_Location_ID()).isEqualTo(bPartnerLocation.getC_BPartner_Location_ID());

		final String billBPartnerContactIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_Bill_User_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_AD_User contact = contactTable.get(billBPartnerContactIdentifier);
		assertThat(contact).isNotNull();
		assertThat(invoiceCandidate.getBill_User_ID()).isEqualTo(contact.getAD_User_ID());

		final String orgIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_AD_Org_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_AD_Org org = orgTable.get(orgIdentifier);
		assertThat(org).isNotNull();
		assertThat(invoiceCandidate.getAD_Org_ID()).isEqualTo(org.getAD_Org_ID());

		final BigDecimal qtyOrdered = DataTableUtil.extractBigDecimalForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_QtyOrdered);
		final BigDecimal qtyDelivered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_QtyDelivered);

		assertThat(invoiceCandidate.getM_Product_ID()).isNotNull();
		assertThat(invoiceCandidate.getQtyOrdered()).isEqualTo(qtyOrdered);
		assertThat(invoiceCandidate.getQtyDelivered()).isEqualTo(qtyDelivered);

		final ZoneId zoneId = orgDAO.getTimeZone(OrgId.ofRepoId(invoiceCandidate.getAD_Org_ID()));
		final Timestamp dateOrdered = DataTableUtil.extractDateTimestampForColumnNameOrNull(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_DateOrdered);
		assertThat(TimeUtil.asLocalDate(invoiceCandidate.getDateOrdered(), zoneId)).isEqualTo(TimeUtil.asLocalDate(dateOrdered, zoneId));

		final Timestamp presetDateInvoiced = DataTableUtil.extractDateTimestampForColumnNameOrNull(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_PresetDateInvoiced);
		assertThat(TimeUtil.asLocalDate(invoiceCandidate.getPresetDateInvoiced(), zoneId)).isEqualTo(TimeUtil.asLocalDate(presetDateInvoiced, zoneId));

		final String description = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_Description);
		final String invoiceRule = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_InvoiceRule);
		final boolean isSOTrx = DataTableUtil.extractBooleanForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_IsSOTrx);
		final String poReference = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_POReference);
		assertThat(invoiceCandidate.getPOReference()).isEqualTo(poReference);
		assertThat(invoiceCandidate.getDescription()).isEqualTo(description);
		assertThat(invoiceCandidate.isSOTrx()).isEqualTo(isSOTrx);
		//dev-note: param isImported is passed as string from feature file as it has value 'E' when an error occurs
		assertThat(invoiceCandidate.isI_IsImported()).isEqualTo(isImported.equals("Y"));

		if (Check.isNotBlank(invoiceRule))
		{
			assertThat(invoiceCandidate.getInvoiceRule()).isEqualTo(invoiceRule);
		}
		else
		{
			assertThat(invoiceCandidate.getInvoiceRule()).isEqualTo(billBPartner.getInvoiceRule());
		}

		final String docTypeIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_C_DocType_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(docTypeIdentifier))
		{
			final I_C_DocType docType = docTypeTable.get(docTypeIdentifier);
			assertThat(docType).isNotNull();
			assertThat(invoiceCandidate.getC_DocType_ID()).isEqualTo(docType.getC_DocType_ID());
		}

		final String uomIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_C_UOM_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(uomIdentifier))
		{
			final I_C_UOM uom = uomTable.get(uomIdentifier);
			assertThat(uom).isNotNull();
			assertThat(invoiceCandidate.getC_UOM_ID()).isEqualTo(uom.getC_UOM_ID());
		}
		else
		{
			assertThat(invoiceCandidate.getC_UOM_ID()).isEqualTo(product.getC_UOM_ID());
		}

		final String iInvoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_I_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
		iInvoiceCandidateTable.putOrReplace(iInvoiceCandIdentifier, invoiceCandidate);
	}
}

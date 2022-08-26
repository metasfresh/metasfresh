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
import de.metas.cucumber.stepdefs.uom.C_UOM_StepDefData;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_I_Invoice_Candidate;
import org.compiere.model.I_M_Product;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;
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

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public I_Invoice_Candidate_StepDef(
			@NonNull final I_Invoice_Candidate_StepDefData iInvoiceCandidateTable,
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_BPartner_Location_StepDefData bPartnerLocationTable,
			@NonNull final AD_User_StepDefData contactTable,
			@NonNull final C_DocType_StepDefData docTypeTable,
			@NonNull final C_UOM_StepDefData uomTable)
	{
		this.iInvoiceCandidateTable = iInvoiceCandidateTable;
		this.bpartnerTable = bpartnerTable;
		this.productTable = productTable;
		this.bPartnerLocationTable = bPartnerLocationTable;
		this.contactTable = contactTable;
		this.docTypeTable = docTypeTable;
		this.uomTable = uomTable;
	}

	@And("^after not more than (.*)s I_Invoice_Candidate is found:$")
	public void validate_I_Invoice_Candidate(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			validateCreatedIInvoiceCandidate(timeoutSec, row);
		}
	}

	@And("metasfresh initially has no I_Invoice_Candidate data")
	public void delete_I_Invoice_Candidate_data()
	{
		DB.executeUpdateEx("DELETE FROM I_Invoice_Candidate cascade", ITrx.TRXNAME_None);
	}

	private void validateCreatedIInvoiceCandidate(final int timeoutSec, @NonNull final Map<String, String> row) throws InterruptedException
	{
		final String isImported = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_I_IsImported);
		final String productValue = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_M_Product_Value);
		final String errorMsg = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_I_ErrorMsg);

		final Supplier<Boolean> iInvoiceCandidateFound = () -> {
			final I_I_Invoice_Candidate iIInvoiceCandidate = queryBL.createQueryBuilder(I_I_Invoice_Candidate.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_I_Invoice_Candidate.COLUMNNAME_I_IsImported, isImported)
					.addEqualsFilter(I_I_Invoice_Candidate.COLUMNNAME_M_Product_Value, productValue)
					.orderByDescending(I_I_Invoice_Candidate.COLUMNNAME_Created)
					.create()
					.first();

			if (iIInvoiceCandidate == null)
			{
				return false;
			}

			if (Check.isNotBlank(errorMsg))
			{
				assertThat(iIInvoiceCandidate.getI_ErrorMsg().trim()).isEqualTo(errorMsg.trim());
			}
			else
			{
				final String billBPartnerIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
				final I_C_BPartner billBPartner = bpartnerTable.get(billBPartnerIdentifier);
				assertThat(billBPartner).isNotNull();
				assertThat(iIInvoiceCandidate.getBill_BPartner_ID()).isEqualTo(billBPartner.getC_BPartner_ID());
				assertThat(iIInvoiceCandidate.getBill_BPartner_Value()).isEqualTo(billBPartner.getValue());

				final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
				final I_M_Product product = productTable.get(productIdentifier);
				assertThat(product).isNotNull();
				assertThat(iIInvoiceCandidate.getM_Product_ID()).isEqualTo(product.getM_Product_ID());

				final String billBPartnerLocationIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_Bill_Location_ID + "." + TABLECOLUMN_IDENTIFIER);

				final I_C_BPartner_Location bPartnerLocation = bPartnerLocationTable.get(billBPartnerLocationIdentifier);
				assertThat(bPartnerLocation).isNotNull();
				assertThat(iIInvoiceCandidate.getBill_Location_ID()).isEqualTo(bPartnerLocation.getC_BPartner_Location_ID());

				final String billBPartnerContactIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_Bill_User_ID + "." + TABLECOLUMN_IDENTIFIER);
				final I_AD_User contact = contactTable.get(billBPartnerContactIdentifier);
				assertThat(contact).isNotNull();
				assertThat(iIInvoiceCandidate.getBill_User_ID()).isEqualTo(contact.getAD_User_ID());

				final BigDecimal qtyOrdered = DataTableUtil.extractBigDecimalForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_QtyOrdered);
				final BigDecimal qtyDelivered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_QtyDelivered);
				final Timestamp dateOrdered = DataTableUtil.extractDateTimestampForColumnNameOrNull(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_DateOrdered);
				final Timestamp presetDateInvoiced = DataTableUtil.extractDateTimestampForColumnNameOrNull(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_PresetDateInvoiced);
				final String poReference = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_POReference);
				final String description = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_Description);
				final String invoiceRule = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_InvoiceRule);
				final boolean isSOTrx = DataTableUtil.extractBooleanForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_IsSOTrx);

				assertThat(iIInvoiceCandidate.getM_Product_ID()).isNotNull();
				assertThat(iIInvoiceCandidate.getQtyOrdered()).isEqualTo(qtyOrdered);
				assertThat(iIInvoiceCandidate.getQtyDelivered()).isEqualTo(qtyDelivered);
				assertThat(TimeUtil.asLocalDate(iIInvoiceCandidate.getDateOrdered())).isEqualTo(TimeUtil.asLocalDate(dateOrdered));
				assertThat(TimeUtil.asLocalDate(iIInvoiceCandidate.getPresetDateInvoiced())).isEqualTo(TimeUtil.asLocalDate(presetDateInvoiced));
				assertThat(iIInvoiceCandidate.getPOReference()).isEqualTo(poReference);
				assertThat(iIInvoiceCandidate.getDescription()).isEqualTo(description);
				assertThat(iIInvoiceCandidate.isSOTrx()).isEqualTo(isSOTrx);
				assertThat(iIInvoiceCandidate.isI_IsImported()).isEqualTo(isImported.equals("Y") ? true : "E");

				if (Check.isNotBlank(invoiceRule))
				{
					assertThat(iIInvoiceCandidate.getInvoiceRule()).isEqualTo(invoiceRule);
				}
				else
				{
					assertThat(iIInvoiceCandidate.getInvoiceRule()).isEqualTo(billBPartner.getInvoiceRule());
				}

				final String docTypeIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_C_DocType_ID + "." + TABLECOLUMN_IDENTIFIER);
				if (Check.isNotBlank(docTypeIdentifier))
				{
					final I_C_DocType docType = docTypeTable.get(docTypeIdentifier);
					assertThat(docType).isNotNull();
					assertThat(iIInvoiceCandidate.getC_DocType_ID()).isEqualTo(docType.getC_DocType_ID());
				}

				final String uomIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_C_UOM_ID + "." + TABLECOLUMN_IDENTIFIER);
				if (Check.isNotBlank(uomIdentifier))
				{
					final I_C_UOM uom = uomTable.get(uomIdentifier);
					assertThat(uom).isNotNull();
					assertThat(iIInvoiceCandidate.getC_UOM_ID()).isEqualTo(uom.getC_UOM_ID());
				}
			}

			final String iInvoiceCandIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_I_Invoice_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
			iInvoiceCandidateTable.putOrReplace(iInvoiceCandIdentifier, iIInvoiceCandidate);

			return true;
		};

		StepDefUtil.tryAndWait(timeoutSec, 500, iInvoiceCandidateFound);
	}
}

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

package de.metas.cucumber.stepdefs.dataImport;

import com.google.common.base.Joiner;
import de.metas.common.util.CoalesceUtil;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_I_ModCntr_Log;
import de.metas.cucumber.stepdefs.AD_User_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.contract.C_Flatrate_Term_StepDefData;
import de.metas.cucumber.stepdefs.importFormat.AD_ImpFormat_StepDefData;
import de.metas.invoicecandidate.model.I_I_Invoice_Candidate;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_AD_ImpFormat;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DataImport;
import org.compiere.model.I_I_BPartner;
import org.compiere.model.I_M_Product;
import org.compiere.util.DB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class C_DataImport_StepDef
{
	private final C_BPartner_StepDefData bpartnerTable;
	private final M_Product_StepDefData productTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;
	private final AD_User_StepDefData contactTable;
	private final C_DataImport_StepDefData dataImportTable;
	private final AD_ImpFormat_StepDefData impFormatTable;
	private final C_Flatrate_Term_StepDefData contractsTable;

	private final TestContext testContext;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public C_DataImport_StepDef(
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_BPartner_Location_StepDefData bPartnerLocationTable,
			@NonNull final AD_User_StepDefData contactTable,
			@NonNull final C_DataImport_StepDefData dataImportTable,
			@NonNull final AD_ImpFormat_StepDefData impFormatTable,
			@NonNull final C_Flatrate_Term_StepDefData contractsTable,
			@NonNull final TestContext testContext)
	{
		this.bpartnerTable = bpartnerTable;
		this.productTable = productTable;
		this.bPartnerLocationTable = bPartnerLocationTable;
		this.contactTable = contactTable;
		this.dataImportTable = dataImportTable;
		this.impFormatTable = impFormatTable;
		this.contractsTable = contractsTable;
		this.testContext = testContext;
	}

	@And("store DataImport string requestBody in context")
	public void store_string_requestBody_in_context(@NonNull final DataTable dataTable)
	{
		final Map<String, String> row = dataTable.asMaps().get(0);

		final String billBPartnerIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
		final String bpartnerValue;
		if (Check.isNotBlank(billBPartnerIdentifier))
		{
			final I_C_BPartner billBPartner = bpartnerTable.get(billBPartnerIdentifier);
			assertThat(billBPartner).isNotNull();
			bpartnerValue = billBPartner.getValue();
		}
		else
		{
			bpartnerValue = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_Bill_BPartner_Value);
		}

		final String billBPartnerLocationIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_Bill_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_BPartner_Location bPartnerLocation = bPartnerLocationTable.get(billBPartnerLocationIdentifier);
		assertThat(bPartnerLocation).isNotNull();

		final String productIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final String productValue;
		if (Check.isNotBlank(productIdentifier))
		{
			final I_M_Product product = productTable.get(productIdentifier);
			assertThat(product).isNotNull();
			productValue = product.getValue();
		}
		else
		{
			productValue = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_M_Product_Value);
		}

		final String qtyOrdered = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_QtyOrdered);
		final String isSOTrx = DataTableUtil.extractStringForColumnName(row, I_I_Invoice_Candidate.COLUMNNAME_IsSOTrx);

		final String billBPartnerContactIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_Bill_User_ID + "." + TABLECOLUMN_IDENTIFIER);
		final int contact = billBPartnerContactIdentifier == null ? 0 : contactTable.get(billBPartnerContactIdentifier).getAD_User_ID();

		final String qtyDelivered = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_QtyDelivered);
		final String dateOrdered = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_DateOrdered);
		final String presetDateInvoiced = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_PresetDateInvoiced);
		final String uomCode = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_X12DE355);
		final String docBaseType = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_DocBaseType);
		final String docSubType = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_DocSubType);
		final String poReference = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_POReference);
		final String description = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_Description);
		final String invoiceRule = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_InvoiceRule);
		final String orgCode = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_OrgCode);
		final String defaultOrgCode = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_Default_OrgCode);
		final String endNote = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_DescriptionBottom);
		final String adUserInChargeIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_AD_User_InCharge_ID + "." + TABLECOLUMN_IDENTIFIER);
		final BigDecimal discount = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_Discount);
		final BigDecimal price = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_Price);
		final String activityValue = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_Invoice_Candidate.COLUMNNAME_C_Activity_Value);

		int userInChargeId = 0;
		if (Check.isNotBlank(adUserInChargeIdentifier))
		{
			final I_AD_User userInCharge = contactTable.get(adUserInChargeIdentifier);
			assertThat(userInCharge).isNotNull();
			userInChargeId = userInCharge.getAD_User_ID();
		}

		final String payload = bpartnerValue + ";"
				+ bPartnerLocation.getC_BPartner_Location_ID() + ";"
				+ (contact == 0 ? "" : contact) + ";"
				+ productValue + ";"
				+ dateOrdered + ";"
				+ qtyOrdered + ";"
				+ qtyDelivered + ";"
				+ uomCode + ";"
				+ isSOTrx + ";"
				+ docBaseType + ";"
				+ docSubType + ";"
				+ presetDateInvoiced + ";"
				+ description + ";"
				+ poReference + ";"
				+ invoiceRule + ";"
				+ orgCode + ";"
				+ defaultOrgCode + ";"
				+ endNote + ";"
				+ (userInChargeId >= 0 ? userInChargeId : "") + ";"
				+ discount + ";"
				+ price + ";"
				+ activityValue + ";";

		testContext.setRequestPayload(payload.replaceAll("null", ""));
	}

	@And("store String as requestBody in context")
	public void store_string_as_requestBody_in_context(@NonNull final DataTable dataTable)
	{
		final Map<String, String> row = CollectionUtils.singleElement(dataTable.asMaps());

		final String content = DataTableUtil.extractStringOrNullForColumnName(row, "String");

		testContext.setRequestPayload(content);
	}

	@And("store file content as requestBody in context")
	public void store_file_content_requestBody_in_context(@NonNull final DataTable dataTable) throws IOException
	{
		final Map<String, String> row = dataTable.asMaps().get(0);

		final String fileName = DataTableUtil.extractStringForColumnName(row, "FileName");

		final InputStream inputStream = C_DataImport_StepDef.class.getClassLoader().getResourceAsStream(fileName);

		final String content = new BufferedReader(
				new InputStreamReader(inputStream, StandardCharsets.UTF_8))
				.lines()
				.collect(Collectors.joining("\n"));

		testContext.setRequestPayload(content);
	}

	@And("metasfresh contains C_DataImport:")
	public void metasfreshContainsDataImport(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String internalName = DataTableUtil.extractStringForColumnName(row, I_C_DataImport.COLUMNNAME_InternalName);

			final I_C_DataImport record = CoalesceUtil
					.coalesceSuppliersNotNull(() -> queryBL.createQueryBuilder(I_C_DataImport.class)
									.addOnlyActiveRecordsFilter()
									.addStringLikeFilter(I_C_DataImport.COLUMNNAME_InternalName, internalName, false)
									.create()
									.firstOnlyOrNull(I_C_DataImport.class),
							() -> InterfaceWrapperHelper.newInstance(I_C_DataImport.class));

			final String impFormatIdentifier = DataTableUtil.extractStringForColumnName(row, I_AD_ImpFormat.COLUMNNAME_AD_ImpFormat_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_AD_ImpFormat importFormat = impFormatTable.getOptional(impFormatIdentifier)
					.orElseThrow(() -> new AdempiereException("Missing I_AD_ImpFormat for identifier!")
							.appendParametersToMessage()
							.setParameter("impFormatIdentifier", impFormatIdentifier));

			record.setAD_ImpFormat_ID(importFormat.getAD_ImpFormat_ID());
			record.setInternalName(internalName);

			InterfaceWrapperHelper.saveRecord(record);

			final String dataRecordIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_DataImport.COLUMNNAME_C_DataImport_ID + "." + TABLECOLUMN_IDENTIFIER);
			dataImportTable.putOrReplace(dataRecordIdentifier, record);
		}
	}

	@And("store business partner DataImport string requestBody in context")
	public void store_business_partner_string_requestBody_in_context(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String bpValue = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_BPValue);
			final String companyName = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_Companyname);
			final String taxId = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_TaxID);
			final String firstName = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_Firstname);
			final String lastName = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_Lastname);
			final Boolean isShipToContactDefault = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_I_BPartner.COLUMNNAME_IsShipToContact_Default, false);
			final Boolean isBillToContactDefault = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_I_BPartner.COLUMNNAME_IsBillToContact_Default, false);
			final String address1 = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_Address1);
			final String address2 = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_Address2);
			final String address3 = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_Address3);
			final String address4 = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_Address4);
			final String city = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_City);
			final String region = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_RegionName);
			final String countryCode = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_CountryCode);
			final String groupValue = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_GroupValue);
			final Boolean isBillToDefault = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_I_BPartner.COLUMNNAME_IsBillToDefault, false);
			final Boolean isShipToDefault = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_I_BPartner.COLUMNNAME_IsShipToDefault, false);
			final String adLanguage = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_GroupValue);
			final String orgValue = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_OrgValue);
			final String postal = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_Postal);
			final String bankDetails = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_BankDetails);
			final String iban = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_IBAN);
			final String qrIban = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_QR_IBAN);
			final String isoCurrencyCode = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_ISO_Code);
			final String isManual = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_IsManuallyCreated);

			final String payload = bpValue + "	"
					+ companyName + "	"
					+ taxId + "	"
					+ firstName + "	"
					+ lastName + "	"
					+ isShipToContactDefault + "	"
					+ isBillToContactDefault + "	"
					+ address1 + "	"
					+ address2 + "	"
					+ address3 + "	"
					+ address4 + "	"
					+ city + "	"
					+ region + "	"
					+ countryCode + "	"
					+ groupValue + "	"
					+ isBillToDefault + "	"
					+ isShipToDefault + "	"
					+ adLanguage + "	"
					+ orgValue + "	"
					+ postal + "	"
					+ bankDetails + "	"
					+ iban + "	"
					+ qrIban + "	"
					+ isoCurrencyCode + "	"
					+ isManual + "	";

			testContext.setRequestPayload(payload.replaceAll("null", ""));
		}
	}

	@And("store modular contract DataImport String requestBody in context")
	public void store_modular_contract_string_requestBody_in_context(@NonNull final DataTable dataTable)
	{
		final StringBuilder payloadBuilder = new StringBuilder();
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String documentType = DataTableUtil.extractStringOrNullForColumnName(row, I_I_ModCntr_Log.COLUMNNAME_ModCntr_Log_DocumentType);
			final String soTrx = DataTableUtil.extractStringOrNullForColumnName(row, I_I_ModCntr_Log.COLUMNNAME_IsSOTrx);
			final String contractDocumentIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID + "." + TABLECOLUMN_IDENTIFIER);
			final String productValue = DataTableUtil.extractStringOrNullForColumnName(row, I_I_ModCntr_Log.COLUMNNAME_ProductValue);
			final String modCntr_InvoicingGroupName = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_ModCntr_Log.COLUMNNAME_ModCntr_InvoicingGroupName);
			final String quantity = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_ModCntr_Log.COLUMNNAME_Qty);
			final String uomSymbol = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_ModCntr_Log.COLUMNNAME_UOMSymbol);
			final String priceActual = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_ModCntr_Log.COLUMNNAME_PriceActual);
			final String priceUom = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_ModCntr_Log.COLUMNNAME_PriceUOM);
			final String amount = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_ModCntr_Log.COLUMNNAME_Amount);
			final String isoCurrencyCode = DataTableUtil.extractStringOrNullForColumnName(row, I_I_ModCntr_Log.COLUMNNAME_ISO_Code);
			final String year = DataTableUtil.extractStringOrNullForColumnName(row, I_I_ModCntr_Log.COLUMNNAME_FiscalYear);
			final String calendar = DataTableUtil.extractStringOrNullForColumnName(row, I_I_ModCntr_Log.COLUMNNAME_CalendarName);
			final String bpartnerValue = DataTableUtil.extractStringOrNullForColumnName(row, I_I_ModCntr_Log.COLUMNNAME_BPartnerValue);
			final String billBpartnerValue = DataTableUtil.extractStringOrNullForColumnName(row, I_I_ModCntr_Log.COLUMNNAME_Bill_BPartner_Value);
			final String collectionPointValue = DataTableUtil.extractStringOrNullForColumnName(row, I_I_ModCntr_Log.COLUMNNAME_CollectionPointValue);
			final LocalDate dateTrx = DataTableUtil.extractLocalDateForColumnName(row, I_I_ModCntr_Log.COLUMNNAME_DateTrx);
			final String warehouseName = DataTableUtil.extractStringOrNullForColumnName(row, I_I_ModCntr_Log.COLUMNNAME_WarehouseName);

			final I_C_Flatrate_Term contract = contractsTable.get(contractDocumentIdentifier);

			payloadBuilder.append("\n"); // do this for the first row because the import format ignores the first row/file header
			//keep in sync with Import Format defined by /window/189/540092
			payloadBuilder.append(Joiner.on(";")
					.useForNull("")
					.join(null,//ModCntr_Log_ID
							null,//TableID
							null,//RecordId
							documentType,
							soTrx,
							contract.getDocumentNo(),
							productValue,
							modCntr_InvoicingGroupName,
							quantity,
							uomSymbol,
							priceActual,
							priceUom,
							amount,
							isoCurrencyCode,
							year,
							calendar,
							null,//ContractModuleName
							bpartnerValue,
							billBpartnerValue,
							collectionPointValue,
							dateTrx,
							warehouseName));
		}
		testContext.setRequestPayload(new String(payloadBuilder.toString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
	}

	@And("load C_DataImport:")
	public void load_C_DataImport(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			loadDataImport(row);
		}
	}

	private void loadDataImport(@NonNull final Map<String, String> row)
	{
		final String dataImportIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_DataImport.COLUMNNAME_C_DataImport_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Integer id = DataTableUtil.extractIntegerOrNullForColumnName(row, "OPT." + I_C_DataImport.COLUMNNAME_C_DataImport_ID);

		if (id != null)
		{
			final I_C_DataImport dataImportRecord = InterfaceWrapperHelper.load(id, I_C_DataImport.class);
			Assertions.assertThat(dataImportRecord).isNotNull();

			dataImportTable.putOrReplace(dataImportIdentifier, dataImportRecord);
		}
	}

	@And("^metasfresh initially has no (I_.*) import data$")
	public void delete_I_Invoice_Candidate_data(@NonNull final String tableName)
	{
		DB.executeUpdateAndThrowExceptionOnFail("DELETE FROM " + tableName + " cascade", ITrx.TRXNAME_None);
	}
}

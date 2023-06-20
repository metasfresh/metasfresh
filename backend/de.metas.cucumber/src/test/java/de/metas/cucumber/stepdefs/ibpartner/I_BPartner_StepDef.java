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

package de.metas.cucumber.stepdefs.ibpartner;

import de.metas.cucumber.stepdefs.C_BP_BankAccount_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.dataImport.C_DataImport_StepDefData;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DataImport;
import org.compiere.model.I_I_BPartner;

import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class I_BPartner_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final I_BPartner_StepDefData iBPartnerTable;
	private final C_BPartner_StepDefData bpartnerTable;
	private final C_BPartner_Location_StepDefData bpLocationTable;
	private final C_BP_BankAccount_StepDefData bpBankAccountTable;
	private final C_DataImport_StepDefData dataImportTable;

	public I_BPartner_StepDef(
			@NonNull final I_BPartner_StepDefData iBPartnerTable,
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final C_BPartner_Location_StepDefData bpLocationTable,
			@NonNull final C_BP_BankAccount_StepDefData bpBankAccountTable,
			@NonNull final C_DataImport_StepDefData dataImportTable)
	{
		this.iBPartnerTable = iBPartnerTable;
		this.bpartnerTable = bpartnerTable;
		this.bpLocationTable = bpLocationTable;
		this.bpBankAccountTable = bpBankAccountTable;
		this.dataImportTable = dataImportTable;
	}

	@And("I_I_BPartner is found:")
	public void find_I_I_BPartner_record(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final IQueryBuilder<I_I_BPartner> queryBuilder = queryBL.createQueryBuilder(I_I_BPartner.class);

			final String dataRecordIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_DataImport.COLUMNNAME_C_DataImport_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_DataImport dataImportRecord = dataImportTable.get(dataRecordIdentifier);
			queryBuilder.addEqualsFilter(I_I_BPartner.COLUMNNAME_C_DataImport_ID, dataImportRecord.getC_DataImport_ID());

			final String bpValue = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_BPValue);
			if (Check.isNotBlank(bpValue))
			{
				queryBuilder.addStringLikeFilter(I_I_BPartner.COLUMNNAME_BPValue, bpValue, false);
			}

			final String companyName = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_Companyname);
			if (Check.isNotBlank(companyName))
			{
				queryBuilder.addStringLikeFilter(I_I_BPartner.COLUMNNAME_Companyname, companyName, false);
			}

			final String bankDetails = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_BankDetails);
			if (Check.isNotBlank(bankDetails))
			{
				queryBuilder.addStringLikeFilter(I_I_BPartner.COLUMNNAME_BankDetails, bankDetails, false);
			}

			final String iban = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_IBAN);
			if (Check.isNotBlank(iban))
			{
				queryBuilder.addStringLikeFilter(I_I_BPartner.COLUMNNAME_IBAN, iban, false);
			}

			final String qrIban = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_QR_IBAN);
			if (Check.isNotBlank(qrIban))
			{
				queryBuilder.addStringLikeFilter(I_I_BPartner.COLUMNNAME_QR_IBAN, qrIban, false);
			}

			final I_I_BPartner importedRecord = queryBuilder.create()
					.firstOnly();

			final String iBpartnerRecordIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_BPartner.COLUMNNAME_I_BPartner_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			iBPartnerTable.putOrReplace(iBpartnerRecordIdentifier, importedRecord);
		}
	}

	@And("validate I_I_BPartner record:")
	public void validate_I_BPartner_record(@NonNull final DataTable dataTable)
	{
		final SoftAssertions softly = new SoftAssertions();

		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String iBpartnerRecordIdentifier = DataTableUtil.extractStringForColumnName(row, I_I_BPartner.COLUMNNAME_I_BPartner_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_I_BPartner ibpRecord = iBPartnerTable.get(iBpartnerRecordIdentifier);

			final Boolean imported = DataTableUtil.extractBooleanForColumnNameOrNull(row, I_I_BPartner.COLUMNNAME_I_IsImported);
			softly.assertThat(ibpRecord.isI_IsImported()).as("IsImported").isEqualTo(imported);

			final Boolean processed = DataTableUtil.extractBooleanForColumnNameOrNull(row, I_I_BPartner.COLUMNNAME_Processed);
			softly.assertThat(ibpRecord.isProcessed()).as("IsProcessed").isEqualTo(processed);

			final String dataRecordIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_DataImport.COLUMNNAME_C_DataImport_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_DataImport dataImportRecord = dataImportTable.get(dataRecordIdentifier);
			softly.assertThat(ibpRecord.getC_DataImport_ID()).as("C_DataImport_ID").isEqualTo(dataImportRecord.getC_DataImport_ID());

			final String bpValue = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_BPValue);
			if (Check.isNotBlank(bpValue))
			{
				softly.assertThat(ibpRecord.getBPValue()).as("BPValue").isEqualTo(bpValue);
			}

			final String companyName = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_Companyname);
			if (Check.isNotBlank(companyName))
			{
				softly.assertThat(ibpRecord.getCompanyname()).as("Companyname").isEqualTo(companyName);
			}

			final String taxId = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_TaxID);
			if (Check.isNotBlank(taxId))
			{
				softly.assertThat(ibpRecord.getTaxID()).as("TaxID").isEqualTo(taxId);
			}

			final String firstName = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_Firstname);
			if (Check.isNotBlank(firstName))
			{
				softly.assertThat(ibpRecord.getFirstname()).as("Firstname").isEqualTo(firstName);
			}

			final String lastName = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_Lastname);
			if (Check.isNotBlank(lastName))
			{
				softly.assertThat(ibpRecord.getLastname()).as("Lastname").isEqualTo(lastName);
			}

			final Boolean isShipToContactDefault = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_I_BPartner.COLUMNNAME_IsShipToContact_Default, false);
			softly.assertThat(ibpRecord.isShipToContact_Default()).as("IsShipToContact_Default").isEqualTo(isShipToContactDefault);

			final Boolean isBillToContactDefault = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_I_BPartner.COLUMNNAME_IsBillToContact_Default, false);
			softly.assertThat(ibpRecord.isBillToContact_Default()).as("IsBillToContact_Default").isEqualTo(isBillToContactDefault);

			final String address1 = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_Address1);
			if (Check.isNotBlank(address1))
			{
				softly.assertThat(ibpRecord.getAddress1()).as("Address1").isEqualTo(address1);
			}

			final String city = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_City);
			if (Check.isNotBlank(city))
			{
				softly.assertThat(ibpRecord.getCity()).as("City").isEqualTo(city);
			}

			final String countryCode = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_CountryCode);
			if (Check.isNotBlank(countryCode))
			{
				softly.assertThat(ibpRecord.getCountryCode()).as("CountryCode").isEqualTo(countryCode);
			}
			final Boolean isBillToDefault = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_I_BPartner.COLUMNNAME_IsBillToDefault, false);

			softly.assertThat(ibpRecord.isBillToDefault()).as("IsBillToDefault").isEqualTo(isBillToDefault);

			final Boolean isShipToDefault = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_I_BPartner.COLUMNNAME_IsShipToDefault, false);
			softly.assertThat(ibpRecord.isShipToDefault()).as("IsShipToDefault").isEqualTo(isShipToDefault);

			final String adLanguage = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_GroupValue);
			if (Check.isNotBlank(adLanguage))
			{
				softly.assertThat(ibpRecord.getAD_Language()).as("AD_Language").isEqualTo(adLanguage);
			}
			final String orgValue = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_OrgValue);
			if (Check.isNotBlank(orgValue))
			{
				softly.assertThat(ibpRecord.getOrgValue()).as("OrgValue").isEqualTo(orgValue);
			}

			final String postal = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_Postal);
			if (Check.isNotBlank(postal))
			{
				softly.assertThat(ibpRecord.getPostal()).as("Postal").isEqualTo(postal);
			}

			final String bankDetails = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_BankDetails);
			if (Check.isNotBlank(bankDetails))
			{
				softly.assertThat(ibpRecord.getBankDetails()).as("BankDetails").isEqualTo(bankDetails);
			}

			final String iban = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_IBAN);
			if (Check.isNotBlank(iban))
			{
				softly.assertThat(ibpRecord.getIBAN()).as("IBAN").isEqualTo(iban);
			}

			final String qrIban = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_QR_IBAN);
			if (Check.isNotBlank(qrIban))
			{
				softly.assertThat(ibpRecord.getQR_IBAN()).as("QR_IBAN").isEqualTo(qrIban);
			}

			final String bpIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_C_BPartner_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(bpIdentifier))
			{
				final I_C_BPartner bpRecord = InterfaceWrapperHelper.load(ibpRecord.getC_BPartner_ID(), I_C_BPartner.class);
				bpartnerTable.putOrReplace(bpIdentifier, bpRecord);
			}

			final String bpLocationIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_C_BPartner_Location_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(bpLocationIdentifier))
			{
				final I_C_BPartner_Location bpLocationRecord = InterfaceWrapperHelper.load(ibpRecord.getC_BPartner_Location_ID(), I_C_BPartner_Location.class);
				bpLocationTable.putOrReplace(bpLocationIdentifier, bpLocationRecord);
			}

			final String bpBankAccountIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_C_BP_BankAccount_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(bpBankAccountIdentifier))
			{
				final I_C_BP_BankAccount bpBankAccountRecord = InterfaceWrapperHelper.load(ibpRecord.getC_BP_BankAccount_ID(), I_C_BP_BankAccount.class);
				bpBankAccountTable.putOrReplace(bpBankAccountIdentifier, bpBankAccountRecord);
			}

			final String isoCode = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_I_BPartner.COLUMNNAME_ISO_Code);
			if (Check.isNotBlank(isoCode))
			{
				softly.assertThat(ibpRecord.getISO_Code()).as("ISO_Code").isEqualTo(isoCode);
			}

			final Boolean isManuallyCreated = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_I_BPartner.COLUMNNAME_IsManuallyCreated, false);
			softly.assertThat(ibpRecord.isManuallyCreated()).as("IsManuallyCreated").isEqualTo(isManuallyCreated);
		}

		softly.assertAll();
	}
}

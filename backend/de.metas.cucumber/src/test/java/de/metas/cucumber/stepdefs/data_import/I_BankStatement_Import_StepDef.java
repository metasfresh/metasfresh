package de.metas.cucumber.stepdefs.data_import;

import de.metas.banking.BankAccountId;
import de.metas.banking.impexp.BankStatementImportTableSqlUpdater;
import de.metas.cucumber.stepdefs.C_BPartner_StepDef;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.impexp.processing.ImportRecordsSelection;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_I_BankStatement;
import org.compiere.util.DB;

import java.util.Objects;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class I_BankStatement_Import_StepDef
{
	private final I_BankStatement_Import_StepDefData iBankStatementTable;
	private final C_BPartner_StepDefData bPartnerTable;

	public I_BankStatement_Import_StepDef(
			@NonNull final I_BankStatement_Import_StepDefData iBankStatementTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable)
	{
		this.iBankStatementTable = iBankStatementTable;
		this.bPartnerTable = bPartnerTable;
	}

	@Given("the C_BPartner Value unique constraint is relaxed")
	public void the_c_bpartner_value_unique_constraint_is_relaxed()
	{
		DB.executeUpdateAndSaveErrorOnFail(
				"DROP INDEX IF EXISTS C_BPartner_Value_Unique",
				ITrx.TRXNAME_None);
		// Also drop DebtorId/CreditorId unique constraints to allow duplicate IDs
		// NOTE: index names have a typo ("Uniqe") from the original migration 5582410
		DB.executeUpdateAndSaveErrorOnFail(
				"DROP INDEX IF EXISTS C_BPartner_DebtorID_Uniqe",
				ITrx.TRXNAME_None);
		DB.executeUpdateAndSaveErrorOnFail(
				"DROP INDEX IF EXISTS C_BPartner_CreditorID_Uniqe",
				ITrx.TRXNAME_None);
	}

	@Given("metasfresh contains C_BPartners with duplicate Values allowed:")
	public void metasfresh_contains_c_bpartners_with_duplicate_values(@NonNull final DataTable dataTable)
	{
		// Deactivate stale BPartners from previous test runs — by Value and by DebtorId/CreditorId
		final java.util.Set<String> deactivatedKeys = new java.util.HashSet<>();
		DataTableRows.of(dataTable).forEach(row -> {
			final String value = row.getAsString(I_C_BPartner.COLUMNNAME_Value);
			if (deactivatedKeys.add("val:" + value))
			{
				DB.executeUpdateAndSaveErrorOnFail(
						"UPDATE C_BPartner SET IsActive='N' WHERE Value=" + DB.TO_STRING(value)
								+ " AND AD_Client_ID=" + StepDefConstants.CLIENT_ID.getRepoId(),
						ITrx.TRXNAME_None);
			}
			row.getAsOptionalInt(I_C_BPartner.COLUMNNAME_DebtorId).ifPresent(debtorId -> {
				if (debtorId > 0 && deactivatedKeys.add("deb:" + debtorId))
				{
					DB.executeUpdateAndSaveErrorOnFail(
							"UPDATE C_BPartner SET IsActive='N' WHERE DebtorId=" + debtorId
									+ " AND AD_Org_ID=" + StepDefConstants.ORG_ID.getRepoId(),
							ITrx.TRXNAME_None);
				}
			});
			row.getAsOptionalInt(I_C_BPartner.COLUMNNAME_CreditorId).ifPresent(creditorId -> {
				if (creditorId > 0 && deactivatedKeys.add("cred:" + creditorId))
				{
					DB.executeUpdateAndSaveErrorOnFail(
							"UPDATE C_BPartner SET IsActive='N' WHERE CreditorId=" + creditorId
									+ " AND AD_Org_ID=" + StepDefConstants.ORG_ID.getRepoId(),
							ITrx.TRXNAME_None);
				}
			});
		});

		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_BPartner bp = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
			bp.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
			bp.setValue(row.getAsString(I_C_BPartner.COLUMNNAME_Value));
			bp.setName(row.getAsString(I_C_BPartner.COLUMNNAME_Name));
			bp.setIsCustomer(row.getAsOptionalBoolean(I_C_BPartner.COLUMNNAME_IsCustomer).orElseFalse());
			bp.setIsVendor(row.getAsOptionalBoolean(I_C_BPartner.COLUMNNAME_IsVendor).orElseFalse());
			bp.setC_BP_Group_ID(C_BPartner_StepDef.BP_GROUP_ID);

			row.getAsOptionalInt(I_C_BPartner.COLUMNNAME_DebtorId).ifPresent(bp::setDebtorId);
			row.getAsOptionalInt(I_C_BPartner.COLUMNNAME_CreditorId).ifPresent(bp::setCreditorId);

			InterfaceWrapperHelper.saveRecord(bp);

			row.getAsOptionalIdentifier()
					.ifPresent(identifier -> bPartnerTable.putOrReplace(identifier, bp));
		});
	}

	@Given("metasfresh contains I_BankStatement:")
	public void metasfresh_contains_I_BankStatement(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_I_BankStatement record = InterfaceWrapperHelper.newInstance(I_I_BankStatement.class);
			record.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());

			row.getAsOptionalString(I_I_BankStatement.COLUMNNAME_Bill_BPartner_Name)
					.ifPresent(record::setBill_BPartner_Name);
			row.getAsOptionalString(I_I_BankStatement.COLUMNNAME_BPartnerValue)
					.ifPresent(record::setBPartnerValue);
			row.getAsOptionalInt(I_I_BankStatement.COLUMNNAME_DebitorOrCreditorId)
					.ifPresent(record::setDebitorOrCreditorId);
			row.getAsOptionalString(I_I_BankStatement.COLUMNNAME_IBAN)
					.ifPresent(record::setIBAN);

			final boolean isImported = row.getAsOptionalBoolean(I_I_BankStatement.COLUMNNAME_I_IsImported).orElseFalse();
			record.setI_IsImported(isImported);

			// If a C_BPartner_ID identifier is provided, set it (for pre-set scenarios)
			row.getAsOptionalIdentifier(I_I_BankStatement.COLUMNNAME_C_BPartner_ID).ifPresent(bpIdentifier -> {
				final I_C_BPartner bp = bpIdentifier.lookupNotNullIn(bPartnerTable);
				record.setC_BPartner_ID(bp.getC_BPartner_ID());
			});

			InterfaceWrapperHelper.saveRecord(record);

			// Null out C_BPartner_ID in DB if it was not explicitly set,
			// because the ORM sets it to 0 (not NULL) by default
			if (!row.getAsOptionalIdentifier(I_I_BankStatement.COLUMNNAME_C_BPartner_ID).isPresent())
			{
				DB.executeUpdateAndSaveErrorOnFail(
						"UPDATE I_BankStatement SET C_BPartner_ID = NULL WHERE I_BankStatement_ID = " + record.getI_BankStatement_ID(),
						ITrx.TRXNAME_None);
			}

			row.getAsOptionalIdentifier().ifPresent(identifier -> iBankStatementTable.putOrReplace(identifier, record));
		});
	}

	@When("the BankStatementImportTableSqlUpdater is invoked")
	public void the_BankStatementImportTableSqlUpdater_is_invoked()
	{
		// Mark stale I_BankStatement rows from previous test runs as imported
		final String currentIdList = iBankStatementTable.streamRecords()
				.map(r -> String.valueOf(r.getI_BankStatement_ID()))
				.collect(Collectors.joining(","));

		if (!currentIdList.isEmpty())
		{
			DB.executeUpdateAndSaveErrorOnFail(
					"UPDATE I_BankStatement SET I_IsImported='Y' WHERE I_IsImported<>'Y' AND I_BankStatement_ID NOT IN (" + currentIdList + ")",
					ITrx.TRXNAME_None);
		}

		final ImportRecordsSelection selection = ImportRecordsSelection.builder()
				.importTableName(I_I_BankStatement.Table_Name)
				.importKeyColumnName(I_I_BankStatement.COLUMNNAME_I_BankStatement_ID)
				.clientId(ClientId.ofRepoId(StepDefConstants.CLIENT_ID.getRepoId()))
				.build();

		BankStatementImportTableSqlUpdater.updateBankStatementImportTable(
				selection,
				/*orgBankAccountId*/ (BankAccountId)null,
				/*bankStatementName*/ null,
				/*bankStatementDate*/ null,
				/*bankStatementId*/ null);
	}

	@Then("validate I_BankStatement import:")
	public void validate_I_BankStatement(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final StepDefDataIdentifier rowIdentifier = row.getAsIdentifier();
			final I_I_BankStatement storedRecord = rowIdentifier.lookupNotNullIn(iBankStatementTable);
			final int iBankStatementId = storedRecord.getI_BankStatement_ID();

			row.getAsOptionalIdentifier(I_I_BankStatement.COLUMNNAME_C_BPartner_ID).ifPresent(bpIdentifier -> {
				final I_C_BPartner expectedBP = bpIdentifier.lookupNotNullIn(bPartnerTable);
				final int actualBPartnerId = DB.getSQLValueEx(
						ITrx.TRXNAME_None,
						"SELECT COALESCE(C_BPartner_ID, 0) FROM I_BankStatement WHERE I_BankStatement_ID = ?",
						iBankStatementId);
				assertThat(actualBPartnerId)
						.as("I_BankStatement[%s].C_BPartner_ID should match %s", rowIdentifier, bpIdentifier)
						.isEqualTo(expectedBP.getC_BPartner_ID());
			});

			row.getAsOptionalString("C_BPartner_ID.isNull").ifPresent(isNull -> {
				if ("Y".equals(isNull))
				{
					final int actualBPartnerId = DB.getSQLValueEx(
							ITrx.TRXNAME_None,
							"SELECT COALESCE(C_BPartner_ID, 0) FROM I_BankStatement WHERE I_BankStatement_ID = ?",
							iBankStatementId);
					assertThat(actualBPartnerId)
							.as("I_BankStatement[%s].C_BPartner_ID should be NULL", rowIdentifier)
							.isEqualTo(0);
				}
			});

			row.getAsOptionalString(I_I_BankStatement.COLUMNNAME_I_ErrorMsg).ifPresent(expectedSubstring -> {
				final String actualErrorMsg = DB.getSQLValueStringEx(
						ITrx.TRXNAME_None,
						"SELECT I_ErrorMsg FROM I_BankStatement WHERE I_BankStatement_ID = ?",
						iBankStatementId);
				assertThat(actualErrorMsg)
						.as("I_BankStatement[%s].I_ErrorMsg should contain '%s'", rowIdentifier, expectedSubstring)
						.contains(expectedSubstring);
			});

			row.getAsOptionalString("I_ErrorMsg.isNull").ifPresent(isNull -> {
				if ("Y".equals(isNull))
				{
					final String actualErrorMsg = DB.getSQLValueStringEx(
							ITrx.TRXNAME_None,
							"SELECT I_ErrorMsg FROM I_BankStatement WHERE I_BankStatement_ID = ?",
							iBankStatementId);
					assertThat(actualErrorMsg)
							.as("I_BankStatement[%s].I_ErrorMsg should be NULL", rowIdentifier)
							.isNullOrEmpty();
				}
			});
		});
	}
}

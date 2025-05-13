package de.metas.cucumber.stepdefs.payselection;

import de.metas.banking.BankAccountId;
import de.metas.banking.PaySelectionId;
import de.metas.banking.payment.IPaySelectionBL;
import de.metas.banking.payment.IPaySelectionDAO;
import de.metas.banking.payment.IPaySelectionUpdater;
import de.metas.banking.payment.PaySelectionMatchingMode;
import de.metas.banking.payment.PaySelectionTrxType;
import de.metas.bpartner.BPartnerId;
import de.metas.cucumber.stepdefs.C_BP_BankAccount_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_PaySelection;

import java.sql.Timestamp;

@RequiredArgsConstructor
public class C_PaySelection_StepDef
{
	@NonNull private final IPaySelectionBL paySelectionBL = Services.get(IPaySelectionBL.class);
	@NonNull private final IPaySelectionDAO paySelectionDAO = Services.get(IPaySelectionDAO.class);
	@NonNull private final C_PaySelection_StepDefData paySelectionTable;
	@NonNull private final C_BP_BankAccount_StepDefData bankAccountTable;
	@NonNull private final C_BPartner_StepDefData bpartnerTable;

	@And("metasfresh contains C_PaySelections")
	public void createC_PaySelections(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_C_PaySelection.COLUMNNAME_C_PaySelection_ID)
				.forEach(this::createC_PaySelection);
	}

	private void createC_PaySelection(@NonNull final DataTableRow row)
	{
		final I_C_PaySelection paySelection = InterfaceWrapperHelper.newInstance(I_C_PaySelection.class);

		final String name = row.suggestValueAndName().getName();
		final BankAccountId orgBankAccountId = bankAccountTable.getOrgBankAccountId(row.getAsIdentifier(I_C_PaySelection.COLUMNNAME_C_BP_BankAccount_ID));
		final Timestamp payDate = row.getAsLocalDateTimestamp(I_C_PaySelection.COLUMNNAME_PayDate);
		final PaySelectionTrxType trxType = row.getAsEnum(I_C_PaySelection.COLUMNNAME_PaySelectionTrxType, PaySelectionTrxType.class);

		paySelection.setName(name);
		paySelection.setC_BP_BankAccount_ID(orgBankAccountId.getRepoId());
		paySelection.setPayDate(payDate);
		paySelection.setPaySelectionTrxType(trxType.getCode());

		paySelectionDAO.save(paySelection);

		row.getAsOptionalIdentifier()
				.ifPresent(identifier -> paySelectionTable.put(identifier, paySelection));
	}

	@And("create C_PaySelectionLines")
	public void createC_PaySelectionLines(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRows.of(dataTable).singleRow();
		final PaySelectionId paySelectionId = row.getAsIdentifier("C_PaySelection_ID").lookupNotNullIdIn(paySelectionTable);
		final PaySelectionMatchingMode matchingMode = row.getAsEnum("Match", PaySelectionMatchingMode.class);
		final BPartnerId bpartnerId = row.getAsOptionalIdentifier("C_BPartner_ID").map(bpartnerTable::getId).orElse(null);

		final IPaySelectionUpdater paySelectionUpdater = paySelectionBL.newPaySelectionUpdater();
		paySelectionUpdater.setC_PaySelection(paySelectionBL.getById(paySelectionId).orElseThrow());
		// paySelectionUpdater.setOnlyDiscount(p_OnlyDiscount);
		// paySelectionUpdater.setOnlyDue(p_OnlyDue);
		// paySelectionUpdater.setIncludeInDispute(p_IncludeInDispute);
		paySelectionUpdater.setMatchRequirement(matchingMode);
		// paySelectionUpdater.setPayDate(p_PayDate);
		// paySelectionUpdater.setPaymentRule(PaymentRule.ofNullableCode(p_PaymentRule));
		paySelectionUpdater.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
		// paySelectionUpdater.setC_BP_Group_ID(p_C_BP_Group_ID);
		// paySelectionUpdater.setPOReference(p_POReference);
		// paySelectionUpdater.setDateInvoiced(p_DateInvoiced);

		paySelectionUpdater.update();
		System.out.println("C_PaySelectionLines created: " + paySelectionUpdater.getSummary());
	}

	@And("^complete C_PaySelection identified by (.*)$")
	public void createC_PaySelections(@NonNull final String paySelectionIdentifierStr)
	{
		final StepDefDataIdentifier paySelectionIdentifier = StepDefDataIdentifier.ofString(paySelectionIdentifierStr);
		final PaySelectionId paySelectionId = paySelectionTable.getId(paySelectionIdentifier);
		paySelectionBL.complete(paySelectionId);
	}
}
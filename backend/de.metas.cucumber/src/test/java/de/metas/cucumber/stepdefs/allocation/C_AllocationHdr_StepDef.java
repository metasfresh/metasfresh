package de.metas.cucumber.stepdefs.allocation;

import de.metas.acct.api.FactAcctQuery;
import de.metas.allocation.api.PaymentAllocationId;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.accounting.FactAcctMatchersFactory;
import de.metas.cucumber.stepdefs.accounting.FactAcctToTabularStringConverter;
import de.metas.money.MoneyService;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_AllocationHdr;

import javax.annotation.Nullable;

import static de.metas.cucumber.stepdefs.accounting.AccountingCucumberHelper.newFactAcctValidator;
import static de.metas.cucumber.stepdefs.accounting.AccountingCucumberHelper.waitUtilPosted;

public class C_AllocationHdr_StepDef
{

	@NonNull private final C_AllocationHdr_StepDefData allocationHdrTable;

	@NonNull private final FactAcctMatchersFactory factAcctMatchersFactory;
	@NonNull private final FactAcctToTabularStringConverter factAcctTabularStringConverter;

	public C_AllocationHdr_StepDef(
			@NonNull final C_AllocationHdr_StepDefData allocationHdrTable,
			@NonNull final C_BPartner_StepDefData bpartnerTable)
	{
		this.allocationHdrTable = allocationHdrTable;

		@NonNull final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
		@NonNull final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);
		this.factAcctMatchersFactory = FactAcctMatchersFactory.builder()
				.uomDAO(uomDAO)
				.moneyService(moneyService)
				.build();
		this.factAcctTabularStringConverter = FactAcctToTabularStringConverter.builder()
				.uomDAO(uomDAO)
				.moneyService(moneyService)
				.bpartnerTable(bpartnerTable)
				.build();
	}

	@And("^Fact_Acct records are found for payment allocation (.*)$")
	public void validateFactAccts(
			@NonNull final String allocationHdrIdentifierStr,
			@Nullable final DataTable table) throws Throwable
	{
		final StepDefDataIdentifier allocationHdrIdentifier = StepDefDataIdentifier.ofString(allocationHdrIdentifierStr);
		final PaymentAllocationId allocationId = allocationHdrTable.getId(allocationHdrIdentifier);
		final TableRecordReference allocationRef = TableRecordReference.of(I_C_AllocationHdr.Table_Name, allocationId);

		waitUtilPosted(allocationRef);

		newFactAcctValidator()
				.factAcctMatchersFactory(factAcctMatchersFactory)
				.factAcctTabularStringConverter(factAcctTabularStringConverter)
				.expectations(table)
				.query(FactAcctQuery.builder().recordRef(allocationRef).build())
				.validate();
	}

	@And("^no Fact_Acct records are found for payment allocation (.*)$")
	public void assertNoFactAccts(
			@NonNull final String allocationHdrIdentifierStr) throws Throwable
	{
		validateFactAccts(allocationHdrIdentifierStr, null);
	}

}

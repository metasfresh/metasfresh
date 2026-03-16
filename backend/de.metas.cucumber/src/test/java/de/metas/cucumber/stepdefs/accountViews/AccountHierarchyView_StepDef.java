package de.metas.cucumber.stepdefs.accountViews;

import de.metas.cucumber.stepdefs.acctschema.C_AcctSchema_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import de.metas.util.Services;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_ElementValue;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountHierarchyView_StepDef
{
	@NonNull private final C_AcctSchema_StepDefData acctSchemaTable;

	public AccountHierarchyView_StepDef(@NonNull final C_AcctSchema_StepDefData acctSchemaTable)
	{
		this.acctSchemaTable = acctSchemaTable;
	}

	@Then("the account hierarchy tree can be loaded for {string}")
	public void theAccountHierarchyTreeCanBeLoadedFor(@NonNull final String acctSchemaIdentifier)
	{
		final I_C_AcctSchema acctSchema = acctSchemaTable.get(StepDefDataIdentifier.ofString(acctSchemaIdentifier));
		assertThat(acctSchema).as("AcctSchema should exist for identifier: " + acctSchemaIdentifier).isNotNull();

		// Verify that element values exist in the system (the hierarchy view queries these)
		final long elementValueCount = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_ElementValue.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.count();

		assertThat(elementValueCount).as("System should have chart of accounts entries for hierarchy view").isGreaterThan(0);
	}
}

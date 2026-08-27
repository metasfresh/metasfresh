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

package de.metas.cucumber.stepdefs.acctschema;

import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.cache.CacheMgt;
import de.metas.costing.CostingMethod;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.accounting.AccountingCucumberHelper;
import de.metas.cucumber.stepdefs.util.IdentifiersResolver;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.money.CurrencyId;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_AcctSchema;

import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static de.metas.acct.interceptor.C_AcctSchema.DISABLE_CHECK_CURRENCY;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class C_AcctSchema_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);

	/** The costing method is global state shared by every scenario on the executor; see the @After below. */
	@NonNull private final List<CostingMethodOverride> costingMethodOverrides = new ArrayList<>();
	@NonNull private final CurrencyRepository currencyRepository = SpringContextHolder.instance.getBean(CurrencyRepository.class);

	@NonNull private final IdentifiersResolver identifiersResolver;
	@NonNull private final C_AcctSchema_StepDefData acctSchemaTable;

	@And("load C_AcctSchema:")
	public void load_C_AcctSchemas(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_C_AcctSchema.COLUMNNAME_C_AcctSchema_ID)
				.forEach(this::loadAcctSchema);
	}

	@And("update C_AcctSchema:")
	public void update_C_AcctSchemas(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_C_AcctSchema.COLUMNNAME_C_AcctSchema_ID)
				.forEach(this::updateAcctSchema);
	}

	@And("load and update C_AcctSchema:")
	public void loadAndUpdate_C_AcctSchemas(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_C_AcctSchema.COLUMNNAME_C_AcctSchema_ID)
				.forEach(this::loadAndUpdate);
	}

	private void loadAndUpdate(final DataTableRow row)
	{
		loadAcctSchema(row);
		updateAcctSchema(row);
	}

	private void updateAcctSchema(final DataTableRow row)
	{
		final StepDefDataIdentifier identifier = row.getAsIdentifier();
		final I_C_AcctSchema acctSchema = acctSchemaTable.get(identifier);
		final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(acctSchema.getC_AcctSchema_ID());

		final CostingMethod costingMethod = row.getAsOptionalEnum(I_C_AcctSchema.COLUMNNAME_CostingMethod, CostingMethod.class).orElse(null);
		if (costingMethod != null)
		{
			costingMethodOverrides.add(new CostingMethodOverride(acctSchemaId, acctSchema.getCostingMethod()));
			acctSchema.setCostingMethod(costingMethod.getCode());
		}

		row.getAsOptionalString("C_Currency_ID")
				.map(CurrencyCode::ofThreeLetterCode)
				.ifPresent(currencyCode -> {
					final CurrencyId currencyId = currencyRepository.getCurrencyIdByCurrencyCode(currencyCode);
					acctSchema.setC_Currency_ID(currencyId.getRepoId());
					DISABLE_CHECK_CURRENCY.setValue(acctSchema, Boolean.TRUE);
				});

		InterfaceWrapperHelper.saveRecord(acctSchema);

		if (costingMethod != null)
		{
			makeCostingMethodEffective(acctSchemaId, costingMethod);
		}

		acctSchemaTable.putOrReplace(identifier, acctSchema);

		row.getAsOptionalBoolean("IsRepostCreatedDocs")
				.ifTrue(this::repostCreatedDocuments);
	}

	/**
	 * Restores any {@code CostingMethod} overrides back to the value the schema carried before. Fires for
	 * every scenario regardless of pass/fail, so a scenario that changes the costing method cannot leak it
	 * to the rest of the executor even when it fails part-way; no-op when nothing was overridden.
	 */
	@After
	public void resetCostingMethodOverrides()
	{
		// Unwound last-first: a scenario can override the same schema more than once (its Background, then the
		// scenario body), and only reverse order puts back the value that was there before any of them.
		for (int i = costingMethodOverrides.size() - 1; i >= 0; i--)
		{
			final CostingMethodOverride override = costingMethodOverrides.get(i);
			final I_C_AcctSchema acctSchema = InterfaceWrapperHelper.load(override.getAcctSchemaId(), I_C_AcctSchema.class);
			acctSchema.setCostingMethod(override.getOriginalCostingMethod());
			InterfaceWrapperHelper.saveRecord(acctSchema);

			makeCostingMethodEffective(override.getAcctSchemaId(), CostingMethod.ofNullableCode(override.getOriginalCostingMethod()));
		}
		costingMethodOverrides.clear();
	}

	/**
	 * Makes {@code costingMethod} effective for readers, not merely persisted.
	 * <p>
	 * The costing method is global state shared by every scenario on the executor, and {@code saveRecord} emits no
	 * UPDATE when the column already holds the wanted value. Without an UPDATE nothing invalidates
	 * {@link IAcctSchemaDAO}'s cache, which a preceding scenario can have left holding the costing method IT set:
	 * that cache is repopulated from the still-uncommitted row while the preceding write is in flight, and the
	 * write's own invalidation has already fired by then. So reset the cache once the write is committed, and
	 * assert - a schema that still reads stale fails here, at the step that owns it, instead of silently
	 * mis-costing a later scenario.
	 */
	private void makeCostingMethodEffective(@NonNull final AcctSchemaId acctSchemaId, @Nullable final CostingMethod costingMethod)
	{
		CacheMgt.get().reset(I_C_AcctSchema.Table_Name, acctSchemaId.getRepoId());

		if (costingMethod != null)
		{
			assertThat(acctSchemaDAO.getById(acctSchemaId).getCosting().getCostingMethod())
					.as("effective CostingMethod of C_AcctSchema_ID=%s", acctSchemaId.getRepoId())
					.isEqualTo(costingMethod);
		}
	}

	@Value
	private static class CostingMethodOverride
	{
		AcctSchemaId acctSchemaId;
		String originalCostingMethod;
	}

	private void loadAcctSchema(@NonNull final DataTableRow row)
	{
		final @NonNull StepDefDataIdentifier identifier = row.getAsIdentifier();

		final String name = row.getAsOptionalName(I_C_AcctSchema.COLUMNNAME_Name).orElse(null);
		if (Check.isNotBlank(name))
		{
			final I_C_AcctSchema acctSchemaRecord = queryBL.createQueryBuilder(I_C_AcctSchema.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_C_AcctSchema.COLUMNNAME_Name, name)
					.create()
					.firstOnlyNotNull(I_C_AcctSchema.class);

			acctSchemaTable.putOrReplace(identifier, acctSchemaRecord);
		}
	}

	private void repostCreatedDocuments()
	{
		AccountingCucumberHelper.repost(identifiersResolver.getAccountableDocumentRefs());
	}

}

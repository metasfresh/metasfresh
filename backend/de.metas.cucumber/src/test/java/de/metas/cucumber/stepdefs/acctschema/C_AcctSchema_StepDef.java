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

import de.metas.costing.CostingLevel;
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
import lombok.Value;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_AcctSchema;

import java.util.LinkedHashMap;
import java.util.Map;

import static de.metas.acct.interceptor.C_AcctSchema.DISABLE_CHECK_CURRENCY;

@RequiredArgsConstructor
public class C_AcctSchema_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final CurrencyRepository currencyRepository = SpringContextHolder.instance.getBean(CurrencyRepository.class);

	@NonNull private final IdentifiersResolver identifiersResolver;
	@NonNull private final C_AcctSchema_StepDefData acctSchemaTable;

	/**
	 * {@code C_AcctSchema_ID} -> the settings the schema had before this scenario's first {@code update C_AcctSchema},
	 * so {@link #restoreAcctSchemas()} can put them back.
	 */
	private final Map<Integer, AcctSchemaSettings> settingsBeforeScenario = new LinkedHashMap<>();

	/**
	 * Restores the costing method, costing level and currency of every accounting schema this scenario updated.
	 *
	 * <p>The schema is shared by every scenario and feature that runs on the same database, so a setting left behind
	 * would leak into whatever runs next: e.g. a feature that pins {@code CostingLevel=C} would make a later feature
	 * that relies on the seed's level run under client-level costing. Steps that update the schema in a Background
	 * set it again for every scenario.
	 */
	@After
	public void restoreAcctSchemas()
	{
		settingsBeforeScenario.forEach((acctSchemaId, settings) -> {
			final I_C_AcctSchema acctSchema = InterfaceWrapperHelper.load(acctSchemaId, I_C_AcctSchema.class);
			acctSchema.setCostingMethod(settings.getCostingMethod());
			acctSchema.setCostingLevel(settings.getCostingLevel());
			if (acctSchema.getC_Currency_ID() != settings.getCurrencyRepoId())
			{
				acctSchema.setC_Currency_ID(settings.getCurrencyRepoId());
				DISABLE_CHECK_CURRENCY.setValue(acctSchema, Boolean.TRUE);
			}
			InterfaceWrapperHelper.saveRecord(acctSchema);
		});
		settingsBeforeScenario.clear();
	}

	@Value
	private static class AcctSchemaSettings
	{
		String costingMethod;
		String costingLevel;
		int currencyRepoId;
	}

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
		settingsBeforeScenario.computeIfAbsent(acctSchema.getC_AcctSchema_ID(), acctSchemaId -> {
			final I_C_AcctSchema current = InterfaceWrapperHelper.load(acctSchemaId, I_C_AcctSchema.class);
			return new AcctSchemaSettings(current.getCostingMethod(), current.getCostingLevel(), current.getC_Currency_ID());
		});

		row.getAsOptionalEnum(I_C_AcctSchema.COLUMNNAME_CostingMethod, CostingMethod.class).ifPresent(costingMethod -> acctSchema.setCostingMethod(costingMethod.getCode()));
		row.getAsOptionalString(I_C_AcctSchema.COLUMNNAME_CostingLevel)
				.map(CostingLevel::ofCode)
				.ifPresent(costingLevel -> acctSchema.setCostingLevel(costingLevel.getCode()));

		row.getAsOptionalString("C_Currency_ID")
				.map(CurrencyCode::ofThreeLetterCode)
				.ifPresent(currencyCode -> {
					final CurrencyId currencyId = currencyRepository.getCurrencyIdByCurrencyCode(currencyCode);
					acctSchema.setC_Currency_ID(currencyId.getRepoId());
					DISABLE_CHECK_CURRENCY.setValue(acctSchema, Boolean.TRUE);
				});

		InterfaceWrapperHelper.saveRecord(acctSchema);

		acctSchemaTable.putOrReplace(identifier, acctSchema);

		row.getAsOptionalBoolean("IsRepostCreatedDocs")
				.ifTrue(this::repostCreatedDocuments);
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

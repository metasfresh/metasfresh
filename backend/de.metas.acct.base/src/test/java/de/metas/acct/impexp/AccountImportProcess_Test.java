package de.metas.acct.impexp;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.ChartOfAccountsId;
import de.metas.acct.api.IAccountDAO;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.interceptor.C_ElementValue;
import de.metas.elementvalue.ChartOfAccountsRepository;
import de.metas.elementvalue.ChartOfAccountsService;
import de.metas.elementvalue.ElementValueRepository;
import de.metas.elementvalue.ElementValueService;
import de.metas.impexp.format.ImportTableDescriptorRepository;
import de.metas.impexp.processing.DBFunctionsRepository;
import de.metas.treenode.TreeNodeRepository;
import de.metas.treenode.TreeNodeService;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.Mutable;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_I_ElementValue;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Properties;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
@ExtendWith(AdempiereTestWatcher.class)
public class AccountImportProcess_Test
{
	private Properties ctx;

	private ChartOfAccountsService chartOfAccountsService;
	private ElementValueService elementValueService;
	private AccountImportTestHelper testHelper;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		ctx = Env.getCtx();

		SpringContextHolder.registerJUnitBean(new DBFunctionsRepository());
		SpringContextHolder.registerJUnitBean(new ImportTableDescriptorRepository());

		this.chartOfAccountsService = new ChartOfAccountsService(new ChartOfAccountsRepository());
		final TreeNodeRepository treeNodeRepository = new TreeNodeRepository();
		final TreeNodeService treeNodeService = new TreeNodeService(treeNodeRepository, chartOfAccountsService);
		final ElementValueRepository elementValueRepository = new ElementValueRepository();
		this.elementValueService = new ElementValueService(elementValueRepository, treeNodeService);
		this.testHelper = new AccountImportTestHelper(chartOfAccountsService, elementValueService, elementValueRepository, treeNodeService, treeNodeRepository);

		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(new C_ElementValue(Services.get(IAcctSchemaDAO.class), Services.get(IAccountDAO.class), treeNodeService)
		{
			@Override
			protected void createValidCombinationIfNeeded(final I_C_ElementValue elementValue)
			{
				// do nothing to avoid DBException
			}
		});
	}

	private void runImportProcess(final List<I_I_ElementValue> importRecords)
	{
		final AccountImportProcess importProcess = new AccountImportProcess(chartOfAccountsService, elementValueService);
		importProcess.setCtx(ctx);
		importRecords.forEach(importRecord -> importProcess.importRecord(new Mutable<>(), importRecord, false));
		importProcess.afterImport();
	}

	@Test
	public void simpleTreeStructure()
	{
		final AccountImportTestHelper.ImportRecordBuilder importRecordTemplate = AccountImportTestHelper.importRecord()
				.chartOfAccountsName("Import Account")
				.accountType("A")
				.accountSign("N");

		//@formatter:off
		final List<I_I_ElementValue> importRecords = ImmutableList.of(
				importRecordTemplate.value("1"    ).name("Aktiven"              ).parentValue(null ).summary(true ).postActual(false).postBudget(false).postStatistical(false).docControlled(false).build(),
				importRecordTemplate.value("10"   ).name("Umlaufvermögen"       ).parentValue("1"  ).summary(true ).postActual(false).postBudget(false).postStatistical(false).docControlled(false).build(),
				importRecordTemplate.value("100"  ).name("Total flüssige Mittel").parentValue("10" ).summary(true ).postActual(false).postBudget(false).postStatistical(false).docControlled(false).build(),
				importRecordTemplate.value("10000").name("Kasse"                ).parentValue("100").summary(false).postActual(true ).postBudget(true ).postStatistical(true ).docControlled(false).defaultAccountName("CASH_ACCOUNT_NAME").build()
		);
		//@formatter:on

		runImportProcess(importRecords);

		importRecords.forEach(testHelper::assertImported);

		final ChartOfAccountsId chartOfAccountsId = CollectionUtils.singleElement(AccountImportTestHelper.extractChartOfAccountsIds(importRecords));
		testHelper.assertTreeStructureIsUpToDate(chartOfAccountsId);
	}
}

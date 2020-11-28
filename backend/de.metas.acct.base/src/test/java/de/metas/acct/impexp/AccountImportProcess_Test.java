package de.metas.acct.impexp;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.Mutable;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_I_ElementValue;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.impexp.format.ImportTableDescriptorRepository;
import de.metas.impexp.processing.DBFunctionsRepository;

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
public class AccountImportProcess_Test
{
	private Properties ctx;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		ctx = Env.getCtx();

		SpringContextHolder.registerJUnitBean(new DBFunctionsRepository());
		SpringContextHolder.registerJUnitBean(new ImportTableDescriptorRepository());
	}

	@Test
	public void testAccountImport()
	{
		final List<I_I_ElementValue> ievs = prepareImportElementValue();

		final AccountImportProcess importProcess = new AccountImportProcess();
		importProcess.setCtx(ctx);

		ievs.forEach(iElelemntValue -> importProcess.importRecord(new Mutable<>(), iElelemntValue, false /* isInsertOnly */));

		ievs.forEach(iElelemntValue -> AccountImportTestHelper.assertImported(iElelemntValue));
	}

	/**
	 * Build a test case for import<br>
	 * <br>
	 * <code>ElementName	 Value	 Name        			ParentValue	 AccountType	AccountSign	IsSummary	PostActual	PostBudget	PostStatistical	IsDocControlled	</code><br>
	 * <code>Import Account	 1	    Aktiven	  			   					 A				N          Y            N            N             N              N         </code><br>
	 * <code>Import Account	 10	    Umlaufvermögen	  			1		     A				N          Y            N            N             N              N         </code><br>
	 * <code>Import Account	 100    Total flüssige Mittel	  	10		     A				N          Y            N            N             N              N         </code><br>
	 * <code>Import Account	 10000  Kasse	  					100		     A				N          N            Y            Y             Y              N         </code><br>
	 *
	 * @param lines
	 */
	private List<I_I_ElementValue> prepareImportElementValue()
	{
		final List<I_I_ElementValue> elements = new ArrayList<>();

		I_I_ElementValue iev = IElementValueFactory.builder()
				.elementName("Import Account")
				.value("1")
				.name("Aktiven")
				.accountType("A")
				.accountSign("N")
				.summary(true)
				.postActual(false)
				.postBudget(false)
				.postStatistical(false)
				.docControlled(false)
				.build();
		elements.add(iev);

		iev = IElementValueFactory.builder()
				.elementName("Import Account")
				.value("10")
				.name("Umlaufvermögen")
				.parentValue("1")
				.accountType("A")
				.accountSign("N")
				.summary(true)
				.postActual(false)
				.postBudget(false)
				.postStatistical(false)
				.docControlled(false)
				.build();
		elements.add(iev);

		iev = IElementValueFactory.builder()
				.elementName("Import Account")
				.value("100")
				.name("Total flüssige Mittel")
				.parentValue("10")
				.accountType("A")
				.accountSign("N")
				.summary(true)
				.postActual(false)
				.postBudget(false)
				.postStatistical(false)
				.docControlled(false)
				.build();
		elements.add(iev);

		iev = IElementValueFactory.builder()
				.elementName("Import Account")
				.value("10000")
				.name("Kasse")
				.parentValue("100")
				.accountType("A")
				.accountSign("N")
				.summary(false)
				.postActual(true)
				.postBudget(true)
				.postStatistical(true)
				.docControlled(false)
				.build();
		elements.add(iev);

		return elements;
	}
}

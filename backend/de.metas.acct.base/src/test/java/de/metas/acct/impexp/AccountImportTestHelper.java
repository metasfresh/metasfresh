package de.metas.acct.impexp;

import lombok.Builder;
import lombok.experimental.UtilityClass;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Element;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_I_ElementValue;

import static org.assertj.core.api.Assertions.assertThat;

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

/**
 * A collection of account import test helpers.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@UtilityClass
class AccountImportTestHelper
{
	@Builder(builderMethodName = "importRecord", builderClassName = "ImportRecordBuilder")
	private static I_I_ElementValue createImportRecord(
			String elementName,
			String value,
			String name,
			String parentValue,
			String accountType,
			String accountSign,
			boolean summary,
			boolean postActual,
			boolean postBudget,
			boolean postStatistical,
			boolean docControlled)
	{
		final I_I_ElementValue importRecord = InterfaceWrapperHelper.newInstance(I_I_ElementValue.class);
		importRecord.setElementName(elementName);
		importRecord.setParentValue(parentValue);
		importRecord.setValue(value);
		importRecord.setName(name);
		importRecord.setAccountSign(accountSign);
		importRecord.setAccountType(accountType);
		importRecord.setPostActual(postActual);
		importRecord.setPostBudget(postBudget);
		importRecord.setPostStatistical(postStatistical);
		importRecord.setIsDocControlled(docControlled);

		InterfaceWrapperHelper.save(importRecord);
		return importRecord;
	}

	public static void assertImported(final I_I_ElementValue importRecord)
	{
		assertElementImported(importRecord);
		assertElementValueImported(importRecord);
	}

	public static void assertElementImported(final I_I_ElementValue importRecord)
	{
		final I_C_Element element = importRecord.getC_Element();
		assertThat(element).isNotNull();
		assertThat(element.getName()).isEqualTo(importRecord.getElementName());
	}

	public static void assertElementValueImported(final I_I_ElementValue importRecord)
	{
		final I_C_ElementValue elementValue = importRecord.getC_ElementValue();
		assertThat(elementValue).isNotNull();
		assertThat(elementValue.getValue()).isEqualTo(importRecord.getValue());
		assertThat(elementValue.getName()).isEqualTo(importRecord.getName());
		assertThat(elementValue.getAccountType()).isEqualTo(importRecord.getAccountType());
		assertThat(elementValue.getAccountSign()).isEqualTo(importRecord.getAccountSign());
		assertThat(elementValue.isSummary()).isEqualTo(importRecord.isSummary());
		assertThat(elementValue.isPostActual()).isEqualTo(importRecord.isPostActual());
		assertThat(elementValue.isPostBudget()).isEqualTo(importRecord.isPostBudget());
		assertThat(elementValue.isPostStatistical()).isEqualTo(importRecord.isPostStatistical());
		assertThat(elementValue.isDocControlled()).isEqualTo(importRecord.isDocControlled());
	}

}

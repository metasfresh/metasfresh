package de.metas.acct.impexp;

import static org.assertj.core.api.Assertions.assertThat;

import org.compiere.model.I_C_Element;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_I_ElementValue;

import lombok.experimental.UtilityClass;

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
 *
 */
@UtilityClass
/* package */class AccountImportTestHelper
{
	public static void assertImported(final I_I_ElementValue ielementvalue)
	{
		assertElementImported(ielementvalue);
		assertElementValueImported(ielementvalue);
	}

	public static void assertElementImported(final I_I_ElementValue ielementvalue)
	{
		final I_C_Element element = ielementvalue.getC_Element();
		assertThat(element).isNotNull();
		assertThat(element.getName()).isNotNull();
		assertThat(element.getName()).isEqualTo(ielementvalue.getElementName());
	}

	public static void assertElementValueImported(final I_I_ElementValue ielementvalue)
	{
		final I_C_ElementValue elementValue = ielementvalue.getC_ElementValue();
		assertThat(elementValue).isNotNull();
		assertThat(elementValue.getValue()).isNotNull();
		assertThat(elementValue.getValue()).isEqualTo(ielementvalue.getValue());
		assertThat(elementValue.getName()).isNotNull();
		assertThat(elementValue.getName()).isEqualTo(ielementvalue.getName());
		assertThat(elementValue.getAccountType()).isEqualTo(ielementvalue.getAccountType());
		assertThat(elementValue.getAccountSign()).isEqualTo(ielementvalue.getAccountSign());
		assertThat(elementValue.isSummary()).isEqualTo(ielementvalue.isSummary());
		assertThat(elementValue.isPostActual()).isEqualTo(ielementvalue.isPostActual());
		assertThat(elementValue.isPostBudget()).isEqualTo(ielementvalue.isPostBudget());
		assertThat(elementValue.isPostStatistical()).isEqualTo(ielementvalue.isPostStatistical());
		assertThat(elementValue.isDocControlled()).isEqualTo(ielementvalue.isDocControlled());
		}

}

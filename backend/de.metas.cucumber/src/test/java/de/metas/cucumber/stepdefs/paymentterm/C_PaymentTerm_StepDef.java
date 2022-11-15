/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.paymentterm;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_PaymentTerm;

import java.util.Map;

public class C_PaymentTerm_StepDef
{
	final C_PaymentTerm_StepDefData paymentTermTable;

	public C_PaymentTerm_StepDef(@NonNull final C_PaymentTerm_StepDefData paymentTermTable)
	{
		this.paymentTermTable = paymentTermTable;
	}

	@And("load C_PaymentTerm by id:")
	public void loadC_PaymentTerm(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final int paymentTermId = DataTableUtil.extractIntForColumnName(row, I_C_PaymentTerm.COLUMNNAME_C_PaymentTerm_ID);

			final I_C_PaymentTerm paymentTerm = InterfaceWrapperHelper.load(paymentTermId, I_C_PaymentTerm.class);

			final String paymentTermIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_PaymentTerm.COLUMNNAME_C_PaymentTerm_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			paymentTermTable.put(paymentTermIdentifier, paymentTerm);
		}
	}

	@And("validate C_PaymentTerm:")
	public void validateC_PaymentTerm(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final SoftAssertions softly = new SoftAssertions();

			final String value = DataTableUtil.extractStringForColumnName(row, I_C_PaymentTerm.COLUMNNAME_Value);
			final String name = DataTableUtil.extractStringForColumnName(row, I_C_PaymentTerm.COLUMNNAME_Name);

			final String incotermsIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_PaymentTerm.COLUMNNAME_C_PaymentTerm_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_C_PaymentTerm paymentTerm = paymentTermTable.get(incotermsIdentifier);

			softly.assertThat(paymentTerm.getValue()).isEqualTo(value);
			softly.assertThat(paymentTerm.getName()).isEqualTo(name);

			softly.assertAll();
		}
	}
}

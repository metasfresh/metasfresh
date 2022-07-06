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

package de.metas.cucumber.stepdefs.ddorder;

import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.util.Services;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_OrderLine;
import org.eevolution.model.I_DD_OrderLine;

import static org.assertj.core.api.Assertions.*;

public class DD_OrderLine_StepDef
{
	private final C_OrderLine_StepDefData orderLineTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public DD_OrderLine_StepDef(@NonNull final C_OrderLine_StepDefData orderLineTable)
	{
		this.orderLineTable = orderLineTable;
	}

	@And("^validate no DD_OrderLine found for orderLine (.*)$")
	public void validate_no_DD_OrderLine_found(@NonNull final String orderLineIdentifier)
	{
		final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);
		assertThat(orderLine).isNotNull();

		final int noOfRecords = queryBL.createQueryBuilder(I_DD_OrderLine.class)
				.addEqualsFilter(I_DD_OrderLine.COLUMN_C_OrderLineSO_ID, orderLine.getC_OrderLine_ID())
				.create()
				.count();

		assertThat(noOfRecords).isEqualTo(0);
	}
}
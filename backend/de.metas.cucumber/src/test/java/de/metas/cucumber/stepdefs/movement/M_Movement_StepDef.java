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

package de.metas.cucumber.stepdefs.movement;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Locator_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.distributionorder.DD_OrderLine_StepDefData;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_MovementLine;
import org.eevolution.model.I_DD_OrderLine;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class M_Movement_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final DD_OrderLine_StepDefData ddOrderLineTable;
	private final M_Locator_StepDefData locatorTable;

	public M_Movement_StepDef(
			@NonNull final DD_OrderLine_StepDefData ddOrderLineTable,
			@NonNull final M_Locator_StepDefData locatorTable)
	{
		this.ddOrderLineTable = ddOrderLineTable;
		this.locatorTable = locatorTable;
	}

	@And("validate I_M_MovementLine for distribution order line")
	public void validate_movement_line(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			final String orderLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_MovementLine.COLUMNNAME_DD_OrderLine_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_DD_OrderLine orderLine = ddOrderLineTable.get(orderLineIdentifier);

			final String locatorToIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_MovementLine.COLUMNNAME_M_LocatorTo_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_M_Locator toLocator = locatorTable.get(locatorToIdentifier);

			final BigDecimal movementQty = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_M_MovementLine.COLUMNNAME_MovementQty);

			final I_M_MovementLine movementLine = queryBL.createQueryBuilder(I_M_MovementLine.class)
					.addEqualsFilter(I_M_MovementLine.COLUMNNAME_DD_OrderLine_ID, orderLine.getDD_Order_ID())
					.addEqualsFilter(I_M_MovementLine.COLUMNNAME_M_LocatorTo_ID, toLocator.getM_Locator_ID())
					.create()
					.firstOnly(I_M_MovementLine.class);

			assertThat(movementLine).isNotNull();
			assertThat(movementLine.getMovementQty()).isEqualTo(movementQty);
		}
	}

}

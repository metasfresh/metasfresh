/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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


package de.metas.cucumber.stepdefs.order;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.order.returnpackage.PalletType;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Order_ReturnPackage;

import javax.annotation.Nullable;
import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Steps for the return packages (Rücknahme Gebinde) of a sales order.
 */
@RequiredArgsConstructor
public class C_Order_ReturnPackage_StepDef
{
	@NonNull private final C_Order_StepDefData orderTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Types the delivered / returned LU quantities into the order's existing return-package rows, the way a user does in the
	 * order tab. The rows themselves are not created here: the step fails if the order has no active row of the given pallet type,
	 * so a scenario using it also proves that the rows were created with the order.
	 * <p>
	 * Columns: {@code PalletType} (code, e.g. {@code EUR}, {@code H1}), {@code OPT.QtyDeliveredLU}, {@code OPT.QtyReturnedLU}.
	 * An empty cell leaves the quantity empty (null), like a field the user did not fill in.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And the C_Order_ReturnPackage rows of the order identified by so_1 are updated:
	 *   | PalletType | OPT.QtyDeliveredLU | OPT.QtyReturnedLU |
	 *   | EUR        | 1                  |                   |
	 *   | H1         |                    | 2                 |
	 * </pre>
	 */
	@And("the C_Order_ReturnPackage rows of the order identified by {word} are updated:")
	public void update_return_packages(@NonNull final String orderIdentifier, @NonNull final DataTable dataTable)
	{
		final I_C_Order order = orderTable.get(orderIdentifier);
		DataTableRows.of(dataTable).forEach(row -> updateReturnPackage(order, row));
	}

	private void updateReturnPackage(@NonNull final I_C_Order order, @NonNull final DataTableRow row)
	{
		final PalletType palletType = PalletType.ofCode(row.getAsString(I_C_Order_ReturnPackage.COLUMNNAME_PalletType));

		final I_C_Order_ReturnPackage returnPackage = queryBL.createQueryBuilder(I_C_Order_ReturnPackage.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Order_ReturnPackage.COLUMNNAME_C_Order_ID, order.getC_Order_ID())
				.addEqualsFilter(I_C_Order_ReturnPackage.COLUMNNAME_PalletType, palletType.getCode())
				.create()
				.firstOnly(I_C_Order_ReturnPackage.class);
		assertThat(returnPackage)
				.as("active C_Order_ReturnPackage row with PalletType=%s of C_Order_ID=%s", palletType.getCode(), order.getC_Order_ID())
				.isNotNull();

		returnPackage.setQtyDeliveredLU(toQtyOrNull(row, I_C_Order_ReturnPackage.COLUMNNAME_QtyDeliveredLU));
		returnPackage.setQtyReturnedLU(toQtyOrNull(row, I_C_Order_ReturnPackage.COLUMNNAME_QtyReturnedLU));
		saveRecord(returnPackage);
	}

	@Nullable
	private static BigDecimal toQtyOrNull(@NonNull final DataTableRow row, @NonNull final String columnName)
	{
		return row.getAsOptionalString(columnName)
				.filter(Check::isNotBlank)
				.map(NumberUtils::asBigDecimal)
				.orElse(null);
	}
}

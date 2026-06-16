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

package de.metas.cucumber.stepdefs;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.util.OptionalBoolean;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.compiere.model.I_M_Locator;

import javax.annotation.Nullable;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.I_M_Locator.COLUMNNAME_M_Locator_ID;
import static org.compiere.model.I_M_Warehouse.COLUMNNAME_M_Warehouse_ID;

@RequiredArgsConstructor
public class M_Locator_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final M_Warehouse_StepDefData warehouseTable;
	@NonNull private final M_Locator_StepDefData locatorTable;
	@NonNull private final TestContext restTestContext;

	/**
	 * Loads existing {@code M_Locator} records by warehouse + Value and stores them in the StepDefData table
	 * so they can be referenced by later steps.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code Value} — the locator's unique value within the warehouse; used as the lookup key.</li>
	 *   <li>{@code M_Warehouse_ID} — identifier of a previously created warehouse.</li>
	 * </ul>
	 *
	 * <p>Optional columns:
	 * <ul>
	 *   <li>{@code Identifier} — name under which to store the record in StepDefData for later reference.</li>
	 *   <li>{@code REST.Context.QRCode} — variable name under which to store the locator's QR-code JSON string
	 *       in the REST test context (used by REST API scenarios that pass QR codes in request headers).</li>
	 * </ul>
	 *
	 * <p>Gherkin usage:
	 * <pre>
	 * And load M_Locator:
	 *   | Value    | M_Warehouse_ID | Identifier  |
	 *   | Standard | stockWH        | stockLocator |
	 * </pre>
	 */
	@And("load M_Locator:")
	public void loadLocators(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(COLUMNNAME_M_Locator_ID)
				.forEach(this::loadLocator);
	}

	private void loadLocator(final DataTableRow row)
	{
		final String value = row.getAsString(I_M_Locator.COLUMNNAME_Value);

		final WarehouseId warehouseId = warehouseTable.getId(row.getAsIdentifier(COLUMNNAME_M_Warehouse_ID));

		final I_M_Locator locatorRecord = queryBL.createQueryBuilder(I_M_Locator.class)
				.addEqualsFilter(I_M_Locator.COLUMNNAME_M_Warehouse_ID, warehouseId)
				.addEqualsFilter(I_M_Locator.COLUMNNAME_Value, value)
				.orderByDescending(COLUMNNAME_M_Locator_ID)
				.create()
				.firstNotNull(I_M_Locator.class);

		row.getAsOptionalIdentifier()
				.ifPresent(locatorIdentifier -> locatorTable.put(locatorIdentifier, locatorRecord));

		row.getAsOptionalString("REST.Context.QRCode")
				.ifPresent(restVariableName -> {
					final String qrCodeString = LocatorQRCode.ofLocator(locatorRecord).toGlobalQRCodeJsonString();
					restTestContext.setVariable(restVariableName, qrCodeString);
				});
	}

	/**
	 * Creates (or updates) {@code M_Locator} records. Existing locators are matched by
	 * ({@code M_Warehouse_ID}, {@code Value}); a new one is created when none matches.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Warehouse_ID</b> — (required, identifier-ref) the warehouse the locator belongs to<br>
	 *   <b>Value</b> — (optional) locator Value; auto-suggested when omitted<br>
	 *   <b>IsDefault</b> — (optional, Y/N) defaults to {@code Y} on create<br>
	 *   <b>IsGroundLocator</b> — (optional, Y/N) marks the locator as a ground-floor picking locator (sourced by {@code DDOrderPickingReplenishmentService} and the mobile "Lagerort leer" resolver); defaults to {@code N} on create<br>
	 *   <b>PriorityNo</b> — (optional) order key for ground locators, ascending; defaults to {@code 50} on create<br>
	 *   <b>X</b> / <b>X1</b> / <b>Y</b> / <b>Z</b> — (optional) warehouse coordinates; each defaults to {@code "0"} on create<br>
	 * @cucumber.depends StepDefData: M_Warehouse_StepDefData, M_Locator_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains M_Locator:
	 *   | Identifier | M_Warehouse_ID | Value | IsGroundLocator | PriorityNo |
	 *   | locatorA   | stockWH        | loc-A | Y               | 10         |
	 *   | locatorB   | stockWH        | loc-B | Y               | 20         |
	 * </pre>
	 */
	@And("metasfresh contains M_Locator:")
	public void create_M_Locator_Simple(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(COLUMNNAME_M_Locator_ID)
				.forEach((row) -> {
					final String value = row.suggestValueAndName().getValue();

					final StepDefDataIdentifier warehouseIdentifier = row.getAsIdentifier(COLUMNNAME_M_Warehouse_ID);
					final WarehouseId warehouseId = warehouseTable.getIdOptional(warehouseIdentifier)
							.orElseGet(() -> warehouseIdentifier.getAsId(WarehouseId.class));

					final I_M_Locator locatorRecord = CoalesceUtil.coalesceSuppliers(
							() -> getExistingLocator(warehouseId, value),
							() -> InterfaceWrapperHelper.newInstance(I_M_Locator.class));
					assertThat(locatorRecord).isNotNull();
					final boolean isNew = InterfaceWrapperHelper.isNew(locatorRecord);

					locatorRecord.setValue(value);
					locatorRecord.setM_Warehouse_ID(warehouseId.getRepoId());

					final OptionalBoolean isDefault = row.getAsOptionalBoolean(I_M_Locator.COLUMNNAME_IsDefault);
					if (isNew || isDefault.isPresent())
					{
						locatorRecord.setIsDefault(isDefault.orElse(true));
					}

					final OptionalBoolean isGroundLocator = row.getAsOptionalBoolean(I_M_Locator.COLUMNNAME_IsGroundLocator);
					if (isNew || isGroundLocator.isPresent())
					{
						locatorRecord.setIsGroundLocator(isGroundLocator.orElse(false));
					}

					final Optional<Integer> priorityNo = row.getAsOptionalInt(I_M_Locator.COLUMNNAME_PriorityNo);
					if (isNew || priorityNo.isPresent())
					{
						locatorRecord.setPriorityNo(priorityNo.orElse(50));
					}

					final Optional<String> x = row.getAsOptionalString(I_M_Locator.COLUMNNAME_X);
					if (isNew || x.isPresent())
					{
						locatorRecord.setX(x.orElse("0"));
					}
					final Optional<String> x1 = row.getAsOptionalString(I_M_Locator.COLUMNNAME_X1);
					if (isNew || x1.isPresent())
					{
						locatorRecord.setX1(x1.orElse("0"));
					}
					final Optional<String> y = row.getAsOptionalString(I_M_Locator.COLUMNNAME_Y);
					if (isNew || y.isPresent())
					{
						locatorRecord.setY(y.orElse("0"));
					}
					final Optional<String> z = row.getAsOptionalString(I_M_Locator.COLUMNNAME_Z);
					if (isNew || z.isPresent())
					{
						locatorRecord.setZ(z.orElse("0"));
					}

					InterfaceWrapperHelper.saveRecord(locatorRecord);

					row.getAsIdentifier().put(locatorTable, locatorRecord);
				});
	}

	@Nullable
	private I_M_Locator getExistingLocator(final WarehouseId warehouseId, final String value)
	{
		return queryBL.createQueryBuilder(I_M_Locator.class)
				.addEqualsFilter(I_M_Locator.COLUMNNAME_M_Warehouse_ID, warehouseId)
				.addEqualsFilter(I_M_Locator.COLUMNNAME_Value, value)
				.create()
				.firstOnlyOrNull(I_M_Locator.class);
	}
}

/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.cucumber.stepdefs.shipment;

import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Locator_StepDefData;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSetInstance_StepDefData;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.cucumber.stepdefs.hu.M_HU_PI_Item_Product_StepDefData;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.inout.InOutId;
import de.metas.logging.LogManager;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.compiere.model.I_M_InOutLine.COLUMNNAME_M_InOutLine_ID;
import static org.compiere.model.I_M_InOutLine.COLUMNNAME_M_InOut_ID;
import static org.compiere.model.I_M_InOutLine.COLUMNNAME_M_Locator_ID;
import static org.compiere.model.I_M_InOutLine.COLUMNNAME_M_Product_ID;
import static org.compiere.model.I_M_InOutLine.COLUMNNAME_MovementQty;
import static org.compiere.model.I_M_InOutLine.COLUMNNAME_QtyEntered;

@RequiredArgsConstructor
public class M_InOut_Line_StepDef
{
	private final Logger logger = LogManager.getLogger(M_InOut_Line_StepDef.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	private final M_InOut_StepDefData shipmentTable;
	private final M_InOutLine_StepDefData shipmentLineTable;
	private final C_OrderLine_StepDefData orderLineTable;
	private final M_Product_StepDefData productTable;
	private final M_Locator_StepDefData locatorTable;
	private final M_HU_PI_Item_Product_StepDefData huPIItemProductTable;
	private final M_AttributeSetInstance_StepDefData asiTable;

	@And("^validate the created (shipment|material receipt) lines$")
	public void validate_created_M_InOutLines(@NonNull final String model_UNUSED, @NonNull final DataTable table)
	{
		DataTableRows.of(table).forEach(this::validateAndLoadInOutLine);
	}

	private void validateAndLoadInOutLine(final DataTableRow row)
	{
		logger.info("validate_created_M_InOutLine: {}", row);

		final InOutId shipmentId = shipmentTable.getId(row.getAsIdentifier("M_InOut_ID"));

		final StepDefDataIdentifier productIdentifier = row.getAsIdentifier(COLUMNNAME_M_Product_ID);
		final int expectedProductId = productTable.getOptional(productIdentifier)
				.map(I_M_Product::getM_Product_ID)
				.orElseGet(productIdentifier::getAsInt);

		//dev-note: we assume the tests are not using the same product on different lines
		final IQueryBuilder<I_M_InOutLine> lineQueryBuilder = queryBL.createQueryBuilder(I_M_InOutLine.class)
				.addEqualsFilter(I_M_InOutLine.COLUMNNAME_M_InOut_ID, shipmentId)
				.addEqualsFilter(COLUMNNAME_M_Product_ID, expectedProductId);

		row.getAsOptionalIdentifier(I_M_InOutLine.COLUMNNAME_C_OrderLine_ID)
				.map(orderLineTable::getId)
				.ifPresent(orderLineId -> lineQueryBuilder.addEqualsFilter(I_M_InOutLine.COLUMNNAME_C_OrderLine_ID, orderLineId));
		row.getAsOptionalBigDecimal(de.metas.inout.model.I_M_InOutLine.COLUMNNAME_QualityDiscountPercent)
				.ifPresent(qualityDiscountPercent -> lineQueryBuilder.addEqualsFilter(de.metas.inout.model.I_M_InOutLine.COLUMNNAME_QualityDiscountPercent, qualityDiscountPercent));

		final I_M_InOutLine shipmentLineRecord = getSingleShipmentLine(lineQueryBuilder.create());
		row.getAsIdentifier(I_M_InOutLine.COLUMNNAME_M_InOutLine_ID).putOrReplace(shipmentLineTable, shipmentLineRecord);
	}

	private static I_M_InOutLine getSingleShipmentLine(final IQuery<I_M_InOutLine> query)
	{
		final List<I_M_InOutLine> lines = query.list();
		if (lines.isEmpty())
		{
			throw new AdempiereException("No shipment lines found")
					.appendParametersToMessage()
					.setParameter("query", query);
		}
		else if (lines.size() > 1)
		{
			throw new AdempiereException("More than one shipment line found")
					.appendParametersToMessage()
					.setParameter("query", query)
					.setParameter("lines", lines);
		}
		else
		{
			return lines.get(0);
		}
	}

	@And("^validate the created (shipment|material receipt) lines by id$")
	public void validateShipmentLinesById(@NonNull final String model_UNUSED, @NonNull final DataTable table)
	{
		DataTableRows.of(table).forEach((row) -> {
			final I_M_InOutLine shipmentLine = InterfaceWrapperHelper.create(row.getAsIdentifier().lookupIn(shipmentLineTable), I_M_InOutLine.class);
			SharedTestContext.put("shipmentLine", shipmentLine);

			validateShipmentLine(shipmentLine, row);
		});
	}

	@And("^validate the (shipment|material receipt) lines do not exist$")
	public void validate_no_created_M_InOutLine(@NonNull final String model_UNUSED, @NonNull final DataTable table)
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			final String shipmentIdentifier = DataTableUtil.extractStringForColumnName(row, "M_InOut_ID.Identifier");

			final I_M_InOut shipmentRecord = shipmentTable.get(shipmentIdentifier);

			final String productIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final Integer expectedProductId = productTable.get(productIdentifier).getM_Product_ID();

			//dev-note: we assume the tests are not using the same product on different lines
			final IQueryBuilder<I_M_InOutLine> lineQueryBuilder = queryBL.createQueryBuilder(I_M_InOutLine.class)
					.addEqualsFilter(I_M_InOutLine.COLUMNNAME_M_InOut_ID, shipmentRecord.getM_InOut_ID())
					.addEqualsFilter(COLUMNNAME_M_Product_ID, expectedProductId);

			final String orderLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_InOutLine.COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(orderLineIdentifier))
			{
				final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);
				lineQueryBuilder.addEqualsFilter(I_M_InOutLine.COLUMNNAME_C_OrderLine_ID, orderLine.getC_OrderLine_ID());
			}

			final I_M_InOutLine shipmentLineRecord = lineQueryBuilder
					.create()
					.firstOnlyOrNull(I_M_InOutLine.class);

			assertThat(shipmentLineRecord).isNull();
		}
	}

	@And("update M_InOutLine:")
	public void update_M_InOutLine(@NonNull final DataTable table)
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			final String shipmentLineIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_InOutLine.COLUMNNAME_M_InOutLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_InOutLine shipmentLine = shipmentLineTable.get(shipmentLineIdentifier, I_M_InOutLine.class);
			assertThat(shipmentLine).isNotNull();

			final BigDecimal qtyEntered = DataTableUtil.extractBigDecimalForColumnName(row, I_M_InOutLine.COLUMNNAME_QtyEntered);
			shipmentLine.setQtyEntered(qtyEntered);

			final BigDecimal movementQty = DataTableUtil.extractBigDecimalForColumnName(row, I_M_InOutLine.COLUMNNAME_MovementQty);
			shipmentLine.setMovementQty(movementQty);

			InterfaceWrapperHelper.saveRecord(shipmentLine);

			shipmentLineTable.putOrReplace(shipmentLineIdentifier, shipmentLine);
		}
	}

	@And("metasfresh contains M_InOutLine")
	public void metasfresh_contains_M_InOutLine(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> table = dataTable.asMaps();
		for (final Map<String, String> row : table)
		{
			final de.metas.inout.model.I_M_InOutLine inOutLine = InterfaceWrapperHelper.newInstance(de.metas.inout.model.I_M_InOutLine.class);

			final String inOutIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_InOut_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_InOut inOut = shipmentTable.get(inOutIdentifier);
			assertThat(inOut).isNotNull();

			inOutLine.setM_InOut_ID(inOut.getM_InOut_ID());

			final BigDecimal qtyEntered = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_QtyEntered);
			inOutLine.setQtyEntered(qtyEntered);

			final BigDecimal movementQty = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_MovementQty);
			inOutLine.setMovementQty(movementQty);

			final String uomCode = DataTableUtil.extractStringForColumnName(row, "UomCode");
			final I_C_UOM uom = uomDAO.getByX12DE355(X12DE355.ofCode(uomCode));
			assertThat(uom).isNotNull();

			inOutLine.setC_UOM_ID(uom.getC_UOM_ID());

			final String productIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(productIdentifier))
			{
				final I_M_Product product = productTable.get(productIdentifier);
				assertThat(product).isNotNull();

				inOutLine.setM_Product_ID(product.getM_Product_ID());
			}

			final String locatorIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_M_Locator_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(locatorIdentifier))
			{
				final I_M_Locator locator = locatorTable.get(locatorIdentifier);
				assertThat(locator).isNotNull();

				inOutLine.setM_Locator_ID(locator.getM_Locator_ID());
			}

			final Boolean isPackingMaterial = DataTableUtil.extractBooleanForColumnNameOrNull(row, "OPT." + de.metas.inout.model.I_M_InOutLine.COLUMNNAME_IsPackagingMaterial);
			if (isPackingMaterial != null)
			{
				inOutLine.setIsPackagingMaterial(isPackingMaterial);
			}

			InterfaceWrapperHelper.saveRecord(inOutLine);

			final String inOutLineIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_InOutLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			shipmentLineTable.putOrReplace(inOutLineIdentifier, inOutLine);
		}
	}

	@And("^validate no M_InOutLine found for M_InOut identified by (.*)$")
	public void validate_no_M_InOutLine_for_inOut(@NonNull final String inOutIdentifier)
	{
		final I_M_InOut inOut = shipmentTable.get(inOutIdentifier);
		assertThat(inOut).isNotNull();

		final I_M_InOutLine inOutLine = queryBL.createQueryBuilder(I_M_InOutLine.class)
				.addEqualsFilter(COLUMNNAME_M_InOut_ID, inOut.getM_InOut_ID())
				.create()
				.firstOnlyOrNull(I_M_InOutLine.class);

		assertThat(inOutLine).isNull();
	}

	private void validateShipmentLine(@NonNull final I_M_InOutLine actual, @NonNull final DataTableRow expected)
	{
		logger.info("validateShipmentLine: expected={}, actual={}", expected, actual);

		final SoftAssertions softly = new SoftAssertions();

		expected.getAsOptionalIdentifier("M_Product_ID")
				.ifPresent(productIdentifier -> {
					final int expectedProductId = productTable.getOptional(productIdentifier)
							.map(I_M_Product::getM_Product_ID)
							.orElseGet(productIdentifier::getAsInt);
					softly.assertThat(actual.getM_Product_ID()).as("M_Product_ID").isEqualTo(expectedProductId);
				});
		expected.getAsOptionalBigDecimal("movementqty")
				.ifPresent(movementQty -> softly.assertThat(actual.getMovementQty()).as("movementqty").isEqualByComparingTo(movementQty));
		expected.getAsOptionalBigDecimal(I_M_InOutLine.COLUMNNAME_QtyDeliveredCatch)
				.ifPresent(qtyDeliveredCatch -> softly.assertThat(actual.getQtyDeliveredCatch()).as("QtyDeliveredCatch").isEqualByComparingTo(qtyDeliveredCatch));
		expected.getAsOptionalBigDecimal(I_M_InOutLine.COLUMNNAME_QtyEntered)
				.ifPresent(qtyEntered -> softly.assertThat(actual.getQtyEntered()).as("QtyEntered").isEqualByComparingTo(qtyEntered));

		expected.getAsOptionalBigDecimal(I_M_InOutLine.COLUMNNAME_QtyEnteredTU)
				.ifPresent(qtyEnteredTU -> softly.assertThat(actual.getQtyEnteredTU()).as("QtyEnteredTU").isEqualByComparingTo(qtyEnteredTU));
		expected.getAsOptionalIdentifier(I_M_InOutLine.COLUMNNAME_M_HU_PI_Item_Product_ID)
				.ifPresent(huPIItemProductIdentifier -> {
					final HUPIItemProductId huPIItemProductId = huPIItemProductIdentifier.isNullPlaceholder() ? null : huPIItemProductTable.getId(huPIItemProductIdentifier);
					softly.assertThat(HUPIItemProductId.ofRepoIdOrNull(actual.getM_HU_PI_Item_Product_ID())).as("M_HU_PI_Item_Product_ID").isEqualTo(huPIItemProductId);
				});

		expected.getAsOptionalString(I_M_InOutLine.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName())
				.map(X12DE355::ofCode)
				.map(uomDAO::getUomIdByX12DE355)
				.ifPresent(uomId -> softly.assertThat(UomId.ofRepoIdOrNull(actual.getC_UOM_ID())).as("C_UOM_ID").isEqualTo(uomId));

		expected.getAsOptionalIdentifier(I_M_InOutLine.COLUMNNAME_M_AttributeSetInstance_ID)
				.ifPresent(asiIdentifier -> {
					final AttributeSetInstanceId asiIdActual = AttributeSetInstanceId.ofRepoIdOrNone(actual.getM_AttributeSetInstance_ID());
					if (asiIdentifier.isNullPlaceholder())
					{
						softly.assertThat(asiIdActual).as("M_AttributeSetInstance_ID").isEqualTo(AttributeSetInstanceId.NONE);
					}
					else
					{
						final AttributeSetInstanceId asiId = asiTable.getIdOptional(asiIdentifier).orElse(null);
						if (asiId == null)
						{
							asiTable.put(asiIdentifier, InterfaceWrapperHelper.load(asiIdActual, I_M_AttributeSetInstance.class));
						}
						else
						{
							softly.assertThat(asiIdActual).as("M_AttributeSetInstance_ID").isEqualTo(asiId);
						}
					}
				});

		expected.getAsOptionalBoolean("processed")
				.ifPresent(processed -> softly.assertThat(actual.isProcessed()).as("processed").isEqualTo(processed));

		softly.assertAll();
	}
}

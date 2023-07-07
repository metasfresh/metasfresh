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

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_ModCntr_Log;
import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Locator_StepDefData;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.contract.C_Flatrate_Term_StepDefData;
import de.metas.cucumber.stepdefs.message.AD_Message_StepDefData;
import de.metas.cucumber.stepdefs.project.C_Project_StepDefData;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_AD_Message;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.compiere.model.I_AD_Message.COLUMNNAME_AD_Message_ID;
import static org.compiere.model.I_M_InOutLine.COLUMNNAME_M_InOutLine_ID;
import static org.compiere.model.I_M_InOutLine.COLUMNNAME_M_InOut_ID;
import static org.compiere.model.I_M_InOutLine.COLUMNNAME_M_Locator_ID;
import static org.compiere.model.I_M_InOutLine.COLUMNNAME_M_Product_ID;
import static org.compiere.model.I_M_InOutLine.COLUMNNAME_MovementQty;
import static org.compiere.model.I_M_InOutLine.COLUMNNAME_QtyEntered;

public class M_InOut_Line_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomDao = Services.get(IUOMDAO.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	private final M_InOut_StepDefData shipmentTable;
	private final M_InOutLine_StepDefData shipmentLineTable;
	private final C_OrderLine_StepDefData orderLineTable;
	private final M_Product_StepDefData productTable;
	private final C_Project_StepDefData projectTable;
	private final M_Locator_StepDefData locatorTable;

	private final C_Flatrate_Term_StepDefData flatrateTermTable;
	private final AD_Message_StepDefData messageTable;

	public M_InOut_Line_StepDef(
			@NonNull final M_InOut_StepDefData shipmentTable,
			@NonNull final M_InOutLine_StepDefData shipmentLineTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_Project_StepDefData projectTable,
			@NonNull final M_Locator_StepDefData locatorTable,
			@NonNull final C_Flatrate_Term_StepDefData flatrateTermTable,
			@NonNull final AD_Message_StepDefData messageTable)
	{
		this.shipmentTable = shipmentTable;
		this.shipmentLineTable = shipmentLineTable;
		this.orderLineTable = orderLineTable;
		this.productTable = productTable;
		this.projectTable = projectTable;
		this.locatorTable = locatorTable;
		this.flatrateTermTable = flatrateTermTable;
		this.messageTable = messageTable;
	}

	@And("^validate the created (shipment|material receipt) lines$")
	public void validate_created_M_InOutLine(@NonNull final String model_UNUSED, @NonNull final DataTable table)
	{
		final List<Map<String, String>> dataTable = table.asMaps();
		for (final Map<String, String> row : dataTable)
		{
			final String shipmentIdentifier = DataTableUtil.extractStringForColumnName(row, "M_InOut_ID.Identifier");

			final I_M_InOut shipmentRecord = shipmentTable.get(shipmentIdentifier);

			final String productIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
			final Integer expectedProductId = productTable.getOptional(productIdentifier)
					.map(I_M_Product::getM_Product_ID)
					.orElseGet(() -> Integer.parseInt(productIdentifier));

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

			final BigDecimal qualityDiscountPercent = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + de.metas.inout.model.I_M_InOutLine.COLUMNNAME_QualityDiscountPercent);
			if (qualityDiscountPercent != null)
			{
				lineQueryBuilder.addEqualsFilter(de.metas.inout.model.I_M_InOutLine.COLUMNNAME_QualityDiscountPercent, qualityDiscountPercent);
			}

			final I_M_InOutLine shipmentLineRecord = lineQueryBuilder
					.create()
					.firstOnlyNotNull(I_M_InOutLine.class);

			validateShipmentLine(shipmentLineRecord, row);

			final String shipmentLineIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_InOutLine.COLUMNNAME_M_InOutLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			shipmentLineTable.putOrReplace(shipmentLineIdentifier, shipmentLineRecord);
		}
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
			final I_M_InOutLine shipmentLine = shipmentLineTable.get(shipmentLineIdentifier);
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
			final de.metas.handlingunits.model.I_M_InOutLine inOutLine = InterfaceWrapperHelper.newInstance(de.metas.handlingunits.model.I_M_InOutLine.class);

			final String inOutIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_InOut_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_InOut inOut = shipmentTable.get(inOutIdentifier);
			assertThat(inOut).isNotNull();

			inOutLine.setM_InOut_ID(inOut.getM_InOut_ID());

			final BigDecimal qtyEntered = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_QtyEntered);
			inOutLine.setQtyEntered(qtyEntered);

			final BigDecimal movementQty = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_MovementQty);
			inOutLine.setMovementQty(movementQty);

			final String uomCode = DataTableUtil.extractStringForColumnName(row, "UomCode");
			final I_C_UOM uom = uomDao.getByX12DE355(X12DE355.ofCode(uomCode));
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

			final Boolean isManualPackingMaterial = DataTableUtil.extractBooleanForColumnNameOrNull(row, "OPT." + de.metas.handlingunits.model.I_M_InOutLine.COLUMNNAME_IsManualPackingMaterial);
			if (isManualPackingMaterial != null)
			{
				inOutLine.setIsManualPackingMaterial(isManualPackingMaterial);
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

	private void validateShipmentLine(@NonNull final I_M_InOutLine shipmentLine, @NonNull final Map<String, String> row)
	{
		final SoftAssertions softly = new SoftAssertions();
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, "M_Product_ID.Identifier");
		final Integer expectedProductId = productTable.getOptional(productIdentifier)
				.map(I_M_Product::getM_Product_ID)
				.orElseGet(() -> Integer.parseInt(productIdentifier));

		final BigDecimal movementqty = DataTableUtil.extractBigDecimalForColumnName(row, "movementqty");
		final boolean processed = DataTableUtil.extractBooleanForColumnName(row, "processed");

		final String x12de355Code = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_InOutLine.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());

		if (Check.isNotBlank(x12de355Code))
		{
			final UomId uomId = uomDao.getUomIdByX12DE355(X12DE355.ofCode(x12de355Code));
			assertThat(shipmentLine.getC_UOM_ID()).isEqualTo(uomId.getRepoId());
		}

		assertThat(shipmentLine.getM_Product_ID()).isEqualTo(expectedProductId);
		assertThat(shipmentLine.getMovementQty()).isEqualByComparingTo(movementqty);
		assertThat(shipmentLine.isProcessed()).isEqualTo(processed);

		final String projectIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_M_InOutLine.COLUMNNAME_C_Project_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		if (Check.isNotBlank(projectIdentifier))
		{
			final Integer projectId = projectTable.getOptional(projectIdentifier)
					.map(I_C_Project::getC_Project_ID)
					.orElseGet(() -> Integer.parseInt(projectIdentifier));

			assertThat(shipmentLine.getC_Project_ID()).isEqualTo(projectId);
		}

		final BigDecimal qtyEntered = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_M_InOutLine.COLUMNNAME_QtyEntered);
		if (qtyEntered != null)
		{
			assertThat(shipmentLine.getQtyEntered()).isEqualByComparingTo(qtyEntered);
		}

		final String flatrateTermIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_ModCntr_Log.COLUMNNAME_C_Flatrate_Term_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(flatrateTermIdentifier))
		{
			final I_C_Flatrate_Term flatrateTermRecord = flatrateTermTable.get(flatrateTermIdentifier);
			softly.assertThat(shipmentLine.getC_Flatrate_Term_ID()).as(I_M_InOutLine.COLUMNNAME_C_Flatrate_Term_ID).isEqualTo(flatrateTermRecord.getC_Flatrate_Term_ID());
		}
	}

	@Given("^delete M_InOutLine identified by (.*) is expecting error$")
	public void order_action_expecting_error(@NonNull final String inOutLineIdentifier, @NonNull final DataTable dataTable)
	{
		final I_M_InOutLine inOutLine = shipmentLineTable.get(inOutLineIdentifier);
		Assertions.assertThat(inOutLine).isNotNull();

		final Map<String, String> row = dataTable.asMaps().get(0);

		boolean errorThrown = false;

		try
		{
			InterfaceWrapperHelper.delete(inOutLine);
		}
		catch (final Exception e)
		{
			errorThrown = true;

			final String errorMessageIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_AD_Message_ID + "." + TABLECOLUMN_IDENTIFIER);

			if (errorMessageIdentifier != null)
			{
				final I_AD_Message errorMessage = messageTable.get(errorMessageIdentifier);

				Assertions.assertThat(e.getMessage()).contains(msgBL.getMsg(Env.getCtx(), AdMessageKey.of(errorMessage.getValue())));
			}
		}

		Assertions.assertThat(errorThrown).isTrue();
	}
}

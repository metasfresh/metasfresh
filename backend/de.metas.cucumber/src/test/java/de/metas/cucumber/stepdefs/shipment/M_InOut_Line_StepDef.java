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

<<<<<<< HEAD
import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Locator_StepDefData;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.project.C_Project_StepDefData;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
=======
import de.metas.common.util.Check;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Locator_StepDefData;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.uom.IUOMDAO;
import de.metas.uom.X12DE355;
>>>>>>> 9ca46724894 (Revert "Revert "Merge remote-tracking branch 'origin/mad_orange_uat' into mad_orange_hotfix"" (#15192))
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
<<<<<<< HEAD
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Project;
=======
import org.adempiere.model.InterfaceWrapperHelper;
>>>>>>> 9ca46724894 (Revert "Revert "Merge remote-tracking branch 'origin/mad_orange_uat' into mad_orange_hotfix"" (#15192))
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
<<<<<<< HEAD
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.compiere.model.I_M_InOutLine.COLUMNNAME_M_InOutLine_ID;
import static org.compiere.model.I_M_InOutLine.COLUMNNAME_M_InOut_ID;
=======
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_M_InOut.COLUMNNAME_M_InOut_ID;
import static org.compiere.model.I_M_InOutLine.COLUMNNAME_M_InOutLine_ID;
>>>>>>> 9ca46724894 (Revert "Revert "Merge remote-tracking branch 'origin/mad_orange_uat' into mad_orange_hotfix"" (#15192))
import static org.compiere.model.I_M_InOutLine.COLUMNNAME_M_Locator_ID;
import static org.compiere.model.I_M_InOutLine.COLUMNNAME_M_Product_ID;
import static org.compiere.model.I_M_InOutLine.COLUMNNAME_MovementQty;
import static org.compiere.model.I_M_InOutLine.COLUMNNAME_QtyEntered;

public class M_InOut_Line_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomDao = Services.get(IUOMDAO.class);

	private final M_InOut_StepDefData shipmentTable;
	private final M_InOutLine_StepDefData shipmentLineTable;
<<<<<<< HEAD
	private final C_OrderLine_StepDefData orderLineTable;
	private final M_Product_StepDefData productTable;
	private final C_Project_StepDefData projectTable;
=======
	private final M_Product_StepDefData productTable;
>>>>>>> 9ca46724894 (Revert "Revert "Merge remote-tracking branch 'origin/mad_orange_uat' into mad_orange_hotfix"" (#15192))
	private final M_Locator_StepDefData locatorTable;

	public M_InOut_Line_StepDef(
			@NonNull final M_InOut_StepDefData shipmentTable,
			@NonNull final M_InOutLine_StepDefData shipmentLineTable,
<<<<<<< HEAD
			@NonNull final C_OrderLine_StepDefData orderLineTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_Project_StepDefData projectTable,
=======
			@NonNull final M_Product_StepDefData productTable,
>>>>>>> 9ca46724894 (Revert "Revert "Merge remote-tracking branch 'origin/mad_orange_uat' into mad_orange_hotfix"" (#15192))
			@NonNull final M_Locator_StepDefData locatorTable)
	{
		this.shipmentTable = shipmentTable;
		this.shipmentLineTable = shipmentLineTable;
<<<<<<< HEAD
		this.orderLineTable = orderLineTable;
		this.productTable = productTable;
		this.projectTable = projectTable;
=======
		this.productTable = productTable;
>>>>>>> 9ca46724894 (Revert "Revert "Merge remote-tracking branch 'origin/mad_orange_uat' into mad_orange_hotfix"" (#15192))
		this.locatorTable = locatorTable;
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
<<<<<<< HEAD
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
=======
					.addEqualsFilter(COLUMNNAME_M_Product_ID, productId)
>>>>>>> 9ca46724894 (Revert "Revert "Merge remote-tracking branch 'origin/mad_orange_uat' into mad_orange_hotfix"" (#15192))
					.create()
					.firstOnlyNotNull(I_M_InOutLine.class);

			validateShipmentLine(shipmentLineRecord, row);

			final String shipmentLineIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_InOutLine.COLUMNNAME_M_InOutLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			shipmentLineTable.putOrReplace(shipmentLineIdentifier, shipmentLineRecord);
		}
	}

<<<<<<< HEAD
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

=======
>>>>>>> 9ca46724894 (Revert "Revert "Merge remote-tracking branch 'origin/mad_orange_uat' into mad_orange_hotfix"" (#15192))
	@And("metasfresh contains M_InOutLine")
	public void metasfresh_contains_M_InOutLine(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> table = dataTable.asMaps();
		for (final Map<String, String> row : table)
		{
<<<<<<< HEAD
			final de.metas.handlingunits.model.I_M_InOutLine inOutLine = InterfaceWrapperHelper.newInstance(de.metas.handlingunits.model.I_M_InOutLine.class);
=======
			final de.metas.inout.model.I_M_InOutLine inOutLine = InterfaceWrapperHelper.newInstance(de.metas.inout.model.I_M_InOutLine.class);
>>>>>>> 9ca46724894 (Revert "Revert "Merge remote-tracking branch 'origin/mad_orange_uat' into mad_orange_hotfix"" (#15192))

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

<<<<<<< HEAD
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

=======
>>>>>>> 9ca46724894 (Revert "Revert "Merge remote-tracking branch 'origin/mad_orange_uat' into mad_orange_hotfix"" (#15192))
			InterfaceWrapperHelper.saveRecord(inOutLine);

			final String inOutLineIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_InOutLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			shipmentLineTable.putOrReplace(inOutLineIdentifier, inOutLine);
		}
	}

<<<<<<< HEAD
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

=======
>>>>>>> 9ca46724894 (Revert "Revert "Merge remote-tracking branch 'origin/mad_orange_uat' into mad_orange_hotfix"" (#15192))
	private void validateShipmentLine(@NonNull final I_M_InOutLine shipmentLine, @NonNull final Map<String, String> row)
	{
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
	}
}

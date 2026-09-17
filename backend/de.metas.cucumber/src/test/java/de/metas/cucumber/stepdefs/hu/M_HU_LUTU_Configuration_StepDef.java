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

package de.metas.cucumber.stepdefs.hu;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_ReceiptSchedule_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.order.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.pporder.PP_Order_BOMLine_StepDefData;
import de.metas.cucumber.stepdefs.pporder.PP_Order_StepDefData;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.allocation.ILUTUProducerAllocationDestination;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IPPOrderReceiptHUProducer;
import de.metas.handlingunits.pporder.api.impl.PPOrderDocumentLUTUConfigurationHandlerTestHelper;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.handlingunits.receiptschedule.impl.ReceiptScheduleHUGenerator;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.Env;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class M_HU_LUTU_Configuration_StepDef
{
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);
	private final IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);
	private final IReceiptScheduleDAO receiptScheduleDAO = Services.get(IReceiptScheduleDAO.class);
	private final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);

	@NonNull private final M_HU_PI_Item_Product_StepDefData huPiItemProductTable;
	@NonNull private final M_HU_PI_StepDefData huPiTable;
	@NonNull private final M_ReceiptSchedule_StepDefData receiptScheduleTable;
	@NonNull private final M_HU_LUTU_Configuration_StepDefData huLutuConfigurationTable;
	@NonNull private final M_HU_StepDefData huTable;
	@NonNull private final M_HU_List_StepDefData huListTable;
	@NonNull private final PP_Order_StepDefData ppOrderTable;
	@NonNull private final PP_Order_BOMLine_StepDefData ppOrderBOMLineTable;
	@NonNull private final C_OrderLine_StepDefData orderLineTable;

	/**
	 * Receives a {@code PP_Order} output into planning HUs, packed per the LU/TU configuration built from
	 * the DataTable row. Without a BOM-line reference it receives the order's <b>main</b> (finished-good)
	 * product; with one it receives that BOM line's <b>co-product / by-product</b> output.
	 * <p>
	 * Required columns: {@code PP_Order_ID} (identifier), {@code M_HU_ID.Identifier},
	 * {@code IsInfiniteQtyLU}, {@code QtyLU}, {@code IsInfiniteQtyTU}, {@code QtyTU},
	 * {@code IsInfiniteQtyCU}, {@code QtyCUsPerTU} and {@code M_HU_PI_Item_Product_ID.Identifier}.
	 * <p>
	 * Optional column {@code PP_Order_BOMLine_ID.Identifier}: when present, the receipt is the referenced
	 * BOM line's co/by-product output (driving the real production BL {@code receivingByOrCoProduct}) instead
	 * of the main product ({@code receivingMainProduct}). Which of the two is 100% unambiguous — it is decided
	 * from the referenced line's {@code ComponentType}: a co-product ({@code CP}) or by-product ({@code BY})
	 * line is a receivable output ({@link BOMComponentType#isByOrCoProduct()}), any other type is an ISSUE
	 * line and can never be received, so a reference to one <b>fails the step loudly</b> rather than guessing.
	 * Omitting the column keeps today's behaviour exactly (main-product receipt) — backward compatible.
	 * <p>
	 * {@code M_HU_ID.Identifier} accepts <b>one or several</b> comma-separated identifiers. The
	 * received HUs are bound to them positionally, and the number of identifiers must match the
	 * number of HUs the configuration actually produces — a mismatch fails the step rather than
	 * silently registering only the first HU. So a receipt that packs into two TUs is written as:
	 * <pre>
	 * And receive HUs for PP_Order with M_HU_LUTU_Configuration:
	 *   | PP_Order_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
	 *   | ppOrder_1   | hu_a,hu_b          | N               | 0     | N               | 2     | N               | 10          | huPiItemProduct_1                  |
	 * </pre>
	 * And a co-product receipt of that same order by adding the BOM-line reference:
	 * <pre>
	 * And receive HUs for PP_Order with M_HU_LUTU_Configuration:
	 *   | PP_Order_ID | PP_Order_BOMLine_ID | M_HU_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
	 *   | ppOrder_1   | coProductBomLine    | huCo               | N               | 0     | N               | 1     | N               | 6           | coProductPiItemProduct             |
	 * </pre>
	 * With {@code QtyLU=0} there is no aggregate LU, so one physical HU is created per TU. The
	 * positional binding is stable: {@code getCreatedHUs()} is backed by a {@code TreeSet} ordered by
	 * ascending {@code M_HU_ID}, and ids are assigned in creation order within the scenario.
	 */
	@And("receive HUs for PP_Order with M_HU_LUTU_Configuration:")
	public void create_M_HU_LUTU_Configuration_for_pp_order(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_M_HU_LUTU_Configuration.COLUMNNAME_M_HU_LUTU_Configuration_ID)
				.forEach(tableRow -> {
					final I_PP_Order ppOrder = ppOrderTable.get(tableRow.getAsIdentifier(I_PP_Order.COLUMNNAME_PP_Order_ID));

					final de.metas.handlingunits.model.I_PP_Order huPPOrder = InterfaceWrapperHelper.load(ppOrder.getPP_Order_ID(), de.metas.handlingunits.model.I_PP_Order.class);

					final I_M_HU_LUTU_Configuration lutuConfiguration = PPOrderDocumentLUTUConfigurationHandlerTestHelper.createNewLUTUConfiguration(huPPOrder);

					final I_M_HU_LUTU_Configuration lutuConfig = computeLUTUConfiguration(lutuConfiguration, tableRow);

					final StepDefDataIdentifier bomLineIdentifier = tableRow
							.getAsOptionalIdentifier(I_PP_Order_BOMLine.COLUMNNAME_PP_Order_BOMLine_ID)
							.filter(StepDefDataIdentifier::isNotNullPlaceholder)
							.orElse(null);

					final List<I_M_HU> hus = createReceiptProducer(ppOrder, bomLineIdentifier)
							.packUsingLUTUConfiguration(lutuConfig)
							.createDraftReceiptCandidatesAndPlanningHUs();

					// M_HU_ID.Identifier may name MORE THAN ONE identifier, comma-separated, for a receipt that
					// packs into several HUs (e.g. QtyTU=2). The received HUs are then bound to the identifiers
					// positionally. A single identifier keeps the previous behaviour exactly: one HU expected.
					final List<StepDefDataIdentifier> huIdentifiers = tableRow.getAsIdentifier(I_M_HU.COLUMNNAME_M_HU_ID).toCommaSeparatedList();

					assertThat(hus)
							.as("received HUs must match the number of identifiers given in M_HU_ID.Identifier")
							.hasSize(huIdentifiers.size());

					for (int i = 0; i < huIdentifiers.size(); i++)
					{
						huTable.putOrReplace(huIdentifiers.get(i), hus.get(i));
					}
				});
	}

	/**
	 * Selects the real production receipt BL for this row: the co/by-product receipt
	 * ({@link IHUPPOrderBL#receivingByOrCoProduct(PPOrderBOMLineId)}) when a BOM-line reference is given,
	 * else the main-product receipt ({@link IHUPPOrderBL#receivingMainProduct(PPOrderId)}).
	 * <p>
	 * The co/by-vs-main decision is resolved unambiguously from the referenced line's {@code ComponentType}:
	 * only a co-product ({@code CP}) or by-product ({@code BY}) line is a receivable output, so a reference to
	 * any other (issue) line fails loud instead of being received against the wrong BL.
	 */
	@NonNull
	private IPPOrderReceiptHUProducer createReceiptProducer(
			@NonNull final I_PP_Order ppOrder,
			@Nullable final StepDefDataIdentifier bomLineIdentifier)
	{
		final PPOrderId ppOrderId = PPOrderId.ofRepoId(ppOrder.getPP_Order_ID());

		// No BOM-line reference -> main (finished-good) product receipt. Backward-compatible default.
		if (bomLineIdentifier == null)
		{
			return huPPOrderBL.receivingMainProduct(ppOrderId);
		}
		else
		{
			final I_PP_Order_BOMLine bomLine = ppOrderBOMLineTable.get(bomLineIdentifier);
			final BOMComponentType componentType = BOMComponentType.ofCode(bomLine.getComponentType());
			if (!componentType.isByOrCoProduct())
			{
				throw new AdempiereException("Cannot receive PP_Order_BOMLine " + bomLineIdentifier
						+ " (M_Product_ID=" + bomLine.getM_Product_ID() + ") as a co/by-product receipt:"
						+ " its ComponentType is " + componentType + " (" + bomLine.getComponentType() + "),"
						+ " which is an issue line, not a receivable output."
						+ " Only a co-product (CP) or by-product (BY) BOM line can be received via receivingByOrCoProduct.");
			}

			return huPPOrderBL.receivingByOrCoProduct(PPOrderBOMLineId.ofRepoId(bomLine.getPP_Order_BOMLine_ID()));
		}
	}

	@And("create M_HU_LUTU_Configuration for M_ReceiptSchedule:")
	public void createLUTUConfigurationForReceiptSchedule(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String receiptScheduleIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_ReceiptSchedule.COLUMNNAME_M_ReceiptSchedule_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_ReceiptSchedule receiptSchedule = receiptScheduleTable.get(receiptScheduleIdentifier);
			assertThat(receiptSchedule).isNotNull();

			InterfaceWrapperHelper.refresh(receiptSchedule);

			final de.metas.handlingunits.model.I_M_ReceiptSchedule huReceiptSchedule = InterfaceWrapperHelper.create(receiptSchedule, de.metas.handlingunits.model.I_M_ReceiptSchedule.class);

			final I_M_HU_LUTU_Configuration lutuConfigDefault = huReceiptScheduleBL
					.createLUTUConfigurationManager(huReceiptSchedule)
					.getCreateLUTUConfiguration();

			huReceiptScheduleBL.adjustLUTUConfiguration(lutuConfigDefault, huReceiptSchedule);

			final String tuHUPIProductIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID + ".TU." + TABLECOLUMN_IDENTIFIER);
			final I_M_HU_PI_Item_Product tuHuPiProduct = huPiItemProductTable.get(tuHUPIProductIdentifier);

			final String luHUPIIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_HU_PI.COLUMNNAME_M_HU_PI_ID + ".LU." + TABLECOLUMN_IDENTIFIER);
			final I_M_HU_PI luHuPi = huPiTable.get(luHUPIIdentifier);

			final ILUTUConfigurationFactory.CreateLUTUConfigRequest lutuConfigRequest = ILUTUConfigurationFactory.CreateLUTUConfigRequest.builder()
					.baseLUTUConfiguration(lutuConfigDefault)
					.qtyLU(lutuConfigDefault.getQtyLU())
					.qtyTU(lutuConfigDefault.getQtyTU())
					.qtyCUsPerTU(lutuConfigDefault.getQtyCUsPerTU())
					.tuHUPIItemProductID(tuHuPiProduct.getM_HU_PI_Item_Product_ID())
					.luHUPIID(luHuPi.getM_HU_PI_ID())
					.build();

			final I_M_HU_LUTU_Configuration lutuConfigurationWithParams = lutuConfigurationFactory.createNewLUTUConfigWithParams(lutuConfigRequest);

			InterfaceWrapperHelper.saveRecord(lutuConfigurationWithParams);

			final String lutuConfigIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_HU_LUTU_Configuration.COLUMNNAME_M_HU_LUTU_Configuration_ID + "." + TABLECOLUMN_IDENTIFIER);
			huLutuConfigurationTable.putOrReplace(lutuConfigIdentifier, lutuConfigurationWithParams);
		}
	}

	@Then("validate M_HU_LUTU_Configuration:")
	public void validateLutuConfig(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String lutuConfigIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_HU_LUTU_Configuration.COLUMNNAME_M_HU_LUTU_Configuration_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_HU_LUTU_Configuration lutuConfig = huLutuConfigurationTable.get(lutuConfigIdentifier);

			final BigDecimal qtyLU = DataTableUtil.extractBigDecimalForColumnName(row, I_M_HU_LUTU_Configuration.COLUMNNAME_QtyLU);
			final BigDecimal qtyTU = DataTableUtil.extractBigDecimalForColumnName(row, I_M_HU_LUTU_Configuration.COLUMNNAME_QtyTU);
			final BigDecimal qtyCUsPerTU = DataTableUtil.extractBigDecimalForColumnName(row, I_M_HU_LUTU_Configuration.COLUMNNAME_QtyCUsPerTU);

			assertThat(lutuConfig.getQtyLU()).isEqualTo(qtyLU);
			assertThat(lutuConfig.getQtyTU()).isEqualTo(qtyTU);
			assertThat(lutuConfig.getQtyCUsPerTU()).isEqualTo(qtyCUsPerTU);
		}
	}

	@And("receive HUs with M_HU_LUTU_Configuration:")
	public void receiveHUsWithGivenLUTU(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.forEach(tableRow -> {
					final String receiptScheduleIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_ReceiptSchedule.COLUMNNAME_M_ReceiptSchedule_ID + "." + TABLECOLUMN_IDENTIFIER);
					final I_M_ReceiptSchedule receiptSchedule = receiptScheduleTable.get(receiptScheduleIdentifier);
					assertThat(receiptSchedule).isNotNull();
					InterfaceWrapperHelper.refresh(receiptSchedule);

					final de.metas.handlingunits.model.I_M_ReceiptSchedule huReceiptSchedule = InterfaceWrapperHelper.create(receiptSchedule, de.metas.handlingunits.model.I_M_ReceiptSchedule.class);

					final String lutuConfigIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_HU_LUTU_Configuration.COLUMNNAME_M_HU_LUTU_Configuration_ID + "." + TABLECOLUMN_IDENTIFIER);
					final I_M_HU_LUTU_Configuration lutuConfig = huLutuConfigurationTable.get(lutuConfigIdentifier);

					generateHUsWithLUTUConfiguration(tableRow, huReceiptSchedule, lutuConfig);
				});
	}

	@And("create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs")
	public void createLUTUConfigurationsForReceiptSchedules(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_M_HU_LUTU_Configuration.COLUMNNAME_M_HU_LUTU_Configuration_ID)
				.forEach(this::createLUTUConfigurationForReceiptSchedule);
	}

	public List<I_M_HU> createLUTUConfigurationForReceiptSchedule(final DataTableRow tableRow)
	{
		final de.metas.handlingunits.model.I_M_ReceiptSchedule huReceiptSchedule = extractReceiptSchedule(tableRow);

		final I_M_HU_LUTU_Configuration lutuConfigDefault = huReceiptScheduleBL
				.createLUTUConfigurationManager(huReceiptSchedule)
				.getCreateLUTUConfiguration();

		final I_M_HU_LUTU_Configuration lutuConfig = computeLUTUConfiguration(lutuConfigDefault, tableRow);

		return generateHUsWithLUTUConfiguration(tableRow, huReceiptSchedule, lutuConfig);
	}

	private de.metas.handlingunits.model.I_M_ReceiptSchedule extractReceiptSchedule(final DataTableRow row)
	{
		// Direct
		{
			final de.metas.handlingunits.model.I_M_ReceiptSchedule huReceiptSchedule = row.getAsOptionalIdentifier(I_M_ReceiptSchedule.COLUMNNAME_M_ReceiptSchedule_ID)
					.map(this::getReceiptSchedule)
					.orElse(null);
			if (huReceiptSchedule != null)
			{
				return huReceiptSchedule;
			}
		}

		//
		// via Order line
		final I_C_OrderLine orderLine = row.getAsOptionalIdentifier("C_OrderLine_ID")
				.map(orderLineTable::get)
				.orElse(null);
		if (orderLine != null)
		{
			final I_M_ReceiptSchedule receiptSchedule = receiptScheduleDAO.retrieveForRecord(orderLine);
			if (receiptSchedule == null)
			{
				throw new AdempiereException("Cannot determine the receipt schedule for " + orderLine);
			}
			return InterfaceWrapperHelper.create(receiptSchedule, de.metas.handlingunits.model.I_M_ReceiptSchedule.class);
		}

		throw new AdempiereException("Cannot determine the receipt schedule from " + row);
	}

	private de.metas.handlingunits.model.I_M_ReceiptSchedule getReceiptSchedule(final StepDefDataIdentifier receiptScheduleIdentifier)
	{
		final I_M_ReceiptSchedule receiptSchedule = receiptScheduleTable.getOptional(receiptScheduleIdentifier).orElse(null);
		if (receiptSchedule != null)
		{
			InterfaceWrapperHelper.refresh(receiptSchedule);
			return InterfaceWrapperHelper.create(receiptSchedule, de.metas.handlingunits.model.I_M_ReceiptSchedule.class);
		}

		final ReceiptScheduleId receiptScheduleId = receiptScheduleIdentifier.getAsId(ReceiptScheduleId.class);
		return huReceiptScheduleBL.getById(receiptScheduleId);
	}

	private List<I_M_HU> generateHUsWithLUTUConfiguration(
			@NonNull final DataTableRow tableRow,
			@NonNull final de.metas.handlingunits.model.I_M_ReceiptSchedule huReceiptSchedule,
			@NonNull final I_M_HU_LUTU_Configuration lutuConfig)
	{
		final IMutableHUContext huContextInitial = huContextFactory.createMutableHUContext();

		final ReceiptScheduleHUGenerator huGenerator = ReceiptScheduleHUGenerator.newInstance(huContextInitial)
				.addM_ReceiptSchedule(huReceiptSchedule)
				.setUpdateReceiptScheduleDefaultConfiguration(false);

		huGenerator.setM_HU_LUTU_Configuration(lutuConfig);

		final ILUTUProducerAllocationDestination lutuProducer = huGenerator.getLUTUProducerAllocationDestination();
		final Quantity qtyCUsTotal = lutuProducer.calculateTotalQtyCU();
		if (qtyCUsTotal.isInfinite())
		{
			throw new AdempiereException("LU/TU configuration is resulting to infinite quantity: " + lutuConfig);
		}
		huGenerator.setQtyToAllocateTarget(qtyCUsTotal);

		// Generate the HUs
		final List<I_M_HU> hus = huGenerator.generateWithinOwnTransaction();
		assertThat(hus).isNotNull();

		final int noOfHusGenerated = tableRow.getAsOptionalInt("numberHUsGenerated").orElse(-1);
		if (noOfHusGenerated > 0)
		{
			assertThat(hus).hasSize(noOfHusGenerated);

			tableRow.getAsOptionalIdentifier("HUList")
					.ifPresent(huListIdentifier -> huListTable.putOrReplace(huListIdentifier, hus));
		}
		else
		{
			assertThat(hus).hasSize(1);

			tableRow.getAsOptionalIdentifier(I_M_HU.COLUMNNAME_M_HU_ID)
					.ifPresent(huIdentifier -> huTable.putOrReplace(huIdentifier, hus.get(0)));
		}

		return hus;
	}

	@NonNull
	private I_M_HU_LUTU_Configuration computeLUTUConfiguration(@NonNull final I_M_HU_LUTU_Configuration lutuConfig, @NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier piProductItemIdentifier = row.getAsIdentifier(I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID);
		final Integer huPiItemProductId = huPiItemProductTable.getOptional(piProductItemIdentifier)
				.map(I_M_HU_PI_Item_Product::getM_HU_PI_Item_Product_ID)
				.orElseGet(piProductItemIdentifier::getAsInt);
		assertThat(huPiItemProductId).isNotNull();

		final boolean isInfiniteQtyCU = row.getAsOptionalBoolean(I_M_HU_LUTU_Configuration.COLUMNNAME_IsInfiniteQtyCU).orElseFalse();
		final BigDecimal qtyCUsPerTU = row.getAsOptionalBigDecimal(I_M_HU_LUTU_Configuration.COLUMNNAME_QtyCUsPerTU).orElse(BigDecimal.ONE);

		// CU
		lutuConfig.setQtyCUsPerTU(qtyCUsPerTU);
		lutuConfig.setIsInfiniteQtyCU(isInfiniteQtyCU);

		// TU
		final boolean isInfiniteQtyTU = row.getAsOptionalBoolean(I_M_HU_LUTU_Configuration.COLUMNNAME_IsInfiniteQtyTU).orElseFalse();
		final BigDecimal qtyTU = row.getAsOptionalBigDecimal(I_M_HU_LUTU_Configuration.COLUMNNAME_QtyTU).orElse(BigDecimal.ONE);

		final I_M_HU_PI_Item_Product tuPIItemProduct = InterfaceWrapperHelper.create(Env.getCtx(), huPiItemProductId, I_M_HU_PI_Item_Product.class, ITrx.TRXNAME_None);
		final I_M_HU_PI tuPI = tuPIItemProduct.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI();
		lutuConfig.setM_HU_PI_Item_Product_ID(tuPIItemProduct.getM_HU_PI_Item_Product_ID());
		lutuConfig.setM_TU_HU_PI(tuPI);
		lutuConfig.setQtyTU(qtyTU);
		lutuConfig.setIsInfiniteQtyTU(isInfiniteQtyTU);

		// LU
		final boolean isInfiniteQtyLU = row.getAsOptionalBoolean(I_M_HU_LUTU_Configuration.COLUMNNAME_IsInfiniteQtyLU).orElseFalse();
		final StepDefDataIdentifier luHuPiIdentifier = row.getAsOptionalIdentifier(I_M_HU_LUTU_Configuration.COLUMNNAME_M_LU_HU_PI_ID).orElse(null);

		if (luHuPiIdentifier != null)
		{
			final BigDecimal qtyLU = row.getAsOptionalBigDecimal(I_M_HU_LUTU_Configuration.COLUMNNAME_QtyLU).orElse(BigDecimal.ONE);
			final HuPackingInstructionsId luHuPiId = huPiTable.getIdOptional(luHuPiIdentifier)
					.orElseGet(() -> luHuPiIdentifier.getAsId(HuPackingInstructionsId.class));
			assertThat(luHuPiId).isNotNull();

			final I_M_HU_PI luPI = handlingUnitsDAO.getPackingInstructionById(luHuPiId);
			final I_M_HU_PI_Version luPIV = handlingUnitsDAO.retrievePICurrentVersion(luPI);
			final I_M_HU_PI_Item luPI_Item = handlingUnitsDAO.retrieveParentPIItemsForParentPI(
							tuPI,
							X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit,
							ILUTUConfigurationFactory.extractBPartnerIdOrNull(lutuConfig))
					.stream()
					.filter(piItem -> piItem.getM_HU_PI_Version_ID() == luPIV.getM_HU_PI_Version_ID())
					.findFirst()
					.orElseThrow(() -> new AdempiereException(tuPI.getName() + " cannot be loaded to " + luPI.getName()));

			lutuConfig.setM_LU_HU_PI(luPI);
			lutuConfig.setM_LU_HU_PI_Item(luPI_Item);
			lutuConfig.setQtyLU(qtyLU);
		}
		else
		{
			lutuConfig.setM_LU_HU_PI_ID(-1);
			lutuConfig.setM_LU_HU_PI_Item_ID(-1);
			lutuConfig.setQtyLU(BigDecimal.ZERO);
		}

		lutuConfig.setIsInfiniteQtyLU(isInfiniteQtyLU);

		InterfaceWrapperHelper.saveRecord(lutuConfig);

		row.getAsOptionalIdentifier().ifPresent(lutuIdentifier -> huLutuConfigurationTable.putOrReplace(lutuIdentifier, lutuConfig));

		return lutuConfig;
	}
}

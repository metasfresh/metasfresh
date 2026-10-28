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
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.pporder.PP_Order_BOMLine_StepDefData;
import de.metas.cucumber.stepdefs.pporder.PP_Order_StepDefData;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.allocation.ILUTUProducerAllocationDestination;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IPPOrderReceiptHUProducer;
import de.metas.handlingunits.pporder.api.impl.PPOrderDocumentLUTUConfigurationHandlerTestHelper;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.handlingunits.receiptschedule.impl.ReceiptScheduleHUGenerator;
import de.metas.i18n.AdMessageKey;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.assertThat;

public class M_HU_LUTU_Configuration_StepDef
{
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);
	private final IPPOrderBOMDAO ppOrderBOMDAO = Services.get(IPPOrderBOMDAO.class);
	private final IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);
	private final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);

	private final M_HU_PI_Item_Product_StepDefData huPiItemProductTable;
	private final M_HU_PI_StepDefData huPiTable;
	private final M_ReceiptSchedule_StepDefData receiptScheduleTable;
	private final M_HU_LUTU_Configuration_StepDefData huLutuConfigurationTable;
	private final M_HU_StepDefData huTable;
	private final M_HU_List_StepDefData huListTable;
	private final PP_Order_StepDefData ppOrderTable;
	private final PP_Order_BOMLine_StepDefData ppOrderBOMLineTable;

	public M_HU_LUTU_Configuration_StepDef(
			@NonNull final M_HU_PI_Item_Product_StepDefData huPiItemProductTable,
			@NonNull final M_HU_PI_StepDefData huPiTable,
			@NonNull final M_ReceiptSchedule_StepDefData receiptScheduleTable,
			@NonNull final M_HU_LUTU_Configuration_StepDefData huLutuConfigurationTable,
			@NonNull final M_HU_StepDefData huTable,
			@NonNull final PP_Order_StepDefData ppOrderTable,
			@NonNull final PP_Order_BOMLine_StepDefData ppOrderBOMLineTable,
			@NonNull final M_HU_List_StepDefData huListTable)
	{
		this.huPiItemProductTable = huPiItemProductTable;
		this.huPiTable = huPiTable;
		this.receiptScheduleTable = receiptScheduleTable;
		this.huLutuConfigurationTable = huLutuConfigurationTable;
		this.huTable = huTable;
		this.ppOrderTable = ppOrderTable;
		this.ppOrderBOMLineTable = ppOrderBOMLineTable;
		this.huListTable = huListTable;
	}

	/**
	 * Receives the main product (or, with {@code PP_Order_BOMLine_ID}, a co-product line) of a manufacturing order using the given LU/TU configuration.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>PP_Order_ID</b> — (required, identifier-ref) the manufacturing order<br>
	 *   <b>M_HU_PI_Item_Product_ID</b> — (required, identifier-ref or plain ID) the TU packing instruction<br>
	 *   <b>QtyCUsPerTU</b> — (optional) quantity per TU; defaults to 1<br>
	 *   <b>IsInfiniteQtyCU</b> — (optional) defaults to false<br>
	 *   <b>QtyTU</b> — (optional) number of TUs (per LU, if there is an LU); defaults to 1<br>
	 *   <b>IsInfiniteQtyTU</b> — (optional) defaults to false<br>
	 *   <b>QtyLU</b> — (optional) number of LUs; defaults to 1; 0 means receive without LU<br>
	 *   <b>IsInfiniteQtyLU</b> — (optional) defaults to false<br>
	 *   <b>M_LU_HU_PI_ID</b> — (required if QtyLU &gt; 0, also when QtyLU is omitted; identifier-ref or plain ID) the LU packing instruction; the TU must be loadable onto it<br>
	 *   <b>M_HU_LUTU_Configuration_ID</b> — (optional, identifier) stores the created LU/TU configuration under this alias<br>
	 *   <b>M_HU_ID</b> — (required unless ErrorMessage_AD_Message is given) alias of the first received HU<br>
	 *   <b>PP_Order_BOMLine_ID</b> — (optional, identifier-ref) receive this co-product line instead of the main product; as in production, the default LU/TU configuration is then built from this BOM line (the co-product), not from the PP_Order<br>
	 *   <b>ExpectedHUCount</b> — (optional) expected number of received top-level HUs; defaults to 1<br>
	 *   <b>ExpectedTUsPerLU</b> — (optional) every received LU holds this many TUs<br>
	 *   <b>ExpectedQtyCUsPerTU</b> — (optional) every received LU holds this quantity per TU (LU quantity = TUs x this)<br>
	 *   <b>ErrorMessage_AD_Message</b> — (optional) the receipt must be refused with an AdempiereException carrying this AD_Message key; no HU or receipt candidate is created<br>
	 *   <b>ErrorMessage_Contains</b> — (optional, with ErrorMessage_AD_Message) text the error message must contain, e.g. the capacity named in it<br>
	 * @cucumber.depends StepDefData: PP_Order_StepDefData, PP_Order_BOMLine_StepDefData, M_HU_PI_Item_Product_StepDefData, M_HU_PI_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And receive HUs for PP_Order with M_HU_LUTU_Configuration:
	 *   | PP_Order_ID | M_HU_ID    | QtyLU | QtyTU | QtyCUsPerTU | M_HU_PI_Item_Product_ID | M_LU_HU_PI_ID | ExpectedHUCount |
	 *   | ppOrder     | receivedLU | 2     | 3     | 15          | huPIP                   | huPI_LU       | 2               |
	 * </pre>
	 */
	@And("receive HUs for PP_Order with M_HU_LUTU_Configuration:")
	public void create_M_HU_LUTU_Configuration_for_pp_order(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_M_HU_LUTU_Configuration.COLUMNNAME_M_HU_LUTU_Configuration_ID)
				.forEach(tableRow -> {
					final I_PP_Order ppOrder = ppOrderTable.get(tableRow.getAsIdentifier(I_PP_Order.COLUMNNAME_PP_Order_ID));

					final de.metas.handlingunits.model.I_PP_Order huPPOrder = InterfaceWrapperHelper.load(ppOrder.getPP_Order_ID(), de.metas.handlingunits.model.I_PP_Order.class);

					final PPOrderBOMLineId coProductBOMLineId = tableRow.getAsOptionalIdentifier(I_PP_Order_BOMLine.COLUMNNAME_PP_Order_BOMLine_ID)
							.map(bomLineIdentifier -> bomLineIdentifier.lookupNotNullIdIn(ppOrderBOMLineTable))
							.orElse(null);

					// Like WEBUI_PP_Order_Receipt: a co-product receipt starts from the BOM line's configuration, so the configuration's product is the co-product.
					final I_M_HU_LUTU_Configuration lutuConfiguration = coProductBOMLineId != null
							? PPOrderDocumentLUTUConfigurationHandlerTestHelper.createNewLUTUConfiguration(ppOrderBOMDAO.getOrderBOMLineById(coProductBOMLineId))
							: PPOrderDocumentLUTUConfigurationHandlerTestHelper.createNewLUTUConfiguration(huPPOrder);

					final I_M_HU_LUTU_Configuration lutuConfig = computeLUTUConfiguration(lutuConfiguration, tableRow);

					final Supplier<List<I_M_HU>> receiveHUs = () -> {
						final IPPOrderReceiptHUProducer huProducer = coProductBOMLineId != null
								? huPPOrderBL.receivingByOrCoProduct(coProductBOMLineId)
								: huPPOrderBL.receivingMainProduct(PPOrderId.ofRepoId(ppOrder.getPP_Order_ID()));
						return huProducer
								.packUsingLUTUConfiguration(lutuConfig)
								.createDraftReceiptCandidatesAndPlanningHUs();
					};

					final AdMessageKey expectedErrorAdMessage = tableRow.getAsOptionalString("ErrorMessage_AD_Message").map(AdMessageKey::of).orElse(null);
					if (expectedErrorAdMessage != null)
					{
						final Set<HuId> husBefore = retrieveHuIdsOfOrder(ppOrder);
						final long receiptCandidatesBefore = countReceiptCandidates(ppOrder);

						final AdempiereException refusal = StepDefUtil.assertThrowsWithAdMessage(receiveHUs::get, expectedErrorAdMessage);
						tableRow.getAsOptionalString("ErrorMessage_Contains")
								.ifPresent(text -> assertThat(refusal.getMessage()).contains(text));

						assertThat(retrieveHuIdsOfOrder(ppOrder)).as("HUs assigned to / received for the order by the refused receipt").isEqualTo(husBefore);
						assertThat(countReceiptCandidates(ppOrder)).as("receipt candidates created by the refused receipt").isEqualTo(receiptCandidatesBefore);
						return;
					}

					final List<I_M_HU> hus = receiveHUs.get();

					assertThat(hus).hasSize(tableRow.getAsOptionalInt("ExpectedHUCount").orElse(1));
					hus.forEach(hu -> assertLUStructure(hu, tableRow));

					huTable.putOrReplace(tableRow.getAsIdentifier(I_M_HU.COLUMNNAME_M_HU_ID), hus.get(0));
				});
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
		final StepDefDataIdentifier receiptScheduleIdentifier = tableRow.getAsIdentifier(I_M_ReceiptSchedule.COLUMNNAME_M_ReceiptSchedule_ID);
		final de.metas.handlingunits.model.I_M_ReceiptSchedule huReceiptSchedule = getReceiptSchedule(receiptScheduleIdentifier);

		final I_M_HU_LUTU_Configuration lutuConfigDefault = huReceiptScheduleBL
				.createLUTUConfigurationManager(huReceiptSchedule)
				.getCreateLUTUConfiguration();

		final I_M_HU_LUTU_Configuration lutuConfig = computeLUTUConfiguration(lutuConfigDefault, tableRow);

		return generateHUsWithLUTUConfiguration(tableRow, huReceiptSchedule, lutuConfig);
	}

	private de.metas.handlingunits.model.I_M_ReceiptSchedule getReceiptSchedule(final StepDefDataIdentifier receiptScheduleIdentifier)
	{
		I_M_ReceiptSchedule receiptSchedule = receiptScheduleTable.getOptional(receiptScheduleIdentifier).orElse(null);
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

	/**
	 * Receipt candidates' HUs ({@code PP_Order_Qty.M_HU_ID}) plus the HUs assigned to the order or its BOM lines
	 * ({@code M_HU_Assignment}, filled by {@code AbstractPPOrderReceiptHUProducer#addAssignedHUs}).
	 * Scoped to the order so that HUs created concurrently by other scenarios do not matter.
	 */
	private Set<HuId> retrieveHuIdsOfOrder(@NonNull final I_PP_Order ppOrder)
	{
		final PPOrderId ppOrderId = PPOrderId.ofRepoId(ppOrder.getPP_Order_ID());
		final Set<HuId> huIds = new HashSet<>();

		queryBL.createQueryBuilder(I_PP_Order_Qty.class)
				.addEqualsFilter(I_PP_Order_Qty.COLUMNNAME_PP_Order_ID, ppOrderId)
				.create()
				.list(I_PP_Order_Qty.class)
				.forEach(qty -> huIds.add(HuId.ofRepoId(qty.getM_HU_ID())));

		addAssignedHuIds(huIds, TableRecordReference.of(I_PP_Order.Table_Name, ppOrderId));
		queryBL.createQueryBuilder(I_PP_Order_BOMLine.class)
				.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_PP_Order_ID, ppOrderId)
				.create()
				.idsAsSet(PPOrderBOMLineId::ofRepoId)
				.forEach(bomLineId -> addAssignedHuIds(huIds, TableRecordReference.of(I_PP_Order_BOMLine.Table_Name, bomLineId)));

		return huIds;
	}

	private void addAssignedHuIds(@NonNull final Set<HuId> huIds, @NonNull final TableRecordReference recordRef)
	{
		queryBL.createQueryBuilder(I_M_HU_Assignment.class)
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_AD_Table_ID, recordRef.getAD_Table_ID())
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_Record_ID, recordRef.getRecord_ID())
				.create()
				.list(I_M_HU_Assignment.class)
				.forEach(assignment -> huIds.add(HuId.ofRepoId(assignment.getM_HU_ID())));
	}

	private long countReceiptCandidates(@NonNull final I_PP_Order ppOrder)
	{
		return queryBL.createQueryBuilder(I_PP_Order_Qty.class)
				.addEqualsFilter(I_PP_Order_Qty.COLUMNNAME_PP_Order_ID, PPOrderId.ofRepoId(ppOrder.getPP_Order_ID()))
				.create()
				.count();
	}

	private void assertLUStructure(@NonNull final I_M_HU lu, @NonNull final DataTableRow tableRow)
	{
		final Optional<Integer> expectedTUsPerLU = tableRow.getAsOptionalInt("ExpectedTUsPerLU");
		final Optional<BigDecimal> expectedQtyCUsPerTU = tableRow.getAsOptionalBigDecimal("ExpectedQtyCUsPerTU");
		if (!expectedTUsPerLU.isPresent() && !expectedQtyCUsPerTU.isPresent())
		{
			return;
		}

		final int tusCount = handlingUnitsBL.retrieveIncludedHUs(lu).stream()
				.mapToInt(includedHU -> handlingUnitsBL.getTUsCount(includedHU).toInt())
				.sum();
		expectedTUsPerLU.ifPresent(expected -> assertThat(tusCount).as("TUs in LU " + lu.getM_HU_ID()).isEqualTo(expected));

		final BigDecimal luQty = queryBL.createQueryBuilder(I_M_HU_Storage.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_Storage.COLUMNNAME_M_HU_ID, lu.getM_HU_ID())
				.create()
				.firstOnlyNotNull(I_M_HU_Storage.class)
				.getQty();
		expectedQtyCUsPerTU.ifPresent(expected -> assertThat(luQty)
				.as("quantity of LU " + lu.getM_HU_ID() + " = " + tusCount + " TUs x " + expected)
				.isEqualByComparingTo(expected.multiply(BigDecimal.valueOf(tusCount))));
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
		final BigDecimal qtyLU = row.getAsOptionalBigDecimal(I_M_HU_LUTU_Configuration.COLUMNNAME_QtyLU).orElse(BigDecimal.ONE);

		if (qtyLU.signum() > 0)
		{
			final StepDefDataIdentifier luHuPiIdentifier = row.getAsIdentifier(I_M_HU_LUTU_Configuration.COLUMNNAME_M_LU_HU_PI_ID);
			final Integer luHuPiId = huPiTable.getOptional(luHuPiIdentifier)
					.map(I_M_HU_PI::getM_HU_PI_ID)
					.orElseGet(luHuPiIdentifier::getAsInt);
			assertThat(luHuPiId).isNotNull();

			final I_M_HU_PI luPI = InterfaceWrapperHelper.create(Env.getCtx(), luHuPiId, I_M_HU_PI.class, ITrx.TRXNAME_None);

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
		}
		else
		{
			lutuConfig.setM_LU_HU_PI_ID(-1);
			lutuConfig.setM_LU_HU_PI_Item_ID(-1);
		}

		lutuConfig.setQtyLU(qtyLU);
		lutuConfig.setIsInfiniteQtyLU(isInfiniteQtyLU);

		InterfaceWrapperHelper.saveRecord(lutuConfig);

		row.getAsOptionalIdentifier().ifPresent(lutuIdentifier -> huLutuConfigurationTable.putOrReplace(lutuIdentifier, lutuConfig));

		return lutuConfig;
	}
}

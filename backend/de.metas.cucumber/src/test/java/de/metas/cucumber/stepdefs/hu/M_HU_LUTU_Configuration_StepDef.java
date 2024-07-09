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

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.pporder.PP_Order_StepDefData;
import de.metas.cucumber.stepdefs.receiptschedule.M_ReceiptSchedule_StepDefData;
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
import de.metas.handlingunits.pporder.api.impl.PPOrderDocumentLUTUConfigurationHandlerTestHelper;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.handlingunits.receiptschedule.impl.ReceiptScheduleHUGenerator;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class M_HU_LUTU_Configuration_StepDef
{
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);
	private final IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);
	private final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);

	private final M_HU_PI_Item_Product_StepDefData huPiItemProductTable;
	private final M_HU_PI_StepDefData huPiTable;
	private final M_ReceiptSchedule_StepDefData receiptScheduleTable;
	private final M_HU_LUTU_Configuration_StepDefData huLutuConfigurationTable;
	private final M_HU_StepDefData huTable;
	private final M_HU_List_StepDefData huListTable;
	private final PP_Order_StepDefData ppOrderTable;

	public M_HU_LUTU_Configuration_StepDef(
			@NonNull final M_HU_PI_Item_Product_StepDefData huPiItemProductTable,
			@NonNull final M_HU_PI_StepDefData huPiTable,
			@NonNull final M_ReceiptSchedule_StepDefData receiptScheduleTable,
			@NonNull final M_HU_LUTU_Configuration_StepDefData huLutuConfigurationTable,
			@NonNull final M_HU_StepDefData huTable,
			@NonNull final PP_Order_StepDefData ppOrderTable,
			@NonNull final M_HU_List_StepDefData huListTable)
	{
		this.huPiItemProductTable = huPiItemProductTable;
		this.huPiTable = huPiTable;
		this.receiptScheduleTable = receiptScheduleTable;
		this.huLutuConfigurationTable = huLutuConfigurationTable;
		this.huTable = huTable;
		this.ppOrderTable = ppOrderTable;
		this.huListTable = huListTable;
	}

	@And("receive HUs for PP_Order with M_HU_LUTU_Configuration:")
	public void create_M_HU_LUTU_Configuration_for_pp_order(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			final String ppOrderIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order.COLUMNNAME_PP_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_PP_Order ppOrder = ppOrderTable.get(ppOrderIdentifier);

			final de.metas.handlingunits.model.I_PP_Order huPPOrder = InterfaceWrapperHelper.load(ppOrder.getPP_Order_ID(), de.metas.handlingunits.model.I_PP_Order.class);

			final I_M_HU_LUTU_Configuration lutuConfiguration = PPOrderDocumentLUTUConfigurationHandlerTestHelper.createNewLUTUConfiguration(huPPOrder);

			final I_M_HU_LUTU_Configuration lutuConfig = computeLUTUConfiguration(lutuConfiguration, tableRow);

			final List<I_M_HU> hus = huPPOrderBL.receivingMainProduct(PPOrderId.ofRepoId(ppOrder.getPP_Order_ID()))
					.packUsingLUTUConfiguration(lutuConfig)
					.createDraftReceiptCandidatesAndPlanningHUs();

			assertThat(hus).isNotNull();
			assertThat(hus.size()).isEqualTo(1);

			final String huIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_HU.COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER);
			huTable.putOrReplace(huIdentifier, hus.get(0));
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
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			final String receiptScheduleIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_ReceiptSchedule.COLUMNNAME_M_ReceiptSchedule_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_ReceiptSchedule receiptSchedule = receiptScheduleTable.get(receiptScheduleIdentifier);
			assertThat(receiptSchedule).isNotNull();
			InterfaceWrapperHelper.refresh(receiptSchedule);

			final de.metas.handlingunits.model.I_M_ReceiptSchedule huReceiptSchedule = InterfaceWrapperHelper.create(receiptSchedule, de.metas.handlingunits.model.I_M_ReceiptSchedule.class);

			final String lutuConfigIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_HU_LUTU_Configuration.COLUMNNAME_M_HU_LUTU_Configuration_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_HU_LUTU_Configuration lutuConfig = huLutuConfigurationTable.get(lutuConfigIdentifier);

			generateHUsWithLUTUConfiguration(tableRow, huReceiptSchedule, lutuConfig);
		}
	}

	@And("create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs")
	public void create_I_M_HU_LUTU_Configuration_for_M_ReceiptSchedule(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> tableRow : dataTable.asMaps())
		{
			final String receiptScheduleIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_ReceiptSchedule.COLUMNNAME_M_ReceiptSchedule_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_ReceiptSchedule receiptSchedule = receiptScheduleTable.get(receiptScheduleIdentifier);
			assertThat(receiptSchedule).isNotNull();
			InterfaceWrapperHelper.refresh(receiptSchedule);

			final de.metas.handlingunits.model.I_M_ReceiptSchedule huReceiptSchedule = InterfaceWrapperHelper.load(receiptSchedule.getM_ReceiptSchedule_ID(), de.metas.handlingunits.model.I_M_ReceiptSchedule.class);

			final I_M_HU_LUTU_Configuration lutuConfigDefault = huReceiptScheduleBL
					.createLUTUConfigurationManager(huReceiptSchedule)
					.getCreateLUTUConfiguration();

			final I_M_HU_LUTU_Configuration lutuConfig = computeLUTUConfiguration(lutuConfigDefault, tableRow);

			generateHUsWithLUTUConfiguration(tableRow, huReceiptSchedule, lutuConfig);
		}
	}

	private void generateHUsWithLUTUConfiguration(
			@NonNull final Map<String, String> tableRow,
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

		final Integer noOfHusGenerated = DataTableUtil.extractIntegerOrNullForColumnName(tableRow, "OPT.numberHUsGenerated");

		if (noOfHusGenerated != null)
		{
			final String huListIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "OPT.HUList." + TABLECOLUMN_IDENTIFIER);
			assertThat(hus.size()).isEqualTo(noOfHusGenerated);
			huListTable.putOrReplace(huListIdentifier, hus);
		}
		else
		{
			final String huIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_HU.COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER);
			assertThat(hus.size()).isEqualTo(1);
			huTable.putOrReplace(huIdentifier, hus.get(0));
		}
	}

	@NonNull
	private I_M_HU_LUTU_Configuration computeLUTUConfiguration(@NonNull final I_M_HU_LUTU_Configuration lutuConfig, @NonNull final Map<String, String> row)
	{
		final String piProductItemIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Integer huPiItemProductId = huPiItemProductTable.getOptional(piProductItemIdentifier)
				.map(I_M_HU_PI_Item_Product::getM_HU_PI_Item_Product_ID)
				.orElseGet(() -> Integer.parseInt(piProductItemIdentifier));
		assertThat(huPiItemProductId).isNotNull();

		final boolean isInfiniteQtyCU = DataTableUtil.extractBooleanForColumnName(row, I_M_HU_LUTU_Configuration.COLUMNNAME_IsInfiniteQtyCU);
		final BigDecimal qtyCUsPerTU = DataTableUtil.extractBigDecimalForColumnName(row, I_M_HU_LUTU_Configuration.COLUMNNAME_QtyCUsPerTU);

		// CU
		lutuConfig.setQtyCUsPerTU(qtyCUsPerTU);
		lutuConfig.setIsInfiniteQtyCU(isInfiniteQtyCU);

		// TU
		final boolean isInfiniteQtyTU = DataTableUtil.extractBooleanForColumnName(row, I_M_HU_LUTU_Configuration.COLUMNNAME_IsInfiniteQtyTU);
		final BigDecimal qtyTU = DataTableUtil.extractBigDecimalForColumnName(row, I_M_HU_LUTU_Configuration.COLUMNNAME_QtyTU);

		final I_M_HU_PI_Item_Product tuPIItemProduct = InterfaceWrapperHelper.create(Env.getCtx(), huPiItemProductId, I_M_HU_PI_Item_Product.class, ITrx.TRXNAME_None);
		final I_M_HU_PI tuPI = tuPIItemProduct.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI();
		lutuConfig.setM_HU_PI_Item_Product_ID(tuPIItemProduct.getM_HU_PI_Item_Product_ID());
		lutuConfig.setM_TU_HU_PI(tuPI);
		lutuConfig.setQtyTU(qtyTU);
		lutuConfig.setIsInfiniteQtyTU(isInfiniteQtyTU);

		// LU
		final boolean isInfiniteQtyLU = DataTableUtil.extractBooleanForColumnName(row, I_M_HU_LUTU_Configuration.COLUMNNAME_IsInfiniteQtyLU);
		final BigDecimal qtyLU = DataTableUtil.extractBigDecimalForColumnName(row, I_M_HU_LUTU_Configuration.COLUMNNAME_QtyLU);

		if (qtyLU.signum() > 0)
		{
			final String luHuPiIdentifier = DataTableUtil.extractStringForColumnName(row, "OPT.M_LU_HU_PI_ID." + TABLECOLUMN_IDENTIFIER);
			final Integer luHuPiId = huPiTable.getOptional(luHuPiIdentifier)
					.map(I_M_HU_PI::getM_HU_PI_ID)
					.orElseGet(() -> Integer.parseInt(luHuPiIdentifier));
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

		lutuConfig.setQtyLU(qtyLU);
		lutuConfig.setIsInfiniteQtyLU(isInfiniteQtyLU);

		InterfaceWrapperHelper.saveRecord(lutuConfig);

		final String lutuIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_HU_LUTU_Configuration.COLUMNNAME_M_HU_LUTU_Configuration_ID + "." + TABLECOLUMN_IDENTIFIER);
		huLutuConfigurationTable.putOrReplace(lutuIdentifier, lutuConfig);

		return lutuConfig;
	}
}

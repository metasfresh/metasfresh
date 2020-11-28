package de.metas.handlingunits.client.terminal.pporder.receipt.model;

/*
 * #%L
 * de.metas.handlingunits.client
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.compiere.model.I_C_UOM;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.ReceiptCostCollectorCandidate;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.ILUTUConfigurationEditor;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.client.terminal.lutuconfig.model.LUTUConfigurationEditorModel;
import de.metas.handlingunits.client.terminal.mmovement.exception.MaterialMovementException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IPPOrderReceiptHUProducer;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.PPOrderUtil;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;

import javax.annotation.Nullable;

public class HUPPOrderReceiptModel extends LUTUConfigurationEditorModel
{
	//
	// Services
	private final transient IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
	private final transient IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);
	private final transient IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);

	private List<I_M_HU> createdPlanningHUs = new ArrayList<>();

	public List<I_M_HU> getCreatedPlanningHUs()
	{
		return createdPlanningHUs;
	}

	public HUPPOrderReceiptModel(final ITerminalContext terminalContext)
	{
		super(terminalContext);
	}

	public final void load(final I_PP_Order ppOrder, final List<I_PP_Order_BOMLine> ppOrderBOMLines)
	{
		Check.assumeNotNull(ppOrder, "ppOrder not null");
		Check.assumeNotNull(ppOrderBOMLines, "ppOrderBOMLines not null");

		final List<HUPPOrderReceiptCUKey> receiptProductKeys = new ArrayList<>();

		//
		// Create Receipt Product Key from Order Header
		final HUPPOrderReceiptCUKey finishedGoodCUKey = createFinishedGoodsCUKey(ppOrder);
		receiptProductKeys.add(finishedGoodCUKey);

		//
		// Iterate PP Order BOM Lines and add a new Receipt Product Key for each By/Co-Product BOM Line
		for (final I_PP_Order_BOMLine ppOrderBOMLine : ppOrderBOMLines)
		{
			final HUPPOrderReceiptCUKey coProductCUKey = createCoProductCUKey(ppOrderBOMLine, ppOrder);
			if (coProductCUKey == null)
			{
				continue;
			}
			receiptProductKeys.add(coProductCUKey);
		}

		//
		// Set CU Keys to our layout panel
		getCUKeyLayout().setKeys(receiptProductKeys);

		//
		// By default preselect the configuration of the finished goods product (main product)
		getCUKeyLayout().keyReturned(finishedGoodCUKey);
		getCUKeyLayout().setSelectedKey(finishedGoodCUKey);
	}

	/**
	 * Creates CU Key for finished goods product (i.e. main product)
	 *
	 * @param ppOrder
	 * @return CU Key; never return null
	 */
	private final HUPPOrderReceiptCUKey createFinishedGoodsCUKey(final I_PP_Order ppOrder)
	{
		final ILUTUConfigurationEditor lutuConfigurationEditor = huPPOrderBL
				.createReceiptLUTUConfigurationManager(ppOrder)
				.startEditing()
				.updateFromModel();
		final I_M_HU_LUTU_Configuration lutuConfiguration = lutuConfigurationEditor.getEditingLUTUConfiguration();

		final I_C_UOM uom = ILUTUConfigurationFactory.extractUOMOrNull(lutuConfiguration);
		final ReceiptCostCollectorCandidate receiptCostCollectorCandidate = ReceiptCostCollectorCandidate.builder()
				.order(ppOrder)
				.productId(ProductId.ofRepoId(lutuConfiguration.getM_Product_ID()))
				.qtyToReceive(Quantity.zero(uom))
				.build();

		final Quantity qtyToReceiveTarget = ppOrderBOMBL.getQuantities(ppOrder).getQtyRemainingToProduce();

		final HUPPOrderReceiptCUKey cuKey = new HUPPOrderReceiptCUKey(getTerminalContext(), lutuConfigurationEditor, receiptCostCollectorCandidate, qtyToReceiveTarget);
		loadCUKeyRecursively(cuKey, lutuConfiguration);

		return cuKey;
	}

	/**
	 * Creates CU Key for co/by-product receipt
	 *
	 * @return CU Key or null
	 */
	@Nullable
	private final HUPPOrderReceiptCUKey createCoProductCUKey(final I_PP_Order_BOMLine ppOrderBOMLine, final I_PP_Order ppOrder)
	{
		// Make sure we can receive on this BOM Line
		if (!BOMComponentType.ofCode(ppOrderBOMLine.getComponentType()).isReceipt())
		{
			return null;
		}

		final ILUTUConfigurationEditor lutuConfigurationEditor = huPPOrderBL
				.createReceiptLUTUConfigurationManager(ppOrderBOMLine)
				.startEditing()
				.updateFromModel();
		final I_M_HU_LUTU_Configuration lutuConfiguration = lutuConfigurationEditor.getEditingLUTUConfiguration();

		final I_C_UOM uom = ILUTUConfigurationFactory.extractUOMOrNull(lutuConfiguration);
		final ReceiptCostCollectorCandidate receiptCostCollectorCandidate = ReceiptCostCollectorCandidate.builder()
				.order(ppOrder)
				.orderBOMLine(ppOrderBOMLine)
				.productId(ProductId.ofRepoId(lutuConfiguration.getM_Product_ID()))
				.qtyToReceive(Quantity.zero(uom))
				.build();

		final Quantity qtyToReceiveTarget = ppOrderBOMBL.getQtyToReceive(ppOrderBOMLine);

		final HUPPOrderReceiptCUKey cuKey = new HUPPOrderReceiptCUKey(getTerminalContext(), lutuConfigurationEditor, receiptCostCollectorCandidate, qtyToReceiveTarget);
		loadCUKeyRecursively(cuKey, lutuConfiguration);

		return cuKey;
	}

	@Override
	public final void execute() throws MaterialMovementException
	{
		final HUPPOrderReceiptCUKey productKey = getSelectedCUKey(HUPPOrderReceiptCUKey.class);

		//
		// Get Total Qty that we need to receive
		final BigDecimal qtyToReceive = getTotalQtyCU();
		if (qtyToReceive == null || qtyToReceive.signum() <= 0)
		{
			throw new MaterialMovementException("@Invalid@ @QtyCU@ (@Total@)");
		}

		//
		// Update LU/TU configuration
		final ILUTUConfigurationEditor lutuConfigurationEditor = productKey.getLUTUConfigurationEditor();
		save(lutuConfigurationEditor); // save edited configuration back
		//
		lutuConfigurationEditor
				.save() // save configuration to database
				.pushBackToModel(); // make sure it's set back in model
		//
		final I_M_HU_LUTU_Configuration luTuConfiguration = lutuConfigurationEditor.getLUTUConfiguration();

		// Get UOM
		final I_C_UOM qtyToReceiveUOM = ILUTUConfigurationFactory.extractUOMOrNull(luTuConfiguration);

		//
		// Receive new HUs, update their attributes, and automatically assign them to PP_Order/PP_Order_BOMLine
		final IPPOrderReceiptHUProducer producer = productKey.createReceiptCandidatesProducer();
		createdPlanningHUs = producer.createPlanningHUs(Quantity.of(qtyToReceive, qtyToReceiveUOM));
	}

}

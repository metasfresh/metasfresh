package de.metas.handlingunits.client.terminal.pporder.receipt.view;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.Set;

import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.eevolution.api.IDDOrderBL;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IReceiptCostCollectorCandidate;
import org.eevolution.model.I_PP_Cost_Collector;

import de.metas.adempiere.form.terminal.ITerminalDialog;
import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUTrxBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.allocation.impl.IMutableAllocationResult;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUKey;
import de.metas.handlingunits.client.terminal.editor.view.HUEditorPanel;
import de.metas.handlingunits.client.terminal.lutuconfig.model.CUKey;
import de.metas.handlingunits.client.terminal.pporder.receipt.model.HUPPOrderReceiptCUKey;
import de.metas.handlingunits.client.terminal.pporder.receipt.model.HUPPOrderReceiptHUEditorModel;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.pporder.api.IHUPPCostCollectorBL;
import de.metas.handlingunits.storage.IHUProductStorage;

public final class HUPPOrderReceiptHUEditorPanel extends HUEditorPanel
{
	public HUPPOrderReceiptHUEditorPanel(final HUPPOrderReceiptHUEditorModel model)
	{
		super(model);
	}

	@Override
	protected HUPPOrderReceiptHUEditorModel getHUEditorModel()
	{
		return (HUPPOrderReceiptHUEditorModel)super.getHUEditorModel();
	}

	@Override
	protected final void onDialogOkAfterSave(final ITerminalDialog dialog)
	{
		//
		// Run super first, to make sure model is saved
		super.onDialogOkAfterSave(dialog);

		final HUPPOrderReceiptHUEditorModel model = getHUEditorModel();

		final Set<HUKey> selectedHUKeys = model.getSelectedHUKeys();
		if (selectedHUKeys == null || selectedHUKeys.isEmpty())
		{
			throw new TerminalException("@NoSelection@");
		}

		// get the selected cuKey
		final CUKey cuKey = model.getCUKey();

		if (!(cuKey instanceof HUPPOrderReceiptCUKey))
		{
			model.doMoveForwardSelectedHUs();
			return;
		}

		final HUPPOrderReceiptCUKey orderReceiptCUKey = (HUPPOrderReceiptCUKey)cuKey;

		final IReceiptCostCollectorCandidate receiptCostCollectorCandidate = orderReceiptCUKey.getReceiptCostCollectorCandidate();

		BigDecimal qtyCUTotal = BigDecimal.ZERO;
		for (final HUKey huKey : selectedHUKeys)
		{
			// activate the selected HUs
			final I_M_HU selectedHU = huKey.getM_HU();

			final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(selectedHU);
			final IHUContext huContext = Services.get(IHandlingUnitsBL.class).createMutableHUContext(contextProvider);

			Services.get(IHandlingUnitsBL.class).setHUStatus(huContext, selectedHU, X_M_HU.HUSTATUS_Active);

			final IHUProductStorage productStorage = huKey.getProductStorageOrNull(receiptCostCollectorCandidate.getM_Product());
			if (productStorage == null)
			{
				continue;
			}

			final BigDecimal productStorageQty = productStorage.getQty(receiptCostCollectorCandidate.getC_UOM());
			qtyCUTotal = qtyCUTotal.add(productStorageQty);
		}
		receiptCostCollectorCandidate.setQtyToReceive(qtyCUTotal);

		final I_PP_Cost_Collector collector = Services.get(IPPCostCollectorBL.class).createReceipt(receiptCostCollectorCandidate);
		Services.get(IHUPPCostCollectorBL.class).assignHUs(collector, model.getSelectedHUs());

		//
		// Complete forward DD Order if exists.
		// We need to do this after first MO receipt, to allow the Bereitsteller to move the materials forward.
		// task 07395
		Services.get(IDDOrderBL.class).completeForwardDDOrdersIfNeeded(collector.getPP_Order());
	}

	@Override
	protected void onDialogOkBeforeSave(final ITerminalDialog dialog)
	{
		// 08077
		// Logic moved from de.metas.handlingunits.pporder.api.impl.PPOrderHUAssignmentListener.onHUAssigned(I_M_HU, Object, String)
		// This is the place when we want the HUs to be activated
		// service
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		final HUPPOrderReceiptHUEditorModel model = getHUEditorModel();

		final Set<HUKey> selectedHUKeys = model.getSelectedHUKeys();
		if (selectedHUKeys == null || selectedHUKeys.isEmpty())
		{
			throw new TerminalException("@NoSelection@");
		}

		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(model.getPP_Order());
		final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);

		huTrxBL.createHUContextProcessorExecutor(contextProvider)
				.run(new IHUContextProcessor()
				{

					@Override
					public IMutableAllocationResult process(final IHUContext huContext)
					{
						for (final HUKey huKey : selectedHUKeys)
						{
							final I_M_HU hu = huKey.getM_HU();

							final boolean isPhysicalHU = handlingUnitsBL.isPhysicalHU(hu.getHUStatus());

							if (isPhysicalHU)
							{
								// in case of a physical HU, we don't need to activate and collect it for the empties movements, because that was already done.
								// concrete case: in both empfang and verteilung the boxes were coming from gebindelager to our current warehouse
								// ... but when you get to verteilung the boxes are already there
								continue;
							}
							handlingUnitsBL.setHUStatus(huContext, hu, X_M_HU.HUSTATUS_Active); // actually settings the status
							final String trxName = huContext.getTrxName();
							InterfaceWrapperHelper.save(hu, trxName);

							//
							// Ask the API to get the packing materials needed to the HU which we just activate it
							huContext.getHUPackingMaterialsCollector().removeHURecursively(hu);
						}
						return NULL_RESULT; // don't care
					}

				});

	}

}

package de.metas.ui.web.pporder.process;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.eevolution.model.I_PP_Order_BOMLine;

import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IPPOrderReceiptHUProducer;
import de.metas.printing.esb.base.util.Check;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.pporder.PPOrderLineRow;
import de.metas.ui.web.pporder.PPOrderLineType;
import de.metas.ui.web.pporder.PPOrderLinesView;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class WEBUI_PP_Order_Receipt
		extends WEBUI_PP_Order_Template
		implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	// services
	private final transient IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);
	private final transient IViewsRepository viewsRepo = Adempiere.getBean(IViewsRepository.class);

	// parameters
	@Param(parameterName = PackingInfoProcessParams.PARAM_M_HU_PI_Item_Product_ID, mandatory = true)
	private I_M_HU_PI_Item_Product p_M_HU_PI_Item_Product;

	@Param(parameterName = PackingInfoProcessParams.PARAM_M_HU_PI_Item_ID, mandatory = false)
	private I_M_HU_PI_Item p_M_HU_PI_Item;

	@Param(parameterName = PackingInfoProcessParams.PARAM_QtyCU, mandatory = true)
	private BigDecimal p_QtyCU;

	@Param(parameterName = PackingInfoProcessParams.PARAM_QtyTU, mandatory = true)
	private BigDecimal p_QtyTU;

	@Param(parameterName = PackingInfoProcessParams.PARAM_QtyLU, mandatory = false)
	private BigDecimal p_QtyLU;

	private transient PackingInfoProcessParams _packingInfoParams;

	/**
	 * Makes sure that an instance exists and is in sync with this processe's parameters.
	 * 
	 * @return
	 */
	private PackingInfoProcessParams getPackingInfoParams()
	{
		if (_packingInfoParams == null)
		{
			_packingInfoParams = PackingInfoProcessParams.builder()
					.defaultLUTUConfigManager(createDefaultLUTUConfigManager(getSingleSelectedRow()))
					.enforcePhysicalTU(false) // allow to to produce just the CU, without a TU etc..maybe later we'll add a sysconfig for this
					.build();
		}

		// Update it from the user-selected process parameters
		if (p_M_HU_PI_Item_Product != null)
		{
			_packingInfoParams.setTU_HU_PI_Item_Product_ID(p_M_HU_PI_Item_Product.getM_HU_PI_Item_Product_ID());
		}

		if (p_M_HU_PI_Item != null) // this parameter is not mandatory
		{
			_packingInfoParams.setLuPiItemId(p_M_HU_PI_Item.getM_HU_PI_Item_ID());
		}
		else
		{
			_packingInfoParams.setLuPiItemId(0);
		}

		_packingInfoParams.setQtyLU(p_QtyLU);
		_packingInfoParams.setQtyTU(p_QtyTU);
		_packingInfoParams.setQtyCU(p_QtyCU);

		return _packingInfoParams;
	}

	private IDocumentLUTUConfigurationManager createDefaultLUTUConfigManager(@NonNull final PPOrderLineRow row)
	{
		final PPOrderLineType type = row.getType();
		if (type == PPOrderLineType.MainProduct)
		{
			final int ppOrderId = row.getPP_Order_ID();
			final I_PP_Order ppOrder = load(ppOrderId, I_PP_Order.class);
			return huPPOrderBL.createReceiptLUTUConfigurationManager(ppOrder);
		}
		else if (type == PPOrderLineType.BOMLine_ByCoProduct)
		{
			final int ppOrderBOMLineId = row.getPP_Order_BOMLine_ID();
			final I_PP_Order_BOMLine ppOrderBOMLine = load(ppOrderBOMLineId, I_PP_Order_BOMLine.class);
			return huPPOrderBL.createReceiptLUTUConfigurationManager(ppOrderBOMLine);
		}
		else
		{
			throw new AdempiereException("Receiving is not allowed");
		}
	}

	private final IPPOrderReceiptHUProducer createReceiptCandidatesProducer(final PPOrderLineRow row)
	{
		final PPOrderLineType type = row.getType();
		if (type == PPOrderLineType.MainProduct)
		{
			final int ppOrderId = row.getPP_Order_ID();
			final I_PP_Order ppOrder = load(ppOrderId, I_PP_Order.class);
			return IPPOrderReceiptHUProducer.receiveMainProduct(ppOrder);
		}
		else if (type == PPOrderLineType.BOMLine_ByCoProduct)
		{
			final int ppOrderBOMLineId = row.getPP_Order_BOMLine_ID();
			final I_PP_Order_BOMLine ppOrderBOMLine = load(ppOrderBOMLineId, I_PP_Order_BOMLine.class);
			return IPPOrderReceiptHUProducer.receiveByOrCoProduct(ppOrderBOMLine);
		}
		else
		{
			throw new AdempiereException("Receiving is not allowed");
		}
	}

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getSelectedRowIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final PPOrderLinesView ppOrder = getView();
		if (!(ppOrder.isStatusPlanning() || ppOrder.isStatusReview()))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not planning or in review");
		}

		final PPOrderLineRow ppOrderLineRow = getSingleSelectedRow();
		if (!ppOrderLineRow.isReceipt())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("ppOrderLineRow is not a receipt line; ppOrderLineRow=" + ppOrderLineRow);
		}
		if (ppOrderLineRow.isProcessed())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("ppOrderLineRow is already processed; ppOrderLineRow=" + ppOrderLineRow);
		}

		//
		// OK, Override caption with current packing info, if any
		final String packingInfo = getSingleSelectedRow().getPackingInfo();
		if (!Check.isEmpty(packingInfo, true))
		{
			return ProcessPreconditionsResolution.builder()
					.setCaptionOverride(packingInfo)
					.accept();
		}

		//
		// OK
		return ProcessPreconditionsResolution.accept();
	}

	/**
	 * <a href="https://github.com/metasfresh/metasfresh-webui-api/issues/528">metasfresh/metasfresh-webui-api#528</a>: never return the "No Packing Item" a.k.a. virtual {@code M_HU_PI_Item_Product_ID}.
	 */
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		return getPackingInfoParams().getParameterDefaultValue(parameter.getColumnName());
	}

	/**
	 *
	 * @return a list of PI item products that match the selected CU's product and partner, sorted by name.
	 */
	@ProcessParamLookupValuesProvider(parameterName = PackingInfoProcessParams.PARAM_M_HU_PI_Item_Product_ID, dependsOn = {}, numericKey = true, lookupTableName = I_M_HU_PI_Item_Product.Table_Name)
	public LookupValuesList getM_HU_PI_Item_Products()
	{
		return getPackingInfoParams().getM_HU_PI_Item_Products();
	}

	/**
	 * For the currently selected pip this method loads att
	 * 
	 * @return
	 */
	@ProcessParamLookupValuesProvider(parameterName = PackingInfoProcessParams.PARAM_M_HU_PI_Item_ID, dependsOn = PackingInfoProcessParams.PARAM_M_HU_PI_Item_Product_ID, numericKey = true, lookupTableName = I_M_HU_PI_Item.Table_Name)
	public LookupValuesList getM_HU_PI_Item_IDs()
	{
		return getPackingInfoParams().getM_HU_PI_Item_IDs(p_M_HU_PI_Item_Product);
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final PPOrderLineRow selectedRow = getSingleSelectedRow();
		final IPPOrderReceiptHUProducer receiptCandidatesProducer = createReceiptCandidatesProducer(selectedRow);

		// Calculate and set the LU/TU config from packing info params and defaults
		final I_M_HU_LUTU_Configuration lutuConfig = getPackingInfoParams().createAndSaveNewLUTUConfig();
		receiptCandidatesProducer.setM_HU_LUTU_Configuration(lutuConfig);

		receiptCandidatesProducer.createReceiptCandidatesAndPlanningHUs();

		return MSG_OK;
	}

	@Override
	protected void postProcess(boolean success)
	{
		// Invalidate the view because for sure we have changes
		final PPOrderLinesView ppOrderLinesView = getView();
		ppOrderLinesView.invalidateAll();

		viewsRepo.notifyRecordChanged(I_PP_Order.Table_Name, ppOrderLinesView.getPP_Order_ID());
	}
}

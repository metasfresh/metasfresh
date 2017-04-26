package de.metas.ui.web.pporder.process;

import java.math.BigDecimal;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.eevolution.model.I_PP_Order_BOMLine;

import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
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
import de.metas.quantity.Quantity;
import de.metas.ui.web.pporder.PPOrderLineRow;
import de.metas.ui.web.pporder.PPOrderLineType;
import de.metas.ui.web.pporder.PPOrderLinesView;
import de.metas.ui.web.view.IDocumentViewsRepository;

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
	private final transient IDocumentViewsRepository viewsRepo = Adempiere.getBean(IDocumentViewsRepository.class);

	//
	// Parameters
	@Param(parameterName = PackingInfoProcessParams.PARAM_M_HU_PI_Item_Product_ID)
	private int p_M_HU_PI_Item_Product_ID;
	//
	@Param(parameterName = PackingInfoProcessParams.PARAM_M_LU_HU_PI_ID)
	private int p_M_LU_HU_PI_ID;
	//
	@Param(parameterName = PackingInfoProcessParams.PARAM_QtyCU)
	private BigDecimal p_QtyCU;
	//
	@Param(parameterName = PackingInfoProcessParams.PARAM_QtyTU)
	private BigDecimal p_QtyTU;
	//
	@Param(parameterName = PackingInfoProcessParams.PARAM_QtyLU)
	private BigDecimal p_QtyLU;

	private transient PackingInfoProcessParams _packingInfoParams;

	private PackingInfoProcessParams getPackingInfoParams()
	{
		if (_packingInfoParams == null)
		{
			_packingInfoParams = PackingInfoProcessParams.builder()
					.defaultLUTUConfigManager(createDefaultLUTUConfigManager(getSingleSelectedRow()))
					.build();
		}

		// Update it
		_packingInfoParams.setTU_HU_PI_Item_Product_ID(p_M_HU_PI_Item_Product_ID);
		_packingInfoParams.setLU_HU_PI_ID(p_M_LU_HU_PI_ID);
		_packingInfoParams.setQtyLU(p_QtyLU);
		_packingInfoParams.setQtyTU(p_QtyTU);
		_packingInfoParams.setQtyCU(p_QtyCU);

		return _packingInfoParams;
	}

	private IDocumentLUTUConfigurationManager createDefaultLUTUConfigManager(final PPOrderLineRow row)
	{
		final PPOrderLineType type = row.getType();
		if (type == PPOrderLineType.MainProduct)
		{
			final int ppOrderId = row.getPP_Order_ID();
			final I_PP_Order ppOrder = InterfaceWrapperHelper.create(getCtx(), ppOrderId, I_PP_Order.class, ITrx.TRXNAME_ThreadInherited);
			return huPPOrderBL.createReceiptLUTUConfigurationManager(ppOrder);
		}
		else if (type == PPOrderLineType.BOMLine_ByCoProduct)
		{
			final int ppOrderBOMLineId = row.getPP_Order_BOMLine_ID();
			final I_PP_Order_BOMLine ppOrderBOMLine = InterfaceWrapperHelper.create(getCtx(), ppOrderBOMLineId, I_PP_Order_BOMLine.class, ITrx.TRXNAME_ThreadInherited);
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
			final I_PP_Order ppOrder = InterfaceWrapperHelper.create(getCtx(), ppOrderId, I_PP_Order.class, ITrx.TRXNAME_ThreadInherited);
			return IPPOrderReceiptHUProducer.receiveMainProduct(ppOrder);
		}
		else if (type == PPOrderLineType.BOMLine_ByCoProduct)
		{
			final int ppOrderBOMLineId = row.getPP_Order_BOMLine_ID();
			final I_PP_Order_BOMLine ppOrderBOMLine = InterfaceWrapperHelper.create(getCtx(), ppOrderBOMLineId, I_PP_Order_BOMLine.class, ITrx.TRXNAME_ThreadInherited);
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
		if (getSelectedDocumentIds().size() != 1)
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final PPOrderLinesView ppOrder = getView();
		if (!(ppOrder.isStatusPlanning() || ppOrder.isStatusReview()))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not planning or in review");
		}

		final PPOrderLineRow ppOrderLine = getSingleSelectedRow();
		if (!ppOrderLine.isReceipt())
		{
			return ProcessPreconditionsResolution.reject("not a receipt line");
		}
		if (ppOrderLine.isProcessed())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("row processed");
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

	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		return getPackingInfoParams().getParameterDefaultValue(parameter.getColumnName());
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final PPOrderLineRow selectedRow = getSingleSelectedRow();
		final IPPOrderReceiptHUProducer receiptCandidatesProducer = createReceiptCandidatesProducer(selectedRow);

		//
		// Calculate and set the LU/TU config from packing info params and defaults
		final I_M_HU_LUTU_Configuration lutuConfig = getPackingInfoParams().createNewLUTUConfig();
		receiptCandidatesProducer.setM_HU_LUTU_Configuration(lutuConfig);

		//
		// Create
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

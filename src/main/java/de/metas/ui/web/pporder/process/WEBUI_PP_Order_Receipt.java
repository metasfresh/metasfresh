package de.metas.ui.web.pporder.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.eevolution.model.I_PP_Order_BOMLine;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IPPOrderReceiptHUProducer;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.quantity.Quantity;
import de.metas.ui.web.pporder.PPOrderLineRow;
import de.metas.ui.web.pporder.PPOrderLineType;

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
		implements IProcessPrecondition
{
	// services
	private final transient IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);
	private final transient ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);

	//
	// Parameters
	private static final String PARAM_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";
	@Param(parameterName = PARAM_M_HU_PI_Item_Product_ID)
	private int p_M_HU_PI_Item_Product_ID;
	//
	private static final String PARAM_M_LU_HU_PI_ID = "M_LU_HU_PI_ID";
	@Param(parameterName = PARAM_M_LU_HU_PI_ID)
	private int p_M_LU_HU_PI_ID;
	//
	private static final String PARAM_QtyCU = "QtyCU";
	@Param(parameterName = PARAM_QtyCU)
	private BigDecimal p_QtyCU;
	//
	private static final String PARAM_QtyTU = "QtyTU";
	@Param(parameterName = PARAM_QtyTU)
	private BigDecimal p_QtyTU;
	//
	private static final String PARAM_QtyLU = "QtyLU";
	@Param(parameterName = PARAM_QtyLU)
	private BigDecimal p_QtyLU;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedDocumentIds().size() != 1)
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final PPOrderLineRow row = getSingleSelectedRow();
		if (!row.isReceipt())
		{
			return ProcessPreconditionsResolution.reject("not a receipt line");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		createPlanningHUs();

		getView().invalidateAll();
		return MSG_OK;
	}

	private Collection<I_M_HU> createPlanningHUs()
	{
		final IPPOrderReceiptHUProducer receiptProducer;
		final I_M_HU_LUTU_Configuration lutuConfigTemplate;
		final PPOrderLineRow row = getSingleSelectedRow();
		final PPOrderLineType type = row.getType();
		if (type == PPOrderLineType.MainProduct)
		{
			final int ppOrderId = row.getPP_Order_ID();
			final I_PP_Order ppOrder = InterfaceWrapperHelper.create(getCtx(), ppOrderId, I_PP_Order.class, ITrx.TRXNAME_ThreadInherited);
			lutuConfigTemplate = huPPOrderBL.createReceiptLUTUConfigurationManager(ppOrder).getCreateLUTUConfiguration();
			receiptProducer = IPPOrderReceiptHUProducer.receiveMainProduct(ppOrder);
		}
		else if (type == PPOrderLineType.BOMLine_ByCoProduct)
		{
			final int ppOrderBOMLineId = row.getPP_Order_BOMLine_ID();
			final I_PP_Order_BOMLine ppOrderBOMLine = InterfaceWrapperHelper.create(getCtx(), ppOrderBOMLineId, I_PP_Order_BOMLine.class, ITrx.TRXNAME_ThreadInherited);
			lutuConfigTemplate = huPPOrderBL.createReceiptLUTUConfigurationManager(ppOrderBOMLine).getCreateLUTUConfiguration();
			receiptProducer = IPPOrderReceiptHUProducer.receiveByOrCoProduct(ppOrderBOMLine);
		}
		else
		{
			throw new AdempiereException("Receiving is not allowed");
		}

		//
		// Calculate and set the LU/TU config
		final I_M_HU_LUTU_Configuration lutuConfig = createM_HU_LUTU_Configuration(lutuConfigTemplate);
		lutuConfigurationFactory.save(lutuConfig);
		receiptProducer.setM_HU_LUTU_Configuration(lutuConfig);


		//
		// Calculate total quantity to receive
		final Quantity qtyCUsTotal = lutuConfigurationFactory.calculateQtyCUsTotal(lutuConfig);
		if(qtyCUsTotal.isZero())
		{
			throw new AdempiereException("Zero quantity to receive");
		}
		else if(qtyCUsTotal.isInfinite())
		{
			throw new AdempiereException("Quantity to receive was not determined");
		}

		//
		// Generate the HUs and return them
		final List<I_M_HU> planningHUs = receiptProducer.createReceiptCandidatesAndPlanningHUs(qtyCUsTotal.getQty(), qtyCUsTotal.getUOM());
		return planningHUs;
	}

	private I_M_HU_LUTU_Configuration createM_HU_LUTU_Configuration(final I_M_HU_LUTU_Configuration template)
	{
		// Validate parameters
		final int M_LU_HU_PI_ID = p_M_LU_HU_PI_ID;
		final int M_HU_PI_Item_Product_ID = p_M_HU_PI_Item_Product_ID;
		final BigDecimal qtyCU = p_QtyCU;
		final BigDecimal qtyTU = p_QtyTU;
		final BigDecimal qtyLU = p_QtyLU;
		if (M_LU_HU_PI_ID <= 0)
		{
			throw new FillMandatoryException(PARAM_M_LU_HU_PI_ID);
		}
		if (M_HU_PI_Item_Product_ID <= 0)
		{
			throw new FillMandatoryException(PARAM_M_HU_PI_Item_Product_ID);
		}
		if (qtyCU == null || qtyCU.signum() <= 0)
		{
			throw new FillMandatoryException(PARAM_QtyCU);
		}
		if (qtyTU == null || qtyTU.signum() <= 0)
		{
			throw new FillMandatoryException(PARAM_QtyTU);
		}
		if (qtyLU == null || qtyLU.signum() <= 0)
		{
			throw new FillMandatoryException(PARAM_QtyLU);
		}

		final I_M_HU_LUTU_Configuration lutuConfigurationNew = InterfaceWrapperHelper.copy()
				.setFrom(template)
				.copyToNew(I_M_HU_LUTU_Configuration.class);
		//
		// CU
		lutuConfigurationNew.setQtyCU(qtyCU);
		lutuConfigurationNew.setIsInfiniteQtyCU(false);

		//
		// TU
		final I_M_HU_PI_Item_Product tuPIItemProduct = InterfaceWrapperHelper.create(getCtx(), M_HU_PI_Item_Product_ID, I_M_HU_PI_Item_Product.class, ITrx.TRXNAME_None);
		final I_M_HU_PI tuPI = tuPIItemProduct.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI();
		lutuConfigurationNew.setM_HU_PI_Item_Product(tuPIItemProduct);
		lutuConfigurationNew.setM_TU_HU_PI(tuPI);
		lutuConfigurationNew.setQtyTU(qtyTU);
		lutuConfigurationNew.setIsInfiniteQtyTU(false);

		//
		// LU
		if (M_LU_HU_PI_ID > 0)
		{
			final I_M_HU_PI luPI = InterfaceWrapperHelper.create(getCtx(), M_LU_HU_PI_ID, I_M_HU_PI.class, ITrx.TRXNAME_None);

			final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
			final I_M_HU_PI_Version luPIV = handlingUnitsDAO.retrievePICurrentVersion(luPI);
			final List<I_M_HU_PI_Item> luPI_ItemsAvailable = handlingUnitsDAO.retrieveParentPIItemsForParentPI(tuPI, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit, lutuConfigurationNew.getC_BPartner());
			final I_M_HU_PI_Item luPI_Item = luPI_ItemsAvailable
					.stream()
					.filter(piItem -> piItem.getM_HU_PI_Version_ID() == luPIV.getM_HU_PI_Version_ID())
					.findFirst()
					.orElseThrow(() -> {
						final String luPI_ItemsAvailableStr = luPI_ItemsAvailable.stream()
								.map(item -> item.getM_HU_PI_Version().getM_HU_PI().getName())
								.distinct()
								.collect(Collectors.joining(", "));
						return new AdempiereException(tuPI.getName() + " cannot be loaded to " + luPI.getName()
								+ "\n Available LU PI items: " + luPI_ItemsAvailableStr);
					});;

			lutuConfigurationNew.setM_LU_HU_PI(luPI);
			lutuConfigurationNew.setM_LU_HU_PI_Item(luPI_Item);
			lutuConfigurationNew.setQtyLU(qtyLU);
			lutuConfigurationNew.setIsInfiniteQtyLU(false);
		}
		else
		{
			lutuConfigurationNew.setM_LU_HU_PI(null);
			lutuConfigurationNew.setM_LU_HU_PI_Item(null);
			lutuConfigurationNew.setQtyLU(null);
		}

		// InterfaceWrapperHelper.save(lutuConfigurationNew); // expected to not be saved (important)

		return lutuConfigurationNew;
	}

}

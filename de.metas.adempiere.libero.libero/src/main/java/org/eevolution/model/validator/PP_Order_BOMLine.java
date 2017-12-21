package org.eevolution.model.validator;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
import java.math.RoundingMode;

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.ModelValidator;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.X_PP_Order_BOMLine;

import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.LiberoException;

@Validator(I_PP_Order_BOMLine.class)
public class PP_Order_BOMLine
{
	private static final String DYNATTR_ExplodePhantomRunnable = PP_Order_BOMLine.class.getName() + "#explodePhantomRunnable";
	
	@Init
	public void init()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(new org.eevolution.callout.PP_Order_BOMLine());
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_PP_Order_BOMLine orderBOMLine, final int changeType)
	{
		final IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);

		final boolean newRecord = ModelValidator.TYPE_BEFORE_NEW == changeType;

		// Victor Perez: The best practice in this case you do should change the component you need
		// adding a new line in Order BOM Line with new component so do not is right
		// delete or change a component because this information is use to calculate
		// the variances cost ( https://sourceforge.net/tracker/?func=detail&atid=934929&aid=2724579&group_id=176962 )
		if (!orderBOMLine.isActive())
		{
			throw new LiberoException("De-Activating an BOM Line is not allowed"); // TODO: translate
		}
		if (!newRecord && InterfaceWrapperHelper.isValueChanged(orderBOMLine, I_PP_Order_BOMLine.COLUMNNAME_M_Product_ID))
		{
			throw new LiberoException("Changing Product is not allowed"); // TODO: translate
		}

		//
		// Get Line No
		if (orderBOMLine.getLine() == 0)
		{
			final int line = Services.get(IPPOrderBOMDAO.class).retrieveNextLineNo(orderBOMLine.getPP_Order());
			orderBOMLine.setLine(line);
		}

		//
		// If Phantom, we need to explode this line (see afterSave):
		if (newRecord && X_PP_Order_BOMLine.COMPONENTTYPE_Phantom.equals(orderBOMLine.getComponentType()))
		{
			final BigDecimal qtyOrderedForPhantom = orderBOMLine.getQtyRequiered();
			orderBOMLine.setQtyRequiered(BigDecimal.ZERO);

			final Runnable explodePhantomRunnable = new Runnable()
			{
				@Override
				public void run()
				{
					ppOrderBOMBL.explodePhantom(orderBOMLine, qtyOrderedForPhantom);
				}
			};
			InterfaceWrapperHelper.setDynAttribute(orderBOMLine, DYNATTR_ExplodePhantomRunnable, explodePhantomRunnable);
		}

		if (newRecord
				|| InterfaceWrapperHelper.isValueChanged(orderBOMLine, I_PP_Order_BOMLine.COLUMNNAME_C_UOM_ID)
				|| InterfaceWrapperHelper.isValueChanged(orderBOMLine, I_PP_Order_BOMLine.COLUMNNAME_QtyEntered)
				|| InterfaceWrapperHelper.isValueChanged(orderBOMLine, I_PP_Order_BOMLine.COLUMNNAME_QtyRequiered))
		{
			final I_C_UOM uom = orderBOMLine.getC_UOM();
			final int precision = uom.getStdPrecision();
			orderBOMLine.setQtyEntered(orderBOMLine.getQtyEntered().setScale(precision, RoundingMode.UP));
			orderBOMLine.setQtyRequiered(orderBOMLine.getQtyRequiered().setScale(precision, RoundingMode.UP));
		}

		//
		// Validate Warehouse/Locator
		if (InterfaceWrapperHelper.isValueChanged(orderBOMLine, I_PP_Order_BOMLine.COLUMNNAME_M_Warehouse_ID)
				|| InterfaceWrapperHelper.isValueChanged(orderBOMLine, I_PP_Order_BOMLine.COLUMNNAME_M_Locator_ID))
		{
			final int warehouseId = orderBOMLine.getM_Warehouse_ID();
			final I_M_Locator locator = orderBOMLine.getM_Locator();
			if (locator == null || locator.getM_Locator_ID() <= 0 || locator.getM_Warehouse_ID() != warehouseId)
			{
				final I_M_Warehouse warehouse = orderBOMLine.getM_Warehouse();
				final I_M_Locator locatorNew = Services.get(IWarehouseBL.class).getDefaultLocator(warehouse);
				orderBOMLine.setM_Locator(locatorNew);
			}
		}

		if (!newRecord
				&& (InterfaceWrapperHelper.isValueChanged(orderBOMLine, I_PP_Order_BOMLine.COLUMNNAME_QtyDelivered)
						|| InterfaceWrapperHelper.isValueChanged(orderBOMLine, I_PP_Order_BOMLine.COLUMNNAME_QtyRequiered)
						|| InterfaceWrapperHelper.isValueChanged(orderBOMLine, I_PP_Order_BOMLine.COLUMNNAME_M_Warehouse_ID)
						|| InterfaceWrapperHelper.isValueChanged(orderBOMLine, I_PP_Order_BOMLine.COLUMNNAME_M_Locator_ID)
						|| InterfaceWrapperHelper.isValueChanged(orderBOMLine, I_PP_Order_BOMLine.COLUMNNAME_Processed) // changed to processed
				) //
		) //
		{
			ppOrderBOMBL.reserveStock(orderBOMLine);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void afterSave(final I_PP_Order_BOMLine orderBOMLine)
	{
		final Runnable explodePhantomRunnable = InterfaceWrapperHelper.getDynAttribute(orderBOMLine, DYNATTR_ExplodePhantomRunnable);
		if (explodePhantomRunnable != null)
		{
			explodePhantomRunnable.run();
			InterfaceWrapperHelper.setDynAttribute(orderBOMLine, DYNATTR_ExplodePhantomRunnable, null);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void beforeDelete(final I_PP_Order_BOMLine orderBOMLine)
	{
		// Release Reservation
		orderBOMLine.setQtyRequiered(BigDecimal.ZERO);
		Services.get(IPPOrderBOMBL.class).reserveStock(orderBOMLine);
	}

	public void updateReservationOnWarehouseLocatorChange(final I_PP_Order_BOMLine orderBOMLine)
	{

	}

}

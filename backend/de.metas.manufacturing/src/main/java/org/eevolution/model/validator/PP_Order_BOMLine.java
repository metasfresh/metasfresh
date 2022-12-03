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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.material.planning.pporder.impl.PPOrderBOMBL;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.ModelValidator;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order_BOMLine;

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
	public void beforeSave(final I_PP_Order_BOMLine orderBOMLine, final ModelChangeType changeType)
	{
		final IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);

		final boolean newRecord = changeType.isNew();

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
			final PPOrderId orderId = PPOrderId.ofRepoId(orderBOMLine.getPP_Order_ID());
			final int line = Services.get(IPPOrderBOMDAO.class).retrieveNextLineNo(orderId);
			orderBOMLine.setLine(line);
		}

		//
		// If Phantom, we need to explode this line (see afterSave):
		if (newRecord && BOMComponentType.ofCode(orderBOMLine.getComponentType()).isPhantom())
		{
			final Quantity qtyRequired = ppOrderBOMBL.getQtyRequiredToIssue(orderBOMLine);
			ppOrderBOMBL.setQtyRequiredToIssueOrReceive(orderBOMLine, qtyRequired.toZero());

			final Runnable explodePhantomRunnable = () -> ppOrderBOMBL.explodePhantom(orderBOMLine, qtyRequired);
			InterfaceWrapperHelper.setDynAttribute(orderBOMLine, DYNATTR_ExplodePhantomRunnable, explodePhantomRunnable);
		}

		//
		// Validate Warehouse/Locator
		if (InterfaceWrapperHelper.isValueChanged(orderBOMLine, I_PP_Order_BOMLine.COLUMNNAME_M_Warehouse_ID)
				|| InterfaceWrapperHelper.isValueChanged(orderBOMLine, I_PP_Order_BOMLine.COLUMNNAME_M_Locator_ID))
		{
			final WarehouseId warehouseId = WarehouseId.ofRepoId(orderBOMLine.getM_Warehouse_ID());
			final LocatorId locatorId = Services.get(IWarehouseDAO.class).getLocatorIdByRepoIdOrNull(orderBOMLine.getM_Locator_ID());
			if (locatorId == null || !locatorId.getWarehouseId().equals(warehouseId))
			{
				final LocatorId locatorIdToUse = Services.get(IWarehouseBL.class).getDefaultLocatorId(warehouseId);
				orderBOMLine.setM_Locator_ID(locatorIdToUse.getRepoId());
			}
		}

		//
		// Validate Issuing Tolerance
		PPOrderBOMBL.extractIssuingToleranceSpec(orderBOMLine);
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
}

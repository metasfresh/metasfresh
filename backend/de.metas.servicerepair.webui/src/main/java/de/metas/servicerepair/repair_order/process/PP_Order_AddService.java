/*
 * #%L
 * de.metas.servicerepair.webui
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.servicerepair.repair_order.process;

import de.metas.process.IProcessParametersCallout;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.servicerepair.repair_order.RepairManufacturingOrderService;
import de.metas.servicerepair.repair_order.model.RepairManufacturingOrderServicePerformed;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;

import java.math.BigDecimal;
import java.util.HashMap;

public class PP_Order_AddService extends JavaProcess
		implements IProcessPrecondition, IProcessParametersCallout
{
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final RepairManufacturingOrderService repairManufacturingOrderService = SpringContextHolder.instance.getBean(RepairManufacturingOrderService.class);

	private static final String PARAM_M_Product_ID = "M_Product_ID";
	@Param(parameterName = PARAM_M_Product_ID, mandatory = true)
	private ProductId serviceProductId;

	@Param(parameterName = "C_UOM_ID", mandatory = true)
	private UomId uomId;

	@Param(parameterName = "Qty", mandatory = true)
	private BigDecimal qtyBD;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final PPOrderId orderId = PPOrderId.ofRepoId(context.getSingleSelectedRecordId());
		return checkPreconditionsApplicable(orderId);
	}

	private ProcessPreconditionsResolution checkPreconditionsApplicable(final PPOrderId orderId)
	{
		if (!repairManufacturingOrderService.isCompletedRepairOrder(orderId))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not a completed repair manufacturing order");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	public void onParameterChanged(final String parameterName)
	{
		if (PARAM_M_Product_ID.equals(parameterName))
		{
			if (serviceProductId != null)
			{
				final RepairManufacturingOrderServicePerformed servicePerformed = repairManufacturingOrderService
						.getPerformedService(getOrderId(), serviceProductId)
						.orElse(null);

				if (servicePerformed != null)
				{
					this.uomId = servicePerformed.getQty().getUomId();
					this.qtyBD = servicePerformed.getQty().toBigDecimal();
				}
				else
				{
					this.uomId = productBL.getStockUOMId(serviceProductId);
					this.qtyBD = BigDecimal.ONE;
				}
			}
		}
	}

	@Override
	protected String doIt()
	{
		repairManufacturingOrderService.updateServicePerformed(
				getOrderId(),
				serviceProductId,
				Quantitys.create(qtyBD, uomId));

		return MSG_OK;
	}

	private PPOrderId getOrderId()
	{
		return PPOrderId.ofRepoId(getRecord_ID());
	}
}

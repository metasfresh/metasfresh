package org.eevolution.mrp.spi.impl;

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
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Set;

import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.mrp.api.IMRPBL;
import org.eevolution.mrp.api.IMRPCreateSupplyRequest;
import org.eevolution.mrp.api.IMRPDemandToSupplyAllocation;
import org.eevolution.mrp.api.IMRPExecutor;
import org.eevolution.mrp.spi.IMRPSupplyProducer;

import de.metas.material.planning.IMaterialPlanningContext;

/**
 * An {@link IMRPSupplyProducer} implementation which just creates a Supply MRP record to balance the demand (see {@link #createSupply(IMaterialPlanningContext, IMRPExecutor)}).
 *
 * @author tsa
 *
 */
@org.junit.Ignore
public class MockedMRPSupplyProducer implements IMRPSupplyProducer
{
	@Override
	public Class<?> getDocumentClass()
	{
		return null;
	}

	@Override
	public Set<String> getSourceTableNames()
	{
		return Collections.emptySet();
	}

	@Override
	public boolean applies(IMaterialPlanningContext mrpContext, IMutable<String> notAppliesReason)
	{
		return true;
	}

	@Override
	public void onRecordChange(Object model, ModelChangeType changeType)
	{
		// nothing
	}

	@Override
	public void onDocumentChange(Object model, DocTimingType timing)
	{
		// nothing
	}

	@Override
	public boolean isRecreatedMRPRecordsSupported(String tableName)
	{
		return false;
	}

	@Override
	public void recreateMRPRecords(Object model)
	{
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public void createSupply(final IMRPCreateSupplyRequest request)
	{
		final IMaterialPlanningContext mrpContext = request.getMrpContext();
		final I_PP_Product_Planning productPlanningData = mrpContext.getProductPlanning();

		final BigDecimal qtyToSupply = request.getQtyToSupply().getQty();
		final Timestamp supplyDateFinishSchedule = TimeUtil.asTimestamp(request.getDemandDate());
		final int durationDays = productPlanningData.getDeliveryTime_Promised().intValueExact();
		final Timestamp supplyDateStartSchedule = TimeUtil.addDays(supplyDateFinishSchedule, 0 - durationDays);

		final I_PP_MRP mrp = InterfaceWrapperHelper.newInstance(I_PP_MRP.class, mrpContext);
		mrp.setIsActive(true);

		// Dimension
		mrp.setAD_Org(mrpContext.getAD_Org());
		mrp.setM_Warehouse(mrpContext.getM_Warehouse());
		mrp.setS_Resource(mrpContext.getPlant());

		// Product & Qty
		final I_M_Product product = mrpContext.getM_Product();
		mrp.setM_Product(product);
		Services.get(IMRPBL.class).setQty(mrp, qtyToSupply, qtyToSupply, product.getC_UOM());

		// Dates
		mrp.setDateStartSchedule(supplyDateStartSchedule);
		mrp.setDateFinishSchedule(supplyDateFinishSchedule);
		mrp.setDateOrdered(supplyDateStartSchedule);
		mrp.setDatePromised(supplyDateFinishSchedule);

		// Type
		mrp.setTypeMRP(X_PP_MRP.TYPEMRP_Supply);
		mrp.setDocStatus(X_PP_MRP.DOCSTATUS_Completed);
		mrp.setOrderType(X_PP_MRP.ORDERTYPE_PurchaseOrder);

		// NOTE: we need to mark IsAvailable=N because else we will get MRP-050 for these records
		mrp.setIsAvailable(false);

		InterfaceWrapperHelper.save(mrp);
	}

	@Override
	public void cleanup(IMaterialPlanningContext mrpContext, IMRPExecutor executor)
	{
		// nothing
	}

	@Override
	public void onQtyOnHandReservation(final IMaterialPlanningContext mrpContext,
			final IMRPExecutor mrpExecutor,
			final IMRPDemandToSupplyAllocation mrpDemandToSupplyAllocation)
	{
		// nothing
	}
}

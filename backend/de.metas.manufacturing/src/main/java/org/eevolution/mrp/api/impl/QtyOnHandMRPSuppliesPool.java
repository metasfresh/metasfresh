package org.eevolution.mrp.api.impl;

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
import java.util.List;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.adempiere.util.text.annotation.ToStringBuilder;
import org.compiere.model.I_C_UOM;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.mrp.api.IMRPBL;
import org.eevolution.mrp.api.IMRPContextRunnable;
import org.eevolution.mrp.api.IMRPDAO;
import org.eevolution.mrp.api.IMRPDemandToSupplyAllocation;
import org.eevolution.mrp.api.IMRPSuppliesPool;

import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.IMutableMRPContext;
import de.metas.util.Check;
import de.metas.util.Services;

public class QtyOnHandMRPSuppliesPool implements IMRPSuppliesPool
{
	//
	// Services
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IMRPBL mrpBL = Services.get(IMRPBL.class);

	@ToStringBuilder(skip = true)
	private final IMaterialPlanningContext _mrpContext;
	private final BigDecimal _qtyOnHandInitial;

	public QtyOnHandMRPSuppliesPool(final IMaterialPlanningContext mrpContext)
	{
		super();

		Check.assumeNotNull(mrpContext, "mrpContext not null");
		_mrpContext = mrpContext;

		final BigDecimal qtyOnHand = mrpContext.getQtyProjectOnHand();
		Check.assumeNotNull(qtyOnHand, "qtyOnHand not null");
		_qtyOnHandInitial = qtyOnHand;
	}

	private IMaterialPlanningContext getMRPContext()
	{
		return _mrpContext;
	}

	@Override
	public void setMRPSupplies(final List<IMutableMRPRecordAndQty> mrpSupplies)
	{
		// nothing because we don't care about current MRP supplies
	}

	@Override
	public List<IMRPDemandToSupplyAllocation> allocate(final IMutableMRPRecordAndQty mrpDemand)
	{
		Check.assumeNotNull(mrpDemand, "mrpDemand not null");

		final BigDecimal qtyDemandToAllocate = mrpDemand.getQty();
		if (qtyDemandToAllocate.signum() <= 0)
		{
			return Collections.emptyList();
		}

		final BigDecimal qtyProjectedOnHandAvailable = getQtyOnHandAvailable();

		//
		// Calculate how much we can reserve from our Projected QtyOnHand (PQOH)
		final BigDecimal qtyProjectedOnHandToReserve = qtyProjectedOnHandAvailable.min(qtyDemandToAllocate);
		if (qtyProjectedOnHandToReserve.signum() <= 0)
		{
			return Collections.emptyList(); // nothing to reserve
		}

		//
		// Create MRP Qty On Hand reservation.
		final I_PP_MRP mrpSupplyRecord = createMRPSupplyForQtyOnHandReservation(mrpDemand, qtyProjectedOnHandToReserve);

		//
		// Create allocation
		final IMRPDemandToSupplyAllocation alloc = new MRPDemandToSupplyAllocation(mrpDemand.getPP_MRP(), mrpSupplyRecord, qtyProjectedOnHandToReserve);

		//
		// Process allocation
		// => decrease QtyOnHand available
		// => decrease Demand Qty available to allocate
		{
			final BigDecimal qtyAllocated = alloc.getQtyAllocated();
			subtractQtyOnHandAvailable(qtyAllocated);
			mrpDemand.subtractQty(qtyAllocated);
		}

		return Collections.singletonList(alloc);
	}

	public BigDecimal getQtyOnHandInitial()
	{
		return _qtyOnHandInitial;
	}

	private BigDecimal getQtyOnHandAvailable()
	{
		return getMRPContext().getQtyProjectOnHand();
	}

	private void subtractQtyOnHandAvailable(final BigDecimal qtyOnHand)
	{
		final IMaterialPlanningContext mrpContext = getMRPContext();
		BigDecimal qtyOnHandAvailable = mrpContext.getQtyProjectOnHand();
		qtyOnHandAvailable = qtyOnHandAvailable.subtract(qtyOnHand);
		mrpContext.setQtyProjectOnHand(qtyOnHandAvailable);
	}

	public boolean hasQtyOnHandAvailable()
	{
		return getQtyOnHandAvailable().signum() > 0;
	}

	private I_PP_MRP createMRPSupplyForQtyOnHandReservation(final IMRPRecordAndQty mrpDemand, final BigDecimal qtyProjectedOnHandToReserve)
	{
		final IMaterialPlanningContext mrpContext = getMRPContext();
		Check.assumeNotNull(mrpContext, "mrpContext not null");
		Check.assume(qtyProjectedOnHandToReserve.signum() > 0, "qtyProjectedOnHandToReserve > 0 but it was {}", qtyProjectedOnHandToReserve);
		trxManager.assertTrxNotNull(mrpContext);

		final I_PP_MRP mrpDemandRecord = mrpDemand.getPP_MRP();
		final I_C_UOM uom = mrpBL.getC_UOM(mrpDemandRecord);
		final Timestamp date = mrpDemandRecord.getDateStartSchedule();

		final IMutable<I_PP_MRP> mrpQtyOnHandToReturn = new Mutable<I_PP_MRP>();
		mrpBL.executeInMRPContext(mrpContext, new IMRPContextRunnable()
		{

			@Override
			public void run(final IMutableMRPContext mrpContextLocal)
			{
				mrpContextLocal.setMRPDemands(Collections.singletonList(mrpDemandRecord));

				final I_PP_MRP mrpQtyOnHand = mrpBL.createMRP(mrpContextLocal);
				// Planning segment
				mrpQtyOnHand.setAD_Org(mrpContextLocal.getAD_Org());
				mrpQtyOnHand.setM_Warehouse(mrpContextLocal.getM_Warehouse());
				mrpQtyOnHand.setS_Resource(mrpContextLocal.getPlant());
				mrpQtyOnHand.setM_Product(mrpContextLocal.getM_Product());
				// Type
				mrpQtyOnHand.setTypeMRP(X_PP_MRP.TYPEMRP_Supply);
				mrpQtyOnHand.setOrderType(X_PP_MRP.ORDERTYPE_QuantityOnHandReservation);
				mrpQtyOnHand.setDocStatus(X_PP_MRP.DOCSTATUS_Completed); // Firm
				// Qtys
				final BigDecimal qtyRequired = BigDecimal.ZERO;
				mrpBL.setQty(mrpQtyOnHand, qtyRequired, qtyProjectedOnHandToReserve, uom);
				// Dates
				mrpQtyOnHand.setDateOrdered(date);
				mrpQtyOnHand.setDatePromised(date);
				mrpQtyOnHand.setDateConfirm(date);
				mrpQtyOnHand.setDateStartSchedule(date);
				mrpQtyOnHand.setDateFinishSchedule(date);
				mrpQtyOnHand.setDateStart(date);
				// Other
				mrpQtyOnHand.setIsAvailable(false); // not available because it is allocated already (that's why we are doing this)
				mrpQtyOnHand.setPP_MRP_Parent(mrpDemandRecord);
				mrpQtyOnHand.setC_BPartner_ID(mrpDemandRecord.getC_BPartner_ID());
				mrpQtyOnHand.setC_OrderLineSO_ID(mrpDemandRecord.getC_OrderLineSO_ID());
				//
				// Save it
				Services.get(IMRPDAO.class).save(mrpQtyOnHand);
				mrpQtyOnHandToReturn.setValue(mrpQtyOnHand);

				// create MRP demand - MRP supply allocation
				// NOTE: we assume this is done automatically be a interceptor on PP_MRP
				// mrpAllocDAO.createMRPAllocs()
				// .setMRPDemand(mrpDemandRecord)
				// .setMRPSupply(mrpQtyOnHand)
				// .build();
			}
		});

		return mrpQtyOnHandToReturn.getValue();
	}
}

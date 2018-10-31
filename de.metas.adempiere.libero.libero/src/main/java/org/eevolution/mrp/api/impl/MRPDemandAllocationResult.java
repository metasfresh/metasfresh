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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adempiere.util.lang.ObjectUtils;
import org.eevolution.mrp.api.IMRPDemandAllocationResult;
import org.eevolution.mrp.api.IMRPDemandToSupplyAllocation;

import de.metas.util.Check;

/**
 * Immutable MRP Demands allocation result.
 * 
 * @author tsa
 *
 */
public final class MRPDemandAllocationResult implements IMRPDemandAllocationResult
{
	public static final MRPDemandAllocationResult ZERO = new MRPDemandAllocationResult(
			BigDecimal.ZERO, // qtySupplied,
			BigDecimal.ZERO, // qtyScheduledReceipts,
			BigDecimal.ZERO, // qtyOnHandReserved,
			BigDecimal.ZERO, // qtyNetReqRemaining,
			Collections.<IMRPRecordAndQty> emptyList(), // mrpDemandsToAllocate
			Collections.<IMRPDemandToSupplyAllocation> emptyList()
			);

	private final BigDecimal qtyNetReqRemaining;
	private final BigDecimal qtyScheduledReceipts;
	private final BigDecimal qtyOnHandReserved;
	private final BigDecimal qtySupplied;
	private final List<IMRPRecordAndQty> mrpDemandsToAllocate;
	private final List<IMRPDemandToSupplyAllocation> mrpDemand2supplyAllocations;

	public MRPDemandAllocationResult(final BigDecimal qtySupplied,
			final BigDecimal qtyScheduledReceipts,
			final BigDecimal qtyOnHandReserved,
			final BigDecimal qtyNetReqRemaining,
			final List<? extends IMRPRecordAndQty> mrpDemandsToAllocate,
			final List<IMRPDemandToSupplyAllocation> mrpDemand2supplyAllocations)
	{
		super();

		Check.assumeNotNull(qtySupplied, "qtySupplied not null");
		this.qtySupplied = qtySupplied;

		Check.assumeNotNull(qtyScheduledReceipts, "qtyScheduledReceipts not null");
		this.qtyScheduledReceipts = qtyScheduledReceipts;

		Check.assumeNotNull(qtyOnHandReserved, "qtyOnHandReserved not null");
		this.qtyOnHandReserved = qtyOnHandReserved;

		Check.assumeNotNull(qtyNetReqRemaining, "qtyNetReqRemaining not null");
		this.qtyNetReqRemaining = qtyNetReqRemaining;

		Check.assumeNotNull(mrpDemandsToAllocate, "mrpDemandsToAllocate not null");
		this.mrpDemandsToAllocate = Collections.unmodifiableList(new ArrayList<IMRPRecordAndQty>(mrpDemandsToAllocate));
		
		Check.assumeNotNull(mrpDemand2supplyAllocations, "mrpDemand2supplyAllocations not null");
		this.mrpDemand2supplyAllocations = Collections.unmodifiableList(new ArrayList<>(mrpDemand2supplyAllocations));
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public BigDecimal getQtyNetReqRemaining()
	{
		return qtyNetReqRemaining;
	}

	@Override
	public BigDecimal getQtyScheduledReceipts()
	{
		return qtyScheduledReceipts;
	}

	@Override
	public BigDecimal getQtyOnHandReserved()
	{
		return qtyOnHandReserved;
	}

	@Override
	public BigDecimal getQtySupplied()
	{
		return qtySupplied;
	}

	@Override
	public List<IMRPRecordAndQty> getRemainingMRPDemandsToAllocate()
	{
		return mrpDemandsToAllocate;
	}

	@Override
	public List<IMRPDemandToSupplyAllocation> getMRPDemandToSupplyAllocations()
	{
		return mrpDemand2supplyAllocations;
	}
}

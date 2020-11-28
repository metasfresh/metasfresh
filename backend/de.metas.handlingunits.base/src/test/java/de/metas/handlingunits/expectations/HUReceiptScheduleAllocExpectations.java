package de.metas.handlingunits.expectations;

/*
 * #%L
 * de.metas.handlingunits.base
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
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.test.AbstractExpectation;
import org.adempiere.util.test.ErrorMessage;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.model.I_M_ReceiptSchedule_Alloc;
import de.metas.util.Check;
import de.metas.util.Services;

public class HUReceiptScheduleAllocExpectations<ParentExpectationType> extends AbstractExpectation<ParentExpectationType>
{
	private I_M_ReceiptSchedule receiptSchedule;
	private BigDecimal huQtyAllocatedExpected;

	public HUReceiptScheduleAllocExpectations(final ParentExpectationType parentExpectation)
	{
		super(parentExpectation);
	}

	public HUReceiptScheduleAllocExpectations<ParentExpectationType> assertExpectedForTU(final ErrorMessage message, final I_M_HU tuHU)
	{
		ErrorMessage messageToUse = derive(message);

		final List<I_M_ReceiptSchedule_Alloc> allocs = retrieveReceiptScheduleAllocsForTU(tuHU);

		messageToUse = messageToUse
				.addContextInfo("Allocations for TU", allocs)
				.addContextInfo("TU", tuHU)
				.addContextInfo("Receipt Scedule", receiptSchedule);

		BigDecimal huQtyAllocatedActual = BigDecimal.ZERO;
		for (final I_M_ReceiptSchedule_Alloc rsa : allocs)
		{
			final BigDecimal rsa_HUQtyAllocated = rsa.getHU_QtyAllocated();
			huQtyAllocatedActual = huQtyAllocatedActual.add(rsa_HUQtyAllocated);
		}

		assertEquals(messageToUse, huQtyAllocatedExpected, huQtyAllocatedActual);

		return this;
	}

	private final List<I_M_ReceiptSchedule_Alloc> retrieveReceiptScheduleAllocsForTU(final I_M_HU tuHU)
	{
		Check.assumeNotNull(receiptSchedule, "receiptSchedule not null");
		Check.assumeNotNull(tuHU, "tuHU not null");

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ReceiptSchedule_Alloc.class, getContext())
				.addEqualsFilter(de.metas.inoutcandidate.model.I_M_ReceiptSchedule_Alloc.COLUMNNAME_M_ReceiptSchedule_ID, receiptSchedule.getM_ReceiptSchedule_ID())
				.addEqualsFilter(I_M_ReceiptSchedule_Alloc.COLUMNNAME_M_TU_HU_ID, tuHU.getM_HU_ID())
				.addOnlyActiveRecordsFilter()
				.create()
				.list();
	}

	public HUReceiptScheduleAllocExpectations<ParentExpectationType> receiptSchedule(final I_M_ReceiptSchedule receiptSchedule)
	{
		this.receiptSchedule = receiptSchedule;
		return this;
	}

	public HUReceiptScheduleAllocExpectations<ParentExpectationType> huQtyAllocated(final BigDecimal huQtyAllocated)
	{
		this.huQtyAllocatedExpected = huQtyAllocated;
		return this;
	}

	public HUReceiptScheduleAllocExpectations<ParentExpectationType> huQtyAllocated(final String huQtyAllocated)
	{
		return huQtyAllocated(new BigDecimal(huQtyAllocated));
	}

	public HUReceiptScheduleAllocExpectations<ParentExpectationType> huQtyAllocated(final int huQtyAllocated)
	{
		return huQtyAllocated(new BigDecimal(huQtyAllocated));
	}
}

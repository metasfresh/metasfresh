package de.metas.inoutcandidate.expectations;

/*
 * #%L
 * de.metas.swat.base
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

import org.adempiere.util.test.AbstractExpectation;
import org.adempiere.util.test.ErrorMessage;

import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;

public class ReceiptScheduleExpectation<ParentExpectationType> extends AbstractExpectation<ParentExpectationType>
{
	public static ReceiptScheduleExpectation<Object> newExpectation()
	{
		return new ReceiptScheduleExpectation<>();
	}

	private BigDecimal qtyMoved;
	private BigDecimal qtyMovedWithIssues;
	private BigDecimal qualityDiscountPercent;

	public ReceiptScheduleExpectation<ParentExpectationType> assertExpected(final I_M_ReceiptSchedule actual)
	{
		final ErrorMessage messageToUse = newErrorMessage()
				.addContextInfo("Receipt schedule", actual);

		assertNotNull(messageToUse.expect("receipt schedule not null"), actual);

		if (qtyMoved != null)
		{
			assertEquals(messageToUse.expect("qtyMoved"), qtyMoved, actual.getQtyMoved());
		}
		if (qtyMovedWithIssues != null)
		{
			assertEquals(messageToUse.expect("qtyMovedWithIssues"), qtyMovedWithIssues, actual.getQtyMovedWithIssues());
		}
		if (qualityDiscountPercent != null)
		{
			assertEquals(messageToUse.expect("qualityDiscountPercent"), qualityDiscountPercent, actual.getQualityDiscountPercent());
		}

		return this;
	}

	public ReceiptScheduleExpectation<ParentExpectationType> qtyMoved(final BigDecimal qtyMoved)
	{
		this.qtyMoved = qtyMoved;
		return this;
	}

	public ReceiptScheduleExpectation<ParentExpectationType> qtyMoved(final String qtyMoved)
	{
		return qtyMoved(new BigDecimal(qtyMoved));
	}

	public ReceiptScheduleExpectation<ParentExpectationType> qtyMovedWithIssues(final BigDecimal qtyMovedWithIssues)
	{
		this.qtyMovedWithIssues = qtyMovedWithIssues;
		return this;
	}

	public ReceiptScheduleExpectation<ParentExpectationType> qtyMovedWithIssues(final String qtyMovedWithIssues)
	{
		return qtyMovedWithIssues(new BigDecimal(qtyMovedWithIssues));
	}

	public ReceiptScheduleExpectation<ParentExpectationType> qualityDiscountPercent(final BigDecimal qualityDiscountPercent)
	{
		this.qualityDiscountPercent = qualityDiscountPercent;
		return this;
	}

	public ReceiptScheduleExpectation<ParentExpectationType> qualityDiscountPercent(final String qualityDiscountPercent)
	{
		return qualityDiscountPercent(new BigDecimal(qualityDiscountPercent));
	}

}

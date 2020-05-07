package de.metas.inoutcandidate.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.api.IReceiptScheduleQtysBL;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule_Alloc;
import de.metas.inoutcandidate.spi.impl.IQtyAndQuality;
import de.metas.inoutcandidate.spi.impl.MutableQtyAndQuality;
import de.metas.inoutcandidate.spi.impl.QualityNoticesCollection;

/**
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class ReceiptScheduleQtysBL implements IReceiptScheduleQtysBL
{
	@Override
	public BigDecimal getQtyOrdered(final I_M_ReceiptSchedule rs)
	{
		final BigDecimal qtyOrderedOverride = InterfaceWrapperHelper.getValueOrNull(rs, I_M_ReceiptSchedule.COLUMNNAME_QtyToMove_Override);
		if (qtyOrderedOverride != null)
		{
			return qtyOrderedOverride;
		}

		return rs.getQtyOrdered();
	}

	@Override
	public BigDecimal getQtyMoved(final I_M_ReceiptSchedule rs)
	{
		return rs.getQtyMoved();
	}

	@Override
	public BigDecimal getQtyMovedWithIssues(final I_M_ReceiptSchedule rs)
	{
		return rs.getQtyMovedWithIssues();
	}

	@Override
	public BigDecimal getQtyToMove(final I_M_ReceiptSchedule rs)
	{
		return rs.getQtyToMove();
	}

	@Override
	public BigDecimal getQtyOverUnderDelivery(final I_M_ReceiptSchedule rs)
	{
		return rs.getQtyOrderedOverUnder();
	}

	private final MutableQtyAndQuality getQtysIfActive(final I_M_ReceiptSchedule_Alloc rsa)
	{
		final MutableQtyAndQuality qtys = new MutableQtyAndQuality();

		if (rsa.isActive())
		{
			qtys.addQtyAndQtyWithIssues(rsa.getQtyAllocated(), rsa.getQtyWithIssues());

			if (rsa.getM_InOutLine_ID() > 0)
			{
				final I_M_InOutLine receiptLine = InterfaceWrapperHelper.create(rsa.getM_InOutLine(), I_M_InOutLine.class);
				if (receiptLine.isInDispute())
				{
					final String qualityNoticesString = receiptLine.getQualityNote();
					final QualityNoticesCollection qualityNoticesToAdd = QualityNoticesCollection.valueOfQualityNoticesString(qualityNoticesString);
					qtys.addQualityNotices(qualityNoticesToAdd);
				}
			}
		}

		return qtys;
	}

	/**
	 * Gets current receipt schedule quantities.
	 *
	 * NOTE: we are taking the values directly from receipt schedule and not from getters (e.g. {@link #getQtyMoved(I_M_ReceiptSchedule)}) because we are about to update them.
	 *
	 * @param rs
	 * @return receipt schedule quantities
	 */
	private final MutableQtyAndQuality getQtysForUpdate(final I_M_ReceiptSchedule rs)
	{
		final BigDecimal qtyMoved = rs.getQtyMoved();
		final BigDecimal qtyMovedWithIssues = rs.getQtyMovedWithIssues();
		final QualityNoticesCollection qualityNotices = QualityNoticesCollection.valueOfQualityNoticesString(rs.getQualityNote());
		final MutableQtyAndQuality qtys = new MutableQtyAndQuality();
		qtys.addQtyAndQtyWithIssues(qtyMoved, qtyMovedWithIssues);
		qtys.addQualityNotices(qualityNotices);

		return qtys;
	}

	private final void setQtys(final I_M_ReceiptSchedule rs, final IQtyAndQuality qtys)
	{
		rs.setQtyMoved(qtys.getQtyTotal());
		rs.setQtyMovedWithIssues(qtys.getQtyWithIssuesExact());
		rs.setQualityDiscountPercent(qtys.getQualityDiscountPercent());
		rs.setQualityNote(qtys.getQualityNotices().asQualityNoticesString());
	}

	@Override
	public void onReceiptScheduleChanged(final I_M_ReceiptSchedule receiptSchedule)
	{
		final BigDecimal qtyToMove = calculateQtyToMove(receiptSchedule);
		receiptSchedule.setQtyToMove(qtyToMove);

		final BigDecimal qtyOverUnderDelivery = calculateQtyOverUnderDelivery(receiptSchedule);
		receiptSchedule.setQtyOrderedOverUnder(qtyOverUnderDelivery);
	}

	@Override
	public void onReceiptScheduleAdded(final I_M_ReceiptSchedule_Alloc receiptScheduleAlloc)
	{
		final MutableQtyAndQuality qtyDiff = getQtysIfActive(receiptScheduleAlloc);
		updateQtyMoved(receiptScheduleAlloc, qtyDiff);
	}

	@Override
	public void onReceiptScheduleUpdated(final I_M_ReceiptSchedule_Alloc receiptScheduleAlloc)
	{
		final I_M_ReceiptSchedule_Alloc receiptScheduleAllocOld = InterfaceWrapperHelper.createOld(receiptScheduleAlloc, I_M_ReceiptSchedule_Alloc.class);

		final MutableQtyAndQuality qtysNew = getQtysIfActive(receiptScheduleAlloc);
		final MutableQtyAndQuality qtysOld = getQtysIfActive(receiptScheduleAllocOld);

		qtysNew.subtractQtys(qtysOld);
		final MutableQtyAndQuality qtysDiff = qtysNew;

		updateQtyMoved(receiptScheduleAlloc, qtysDiff);
	}

	@Override
	public void onReceiptScheduleDeleted(final I_M_ReceiptSchedule_Alloc receiptScheduleAlloc)
	{
		final MutableQtyAndQuality qtyDiff = getQtysIfActive(receiptScheduleAlloc);
		updateQtyMoved(receiptScheduleAlloc, qtyDiff.negateQtys());
	}

	private final void updateQtyMoved(final I_M_ReceiptSchedule_Alloc receiptScheduleAlloc, final IQtyAndQuality qtysDiff)
	{
		if (qtysDiff.isZero())
		{
			return;
		}

		final I_M_ReceiptSchedule receiptSchedule = receiptScheduleAlloc.getM_ReceiptSchedule();

		// Make sure our receipt schedule is up2date and not some staled cached version
		// because we need QtyMoved(old) to be fresh
		InterfaceWrapperHelper.refresh(receiptSchedule);

		final MutableQtyAndQuality receiptScheduleQtys = getQtysForUpdate(receiptSchedule);
		receiptScheduleQtys.add(qtysDiff);
		setQtys(receiptSchedule, receiptScheduleQtys);

		// QtyToMove shall be updated in "onReceiptScheduleChanged"
		// NOTE: make sure it is called right away (see 6185, G01T010)
		// note: it's called via model interceptor on save.
		// onReceiptScheduleChanged(receiptSchedule);

		InterfaceWrapperHelper.save(receiptSchedule);
	}

	protected final BigDecimal calculateQtyToMove(final I_M_ReceiptSchedule rs)
	{
		final BigDecimal qtyOrdered = getQtyOrdered(rs);
		final BigDecimal qtyMoved = getQtyMoved(rs);
		BigDecimal qtyToMove = qtyOrdered.subtract(qtyMoved);

		// In case there is an over-delivery we don't want our QtyToMove to be negative but ZERO
		if (qtyToMove.signum() <= 0)
		{
			qtyToMove = BigDecimal.ZERO;
		}

		return qtyToMove;
	}

	protected final BigDecimal calculateQtyOverUnderDelivery(final I_M_ReceiptSchedule rs)
	{
		final BigDecimal qtyOrdered = getQtyOrdered(rs);
		final BigDecimal qtyMoved = getQtyMoved(rs);

		final BigDecimal qtyOverUnderDelivery = qtyMoved.subtract(qtyOrdered);

		//
		// Case: receipt schedule was marked as processed.
		// Now we precisely know which is the actual Over/Under Delivery.
		// i.e. if qtyOverUnder < 0 it means it's an under delivery (we delivered less)
		// but we know this for sure ONLY when receipt schedule line is closed.
		// Else we can consider that more receipts will come after.
		if (rs.isProcessed())
		{
			return qtyOverUnderDelivery;
		}

		//
		// Case: we have an Over-Delivery (i.e. we delivered more than it was required)
		if (qtyOverUnderDelivery.signum() > 0)
		{
			return qtyOverUnderDelivery;
		}
		//
		// Case: we delivered exactly what was needed
		else if (qtyOverUnderDelivery.signum() == 0)
		{
			return BigDecimal.ZERO;
		}
		//
		// Case: we have an Under-Delivery (i.e. we delivered less than it was required)
		// Because receipt schedule is not yet processed, we are not sure this is a true under-delivery so we consider it ZERO
		else
		{
			return BigDecimal.ZERO;
		}
	}
}

package de.metas.inoutcandidate.api;

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

import org.adempiere.util.ISingletonService;

import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule_Alloc;

/**
 * Implementations of this handler are responsible for maintaining following fields:
 * <ul>
 * <li> {@link I_M_ReceiptSchedule#COLUMNNAME_QtyMoved}
 * <li> {@link I_M_ReceiptSchedule#COLUMNNAME_QtyToMove}
 * <li> {@link I_M_ReceiptSchedule#COLUMNNAME_QtyOrderedOverUnder}
 * <li> {@link I_M_ReceiptSchedule#COLUMNNAME_QtyMovedWithIssues}
 * <li> {@link I_M_ReceiptSchedule#COLUMNNAME_QualityDiscountPercent}
 * <li> {@link I_M_ReceiptSchedule#COLUMNNAME_QualityNote}
 * </ul>
 *
 * @author tsa
 *
 */
public interface IReceiptScheduleQtysBL extends ISingletonService
{
	/**
	 * Gets Target Qty (i.e. how much shall we receive in the end).
	 *
	 * NOTE: {@link I_M_ReceiptSchedule#COLUMNNAME_QtyToMove_Override} is checked first and if is not set then {@link I_M_ReceiptSchedule#COLUMNNAME_QtyOrdered} is considered.
	 *
	 * @param rs
	 * @return target qty
	 */
	BigDecimal getQtyOrdered(I_M_ReceiptSchedule rs);

	/**
	 * Gets QtyToMove.
	 *
	 * NOTE: QtyToMove_Override is not considered
	 *
	 * @param rs
	 * @return qty to move
	 */
	BigDecimal getQtyToMove(final I_M_ReceiptSchedule rs);

	/**
	 * Gets QtyMoved
	 *
	 * @param rs
	 * @return qty moved
	 */
	BigDecimal getQtyMoved(final I_M_ReceiptSchedule rs);

	BigDecimal getQtyMovedWithIssues(I_M_ReceiptSchedule rs);

	/**
	 * Gets Qty Over/Under Delivery
	 * @param rs
	 * @return Qty Over/Under Delivery
	 */
	BigDecimal getQtyOverUnderDelivery(I_M_ReceiptSchedule rs);

	/**
	 * Called by model interceptor {@link de.metas.inoutcandidate.modelvalidator.M_ReceiptSchedule} when the given <code>receiptSchedule</code> was created/updated and quantities needs to be set.
	 * Since it's called by the MI, there is no need to call it manually throughout the code.
	 * Please keep that MI in sync when you change those methods to use more/less/different properties of the given <code>receiptSchedule</code>.
	 *
	 * NOTE: implementor of this method shall not save the receiptSchedule. API is handling this.
	 *
	 * @param receiptSchedule
	 */
	void onReceiptScheduleChanged(I_M_ReceiptSchedule receiptSchedule);

	/**
	 * Called when an {@link I_M_ReceiptSchedule_Alloc} was created
	 *
	 * @param receiptScheduleAlloc
	 */
	void onReceiptScheduleAdded(final I_M_ReceiptSchedule_Alloc receiptScheduleAlloc);

	/**
	 * Called when an {@link I_M_ReceiptSchedule_Alloc} was changed
	 *
	 * @param receiptScheduleAlloc
	 */
	void onReceiptScheduleUpdated(final I_M_ReceiptSchedule_Alloc receiptScheduleAlloc);

	/**
	 * Called when an {@link I_M_ReceiptSchedule_Alloc} was deleted
	 *
	 * @param receiptScheduleAlloc
	 */
	void onReceiptScheduleDeleted(final I_M_ReceiptSchedule_Alloc receiptScheduleAlloc);
}

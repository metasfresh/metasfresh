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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.adempiere.util.agg.key.IAggregationKeyBuilder;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;

import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule_Alloc;
import de.metas.inoutcandidate.modelvalidator.C_OrderLine_ReceiptSchedule;
import de.metas.inoutcandidate.spi.IReceiptScheduleListener;
import de.metas.interfaces.I_C_BPartner;

public interface IReceiptScheduleBL extends ISingletonService
{
	void addReceiptScheduleListener(IReceiptScheduleListener listener);

	/**
	 * Create {@link IInOutProducer} instance for given initial result
	 *
	 * @param resultInitial
	 * @param complete true if generated documents shall be completed
	 * @return producer
	 */
	IInOutProducer createInOutProducer(InOutGenerateResult resultInitial, boolean complete);

	/**
	 *
	 * NOTE: this method assumes that <code>receiptSchedules</code> are already ordered by aggregation keys.
	 *
	 * @param ctx
	 * @param receiptSchedules
	 * @param result results collector
	 * @param complete if true, complete generated receipts
	 */
	void generateInOuts(Properties ctx, Iterator<I_M_ReceiptSchedule> receiptSchedules, InOutGenerateResult result, boolean complete);

	void generateInOuts(Properties ctx, IInOutProducer producer, Iterator<I_M_ReceiptSchedule> receiptSchedules);

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
	 *
	 * @param rs
	 * @return qty moved
	 */
	BigDecimal getQtyMoved(I_M_ReceiptSchedule rs);

	BigDecimal getQtyMovedWithIssues(I_M_ReceiptSchedule rs);

	/**
	 *
	 * @param rs
	 * @return override-qty or the qty (if no override set) of the given {@code rs}.
	 */
	BigDecimal getQtyToMove(final I_M_ReceiptSchedule rs);

	/**
	 *
	 * @param rs
	 * @return M_Warehouse_Override_ID and falls back to M_Warehouse_ID if no override value is set
	 */
	int getM_Warehouse_Effective_ID(final I_M_ReceiptSchedule rs);

	/**
	 * @param rs
	 * @return M_Warehouse_Override and falls back to M_Warehouse if no override value is set
	 */
	I_M_Warehouse getM_Warehouse_Effective(I_M_ReceiptSchedule rs);

	/**
	 * Gets effective locator to be used when receiving materials with this receipt schedule.
	 *
	 * @param rs
	 * @return default locator for {@link #getM_Warehouse_Effective(I_M_ReceiptSchedule)}.
	 */
	I_M_Locator getM_Locator_Effective(I_M_ReceiptSchedule rs);

	/**
	 *
	 * @param rs
	 * @return C_BP_Location_Override_ID and falls back to C_BPartner_Location_ID if no override value is set
	 */
	int getC_BPartner_Location_Effective_ID(I_M_ReceiptSchedule rs);

	/**
	 * @param rs
	 * @return C_BPartner_Location_Override and falls back to C_BPartner_Location if no override value is set
	 */

	I_C_BPartner_Location getC_BPartner_Location_Effective(I_M_ReceiptSchedule rs);

	/**
	 * @param rs
	 * @return C_BPartner_Override_ID and falls back to C_BPartner_ID if no override value is set
	 */
	int getC_BPartner_Effective_ID(I_M_ReceiptSchedule rs);

	/**
	 * @param rs
	 * @return C_BPartner_Override and falls back to C_BPartner if no override value is set
	 */
	I_C_BPartner getC_BPartner_Effective(I_M_ReceiptSchedule rs);

	/**
	 * @param rs
	 * @return AD_User_Override_ID and falls back to AD_User_ID if no override value is set
	 */
	int getAD_User_Effective_ID(I_M_ReceiptSchedule rs);

	/**
	 * @param rs
	 * @return AD_User_Override and falls back to AD_User if no override value is set
	 */
	I_AD_User getAD_User_Effective(I_M_ReceiptSchedule rs);

	/**
	 *
	 * @param rs
	 * @return M_AttributeSetInstance_ID to use
	 */
	int getM_AttributeSetInstance_Effective_ID(I_M_ReceiptSchedule rs);

	/**
	 *
	 * @param rs
	 * @return {@link I_M_AttributeSetInstance} to use
	 */
	I_M_AttributeSetInstance getM_AttributeSetInstance_Effective(I_M_ReceiptSchedule rs);

	/**
	 * Sets the effective attribute set instance (i.e. M_AttributeSetInstance_Override_ID)
	 *
	 * @param rs
	 * @param asi
	 */
	void setM_AttributeSetInstance_Effective(I_M_ReceiptSchedule rs, I_M_AttributeSetInstance asi);

	IAggregationKeyBuilder<I_M_ReceiptSchedule> getHeaderAggregationKeyBuilder();

	/**
	 * Updates {@link I_M_ReceiptSchedule#COLUMNNAME_BPartnerAddress}
	 *
	 * @param receiptSchedule
	 */
	void updateBPartnerAddress(I_M_ReceiptSchedule receiptSchedule);

	/**
	 * Updates {@link I_M_ReceiptSchedule#COLUMNNAME_BPartnerAddress_Override}
	 *
	 * @param receiptSchedule
	 */
	void updateBPartnerAddressOverride(I_M_ReceiptSchedule receiptSchedule);

	/**
	 * Gets {@link I_M_ReceiptSchedule_Alloc} from {@link I_M_ReceiptSchedule} to {@link I_M_InOutLine}.
	 *
	 * If allocation does not exist, it will be created. If allocation exists, it will be returned untouched.
	 *
	 * @param receiptSchedule
	 * @param receipt
	 * @return
	 */
	I_M_ReceiptSchedule_Alloc createRsaIfNotExists(I_M_ReceiptSchedule receiptSchedule, I_M_InOutLine receipt);

	/**
	 * Allocate given receipt line to the list of of provided receipt schedules.
	 *
	 * NOTE: if all receipt schedules were linked to the same Order Line, that order line will be set to given receipt.
	 *
	 * @param receiptSchedules
	 * @param receiptLine
	 * @return receipt schedule allocations that were created
	 */
	List<I_M_ReceiptSchedule_Alloc> createReceiptScheduleAllocations(List<? extends I_M_ReceiptSchedule> receiptSchedules, I_M_InOutLine receiptLine);

	IReceiptScheduleAllocBuilder createReceiptScheduleAlloc();

	/**
	 * updates preparation time
	 *
	 * @param receiptSchedule
	 */
	void updatePreparationTime(I_M_ReceiptSchedule receiptSchedule);

	/**
	 * Reverse given allocation by creating a new allocation which is canceling the effect.
	 *
	 * NOTE: reversal allocation will not be saved because user may want to change some fields before saving
	 *
	 * @param rsa
	 * @return reversal allocation (not saved)
	 */
	I_M_ReceiptSchedule_Alloc reverseAllocation(I_M_ReceiptSchedule_Alloc rsa);

	/**
	 * Close receipt schedule line and mark it as processed.
	 *
	 * Call the {@link IReceiptScheduleListener}s' beforeClose method, then set the schedule to <code>Processed=Y</code>, then saves the given <code>receiptSchedule</code>, then calls the listeners' afterClose() methods and finally saves the record again.
	 * <p>
	 * The saving prior to <code>afterClose()</code> is done because the listeners might also retrieve the same <code>receiptSchedule</code> data record from the DB and might also change and save it.
	 * See {@link C_OrderLine_ReceiptSchedule} for an example.
	 * <p>
	 * The saving after <code>afterClose()</code> is done to acomodate for listeners that only alter the given <code>receiptSchedule</code> without saving it (which is actually the nice thing to do).
	 *
	 *
	 * @param rs
	 */
	void close(I_M_ReceiptSchedule receiptSchedule);

	/**
	 * Re-open a closed receipt schedule line. Similar to {@link #close(I_M_ReceiptSchedule)}. Also, we save the given <code>receiptSchedule</code> twice for similar reasons.
	 *
	 * @param receiptSchedule
	 */
	void reopen(I_M_ReceiptSchedule receiptSchedule);

	/**
	 * Checks if given receipt schedule is closed (i.e. {@link I_M_ReceiptSchedule#isProcessed()}).
	 *
	 * @param receiptSchedule
	 * @return true if receipt schedule is closed
	 */
	boolean isClosed(I_M_ReceiptSchedule receiptSchedule);
}

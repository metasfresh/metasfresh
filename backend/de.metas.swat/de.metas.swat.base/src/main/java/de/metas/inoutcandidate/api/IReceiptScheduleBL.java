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

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.exportaudit.APIExportStatus;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule_Alloc;
import de.metas.inoutcandidate.modelvalidator.C_OrderLine_ReceiptSchedule;
import de.metas.inoutcandidate.spi.IReceiptScheduleListener;
import de.metas.interfaces.I_C_BPartner;
import de.metas.process.PInstanceId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.util.agg.key.IAggregationKeyBuilder;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Warehouse;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public interface IReceiptScheduleBL extends ISingletonService
{
	void addReceiptScheduleListener(IReceiptScheduleListener listener);

	/**
	 * Create {@link IInOutProducer} instance for given initial result
	 *
	 * @param complete true if generated documents shall be completed
	 * @return producer
	 */
	IInOutProducer createInOutProducer(InOutGenerateResult resultInitial, boolean complete);

	/**
	 * NOTE: this method assumes that <code>receiptSchedules</code> are already ordered by aggregation keys.
	 *
	 * @param result   results collector
	 * @param complete if true, complete generated receipts
	 */
	void generateInOuts(Properties ctx, Iterator<I_M_ReceiptSchedule> receiptSchedules, InOutGenerateResult result, boolean complete);

	void generateInOuts(Properties ctx, IInOutProducer producer, Iterator<I_M_ReceiptSchedule> receiptSchedules);

	/**
	 * Gets Target Qty (i.e. how much shall we receive in the end).
	 * <p>
	 * NOTE: {@link I_M_ReceiptSchedule#COLUMNNAME_QtyToMove_Override} is checked first and if is not set then {@link I_M_ReceiptSchedule#COLUMNNAME_QtyOrdered} is considered.
	 *
	 * @return target qty
	 */
	BigDecimal getQtyOrdered(I_M_ReceiptSchedule rs);

	/**
	 * @return qty moved
	 */
	BigDecimal getQtyMoved(I_M_ReceiptSchedule rs);

	BigDecimal getQtyMovedWithIssues(I_M_ReceiptSchedule rs);

	/**
	 * @return override-qty or the qty (if no override set) of the given {@code rs}.
	 */
	StockQtyAndUOMQty getQtyToMove(I_M_ReceiptSchedule rs);

	/**
	 * @return M_Warehouse_Override_ID and falls back to M_Warehouse_ID if no override value is set
	 */
	WarehouseId getWarehouseEffectiveId(I_M_ReceiptSchedule rs);

	/**
	 * @return M_Warehouse_Override and falls back to M_Warehouse if no override value is set
	 */
	I_M_Warehouse getM_Warehouse_Effective(I_M_ReceiptSchedule rs);

	/**
	 * Gets effective locator to be used when receiving materials with this receipt schedule.
	 *
	 * @return default locator for {@link #getM_Warehouse_Effective(I_M_ReceiptSchedule)}.
	 */
	LocatorId getLocatorEffectiveId(I_M_ReceiptSchedule rs);

	/**
	 * @return C_BP_Location_Override_ID and falls back to C_BPartner_Location_ID if no override value is set
	 */
	int getC_BPartner_Location_Effective_ID(I_M_ReceiptSchedule rs);

	/**
	 * @return C_BPartner_Location_Override and falls back to C_BPartner_Location if no override value is set
	 */

	I_C_BPartner_Location getC_BPartner_Location_Effective(I_M_ReceiptSchedule rs);

	BPartnerId getBPartnerEffectiveId(I_M_ReceiptSchedule rs);

	/**
	 * @return C_BPartner_Override_ID and falls back to C_BPartner_ID if no override value is set
	 * @deprecated Please use {@link #getBPartnerEffectiveId(I_M_ReceiptSchedule)} which returns a {@link BPartnerId}
	 */
	@Deprecated
	int getC_BPartner_Effective_ID(I_M_ReceiptSchedule rs);

	/**
	 * @return C_BPartner_Override and falls back to C_BPartner if no override value is set
	 */
	I_C_BPartner getC_BPartner_Effective(I_M_ReceiptSchedule rs);

	/**
	 * @return AD_User_Override_ID and falls back to AD_User_ID if no override value is set
	 */
	@Nullable
	BPartnerContactId getBPartnerContactID(I_M_ReceiptSchedule rs);

	/**
	 * @return M_AttributeSetInstance_ID to use
	 */
	int getM_AttributeSetInstance_Effective_ID(I_M_ReceiptSchedule rs);

	/**
	 * @return {@link I_M_AttributeSetInstance} to use
	 */
	I_M_AttributeSetInstance getM_AttributeSetInstance_Effective(I_M_ReceiptSchedule rs);

	/**
	 * Sets the effective attribute set instance (i.e. M_AttributeSetInstance_Override_ID)
	 */
	void setM_AttributeSetInstance_Effective(I_M_ReceiptSchedule rs, AttributeSetInstanceId asiId);

	IAggregationKeyBuilder<I_M_ReceiptSchedule> getHeaderAggregationKeyBuilder();

	/**
	 * Updates {@link I_M_ReceiptSchedule#COLUMNNAME_BPartnerAddress}
	 */
	void updateBPartnerAddress(I_M_ReceiptSchedule receiptSchedule);

	/**
	 * Updates {@link I_M_ReceiptSchedule#COLUMNNAME_BPartnerAddress_Override}
	 */
	void updateBPartnerAddressOverride(I_M_ReceiptSchedule receiptSchedule);

	/**
	 * Gets {@link I_M_ReceiptSchedule_Alloc} from {@link I_M_ReceiptSchedule} to {@link I_M_InOutLine}.
	 * <p>
	 * If allocation does not exist, it will be created. If allocation exists, it will be returned untouched.
	 */
	I_M_ReceiptSchedule_Alloc createRsaIfNotExists(I_M_ReceiptSchedule receiptSchedule, I_M_InOutLine receipt);

	/**
	 * Allocate given receipt line to the list of of provided receipt schedules.
	 * <p>
	 * NOTE: if all receipt schedules were linked to the same Order Line, that order line will be set to given receipt.
	 *
	 * @return receipt schedule allocations that were created
	 */
	List<I_M_ReceiptSchedule_Alloc> createReceiptScheduleAllocations(List<? extends I_M_ReceiptSchedule> receiptSchedules, I_M_InOutLine receiptLine);

	IReceiptScheduleAllocBuilder createReceiptScheduleAlloc();

	/**
	 * updates preparation time
	 */
	void updatePreparationTime(I_M_ReceiptSchedule receiptSchedule);

	/**
	 * Reverse given allocation by creating a new allocation which is canceling the effect.
	 * <p>
	 * NOTE: reversal allocation will not be saved because user may want to change some fields before saving
	 *
	 * @return reversal allocation (not saved)
	 */
	I_M_ReceiptSchedule_Alloc reverseAllocation(I_M_ReceiptSchedule_Alloc rsa);

	/**
	 * Close receipt schedule line and mark it as processed.
	 * <p>
	 * Call the {@link IReceiptScheduleListener}s' beforeClose method, then set the schedule to <code>Processed=Y</code>, then save the given <code>receiptSchedule</code>, then call the listeners' afterClose() methods and finally save the record again.
	 * <p>
	 * The saving prior to <code>afterClose()</code> is done because the listeners might also retrieve the same <code>receiptSchedule</code> data record from the DB and might also change and save it.
	 * See {@link C_OrderLine_ReceiptSchedule} for an example.
	 * <p>
	 * The saving after <code>afterClose()</code> is done to accommodate for listeners that only alter the given <code>receiptSchedule</code> without saving it (which is actually the nice thing to do).
	 */
	void close(I_M_ReceiptSchedule receiptSchedule);

	/**
	 * Re-open a closed receipt schedule line. Similar to {@link #close(I_M_ReceiptSchedule)}. Also, we save the given <code>receiptSchedule</code> twice for similar reasons.
	 */
	void reopen(I_M_ReceiptSchedule receiptSchedule);

	/**
	 * Checks if given receipt schedule is closed (i.e. {@link I_M_ReceiptSchedule#isProcessed()}).
	 *
	 * @return true if receipt schedule is closed
	 */
	boolean isClosed(I_M_ReceiptSchedule receiptSchedule);

	void applyReceiptScheduleChanges(ApplyReceiptScheduleChangesRequest applyReceiptScheduleChangesRequest);

	void updateExportStatus(@NonNull APIExportStatus exportStatus, @NonNull PInstanceId pinstanceId);

	void updateCanBeExportedFrom(@NonNull I_M_ReceiptSchedule receiptSchedule);
}

package de.metas.inoutcandidate.api;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.util.ISingletonService;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.MOrderLine;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.storage.IStorageSegment;

/**
 * Implementers give database access to {@link I_M_ShipmentSchedule} instances (DAO).
 *
 * @author ts
 *
 */
public interface IShipmentSchedulePA extends ISingletonService
{
	/**
	 * @return the shipment schedule entry that refers to the given order line or <code>null</code>
	 */
	I_M_ShipmentSchedule retrieveForOrderLine(org.compiere.model.I_C_OrderLine orderLine);

	/**
	 * @return the shipment schedule entry that refers to the given order line or <code>null</code>
	 */
	I_M_ShipmentSchedule retrieveForOrderLine(int orderLineId);

	/**
	 * @param ctx
	 * @param adTableId
	 * @param recordId
	 * @param trxName
	 * @return the shipment schedule entries that refer to record (AD_Table_ID, Record_ID)
	 */
	List<I_M_ShipmentSchedule> retrieveUnprocessedForRecord(Properties ctx, int adTableId, int recordId, String trxName);

	/**
	 *
	 * @param order
	 * @param trxName
	 * @return
	 * @deprecated Use {@link MShipmentSchedule#retrieveForOrder(Properties, int, String)} instead
	 */
	@Deprecated
	Collection<I_M_ShipmentSchedule> retrieveForOrder(I_C_Order order, String trxName);

	Collection<I_M_ShipmentSchedule> retrieveForProduct(int productId, String trxName);

	List<I_M_ShipmentSchedule> retrieveAll(String trxName);

	/**
	 *
	 * @param bPartnerId
	 * @param trxName
	 * @return
	 * @deprecated Please check if you can use {@link #retrieveForBPartner(int, IContextAware)}
	 */
	@Deprecated
	Collection<I_M_ShipmentSchedule> retrieveForBPartner(int bPartnerId, String trxName);

	/**
	 * Returns an iterator with those scheds that reference the given <code>bPartner</code> (via <code>COALESCE(C_BPArtner_Override_ID,C_BPArtner_ID)</code>), are active and are not yet processed.
	 * Note that the partner is also used as context provider (ctx and trxName).
	 *
	 * @param bPartner
	 * @return
	 */
	Iterator<I_M_ShipmentSchedule> retrieveForBPartner(I_C_BPartner bPartner);

	/**
	 * Loads the valid {@link I_M_ShipmentSchedule} and {@link I_C_OrderLine} instances for the given bPartner. They are returned in the right order, such that the available products can be allocated
	 * from the first to the last element. However, this method doesn't have to make sure that all relevant shipment schedules are valid.
	 *
	 * @param bPartnerId the id of the bPartner whose shipment schedule and order lines need to be loaded.
	 *
	 * @param includeUncompleted if <code>true</code>, also those shipment schedules are returned that already have an uncompleted {@link I_M_InOutLine}.
	 * @param trxName
	 * @return
	 */
	List<OlAndSched> retrieveOlAndSchedsForBPartner(
			int bPartnerId, boolean includeUncompleted, String trxName);

	List<OlAndSched> retrieveOlAndSchedsForProduct(
			Properties ctx, int productId, String trxName);

	/**
	 * Retrieves from the DB "invalid" {@link I_M_ShipmentSchedule}s (i.e. those instances that need some updating) together with their {@link I_C_OrderLine}s. The
	 * <code>M_SipmentSchedule_Recompute</code> records that point to those scheds, are marked with the given <code>adPinstanceId</code>.<br>
	 * Task 08727: Note that this "marking/tagging" is done
	 * out-of-trx so that the info is directly available.
	 * <p>
	 * <b>IMPORTANT:</b> even if a shipment schedule is locked (by a <code>T_Lock</code>) record, then that schedule is still retrieved and its <code>M_SipmentSchedule_Recompute</code> record is
	 * marked with the given <code>adPinstanceId</code>.
	 *
	 * @return the {@link I_C_OrderLine}s contained in the {@link OlAndSched} instances are {@link MOrderLine}s.
	 */
	List<OlAndSched> retrieveInvalid(int adPinstanceId, String trxName);

	/**
	 * Returns <code>true</code> if there is a <code>M_ShipmentSchedule_Recompute</code> record pointing at the given <code>sched</code>.
	 *
	 * @param sched
	 * @return
	 */
	boolean isInvalid(I_M_ShipmentSchedule sched);

	/**
	 * Loads the shipment schedules that are valid. They are returned in the right order, such that the available products can be allocated from the first to the last element. However, this method
	 * doesn't have to make sure that all relevant shipment schedules are valid.
	 *
	 * @param includeUncompleted if <code>true</code>, also those shipment schedules are returned that already have an uncompleted {@link I_M_InOutLine}.
	 * @param trxName
	 * @return
	 */
	List<OlAndSched> retrieveForShipmentRun(boolean includeUncompleted, String trxName);

	/**
	 * Sets the {@link I_M_ShipmentSchedule#COLUMNNAME_IsValid} column to <code>'N'</code> for all shipment schedule entries whose order line has the given product id.
	 *
	 * @param productId
	 * @param trxName
	 * @deprecated please be more selective with the invalidation, using storage segments
	 */
	@Deprecated
	void invalidateForProduct(int productId, String trxName);

	/**
	 * @param productIds
	 * @param trxName
	 * @deprecated please be more selective with the invalidation, using storage segments
	 */
	@Deprecated
	void invalidateForProducts(Collection<Integer> productIds, String trxName);

	/**
	 * Invalidates all shipment schedules which have one of the given <code>headerAggregationKeys</code>.
	 *
	 * @param headerAggregationKeys
	 * @param trxName
	 */
	void invalidateForHeaderAggregationKeys(Collection<String> headerAggregationKeys, String trxName);

	/**
	 * Invalidate given shipment schedules, even if they are already Processed.
	 *
	 * @param shipmentSchedules
	 * @param trxName
	 */
	void invalidate(Collection<I_M_ShipmentSchedule> shipmentSchedules, String trxName);

	/**
	 * Invalidates shipment schedules for the given storage segments.
	 * <p>
	 * <b>IMPORTANT:</b> won't invalidate any processed schedules.
	 *
	 * @param storageSegments
	 */
	void invalidate(Collection<IStorageSegment> storageSegments);

	/**
	 * Invalidate (i.e. schedule for recompute) all records from current tenant
	 *
	 * @param ctx
	 * @see #invalidateForProduct(int, String)
	 */
	void invalidateAll(Properties ctx);

	/** Delete M_ShipmentSchedule_Recompute records for given tag */
	void deleteRecomputeMarkers(int adPInstanceId, String trxName);

	/** Untag M_ShipmentSchedule_Recompute records which were tagged with given tag */
	void releaseRecomputeMarker(int adPInstanceId, String trxName);

	void setIsDiplayedForProduct(int productId, boolean displayed, String trxName);

	/**
	 * Deletes all {@link I_M_ShipmentSchedule} records whose {@link I_C_OrderLine} is not there anymore.
	 *
	 * It can occur that an order line for a given shipment schedule record is gone.
	 *
	 * @param trxName
	 */
	void deleteSchedulesWithOutOl(String trxName);

	/**
	 * Mass update DeliveryDate_Override
	 * No invalidation.
	 *
	 * @param deliveryDate
	 * @param ADPinstance_ID
	 * @param trxName
	 */
	void updateDeliveryDate_Override(Timestamp deliveryDate, int ADPinstance_ID, String trxName);

	/**
	 * Mass update PreparationDate_Override
	 * Invalidation in case preparationDate is null
	 *
	 * @param preparationDate
	 * @param ADPinstance_ID
	 * @param trxName
	 */
	void updatePreparationDate_Override(Timestamp preparationDate, int ADPinstance_ID, String trxName);

	/**
	 * Create selection based on the userSelectionFilter and ad_Pinstance_ID
	 * <ul>
	 * <li>Method used for processes that are based on user selection
	 * <li>The selection will be created out of transaction
	 * </ul>
	 *
	 * @param ctx
	 * @param userSelectionFilter
	 * @return the created queryBuilder
	 */
	IQueryBuilder<I_M_ShipmentSchedule> createQueryForShipmentScheduleSelection(Properties ctx, IQueryFilter<I_M_ShipmentSchedule> userSelectionFilter);

	/**
	 * Retrieve all the Shipment Schedules that the given invoice candidate is based on.
	 *
	 * @param candidate
	 * @return
	 */
	Set<I_M_ShipmentSchedule> retrieveForInvoiceCandidate(I_C_Invoice_Candidate candidate);

	/**
	 * Retrieve all the SHipment Schedules that the given inout line is based on
	 *
	 * @param inoutLine
	 * @return
	 */
	Set<I_M_ShipmentSchedule> retrieveForInOutLine(de.metas.inout.model.I_M_InOutLine inoutLine);

	void deleteAllForReference(TableRecordReference referencedRecord);
}

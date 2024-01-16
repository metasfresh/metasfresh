package de.metas.inoutcandidate.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.process.PInstanceId;
import de.metas.product.ProductId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.MOrderLine;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Implementers give database access to {@link I_M_ShipmentSchedule} instances (DAO).
 *
 * @author ts
 */
public interface IShipmentSchedulePA extends ISingletonService
{
	I_M_ShipmentSchedule getById(ShipmentScheduleId id);

	<T extends I_M_ShipmentSchedule> T getById(ShipmentScheduleId id, Class<T> modelClass);

	Map<ShipmentScheduleId, I_M_ShipmentSchedule> getByIdsOutOfTrx(Set<ShipmentScheduleId> ids);

	<T extends I_M_ShipmentSchedule> Map<ShipmentScheduleId, T> getByIdsOutOfTrx(Set<ShipmentScheduleId> ids, Class<T> modelClass);

	Map<ShipmentScheduleId, I_M_ShipmentSchedule> getByIds(Set<ShipmentScheduleId> ids);

	boolean anyMatchByOrderId(@NonNull OrderId orderId);

	boolean anyMatchByOrderIds(@NonNull Collection<OrderId> orderIds);

	<T extends I_M_ShipmentSchedule> Map<ShipmentScheduleId, T> getByIds(Set<ShipmentScheduleId> ids, Class<T> clazz);

	@Nullable
	I_M_ShipmentSchedule getByOrderLineId(OrderLineId orderLineId);

	@Nullable
	ShipmentScheduleId getShipmentScheduleIdByOrderLineId(OrderLineId orderLineId);

	boolean existsExportedShipmentScheduleForOrder(@NonNull final OrderId orderId);

	Set<ShipmentScheduleId> retrieveUnprocessedIdsByOrderId(OrderId orderId);

	/**
	 * @return the shipment schedule entries that refer to given record
	 */
	List<I_M_ShipmentSchedule> retrieveUnprocessedForRecord(TableRecordReference recordRef);

	Stream<I_M_ShipmentSchedule> streamUnprocessedByPartnerIdAndAllowConsolidateInOut(BPartnerId bpartnerId, boolean allowConsolidateInOut);

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
	List<OlAndSched> retrieveInvalid(PInstanceId pinstanceId);

	void setIsDiplayedForProduct(ProductId productId, boolean displayed);

	/**
	 * Mass update DeliveryDate_Override
	 * No invalidation.
	 */
	void updateDeliveryDate_Override(Timestamp deliveryDate, PInstanceId pinstanceId);

	/**
	 * Mass update PreparationDate_Override
	 * Invalidation in case preparationDate is null
	 */
	void updatePreparationDate_Override(Timestamp preparationDate, PInstanceId pinstanceId);

	/**
	 * Create selection based on the userSelectionFilter and ad_Pinstance_ID
	 * <ul>
	 * <li>Method used for processes that are based on user selection
	 * <li>The selection will be created out of transaction
	 * </ul>
	 */
	IQueryBuilder<I_M_ShipmentSchedule> createQueryForShipmentScheduleSelection(Properties ctx, IQueryFilter<I_M_ShipmentSchedule> userSelectionFilter);

	/**
	 * Retrieve all the Shipment Schedules that the given invoice candidate is based on.
	 */
	Set<I_M_ShipmentSchedule> retrieveForInvoiceCandidate(I_C_Invoice_Candidate candidate);

	/**
	 * Retrieve all the SHipment Schedules that the given inout line is based on
	 */
	Set<I_M_ShipmentSchedule> retrieveForInOutLine(I_M_InOutLine inoutLine);

	void deleteAllForReference(TableRecordReference referencedRecord);

	Set<ProductId> getProductIdsByShipmentScheduleIds(Collection<ShipmentScheduleId> shipmentScheduleIds);

	void save(I_M_ShipmentSchedule record);

	ImmutableList<I_M_ShipmentSchedule> getByReferences(ImmutableList<TableRecordReference> recordRefs);

	Collection<I_M_ShipmentSchedule> getByOrderId(@NonNull OrderId orderId);

	Collection<I_M_ShipmentSchedule> getByOrderIds(@NonNull Collection<OrderId> orderIds);

	ImmutableSet<ShipmentScheduleId> retrieveScheduleIdsByOrderId(OrderId orderId);

	ImmutableSet<OrderId> getOrderIds(IQueryFilter<? extends I_M_ShipmentSchedule> filter);
}

package de.metas.shippingnotification;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.calendar.standard.CalendarId;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.calendar.standard.YearId;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.DocTypeId;
import de.metas.document.engine.DocStatus;
import de.metas.inout.ShipmentScheduleId;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import de.metas.shippingnotification.model.I_M_Shipping_NotificationLine;
import de.metas.uom.UomId;
import de.metas.util.GuavaCollectors;
import de.metas.util.StringUtils;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
class ShippingNotificationLoaderAndSaver
{
	@NonNull private final IQueryBL queryBL;

	private final HashMap<ShippingNotificationId, I_M_Shipping_Notification> headersById = new HashMap<>();
	private final HashSet<ShippingNotificationId> headerIdsToAvoidSaving = new HashSet<>();
	private final HashMap<ShippingNotificationId, ArrayList<I_M_Shipping_NotificationLine>> linesByHeaderId = new HashMap<>();
	private final HashMap<ShippingNotificationLineId, I_M_Shipping_NotificationLine> linesByLineId = new HashMap<>();

	public void addToCacheAndAvoidSaving(@NonNull final I_M_Shipping_Notification record)
	{
		@NonNull final ShippingNotificationId glJournalId = extractId(record);
		headersById.put(glJournalId, record);
		headerIdsToAvoidSaving.add(glJournalId);
	}

	public static ShippingNotificationId extractId(final I_M_Shipping_Notification record)
	{
		return ShippingNotificationId.ofRepoId(record.getM_Shipping_Notification_ID());
	}

	@Nullable
	private ShippingNotificationId extractIdOrNull(final I_M_Shipping_Notification record)
	{
		return ShippingNotificationId.ofRepoIdOrNull(record.getM_Shipping_Notification_ID());
	}

	@NonNull
	public ShippingNotification getById(@NonNull final ShippingNotificationId id)
	{
		final I_M_Shipping_Notification headerRecord = getHeaderRecordById(id);
		final List<I_M_Shipping_NotificationLine> lineRecords = getLineRecords(id);
		return fromRecord(headerRecord, lineRecords);
	}

	@NonNull
	public I_M_Shipping_NotificationLine getLineRecordByLineId(@NonNull final ShippingNotificationLineId id)
	{
		return linesByLineId.computeIfAbsent(id, this::retrieveLineRecordByLineId);
	}

	@NonNull
	public I_M_Shipping_NotificationLine retrieveLineRecordByLineId(@NonNull final ShippingNotificationLineId id)
	{
		return InterfaceWrapperHelper.load(id, I_M_Shipping_NotificationLine.class);
	}

	@NonNull
	public ShippingNotificationCollection getByIds(@NonNull final Set<ShippingNotificationId> ids)
	{
		if (ids.isEmpty())
		{
			return ShippingNotificationCollection.EMPTY;
		}

		final Map<ShippingNotificationId, I_M_Shipping_Notification> headerRecordsById = getHeaderRecordsByIds(ids);
		final Map<ShippingNotificationId, ArrayList<I_M_Shipping_NotificationLine>> lineRecordsById = getLineRecords(ids);

		return ids.stream()
				.map(id -> {
					final I_M_Shipping_Notification headerRecord = headerRecordsById.get(id);
					if (headerRecord == null)
					{
						throw new AdempiereException("No shipping notificatin found for " + id);
					}

					final List<I_M_Shipping_NotificationLine> lineRecords = CoalesceUtil.coalesceNotNull(lineRecordsById.get(id), ImmutableList.of());
					return fromRecord(headerRecord, lineRecords);
				})
				.collect(ShippingNotificationCollection.collect());
	}

	public ShippingNotificationCollection getByQuery(@NonNull final ShippingNotificationQuery query)
	{
		final ImmutableSet<ShippingNotificationId> shippingNotificationIds = toSqlQuery(query).create().listIds(ShippingNotificationId::ofRepoId);
		return getByIds(shippingNotificationIds);
	}

	@NonNull
	public I_M_Shipping_Notification getHeaderRecordById(@NonNull final ShippingNotificationId id)
	{
		return getHeaderRecordByIdIfExists(id).orElseThrow(() -> new AdempiereException("No Shipping Notification found for " + id));
	}

	private Optional<I_M_Shipping_Notification> getHeaderRecordByIdIfExists(@NonNull final ShippingNotificationId id)
	{
		return Optional.ofNullable(headersById.computeIfAbsent(id, this::retrieveHeaderRecordById));
	}

	@Nullable
	private I_M_Shipping_Notification retrieveHeaderRecordById(@NonNull final ShippingNotificationId id)
	{
		return queryBL.createQueryBuilder(I_M_Shipping_Notification.class)
				.addEqualsFilter(I_M_Shipping_Notification.COLUMNNAME_M_Shipping_Notification_ID, id)
				.create()
				.firstOnly(I_M_Shipping_Notification.class);
	}

	public Map<ShippingNotificationId, I_M_Shipping_Notification> getHeaderRecordsByIds(@NonNull final Set<ShippingNotificationId> ids)
	{
		return CollectionUtils.getAllOrLoadReturningMap(this.headersById, ids, this::retrieveHeaderRecordsByIds);
	}

	private Map<ShippingNotificationId, I_M_Shipping_Notification> retrieveHeaderRecordsByIds(@NonNull final Set<ShippingNotificationId> ids)
	{
		return queryBL.createQueryBuilder(I_M_Shipping_Notification.class)
				.addInArrayFilter(I_M_Shipping_Notification.COLUMNNAME_M_Shipping_Notification_ID, ids)
				.create()
				.map(ShippingNotificationLoaderAndSaver::extractId);
	}

	public ArrayList<I_M_Shipping_NotificationLine> getLineRecords(@NonNull final ShippingNotificationId id)
	{
		return linesByHeaderId.computeIfAbsent(id, this::retrieveLineRecords);
	}

	@NonNull
	public Stream<ShippingNotificationId> streamIds(@NonNull final IQueryFilter<I_M_Shipping_Notification> shippingNotificationFilter)
	{
		return queryBL.createQueryBuilder(I_M_Shipping_Notification.class)
				.filter(shippingNotificationFilter)
				.create()
				.iterateAndStreamIds(ShippingNotificationId::ofRepoId);
	}

	private ArrayList<I_M_Shipping_NotificationLine> retrieveLineRecords(final @NonNull ShippingNotificationId id)
	{
		return queryLinesByHeaderIds(ImmutableSet.of(id))
				.orderBy(I_M_Shipping_NotificationLine.COLUMNNAME_M_Shipping_Notification_ID)
				.orderBy(I_M_Shipping_NotificationLine.COLUMNNAME_Line)
				.orderBy(I_M_Shipping_NotificationLine.COLUMNNAME_M_Shipping_NotificationLine_ID)
				.create()
				.stream()
				.collect(Collectors.toCollection(ArrayList::new));
	}

	public Map<ShippingNotificationId, ArrayList<I_M_Shipping_NotificationLine>> getLineRecords(@NonNull final Collection<ShippingNotificationId> ids)
	{
		return CollectionUtils.getAllOrLoadReturningMap(linesByHeaderId, ids, this::retrieveLineRecords);
	}

	private Map<ShippingNotificationId, ArrayList<I_M_Shipping_NotificationLine>> retrieveLineRecords(final @NonNull Set<ShippingNotificationId> ids)
	{
		final HashMap<ShippingNotificationId, ArrayList<I_M_Shipping_NotificationLine>> result = new HashMap<>();
		queryLinesByHeaderIds(ids)
				.create()
				.forEach(lineRecord -> {
					final ShippingNotificationId shippingNotificationId = ShippingNotificationId.ofRepoId(lineRecord.getM_Shipping_Notification_ID());
					result.computeIfAbsent(shippingNotificationId, id -> new ArrayList<>()).add(lineRecord);
				});
		return result;
	}

	private IQueryBuilder<I_M_Shipping_NotificationLine> queryLinesByHeaderIds(final @NonNull Set<ShippingNotificationId> ids)
	{
		return queryBL.createQueryBuilder(I_M_Shipping_NotificationLine.class)
				.addInArrayFilter(I_M_Shipping_NotificationLine.COLUMNNAME_M_Shipping_Notification_ID, ids);
	}

	private static ShippingNotification fromRecord(
			@NonNull final I_M_Shipping_Notification record,
			@NonNull final List<I_M_Shipping_NotificationLine> lineRecords)
	{
		return ShippingNotification.builder()
				.id(ShippingNotificationId.ofRepoIdOrNull(record.getM_Shipping_Notification_ID()))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(record.getAD_Client_ID(), record.getAD_Org_ID()))
				.docTypeId(DocTypeId.ofRepoId(record.getC_DocType_ID()))
				.documentNo(record.getDocumentNo())
				.bpartnerAndLocationId(BPartnerLocationId.ofRepoId(record.getC_BPartner_ID(), record.getC_BPartner_Location_ID()))
				.contactId(BPartnerContactId.ofRepoIdOrNull(record.getC_BPartner_ID(), record.getAD_User_ID()))
				.auctionId(record.getC_Auction_ID())
				.locatorId(LocatorId.ofRepoId(record.getM_Warehouse_ID(), record.getM_Locator_ID()))
				.salesOrderId(OrderId.ofRepoId(record.getC_Order_ID()))
				.dateAcct(record.getDateAcct().toInstant())
				.physicalClearanceDate(record.getPhysicalClearanceDate().toInstant())
				.harvestingYearId(YearAndCalendarId.ofRepoIdOrNull(record.getHarvesting_Year_ID(), record.getC_Harvesting_Calendar_ID()))
				.poReference(StringUtils.trimBlankToNull(record.getPOReference()))
				.description(StringUtils.trimBlankToNull(record.getDescription()))
				.docStatus(DocStatus.ofCode(record.getDocStatus()))
				.docAction(record.getDocAction())
				.processed(record.isProcessed())
				.reversalId(ShippingNotificationId.ofRepoIdOrNull(record.getReversal_ID()))
				.lines(lineRecords.stream()
						.map(ShippingNotificationLoaderAndSaver::fromRecord)
						.sorted(Comparator.comparing(ShippingNotificationLine::getLine))
						.collect(Collectors.toList()))
				.build();
	}

	private static ShippingNotificationLine fromRecord(@NonNull final I_M_Shipping_NotificationLine record)
	{
		return ShippingNotificationLine.builder()
				.id(ShippingNotificationLineId.ofRepoId(record.getM_Shipping_NotificationLine_ID()))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.asiId(AttributeSetInstanceId.ofRepoIdOrNone(record.getM_AttributeSetInstance_ID()))
				.qty(Quantitys.of(record.getMovementQty(), UomId.ofRepoId(record.getC_UOM_ID())))
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(record.getM_ShipmentSchedule_ID()))
				.salesOrderAndLineId(OrderAndLineId.ofRepoIds(record.getC_Order_ID(), record.getC_OrderLine_ID()))
				.line(SeqNo.ofInt(record.getLine()))
				.reversalLineId(ShippingNotificationLineId.ofRepoIdOrNull(record.getReversal_ID()))
				.build();
	}

	public I_M_Shipping_Notification save(final ShippingNotification shippingNotification)
	{
		final I_M_Shipping_Notification shippingNotificationRecord = shippingNotification.getId() != null
				? getHeaderRecordById(shippingNotification.getId())
				: InterfaceWrapperHelper.newInstance(I_M_Shipping_Notification.class);

		updateRecord(shippingNotificationRecord, shippingNotification);
		saveRecordIfAllowed(shippingNotificationRecord);
		shippingNotification.markAsSaved(ShippingNotificationId.ofRepoId(shippingNotificationRecord.getM_Shipping_Notification_ID()));
		shippingNotification.setDocumentNo(shippingNotificationRecord.getDocumentNo());

		final HashMap<ShippingNotificationLineId, I_M_Shipping_NotificationLine> existingLineRecordsById = getLineRecords(shippingNotification.getId())
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(lineRecord -> ShippingNotificationLineId.ofRepoId(lineRecord.getM_Shipping_NotificationLine_ID())));

		for (final ShippingNotificationLine line : shippingNotification.getLines())
		{
			I_M_Shipping_NotificationLine lineRecord = line.getId() != null
					? existingLineRecordsById.remove(line.getId())
					: null;

			if (lineRecord == null)
			{
				lineRecord = InterfaceWrapperHelper.newInstance(I_M_Shipping_NotificationLine.class);
			}

			updateRecord(lineRecord, line, shippingNotification);
			saveLine(lineRecord);
			line.markAsSaved(ShippingNotificationLineId.ofRepoId(lineRecord.getM_Shipping_NotificationLine_ID()));
		}

		InterfaceWrapperHelper.deleteAll(existingLineRecordsById.values());

		return shippingNotificationRecord;
	}

	private static void updateRecord(@NonNull final I_M_Shipping_Notification record, @NonNull final ShippingNotification from)
	{
		final YearAndCalendarId harvestingYearId = from.getHarvestingYearId();
		final YearId yearId = harvestingYearId == null ? null : harvestingYearId.yearId();
		final CalendarId calendarId = harvestingYearId == null ? null : harvestingYearId.calendarId();

		record.setAD_Org_ID(from.getClientAndOrgId().getOrgId().getRepoId());
		record.setC_DocType_ID(from.getDocTypeId().getRepoId());
		record.setDocumentNo(from.getDocumentNo());
		record.setC_BPartner_ID(from.getBpartnerAndLocationId().getBpartnerId().getRepoId());
		record.setC_BPartner_Location_ID(from.getBpartnerAndLocationId().getRepoId());
		record.setAD_User_ID(from.getContactId() != null ? from.getContactId().getRepoId() : -1);
		record.setC_Auction_ID(from.getAuctionId());
		record.setM_Warehouse_ID(from.getLocatorId().getWarehouseId().getRepoId());
		record.setM_Locator_ID(from.getLocatorId().getRepoId());
		record.setC_Order_ID(from.getSalesOrderId().getRepoId());
		record.setDateAcct(Timestamp.from(from.getDateAcct()));
		record.setPhysicalClearanceDate(Timestamp.from(from.getPhysicalClearanceDate()));
		record.setDateAcct(Timestamp.from(from.getPhysicalClearanceDate()));
		record.setHarvesting_Year_ID(YearId.toRepoId(yearId));
		record.setC_Harvesting_Calendar_ID(CalendarId.toRepoId(calendarId));
		record.setPOReference(from.getPoReference());
		record.setDescription(from.getDescription());
		record.setDocStatus(from.getDocStatus().getCode());
		record.setDocAction(from.getDocAction());
		record.setProcessed(from.isProcessed());
		record.setReversal_ID(ShippingNotificationId.toRepoId(from.getReversalId()));
		record.setBPartnerAddress(from.getBpaddress());
	}

	private static void updateRecord(
			@NonNull final I_M_Shipping_NotificationLine record,
			@NonNull final ShippingNotificationLine fromLine,
			@NonNull final ShippingNotification fromHeader)
	{
		record.setM_Shipping_Notification_ID(fromHeader.getIdNotNull().getRepoId());
		record.setProcessed(fromHeader.isProcessed());

		record.setReversal_ID(ShippingNotificationLineId.toRepoId(fromLine.getReversalLineId()));

		record.setLine(fromLine.getLine().toInt());
		record.setM_Product_ID(fromLine.getProductId().getRepoId());
		record.setM_AttributeSetInstance_ID(fromLine.getAsiId().getRepoId());
		record.setMovementQty(fromLine.getQty().toBigDecimal());
		record.setC_UOM_ID(fromLine.getQty().getUomId().getRepoId());
		record.setM_ShipmentSchedule_ID(fromLine.getShipmentScheduleId().getRepoId());
		record.setC_Order_ID(fromLine.getSalesOrderAndLineId().getOrderRepoId());
		record.setC_OrderLine_ID(fromLine.getSalesOrderAndLineId().getOrderLineRepoId());
	}

	private void saveRecordIfAllowed(@NonNull final I_M_Shipping_Notification shippingNotificationRecord)
	{
		final ShippingNotificationId id = extractIdOrNull(shippingNotificationRecord);
		if (id != null && headerIdsToAvoidSaving.contains(id))
		{
			return;
		}
		InterfaceWrapperHelper.saveRecord(shippingNotificationRecord);
	}

	private void saveLine(@NonNull final I_M_Shipping_NotificationLine shippingNotificationLineRecord)
	{
		InterfaceWrapperHelper.save(shippingNotificationLineRecord);
	}

	public <R> R updateWhileSaving(
			@NonNull final I_M_Shipping_Notification record,
			@NonNull final Function<ShippingNotification, R> consumer)
	{
		final R retValue;
		final ShippingNotificationId id = extractIdOrNull(record);
		if (id == null)
		{
			final ShippingNotification shippingNotification = fromRecord(record, ImmutableList.of());
			retValue = consumer.apply(shippingNotification);
			if (!shippingNotification.getLines().isEmpty())
			{
				throw new AdempiereException("Adding lines to a new shipper notification which is not allowed to be saved is allowed");
			}
			saveRecordIfAllowed(record);
		}
		else
		{
			addToCacheAndAvoidSaving(record);
			final ShippingNotification shippingNotification = getById(id);
			retValue = consumer.apply(shippingNotification);
			save(shippingNotification);
		}

		return retValue;
	}

	public IQueryBuilder<I_M_Shipping_Notification> toSqlQuery(final ShippingNotificationQuery query)
	{
		final IQueryBuilder<I_M_Shipping_Notification> sqlQueryBuilder = queryBL.createQueryBuilder(I_M_Shipping_Notification.class);

		if (query.getOnlyDocStatuses() != null && !query.getOnlyDocStatuses().isEmpty())
		{
			sqlQueryBuilder.addInArrayFilter(I_M_Shipping_Notification.COLUMNNAME_DocStatus, query.getOnlyDocStatuses());
		}

		if (query.getOrderIds() != null && !query.getOrderIds().isEmpty())
		{
			sqlQueryBuilder.addInArrayFilter(I_M_Shipping_Notification.COLUMNNAME_C_Order_ID, query.getOrderIds());
		}

		return sqlQueryBuilder;
	}

	public boolean anyMatch(@NonNull final ShippingNotificationQuery query)
	{
		return toSqlQuery(query).anyMatch();
	}

	public ImmutableSet<ShippingNotificationId> listIds(@NonNull final ShippingNotificationQuery query) {return toSqlQuery(query).create().listIds(ShippingNotificationId::ofRepoId);}
}

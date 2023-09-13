package de.metas.shippingnotification;

import com.google.common.collect.ImmutableList;
import de.metas.acct.gljournal_sap.SAPGLJournalLine;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.document.DocTypeId;
import de.metas.document.engine.DocStatus;
import de.metas.inout.ShipmentScheduleId;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import de.metas.shippingnotification.model.I_M_Shipping_NotificationLine;
import de.metas.uom.UomId;
import de.metas.util.GuavaCollectors;
import de.metas.util.StringUtils;
import de.metas.util.lang.SeqNo;
import de.metas.util.lang.SeqNoProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class ShippingNotificationLoaderAndSaver
{
	@NonNull private final IQueryBL queryBL;

	private final HashMap<ShippingNotificationId, I_M_Shipping_Notification> headersById = new HashMap<>();
	private final HashSet<ShippingNotificationId> headerIdsToAvoidSaving = new HashSet<>();
	private final HashMap<ShippingNotificationId, ArrayList<I_M_Shipping_NotificationLine>> linesByHeaderId = new HashMap<>();

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
	private I_M_Shipping_Notification getHeaderRecordById(@NonNull final ShippingNotificationId id)
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

	public ArrayList<I_M_Shipping_NotificationLine> getLineRecords(@NonNull final ShippingNotificationId id)
	{
		return linesByHeaderId.computeIfAbsent(id, this::retrieveLineRecords);
	}

	private ArrayList<I_M_Shipping_NotificationLine> retrieveLineRecords(final @NonNull ShippingNotificationId id)
	{
		return queryLinesByHeaderId(id)
				.create()
				.stream()
				.collect(Collectors.toCollection(ArrayList::new));

	}

	private IQueryBuilder<I_M_Shipping_NotificationLine> queryLinesByHeaderId(final @NonNull ShippingNotificationId id)
	{
		return queryBL.createQueryBuilder(I_M_Shipping_NotificationLine.class)
				.addInArrayFilter(I_M_Shipping_NotificationLine.COLUMNNAME_M_Shipping_Notification_ID, id);
	}

	private static ShippingNotification fromRecord(
			@NonNull final I_M_Shipping_Notification record,
			@NonNull final List<I_M_Shipping_NotificationLine> lineRecords)
	{
		return ShippingNotification.builder()
				.id(ShippingNotificationId.ofRepoIdOrNull(record.getM_Shipping_Notification_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.docTypeId(DocTypeId.ofRepoId(record.getC_DocType_ID()))
				.bpartnerAndLocationId(BPartnerLocationId.ofRepoId(record.getC_BPartner_ID(), record.getC_BPartner_Location_ID()))
				.contactId(BPartnerContactId.ofRepoIdOrNull(record.getC_BPartner_ID(), record.getAD_User_ID()))
				.auctionId(record.getC_Auction_ID())
				.locatorId(LocatorId.ofRepoId(record.getM_Warehouse_ID(), record.getM_Locator_ID()))
				.orderId(OrderId.ofRepoId(record.getC_Order_ID()))
				.physicalClearanceDate(record.getPhysicalClearanceDate().toInstant())
				.harvestringYearId(YearAndCalendarId.ofRepoId(record.getHarvesting_Year_ID(), record.getC_Harvesting_Calendar_ID()))
				.poReference(StringUtils.trimBlankToNull(record.getPOReference()))
				.description(StringUtils.trimBlankToNull(record.getDescription()))
				.docStatus(DocStatus.ofCode(record.getDocStatus()))
				.processed(record.isProcessed())
				.lines(lineRecords.stream()
						.map(ShippingNotificationLoaderAndSaver::fromRecord)
						.collect(Collectors.toList()))
				.build();
	}

	private static ShippingNotificationLine fromRecord(@NonNull final I_M_Shipping_NotificationLine record)
	{
		return ShippingNotificationLine.builder()
				.id(ShippingNotificationLineId.ofRepoId(record.getM_Shipping_NotificationLine_ID()))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.asiId(AttributeSetInstanceId.ofRepoIdOrNone(record.getM_AttributeSetInstance_ID()))
				.qty(Quantitys.create(record.getMovementQty(), UomId.ofRepoId(record.getC_UOM_ID())))
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(record.getM_ShipmentSchedule_ID()))
				.orderAndLineId(OrderAndLineId.ofRepoIds(record.getC_Order_ID(), record.getC_OrderLine_ID()))
				.line(SeqNo.ofInt(record.getLine()))
				.build();
	}

	public I_M_Shipping_Notification save(final ShippingNotification shippingNotification)
	{
		final I_M_Shipping_Notification shippingNotificationRecord = shippingNotification.getId() != null
				? InterfaceWrapperHelper.load(shippingNotification.getId(), I_M_Shipping_Notification.class)
				: InterfaceWrapperHelper.newInstance(I_M_Shipping_Notification.class);

		updateRecord(shippingNotificationRecord, shippingNotification);
		saveRecordIfAllowed(shippingNotificationRecord);
		shippingNotification.markAsSaved(ShippingNotificationId.ofRepoId(shippingNotificationRecord.getM_Shipping_Notification_ID()));

		final HashMap<ShippingNotificationLineId, I_M_Shipping_NotificationLine> existingLineRecordsById = queryBL.createQueryBuilder(I_M_Shipping_NotificationLine.class)
				.addEqualsFilter(I_M_Shipping_NotificationLine.COLUMNNAME_M_Shipping_Notification_ID, shippingNotification.getId())
				.create()
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(lineRecord -> ShippingNotificationLineId.ofRepoId(lineRecord.getM_Shipping_NotificationLine_ID())));

		for (ShippingNotificationLine line : shippingNotification.getLines())
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
		record.setAD_Org_ID(from.getOrgId().getRepoId());
		record.setC_DocType_ID(from.getDocTypeId().getRepoId());
		record.setC_BPartner_ID(from.getBpartnerAndLocationId().getBpartnerId().getRepoId());
		record.setC_BPartner_Location_ID(from.getBpartnerAndLocationId().getRepoId());
		record.setAD_User_ID(from.getContactId() != null ? from.getContactId().getRepoId() : -1);
		record.setC_Auction_ID(from.getAuctionId());
		record.setM_Warehouse_ID(from.getLocatorId().getWarehouseId().getRepoId());
		record.setM_Locator_ID(from.getLocatorId().getRepoId());
		record.setC_Order_ID(from.getOrderId().getRepoId());
		record.setPhysicalClearanceDate(Timestamp.from(from.getPhysicalClearanceDate()));
		record.setDateAcct(Timestamp.from(from.getPhysicalClearanceDate()));
		record.setHarvesting_Year_ID(from.getHarvestringYearId().yearId().getRepoId());
		record.setC_Harvesting_Calendar_ID(from.getHarvestringYearId().calendarId().getRepoId());
		record.setPOReference(from.getPoReference());
		record.setDescription(from.getDescription());
		record.setDocStatus(from.getDocStatus().getCode());
		record.setProcessed(from.isProcessed());
		record.setBPartnerAddress(from.getBpaddress());
	}

	private static void updateRecord(
			@NonNull final I_M_Shipping_NotificationLine record,
			@NonNull final ShippingNotificationLine fromLine,
			@NonNull final ShippingNotification fromHeader)
	{
		record.setProcessed(fromHeader.isProcessed());
		record.setM_Product_ID(fromLine.getProductId().getRepoId());
		record.setM_AttributeSetInstance_ID(fromLine.getAsiId().getRepoId());
		record.setMovementQty(fromLine.getQty().toBigDecimal());
		record.setC_UOM_ID(fromLine.getQty().getUomId().getRepoId());
		record.setM_ShipmentSchedule_ID(fromLine.getShipmentScheduleId().getRepoId());
		record.setC_Order_ID(fromLine.getOrderAndLineId().getOrderRepoId());
		record.setC_OrderLine_ID(fromLine.getOrderAndLineId().getOrderLineRepoId());
		record.setM_Shipping_Notification_ID(fromHeader.getId().getRepoId());
		record.setLine(fromLine.getLine().toInt());
	}

	private void saveRecordIfAllowed(@NonNull I_M_Shipping_Notification shippingNotificationRecord)
	{
		final ShippingNotificationId id = extractIdOrNull(shippingNotificationRecord);
		if (id != null && headerIdsToAvoidSaving.contains(id))
		{
			return;
		}
		InterfaceWrapperHelper.saveRecord(shippingNotificationRecord);
	}

	private void saveLine(@NonNull I_M_Shipping_NotificationLine shippingNotificationLineRecord)
	{
		InterfaceWrapperHelper.save(shippingNotificationLineRecord);
	}

	public void updateById(@NonNull final ShippingNotificationId id, @NonNull final Consumer<ShippingNotification> consumer)
	{
		final ShippingNotification shippingNotification = getById(id);
		consumer.accept(shippingNotification);
		save(shippingNotification);
	}

	public void updateWhileSaving(
			@NonNull final I_M_Shipping_Notification record,
			@NonNull final Consumer<ShippingNotification> consumer)
	{
		final ShippingNotificationId id = extractIdOrNull(record);
		if (id == null)
		{
			final ShippingNotification shippingNotification = fromRecord(record, ImmutableList.of());
			consumer.accept(shippingNotification);
			if (!shippingNotification.getLines().isEmpty())
			{
				throw new AdempiereException("Adding lines to a new shipper notification which is not allowed to be saved is allowed");
			}
			saveRecordIfAllowed(record);
		}
		else
		{
			addToCacheAndAvoidSaving(record);
			updateById(id, consumer);
		}
	}
}

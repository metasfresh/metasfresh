/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.order.attachmenteditor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryRepository;
import de.metas.bpartner.BPartnerId;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.util.Services;
import de.metas.vertical.healthcare.alberta.bpartner.patient.AlbertaPatient;
import de.metas.vertical.healthcare.alberta.bpartner.patient.AlbertaPatientRepository;
import de.metas.vertical.healthcare.alberta.model.I_Alberta_PrescriptionRequest;
import de.metas.vertical.healthcare.alberta.prescription.dao.AlbertaPrescriptionRequestDAO;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

final class OrderAttachmentRowsLoader
{
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	private final AlbertaPrescriptionRequestDAO albertaPrescriptionRequestDAO;
	private final AttachmentEntryRepository attachmentEntryRepository;
	private final AlbertaPatientRepository albertaPatientRepository;

	private final LookupDataSource patientLookup;
	private final LookupDataSource payerLookup;
	private final LookupDataSource pharmacyLookup;
	private final OrderId selectedPurchaseOrderId;

	@Builder
	private OrderAttachmentRowsLoader(
			@NonNull final AttachmentEntryRepository attachmentEntryRepository,
			@NonNull final AlbertaPrescriptionRequestDAO albertaPrescriptionRequestDAO,
			@NonNull final AlbertaPatientRepository albertaPatientRepository,
			@NonNull final LookupDataSource patientLookup,
			@NonNull final LookupDataSource payerLookup,
			@NonNull final LookupDataSource pharmacyLookup,
			@NonNull final OrderId orderId)
	{
		this.attachmentEntryRepository = attachmentEntryRepository;
		this.albertaPrescriptionRequestDAO = albertaPrescriptionRequestDAO;
		this.albertaPatientRepository = albertaPatientRepository;
		this.patientLookup = patientLookup;
		this.payerLookup = payerLookup;
		this.pharmacyLookup = pharmacyLookup;

		this.selectedPurchaseOrderId = orderId;
	}

	public OrderAttachmentRows load()
	{
		final I_C_Order selectedPurchaseOrder = orderDAO.getById(selectedPurchaseOrderId);

		final Optional<I_C_Order> salesOrder = Optional.ofNullable(OrderId.ofRepoIdOrNull(selectedPurchaseOrder.getLink_Order_ID()))
				.map(orderDAO::getById);

		final Optional<I_Alberta_PrescriptionRequest> albertaPrescriptionRequest = salesOrder
				.map(I_C_Order::getC_Order_ID)
				.map(OrderId::ofRepoId)
				.map(albertaPrescriptionRequestDAO::getForOrderId)
				.filter(list -> !list.isEmpty())
				.map(prescriptionList -> prescriptionList.get(0));

		final ImmutableSet<TableRecordReference> tableRecordReferences = getTargetTableRecordReferences(salesOrder.orElse(null), albertaPrescriptionRequest.orElse(null));

		final Set<AttachmentEntry> attachmentEntries = attachmentEntryRepository.getByReferencedRecords(tableRecordReferences);

		final ImmutableList<OrderAttachmentRow> rows = attachmentEntries.stream()
				.map(attachmentEntry -> toOrderAttachmentRow(attachmentEntry, selectedPurchaseOrder, salesOrder.orElse(null), albertaPrescriptionRequest.orElse(null)))
				.collect(ImmutableList.toImmutableList());

		return OrderAttachmentRows.builder()
				.rows(rows)
				.build();
	}

	@Nullable
	private OrderAttachmentRow toOrderAttachmentRow(
			@NonNull final AttachmentEntry attachmentEntry,
			@NonNull final I_C_Order selectedPurchaseOrder,
			@Nullable final I_C_Order salesOrder,
			@Nullable final I_Alberta_PrescriptionRequest albertaPrescriptionRequest)
	{
		final ZonedDateTime datePromised = Optional.ofNullable(salesOrder)
				.map(so -> {
					final ZoneId timeZone = orderBL.getTimeZone(so);
					return TimeUtil.asZonedDateTime(so.getDatePromised(), timeZone);
				})
				.orElse(null);

		final Set<TableRecordReference> soAndPoRecordReferences = getSoAndPoRecordReferences(salesOrder);

		final boolean isAttachToPurchaseOrder = attachmentEntry.getLinkedRecords()
				.stream()
				.anyMatch(soAndPoRecordReferences::contains);

		final OrderAttachmentRow.OrderAttachmentRowBuilder builder = OrderAttachmentRow.builder();

		if (albertaPrescriptionRequest != null)
		{
			builder.patient(extractPatientFromPrescriptionRequest(albertaPrescriptionRequest))
					.payer(extractPayerFromPrescriptionRequest(albertaPrescriptionRequest))
					.pharmacy(extractPharmacyFromPrescriptionRequest(albertaPrescriptionRequest));
		}
		else if (salesOrder != null)
		{
			builder.patient(extractPatientFromSalesOrder(salesOrder))
					.payer(extractPayerFromSalesOrder(salesOrder));
		}

		final ImmutableSet<TableRecordReference> allTargetRecordReferences = getTargetTableRecordReferences(salesOrder, albertaPrescriptionRequest);

		return builder
				.datePromised(datePromised)
				.filename(attachmentEntry.getFilename_Override(allTargetRecordReferences).orElseGet(attachmentEntry::getFilename))
				.isAttachToPurchaseOrder(isAttachToPurchaseOrder)
				.isAttachToPurchaseOrderInitial(isAttachToPurchaseOrder)
				.selectedPurchaseOrder(selectedPurchaseOrder)
				.attachmentEntry(attachmentEntry)
				.build();
	}

	@NonNull
	private ImmutableSet<TableRecordReference> getTargetTableRecordReferences(
			@Nullable final I_C_Order salesOrder,
			@Nullable final I_Alberta_PrescriptionRequest albertaPrescriptionRequest)
	{
		final ImmutableSet.Builder<TableRecordReference> recordReferenceBuilder = ImmutableSet.builder();

		recordReferenceBuilder.add(TableRecordReference.of(I_C_Order.Table_Name, selectedPurchaseOrderId.getRepoId()));

		Optional.ofNullable(salesOrder)
				.map(I_C_Order::getC_Order_ID)
				.map(salesOrderId -> TableRecordReference.of(I_C_Order.Table_Name, salesOrderId))
				.ifPresent(recordReferenceBuilder::add);

		Optional.ofNullable(salesOrder)
				.map(I_C_Order::getC_BPartner_ID)
				.map(bPartnerId -> TableRecordReference.of(I_C_BPartner.Table_Name, bPartnerId))
				.ifPresent(recordReferenceBuilder::add);

		Optional.ofNullable(albertaPrescriptionRequest)
				.map(request -> TableRecordReference.of(I_Alberta_PrescriptionRequest.Table_Name, request.getAlberta_PrescriptionRequest_ID()))
				.ifPresent(recordReferenceBuilder::add);

		return recordReferenceBuilder.build();
	}

	@Nullable
	private LookupValue extractPatientFromPrescriptionRequest(@NonNull final I_Alberta_PrescriptionRequest albertaPrescriptionRequest)
	{
		final BPartnerId patientBPartnerId = BPartnerId.ofRepoId(albertaPrescriptionRequest.getC_BPartner_Patient_ID());
		return patientLookup.findById(patientBPartnerId);
	}

	@Nullable
	private LookupValue extractPayerFromPrescriptionRequest(@NonNull final I_Alberta_PrescriptionRequest albertaPrescriptionRequest)
	{
		return Optional.of(albertaPrescriptionRequest)
				.map(albertaPrescriptionRequestDAO::getPayer)
				.map(I_C_BPartner::getC_BPartner_ID)
				.map(BPartnerId::ofRepoId)
				.map(payerLookup::findById)
				.orElse(null);
	}

	@Nullable
	private LookupValue extractPharmacyFromPrescriptionRequest(@NonNull final I_Alberta_PrescriptionRequest albertaPrescriptionRequest)
	{
		final BPartnerId pharmacyId = BPartnerId.ofRepoId(albertaPrescriptionRequest.getC_BPartner_Pharmacy_ID());
		return pharmacyLookup.findById(pharmacyId);
	}

	@Nullable
	private LookupValue extractPatientFromSalesOrder(@NonNull final I_C_Order salesOrder)
	{
		final BPartnerId patientBpartnerId = BPartnerId.ofRepoId(salesOrder.getC_BPartner_ID());
		return patientLookup.findById(patientBpartnerId);
	}

	@Nullable
	private LookupValue extractPayerFromSalesOrder(@NonNull final I_C_Order salesOrder)
	{
		final BPartnerId patientBPartnerId = BPartnerId.ofRepoId(salesOrder.getC_BPartner_ID());

		return albertaPatientRepository.getByBPartnerId(patientBPartnerId)
				.map(AlbertaPatient::getPayerId)
				.map(payerLookup::findById)
				.orElse(null);
	}

	@NonNull
	private Set<TableRecordReference> getSoAndPoRecordReferences(@Nullable final I_C_Order salesOrder)
	{
		final Set<TableRecordReference> soAndPoRecordReferences = new HashSet<>();
		soAndPoRecordReferences.add(getPurchaseOrderRecordRef());
		getSalesOrderRecordRef(salesOrder).ifPresent(soAndPoRecordReferences::add);

		return soAndPoRecordReferences;
	}

	@NonNull
	private Optional<TableRecordReference> getSalesOrderRecordRef(@Nullable final I_C_Order salesOrder)
	{
		return Optional.ofNullable(salesOrder)
				.map(I_C_Order::getC_Order_ID)
				.map(orderId -> TableRecordReference.of(I_C_Order.Table_Name, orderId));
	}

	@NonNull
	private TableRecordReference getPurchaseOrderRecordRef()
	{
		return TableRecordReference.of(I_C_Order.Table_Name, selectedPurchaseOrderId);
	}
}

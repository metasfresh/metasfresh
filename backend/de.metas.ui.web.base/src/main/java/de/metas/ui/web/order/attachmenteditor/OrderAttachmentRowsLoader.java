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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryId;
import de.metas.attachments.AttachmentEntryRepository;
import de.metas.bpartner.BPartnerId;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.util.Services;
import de.metas.vertical.healthcare.alberta.bpartner.patient.AlbertaPatient;
import de.metas.vertical.healthcare.alberta.bpartner.patient.AlbertaPatientRepository;
import de.metas.vertical.healthcare.alberta.model.I_Alberta_PrescriptionRequest;
import de.metas.vertical.healthcare.alberta.prescription.dao.AlbertaPrescriptionRequestDAO;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

@Value
class OrderAttachmentRowsLoader
{
	IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	IOrderBL orderBL = Services.get(IOrderBL.class);

	@NonNull
	AlbertaPrescriptionRequestDAO albertaPrescriptionRequestDAO;
	@NonNull
	AttachmentEntryRepository attachmentEntryRepository;
	@NonNull
	AlbertaPatientRepository albertaPatientRepository;
	@NonNull
	PurchaseCandidateRepository purchaseCandidateRepository;
	@NonNull
	LookupDataSource patientLookup;
	@NonNull
	LookupDataSource payerLookup;
	@NonNull
	LookupDataSource pharmacyLookup;
	@NonNull
	OrderId selectedPurchaseOrderId;

	@NonFinal
	I_C_Order purchaseOrder;
	@NonFinal
	@NonNull
	Map<TableRecordReference, I_C_Order> salesOrderRecordRefs = ImmutableMap.of();
	@NonFinal
	@NonNull
	Map<TableRecordReference, I_Alberta_PrescriptionRequest> prescriptionRequestRecordRefs = ImmutableMap.of();
	@NonFinal
	@NonNull
	Set<TableRecordReference> bPartnerRecordRefs = ImmutableSet.of();

	@Builder
	private OrderAttachmentRowsLoader(
			@NonNull final AttachmentEntryRepository attachmentEntryRepository,
			@NonNull final AlbertaPrescriptionRequestDAO albertaPrescriptionRequestDAO,
			@NonNull final AlbertaPatientRepository albertaPatientRepository,
			@NonNull final PurchaseCandidateRepository purchaseCandidateRepository,
			@NonNull final LookupDataSource patientLookup,
			@NonNull final LookupDataSource payerLookup,
			@NonNull final LookupDataSource pharmacyLookup,
			@NonNull final OrderId purchaseOrderId)
	{
		this.attachmentEntryRepository = attachmentEntryRepository;
		this.albertaPrescriptionRequestDAO = albertaPrescriptionRequestDAO;
		this.albertaPatientRepository = albertaPatientRepository;
		this.purchaseCandidateRepository = purchaseCandidateRepository;
		this.patientLookup = patientLookup;
		this.payerLookup = payerLookup;
		this.pharmacyLookup = pharmacyLookup;

		this.selectedPurchaseOrderId = purchaseOrderId;
	}

	public OrderAttachmentRows load()
	{
		init();

		final Set<TableRecordReference> allTargetTableRecordRefs = Stream.of(ImmutableSet.of(getPurchaseOrderRecordRef()),
																			 salesOrderRecordRefs.keySet(),
																			 prescriptionRequestRecordRefs.keySet(),
																			 bPartnerRecordRefs)
				.flatMap(Set::stream)
				.collect(ImmutableSet.toImmutableSet());

		final Map<String, AttachmentRowBuilder.PriorityRowBuilder> tableName2PriorityRowBuilder = ImmutableMap.of(
				I_C_BPartner.Table_Name, AttachmentRowBuilder.PriorityRowBuilder.of(AttachmentRowBuilder.Priority.of(0), this::buildRowFromCustomer),
				I_C_Order.Table_Name, AttachmentRowBuilder.PriorityRowBuilder.of(AttachmentRowBuilder.Priority.of(1), this::buildRowFromSalesOrder),
				I_Alberta_PrescriptionRequest.Table_Name, AttachmentRowBuilder.PriorityRowBuilder.of(AttachmentRowBuilder.Priority.of(2), this::buildRowFromPrescription));

		final AttachmentRowBuilder attachmentRowBuilder = AttachmentRowBuilder.builder()
				.purchaseOrderRowBuilder(this::buildRowFromPurchaseOrder)
				.purchaseOrderId(selectedPurchaseOrderId)
				.tableName2PriorityRowBuilder(tableName2PriorityRowBuilder)
				.targetRecordReferences(allTargetTableRecordRefs)
				.build();

		final ImmutableList<OrderAttachmentRow> rows = attachmentEntryRepository.getByReferencedRecords(allTargetTableRecordRefs)
				.stream()
				.map(attachmentRowBuilder::buildRowsFor)
				.flatMap(List::stream)
				.collect(ImmutableList.toImmutableList());

		return OrderAttachmentRows.builder()
				.rows(rows)
				.build();
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
	private OrderAttachmentRow buildRowFromPrescription(@NonNull final TableRecordReference recordReference, @NonNull final AttachmentEntry attachmentEntry)
	{
		final I_Alberta_PrescriptionRequest prescriptionRequest = Optional.ofNullable(prescriptionRequestRecordRefs.get(recordReference))
				.orElseThrow(() -> new AdempiereException("No I_Alberta_PrescriptionRequest present for given record reference: " + recordReference));

		final boolean isAttachToPurchaseOrder = attachmentEntry.hasLinkToRecord(getPurchaseOrderRecordRef())
				|| salesOrderRecordRefs.keySet().stream().anyMatch(attachmentEntry::hasLinkToRecord);

		return OrderAttachmentRow.builder()
				.rowId(buildRowId(attachmentEntry.getId(), recordReference))
				.patient(extractPatientFromPrescriptionRequest(prescriptionRequest))
				.payer(extractPayerFromPrescriptionRequest(prescriptionRequest))
				.pharmacy(extractPharmacyFromPrescriptionRequest(prescriptionRequest))
				.filename(attachmentEntry.getFilename(recordReference))
				.isAttachToPurchaseOrder(isAttachToPurchaseOrder)
				.selectedPurchaseOrder(purchaseOrder)
				.attachmentEntry(attachmentEntry)
				.build();
	}

	@NonNull
	private OrderAttachmentRow buildRowFromPurchaseOrder(@NonNull final AttachmentEntry attachmentEntry)
	{
		final TableRecordReference purchaseOrderRecordRef = getPurchaseOrderRecordRef();

		if (!attachmentEntry.hasLinkToRecord(purchaseOrderRecordRef))
		{
			throw new AdempiereException("Given attachmentEntry is not linked to purchase order!")
					.appendParametersToMessage()
					.setParameter("attachmentEntry", attachmentEntry)
					.setParameter("purchaseOrderRecordRef", purchaseOrderRecordRef);
		}

		return OrderAttachmentRow.builder()
				.rowId(buildRowId(attachmentEntry.getId(), getPurchaseOrderRecordRef()))
				.filename(attachmentEntry.getFilename(getPurchaseOrderRecordRef()))
				.isAttachToPurchaseOrder(true)
				.selectedPurchaseOrder(purchaseOrder)
				.attachmentEntry(attachmentEntry)
				.build();
	}

	@NonNull
	private OrderAttachmentRow buildRowFromSalesOrder(@NonNull final TableRecordReference recordReference, @NonNull final AttachmentEntry attachmentEntry)
	{
		final I_C_Order salesOrder;

		salesOrder = Optional.ofNullable(salesOrderRecordRefs.get(recordReference))
				.orElseThrow(() -> new AdempiereException("No I_C_Order present for given record reference: " + recordReference));

		final ZoneId timeZone = orderBL.getTimeZone(salesOrder);

		final ZonedDateTime datePromised = TimeUtil.asZonedDateTime(salesOrder.getDatePromised(), timeZone);

		return OrderAttachmentRow.builder()
				.rowId(buildRowId(attachmentEntry.getId(), recordReference))
				.patient(extractPatientFromSalesOrder(salesOrder))
				.payer(extractPayerFromSalesOrder(salesOrder))
				.datePromised(datePromised)
				.filename(attachmentEntry.getFilename(recordReference))
				.isAttachToPurchaseOrder(true)
				.selectedPurchaseOrder(purchaseOrder)
				.attachmentEntry(attachmentEntry)
				.build();
	}

	@NonNull
	private OrderAttachmentRow buildRowFromCustomer(@NonNull final TableRecordReference recordReference, @NonNull final AttachmentEntry attachmentEntry)
	{
		if (!bPartnerRecordRefs.contains(recordReference))
		{
			throw new AdempiereException("Given recordReference is not among known ones!")
					.appendParametersToMessage()
					.setParameter("GivenTableRecordRef", recordReference)
					.setParameter("KnownTableRecordRef", bPartnerRecordRefs);
		}

		return OrderAttachmentRow.builder()
				.rowId(buildRowId(attachmentEntry.getId(), recordReference))
				.patient(patientLookup.findById(recordReference.getRecord_ID()))
				.filename(attachmentEntry.getFilename(recordReference))
				.isAttachToPurchaseOrder(attachmentEntry.hasLinkToRecord(getPurchaseOrderRecordRef()))
				.selectedPurchaseOrder(purchaseOrder)
				.attachmentEntry(attachmentEntry)
				.build();
	}

	@NonNull
	private TableRecordReference getPurchaseOrderRecordRef()
	{
		return TableRecordReference.of(I_C_Order.Table_Name, selectedPurchaseOrderId);
	}

	@NonNull
	@VisibleForTesting
	public static DocumentId buildRowId(@NonNull final AttachmentEntryId attachmentEntryId, @NonNull final TableRecordReference tableRecordReference)
	{
		return DocumentId.ofComposedKeyParts(ImmutableList.of(attachmentEntryId.getRepoId(), tableRecordReference.getAD_Table_ID(), tableRecordReference.getRecord_ID()));
	}

	private void init()
	{
		purchaseOrder = orderDAO.getById(selectedPurchaseOrderId);

		final Optional<List<I_C_Order>> salesOrders = getSalesOrders();

		salesOrderRecordRefs = salesOrders.map(this::getSalesOrdersRecordRef).orElse(ImmutableMap.of());

		bPartnerRecordRefs = salesOrders.map(this::getSalesOrderPartnersRecordRef).orElse(ImmutableSet.of());

		final Set<OrderId> salesOrderIds = salesOrderRecordRefs.keySet()
				.stream()
				.map(TableRecordReference::getRecord_ID)
				.map(OrderId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		prescriptionRequestRecordRefs = getAlbertaPrescriptionsRecordRefs(salesOrderIds).orElse(ImmutableMap.of());
	}

	@NonNull
	private Optional<List<I_C_Order>> getSalesOrders()
	{
		final ImmutableSet.Builder<OrderId> salesOrderIds = ImmutableSet.builder();
		salesOrderIds.addAll(orderDAO.getSalesOrderIdsByPurchaseOrderId(selectedPurchaseOrderId));

		if (purchaseOrder.getLink_Order_ID() > 0) // C_OrderLine.Link_OrderLine_ID might be null, but there might be a 1:1 linked sales order
		{
			salesOrderIds.add(OrderId.ofRepoId(purchaseOrder.getLink_Order_ID()));
		}

		purchaseCandidateRepository.getAllByPurchaseOrderId(selectedPurchaseOrderId)
				.stream()
				.map(PurchaseCandidate::getSalesOrderAndLineIdOrNull)
				.filter(Objects::nonNull)
				.map(OrderAndLineId::getOrderId)
				.forEach(salesOrderIds::add);

		return Optional.of(orderDAO.getByIds(salesOrderIds.build()));
	}

	@NonNull
	private Map<TableRecordReference, I_C_Order> getSalesOrdersRecordRef(@NonNull final List<I_C_Order> salesOrders)
	{
		return salesOrders
				.stream()
				.collect(ImmutableMap.toImmutableMap(order -> TableRecordReference.of(I_C_Order.Table_Name, order.getC_Order_ID()),
													 Function.identity()));
	}

	@NonNull
	private ImmutableSet<TableRecordReference> getSalesOrderPartnersRecordRef(@NonNull final List<I_C_Order> salesOrders)
	{
		return salesOrders
				.stream()
				.map(order -> TableRecordReference.of(I_C_BPartner.Table_Name, order.getC_BPartner_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	private Optional<Map<TableRecordReference, I_Alberta_PrescriptionRequest>> getAlbertaPrescriptionsRecordRefs(@NonNull final Set<OrderId> salesOrderIds)
	{
		if (salesOrderIds.isEmpty())
		{
			return Optional.empty();
		}

		return Optional.of(albertaPrescriptionRequestDAO.getByOrderIds(salesOrderIds)
								   .stream()
								   .collect(ImmutableMap.toImmutableMap(prescription -> TableRecordReference.of(I_Alberta_PrescriptionRequest.Table_Name, prescription.getAlberta_PrescriptionRequest_ID()),
																		Function.identity())));
	}
}

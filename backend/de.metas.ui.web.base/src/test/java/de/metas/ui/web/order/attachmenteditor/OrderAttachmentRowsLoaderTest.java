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
import de.metas.adempiere.model.I_C_Order;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryCreateRequest;
import de.metas.attachments.AttachmentEntryFactory;
import de.metas.attachments.AttachmentEntryRepository;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.listener.TableAttachmentListenerRepository;
import de.metas.attachments.listener.TableAttachmentListenerService;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.DemandGroupReference;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.quantity.Quantity;
import de.metas.ui.web.shipment_candidates_editor.MockedLookupDataSource;
import de.metas.util.Services;
import de.metas.vertical.healthcare.alberta.bpartner.patient.AlbertaPatientRepository;
import de.metas.vertical.healthcare.alberta.model.I_Alberta_PrescriptionRequest;
import de.metas.vertical.healthcare.alberta.model.I_C_BPartner_AlbertaPatient;
import de.metas.vertical.healthcare.alberta.prescription.dao.AlbertaPrescriptionRequestDAO;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_PO_OrderLine_Alloc;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static de.metas.ui.web.order.attachmenteditor.OrderAttachmentRowsLoader.buildRowId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class OrderAttachmentRowsLoaderTest
{
	private PurchaseCandidateRepository purchaseCandidateRepository;

	private I_C_BPartner bpartnerRecord;
	private AttachmentEntryRepository attachmentEntryRepository;
	private final AttachmentEntryService attachmentEntryService = AttachmentEntryService.createInstanceForUnitTesting();

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_User_ID, 10); // will be in the attachment-entry's CreatedUpdatedInfo

		bpartnerRecord = createPartner("bpartner");

		final AttachmentEntryFactory attachmentEntryFactory = new AttachmentEntryFactory();
		final TableAttachmentListenerService tableAttachmentListenerService = new TableAttachmentListenerService(new TableAttachmentListenerRepository());
		attachmentEntryRepository = new AttachmentEntryRepository(attachmentEntryFactory, tableAttachmentListenerService);
		purchaseCandidateRepository = Mockito.mock(PurchaseCandidateRepository.class);
	}

	@Test
	public void givenAttachmentLinkedToSOCustomer_whenLoad_thenLoadFromSO()
	{
		//given
		final I_C_Order purchaseOrder = createPurchaseOrder();
		final I_C_Order salesOrder = createSalesOrderForPO(purchaseOrder);
		salesOrder.setC_BPartner_ID(bpartnerRecord.getC_BPartner_ID());
		save(salesOrder);

		final AttachmentEntry bpartnerAttachmentEntry = attachmentEntryService.createNewAttachment(bpartnerRecord, "bPartnerAttachment", "bPartnerAttachment.data".getBytes());

		//when
		final OrderAttachmentRowsLoader loader = newOrderAttachmentRowsLoader(OrderId.ofRepoId(purchaseOrder.getC_Order_ID()));
		final OrderAttachmentRows orderAttachmentRows = loader.load();

		//then
		assertThat(orderAttachmentRows.getAllRows().size()).isEqualTo(1);

		final OrderAttachmentRow orderAttachmentRow = orderAttachmentRows.getAllRows().iterator().next();

		assertThat(orderAttachmentRow.getId()).isEqualTo(buildRowId(bpartnerAttachmentEntry.getId(), TableRecordReference.of(bpartnerRecord)));
		assertThat(orderAttachmentRow.getIsAttachToPurchaseOrder()).isEqualTo(Boolean.FALSE);
		assertThat(orderAttachmentRow.getPatient().getId()).isEqualTo(bpartnerRecord.getC_BPartner_ID());
		assertThat(TimeUtil.asTimestamp(orderAttachmentRow.getDatePromised())).isNull();
		assertThat(orderAttachmentRow.getFilename()).isEqualTo(bpartnerAttachmentEntry.getFilename());
		assertThat(orderAttachmentRow.getPayer()).isNull();
		assertThat(orderAttachmentRow.getPharmacy()).isNull();
	}

	@Test
	public void givenAttachmentLinkedToPOAndSOCustomer_whenLoad_thenLoadFromSOCustomer()
	{
		//given
		final I_C_Order purchaseOrder = createPurchaseOrder();
		final I_C_Order salesOrder = createSalesOrderForPO(purchaseOrder);
		salesOrder.setC_BPartner_ID(bpartnerRecord.getC_BPartner_ID());
		save(salesOrder);

		final List<TableRecordReference> attachmentLinks = ImmutableList.of(TableRecordReference.of(bpartnerRecord), TableRecordReference.of(purchaseOrder));

		final AttachmentEntry attachmentEntry = attachmentEntryService
				.createNewAttachment(attachmentLinks, AttachmentEntryCreateRequest.builderFromByteArray("2linksAttachment", "2linksAttachment.data".getBytes()).build());

		//when
		final OrderAttachmentRowsLoader loader = newOrderAttachmentRowsLoader(OrderId.ofRepoId(purchaseOrder.getC_Order_ID()));
		final OrderAttachmentRows orderAttachmentRows = loader.load();

		//then
		assertThat(orderAttachmentRows.getAllRows().size()).isEqualTo(1);

		final OrderAttachmentRow orderAttachmentRow = orderAttachmentRows.getAllRows().iterator().next();

		assertThat(orderAttachmentRow.getId()).isEqualTo(buildRowId(attachmentEntry.getId(), TableRecordReference.of(bpartnerRecord)));
		assertThat(orderAttachmentRow.getIsAttachToPurchaseOrder()).isEqualTo(Boolean.TRUE);
		assertThat(orderAttachmentRow.getPatient().getId()).isEqualTo(bpartnerRecord.getC_BPartner_ID());
		assertThat(TimeUtil.asTimestamp(orderAttachmentRow.getDatePromised())).isNull();
		assertThat(orderAttachmentRow.getFilename()).isEqualTo(attachmentEntry.getFilename());
		assertThat(orderAttachmentRow.getPayer()).isNull();
		assertThat(orderAttachmentRow.getPharmacy()).isNull();
	}

	@Test
	public void givenAttachmentLinkedToSOAndPrescription_whenLoad_thenLoadFromPrescription()
	{
		//given
		final I_C_Order purchaseOrder = createPurchaseOrder();
		final I_C_Order salesOrder = createSalesOrderForPO(purchaseOrder);
		salesOrder.setC_BPartner_ID(bpartnerRecord.getC_BPartner_ID());
		save(salesOrder);

		final I_Alberta_PrescriptionRequest prescriptionRequest = createPrescriptionRequest(1, OrderId.ofRepoId(salesOrder.getC_Order_ID()), true);

		final TableRecordReference prescriptionRef = TableRecordReference.of(I_Alberta_PrescriptionRequest.Table_Name, prescriptionRequest.getAlberta_PrescriptionRequest_ID());

		final List<TableRecordReference> attachmentLinks = ImmutableList.of(TableRecordReference.of(I_C_Order.Table_Name, salesOrder.getC_Order_ID()), prescriptionRef);

		final AttachmentEntry attachmentEntry = attachmentEntryService
				.createNewAttachment(attachmentLinks, AttachmentEntryCreateRequest.builderFromByteArray("2linksAttachment", "2linksAttachment.data".getBytes()).build());

		//when
		final OrderAttachmentRowsLoader loader = newOrderAttachmentRowsLoader(OrderId.ofRepoId(purchaseOrder.getC_Order_ID()));
		final OrderAttachmentRows orderAttachmentRows = loader.load();

		//then
		assertThat(orderAttachmentRows.getAllRows().size()).isEqualTo(1);

		final OrderAttachmentRow orderAttachmentRow = orderAttachmentRows.getAllRows().iterator().next();

		assertThat(orderAttachmentRow.getId()).isEqualTo(buildRowId(attachmentEntry.getId(), prescriptionRef));
		assertThat(orderAttachmentRow.getIsAttachToPurchaseOrder()).isEqualTo(Boolean.TRUE);
		assertThat(orderAttachmentRow.getPatient().getIdAsInt()).isEqualTo(prescriptionRequest.getC_BPartner_Patient_ID());
		assertThat(orderAttachmentRow.getDatePromised()).isNull();
		assertThat(orderAttachmentRow.getFilename()).isEqualTo(attachmentEntry.getFilename());
		assertThat(orderAttachmentRow.getPayer().getIdAsInt()).isEqualTo(getPayerBPartnerIdFromPatientBPartnerId(prescriptionRequest.getC_BPartner_Patient_ID()).getRepoId());
		assertThat(orderAttachmentRow.getPharmacy().getIdAsInt()).isEqualTo(prescriptionRequest.getC_BPartner_Pharmacy_ID());
	}

	@Test
	public void givenAttachmentLinkedToSOAnd2Prescriptions_whenLoad_thenLoad2RowsFor2Prescription()
	{
		//given
		final I_C_Order purchaseOrder = createPurchaseOrder();
		final I_C_Order salesOrder = createSalesOrderForPO(purchaseOrder);
		salesOrder.setC_BPartner_ID(bpartnerRecord.getC_BPartner_ID());
		save(salesOrder);

		final I_Alberta_PrescriptionRequest prescriptionRequest = createPrescriptionRequest(1, OrderId.ofRepoId(salesOrder.getC_Order_ID()), true);
		final I_Alberta_PrescriptionRequest prescriptionRequest2 = createPrescriptionRequest(2, OrderId.ofRepoId(salesOrder.getC_Order_ID()), true);

		final TableRecordReference prescriptionRef1 = TableRecordReference.of(prescriptionRequest);
		final TableRecordReference prescriptionRef2 = TableRecordReference.of(prescriptionRequest2);

		final List<TableRecordReference> attachmentLinks = ImmutableList.of(TableRecordReference.of(salesOrder), prescriptionRef1, prescriptionRef2);

		final AttachmentEntry attachmentEntry = attachmentEntryService
				.createNewAttachment(attachmentLinks, AttachmentEntryCreateRequest.builderFromByteArray("2linksAttachment", "2linksAttachment.data".getBytes()).build());

		//when
		final OrderAttachmentRowsLoader loader = newOrderAttachmentRowsLoader(OrderId.ofRepoId(purchaseOrder.getC_Order_ID()));
		final OrderAttachmentRows orderAttachmentRows = loader.load();

		//then
		assertThat(orderAttachmentRows.getAllRows().size()).isEqualTo(2);
		assertThat(orderAttachmentRows.getById(buildRowId(attachmentEntry.getId(), prescriptionRef1))).isNotNull();
		assertThat(orderAttachmentRows.getById(buildRowId(attachmentEntry.getId(), prescriptionRef2))).isNotNull();

		for (final OrderAttachmentRow orderAttachmentRow : orderAttachmentRows.getAllRows())
		{
			final I_Alberta_PrescriptionRequest currentPrescription = orderAttachmentRow.getId().equals(buildRowId(attachmentEntry.getId(), prescriptionRef1))
					? prescriptionRequest
					: prescriptionRequest2;

			assertThat(orderAttachmentRow.getId()).isEqualTo(buildRowId(attachmentEntry.getId(), TableRecordReference.of(currentPrescription)));

			assertThat(orderAttachmentRow.getIsAttachToPurchaseOrder()).isEqualTo(Boolean.TRUE);
			assertThat(orderAttachmentRow.getDatePromised()).isNull();

			assertThat(orderAttachmentRow.getFilename()).isEqualTo(attachmentEntry.getFilename());

			assertThat(orderAttachmentRow.getPatient().getIdAsInt())
					.isEqualTo(currentPrescription.getC_BPartner_Patient_ID());

			assertThat(orderAttachmentRow.getPayer().getIdAsInt())
					.isEqualTo(getPayerBPartnerIdFromPatientBPartnerId(currentPrescription.getC_BPartner_Patient_ID()).getRepoId());

			assertThat(orderAttachmentRow.getPharmacy().getIdAsInt())
					.isEqualTo(currentPrescription.getC_BPartner_Pharmacy_ID());
		}
	}

	@Test
	public void givenAttachmentLinkedToPOAndSO_whenLoad_thenLoadFromSO()
	{
		//given
		final I_C_Order purchaseOrder = createPurchaseOrder();
		final I_C_Order salesOrder = createSalesOrderForPO(purchaseOrder);
		salesOrder.setC_BPartner_ID(bpartnerRecord.getC_BPartner_ID());
		save(salesOrder);

		final TableRecordReference salesOrderRef = TableRecordReference.of(salesOrder);

		final List<TableRecordReference> attachmentLinks = ImmutableList.of(salesOrderRef, TableRecordReference.of(purchaseOrder));

		final AttachmentEntry attachmentEntry = attachmentEntryService
				.createNewAttachment(attachmentLinks, AttachmentEntryCreateRequest.builderFromByteArray("2linksAttachment", "2linksAttachment.data".getBytes()).build());

		//when
		final OrderAttachmentRowsLoader loader = newOrderAttachmentRowsLoader(OrderId.ofRepoId(purchaseOrder.getC_Order_ID()));
		final OrderAttachmentRows orderAttachmentRows = loader.load();

		//then
		assertThat(orderAttachmentRows.getAllRows().size()).isEqualTo(1);
		assertThat(orderAttachmentRows.getById(buildRowId(attachmentEntry.getId(), salesOrderRef))).isNotNull();

		final OrderAttachmentRow orderAttachmentRow = orderAttachmentRows.getAllRows().iterator().next();

		assertThat(orderAttachmentRow.getIsAttachToPurchaseOrder()).isEqualTo(Boolean.TRUE);

		assertThat(orderAttachmentRow.getFilename()).isEqualTo(attachmentEntry.getFilename());

		assertThat(TimeUtil.asTimestamp(orderAttachmentRow.getDatePromised())).isEqualTo(salesOrder.getDatePromised());

		assertThat(orderAttachmentRow.getPatient().getIdAsInt())
				.isEqualTo(salesOrder.getC_BPartner_ID());

		assertThat(orderAttachmentRow.getPayer()).isNull();

		assertThat(orderAttachmentRow.getPharmacy()).isNull();
	}

	@Test
	public void givenAttachmentLinkedToPO_whenLoad_thenLoadFromPO()
	{
		//given
		final I_C_Order purchaseOrder = createPurchaseOrder();

		final TableRecordReference purchaseOrderRef = TableRecordReference.of(purchaseOrder);

		final List<TableRecordReference> attachmentLinks = ImmutableList.of(purchaseOrderRef);

		final AttachmentEntry attachmentEntry = attachmentEntryService
				.createNewAttachment(attachmentLinks, AttachmentEntryCreateRequest.builderFromByteArray("POLinkAttachment", "POLinkAttachment.data".getBytes()).build());

		//when
		final OrderAttachmentRowsLoader loader = newOrderAttachmentRowsLoader(OrderId.ofRepoId(purchaseOrder.getC_Order_ID()));
		final OrderAttachmentRows orderAttachmentRows = loader.load();

		//then
		assertThat(orderAttachmentRows.getAllRows().size()).isEqualTo(1);
		assertThat(orderAttachmentRows.getById(buildRowId(attachmentEntry.getId(), purchaseOrderRef))).isNotNull();

		final OrderAttachmentRow orderAttachmentRow = orderAttachmentRows.getAllRows().iterator().next();

		assertThat(orderAttachmentRow.getIsAttachToPurchaseOrder()).isEqualTo(Boolean.TRUE);
		assertThat(orderAttachmentRow.getFilename()).isEqualTo(attachmentEntry.getFilename());
		assertThat(TimeUtil.asTimestamp(orderAttachmentRow.getDatePromised())).isNull();
		assertThat(orderAttachmentRow.getPatient()).isNull();
		assertThat(orderAttachmentRow.getPayer()).isNull();
		assertThat(orderAttachmentRow.getPharmacy()).isNull();
	}

	@Test
	public void givenAttachmentLinkedToPrescriptionWithNoPayer_whenLoad_thenLoadFromPrescription()
	{
		//given
		final I_C_Order purchaseOrder = createPurchaseOrder();
		final I_C_Order salesOrder = createSalesOrderForPO(purchaseOrder);
		salesOrder.setC_BPartner_ID(bpartnerRecord.getC_BPartner_ID());
		save(salesOrder);

		final I_Alberta_PrescriptionRequest prescriptionRequest = createPrescriptionRequest(1, OrderId.ofRepoId(salesOrder.getC_Order_ID()), false);

		final TableRecordReference prescriptionRecordRef = TableRecordReference.of(prescriptionRequest);

		final List<TableRecordReference> attachmentLinks = ImmutableList.of(prescriptionRecordRef);

		final AttachmentEntry attachmentEntry = attachmentEntryService
				.createNewAttachment(attachmentLinks, AttachmentEntryCreateRequest.builderFromByteArray("PrescriptionAttachment", "PrescriptionAttachment.data".getBytes()).build());

		//when
		final OrderAttachmentRowsLoader loader = newOrderAttachmentRowsLoader(OrderId.ofRepoId(purchaseOrder.getC_Order_ID()));
		final OrderAttachmentRows orderAttachmentRows = loader.load();

		//then
		assertThat(orderAttachmentRows.getAllRows().size()).isEqualTo(1);
		assertThat(orderAttachmentRows.getById(buildRowId(attachmentEntry.getId(), prescriptionRecordRef))).isNotNull();

		final OrderAttachmentRow orderAttachmentRow = orderAttachmentRows.getAllRows().iterator().next();

		assertThat(orderAttachmentRow.getIsAttachToPurchaseOrder()).isEqualTo(Boolean.FALSE);
		assertThat(orderAttachmentRow.getFilename()).isEqualTo(attachmentEntry.getFilename());
		assertThat(TimeUtil.asTimestamp(orderAttachmentRow.getDatePromised())).isNull();
		assertThat(orderAttachmentRow.getPatient().getIdAsInt()).isEqualTo(prescriptionRequest.getC_BPartner_Patient_ID());
		assertThat(orderAttachmentRow.getPharmacy().getIdAsInt()).isEqualTo(prescriptionRequest.getC_BPartner_Pharmacy_ID());
		assertThat(orderAttachmentRow.getPayer()).isNull();
	}

	@Test
	public void givenAttachmentLinkedToPOAndUnrelatedSO_whenLoad_thenLoadFromPO()
	{
		//given
		final I_C_Order purchaseOrder = createPurchaseOrder();
		final I_C_Order purchaseOrder1 = createPurchaseOrder();
		final I_C_Order unrelatedSalesOrder = createSalesOrderForPO(purchaseOrder1);
		unrelatedSalesOrder.setC_BPartner_ID(bpartnerRecord.getC_BPartner_ID());
		save(unrelatedSalesOrder);

		final TableRecordReference unrelatedSalesOrderRef = TableRecordReference.of(unrelatedSalesOrder);

		final List<TableRecordReference> attachmentLinks = ImmutableList.of(TableRecordReference.of(purchaseOrder), unrelatedSalesOrderRef);

		final AttachmentEntry attachmentEntry = attachmentEntryService
				.createNewAttachment(attachmentLinks, AttachmentEntryCreateRequest.builderFromByteArray("2linksAttachment", "2linksAttachment.data".getBytes()).build());

		//when
		final OrderAttachmentRowsLoader loader = newOrderAttachmentRowsLoader(OrderId.ofRepoId(purchaseOrder.getC_Order_ID()));
		final OrderAttachmentRows orderAttachmentRows = loader.load();

		//then
		assertThat(orderAttachmentRows.getAllRows().size()).isEqualTo(1);
		assertThat(orderAttachmentRows.getById(buildRowId(attachmentEntry.getId(), TableRecordReference.of(purchaseOrder)))).isNotNull();

		final OrderAttachmentRow orderAttachmentRow = orderAttachmentRows.getAllRows().iterator().next();

		assertThat(orderAttachmentRow.getIsAttachToPurchaseOrder()).isEqualTo(Boolean.TRUE);

		assertThat(orderAttachmentRow.getFilename()).isEqualTo(attachmentEntry.getFilename());

		assertThat(TimeUtil.asTimestamp(orderAttachmentRow.getDatePromised())).isNull();

		assertThat(orderAttachmentRow.getPatient()).isNull();

		assertThat(orderAttachmentRow.getPayer()).isNull();

		assertThat(orderAttachmentRow.getPharmacy()).isNull();
	}

	@Test
	public void givenAttachmentLinkedToSOCustomerAndSOLinkedThroughPurchaseCandidate_whenLoad_thenLoadFromSO()
	{
		//given
		final I_C_Order purchaseOrder = createPurchaseOrder();
		final OrderAndLineId salesOrderAndLineId = createSalesOrder();
		final PurchaseCandidate purchaseCandidate = createPurchaseCandidate(salesOrderAndLineId);

		when(purchaseCandidateRepository.getAllByPurchaseOrderId(any(OrderId.class))).thenReturn(ImmutableList.of(purchaseCandidate));

		final AttachmentEntry bpartnerAttachmentEntry = attachmentEntryService.createNewAttachment(bpartnerRecord, "bPartnerAttachment", "bPartnerAttachment.data".getBytes());

		//when
		final OrderAttachmentRowsLoader loader = newOrderAttachmentRowsLoader(OrderId.ofRepoId(purchaseOrder.getC_Order_ID()));
		final OrderAttachmentRows orderAttachmentRows = loader.load();

		//then
		assertThat(orderAttachmentRows.getAllRows().size()).isEqualTo(1);

		final OrderAttachmentRow orderAttachmentRow = orderAttachmentRows.getAllRows().iterator().next();

		assertThat(orderAttachmentRow.getId()).isEqualTo(buildRowId(bpartnerAttachmentEntry.getId(), TableRecordReference.of(bpartnerRecord)));
		assertThat(orderAttachmentRow.getIsAttachToPurchaseOrder()).isEqualTo(Boolean.FALSE);
		assertThat(orderAttachmentRow.getPatient().getId()).isEqualTo(bpartnerRecord.getC_BPartner_ID());
		assertThat(TimeUtil.asTimestamp(orderAttachmentRow.getDatePromised())).isNull();
		assertThat(orderAttachmentRow.getFilename()).isEqualTo(bpartnerAttachmentEntry.getFilename());
		assertThat(orderAttachmentRow.getPayer()).isNull();
		assertThat(orderAttachmentRow.getPharmacy()).isNull();
	}

	private OrderAttachmentRowsLoader newOrderAttachmentRowsLoader(final OrderId orderId)
	{
		return OrderAttachmentRowsLoader.builder()
				.attachmentEntryRepository(attachmentEntryRepository)
				.albertaPrescriptionRequestDAO(new AlbertaPrescriptionRequestDAO(new AlbertaPatientRepository()))
				.albertaPatientRepository(new AlbertaPatientRepository())
				.purchaseCandidateRepository(purchaseCandidateRepository)
				.patientLookup(MockedLookupDataSource.withNamePrefix("patient"))
				.payerLookup(MockedLookupDataSource.withNamePrefix("payer"))
				.pharmacyLookup(MockedLookupDataSource.withNamePrefix("payerPharmacy"))
				.purchaseOrderId(orderId)
				.build();
	}

	@Builder
	private I_C_Order createPurchaseOrder()
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setIsSOTrx(false);

		save(order);

		final I_C_OrderLine purchaseOrderLine = newInstance(I_C_OrderLine.class);
		purchaseOrderLine.setC_Order_ID(order.getC_Order_ID());
		save(purchaseOrderLine);

		return order;
	}

	@Builder
	private I_C_Order createSalesOrderForPO(final I_C_Order purchaseOrder)
	{
		final I_C_Order salesOrder = newInstance(I_C_Order.class);
		salesOrder.setIsSOTrx(true);
		salesOrder.setDatePromised(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 1, 1)));

		save(salesOrder);

		final I_C_OrderLine salesOrderLine = newInstance(I_C_OrderLine.class);
		salesOrderLine.setC_Order_ID(salesOrder.getC_Order_ID());
		save(salesOrderLine);

		final I_C_OrderLine purchaseOrderLine = Services.get(IQueryBL.class).createQueryBuilder(I_C_OrderLine.class)
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, purchaseOrder.getC_Order_ID())
				.create()
				.firstOnlyNotNull(I_C_OrderLine.class);
		save(purchaseOrderLine);

		final I_C_PO_OrderLine_Alloc poOrderLineAlloc = newInstance(I_C_PO_OrderLine_Alloc.class);
		poOrderLineAlloc.setC_SO_OrderLine(salesOrderLine);
		poOrderLineAlloc.setC_PO_OrderLine(purchaseOrderLine);
		save(poOrderLineAlloc);

		return salesOrder;
	}

	private OrderAndLineId createSalesOrder()
	{
		final I_C_Order salesOrder = newInstance(I_C_Order.class);
		salesOrder.setIsSOTrx(true);
		salesOrder.setDatePromised(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 1, 1)));
		salesOrder.setC_BPartner_ID(bpartnerRecord.getC_BPartner_ID());

		save(salesOrder);

		final I_C_OrderLine salesOrderLine = newInstance(I_C_OrderLine.class);
		salesOrderLine.setC_Order_ID(salesOrder.getC_Order_ID());

		save(salesOrderLine);

		return OrderAndLineId.of(OrderId.ofRepoId(salesOrder.getC_Order_ID()),
								 OrderLineId.ofRepoId(salesOrderLine.getC_OrderLine_ID()));
	}

	private I_C_BPartner createPartner(@NonNull final String name)
	{
		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setName(name);

		save(partner);

		return partner;
	}

	private I_C_BPartner_AlbertaPatient createAlbertaPatient(@NonNull final BPartnerId bPartnerId, @Nullable final BPartnerId payerBPartnerId)
	{
		final I_C_BPartner_AlbertaPatient albertaPatient = newInstance(I_C_BPartner_AlbertaPatient.class);
		albertaPatient.setC_BPartner_ID(bPartnerId.getRepoId());

		if (payerBPartnerId != null)
		{
			albertaPatient.setC_BPartner_Payer_ID(payerBPartnerId.getRepoId());
		}

		save(albertaPatient);

		return albertaPatient;
	}

	private I_Alberta_PrescriptionRequest createPrescriptionRequest(final int prescriptionIndex, @NonNull final OrderId orderId, final boolean withPayer)
	{
		final I_C_BPartner patientBPartner = createPartner("prescription_" + prescriptionIndex + "_patient");

		I_C_BPartner payerBPartner = null;
		if (withPayer)
		{
			payerBPartner = createPartner("prescription_" + prescriptionIndex + "_payer");
		}

		final BPartnerId payerId = withPayer ? BPartnerId.ofRepoId(payerBPartner.getC_BPartner_ID()) : null;

		createAlbertaPatient(BPartnerId.ofRepoId(patientBPartner.getC_BPartner_ID()), payerId);

		final I_C_BPartner pharmacyBPartner = createPartner("prescription_" + prescriptionIndex + "_pharmacy");

		final I_Alberta_PrescriptionRequest prescriptionRequest = InterfaceWrapperHelper.newInstance(I_Alberta_PrescriptionRequest.class);

		prescriptionRequest.setC_Order_ID(orderId.getRepoId());
		prescriptionRequest.setC_BPartner_Patient_ID(patientBPartner.getC_BPartner_ID());
		prescriptionRequest.setC_BPartner_Pharmacy_ID(pharmacyBPartner.getC_BPartner_ID());

		save(prescriptionRequest);

		return prescriptionRequest;
	}

	@Nullable
	private BPartnerId getPayerBPartnerIdFromPatientBPartnerId(final int patientBPartnerId)
	{
		final I_C_BPartner_AlbertaPatient albertaPatient = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner_AlbertaPatient.class)
				.addEqualsFilter(I_C_BPartner_AlbertaPatient.COLUMNNAME_C_BPartner_ID, patientBPartnerId)
				.create()
				.firstOnlyNotNull(I_C_BPartner_AlbertaPatient.class);

		return BPartnerId.ofRepoIdOrNull(albertaPatient.getC_BPartner_Payer_ID());
	}

	private PurchaseCandidate createPurchaseCandidate(@NonNull final OrderAndLineId orderAndLineId)
	{
		final ProductId productId = ProductId.ofRepoId(5);
		final AttributeSetInstanceId attributeSetInstanceId = AttributeSetInstanceId.ofRepoId(6);

		return PurchaseCandidate.builder()
				.id(PurchaseCandidateId.ofRepoIdOrNull(1))
				.groupReference(DemandGroupReference.EMPTY)
				.salesOrderAndLineIdOrNull(orderAndLineId)
				.orgId(OrgId.ofRepoId(3))
				.warehouseId(WarehouseId.ofRepoId(4))
				.productId(productId)
				.attributeSetInstanceId(attributeSetInstanceId)
				.vendorProductNo(String.valueOf(productId.getRepoId()))
				.vendorId(BPartnerId.ofRepoId(7))
				.qtyToPurchase(Quantity.zero(newInstance(I_C_UOM.class)))
				.purchaseDatePromised(SystemTime.asZonedDateTime().truncatedTo(ChronoUnit.DAYS))
				.build();
	}
}

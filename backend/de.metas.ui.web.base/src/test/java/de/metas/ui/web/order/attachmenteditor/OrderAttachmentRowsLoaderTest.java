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

import de.metas.adempiere.model.I_C_Order;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryFactory;
import de.metas.attachments.AttachmentEntryRepository;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.listener.TableAttachmentListenerRepository;
import de.metas.attachments.listener.TableAttachmentListenerService;
import de.metas.order.OrderId;
import de.metas.ui.web.shipment_candidates_editor.MockedLookupDataSource;
import de.metas.vertical.healthcare.alberta.bpartner.patient.AlbertaPatientRepository;
import de.metas.vertical.healthcare.alberta.prescription.dao.AlbertaPrescriptionRequestDAO;
import lombok.Builder;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.*;

public class OrderAttachmentRowsLoaderTest
{
	private I_C_BPartner bpartnerRecord;
	private AttachmentEntryRepository attachmentEntryRepository;
	private final AttachmentEntryService attachmentEntryService = AttachmentEntryService.createInstanceForUnitTesting();

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		bpartnerRecord = createPartner();

		final AttachmentEntryFactory attachmentEntryFactory = new AttachmentEntryFactory();
		final TableAttachmentListenerService tableAttachmentListenerService = new TableAttachmentListenerService(new TableAttachmentListenerRepository());
		attachmentEntryRepository = new AttachmentEntryRepository(attachmentEntryFactory, tableAttachmentListenerService);
	}

	private OrderAttachmentRowsLoader newOrderAttachmentRowsLoader(final OrderId orderId)
	{
		return OrderAttachmentRowsLoader.builder()
				.attachmentEntryRepository(attachmentEntryRepository)
				.albertaPrescriptionRequestDAO(new AlbertaPrescriptionRequestDAO(new AlbertaPatientRepository()))
				.albertaPatientRepository(new AlbertaPatientRepository())
				.patientLookup(MockedLookupDataSource.withNamePrefix("patient"))
				.payerLookup(MockedLookupDataSource.withNamePrefix("payer"))
				.pharmacyLookup(MockedLookupDataSource.withNamePrefix("payerPharmacy"))
				.orderId(orderId)
				.build();
	}

	@Builder
	private I_C_Order createPurchaseOrder()
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setC_BPartner_ID(bpartnerRecord.getC_BPartner_ID());
		order.setIsSOTrx(false);

		save(order);

		return order;
	}

	@Builder
	private I_C_Order createSalesOrderForPO(final I_C_Order purchaseOrder)
	{
		final I_C_Order salesOrder = newInstance(I_C_Order.class);
		salesOrder.setC_BPartner_ID(bpartnerRecord.getC_BPartner_ID());
		salesOrder.setIsSOTrx(true);
		salesOrder.setDatePromised(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 1, 1)));

		save(salesOrder);

		purchaseOrder.setLink_Order_ID(salesOrder.getC_Order_ID());
		save(purchaseOrder);

		return salesOrder;
	}

	private I_C_BPartner createPartner()
	{
		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setName("name");
		partner.setAD_Language("en");

		save(partner);

		return partner;
	}

	@Test
	public void load()
	{
		//given
		final I_C_Order purchaseOrder = createPurchaseOrder();
		final I_C_Order salesOrder = createSalesOrderForPO(purchaseOrder);
		final AttachmentEntry bpartnerAttachmentEntry = attachmentEntryService.createNewAttachment(bpartnerRecord, "bPartnerAttachment", "bPartnerAttachment.data".getBytes());

		//when
		final OrderAttachmentRowsLoader loader = newOrderAttachmentRowsLoader(OrderId.ofRepoId(purchaseOrder.getC_Order_ID()));

		//then
		final OrderAttachmentRows orderAttachmentRows = loader.load();

		assertThat((long)orderAttachmentRows.getAllRows().size()).isEqualTo(1);

		final OrderAttachmentRow orderAttachmentRow = orderAttachmentRows.getAllRows().iterator().next();

		assertThat(orderAttachmentRow.getId().toInt()).isEqualTo(bpartnerAttachmentEntry.getId().getRepoId());
		assertThat(orderAttachmentRow.getIsAttachToPurchaseOrder()).isEqualTo(Boolean.FALSE);
		assertThat(orderAttachmentRow.getPatient().getId()).isEqualTo(bpartnerRecord.getC_BPartner_ID());
		assertThat(TimeUtil.asTimestamp(orderAttachmentRow.getDatePromised())).isEqualTo(salesOrder.getDatePromised());
		assertThat(orderAttachmentRow.getFilename()).isEqualTo(bpartnerAttachmentEntry.getFilename());
		assertThat(orderAttachmentRow.getPayer()).isNull();
		assertThat(orderAttachmentRow.getPharmacy()).isNull();
	}
}

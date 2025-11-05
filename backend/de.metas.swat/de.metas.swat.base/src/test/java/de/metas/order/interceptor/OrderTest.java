package de.metas.order.interceptor;

import de.metas.adempiere.model.I_C_Order;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.BPartnerSupplierApprovalRepository;
import de.metas.bpartner.BPartnerSupplierApprovalService;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.common.util.time.SystemTime;
import de.metas.doctype.CopyDescriptionAndDocumentNote;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.location.impl.DocumentLocationBL;
import de.metas.greeting.GreetingRepository;
import de.metas.money.CurrencyId;
import de.metas.order.impl.OrderLineDetailRepository;
import de.metas.order.model.interceptor.C_Order;
import de.metas.order.paymentschedule.service.OrderPayScheduleService;
import de.metas.shipping.PurchaseOrderToShipperTransportationService;
import de.metas.user.UserGroupRepository;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Incoterms;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.I_M_Shipper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class OrderTest
{
	public static final String PARTNER_NAME_1 = "PartnerName1";
	public static final String ENGLISH = "en_US";
	private static final int TEST_ORG_ID = 0;
	private I_C_Incoterms defaultIncoterm;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		SpringContextHolder.registerJUnitBean(new GreetingRepository());

		final BPartnerBL bpartnerBL = new BPartnerBL(new UserRepository());
		final DocumentLocationBL documentLocationBL = DocumentLocationBL.newInstanceForUnitTesting();
		final OrderLineDetailRepository orderLineDetailRepository = new OrderLineDetailRepository();
		final BPartnerSupplierApprovalService partnerSupplierApprovalService = new BPartnerSupplierApprovalService(new BPartnerSupplierApprovalRepository(), new UserGroupRepository());
		final PurchaseOrderToShipperTransportationService purchaseOrderToShipperTransportationService = PurchaseOrderToShipperTransportationService.newInstanceForUnitTesting();
		final OrderPayScheduleService orderPayScheduleService = OrderPayScheduleService.newInstanceForUnitTesting();
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(new C_Order(bpartnerBL, orderLineDetailRepository, documentLocationBL, partnerSupplierApprovalService, purchaseOrderToShipperTransportationService, orderPayScheduleService));

		defaultIncoterm = newInstance(I_C_Incoterms.class);
		defaultIncoterm.setName("System Default Incoterm");
		defaultIncoterm.setIsDefault(true);
		defaultIncoterm.setAD_Org_ID(TEST_ORG_ID);
		defaultIncoterm.setDefaultLocation("Default City");
		save(defaultIncoterm);
	}

	@Test
	public void updateDescriptionFromDocType()
	{
		final String docTypeName1 = "DocType1";
		final String descriptionDocType1 = "Description DocType1";
		final String documentNoteDocType1 = "Document Note DocType1";

		final I_C_DocType docType1 = createDocType(docTypeName1, descriptionDocType1, documentNoteDocType1);

		final BPartnerLocationId partner1 = createPartnerAndLocation(PARTNER_NAME_1, ENGLISH);

		final I_M_Shipper shipper1 = createShipper();

		final I_C_Order order = createOrder(partner1, shipper1);

		final I_C_PaymentTerm paymentTerm = createPaymentTerm();
		order.setC_PaymentTerm_ID(paymentTerm.getC_PaymentTerm_ID());

		order.setC_DocTypeTarget_ID(docType1.getC_DocType_ID());
		save(order);

		Assertions.assertEquals(descriptionDocType1, order.getDescription());
		Assertions.assertEquals(documentNoteDocType1, order.getDescriptionBottom());

		final String newOrderDescription = "New order description";

		final String newOrderDocumentNote = "New order document note";

		order.setDescription(newOrderDescription);
		order.setDescriptionBottom(newOrderDocumentNote);

		save(order);

		Assertions.assertEquals(newOrderDescription, order.getDescription());
		Assertions.assertEquals(newOrderDocumentNote, order.getDescriptionBottom());

		Services.get(IDocumentBL.class).processEx(order, IDocument.ACTION_Complete);

		Assertions.assertEquals(newOrderDescription, order.getDescription());
		Assertions.assertEquals(newOrderDocumentNote, order.getDescriptionBottom());
	}

	private I_M_Shipper createShipper()
	{
		final I_M_Shipper shipper = newInstance(I_M_Shipper.class);
		shipper.setName("ShipperName");
		save(shipper);
		return shipper;
	}

	private I_C_PaymentTerm createPaymentTerm()
	{
		final I_C_PaymentTerm paymentTerm = newInstance(I_C_PaymentTerm.class);
		paymentTerm.setName("Paymentterm1");
		paymentTerm.setValue("PaymenttermValue1");
		save(paymentTerm);
		return paymentTerm;
	}

	@SuppressWarnings("SameParameterValue")
	private BPartnerLocationId createPartnerAndLocation(final String name, final String language)
	{
		final I_C_BP_Group group = newInstance(I_C_BP_Group.class);
		group.setName("BPGroup");
		save(group);

		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setName(name);
		partner.setAD_Language(language);
		partner.setC_BP_Group(group);

		save(partner);

		final I_C_BPartner_Location location = newInstance(I_C_BPartner_Location.class);
		location.setC_BPartner_ID(partner.getC_BPartner_ID());
		save(location);

		return BPartnerLocationId.ofRepoId(partner.getC_BPartner_ID(), location.getC_BPartner_Location_ID());
	}

	@SuppressWarnings("SameParameterValue")
	private I_C_DocType createDocType(final String name, final String description, final String documentNote)
	{
		final I_C_DocType doctype = newInstance(I_C_DocType.class);
		doctype.setName(name);
		doctype.setDescription(description);
		doctype.setDocumentNote(documentNote);

		doctype.setCopyDescriptionAndDocumentNote(CopyDescriptionAndDocumentNote.CopyDescAndDocumentNote.getCode());

		save(doctype);

		return doctype;
	}

	private I_C_Order createOrder(final BPartnerLocationId bPartnerLocationId, final I_M_Shipper shipper1)
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setC_BPartner_ID(bPartnerLocationId.getBpartnerId().getRepoId());
		order.setC_BPartner_Location_ID(bPartnerLocationId.getRepoId());
		order.setM_Shipper_ID(shipper1.getM_Shipper_ID());
		order.setDatePromised(SystemTime.asTimestamp());
		order.setC_Currency_ID(CurrencyId.EUR.getRepoId());

		save(order);

		return order;
	}

	@Test
	public void testSalesRepSameIdAsBPartner()
	{
		final BPartnerLocationId bPartnerLocationId = createPartnerAndLocation(PARTNER_NAME_1, ENGLISH);
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setC_BPartner_ID(bPartnerLocationId.getBpartnerId().getRepoId());
		order.setC_BPartner_SalesRep_ID(bPartnerLocationId.getBpartnerId().getRepoId());

		Assertions.assertThrows(AdempiereException.class, () -> save(order));
	}

	@Test
	public void testSetIncoterms_fromBPartner()
	{
		final I_C_BP_Group group = newInstance(I_C_BP_Group.class);
		group.setName("BPGroup");
		save(group);

		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setName("PartnerWithIncoterms");
		bpartner.setC_Incoterms_Customer_ID(100);
		bpartner.setIncotermLocation("City");
		bpartner.setC_BP_Group(group);
		save(bpartner);

		final I_C_Order order = newInstance(I_C_Order.class);
		order.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		order.setIsSOTrx(true);

		save(order);

		Assertions.assertEquals(100, order.getC_Incoterms_ID());
		Assertions.assertEquals("City", order.getIncotermLocation());
	}

	@Test
	public void testSetPOIncoterms_fromBPartner()
	{
		final I_C_BP_Group group = newInstance(I_C_BP_Group.class);
		group.setName("BPGroup");
		save(group);

		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setName("PartnerWithIncoterms");
		bpartner.setC_Incoterms_Vendor_ID(100);
		bpartner.setPO_IncotermLocation("City");
		bpartner.setC_BP_Group(group);
		save(bpartner);

		final I_C_Order order = newInstance(I_C_Order.class);
		order.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		order.setIsSOTrx(false);

		save(order);

		Assertions.assertEquals(100, order.getC_Incoterms_ID());
		Assertions.assertEquals("City", order.getIncotermLocation());
	}

	@Test
	public void testSetIncoterms_fallbackToDefault()
	{
		final I_C_BP_Group group = newInstance(I_C_BP_Group.class);
		group.setName("BPGroup");
		save(group);

		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setName("PartnerNoIncoterms");
		bpartner.setC_Incoterms_Customer_ID(0);
		bpartner.setC_BP_Group(group);
		save(bpartner);

		final I_C_Order order = newInstance(I_C_Order.class);
		order.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		order.setAD_Org_ID(TEST_ORG_ID);
		order.setIsSOTrx(true);

		save(order);

		Assertions.assertEquals(defaultIncoterm.getC_Incoterms_ID(), order.getC_Incoterms_ID());
		Assertions.assertEquals("Default City", order.getIncotermLocation());
	}

	@Test
	public void testSetPOIncoterms_fallbackToDefault()
	{
		final I_C_BP_Group group = newInstance(I_C_BP_Group.class);
		group.setName("BPGroup");
		save(group);

		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setName("PartnerNoIncoterms");
		bpartner.setC_Incoterms_Vendor_ID(0);
		bpartner.setC_BP_Group(group);
		save(bpartner);

		final I_C_Order order = newInstance(I_C_Order.class);
		order.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		order.setAD_Org_ID(TEST_ORG_ID);
		order.setIsSOTrx(false);

		save(order);

		Assertions.assertEquals(defaultIncoterm.getC_Incoterms_ID(), order.getC_Incoterms_ID());
		Assertions.assertEquals("Default City", order.getIncotermLocation());
	}

}

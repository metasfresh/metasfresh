package de.metas.order.interceptor;

import de.metas.adempiere.model.I_C_Order;
import de.metas.bpartner.BPartnerSupplierApprovalRepository;
import de.metas.bpartner.BPartnerSupplierApprovalService;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.location.impl.DocumentLocationBL;
import de.metas.order.impl.OrderLineDetailRepository;
import de.metas.order.model.interceptor.C_Order;
import de.metas.project.service.ProjectService;
import de.metas.user.UserGroupRepository;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import de.metas.warehouseassignment.ProductWarehouseAssignmentRepository;
import de.metas.warehouseassignment.ProductWarehouseAssignmentService;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
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

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final BPartnerBL bpartnerBL = new BPartnerBL(new UserRepository());
		final DocumentLocationBL documentLocationBL = new DocumentLocationBL(bpartnerBL);
		final OrderLineDetailRepository orderLineDetailRepository = new OrderLineDetailRepository();
		final BPartnerSupplierApprovalService partnerSupplierApprovalService = new BPartnerSupplierApprovalService(new BPartnerSupplierApprovalRepository(), new UserGroupRepository());
		final ProductWarehouseAssignmentService productWarehouseAssignmentService = new ProductWarehouseAssignmentService(new ProductWarehouseAssignmentRepository());
		final ProjectService projectService = ProjectService.newInstanceForUnitTesting();
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(new C_Order(bpartnerBL, orderLineDetailRepository, documentLocationBL, partnerSupplierApprovalService, productWarehouseAssignmentService, projectService));
	}

	@Test
	public void updateDescriptionFromDocType()
	{

		final String docTypeName1 = "DocType1";
		final String descriptionDocType1 = "Description DocType1";
		final String documentNoteDocType1 = "Document Note DocType1";

		final I_C_DocType docType1 = createDocType(docTypeName1, descriptionDocType1, documentNoteDocType1);

		final I_C_BPartner partner1 = createPartner(PARTNER_NAME_1, ENGLISH);

		final I_C_Order order = createOrder(partner1);

		order.setC_DocTypeTarget_ID(docType1.getC_DocType_ID());
		save(order);

		Assertions.assertEquals(order.getDescription(), descriptionDocType1);
		Assertions.assertEquals(order.getDescriptionBottom(), documentNoteDocType1);

		final String newOrderDescription = "New order description";

		final String newOrderDocumentNote = "New order document note";

		order.setDescription(newOrderDescription);
		order.setDescriptionBottom(newOrderDocumentNote);

		save(order);

		Assertions.assertEquals(order.getDescription(), newOrderDescription);
		Assertions.assertEquals(order.getDescriptionBottom(), newOrderDocumentNote);

		Services.get(IDocumentBL.class).processEx(order, IDocument.ACTION_Complete);

		Assertions.assertEquals(order.getDescription(), newOrderDescription);
		Assertions.assertEquals(order.getDescriptionBottom(), newOrderDocumentNote);
	}

	@SuppressWarnings("SameParameterValue")
	private I_C_BPartner createPartner(final String name, final String language)
	{
		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setName(name);
		partner.setAD_Language(language);

		save(partner);

		return partner;
	}

	@SuppressWarnings("SameParameterValue")
	private I_C_DocType createDocType(final String name, final String description, final String documentNote)
	{
		final I_C_DocType doctype = newInstance(I_C_DocType.class);
		doctype.setName(name);
		doctype.setDescription(description);
		doctype.setDocumentNote(documentNote);

		doctype.setIsCopyDescriptionToDocument(true);

		save(doctype);

		return doctype;
	}

	private I_C_Order createOrder(final I_C_BPartner partner)
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setC_BPartner_ID(partner.getC_BPartner_ID());

		save(order);

		return order;
	}

	@Test
	public void testSalesRepSameIdAsBPartner()
	{
		final I_C_BPartner partner = createPartner(PARTNER_NAME_1, ENGLISH);
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setC_BPartner_ID(partner.getC_BPartner_ID());
		order.setC_BPartner_SalesRep_ID(partner.getC_BPartner_ID());

		Assertions.assertThrows(AdempiereException.class, () -> save(order));
	}

}

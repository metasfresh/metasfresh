package de.metas.order.interceptor;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.junit.Assert.assertTrue;

import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.junit.Before;
import org.junit.Test;

import de.metas.adempiere.model.I_C_Order;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.order.model.interceptor.C_Order;
import de.metas.util.Services;

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
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(C_Order.INSTANCE);
	}

	@Test
	public void updateDescriptionFromDocType()
	{
		final String partnerName1 = "PartnerName1";
		final String english = "en_US";

		final String docTypeName1 = "DocType1";
		final String descriptionDocType1 = "Description DocType1";
		final String documentNoteDocType1 = "Document Note DocType1";

		final I_C_DocType docType1 = createDocType(docTypeName1, descriptionDocType1, documentNoteDocType1);

		final I_C_BPartner partner1 = createPartner(partnerName1, english);

		final I_C_Order order = createOrder(partner1);

		order.setC_DocTypeTarget_ID(docType1.getC_DocType_ID());
		save(order);

		assertTrue(descriptionDocType1.equals(order.getDescription()));
		assertTrue(documentNoteDocType1.equals(order.getDescriptionBottom()));

		final String newOrderDescription = "New order description";

		final String newOrderDocumentNote = "New order document note";

		order.setDescription(newOrderDescription);
		order.setDescriptionBottom(newOrderDocumentNote);

		save(order);

		assertTrue(newOrderDescription.equals(order.getDescription()));
		assertTrue(newOrderDocumentNote.equals(order.getDescriptionBottom()));

		Services.get(IDocumentBL.class).processEx(order, IDocument.ACTION_Complete);

		assertTrue(newOrderDescription.equals(order.getDescription()));
		assertTrue(newOrderDocumentNote.equals(order.getDescriptionBottom()));
	}

	private I_C_BPartner createPartner(final String name, final String language)
	{
		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setName(name);
		partner.setAD_Language(language);

		save(partner);

		return partner;
	}

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
}

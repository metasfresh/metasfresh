package de.metas.dunning.invoice;

import de.metas.dunning.DunningDocId;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static de.metas.dunning.invoice.DunningDocTestFixtures.addInvoiceLine;
import static de.metas.dunning.invoice.DunningDocTestFixtures.addLine;
import static de.metas.dunning.invoice.DunningDocTestFixtures.createDunningDoc;
import static de.metas.dunning.invoice.DunningDocTestFixtures.createInvoice;
import static org.adempiere.model.InterfaceWrapperHelper.getModelTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.dunning
 * %%
 * Copyright (C) 2026 metas GmbH
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

class DunningServiceTest
{
	private DunningService dunningService;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		dunningService = new DunningService();
	}

	@Test
	void retrieveDunnedInvoices_returnsInvoicesOfTheGivenDunningDocOnly()
	{
		final I_C_Invoice invoice1 = createInvoice();
		final I_C_Invoice invoice2 = createInvoice();
		final I_C_Invoice invoiceOfOtherDunningDoc = createInvoice();

		final I_C_DunningDoc dunningDoc = createDunningDoc();
		addInvoiceLine(dunningDoc, invoice1);
		addInvoiceLine(dunningDoc, invoice2);

		final I_C_DunningDoc otherDunningDoc = createDunningDoc();
		addInvoiceLine(otherDunningDoc, invoiceOfOtherDunningDoc);

		assertThat(dunningService.retrieveDunnedInvoices(DunningDocId.ofRepoId(dunningDoc.getC_DunningDoc_ID())))
				.extracting(I_C_Invoice::getC_Invoice_ID)
				.containsExactlyInAnyOrder(invoice1.getC_Invoice_ID(), invoice2.getC_Invoice_ID());
	}

	@Test
	void retrieveDunnedInvoices_areOrderedByDateInvoiced_thenDocumentNo_thenId()
	{
		// ids ascend in creation order, which deliberately differs from the expected order
		final I_C_Invoice latest = createInvoice(LocalDate.of(2026, 3, 1), "1000");
		final I_C_Invoice sameDateHigherDocumentNo = createInvoice(LocalDate.of(2026, 2, 1), "B-2");
		final I_C_Invoice earliest = createInvoice(LocalDate.of(2026, 1, 1), "9999");
		final I_C_Invoice sameDateLowerDocumentNo = createInvoice(LocalDate.of(2026, 2, 1), "A-1");
		final I_C_Invoice sameDateAndDocumentNoLowerId = createInvoice(LocalDate.of(2026, 2, 15), "X-1");
		final I_C_Invoice sameDateAndDocumentNoHigherId = createInvoice(LocalDate.of(2026, 2, 15), "X-1");

		// the order of the dunning doc lines must not matter
		final I_C_DunningDoc dunningDoc = createDunningDoc();
		addInvoiceLine(dunningDoc, sameDateAndDocumentNoHigherId);
		addInvoiceLine(dunningDoc, latest);
		addInvoiceLine(dunningDoc, sameDateHigherDocumentNo);
		addInvoiceLine(dunningDoc, sameDateLowerDocumentNo);
		addInvoiceLine(dunningDoc, earliest);
		addInvoiceLine(dunningDoc, sameDateAndDocumentNoLowerId);

		assertThat(dunningService.retrieveDunnedInvoices(DunningDocId.ofRepoId(dunningDoc.getC_DunningDoc_ID())))
				.extracting(I_C_Invoice::getC_Invoice_ID)
				.containsExactly(
						earliest.getC_Invoice_ID(),
						sameDateLowerDocumentNo.getC_Invoice_ID(),
						sameDateHigherDocumentNo.getC_Invoice_ID(),
						sameDateAndDocumentNoLowerId.getC_Invoice_ID(),
						sameDateAndDocumentNoHigherId.getC_Invoice_ID(),
						latest.getC_Invoice_ID());
	}

	@Test
	void retrieveDunnedInvoices_ignoresCandidatesOfOtherTables()
	{
		final I_C_Invoice invoice = createInvoice();
		final I_C_Invoice invoiceWhoseIdIsReferencedFromAnotherTable = createInvoice();
		final I_C_BPartner notAnInvoice = newInstance(I_C_BPartner.class);
		saveRecord(notAnInvoice);

		final I_C_DunningDoc dunningDoc = createDunningDoc();
		addInvoiceLine(dunningDoc, invoice);

		// a candidate that is part of the same dunning doc but references a record of another table
		final I_C_Dunning_Candidate otherTableCandidate = newInstance(I_C_Dunning_Candidate.class);
		otherTableCandidate.setAD_Table_ID(getModelTableId(notAnInvoice));
		// same Record_ID as an existing invoice, but the candidate's AD_Table_ID says it is not an invoice
		otherTableCandidate.setRecord_ID(invoiceWhoseIdIsReferencedFromAnotherTable.getC_Invoice_ID());
		saveRecord(otherTableCandidate);
		addLine(dunningDoc, otherTableCandidate);

		assertThat(dunningService.retrieveDunnedInvoices(DunningDocId.ofRepoId(dunningDoc.getC_DunningDoc_ID())))
				.extracting(I_C_Invoice::getC_Invoice_ID)
				.containsExactly(invoice.getC_Invoice_ID());
	}

	@Test
	void retrieveDunnedInvoices_dunningDocWithoutLines_returnsEmpty()
	{
		final I_C_DunningDoc dunningDoc = createDunningDoc();

		assertThat(dunningService.retrieveDunnedInvoices(DunningDocId.ofRepoId(dunningDoc.getC_DunningDoc_ID()))).isEmpty();
	}
}

package de.metas.contracts.subscription.order.restart;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
import de.metas.util.RelatedRecordsProvider.SourceRecordsKey;

/*
 * #%L
 * de.metas.contracts
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

public class RelatedInvoicesForSubscriptionProviderTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void provideRelatedRecords()
	{
		final I_C_Flatrate_Term flatrateTermRecord = newInstance(I_C_Flatrate_Term.class);
		saveRecord(flatrateTermRecord);

		final I_C_Invoice_Candidate invoiceCandidateRecord = newInstance(I_C_Invoice_Candidate.class);
		invoiceCandidateRecord.setAD_Table_ID(getTableId(I_C_Flatrate_Term.class));
		invoiceCandidateRecord.setRecord_ID(flatrateTermRecord.getC_Flatrate_Term_ID());
		saveRecord(invoiceCandidateRecord);

		final I_C_Invoice invoiceRecord = newInstance(I_C_Invoice.class);
		saveRecord(invoiceRecord);

		final I_C_InvoiceLine invoiceLineRecord = newInstance(I_C_InvoiceLine.class);
		invoiceLineRecord.setC_Invoice(invoiceRecord);
		saveRecord(invoiceLineRecord);

		final I_C_Invoice_Line_Alloc ilaRecord = newInstance(I_C_Invoice_Line_Alloc.class);
		ilaRecord.setC_Invoice_Candidate(invoiceCandidateRecord);
		ilaRecord.setC_InvoiceLine(invoiceLineRecord);
		saveRecord(ilaRecord);

		final TableRecordReference input = TableRecordReference.of(flatrateTermRecord);

		// invoke the method under test
		final IPair<SourceRecordsKey, List<ITableRecordReference>> result = new RelatedInvoicesForSubscriptionsProvider().provideRelatedRecords(ImmutableList.of(input));

		final List<ITableRecordReference> resultRecords = result.getRight();
		assertThat(resultRecords).containsOnly(TableRecordReference.of(invoiceRecord));
	}

}

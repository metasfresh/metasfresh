/*
 * #%L
 * de.metas.swat.base
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

package de.metas.invoicecandidate.modelvalidator;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Recompute;
import org.adempiere.ad.dao.IQueryBL;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Order_CompensationGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies {@link C_OrderLine#syncInvoiceCandidateGroupReference}.
 */
class C_OrderLine_Test
{
	private C_OrderLine interceptor;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		interceptor = C_OrderLine.newInstanceForUnitTesting();
	}

	private I_C_OrderLine newOrderLineWithInvoiceCandidate()
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		saveRecord(order);

		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setC_Order_ID(order.getC_Order_ID());
		saveRecord(orderLine);

		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class);
		ic.setC_Order_ID(order.getC_Order_ID());
		ic.setAD_Table_ID(TableRecordReference.of(orderLine).getAD_Table_ID());
		ic.setRecord_ID(orderLine.getC_OrderLine_ID());
		saveRecord(ic);

		return orderLine;
	}

	@Test
	void regrouping_syncsNotProcessedCandidate()
	{
		final I_C_OrderLine orderLine = newOrderLineWithInvoiceCandidate();
		final I_C_Invoice_Candidate ic = soleInvoiceCandidateFor(orderLine);
		assertThat(ic.getC_Order_CompensationGroup_ID()).isLessThanOrEqualTo(0);

		orderLine.setC_Order_CompensationGroup_ID(1000042);
		saveRecord(orderLine);
		interceptor.syncInvoiceCandidateGroupReference(orderLine);

		assertThat(soleInvoiceCandidateFor(orderLine).getC_Order_CompensationGroup_ID()).isEqualTo(1000042);
	}

	@Test
	void ungrouping_clearsNotProcessedCandidate()
	{
		final I_C_OrderLine orderLine = newOrderLineWithInvoiceCandidate();
		orderLine.setC_Order_CompensationGroup_ID(1000042);
		saveRecord(orderLine);
		interceptor.syncInvoiceCandidateGroupReference(orderLine);
		assertThat(soleInvoiceCandidateFor(orderLine).getC_Order_CompensationGroup_ID()).isEqualTo(1000042);

		// ungroup, as OrderGroupRepository.destroyGroup/setGroupIdToLines does
		orderLine.setC_Order_CompensationGroup_ID(-1);
		saveRecord(orderLine);
		interceptor.syncInvoiceCandidateGroupReference(orderLine);

		assertThat(soleInvoiceCandidateFor(orderLine).getC_Order_CompensationGroup_ID()).isEqualTo(-1);
	}

	@Test
	void invoicedCandidate_isNeverTouched()
	{
		final I_C_OrderLine orderLine = newOrderLineWithInvoiceCandidate();
		final I_C_Invoice_Candidate ic = soleInvoiceCandidateFor(orderLine);
		ic.setProcessed(true);
		ic.setQtyInvoiced(BigDecimal.ONE);
		saveRecord(ic);

		orderLine.setC_Order_CompensationGroup_ID(1000042);
		saveRecord(orderLine);
		interceptor.syncInvoiceCandidateGroupReference(orderLine);

		// still whatever it was before -- the (already invoiced) candidate's group membership is history
		assertThat(soleInvoiceCandidateFor(orderLine).getC_Order_CompensationGroup_ID()).isLessThanOrEqualTo(0);
	}

	/**
	 * Reactivating an order closes its candidates, and completing it again regroups its order lines before it reopens them.
	 */
	@Test
	void closedButNotInvoicedCandidate_isSyncedIntoContractGroup()
	{
		final I_C_OrderLine orderLine = newOrderLineWithInvoiceCandidate();
		closeInvoiceCandidate(orderLine);
		final I_C_Order_CompensationGroup groupHeader = newGroupHeader(orderLine, 1000001);

		orderLine.setC_Order_CompensationGroup_ID(groupHeader.getC_Order_CompensationGroup_ID());
		saveRecord(orderLine);
		interceptor.syncInvoiceCandidateGroupReference(orderLine);

		assertThat(soleInvoiceCandidateFor(orderLine).getC_Order_CompensationGroup_ID()).isEqualTo(groupHeader.getC_Order_CompensationGroup_ID());
	}

	@Test
	void closedButNotInvoicedCandidate_isUngroupedFromContractGroup()
	{
		final I_C_OrderLine orderLine = newOrderLineWithInvoiceCandidate();
		final I_C_Order_CompensationGroup groupHeader = newGroupHeader(orderLine, 1000001);
		orderLine.setC_Order_CompensationGroup_ID(groupHeader.getC_Order_CompensationGroup_ID());
		saveRecord(orderLine);
		interceptor.syncInvoiceCandidateGroupReference(orderLine);
		closeInvoiceCandidate(orderLine);

		orderLine.setC_Order_CompensationGroup_ID(-1);
		saveRecord(orderLine);
		interceptor.syncInvoiceCandidateGroupReference(orderLine);

		assertThat(soleInvoiceCandidateFor(orderLine).getC_Order_CompensationGroup_ID()).isEqualTo(-1);
	}

	@Test
	void closedButNotInvoicedCandidate_isNotTouchedByNonContractGroup()
	{
		final I_C_OrderLine orderLine = newOrderLineWithInvoiceCandidate();
		closeInvoiceCandidate(orderLine);
		final I_C_Order_CompensationGroup groupHeader = newGroupHeader(orderLine, -1);

		orderLine.setC_Order_CompensationGroup_ID(groupHeader.getC_Order_CompensationGroup_ID());
		saveRecord(orderLine);
		interceptor.syncInvoiceCandidateGroupReference(orderLine);

		assertThat(soleInvoiceCandidateFor(orderLine).getC_Order_CompensationGroup_ID()).isLessThanOrEqualTo(0);
	}

	/**
	 * A regrouped candidate is recomputed by the invoice candidate update process, so that whatever depends on its group
	 * (e.g. whether it is the discount line of a contract-created group, which is no refund base) follows the new group.
	 */
	@Test
	void regrouping_invalidatesTheMovedCandidate()
	{
		final I_C_OrderLine orderLine = newOrderLineWithInvoiceCandidate();
		final I_C_Invoice_Candidate ic = soleInvoiceCandidateFor(orderLine);
		ic.setIsGroupCompensationLine(true); // a fixed-amount discount line: the group handler invalidates nothing for it
		saveRecord(ic);
		final I_C_Order_CompensationGroup contractGroup = newGroupHeader(orderLine, 1000099);

		orderLine.setC_Order_CompensationGroup_ID(contractGroup.getC_Order_CompensationGroup_ID());
		saveRecord(orderLine);
		interceptor.syncInvoiceCandidateGroupReference(orderLine);

		assertThat(Services.get(IQueryBL.class).createQueryBuilder(I_C_Invoice_Candidate_Recompute.class)
				.create()
				.listDistinct(I_C_Invoice_Candidate_Recompute.COLUMNNAME_C_Invoice_Candidate_ID, Integer.class))
				.contains(ic.getC_Invoice_Candidate_ID());
	}

	private static void closeInvoiceCandidate(final I_C_OrderLine orderLine)
	{
		final I_C_Invoice_Candidate ic = soleInvoiceCandidateFor(orderLine);
		ic.setProcessed(true);
		saveRecord(ic);
	}

	private static I_C_Order_CompensationGroup newGroupHeader(final I_C_OrderLine orderLine, final int flatrateTermId)
	{
		final I_C_Order_CompensationGroup groupHeader = newInstance(I_C_Order_CompensationGroup.class);
		groupHeader.setC_Order_ID(orderLine.getC_Order_ID());
		groupHeader.setC_Flatrate_Term_ID(flatrateTermId);
		saveRecord(groupHeader);
		return groupHeader;
	}

	private static I_C_Invoice_Candidate soleInvoiceCandidateFor(final I_C_OrderLine orderLine)
	{
		final List<I_C_Invoice_Candidate> ics = Services.get(IInvoiceCandDAO.class)
				.retrieveReferencing(TableRecordReference.of(orderLine));
		assertThat(ics).hasSize(1);
		return ics.get(0);
	}
}

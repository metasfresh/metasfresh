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

package de.metas.invoicecandidate.compensationGroup;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Recompute;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequestFactory;
import de.metas.order.compensationGroup.GroupTemplateRepository;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Order_CompensationGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

class InvoiceCandidateGroupCompensationChangesHandlerTest
{
	private InvoiceCandidateGroupCompensationChangesHandler handler;
	private I_C_Order order;
	private int orderCompensationGroupId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		order = newInstance(I_C_Order.class);
		order.setDocumentNo("order"); // no generated document number, no log output about it
		saveRecord(order);

		final I_C_Order_CompensationGroup groupHeader = newInstance(I_C_Order_CompensationGroup.class);
		groupHeader.setC_Order_ID(order.getC_Order_ID());
		saveRecord(groupHeader);
		orderCompensationGroupId = groupHeader.getC_Order_CompensationGroup_ID();

		handler = InvoiceCandidateGroupCompensationChangesHandler.builder()
				.groupsRepo(new InvoiceCandidateGroupRepository(Mockito.mock(GroupCompensationLineCreateRequestFactory.class), new GroupTemplateRepository(Optional.empty()), Optional.empty()))
				.build();
	}

	private I_C_Invoice_Candidate createCandidate(final boolean compensationLine, final String amtType, final boolean processed)
	{
		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class);
		ic.setC_Order_ID(order.getC_Order_ID());
		ic.setC_Order_CompensationGroup_ID(orderCompensationGroupId);
		ic.setIsGroupCompensationLine(compensationLine);
		ic.setGroupCompensationAmtType(amtType);
		ic.setProcessed(processed);
		saveRecord(ic);
		return ic;
	}

	private static List<Integer> invalidatedCandidateIds()
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_Invoice_Candidate_Recompute.class)
				.create()
				.listDistinct(I_C_Invoice_Candidate_Recompute.COLUMNNAME_C_Invoice_Candidate_ID, Integer.class);
	}

	/**
	 * The percent discount is repriced from the goods still to invoice, so it must be recomputed when goods get processed;
	 * a fixed-amount compensation line does not depend on the goods.
	 */
	@Test
	void processedGoods_invalidatesPercentButNotFixedAmountCompensationLines()
	{
		final I_C_Invoice_Candidate goods = createCandidate(false, null, true);
		final I_C_Invoice_Candidate percentDiscount = createCandidate(true, X_C_Invoice_Candidate.GROUPCOMPENSATIONAMTTYPE_Percent, false);
		final I_C_Invoice_Candidate fixedDiscount = createCandidate(true, X_C_Invoice_Candidate.GROUPCOMPENSATIONAMTTYPE_PriceAndQty, false);

		handler.onInvoiceCandidateChanged(goods);

		assertThat(invalidatedCandidateIds())
				.contains(percentDiscount.getC_Invoice_Candidate_ID())
				.doesNotContain(fixedDiscount.getC_Invoice_Candidate_ID(), goods.getC_Invoice_Candidate_ID());
	}

	@Test
	void notProcessedGoods_invalidatesAllCompensationLines()
	{
		final I_C_Invoice_Candidate goods = createCandidate(false, null, false);
		final I_C_Invoice_Candidate percentDiscount = createCandidate(true, X_C_Invoice_Candidate.GROUPCOMPENSATIONAMTTYPE_Percent, false);
		final I_C_Invoice_Candidate fixedDiscount = createCandidate(true, X_C_Invoice_Candidate.GROUPCOMPENSATIONAMTTYPE_PriceAndQty, false);

		handler.onInvoiceCandidateChanged(goods);

		assertThat(invalidatedCandidateIds())
				.contains(percentDiscount.getC_Invoice_Candidate_ID(), fixedDiscount.getC_Invoice_Candidate_ID());
	}
}

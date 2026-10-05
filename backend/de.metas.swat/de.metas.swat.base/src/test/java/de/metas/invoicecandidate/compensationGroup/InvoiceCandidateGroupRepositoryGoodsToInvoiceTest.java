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
import de.metas.order.OrderId;
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequestFactory;
import de.metas.order.compensationGroup.GroupId;
import de.metas.order.compensationGroup.OrderGroupRepository;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Order_CompensationGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.function.Consumer;

import static java.math.BigDecimal.ONE;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * The goods to invoice now that a percent discount goes with leave out the goods the invoicing skips (see {@code InvoiceCandBL#getInvoicingSkipReasonOrNull}).
 */
class InvoiceCandidateGroupRepositoryGoodsToInvoiceTest
{
	private InvoiceCandidateGroupRepository repo;
	private I_C_Order order;
	private GroupId groupId;

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
		groupId = OrderGroupRepository.createGroupId(OrderId.ofRepoId(order.getC_Order_ID()), groupHeader.getC_Order_CompensationGroup_ID());

		repo = new InvoiceCandidateGroupRepository(Mockito.mock(GroupCompensationLineCreateRequestFactory.class), OrderGroupRepository.newInstanceForUnitTesting());
	}

	private I_C_Invoice_Candidate createGoodsToInvoice(final Consumer<I_C_Invoice_Candidate> customizer)
	{
		final I_C_Invoice_Candidate goods = newInstance(I_C_Invoice_Candidate.class);
		goods.setC_Order_ID(order.getC_Order_ID());
		goods.setC_Order_CompensationGroup_ID(groupId.getOrderCompensationGroupId());
		goods.setQtyOrdered(ONE);
		goods.setQtyToInvoice(ONE);
		customizer.accept(goods);
		saveRecord(goods);
		return goods;
	}

	@Test
	void goodsToInvoice_counted()
	{
		final I_C_Invoice_Candidate goods = createGoodsToInvoice(ic -> {});

		assertThat(repo.hasRegularInvoiceCandidatesToInvoice(groupId)).isTrue();
		assertThat(repo.retrieveFirstRegularInvoiceCandidateToInvoice(groupId)).map(I_C_Invoice_Candidate::getC_Invoice_Candidate_ID).contains(goods.getC_Invoice_Candidate_ID());
	}

	@Test
	void goodsToClear_notCounted()
	{
		createGoodsToInvoice(ic -> ic.setIsToClear(true));

		assertThat(repo.hasRegularInvoiceCandidatesToInvoice(groupId)).isFalse();
	}

	@Test
	void simulationGoods_notCounted()
	{
		createGoodsToInvoice(ic -> ic.setIsSimulation(true));

		assertThat(repo.hasRegularInvoiceCandidatesToInvoice(groupId)).isFalse();
	}

	@Test
	void goodsInDisputeOrInError_notTheOnesToAlignDatesWith()
	{
		createGoodsToInvoice(ic -> ic.setIsInDispute(true));
		createGoodsToInvoice(ic -> ic.setIsError(true));
		final I_C_Invoice_Candidate goods = createGoodsToInvoice(ic -> {});

		assertThat(repo.retrieveFirstRegularInvoiceCandidateToInvoice(groupId)).map(I_C_Invoice_Candidate::getC_Invoice_Candidate_ID).contains(goods.getC_Invoice_Candidate_ID());
	}
}

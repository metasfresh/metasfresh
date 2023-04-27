/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.commission.mediated.approvedforinvoice;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionShareId;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerDocumentId;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.mediatedorder.MediatedOrderLineDocId;
import de.metas.contracts.commission.commissioninstance.services.repos.CommissionInstanceRepository;
import de.metas.contracts.commission.commissioninstance.services.repos.CommissionSettlementShareRepository;
import de.metas.contracts.commission.model.I_C_Commission_Instance;
import de.metas.contracts.commission.model.I_C_Commission_Share;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.approvedforinvoice.IApprovedForInvoicingFinder;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class MediatedCommissionDependent implements IApprovedForInvoicingFinder
{
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

	@NonNull
	private final CommissionInstanceRepository commissionInstanceRepository;
	@NonNull
	private final CommissionSettlementShareRepository commissionSettlementShareRepository;

	public MediatedCommissionDependent(
			@NonNull final CommissionInstanceRepository commissionInstanceRepository,
			@NonNull final CommissionSettlementShareRepository commissionSettlementShareRepository)
	{
		this.commissionInstanceRepository = commissionInstanceRepository;
		this.commissionSettlementShareRepository = commissionSettlementShareRepository;
	}

	@Override
	public @NonNull String getTableName()
	{
		return I_C_Order.Table_Name;
	}

	@Override
	public @NonNull List<I_C_Invoice_Candidate> findApprovedForReference(final @NonNull TableRecordReference recordReference)
	{
		final OrderId orderId = recordReference.getIdAssumingTableName(getTableName(), OrderId::ofRepoId);

		final I_C_Order order = orderDAO.getById(orderId);

		if (!orderBL.isMediated(order))
		{
			return ImmutableList.of();
		}

		final IQuery<I_C_Commission_Instance> documentBasedCommissionInstanceQuery = commissionInstanceRepository
				.computeQueryForTriggerDocumentIds(getTriggerDocumentIdCandidates(orderId));

		final Set<CommissionShareId> commissionShareIdSet = commissionSettlementShareRepository.retrieveShareIdsForInstanceQuery(documentBasedCommissionInstanceQuery);

		final TableRecordReferenceSet commissionShareReferences = commissionShareIdSet
				.stream()
				.map(commissionShareId -> TableRecordReference.of(I_C_Commission_Share.Table_Name, commissionShareId))
				.collect(TableRecordReferenceSet.collect());

		return invoiceCandDAO.retrieveApprovedForInvoiceReferencing(commissionShareReferences);
	}

	@NonNull
	private Set<CommissionTriggerDocumentId> getTriggerDocumentIdCandidates(@NonNull final OrderId orderId)
	{
		return orderDAO.retrieveOrderLines(orderId)
				.stream()
				.map(I_C_OrderLine::getC_OrderLine_ID)
				.map(OrderLineId::ofRepoId)
				.map(MediatedOrderLineDocId::new)
				.collect(ImmutableSet.toImmutableSet());
	}
}

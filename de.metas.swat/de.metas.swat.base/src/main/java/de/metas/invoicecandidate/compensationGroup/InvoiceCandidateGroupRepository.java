package de.metas.invoicecandidate.compensationGroup;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Component;

import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.IOrderBL;
import de.metas.order.compensationGroup.Group;
import de.metas.order.compensationGroup.Group.GroupBuilder;
import de.metas.order.compensationGroup.GroupCompensationAmtType;
import de.metas.order.compensationGroup.GroupCompensationLine;
import de.metas.order.compensationGroup.GroupCompensationType;
import de.metas.order.compensationGroup.GroupId;
import de.metas.order.compensationGroup.GroupRegularLine;
import de.metas.order.compensationGroup.GroupRepository;
import de.metas.order.compensationGroup.OrderGroupRepository;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Component
public class InvoiceCandidateGroupRepository implements GroupRepository
{
	private final transient IOrderBL orderBL = Services.get(IOrderBL.class);
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public Group retrieveGroup(final GroupId groupId)
	{
		final List<I_C_Invoice_Candidate> invoiceCandidates = retrieveInvoiceCandidatesForGroup(groupId);
		if (invoiceCandidates.isEmpty())
		{
			throw new AdempiereException("No invoice candidates found for " + groupId);
		}

		final Group group = createGroupFromInvoiceCandidates(invoiceCandidates);
		if (!group.getGroupId().equals(groupId))
		{
			// shall not happen
			throw new AdempiereException("Invalid groupId for group " + group)
					.setParameter("expectedGroupId", groupId)
					.appendParametersToMessage();
		}
		return group;

	}

	private Group createGroupFromInvoiceCandidates(final List<I_C_Invoice_Candidate> invoiceCandidates)
	{
		final GroupId groupId = extractSingleGroupId(invoiceCandidates);

		final I_C_Order order = invoiceCandidates.get(0).getC_Order();
		final int precision = orderBL.getPrecision(order);

		final GroupBuilder groupBuilder = Group.builder()
				.groupId(groupId)
				.precision(precision)
				.bpartnerId(order.getC_BPartner_ID())
				.isSOTrx(order.isSOTrx());

		for (final I_C_Invoice_Candidate invoiceCandidate : invoiceCandidates)
		{
			if (!invoiceCandidate.isGroupCompensationLine())
			{
				final GroupRegularLine regularLine = createReqularLine(invoiceCandidate);
				groupBuilder.regularLine(regularLine);
			}
			else
			{
				final GroupCompensationLine compensationLine = createCompensationLine(invoiceCandidate);
				groupBuilder.compensationLine(compensationLine);
			}
		}

		return groupBuilder.build();
	}

	private GroupRegularLine createReqularLine(final I_C_Invoice_Candidate invoiceCandidate)
	{
		return GroupRegularLine.builder()
				.lineNetAmt(invoiceCandidate.getNetAmtToInvoice())
				.build();
	}

	/**
	 * note to dev: keep in sync with {@link #updateInvoiceCandidateFromCompensationLine(I_C_Invoice_Candidate, GroupCompensationLine, GroupId)}
	 */
	private GroupCompensationLine createCompensationLine(final I_C_Invoice_Candidate invoiceCandidate)
	{
		final BigDecimal price = invoiceCandidate.getPriceEntered();
		final BigDecimal qty = invoiceCandidate.getQtyToInvoice();
		final BigDecimal lineNetAmt = price.multiply(qty);

		return GroupCompensationLine.builder()
				.repoId(extractLineId(invoiceCandidate))
				.seqNo(invoiceCandidate.getLine())
				.productId(invoiceCandidate.getM_Product_ID())
				.uomId(invoiceCandidate.getC_UOM_ID())
				.type(GroupCompensationType.ofAD_Ref_List_Value(invoiceCandidate.getGroupCompensationType()))
				.amtType(GroupCompensationAmtType.ofAD_Ref_List_Value(invoiceCandidate.getGroupCompensationAmtType()))
				.percentage(invoiceCandidate.getGroupCompensationPercentage())
				.baseAmt(invoiceCandidate.getGroupCompensationBaseAmt())
				.price(price)
				.qty(qty)
				.lineNetAmt(lineNetAmt)
				.build();
	}

	public int extractLineId(final I_C_Invoice_Candidate invoiceCandidate)
	{
		return invoiceCandidate.getC_Invoice_Candidate_ID();
	}

	/**
	 * note to dev: keep in sync with {@link #createCompensationLine(I_C_Invoice_Candidate)}
	 */
	private void updateInvoiceCandidateFromCompensationLine(final I_C_Invoice_Candidate invoiceCandidate, final GroupCompensationLine compensationLine, final GroupId groupId)
	{
		invoiceCandidate.setGroupCompensationBaseAmt(compensationLine.getBaseAmt());

		invoiceCandidate.setQtyToInvoice(compensationLine.getQty());
		invoiceCandidate.setPriceEntered(compensationLine.getPrice());
		invoiceCandidate.setPriceActual(compensationLine.getPrice());
	}

	private GroupId extractSingleGroupId(final List<I_C_Invoice_Candidate> invoiceCandidates)
	{
		Check.assumeNotEmpty(invoiceCandidates, "orderLines is not empty");
		return invoiceCandidates.stream()
				.map(this::extractGroupId)
				.distinct()
				.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("Invoice candidates are not part of the same group: " + invoiceCandidates)));
	}

	public GroupId extractGroupId(final I_C_Invoice_Candidate invoiceCandidate)
	{
		InvoiceCandidateCompensationGroupUtils.assertInGroup(invoiceCandidate);
		return GroupId.of(I_C_Order.Table_Name, invoiceCandidate.getC_Order_ID(), invoiceCandidate.getC_Order_CompensationGroup_ID());
	}

	@Override
	public void saveGroup(@NonNull final Group group)
	{
		final GroupId groupId = group.getGroupId();
		final InvoiceCandidatesStorage invoiceCandidatesStorage = retrieveStorage(groupId);
		saveGroup(group, invoiceCandidatesStorage);
	}

	public void saveGroup(final Group group, final InvoiceCandidatesStorage invoiceCandidatesStorage)
	{
		final GroupId groupId = group.getGroupId();

		// Save compensation lines
		for (final GroupCompensationLine compensationLine : group.getCompensationLines())
		{
			final int invoiceCandidateId = compensationLine.getRepoId();
			final I_C_Invoice_Candidate invoiceCandidate = invoiceCandidatesStorage.getByIdIfPresent(invoiceCandidateId);
			if (invoiceCandidate == null)
			{
				// shall not happen
				throw new AdempiereException("No invoice candidate found for compensation line: " + compensationLine);
			}

			updateInvoiceCandidateFromCompensationLine(invoiceCandidate, compensationLine, groupId);
			invoiceCandidatesStorage.save(invoiceCandidate);
		}
	}

	@Override
	public Group retrieveOrCreateGroup(final RetrieveOrCreateGroupRequest request)
	{
		throw new UnsupportedOperationException();
	}

	private List<I_C_Invoice_Candidate> retrieveInvoiceCandidatesForGroup(final GroupId groupId)
	{
		return retrieveInvoiceCandidatesForGroupQuery(groupId).create().list(I_C_Invoice_Candidate.class);
	}

	private IQueryBuilder<I_C_Invoice_Candidate> retrieveInvoiceCandidatesForGroupQuery(final GroupId groupId)
	{
		final int orderId = OrderGroupRepository.extractOrderIdFromGroupId(groupId);
		final int orderCompensationGroupId = groupId.getOrderCompensationGroupId();

		return queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_C_Order_ID, orderId)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_C_Order_CompensationGroup_ID, orderCompensationGroupId);
	}

	private InvoiceCandidatesStorage retrieveStorage(final GroupId groupId)
	{
		final List<I_C_Invoice_Candidate> invoiceCandidates = retrieveInvoiceCandidatesForGroupQuery(groupId)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_IsGroupCompensationLine, true)
				.create()
				.list(I_C_Invoice_Candidate.class);

		return InvoiceCandidatesStorage.builder()
				.groupId(groupId)
				.invoiceCandidates(invoiceCandidates)
				.performDatabaseChanges(true)
				.build();
	}

	public Group createPartialGroupFromCompensationLine(@NonNull final I_C_Invoice_Candidate invoiceCandidate)
	{
		InvoiceCandidateCompensationGroupUtils.assertCompensationLine(invoiceCandidate);

		final GroupCompensationLine compensationLine = createCompensationLine(invoiceCandidate);
		final GroupRegularLine aggregatedRegularLine = GroupRegularLine.builder()
				.lineNetAmt(compensationLine.getBaseAmt())
				.build();

		final IOrderBL orderBL = Services.get(IOrderBL.class);
		final int precision = orderBL.getPrecision(invoiceCandidate.getC_Order());

		return Group.builder()
				.groupId(extractGroupId(invoiceCandidate))
				.precision(precision)
				.regularLine(aggregatedRegularLine)
				.compensationLine(compensationLine)
				.build();
	}

	public InvoiceCandidatesStorage createNotSaveableSingleOrderLineStorage(@NonNull final I_C_Invoice_Candidate invoiceCandidate)
	{
		return InvoiceCandidatesStorage.builder()
				.groupId(extractGroupId(invoiceCandidate))
				.invoiceCandidate(invoiceCandidate)
				.performDatabaseChanges(false)
				.build();
	}

	public void invalidateCompensationInvoiceCandidatesOfGroup(final GroupId groupId)
	{
		final IQuery<I_C_Invoice_Candidate> query = retrieveInvoiceCandidatesForGroupQuery(groupId)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_IsGroupCompensationLine, true) // only compensation lines
				.create();
		Services.get(IInvoiceCandDAO.class).invalidateCandsFor(query);
	}
}

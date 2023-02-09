/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.contacts.invoice.interim.service.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.contacts.invoice.interim.InterimInvoiceFlatrateTerm;
import de.metas.contacts.invoice.interim.InterimInvoiceFlatrateTermId;
import de.metas.contacts.invoice.interim.InterimInvoiceFlatrateTermQuery;
import de.metas.contacts.invoice.interim.service.IInterimInvoiceFlatrateTermDAO;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_InterimInvoice_FlatrateTerm;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.stream.Stream;

/**
 * This repo is about {@link I_C_InterimInvoice_FlatrateTerm}.
 */
@Repository
public class InterimInvoiceFlatrateTermDAO implements IInterimInvoiceFlatrateTermDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

	@Nullable
	@Override
	public InterimInvoiceFlatrateTerm getById(@NonNull final InterimInvoiceFlatrateTermId id)
	{
		return queryBL.createQueryBuilder(I_C_InterimInvoice_FlatrateTerm.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_InterimInvoice_FlatrateTerm.COLUMNNAME_C_Interim_Invoice_Candidate_ID, id)
				.create()
				.firstOptional(I_C_InterimInvoice_FlatrateTerm.class)
				.map(this::fromDbObject)
				.orElse(null);
	}

	public InterimInvoiceFlatrateTerm save(@NonNull final InterimInvoiceFlatrateTerm interimInvoiceFlatrateTerm)
	{
		final I_C_InterimInvoice_FlatrateTerm model = toDbObject(interimInvoiceFlatrateTerm);
		InterfaceWrapperHelper.save(model);
		return interimInvoiceFlatrateTerm.toBuilder()
				.id(InterimInvoiceFlatrateTermId.ofRepoId(model.getC_InterimInvoice_FlatrateTerm_ID()))
				.build();
	}

	@Override
	public Stream<InterimInvoiceFlatrateTerm> retrieveBy(final InterimInvoiceFlatrateTermQuery query)
	{
		final IQueryBuilder<I_C_Flatrate_Term> flatrateTermQueryBuilder = queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter();

		final BPartnerId bpartnerId = query.getBpartnerId();
		if (bpartnerId != null)
		{
			flatrateTermQueryBuilder.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID, bpartnerId);
		}

		final Instant dateOn = query.getDateOn();
		if (dateOn != null)
		{
			flatrateTermQueryBuilder.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_StartDate, Operator.LESS_OR_EQUAL, dateOn);
			flatrateTermQueryBuilder.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_EndDate, Operator.GREATER_OR_EQUAL, dateOn);
		}

		final Instant startDate = query.getStartDate();
		if (startDate != null)
		{
			flatrateTermQueryBuilder.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_StartDate, startDate);
		}

		final Instant endDate = query.getEndDate();
		if (endDate != null)
		{
			flatrateTermQueryBuilder.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_EndDate, endDate);
		}

		final FlatrateTermId flatrateTermId = query.getFlatrateTermId();
		if (flatrateTermId != null)
		{
			flatrateTermQueryBuilder.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID, flatrateTermId);
		}

		final IQueryBuilder<I_C_InterimInvoice_FlatrateTerm> interimInvoiceFlatrateTermQueryBuilder = flatrateTermQueryBuilder.andCollectChildren(I_C_InterimInvoice_FlatrateTerm.COLUMNNAME_C_Flatrate_Term_ID, I_C_InterimInvoice_FlatrateTerm.class)
				.addOnlyActiveRecordsFilter();

		if (query.getOrderLineId() != null)
		{
			interimInvoiceFlatrateTermQueryBuilder.addEqualsFilter(I_C_InterimInvoice_FlatrateTerm.COLUMNNAME_C_OrderLine_ID, query.getOrderLineId());
		}

		return interimInvoiceFlatrateTermQueryBuilder
				.addEqualsFilter(I_C_InterimInvoice_FlatrateTerm.COLUMNNAME_M_Product_ID, query.getProductId())

				.orderByDescending(I_C_InterimInvoice_FlatrateTerm.COLUMNNAME_C_InterimInvoice_FlatrateTerm_ID)
				.create()
				.stream()
				.map(this::fromDbObject);
	}

	/**
	 * Lookup if an interimInvoice contract exists that would match this InOutLine (Bpartner/Product/Date/OrderLine).
	 */
	@Nullable
	public InterimInvoiceFlatrateTerm getInterimInvoiceOverviewForInOutLine(@NonNull final I_M_InOutLine inOutLine)
	{
		final ProductId productId = ProductId.ofRepoIdOrNull(inOutLine.getM_Product_ID());
		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(inOutLine.getC_OrderLine_ID());
		//assume we can't look up an interimInvoice overview without this data
		if (productId == null || orderLineId == null)
		{
			return null;
		}

		final I_M_InOut inOut = inOutDAO.getById(InOutId.ofRepoId(inOutLine.getM_InOut_ID()));
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(inOut.getC_BPartner_ID());

		return retrieveBy(InterimInvoiceFlatrateTermQuery.builder()
				.productId(productId)
				.bpartnerId(bpartnerId)
				.dateOn(TimeUtil.asInstant(inOut.getMovementDate()))
				.orderLineId(orderLineId)
				.build())
				.findFirst()
				.orElse(null);
	}

	@Nullable
	public InterimInvoiceFlatrateTerm getInterimInvoiceForFlatrateTermAndOrderLineId(@NonNull final FlatrateTermId flatrateTermId, final @NonNull OrderLineId orderLineId)
	{
		return queryBL.createQueryBuilder(I_C_InterimInvoice_FlatrateTerm.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_InterimInvoice_FlatrateTerm.COLUMNNAME_C_Flatrate_Term_ID, flatrateTermId)
				.addEqualsFilter(I_C_InterimInvoice_FlatrateTerm.COLUMNNAME_C_OrderLine_ID, orderLineId)
				.orderByDescending(I_C_InterimInvoice_FlatrateTerm.COLUMNNAME_C_InterimInvoice_FlatrateTerm_ID)
				.create()
				.firstOptional(I_C_InterimInvoice_FlatrateTerm.class)
				.map(this::fromDbObject)
				.orElse(null);
	}

	@Nullable
	public InterimInvoiceFlatrateTerm getInterimInvoiceFlatrateTermForWithwoldingOrInterimICId(@NonNull final InvoiceCandidateId icId)
	{
		return queryBL.createQueryBuilder(I_C_InterimInvoice_FlatrateTerm.class)
				.addOnlyActiveRecordsFilter()
				.filter(queryBL.createCompositeQueryFilter(I_C_InterimInvoice_FlatrateTerm.class)
						.setJoinOr()
						.addEqualsFilter(I_C_InterimInvoice_FlatrateTerm.COLUMNNAME_C_Invoice_Candidate_Withholding_ID, icId)
						.addEqualsFilter(I_C_InterimInvoice_FlatrateTerm.COLUMNNAME_C_Interim_Invoice_Candidate_ID, icId))
				.orderByDescending(I_C_InterimInvoice_FlatrateTerm.COLUMNNAME_C_InterimInvoice_FlatrateTerm_ID)
				.create()
				.firstOptional(I_C_InterimInvoice_FlatrateTerm.class)
				.map(this::fromDbObject)
				.orElse(null);
	}

	/**
	 * Verify if the given {@link InterimInvoiceFlatrateTerm} is still usable.
	 * Possible reasons for not being usable are:
	 * <ul>
	 *     <li>An invoice has been created for {@link InterimInvoiceFlatrateTerm#getInterimInvoiceCandidateId()}.</li>
	 *     <li>An invoice has been created for {@link InterimInvoiceFlatrateTerm#getWithholdingInvoiceCandidateId()} (Why anyone would?).</li>
	 * </ul>
	 *
	 * @param interimInvoiceFlatrateTerm the {@link InterimInvoiceFlatrateTerm} to verify
	 * @return true if the given {@link InterimInvoiceFlatrateTerm} is still usable, false otherwise
	 */
	public boolean isInterimInvoiceStillUsable(@NonNull final InterimInvoiceFlatrateTerm interimInvoiceFlatrateTerm)
	{
		if (interimInvoiceFlatrateTerm.getInterimInvoiceCandidateId() == null && interimInvoiceFlatrateTerm.getWithholdingInvoiceCandidateId() == null)
		{
			return true;
		}

		return queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID, interimInvoiceFlatrateTerm.getInterimInvoiceCandidateId(), interimInvoiceFlatrateTerm.getWithholdingInvoiceCandidateId())
				.create()
				.list()
				.stream()
				.filter(invoiceCand -> invoiceCand.getQtyInvoiced().compareTo(BigDecimal.ZERO) == 0)
				.map(I_C_Invoice_Candidate::getC_Invoice_Candidate_ID)
				.map(InvoiceCandidateId::ofRepoId)
				.count() == 2;
	}

	private InterimInvoiceFlatrateTerm fromDbObject(@NonNull final I_C_InterimInvoice_FlatrateTerm dbObject)
	{
		final UomId uomId = UomId.ofRepoId(dbObject.getC_UOM_ID());
		final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(dbObject.getC_Currency_ID());
		return InterimInvoiceFlatrateTerm.builder()
				.id(InterimInvoiceFlatrateTermId.ofRepoId(dbObject.getC_InterimInvoice_FlatrateTerm_ID()))
				.flatrateTermId(FlatrateTermId.ofRepoId(dbObject.getC_Flatrate_Term_ID()))
				.orderLineId(OrderLineId.ofRepoId(dbObject.getC_OrderLine_ID()))
				.withholdingInvoiceCandidateId(InvoiceCandidateId.ofRepoIdOrNull(dbObject.getC_Invoice_Candidate_Withholding_ID()))
				.interimInvoiceCandidateId(InvoiceCandidateId.ofRepoIdOrNull(dbObject.getC_Interim_Invoice_Candidate_ID()))
				.productId(ProductId.ofRepoId(dbObject.getM_Product_ID()))
				.uomId(uomId)
				.currencyId(currencyId)
				.qtyOrdered(Quantitys.create(dbObject.getQtyOrdered(), uomId))
				.qtyDelivered(Quantitys.create(dbObject.getQtyDeliveredInUOM(), uomId))
				.qtyInvoiced(Quantitys.create(dbObject.getQtyInvoiced(), uomId))
				.priceActual(Money.ofOrNull(dbObject.getPriceActual(), currencyId))
				.build();
	}

	private I_C_InterimInvoice_FlatrateTerm toDbObject(@NonNull final InterimInvoiceFlatrateTerm object)
	{
		final I_C_InterimInvoice_FlatrateTerm dbObject = InterfaceWrapperHelper.loadOrNew(object.getId(), I_C_InterimInvoice_FlatrateTerm.class);
		dbObject.setC_Flatrate_Term_ID(object.getFlatrateTermId().getRepoId());
		dbObject.setC_OrderLine_ID(object.getOrderLineId().getRepoId());
		dbObject.setC_Invoice_Candidate_Withholding_ID(InvoiceCandidateId.toRepoId(object.getWithholdingInvoiceCandidateId()));
		dbObject.setC_Interim_Invoice_Candidate_ID(InvoiceCandidateId.toRepoId(object.getInterimInvoiceCandidateId()));
		dbObject.setM_Product_ID(ProductId.toRepoId(object.getProductId()));
		dbObject.setC_UOM_ID(UomId.toRepoId(object.getUomId()));
		dbObject.setC_Currency_ID(CurrencyId.toRepoId(object.getCurrencyId()));
		dbObject.setQtyOrdered(Quantity.toBigDecimal(object.getQtyOrdered()));
		dbObject.setQtyDeliveredInUOM(Quantity.toBigDecimal(object.getQtyDelivered()));
		dbObject.setQtyInvoiced(Quantity.toBigDecimal(object.getQtyInvoiced()));
		dbObject.setPriceActual(Money.toBigDecimalOrZero(object.getPriceActual()));
		return dbObject;
	}

}

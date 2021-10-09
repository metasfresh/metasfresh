package de.metas.inout.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.document.DocTypeId;
import de.metas.document.engine.IDocument;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

public class InOutDAO implements IInOutDAO
{
	private static final Logger logger = LogManager.getLogger(InOutDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public I_M_InOut getById(@NonNull final InOutId inoutId)
	{
		return load(inoutId, I_M_InOut.class);
	}

	@Nullable
	@Override
	public <T extends I_M_InOut> T getById(@NonNull final InOutId inoutId, @NonNull final Class<T> modelClass)
	{
		return load(inoutId, modelClass);
	}

	@Override
	public I_M_InOutLine getLineById(@NonNull final InOutLineId inoutLineId)
	{
		return load(inoutLineId, I_M_InOutLine.class);
	}

	@Override
	public <T extends I_M_InOutLine> List<T> getLinesByIds(@NonNull final Set<InOutLineId> inoutLineIds, final Class<T> returnType)
	{
		final Set<Integer> ids = inoutLineIds.stream().map(InOutLineId::getRepoId).collect(Collectors.toSet());

		return queryBL.createQueryBuilder(returnType)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_InOutLine.COLUMNNAME_M_InOutLine_ID, ids)
				.create()
				.list(returnType);
	}

	@NonNull
	@Override
	public ImmutableList<InOutId> retrieveByShipperTransportation(@NonNull final ShipperTransportationId shipperTransportationId)
	{
		return queryBL
				.createQueryBuilder(de.metas.inout.model.I_M_InOut.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(de.metas.inout.model.I_M_InOut.COLUMNNAME_M_ShipperTransportation, shipperTransportationId)
				.create()
				.listIds(InOutId::ofRepoId)
				.asList();
	}

	@Override
	public <T extends I_M_InOutLine> T getLineById(@NonNull final InOutLineId inoutLineId, final Class<T> modelClass)
	{
		@SuppressWarnings("UnnecessaryLocalVariable") final T inoutLine = loadOutOfTrx(inoutLineId.getRepoId(), modelClass);
		return inoutLine;
	}

	@Override
	public List<I_M_InOutLine> retrieveLines(final I_M_InOut inOut)
	{
		final boolean retrieveAll = false;
		return retrieveLines(inOut, retrieveAll, I_M_InOutLine.class);
	}

	@Override
	public List<I_M_InOutLine> retrieveAllLines(final I_M_InOut inOut)
	{
		final boolean retrieveAll = true;
		return retrieveLines(inOut, retrieveAll, I_M_InOutLine.class);
	}

	@Override
	public <T extends I_M_InOutLine> List<T> retrieveLines(final I_M_InOut inOut, final Class<T> inoutLineClass)
	{
		final boolean retrieveAll = false;
		return retrieveLines(inOut, retrieveAll, inoutLineClass);
	}

	private <T extends I_M_InOutLine> List<T> retrieveLines(
			@NonNull final I_M_InOut inOut,
			final boolean retrieveAll,
			@NonNull final Class<T> inoutLineClass)
	{
		final IQueryBuilder<I_M_InOutLine> queryBuilder = queryBL.createQueryBuilder(I_M_InOutLine.class, inOut)
				.addEqualsFilter(I_M_InOutLine.COLUMN_M_InOut_ID, inOut.getM_InOut_ID())
				.orderBy(I_M_InOutLine.COLUMNNAME_Line)
				.orderBy(I_M_InOutLine.COLUMNNAME_M_InOutLine_ID);

		if (!retrieveAll)
		{
			queryBuilder.addOnlyActiveRecordsFilter();
		}

		final List<T> inoutLines = queryBuilder
				.create()
				.list(inoutLineClass);

		// Optimization: set M_InOut link
		for (final I_M_InOutLine inoutLine : inoutLines)
		{
			inoutLine.setM_InOut(inOut);
		}
		return inoutLines;
	}

	@Override
	public List<I_M_InOutLine> retrieveLinesForInOuts(final Collection<? extends I_M_InOut> inouts)
	{
		Check.assumeNotEmpty(inouts, "inouts is not empty");

		final Map<Integer, I_M_InOut> inoutsById = inouts.stream().collect(GuavaCollectors.toImmutableMapByKey(I_M_InOut::getM_InOut_ID));

		final List<I_M_InOutLine> inoutLines = queryBL.createQueryBuilder(I_M_InOutLine.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_InOutLine.COLUMN_M_InOut_ID, inoutsById.keySet())
				.orderBy(I_M_InOutLine.COLUMNNAME_M_InOut_ID)
				.orderBy(I_M_InOutLine.COLUMNNAME_Line)
				.orderBy(I_M_InOutLine.COLUMNNAME_M_InOutLine_ID)
				.create()
				.listImmutable(I_M_InOutLine.class);

		// Optimization: set M_InOut link
		inoutLines.forEach(inoutLine -> inoutLine.setM_InOut(inoutsById.get(inoutLine.getM_InOut_ID())));

		return inoutLines;
	}

	@Override
	public List<I_M_InOutLine> retrieveLinesForOrderLine(final I_C_OrderLine orderLine)
	{
		return retrieveLinesForOrderLine(orderLine, I_M_InOutLine.class);
	}

	@Override
	public <T extends I_M_InOutLine> List<T> retrieveLinesForOrderLine(final I_C_OrderLine orderLine, final Class<T> clazz)
	{
		final IQueryBuilder<I_M_InOutLine> queryBuilder = queryBL.createQueryBuilder(I_M_InOutLine.class, orderLine)
				.addEqualsFilter(I_M_InOutLine.COLUMN_C_OrderLine_ID, orderLine.getC_OrderLine_ID())
				// .filterByClientId()
				.addOnlyActiveRecordsFilter();
		queryBuilder.orderBy()
				.addColumn(I_M_InOutLine.COLUMNNAME_M_InOutLine_ID);

		return queryBuilder.create()
				.list(clazz);
	}

	@Override
	public <T extends I_M_InOutLine> List<T> retrieveLinesWithoutOrderLine(final I_M_InOut inOut, final Class<T> clazz)
	{
		final IQueryBuilder<I_M_InOutLine> queryBuilder = queryBL.createQueryBuilder(I_M_InOutLine.class, inOut)
				.addEqualsFilter(I_M_InOutLine.COLUMNNAME_M_InOut_ID, inOut.getM_InOut_ID())
				.addEqualsFilter(I_M_InOutLine.COLUMNNAME_C_OrderLine_ID, null)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient();
		queryBuilder.orderBy()
				.addColumn(I_M_InOutLine.COLUMNNAME_M_InOutLine_ID);

		return queryBuilder.create()
				.list(clazz);
	}

	@Override
	public IQueryBuilder<I_M_InOutLine> createUnprocessedShipmentLinesQuery(final Properties ctx)
	{
		return queryBL.createQueryBuilder(I_M_InOut.class, ctx, ITrx.TRXNAME_None)
				.addInArrayOrAllFilter(I_M_InOut.COLUMNNAME_DocStatus,
						IDocument.STATUS_Drafted,  // task: 07448: we also need to consider drafted shipments, because that's the customer workflow, and qty in a drafted InOut don'T couln'T at picked
						// anymore, because they are already in a shipper-transportation
						IDocument.STATUS_InProgress,
						IDocument.STATUS_WaitingConfirmation)
				.addEqualsFilter(I_M_InOut.COLUMNNAME_IsSOTrx, true)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.andCollectChildren(I_M_InOutLine.COLUMN_M_InOut_ID, I_M_InOutLine.class);
	}

	@Override
	public IQueryBuilder<I_M_InOutLine> retrieveAllReferencingLinesBuilder(final I_M_InOutLine packingMaterialLine)
	{
		return queryBL
				.createQueryBuilder(I_M_InOutLine.class, packingMaterialLine)
				// .addOnlyActiveRecordsFilter() add all, also inactive ones
				.addEqualsFilter(de.metas.inout.model.I_M_InOutLine.COLUMNNAME_M_PackingMaterial_InOutLine_ID, packingMaterialLine.getM_InOutLine_ID())
				.orderBy()
				.addColumn(I_M_InOutLine.COLUMNNAME_Line)
				.addColumn(I_M_InOutLine.COLUMNNAME_M_InOutLine_ID).endOrderBy();

	}

	@Override
	public List<Integer> retrieveLineIdsWithQualityDiscount(final I_M_InOut inOut)
	{

		final IQueryBuilder<I_M_InOutLine> queryBuilder = createInDisputeQueryBuilder(inOut);
		return queryBuilder
				.create()
				.listIds();
	}

	@Override
	@Nullable
	public I_M_InOutLine retrieveLineWithQualityDiscount(@NonNull final I_M_InOutLine originInOutLine)
	{
		final IQueryBuilder<I_M_InOutLine> queryBuilder = createInDisputeQueryBuilder(originInOutLine.getM_InOut());

		final int orderLineID = originInOutLine.getC_OrderLine_ID();

		return queryBuilder.addEqualsFilter(I_M_InOutLine.COLUMNNAME_C_OrderLine_ID, orderLineID)
				.create()
				.firstOnly(I_M_InOutLine.class);
	}

	private IQueryBuilder<I_M_InOutLine> createInDisputeQueryBuilder(final I_M_InOut inOut)
	{
		return queryBL
				.createQueryBuilder(I_M_InOutLine.class, inOut)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(de.metas.inout.model.I_M_InOutLine.COLUMNNAME_M_InOut_ID, inOut.getM_InOut_ID())
				.addCompareFilter(de.metas.inout.model.I_M_InOutLine.COLUMNNAME_QualityDiscountPercent, Operator.GREATER, BigDecimal.ZERO);
	}

	@Override
	public LocalDate getLastInOutDate(@NonNull final BPartnerId bpartnerId, @NonNull final ProductId productId, @NonNull final SOTrx soTrx)
	{
		return queryBL.createQueryBuilder(I_M_InOutLine.class)
				.addEqualsFilter(I_M_InOutLine.COLUMNNAME_M_Product_ID, productId.getRepoId())
				.andCollect(I_M_InOutLine.COLUMN_M_InOut_ID)
				.addEqualsFilter(I_M_InOut.COLUMNNAME_C_BPartner_ID, bpartnerId.getRepoId())
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_InOut.COLUMN_IsSOTrx, soTrx.toBoolean())
				.create()
				.aggregate(I_M_InOut.COLUMN_MovementDate, Aggregate.MAX, LocalDate.class);
	}

	@Override
	public I_M_InOut retrieveInOut(@NonNull final List<I_M_InOutLine> receiptLines)
	{
		final int inOutId = CollectionUtils.extractSingleElement(receiptLines, I_M_InOutLine::getM_InOut_ID);

		return load(inOutId, I_M_InOut.class);
	}

	@Override
	public Stream<InOutId> streamInOutIdsByBPartnerId(@NonNull final BPartnerId bpartnerId)
	{
		return queryBL.createQueryBuilder(I_M_InOut.class)
				.addEqualsFilter(I_M_InOut.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.create()
				.listIds(InOutId::ofRepoId)
				.stream();
	}

	@Override
	public Set<InOutAndLineId> retrieveLinesForInOutId(final InOutId inOutId)
	{
		final I_M_InOut inOut = getById(inOutId);

		return retrieveLines(inOut)
				.stream()
				.map(this::extractInOutAndLineId)
				.collect(ImmutableSet.toImmutableSet());
	}

	private InOutAndLineId extractInOutAndLineId(final I_M_InOutLine line)
	{
		return InOutAndLineId.ofRepoId(line.getM_InOut_ID(), line.getM_InOutLine_ID());
	}

	@Override
	public void setExportedInCustomsInvoice(final InOutId shipmentId)
	{
		final I_M_InOut shipment = getById(shipmentId, I_M_InOut.class);

		if (shipment != null && !shipment.isExportedToCustomsInvoice())
		{
			shipment.setIsExportedToCustomsInvoice(true);
			save(shipment);
		}
	}

	@Override
	public void unsetLineNos(@NonNull final ImmutableList<InOutLineId> inOutLineIdsToUnset)
	{
		final ICompositeQueryUpdater<I_M_InOutLine> updater = queryBL.createCompositeQueryUpdater(I_M_InOutLine.class)
				.addSetColumnValue(I_M_InOutLine.COLUMNNAME_Line, 0);

		final int unsetCount = queryBL.createQueryBuilder(I_M_InOutLine.class)
				.addInArrayFilter(I_M_InOutLine.COLUMNNAME_M_InOutLine_ID, inOutLineIdsToUnset)
				.addCompareFilter(I_M_InOutLine.COLUMNNAME_Line, Operator.GREATER, 0)
				.create()
				.update(updater);
		logger.debug("LineNo was set to 0 for {} M_InOutLine records; inOutLineIdsToUnset.size={}", unsetCount, inOutLineIdsToUnset.size());
	}

	@Override
	@Nullable
	public I_M_InOut getInOutByDocumentNumber(@NonNull final String documentNo, @NonNull final DocTypeId docTypeId, @NonNull final OrgId orgId)
	{
		final List<I_M_InOut> inOutList = queryBL.createQueryBuilder(I_M_InOut.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_InOut.COLUMNNAME_AD_Org_ID, orgId)
				.addEqualsFilter(I_M_InOut.COLUMNNAME_C_DocType_ID, docTypeId)
				.addEqualsFilter(I_M_InOut.COLUMNNAME_DocumentNo, documentNo)
				.create()
				.list();

		if (inOutList.size() == 1)
		{
			return inOutList.get(0);
		}

		return null;
	}

	@Override
	public ImmutableMap<InOutLineId, I_M_InOut> retrieveInOutByLineIds(@NonNull final Set<InOutLineId> inOutLineIds)
	{
		final List<I_M_InOutLine> inOutLines = queryBL.createQueryBuilder(I_M_InOutLine.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_InOutLine.COLUMNNAME_M_InOutLine_ID, inOutLineIds)
				.create()
				.list();

		final Set<Integer> inOutIds = inOutLines.stream().map(I_M_InOutLine::getM_InOut_ID).collect(ImmutableSet.toImmutableSet());

		final Map<Integer, I_M_InOut> inOutRecordsById = queryBL.createQueryBuilder(I_M_InOut.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_InOut.COLUMNNAME_M_InOut_ID, inOutIds)
				.create()
				.mapById();

		final ImmutableMap.Builder<InOutLineId, I_M_InOut> lineId2InOutBuilder = ImmutableMap.builder();

		inOutLines.forEach(inOutLine -> {
			final InOutLineId inOutLineId = InOutLineId.ofRepoId(inOutLine.getM_InOutLine_ID());

			lineId2InOutBuilder.put(inOutLineId, inOutRecordsById.get(inOutLine.getM_InOut_ID()));
		});

		return lineId2InOutBuilder.build();
	}

	@Override
	public void save(@NonNull final I_M_InOut inout)
	{
		InterfaceWrapperHelper.saveRecord(inout);
	}

	@Override
	public void save(@NonNull final I_M_InOutLine inoutLine)
	{
		InterfaceWrapperHelper.saveRecord(inoutLine);
	}

	@Override
	public List<I_M_InOutLine> retrieveShipmentLinesForOrderId(@NonNull final Set<OrderId> orderIds)
	{
		final List<Integer> shipmentIds = queryBL.createQueryBuilder(I_M_InOut.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_InOut.COLUMNNAME_C_Order_ID, orderIds)
				.create()
				.listDistinct(I_M_InOut.COLUMNNAME_M_InOut_ID, Integer.class);

		if (shipmentIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL.createQueryBuilder(I_M_InOutLine.class)
				.addInArrayFilter(I_M_InOutLine.COLUMN_M_InOut_ID, shipmentIds)
				.create()
				.listImmutable(I_M_InOutLine.class);
	}
}

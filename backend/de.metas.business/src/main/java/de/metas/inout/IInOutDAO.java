package de.metas.inout;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.document.DocTypeId;
import de.metas.lang.SOTrx;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Stream;

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

public interface IInOutDAO extends ISingletonService
{
	I_M_InOut getById(InOutId inoutId);

	@Nullable
	<T extends I_M_InOut> T getById(@NonNull InOutId inoutId, @NonNull Class<T> modelClass);

	List<I_M_InOut> getByOrderId(@NonNull OrderId orderId);

	I_M_InOutLine getLineByIdInTrx(InOutLineId inoutLineId);

	<T extends I_M_InOutLine> List<T> getLinesByIds(Set<InOutLineId> inoutLineIds, Class<T> returnType);

	List<I_M_InOutLine> retrieveLines(I_M_InOut inOut);

	List<I_M_InOutLine> retrieveAllLines(I_M_InOut inOut);

	/**
	 * Retrieve all (active) lines of given <code>inOut</code>.
	 *
	 * @return <code>inOut</code>'s lines
	 */
	<T extends I_M_InOutLine> List<T> retrieveLines(I_M_InOut inOut, Class<T> inoutLineClass);

	ImmutableSet<InOutLineId> retrieveActiveLineIdsByInOutIds(Set<InOutId> inoutIds);

	List<I_M_InOutLine> retrieveLinesForInOuts(Collection<? extends I_M_InOut> inouts);

	Set<InOutAndLineId> retrieveLineIdsByOrderLineIds(Set<OrderLineId> orderLineIds);

	/**
	 * For the given <code>inOut</code> the method returns those inout lines that don't reference an order line.
	 */
	<T extends I_M_InOutLine> List<T> retrieveLinesWithoutOrderLine(I_M_InOut inOut, Class<T> clazz);

	List<I_M_InOutLine> retrieveLinesForOrderLine(I_C_OrderLine orderLine);

	<T extends I_M_InOutLine> List<T> retrieveCompleteOrClosedLinesForOrderLine(@NonNull final OrderLineId orderLineId, Class<T> clazz);

	<T extends I_M_InOutLine> List<T> retrieveLinesForOrderLine(I_C_OrderLine orderLine, Class<T> clazz);

	/**
	 * @return query to retrieve all {@link I_M_InOutLine}s which are part of a shipment with doc status <code>Draft</code>, <code>InProgress</code> or <code>WaitingConfirmation</code>.
	 */
	IQueryBuilder<I_M_InOutLine> createUnprocessedShipmentLinesQuery(Properties ctx);

	/**
	 * Returns all (including inactive) M_InOutLines that reference the given <code>packingMaterialLine</code> from their <code>M_PackingMaterial_InOutLine_ID</code> value.
	 */
	IQueryBuilder<I_M_InOutLine> retrieveAllReferencingLinesBuilder(I_M_InOutLine packingMaterialLine);

	/**
	 * Returns all the M_InOutLine IDs of the given inout that have quality issues ( QualityDiscountPercent > 0).
	 */
	List<Integer> retrieveLineIdsWithQualityDiscount(I_M_InOut inOut);

	/**
	 * Retrieve the M_InOutLine that is in dispute (has qty with issues) and is based on the same order line as the inout line given as parameter.
	 */
	@Nullable
	I_M_InOutLine retrieveLineWithQualityDiscount(@NonNull I_M_InOutLine originInOutLine);

	LocalDate getLastInOutDate(BPartnerId bpartnerId, ProductId productId, SOTrx soTrx);

	I_M_InOut retrieveInOut(List<I_M_InOutLine> receiptLines);

	Stream<InOutId> streamInOutIdsByBPartnerId(BPartnerId bpartnerId);

	Set<InOutAndLineId> retrieveLinesForInOutId(InOutId inOutId);

	I_M_InOutLine getLineByIdInTrx(@NonNull InOutAndLineId inoutLineId);

	<T extends I_M_InOutLine> T getLineByIdOutOfTrx(@NonNull InOutLineId inoutLineId, Class<T> modelClass);

	<T extends I_M_InOutLine> T getLineByIdInTrx(@NonNull InOutLineId inoutLineId, @NonNull Class<T> modelClass);

	@NonNull
	ImmutableList<InOutId> retrieveByShipperTransportation(@NonNull ShipperTransportationId shipperTransportationId);

	void setExportedInCustomsInvoice(InOutId shipmentId);

	void unsetLineNos(ImmutableList<InOutLineId> inOutLineIdsToUnset);

	I_M_InOut getInOutByDocumentNumber(String documentNo, DocTypeId docTypeId, OrgId orgId);

	ImmutableMap<InOutLineId, I_M_InOut> retrieveInOutByLineIds(Set<InOutLineId> inOutLineIds);

	void save(@NonNull I_M_InOut inout);

	void save(@NonNull I_M_InOutLine inoutLine);

	List<I_M_InOutLine> retrieveShipmentLinesForOrderId(Set<OrderId> orderIds);

	<T extends I_M_InOut> Map<InOutId, T> getShipmentsByIds(Set<InOutId> inOutIds, Class<T> modelClass);

	Optional<I_M_InOutLine> getReversalLineForLineId(@NonNull final InOutLineId inoutLineId);

	Collection<InOutAndLineId> retrieveLineIdsForOrderLineIdAvailableForInterimInvoice(OrderLineId orderLine);

	Optional<OrderId> getOrderIdForLineId(@NonNull InOutLineId inoutLineId);

	Stream<I_M_InOutLine> stream(@NonNull InOutLineQuery query);
}

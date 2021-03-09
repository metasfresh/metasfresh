package de.metas.material.planning.pporder.impl;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.Collection;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOM;
import org.eevolution.model.I_PP_Order_BOMLine;

import com.google.common.collect.ImmutableList;

import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import de.metas.util.Services;
import lombok.NonNull;

public class PPOrderBOMDAO implements IPPOrderBOMDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public I_PP_Order_BOMLine getOrderBOMLineById(@NonNull final PPOrderBOMLineId orderBOMLineId)
	{
		return load(orderBOMLineId, I_PP_Order_BOMLine.class);
	}

	@Override
	public I_PP_Order_BOM getByOrderIdOrNull(@NonNull final PPOrderId orderId)
	{
		return queryBL
				.createQueryBuilder(I_PP_Order_BOM.class)
				.addEqualsFilter(I_PP_Order_BOM.COLUMNNAME_PP_Order_ID, orderId)
				.create()
				// .setOnlyActiveRecords(true) // we shall have only active records anyway
				.firstOnly(I_PP_Order_BOM.class);
	}

	@Override
	public List<I_PP_Order_BOMLine> retrieveOrderBOMLines(final I_PP_Order order)
	{
		final PPOrderId orderId = PPOrderId.ofRepoId(order.getPP_Order_ID());
		final List<I_PP_Order_BOMLine> orderBOMLines = retrieveOrderBOMLines(ImmutableList.of(orderId), I_PP_Order_BOMLine.class);

		// Optimization: set parent link
		for (final I_PP_Order_BOMLine orderBOMLine : orderBOMLines)
		{
			orderBOMLine.setPP_Order(order);
		}

		return orderBOMLines;
	}

	@Override
	public List<I_PP_Order_BOMLine> retrieveOrderBOMLines(@NonNull final PPOrderId orderId)
	{
		return retrieveOrderBOMLines(ImmutableList.of(orderId), I_PP_Order_BOMLine.class);
	}

	@Override
	public <T extends I_PP_Order_BOMLine> List<T> retrieveOrderBOMLines(
			@NonNull final PPOrderId orderId,
			@NonNull final Class<T> orderBOMLineClass)
	{
		return retrieveOrderBOMLines(ImmutableList.of(orderId), orderBOMLineClass);
	}

	@Override
	public <T extends I_PP_Order_BOMLine> List<T> retrieveOrderBOMLines(
			@NonNull final Collection<PPOrderId> orderIds,
			@NonNull final Class<T> orderBOMLineClass)
	{
		if (orderIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL.createQueryBuilder(orderBOMLineClass)
				.addInArrayFilter(I_PP_Order_BOMLine.COLUMNNAME_PP_Order_ID, orderIds)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_PP_Order_BOMLine.COLUMNNAME_PP_Order_ID)
				.orderBy(I_PP_Order_BOMLine.COLUMNNAME_Line)
				//
				.create()
				.listImmutable(orderBOMLineClass);
	}

	@Override
	public I_PP_Order_BOMLine retrieveOrderBOMLine(@NonNull final I_PP_Order ppOrder, @NonNull final I_M_Product product)
	{
		return queryBL.createQueryBuilder(I_PP_Order_BOMLine.class, ppOrder)
				.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_PP_Order_ID, ppOrder.getPP_Order_ID())
				.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_M_Product_ID, product.getM_Product_ID())
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_PP_Order_BOMLine.class);
	}

	@Override
	public void deleteOrderBOMLinesByOrderId(@NonNull final PPOrderId orderId)
	{
		final List<I_PP_Order_BOMLine> lines = queryBL
				.createQueryBuilder(I_PP_Order_BOMLine.class)
				.addEqualsFilter(I_PP_Order_BOMLine.COLUMN_PP_Order_ID, orderId)
				// .addOnlyActiveRecordsFilter()
				.create()
				.list();

		for (final I_PP_Order_BOMLine line : lines)
		{
			line.setProcessed(false);
			InterfaceWrapperHelper.delete(line);
		}
	}

	@Override
	public int retrieveNextLineNo(@NonNull final PPOrderId orderId)
	{
		Integer maxLine = queryBL
				.createQueryBuilder(I_PP_Order_BOMLine.class)
				.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_PP_Order_ID, orderId)
				.create()
				.aggregate(I_PP_Order_BOMLine.COLUMNNAME_Line, Aggregate.MAX, Integer.class);

		if (maxLine == null)
		{
			maxLine = 0;
		}

		final int nextLine = maxLine + 10;
		return nextLine;
	}

	@Override
	public void save(@NonNull final I_PP_Order_BOM orderBOM)
	{
		saveRecord(orderBOM);
	}

	@Override
	public void save(@NonNull final I_PP_Order_BOMLine orderBOMLine)
	{
		saveRecord(orderBOMLine);
	}

	@Override
	public void deleteByOrderId(@NonNull final PPOrderId orderId)
	{
		final I_PP_Order_BOM orderBOM = getByOrderIdOrNull(orderId);
		if (orderBOM != null)
		{
			InterfaceWrapperHelper.delete(orderBOM);
		}
	}

	@Override
	public void markBOMLinesAsProcessed(@NonNull final PPOrderId orderId)
	{
		for (final I_PP_Order_BOMLine orderBOMLine : retrieveOrderBOMLines(orderId))
		{
			orderBOMLine.setProcessed(true);
			save(orderBOMLine);
		}
	}

	@Override
	public void markBOMLinesAsNotProcessed(@NonNull final PPOrderId orderId)
	{
		for (final I_PP_Order_BOMLine orderBOMLine : retrieveOrderBOMLines(orderId))
		{
			orderBOMLine.setProcessed(false);
			save(orderBOMLine);
		}
	}

}

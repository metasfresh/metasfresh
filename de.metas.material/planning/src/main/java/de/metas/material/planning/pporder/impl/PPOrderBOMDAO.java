package de.metas.material.planning.pporder.impl;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOM;
import org.eevolution.model.I_PP_Order_BOMLine;

import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.PPOrderBOMLineId;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.util.Services;
import lombok.NonNull;

public class PPOrderBOMDAO implements IPPOrderBOMDAO
{
	@Override
	public I_PP_Order_BOMLine getOrderBOMLineById(@NonNull final PPOrderBOMLineId orderBOMLineId)
	{
		return load(orderBOMLineId, I_PP_Order_BOMLine.class);
	}

	@Override
	public I_PP_Order_BOM getByOrderId(@NonNull final PPOrderId orderId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_PP_Order_BOM.class)
				.addEqualsFilter(I_PP_Order_BOM.COLUMNNAME_PP_Order_ID, orderId)
				.create()
				// .setOnlyActiveRecords(true) // we shall have only active records anyway
				.firstOnly(I_PP_Order_BOM.class);
	}

	@Override
	public List<I_PP_Order_BOMLine> retrieveOrderBOMLines(final I_PP_Order order)
	{
		return retrieveOrderBOMLines(order, I_PP_Order_BOMLine.class);
	}

	@Override
	public <T extends I_PP_Order_BOMLine> List<T> retrieveOrderBOMLines(@NonNull final I_PP_Order order, @NonNull final Class<T> orderBOMLineClass)
	{
		final PPOrderId orderId = PPOrderId.ofRepoId(order.getPP_Order_ID());
		final List<T> orderBOMLines = retrieveOrderBOMLines(orderId, orderBOMLineClass);

		// Optimization: set parent link
		for (final T orderBOMLine : orderBOMLines)
		{
			orderBOMLine.setPP_Order(order);
		}

		return orderBOMLines;
	}

	@Override
	public List<I_PP_Order_BOMLine> retrieveOrderBOMLines(@NonNull final PPOrderId orderId)
	{
		return retrieveOrderBOMLines(orderId, I_PP_Order_BOMLine.class);
	}

	@Override
	public <T extends I_PP_Order_BOMLine> List<T> retrieveOrderBOMLines(@NonNull final PPOrderId orderId, @NonNull final Class<T> orderBOMLineClass)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(orderBOMLineClass)
				.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_PP_Order_ID, orderId)
				.addOnlyActiveRecordsFilter()
				//
				.orderBy()
				.addColumn(I_PP_Order_BOMLine.COLUMNNAME_Line, Direction.Ascending, Nulls.Last)
				.endOrderBy()
				//
				.create()
				.list(orderBOMLineClass);
	}

	@Override
	public I_PP_Order_BOMLine retrieveOrderBOMLine(@NonNull final I_PP_Order ppOrder, @NonNull final I_M_Product product)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_PP_Order_BOMLine.class, ppOrder)
				.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_PP_Order_ID, ppOrder.getPP_Order_ID())
				.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_M_Product_ID, product.getM_Product_ID())
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_PP_Order_BOMLine.class);
	}

	@Override
	public void deleteOrderBOMLinesByOrderId(@NonNull final PPOrderId orderId)
	{
		final List<I_PP_Order_BOMLine> lines = Services.get(IQueryBL.class)
				.createQueryBuilder(I_PP_Order_BOMLine.class)
				.addEqualsFilter(I_PP_Order_BOMLine.COLUMN_PP_Order_ID, orderId)
				// .addOnlyActiveRecordsFilter()
				.create()
				.list();
		
		for(final I_PP_Order_BOMLine line : lines)
		{
			line.setProcessed(false);
			InterfaceWrapperHelper.delete(line);
		}
	}

	@Override
	public int retrieveNextLineNo(@NonNull final PPOrderId orderId)
	{
		Integer maxLine = Services.get(IQueryBL.class)
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
		I_PP_Order_BOM orderBOM = getByOrderId(orderId);
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

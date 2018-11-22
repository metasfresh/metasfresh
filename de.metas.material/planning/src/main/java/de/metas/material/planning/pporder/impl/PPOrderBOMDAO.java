package de.metas.material.planning.pporder.impl;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.Collections;
import java.util.List;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOM;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.X_PP_Order_BOMLine;

import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

public class PPOrderBOMDAO implements IPPOrderBOMDAO
{
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
	public <T extends I_PP_Order_BOMLine> List<T> retrieveOrderBOMLineAlternatives(final I_PP_Order_BOMLine orderBOMLine, final Class<T> orderBOMLineClass)
	{
		Check.assumeNotNull(orderBOMLine, "orderBOMLine not null");
		Check.assumeNotNull(orderBOMLineClass, "orderBOMLineClass not null");

		//
		// Make sure we are not going to retrieve alternatives for an alternative
		if (X_PP_Order_BOMLine.COMPONENTTYPE_Variant.equals(orderBOMLine.getComponentType()))
		{
			throw new LiberoException("Cannot get alternative order BOM lines for an alternative line: " + orderBOMLine);
		}

		//
		// If BOM Line does not have VariantGroup set it means it does not have alternatives, so return empty right away
		final String variantGroup = orderBOMLine.getVariantGroup();
		if (Check.isEmpty(variantGroup, true))
		{
			return Collections.emptyList();
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<T> queryBuilder = queryBL.createQueryBuilder(orderBOMLineClass, orderBOMLine)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_PP_Order_ID, orderBOMLine.getPP_Order_ID())
				.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_ComponentType, X_PP_Order_BOMLine.COMPONENTTYPE_Variant)
				.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_VariantGroup, variantGroup);

		queryBuilder.orderBy()
				.addColumn(I_PP_Order_BOMLine.COLUMNNAME_Line, Direction.Ascending, Nulls.Last);

		final List<T> orderBOMLineAlternatives = queryBuilder
				.create()
				.list(orderBOMLineClass);

		// Optimization: set parent link
		if (!orderBOMLineAlternatives.isEmpty())
		{
			final I_PP_Order ppOrder = orderBOMLine.getPP_Order();
			for (final T orderBOMLineAlt : orderBOMLineAlternatives)
			{
				orderBOMLineAlt.setPP_Order(ppOrder);
			}
		}

		return orderBOMLineAlternatives;
	}

	@Override
	public I_PP_Order_BOMLine retrieveComponentBOMLineForAlternative(final I_PP_Order_BOMLine orderBOMLineAlternative)
	{
		Check.assumeNotNull(orderBOMLineAlternative, "orderBOMLineAlternative not null");

		// Make sure we are dealing with an alternative BOM Line
		if (!X_PP_Order_BOMLine.COMPONENTTYPE_Variant.equals(orderBOMLineAlternative.getComponentType()))
		{
			throw new LiberoException("Order BOM Line is not an component alternative: " + orderBOMLineAlternative);
		}

		// If our Alternative BOM Line does not have VariantGroup => error because it's not consistent
		final String variantGroup = orderBOMLineAlternative.getVariantGroup();
		if (Check.isEmpty(variantGroup, true))
		{
			throw new LiberoException("Alternative BOM Line does not have a VariantGroup set: " + orderBOMLineAlternative);
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_PP_Order_BOMLine> queryBuilder = queryBL.createQueryBuilder(I_PP_Order_BOMLine.class, orderBOMLineAlternative)
				.addOnlyActiveRecordsFilter()
				// Same manufacturing order
				.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_PP_Order_ID, orderBOMLineAlternative.getPP_Order_ID())
				// Exclude the alternatives/variants lines
				.addNotEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_ComponentType, X_PP_Order_BOMLine.COMPONENTTYPE_Variant)
				// For our VariantGroup
				.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_VariantGroup, variantGroup);

		// Get the Component BOM Line
		// NOTE: we assume there is ONLY one
		final I_PP_Order_BOMLine orderBOMLineComponent = queryBuilder.create().firstOnly(I_PP_Order_BOMLine.class);
		return orderBOMLineComponent;
	}

	@Override
	public I_PP_Order_BOMLine retrieveOrderBOMLine(final I_PP_Order ppOrder, final I_M_Product product)
	{
		Check.assumeNotNull(ppOrder, LiberoException.class, "ppOrder not null");
		Check.assumeNotNull(product, LiberoException.class, "product not null");

		return Services.get(IQueryBL.class).createQueryBuilder(I_PP_Order_BOMLine.class, ppOrder)
				.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_PP_Order_ID, ppOrder.getPP_Order_ID())
				.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_M_Product_ID, product.getM_Product_ID())
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_PP_Order_BOMLine.class);
	}

	@Override
	public List<I_PP_Order_BOMLine> retrieveAllOrderBOMLines(final I_PP_Order_BOM orderBOM)
	{
		final IQueryBuilder<I_PP_Order_BOMLine> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_PP_Order_BOMLine.class, orderBOM)
				.addEqualsFilter(I_PP_Order_BOMLine.COLUMN_PP_Order_BOM_ID, orderBOM.getPP_Order_BOM_ID())
		// .addOnlyActiveRecordsFilter()
		;

		queryBuilder.orderBy()
				.addColumn(I_PP_Order_BOMLine.COLUMN_Line, Direction.Ascending, Nulls.Last);
		return queryBuilder
				.create()
				.list(I_PP_Order_BOMLine.class);
	}

	@Override
	public int retrieveNextLineNo(final I_PP_Order order)
	{
		final IQueryBuilder<I_PP_Order_BOMLine> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_PP_Order_BOMLine.class, order);

		final ICompositeQueryFilter<I_PP_Order_BOMLine> filters = queryBuilder.getCompositeFilter();
		filters.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_PP_Order_ID, order.getPP_Order_ID());

		Integer maxLine = queryBuilder
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
}

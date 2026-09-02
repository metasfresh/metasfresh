package de.metas.mforecast.generator;

import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.IQuery;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public class SalesHistoryRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public BigDecimal retrieveTotalSalesQty(@NonNull final SalesHistoryQuery query)
	{
		final IQuery<I_C_Order> salesOrderQuery = queryBL.createQueryBuilder(I_C_Order.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Order.COLUMNNAME_IsSOTrx, true)
				.addInArrayFilter(I_C_Order.COLUMNNAME_DocStatus, "CO", "CL")
				.addCompareFilter(I_C_Order.COLUMNNAME_DateOrdered, Operator.GREATER_OR_EQUAL, TimeUtil.asTimestamp(query.getDateFrom()))
				.addCompareFilter(I_C_Order.COLUMNNAME_DateOrdered, Operator.LESS, TimeUtil.asTimestamp(query.getDateTo()))
				.create();

		final BigDecimal result = queryBL.createQueryBuilder(I_C_OrderLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_M_Product_ID, query.getProductId())
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_AD_Org_ID, query.getOrgId())
				.addInSubQueryFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, I_C_Order.COLUMNNAME_C_Order_ID, salesOrderQuery)
				.create()
				.aggregate(I_C_OrderLine.COLUMNNAME_QtyOrdered, IQuery.Aggregate.SUM, BigDecimal.class);

		return result != null ? result : BigDecimal.ZERO;
	}
}

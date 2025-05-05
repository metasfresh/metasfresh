package de.metas.order.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.document.engine.IDocument;
import de.metas.order.DeliveryViaRule;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.eevolution.api.PPCostCollectorId;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class OrderDAO extends AbstractOrderDAO
{
	@Override
	public List<I_M_InOut> retrieveInOutsForMatchingOrderLines(final I_C_Order order)
	{
		//
		// TODO 05768 keep legacy logic; this stuff should be upgraded, but it's out of scope here
		//
		final Properties ctx = InterfaceWrapperHelper.getCtx(order);
		final String trxName = InterfaceWrapperHelper.getTrxName(order);

		final String whereClause = "EXISTS (SELECT 1 FROM M_InOutLine iol, C_OrderLine ol"
				+ " WHERE iol.M_InOut_ID=M_InOut.M_InOut_ID"
				+ " AND iol.C_OrderLine_ID=ol.C_OrderLine_ID"
				+ " AND ol.C_Order_ID=?)";
		return new Query(ctx, org.compiere.model.I_M_InOut.Table_Name, whereClause, trxName)
				.setParameters(order.getC_Order_ID())
				.setOrderBy("M_InOut_ID DESC")
				.list(I_M_InOut.class);
	}

	@Override
	public List<I_C_Order> retrievePurchaseOrdersForPickup(
			final I_C_BPartner_Location bpLoc,
			final Date deliveryDateTime,
			final Date deliveryDateTimeMax)
	{
		// retrieve purchase orders in specified time frame
		final IQueryBuilder<I_C_Order> queryBuilder =
				Services.get(IQueryBL.class).createQueryBuilder(I_C_Order.class, bpLoc)
						.addEqualsFilter(I_C_Order.COLUMNNAME_IsSOTrx, false)

						// ts: it seems not really required and might be even counterproductive if we somhow extend
						// purchase order with BP/relations or whatever.
						// .addEqualsFilter(I_C_Order.COLUMNNAME_C_BPartner_ID, bpLoc.getC_BPartner_ID())

						.addEqualsFilter(I_C_Order.COLUMNNAME_DeliveryViaRule, DeliveryViaRule.Pickup)

						.addEqualsFilter(I_C_Order.COLUMNNAME_C_BPartner_Location_ID, bpLoc.getC_BPartner_Location_ID())

						// only orders that are not voided, reversed or closed
						.addEqualsFilter(I_C_Order.COLUMNNAME_DocStatus, IDocument.STATUS_Completed)

						// DatePromised between DeliveryDateTime and DeliveryDateTimeMax
						.addCompareFilter(I_C_Order.COLUMNNAME_DatePromised, Operator.LESS_OR_EQUAL, deliveryDateTimeMax)
						.addCompareFilter(I_C_Order.COLUMNNAME_DatePromised, Operator.GREATER_OR_EQUAL, deliveryDateTime)

						.addOnlyActiveRecordsFilter();

		return queryBuilder.create().list();
	}

	@Override
	public BigDecimal getNotInvoicedAmt(@NonNull final BPartnerId bpartnerId)
	{
		final String sql = "SELECT COALESCE(SUM(COALESCE("
				+ "currencyBase((ol.QtyDelivered-ol.QtyInvoiced)*ol.PriceActual,o.C_Currency_ID,o.DateOrdered, o.AD_Client_ID,o.AD_Org_ID) ,0)),0) "
				+ "FROM C_OrderLine ol"
				+ " INNER JOIN C_Order o ON (ol.C_Order_ID=o.C_Order_ID) "
				+ "WHERE o.IsSOTrx='Y' AND Bill_BPartner_ID=?";

		return DB.getSQLValueBDEx(ITrx.TRXNAME_None, sql, bpartnerId);
	}

	@Override
	public Optional<PPCostCollectorId> getPPCostCollectorId(@NonNull final OrderLineId orderLineId)
	{
		final String sql = "SELECT " + I_C_OrderLine.COLUMNNAME_PP_Cost_Collector_ID
				+ " FROM C_OrderLine WHERE C_OrderLine_ID=? AND PP_Cost_Collector_ID IS NOT NULL";
		return Optional.ofNullable(PPCostCollectorId.ofRepoIdOrNull(DB.getSQLValueEx(ITrx.TRXNAME_ThreadInherited, sql, orderLineId)));
	}
}

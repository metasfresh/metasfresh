package org.adempiere.model;

import de.metas.adempiere.model.I_C_Order;
import de.metas.order.OrderFreightCostsService;
import de.metas.util.collections.CompositePredicate;
import lombok.NonNull;
import org.compiere.Adempiere;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Order_CompensationGroup;
import org.compiere.model.PO;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

public class MOrderLinePOCopyRecordSupport extends GeneralCopyRecordSupport
{
	private static final String DYNATTR_OrderCompensationGroupIdsMap = "OrderCompensationGroupIdsMap";

	/**
	 * Skip predicates: if it's evaluated <code>true</code> (i.e. {@link Predicate#test(Object)} returns true) then the order line will NOT copied.
	 */
	private static final CompositePredicate<I_C_OrderLine> skipPredicates = new CompositePredicate<I_C_OrderLine>()
			.addPredicate(orderLine -> isNotFreightCost(orderLine));

	private static boolean isNotFreightCost(final @NonNull I_C_OrderLine orderLine)
	{
		final OrderFreightCostsService ordersFreightCostService = Adempiere.getBean(OrderFreightCostsService.class);
		return !ordersFreightCostService.isFreightCostOrderLine(orderLine);
	}

	/**
	 * Add a skip filter.
	 * <p>
	 * In case given skip filter evaluates the order line as true (i.e. {@link Predicate#test(Object)} returns true) then the order line will NOT copied.
	 */
	public static void addSkipPredicate(final Predicate<I_C_OrderLine> skipPredicate)
	{
		skipPredicates.addPredicate(skipPredicate);
	}

	/**
	 * @return true if the record shall be copied
	 */
	public static boolean isCopyRecord(final I_C_OrderLine orderLine)
	{
		return skipPredicates.isEmpty() || skipPredicates.test(orderLine);
	}

	@Override
	public void copyRecord(final PO po, final String trxName)
	{
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(po, I_C_OrderLine.class);

		// Check if we shall skip this record
		if (!isCopyRecord(orderLine))
		{
			return;
		}

		// delegate to super
		super.copyRecord(po, trxName);
	}

	@Override
	protected void onRecordCopied(final PO to, final PO from)
	{
		final I_C_OrderLine toOrderLine = InterfaceWrapperHelper.create(to, I_C_OrderLine.class);
		final I_C_OrderLine fromOrderLine = InterfaceWrapperHelper.create(from, I_C_OrderLine.class);

		onOrderLineCopied(toOrderLine, fromOrderLine);
	}

	private void onOrderLineCopied(final I_C_OrderLine toOrderLine, final I_C_OrderLine fromOrderLine)
	{
		toOrderLine.setC_Order_CompensationGroup_ID(getOrCloneOrderCompensationGroup(fromOrderLine.getC_Order_CompensationGroup_ID()));
	}

	private int getOrCloneOrderCompensationGroup(final int orderCompensationGroupId)
	{
		if (orderCompensationGroupId <= 0)
		{
			return -1;
		}

		final I_C_Order toOrder = getParentModel(I_C_Order.class);
		Map<Integer, Integer> orderCompensationGroupIdsMap = InterfaceWrapperHelper.getDynAttribute(toOrder, DYNATTR_OrderCompensationGroupIdsMap);
		if (orderCompensationGroupIdsMap == null)
		{
			orderCompensationGroupIdsMap = new HashMap<>();
			InterfaceWrapperHelper.setDynAttribute(toOrder, DYNATTR_OrderCompensationGroupIdsMap, orderCompensationGroupIdsMap);
		}

		final int toOrderId = toOrder.getC_Order_ID();
		final int orderCompensationGroupIdNew = orderCompensationGroupIdsMap.computeIfAbsent(orderCompensationGroupId, k -> cloneOrderCompensationGroup(orderCompensationGroupId, toOrderId));
		return orderCompensationGroupIdNew;
	}

	private static int cloneOrderCompensationGroup(final int orderCompensationGroupId, final int toOrderId)
	{
		final I_C_Order_CompensationGroup orderCompensationGroup = load(orderCompensationGroupId, I_C_Order_CompensationGroup.class);
		final I_C_Order_CompensationGroup orderCompensationGroupNew = newInstance(I_C_Order_CompensationGroup.class);
		InterfaceWrapperHelper.copyValues(orderCompensationGroup, orderCompensationGroupNew);
		orderCompensationGroupNew.setC_Order_ID(toOrderId);
		orderCompensationGroupNew.setPP_Product_BOM_ID(-1); // don't copy the Quotation BOM; another one has to be created
		InterfaceWrapperHelper.save(orderCompensationGroupNew);
		return orderCompensationGroupNew.getC_Order_CompensationGroup_ID();
	}
}

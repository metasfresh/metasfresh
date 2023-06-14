package de.metas.order.copy;

import de.metas.adempiere.model.I_C_Order;
import de.metas.copy_with_details.GeneralCopyRecordSupport;
import de.metas.copy_with_details.template.CopyTemplate;
import de.metas.document.dimension.OrderLineDimensionFactory;
import de.metas.order.OrderFreightCostsService;
import de.metas.order.OrderLineId;
import de.metas.util.Check;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Order_CompensationGroup;
import org.compiere.model.PO;

import java.util.HashMap;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

public class C_OrderLine_CopyRecordSupport extends GeneralCopyRecordSupport
{
	private final OrderFreightCostsService ordersFreightCostService = SpringContextHolder.instance.getBean(OrderFreightCostsService.class);

	private static final String DYNATTR_OrderCompensationGroupIdsMap = "OrderCompensationGroupIdsMap";

	private final OrderLineDimensionFactory orderLineDimensionFactory = SpringContextHolder.instance.getBean(OrderLineDimensionFactory.class);

	/**
	 * @return true if the record shall be copied
	 */
	@Override
	protected boolean isCopyRecord(final PO fromPO)
	{
		final I_C_OrderLine fromOrderLine = InterfaceWrapperHelper.create(fromPO, I_C_OrderLine.class);

		return !fromOrderLine.isPackagingMaterial()
				&& !ordersFreightCostService.isFreightCostOrderLine(fromOrderLine);
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

		orderLineDimensionFactory.updateRecord(toOrderLine, orderLineDimensionFactory.getFromRecord(fromOrderLine));
	}

	@Override
	protected void onRecordAndChildrenCopied(final PO to, final PO from, final CopyTemplate template)
	{
		final I_C_OrderLine toOrderLine = InterfaceWrapperHelper.create(to, I_C_OrderLine.class);
		final I_C_OrderLine fromOrderLine = InterfaceWrapperHelper.create(from, I_C_OrderLine.class);
		onRecordAndChildrenCopied(toOrderLine, fromOrderLine);
	}

	private void onRecordAndChildrenCopied(final I_C_OrderLine toOrderLine, final I_C_OrderLine fromOrderLine)
	{
		ClonedOrderLinesInfo.getOrCreate(getTargetOrder())
				.addOriginalToClonedOrderLineMapping(
						OrderLineId.ofRepoId(fromOrderLine.getC_OrderLine_ID()),
						OrderLineId.ofRepoId(toOrderLine.getC_OrderLine_ID()));
	}

	private I_C_Order getTargetOrder() {return Check.assumeNotNull(getParentModel(I_C_Order.class), "target order is not null");}

	private int getOrCloneOrderCompensationGroup(final int orderCompensationGroupId)
	{
		if (orderCompensationGroupId <= 0)
		{
			return -1;
		}

		final I_C_Order toOrder = getTargetOrder();
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

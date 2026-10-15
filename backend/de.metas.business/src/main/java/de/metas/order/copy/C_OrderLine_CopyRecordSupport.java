package de.metas.order.copy;

import de.metas.adempiere.model.I_C_Order;
import de.metas.copy_with_details.GeneralCopyRecordSupport;
import de.metas.copy_with_details.template.CopyTemplate;
import de.metas.document.dimension.OrderLineDimensionFactory;
import de.metas.order.OrderFreightCostsService;
import de.metas.order.OrderLineId;
import de.metas.order.compensationGroup.OrderGroupRepository;
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
	 * Skips the lines that the system generates itself and the target order gets anew: packing-material lines, freight-cost lines
	 * and the compensation (e.g. bonus discount) lines of a contract-created compensation group (one whose header carries a
	 * {@code C_Flatrate_Term_ID}, see {@link OrderGroupRepository#createContractCreatedGroupsQueryBuilder()}); the latter are
	 * recreated when the target order is prepared for completion.
	 *
	 * @return true if the record shall be copied
	 */
	@Override
	protected boolean isCopyRecord(final PO fromPO)
	{
		final I_C_OrderLine fromOrderLine = InterfaceWrapperHelper.create(fromPO, I_C_OrderLine.class);

		return !fromOrderLine.isPackagingMaterial()
				&& !ordersFreightCostService.isFreightCostOrderLine(fromOrderLine)
				&& !(fromOrderLine.isGroupCompensationLine() && isContractCreatedGroup(fromOrderLine.getC_Order_CompensationGroup_ID()));
	}

	@Override
	protected void onRecordCopied(final PO to, final PO from, final CopyTemplate template)
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

	/**
	 * @return the target order's copy of the given compensation group (cloned on first use), or {@code -1} when the line is not grouped
	 * or its group is contract-created: such a group is not cloned, because it is recreated, together with the regrouping of the
	 * target order's lines, when the target order is prepared for completion.
	 */
	private int getOrCloneOrderCompensationGroup(final int orderCompensationGroupId)
	{
		if (orderCompensationGroupId <= 0 || isContractCreatedGroup(orderCompensationGroupId))
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

	private static boolean isContractCreatedGroup(final int orderCompensationGroupId)
	{
		return orderCompensationGroupId > 0
				&& OrderGroupRepository.createContractCreatedGroupsQueryBuilder()
				.addEqualsFilter(I_C_Order_CompensationGroup.COLUMNNAME_C_Order_CompensationGroup_ID, orderCompensationGroupId)
				.create()
				.anyMatch();
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

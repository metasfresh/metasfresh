package org.eevolution.api.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Maps;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.pair.ImmutablePair;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.material.planning.pporder.PPAlwaysAvailableToUser;
import de.metas.material.planning.pporder.PPOrderTargetPlanningStatus;
import de.metas.material.planning.pporder.PPRoutingActivityId;
import de.metas.material.planning.pporder.PPRoutingActivityTemplateId;
import de.metas.material.planning.pporder.PPRoutingActivityType;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.material.planning.pporder.UserInstructions;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.workflow.WFDurationUnit;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IPPOrderRoutingRepository;
import org.eevolution.api.PPOrderActivityScheduleChangeRequest;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderRouting;
import org.eevolution.api.PPOrderRoutingActivity;
import org.eevolution.api.PPOrderRoutingActivityCode;
import org.eevolution.api.PPOrderRoutingActivityId;
import org.eevolution.api.PPOrderRoutingActivityStatus;
import org.eevolution.api.PPOrderRoutingProduct;
import org.eevolution.api.PPOrderRoutingProductId;
import org.eevolution.model.I_PP_Order_Node;
import org.eevolution.model.I_PP_Order_NodeNext;
import org.eevolution.model.I_PP_Order_Node_Product;
import org.eevolution.model.I_PP_Order_Workflow;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class PPOrderRoutingRepository implements IPPOrderRoutingRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IProductBL productBL = Services.get(IProductBL.class);

	@Override
	public PPOrderRouting getByOrderId(@NonNull final PPOrderId orderId)
	{
		//
		// Order Routing header
		final I_PP_Order_Workflow orderRoutingRecord = retrieveOrderWorkflowOrNull(orderId);
		Check.assumeNotNull(orderRoutingRecord, "Parameter orderWorkflow is not null");
		final WFDurationUnit durationUnit = WFDurationUnit.ofCode(orderRoutingRecord.getDurationUnit());
		final int unitsPerCycle = orderRoutingRecord.getUnitsCycles().intValue();

		//
		// Order Activities
		final ImmutableList<PPOrderRoutingActivity> orderActivities = retrieveOrderNodes(orderId)
				.stream()
				.map(orderActivityRecord -> toPPOrderRoutingActivity(orderActivityRecord, durationUnit, unitsPerCycle))
				.collect(ImmutableList.toImmutableList());

		final ImmutableList<PPOrderRoutingProduct> orderProducts = retrieveOrderNodeProducts(orderId)
				.stream()
				.map(this::toPPOrderRoutingProduct)
				.collect(ImmutableList.toImmutableList());

		final ImmutableMap<Integer, PPOrderRoutingActivityCode> activityCodesByRepoId = orderActivities
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						orderActivity -> orderActivity.getId().getRepoId(),
						PPOrderRoutingActivity::getCode));

		//
		// First Activity Code
		final PPOrderRoutingActivityCode firstActivityCode = activityCodesByRepoId.get(orderRoutingRecord.getPP_Order_Node_ID());

		//
		// Order Activities Transitions
		final ImmutableSetMultimap<PPOrderRoutingActivityCode, PPOrderRoutingActivityCode> codeToNextCodeMap = retrieveOrderNodeNexts(orderId)
				.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(
						nodeNextRecord -> activityCodesByRepoId.get(nodeNextRecord.getPP_Order_Node_ID()),
						nodeNextRecord -> activityCodesByRepoId.get(nodeNextRecord.getPP_Order_Next_ID())));

		return PPOrderRouting.builder()
				.ppOrderId(orderId)
				.routingId(PPRoutingId.ofRepoId(orderRoutingRecord.getAD_Workflow_ID()))
				.durationUnit(durationUnit)
				.qtyPerBatch(orderRoutingRecord.getQtyBatchSize())
				//
				.firstActivityCode(firstActivityCode)
				.activities(orderActivities)
				.products(orderProducts)
				.codeToNextCodeMap(codeToNextCodeMap)
				//
				.build();
	}

	private PPOrderRoutingProduct toPPOrderRoutingProduct(final I_PP_Order_Node_Product product)
	{
		final PPOrderId orderId = PPOrderId.ofRepoId(product.getPP_Order_ID());
		final PPOrderRoutingActivityId activityId = PPOrderRoutingActivityId.ofRepoId(orderId, product.getPP_Order_Node_ID());
		final PPOrderRoutingProductId id = PPOrderRoutingProductId.ofRepoId(activityId, product.getPP_Order_Node_Product_ID());
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());
		return PPOrderRoutingProduct.builder()
				.id(id)
				.productId(productId)
				.qty(Quantity.of(product.getQty(), productBL.getStockUOM(productId)))
				.subcontracting(product.isSubcontracting())
				.seqNo(product.getSeqNo())
				.build();
	}

	@Override
	public PPOrderRoutingActivity getOrderRoutingActivity(@NonNull final PPOrderRoutingActivityId orderRoutingActivityId)
	{
		//
		// Order Routing header
		final PPOrderId orderId = orderRoutingActivityId.getOrderId();
		final I_PP_Order_Workflow orderRoutingRecord = retrieveOrderWorkflowOrNull(orderId);
		Check.assumeNotNull(orderRoutingRecord, "Parameter orderWorkflow is not null");
		final WFDurationUnit durationUnit = WFDurationUnit.ofCode(orderRoutingRecord.getDurationUnit());
		final int unitsPerCycle = orderRoutingRecord.getUnitsCycles().intValue();

		final I_PP_Order_Node orderActivityRecord = load(orderRoutingActivityId, I_PP_Order_Node.class);
		return toPPOrderRoutingActivity(orderActivityRecord, durationUnit, unitsPerCycle);
	}

	@Override
	public void deleteByOrderId(@NonNull final PPOrderId orderId)
	{
		trxManager.runInThreadInheritedTrx(() -> deleteByOrderIdInTrx(orderId));
	}

	public void deleteByOrderIdInTrx(@NonNull final PPOrderId orderId)
	{
		//
		// Set PP_Order_Workflow.PP_Order_Node_ID to null
		// ... to be able to delete nodes first
		final I_PP_Order_Workflow orderWorkflow = retrieveOrderWorkflowOrNull(orderId);
		if (orderWorkflow != null)
		{
			orderWorkflow.setPP_Order_Node_ID(-1);
			saveRecord(orderWorkflow);
		}

		//
		// Delete PP_Order_NodeNext
		for (final I_PP_Order_NodeNext orderNodeNext : retrieveOrderNodeNexts(orderId))
		{
			InterfaceWrapperHelper.delete(orderNodeNext);
		}

		for (final I_PP_Order_Node_Product products : retrieveOrderNodeProducts(orderId))
		{
			InterfaceWrapperHelper.delete(products);
		}
		//
		// Delete PP_Order_Node
		for (final I_PP_Order_Node orderNode : retrieveOrderNodes(orderId))
		{
			InterfaceWrapperHelper.delete(orderNode);
		}

		//
		// Delete PP_Order_Workflow
		// (after everything else which depends on this was deleted)
		if (orderWorkflow != null)
		{
			InterfaceWrapperHelper.delete(orderWorkflow);
		}
	}

	@Nullable
	private I_PP_Order_Workflow retrieveOrderWorkflowOrNull(@NonNull final PPOrderId orderId)
	{
		return queryBL.createQueryBuilder(I_PP_Order_Workflow.class)
				.addEqualsFilter(I_PP_Order_Workflow.COLUMNNAME_PP_Order_ID, orderId)
				.create()
				.firstOnly(I_PP_Order_Workflow.class);
	}

	private List<I_PP_Order_Node> retrieveOrderNodes(final PPOrderId orderId)
	{
		return queryBL
				.createQueryBuilder(I_PP_Order_Node.class)
				.addEqualsFilter(I_PP_Order_Node.COLUMNNAME_PP_Order_ID, orderId)
				.create()
				.list();
	}

	private List<I_PP_Order_Node_Product> retrieveOrderNodeProducts(final PPOrderId orderId)
	{
		return queryBL
				.createQueryBuilder(I_PP_Order_Node_Product.class)
				.addEqualsFilter(I_PP_Order_Node_Product.COLUMNNAME_PP_Order_ID, orderId)
				.create()
				.list();
	}

	private List<I_PP_Order_NodeNext> retrieveOrderNodeNexts(@NonNull final PPOrderId orderId)
	{
		return queryBL
				.createQueryBuilder(I_PP_Order_NodeNext.class)
				.addEqualsFilter(I_PP_Order_NodeNext.COLUMNNAME_PP_Order_ID, orderId)
				.create()
				.list();
	}

	@Override
	public PPOrderRoutingActivity getFirstActivity(@NonNull final PPOrderId orderId)
	{
		final I_PP_Order_Workflow orderWorkflow = retrieveOrderWorkflowOrNull(orderId);
		final PPOrderRoutingActivityId firstActivityId = PPOrderRoutingActivityId.ofRepoId(orderId, orderWorkflow.getPP_Order_Node_ID());
		return getOrderRoutingActivity(firstActivityId);
	}

	private PPOrderRoutingActivity toPPOrderRoutingActivity(
			@NonNull final I_PP_Order_Node record,
			@NonNull final WFDurationUnit durationUnit,
			final int unitsPerCycle)
	{
		final PPOrderId orderId = PPOrderId.ofRepoId(record.getPP_Order_ID());

		final PPRoutingId routingId = PPRoutingId.ofRepoId(record.getAD_Workflow_ID());

		final ResourceId resourceId = ResourceId.ofRepoId(record.getS_Resource_ID());
		final UomId uomId = UomId.ofRepoId(record.getC_UOM_ID());

		return PPOrderRoutingActivity.builder()
				.id(PPOrderRoutingActivityId.ofRepoId(orderId, record.getPP_Order_Node_ID()))
				.type(PPRoutingActivityType.ofCode(record.getPP_Activity_Type()))
				.code(PPOrderRoutingActivityCode.ofString(record.getValue()))
				.name(record.getName())
				.routingActivityId(PPRoutingActivityId.ofRepoId(routingId, record.getAD_WF_Node_ID()))
				//
				.subcontracting(record.isSubcontracting())
				.subcontractingVendorId(BPartnerId.ofRepoIdOrNull(record.getC_BPartner_ID()))
				//
				.milestone(record.isMilestone())
				.alwaysAvailableToUser(PPAlwaysAvailableToUser.ofNullableCode(record.getPP_AlwaysAvailableToUser()))
				.userInstructions(UserInstructions.ofNullableString(record.getPP_UserInstructions()))
				.targetPlanningStatus(PPOrderTargetPlanningStatus.ofNullableCode(record.getTargetPlanningStatus()))
				//
				.resourceId(resourceId)
				//
				.status(PPOrderRoutingActivityStatus.ofDocStatus(record.getDocStatus()))
				//
				// Standard values
				.durationUnit(durationUnit)
				.queuingTime(Duration.of(record.getQueuingTime(), durationUnit.getTemporalUnit()))
				.setupTime(Duration.of(record.getSetupTime(), durationUnit.getTemporalUnit()))
				.waitingTime(Duration.of(record.getWaitingTime(), durationUnit.getTemporalUnit()))
				.movingTime(Duration.of(record.getMovingTime(), durationUnit.getTemporalUnit()))
				.durationPerOneUnit(Duration.of(record.getDuration(), durationUnit.getTemporalUnit()))
				.unitsPerCycle(unitsPerCycle)
				//
				// Planned values
				.setupTimeRequired(Duration.of(record.getSetupTimeRequiered(), durationUnit.getTemporalUnit()))
				.durationRequired(Duration.of(record.getDurationRequiered(), durationUnit.getTemporalUnit()))
				.qtyRequired(Quantitys.of(record.getQtyRequiered(), uomId))
				//
				// Reported values
				.setupTimeReal(Duration.of(record.getSetupTimeReal(), durationUnit.getTemporalUnit()))
				.durationReal(Duration.of(record.getDurationReal(), durationUnit.getTemporalUnit()))
				.qtyDelivered(Quantitys.of(record.getQtyDelivered(), uomId))
				.qtyScrapped(Quantitys.of(record.getQtyScrap(), uomId))
				.qtyRejected(Quantitys.of(record.getQtyReject(), uomId))
				.dateStart(TimeUtil.asInstant(record.getDateStart()))
				.dateFinish(TimeUtil.asInstant(record.getDateFinish()))
				.alwaysAvailableToUser(CoalesceUtil.coalesceNotNull(PPAlwaysAvailableToUser.ofNullableCode(record.getPP_AlwaysAvailableToUser()), PPAlwaysAvailableToUser.DEFAULT))
				//
				.scannedQRCode(GlobalQRCode.ofNullableString(record.getScannedQRCode()))
				//
				.build();
	}

	@Override
	public void changeActivitiesScheduling(@NonNull final PPOrderId orderId, @NonNull final List<PPOrderActivityScheduleChangeRequest> changeRequests)
	{
		trxManager.runInThreadInheritedTrx(() -> changeActivitiesSchedulingInTrx(orderId, changeRequests));
	}

	public void changeActivitiesSchedulingInTrx(@NonNull final PPOrderId orderId, @NonNull final List<PPOrderActivityScheduleChangeRequest> changeRequests)
	{
		final Map<PPOrderRoutingActivityId, PPOrderActivityScheduleChangeRequest> changeRequestsByActivityId = Maps.uniqueIndex(changeRequests, PPOrderActivityScheduleChangeRequest::getOrderRoutingActivityId);
		for (final I_PP_Order_Node orderActivity : retrieveOrderNodes(orderId))
		{
			final PPOrderRoutingActivityId orderRoutingActivityId = PPOrderRoutingActivityId.ofRepoId(orderId, orderActivity.getPP_Order_Node_ID());
			final PPOrderActivityScheduleChangeRequest activityChangeRequest = changeRequestsByActivityId.get(orderRoutingActivityId);
			if (activityChangeRequest == null)
			{
				continue;
			}

			applyActivityChanges(orderActivity, activityChangeRequest);
		}
	}

	private void applyActivityChanges(@NonNull final I_PP_Order_Node orderActivity, @NonNull final PPOrderActivityScheduleChangeRequest activityChangeRequest)
	{
		orderActivity.setDateStartSchedule(TimeUtil.asTimestamp(activityChangeRequest.getScheduledStartDate()));
		orderActivity.setDateFinishSchedule(TimeUtil.asTimestamp(activityChangeRequest.getScheduledEndDate()));
		saveRecord(orderActivity);
	}

	@Override
	public void save(@NonNull final PPOrderRouting orderRouting)
	{
		trxManager.runInThreadInheritedTrx(() -> saveInTrx(orderRouting));
	}

	private void saveInTrx(@NonNull final PPOrderRouting orderRouting)
	{
		//
		// Order Routing header
		final PPOrderId orderId = orderRouting.getPpOrderId();
		I_PP_Order_Workflow routingRecord = retrieveOrderWorkflowOrNull(orderId);
		if (routingRecord == null)
		{
			routingRecord = toNewOrderWorkflowRecord(orderRouting);
		}
		else
		{
			updateOrderWorkflowRecord(routingRecord, orderRouting);
		}
		// NOTE: first activity will be set later, see below...
		saveRecord(routingRecord);
		final int ppOrderWorkflowId = routingRecord.getPP_Order_Workflow_ID();

		//
		// Order Activities
		final Collection<I_PP_Order_Node> activityRecordsToDelete;
		final Map<Integer, PPOrderRoutingActivityId> wfNodeToActivityId = new HashMap<>();
		{
			final HashMap<PPOrderRoutingActivityId, I_PP_Order_Node> existingActivityRecords = retrieveOrderNodes(orderId)
					.stream()
					.collect(GuavaCollectors.toHashMapByKey(this::extractPPOrderRoutingActivityId));

			// Create/Update
			for (final PPOrderRoutingActivity activity : orderRouting.getActivities())
			{
				I_PP_Order_Node activityRecord = existingActivityRecords.remove(activity.getId());
				if (activityRecord == null)
				{
					activityRecord = toNewOrderNodeRecord(activity, orderId, ppOrderWorkflowId);
				}
				else
				{
					updateOrderNodeRecord(activityRecord, activity);
				}

				saveRecord(activityRecord);
				final PPOrderRoutingActivityId id = extractPPOrderRoutingActivityId(activityRecord);
				wfNodeToActivityId.put(activity.getRoutingActivityId().getRepoId(), id);
				activity.setId(id);
			}

			//
			activityRecordsToDelete = existingActivityRecords.values();
		}

		final Collection<I_PP_Order_Node_Product> productRecordsToDelete;
		{
			final HashMap<PPOrderRoutingProductId, I_PP_Order_Node_Product> existingProductRecords = retrieveOrderNodeProducts(orderId)
					.stream()
					.collect(GuavaCollectors.toHashMapByKey(this::extractPPOrderRoutingProductId));

			// Create/Update
			for (final PPOrderRoutingProduct product : orderRouting.getProducts())
			{
				I_PP_Order_Node_Product productRecord = existingProductRecords.remove(product.getId());
				if (productRecord == null)
				{
					productRecord = toNewOrderNodeProductRecord(product, orderId, ppOrderWorkflowId, wfNodeToActivityId);
				}
				else
				{
					updateOrderNodeProductRecord(productRecord, product);
				}

				saveRecord(productRecord);
			}

			//
			productRecordsToDelete = existingProductRecords.values();
		}

		//
		// Set First Activity
		routingRecord.setPP_Order_Node_ID(orderRouting.getFirstActivity().getId().getRepoId());
		saveRecord(routingRecord);
		//
		// Transitions
		{
			final ArrayListMultimap<ImmutablePair<PPOrderRoutingActivityId, PPOrderRoutingActivityId>, I_PP_Order_NodeNext> allExistingNodeNexts = retrieveOrderNodeNexts(orderId)
					.stream()
					.collect(GuavaCollectors.toArrayListMultimapByKey(this::extractCurrentAndNextActivityIdPair));

			for (final PPOrderRoutingActivity activity : orderRouting.getActivities())
			{
				final PPOrderRoutingActivityId activityId = activity.getId();

				for (final PPOrderRoutingActivity nextActivity : orderRouting.getNextActivities(activity))
				{
					final PPOrderRoutingActivityId nextActivityId = nextActivity.getId();

					final List<I_PP_Order_NodeNext> existingNodeNexts = allExistingNodeNexts.removeAll(ImmutablePair.of(activityId, nextActivityId));
					if (existingNodeNexts.isEmpty())
					{
						final I_PP_Order_NodeNext nodeNextRecord = toNewOrderNodeNextRecord(activity, nextActivity);
						saveRecord(nodeNextRecord);
					}
					else
					{
						final I_PP_Order_NodeNext nodeNextRecord = existingNodeNexts.remove(0);
						updateOrderNodeNextRecord(nodeNextRecord, activity, nextActivity);
						saveRecord(nodeNextRecord);

						InterfaceWrapperHelper.deleteAll(existingNodeNexts);
					}
				}
			}
		}

		//
		// Delete remaining nodes if any
		InterfaceWrapperHelper.deleteAll(productRecordsToDelete);
		InterfaceWrapperHelper.deleteAll(activityRecordsToDelete);
	}

	private void updateOrderNodeProductRecord(final I_PP_Order_Node_Product record, final PPOrderRoutingProduct product)
	{
		record.setM_Product_ID(product.getProductId().getRepoId());
		if (product.getQty() != null)
		{
			record.setQty(product.getQty().toBigDecimal());
		}
		record.setSeqNo(product.getSeqNo());
		record.setIsSubcontracting(product.isSubcontracting());
		record.setSpecification(product.getSpecification());
	}

	private I_PP_Order_Node_Product toNewOrderNodeProductRecord(final PPOrderRoutingProduct product, final PPOrderId orderId, final int ppOrderWorkflowId, final Map<Integer, PPOrderRoutingActivityId> wfNodeToActivityId)
	{
		final I_PP_Order_Node_Product record = InterfaceWrapperHelper.newInstance(I_PP_Order_Node_Product.class);
		record.setPP_Order_ID(orderId.getRepoId());
		record.setPP_Order_Workflow_ID(ppOrderWorkflowId);
		record.setPP_Order_Node_ID(wfNodeToActivityId.get(product.getId().getActivityId().getRepoId()).getRepoId());

		updateOrderNodeProductRecord(record, product);

		return record;
	}

	private ImmutablePair<PPOrderRoutingActivityId, PPOrderRoutingActivityId> extractCurrentAndNextActivityIdPair(final I_PP_Order_NodeNext record)
	{
		final PPOrderId orderId = PPOrderId.ofRepoId(record.getPP_Order_ID());
		return ImmutablePair.of(
				PPOrderRoutingActivityId.ofRepoId(orderId, record.getPP_Order_Node_ID()),
				PPOrderRoutingActivityId.ofRepoId(orderId, record.getPP_Order_Next_ID()));
	}

	private PPOrderRoutingActivityId extractPPOrderRoutingActivityId(final I_PP_Order_Node record)
	{
		final PPOrderId orderId = PPOrderId.ofRepoId(record.getPP_Order_ID());
		return PPOrderRoutingActivityId.ofRepoId(orderId, record.getPP_Order_Node_ID());
	}

	private PPOrderRoutingProductId extractPPOrderRoutingProductId(final I_PP_Order_Node_Product record)
	{
		final PPOrderId orderId = PPOrderId.ofRepoId(record.getPP_Order_ID());
		final PPOrderRoutingActivityId activityId = PPOrderRoutingActivityId.ofRepoId(orderId, record.getPP_Order_Node_ID());
		return PPOrderRoutingProductId.ofRepoId(activityId, record.getPP_Order_Node_Product_ID());
	}

	private I_PP_Order_Workflow toNewOrderWorkflowRecord(final PPOrderRouting from)
	{
		final I_PP_Order_Workflow record = InterfaceWrapperHelper.newInstance(I_PP_Order_Workflow.class);
		record.setPP_Order_ID(from.getPpOrderId().getRepoId());

		record.setWaitingTime(0);
		record.setWorkingTime(0);
		record.setDurationLimit(0);
		record.setQueuingTime(0);
		record.setSetupTime(0);
		record.setMovingTime(0);
		record.setDuration(0);

		updateOrderWorkflowRecord(record, from);

		return record;
	}

	private void updateOrderWorkflowRecord(final I_PP_Order_Workflow record, final PPOrderRouting from)
	{
		record.setIsActive(true);
		record.setAD_Workflow_ID(from.getRoutingId().getRepoId());
		record.setDurationUnit(from.getDurationUnit().getCode());
		record.setQtyBatchSize(from.getQtyPerBatch());
	}

	private I_PP_Order_Node toNewOrderNodeRecord(
			final PPOrderRoutingActivity activity,
			final PPOrderId ppOrderId,
			final int ppOrderWorkflowId)
	{
		final I_PP_Order_Node record = InterfaceWrapperHelper.newInstance(I_PP_Order_Node.class);
		record.setPP_Order_ID(ppOrderId.getRepoId());
		record.setPP_Order_Workflow_ID(ppOrderWorkflowId);

		updateOrderNodeRecord(record, activity);

		return record;
	}

	private void updateOrderNodeRecord(final I_PP_Order_Node record, final PPOrderRoutingActivity from)
	{
		final WFDurationUnit durationUnit = from.getDurationUnit();

		record.setPP_Activity_Type(from.getType().getCode());

		record.setIsActive(true);
		record.setValue(from.getCode().getAsString());
		record.setName(from.getName());

		record.setAD_Workflow_ID(from.getRoutingActivityId().getRoutingId().getRepoId());
		record.setAD_WF_Node_ID(from.getRoutingActivityId().getRepoId());

		record.setIsSubcontracting(from.isSubcontracting());
		record.setC_BPartner_ID(BPartnerId.toRepoId(from.getSubcontractingVendorId()));

		record.setIsMilestone(from.isMilestone());
		record.setPP_AlwaysAvailableToUser(from.getAlwaysAvailableToUser().getCode());
		record.setPP_UserInstructions(from.getUserInstructions() != null ? from.getUserInstructions().getAsString() : null);
		record.setTargetPlanningStatus(from.getTargetPlanningStatus() != null ? from.getTargetPlanningStatus().getCode() : null);

		record.setS_Resource_ID(from.getResourceId().getRepoId());

		record.setDocStatus(from.getStatus().getDocStatus());

		//
		// Standard values
		record.setSetupTime(durationUnit.toInt(from.getSetupTime()));
		record.setSetupTimeRequiered(durationUnit.toInt(from.getSetupTime()));
		record.setMovingTime(durationUnit.toInt(from.getMovingTime()));
		record.setWaitingTime(durationUnit.toInt(from.getWaitingTime()));
		record.setQueuingTime(durationUnit.toInt(from.getQueuingTime()));
		record.setDuration(durationUnit.toInt(from.getDurationPerOneUnit()));
		record.setDurationRequiered(durationUnit.toInt(from.getDurationRequired()));

		//
		// Planned values
		record.setSetupTimeRequiered(durationUnit.toInt(from.getSetupTimeRequired()));
		record.setDurationRequiered(durationUnit.toInt(from.getDurationRequired()));
		record.setQtyRequiered(from.getQtyRequired().toBigDecimal());
		record.setC_UOM_ID(from.getQtyRequired().getUomId().getRepoId());

		//
		// Reported values
		record.setSetupTimeReal(durationUnit.toInt(from.getSetupTimeReal()));
		record.setDurationReal(durationUnit.toInt(from.getDurationReal()));
		record.setQtyDelivered(from.getQtyDelivered().toBigDecimal());
		record.setQtyScrap(from.getQtyScrapped().toBigDecimal());
		record.setQtyReject(from.getQtyRejected().toBigDecimal());
		record.setDateStart(TimeUtil.asTimestamp(from.getDateStart()));
		record.setDateFinish(TimeUtil.asTimestamp(from.getDateFinish()));

		final PPRoutingActivityTemplateId activityTemplateId = from.getActivityTemplateId();
		record.setAD_WF_Node_Template_ID(PPRoutingActivityTemplateId.toRepoId(activityTemplateId));

		record.setScannedQRCode(from.getScannedQRCode() != null ? from.getScannedQRCode().getAsString() : null);
	}

	private I_PP_Order_NodeNext toNewOrderNodeNextRecord(final PPOrderRoutingActivity activity, final PPOrderRoutingActivity nextActivity)
	{
		final I_PP_Order_NodeNext record = InterfaceWrapperHelper.newInstance(I_PP_Order_NodeNext.class);
		updateOrderNodeNextRecord(record, activity, nextActivity);
		return record;
	}

	private void updateOrderNodeNextRecord(final I_PP_Order_NodeNext record, final PPOrderRoutingActivity activity, final PPOrderRoutingActivity nextActivity)
	{
		final PPOrderId orderId = activity.getOrderId();

		// record.setAD_Org_ID(orderNode.getAD_Org_ID());
		record.setPP_Order_ID(orderId.getRepoId());

		record.setPP_Order_Node_ID(activity.getId().getRepoId());
		record.setAD_WF_Node_ID(activity.getRoutingActivityId().getRepoId());

		record.setPP_Order_Next_ID(nextActivity.getId().getRepoId());
		record.setAD_WF_Next_ID(nextActivity.getRoutingActivityId().getRepoId());

		record.setEntityType("U");
		// record.setIsStdUserWorkflow(wfNodeNext.isStdUserWorkflow());
		record.setSeqNo(10);
		// record.setTransitionCode();
	}

}

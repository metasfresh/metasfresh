package de.metas.handlingunits.pporder.source_hu;

import com.google.common.collect.ImmutableSet;
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleService;
import de.metas.i18n.BooleanWithReason;
import de.metas.util.Services;
import lombok.NonNull;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Service;

@Service
public class PPOrderSourceHUService
{
	private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final PPOrderSourceHURepository ppOrderSourceHURepository;
	private final PPOrderIssueScheduleService ppOrderIssueScheduleService;

	public PPOrderSourceHUService(
			@NonNull final PPOrderSourceHURepository ppOrderSourceHURepository,
			@NonNull final PPOrderIssueScheduleService ppOrderIssueScheduleService)
	{
		this.ppOrderSourceHURepository = ppOrderSourceHURepository;
		this.ppOrderIssueScheduleService = ppOrderIssueScheduleService;
	}

	public void addSourceHU(@NonNull final PPOrderId ppOrderId, @NonNull final HuId huId)
	{
		checkEligibleToAddToManufacturingOrder(ppOrderId).assertTrue();
		checkEligibleToAddAsSourceHU(huId).assertTrue();
		ppOrderSourceHURepository.addSourceHU(ppOrderId, huId);
	}

	public BooleanWithReason checkEligibleToAddAsSourceHU(@NonNull final HuId huId)
	{
		final I_M_HU hu = handlingUnitsBL.getById(huId);
		if (!X_M_HU.HUSTATUS_Active.equals(hu.getHUStatus()))
		{
			return BooleanWithReason.falseBecause("HU is not active");
		}

		if (!handlingUnitsBL.isTopLevel(hu))
		{
			return BooleanWithReason.falseBecause("HU is not top level");
		}

		return BooleanWithReason.TRUE;
	}

	public BooleanWithReason checkEligibleToAddToManufacturingOrder(@NonNull final PPOrderId ppOrderId)
	{
		final I_PP_Order ppOrder = ppOrderBL.getById(ppOrderId);
		final DocStatus ppOrderDocStatus = DocStatus.ofNullableCodeOrUnknown(ppOrder.getDocStatus());
		if (!ppOrderDocStatus.isCompleted())
		{
			return BooleanWithReason.falseBecause("Order is not completed");
		}

		if (ppOrderIssueScheduleService.matchesByOrderId(ppOrderId))
		{
			return BooleanWithReason.falseBecause("Manufacturing Job already started");
		}

		return BooleanWithReason.TRUE;
	}

	public ImmutableSet<HuId> getSourceHUIds(@NonNull final PPOrderId ppOrderId)
	{
		return ppOrderSourceHURepository.getSourceHUIds(ppOrderId);
	}
}

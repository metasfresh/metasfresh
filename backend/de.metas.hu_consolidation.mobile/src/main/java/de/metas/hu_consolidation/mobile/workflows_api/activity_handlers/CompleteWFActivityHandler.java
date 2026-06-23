package de.metas.hu_consolidation.mobile.workflows_api.activity_handlers;

import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.handlingunits.grai.HUGraiService;
import de.metas.handlingunits.grai.HUGraiSnapshot;
import de.metas.hu_consolidation.mobile.HUConsolidationApplication;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJob;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobService;
import de.metas.hu_consolidation.mobile.job.HUConsolidationTarget;
import de.metas.i18n.IMsgBL;
import de.metas.util.Services;
import de.metas.workflow.rest_api.activity_features.user_confirmation.UserConfirmationRequest;
import de.metas.workflow.rest_api.activity_features.user_confirmation.UserConfirmationSupport;
import de.metas.workflow.rest_api.activity_features.user_confirmation.UserConfirmationSupportUtil;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import de.metas.workflow.rest_api.model.UIComponent;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import de.metas.workflow.rest_api.model.WFActivityType;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.service.WFActivityHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

import static de.metas.hu_consolidation.mobile.HUConsolidationApplication.getHUConsolidationJob;
import static de.metas.workflow.rest_api.service.Constants.ARE_YOU_SURE;

@Component
@RequiredArgsConstructor
public class CompleteWFActivityHandler implements WFActivityHandler, UserConfirmationSupport
{
	public static final WFActivityType HANDLED_ACTIVITY_TYPE = WFActivityType.ofString("huConsolidation.complete");

	@NonNull private final IMsgBL msgBL = Services.get(IMsgBL.class);
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final HUConsolidationJobService jobService;
	@NonNull private final HUGraiService huGraiService;

	/**
	 * Lazily injected to break the mutual dependency:
	 * HUConsolidationApplication (lazy) → CompleteWFActivityHandler → HUConsolidationApplication.
	 */
	@Autowired
	@Lazy
	private HUConsolidationApplication huConsolidationApplication;

	@Override
	public WFActivityType getHandledActivityType()
	{
		return HANDLED_ACTIVITY_TYPE;
	}

	@Override
	public UIComponent getUIComponent(
			final @NonNull WFProcess wfProcess,
			final @NonNull WFActivity wfActivity,
			final @NonNull JsonOpts jsonOpts)
	{
		return UserConfirmationSupportUtil.createUIComponent(
				UserConfirmationSupportUtil.UIComponentProps.builderFrom(wfActivity)
						.question(msgBL.getMsg(jsonOpts.getAdLanguage(), ARE_YOU_SURE))
						.build());
	}

	@Override
	public WFActivityStatus computeActivityState(final WFProcess wfProcess, final WFActivity completeDistributionWFActivity)
	{
		final HUConsolidationJob job = getHUConsolidationJob(wfProcess);
		return computeActivityState(job);
	}

	/**
	 * Package-private: also called directly from tests.
	 * Returns {@link WFActivityStatus#COMPLETED} when GRAI scan is not required or all required GRAIs
	 * on the current target LU are assigned; returns {@link WFActivityStatus#NOT_STARTED} (not-ready)
	 * when GRAI scan is required but the current target LU has unfilled TU slots.
	 */
	public WFActivityStatus computeActivityState(@NonNull final HUConsolidationJob job)
	{
		if (!isGraiScanEnabled(job))
		{
			return WFActivityStatus.COMPLETED;
		}

		final HUGraiSnapshot snapshot = getTargetLUSnapshot(job);
		if (snapshot == null || snapshot.isAllGraisAssigned())
		{
			return WFActivityStatus.COMPLETED;
		}

		return WFActivityStatus.NOT_STARTED;
	}

	/**
	 * Validates GRAI completeness and throws a user-facing {@link AdempiereException} when GRAI scan
	 * is required but the current target LU still has unfilled TU GRAI slots.
	 * Package-private: used by {@link #userConfirmed} and callable directly from tests.
	 */
	void checkGraisComplete(@NonNull final HUConsolidationJob job)
	{
		if (!isGraiScanEnabled(job))
		{
			return;
		}

		final HUGraiSnapshot snapshot = getTargetLUSnapshot(job);
		if (snapshot != null)
		{
			snapshot.assertAllGraisAssigned();
		}
	}

	@Override
	public WFProcess userConfirmed(final UserConfirmationRequest request)
	{
		request.assertActivityType(HANDLED_ACTIVITY_TYPE);
		final HUConsolidationJob job = getHUConsolidationJob(request.getWfProcess());
		checkGraisComplete(job);
		return huConsolidationApplication.mapJob(request.getWfProcess(), jobService::complete);
	}

	/** {@code GRAIRequired != No} → GRAI scan is required (YesWithDummyGRAIs treated as Yes — no dummy-fill here). */
	private boolean isGraiScanEnabled(@NonNull final HUConsolidationJob job)
	{
		return GraiScanHelper.isGraiScanEnabled(job, bpartnerDAO);
	}

	@Nullable
	private HUGraiSnapshot getTargetLUSnapshot(@NonNull final HUConsolidationJob job)
	{
		final HUConsolidationTarget currentTarget = job.getCurrentTarget();
		if (currentTarget == null || !currentTarget.isExistingLU())
		{
			return null;
		}
		return huGraiService.getSnapshot(currentTarget.getLuIdNotNull()).orElse(null);
	}
}

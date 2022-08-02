package de.metas.project.workorder.interceptor;

import de.metas.calendar.CalendarEntryId;
import de.metas.calendar.MultiCalendarService;
import de.metas.product.ResourceId;
import de.metas.project.workorder.WOProjectResourceRepository;
import de.metas.project.workorder.WOProjectService;
import de.metas.project.workorder.WOProjectStepId;
import de.metas.project.workorder.calendar.BudgetAndWOCalendarEntryIdConverters;
import de.metas.project.workorder.conflicts.WOProjectConflictService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Project_WO_Resource;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;

@Component
@Interceptor(I_C_Project_WO_Resource.class)
public class C_Project_WO_Resource
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final WOProjectService woProjectService;
	private final WOProjectConflictService woProjectConflictService;
	private final MultiCalendarService multiCalendarService;

	public C_Project_WO_Resource(
			@NonNull final WOProjectService woProjectService,
			@NonNull final WOProjectConflictService woProjectConflictService,
			@NonNull final MultiCalendarService multiCalendarService)
	{
		this.woProjectService = woProjectService;
		this.woProjectConflictService = woProjectConflictService;
		this.multiCalendarService = multiCalendarService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void afterSave(@NonNull final I_C_Project_WO_Resource record, @NonNull final ModelChangeType changeType)
	{
		// validate
		WOProjectResourceRepository.fromRecord(record);

		notifyEntryChanged(record, changeType);
		updateStepDatesAfterCommit(WOProjectStepId.ofRepoId(record.getC_Project_ID(), record.getC_Project_WO_Step_ID()));
		checkConflictsAfterCommitIfUserChange(record, changeType);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_DELETE)
	public void afterDelete(@NonNull final I_C_Project_WO_Resource record, @NonNull final ModelChangeType changeType)
	{
		notifyEntryChanged(record, changeType);
		updateStepDatesAfterCommit(WOProjectStepId.ofRepoId(record.getC_Project_ID(), record.getC_Project_WO_Step_ID()));
		checkConflictsAfterCommitIfUserChange(record, changeType);
	}

	private void updateStepDatesAfterCommit(@NonNull final WOProjectStepId stepId)
	{
		trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.Fail)
				.getPropertyAndProcessAfterCommit(
						"C_Project_WO_Resource.updateStepDatesAfterCommit",
						HashSet::new,
						woProjectService::updateStepsDateRange
				)
				.add(stepId);
	}

	private void notifyEntryChanged(
			@NonNull final I_C_Project_WO_Resource record,
			@NonNull final ModelChangeType changeType)
	{
		final CalendarEntryId entryId = extractCalendarEntryId(record);
		if (changeType.isNewOrChange() && record.isActive())
		{
			multiCalendarService.notifyEntryUpdated(entryId);
		}
		else
		{
			multiCalendarService.notifyEntryDeleted(entryId);
		}
	}

	@NonNull
	private static CalendarEntryId extractCalendarEntryId(final I_C_Project_WO_Resource record)
	{
		return BudgetAndWOCalendarEntryIdConverters.from(WOProjectResourceRepository.extractWOProjectResourceId(record));
	}

	private void checkConflictsAfterCommitIfUserChange(@NonNull final I_C_Project_WO_Resource record, @NonNull final ModelChangeType changeType)
	{
		if (!InterfaceWrapperHelper.isUIAction(record))
		{
			return;
		}

		final HashSet<ResourceId> resourceIdsToCheck = new HashSet<>();
		extractResourceId(record).ifPresent(resourceIdsToCheck::add);

		if (changeType.isChange())
		{
			final I_C_Project_WO_Resource recordOld = InterfaceWrapperHelper.createOld(record, I_C_Project_WO_Resource.class);
			extractResourceId(recordOld).ifPresent(resourceIdsToCheck::add);
		}

		trxManager.accumulateAndProcessAfterCommit(
				"C_Project_WO_Resource.checkConflicts",
				resourceIdsToCheck,
				woProjectConflictService::checkAllConflicts);
	}

	private static Optional<ResourceId> extractResourceId(final I_C_Project_WO_Resource record)
	{
		return ResourceId.optionalOfRepoId(record.getS_Resource_ID());
	}
}

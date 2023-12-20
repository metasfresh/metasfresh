/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.project.budget.interceptor;

import de.metas.calendar.CalendarEntryId;
import de.metas.calendar.MultiCalendarService;
import de.metas.product.ResourceId;
import de.metas.project.budget.BudgetProjectResourceRepository;
import de.metas.project.budget.BudgetProjectResourceService;
import de.metas.project.workorder.calendar.BudgetAndWOCalendarEntryIdConverters;
import de.metas.resource.ResourceGroupId;
import de.metas.resource.ResourceService;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Project_Resource_Budget;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_C_Project_Resource_Budget.class)
public class C_Project_Resource_Budget
{
	private final MultiCalendarService multiCalendarService;
	private final BudgetProjectResourceService budgetProjectResourceService;
	private final ResourceService resourceService;

	public C_Project_Resource_Budget(
			@NonNull final MultiCalendarService multiCalendarService,
			@NonNull final BudgetProjectResourceService budgetProjectResourceService,
			@NonNull final ResourceService resourceService)
	{
		this.multiCalendarService = multiCalendarService;
		this.budgetProjectResourceService = budgetProjectResourceService;
		this.resourceService = resourceService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void afterSave(final I_C_Project_Resource_Budget record, final ModelChangeType changeType)
	{
		// make sure it's valid
		BudgetProjectResourceRepository.fromRecord(record);

		notifyIfUserChange(record, changeType);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_DELETE)
	public void afterDelete(final I_C_Project_Resource_Budget record, final ModelChangeType changeType)
	{
		notifyIfUserChange(record, changeType);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_C_Project_Resource_Budget.COLUMNNAME_PlannedDuration)
	public void computePlannedDuration(final I_C_Project_Resource_Budget record)
	{
		if (record.getPlannedDuration() == null || record.getPlannedDuration().signum() == 0)
		{
			budgetProjectResourceService.computePlannedDuration(record).ifPresent(record::setPlannedDuration);
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = {
			I_C_Project_Resource_Budget.COLUMNNAME_S_Resource_ID,
			I_C_Project_Resource_Budget.COLUMNNAME_S_Resource_Group_ID })
	public void setResourceGroupIdAndValidateUOM(final I_C_Project_Resource_Budget record)
	{
		resourceService.getResourceGroupId(ResourceId.ofRepoIdOrNull(record.getS_Resource_ID()))
				.map(ResourceGroupId::getRepoId)
				.ifPresent(record::setS_Resource_Group_ID);

		budgetProjectResourceService.validateDurationUOMFromResource(record);
	}

	private void notifyIfUserChange(
			@NonNull final I_C_Project_Resource_Budget record,
			@NonNull final ModelChangeType changeType)
	{
		if (!InterfaceWrapperHelper.isUIAction(record))
		{
			return;
		}

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
	private static CalendarEntryId extractCalendarEntryId(final I_C_Project_Resource_Budget record)
	{
		return BudgetAndWOCalendarEntryIdConverters.from(BudgetProjectResourceRepository.extractBudgetProjectResourceId(record));
	}

}

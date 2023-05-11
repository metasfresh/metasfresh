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

package de.metas.project.budget.callout;

import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.project.budget.BudgetProject;
import de.metas.project.budget.BudgetProjectRepository;
import de.metas.project.budget.BudgetProjectResourceService;
import de.metas.resource.Resource;
import de.metas.resource.ResourceGroup;
import de.metas.resource.ResourceGroupId;
import de.metas.resource.ResourceService;
import de.metas.resource.ResourceType;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.ui.spi.ITabCallout;
import org.adempiere.ad.ui.spi.TabCallout;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Project_Resource_Budget;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Callout(I_C_Project_Resource_Budget.class)
@TabCallout(I_C_Project_Resource_Budget.class)
public class C_Project_Resource_Budget implements ITabCallout
{
	private final BudgetProjectRepository budgetProjectRepository;
	private final BudgetProjectResourceService budgetProjectResourceService;
	private final ResourceService resourceService;
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	public C_Project_Resource_Budget(
			@NonNull final BudgetProjectRepository budgetProjectRepository,
			@NonNull final BudgetProjectResourceService budgetProjectResourceService,
			@NonNull final ResourceService resourceService)
	{
		this.budgetProjectRepository = budgetProjectRepository;
		this.budgetProjectResourceService = budgetProjectResourceService;
		this.resourceService = resourceService;
	}

	@PostConstruct
	public void postConstruct()
	{
		// register ourselves
		final IProgramaticCalloutProvider programmaticCalloutsProvider = Services.get(IProgramaticCalloutProvider.class);
		programmaticCalloutsProvider.registerAnnotatedCallout(this);
	}

	@Override
	public void onNew(@NonNull final ICalloutRecord calloutRecord)
	{
		final I_C_Project_Resource_Budget budgetRecord = calloutRecord.getModel(I_C_Project_Resource_Budget.class);
		final ProjectId budgetProjectId = ProjectId.ofRepoId(budgetRecord.getC_Project_ID());
		final BudgetProject budgetProject = budgetProjectRepository.getOptionalById(budgetProjectId)
				.orElseThrow(() -> new AdempiereException("Not a valid budget project"));

		budgetRecord.setAD_Org_ID(budgetProject.getOrgId().getRepoId());
		budgetRecord.setC_Currency_ID(budgetProject.getCurrencyId().getRepoId());
	}

	@CalloutMethod(columnNames = I_C_Project_Resource_Budget.COLUMNNAME_S_Resource_Group_ID)
	public void onS_Resource_Group_ID(final I_C_Project_Resource_Budget record)
	{
		updateFromResourceOrResourceGroup(record);
	}

	@CalloutMethod(columnNames = I_C_Project_Resource_Budget.COLUMNNAME_S_Resource_Group_ID)
	public void onS_Resource_ID(final I_C_Project_Resource_Budget record)
	{
		updateFromResourceOrResourceGroup(record);
	}

	@CalloutMethod(columnNames = I_C_Project_Resource_Budget.COLUMNNAME_PlannedAmt)
	public void onPlannedAmount(final I_C_Project_Resource_Budget record)
	{
		updatePlannedDuration(record);
	}

	@CalloutMethod(columnNames = I_C_Project_Resource_Budget.COLUMNNAME_PricePerTimeUOM)
	public void onPricePerTimeUOM(final I_C_Project_Resource_Budget record)
	{
		updatePlannedDuration(record);
	}

	@CalloutMethod(columnNames = I_C_Project_Resource_Budget.COLUMNNAME_C_UOM_Time_ID)
	public void onC_UOM_Time_ID(final I_C_Project_Resource_Budget record)
	{
		updatePlannedDuration(record);
	}

	private void updateFromResourceOrResourceGroup(final I_C_Project_Resource_Budget record)
	{
		final ResourceId resourceId = ResourceId.ofRepoIdOrNull(record.getS_Resource_ID());
		ResourceGroupId resourceGroupId = ResourceGroupId.ofRepoIdOrNull(record.getS_Resource_Group_ID());
		final UomId durationUomId;
		if (resourceId != null)
		{
			final Resource resource = resourceService.getResourceById(resourceId);
			final ResourceType resourceType = resourceService.getResourceTypeById(resource.getResourceTypeId());
			resourceGroupId = resource.getResourceGroupId();
			durationUomId = resourceType.getDurationUomId();
		}
		else if (resourceGroupId != null)
		{
			final ResourceGroup resourceGroup = resourceService.getGroupById(resourceGroupId);
			durationUomId = uomDAO.getUomIdByTemporalUnit(resourceGroup.getDurationUnit());
		}
		else
		{
			// do nothing
			return;
		}

		record.setS_Resource_Group_ID(ResourceGroupId.toRepoId(resourceGroupId));
		record.setC_UOM_Time_ID(durationUomId.getRepoId());
	}

	private void updatePlannedDuration(final I_C_Project_Resource_Budget record)
	{
		budgetProjectResourceService.computePlannedDuration(record).ifPresent(record::setPlannedDuration);
	}
}

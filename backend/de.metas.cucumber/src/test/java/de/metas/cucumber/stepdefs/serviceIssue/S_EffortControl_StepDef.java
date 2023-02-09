/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.serviceIssue;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.activity.C_Activity_StepDefData;
import de.metas.cucumber.stepdefs.project.C_Project_StepDefData;
import de.metas.serviceprovider.effortcontrol.EffortControlService;
import de.metas.serviceprovider.effortcontrol.repository.EffortControlRepository;
import de.metas.serviceprovider.model.I_S_EffortControl;
import de.metas.serviceprovider.model.I_S_Issue;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_Project;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.serviceprovider.model.I_S_EffortControl.COLUMNNAME_C_Activity_ID;
import static de.metas.serviceprovider.model.I_S_EffortControl.COLUMNNAME_C_Project_ID;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class S_EffortControl_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final EffortControlService effortControlService = SpringContextHolder.instance.getBean(EffortControlService.class);

	private final C_Activity_StepDefData activityTable;
	private final C_Project_StepDefData projectTable;
	private final S_EffortControl_StepDefData effortControlTable;

	public S_EffortControl_StepDef(
			@NonNull final C_Activity_StepDefData activityTable,
			@NonNull final C_Project_StepDefData projectTable,
			@NonNull final S_EffortControl_StepDefData effortControlTable)
	{
		this.activityTable = activityTable;
		this.projectTable = projectTable;
		this.effortControlTable = effortControlTable;
	}

	@And("metasfresh contains S_EffortControl:")
	public void create_S_EffortControl(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String activityIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Activity_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_C_Activity activity = activityTable.get(activityIdentifier);

			final String projectIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Project_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_C_Project project = projectTable.get(projectIdentifier);

			final I_S_EffortControl effortControl = CoalesceUtil.coalesceSuppliersNotNull(() -> queryBL.createQueryBuilder(I_S_EffortControl.class)
																								  .addEqualsFilter(COLUMNNAME_C_Activity_ID, activity.getC_Activity_ID())
																								  .addEqualsFilter(COLUMNNAME_C_Project_ID, project.getC_Project_ID())
																								  .create()
																								  .firstOnlyOrNull(I_S_EffortControl.class),
																						  () -> newInstance(I_S_EffortControl.class));

			effortControl.setC_Activity_ID(activity.getC_Activity_ID());
			effortControl.setC_Project_ID(project.getC_Project_ID());

			final BigDecimal invoiceableHours = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_S_EffortControl.COLUMNNAME_InvoiceableHours);
			if (invoiceableHours != null)
			{
				effortControl.setInvoiceableHours(invoiceableHours);
			}

			final BigDecimal budget = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_S_EffortControl.COLUMNNAME_Budget);
			if (budget != null)
			{
				effortControl.setBudget(budget);
			}

			final String pendingEffortSum = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_S_EffortControl.COLUMNNAME_PendingEffortSum);
			if (Check.isNotBlank(pendingEffortSum))
			{
				effortControl.setPendingEffortSum(pendingEffortSum);
			}

			final String effortSum = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_S_EffortControl.COLUMNNAME_EffortSum);
			if (Check.isNotBlank(effortSum))
			{
				effortControl.setEffortSum(effortSum);
			}

			saveRecord(effortControl);

			final String effortControlIdentifier = DataTableUtil.extractStringForColumnName(row, I_S_EffortControl.COLUMNNAME_S_EffortControl_ID + "." + TABLECOLUMN_IDENTIFIER);
			effortControlTable.put(effortControlIdentifier, effortControl);
		}
	}

	@And("^after not more than (.*)s, S_EffortControl is found:$")
	public void retrieve_S_EffortControl(final int timeoutSeconds, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			StepDefUtil.tryAndWait(timeoutSeconds, 500, () -> loadEffortControl(row));
		}
	}

	@And("^after not more than (.*)s, S_EffortControl is validated:$")
	public void validate_S_EffortControl(final int timeoutSeconds, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			StepDefUtil.tryAndWaitForItem(timeoutSeconds, 500, () -> validateEffortControl(row));
		}
	}

	@And("'generate invoice candidate' from effort control process is invoked")
	public void generateInvoiceCandidateFromEffortControl(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String effortControlIdentifier = DataTableUtil.extractStringForColumnName(row, I_S_EffortControl.COLUMNNAME_S_EffortControl_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_S_EffortControl effortControl = effortControlTable.get(effortControlIdentifier);

			effortControlService.generateICFromEffortControl(EffortControlRepository.fromRecord(effortControl));
		}
	}

	private boolean loadEffortControl(final @NonNull Map<String, String> row)
	{
		final String activityIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Activity.COLUMNNAME_C_Activity_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_Activity activity = activityTable.get(activityIdentifier);

		final String projectIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Project.COLUMNNAME_C_Project_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_C_Project project = projectTable.get(projectIdentifier);

		final I_S_EffortControl effortControl = queryBL.createQueryBuilder(I_S_EffortControl.class)
				.addEqualsFilter(COLUMNNAME_C_Activity_ID, activity.getC_Activity_ID())
				.addEqualsFilter(COLUMNNAME_C_Project_ID, project.getC_Project_ID())
				.create()
				.firstOnlyOrNull(I_S_EffortControl.class);

		if (effortControl == null)
		{
			return false;
		}

		final String effortControlIdentifier = DataTableUtil.extractStringForColumnName(row, I_S_EffortControl.COLUMNNAME_S_EffortControl_ID + "." + TABLECOLUMN_IDENTIFIER);
		effortControlTable.put(effortControlIdentifier, effortControl);

		return true;
	}

	private ItemProvider.ProviderResult<I_S_EffortControl> validateEffortControl(final @NonNull Map<String, String> row)
	{
		final String effortControlIdentifier = DataTableUtil.extractStringForColumnName(row, I_S_EffortControl.COLUMNNAME_S_EffortControl_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_S_EffortControl effortControl = effortControlTable.get(effortControlIdentifier);

		final ImmutableList.Builder<String> errorCollectors = ImmutableList.builder();

		final String activityIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Activity.COLUMNNAME_C_Activity_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Activity activity = activityTable.get(activityIdentifier);
		if (activity.getC_Activity_ID() != effortControl.getC_Activity_ID())
		{
			errorCollectors.add(MessageFormat.format("S_EffortControl={0}; Expecting C_Activity_ID={1} but actual is {2}",
													 effortControl.getS_EffortControl_ID(), activity.getC_Activity_ID(), effortControl.getC_Activity_ID()));
		}

		final String projectIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Project.COLUMNNAME_C_Project_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_Project project = projectTable.get(projectIdentifier);
		if (project.getC_Project_ID() != effortControl.getC_Project_ID())
		{
			errorCollectors.add(MessageFormat.format("S_EffortControl={0}; Expecting C_Project.Name={1} but actual is {2}",
													 effortControl.getS_EffortControl_ID(), project.getC_Project_ID(), effortControl.getC_Project_ID()));
		}

		final BigDecimal invoiceableHours = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_S_EffortControl.COLUMNNAME_InvoiceableHours);
		if (invoiceableHours != null && !effortControl.getInvoiceableHours().equals(invoiceableHours))
		{
			errorCollectors.add(MessageFormat.format("S_EffortControl={0}; Expecting InvoiceableHours={1} but actual is {2}",
													 effortControl.getS_EffortControl_ID(), invoiceableHours, effortControl.getInvoiceableHours()));
		}

		final String pendingEffortSum = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_S_EffortControl.COLUMNNAME_PendingEffortSum);
		if (Check.isNotBlank(pendingEffortSum) && !effortControl.getPendingEffortSum().equals(pendingEffortSum))
		{
			errorCollectors.add(MessageFormat.format("S_EffortControl={0}; Expecting PendingEffortSum={1} but actual is {2}",
													 effortControl.getS_EffortControl_ID(), pendingEffortSum, effortControl.getPendingEffortSumSeconds()));
		}

		final String effortSum = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_S_EffortControl.COLUMNNAME_EffortSum);
		if (Check.isNotBlank(effortSum) && !effortControl.getEffortSum().equals(effortSum))
		{
			errorCollectors.add(MessageFormat.format("S_EffortControl={0}; Expecting EffortSum={1} but actual is {2}",
													 effortControl.getS_EffortControl_ID(), effortSum, effortControl.getEffortSumSeconds()));
		}

		final BigDecimal budget = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_S_EffortControl.COLUMNNAME_Budget);
		if (budget != null && !effortControl.getBudget().equals(budget))
		{
			errorCollectors.add(MessageFormat.format("S_EffortControl={0}; Expecting Budget={1} but actual is {2}",
													 effortControl.getS_EffortControl_ID(), budget, effortControl.getBudget()));
		}

		final Boolean isIssueClosed = DataTableUtil.extractBooleanForColumnNameOrNull(row, "OPT." + I_S_EffortControl.COLUMNNAME_IsIssueClosed);
		if (isIssueClosed != null && isIssueClosed(effortControl) != isIssueClosed)
		{
			errorCollectors.add(MessageFormat.format("S_EffortControl={0}; Expecting IsIssueClosed={1} but actual is {2}",
													 effortControl.getS_EffortControl_ID(), isIssueClosed, isIssueClosed(effortControl)));
		}

		final Boolean isOverBudget = DataTableUtil.extractBooleanForColumnNameOrNull(row, "OPT." + I_S_EffortControl.COLUMNNAME_IsOverBudget);
		if (isOverBudget != null && effortControl.isOverBudget() != isOverBudget)
		{
			errorCollectors.add(MessageFormat.format("S_EffortControl={0}; Expecting IsOverBudget={1} but actual is {2}",
													 effortControl.getS_EffortControl_ID(), isOverBudget, effortControl.isOverBudget()));
		}

		final List<String> errors = errorCollectors.build();

		if (!errors.isEmpty())
		{
			final String errorMessages = String.join(" && \n", errors);
			return ItemProvider.ProviderResult.resultWasNotFound(errorMessages);
		}

		return ItemProvider.ProviderResult.resultWasFound(effortControl);
	}

	private boolean isIssueClosed(@NonNull final I_S_EffortControl effortControl)
	{
		final List<I_S_Issue> issues = queryBL.createQueryBuilder(I_S_Issue.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_Issue.COLUMNNAME_C_Activity_ID, effortControl.getC_Activity_ID())
				.addEqualsFilter(I_S_Issue.COLUMNNAME_C_Project_ID, effortControl.getC_Project_ID())
				.addEqualsFilter(I_S_Issue.COLUMNNAME_AD_Org_ID, effortControl.getAD_Org_ID())
				.create()
				.list();

		if (issues.isEmpty())
		{
			return false;
		}

		return issues.stream().allMatch(I_S_Issue::isProcessed);
	}
}

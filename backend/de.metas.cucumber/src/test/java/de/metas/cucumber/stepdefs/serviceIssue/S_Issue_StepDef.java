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

import de.metas.cucumber.stepdefs.AD_User_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.activity.C_Activity_StepDefData;
import de.metas.cucumber.stepdefs.project.C_Project_StepDefData;
import de.metas.serviceprovider.model.I_S_Issue;
import de.metas.serviceprovider.model.I_S_IssueLabel;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_Project;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.serviceprovider.model.I_S_Issue.COLUMNNAME_BudgetedEffort;
import static de.metas.serviceprovider.model.I_S_Issue.COLUMNNAME_C_Activity_ID;
import static de.metas.serviceprovider.model.I_S_Issue.COLUMNNAME_C_Project_ID;
import static de.metas.serviceprovider.model.I_S_Issue.COLUMNNAME_InvoiceableEffort;
import static de.metas.serviceprovider.model.I_S_Issue.COLUMNNAME_IsEffortIssue;
import static de.metas.serviceprovider.model.I_S_Issue.COLUMNNAME_IssueEffort;
import static de.metas.serviceprovider.model.I_S_Issue.COLUMNNAME_IssueType;
import static de.metas.serviceprovider.model.I_S_Issue.COLUMNNAME_Name;
import static de.metas.serviceprovider.model.I_S_Issue.COLUMNNAME_S_Issue_ID;
import static de.metas.serviceprovider.model.I_S_Issue.COLUMNNAME_Status;
import static de.metas.serviceprovider.model.I_S_Issue.COLUMNNAME_Value;
import static org.adempiere.model.InterfaceWrapperHelper.deleteRecord;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class S_Issue_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final S_Issue_StepDefData sIssueTable;
	private final S_IssueLabel_StepDefData sIssueLabelTable;
	private final C_Activity_StepDefData activityTable;
	private final C_Project_StepDefData projectTable;
	private final AD_User_StepDefData userTable;

	public S_Issue_StepDef(
			@NonNull final S_Issue_StepDefData sIssueTable,
			@NonNull final S_IssueLabel_StepDefData sIssueLabelTable,
			@NonNull final C_Activity_StepDefData activityTable,
			@NonNull final C_Project_StepDefData projectTable,
			@NonNull final AD_User_StepDefData userTable)
	{
		this.sIssueTable = sIssueTable;
		this.sIssueLabelTable = sIssueLabelTable;
		this.activityTable = activityTable;
		this.projectTable = projectTable;
		this.userTable = userTable;
	}

	@And("metasfresh contains S_Issue:")
	public void add_S_Issue(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String value = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_Value);
			final String name = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_Name);
			final String issueType = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_IssueType);
			final boolean isEffortIssue = DataTableUtil.extractBooleanForColumnName(row, COLUMNNAME_IsEffortIssue);

			final I_S_Issue issue = newInstance(I_S_Issue.class);

			issue.setValue(value);
			issue.setName(Check.isNotBlank(name) ? name : value);
			issue.setIssueType(issueType);
			issue.setIsEffortIssue(isEffortIssue);

			final String activityIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_Activity_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(activityIdentifier))
			{
				final I_C_Activity activity = activityTable.get(activityIdentifier);
				issue.setC_Activity_ID(activity.getC_Activity_ID());
			}

			final String projectIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_Project_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(projectIdentifier))
			{
				final I_C_Project project = projectTable.get(projectIdentifier);
				issue.setC_Project_ID(project.getC_Project_ID());
			}

			final BigDecimal invoiceableEffort = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_InvoiceableEffort);
			if (invoiceableEffort != null)
			{
				issue.setInvoiceableEffort(invoiceableEffort);
			}

			final BigDecimal budgetedEffort = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_BudgetedEffort);
			if (budgetedEffort != null)
			{
				issue.setBudgetedEffort(budgetedEffort);
			}

			final String issueEffort = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_IssueEffort);
			if (Check.isNotBlank(issueEffort))
			{
				issue.setIssueEffort(issueEffort);
			}

			final String parentIssueIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_S_Issue.COLUMNNAME_S_Parent_Issue_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(parentIssueIdentifier))
			{
				final I_S_Issue parentIssue = sIssueTable.get(parentIssueIdentifier);
				issue.setS_Parent_Issue_ID(parentIssue.getS_Issue_ID());
			}

			final BigDecimal externalIssueNo = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_S_Issue.COLUMNNAME_ExternalIssueNo);
			if (externalIssueNo != null)
			{
				issue.setExternalIssueNo(externalIssueNo);
			}

			final String userIdentifier = DataTableUtil.extractNullableStringForColumnName(row, "OPT." + I_S_Issue.COLUMNNAME_AD_User_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(userIdentifier))
			{
				final String nullableIdentifier = DataTableUtil.nullToken2Null(userIdentifier);
				if (nullableIdentifier == null)
				{
					issue.setAD_User_ID(-1);
				}
				else
				{
					final I_AD_User user = userTable.get(userIdentifier);
					issue.setAD_User_ID(user.getAD_User_ID());
				}
			}

			saveRecord(issue);

			final String issueIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_S_Issue_ID + "." + TABLECOLUMN_IDENTIFIER);
			sIssueTable.put(issueIdentifier, issue);
		}
	}

	@And("update S_Issue:")
	public void update_S_Issue(@NonNull final DataTable dataTable)
	{
		dataTable.asMaps().forEach(this::updateIssue);
	}

	@And("load Cost Center Activity from issue:")
	public void get_Cost_Center(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			loadCostCenterActivityFromIssue(row);
		}
	}

	@And("Cost Center Activity removed from issue:")
	public void removed_Cost_Center(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			assertThat(loadCostCenterActivityFromIssue(row)).isFalse();
		}
	}

	@And("metasfresh contains S_IssueLabel:")
	public void add_S_IssueLabel(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String issueIdentifier = DataTableUtil.extractStringForColumnName(row, I_S_IssueLabel.COLUMNNAME_S_Issue_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_S_Issue issue = sIssueTable.get(issueIdentifier);

			final String label = DataTableUtil.extractStringForColumnName(row, I_S_IssueLabel.COLUMNNAME_Label);

			final I_S_IssueLabel issueLabel = newInstance(I_S_IssueLabel.class);
			issueLabel.setS_Issue_ID(issue.getS_Issue_ID());
			issueLabel.setLabel(label);

			saveRecord(issueLabel);

			final String issueLabelIdentifier = DataTableUtil.extractStringForColumnName(row, I_S_IssueLabel.COLUMNNAME_S_IssueLabel_ID + "." + TABLECOLUMN_IDENTIFIER);
			sIssueLabelTable.put(issueLabelIdentifier, issueLabel);
		}
	}

	@And("S_IssueLabel is found:")
	public void retrieve_S_IssueLabel(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String issueIdentifier = DataTableUtil.extractStringForColumnName(row, I_S_IssueLabel.COLUMNNAME_S_Issue_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_S_Issue issue = sIssueTable.get(issueIdentifier);

			final String label = DataTableUtil.extractStringForColumnName(row, I_S_IssueLabel.COLUMNNAME_Label);

			final I_S_IssueLabel issueLabel = queryBL.createQueryBuilder(I_S_IssueLabel.class)
					.addEqualsFilter(I_S_IssueLabel.COLUMNNAME_S_Issue_ID, issue.getS_Issue_ID())
					.addEqualsFilter(I_S_IssueLabel.COLUMNNAME_Label, label)
					.create()
					.firstOnlyNotNull(I_S_IssueLabel.class);

			final String issueLabelIdentifier = DataTableUtil.extractStringForColumnName(row, I_S_IssueLabel.COLUMNNAME_S_IssueLabel_ID + "." + TABLECOLUMN_IDENTIFIER);
			sIssueLabelTable.put(issueLabelIdentifier, issueLabel);
		}
	}

	@And("S_IssueLabel is removed:")
	public void removed_S_IssueLabel(@NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String issueLabelIdentifier = DataTableUtil.extractStringForColumnName(row, I_S_IssueLabel.COLUMNNAME_S_IssueLabel_ID + "." + TABLECOLUMN_IDENTIFIER);
			final Optional<I_S_IssueLabel> issueLabel = sIssueLabelTable.getOptional(issueLabelIdentifier);

			assertThat(issueLabel.isPresent()).isFalse();
		}
	}

	@And("remove S_IssueLabel:")
	public void remove_S_IssueLabel(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String issueLabelIdentifier = DataTableUtil.extractStringForColumnName(row, I_S_IssueLabel.COLUMNNAME_S_IssueLabel_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_S_IssueLabel issueLabel = sIssueLabelTable.get(issueLabelIdentifier);

			deleteRecord(issueLabel);
		}
	}

	@And("reopen S_Issue:")
	public void reopenS_Issue(@NonNull final DataTable dataTable)
	{
		dataTable.asMaps().forEach(this::updateIssue);
	}

	@And("validate S_Issue:")
	public void validateS_Issue(@NonNull final DataTable dataTable)
	{
		dataTable.asMaps().forEach(this::validateIssue);
	}

	@NonNull
	private Boolean loadCostCenterActivityFromIssue(final @NonNull Map<String, String> row)
	{
		final String issueIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_S_Issue_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_S_Issue issue = sIssueTable.get(issueIdentifier);

		if (issue.getC_Activity_ID() <= 0)
		{
			return false;
		}

		final I_C_Activity activity = load(issue.getC_Activity_ID(), I_C_Activity.class);

		final String activityIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Activity_ID + "." + TABLECOLUMN_IDENTIFIER);
		activityTable.put(activityIdentifier, activity);

		return true;
	}

	private void updateIssue(@NonNull final Map<String, String> row)
	{
		final String issueIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_S_Issue_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_S_Issue issue = sIssueTable.get(issueIdentifier);

		final String activityIdentifier = DataTableUtil.extractNullableStringForColumnName(row, "OPT." + COLUMNNAME_C_Activity_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(activityIdentifier))
		{
			if (DataTableUtil.nullToken2Null(activityIdentifier) != null)
			{
				final I_C_Activity activity = activityTable.get(activityIdentifier);
				issue.setC_Activity_ID(activity.getC_Activity_ID());
			}
			else
			{
				issue.setC_Activity_ID(0);
			}
		}

		final BigDecimal invoiceableEffort = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_InvoiceableEffort);
		if (invoiceableEffort != null)
		{
			issue.setInvoiceableEffort(invoiceableEffort);
		}

		final String projectIdentifier = DataTableUtil.extractNullableStringForColumnName(row, "OPT." + COLUMNNAME_C_Project_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(projectIdentifier))
		{
			if (DataTableUtil.nullToken2Null(projectIdentifier) != null)
			{
				final I_C_Project project = projectTable.get(projectIdentifier);
				issue.setC_Project_ID(project.getC_Project_ID());
			}
			else
			{
				issue.setC_Project_ID(0);
			}
		}

		final BigDecimal budgetedEffort = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_BudgetedEffort);
		if (budgetedEffort != null)
		{
			issue.setBudgetedEffort(budgetedEffort);
		}

		final String status = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_Status);
		if (Check.isNotBlank(status))
		{
			issue.setStatus(status);
		}

		final Boolean processed = DataTableUtil.extractBooleanForColumnNameOrNull(row, "OPT." + I_S_Issue.COLUMNNAME_Processed);
		if (processed != null)
		{
			issue.setProcessed(processed);
		}

		saveRecord(issue);

		sIssueTable.putOrReplace(issueIdentifier, issue);
	}

	private void validateIssue(@NonNull final Map<String, String> row)
	{
		final SoftAssertions softly = new SoftAssertions();

		final String issueIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_S_Issue_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_S_Issue issue = sIssueTable.get(issueIdentifier);

		final String value = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_Value);
		softly.assertThat(issue.getValue()).isEqualTo(value);

		final String name = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_Name);
		if (Check.isNotBlank(name))
		{
			softly.assertThat(issue.getName()).isEqualTo(name);
		}

		final String issueType = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_IssueType);
		softly.assertThat(issue.getIssueType()).isEqualTo(issueType);

		final boolean isEffortIssue = DataTableUtil.extractBooleanForColumnName(row, COLUMNNAME_IsEffortIssue);
		softly.assertThat(issue.isEffortIssue()).isEqualTo(isEffortIssue);

		final BigDecimal invoiceableEffort = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + COLUMNNAME_InvoiceableEffort);
		if (invoiceableEffort != null)
		{
			softly.assertThat(issue.getInvoiceableEffort()).isEqualTo(invoiceableEffort);
		}

		final String activityIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_Activity_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(activityIdentifier))
		{
			final I_C_Activity costCenter = activityTable.get(activityIdentifier);
			softly.assertThat(issue.getC_Activity_ID()).isEqualTo(costCenter.getC_Activity_ID());
		}

		final String projectIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_Project_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(projectIdentifier))
		{
			final I_C_Project project = projectTable.get(projectIdentifier);
			softly.assertThat(issue.getC_Project_ID()).isEqualTo(project.getC_Project_ID());
		}

		final BigDecimal externalIssueNo = DataTableUtil.extractBigDecimalOrNullForColumnName(row, "OPT." + I_S_Issue.COLUMNNAME_ExternalIssueNo);
		if (externalIssueNo != null)
		{
			softly.assertThat(issue.getExternalIssueNo()).isEqualTo(externalIssueNo);
		}

		final String status = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_Status);
		if (Check.isNotBlank(status))
		{
			softly.assertThat(issue.getStatus()).isEqualTo(status);
		}

		final Boolean processed = DataTableUtil.extractBooleanForColumnNameOrNull(row, "OPT." + I_S_Issue.COLUMNNAME_Processed);
		if (processed != null)
		{
			softly.assertThat(issue.isProcessed()).isEqualTo(processed);
		}

		final String issueEffort = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_IssueEffort);
		if (Check.isNotBlank(issueEffort))
		{
			softly.assertThat(issue.getIssueEffort()).isEqualTo(issueEffort);
		}

		final String parentIssueIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_S_Issue.COLUMNNAME_S_Parent_Issue_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(parentIssueIdentifier))
		{
			final I_S_Issue parentIssue = sIssueTable.get(parentIssueIdentifier);
			softly.assertThat(issue.getS_Parent_Issue_ID()).isEqualTo(parentIssue.getS_Issue_ID());
		}

		final String userIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_S_Issue.COLUMNNAME_AD_User_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(userIdentifier))
		{
			final I_AD_User user = userTable.get(userIdentifier);

			softly.assertThat(issue.getAD_User_ID()).isEqualTo(user.getAD_User_ID());
		}

		final String invoicingErrorMsg = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_S_Issue.COLUMNNAME_InvoicingErrorMsg);
		if (Check.isNotBlank(invoicingErrorMsg))
		{
			softly.assertThat(issue.getInvoicingErrorMsg()).contains(invoicingErrorMsg);
		}

		final Boolean isInvoicingError = DataTableUtil.extractBooleanForColumnNameOrNull(row, "OPT." + I_S_Issue.COLUMNNAME_IsInvoicingError);
		if (isInvoicingError != null)
		{
			softly.assertThat(issue.isInvoicingError()).isEqualTo(isInvoicingError);
		}

		sIssueTable.putOrReplace(issueIdentifier, issue);

		softly.assertAll();
	}
}

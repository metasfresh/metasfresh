/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.cucumber.stepdefs.accounting;

import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.document.DocBaseType;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_PeriodControl;
import org.compiere.model.MPeriod;
import org.compiere.model.X_C_PeriodControl;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class C_PeriodControl_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);

	/**
	 * {@code C_AcctSchema_ID} -> the {@code AutoPeriodControl} the accounting schema had before this scenario changed it.
	 */
	private final Map<AcctSchemaId, Boolean> autoPeriodControlBeforeScenario = new LinkedHashMap<>();

	/**
	 * {@code C_PeriodControl_ID} -> the {@code PeriodStatus} the period control had before this scenario changed it.
	 */
	private final Map<Integer, String> periodStatusBeforeScenario = new LinkedHashMap<>();

	@And("^all periods are open$")
	public void all_periods_are_open()
	{
		queryBL.createQueryBuilder(I_C_PeriodControl.class)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_C_PeriodControl.COLUMNNAME_PeriodStatus, X_C_PeriodControl.PERIODSTATUS_Open)
				.execute();
	}

	/**
	 * Switches the accounting schema of the context's client and organization from automatic to manual period control,
	 * so that each document base type's period is open or closed by its own {@code C_PeriodControl}.
	 * The accounting schema is set back after the scenario, also when a step failed.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And the accounting schema of the organization uses manual period control
	 * </pre>
	 */
	@And("^the accounting schema of the organization uses manual period control$")
	public void accountingSchemaUsesManualPeriodControl()
	{
		final AcctSchemaId acctSchemaId = acctSchemaDAO.getByClientAndOrg(Env.getCtx()).getId();
		final I_C_AcctSchema acctSchemaRecord = acctSchemaDAO.getRecordById(acctSchemaId);
		autoPeriodControlBeforeScenario.putIfAbsent(acctSchemaId, acctSchemaRecord.isAutoPeriodControl());

		acctSchemaRecord.setAutoPeriodControl(false);
		saveRecord(acctSchemaRecord);
	}

	/**
	 * Opens or closes the period control of a document base type in the period of the given accounting date.
	 * Each period control is set back after the scenario, also when a step failed.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>DocBaseType</b> — (required) the document base type of the period control, e.g. {@code CMA} (payment allocation)<br>
	 *   <b>DateAcct</b> — (required) an accounting date in the period<br>
	 *   <b>PeriodStatus</b> — (required) {@code O} (open) or {@code C} (closed)
	 * @cucumber.example
	 * <pre>
	 * And update C_PeriodControl:
	 *   | DocBaseType | DateAcct   | PeriodStatus |
	 *   | CMA         | 2026-08-03 | C            |
	 * </pre>
	 */
	@And("update C_PeriodControl:")
	public void updatePeriodControls(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final DocBaseType docBaseType = DocBaseType.ofCode(row.getAsString(I_C_PeriodControl.COLUMNNAME_DocBaseType));
			final LocalDate dateAcct = row.getAsLocalDate("DateAcct");
			final String periodStatus = row.getAsString(I_C_PeriodControl.COLUMNNAME_PeriodStatus);

			final I_C_PeriodControl periodControl = getPeriodControl(docBaseType, dateAcct);
			periodStatusBeforeScenario.putIfAbsent(periodControl.getC_PeriodControl_ID(), periodControl.getPeriodStatus());

			periodControl.setPeriodStatus(periodStatus);
			saveRecord(periodControl);
		});
	}

	private I_C_PeriodControl getPeriodControl(@NonNull final DocBaseType docBaseType, @NonNull final LocalDate dateAcct)
	{
		final MPeriod period = MPeriod.getOrFail(Env.getCtx(), TimeUtil.asTimestamp(dateAcct), Env.getAD_Org_ID(Env.getCtx()));
		final I_C_PeriodControl periodControl = queryBL.createQueryBuilder(I_C_PeriodControl.class)
				.addEqualsFilter(I_C_PeriodControl.COLUMNNAME_C_Period_ID, period.getC_Period_ID())
				.addEqualsFilter(I_C_PeriodControl.COLUMNNAME_DocBaseType, docBaseType.getCode())
				.create()
				.firstOnly(I_C_PeriodControl.class);
		if (periodControl == null)
		{
			throw new AdempiereException("No C_PeriodControl found")
					.appendParametersToMessage()
					.setParameter("C_Period_ID", period.getC_Period_ID())
					.setParameter("DocBaseType", docBaseType);
		}
		return periodControl;
	}

	/**
	 * Sets back what {@link #accountingSchemaUsesManualPeriodControl()} and {@link #updatePeriodControls(DataTable)} changed.
	 * Accounting schemas and periods are shared by every scenario on the executor; an {@code @After} hook also runs when a step failed.
	 */
	@After
	public void restorePeriodControl()
	{
		periodStatusBeforeScenario.forEach((periodControlId, periodStatus) -> {
			final I_C_PeriodControl periodControl = load(periodControlId, I_C_PeriodControl.class);
			periodControl.setPeriodStatus(periodStatus);
			saveRecord(periodControl);
		});
		periodStatusBeforeScenario.clear();

		autoPeriodControlBeforeScenario.forEach((acctSchemaId, autoPeriodControl) -> {
			final I_C_AcctSchema acctSchemaRecord = acctSchemaDAO.getRecordById(acctSchemaId);
			acctSchemaRecord.setAutoPeriodControl(autoPeriodControl);
			saveRecord(acctSchemaRecord);
		});
		autoPeriodControlBeforeScenario.clear();
	}
}

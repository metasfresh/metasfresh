/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.costrevaluation.callout;

import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.CostElementId;
import de.metas.costrevaluation.CostRevaluationId;
import de.metas.costrevaluation.CostRevaluationService;
import de.metas.costrevaluation.RevaluationSource;
import de.metas.document.engine.DocStatus;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_CostRevaluation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link M_CostRevaluation#onDateAcctChanged(I_M_CostRevaluation, ICalloutField)} and
 * {@link M_CostRevaluation#onRevaluationSourceChanged(I_M_CostRevaluation)}.
 * <p>
 * The callout sees the edited record plus, via {@link ICalloutField#getModelBeforeChanges(Class)}, the record as it was
 * before the user's edit. Both are modelled here as POJO records.
 */
class M_CostRevaluationTest
{
	private CostRevaluationService costRevaluationService;
	private M_CostRevaluation callout;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		costRevaluationService = mock(CostRevaluationService.class);
		when(costRevaluationService.findPresetCostElement(any(), any())).thenReturn(Optional.of(CostElementId.ofRepoId(1000008)));
		callout = new M_CostRevaluation(costRevaluationService);
	}

	private static Timestamp day(final int year, final int month, final int dayOfMonth)
	{
		return Timestamp.valueOf(LocalDate.of(year, month, dayOfMonth).atStartOfDay());
	}

	private static I_M_CostRevaluation record(
			@Nullable final Timestamp dateAcct,
			@Nullable final Timestamp evaluationStartDate,
			@Nullable final DocStatus docStatus)
	{
		final I_M_CostRevaluation record = newInstance(I_M_CostRevaluation.class);
		record.setDateAcct(dateAcct);
		record.setEvaluationStartDate(evaluationStartDate);
		record.setDocStatus(docStatus != null ? docStatus.getCode() : null);
		return record;
	}

	private static ICalloutField calloutFieldWithRecordBeforeChanges(final I_M_CostRevaluation recordBeforeChanges)
	{
		final ICalloutField calloutField = mock(ICalloutField.class);
		when(calloutField.getModelBeforeChanges(I_M_CostRevaluation.class)).thenReturn(recordBeforeChanges);
		return calloutField;
	}

	@Nested
	class DateAcctChanged
	{
		@Test
		void evaluationStartDateFollows_whenItWasEqualToOldDateAcct()
		{
			final I_M_CostRevaluation recordBeforeChanges = record(day(2020, 1, 15), day(2020, 1, 15), DocStatus.Drafted);
			final I_M_CostRevaluation record = record(day(2020, 2, 20), day(2020, 1, 15), DocStatus.Drafted);

			callout.onDateAcctChanged(record, calloutFieldWithRecordBeforeChanges(recordBeforeChanges));

			assertThat(record.getEvaluationStartDate()).isEqualTo(day(2020, 2, 20));
		}

		@Test
		void evaluationStartDateFollows_whenItIsNull()
		{
			final I_M_CostRevaluation recordBeforeChanges = record(day(2020, 1, 15), null, DocStatus.Drafted);
			final I_M_CostRevaluation record = record(day(2020, 2, 20), null, DocStatus.Drafted);

			callout.onDateAcctChanged(record, calloutFieldWithRecordBeforeChanges(recordBeforeChanges));

			assertThat(record.getEvaluationStartDate()).isEqualTo(day(2020, 2, 20));
		}

		@Test
		void onNewRecordWithoutDocStatus_evaluationStartDateFollows()
		{
			final I_M_CostRevaluation recordBeforeChanges = record(day(2020, 1, 15), day(2020, 1, 15), null);
			final I_M_CostRevaluation record = record(day(2020, 2, 20), day(2020, 1, 15), null);

			callout.onDateAcctChanged(record, calloutFieldWithRecordBeforeChanges(recordBeforeChanges));

			assertThat(record.getEvaluationStartDate()).isEqualTo(day(2020, 2, 20));
		}

		@Test
		void evaluationStartDateFollows_whenUserHadSetItSeparately()
		{
			final I_M_CostRevaluation recordBeforeChanges = record(day(2020, 1, 15), day(2019, 12, 1), DocStatus.Drafted);
			final I_M_CostRevaluation record = record(day(2020, 2, 20), day(2019, 12, 1), DocStatus.Drafted);

			callout.onDateAcctChanged(record, calloutFieldWithRecordBeforeChanges(recordBeforeChanges));

			assertThat(record.getEvaluationStartDate()).isEqualTo(day(2020, 2, 20));
		}

		@Test
		void copyFromCostElement_handSetCutOffDateKept()
		{
			final I_M_CostRevaluation recordBeforeChanges = record(day(2020, 1, 15), day(2019, 12, 1), DocStatus.Drafted);
			recordBeforeChanges.setRevaluationSource(RevaluationSource.CopyFromCostElement.getCode());
			final I_M_CostRevaluation record = record(day(2020, 2, 20), day(2019, 12, 1), DocStatus.Drafted);
			record.setRevaluationSource(RevaluationSource.CopyFromCostElement.getCode());

			callout.onDateAcctChanged(record, calloutFieldWithRecordBeforeChanges(recordBeforeChanges));

			assertThat(record.getEvaluationStartDate()).isEqualTo(day(2019, 12, 1));
		}

		@Test
		void evaluationStartDateKept_whenDocumentIsNotDraft()
		{
			final I_M_CostRevaluation recordBeforeChanges = record(day(2020, 1, 15), day(2020, 1, 15), DocStatus.Completed);
			final I_M_CostRevaluation record = record(day(2020, 2, 20), day(2020, 1, 15), DocStatus.Completed);

			callout.onDateAcctChanged(record, calloutFieldWithRecordBeforeChanges(recordBeforeChanges));

			assertThat(record.getEvaluationStartDate()).isEqualTo(day(2020, 1, 15));
		}

		@Test
		void cleared_evaluationStartDateFollows()
		{
			final I_M_CostRevaluation recordBeforeChanges = record(day(2020, 1, 15), day(2020, 1, 15), DocStatus.Drafted);
			final I_M_CostRevaluation record = record(null, day(2020, 1, 15), DocStatus.Drafted);

			callout.onDateAcctChanged(record, calloutFieldWithRecordBeforeChanges(recordBeforeChanges));

			assertThat(record.getEvaluationStartDate()).isNull();
		}
	}

	@Nested
	class AcctSchemaChanged
	{
		private I_M_CostRevaluation draftWithAcctSchema(@Nullable final RevaluationSource revaluationSource)
		{
			final I_M_CostRevaluation record = record(day(2020, 1, 15), day(2020, 1, 15), DocStatus.Drafted);
			record.setRevaluationSource(revaluationSource != null ? revaluationSource.getCode() : null);
			record.setC_AcctSchema_ID(1000000);
			record.setM_CostElement_ID(1000000); // the element of the previous schema
			return record;
		}

		@Test
		void callout_acctSchemaChanged_presetsCostElement()
		{
			final I_M_CostRevaluation record = draftWithAcctSchema(RevaluationSource.Manual);

			callout.onAcctSchemaChanged(record);

			assertThat(record.getM_CostElement_ID()).isEqualTo(1000008);
			verify(costRevaluationService).findPresetCostElement(any(), eq(AcctSchemaId.ofRepoId(1000000)));
		}

		@Test
		void callout_acctSchemaChanged_keepsCostElement_whenThereIsNoPreset()
		{
			when(costRevaluationService.findPresetCostElement(any(), any())).thenReturn(Optional.empty());
			final I_M_CostRevaluation record = draftWithAcctSchema(RevaluationSource.Manual);

			callout.onAcctSchemaChanged(record);

			assertThat(record.getM_CostElement_ID()).isEqualTo(1000000);
		}

		@Test
		void callout_acctSchemaChanged_keepsCostElement_whenTheDraftHasLines()
		{
			final I_M_CostRevaluation record = draftWithAcctSchema(RevaluationSource.Manual);
			record.setM_CostRevaluation_ID(4711);
			when(costRevaluationService.hasActiveLines(CostRevaluationId.ofRepoId(4711))).thenReturn(true);

			callout.onAcctSchemaChanged(record);

			assertThat(record.getM_CostElement_ID()).isEqualTo(1000000);
		}

		@Test
		void callout_acctSchemaChanged_keepsCostElement_whenDocumentIsNotDraft()
		{
			final I_M_CostRevaluation record = draftWithAcctSchema(RevaluationSource.Manual);
			record.setDocStatus(DocStatus.Completed.getCode());

			callout.onAcctSchemaChanged(record);

			assertThat(record.getM_CostElement_ID()).isEqualTo(1000000);
		}

		@Test
		void callout_copyFromCostElement_acctSchemaChanged_keepsItsTargetElement()
		{
			final I_M_CostRevaluation record = draftWithAcctSchema(RevaluationSource.CopyFromCostElement);

			callout.onAcctSchemaChanged(record);

			assertThat(record.getM_CostElement_ID()).isEqualTo(1000000);
		}
	}

	@Nested
	class RevaluationSourceChanged
	{
		@Test
		void sourceSwitchedToManual_startDateFollowsDateAcct()
		{
			final I_M_CostRevaluation record = record(day(2020, 1, 15), day(2019, 12, 1), DocStatus.Drafted);
			record.setRevaluationSource(RevaluationSource.Manual.getCode());

			callout.onRevaluationSourceChanged(record);

			assertThat(record.getEvaluationStartDate()).isEqualTo(day(2020, 1, 15));
		}

		/**
		 * An unset source counts as Manual, the column's default.
		 */
		@Test
		void sourceCleared_startDateFollowsDateAcct()
		{
			final I_M_CostRevaluation record = record(day(2020, 1, 15), day(2019, 12, 1), DocStatus.Drafted);
			record.setRevaluationSource(null);

			callout.onRevaluationSourceChanged(record);

			assertThat(record.getEvaluationStartDate()).isEqualTo(day(2020, 1, 15));
		}

		@Test
		void sourceSwitchedToCopyFromCostElement_startDateKept()
		{
			final I_M_CostRevaluation record = record(day(2020, 1, 15), day(2019, 12, 1), DocStatus.Drafted);
			record.setRevaluationSource(RevaluationSource.CopyFromCostElement.getCode());

			callout.onRevaluationSourceChanged(record);

			assertThat(record.getEvaluationStartDate()).isEqualTo(day(2019, 12, 1));
		}

		@Test
		void sourceSwitchedToManual_startDateKept_whenDocumentIsNotDraft()
		{
			final I_M_CostRevaluation record = record(day(2020, 1, 15), day(2019, 12, 1), DocStatus.Completed);
			record.setRevaluationSource(RevaluationSource.Manual.getCode());

			callout.onRevaluationSourceChanged(record);

			assertThat(record.getEvaluationStartDate()).isEqualTo(day(2019, 12, 1));
		}
	}

}

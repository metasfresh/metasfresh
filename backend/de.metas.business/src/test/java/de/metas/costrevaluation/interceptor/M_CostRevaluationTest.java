/*
 * #%L
 * de.metas.business
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

package de.metas.costrevaluation.interceptor;

import de.metas.costrevaluation.CostRevaluationService;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_CostRevaluation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDate;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Unit tests for {@link M_CostRevaluation#beforeNew(I_M_CostRevaluation)}.
 * <p>
 * A POJO-backed record ({@code AdempiereTestHelper.get().init()} +
 * {@code InterfaceWrapperHelper.newInstance}) reports {@code isUIAction() == false}, i.e. it models a
 * <b>non-UI</b> write path (REST / import / OLCand). The UI-action branch cannot be simulated with the
 * POJO wrapper (it always returns {@code false}); it is also dead on the real WebUI path, where the
 * AD_Column default {@code @#Date@} populates {@code DateAcct} at document-init, before {@code beforeNew}
 * fires — so the DateAcct safety-net never runs on the UI. Coverage here is therefore focused on the
 * behaviour the finding is about: non-UI callers must fail loud, not silently post "today".
 */
class M_CostRevaluationTest
{
	private M_CostRevaluation interceptor;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		// beforeNew never touches the service; a mock keeps the field initializer happy.
		interceptor = new M_CostRevaluation(mock(CostRevaluationService.class));
	}

	@Test
	void beforeNew_nonUiPath_dateAcctOmitted_isNotSilentlyDefaultedToToday()
	{
		final I_M_CostRevaluation record = newInstance(I_M_CostRevaluation.class);
		// DateAcct deliberately left unset; a POJO record => isUIAction() == false (non-UI path)
		assertThat(record.getDateAcct()).as("precondition: DateAcct unset").isNull();

		interceptor.beforeNew(record);

		// A backdated REST/import/OLCand caller that omits DateAcct must NOT get a silent "today"
		// default — DateAcct stays null so the mandatory-column save fails loud.
		assertThat(record.getDateAcct())
				.as("non-UI path must not fabricate a posting date")
				.isNull();
	}

	@Test
	void beforeNew_evaluationStartDate_defaultsToDateAcct_onAllPaths()
	{
		final I_M_CostRevaluation record = newInstance(I_M_CostRevaluation.class);
		final Timestamp dateAcct = Timestamp.valueOf(LocalDate.of(2020, 1, 15).atStartOfDay());
		record.setDateAcct(dateAcct);
		// EvaluationStartDate deliberately left unset

		interceptor.beforeNew(record);

		// Forward-only default is a data-integrity invariant on every write path (no isUIAction guard):
		// it derives from the caller-supplied DateAcct, never from wall-clock — so it is unaffected by
		// the DateAcct guard and still fires on the non-UI path.
		assertThat(record.getEvaluationStartDate()).isEqualTo(dateAcct);
	}
}

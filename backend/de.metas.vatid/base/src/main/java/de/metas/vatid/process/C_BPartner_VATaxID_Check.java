/*
 * #%L
 * metasfresh-vatid-base
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

package de.metas.vatid.process;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.vatid.VATaxIDMassCheckRequest;
import de.metas.vatid.VATaxIDMassCheckResult;
import de.metas.vatid.VATaxIDMassCheckService;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;

/**
 * The manual/scheduled VAT-ID check: available on the Business Partner window, runnable on a single partner
 * or a selection, and wired to the nightly {@code AD_Scheduler} — the same code path either way.
 *
 * <p>Thin glue: resolves this run's {@code C_BPartner} ids and the {@code MaxChecksPerRun} parameter, then
 * delegates everything else to {@link VATaxIDMassCheckService#run(VATaxIDMassCheckRequest)}.
 *
 * <p><b>Selection vs. nightly schedule.</b> A user-triggered run always carries a table/selection on its
 * {@code ProcessInfo}; the scheduler builds none. {@link #getTableName()} distinguishes them — non-null
 * means "read the selection", null means "sweep every due VAT-ID", which the service selects itself by
 * streaming. Without that branch the scheduled run would hit {@code @NoSelection@}.
 */
public class C_BPartner_VATaxID_Check extends JavaProcess implements IProcessPrecondition
{
	@VisibleForTesting
	public static final String PARA_MaxChecksPerRun = "MaxChecksPerRun";

	@NonNull private final VATaxIDMassCheckService massCheckService = SpringContextHolder.instance.getBean(VATaxIDMassCheckService.class);

	/**
	 * Empty or {@code <= 0} means no limit — see the {@code AD_Process_Para}'s own description, which this
	 * field's default (0) already satisfies: a blank parameter is never bound (see {@link Param}), leaving
	 * the field at 0.
	 */
	@Param(parameterName = PARA_MaxChecksPerRun, mandatory = false)
	private int p_MaxChecksPerRun = 0;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.getSelectionSize().isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final boolean nightlyRun = getTableName() == null;
		// A nightly run has no selection to pass: VATaxIDMassCheckService streams the due records itself.
		final ImmutableList<BPartnerId> selectedBPartnerIds = nightlyRun
				? ImmutableList.of()
				: retrieveSelectedBPartnerIds();

		final VATaxIDMassCheckResult result = massCheckService.run(VATaxIDMassCheckRequest.builder()
				.selectedBPartnerIds(selectedBPartnerIds)
				.maxChecksPerRun(p_MaxChecksPerRun)
				.pinstanceId(getPinstanceId())
				.nightlyRun(nightlyRun)
				.build());

		return result.getCheckedCount() + " checked, " + result.getPendingCount() + " pending";
	}

	/**
	 * The {@code C_BPartner_ID}s this run's own selection covers — a single record or a multi-record
	 * selection alike, per {@link #retrieveSelectedRecordsQueryBuilder}. Only ever called when
	 * {@link #getTableName()} is non-null (see {@link #doIt()}), so this never hits the
	 * {@code @NoSelection@} branch that method throws for a genuinely selection-less run.
	 */
	@NonNull
	private ImmutableList<BPartnerId> retrieveSelectedBPartnerIds()
	{
		return retrieveSelectedRecordsQueryBuilder(I_C_BPartner.class)
				.orderBy(I_C_BPartner.COLUMNNAME_C_BPartner_ID)
				.create()
				.listImmutable(I_C_BPartner.class)
				.stream()
				.map(bpartnerRecord -> BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID()))
				.collect(ImmutableList.toImmutableList());
	}
}

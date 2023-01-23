/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.bpartner.process;

import de.metas.bpartner.service.BPartnerStats;
import de.metas.bpartner.service.IBPartnerStatsDAO;
import de.metas.bpartner.service.impl.BPartnerStatsService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner_Stats;

public class C_BPartner_Stats_ComputeBPartnerStats extends JavaProcess implements IProcessPrecondition
{
	private final BPartnerStatsService bpartnerStatsService = SpringContextHolder.instance.getBean(BPartnerStatsService.class);

	@Override
	protected String doIt()
	{
		final I_C_BPartner_Stats stats = getRecord(I_C_BPartner_Stats.class);
		final BPartnerStats bpStats = Services.get(IBPartnerStatsDAO.class).getCreateBPartnerStats(stats.getC_BPartner_ID());
		bpartnerStatsService.updateBPartnerStatistics(bpStats);
		return "@Success@";
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		if (!I_C_BPartner_Stats.Table_Name.equals(context.getTableName()))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not running on C_BPartner_Stats table");
		}

		return ProcessPreconditionsResolution.accept();
	}
}

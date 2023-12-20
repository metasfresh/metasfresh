package de.metas.dunning.process;

/*
 * #%L
 * de.metas.dunning
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.dunning.api.IDunningBL;
import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.api.IDunningDAO;
import de.metas.dunning.api.impl.RecomputeDunningCandidatesQuery;
import de.metas.dunning.interfaces.I_C_Dunning;
import de.metas.dunning.interfaces.I_C_DunningLevel;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.dunning.spi.IDunnableSource;
import de.metas.organization.OrgId;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnableAdapter;

import javax.annotation.Nullable;
import java.sql.Timestamp;

/**
 * Process responsible for generating dunning candidates for all configured {@link IDunnableSource}s
 *
 * @author tsa
 */
public class C_Dunning_Candidate_Create extends JavaProcess
{
	private static final String PARAM_DunningDate = "DunningDate";
	@Nullable
	private Timestamp p_DunningDate = null;

	private static final String PARAM_IsOnlyUpdate = "IsOnlyUpdate";
	private boolean p_IsOnlyUpdate = false;

	private static final String PARAM_AD_Org_ID = I_C_Dunning_Candidate.COLUMNNAME_AD_Org_ID;
	@Nullable
	private OrgId p_AD_Org_ID = null;

	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IDunningBL dunningBL = Services.get(IDunningBL.class);
	private final IDunningDAO dunningDAO = Services.get(IDunningDAO.class);

	@Override
	protected void prepare()
	{
		// Defaults
		p_DunningDate = Env.getContextAsDate(getCtx(), "#Date");
		p_IsOnlyUpdate = false;
		p_AD_Org_ID = null;

		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			if (para.getParameter() == null)
			{
				// skip if no parameter value
				continue;
			}

			final String name = para.getParameterName();
			if (PARAM_DunningDate.equals(name))
			{
				p_DunningDate = para.getParameterAsTimestamp();
			}
			else if (PARAM_IsOnlyUpdate.equals(name))
			{
				p_IsOnlyUpdate = para.getParameterAsBoolean();
			}
			else if (PARAM_AD_Org_ID.equals(name))
			{
				p_AD_Org_ID = para.getParameterAsRepoId(OrgId::ofRepoIdOrNull);
			}
		}
	}

	@Override
	protected String doIt()
	{
		//
		// Generate dunning candidates
		for (final I_C_Dunning dunning : dunningDAO.retrieveDunnings())
		{
			for (final I_C_DunningLevel dunningLevel : dunningDAO.retrieveDunningLevels(dunning))
			{
				generateCandidates(dunningLevel);
			}
		}



		return MSG_OK;
	}

	private void generateCandidates(final I_C_DunningLevel dunningLevel)
	{
		trxManager.runInNewTrx(new TrxRunnableAdapter()
		{

			@Override
			public void run(final String localTrxName)
			{
				final RecomputeDunningCandidatesQuery recomputeDunningCandidatesQuery = buildRecomputeDunningCandidatesQuery();
				final IDunningContext context = dunningBL.createDunningContext(getCtx(), dunningLevel, p_DunningDate, get_TrxName(), recomputeDunningCandidatesQuery);

				final int countDelete;
				if (!p_IsOnlyUpdate)
				{
					countDelete = dunningDAO.deleteNotProcessedCandidates(context, dunningLevel);
				}
				else
				{
					countDelete = dunningDAO.deleteTargetObsoleteCandidates(recomputeDunningCandidatesQuery, dunningLevel);
				}
				addLog("@C_DunningLevel@ " + dunningLevel.getName() + ": " + countDelete + " record(s) deleted");

				final int countCreateUpdate = dunningBL.createDunningCandidates(context);
				addLog("@C_DunningLevel@ " + dunningLevel.getName() + ": " + countCreateUpdate + " record(s) created/updated");
			}
		});
	}

	@NonNull
	private RecomputeDunningCandidatesQuery buildRecomputeDunningCandidatesQuery()
	{
		return RecomputeDunningCandidatesQuery.builder()
				.onlyTargetRecordsFilter(p_IsOnlyUpdate ? getProcessInfo().getQueryFilterOrElseTrue() : null)
				.orgId(p_AD_Org_ID)
				.build();
	}
}

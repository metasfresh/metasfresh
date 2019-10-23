package org.adempiere.ad.migration.service.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.adempiere.ad.migration.model.I_AD_Migration;
import org.adempiere.ad.migration.model.I_AD_MigrationStep;
import org.adempiere.ad.migration.model.X_AD_Migration;
import org.adempiere.ad.migration.service.IMigrationBL;
import org.adempiere.ad.migration.service.IMigrationDAO;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.util.Services;

public class MigrationBL implements IMigrationBL
{
	@Override
	public void updateStatus(I_AD_Migration migration)
	{
		final IMigrationDAO dao = Services.get(IMigrationDAO.class);

		final int total = dao.countMigrationSteps(migration, null);
		final int applied = dao.countMigrationSteps(migration, X_AD_Migration.STATUSCODE_Applied);
		final int unapplied = dao.countMigrationSteps(migration, X_AD_Migration.STATUSCODE_Unapplied);
		final int failed = dao.countMigrationSteps(migration, X_AD_Migration.STATUSCODE_Failed);

		if (applied == total && applied > 0)
		{
			migration.setStatusCode(X_AD_Migration.STATUSCODE_Applied);
			migration.setApply(X_AD_Migration.APPLY_Rollback);
		}
		else if (unapplied == total && unapplied > 0)
		{
			migration.setStatusCode(X_AD_Migration.STATUSCODE_Unapplied);
			migration.setApply(X_AD_Migration.APPLY_Apply);
		}
		else if (total > applied && applied > 0)
		{
			migration.setStatusCode(X_AD_Migration.STATUSCODE_PartiallyApplied);
			migration.setApply(X_AD_Migration.APPLY_Rollback);
		}
		else if (total == failed && failed > 0)
		{
			migration.setStatusCode(X_AD_Migration.STATUSCODE_Failed);
			migration.setApply(X_AD_Migration.APPLY_Rollback);
		}

		InterfaceWrapperHelper.save(migration);
	}

	@Override
	public void sortStepsByCreated(I_AD_Migration migration)
	{
		if (migration == null || migration.getAD_Migration_ID() <= 0)
		{
			return;
		}

		final List<I_AD_MigrationStep> steps = Services.get(IMigrationDAO.class).retrieveSteps(migration, true);

		Collections.sort(steps, new Comparator<I_AD_MigrationStep>()
		{
			@Override
			public int compare(I_AD_MigrationStep s1, I_AD_MigrationStep s2)
			{
				return s1.getCreated().compareTo(s2.getCreated());
			}
		});

		int seqNo = 10;
		for (final I_AD_MigrationStep step : steps)
		{
			step.setSeqNo(seqNo);
			InterfaceWrapperHelper.save(step);
			seqNo += 10;
		}
	}

	@Override
	public void mergeMigration(final I_AD_Migration to, final I_AD_Migration from)
	{
		Services.get(IMigrationDAO.class).mergeMigration(to, from);
	}

	@Override
	public String getSummary(I_AD_Migration migration)
	{
		if (migration == null)
		{
			return "";
		}

		return "Migration["
				+ migration.getSeqNo() + "-" + migration.getName() + "-" + migration.getEntityType()
				+ ", ID=" + migration.getAD_Migration_ID()
				+ "]";
	}

	@Override
	public void setSeqNo(final I_AD_Migration migration)
	{
		final int maxSeqNo = Services.get(IMigrationDAO.class).getMigrationLastSeqNo(migration);
		final int nextSeqNo = maxSeqNo + 10;
		migration.setSeqNo(nextSeqNo);
	}

	@Override
	public String getSummary(final I_AD_MigrationStep step)
	{
		if (step == null)
		{
			return "";
		}

		final I_AD_Migration parent = step.getAD_Migration();
		return "Migration: " + parent.getName() + ", Step: " + step.getSeqNo() + ", Type: " + step.getStepType();
	}

	@Override
	public void setSeqNo(final I_AD_MigrationStep step)
	{
		final int maxSeqNo = Services.get(IMigrationDAO.class).getMigrationStepLastSeqNo(step);
		final int nextSeqNo = maxSeqNo + 10;
		step.setSeqNo(nextSeqNo);
	}

}

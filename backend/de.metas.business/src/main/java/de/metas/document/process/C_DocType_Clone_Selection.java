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

package de.metas.document.process;

import de.metas.document.IDocTypeBL;
import de.metas.organization.OrgId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.PInstanceId;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_DocType;
import org.compiere.util.DB;

import java.util.Iterator;

public class C_DocType_Clone_Selection extends JavaProcess implements IProcessPrecondition
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);

	private static final String PARAM_AD_ORG_ID = I_AD_Org.COLUMNNAME_AD_Org_ID;
	@Param(parameterName = PARAM_AD_ORG_ID, mandatory = true)
	private int toOrgId;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final OrgId orgId = OrgId.ofRepoId(toOrgId);
		final PInstanceId pinstanceId = getPinstanceId();

		final Iterator<I_C_DocType> docTypes = docTypeBL.retrieveForSelection(pinstanceId);

		int count = 0;
		while (docTypes.hasNext())
		{
			docTypeBL.cloneToOrg(docTypes.next(), orgId);
			count++;
		}

		return "@Copied@=" + count;
	}

	@Override
	@RunOutOfTrx
	protected void prepare()
	{
		if (createSelection() <= 0)
		{
			throw new AdempiereException("@NoSelection@");
		}
	}

	private int createSelection()
	{
		final IQueryBuilder<I_C_DocType> queryBuilder = createDocTypesQueryBuilder();

		final PInstanceId adPInstanceId = getPinstanceId();

		Check.assumeNotNull(adPInstanceId, "adPInstanceId is not null");

		DB.deleteT_Selection(adPInstanceId, ITrx.TRXNAME_ThreadInherited);

		return queryBuilder
				.create()
				.createSelection(adPInstanceId);
	}

	@NonNull
	private IQueryBuilder<I_C_DocType> createDocTypesQueryBuilder()
	{
		final IQueryFilter<I_C_DocType> userSelectionFilter = getProcessInfo().getQueryFilterOrElse(null);

		if (userSelectionFilter == null)
		{
			throw new AdempiereException("@NoSelection@");
		}

		return queryBL
				.createQueryBuilder(I_C_DocType.class, getCtx(), ITrx.TRXNAME_None)
				.filter(userSelectionFilter);
	}
}

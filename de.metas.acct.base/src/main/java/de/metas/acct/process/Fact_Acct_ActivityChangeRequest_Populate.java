package de.metas.acct.process;

import java.util.HashSet;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.acct.model.I_Fact_Acct_ActivityChangeRequest;
import de.metas.acct.model.I_Fact_Acct_ActivityChangeRequest_Source_v;
import de.metas.process.Param;
import de.metas.process.Process;
import de.metas.process.JavaProcess;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Populates {@link I_Fact_Acct_ActivityChangeRequest} table by filtering from {@link I_Fact_Acct_ActivityChangeRequest_Source_v}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 * @task http://dewiki908/mediawiki/index.php/09110_Konten_KST_Pflicht_%28105477200774%29
 */
@Process(requiresCurrentRecordWhenCalledFromGear = false)
public class Fact_Acct_ActivityChangeRequest_Populate extends JavaProcess
{
	@Param(parameterName = "C_Period_ID")
	private int p_C_Period_ID;
	@Param(parameterName = "C_ElementValue_ID")
	private int p_C_ElementValue_ID;
	@Param(parameterName = "C_BPartner_ID")
	private int p_C_BPartner_ID;
	@Param(parameterName = "M_Product_ID")
	private int p_M_Product_ID;
	@Param(parameterName = "C_DocType_ID")
	private int p_C_DocType_ID;
	@Param(parameterName = "DocumentNo")
	private String p_DocumentNo;
	@Param(parameterName = "DocBaseType")
	private String p_DocBaseType;
	@Param(parameterName = "IsMandatoryActivity")
	private Boolean p_IsMandatoryActivity;
	@Param(parameterName = "IsActivityNull")
	private Boolean p_IsActivityNull;
	//
	@Param(parameterName = "DeleteOld")
	private boolean p_DeleteOld;

	// services
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient IADTableDAO tableDAO = Services.get(IADTableDAO.class);

	@Override
	protected String doIt() throws Exception
	{
		//
		// Delete previous entries
		if (p_DeleteOld)
		{
			queryBL.createQueryBuilder(I_Fact_Acct_ActivityChangeRequest.class, getCtx(), ITrx.TRXNAME_ThreadInherited)
					.addOnlyContextClient()
					.create()
					.deleteDirectly();
		}

		//
		// Populate the requests table with new entries
		final int createdCount = retrieveFactAccts()
				.create()
				.insertDirectlyInto(I_Fact_Acct_ActivityChangeRequest.class)
				.mapCommonColumns()
				.mapPrimaryKey()
				.execute();

		return "@Created@ #" + createdCount;
	}

	private final IQueryBuilder<I_Fact_Acct_ActivityChangeRequest_Source_v> retrieveFactAccts()
	{
		final IQueryBuilder<I_Fact_Acct_ActivityChangeRequest_Source_v> queryBuilder = queryBL.createQueryBuilder(I_Fact_Acct_ActivityChangeRequest_Source_v.class, getCtx(),
				ITrx.TRXNAME_ThreadInherited);
		if (p_C_Period_ID > 0)
		{
			queryBuilder.addEqualsFilter(I_Fact_Acct_ActivityChangeRequest_Source_v.COLUMN_C_Period_ID, p_C_Period_ID);
		}
		if (p_C_ElementValue_ID > 0)
		{
			queryBuilder.addEqualsFilter(I_Fact_Acct_ActivityChangeRequest_Source_v.COLUMN_Account_ID, p_C_ElementValue_ID);
		}
		if (p_C_BPartner_ID > 0)
		{
			queryBuilder.addEqualsFilter(I_Fact_Acct_ActivityChangeRequest_Source_v.COLUMN_C_BPartner_ID, p_C_BPartner_ID);
		}
		if (p_M_Product_ID > 0)
		{
			queryBuilder.addEqualsFilter(I_Fact_Acct_ActivityChangeRequest_Source_v.COLUMN_M_Product_ID, p_M_Product_ID);
		}
		if (p_C_DocType_ID > 0)
		{
			queryBuilder.addEqualsFilter(I_Fact_Acct_ActivityChangeRequest_Source_v.COLUMN_C_DocType_ID, p_C_DocType_ID);
		}
		if (!Check.isEmpty(p_DocumentNo, true))
		{
			queryBuilder.addEqualsFilter(I_Fact_Acct_ActivityChangeRequest_Source_v.COLUMN_DocumentNo, p_DocumentNo.trim());
		}
		if (!Check.isEmpty(p_DocBaseType, true))
		{
			queryBuilder.addEqualsFilter(I_Fact_Acct_ActivityChangeRequest_Source_v.COLUMN_DocBaseType, p_DocBaseType);
		}
		if (p_IsMandatoryActivity != null)
		{
			queryBuilder.addEqualsFilter(I_Fact_Acct_ActivityChangeRequest_Source_v.COLUMN_IsMandatoryActivity, p_IsMandatoryActivity);
		}
		if (p_IsActivityNull != null)
		{
			if (p_IsActivityNull)
			{
				queryBuilder.addEqualsFilter(I_Fact_Acct_ActivityChangeRequest_Source_v.COLUMN_C_Activity_ID, null);
			}
			else
			{
				queryBuilder.addNotEqualsFilter(I_Fact_Acct_ActivityChangeRequest_Source_v.COLUMN_C_Activity_ID, null);
			}
		}

		//
		// Only supported tables
		{
			final Set<Integer> adTableIds = new HashSet<>();
			for (final String tableName : Fact_Acct_ActivityChangeRequest_Process.headerTableName2lineModelClass.keySet())
			{
				final int adTableId = tableDAO.retrieveTableId(tableName);
				adTableIds.add(adTableId);
			}
			Check.assumeNotEmpty(adTableIds, "Some supported tables shall be defined, but there are none"); // shall not happen

			queryBuilder.addInArrayOrAllFilter(I_Fact_Acct_ActivityChangeRequest_Source_v.COLUMN_AD_Table_ID, adTableIds);
		}

		return queryBuilder;
	}
}

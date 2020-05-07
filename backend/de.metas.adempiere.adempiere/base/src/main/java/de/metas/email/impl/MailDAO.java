package de.metas.email.impl;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_MailConfig;
import org.compiere.model.I_C_DocType;

import de.metas.email.IMailDAO;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class MailDAO implements IMailDAO
{

	@Override
	public List<I_AD_MailConfig> retrieveMailConfigs(final I_AD_Client client, final int orgID, final int processID, final I_C_DocType docType, final String customType)
	{
		final IQueryBuilder<I_AD_MailConfig> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_MailConfig.class, client);

		// Client ID
		queryBuilder.addEqualsFilter(I_AD_MailConfig.COLUMNNAME_AD_Client_ID, client.getAD_Client_ID());

		if (!Check.isEmpty(customType, true))
		{
			queryBuilder.addEqualsFilter(I_AD_MailConfig.COLUMNNAME_CustomType, customType);

		}
		else if (processID > 0)
		{
			queryBuilder.addEqualsFilter(I_AD_MailConfig.COLUMNNAME_AD_Process_ID, processID);

		}

		// task FRESH-203
		// DocBaseType and DocSubType added in the mail config

		if (docType != null)
		{
			final String docBaseType = docType.getDocBaseType();

			if (!Check.isEmpty(docBaseType, true))
			{
				queryBuilder.addEqualsFilter(I_AD_MailConfig.COLUMN_DocBaseType, docBaseType);
			}

			final String docSubType = docType.getDocSubType();

			if (!Check.isEmpty(docSubType, true))
			{
				queryBuilder.addInArrayOrAllFilter(I_AD_MailConfig.COLUMN_DocSubType, docSubType, null);
			}

		}

		// Only active records
		queryBuilder.addOnlyActiveRecordsFilter();

		// Order by Org, Desc, Nulls Last
		queryBuilder.orderBy()
				.addColumn(I_AD_MailConfig.COLUMNNAME_AD_Org_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_AD_MailConfig.COLUMN_DocSubType, Direction.Ascending, Nulls.Last)
				.endOrderBy();

		final List<I_AD_MailConfig> configs = queryBuilder.create().list();
		
		
		return configs;
	}
}

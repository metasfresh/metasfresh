package de.metas.email.impl;

import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.service.ClientId;
import org.adempiere.service.OrgId;
import org.compiere.model.I_AD_MailConfig;

import de.metas.document.DocBaseAndSubType;
import de.metas.email.EMailCustomType;
import de.metas.email.IMailDAO;
import de.metas.process.AdProcessId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

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
	public List<I_AD_MailConfig> retrieveMailConfigs(
			@NonNull final ClientId clientId,
			final OrgId orgId_NOTUSED, // FIXME: why is not used????
			@Nullable final AdProcessId processId,
			@Nullable final DocBaseAndSubType docBaseAndSubType,
			@Nullable final EMailCustomType customType)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_AD_MailConfig> queryBuilder = queryBL.createQueryBuilderOutOfTrx(I_AD_MailConfig.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_MailConfig.COLUMNNAME_AD_Client_ID, clientId);

		// Order by Org, Desc, Nulls Last
		queryBuilder.orderBy()
				.addColumn(I_AD_MailConfig.COLUMNNAME_AD_Org_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_AD_MailConfig.COLUMN_DocSubType, Direction.Ascending, Nulls.Last)
				.endOrderBy();

		if (customType != null)
		{
			queryBuilder.addEqualsFilter(I_AD_MailConfig.COLUMNNAME_CustomType, customType.getCode());
		}
		else if (processId != null)
		{
			queryBuilder.addEqualsFilter(I_AD_MailConfig.COLUMNNAME_AD_Process_ID, processId);

		}

		//
		// DocBaseType and DocSubType added in the mail config (task FRESH-203)
		if (docBaseAndSubType != null)
		{
			final String docBaseType = docBaseAndSubType.getDocBaseType();
			if (!Check.isEmpty(docBaseType, true))
			{
				queryBuilder.addEqualsFilter(I_AD_MailConfig.COLUMN_DocBaseType, docBaseType);
			}

			final String docSubType = docBaseAndSubType.getDocSubType();
			if (!Check.isEmpty(docSubType, true))
			{
				queryBuilder.addInArrayFilter(I_AD_MailConfig.COLUMN_DocSubType, docSubType, null);
			}

		}

		return queryBuilder.create().list();
	}
}

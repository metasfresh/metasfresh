package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.config;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;

import de.metas.util.Services;
import de.metas.util.lang.RepoIdAware;
import de.metas.vertical.healthcare.forum_datenaustausch_ch.commons.model.I_HC_Forum_Datenaustausch_Config;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_base
 * %%
 * Copyright (C) 2018 metas GmbH
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

/** Under the hood, the two configs are both stored in {@link I_HC_Forum_Datenaustausch_Config}, so the repositories share some code. */
@UtilityClass
public class ConfigRepositoryUtil
{
	public I_HC_Forum_Datenaustausch_Config retrieveRecordForQueryOrNull(@NonNull final ConfigQuery query)
	{
		final IQueryBuilder<I_HC_Forum_Datenaustausch_Config> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_HC_Forum_Datenaustausch_Config.class)
				.addOnlyActiveRecordsFilter();
		if (query.getBpartnerId() != null)
		{
			queryBuilder.addInArrayFilter(I_HC_Forum_Datenaustausch_Config.COLUMN_Bill_BPartner_ID, query.getBpartnerId(), null);
		}

		final I_HC_Forum_Datenaustausch_Config configRecord = queryBuilder
				.orderByDescending(I_HC_Forum_Datenaustausch_Config.COLUMN_Bill_BPartner_ID)
				.create()
				.first(I_HC_Forum_Datenaustausch_Config.class);
		return configRecord;
	}


	@Value
	@Builder
	public static class ConfigQuery
	{
		RepoIdAware bpartnerId;
	}
}

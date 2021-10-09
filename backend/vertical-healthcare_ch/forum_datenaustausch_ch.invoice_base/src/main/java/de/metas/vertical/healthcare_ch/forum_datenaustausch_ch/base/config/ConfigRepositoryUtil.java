package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.config;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.util.Services;
import de.metas.vertical.healthcare.forum_datenaustausch_ch.commons.model.I_HC_Forum_Datenaustausch_Config;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;

import javax.annotation.Nullable;

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

/**
 * Under the hood, the three configs are all stored in {@link I_HC_Forum_Datenaustausch_Config}, so the repositories share some code.
 */
@UtilityClass
public class ConfigRepositoryUtil
{
	@Nullable
	public I_HC_Forum_Datenaustausch_Config retrieveRecordForQueryOrNull(@Nullable final BPartnerQuery query)
	{
		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_HC_Forum_Datenaustausch_Config> queryBuilder = queryBL
				.createQueryBuilder(I_HC_Forum_Datenaustausch_Config.class)
				.addOnlyActiveRecordsFilter();
		if (query != null)
		{
			final BPartnerId bPartnerId = bpartnerDAO
					.retrieveBPartnerIdBy(query)
					.orElse(null);
			queryBuilder.addInArrayFilter(I_HC_Forum_Datenaustausch_Config.COLUMNNAME_Bill_BPartner_ID, bPartnerId, null);
		}

		return queryBuilder
				.orderByDescending(I_HC_Forum_Datenaustausch_Config.COLUMNNAME_Bill_BPartner_ID)
				.create()
				.first(I_HC_Forum_Datenaustausch_Config.class);
	}
}

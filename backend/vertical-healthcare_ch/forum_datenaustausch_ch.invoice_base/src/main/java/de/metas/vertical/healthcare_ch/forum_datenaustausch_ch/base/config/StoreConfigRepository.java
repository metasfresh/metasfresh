package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.config;

import de.metas.bpartner.service.BPartnerQuery;
import de.metas.vertical.healthcare.forum_datenaustausch_ch.commons.model.I_HC_Forum_Datenaustausch_Config;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.ForumDatenaustauschChConstants;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_commons
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

@Repository
@Profile(ForumDatenaustauschChConstants.PROFILE)
public class StoreConfigRepository
{
	public StoreConfig getForQueryOrNull(@NonNull final BPartnerQuery query)
	{
		final I_HC_Forum_Datenaustausch_Config configRecord = ConfigRepositoryUtil.retrieveRecordForQueryOrNull(query);
		return ofRecordOrNull(configRecord);
	}

	private StoreConfig ofRecordOrNull(@Nullable final I_HC_Forum_Datenaustausch_Config queryRecord)
	{
		if (queryRecord == null)
		{
			return null;
		}
		return StoreConfig
				.builder()
				.directory(queryRecord.getStoreDirectory())
				.build();
	}
}

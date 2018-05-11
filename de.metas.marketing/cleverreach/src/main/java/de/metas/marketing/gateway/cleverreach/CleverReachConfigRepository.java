package de.metas.marketing.gateway.cleverreach;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;

import de.metas.marketing.cleverreach.model.I_MKTG_CleverReach_Config;

/*
 * #%L
 * marketing-cleverreach
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

public class CleverReachConfigRepository
{
	public CleverReachConfig getById(final int id)
	{
		final I_MKTG_CleverReach_Config configRecord = Services.get(IQueryBL.class).createQueryBuilder(I_MKTG_CleverReach_Config.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MKTG_CleverReach_Config.COLUMN_MKTG_Platform_ID, id)
				.create()
				.firstOnly(I_MKTG_CleverReach_Config.class);

		return CleverReachConfig.builder()
				.client_id(configRecord.getCustomerNo())
				.login(configRecord.getUserName())
				.password(configRecord.getPassword())
				.build();

	}
}

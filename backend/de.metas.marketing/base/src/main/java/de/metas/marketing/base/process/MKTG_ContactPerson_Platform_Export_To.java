package de.metas.marketing.base.process;

import de.metas.marketing.base.CampaignSyncService;
import de.metas.marketing.base.model.CampaignId;
import de.metas.marketing.base.model.I_MKTG_Campaign;
import de.metas.marketing.base.model.SyncDirection;
import de.metas.process.JavaProcess;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;

/*
 * #%L
 * marketing-base
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

public class MKTG_ContactPerson_Platform_Export_To extends JavaProcess
{
	private final CampaignSyncService syncService = SpringContextHolder.instance.getBean(CampaignSyncService.class);

	@Override
	protected String doIt()
	{
		syncService.syncContacts(getCampaignId(), SyncDirection.LOCAL_TO_REMOTE);
		return MSG_OK;
	}

	@NonNull
	private CampaignId getCampaignId()
	{
		return TableRecordReference.of(getTableName(), getRecord_ID())
				.getIdAssumingTableName(I_MKTG_Campaign.Table_Name, CampaignId::ofRepoId);
	}
}

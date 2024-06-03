/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.procurement.base.process;

import de.metas.common.procurement.sync.protocol.dto.SyncBPartner;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutBPartnersRequest;
import de.metas.process.JavaProcess;
import de.metas.procurement.base.IAgentSyncBL;
import de.metas.procurement.base.impl.SyncObjectsFactory;
import org.compiere.SpringContextHolder;

import java.util.List;

public class C_BPartner_PushAllToProcurement extends JavaProcess
{
	private final IAgentSyncBL agentSyncBL = SpringContextHolder.instance.getBean(IAgentSyncBL.class);

	@Override
	protected String doIt()
	{
		final List<SyncBPartner> syncBPartners = SyncObjectsFactory.newFactory()
				.createAllSyncBPartners();

		agentSyncBL.syncBPartners(PutBPartnersRequest.builder()
										  .bpartners(syncBPartners)
										  .build());

		return "@Sent@ (" + syncBPartners.size() + " records)";
	}
}

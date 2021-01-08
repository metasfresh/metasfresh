/*
 * #%L
 * de-metas-common-procurement
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.common.procurement.sync;

import de.metas.common.procurement.sync.protocol.RequestToProcurementWeb;
import de.metas.common.procurement.sync.protocol.dto.SyncConfirmation;
import de.metas.common.procurement.sync.protocol.dto.SyncRfQ;
import de.metas.common.procurement.sync.protocol.dto.SyncRfQCloseEvent;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutBPartnersRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutInfoMessageRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutProductsRequest;

import java.util.List;

/**
 * This is implemented in the procurementUI (agent) and called according to {@link RequestToProcurementWeb}s received from metasfresh.
 * <p>
 * Note that currently we don't have to use the Consumes and Produces annotations, because we specify those types in the client.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public interface IAgentSync
{
	void syncBPartners(PutBPartnersRequest request);

	void syncProducts(PutProductsRequest request);

	void syncInfoMessage(PutInfoMessageRequest request);

	void confirm(List<SyncConfirmation> syncConfirmations);

	void syncRfQs(List<SyncRfQ> syncRfqs);

	void closeRfQs(List<SyncRfQCloseEvent> syncRfQCloseEvents);
}

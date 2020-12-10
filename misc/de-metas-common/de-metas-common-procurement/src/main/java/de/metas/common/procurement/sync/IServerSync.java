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

import de.metas.common.procurement.sync.protocol.dto.SyncBPartner;
import de.metas.common.procurement.sync.protocol.dto.SyncProduct;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.PutProductSuppliesRequest;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.PutWeeklySupplyRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutRfQChangeRequest;

import java.util.List;

/**
 * This is implemented in metasfresh (server) and called from the procurementUI (agent).
 * <p>
 * Note that currently we don't have to use the Consumes and Produces annotations, because we specify those types in the client.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IServerSync
{
	/**
	 * Get all partners that have at least one contract, with all their contracts and products.
	 * Also get all products that do not belong to a particular contract, but can be offered by the vendor none the less.
	 */
	List<SyncBPartner> getAllBPartners();

	List<SyncProduct> getAllProducts();

	String getInfoMessage();

	/**
	 * Report a product supply to metasfresh. Create <code>PMM_QtyReport_Event</code>.
	 */
	void reportProductSupplies(PutProductSuppliesRequest request);

	/**
	 * Report the supplier's forecast.
	 */
	void reportWeekSupply(PutWeeklySupplyRequest request);
	
	void reportRfQChanges(PutRfQChangeRequest request);
}

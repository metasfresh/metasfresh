package de.metas.vertical.pharma.vendor.gateway.msv3;

import de.metas.vertical.pharma.vendor.gateway.msv3.availability.MSV3AvailiabilityClient;
import de.metas.vertical.pharma.vendor.gateway.msv3.config.MSV3ClientConfig;
import de.metas.vertical.pharma.vendor.gateway.msv3.purchaseOrder.MSV3PurchaseOrderClient;
import de.metas.vertical.pharma.vendor.gateway.msv3.testconnection.MSV3TestConnectionClient;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.msv3
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

public interface MSV3ClientFactory
{
	String getVersionId();

	MSV3TestConnectionClient newTestConnectionClient(MSV3ClientConfig config);

	MSV3AvailiabilityClient newAvailabilityClient(MSV3ClientConfig config);

	MSV3PurchaseOrderClient newPurchaseOrderClient(MSV3ClientConfig config);
}

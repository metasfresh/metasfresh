package de.metas.shipper.gateway.derkurier;

import de.metas.shipper.gateway.spi.model.Address;

/*
 * #%L
 * de.metas.shipper.gateway.derkurier
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

public class DerKurierConstants
{
	/** Important: needs to be kept in sync with the respective shippergateway AD_Reflist value in M_Sipper. */
	public static final String SHIPPER_GATEWAY_ID = "derKurier";

	public static final String SYSCONFIG_DERKURIER_LABEL_PROCESS_ID = "de.metas.shipper.gateway.derkurier.PackageLabel.AD_Process_ID";

	public static final String DATE_FORMAT = "yyyy-MM-dd";

	public static final String TIME_FORMAT = "HH:mm";

	/** used to join&split the street1 and street2 that we have in {@link Address} into the one "street" field that we have at "Der Kurier" */
	public static final String STREET_DELIMITER = " - ";
}

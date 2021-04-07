/*
 * #%L
 * de-metas-camel-alberta-camelroutes
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

package de.metas.camel.alberta;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface GetPatientsRouteConstants
{
	String ALBERTA_SYSTEM_NAME = "ALBERTA";
	String ESR_TYPE_BPARTNER = "BPartner";
	String COUNTRY_CODE_DE = "DE";

	String EXTERNAL_ID_PREFIX = "ext-";
	String BILLING_ADDR_PREFIX = "billingAddress_";
	String SHIPPING_ADDR_PREFIX = "shippingAddress_";

	//Route Headers
	String HEADER_ORG_CODE = "orgCode";

	//Route properties
	String ROUTE_PROPERTY_ORG_CODE = "orgCode";
	String ROUTE_PROPERTY_ALBERTA_CONN_DETAILS = "albertaConnectionDetails";
	String ROUTE_PROPERTY_CURRENT_PATIENT = "currentPatient";
	String ROUTE_PROPERTY_BP_IDENTIFIER_TO_ROLE = "bpIdentifier2Role";

	//Alberta constants
	@AllArgsConstructor
	@Getter
	enum PatientStatus {
		CREATED("created"),
		UPDATED("updated");

		private final String value;
	}
}

/*
 * #%L
 * de.metas.shipper.gateway.dpd
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.shipper.gateway.dpd.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

@UtilityClass
public class DpdClientUtil
{
	public static final String LOGIN_SERVICE_API_URL = "https://public-ws-stage.dpd.com/services/LoginService/V2_0/";
	public static final String SHIPMENT_SERVICE_API_URL = "https://public-ws-stage.dpd.com/services/ShipmentService/V3_2/";

	@NonNull
	public static WebServiceTemplate createWebServiceTemplate()
	{
		final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setPackagesToScan(
				"com.dpd.common"
		);

		final WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		//		webServiceTemplate.setDefaultUri(apiUrl);
		webServiceTemplate.setMarshaller(marshaller);
		webServiceTemplate.setUnmarshaller(marshaller);
		return webServiceTemplate;
	}
}

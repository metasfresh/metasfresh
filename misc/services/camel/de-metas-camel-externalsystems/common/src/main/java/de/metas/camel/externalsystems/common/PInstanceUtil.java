/*
 * #%L
 * de-metas-camel-externalsystems-common
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.common;

import de.metas.common.externalsystem.JsonExternalSystemRequest;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.camel.Exchange;

import static de.metas.common.externalsystem.ExternalSystemConstants.HEADER_PINSTANCE_ID;

@UtilityClass
public class PInstanceUtil
{
	public void setPInstanceHeader(
			@NonNull final Exchange exchange,
			@NonNull final JsonExternalSystemRequest externalSystemRequest)
	{
		if (externalSystemRequest.getAdPInstanceId() != null)
		{
			exchange.getIn().setHeader(HEADER_PINSTANCE_ID, externalSystemRequest.getAdPInstanceId().getValue());
			exchange.getIn().setHeader(ExternalSystemCamelConstants.HEADER_PINSTANCE_ID, externalSystemRequest.getAdPInstanceId().getValue());
		}
	}
}

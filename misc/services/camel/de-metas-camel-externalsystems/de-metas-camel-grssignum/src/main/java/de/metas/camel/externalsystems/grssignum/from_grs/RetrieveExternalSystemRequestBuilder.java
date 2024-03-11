/*
 * #%L
 * de-metas-camel-grssignum
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

package de.metas.camel.externalsystems.grssignum.from_grs;

import de.metas.camel.externalsystems.common.auth.TokenCredentials;
import de.metas.camel.externalsystems.common.v2.RetrieveExternalSystemInfoCamelRequest;
import de.metas.common.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.springframework.security.core.context.SecurityContextHolder;

import static de.metas.camel.externalsystems.grssignum.GRSSignumConstants.GRSSIGNUM_SYSTEM_NAME;

@UtilityClass
public class RetrieveExternalSystemRequestBuilder
{
	public static void buildAndAttachRetrieveExternalSystemRequest(@NonNull final Exchange exchange)
	{
		final TokenCredentials credentials = (TokenCredentials)SecurityContextHolder.getContext().getAuthentication().getCredentials();

		if (Check.isBlank(credentials.getExternalSystemValue()))
		{
			throw new RuntimeCamelException("Missing TokenCredentials.ExternalSystemValue!");
		}

		final RetrieveExternalSystemInfoCamelRequest request = RetrieveExternalSystemInfoCamelRequest.builder()
				.externalSystemChildValue(credentials.getExternalSystemValue())
				.externalSystemConfigType(GRSSIGNUM_SYSTEM_NAME)
				.build();

		exchange.getIn().setBody(request, RetrieveExternalSystemInfoCamelRequest.class);
	}
}

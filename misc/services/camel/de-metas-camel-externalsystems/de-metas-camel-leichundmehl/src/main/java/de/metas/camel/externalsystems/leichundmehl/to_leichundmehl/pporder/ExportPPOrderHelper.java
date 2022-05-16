/*
 * #%L
 * de-metas-camel-leichundmehl
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

package de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.pporder;

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.ftp.FTPCredentials;
import de.metas.common.bpartner.v2.response.JsonResponseComposite;
import de.metas.common.bpartner.v2.response.JsonResponseLocation;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.camel.Predicate;

import java.util.Map;
import java.util.Objects;

import static de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants.ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_FTP_DIRECTORY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_FTP_HOST;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_FTP_PASSWORD;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_FTP_PORT;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_FTP_USERNAME;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_PP_ORDER_ID;

@UtilityClass
public class ExportPPOrderHelper
{
	@NonNull
	public FTPCredentials getFTPCredentials(@NonNull final Map<String, String> params)
	{
		if (Check.isBlank(params.get(ExternalSystemConstants.PARAM_FTP_HOST)))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_FTP_HOST);
		}

		if (Check.isBlank(params.get(PARAM_FTP_PORT)))
		{
			throw new RuntimeException("Missing mandatory param: " + PARAM_FTP_PORT);
		}

		if (Check.isBlank(params.get(ExternalSystemConstants.PARAM_FTP_USERNAME)))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_FTP_USERNAME);
		}

		if (Check.isBlank(params.get(ExternalSystemConstants.PARAM_FTP_PASSWORD)))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_FTP_PASSWORD);
		}

		if (Check.isBlank(params.get(PARAM_FTP_DIRECTORY)))
		{
			throw new RuntimeException("Missing mandatory param: " + PARAM_FTP_DIRECTORY);
		}

		if (Check.isBlank(params.get(ExternalSystemConstants.PARAM_PP_ORDER_ID)))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_PP_ORDER_ID);
		}

		return FTPCredentials.builder()
				.ftpHost(params.get(PARAM_FTP_HOST))
				.ftpPort(params.get(PARAM_FTP_PORT))
				.ftpUsername(params.get(PARAM_FTP_USERNAME))
				.ftpPassword(params.get(PARAM_FTP_PASSWORD))
				.ftpDirectory(params.get(PARAM_FTP_DIRECTORY))
				.ftpFilename(computeFileName(params.get(PARAM_PP_ORDER_ID)))
				.build();
	}

	@NonNull
	public String computeFileName(@NonNull final String pporderId)
	{
		return "ManufacturingOrder_" + pporderId + ".json";
	}

	@NonNull
	public Predicate ppOrderHasBPartnerAssigned()
	{
		return exchange -> {
			final ExportPPOrderRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_EXPORT_PP_ORDER_CONTEXT, ExportPPOrderRouteContext.class);

			return (context.getBPartnerId() != null && context.getBPartnerId().getValue() != 0);
		};
	}

	@NonNull
	public ImmutableList<String> getBPartnerGLNList(@NonNull final JsonResponseComposite jsonResponseComposite)
	{
		return jsonResponseComposite.getLocations()
				.stream()
				.map(JsonResponseLocation::getGln)
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}
}

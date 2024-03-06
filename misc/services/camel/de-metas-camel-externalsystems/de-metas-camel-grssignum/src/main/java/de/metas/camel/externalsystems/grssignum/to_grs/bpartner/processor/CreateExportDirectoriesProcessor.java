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
package de.metas.camel.externalsystems.grssignum.to_grs.bpartner.processor;

import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.grssignum.GRSSignumConstants;
import de.metas.camel.externalsystems.grssignum.to_grs.bpartner.ExportBPartnerRouteContext;
import de.metas.common.bpartner.v2.response.JsonResponseComposite;
import de.metas.common.externalsystem.JsonExportDirectorySettings;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.File;
import java.nio.file.Path;

public class CreateExportDirectoriesProcessor implements Processor
{
	private final ProcessLogger processLogger;

	public CreateExportDirectoriesProcessor(@NonNull final ProcessLogger processLogger)
	{
		this.processLogger = processLogger;
	}

	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final ExportBPartnerRouteContext routeContext = ProcessorHelper.getPropertyOrThrowError(exchange, GRSSignumConstants.ROUTE_PROPERTY_EXPORT_BPARTNER_CONTEXT, ExportBPartnerRouteContext.class);

		createBPartnerExportDirectories(routeContext);
	}

	private void createBPartnerExportDirectories(@NonNull final ExportBPartnerRouteContext routeContext)
	{
		final JsonExportDirectorySettings jsonExportDirectorySettings = routeContext.getJsonExportDirectorySettings();
		final String bpartnerCode = routeContext.getJsonResponseComposite().getBpartner().getCode();

		if (jsonExportDirectorySettings == null)
		{
			processLogger.logMessage("No export directories settings found for C_BPartner.Value: " + bpartnerCode, routeContext.getPinstanceId());
			return;
		}

		jsonExportDirectorySettings.getDirectoriesPath(bpartnerCode)
				.forEach(CreateExportDirectoriesProcessor::createDirectory);

		routeContext.setBPartnerBasePath(bpartnerCode);
	}

	private static void createDirectory(@NonNull final Path directoryPath)
	{
		final File directory = directoryPath.toFile();
		if (directory.exists())
		{
			return;
		}

		if (!directory.mkdirs())
		{
			throw new RuntimeException("Could not create directory with path: " + directoryPath);
		}
	}
}

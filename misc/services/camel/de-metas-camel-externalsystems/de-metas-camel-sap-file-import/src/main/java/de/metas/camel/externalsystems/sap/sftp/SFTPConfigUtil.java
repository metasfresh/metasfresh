/*
 * #%L
 * de-metas-camel-sap-file-import
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

package de.metas.camel.externalsystems.sap.sftp;

import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.time.Duration;
import java.util.Map;

@UtilityClass
public class SFTPConfigUtil
{
	@NonNull
	public static SFTPConfig extractSFTPConfig(@NonNull final JsonExternalSystemRequest request)
	{
		final Map<String, String> requestParameters = request.getParameters();

		return SFTPConfig.builder()
				.username(requestParameters.get(ExternalSystemConstants.PARAM_SFTP_USERNAME))
				.password(requestParameters.get(ExternalSystemConstants.PARAM_SFTP_PASSWORD))
				.hostName(requestParameters.get(ExternalSystemConstants.PARAM_SFTP_HOST_NAME))
				.port(requestParameters.get(ExternalSystemConstants.PARAM_SFTP_PORT))
				.targetDirectoryProduct(requestParameters.get(ExternalSystemConstants.PARAM_SFTP_PRODUCT_TARGET_DIRECTORY))
				.targetDirectoryBPartner(requestParameters.get(ExternalSystemConstants.PARAM_SFTP_BPARTNER_TARGET_DIRECTORY))
				.processedFilesFolder(requestParameters.get(ExternalSystemConstants.PARAM_PROCESSED_DIRECTORY))
				.erroredFilesFolder(requestParameters.get(ExternalSystemConstants.PARAM_ERRORED_DIRECTORY))
				.pollingFrequency(Duration.ofMillis(Long.parseLong(requestParameters.get(ExternalSystemConstants.PARAM_POLLING_FREQUENCY_MS))))
				.build();
	}
}

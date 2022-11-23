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
import org.apache.camel.CamelContext;

import java.time.Duration;
import java.util.Map;

import static de.metas.camel.externalsystems.sap.SAPConstants.DEFAULT_RENAME_PATTERN;
import static de.metas.camel.externalsystems.sap.SAPConstants.SEEN_FILE_RENAME_PATTERN_PROPERTY_NAME;

@UtilityClass
public class SFTPConfigUtil
{
	@NonNull
	public static SFTPConfig extractSFTPConfig(@NonNull final JsonExternalSystemRequest request, @NonNull final CamelContext context)
	{
		final String seenFileRenamePattern = context.getPropertiesComponent().resolveProperty(SEEN_FILE_RENAME_PATTERN_PROPERTY_NAME)
				.orElse(DEFAULT_RENAME_PATTERN);

		final Map<String, String> requestParameters = request.getParameters();

		return SFTPConfig.builder()
				.username(requestParameters.get(ExternalSystemConstants.PARAM_SFTP_USERNAME))
				.password(requestParameters.get(ExternalSystemConstants.PARAM_SFTP_PASSWORD))
				.hostName(requestParameters.get(ExternalSystemConstants.PARAM_SFTP_HOST_NAME))
				.port(requestParameters.get(ExternalSystemConstants.PARAM_SFTP_PORT))
				.pollingFrequency(Duration.ofMillis(Long.parseLong(requestParameters.get(ExternalSystemConstants.PARAM_POLLING_FREQUENCY_MS))))

				.seenFileRenamePattern(seenFileRenamePattern)
				.processedFilesFolder(requestParameters.get(ExternalSystemConstants.PARAM_PROCESSED_DIRECTORY))
				.erroredFilesFolder(requestParameters.get(ExternalSystemConstants.PARAM_ERRORED_DIRECTORY))

				.targetDirectoryProduct(requestParameters.get(ExternalSystemConstants.PARAM_SFTP_PRODUCT_TARGET_DIRECTORY))
				.fileNamePatternProduct(requestParameters.get(ExternalSystemConstants.PARAM_SFTP_PRODUCT_FILE_NAME_PATTERN))

				.targetDirectoryBPartner(requestParameters.get(ExternalSystemConstants.PARAM_SFTP_BPARTNER_TARGET_DIRECTORY))
				.fileNamePatternBPartner(requestParameters.get(ExternalSystemConstants.PARAM_SFTP_BPARTNER_FILE_NAME_PATTERN))

				.targetDirectoryCreditLimit(requestParameters.get(ExternalSystemConstants.PARAM_SFTP_CREDIT_LIMIT_TARGET_DIRECTORY))
				.fileNamePatternCreditLimit(requestParameters.get(ExternalSystemConstants.PARAM_SFTP_CREDIT_LIMIT_FILENAME_PATTERN))
				.build();
	}
}

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

package de.metas.camel.externalsystems.sap.config;

import de.metas.common.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Optional;

@Value
@Builder
public class SFTPConfig implements BPartnerFileEndpointConfig, ProductFileEndpointConfig, CreditLimitFileEndpointConfig, ConversionRateFileEndpointConfig
{
	@NonNull
	String username;

	@Nullable
	String password;

	@NonNull
	String port;

	@NonNull
	String hostName;

	@NonNull
	String processedFilesFolder;

	@NonNull
	String erroredFilesFolder;

	@NonNull
	String seenFileRenamePattern;

	@NonNull
	Duration pollingFrequency;

	//product specific
	@Nullable
	String targetDirectoryProduct;

	@Nullable
	String fileNamePatternProduct;

	//bpartner specific
	@Nullable
	String targetDirectoryBPartner;

	@Nullable
	String fileNamePatternBPartner;

	//credit limit specific
	@Nullable
	String targetDirectoryCreditLimit;

	@Nullable
	String fileNamePatternCreditLimit;

	//conversion rate specific
	@Nullable
	String targetDirectoryConversionRate;

	@Nullable
	String fileNamePatternConversionRate;

	@Override
	@NonNull
	public String getProductFileEndpoint()
	{
		return getSFTPConnectionString(targetDirectoryProduct, fileNamePatternProduct);
	}

	@Override
	@NonNull
	public String getBPartnerFileEndpoint()
	{
		return getSFTPConnectionString(targetDirectoryBPartner, fileNamePatternBPartner);
	}

	@Override
	@NonNull
	public String getCreditLimitFileEndpoint()
	{
		return getSFTPConnectionString(targetDirectoryCreditLimit, fileNamePatternCreditLimit);
	}

	@Override
	@NonNull
	public String getConversionRateFileEndpoint()
	{
		return getSFTPConnectionString(targetDirectoryConversionRate, fileNamePatternConversionRate);
	}

	@NonNull
	private String getSFTPConnectionString(@Nullable final String targetDir, @Nullable final String includeFilePattern)
	{
		final StringBuilder sftpEndpoint = new StringBuilder("sftp://");
		sftpEndpoint.append(username)
				.append("@")
				.append(hostName)
				.append(":")
				.append(port)
				.append("/")
				.append(Optional.ofNullable(targetDir).filter(Check::isNotBlank).orElse(""))
				.append("?")
				.append("delay=").append(pollingFrequency.toMillis())
				.append("&")
				.append("move=").append(processedFilesFolder).append("/").append(seenFileRenamePattern)
				.append("&")
				.append("moveFailed=").append(erroredFilesFolder).append("/").append(seenFileRenamePattern);

		Optional.ofNullable(password).ifPresent(pass -> sftpEndpoint.append("&").append("password=").append(pass));
		Optional.ofNullable(includeFilePattern).ifPresent(filePattern -> sftpEndpoint.append("&").append("antInclude=").append(filePattern));

		return sftpEndpoint.toString();
	}
}

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
public class LocalFileConfig implements BPartnerFileEndpointConfig, ProductFileEndpointConfig, CreditLimitFileEndpointConfig, ConversionRateFileEndpointConfig
{
	@NonNull
	String rootLocation;

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
		return getLocalFileConnectionString(targetDirectoryProduct, fileNamePatternProduct);
	}

	@Override
	@NonNull
	public String getBPartnerFileEndpoint()
	{
		return getLocalFileConnectionString(targetDirectoryBPartner, fileNamePatternBPartner);
	}

	@Override
	@NonNull
	public String getCreditLimitFileEndpoint()
	{
		return getLocalFileConnectionString(targetDirectoryCreditLimit, fileNamePatternCreditLimit);
	}

	@Override
	@NonNull
	public String getConversionRateFileEndpoint()
	{
		return getLocalFileConnectionString(targetDirectoryConversionRate, fileNamePatternConversionRate);
	}

	@NonNull
	private String getLocalFileConnectionString(@Nullable final String targetDir, @Nullable final String includeFilePattern)
	{
		final StringBuilder fileEndpoint = new StringBuilder("file://");
		fileEndpoint.append(rootLocation)
				.append("/")
				.append(Optional.ofNullable(targetDir).filter(Check::isNotBlank).orElse(""))
				.append("?")
				.append("delay=").append(pollingFrequency.toMillis())
				.append("&")
				.append("move=").append(processedFilesFolder).append("/").append(seenFileRenamePattern)
				.append("&")
				.append("moveFailed=").append(erroredFilesFolder).append("/").append(seenFileRenamePattern);

		Optional.ofNullable(includeFilePattern).ifPresent(filePattern -> fileEndpoint.append("&").append("antInclude=").append(filePattern));

		return fileEndpoint.toString();
	}
}

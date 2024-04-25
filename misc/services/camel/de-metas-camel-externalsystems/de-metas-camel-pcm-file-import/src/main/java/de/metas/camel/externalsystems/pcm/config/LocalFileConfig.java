package de.metas.camel.externalsystems.pcm.config;

import de.metas.common.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Duration;

@Value
@Builder
public class LocalFileConfig implements BPartnerFileEndpointConfig, ProductFileEndpointConfig, WarehouseFileEndpointConfig, PurchaseOrderFileEndpointConfig
{
	@NonNull
	String rootLocation;

	@NonNull
	String processedFilesFolder;

	@NonNull
	String errorFilesFolder;

	@NonNull
	String seenFileRenamePattern;

	@NonNull
	Duration pollingFrequency;

	@Nullable
	String fileNamePatternProduct;
	@Nullable
	String fileNamePatternBPartner;
	@Nullable
	String fileNamePatternWarehouse;
	@Nullable
	String fileNamePatternPurchaseOrder;

	@Override
	@NonNull
	public String getProductFileEndpoint()
	{
		return getLocalFileConnectionString(fileNamePatternProduct);
	}

	@Override
	@NonNull
	public String getBPartnerFileEndpoint()
	{
		return getLocalFileConnectionString(fileNamePatternBPartner);
	}

	@Override
	@NonNull
	public String getWarehouseFileEndpoint()
	{
		return getLocalFileConnectionString(fileNamePatternWarehouse);
	}

	@Override
	@NonNull
	public String getPurchaseOrderFileEndpoint()
	{
		return getLocalFileConnectionString(fileNamePatternPurchaseOrder);
	}

	@NonNull
	private String getLocalFileConnectionString(@Nullable final String includeFilePattern)
	{
		final StringBuilder fileEndpoint = new StringBuilder("file://");
		fileEndpoint.append(rootLocation)
				.append("?")
				.append("charset=utf-8")
				.append("&")
				.append("delay=").append(pollingFrequency.toMillis())
				.append("&")
				.append("move=").append(processedFilesFolder).append("/").append(seenFileRenamePattern)
				.append("&")
				.append("moveFailed=").append(errorFilesFolder).append("/").append(seenFileRenamePattern);

		if(Check.isNotBlank(includeFilePattern))
		{
			fileEndpoint.append("&").append("antInclude=").append(includeFilePattern);
		}

		return fileEndpoint.toString();
	}
}

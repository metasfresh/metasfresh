package de.metas.frontend_testing.masterdata.warehouse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonWarehouseResponse
{
	int warehouseId;
	String warehouseCode;
	String warehouseName;
	int locatorId;
	String locatorCode;
	String locatorQRCode;

	@Nullable Map<String, JsonWarehouseResponse.Locator> locators;

	//
	//
	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	public static class Locator
	{
		int id;
		@NonNull String code;
		@JsonProperty("isDefault") boolean isDefault;
		@NonNull String x;
		@NonNull String y;
		@NonNull String z;
		@NonNull String x1;
	}

}

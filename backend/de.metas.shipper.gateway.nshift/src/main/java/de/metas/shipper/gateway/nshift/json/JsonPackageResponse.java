package de.metas.shipper.gateway.nshift.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonPackageResponse
{

	@JsonProperty("PkgNo")
	String pkgNo;
}

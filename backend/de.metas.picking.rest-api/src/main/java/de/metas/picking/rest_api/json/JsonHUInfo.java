package de.metas.picking.rest_api.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.common.handlingunits.JsonHUQRCode;
import de.metas.common.handlingunits.JsonHUType;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class JsonHUInfo
{
	@Nullable String id;
	@NonNull JsonHUType unitType;
	@Nullable @JsonInclude(JsonInclude.Include.NON_NULL) Integer qtyTUs;
	@Nullable JsonHUQRCode huQRCode;

	// gh#29069: product info for overdelivery detection
	@Nullable @JsonInclude(JsonInclude.Include.NON_NULL) String productNo;
	@Nullable @JsonInclude(JsonInclude.Include.NON_NULL) BigDecimal productQty;
	@Nullable @JsonInclude(JsonInclude.Include.NON_NULL) String productUom;
}

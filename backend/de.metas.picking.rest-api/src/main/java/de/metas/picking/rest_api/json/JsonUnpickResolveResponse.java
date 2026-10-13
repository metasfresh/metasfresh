package de.metas.picking.rest_api.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@Jacksonized
public class JsonUnpickResolveResponse
{
	/** Numeric product ID (repo ID), as a String for consistency with other picking JSON DTOs */
	@NonNull String productId;
	@NonNull String productName;

	/** Total qty currently packed for this product across all steps of the job */
	@Nullable BigDecimal packedQty;
	/** UOM symbol for packedQty (e.g. "Stk", "kg") */
	@Nullable String packedQtyUom;

	/** true when packedQty > 0 — i.e. there is something to unpick */
	boolean unpickable;
}

package de.metas.handlingunits.qrcodes.model;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * @implNote See {@link de.metas.handlingunits.qrcodes.model.json.JsonConverter} for tools to convert from/to JSON, {@link de.metas.global_qrcodes.GlobalQRCode} etc.
 */
@Value
@Builder
@Jacksonized
public class HUQRCode
{
	@NonNull HUQRCodeUnitType huUnitType;

	@NonNull HUQRCodeUniqueId id;

	@NonNull HUQRCodeProductInfo product;
	@NonNull ImmutableList<HUQRCodeAttribute> attributes;
}

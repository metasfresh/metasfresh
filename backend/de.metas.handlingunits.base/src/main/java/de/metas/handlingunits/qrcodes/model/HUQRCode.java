package de.metas.handlingunits.qrcodes.model;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

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

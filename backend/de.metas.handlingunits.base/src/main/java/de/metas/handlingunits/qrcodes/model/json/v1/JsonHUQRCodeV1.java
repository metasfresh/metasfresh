package de.metas.handlingunits.qrcodes.model.json.v1;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUniqueId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonHUQRCodeV1
{
	@NonNull HUQRCodeUniqueId id;

	@NonNull JsonHUQRCodePackingInfoV1 packingInfo;
	@Nullable JsonHUQRCodeProductInfoV1 product;
	@NonNull ImmutableList<JsonHUQRCodeAttributeV1> attributes;
}

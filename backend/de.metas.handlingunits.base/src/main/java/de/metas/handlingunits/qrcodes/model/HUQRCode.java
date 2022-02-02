package de.metas.handlingunits.qrcodes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.qrcodes.model.json.HUQRCodeJsonConverter;
import de.metas.handlingunits.qrcodes.model.json.JsonRenderedHUQRCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * @implNote See {@link HUQRCodeJsonConverter} for tools to convert from/to JSON, {@link de.metas.global_qrcodes.GlobalQRCode} etc.
 */
@Value
@Builder
@Jacksonized // NOTE: we are making it json friendly mainly for snapshot testing
public class HUQRCode
{
	@NonNull HUQRCodeUniqueId id;

	@NonNull HUQRCodePackingInfo packingInfo;
	@NonNull HUQRCodeProductInfo product;
	@NonNull ImmutableList<HUQRCodeAttribute> attributes;

	public static boolean equals(@Nullable final HUQRCode o1, @Nullable final HUQRCode o2)
	{
		return Objects.equals(o1, o2);
	}

	@JsonIgnore
	public JsonRenderedHUQRCode toRenderedJson()
	{
		return HUQRCodeJsonConverter.toRenderedJson(this);
	}
}

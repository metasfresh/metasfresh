package de.metas.gplr.model;

import de.metas.util.StringUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.annotation.Nullable;

@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GPLRShipperRenderedString
{
	@NonNull String renderedString;

	public static GPLRShipperRenderedString ofShipperName(@NonNull final String shipperName)
	{
		return new GPLRShipperRenderedString(shipperName);
	}

	@Nullable
	public static GPLRShipperRenderedString ofNullableRenderedString(@Nullable String renderedString)
	{
		return StringUtils.trimBlankToOptional(renderedString).map(GPLRShipperRenderedString::new).orElse(null);
	}

	@Override
	public String toString() {return toRenderedString();}

	public String toRenderedString() {return renderedString;}
}

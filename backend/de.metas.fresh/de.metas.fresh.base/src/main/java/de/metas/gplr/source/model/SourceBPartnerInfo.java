package de.metas.gplr.source.model;

import de.metas.location.CountryCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import javax.annotation.Nullable;

@Value
@Builder
public class SourceBPartnerInfo
{
	@NonNull String code;
	@NonNull String name;
	@Nullable String vatId;
	@With boolean isDropShip;

	@Nullable CountryCode countryCode;
}

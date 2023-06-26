package de.metas.gplr.source;

import de.metas.location.CountryCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class SourceBPartnerInfo
{
	@NonNull String code;
	@NonNull String name;
	@Nullable String vatId;

	@Nullable CountryCode countryCode;
}

package de.metas.deliveryplanning;

import com.google.common.base.Joiner;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class MeansOfTransportation
{
	@NonNull MeansOfTransportationId id;
	@Nullable MeansOfTransportationType type;
	@NonNull String code;
	@NonNull String name;
	@Nullable String IMO_MMSI_Number;
	@Nullable String plateNumber;
	@Nullable String RTC;
	@Nullable String plane;

	public ITranslatableString toDisplayableString()
	{
		return TranslatableStrings.anyLanguage(Joiner.on(" ")
				.skipNulls()
				.join(code, name, IMO_MMSI_Number, plateNumber, RTC, plane));
	}
}

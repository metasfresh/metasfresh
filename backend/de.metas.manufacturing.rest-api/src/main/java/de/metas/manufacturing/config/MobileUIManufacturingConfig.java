package de.metas.manufacturing.config;

import de.metas.util.OptionalBoolean;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Optional;

@Value
@Builder
public class MobileUIManufacturingConfig
{
	@NonNull OptionalBoolean isScanResourceRequired;

	public MobileUIManufacturingConfig fallbackTo(@NonNull final MobileUIManufacturingConfig other)
	{
		final MobileUIManufacturingConfig result = MobileUIManufacturingConfig.builder()
				.isScanResourceRequired(this.isScanResourceRequired.ifUnknown(other.isScanResourceRequired))
				.build();
		if (result.equals(this))
		{
			return this;
		}
		else if (result.equals(other))
		{
			return other;
		}
		else
		{
			return result;
		}
	}

	public static Optional<MobileUIManufacturingConfig> merge(@Nullable final MobileUIManufacturingConfig... configs)
	{
		if (configs == null || configs.length <= 0)
		{
			return Optional.empty();
		}

		MobileUIManufacturingConfig result = null;
		for (final MobileUIManufacturingConfig config : configs)
		{
			if (config == null)
			{
				continue;
			}

			result = result != null ? result.fallbackTo(config) : config;
		}

		return Optional.ofNullable(result);
	}

}
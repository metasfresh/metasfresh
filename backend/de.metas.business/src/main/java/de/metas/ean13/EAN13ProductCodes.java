package de.metas.ean13;

import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPartnerId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.Optional;

@EqualsAndHashCode(doNotUseGetters = true)
@ToString
@Builder
public class EAN13ProductCodes
{
	@NonNull private final String productValue;
	@Nullable private final EAN13ProductCode defaultCode;
	@NonNull @Singular private final ImmutableMap<BPartnerId, EAN13ProductCode> codes;

	public Optional<EAN13ProductCode> getCode(@Nullable final BPartnerId bpartnerId)
	{
		if (bpartnerId != null)
		{
			final EAN13ProductCode code = codes.get(bpartnerId);
			if (code != null)
			{
				return Optional.of(code);
			}
		}

		return Optional.ofNullable(defaultCode);
	}

	public boolean contains(@NonNull final EAN13ProductCode expectedProductNo, @Nullable final BPartnerId bpartnerId)
	{
		final EAN13ProductCode actualProductNo = getCode(bpartnerId).orElse(null);
		return actualProductNo != null && EAN13ProductCode.equals(expectedProductNo, actualProductNo);
	}

	public boolean isValidProductNo(@NonNull final EAN13 ean13, @Nullable final BPartnerId bpartnerId)
	{
		final EAN13Prefix ean13Prefix = ean13.getPrefix();
		final EAN13ProductCode ean13ProductNo = ean13.getProductNo();
		if (ean13Prefix.isVariableWeight()) // 28
		{
			return contains(ean13ProductNo, bpartnerId)
					|| ean13ProductNo.isPrefixOf(productValue);
		}
		else if (ean13Prefix.isInternalUseOrVariableMeasure()) // 29
		{
			return contains(ean13ProductNo, bpartnerId);
		}
		else
		{
			return false;
		}

	}

}

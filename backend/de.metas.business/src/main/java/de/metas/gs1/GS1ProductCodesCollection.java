package de.metas.gs1;

import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPartnerId;
import de.metas.gs1.ean13.EAN13;
import de.metas.gs1.ean13.EAN13Prefix;
import de.metas.gs1.ean13.EAN13ProductCode;
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
public class GS1ProductCodesCollection
{
	@NonNull private final String productValue;
	@NonNull private final GS1ProductCodes defaultCodes;
	@NonNull @Singular("codes") private final ImmutableMap<BPartnerId, GS1ProductCodes> codesByBPartnerId;

	public boolean contains(@NonNull final EAN13ProductCode expectedProductNo, @Nullable final BPartnerId bpartnerId)
	{
		final GS1ProductCodes bpartnerCodes = bpartnerId != null ? codesByBPartnerId.get(bpartnerId) : null;
		return (bpartnerCodes != null && bpartnerCodes.isMatching(expectedProductNo))
				|| defaultCodes.isMatching(expectedProductNo);
	}

	private boolean endsWith(@NonNull final EAN13ProductCode expectedProductNo, @Nullable final BPartnerId bpartnerId)
	{
		final GS1ProductCodes bpartnerCodes = bpartnerId != null ? codesByBPartnerId.get(bpartnerId) : null;
		return (bpartnerCodes != null && bpartnerCodes.endsWith(expectedProductNo))
				|| defaultCodes.endsWith(expectedProductNo);
	}

	public boolean isValidProductNo(@NonNull final EAN13 ean13, @Nullable final BPartnerId bpartnerId)
	{
		final EAN13Prefix ean13Prefix = ean13.getPrefix();
		final EAN13ProductCode ean13ProductNo = ean13.getProductNo();

		// 28 - Variable-Weight barcodes
		if (ean13Prefix.isVariableWeight())
		{
			return contains(ean13ProductNo, bpartnerId)
					|| ean13ProductNo.isPrefixOf(productValue);
		}
		// 29 - Internal Use / Variable measure
		else if (ean13Prefix.isInternalUseOrVariableMeasure())
		{
			return contains(ean13ProductNo, bpartnerId);
		}
		// Parse regular product codes for other prefixes
		else
		{
			return endsWith(ean13ProductNo, bpartnerId);
		}
	}

	public Optional<GS1ProductCodes> getEffectiveCodes(@Nullable final BPartnerId bpartnerId)
	{
		final GS1ProductCodes bpartnerCodes = bpartnerId != null ? codesByBPartnerId.get(bpartnerId) : null;
		final GS1ProductCodes effectiveCodes = bpartnerCodes != null
				? bpartnerCodes.fallbackTo(defaultCodes)
				: defaultCodes;

		return Optional.ofNullable(effectiveCodes.isEmpty() ? null : effectiveCodes);
	}
}

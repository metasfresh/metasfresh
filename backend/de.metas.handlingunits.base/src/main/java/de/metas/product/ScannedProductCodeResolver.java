package de.metas.product;

import com.google.common.collect.ImmutableSet;
import de.metas.gs1.GTIN;
import de.metas.handlingunits.edi.EDIProductLookupService;
import de.metas.scannable_code.ScannedCode;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScannedProductCodeResolver
{
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final EDIProductLookupService ediProductLookupService;

	public Optional<ResolvedScannedProductCodes> resolve(@NonNull final ScannedCode scannedCode)
	{
		final GTIN gtin = GTIN.ofScannedCode(scannedCode).orElse(null);
		if (gtin != null)
		{
			final ProductId productId = productBL.getProductIdByGTIN(gtin).orElse(null);
			if (productId != null)
			{
				return Optional.of(ResolvedScannedProductCodes.ofSingleCode(
						ResolvedScannedProductCode.builder()
								.productId(productId)
								.build()
				));
			}
		}

		return ResolvedScannedProductCodes.optionalOfCollection(
				ediProductLookupService.lookupByUPC(scannedCode.getAsString(), true)
						.stream()
						.collect(ImmutableSet.toImmutableSet())
		);
	}

}

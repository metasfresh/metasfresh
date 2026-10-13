package de.metas.handlingunits.shipping;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.product.PackageDimensions;
import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Value
@Builder
public class CreatePackageForHURequest
{
	@NonNull I_M_HU hu;
	@Nullable @With ShipperId shipperId;
	@Nullable @With BigDecimal weightInKg;
	// When set, overrides the HU-derived package dimensions (used when splitting a loose CU into
	// N single-unit M_Packages, where each package is one unit, not the whole HU).
	@Nullable @With PackageDimensions packageDimensions;

	public static CreatePackageForHURequestBuilder builderFrom(@NonNull final I_M_HU hu)
	{
		return builder().hu(hu);
	}

	public static CreatePackageForHURequest ofHU(@NonNull final I_M_HU hu) {return builderFrom(hu).build();}

	public static List<CreatePackageForHURequest> ofHUsList(@NonNull final Collection<I_M_HU> hus)
	{
		return hus.stream()
				.map(CreatePackageForHURequest::ofHU)
				.collect(ImmutableList.toImmutableList());
	}
}

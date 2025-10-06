package de.metas.shipper.gateway.spi;

import com.google.common.collect.ImmutableSet;
import de.metas.shipping.mpackage.PackageId;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Value
@Builder
public
class CreateDraftDeliveryOrderRequest
{
	@NonNull DeliveryOrderKey deliveryOrderKey;
	@NonNull Set<PackageInfo> packageInfos;

	public Set<PackageId> getPackageIds() {return packageInfos.stream().map(PackageInfo::getPackageId).collect(ImmutableSet.toImmutableSet());}

	public BigDecimal getAllPackagesGrossWeightInKg(@NonNull final BigDecimal minWeightKg)
	{
		final BigDecimal weightInKg = packageInfos.stream()
				.map(PackageInfo::getWeightInKg)
				.filter(weight -> weight != null && weight.signum() > 0)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		return weightInKg.max(minWeightKg);
	}

	@NonNull
	public Optional<String> getAllPackagesContentDescription()
	{
		final String description = packageInfos.stream()
				.map(PackageInfo::getDescription)
				.map(StringUtils::trimBlankToNull)
				.filter(Objects::nonNull)
				.collect(Collectors.joining(", "));
		return StringUtils.trimBlankToOptional(description);
	}

	@Value
	@Builder
	public static class PackageInfo
	{
		@NonNull PackageId packageId;
		@Nullable String poReference;
		@Nullable String description;
		@Nullable BigDecimal weightInKg;

		public BigDecimal getWeightInKgOr(BigDecimal minValue) {return weightInKg != null ? weightInKg.max(minValue) : minValue;}
	}
}

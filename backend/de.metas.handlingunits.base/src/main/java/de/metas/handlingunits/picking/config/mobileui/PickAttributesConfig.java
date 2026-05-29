package de.metas.handlingunits.picking.config.mobileui;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.Map;

@Value
public class PickAttributesConfig
{
	@NonNull public static final PickAttributesConfig UNKNOWN = builder().build();
	@NonNull public static final PickAttributesConfig DEFAULT = builder()
			.attributeToRead(PickAttribute.BestBeforeDate, true)
			.attributeToRead(PickAttribute.LotNo, true)
			.build();

	@NonNull ImmutableMap<PickAttribute, Boolean> attributesToRead;
	@NonNull ImmutableSet<PickAttribute> attributesToReadSet;

	@Builder
	private PickAttributesConfig(@NonNull @Singular("attributeToRead") final ImmutableMap<PickAttribute, Boolean> attributesToRead)
	{
		this.attributesToRead = attributesToRead;
		this.attributesToReadSet = attributesToRead.entrySet()
				.stream()
				.filter(Map.Entry::getValue)
				.map(Map.Entry::getKey)
				.collect(ImmutableSet.toImmutableSet());
	}

	public PickAttributesConfig fallbackTo(final @NonNull PickAttributesConfig fallback)
	{
		return builder()
				.attributesToRead(CollectionUtils.mergeMaps(fallback.attributesToRead, this.attributesToRead))
				.build();
	}
	
	public boolean isReadAttribute(@NonNull final PickAttribute pickAttribute)
	{
		return attributesToReadSet.contains(pickAttribute);
	}
}

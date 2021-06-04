package de.metas.order.compensationGroup;

import com.google.common.collect.ImmutableSet;
import de.metas.product.ProductId;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import java.util.Collection;

@EqualsAndHashCode
@ToString
public class FlatrateConditionsExcludedProducts
{
	@Value(staticConstructor = "of")
	public static class GroupTemplateIdAndProductId
	{
		@NonNull
		GroupTemplateId groupTemplateId;

		@NonNull
		ProductId productId;
	}

	private final ImmutableSet<GroupTemplateIdAndProductId> exclusions;

	public FlatrateConditionsExcludedProducts(@NonNull final Collection<GroupTemplateIdAndProductId> exclusions)
	{
		this.exclusions = ImmutableSet.copyOf(exclusions);
	}

	public boolean isExcluded(@NonNull final GroupTemplateId groupTemplateId, @NonNull final ProductId productId)
	{
		return exclusions.contains(GroupTemplateIdAndProductId.of(groupTemplateId, productId));
	}
}

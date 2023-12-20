package de.metas.ad_reference;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@EqualsAndHashCode
@ToString
public class ADRefList
{
	@Getter @NonNull private final ReferenceId referenceId;
	@Getter @NonNull final String name;
	@Getter boolean isOrderByValue;
	private final ImmutableMap<String, ADRefListItem> itemsByValue;
	private final ImmutableList<ADRefListItem> items; // required for toBuilder()

	@Builder(toBuilder = true)
	private ADRefList(
			final @NonNull ReferenceId referenceId,
			final @NonNull String name,
			final boolean isOrderByValue,
			final @NonNull List<ADRefListItem> items)
	{

		this.referenceId = referenceId;
		this.name = name;
		this.isOrderByValue = isOrderByValue;
		this.items = ImmutableList.copyOf(items);
		this.itemsByValue = Maps.uniqueIndex(items, ADRefListItem::getValue);
	}

	public Set<String> getValues() {return itemsByValue.keySet();}

	public Collection<ADRefListItem> getItems() {return itemsByValue.values();}

	public Optional<ADRefListItem> getItemByValue(@Nullable final String value)
	{
		return Optional.ofNullable(itemsByValue.get(value));
	}

	public boolean containsValue(final String value)
	{
		return itemsByValue.get(value) != null;
	}
}

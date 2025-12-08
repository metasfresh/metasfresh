package de.metas.attributes_included_tab.descriptor;

import com.google.common.collect.ImmutableList;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.adempiere.mm.attributes.AttributeSetId;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
class AttributesIncludedTabUserConfigList
{
	private static final AttributesIncludedTabUserConfigList EMPTY = new AttributesIncludedTabUserConfigList(ImmutableList.of());
	private final ImmutableList<AttributesIncludedTabUserConfig> list;

	private AttributesIncludedTabUserConfigList(final List<AttributesIncludedTabUserConfig> list)
	{
		this.list = ImmutableList.copyOf(list);
	}

	public static AttributesIncludedTabUserConfigList of(final List<AttributesIncludedTabUserConfig> list)
	{
		return list.isEmpty() ? EMPTY : new AttributesIncludedTabUserConfigList(list);
	}

	public Set<AttributeSetId> getAttributeSetIds()
	{
		return list.stream().map(AttributesIncludedTabUserConfig::getAttributeSetId).collect(Collectors.toSet());
	}

	public Stream<AttributesIncludedTabUserConfig> stream() {return list.stream();}
}

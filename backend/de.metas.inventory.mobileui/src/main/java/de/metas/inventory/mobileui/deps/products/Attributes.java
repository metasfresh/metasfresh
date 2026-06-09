package de.metas.inventory.mobileui.deps.products;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode
@ToString
public class Attributes
{
	private static final Attributes EMPTY = new Attributes(ImmutableList.of());

	@NonNull private final ImmutableList<Attribute> list;
	@NonNull private final ImmutableMap<AttributeCode, Attribute> byCode;

	private Attributes(@NonNull final List<Attribute> list)
	{
		this.list = ImmutableList.copyOf(list);
		this.byCode = Maps.uniqueIndex(list, Attribute::getAttributeCode);
	}

	public static Attributes of(final ImmutableAttributeSet attributeSet)
	{
		if (attributeSet.isEmpty()) {return EMPTY;}

		return ofList(
				attributeSet.getAttributeCodes()
						.stream()
						.map(attributeCode -> toAttribute(attributeSet, attributeCode))
						.collect(ImmutableList.toImmutableList())
		);
	}

	private static Attribute toAttribute(@NonNull ImmutableAttributeSet attributeSet, @NonNull AttributeCode attributeCode)
	{
		return Attribute.builder()
				.attributeCode(attributeCode)
				.displayName(TranslatableStrings.anyLanguage(attributeSet.getAttributeNameByCode(attributeCode)))
				.valueType(attributeSet.getAttributeValueType(attributeCode))
				.value(attributeSet.getValue(attributeCode))
				.build();

	}

	public static Attributes ofList(final List<Attribute> list)
	{
		return list.isEmpty() ? EMPTY : new Attributes(list);
	}

	public boolean hasAttribute(final @NonNull AttributeCode attributeCode)
	{
		return byCode.containsKey(attributeCode);
	}

	@NonNull
	public Attribute getAttribute(@NonNull final AttributeCode attributeCode)
	{
		final Attribute attribute = byCode.get(attributeCode);
		if (attribute == null)
		{
			throw new AdempiereException("No attribute `" + attributeCode + "` found in " + byCode.keySet());
		}
		return attribute;
	}

	public List<Attribute> getAttributes() {return list;}

	public Attributes retainOnly(@NonNull final Set<AttributeCode> attributeCodes, @NonNull final Attributes fallback)
	{
		Check.assumeNotEmpty(attributeCodes, "attributeCodes is not empty");

		final ArrayList<Attribute> newList = new ArrayList<>();
		for (AttributeCode attributeCode : attributeCodes)
		{
			if (hasAttribute(attributeCode))
			{
				newList.add(getAttribute(attributeCode));
			}
			else if (fallback.hasAttribute(attributeCode))
			{
				newList.add(fallback.getAttribute(attributeCode));
			}
		}

		return ofList(newList);
	}
}

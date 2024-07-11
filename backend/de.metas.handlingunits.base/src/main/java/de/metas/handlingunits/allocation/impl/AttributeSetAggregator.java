package de.metas.handlingunits.allocation.impl;

import de.metas.handlingunits.attribute.weightable.Weightables;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.compiere.model.I_M_Attribute;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;

class AttributeSetAggregator
{
	private final HashMap<AttributeCode, AttributeAggregator> attributeAggregators = new HashMap<>();

	void collect(@NonNull final IAttributeSet from)
	{
		from.getAttributes()
				.stream()
				.filter(AttributeSetAggregator::isAggregable)
				.forEach(attribute -> getAttributeAggregator(attribute).collect(from));
	}

	@NonNull
	private AttributeAggregator getAttributeAggregator(@NonNull final I_M_Attribute attribute)
	{
		return attributeAggregators.computeIfAbsent(
				AttributeCode.ofString(attribute.getValue()),
				attributeCode -> new AttributeAggregator(attributeCode, AttributeValueType.ofCode(attribute.getAttributeValueType()))
		);
	}

	void updateAggregatedValuesTo(@NonNull final IAttributeSet to)
	{
		attributeAggregators.values()
				.forEach(attributeAggregator -> attributeAggregator.updateAggregatedValueTo(to));
	}

	private static boolean isAggregable(@NonNull final I_M_Attribute attribute)
	{
		return !Weightables.isWeightableAttribute(AttributeCode.ofString(attribute.getValue()));
	}

	//
	//
	//

	@RequiredArgsConstructor
	private static class AttributeAggregator
	{
		private final AttributeCode attributeCode;
		private final AttributeValueType attributeValueType;
		private final HashSet<Object> values = new HashSet<>();

		void collect(@NonNull final IAttributeSet from)
		{
			final Object value = attributeValueType.map(new AttributeValueType.CaseMapper<Object>()
			{
				@Nullable
				@Override
				public Object string()
				{
					return from.getValueAsString(attributeCode);
				}

				@Override
				public Object number()
				{
					return from.getValueAsBigDecimal(attributeCode);
				}

				@Nullable
				@Override
				public Object date()
				{
					return from.getValueAsDate(attributeCode);
				}

				@Nullable
				@Override
				public Object list()
				{
					return from.getValueAsString(attributeCode);
				}
			});

			values.add(value);
		}

		void updateAggregatedValueTo(@NonNull final IAttributeSet to)
		{
			if (values.size() == 1 && to.hasAttribute(attributeCode))
			{
				to.setValue(attributeCode, values.iterator().next());
			}
		}
	}
}

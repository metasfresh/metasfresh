package de.metas.attributes_included_tab.data;

import com.google.common.collect.ImmutableMap;
import de.metas.util.NumberUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.mm.attributes.AttributeId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

@EqualsAndHashCode
@ToString
public class AttributesIncludedTabData
{
	@NonNull @Getter private final AttributesIncludedTabDataKey key;
	@NonNull private final ImmutableMap<AttributeId, AttributesIncludedTabDataField> fields;

	@Builder
	private AttributesIncludedTabData(
			@NonNull final AttributesIncludedTabDataKey key,
			@Nullable final Map<AttributeId, AttributesIncludedTabDataField> fields)
	{
		this.key = key;
		this.fields = fields != null ? ImmutableMap.copyOf(fields) : ImmutableMap.of();
	}

	public Collection<AttributesIncludedTabDataField> getFields() {return fields.values();}

	public String getValueAsString(@NonNull final AttributeId attributeId)
	{
		final AttributesIncludedTabDataField field = fields.get(attributeId);
		return field != null ? field.getValueString() : null;
	}

	public BigDecimal getValueAsBigDecimal(final AttributeId attributeId)
	{
		final AttributesIncludedTabDataField field = fields.get(attributeId);
		return field != null ? field.getValueNumber() : null;
	}

	public Integer getValueAsInteger(final AttributeId attributeId)
	{
		return NumberUtils.asInteger(getValueAsBigDecimal(attributeId), null);
	}

	public LocalDate getValueAsLocalDate(final AttributeId attributeId)
	{
		final AttributesIncludedTabDataField field = fields.get(attributeId);
		return field != null ? field.getValueDate() : null;
	}
}

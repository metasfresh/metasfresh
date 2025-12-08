package de.metas.attributes_included_tab.descriptor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.i18n.ITranslatableString;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.service.ClientId;

import java.util.List;

@Value
public class AttributesIncludedTabDescriptor
{
	@NonNull AttributesIncludedTabId id;
	@NonNull AttributeSetId attributeSetId;
	@NonNull ClientId clientId;
	@NonNull AdTableId parentTableId;
	@NonNull ITranslatableString caption;
	int seqNo;

	@NonNull ImmutableList<AttributesIncludedTabFieldDescriptor> fieldsInOrder;
	@NonNull @Getter(AccessLevel.NONE) ImmutableMap<AttributeId, AttributesIncludedTabFieldDescriptor> fieldsByAttributeId;

	@Builder
	private AttributesIncludedTabDescriptor(
			@NonNull final AttributesIncludedTabId id,
			@NonNull final AttributeSetId attributeSetId,
			@NonNull final ClientId clientId,
			@NonNull final AdTableId parentTableId,
			@NonNull final ITranslatableString caption,
			final int seqNo,
			@NonNull final List<AttributesIncludedTabFieldDescriptor> fields)
	{
		this.id = id;
		this.attributeSetId = attributeSetId;
		this.clientId = clientId;
		this.parentTableId = parentTableId;
		this.caption = caption;
		this.seqNo = seqNo;
		this.fieldsInOrder = ImmutableList.copyOf(fields);
		this.fieldsByAttributeId = Maps.uniqueIndex(fields, AttributesIncludedTabFieldDescriptor::getAttributeId);
	}

	public ImmutableSet<AttributeId> getAttributeIds()
	{
		return fieldsByAttributeId.keySet();
	}

	public AttributesIncludedTabFieldDescriptor getFieldByAttributeId(@NonNull final AttributeId attributeId)
	{
		final AttributesIncludedTabFieldDescriptor field = fieldsByAttributeId.get(attributeId);
		if (field == null)
		{
			throw new AdempiereException("Attribute " + attributeId + " not found in " + this);
		}
		return field;
	}

}

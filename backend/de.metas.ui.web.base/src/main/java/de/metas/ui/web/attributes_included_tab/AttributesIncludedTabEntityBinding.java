package de.metas.ui.web.attributes_included_tab;

import com.google.common.collect.ImmutableSet;
import de.metas.attributes_included_tab.descriptor.AttributesIncludedTabDescriptor;
import de.metas.attributes_included_tab.descriptor.AttributesIncludedTabFieldDescriptor;
import de.metas.attributes_included_tab.descriptor.AttributesIncludedTabId;
import de.metas.ui.web.window.descriptor.DocumentEntityDataBindingDescriptor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.mm.attributes.AttributeId;

@Builder
@EqualsAndHashCode
@ToString
class AttributesIncludedTabEntityBinding implements DocumentEntityDataBindingDescriptor
{
	@NonNull @Getter private final AttributesIncludedTabDocumentsRepository documentsRepository;
	@NonNull private final AttributesIncludedTabDescriptor includedTab;

	public AttributesIncludedTabId getAttributesIncludedTabId() {return includedTab.getId();}

	public ImmutableSet<AttributeId> getAttributeIds() {return includedTab.getAttributeIds();}

	public String getFieldNameByAttributeId(@NonNull final AttributeId attributeId)
	{
		final AttributesIncludedTabFieldDescriptor field = includedTab.getFieldByAttributeId(attributeId);
		return AttributesIncludedTabFieldBinding.computeFieldName(field);
	}
}

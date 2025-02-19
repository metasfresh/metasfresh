package de.metas.attributes_included_tab;

import de.metas.attributes_included_tab.data.AttributesIncludedTabData;
import de.metas.attributes_included_tab.data.AttributesIncludedTabDataField;
import de.metas.attributes_included_tab.data.AttributesIncludedTabDataKey;
import de.metas.attributes_included_tab.data.AttributesIncludedTabDataRepository;
import de.metas.attributes_included_tab.descriptor.AttributesIncludedTabDescriptor;
import de.metas.attributes_included_tab.descriptor.AttributesIncludedTabDescriptorService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.spi.IAttributeValuesProvider;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;

@Service
@RequiredArgsConstructor
public class AttributesIncludedTabService
{
	@NonNull private final AttributesIncludedTabDescriptorService descriptorService;
	@NonNull private final AttributesIncludedTabDataRepository dataRepository;

	public List<AttributesIncludedTabDescriptor> getDescriptorsBy(final AdTableId adTableId)
	{
		return descriptorService.listBy(adTableId);
	}

	public AttributesIncludedTabData getData(@NonNull final AttributesIncludedTabDataKey key)
	{
		return dataRepository.getByKey(key);
	}

	public IAttributeValuesProvider createAttributeValuesProvider(final @NonNull AttributeId attributeId)
	{
		return descriptorService.createAttributeValuesProvider(attributeId);
	}

	public AttributesIncludedTabData updateByKey(
			@NonNull final AttributesIncludedTabDataKey key,
			@NonNull final Collection<AttributeId> attributeIds,
			@NonNull final BiFunction<AttributeId, AttributesIncludedTabDataField, AttributesIncludedTabDataField> mapper)
	{
		return dataRepository.updateByKey(key, attributeIds, mapper);
	}

}

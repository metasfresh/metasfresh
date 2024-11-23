package de.metas.attributes_included_tab.descriptor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.i18n.IModelTranslationMap;
import de.metas.uom.UomId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetAttributeIdsList;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.mm.attributes.MultiAttributeSetAttributeIdsList;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.mm.attributes.spi.IAttributeValuesProvider;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeSet_IncludedTab;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttributesIncludedTabDescriptorService
{
	@NonNull private final AttributesIncludedTabUserConfigRepository userConfigRepository;
	@NonNull private final IAttributesBL attributesBL = Services.get(IAttributesBL.class);
	@NonNull private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

	private final CCache<Integer, AttributesIncludedTabMap> cache = CCache.<Integer, AttributesIncludedTabMap>builder()
			.tableName(I_M_AttributeSet_IncludedTab.Table_Name)
			.additionalTableNameToResetFor(I_M_AttributeSet.Table_Name)
			.additionalTableNameToResetFor(I_M_Attribute.Table_Name)
			.build();

	public List<AttributesIncludedTabDescriptor> listBy(@NonNull AdTableId adTableId)
	{
		return getMap().listBy(adTableId);
	}

	private AttributesIncludedTabMap getMap()
	{
		return cache.getOrLoad(0, this::retrieveMap);
	}

	private AttributesIncludedTabMap retrieveMap()
	{
		final AttributesIncludedTabUserConfigList userConfigs = userConfigRepository.list();
		final MultiAttributeSetAttributeIdsList attributeSets = attributeDAO.getAttributeIdsByAttributeSetIds(userConfigs.getAttributeSetIds());
		final ImmutableMap<AttributeId, I_M_Attribute> attributesById = Maps.uniqueIndex(
				attributeDAO.getAttributesByIds(attributeSets.getAttributeIds()),
				attribute -> AttributeId.ofRepoId(attribute.getM_Attribute_ID())
		);

		return userConfigs.stream()
				.map(userConfig -> toAttributesIncludedTab(userConfig, attributeSets, attributesById))
				.collect(GuavaCollectors.collectUsingListAccumulator(AttributesIncludedTabMap::of));
	}

	private static AttributesIncludedTabDescriptor toAttributesIncludedTab(
			@NonNull final AttributesIncludedTabUserConfig userConfig,
			@NonNull final MultiAttributeSetAttributeIdsList attributeSets,
			@NonNull final ImmutableMap<AttributeId, I_M_Attribute> attributesById)
	{
		final AttributeSetAttributeIdsList attributeSet = attributeSets.getByAttributeSetId(userConfig.getAttributeSetId());

		return AttributesIncludedTabDescriptor.builder()
				.id(userConfig.getId())
				.attributeSetId(userConfig.getAttributeSetId())
				.clientId(userConfig.getClientId())
				.parentTableId(userConfig.getParentTableId())
				.caption(userConfig.getCaption())
				.seqNo(userConfig.getSeqNo())
				.fields(attributeSet.getAttributeIdsInOrder()
						.stream()
						.map(attributeId -> toAttributesIncludedTabField(attributeId, attributesById))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private static AttributesIncludedTabFieldDescriptor toAttributesIncludedTabField(
			@NonNull final AttributeId attributeId,
			@NonNull final ImmutableMap<AttributeId, I_M_Attribute> attributesById)
	{
		final I_M_Attribute attribute = attributesById.get(attributeId);
		if (attribute == null)
		{
			throw new AdempiereException("No Attribute with ID " + attributeId + " found");
		}

		final IModelTranslationMap trls = InterfaceWrapperHelper.getModelTranslationMap(attribute);

		return AttributesIncludedTabFieldDescriptor.builder()
				.caption(trls.getColumnTrl(I_M_Attribute.COLUMNNAME_Name, attribute.getName()))
				.description(trls.getColumnTrl(I_M_Attribute.COLUMNNAME_Description, attribute.getDescription()))
				.attributeId(attributeId)
				.attributeCode(AttributeCode.ofString(attribute.getValue()))
				.attributeValueType(AttributeValueType.ofCode(attribute.getAttributeValueType()))
				.readOnlyValues(attribute.isReadOnlyValues())
				.mandatory(attribute.isMandatory())
				.uomId(UomId.ofRepoIdOrNull(attribute.getC_UOM_ID()))
				.build();
	}

	@Nullable
	public IAttributeValuesProvider createAttributeValuesProvider(final AttributeId attributeId)
	{
		return attributesBL.createAttributeValuesProvider(attributeId);
	}

	//
	//
	//
	//
	//

	private static class AttributesIncludedTabMap
	{
		private static final AttributesIncludedTabMap EMPTY = new AttributesIncludedTabMap(ImmutableList.of());

		private final ImmutableListMultimap<AdTableId, AttributesIncludedTabDescriptor> byTableId;

		private AttributesIncludedTabMap(final List<AttributesIncludedTabDescriptor> list)
		{
			this.byTableId = list.stream()
					.sorted(Comparator.comparing(AttributesIncludedTabDescriptor::getParentTableId)
							.thenComparing(AttributesIncludedTabDescriptor::getSeqNo))
					.collect(ImmutableListMultimap.toImmutableListMultimap(AttributesIncludedTabDescriptor::getParentTableId, tab -> tab));
		}

		public static AttributesIncludedTabMap of(final List<AttributesIncludedTabDescriptor> list)
		{
			return list.isEmpty() ? EMPTY : new AttributesIncludedTabMap(list);
		}

		public List<AttributesIncludedTabDescriptor> listBy(final @NonNull AdTableId adTableId)
		{
			return byTableId.get(adTableId);
		}
	}
}

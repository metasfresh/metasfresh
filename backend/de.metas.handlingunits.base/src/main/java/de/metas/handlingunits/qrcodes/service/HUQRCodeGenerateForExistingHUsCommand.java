package de.metas.handlingunits.qrcodes.service;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.generichumodel.HUType;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.QRCodeConfiguration;
import de.metas.handlingunits.qrcodes.model.QRCodeConfigurationId;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.compiere.model.I_M_Attribute;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class HUQRCodeGenerateForExistingHUsCommand
{
	@NonNull private final IHandlingUnitsBL handlingUnitsBL;
	@NonNull private final IProductBL productBL;
	@NonNull private final IAttributeDAO attributeDAO;
	@NonNull private final HUQRCodesRepository huQRCodesRepository;
	@NonNull private final QRCodeConfigurationService qrCodeConfigurationService;
	@NonNull private final IAttributeStorageFactory attributeStorageFactory;

	@NonNull private final ImmutableSet<HuId> huIds;

	private HUQRCodeGenerateCommand.Cache cache = null;

	//
	// Value Extractors
	@FunctionalInterface
	private interface AttributeExtractor
	{
		HUQRCodeGenerateRequest.Attribute extractAttribute(final ImmutableAttributeSet attributes, final AttributeCode attributeCode);
	}

	private static final AttributeExtractor LOCAL_DATE_EXTRACTOR = (attributes, attributeCode) -> HUQRCodeGenerateRequest.Attribute.builder()
			.code(attributeCode)
			.valueDate(attributes.getValueAsLocalDate(attributeCode))
			.build();
	private static final AttributeExtractor STRING_EXTRACTOR = (attributes, attributeCode) -> HUQRCodeGenerateRequest.Attribute.builder()
			.code(attributeCode)
			.valueString(attributes.getValueAsString(attributeCode))
			.build();
	private static final AttributeExtractor BIG_DECIMAL_EXTRACTOR = (attributes, attributeCode) -> HUQRCodeGenerateRequest.Attribute.builder()
			.code(attributeCode)
			.valueNumber(attributes.getValueAsBigDecimal(attributeCode))
			.build();

	@Builder
	private HUQRCodeGenerateForExistingHUsCommand(
			final @NonNull IHandlingUnitsBL handlingUnitsBL,
			final @NonNull IProductBL productBL,
			final @NonNull IAttributeDAO attributeDAO, final @NonNull HUQRCodesRepository huQRCodesRepository,
            final @NonNull QRCodeConfigurationService qrCodeConfigurationService,
            final @NonNull IAttributeStorageFactoryService attributeStorageFactoryService,
			final @NonNull @Singular ImmutableSet<HuId> huIds)
	{
		this.handlingUnitsBL = handlingUnitsBL;
		this.productBL = productBL;
		this.attributeDAO = attributeDAO;
		this.huQRCodesRepository = huQRCodesRepository;
		this.qrCodeConfigurationService = qrCodeConfigurationService;
		this.attributeStorageFactory = attributeStorageFactoryService.createHUAttributeStorageFactory();
		this.huIds = huIds;
	}

	public HUQRCodeGenerateForExistingHUsResult execute()
	{
		if (qrCodeConfigurationService.isAtLeastOneActiveConfig())
		{
			return executeWithGrouping();
		}
		else
		{
			return executeWithoutGrouping();
		}
	}

	private HUQRCodeGenerateForExistingHUsResult executeWithoutGrouping()
	{
		final ImmutableSetMultimap<HuId, HUQRCode> existingQRCodesByHuId = huQRCodesRepository.getQRCodeByHuIds(huIds);
		final List<I_M_HU> hus = handlingUnitsBL.getByIds(huIds);

		final HashMultimap<HuId, HUQRCode> result = HashMultimap.create();
		for (final I_M_HU hu : hus)
		{
			final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());
			final ImmutableSet<HUQRCode> existingQRCodes = existingQRCodesByHuId.get(huId);
			result.putAll(huId, existingQRCodes);

			final List<HUQRCode> newQRCodes = generateRemainingQRCodes(hu, existingQRCodes.size(), null);
			result.putAll(huId, newQRCodes);

			newQRCodes.forEach(newQRCode -> huQRCodesRepository.createNew(newQRCode, huId));
		}

		return new HUQRCodeGenerateForExistingHUsResult(result);
	}

	private List<HUQRCode> generateRemainingQRCodes(
			@NonNull final I_M_HU hu,
			final int alreadyGeneratedQRCodesCount,
			@Nullable final QRCodeConfiguration qrCodeConfiguration)
	{
		final I_M_HU_PI_Version piVersion;
		final int requiredQRCodesCount;
		if (handlingUnitsBL.isAggregateHU(hu))
		{
			piVersion = handlingUnitsBL.getEffectivePIVersion(hu);

			if (qrCodeConfiguration != null
					&& qrCodeConfiguration.isOneQRCodeForAggregatedTUsEnabled()
					&& alreadyGeneratedQRCodesCount <= 1)
			{
				requiredQRCodesCount = 1 - alreadyGeneratedQRCodesCount;
			}
			else
			{
				final QtyTU qtyTUs = handlingUnitsBL.getTUsCount(hu);
				requiredQRCodesCount = qtyTUs.toInt() - alreadyGeneratedQRCodesCount;
			}
		}
		else
		{
			piVersion = handlingUnitsBL.getPIVersion(hu);
			requiredQRCodesCount = 1 - alreadyGeneratedQRCodesCount;
		}

		if (requiredQRCodesCount < 1)
		{
			return ImmutableList.of();
		}

		if (cache == null)
		{
			cache = HUQRCodeGenerateCommand.newSharedCache();
		}

		return HUQRCodeGenerateCommand.builder()
				.handlingUnitsBL(handlingUnitsBL)
				.productBL(productBL)
				.attributeDAO(attributeDAO)
				.sharedCache(cache)
				.request(HUQRCodeGenerateRequest.builder()
						.productId(getProductId(hu))
						.huPackingInstructionsId(HuPackingInstructionsId.ofRepoId(piVersion.getM_HU_PI_ID()))
						.attributes(getHUQRCodeAttributes(hu))
						.count(requiredQRCodesCount)
						.build())
				.build()
				.execute();
	}

	@NonNull
	private ProductId getProductId(final I_M_HU hu)
	{
		final ProductId productId = handlingUnitsBL.getStorageFactory().getStorage(hu).getSingleProductIdOrNull();
		if (productId == null)
		{
			throw new AdempiereException("Only single product storages are supported");
		}
		return productId;
	}

	private ImmutableList<HUQRCodeGenerateRequest.Attribute> getHUQRCodeAttributes(final I_M_HU hu)
	{
		ImmutableList.Builder<HUQRCodeGenerateRequest.Attribute> result = ImmutableList.builder();

		final ImmutableAttributeSet attributes = handlingUnitsBL.getImmutableAttributeSet(hu);

		extractHUQRCodeAttribute(attributes, AttributeConstants.ATTR_BestBeforeDate, LOCAL_DATE_EXTRACTOR).ifPresent(result::add);
		extractHUQRCodeAttribute(attributes, AttributeConstants.ATTR_LotNumber, STRING_EXTRACTOR).ifPresent(result::add);
		extractHUQRCodeAttribute(attributes, Weightables.ATTR_WeightNet, BIG_DECIMAL_EXTRACTOR).ifPresent(result::add);

		return result.build();
	}

	private Optional<HUQRCodeGenerateRequest.Attribute> extractHUQRCodeAttribute(
			@NonNull final ImmutableAttributeSet attributes,
			@NonNull final AttributeCode attributeCode,
			@NonNull final AttributeExtractor attributeExtractor)
	{
		return attributes.hasAttribute(attributeCode)
				? Optional.of(attributeExtractor.extractAttribute(attributes, attributeCode))
				: Optional.empty();
	}

	@NonNull
	private HUQRCodeGenerateForExistingHUsResult executeWithGrouping()
	{
		final ImmutableList<ImmutableList<HUItemToGroup>> groups = groupHUsForSharingQRCodes();
		final ImmutableSet<HuId> huIds = groups.stream()
				.map(HUQRCodeGenerateForExistingHUsCommand::getHuIds)
				.flatMap(Collection::stream)
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableSetMultimap<HuId, HUQRCode> existingQRCodesByHuId = huQRCodesRepository.getQRCodeByHuIds(huIds);

		final HashMultimap<HuId, HUQRCode> result = HashMultimap.create();
		groups.stream()
				.map(group -> generateQRCodeForGroup(group, existingQRCodesByHuId))
				.forEach(result::putAll);

		return new HUQRCodeGenerateForExistingHUsResult(result);
	}

	private HashMultimap<HuId, HUQRCode> generateQRCodeForGroup(
			@NonNull final List<HUItemToGroup> group,
			@NonNull final ImmutableSetMultimap<HuId, HUQRCode> existingQRCodesByHuId)
	{
		final HashMultimap<HuId, HUQRCode> result = HashMultimap.create();
		if (group.size() == 1)
		{
			final HUItemToGroup singleItem = group.get(0);
			final List<HUQRCode> qrCodes = generateQRCodeForItem(singleItem, existingQRCodesByHuId.get(singleItem.getHuId()));
			result.putAll(singleItem.getHuId(), qrCodes);
		}
		else
		{
			final ArrayList<HUItemToGroup> husToGenerateQRFor = new ArrayList<>();
			HUQRCode sharedHUQrCode = null;
			for (final HUItemToGroup item : group)
			{
				final ImmutableSet<HUQRCode> existingQRCodes = existingQRCodesByHuId.get(item.getHuId());
				if (existingQRCodes.size() > 1)
				{
					result.putAll(item.getHuId(), generateQRCodeForItem(item, existingQRCodes));
				}
				else if (existingQRCodes.size() == 1)
				{
					sharedHUQrCode = existingQRCodes.iterator().next();
					result.putAll(item.getHuId(), existingQRCodes);
				}
				else
				{
					husToGenerateQRFor.add(item);
				}
			}

			if (!husToGenerateQRFor.isEmpty())
			{
				final HUQRCode groupHUQrCode = sharedHUQrCode != null
						? sharedHUQrCode
						// pick the first one in the group as they are all the same
						: generateQRCodeForItem(husToGenerateQRFor.get(0), ImmutableSet.of()).get(0);

				huQRCodesRepository.assign(groupHUQrCode, getHuIds(husToGenerateQRFor));

				husToGenerateQRFor.forEach(hu -> result.put(hu.getHuId(), groupHUQrCode));
			}
		}

		return result;
	}

	@NonNull
	private ImmutableList<HUQRCode> generateQRCodeForItem(
			@NonNull final HUItemToGroup item,
			@NonNull final ImmutableSet<HUQRCode> existingQRCodes)
	{
		final ImmutableList.Builder<HUQRCode> result = ImmutableList.builder();
		result.addAll(existingQRCodes);

		final List<HUQRCode> newQRCodes = generateRemainingQRCodes(item.getHu(), existingQRCodes.size(), item.getProductQrCodeConfiguration());
		result.addAll(newQRCodes);

		newQRCodes.forEach(newQRCode -> huQRCodesRepository.createNew(newQRCode, item.getHuId()));
		return result.build();
	}

	@NonNull
	private ImmutableList<ImmutableList<HUItemToGroup>> groupHUsForSharingQRCodes()
	{
		return GroupBuilder.ofBuildKeyFunction(this::buildGroupKey)
				.addAllToGroups(getHUsToGroup())
				.buildGroups();
	}

	@NonNull
	private List<HUItemToGroup> getHUsToGroup()
	{
		final Map<HuId, I_M_HU> huId2Hu = getId2HU();

		final Map<HuId, ProductId> huId2Product = handlingUnitsBL.getStorageFactory()
				.streamHUProductStorages(ImmutableList.copyOf(huId2Hu.values()))
				.collect(Collectors.groupingBy(IHUProductStorage::getHuId,
											   Collectors.mapping(IHUProductStorage::getProductId, Collectors.toSet())))
				.entrySet()
				.stream()
				.filter(huId2Products -> huId2Products.getValue().size() == 1)
				.collect(ImmutableMap.toImmutableMap(
						Map.Entry::getKey,
						huId2ProductEntry -> huId2ProductEntry.getValue().iterator().next()));

		final Map<ProductId, QRCodeConfigurationId> productId2QRCodeConfig = productBL
				.getByIds(ImmutableSet.copyOf(huId2Product.values()))
				.stream()
				.filter(product -> product.getQRCode_Configuration_ID() > 0)
				.collect(ImmutableMap.toImmutableMap(product -> ProductId.ofRepoId(product.getM_Product_ID()),
													 product -> QRCodeConfigurationId.ofRepoId(product.getQRCode_Configuration_ID())));

		final Map<QRCodeConfigurationId, QRCodeConfiguration> id2Configuration = qrCodeConfigurationService
				.getByIds(ImmutableSet.copyOf(productId2QRCodeConfig.values()));

		final Function<ProductId, QRCodeConfiguration> getQRConfigForProduct = productId ->
				Optional.ofNullable(productId2QRCodeConfig.get(productId))
						.map(id2Configuration::get)
						.orElse(null);

		return huId2Product.entrySet()
				.stream()
				.map(huId2ProductId -> HUItemToGroup.builder()
						.hu(huId2Hu.get(huId2ProductId.getKey()))
						.productId(huId2ProductId.getValue())
						.productQrCodeConfiguration(getQRConfigForProduct.apply(huId2ProductId.getValue()))
						.build())
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private GroupKey buildGroupKey(@NonNull final HUItemToGroup huItemToGroup)
	{
		if (!huItemToGroup.isGroupingByMatchingAttributesEnabled())
		{
			return SingleGroupKey.ofHuId(huItemToGroup.getHuId());
		}

		final I_M_HU_PI_Version piVersion = handlingUnitsBL.getEffectivePIVersion(huItemToGroup.getHu());
		if (piVersion == null)
		{
			return SingleGroupKey.ofHuId(huItemToGroup.getHuId());
		}

		if (HUType.TransportUnit != HUType.ofCodeOrNull(piVersion.getHU_UnitType()))
		{
			return SingleGroupKey.ofHuId(huItemToGroup.getHuId());
		}

		return TUGroupKey.builder()
				.packingInstructionsId(HuPackingInstructionsId.ofRepoId(piVersion.getM_HU_PI_ID()))
				.productId(huItemToGroup.getProductId())
				.attributesKey(getAttributesKey(huItemToGroup))
				.build();
	}

	@NonNull
	private Map<HuId, I_M_HU> getId2HU()
	{
		return handlingUnitsBL.getByIds(huIds)
				.stream()
				.collect(ImmutableMap.toImmutableMap(hu -> HuId.ofRepoId(hu.getM_HU_ID()), Function.identity()));
	}

	@NonNull
	private AttributesKey getAttributesKey(@NonNull final HUItemToGroup hu)
	{
		final IAttributeStorage attributeStorage = attributeStorageFactory.getAttributeStorage(hu.getHu());

		final Set<AttributeId> attributeIds = hu.getAttributesAssumingGroupingIsEnabled();

		final Predicate<I_M_Attribute> qrMatchingRelevantAttributes =
				attribute -> attributeIds.contains(AttributeId.ofRepoId(attribute.getM_Attribute_ID()));
		final ImmutableAttributeSet requestedAttributeSet = ImmutableAttributeSet.createSubSet(attributeStorage, qrMatchingRelevantAttributes);

		return AttributesKeys
				.createAttributesKeyFromAttributeSet(requestedAttributeSet)
				.orElse(AttributesKey.NONE);
	}

	@NonNull
	private static ImmutableSet<HuId> getHuIds(@NonNull final Collection<HUItemToGroup> hus)
	{
		return hus.stream()
				.map(HUItemToGroup::getHuId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@Value(staticConstructor = "ofBuildKeyFunction")
	private static class GroupBuilder
	{
		@NonNull Function<HUItemToGroup, GroupKey> buildKeyFunction;
		@NonNull Map<GroupKey, ImmutableList.Builder<HUItemToGroup>> key2Group = new HashMap<>();

		public GroupBuilder addAllToGroups(@NonNull final Collection<HUItemToGroup> items)
		{
			items.forEach(this::addToGroup);
			return this;
		}

		private void addToGroup(@NonNull final HUItemToGroup huItemToGroup)
		{
			final GroupKey groupKey = buildKeyFunction.apply(huItemToGroup);
			final ImmutableList.Builder<HUItemToGroup> huGroup = key2Group.getOrDefault(groupKey, ImmutableList.builder());
			huGroup.add(huItemToGroup);
			key2Group.put(groupKey, huGroup);
		}

		@NonNull
		public ImmutableList<ImmutableList<HUItemToGroup>> buildGroups()
		{
			return key2Group.values()
					.stream()
					.map(ImmutableList.Builder::build)
					.collect(ImmutableList.toImmutableList());
		}
	}

	@Value
	@Builder
	private static class HUItemToGroup
	{
		@NonNull I_M_HU hu;
		@NonNull ProductId productId;
		@Nullable QRCodeConfiguration productQrCodeConfiguration;

		@NonNull
		public HuId getHuId()
		{
			return HuId.ofRepoId(hu.getM_HU_ID());
		}

		@NonNull
		public Set<AttributeId> getAttributesAssumingGroupingIsEnabled()
		{
			Check.assume(isGroupingByMatchingAttributesEnabled(), "Assuming grouping by attributes is enabled!");
			return productQrCodeConfiguration.getGroupByAttributeIds();
		}

		public boolean isGroupingByMatchingAttributesEnabled()
		{
			return productQrCodeConfiguration != null && productQrCodeConfiguration.isGroupingByAttributesEnabled();
		}
	}

	private interface GroupKey {}

	@Value
	@Builder
	private static class TUGroupKey implements GroupKey
	{
		@NonNull ProductId productId;
		@NonNull HuPackingInstructionsId packingInstructionsId;
		@NonNull AttributesKey attributesKey;
	}

	@Value(staticConstructor = "ofHuId")
	private static class SingleGroupKey implements GroupKey
	{
		@NonNull HuId huId;
	}
}

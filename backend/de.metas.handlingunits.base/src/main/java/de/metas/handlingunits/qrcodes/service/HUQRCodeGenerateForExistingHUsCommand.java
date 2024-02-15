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
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.AttributesKeys;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
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
	@NonNull
	private final IHandlingUnitsBL handlingUnitsBL;
	@NonNull
	private final IProductBL productBL;
	@NonNull
	private final IAttributeDAO attributeDAO;
	@NonNull
	private final HUQRCodesRepository huQRCodesRepository;
	@NonNull
	private final QRCodeConfigurationRepository qrCodeConfigurationRepository;
	@NonNull
	private final IAttributeStorageFactory attributeStorageFactory;

	@NonNull
	private final HUQRCodeGenerateForExistingHUsRequest request;

	private HUQRCodeGenerateCommand.Cache cache = null;

	@Builder
	private HUQRCodeGenerateForExistingHUsCommand(
			final @NonNull IHandlingUnitsBL handlingUnitsBL,
			final @NonNull IProductBL productBL,
			final @NonNull IAttributeDAO attributeDAO,
			final @NonNull HUQRCodesRepository huQRCodesRepository,
			final @NonNull QRCodeConfigurationRepository qrCodeConfigurationRepository,
			final @NonNull IAttributeStorageFactoryService attributeStorageFactoryService,
			final @NonNull HUQRCodeGenerateForExistingHUsRequest request)
	{
		this.handlingUnitsBL = handlingUnitsBL;
		this.productBL = productBL;
		this.attributeDAO = attributeDAO;
		this.huQRCodesRepository = huQRCodesRepository;
		this.qrCodeConfigurationRepository = qrCodeConfigurationRepository;
		this.attributeStorageFactory = attributeStorageFactoryService.createHUAttributeStorageFactory();
		this.request = request;
	}

	public HUQRCodeGenerateForExistingHUsResult execute()
	{
		if (qrCodeConfigurationRepository.isAtLeastOneActiveConfig())
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
		final ImmutableSet<HuId> huIds = request.getHuIds();
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
					&& qrCodeConfiguration.isOneQRCodeForAggregatedTUsRequired()
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
						.attributes(ImmutableList.of()) // TODO extract relevant attributes from HU
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

	private HUQRCodeGenerateForExistingHUsResult executeWithGrouping()
	{
		final ImmutableList<ImmutableList<HUItemToGroup>> groups = groupHUsForSharingQRCodes();
		final Set<HuId> huIds = groups.stream()
				.flatMap(Collection::stream)
				.map(HUItemToGroup::getHuId)
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
			result.putAll(generateQRCodeForItem(group.get(0), existingQRCodesByHuId));
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
					result.putAll(generateQRCodeForItem(item, existingQRCodesByHuId));
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
						: generateQRCodeForItem(husToGenerateQRFor.get(0), ImmutableSetMultimap.of())
						.get(husToGenerateQRFor.get(0).getHuId())
						.iterator().next();

				huQRCodesRepository.assign(groupHUQrCode, getHuIds(husToGenerateQRFor));

				husToGenerateQRFor.forEach(hu -> result.put(hu.getHuId(), groupHUQrCode));
			}
		}

		return result;
	}

	@NonNull
	private HashMultimap<HuId, HUQRCode> generateQRCodeForItem(
			@NonNull final HUItemToGroup item,
			@NonNull final ImmutableSetMultimap<HuId, HUQRCode> existingQRCodesByHuId)
	{
		final HashMultimap<HuId, HUQRCode> result = HashMultimap.create();
		final ImmutableSet<HUQRCode> existingQRCodes = existingQRCodesByHuId.get(item.getHuId());
		result.putAll(item.getHuId(), existingQRCodes);

		final List<HUQRCode> newQRCodes = generateRemainingQRCodes(item.getHu(), existingQRCodes.size(), item.getQrCodeConfiguration());
		result.putAll(item.getHuId(), newQRCodes);

		newQRCodes.forEach(newQRCode -> huQRCodesRepository.createNew(newQRCode, item.getHuId()));
		return result;
	}

	@NonNull
	private ImmutableList<ImmutableList<HUItemToGroup>> groupHUsForSharingQRCodes()
	{
		final GroupBuilder groupBuilder = GroupBuilder.ofKeyFunction(this::buildGroupKey);
		getHUsToGroup(getId2HU()).forEach(groupBuilder::addToGroup);
		return groupBuilder.buildGroups();
	}

	@NonNull
	private List<HUItemToGroup> getHUsToGroup(@NonNull final Map<HuId, I_M_HU> huId2Hu)
	{
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

		final Map<QRCodeConfigurationId, QRCodeConfiguration> id2Configuration = qrCodeConfigurationRepository
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
						.qrCodeConfiguration(getQRConfigForProduct.apply(huId2ProductId.getValue()))
						.build())
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private GroupKey buildGroupKey(@NonNull final HUItemToGroup huItemToGroup)
	{
		if (!huItemToGroup.isGroupingByMatchingAttributesRequired())
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
		return handlingUnitsBL.getByIds(request.getHuIds())
				.stream()
				.collect(ImmutableMap.toImmutableMap(hu -> HuId.ofRepoId(hu.getM_HU_ID()), Function.identity()));
	}

	@NonNull
	private AttributesKey getAttributesKey(@NonNull final HUItemToGroup hu)
	{
		final IAttributeStorage attributeStorage = attributeStorageFactory.getAttributeStorage(hu.getHu());

		final Set<AttributeId> attributeIds = hu.getAttributesAssumingGroupingIsRequired();

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

	@Value(staticConstructor = "ofKeyFunction")
	private static class GroupBuilder
	{
		@NonNull Function<HUItemToGroup, GroupKey> buildKeyFunction;
		@NonNull Map<GroupKey, ImmutableList.Builder<HUItemToGroup>> key2Group = new HashMap<>();

		public void addToGroup(@NonNull final HUItemToGroup huItemToGroup)
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
		@Nullable QRCodeConfiguration qrCodeConfiguration;

		@NonNull
		public HuId getHuId()
		{
			return HuId.ofRepoId(hu.getM_HU_ID());
		}

		@NonNull
		public Set<AttributeId> getAttributesAssumingGroupingIsRequired()
		{
			Check.assume(qrCodeConfiguration != null && qrCodeConfiguration.isGroupingByAttributesRequired(), "Assuming grouping by attributes is required!");
			return qrCodeConfiguration.getAttributeIds();
		}

		public boolean isGroupingByMatchingAttributesRequired()
		{
			return qrCodeConfiguration != null && qrCodeConfiguration.isGroupingByAttributesRequired();
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

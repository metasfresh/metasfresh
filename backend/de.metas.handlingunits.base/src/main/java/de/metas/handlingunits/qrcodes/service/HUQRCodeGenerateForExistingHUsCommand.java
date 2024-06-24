package de.metas.handlingunits.qrcodes.service;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodeAttribute;
import de.metas.handlingunits.qrcodes.model.HUQRCodePackingInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeProductInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUniqueId;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUnitType;
import de.metas.i18n.ITranslatableString;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class HUQRCodeGenerateForExistingHUsCommand
{
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	@NonNull private final HUQRCodesRepository huQRCodesRepository;

	@NonNull final ImmutableSet<HuId> huIds;

	//
	// State
	private final HashMap<AttributeCode, String> attributeDisplayNames = new HashMap<>();

	//
	// Value Extractors
	@FunctionalInterface
	private interface AttributeValueExtractor
	{
		String extractValueAsString(final ImmutableAttributeSet attributes, final AttributeCode attributeCode);
	}

	private static final AttributeValueExtractor LOCAL_DATE_EXTRACTOR = (attributes, attributeCode) -> {
		final LocalDate localDate = attributes.getValueAsLocalDate(attributeCode);
		return localDate != null ? localDate.toString() : null;
	};
	private static final AttributeValueExtractor STRING_EXTRACTOR = ImmutableAttributeSet::getValueAsString;
	private static final AttributeValueExtractor BIG_DECIMAL_EXTRACTOR = (attributes, attributeCode) -> {
		final BigDecimal bd = attributes.getValueAsBigDecimal(attributeCode);
		return bd != null ? bd.toString() : null;
	};

	@Builder
	private HUQRCodeGenerateForExistingHUsCommand(
			final @NonNull HUQRCodesRepository huQRCodesRepository,
			final @NonNull @Singular ImmutableSet<HuId> huIds)
	{
		this.huQRCodesRepository = huQRCodesRepository;
		this.huIds = huIds;
	}

	public HUQRCodeGenerateForExistingHUsResult execute()
	{
		final ImmutableSetMultimap<HuId, HUQRCode> existingQRCodesByHuId = huQRCodesRepository.getQRCodeByHuIds(huIds);

		final HashMultimap<HuId, HUQRCode> result = HashMultimap.create();
		for (final HuId huId : huIds)
		{
			final ImmutableSet<HUQRCode> existingQRCodes = existingQRCodesByHuId.get(huId);
			result.putAll(huId, existingQRCodes);

			final Set<HUQRCode> newQRCodes = generateRemainingQRCodesAndAssign(huId, existingQRCodes.size());
			result.putAll(huId, newQRCodes);
		}

		return new HUQRCodeGenerateForExistingHUsResult(result);
	}

	private Set<HUQRCode> generateRemainingQRCodesAndAssign(@NonNull final HuId huId, final int alreadyGeneratedQRCodesCount)
	{
		final I_M_HU_PI_Version piVersion;
		final int requiredQRCodesCount;
		final I_M_HU hu = handlingUnitsBL.getById(huId);
		if (handlingUnitsBL.isAggregateHU(hu))
		{
			piVersion = handlingUnitsBL.getEffectivePIVersion(hu);
			final QtyTU qtyTUs = handlingUnitsBL.getTUsCount(hu);
			requiredQRCodesCount = qtyTUs.toInt() - alreadyGeneratedQRCodesCount;
		}
		else
		{
			piVersion = handlingUnitsBL.getPIVersion(hu);
			requiredQRCodesCount = 1 - alreadyGeneratedQRCodesCount;
		}

		if (requiredQRCodesCount <= 0)
		{
			return ImmutableSet.of();
		}

		final HUQRCode.HUQRCodeBuilder huQRCodeTemplate = HUQRCode.builder()
				//.id(...) // will be set below
				.packingInfo(getHUQRCodePackingInfo(piVersion))
				.product(getHUQRCodeProductInfo(hu))
				.attributes(getHUQRCodeAttributes(hu));

		final HashSet<HUQRCode> result = new HashSet<>(requiredQRCodesCount);
		for (int i = 1; i <= requiredQRCodesCount; i++)
		{
			final HUQRCode huQRCode = huQRCodeTemplate.id(HUQRCodeUniqueId.random()).build();
			huQRCodesRepository.createNew(huQRCode, huId);

			result.add(huQRCode);
		}

		return result;
	}

	private HUQRCodePackingInfo getHUQRCodePackingInfo(final I_M_HU_PI_Version piVersion)
	{
		final I_M_HU_PI pi = handlingUnitsBL.getPI(piVersion);

		return HUQRCodePackingInfo.builder()
				.huUnitType(HUQRCodeUnitType.ofCode(piVersion.getHU_UnitType()))
				.packingInstructionsId(HuPackingInstructionsId.ofRepoId(piVersion.getM_HU_PI_ID()))
				.caption(pi.getName())
				.build();
	}

	private HUQRCodeProductInfo getHUQRCodeProductInfo(final I_M_HU hu)
	{
		final ProductId productId = handlingUnitsBL.getStorageFactory().getStorage(hu).getSingleProductIdOrNull();
		if (productId == null)
		{
			throw new AdempiereException("Only single product storages are supported");
		}

		final I_M_Product product = productBL.getById(productId);

		return HUQRCodeProductInfo.builder()
				.id(productId)
				.code(product.getValue())
				.name(product.getName())
				.build();
	}

	private ImmutableList<HUQRCodeAttribute> getHUQRCodeAttributes(final I_M_HU hu)
	{
		ImmutableList.Builder<HUQRCodeAttribute> result = ImmutableList.builder();

		final ImmutableAttributeSet attributes = handlingUnitsBL.getImmutableAttributeSet(hu);

		extractHUQRCodeAttribute(attributes, AttributeConstants.ATTR_BestBeforeDate, LOCAL_DATE_EXTRACTOR).ifPresent(result::add);
		extractHUQRCodeAttribute(attributes, AttributeConstants.ATTR_LotNumber, STRING_EXTRACTOR).ifPresent(result::add);
		extractHUQRCodeAttribute(attributes, Weightables.ATTR_WeightNet, BIG_DECIMAL_EXTRACTOR).ifPresent(result::add);

		return result.build();
	}

	private Optional<HUQRCodeAttribute> extractHUQRCodeAttribute(
			@NonNull final ImmutableAttributeSet attributes,
			@NonNull final AttributeCode attributeCode,
			@NonNull final AttributeValueExtractor valueExtractor)
	{
		if (!attributes.hasAttribute(attributeCode))
		{
			return Optional.empty();
		}

		final String value = valueExtractor.extractValueAsString(attributes, attributeCode);

		return Optional.of(HUQRCodeAttribute.builder()
				.code(attributeCode)
				.displayName(getAttributeDisplayName(attributeCode))
				.value(value)
				.valueRendered(value)
				.build());
	}

	private String getAttributeDisplayName(final AttributeCode attributeCode)
	{
		return attributeDisplayNames.computeIfAbsent(attributeCode, this::computeAttributeDisplayName);
	}

	private String computeAttributeDisplayName(final AttributeCode attributeCode)
	{
		return attributeDAO.getAttributeDisplayNameByValue(attributeCode)
				.map(ITranslatableString::getDefaultValue)
				.orElseGet(attributeCode::getCode);
	}
}

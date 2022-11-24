package de.metas.handlingunits.qrcodes.service;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodePackingInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeProductInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUniqueId;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUnitType;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Product;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class HUQRCodeGenerateForExistingHUsCommand
{
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final HUQRCodesRepository huQRCodesRepository;

	@NonNull private final HUQRCodeGenerateForExistingHUsRequest request;

	@Builder
	private HUQRCodeGenerateForExistingHUsCommand(
			final @NonNull HUQRCodesRepository huQRCodesRepository,
			final @NonNull HUQRCodeGenerateForExistingHUsRequest request)
	{
		this.huQRCodesRepository = huQRCodesRepository;
		this.request = request;
	}

	public HUQRCodeGenerateForExistingHUsResult execute()
	{
		final ImmutableSet<HuId> huIds = request.getHuIds();
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

		if(requiredQRCodesCount <= 0)
		{
			return ImmutableSet.of();
		}

		final I_M_HU_PI pi = handlingUnitsBL.getPI(piVersion);

		final ProductId productId = handlingUnitsBL.getStorageFactory().getStorage(hu).getSingleProductIdOrNull();
		if (productId == null)
		{
			throw new AdempiereException("Only single product storages are supported");
		}

		final I_M_Product product = productBL.getById(productId);
		final HUQRCode.HUQRCodeBuilder huQRCodeTemplate = HUQRCode.builder()
				//.id(...) // will be set below
				.packingInfo(HUQRCodePackingInfo.builder()
						.huUnitType(HUQRCodeUnitType.ofCode(piVersion.getHU_UnitType()))
						.packingInstructionsId(HuPackingInstructionsId.ofRepoId(piVersion.getM_HU_PI_ID()))
						.caption(pi.getName())
						.build())
				.product(HUQRCodeProductInfo.builder()
						.id(productId)
						.code(product.getValue())
						.name(product.getName())
						.build())
				.attributes(ImmutableList.of())        // TODO: attributes fetch relevant attributes
				;

		final HashSet<HUQRCode> result = new HashSet<>(requiredQRCodesCount);
		for(int i = 1; i <= requiredQRCodesCount; i++)
		{
			final HUQRCode huQRCode = huQRCodeTemplate
					.id(HUQRCodeUniqueId.ofUUID(UUID.randomUUID()))
					.build();
			huQRCodesRepository.createNew(huQRCode, huId);

			result.add(huQRCode);
		}

		return result;
	}
}

package de.metas.handlingunits.qrcodes.service;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuItemId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.qrcodes.model.HUOrAggregatedTUItemId;
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
		final ImmutableSet<HUOrAggregatedTUItemId> huOrAggregatedTUItemIds = request.getHuOrAggregatedTUItemIds();
		final ImmutableSetMultimap<HUOrAggregatedTUItemId, HUQRCode> existingQRCodes = huQRCodesRepository.getQRCodeByHuOrAggregatedTUItemIds(huOrAggregatedTUItemIds);

		final HashMultimap<HUOrAggregatedTUItemId, HUQRCode> result = HashMultimap.create();
		for (final HUOrAggregatedTUItemId huOrAggregatedTUItemId : huOrAggregatedTUItemIds)
		{
			final Set<HUQRCode> huQRCodes = existingQRCodes.containsKey(huOrAggregatedTUItemId)
					? existingQRCodes.get(huOrAggregatedTUItemId)
					: generateQRCodesAndAssign(huOrAggregatedTUItemId);

			result.putAll(huOrAggregatedTUItemId, huQRCodes);
		}

		return new HUQRCodeGenerateForExistingHUsResult(result);
	}

	private Set<HUQRCode> generateQRCodesAndAssign(final HUOrAggregatedTUItemId huOrAggregatedTUItemId)
	{
		if (huOrAggregatedTUItemId.isHuId())
		{
			return ImmutableSet.of(generateQRCodesAndAssign_forHuId(huOrAggregatedTUItemId.getHuId()));
		}
		else if (huOrAggregatedTUItemId.isAggregatedTU())
		{
			return generateQRCodesAndAssign_forAggregatedTUItemId(huOrAggregatedTUItemId.getAggregatedTUItemId());
		}
		else
		{
			throw new AdempiereException("Unknown HUOrAggregatedTUItemId type: " + huOrAggregatedTUItemId);
		}
	}

	private HUQRCode generateQRCodesAndAssign_forHuId(@NonNull final HuId huId)
	{
		final I_M_HU hu = handlingUnitsBL.getById(huId);
		final I_M_HU_PI_Version piVersion = handlingUnitsBL.getPIVersion(hu);
		final I_M_HU_PI pi = handlingUnitsBL.getPI(piVersion);

		final ProductId productId = handlingUnitsBL.getStorageFactory().getStorage(hu).getSingleProductIdOrNull();
		if (productId == null)
		{
			throw new AdempiereException("Only single product storages are supported");
		}

		final I_M_Product product = productBL.getById(productId);

		final HUQRCode huQRCode = HUQRCode.builder()
				.id(HUQRCodeUniqueId.ofUUID(UUID.randomUUID()))
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
				.attributes(ImmutableList.of()) // TODO: attributes fetch relevant attributes
				.build();

		huQRCodesRepository.createNew(huQRCode, HUOrAggregatedTUItemId.ofHuId(huId));

		return huQRCode;
	}

	private Set<HUQRCode> generateQRCodesAndAssign_forAggregatedTUItemId(@NonNull final HuItemId aggregatedTUItemId)
	{
		// TODO impl
		throw new AdempiereException("Generating QR codes for aggregated TU items is not supported yet");
	}

}

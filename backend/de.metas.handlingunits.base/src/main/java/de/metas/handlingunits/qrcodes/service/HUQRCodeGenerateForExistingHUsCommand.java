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
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeDAO;

import java.util.List;

public class HUQRCodeGenerateForExistingHUsCommand
{
	@NonNull private final IHandlingUnitsBL handlingUnitsBL;
	@NonNull private final IProductBL productBL;
	@NonNull private final IAttributeDAO attributeDAO;
	@NonNull private final HUQRCodesRepository huQRCodesRepository;

	@NonNull private final HUQRCodeGenerateForExistingHUsRequest request;

	private HUQRCodeGenerateCommand.Cache cache = null;

	@Builder
	private HUQRCodeGenerateForExistingHUsCommand(
			final @NonNull IHandlingUnitsBL handlingUnitsBL,
			final @NonNull IProductBL productBL,
			final @NonNull IAttributeDAO attributeDAO, final @NonNull HUQRCodesRepository huQRCodesRepository,
			final @NonNull HUQRCodeGenerateForExistingHUsRequest request)
	{
		this.handlingUnitsBL = handlingUnitsBL;
		this.productBL = productBL;
		this.attributeDAO = attributeDAO;
		this.huQRCodesRepository = huQRCodesRepository;
		this.request = request;
	}

	public HUQRCodeGenerateForExistingHUsResult execute()
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

			final List<HUQRCode> newQRCodes = generateRemainingQRCodesAndAssign(hu, existingQRCodes.size());
			result.putAll(huId, newQRCodes);

			newQRCodes.forEach(newQRCode -> huQRCodesRepository.createNew(newQRCode, huId));
		}

		return new HUQRCodeGenerateForExistingHUsResult(result);
	}

	private List<HUQRCode> generateRemainingQRCodesAndAssign(@NonNull final I_M_HU hu, final int alreadyGeneratedQRCodesCount)
	{
		final I_M_HU_PI_Version piVersion;
		final int requiredQRCodesCount;
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
}

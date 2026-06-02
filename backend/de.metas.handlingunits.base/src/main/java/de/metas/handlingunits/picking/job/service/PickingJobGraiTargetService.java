package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.grai.GRAI;
import de.metas.handlingunits.grai.HUPIGraiRepository;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.picking.job.model.LUPickingTarget;
import de.metas.i18n.AdMessageKey;
import de.metas.product.ProductId;
import de.metas.scannable_code.ScannedCode;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Resolves the TU type and capacity for a GRAI-scan picking target, and verifies the resolved TU
 * is includable in the effective picking-target LU.
 */
@Service
@RequiredArgsConstructor
public class PickingJobGraiTargetService
{
	private static final AdMessageKey MSG_INVALID_GRAI_BARCODE = AdMessageKey.of("de.metas.handlingunits.picking.InvalidGRAIBarcode");
	private static final AdMessageKey MSG_TU_NOT_ALLOWED_ON_LU = AdMessageKey.of("de.metas.handlingunits.picking.GRAITUNotAllowedOnLU");
	private static final AdMessageKey MSG_NO_CAPACITY_FOR_PRODUCT = AdMessageKey.of("de.metas.handlingunits.picking.GRAINoCapacityForProduct");

	@NonNull private final HUPIGraiRepository huPIGraiRepository;

	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHUPIItemProductDAO piipDAO = Services.get(IHUPIItemProductDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	/**
	 * Parses the scanned GRAI, resolves the TU type, checks it against the effective LU target (if any)
	 * and resolves the capacity for the line's product. Creates no HU.
	 *
	 * @param luTarget the effective LU picking target; {@code null} → the TU-LU check is skipped
	 */
	@NonNull
	public GraiTuResolution resolveTuTypeAndCapacity(
			@NonNull final ScannedCode scannedGrai,
			@Nullable final LUPickingTarget luTarget,
			@NonNull final ProductId lineProductId)
	{
		final GRAI grai = GRAI.parse(scannedGrai.getAsString());
		if (grai == null)
		{
			throw new AdempiereException(MSG_INVALID_GRAI_BARCODE, scannedGrai.getAsString());
		}

		final HuPackingInstructionsId tuPIId = huPIGraiRepository.resolveHuPackingInstructionsId(grai);

		if (luTarget != null)
		{
			assertTuAllowedOnLu(tuPIId, luTarget);
		}

		final HUPIItemProductId huPIItemProductId = resolveCapacity(tuPIId, lineProductId);

		return GraiTuResolution.builder()
				.grai(grai)
				.tuPIId(tuPIId)
				.huPIItemProductId(huPIItemProductId)
				.build();
	}

	/**
	 * Verifies the TU PI is permitted on the LU picking target.
	 */
	public void assertTuAllowedOnLu(
			@NonNull final HuPackingInstructionsId tuPIId,
			@NonNull final LUPickingTarget luTarget)
	{
		final HuPackingInstructionsId luPIId = resolveLuPackingInstructionsId(luTarget);

		// NOTE: bpartnerId=null intentionally — only generic (no-partner) M_HU_PI_Items are matched.
		// Partner-specific inclusions (C_BPartner_ID != null) are not considered here because
		// GRAI mapping is configured at the general M_HU_PI level, and generic inclusions cover the expected case.
		final boolean allowed = handlingUnitsDAO.retrieveFirstPIItem(luPIId, tuPIId, /* bpartnerId= */ null).isPresent();

		if (!allowed)
		{
			throw new AdempiereException(MSG_TU_NOT_ALLOWED_ON_LU);
		}
	}

	/**
	 * Returns the {@link HUPIItemProductId} of the active capacity record for the given TU packing instruction and product;
	 * the default-for-product record is preferred when multiple matches exist.
	 */
	@NonNull
	public HUPIItemProductId resolveCapacity(
			@NonNull final HuPackingInstructionsId tuPIId,
			@NonNull final ProductId productId)
	{
		final I_M_HU_PI_Version tuPIVersion = handlingUnitsDAO.retrievePICurrentVersion(tuPIId);
		final I_M_HU_PI_Item miItem = handlingUnitsDAO.retrievePIItemMaterial(tuPIVersion);

		final List<I_M_HU_PI_Item_Product> allPiips = piipDAO.retrievePIMaterialItemProducts(miItem);

		final ImmutableList<I_M_HU_PI_Item_Product> matching = allPiips.stream()
				.filter(piip -> piip.isAllowAnyProduct() || ProductId.ofRepoId(piip.getM_Product_ID()).equals(productId))
				.collect(ImmutableList.toImmutableList());

		if (matching.isEmpty())
		{
			throw new AdempiereException(MSG_NO_CAPACITY_FOR_PRODUCT);
		}

		final I_M_HU_PI_Item_Product selected = matching.stream()
				.filter(I_M_HU_PI_Item_Product::isDefaultForProduct)
				.findFirst()
				.orElse(matching.get(0));

		return HUPIItemProductId.ofRepoId(selected.getM_HU_PI_Item_Product_ID());
	}

	@NonNull
	private HuPackingInstructionsId resolveLuPackingInstructionsId(@NonNull final LUPickingTarget luTarget)
	{
		if (luTarget.isNewLU())
		{
			return luTarget.getLuPIIdNotNull();
		}
		else
		{
			final HuId luId = luTarget.getLuIdNotNull();
			final I_M_HU lu = handlingUnitsDAO.getById(luId);
			return handlingUnitsBL.getPackingInstructionsId(lu);
		}
	}
}

package de.metas.handlingunits.picking.job.service.commands.grai;

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
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for GRAI picking target checks — specifically, verifying that a resolved TU packing-instruction
 * is includable in the effective picking-target LU's packing instruction.
 */
@Service
public class PickingJobGraiTargetService
{
	private static final AdMessageKey MSG_INVALID_GRAI_BARCODE = AdMessageKey.of("de.metas.handlingunits.picking.InvalidGRAIBarcode");
	private static final AdMessageKey MSG_TU_NOT_ALLOWED_ON_LU = AdMessageKey.of("de.metas.handlingunits.picking.GRAITUNotAllowedOnLU");
	private static final AdMessageKey MSG_NO_CAPACITY_FOR_PRODUCT = AdMessageKey.of("de.metas.handlingunits.picking.GRAINoCapacityForProduct");

	private final HUPIGraiRepository huPIGraiRepository = new HUPIGraiRepository();

	/**
	 * Steps 1..4 of the GRAI flow: parse, resolve TU type, optional LU check, capacity.
	 * Does <b>not</b> create any HU.
	 *
	 * @param scannedGrai   the raw scanned barcode (step 1)
	 * @param luTargetOpt   the effective LU picking target; empty → the TU-LU check (step 3) is skipped
	 * @param lineProductId the line's product, used to derive capacity (step 4)
	 * @return a {@link GraiTuResolution} holding the parsed GRAI, the resolved TU PI id and the PIIP id
	 */
	@NonNull
	public GraiTuResolution resolveTuTypeAndCapacity(
			@NonNull final String scannedGrai,
			@NonNull final Optional<LUPickingTarget> luTargetOpt,
			@NonNull final ProductId lineProductId)
	{
		// 1. Parse
		final GRAI grai = GRAI.parse(scannedGrai);
		if (grai == null)
		{
			throw new AdempiereException(MSG_INVALID_GRAI_BARCODE, scannedGrai);
		}

		// 2. Resolve TU type — propagates GRAINoMatchingTUType
		final HuPackingInstructionsId tuPIId = huPIGraiRepository.resolveHuPackingInstructionsId(grai);

		// 3. TU-LU association check — only when there is an effective LU target.
		// A loose TU with no LU target has nothing to be checked against, so the check is skipped.
		luTargetOpt.ifPresent(luTarget -> assertTuAllowedOnLu(tuPIId, luTarget));

		// 4. Capacity for the line's product — propagates GRAINoCapacityForProduct
		final HUPIItemProductId huPIItemProductId = resolveCapacity(tuPIId, lineProductId);

		return new GraiTuResolution(grai, tuPIId, huPIItemProductId);
	}

	/**
	 * Result of steps 1..4 of the GRAI flow.
	 */
	@Value
	public static class GraiTuResolution
	{
		@NonNull GRAI grai;
		@NonNull HuPackingInstructionsId tuPIId;
		@NonNull HUPIItemProductId huPIItemProductId;
	}

	/**
	 * Verifies the TU PI is permitted on the LU picking target; throws keyed {@code GRAITUNotAllowedOnLU} otherwise.
	 *
	 * @throws AdempiereException keyed on {@code de.metas.handlingunits.picking.GRAITUNotAllowedOnLU}
	 *                            when no matching M_HU_PI_Item is found.
	 */
	public void assertTuAllowedOnLu(
			@NonNull final HuPackingInstructionsId tuPIId,
			@NonNull final LUPickingTarget luTarget)
	{
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final HuPackingInstructionsId luPIId = resolveLuPackingInstructionsId(luTarget, handlingUnitsDAO);

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
	 * Resolves the {@link HUPIItemProductId} (PIIP) for the given TU packing-instruction and product.
	 * <p>
	 * Path: TU {@code M_HU_PI} → current {@code M_HU_PI_Version} → the Material ({@code ItemType='MI'})
	 * {@code M_HU_PI_Item} → its {@code M_HU_PI_Item_Product} rows filtered by {@code productId}.
	 * Among the matching rows, the default-for-product row ({@code IsDefaultForProduct=Y}) is preferred;
	 * otherwise the first row is returned. No matching row → throws keyed {@code GRAINoCapacityForProduct}.
	 *
	 * @throws AdempiereException keyed on {@code de.metas.handlingunits.picking.GRAINoCapacityForProduct}
	 *                            when no {@code M_HU_PI_Item_Product} exists for the given product on this TU PI.
	 */
	@NonNull
	public HUPIItemProductId resolveCapacity(
			@NonNull final HuPackingInstructionsId tuPIId,
			@NonNull final ProductId productId)
	{
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final IHUPIItemProductDAO piipDAO = Services.get(IHUPIItemProductDAO.class);

		final I_M_HU_PI_Version tuPIVersion = handlingUnitsDAO.retrievePICurrentVersion(tuPIId);
		final I_M_HU_PI_Item miItem = handlingUnitsDAO.retrievePIItemMaterial(tuPIVersion);

		final List<I_M_HU_PI_Item_Product> allPiips = piipDAO.retrievePIMaterialItemProducts(miItem);

		// Filter to those matching the requested productId (or AllowAnyProduct)
		final ImmutableList<I_M_HU_PI_Item_Product> matching = allPiips.stream()
				.filter(piip -> piip.isAllowAnyProduct() || ProductId.ofRepoId(piip.getM_Product_ID()).equals(productId))
				.collect(ImmutableList.toImmutableList());

		if (matching.isEmpty())
		{
			throw new AdempiereException(MSG_NO_CAPACITY_FOR_PRODUCT);
		}

		// Prefer the default-for-product row; fall back to the first
		final I_M_HU_PI_Item_Product selected = matching.stream()
				.filter(I_M_HU_PI_Item_Product::isDefaultForProduct)
				.findFirst()
				.orElse(matching.get(0));

		return HUPIItemProductId.ofRepoId(selected.getM_HU_PI_Item_Product_ID());
	}

	/**
	 * Resolves the LU's M_HU_PI_ID from the given {@link LUPickingTarget}.
	 * <ul>
	 *   <li>New LU: the PI id is carried directly on the target.</li>
	 *   <li>Existing LU: derive the PI from the existing HU record via {@link IHandlingUnitsBL}.</li>
	 * </ul>
	 */
	@NonNull
	private static HuPackingInstructionsId resolveLuPackingInstructionsId(
			@NonNull final LUPickingTarget luTarget,
			@NonNull final IHandlingUnitsDAO handlingUnitsDAO)
	{
		if (luTarget.isNewLU())
		{
			return luTarget.getLuPIIdNotNull();
		}
		else
		{
			final HuId luId = luTarget.getLuIdNotNull();
			final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
			final I_M_HU lu = handlingUnitsDAO.getById(luId);
			return handlingUnitsBL.getPackingInstructionsId(lu);
		}
	}
}

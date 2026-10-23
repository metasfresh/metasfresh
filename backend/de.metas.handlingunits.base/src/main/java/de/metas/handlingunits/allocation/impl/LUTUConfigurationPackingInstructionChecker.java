package de.metas.handlingunits.allocation.impl;

import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.i18n.AdMessageKey;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * Refuses LU/TU configurations that contradict the packing instruction (M_HU_PI_Item_Product) they are based on.
 * <p>
 * Rules (the check is skipped for "No Packing Item"):
 * <ul>
 *     <li>with a finite-capacity packing instruction, the quantity per TU must be the packing instruction's quantity</li>
 *     <li>the number of TUs per LU must not exceed the LU's capacity (M_HU_PI_Item.Qty), unless that capacity is <= 0</li>
 * </ul>
 */
@Service
public class LUTUConfigurationPackingInstructionChecker
{
	private static final AdMessageKey MSG_QtyCUsPerTU_NotMatchingPackingInstruction = AdMessageKey.of("M_HU_LUTU_Configuration_QtyCUsPerTU_NotMatchingPackingInstruction");
	private static final AdMessageKey MSG_QtyTU_ExceedsLUCapacity = AdMessageKey.of("M_HU_LUTU_Configuration_QtyTU_ExceedsLUCapacity");

	@NonNull private final IHUPIItemProductBL huPIItemProductBL = Services.get(IHUPIItemProductBL.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	public void assertLUTUConfigurationMatchesPackingInstruction(@NonNull final I_M_HU_LUTU_Configuration lutuConfig)
	{
		final HUPIItemProductId piItemProductId = HUPIItemProductId.ofRepoIdOrNull(lutuConfig.getM_HU_PI_Item_Product_ID());
		if (piItemProductId == null || piItemProductId.isVirtualHU())
		{
			return; // "No Packing Item"
		}

		final HUPIItemProduct piItemProduct = huPIItemProductBL.getById(piItemProductId);
		if (piItemProduct.isFiniteTU())
		{
			assertQtyCUsPerTUMatches(lutuConfig, piItemProduct);
		}

		assertQtyTUWithinLUCapacity(lutuConfig);
	}

	private void assertQtyCUsPerTUMatches(@NonNull final I_M_HU_LUTU_Configuration lutuConfig, @NonNull final HUPIItemProduct piItemProduct)
	{
		if (lutuConfig.isInfiniteQtyCU())
		{
			return;
		}

		final Quantity piQty = piItemProduct.getQtyCUsPerTU();
		final UomId configUomId = UomId.ofRepoIdOrNull(lutuConfig.getC_UOM_ID());
		final Quantity configQty = Quantitys.of(lutuConfig.getQtyCUsPerTU(), configUomId != null ? configUomId : piQty.getUomId());
		final Quantity configQtyInPiUom = convertTo(configQty, ProductId.ofRepoId(lutuConfig.getM_Product_ID()), piQty.getUomId());

		if (configQtyInPiUom.toBigDecimal().compareTo(piQty.toBigDecimal()) != 0)
		{
			throw new AdempiereException(MSG_QtyCUsPerTU_NotMatchingPackingInstruction, format(piQty.toBigDecimal()), format(configQtyInPiUom.toBigDecimal()));
		}
	}

	/**
	 * @return the packing instruction's quantity per TU, expressed in the given UOM (e.g. the UOM of an LU/TU configuration);
	 * the number the {@link #assertLUTUConfigurationMatchesPackingInstruction(I_M_HU_LUTU_Configuration)} check expects for a finite-capacity packing instruction.
	 * @param targetUomId {@code null} means the packing instruction's own UOM
	 */
	public BigDecimal getQtyCUsPerTUInUom(
			@NonNull final HUPIItemProduct piItemProduct,
			@NonNull final ProductId productId,
			@Nullable final UomId targetUomId)
	{
		final Quantity piQty = piItemProduct.getQtyCUsPerTU();
		return targetUomId != null
				? convertTo(piQty, productId, targetUomId).toBigDecimal()
				: piQty.toBigDecimal();
	}

	private Quantity convertTo(@NonNull final Quantity qty, @NonNull final ProductId productId, @NonNull final UomId targetUomId)
	{
		return qty.getUomId().equals(targetUomId)
				? qty
				: uomConversionBL.convertQuantityTo(qty, productId, targetUomId);
	}

	/**
	 * @return the maximum number of TUs the given LU (pallet) holds; empty if the LU is unknown or has no capacity limit (capacity <= 0)
	 */
	public static Optional<BigDecimal> getLUCapacityLimit(@Nullable final I_M_HU_PI_Item luPIItem)
	{
		if (luPIItem == null)
		{
			return Optional.empty();
		}
		final BigDecimal luCapacity = luPIItem.getQty();
		return luCapacity.signum() > 0 ? Optional.of(luCapacity) : Optional.empty();
	}

	private static void assertQtyTUWithinLUCapacity(@NonNull final I_M_HU_LUTU_Configuration lutuConfig)
	{
		if (lutuConfig.isInfiniteQtyTU())
		{
			return;
		}

		final BigDecimal luCapacity = getLUCapacityLimit(lutuConfig.getM_LU_HU_PI_Item()).orElse(null);
		if (luCapacity != null && lutuConfig.getQtyTU().compareTo(luCapacity) > 0)
		{
			throw new AdempiereException(MSG_QtyTU_ExceedsLUCapacity, format(luCapacity), format(lutuConfig.getQtyTU()));
		}
	}

	private static String format(final BigDecimal qty)
	{
		return qty.stripTrailingZeros().toPlainString();
	}
}

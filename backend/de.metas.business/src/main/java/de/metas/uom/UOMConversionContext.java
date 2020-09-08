package de.metas.uom;

import static de.metas.common.util.CoalesceUtil.coalesce;

import javax.annotation.Nullable;

import org.compiere.model.I_M_Product;

import de.metas.product.ProductId;
import lombok.Value;

@Value
public final class UOMConversionContext
{
	public enum Rounding
	{
		/** The result is rounded to the target uom's precision. The default. */
		TO_TARGET_UOM_PRECISION,

		/** The result has at least the same scale as the input quantity, even if that scale is bigger than the UOM's precision. */
		PRESERVE_SCALE;
	}

	public static UOMConversionContext of(@Nullable final ProductId productId, @Nullable final Rounding rounding)
	{
		return new UOMConversionContext(productId, rounding);
	}

	public static UOMConversionContext of(@Nullable final ProductId productId)
	{
		return new UOMConversionContext(productId, null/* rounding */);
	}

	public static UOMConversionContext of(final int productRepoId)
	{
		return of(ProductId.ofRepoIdOrNull(productRepoId));
	}

	/**
	 * @deprecated please use {@link #of(ProductId)}.
	 */
	@Deprecated
	public static UOMConversionContext of(final I_M_Product product)
	{
		return of(product != null ? ProductId.ofRepoId(product.getM_Product_ID()) : null);
	}

	ProductId productId;

	Rounding rounding;

	private UOMConversionContext(
			@Nullable final ProductId productId,
			@Nullable final Rounding rounding)
	{
		this.productId = productId;
		this.rounding = coalesce(rounding, Rounding.TO_TARGET_UOM_PRECISION);
	}
}

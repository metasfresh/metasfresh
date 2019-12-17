package de.metas.uom;

import javax.annotation.Nullable;

import org.compiere.model.I_M_Product;

import de.metas.product.ProductId;
import lombok.Value;

@Value
public final class UOMConversionContext
{
	public static UOMConversionContext of(final ProductId productId)
	{
		return new UOMConversionContext(productId);
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

	private final ProductId productId;

	public enum Rounding
	{
		TO_TARGET_UOM_PRECISION,


	}

	private UOMConversionContext(@Nullable final ProductId productId)
	{
		this.productId = productId;
	}
}

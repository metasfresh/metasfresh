package de.metas.pricing.attributebased;

import com.google.common.base.MoreObjects;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.DisplayType;

import javax.annotation.concurrent.Immutable;
import java.util.Optional;

/**
 * {@link IProductPriceAware} plain and immutable implementation.
 *
 * @author tsa
 *
 */
@Immutable
public class ProductPriceAware implements IProductPriceAware
{
	/**
	 * Introspects given model and returns {@link IProductPriceAware} is possible.
	 * <p>
	 * Basically a model to be {@link IProductPriceAware}
	 * <ul>
	 * <li>it has to implement that interface and in this case it will be returned right away.
	 * <li>or it needs to have the {@link IProductPriceAware#COLUMNNAME_M_ProductPrice_ID} column.
	 * </ul>
	 *
	 * @return {@link IProductPriceAware}.
	 */
	public static Optional<IProductPriceAware> ofModel(final Object model)
	{
		if (model == null)
		{
			return Optional.empty();
		}
		if (model instanceof IProductPriceAware)
		{
			final IProductPriceAware productPriceAware = (IProductPriceAware)model;
			return Optional.of(productPriceAware);
		}

		// If model does not have product price column we consider that it's not product price aware
		if (!InterfaceWrapperHelper.hasModelColumnName(model, COLUMNNAME_M_ProductPrice_ID))
		{
			return Optional.empty();
		}

		final Integer productPriceId = InterfaceWrapperHelper.getValueOrNull(model, COLUMNNAME_M_ProductPrice_ID);
		final boolean productPriceIdSet = productPriceId != null && productPriceId > 0;

		final boolean isExplicitProductPrice;
		if (InterfaceWrapperHelper.hasModelColumnName(model, COLUMNNAME_IsExplicitProductPriceAttribute))
		{
			final Object isExplicitObj = InterfaceWrapperHelper.getValueOrNull(model, COLUMNNAME_IsExplicitProductPriceAttribute);
			isExplicitProductPrice = DisplayType.toBooleanNonNull(isExplicitObj, false);
		}
		else
		{
			// In case the "IsExplicit" column is missing,
			// we are considering it as Explicit if the actual M_ProductPrice_ID is set.
			isExplicitProductPrice = productPriceIdSet;
		}

		final IProductPriceAware productPriceAware = new ProductPriceAware(
				isExplicitProductPrice,
				productPriceIdSet ? productPriceId : -1);
		return Optional.of(productPriceAware);
	}

	private final boolean isExplicitProductPrice;
	private final int productPriceId;

	private ProductPriceAware(final boolean isExplicitProductPrice, final int productPriceId)
	{
		this.isExplicitProductPrice = isExplicitProductPrice;
		this.productPriceId = productPriceId;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("productPriceId", productPriceId)
				.add("isExplicit", isExplicitProductPrice)
				.toString();
	}

	@Override
	public boolean isExplicitProductPriceAttribute()
	{
		return isExplicitProductPrice;
	}

	@Override
	public int getM_ProductPrice_ID()
	{
		return productPriceId;
	}
}

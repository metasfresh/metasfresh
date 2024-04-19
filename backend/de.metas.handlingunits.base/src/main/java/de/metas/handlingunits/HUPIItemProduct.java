package de.metas.handlingunits;

import de.metas.i18n.ITranslatableString;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityUOMConverter;
import de.metas.quantity.QuantityUOMConverters;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Value
public class HUPIItemProduct
{
	@NonNull HUPIItemProductId id;
	@NonNull ITranslatableString name;
	@Nullable ProductId productId;
	@Nullable Quantity qtyCUsPerTU;

	@Builder
	private HUPIItemProduct(
			@NonNull final HUPIItemProductId id,
			@NonNull final ITranslatableString name,
			@Nullable final ProductId productId,
			@Nullable final Quantity qtyCUsPerTU)
	{
		this.id = id;
		this.name = name;
		this.productId = productId;
		this.qtyCUsPerTU = qtyCUsPerTU != null && qtyCUsPerTU.signum() > 0 && !Quantity.isInfinite(qtyCUsPerTU.toBigDecimal()) ? qtyCUsPerTU : null;
	}

	public boolean isFiniteTU() {return !id.isVirtualHU() && !isInfiniteCapacity();}

	public boolean isInfiniteCapacity() {return qtyCUsPerTU == null;}

	private void assertProductMatches(@NonNull final ProductId productId)
	{
		if (this.productId != null && !ProductId.equals(this.productId, productId))
		{
			throw new AdempiereException("Product ID " + productId + " does not match the product of " + this);
		}
	}

	@NonNull
	public QtyTU computeQtyTUsOfTotalCUs(@NonNull final Quantity totalCUs, @NonNull final ProductId productId)
	{
		return computeQtyTUsOfTotalCUs(totalCUs, productId, QuantityUOMConverters.noConversion());
	}

	@NonNull
	public QtyTU computeQtyTUsOfTotalCUs(@NonNull final Quantity totalCUs, @NonNull final ProductId productId, @NonNull final QuantityUOMConverter uomConverter)
	{
		assertProductMatches(productId);

		if (totalCUs.signum() <= 0)
		{
			return QtyTU.ZERO;
		}

		// Infinite capacity
		if (qtyCUsPerTU == null)
		{
			return QtyTU.ONE;
		}

		final Quantity totalCUsConv = uomConverter.convertQuantityTo(totalCUs, productId, qtyCUsPerTU.getUomId());
		final BigDecimal qtyTUs = totalCUsConv.toBigDecimal().divide(qtyCUsPerTU.toBigDecimal(), 0, RoundingMode.UP);
		return QtyTU.ofBigDecimal(qtyTUs);
	}
}

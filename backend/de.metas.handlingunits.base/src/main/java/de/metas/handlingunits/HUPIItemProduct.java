package de.metas.handlingunits;

import de.metas.i18n.ITranslatableString;
import de.metas.product.ProductId;
import de.metas.quantity.Capacity;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityUOMConverter;
import de.metas.quantity.QuantityUOMConverters;
import de.metas.util.Check;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Value
@EqualsAndHashCode(doNotUseGetters = true)
public class HUPIItemProduct
{
	@NonNull HUPIItemProductId id;
	@NonNull ITranslatableString name;
	@Nullable String description;
	@NonNull HuPackingInstructionsItemId piItemId;
	@Nullable ProductId productId;
	@Nullable Quantity qtyCUsPerTU;

	@Builder
	private HUPIItemProduct(
			@NonNull final HUPIItemProductId id,
			@NonNull final ITranslatableString name,
			@Nullable final String description,
			@NonNull final HuPackingInstructionsItemId piItemId,
			@Nullable final ProductId productId,
			@Nullable final Quantity qtyCUsPerTU)
	{
		this.id = id;
		this.name = name;
		this.description = description;
		this.piItemId = piItemId;
		this.productId = productId;
		this.qtyCUsPerTU = qtyCUsPerTU != null && qtyCUsPerTU.signum() > 0 && !Quantity.isInfinite(qtyCUsPerTU.toBigDecimal()) ? qtyCUsPerTU : null;
	}

	@Override
	public String toString() {return name.getDefaultValue();}

	public Optional<ProductId> getProductId() {return Optional.ofNullable(productId);}

	public boolean isAllowAnyProduct() {return productId == null;}

	public Optional<I_C_UOM> getUom() {return qtyCUsPerTU != null ? Optional.of(qtyCUsPerTU.getUOM()) : Optional.empty();}

	public boolean isFiniteTU() {return !id.isVirtualHU() && !isInfiniteCapacity();}

	public boolean isInfiniteCapacity() {return qtyCUsPerTU == null;}

	@NonNull
	public Quantity getQtyCUsPerTU() {return Check.assumeNotNull(qtyCUsPerTU, "Expecting finite capacity: {}", this);}

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

	@NonNull
	public Quantity computeQtyCUsOfQtyTUs(@NonNull final QtyTU qtyTU)
	{
		if (qtyCUsPerTU == null)
		{
			throw new AdempiereException("Cannot calculate qty of CUs for infinite capacity");
		}

		return qtyCUsPerTU.multiply(qtyTU.toInt());
	}

	public Capacity toCapacity()
	{
		if (productId == null)
		{
			throw new AdempiereException("Cannot convert to Capacity when no product is specified")
					.setParameter("huPIItemProduct", this);
		}

		if (qtyCUsPerTU == null)
		{
			throw new AdempiereException("Cannot determine the UOM of " + this)
					.setParameter("huPIItemProduct", this);
		}

		return Capacity.createCapacity(qtyCUsPerTU, productId);
	}
}

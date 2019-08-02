package de.metas.quantity;

import java.util.Optional;

import javax.annotation.Nullable;

import de.metas.product.ProductId;
import de.metas.util.Check;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

@Value
public class StockQtyAndUOMQty
{
	ProductId productId;

	Quantity stockQty;

	/** Quantity in a "parallel" UOM. Note that often there is no fix UOM conversion rule between this quantity and {@link #getStockingQty()}. */
	@Getter(AccessLevel.NONE)
	Optional<Quantity> uomQty;

	public Optional<Quantity> getUOMQty()
	{
		return uomQty;
	}

	@Builder
	private StockQtyAndUOMQty(
			@NonNull final ProductId productId,
			@NonNull final Quantity stockQty,
			@Nullable final Quantity uomQty)
	{
		this.productId = productId;
		this.stockQty = stockQty;
		this.uomQty = Optional.ofNullable(uomQty);
	}

	public StockQtyAndUOMQty add(@NonNull final StockQtyAndUOMQty other)
	{
		Check.assumeEquals(productId, other.productId, "The other instance's productId need to be equan to this instance's productId; this={}; other={}", this, other);

		final StockQtyAndUOMQtyBuilder result = StockQtyAndUOMQty.builder()
				.productId(productId)
				.stockQty(stockQty.add(other.getStockQty()));

		if (getUOMQty().isPresent())
		{
			Check.assume(other.uomQty.isPresent(), "If this instance's uomQty is present, then the other instance's uomQty also needs to be present; this={}; other={}", this, other);
			result.uomQty(uomQty.get().add(other.uomQty.get()));
		}

		return result.build();
	}

	public int signum()
	{
		return getStockQty().signum();
	}

	public StockQtyAndUOMQty negate()
	{
		final StockQtyAndUOMQtyBuilder result = StockQtyAndUOMQty
				.builder()
				.productId(productId)
				.stockQty(stockQty.negate());

		if (uomQty.isPresent())
		{
			result.uomQty(uomQty.get().negate());
		}

		return result.build();
	}

	public StockQtyAndUOMQty subtract(@NonNull final StockQtyAndUOMQty other)
	{
		Check.assumeEquals(productId, other.productId, "The other instance's productId need to be equan to this instance's productId; this={}; other={}", this, other);

		final StockQtyAndUOMQtyBuilder result = StockQtyAndUOMQty.builder()
				.productId(productId)
				.stockQty(stockQty.subtract(other.getStockQty()));

		if (getUOMQty().isPresent())
		{
			Check.assume(other.uomQty.isPresent(), "If this instance's uomQty is present, then the other instance's uomQty also needs to be present; this={}; other={}", this, other);
			result.uomQty(uomQty.get().subtract(other.uomQty.get()));
		}

		return result.build();
	}
}

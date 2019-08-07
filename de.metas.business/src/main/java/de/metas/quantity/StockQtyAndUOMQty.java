package de.metas.quantity;

import java.util.Optional;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.product.ProductId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public class StockQtyAndUOMQty
{
	ProductId productId;

	Quantity stockQty;

	/** Quantity in a "parallel" UOM. Note that often there is no fix UOM conversion rule between this quantity and {@link #getStockingQty()}. */
	Quantity uomQty;


	@JsonIgnore
	public Optional<Quantity> getUOMQtyOpt()
	{// TODO consider getting rid of optional altogether
		return Optional.ofNullable(uomQty);
	}

	@Builder(toBuilder = true)
	@JsonCreator
	private StockQtyAndUOMQty(
			@JsonProperty("productId") @NonNull final ProductId productId,
			@JsonProperty("stockQty") @NonNull final Quantity stockQty,
			@JsonProperty("uomQty") @Nullable final Quantity uomQty)
	{
		this.productId = productId;
		this.stockQty = stockQty;
		this.uomQty = uomQty;
	}

	public StockQtyAndUOMQty add(@NonNull final StockQtyAndUOMQty other)
	{
		Check.assumeEquals(productId, other.productId, "The other instance's productId need to be equan to this instance's productId; this={}; other={}", this, other);

		final StockQtyAndUOMQtyBuilder result = StockQtyAndUOMQty.builder()
				.productId(productId)
				.stockQty(stockQty.add(other.getStockQty()));

		if (getUOMQtyOpt().isPresent())
		{
			Check.assume(other.getUOMQtyOpt().isPresent(), "If this instance's uomQty is present, then the other instance's uomQty also needs to be present; this={}; other={}", this, other);
			result.uomQty(uomQty.add(other.uomQty));
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

		if (getUOMQtyOpt().isPresent())
		{
			result.uomQty(uomQty.negate());
		}

		return result.build();
	}

	public StockQtyAndUOMQty subtract(@NonNull final StockQtyAndUOMQty other)
	{
		Check.assumeEquals(productId, other.productId, "The other instance's productId need to be equan to this instance's productId; this={}; other={}", this, other);

		final StockQtyAndUOMQtyBuilder result = StockQtyAndUOMQty.builder()
				.productId(productId)
				.stockQty(stockQty.subtract(other.getStockQty()));

		if (getUOMQtyOpt().isPresent())
		{
			Check.assume(other.getUOMQtyOpt().isPresent(), "If this instance's uomQty is present, then the other instance's uomQty also needs to be present; this={}; other={}", this, other);
			result.uomQty(uomQty.subtract(other.uomQty));
		}

		return result.build();
	}
}

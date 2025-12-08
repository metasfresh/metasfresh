package de.metas.handlingunits.storage;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

/**
 * {@link IProductStorage} expectation
 *
 * @author tsa
 *
 */
public class ProductStorageExpectation
{
	private BigDecimal qtyCapacity;
	private BigDecimal qty;
	private BigDecimal qtyFree;

	public ProductStorageExpectation()
	{
		super();
	}

	public ProductStorageExpectation assertExpected(final IProductStorage productStorage)
	{
		final String message = null;
		return assertExpected(message, productStorage);
	}

	public ProductStorageExpectation assertExpected(final String message, final IProductStorage productStorage)
	{
		final String prefix = (message == null ? "" : message)
				+ "\nProduct storage: " + productStorage
				+ "\n\nInvalid: ";

		assertThat(productStorage)
			.as(prefix + "productStorage is null")
			.isNotNull();

		if (qtyCapacity != null)
		{
			assertThat(productStorage.getQtyCapacity())
				.as(prefix + "QtyCapacity")
				.isEqualByComparingTo(qtyCapacity);
		}
		if (qty != null)
		{
			assertThat(productStorage.getQty().toBigDecimal())
				.as(prefix + "Qty")
				.isEqualByComparingTo(qty);
		}

		if (qtyFree != null)
		{
			assertThat(productStorage.getQtyFree())
				.as(prefix + "QtyFree")
				.isEqualByComparingTo(qtyFree);
		}
		else
		{
			assertThat(productStorage.getQtyFree())
				.as(prefix + "QtyFree = QtyCapacity - Qty")
				.isEqualByComparingTo(
					productStorage.getQtyCapacity().subtract(productStorage.getQty().toBigDecimal()));
		}

		return this;
	}

	public ProductStorageExpectation qtyCapacity(final BigDecimal qtyCapacity)
	{
		this.qtyCapacity = qtyCapacity;
		return this;
	}

	public ProductStorageExpectation qty(final BigDecimal qty)
	{
		this.qty = qty;
		return this;
	}

	public ProductStorageExpectation qtyFree(final BigDecimal qtyFree)
	{
		this.qtyFree = qtyFree;
		return this;
	}
}
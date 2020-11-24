package de.metas.quantity;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import org.compiere.model.I_C_UOM;

import de.metas.product.ProductId;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/**
 * Uom-based capacity definition for all sorts of containers.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@EqualsAndHashCode
public final class Capacity
{

	public static Capacity createInfiniteCapacity(
			@NonNull final ProductId productId,
			@NonNull final I_C_UOM uom)
	{
		return new Capacity(productId, uom);
	}

	public static Capacity createZeroCapacity(
			@NonNull final ProductId productId,
			@NonNull final I_C_UOM uom,
			final boolean allowNegativeCapacity)
	{
		return new Capacity(BigDecimal.ZERO, productId, uom, allowNegativeCapacity);
	}

	public static Capacity createCapacity(
			@NonNull final BigDecimal qty,
			@NonNull final ProductId productId,
			@NonNull final I_C_UOM uom,
			final boolean allowNegativeCapacity)
	{
		return new Capacity(qty, productId, uom, allowNegativeCapacity);
	}

	private final ProductId productId;
	private final I_C_UOM uom;
	private final BigDecimal capacity;

	private final boolean infiniteCapacity;
	private final boolean allowNegativeCapacity;

	/**
	 * Constructs a finite capacity definition
	 */
	private Capacity(
			@NonNull final BigDecimal capacity,
			@NonNull final ProductId productId,
			@NonNull final I_C_UOM uom,
			final boolean allowNegativeCapacity)
	{
		this.productId = productId;
		this.uom = uom;

		this.capacity = capacity;

		infiniteCapacity = false;
		this.allowNegativeCapacity = allowNegativeCapacity;
	}

	/**
	 * Constructs an infinite capacity definition
	 */
	private Capacity(@NonNull final ProductId productId, @NonNull final I_C_UOM uom)
	{
		this.productId = productId;
		this.uom = uom;

		capacity = null;

		infiniteCapacity = true;
		allowNegativeCapacity = true;
	}

	public boolean isInfiniteCapacity()
	{
		return infiniteCapacity;
	}

	public boolean isAllowNegativeCapacity()
	{
		Check.assume(!isInfiniteCapacity(), "Cannot retrieve if it's infinite for {}", this);
		return allowNegativeCapacity;
	}

	/**
	 * Will throw "Assumption Failure" if capacity is unlimited (should never be called without first checking with {@link #isInfiniteCapacity()} first)
	 *
	 * @return capacity
	 */
	public BigDecimal toBigDecimal()
	{
		Check.assume(!isInfiniteCapacity(), "Cannot retrieve capacity as BigDecimal if it's infinite; this={}", this);
		return capacity;
	}

	public Quantity toQuantity()
	{
		Check.assume(!isInfiniteCapacity(), "Cannot retrieve capacity Quantity if it's infinite; this={}", this);
		return Quantity.of(capacity, uom);
	}

	public Capacity convertToUOM(
			@NonNull final I_C_UOM uomTo,
			@NonNull final QuantityUOMConverter uomConverter)
	{
		if (uom.getC_UOM_ID() == uomTo.getC_UOM_ID())
		{
			return this;
		}
		else if (isInfiniteCapacity())
		{
			return createInfiniteCapacity(productId, uomTo);
		}
		else
		{
			final BigDecimal capacityConv = uomConverter.convertQty(productId, capacity, uom, uomTo);
			return createCapacity(capacityConv, productId, uomTo, allowNegativeCapacity);
		}
	}

	public Capacity subtractQuantity(
			@NonNull final Quantity quantity,
			@NonNull final QuantityUOMConverter uomConverter)
	{
		// If it's infinite capacity, there is nothing to adjust
		if (infiniteCapacity)
		{
			return this;
		}

		// Qty used is ZERO so there is nothing to adjust
		if (quantity.isZero())
		{
			return this;
		}

		final BigDecimal qtyUsedConv = uomConverter
				.convertQty(getProductId(),
						quantity.toBigDecimal(),
						quantity.getUOM(),
						uom);

		final BigDecimal capacityAvailable = capacity.subtract(qtyUsedConv);

		// We got negative capacity
		if (capacityAvailable.signum() <= 0)
		{
			final boolean mustCreateZeroCapacity =
					// Yes, if capacity definition does not allow negative capacity
					!allowNegativeCapacity
							// note: if *both* capacityAvailable and qtyUsed/qtyOsedConf we negative, it would not influence the sign of the resulting capacity
							&& qtyUsedConv.signum() > 0;
			if (mustCreateZeroCapacity)
			{
				return createZeroCapacity(productId,
						uom,
						allowNegativeCapacity);
			}
		}

		return createCapacity(capacityAvailable,
				productId,
				uom,
				allowNegativeCapacity);
	}

	/**
	 * Calculates how many capacities like <code>capacityDef</code> do we need to cover given <code>qty</code>
	 *
	 * <p>
	 * e.g. if Qty=13 and Capacity=10 then QtyPacks=2 (13/10 rounded up).
	 *
	 * @param qty
	 * @param targetUom quantity's unit of measure
	 * @param capacityDef
	 * @return how many capacities are required or NULL if capacity is not available
	 */
	public Optional<QuantityTU> calculateQtyTU(
			@NonNull final BigDecimal qty,
			@NonNull final I_C_UOM targetUom,
			@NonNull final QuantityUOMConverter uomConverter)
	{
		// Infinite capacity => one pack would be sufficient
		if (infiniteCapacity)
		{
			return Optional.of(QuantityTU.ONE);
		}

		// Qty is zero => zero packs
		if (qty.signum() == 0)
		{
			return Optional.of(QuantityTU.ZERO);
		}

		// Capacity is ZERO => N/A
		if (capacity.signum() <= 0)
		{
			return Optional.empty();
		}

		// Convert Qty to Capacity's UOM
		final BigDecimal qtyConv = uomConverter.convertQty(productId, qty, uom, targetUom);

		final int qtyTUs = qtyConv.divide(capacity, 0, RoundingMode.UP).intValueExact();
		return Optional.of(QuantityTU.ofInt(qtyTUs));
	}

	public Capacity multiply(final int multiplier)
	{
		Check.assume(multiplier >= 0, "multiplier = {} needs to be 0", multiplier);

		// If capacity is infinite, there is no point to multiply it
		if (infiniteCapacity)
		{
			return this;
		}

		final BigDecimal capacityNew = capacity.multiply(BigDecimal.valueOf(multiplier));

		return createCapacity(
				capacityNew,
				productId,
				uom,
				allowNegativeCapacity);
	}

	public ProductId getProductId()
	{
		return productId;
	}

	public I_C_UOM getC_UOM()
	{
		return uom;
	}

	@Override
	public String toString()
	{
		return "CapacityImpl ["
				+ "infiniteCapacity=" + infiniteCapacity
				+ ", capacity(qty)=" + capacity
				+ ", product=" + productId
				+ ", uom=" + (uom == null ? "null" : uom.getUOMSymbol())
				+ ", allowNegativeCapacity=" + allowNegativeCapacity
				+ "]";
	}
}

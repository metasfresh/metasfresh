package de.metas.quantity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.uom.UOMPrecision;
import de.metas.uom.UOMType;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.stream.Stream;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;

/**
 * Immutable Quantity.
 * <p>
 * Besides quantity value ({@link #getQty()}) and it's uom ({@link #getUOM()} this object contains also source quantity/uom.
 * <p>
 * The actual meaning of source quantity/uom depends on who constructs the {@link Quantity} object but the general meaning is: the quantity/uom in some source or internal UOM.
 * <p>
 * e.g. when you ask a storage to allocate a quantity/uom that method could return a quantity object containing how much was allocated (in requested UOM), but the source quantity/uom is the quantity
 * in storage's UOM.
 *
 * @author tsa
 */
@JsonDeserialize(using = QuantityDeserializer.class)
@JsonSerialize(using = QuantitySerializer.class)
public final class Quantity implements Comparable<Quantity>
{
	/**
	 * To create an instance an {@link UomId} instead of {@link I_C_UOM}, use {@link Quantitys#of(BigDecimal, UomId)}.
	 */
	public static Quantity of(@NonNull final String qty, @NonNull final I_C_UOM uomRecord)
	{
		return of(new BigDecimal(qty), uomRecord);
	}

	/**
	 * To create an instance an {@link UomId} instead of {@link I_C_UOM}, use {@link Quantitys#of(BigDecimal, UomId)}.
	 */
	public static Quantity of(@NonNull final BigDecimal qty, @NonNull final I_C_UOM uomRecord)
	{
		return new Quantity(qty, uomRecord);
	}

	@Nullable
	public static Quantity ofNullable(@Nullable final BigDecimal qty, @Nullable final I_C_UOM uom)
	{
		if (qty == null || uom == null)
		{
			return null;
		}
		return of(qty, uom);
	}

	/**
	 * To create an instance an {@link UomId} instead of {@link I_C_UOM}, use {@link Quantitys#of(BigDecimal, UomId)}.
	 */
	public static Quantity of(final int qty, @NonNull final I_C_UOM uomRecord)
	{
		return of(BigDecimal.valueOf(qty), uomRecord);
	}

	public static boolean isInfinite(final BigDecimal qty)
	{
		return QTY_INFINITE.compareTo(qty) == 0;
	}

	public static Quantity addNullables(@Nullable final Quantity qty1, @Nullable final Quantity qty2)
	{
		if (qty1 == null)
		{
			return qty2;
		}
		else if (qty2 == null)
		{
			return qty1;
		}
		else
		{
			return qty1.add(qty2);
		}
	}

	public static Quantity addToNullable(
			@Nullable final Quantity quantity,
			@NonNull final BigDecimal augent,
			@NonNull final I_C_UOM augentUOM)
	{
		final Quantity augentQuantity = new Quantity(augent, augentUOM);
		if (quantity == null)
		{
			return augentQuantity;
		}
		return addNullables(quantity, augentQuantity);
	}

	public static BigDecimal toBigDecimal(@Nullable final Quantity quantity)
	{
		if (quantity == null)
		{
			return ZERO;
		}
		return quantity.toBigDecimal();
	}

	public static int toUomRepoId(@Nullable final Quantity quantity)
	{
		if (quantity == null)
		{
			return -1;
		}
		return quantity.getUOM().getC_UOM_ID();
	}

	public static UomId getCommonUomIdOfAll(final Quantity... quantities)
	{
		return UomId.getCommonUomIdOfAll(Quantity::getUomId, "quantity", quantities);
	}

	public static void assertSameUOM(@Nullable final Quantity... quantities)
	{
		if (quantities == null || quantities.length <= 0)
		{
			return;
		}

		final ImmutableListMultimap<UomId, Quantity> qtysByUOM = Stream.of(quantities)
				.filter(Objects::nonNull)
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						Quantity::getUomId,
						qty -> qty));

		// no quantities it's OK
		if (qtysByUOM.isEmpty())
		{
			return;
		}

		final ImmutableSet<UomId> uomIds = qtysByUOM.keySet();
		if (uomIds.size() > 1)
		{
			throw new AdempiereException("at least two quantity instances have different UOMs: " + qtysByUOM);
		}
	}

	public static final BigDecimal QTY_INFINITE = BigDecimal.valueOf(Long.MAX_VALUE); // NOTE: we need a new instance to make sure it's unique

	// NOTE to dev: all fields shall be final because this is a immutable object. Please keep that logic if u are adding more fields
	private final BigDecimal qty;

	@JsonIgnore // TODO: better map to the uom' X12DE355 code or similar
	private final I_C_UOM uom;

	private final BigDecimal sourceQty;

	@JsonIgnore // TODO: better map to the uom' X12DE355 code or similar
	private final I_C_UOM sourceUom;

	/**
	 * Constructs a quantity object without source quantity/uom. More preciselly, source quantity/uom will be set so same values as quantity/uom.
	 */
	public Quantity(@NonNull final BigDecimal qty, @NonNull final I_C_UOM uom)
	{
		this(qty, uom, qty, uom);
	}

	public Quantity(
			@NonNull final BigDecimal qty,
			@NonNull final I_C_UOM uom,
			@NonNull final BigDecimal sourceQty,
			@NonNull final I_C_UOM sourceUOM)
	{
		this.qty = qty;
		this.uom = uom;
		this.sourceQty = sourceQty;
		this.sourceUom = sourceUOM;
	}

	@Override
	public String toString()
	{
		if (uom.getC_UOM_ID() == sourceUom.getC_UOM_ID()
				&& qty.compareTo(sourceQty) == 0)
		{
			return qty + " " + uom.getUOMSymbol();
		}
		else
		{
			return qty + " " + uom.getUOMSymbol() + " (source: " + sourceQty + " " + sourceUom.getUOMSymbol() + ")";
		}
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(qty)
				.append(uom.getC_UOM_ID())
				.append(sourceQty)
				.append(sourceUom.getC_UOM_ID())
				.toHashcode();
	}

	@Override
	@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final Quantity other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(this.qty, other.qty)
				.append(this.uom.getC_UOM_ID(), other.uom.getC_UOM_ID())
				.append(this.sourceQty, other.sourceQty)
				.append(this.sourceUom.getC_UOM_ID(), other.sourceUom.getC_UOM_ID())
				.isEqual();
	}

	/**
	 * Checks if <code>this</code> quantity equals with <code>other</code>, not considering the source (i.e. {@link #getSourceQty()}, {@link #getSourceUOM()}).
	 *
	 * @return true if they are equal
	 */
	public boolean equalsIgnoreSource(final Quantity other)
	{
		if (this == other)
		{
			return true;
		}
		if (other == null)
		{
			return false;
		}
		return new EqualsBuilder()
				.append(this.qty, other.qty)
				.append(this.uom.getC_UOM_ID(), other.uom.getC_UOM_ID())
				.isEqual();
	}

	/**
	 * Checks if this quantity and given quantity are equal when comparing the current values (i.e. {@link #getQty()}, {@link #getUOM()}).
	 * <p>
	 * NOTE: quantities will be compared by using {@link BigDecimal#compareTo(BigDecimal)} instead of {@link BigDecimal#equals(Object)}.
	 *
	 * @return true if current Qty/UOM are comparable equal.
	 */
	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean qtyAndUomCompareToEquals(@Nullable final Quantity quantity)
	{
		if (this == quantity)
		{
			return true;
		}
		if (quantity == null)
		{
			return false;
		}

		if (this.getQty().compareTo(quantity.getQty()) != 0)
		{
			return false;
		}

		return this.getUOMId() == quantity.getUOMId();
	}

	/**
	 * @return Quantity value; never return null
	 */
	public BigDecimal toBigDecimal()
	{
		return qty;
	}

	/**
	 * @deprecated Please use {@link #toBigDecimal()}
	 */
	@Deprecated
	public BigDecimal getQty()
	{
		return toBigDecimal();
	}

	/**
	 * @return true if quantity value is infinite
	 */
	public boolean isInfinite()
	{
		return isInfinite(qty);
	}

	/**
	 * @return quantity's UOM; never return null
	 */
	public I_C_UOM getUOM()
	{
		return uom;
	}

	/**
	 * @return quantity's C_UOM_ID
	 */
	@Deprecated
	public int getUOMId()
	{
		return uom.getC_UOM_ID();
	}

	public UomId getUomId()
	{
		return UomId.ofRepoId(uom.getC_UOM_ID());
	}

	public String getUOMSymbol()
	{
		return uom.getUOMSymbol();
	}

	public X12DE355 getX12DE355()
	{
		return X12DE355.ofCode(uom.getX12DE355());
	}

	/**
	 * @return source quantity; never null
	 */
	public BigDecimal getSourceQty()
	{
		return sourceQty;
	}

	/**
	 * @return source quatity's UOM
	 */
	public I_C_UOM getSourceUOM()
	{
		return sourceUom;
	}

	/**
	 * @return source quatity's C_UOM_ID
	 */
	@Deprecated
	public int getSource_UOM_ID()
	{
		return sourceUom.getC_UOM_ID();
	}

	public UomId getSourceUomId()
	{
		return UomId.ofRepoId(sourceUom.getC_UOM_ID());
	}

	/**
	 * If you don't have a {@link I_C_UOM} record, but an {@link UomId}, consider using {@link Quantitys#zero(UomId)}.
	 *
	 * @return ZERO quantity (using given UOM)
	 */
	public static Quantity zero(final I_C_UOM uom)
	{
		return new Quantity(ZERO, uom, ZERO, uom);
	}

	/**
	 * @return ZERO quantity (but preserve UOMs)
	 */
	public Quantity toZero()
	{
		if (qty.signum() == 0 && sourceQty.signum() == 0)
		{
			return this;
		}
		return new Quantity(ZERO, uom, ZERO, sourceUom);
	}

	public Quantity toOne()
	{
		if (ONE.compareTo(qty) == 0)
		{
			return this;
		}
		return new Quantity(ONE, uom);
	}

	public Quantity toZeroIfNegative()
	{
		return qty.signum() >= 0 ? this : toZero();
	}

	/**
	 * @return infinite quantity (using given UOM)
	 */
	public static Quantity infinite(final I_C_UOM uom)
	{
		return new Quantity(QTY_INFINITE, uom, QTY_INFINITE, uom);
	}

	/**
	 * @return infinite quantity (but preserve UOMs)
	 */
	public Quantity toInfinite()
	{
		if (isInfinite())
		{
			return this;
		}
		return new Quantity(QTY_INFINITE, uom, QTY_INFINITE, sourceUom);
	}

	public Quantity abs()
	{
		return signum() >= 0 ? this : negate();
	}

	public Quantity negate()
	{
		if (isZero())
		{
			return this;
		}

		return new Quantity(qty.negate(), uom, sourceQty.negate(), sourceUom);
	}

	/**
	 * @return negated quantity if <code>condition</code> is true; else return this
	 */
	public Quantity negateIf(final boolean condition)
	{
		return condition ? negate() : this;
	}

	public Quantity negateIfNot(final boolean condition)
	{
		return !condition ? negate() : this;
	}

	/**
	 * Calculates the Weighted Average between this Quantity and a previous given average.
	 * <p>
	 * i.e. Current Weighted Avg = (<code>previousAverage</code> * <code>previousAverageWeight</code> + this quantity) / (<code>previousAverageWeight</code> + 1)
	 *
	 * @return weighted average
	 */
	public Quantity weightedAverage(@NonNull final BigDecimal previousAverage, final int previousAverageWeight)
	{
		Check.assume(previousAverageWeight >= 0, "previousAverageWeight >= 0");

		final BigDecimal previousAverageWeightBD = BigDecimal.valueOf(previousAverageWeight);
		final BigDecimal count = BigDecimal.valueOf(previousAverageWeight + 1);
		final int precision = getUOM().getStdPrecision();

		final BigDecimal currentQty = getQty();
		final BigDecimal currentWeightAverage = previousAverage
				.multiply(previousAverageWeightBD)
				.add(currentQty)
				.divide(count, precision, RoundingMode.HALF_UP);

		return new Quantity(currentWeightAverage, getUOM());
	}

	/**
	 * Interchange current Qty/UOM with source Qty/UOM.
	 * <p>
	 * e.g. If your quantity is "5kg (source: 5000g)" then this method will return "5000g (source: 5kg)"
	 *
	 * @return a new instance with current Qty/UOM and source Qty/UOM interchanged.
	 */
	public Quantity switchToSource()
	{
		return new Quantity(getSourceQty(), getSourceUOM(), toBigDecimal(), getUOM());
	}

	/**
	 * Interchange the Qty/UOM with source Qty/UOM if the source is more precise.
	 * Source is considered more precise if it's numeric value is bigger.
	 * <p>
	 * This method is usually used before persisting to database where we want to persist the most precise amount,
	 * because else, when we will load it back we won't get the same figures.
	 */
	public Quantity switchToSourceIfMorePrecise()
	{
		if (getSourceQty().compareTo(toBigDecimal()) > 0)
		{
			return switchToSource();
		}
		else
		{
			return this;
		}
	}

	public Quantity withoutSource()
	{
		if (uom.getC_UOM_ID() == sourceUom.getC_UOM_ID()
				&& qty.compareTo(sourceQty) == 0)
		{
			return this;
		}
		else
		{
			return new Quantity(qty, uom);
		}
	}

	/**
	 * Returns the signum function of current quantity (i.e. {@link #getQty()} ).
	 *
	 * @return -1, 0, or 1 as the value of current quantity is negative, zero, or positive.
	 */
	public int signum()
	{
		return toBigDecimal().signum();
	}

	/**
	 * Checks if current quantity zero.
	 * <p>
	 * This is just a convenient method for checking if the {@link #signum()} is zero.
	 *
	 * @return true if {@link #signum()} is zero.
	 */
	public boolean isZero()
	{
		return signum() == 0;
	}

	public boolean isOne()
	{
		return ONE.compareTo(qty) == 0;
	}

	public boolean isPositive()
	{
		return signum() > 0;
	}

	/**
	 * Adds given quantity and returns the result.
	 * Assumes that the UOMs are equal.
	 * <p>
	 * Note: {@link Quantitys#add(de.metas.uom.UOMConversionContext, Quantity, Quantity)} adds by converting quantities between UOMs
	 *
	 * @throws QuantitiesUOMNotMatchingExpection if this quantity and qtyToAdd are not UOM compatible
	 *                                           To add instances with different UOMs, use {@link Quantitys#add(de.metas.uom.UOMConversionContext, Quantity, Quantity)}.
	 */
	public Quantity add(@NonNull final Quantity qtyToAdd)
	{
		if (qtyToAdd.isZero())
		{
			return this;
		}

		// Get QtyToAdd value (mandatory)
		final BigDecimal qtyToAdd_Value;
		final int uomId = this.getUOMId();
		final int qtyToAdd_uomId = qtyToAdd.getUOMId();
		final int qtyToAdd_sourceUomId = qtyToAdd.getSource_UOM_ID();
		if (uomId == qtyToAdd_uomId)
		{
			qtyToAdd_Value = qtyToAdd.toBigDecimal();
		}
		else if (uomId == qtyToAdd_sourceUomId)
		{
			qtyToAdd_Value = qtyToAdd.getSourceQty();
		}
		else
		{
			throw new QuantitiesUOMNotMatchingExpection("Cannot add " + qtyToAdd + " to " + this + " because UOMs are not compatible");
		}

		//
		// Get QtyToAdd source value
		final BigDecimal qtyToAdd_SourceValue;
		final int sourceUomId = this.getSource_UOM_ID();
		if (sourceUomId == qtyToAdd_sourceUomId)
		{
			qtyToAdd_SourceValue = qtyToAdd.getSourceQty();
		}
		else if (sourceUomId == qtyToAdd_uomId)
		{
			qtyToAdd_SourceValue = qtyToAdd.toBigDecimal();
		}
		else
		{
			// Source UOM does not match. We can skip it
			qtyToAdd_SourceValue = null;
		}

		//
		// Compute new Quantity's values
		final BigDecimal qtyNew_Value = this.toBigDecimal().add(qtyToAdd_Value);
		final I_C_UOM qtyNew_UOM = this.getUOM();
		final BigDecimal qtyNew_SourceValue;
		final I_C_UOM qtyNew_SourceUOM;
		if (qtyToAdd_SourceValue == null)
		{
			qtyNew_SourceValue = qtyNew_Value;
			qtyNew_SourceUOM = qtyNew_UOM;
		}
		else
		{
			qtyNew_SourceValue = this.getSourceQty().add(qtyToAdd_SourceValue);
			qtyNew_SourceUOM = this.getSourceUOM();
		}

		//
		// Create the new quantity and return it
		return new Quantity(qtyNew_Value, qtyNew_UOM, qtyNew_SourceValue, qtyNew_SourceUOM);
	}

	public Quantity add(@NonNull final BigDecimal qtyToAdd)
	{
		if (qtyToAdd.signum() == 0)
		{
			return this;
		}
		return add(of(qtyToAdd, uom));
	}

	public Quantity add(@NonNull final Percent percent)
	{
		if (percent.isZero())
		{
			return this;
		}

		return new Quantity(
				percent.addToBase(this.qty, this.uom.getStdPrecision()),
				this.uom,
				percent.addToBase(this.sourceQty, this.sourceUom.getStdPrecision()),
				this.sourceUom);
	}

	public Quantity subtract(@NonNull final Percent percent)
	{
		if (percent.isZero())
		{
			return this;
		}

		return new Quantity(
				percent.subtractFromBase(this.qty, this.uom.getStdPrecision()),
				this.uom,
				percent.subtractFromBase(this.sourceQty, this.sourceUom.getStdPrecision()),
				this.sourceUom);
	}

	public Quantity subtract(@NonNull final Quantity qtyToSubtract)
	{
		if (qtyToSubtract.isZero())
		{
			return this;
		}
		final Quantity qtyToAdd = qtyToSubtract.negate();
		return add(qtyToAdd);
	}

	public Quantity subtract(@NonNull final BigDecimal qtyToSubtract)
	{
		if (qtyToSubtract.signum() == 0)
		{
			return this;
		}
		return add(qtyToSubtract.negate());
	}

	/**
	 * @return minimum of <code>this</code> and <code>qtyToCompare</code>
	 */
	public Quantity min(@NonNull final Quantity qtyToCompare)
	{
		if (this.compareTo(qtyToCompare) <= 0)
		{
			return this;
		}
		else
		{
			return qtyToCompare;
		}
	}

	/**
	 * @return maximum of <code>this</code> and <code>qtyToCompare</code>.
	 */
	public Quantity max(@NonNull final Quantity qtyToCompare)
	{
		if (this.compareTo(qtyToCompare) >= 0)
		{
			return this;
		}
		else
		{
			return qtyToCompare;
		}
	}

	@Override
	public int compareTo(@NonNull final Quantity quantity)
	{
		final Quantity diff = this.subtract(quantity);
		return diff.signum();
	}

	public boolean isGreaterThan(@NonNull final Quantity other)
	{
		return this.compareTo(other) > 0;
	}

	public Quantity divide(@NonNull final BigDecimal divisor)
	{
		if (BigDecimal.ONE.compareTo(divisor) == 0)
		{
			return this;
		}

		return new Quantity(
				qty.divide(divisor, uom.getStdPrecision(), RoundingMode.HALF_UP),
				uom,
				sourceQty.divide(divisor, sourceUom.getStdPrecision(), RoundingMode.HALF_UP),
				sourceUom);
	}

	public Quantity divide(
			@NonNull final BigDecimal divisor,
			final int scale,
			@NonNull final RoundingMode roundingMode)
	{
		return new Quantity(
				qty.divide(divisor, scale, roundingMode),
				uom,
				sourceQty.divide(divisor, scale, roundingMode),
				sourceUom);
	}

	public Quantity multiply(final int multiplicand)
	{
		return multiply(BigDecimal.valueOf(multiplicand));
	}

	public Quantity multiply(final BigDecimal multiplicand)
	{
		if (multiplicand.compareTo(ONE) == 0)
		{
			return this;
		}

		return new Quantity(qty.multiply(multiplicand), uom, sourceQty.multiply(multiplicand), sourceUom);
	}

	public Quantity multiply(@NonNull final Percent percent)
	{
		final UOMPrecision precision = getUOMPrecision();
		return multiply(percent, precision.toInt(), precision.getRoundingMode());
	}

	public Quantity multiply(@NonNull final Percent percent, @NonNull final RoundingMode roundingMode)
	{
		final UOMPrecision precision = getUOMPrecision();
		return multiply(percent, precision.toInt(), roundingMode);
	}

	private Quantity multiply(
			@NonNull final Percent percent,
			final int precision,
			@NonNull RoundingMode roundingMode)
	{
		final BigDecimal newQty = percent.computePercentageOf(this.qty, precision, roundingMode);

		return this.qty.compareTo(newQty) != 0
				? new Quantity(newQty, uom)
				: this;
	}

	public Quantity roundToUOMPrecision()
	{
		final UOMPrecision precision = getUOMPrecision();
		final BigDecimal qtyRounted = precision.roundIfNeeded(qty);
		return qty.equals(qtyRounted)
				? this
				: new Quantity(qtyRounted, uom, sourceQty, sourceUom);
	}

	private UOMPrecision getUOMPrecision()
	{
		return UOMPrecision.ofInt(uom.getStdPrecision());
	}

	public Quantity setScale(final UOMPrecision newScale, @NonNull final RoundingMode roundingMode)
	{
		final BigDecimal newQty = qty.setScale(newScale.toInt(), roundingMode);

		return new Quantity(
				newQty,
				uom,
				sourceQty != null ? sourceQty.setScale(newScale.toInt(), roundingMode) : newQty,
				sourceUom != null ? sourceUom : uom);
	}

	public int intValueExact()
	{
		return toBigDecimal().intValueExact();
	}

	public boolean isWeightable()
	{
		return UOMType.ofNullableCodeOrOther(uom.getUOMType()).isWeight();
	}

	public Percent percentageOf(@NonNull final Quantity whole)
	{
		assertSameUOM(this, whole);
		return Percent.of(toBigDecimal(), whole.toBigDecimal());
	}
	
	private void assertUOMOrSourceUOM(@NonNull final UomId uomId)
	{
		if (!getUomId().equals(uomId) && !getSourceUomId().equals(uomId))
		{
			throw new QuantitiesUOMNotMatchingExpection("UOMs are not compatible")
					.appendParametersToMessage()
					.setParameter("Qty.UOM", getUomId())
					.setParameter("assertUOM", uomId);
		}
	}
	
	@NonNull
	public BigDecimal toBigDecimalAssumingUOM(@NonNull final UomId uomId)
	{
		assertUOMOrSourceUOM(uomId);
		
		return getUomId().equals(uomId) ? toBigDecimal() : getSourceQty();
	}
}

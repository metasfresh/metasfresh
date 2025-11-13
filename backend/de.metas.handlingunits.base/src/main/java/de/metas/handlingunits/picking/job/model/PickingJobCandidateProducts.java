package de.metas.handlingunits.picking.job.model;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.i18n.ITranslatableString;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.GuavaCollectors;
import de.metas.util.OptionalBoolean;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;

@EqualsAndHashCode
@ToString(of = "byProductId")
public class PickingJobCandidateProducts implements Iterable<PickingJobCandidateProduct>
{
	@NonNull private final ImmutableMap<ProductId, PickingJobCandidateProduct> byProductId;
	@Nullable private final PickingJobCandidateProduct singleProduct;
	@Nullable private OptionalBoolean _hasQtyAvailableToPick = null; // lazy

	private PickingJobCandidateProducts(@NonNull final ImmutableMap<ProductId, PickingJobCandidateProduct> byProductId)
	{
		this.byProductId = byProductId;
		this.singleProduct = byProductId.size() == 1
				? byProductId.values().iterator().next()
				: null;

	}

	public static PickingJobCandidateProducts newInstance() {return new PickingJobCandidateProducts(ImmutableMap.of());}

	public static PickingJobCandidateProducts ofList(final List<PickingJobCandidateProduct> list)
	{
		return new PickingJobCandidateProducts(Maps.uniqueIndex(list, PickingJobCandidateProduct::getProductId));
	}

	public static PickingJobCandidateProducts of(@NonNull final PickingJobCandidateProduct product)
	{
		return new PickingJobCandidateProducts(ImmutableMap.of(product.getProductId(), product));
	}

	public static Collector<PickingJobCandidateProduct, ?, PickingJobCandidateProducts> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(PickingJobCandidateProducts::ofList);
	}

	public Set<ProductId> getProductIds() {return byProductId.keySet();}

	@Override
	@NonNull
	public Iterator<PickingJobCandidateProduct> iterator() {return byProductId.values().iterator();}

	public OptionalBoolean hasQtyAvailableToPick()
	{
		OptionalBoolean hasQtyAvailableToPick = this._hasQtyAvailableToPick;
		if (_hasQtyAvailableToPick == null)
		{
			hasQtyAvailableToPick = this._hasQtyAvailableToPick = computeHasQtyAvailableToPick();
		}
		return hasQtyAvailableToPick;
	}

	private @NotNull OptionalBoolean computeHasQtyAvailableToPick()
	{
		if (byProductId.isEmpty())
		{
			return OptionalBoolean.UNKNOWN;
		}

		boolean hasFalse = false;
		boolean hasUnknown = false;

		for (PickingJobCandidateProduct product : byProductId.values())
		{
			final OptionalBoolean result = product.hasQtyAvailableToPick();
			switch (result)
			{
				case TRUE:
					return OptionalBoolean.TRUE;
				case FALSE:
					hasFalse = true;
					break;
				case UNKNOWN:
					hasUnknown = true;
					break;
			}
		}

		// If we only have FALSE values, return FALSE
		if (hasFalse && !hasUnknown)
		{
			return OptionalBoolean.FALSE;
		}

		// Otherwise, return UNKNOWN
		return OptionalBoolean.UNKNOWN;
	}

	public PickingJobCandidateProducts updatingEachProduct(@NonNull UnaryOperator<PickingJobCandidateProduct> updater)
	{
		if (byProductId.isEmpty())
		{
			return this;
		}

		final ImmutableMap<ProductId, PickingJobCandidateProduct> byProductIdNew = byProductId.values()
				.stream()
				.map(updater)
				.collect(ImmutableMap.toImmutableMap(PickingJobCandidateProduct::getProductId, product -> product));

		return Objects.equals(this.byProductId, byProductIdNew)
				? this
				: new PickingJobCandidateProducts(byProductIdNew);
	}

	@Nullable
	public ProductId getSingleProductIdOrNull()
	{
		return singleProduct != null ? singleProduct.getProductId() : null;
	}

	@Nullable
	public Quantity getSingleQtyToDeliverOrNull()
	{
		return singleProduct != null ? singleProduct.getQtyToDeliver() : null;
	}

	@Nullable
	public Quantity getSingleQtyAvailableToPickOrNull()
	{
		return singleProduct != null ? singleProduct.getQtyAvailableToPick() : null;
	}

	@Nullable
	public ITranslatableString getSingleProductNameOrNull()
	{
		return singleProduct != null ? singleProduct.getProductName() : null;
	}
}
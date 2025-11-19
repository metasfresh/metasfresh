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

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;

@EqualsAndHashCode(doNotUseGetters = true)
@ToString(of = "byProductId")
@SuppressWarnings({ "OptionalUsedAsFieldOrParameterType", "OptionalAssignedToNull" })
public class PickingJobCandidateProducts implements Iterable<PickingJobCandidateProduct>
{
	@NonNull private final ImmutableMap<ProductId, PickingJobCandidateProduct> byProductId;
	@Nullable private final PickingJobCandidateProduct singleProduct;
	@Nullable private Optional<QtyAvailableStatus> _qtyAvailableStatus = null; // lazy

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
		final QtyAvailableStatus qtyAvailableStatus = getQtyAvailableStatus().orElse(null);
		return qtyAvailableStatus == null
				? OptionalBoolean.UNKNOWN
				: OptionalBoolean.ofBoolean(qtyAvailableStatus.isPartialOrFullyAvailable());
	}

	public Optional<QtyAvailableStatus> getQtyAvailableStatus()
	{
		Optional<QtyAvailableStatus> qtyAvailableStatus = this._qtyAvailableStatus;
		//noinspection OptionalAssignedToNull
		if (qtyAvailableStatus == null)
		{
			qtyAvailableStatus = this._qtyAvailableStatus = computeQtyAvailableStatus();
		}
		return qtyAvailableStatus;
	}

	private Optional<QtyAvailableStatus> computeQtyAvailableStatus()
	{
		return QtyAvailableStatus.computeOfLines(byProductId.values(), product -> product.getQtyAvailableStatus().orElse(null));
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
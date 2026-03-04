package de.metas.inoutcandidate.qty_reservation;

import com.google.common.collect.ImmutableList;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_QtyReservation;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.function.UnaryOperator;

/**
 * Repository for loading {@link QtyReservation} domain objects from the {@code M_QtyReservation} table.
 */
@Repository
public class QtyReservationRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Load all active, unprocessed reservations (unfulfilled qty) for the given product IDs.
	 * <p>
	 * Returns ALL such reservations for these products, not just those for specific order lines.
	 * This ensures that reservations held by order lines outside a particular batch are also accounted for.
	 */
	@NonNull
	public ImmutableList<QtyReservation> getActiveByProductIds(@NonNull final Set<ProductId> productIds)
	{
		if (productIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL.createQueryBuilder(I_M_QtyReservation.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_QtyReservation.COLUMNNAME_M_Product_ID, productIds)
				.addEqualsFilter(I_M_QtyReservation.COLUMNNAME_Processed, false)
				.create()
				.stream()
				.map(QtyReservationLoaderAndSaver::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Load all active reservation records for the given order line IDs,
	 * apply the {@code updater} to each domain object, and batch-save changed records.
	 * <p>
	 * Records are processed in priority order: OH (On Hand) before PS (Planned Supply).
	 */
	public void updateByOrderLineIds(
			@NonNull final Set<OrderLineId> orderLineIds,
			@NonNull final UnaryOperator<QtyReservation> updater)
	{
		final QtyReservationLoaderAndSaver loaderAndSaver = new QtyReservationLoaderAndSaver();
		loaderAndSaver.updateByOrderLineIds(orderLineIds, updater);
	}
}

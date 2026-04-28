package de.metas.inventory;

import com.google.common.collect.ImmutableSet;
import de.metas.document.engine.DocStatus;
import de.metas.user.UserId;
import de.metas.util.InSetPredicate;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;

@Value
@Builder
public class InventoryQuery
{
	@NonNull @Default QueryLimit limit = QueryLimit.ONE_THOUSAND;
	@NonNull @Default InSetPredicate<UserId> onlyResponsibleIds = InSetPredicate.any();
	@NonNull @Default InSetPredicate<WarehouseId> onlyWarehouseIds = InSetPredicate.any();
	@NonNull @Default InSetPredicate<DocStatus> onlyDocStatuses = InSetPredicate.any();
	@NonNull @Singular ImmutableSet<InventoryId> excludeInventoryIds;

	@SuppressWarnings("unused")
	public static class InventoryQueryBuilder
	{
		public InventoryQueryBuilder noResponsibleId()
		{
			return onlyResponsibleIds(InSetPredicate.onlyNull());
		}

		public InventoryQueryBuilder onlyResponsibleId(@NonNull final UserId responsibleId)
		{
			return onlyResponsibleIds(InSetPredicate.only(responsibleId));
		}

		public InventoryQueryBuilder onlyWarehouseIdOrAny(@Nullable final WarehouseId warehouseId)
		{
			return onlyWarehouseIds(InSetPredicate.onlyOrAny(warehouseId));
		}

		public InventoryQueryBuilder onlyDraftOrInProgress()
		{
			return onlyDocStatuses(InSetPredicate.only(DocStatus.Drafted, DocStatus.InProgress));
		}

		public <T> InventoryQueryBuilder excludeInventoryIdsOf(@Nullable Collection<T> collection, @NonNull final Function<T, InventoryId> inventoryIdFunction)
		{
			if (collection == null || collection.isEmpty()) {return this;}

			final ImmutableSet<InventoryId> inventoryIds = collection.stream()
					.filter(Objects::nonNull)
					.map(inventoryIdFunction)
					.filter(Objects::nonNull)
					.collect(ImmutableSet.toImmutableSet());

			return excludeInventoryIds(inventoryIds);
		}

	}
}

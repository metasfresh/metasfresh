package de.metas.cucumber.stepdefs.costing;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.costing.CostDetail;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.methods.CostAmountType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@EqualsAndHashCode
public class CostDetailRecords
{
	@NonNull private final ImmutableList<CostDetail> list;
	@NonNull private final CostDetailToTabularStringConverter tabularStringConverter;
	@Nullable private final List<String> columnNamesInOrder;

	@NonNull private final ImmutableMap<CostDetailKey, CostDetail> byKey;

	@Builder
	private CostDetailRecords(
			@NonNull final List<CostDetail> list,
			@NonNull final CostDetailToTabularStringConverter tabularStringConverter,
			@Nullable final List<String> columnNamesInOrder)
	{
		this.list = ImmutableList.copyOf(list);
		this.byKey = Maps.uniqueIndex(list, CostDetailKey::of);
		this.tabularStringConverter = tabularStringConverter;
		this.columnNamesInOrder = columnNamesInOrder != null && !columnNamesInOrder.isEmpty() ? columnNamesInOrder : null;
	}

	@Override
	public String toString() {return tabularStringConverter.toTabular(list, 1, columnNamesInOrder).toTabularString();}

	public int size()
	{
		return list.size();
	}

	public Optional<CostDetail> getBy(@NonNull final CostingDocumentRef documentRef, @NonNull final CostAmountType amtType)
	{
		final CostDetailKey key = CostDetailKey.of(documentRef, amtType);
		return Optional.ofNullable(byKey.get(key));
	}

	@Value(staticConstructor = "of")
	private static class CostDetailKey
	{
		@NonNull CostingDocumentRef documentRef;
		@NonNull CostAmountType amtType;

		public static CostDetailKey of(CostDetail costDetail)
		{
			return of(costDetail.getDocumentRef(), costDetail.getAmtType());
		}
	}
}

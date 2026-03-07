package de.metas.cucumber.stepdefs;

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.util.GuavaCollectors;
import io.cucumber.datatable.DataTable;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.function.ThrowingConsumer;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public class DataTableRows
{
	@NonNull private final ImmutableList<DataTableRow> list;

	private DataTableRows(@NonNull final List<DataTableRow> list)
	{
		this.list = ImmutableList.copyOf(list);
	}

	public static DataTableRows of(@NonNull final DataTable dataTable)
	{
		return ofListOfMaps(dataTable.asMaps());
	}

	public static DataTableRows of(@NonNull final DataTableRow row)
	{
		return new DataTableRows(ImmutableList.of(row));
	}

	public static DataTableRows ofListOfMaps(@NonNull final List<Map<String, String>> list)
	{
		final AtomicInteger nextLineNo = new AtomicInteger(1);
		return list
				.stream()
				.map(values -> DataTableRow.builder()
						.lineNo(nextLineNo.getAndIncrement())
						.values(values)
						.build())
				.collect(GuavaCollectors.collectUsingListAccumulator(DataTableRows::new));
	}

	public static DataTableRows ofRows(@NonNull final Collection<DataTableRow> rows)
	{
		return new DataTableRows(ImmutableList.copyOf(rows));
	}

	public DataTableRows setAdditionalRowIdentifierColumnName(@NonNull final String columnName)
	{
		list.forEach(row -> row.setAdditionalRowIdentifierColumnName(columnName));
		return this;
	}

	public int size() {return list.size();}

	public @NonNull ImmutableList<DataTableRow> toList() {return list;}

	public Stream<DataTableRow> stream() {return list.stream();}

	public DataTableRow singleRow()
	{
		if (list.size() == 1)
		{
			return list.get(0);
		}
		else
		{
			throw new AdempiereException("Expected single row but have: " + list);
		}
	}

	public DataTableRow getFirstRow() {return list.get(0);}

	public void forEach(final ThrowingConsumer<? super DataTableRow> consumer)
	{
		forEach((row, index) -> consumer.accept(row));
	}

	@FunctionalInterface
	public interface ThrowingBiConsumer<T, U>
	{
		void accept(T t, U u) throws Throwable;
	}

	public void forEach(final ThrowingBiConsumer<? super DataTableRow, Integer> consumer)
	{
		try
		{
			for (int i = 0; i < list.size(); i++)
			{
				final DataTableRow row = list.get(i);
				final int index = i;

				SharedTestContext.run(() -> {
					SharedTestContext.put("row", row);
					consumer.accept(row, index);
				});
			}
		}
		catch (Error | RuntimeException ex)
		{
			throw ex;
		}
		catch (Throwable ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	// public void forEach(@NonNull final ThrowingConsumer<DataTableRow> consumer) throws Throwable
	// {
	// 	for (final DataTableRow row : list)
	// 	{
	// 		SharedTestContext.run(() -> {
	// 			SharedTestContext.put("row", row);
	// 			consumer.accept(row);
	// 		});
	// 	}
	//
	// }

	public LinkedHashMap<String, DataTableRows> groupBy(@NonNull final String columnName)
	{
		return list.stream()
				.collect(Collectors.groupingBy(
						row -> row.getAsString(columnName),
						LinkedHashMap::new,
						GuavaCollectors.collectUsingListAccumulator(DataTableRows::ofRows)
				));
	}

	public List<String> getColumnNames()
	{
		if (list.isEmpty())
		{
			return ImmutableList.of();
		}

		final DataTableRow firstRow = list.get(0);
		return firstRow.getColumnNames();
	}
}

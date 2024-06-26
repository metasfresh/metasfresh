package de.metas.cucumber.stepdefs;

import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.util.GuavaCollectors;
import io.cucumber.datatable.DataTable;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.function.ThrowingConsumer;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

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

	public static DataTableRows ofListOfMaps(@NonNull final List<Map<String, String>> list)
	{
		final AtomicInteger nextLineNo = new AtomicInteger(1);
		return list
				.stream()
				.map(values -> new DataTableRow(nextLineNo.getAndIncrement(), values))
				.collect(GuavaCollectors.collectUsingListAccumulator(DataTableRows::new));
	}

	public int size() {return list.size();}

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
}

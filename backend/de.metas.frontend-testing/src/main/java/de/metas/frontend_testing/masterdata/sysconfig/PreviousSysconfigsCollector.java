package de.metas.frontend_testing.masterdata.sysconfig;

import com.google.common.collect.ImmutableList;
import de.metas.organization.ClientAndOrgId;
import lombok.NonNull;
import lombok.Value;

import java.util.LinkedHashMap;
import java.util.function.Supplier;

/**
 * Collects the previous values of the sysconfigs a request changes. The first value per sysconfig row
 * (name, {@code AD_Client_ID}, {@code AD_Org_ID}) wins, because later ones were captured after an earlier write.
 */
public class PreviousSysconfigsCollector
{
	private final LinkedHashMap<RowKey, JsonSysconfig> previousByRow = new LinkedHashMap<>();

	public void add(@NonNull final JsonSysconfig previous)
	{
		previousByRow.putIfAbsent(new RowKey(previous.getName(), previous.getClientAndOrgId()), previous);
	}

	public boolean isEmpty() {return previousByRow.isEmpty();}

	/**
	 * Calls the given callable, which collects previous values into this instance before writing.
	 * If it fails, restores what was collected so far and rethrows, because the caller gets no previous values from a failed call.
	 */
	public <T> T callAndRestoreOnFailure(@NonNull final Supplier<T> callable)
	{
		try
		{
			return callable.get();
		}
		catch (final RuntimeException ex)
		{
			try
			{
				RestoreSysconfigsCommand.builder()
						.sysconfigs(toList())
						.build()
						.execute();
			}
			catch (final RuntimeException restoreEx)
			{
				ex.addSuppressed(restoreEx);
			}
			throw ex;
		}
	}

	public ImmutableList<JsonSysconfig> toList() {return ImmutableList.copyOf(previousByRow.values());}

	@Value
	private static class RowKey
	{
		@NonNull String name;
		@NonNull ClientAndOrgId clientAndOrgId;
	}
}

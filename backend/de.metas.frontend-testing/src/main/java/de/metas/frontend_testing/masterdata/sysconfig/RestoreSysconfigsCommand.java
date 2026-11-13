package de.metas.frontend_testing.masterdata.sysconfig;

import lombok.Builder;
import lombok.NonNull;

import java.util.List;

/**
 * Puts each given sysconfig row back to its previous state: writes the previous value and {@code IsActive} to exactly that row,
 * or deletes the row if it did not exist before ({@code null} value, see {@link JsonSysconfig#getValue()}).
 */
@Builder
public class RestoreSysconfigsCommand
{
	@NonNull private final SysconfigRows sysconfigRows = new SysconfigRows();

	@NonNull private final List<JsonSysconfig> sysconfigs;

	public void execute()
	{
		sysconfigs.forEach(sysconfigRows::restore);
	}
}

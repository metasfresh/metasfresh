package de.metas.frontend_testing.masterdata.sysconfig;

import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.service.ISysConfigDAO;

import java.util.List;

/**
 * Puts each given sysconfig row back to its previous state: writes the previous value to exactly that row,
 * or deletes the row if it did not exist before ({@code null} value, see {@link JsonSysconfig#getValue()}).
 */
@Builder
public class RestoreSysconfigsCommand
{
	@NonNull private final ISysConfigDAO sysConfigDAO = Services.get(ISysConfigDAO.class);

	@NonNull private final List<JsonSysconfig> sysconfigs;

	public void execute()
	{
		for (final JsonSysconfig sysconfig : sysconfigs)
		{
			if (sysconfig.getValue() == null)
			{
				sysConfigDAO.deleteExactRow(sysconfig.getName(), sysconfig.getClientAndOrgId());
			}
			else
			{
				sysConfigDAO.setValue(sysconfig.getName(), sysconfig.getValue(), sysconfig.getClientAndOrgId());
			}
		}
	}
}

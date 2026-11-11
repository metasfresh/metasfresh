package de.metas.frontend_testing.masterdata.sysconfig;

import de.metas.cache.CacheMgt;
import de.metas.organization.ClientAndOrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigDAO;
import org.compiere.model.I_AD_SysConfig;

import javax.annotation.Nullable;

/**
 * Reads, writes and restores exactly one {@code AD_SysConfig} row (one name at one {@code AD_Client_ID} / {@code AD_Org_ID}),
 * including its {@code IsActive}: reads ignore an inactive row, so a value written to it would not take effect.
 * <p>
 * Every write resets the sysconfig cache at once, so the test's next request reads the new value; the invalidation fired by the save
 * may be processed later.
 */
public class SysconfigRows
{
	@NonNull private final ISysConfigDAO sysConfigDAO = Services.get(ISysConfigDAO.class);

	/** @return the row's current state; value {@code null} if there is no such row. */
	@NonNull
	public JsonSysconfig getState(@NonNull final String name, @NonNull final ClientAndOrgId clientAndOrgId)
	{
		return JsonSysconfig.ofRow(
				name,
				sysConfigDAO.getValueOfExactRow(name, clientAndOrgId).orElse(null),
				sysConfigDAO.isActiveExactRow(name, clientAndOrgId).orElse(true),
				clientAndOrgId);
	}

	/**
	 * Creates or updates the row and activates it, so that the value takes effect.
	 *
	 * @param value {@code null} stores the no-value marker
	 */
	public void setActiveValue(@NonNull final String name, @Nullable final String value, @NonNull final ClientAndOrgId clientAndOrgId)
	{
		sysConfigDAO.setValue(name, value, clientAndOrgId);
		sysConfigDAO.setActiveExactRow(name, clientAndOrgId, true);
		resetCache();
	}

	/** Puts the row back to the given state: its value and {@code IsActive}, or deletes it if it did not exist (value {@code null}). */
	public void restore(@NonNull final JsonSysconfig state)
	{
		if (state.getValue() == null)
		{
			sysConfigDAO.deleteExactRow(state.getName(), state.getClientAndOrgId());
		}
		else
		{
			sysConfigDAO.setValue(state.getName(), state.getValue(), state.getClientAndOrgId());
			sysConfigDAO.setActiveExactRow(state.getName(), state.getClientAndOrgId(), state.isActive());
		}
		resetCache();
	}

	private static void resetCache()
	{
		CacheMgt.get().reset(I_AD_SysConfig.Table_Name);
	}
}

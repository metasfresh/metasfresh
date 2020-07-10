package org.adempiere.service;

import java.util.Map;

import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.ISingletonService;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.NonNull;

public interface ISysConfigBL extends ISingletonService
{
	/**
	 * Get system configuration property of type string
	 */
	String getValue(String Name, String defaultValue);

	/**
	 * Get system configuration property of type string
	 */
	String getValue(String Name);

	/**
	 * Get system configuration property of type int
	 */
	int getIntValue(String Name, int defaultValue);

	/**
	 * Get system configuration property of type double
	 */
	double getDoubleValue(String Name, double defaultValue);

	/**
	 * Get system configuration property of type boolean
	 */
	boolean getBooleanValue(String Name, boolean defaultValue);

	default <T extends ReferenceListAwareEnum> T getReferenceListAware(final String name, final T defaultValue, final Class<T> type)
	{
		final String code = getValue(name, null);
		if (Check.isEmpty(code, true))
		{
			return defaultValue;
		}

		return ReferenceListAwareEnums.ofCode(code, type);
	}

	default <T extends Enum<T>> T getEnumValue(final String name, final T defaultValue, final Class<T> enumType)
	{
		final String valueStr = getValue(name, null);
		if (Check.isEmpty(valueStr, true))
		{
			return defaultValue;
		}
		return Enum.valueOf(enumType, valueStr);
	}

	/**
	 * Get client configuration property of type string.<br>
	 * If there is more than one matching record, the value of the first <code>AD_SysConfig</code> record, according to <code>ORDER BY AD_Client_ID DESC, AD_Org_ID DESC</code> will be returned.
	 *
	 * @param AD_Client_ID the system will retrieve the value from an <code>AD_SysConfig</code> record that has the given client-ID or <code>0</code>, prefering a records with a not-<code>0</code> ID.
	 * @return String
	 */
	String getValue(String Name, String defaultValue, int AD_Client_ID);

	/**
	 * Get system configuration property of type string.<br>
	 * If there is more than one matching record, the value of the first <code>AD_SysConfig</code> record, according to <code>ORDER BY AD_Client_ID DESC, AD_Org_ID DESC</code> will be returned.

	 */
	String getValue(String Name, int AD_Client_ID);

	/**
	 * Get system configuration property of type int
	 */
	int getIntValue(String Name, int defaultValue, int AD_Client_ID);

	/**
	 * Get system configuration property of type double
	 */
	double getDoubleValue(String Name, double defaultValue, int AD_Client_ID);

	/**
	 * Get system configuration property of type boolean. Valid SysConfig can be <code>Y</code>, <code>N</code> (case is ignored) and whatever {@link Boolean#valueOf(String)} can deal with.
	 */
	boolean getBooleanValue(String Name, boolean defaultValue, int AD_Client_ID);

	/**
	 * Get client configuration property of type string
	 */
	String getValue(String Name, String defaultValue, int AD_Client_ID, int AD_Org_ID);

	/**
	 * Get system configuration property of type string
	 */
	String getValue(String Name, int AD_Client_ID, int AD_Org_ID);

	default String getValue(
			@NonNull final String name,
			@NonNull final ClientId adClientId,
			@NonNull final OrgId adOrgId)
	{
		return getValue(name, adClientId.getRepoId(), adOrgId.getRepoId());
	}

	/**
	 * Get system configuration property of type int
	 */
	int getIntValue(String Name, int defaultValue, int AD_Client_ID, int AD_Org_ID);

	/**
	 * Get system configuration property of type double
	 */
	double getDoubleValue(String Name, double defaultValue, int AD_Client_ID, int AD_Org_ID);

	/**
	 * Get system configuration property of type boolean
	 */
	boolean getBooleanValue(String Name, boolean defaultValue, int AD_Client_ID, int AD_Org_ID);

	void setValue(String name, int value, ClientId clientId, OrgId orgId);

	void setValue(String name, double value, ClientId clientId, OrgId orgId);

	void setValue(String name, boolean value, ClientId clientId, OrgId orgId);

	void setValue(String name, String value, ClientId clientId, OrgId orgId);

	/**
	 * Returns a mapping (name -> value) that includes all AD_SysConfig records whose <code>Name</code> has the given <code>prefix</code>.
	 */
	Map<String, String> getValuesForPrefix(String prefix, int adClientId, int adOrgId);

	default Map<String, String> getValuesForPrefix(
			@NonNull final String prefix,
			@NonNull final ClientId adClientId,
			@NonNull final OrgId adOrgId)
	{
		return getValuesForPrefix(prefix, adClientId.getRepoId(), adOrgId.getRepoId());
	}

	/**
	 * This method is similar {@link #getValuesForPrefix(String, int, int)}, but has the additional <code>removePrefix</code> parameter.
	 *
	 * @param removePrefix if <code>false</code>, then the result is the same as the result of {@link #getValuesForPrefix(String, int, int)}. If <code>true</code>, then the given prefix is removed
	 *            from the AD_SysConfig <code>Name</code> values before adding them to the result map.
	 * @return immutable map of name/value
	 */
	Map<String, String> getValuesForPrefix(String prefix, boolean removePrefix, int adClientId, int adOrgId);

}

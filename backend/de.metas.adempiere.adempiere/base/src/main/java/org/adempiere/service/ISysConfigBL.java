package org.adempiere.service;

import com.google.common.collect.ImmutableSet;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.util.ISingletonService;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Map;

public interface ISysConfigBL extends ISingletonService
{
	@Nullable
	String getValue(String name, String defaultValue);

	@Nullable
	String getValue(String name);

	int getIntValue(String name, int defaultValue);

	int getPositiveIntValue(String name, int defaultValue);

	/**
	 * Get system configuration property of type boolean
	 */
	boolean getBooleanValue(String name, boolean defaultValue);

	String getValue(@NonNull String name, @Nullable String defaultValue, @NonNull ClientAndOrgId clientAndOrgId);

	<T extends ReferenceListAwareEnum> T getReferenceListAware(final String name, final T defaultValue, final Class<T> type);

	/**
	 * Get client configuration property of type string.<br>
	 * If there is more than one matching record, the value of the first <code>AD_SysConfig</code> record, according to <code>ORDER BY AD_Client_ID DESC, AD_Org_ID DESC</code> will be returned.
	 *
	 * @param AD_Client_ID the system will retrieve the value from an <code>AD_SysConfig</code> record that has the given client-ID or <code>0</code>, prefering a records with a not-<code>0</code> ID.
	 * @return String
	 */
	@Nullable
	String getValue(String name, String defaultValue, int AD_Client_ID);

	@Nullable
	String getValue(String name, int AD_Client_ID);

	int getIntValue(String name, int defaultValue, int AD_Client_ID);

	boolean getBooleanValue(String name, boolean defaultValue, int AD_Client_ID);

	String getValue(String name, @Nullable String defaultValue, int AD_Client_ID, int AD_Org_ID);

	@Nullable
	String getValue(String name, int AD_Client_ID, int AD_Org_ID);

	@Nullable
	String getValue(@NonNull String name, @NonNull ClientAndOrgId clientAndOrgId);

	int getIntValue(String name, int defaultValue, int AD_Client_ID, int AD_Org_ID);

	boolean getBooleanValue(String name, boolean defaultValue, int AD_Client_ID, int AD_Org_ID);

	void setValue(String name, int value, ClientId clientId, OrgId orgId);

	void setValue(String name, boolean value, ClientId clientId, OrgId orgId);

	void setValue(String name, String value, ClientId clientId, OrgId orgId);

	/**
	 * Returns a mapping (name -> value) that includes all AD_SysConfig records whose <code>Name</code> has the given <code>prefix</code>.
	 */
	Map<String, String> getValuesForPrefix(String prefix, int adClientId, int adOrgId);

	Map<String, String> getValuesForPrefix(@NonNull final String prefix, @NonNull final ClientId adClientId, @NonNull final OrgId adOrgId);

	Map<String, String> getValuesForPrefix(@NonNull String prefix, @NonNull ClientAndOrgId clientAndOrgId);

	/**
	 * @param removePrefix If <code>true</code>, then the given prefix is removed from the AD_SysConfig <code>Name</code> values before adding them to the result map.
	 * @return immutable map of name/value
	 */
	Map<String, String> getValuesForPrefix(String prefix, boolean removePrefix, ClientAndOrgId clientAndOrgId);

	<T extends Enum<T>> ImmutableSet<T> getCommaSeparatedEnums(@NonNull String sysconfigName, @NonNull Class<T> enumType);
}

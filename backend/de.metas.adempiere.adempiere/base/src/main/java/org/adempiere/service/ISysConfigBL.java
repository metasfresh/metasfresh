/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package org.adempiere.service;

import com.google.common.collect.ImmutableSet;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.util.ISingletonService;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public interface ISysConfigBL extends ISingletonService
{
	@Contract("_, !null -> !null")
	@Nullable
	String getValue(@NonNull String name, @Nullable String defaultValue);

	@Nullable
	String getValue(@NonNull String name);

	@NonNull
	default Optional<String> getValueOptional(final String name)
	{
		return Optional.ofNullable(getValue(name));
	}

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

	@NonNull
	BigDecimal getBigDecimalValue(@NonNull String name, @NonNull BigDecimal defaultValue);

	boolean getBooleanValue(String name, boolean defaultValue, int AD_Client_ID);

	String getValue(String name, @Nullable String defaultValue, int AD_Client_ID, int AD_Org_ID);

	@Nullable
	String getValue(String name, int AD_Client_ID, int AD_Org_ID);

	@Nullable
	String getValue(@NonNull String name, @NonNull ClientAndOrgId clientAndOrgId);

	int getIntValue(String name, int defaultValue, int AD_Client_ID, int AD_Org_ID);

	int getIntValue(String name, int defaultValue, @NonNull ClientAndOrgId clientAndOrgId);

	boolean getBooleanValue(String name, boolean defaultValue, int AD_Client_ID, int AD_Org_ID);

	boolean getBooleanValue(String name, boolean defaultValue, ClientAndOrgId clientAndOrgId);

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

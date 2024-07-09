package org.adempiere.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.logging.LogManager;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.service.ISysConfigDAO;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * SysConfig Service. Most of the code is copy-paste from MSysConfig
 *
 * @author tsa
 */
public class SysConfigBL implements ISysConfigBL
{
	private static final Logger logger = LogManager.getLogger(SysConfigBL.class);
	private final ISysConfigDAO sysConfigDAO = Services.get(ISysConfigDAO.class);

	@Nullable
	@Override
	public String getValue(final String name, @Nullable final String defaultValue)
	{
		return sysConfigDAO.getValue(name, ClientAndOrgId.SYSTEM).orElse(defaultValue);
	}

	@Nullable
	@Override
	public String getValue(final String name)
	{
		return sysConfigDAO.getValue(name, ClientAndOrgId.SYSTEM).orElse(null);
	}

	@Override
	public int getIntValue(final String name, final int defaultValue)
	{
		return sysConfigDAO.getValue(name, ClientAndOrgId.SYSTEM)
				.map(valueStr -> NumberUtils.asInt(valueStr, defaultValue))
				.orElse(defaultValue);
	}

	@Override
	public int getPositiveIntValue(final String name, final int defaultValue)
	{
		return sysConfigDAO.getValue(name, ClientAndOrgId.SYSTEM)
				.map(valueStr -> NumberUtils.asInt(valueStr, defaultValue))
				.filter(valueInt -> valueInt > 0) // positive
				.orElse(defaultValue);
	}

	@Override
	public boolean getBooleanValue(final String name, final boolean defaultValue)
	{
		return sysConfigDAO.getValue(name, ClientAndOrgId.SYSTEM)
				.map(valueStr -> StringUtils.toBoolean(valueStr, defaultValue))
				.orElse(defaultValue);
	}

	@Nullable
	@Override
	public String getValue(final String name, @Nullable final String defaultValue, final int AD_Client_ID)
	{
		return sysConfigDAO.getValue(name, ClientAndOrgId.ofClientId(AD_Client_ID))
				.orElse(defaultValue);
	}

	@Nullable
	@Override
	public String getValue(final String name, final int AD_Client_ID)
	{
		return sysConfigDAO.getValue(name, ClientAndOrgId.ofClientId(AD_Client_ID))
				.orElse(null);
	}

	@Override
	public int getIntValue(final String name, final int defaultValue, final int AD_Client_ID)
	{
		return sysConfigDAO.getValue(name, ClientAndOrgId.ofClientId(AD_Client_ID))
				.map(valueStr -> NumberUtils.asInt(valueStr, defaultValue))
				.orElse(defaultValue);
	}

	@Override
	public boolean getBooleanValue(final String name, final boolean defaultValue, final int AD_Client_ID)
	{
		return sysConfigDAO.getValue(name, ClientAndOrgId.ofClientId(AD_Client_ID))
				.map(valueStr -> StringUtils.toBoolean(valueStr, defaultValue))
				.orElse(defaultValue);
	}

	@Override
	public String getValue(final String name, final String defaultValue, final int AD_Client_ID, final int AD_Org_ID)
	{
		return sysConfigDAO.getValue(name, ClientAndOrgId.ofClientAndOrg(AD_Client_ID, AD_Org_ID))
				.orElse(defaultValue);
	}

	@Nullable
	@Override
	public String getValue(final String name, final int AD_Client_ID, final int AD_Org_ID)
	{
		return sysConfigDAO.getValue(name, ClientAndOrgId.ofClientAndOrg(AD_Client_ID, AD_Org_ID))
				.orElse(null);
	}

	@Nullable
	@Override
	public String getValue(@NonNull final String name, @NonNull final ClientAndOrgId clientAndOrgId)
	{
		return sysConfigDAO.getValue(name, clientAndOrgId)
				.orElse(null);
	}

	@Override
	public int getIntValue(final String name, final int defaultValue, final int AD_Client_ID, final int AD_Org_ID)
	{
		return sysConfigDAO.getValue(name, ClientAndOrgId.ofClientAndOrg(AD_Client_ID, AD_Org_ID))
				.map(valueStr -> NumberUtils.asInt(valueStr, defaultValue))
				.orElse(defaultValue);
	}

	@Override
	public boolean getBooleanValue(final String name, final boolean defaultValue, final int AD_Client_ID, final int AD_Org_ID)
	{
		return sysConfigDAO.getValue(name, ClientAndOrgId.ofClientAndOrg(AD_Client_ID, AD_Org_ID))
				.map(valueStr -> StringUtils.toBoolean(valueStr, defaultValue))
				.orElse(defaultValue);
	}

	@Override
	public boolean getBooleanValue(final String name, final boolean defaultValue, final ClientAndOrgId clientAndOrgId)
	{
		return sysConfigDAO.getValue(name, clientAndOrgId)
				.map(valueStr -> StringUtils.toBoolean(valueStr, defaultValue))
				.orElse(defaultValue);
	}

	@Override
	public void setValue(
			@NonNull final String name,
			final int value,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId)
	{
		sysConfigDAO.setValue(name, value, ClientAndOrgId.ofClientAndOrg(clientId, orgId));
	}

	@Override
	public void setValue(
			@NonNull final String name,
			final boolean value,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId)
	{
		sysConfigDAO.setValue(name, value, ClientAndOrgId.ofClientAndOrg(clientId, orgId));
	}

	@Override
	public void setValue(
			@NonNull final String name,
			final String value,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId)
	{
		sysConfigDAO.setValue(name, value, ClientAndOrgId.ofClientAndOrg(clientId, orgId));
	}

	private Set<String> getNamesForPrefix(final String prefix, final ClientAndOrgId clientAndOrgId)
	{
		return ImmutableSet.<String>builder()
				.addAll(sysConfigDAO.retrieveNamesForPrefix(prefix, clientAndOrgId))
				.addAll(getSystemPropertyNamesForPrefix(prefix))
				.build();
	}

	private static Set<String> getSystemPropertyNamesForPrefix(final String prefix)
	{
		Check.assumeNotEmpty(prefix, "prefix is not empty");

		final ImmutableSet.Builder<String> result = ImmutableSet.builder();
		for (final Object keyObj : System.getProperties().keySet())
		{
			if (keyObj == null)
			{
				continue;
			}

			final String key = keyObj.toString();
			if (key.startsWith(prefix))
			{
				result.add(key);
			}
		}

		return result.build();
	}

	@Override
	public Map<String, String> getValuesForPrefix(
			@NonNull final String prefix,
			@NonNull final ClientId adClientId,
			@NonNull final OrgId adOrgId)
	{
		final boolean removePrefix = false;
		return getValuesForPrefix(prefix, removePrefix, ClientAndOrgId.ofClientAndOrg(adClientId, adOrgId));
	}

	@Override
	public Map<String, String> getValuesForPrefix(
			@NonNull final String prefix,
			@NonNull final ClientAndOrgId clientAndOrgId)
	{
		final boolean removePrefix = false;
		return getValuesForPrefix(prefix, removePrefix, clientAndOrgId);
	}

	@Override
	public Map<String, String> getValuesForPrefix(final String prefix, final int adClientId, final int adOrgId)
	{
		final boolean removePrefix = false;
		return getValuesForPrefix(prefix, removePrefix, ClientAndOrgId.ofClientAndOrg(adClientId, adOrgId));
	}

	@Override
	public Map<String, String> getValuesForPrefix(final String prefix, final boolean removePrefix, @NonNull final ClientAndOrgId clientAndOrgId)
	{
		final Set<String> paramNames = getNamesForPrefix(prefix, clientAndOrgId);

		final ImmutableMap.Builder<String, String> result = ImmutableMap.builder();
		for (final String paramName : paramNames)
		{
			final String value = sysConfigDAO.getValue(paramName, clientAndOrgId).orElse(null);
			if (value == null)
			{
				continue;
			}

			final String name;
			if (removePrefix && paramName.startsWith(prefix) && !paramName.equals(prefix))
			{
				name = paramName.substring(prefix.length());
			}
			else
			{
				name = paramName;
			}

			result.put(name, value);
		}

		return result.build();
	}

	@Override
	public String getValue(@NonNull final String name, @Nullable final String defaultValue, @NonNull final ClientAndOrgId clientAndOrgId)
	{
		return sysConfigDAO.getValue(name, clientAndOrgId).orElse(defaultValue);
	}

	@Override
	public <T extends ReferenceListAwareEnum> T getReferenceListAware(final String name, final T defaultValue, final Class<T> type)
	{
		final String code = sysConfigDAO.getValue(name, ClientAndOrgId.SYSTEM).orElse(null);
		return code != null && !Check.isBlank(code)
				? ReferenceListAwareEnums.ofCode(code, type)
				: defaultValue;
	}

	@Override
	public <T extends Enum<T>> ImmutableSet<T> getCommaSeparatedEnums(@NonNull final String sysconfigName, @NonNull final Class<T> enumType)
	{
		final String string = StringUtils.trimBlankToNull(sysConfigDAO.getValue(sysconfigName, ClientAndOrgId.SYSTEM).orElse(null));
		if (string == null || string.equals("-"))
		{
			return ImmutableSet.of();
		}

		return Splitter.on(",")
				.trimResults()
				.omitEmptyStrings()
				.splitToList(string)
				.stream()
				.map(name -> {
					try
					{
						return Enum.valueOf(enumType, name);
					}
					catch (final Exception ex)
					{
						logger.warn("Failed converting `{}` to enum {}. Ignoring it.", name, enumType, ex);
						return null;
					}
				})
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

}

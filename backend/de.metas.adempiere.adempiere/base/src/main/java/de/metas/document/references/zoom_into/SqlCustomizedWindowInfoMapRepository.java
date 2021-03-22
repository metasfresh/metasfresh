/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.document.references.zoom_into;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Window;
import org.compiere.util.DB;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;

@Repository
public class SqlCustomizedWindowInfoMapRepository implements CustomizedWindowInfoMapRepository
{
	private final CCache<Integer, CustomizedWindowInfoMap> customizedWindowIdsCache = CCache.<Integer, CustomizedWindowInfoMap>builder()
			.tableName(I_AD_Window.Table_Name)
			.build();

	@Override
	public CustomizedWindowInfoMap get()
	{
		return customizedWindowIdsCache.getOrLoad(0, this::retrieveCustomizedWindowIdsMap);
	}

	private CustomizedWindowInfoMap retrieveCustomizedWindowIdsMap()
	{
		final String sql = "SELECT "
				+ I_AD_Window.COLUMNNAME_AD_Window_ID
				+ "," + I_AD_Window.COLUMNNAME_Overrides_Window_ID
				+ "," + I_AD_Window.COLUMNNAME_IsOverrideInMenu
				+ ", " + I_AD_Window.COLUMNNAME_Name
				+ ", (SELECT array_agg(ARRAY[wtrl.ad_language, wtrl.name]) AS array_agg FROM ad_window_trl wtrl WHERE wtrl.ad_window_id = w.ad_window_id) AS name_trls"
				+ " FROM " + I_AD_Window.Table_Name + " w"
				+ " WHERE IsActive='Y' AND " + I_AD_Window.COLUMNNAME_Overrides_Window_ID + " IS NOT NULL"
				+ " ORDER BY AD_Window_ID";

		final List<CustomizedWindowInfo> customizedWindowInfos = DB.retrieveRows(
				sql,
				ImmutableList.of(),
				this::retrieveCustomizedWindowInfo);

		return CustomizedWindowInfoMap.ofList(customizedWindowInfos);
	}

	private CustomizedWindowInfo retrieveCustomizedWindowInfo(final ResultSet rs) throws SQLException
	{
		return CustomizedWindowInfo.builder()
				.customizationWindowId(AdWindowId.ofRepoId(rs.getInt(I_AD_Window.COLUMNNAME_AD_Window_ID)))
				.customizationWindowCaption(retrieveWindowCaption(rs))
				.baseWindowId(AdWindowId.ofRepoId(rs.getInt(I_AD_Window.COLUMNNAME_Overrides_Window_ID)))
				.overrideInMenu(StringUtils.toBoolean(rs.getString(I_AD_Window.COLUMNNAME_IsOverrideInMenu)))
				.build();
	}

	private static ImmutableTranslatableString retrieveWindowCaption(final ResultSet rs) throws SQLException
	{
		final ImmutableTranslatableString.ImmutableTranslatableStringBuilder result = ImmutableTranslatableString.builder()
				.defaultValue(rs.getString(I_AD_Window.COLUMNNAME_Name));

		final Array sqlArray = rs.getArray("name_trls");
		if (sqlArray != null)
		{
			final String[][] trls = (String[][])sqlArray.getArray();
			for (final String[] languageAndName : trls)
			{
				final String adLanguage = languageAndName[0];
				final String name = languageAndName[1];

				result.trl(adLanguage, name);
			}
		}

		return result.build();
	}

	@Override
	public void assertNoCycles(@NonNull final AdWindowId adWindowId)
	{
		final LinkedHashSet<AdWindowId> seenWindowIds = new LinkedHashSet<>();
		AdWindowId currentWindowId = adWindowId;
		while (currentWindowId != null)
		{
			if (!seenWindowIds.add(currentWindowId))
			{
				throw new AdempiereException("Avoid cycles in customization window chain: " + seenWindowIds);
			}

			currentWindowId = retrieveBaseWindowId(currentWindowId);
		}
	}

	@Nullable
	private AdWindowId retrieveBaseWindowId(@NonNull final AdWindowId customizationWindowId)
	{
		final String sql = "SELECT " + I_AD_Window.COLUMNNAME_Overrides_Window_ID
				+ " FROM " + I_AD_Window.Table_Name
				+ " WHERE " + I_AD_Window.COLUMNNAME_AD_Window_ID + "=?";

		final int baseWindowRepoId = DB.getSQLValueEx(ITrx.TRXNAME_ThreadInherited, sql, customizationWindowId);
		return AdWindowId.ofRepoIdOrNull(baseWindowRepoId);
	}
}

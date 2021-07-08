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

package de.metas.document.references.related_documents.generic;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_C_DataImport;
import org.compiere.model.I_C_DataImport_Run;
import org.compiere.model.POInfo;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Deprecated
public class LegacyGenericZoomInfoDescriptorsRepository implements GenericZoomInfoDescriptorsRepository
{
	private static final Logger logger = LogManager.getLogger(LegacyGenericZoomInfoDescriptorsRepository.class);

	@Override
	public ImmutableList<GenericZoomInfoDescriptor> getZoomInfoDescriptors(@NonNull final String sourceKeyColumnName)
	{
		Check.assumeNotEmpty(sourceKeyColumnName, "sourceKeyColumnName is not empty");

		final List<Object> sqlParams = new ArrayList<>();
		String sql = "SELECT DISTINCT "
				+ "\n   w_so_trl.AD_Language"
				+ "\n , w_so.AD_Window_ID"
				+ "\n , w_so.Name as Name_BaseLang"
				+ "\n , w_so_trl.Name as Name"
				//
				+ "\n , w_po.AD_Window_ID as PO_Window_ID"
				+ "\n , w_po.Name as PO_Name_BaseLang"
				+ "\n , w_po_trl.Name as PO_Name"
				//
				+ "\n , t.TableName "
				+ "\n FROM AD_Table t "
				+ "\n INNER JOIN AD_Window w_so ON (t.AD_Window_ID=w_so.AD_Window_ID) AND w_so.IsActive='Y'" // gh #1489 : only consider active windows
				+ "\n LEFT OUTER JOIN AD_Window_Trl w_so_trl ON (w_so_trl.AD_Window_ID=w_so.AD_Window_ID)"
				+ "\n LEFT OUTER JOIN AD_Window w_po ON (t.PO_Window_ID=w_po.AD_Window_ID) AND w_po.IsActive='Y'" // gh #1489 : only consider active windows
				+ "\n LEFT OUTER JOIN AD_Window_Trl w_po_trl ON (w_po_trl.AD_Window_ID=w_po.AD_Window_ID AND w_po_trl.AD_Language=w_so_trl.AD_Language)";

		//@formatter:off
		sql += "WHERE " // No Import
				+ " t.IsActive='Y'" // gh #1489 : only consider active tables
				+ " AND EXISTS ("
				+ "SELECT 1 FROM AD_Tab tt "
				+ "WHERE (tt.AD_Window_ID=t.AD_Window_ID OR tt.AD_Window_ID=t.PO_Window_ID)"
				+ " AND tt.AD_Table_ID=t.AD_Table_ID"
				+ " AND ("
				// First Tab
				+ " tt.SeqNo=10"
				+ ")"
				+ ")"
				// Consider tables which have an AD_Table_ID/Record_ID reference to our column
				+ " AND (t.AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Column WHERE ColumnName=? AND IsKey='N'))" // #1
		;
		//@formatter:on

		if (isExcludeImportRecords(sourceKeyColumnName))
		{
			sql += "\n AND t.TableName NOT LIKE 'I%'";
		}

		sql += "\n ORDER BY 2";

		sqlParams.add(sourceKeyColumnName);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			final Map<Util.ArrayKey, GenericZoomInfoDescriptorFactory> builders = new LinkedHashMap<>();
			while (rs.next())
			{
				//
				// Get/create the zoom info descriptor builders (one for each target table and window IDs triplet)
				final String targetTableName = rs.getString("TableName");
				final AdWindowId SO_Window_ID = AdWindowId.ofRepoIdOrNull(rs.getInt("AD_Window_ID"));
				final AdWindowId PO_Window_ID = AdWindowId.ofRepoIdOrNull(rs.getInt("PO_Window_ID"));
				final String soNameBaseLang = rs.getString("Name_BaseLang");
				final String poNameBaseLang = rs.getString("PO_Name_BaseLang");
				final Util.ArrayKey key = Util.ArrayKey.of(targetTableName, SO_Window_ID, PO_Window_ID);
				final GenericZoomInfoDescriptorFactory zoomInfoDescriptorBuilder = builders.computeIfAbsent(key, k -> {

					final POInfo targetTableInfo = POInfo.getPOInfo(targetTableName);
					if (targetTableInfo == null)
					{
						logger.warn("No POInfo found for {}. Skip it.", targetTableName);
						return null;
					}

					final GenericZoomInfoDescriptorFactory builder = new GenericZoomInfoDescriptorFactory()
							.setTargetTableName(targetTableName)
							.setTargetHasIsSOTrxColumn(targetTableInfo.hasColumnName(Env.CTXNAME_IsSOTrx))
							.setTargetNames(soNameBaseLang, poNameBaseLang)
							.setTargetWindowIds(SO_Window_ID, PO_Window_ID);

					@SuppressWarnings("UnnecessaryLocalVariable") final String targetColumnName = sourceKeyColumnName;
					final boolean hasTargetColumnName = targetTableInfo.hasColumnName(targetColumnName);

					// Dynamic references AD_Table_ID/Record_ID (task #03921)
					if (!hasTargetColumnName
							&& targetTableInfo.hasColumnName(ITableRecordReference.COLUMNNAME_AD_Table_ID)
							&& targetTableInfo.hasColumnName(ITableRecordReference.COLUMNNAME_Record_ID))
					{
						builder.setDynamicTargetColumnName(true);
					}
					// No target column
					else if (!hasTargetColumnName)
					{
						logger.warn("Target column name {} not found in table {}", targetColumnName, targetTableName);
						return null;
					}
					// Regular target column
					else
					{
						builder.setDynamicTargetColumnName(false);
						builder.setTargetColumnName(targetColumnName);
						if (targetTableInfo.isVirtualColumn(targetColumnName))
						{
							builder.setVirtualTargetColumnSql(targetTableInfo.getColumnSql(targetColumnName));
						}
					}

					return builder;
				});

				if (zoomInfoDescriptorBuilder == null)
				{
					// builder could not be created
					// we expect error to be already reported, so here we just skip it
					continue;
				}

				//
				// Add translation
				final String adLanguage = rs.getString("AD_Language");
				final String soNameTrl = rs.getString("Name");
				final String poNameTrl = rs.getString("PO_Name");
				zoomInfoDescriptorBuilder.putTargetNamesTrl(adLanguage, soNameTrl, poNameTrl);
			}

			//
			// Run each builder, get the list of descriptors and join them together in one list
			return builders.values().stream() // each builder
					.filter(Objects::nonNull) // skip null builders
					.flatMap(builder -> builder.buildAll().stream())
					.collect(ImmutableList.toImmutableList());
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	private static boolean isExcludeImportRecords(final String sourceKeyColumnName)
	{
		return !sourceKeyColumnName.equals(I_C_DataImport.COLUMNNAME_C_DataImport_ID)
				&& !sourceKeyColumnName.equals(I_C_DataImport_Run.COLUMNNAME_C_DataImport_Run_ID);
	}

	//
	//
	// -------------------
	//
	//
	//

	private static final class GenericZoomInfoDescriptorFactory
	{
		private final IADWindowDAO windowDAO = Services.get(IADWindowDAO.class);

		private final ImmutableTranslatableString.ImmutableTranslatableStringBuilder soNameTrl = ImmutableTranslatableString.builder();
		private final ImmutableTranslatableString.ImmutableTranslatableStringBuilder poNameTrl = ImmutableTranslatableString.builder();

		private String targetTableName;
		@Nullable private String targetColumnName;
		private boolean dynamicTargetColumnName;
		private String virtualTargetColumnSql;
		@Nullable private AdWindowId targetSO_Window_ID;
		@Nullable private AdWindowId targetPO_Window_ID;
		private Boolean targetHasIsSOTrxColumn;

		private GenericZoomInfoDescriptorFactory() { }

		public ImmutableList<GenericZoomInfoDescriptor> buildAll()
		{
			final GenericZoomInfoDescriptor.GenericZoomInfoDescriptorBuilder infoBuilder = GenericZoomInfoDescriptor.builder()
					//.nameTrl(...)
					.targetTableName(this.targetTableName)
					.targetColumnName(this.targetColumnName)
					.virtualTargetColumnSql(virtualTargetColumnSql)
					.dynamicTargetColumnName(dynamicTargetColumnName)
					//.targetAD_Window_ID(...)
					//.isSOTrx(...)
					.targetHasIsSOTrxColumn(targetHasIsSOTrxColumn);

			// shall not happen
			if (targetSO_Window_ID == null && targetPO_Window_ID == null)
			{
				return ImmutableList.of();
			}
			else if (targetPO_Window_ID == null)
			{
				return ImmutableList.of(
						infoBuilder.name(this.soNameTrl.build())
								.targetWindowId(targetSO_Window_ID)
								.targetWindowInternalName(computeTargetWindowInternalName(targetSO_Window_ID))
								.isSOTrx(null) // applies for SO and PO
								.build());
			}
			else
			{
				return ImmutableList.of(
						infoBuilder.name(this.soNameTrl.build())
								.targetWindowId(targetSO_Window_ID)
								.targetWindowInternalName(computeTargetWindowInternalName(targetSO_Window_ID))
								.isSOTrx(Boolean.TRUE)
								.build(),
						infoBuilder.name(this.poNameTrl.build())
								.targetWindowId(targetPO_Window_ID)
								.targetWindowInternalName(computeTargetWindowInternalName(targetPO_Window_ID))
								.isSOTrx(Boolean.FALSE)
								.build());
			}
		}

		private String computeTargetWindowInternalName(@NonNull final AdWindowId targetAD_Window_ID)
		{
			//noinspection ConstantConditions
			return CoalesceUtil.coalesceSuppliers(
					() -> windowDAO.retrieveInternalWindowName(targetAD_Window_ID),
					() -> windowDAO.retrieveWindowName(targetAD_Window_ID).getDefaultValue());
		}

		public GenericZoomInfoDescriptorFactory setTargetNames(final String soName, final String poName)
		{
			if (soName != null)
			{
				soNameTrl.defaultValue(soName);
			}
			if (poName != null)
			{
				poNameTrl.defaultValue(poName);
			}
			return this;
		}

		public void putTargetNamesTrl(final String adLanguage, final String soName, final String poName)
		{
			if (soName != null)
			{
				soNameTrl.trl(adLanguage, soName);
			}
			if (poName != null)
			{
				poNameTrl.trl(adLanguage, poName);
			}
		}

		public GenericZoomInfoDescriptorFactory setTargetTableName(final String targetTableName)
		{
			this.targetTableName = targetTableName;
			return this;
		}

		public GenericZoomInfoDescriptorFactory setTargetColumnName(final String targetColumnName)
		{
			this.targetColumnName = targetColumnName;
			return this;
		}

		public void setDynamicTargetColumnName(final boolean dynamicTargetColumnName)
		{
			this.dynamicTargetColumnName = dynamicTargetColumnName;
			this.targetColumnName = null;
		}

		public void setVirtualTargetColumnSql(final String virtualTargetColumnSql)
		{
			this.virtualTargetColumnSql = virtualTargetColumnSql;
		}

		public GenericZoomInfoDescriptorFactory setTargetWindowIds(@Nullable final AdWindowId soWindowId, @Nullable final AdWindowId poWindowId)
		{
			targetSO_Window_ID = soWindowId;
			targetPO_Window_ID = poWindowId;
			return this;
		}

		public GenericZoomInfoDescriptorFactory setTargetHasIsSOTrxColumn(final boolean targetHasIsSOTrxColumn)
		{
			this.targetHasIsSOTrxColumn = targetHasIsSOTrxColumn;
			return this;
		}
	}
}

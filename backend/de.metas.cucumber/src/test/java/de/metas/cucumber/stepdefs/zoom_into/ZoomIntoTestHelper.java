package de.metas.cucumber.stepdefs.zoom_into;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.document.references.related_documents.IZoomSource;
import de.metas.document.references.related_documents.POZoomSource;
import de.metas.document.references.related_documents.RelatedDocumentsCandidateGroup;
import de.metas.document.references.related_documents.RelatedDocumentsFactory;
import de.metas.document.references.related_documents.RelatedDocumentsPermissions;
import de.metas.document.references.related_documents.RelatedDocumentsPermissionsFactory;
import de.metas.document.references.zoom_into.RecordWindowFinder;
import de.metas.i18n.ITranslatableString;
import de.metas.lang.SOTrx;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.MQuery;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.model.POInfoColumn;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;

import javax.annotation.Nullable;
import java.util.Optional;

public class ZoomIntoTestHelper
{
	@NonNull private final IADWindowDAO windowDAO = Services.get(IADWindowDAO.class);
	@NonNull private final RelatedDocumentsFactory relatedDocumentsFactory = SpringContextHolder.instance.getBean(RelatedDocumentsFactory.class);

	public Optional<AdWindowId> resolveZoomIntoWindow(@NonNull final String tableName)
	{
		return RecordWindowFinder.findAdWindowId(tableName);
	}

	public Optional<AdWindowId> resolveZoomIntoWindow(@NonNull final String tableName, @NonNull final SOTrx soTrx)
	{
		return RecordWindowFinder.newInstance(tableName, soTrx).findAdWindowId();
	}

	public Optional<AdWindowId> resolveZoomIntoWindowForRecord(@NonNull final String tableName, final int recordId)
	{
		return RecordWindowFinder.newInstance(tableName, recordId)
				.checkRecordPresentInWindow()
				.findAdWindowId();
	}

	@Nullable
	public String getWindowName(@NonNull final AdWindowId windowId)
	{
		final ITranslatableString name = windowDAO.retrieveWindowName(windowId);
		return name != null ? name.translate("en_US") : null;
	}

	public ImmutableList<String> findTablesWithMissingZoomIntoWindows(@NonNull final ImmutableSet<String> excludedTables)
	{
		final ImmutableList<String> allTableNames = retrieveAllTableNamesFromView();
		final ImmutableList.Builder<String> failedTables = ImmutableList.builder();

		for (final String tableName : allTableNames)
		{
			if (excludedTables.contains(tableName))
			{
				continue;
			}

			final Optional<AdWindowId> windowId = resolveZoomIntoWindow(tableName);
			if (!windowId.isPresent())
			{
				failedTables.add(tableName);
			}
		}

		return failedTables.build();
	}

	private ImmutableList<String> retrieveAllTableNamesFromView()
	{
		final String sql = "SELECT DISTINCT TableName FROM ad_table_windows_v ORDER BY TableName";
		return DB.retrieveRowsOutOfTrx(sql, null, rs -> rs.getString("TableName"));
	}

	public ImmutableList<RelatedDocumentsCandidateGroup> getRelatedDocumentsCandidates(@NonNull final Object record)
	{
		final PO po = InterfaceWrapperHelper.getPO(record);
		final IZoomSource zoomSource = POZoomSource.of(po);
		final RelatedDocumentsPermissions permissions = RelatedDocumentsPermissionsFactory.allowAll();
		return relatedDocumentsFactory.getRelatedDocumentsCandidates(zoomSource, permissions);
	}

	@Nullable
	private String resolveTargetTableName(@NonNull final POInfoColumn column)
	{
		final int displayType = column.getDisplayType();
		if (displayType == DisplayType.Table || displayType == DisplayType.Search)
		{
			final String referencedTable = column.getReferencedTableNameOrNull();
			if (referencedTable != null)
			{
				return referencedTable;
			}
			// Fallback: derive table name from column name (same as TableDir logic)
			return MQuery.getZoomTableName(column.getColumnName());
		}
		else if (displayType == DisplayType.TableDir)
		{
			return MQuery.getZoomTableName(column.getColumnName());
		}
		return null;
	}

	public ImmutableList<ReferenceFieldInfo> scanReferenceFieldsForTable(
			@NonNull final String tableName,
			@NonNull final ImmutableSet<String> excludedColumns)
	{
		final POInfo poInfo = POInfo.getPOInfoNotNull(tableName);
		final ImmutableList.Builder<ReferenceFieldInfo> results = ImmutableList.builder();

		for (int i = 0; i < poInfo.getColumnCount(); i++)
		{
			final POInfoColumn column = poInfo.getColumn(i);
			if (column == null)
			{
				continue;
			}

			final int displayType = column.getDisplayType();
			if (displayType != DisplayType.Table && displayType != DisplayType.TableDir && displayType != DisplayType.Search)
			{
				continue;
			}

			final String columnName = column.getColumnName();
			if (excludedColumns.contains(columnName))
			{
				continue;
			}

			final String targetTableName = resolveTargetTableName(column);
			if (targetTableName == null)
			{
				results.add(ReferenceFieldInfo.builder()
						.sourceTableName(tableName)
						.sourceColumnName(columnName)
						.targetTableName("UNRESOLVED")
						.build());
				continue;
			}

			final Optional<AdWindowId> windowId = RecordWindowFinder.findAdWindowId(targetTableName);
			final String windowName = windowId.map(this::getWindowName).orElse(null);

			results.add(ReferenceFieldInfo.builder()
					.sourceTableName(tableName)
					.sourceColumnName(columnName)
					.targetTableName(targetTableName)
					.resolvedWindowId(windowId.orElse(null))
					.resolvedWindowName(windowName)
					.build());
		}

		return results.build();
	}

	public ImmutableList<ReferenceFieldInfo> verifyZoomToForRecord(
			@NonNull final PO record,
			@NonNull final ImmutableSet<String> excludedColumns)
	{
		final String tableName = record.get_TableName();
		final POInfo poInfo = POInfo.getPOInfoNotNull(tableName);
		final ImmutableList.Builder<ReferenceFieldInfo> results = ImmutableList.builder();

		for (int i = 0; i < poInfo.getColumnCount(); i++)
		{
			final POInfoColumn column = poInfo.getColumn(i);
			if (column == null)
			{
				continue;
			}

			final int displayType = column.getDisplayType();
			if (displayType != DisplayType.Table && displayType != DisplayType.TableDir && displayType != DisplayType.Search)
			{
				continue;
			}

			final String columnName = column.getColumnName();
			if (excludedColumns.contains(columnName))
			{
				continue;
			}

			final Object value = record.get_Value(columnName);
			if (value == null)
			{
				continue; // skip null FK values
			}

			final int recordId;
			if (value instanceof Number)
			{
				recordId = ((Number)value).intValue();
			}
			else
			{
				continue; // not a numeric FK
			}

			if (recordId <= 0)
			{
				continue; // skip non-positive IDs
			}

			final String targetTableName = resolveTargetTableName(column);
			if (targetTableName == null)
			{
				results.add(ReferenceFieldInfo.builder()
						.sourceTableName(tableName)
						.sourceColumnName(columnName)
						.targetTableName("UNRESOLVED")
						.build());
				continue;
			}

			final Optional<AdWindowId> windowId = RecordWindowFinder.newInstance(targetTableName, recordId)
					.checkRecordPresentInWindow()
					.findAdWindowId();
			final String windowName = windowId.map(this::getWindowName).orElse(null);

			results.add(ReferenceFieldInfo.builder()
					.sourceTableName(tableName)
					.sourceColumnName(columnName)
					.targetTableName(targetTableName)
					.resolvedWindowId(windowId.orElse(null))
					.resolvedWindowName(windowName)
					.build());
		}

		return results.build();
	}
}

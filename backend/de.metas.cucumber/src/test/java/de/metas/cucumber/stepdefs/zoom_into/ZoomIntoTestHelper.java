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
import org.compiere.model.PO;
import org.compiere.util.DB;

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
}

package de.metas.error;

import java.util.List;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Issue;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;

import de.metas.document.references.IZoomProvider;
import de.metas.document.references.IZoomSource;
import de.metas.document.references.RecordZoomWindowFinder;
import de.metas.document.references.ZoomInfoCandidate;
import de.metas.document.references.ZoomInfoId;
import de.metas.document.references.ZoomTargetWindow;
import de.metas.i18n.ITranslatableString;
import de.metas.util.Services;
import de.metas.util.lang.Priority;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class AdIssueZoomProvider implements IZoomProvider
{
	private final IErrorManager errorManager = Services.get(IErrorManager.class);
	private final IADReferenceDAO adReferenceDAO = Services.get(IADReferenceDAO.class);

	private final Priority zoomInfoPriority = Priority.HIGHEST;
	private final boolean onlyNotAcknowledged = true;

	@Override
	public List<ZoomInfoCandidate> retrieveZoomInfos(
			@NonNull final IZoomSource source,
			@Nullable final AdWindowId targetWindowId)
	{
		//
		// Get the Issues AD_Window_ID
		final AdWindowId issuesWindowId = RecordZoomWindowFinder.findAdWindowId(I_AD_Issue.Table_Name).orElse(null);
		if (issuesWindowId == null)
		{
			return ImmutableList.of();
		}

		// If not our target window ID, return nothing
		if (targetWindowId != null && !AdWindowId.equals(targetWindowId, issuesWindowId))
		{
			return ImmutableList.of();
		}

		final TableRecordReference recordRef = TableRecordReference.of(source.getAD_Table_ID(), source.getRecord_ID());

		final Supplier<IssueCountersByCategory> issueCountersSupplier = getIssueCountersByCategorySupplier(recordRef);

		final ImmutableList.Builder<ZoomInfoCandidate> result = ImmutableList.builder();
		for (final IssueCategory issueCategory : IssueCategory.values())
		{
			final ZoomInfoId id = ZoomInfoId.ofString("issues-" + issueCategory.getCode());
			final ITranslatableString issueCategoryDisplayName = adReferenceDAO.retrieveListNameTranslatableString(IssueCategory.AD_REFERENCE_ID, issueCategory.getCode());
			final IntSupplier recordsCountSupplier = () -> issueCountersSupplier.get().getCountOrZero(issueCategory);

			result.add(ZoomInfoCandidate.builder()
					.id(id)
					.internalName(id.toJson())
					.targetWindow(ZoomTargetWindow.ofAdWindowIdAndCategory(issuesWindowId, issueCategory, issueCategoryDisplayName))
					.priority(zoomInfoPriority)
					.query(createMQuery(recordRef, issueCategory))
					.destinationDisplay(issueCategoryDisplayName)
					.recordsCountSupplier(recordsCountSupplier)
					.build());
		}

		return result.build();
	}

	private Supplier<IssueCountersByCategory> getIssueCountersByCategorySupplier(@NonNull final TableRecordReference recordRef)
	{
		return Suppliers.memoize(() -> errorManager.getIssueCountersByCategory(recordRef, onlyNotAcknowledged));
	}

	private MQuery createMQuery(@NonNull final TableRecordReference recordRef, @NonNull final IssueCategory issueCategory)
	{
		final MQuery query = new MQuery(I_AD_Issue.Table_Name);
		query.addRestriction(I_AD_Issue.COLUMNNAME_AD_Table_ID, Operator.EQUAL, recordRef.getAD_Table_ID());
		query.addRestriction(I_AD_Issue.COLUMNNAME_Record_ID, Operator.EQUAL, recordRef.getRecord_ID());
		query.addRestriction(I_AD_Issue.COLUMNNAME_IssueCategory, Operator.EQUAL, issueCategory.getCode());
		if (onlyNotAcknowledged)
		{
			query.addRestriction(I_AD_Issue.COLUMNNAME_Processed, Operator.EQUAL, false);
		}

		return query;
	}
}

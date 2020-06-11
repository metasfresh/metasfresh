package de.metas.error;

import java.util.List;
import java.util.function.IntSupplier;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Issue;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;

import com.google.common.collect.ImmutableList;

import de.metas.document.references.IZoomProvider;
import de.metas.document.references.IZoomSource;
import de.metas.document.references.RecordZoomWindowFinder;
import de.metas.document.references.ZoomInfoCandidate;
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
	private final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final Priority zoomInfoPriority = Priority.HIGHEST;

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

		//
		// Build query and check count if needed
		final MQuery query = new MQuery(I_AD_Issue.Table_Name);
		query.addRestriction(I_AD_Issue.COLUMNNAME_AD_Table_ID, Operator.EQUAL, source.getAD_Table_ID());
		query.addRestriction(I_AD_Issue.COLUMNNAME_Record_ID, Operator.EQUAL, source.getRecord_ID());

		final ITranslatableString destinationDisplay = adWindowDAO.retrieveWindowName(issuesWindowId);

		final IntSupplier recordsCountSupplier = createRecordsCountSupplier(source);

		return ImmutableList.of(
				ZoomInfoCandidate.builder()
						.id(I_AD_Issue.Table_Name)
						.internalName(I_AD_Issue.Table_Name)
						.adWindowId(issuesWindowId)
						.priority(zoomInfoPriority)
						.query(query)
						.destinationDisplay(destinationDisplay)
						.recordsCountSupplier(recordsCountSupplier)
						.build());
	}

	private IntSupplier createRecordsCountSupplier(@NonNull final IZoomSource source)
	{
		final IQuery<I_AD_Issue> query = queryBL.createQueryBuilder(I_AD_Issue.class)
				.addEqualsFilter(I_AD_Issue.COLUMNNAME_AD_Table_ID, source.getAD_Table_ID())
				.addEqualsFilter(I_AD_Issue.COLUMNNAME_Record_ID, source.getRecord_ID())
				.create();

		return () -> query.count();
	}
}

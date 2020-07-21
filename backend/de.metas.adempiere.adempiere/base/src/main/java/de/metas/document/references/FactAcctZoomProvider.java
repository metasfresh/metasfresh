package de.metas.document.references;

import java.util.List;
import java.util.function.IntSupplier;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.MQuery;
import org.compiere.model.MQuery.Operator;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import de.metas.util.Services;
import de.metas.util.lang.Priority;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

class FactAcctZoomProvider implements IZoomProvider
{
	public static final transient FactAcctZoomProvider instance = new FactAcctZoomProvider();
	private static final String COLUMNNAME_Posted = "Posted";

	private final Priority zoomInfoPriority = Priority.HIGHEST;

	private FactAcctZoomProvider()
	{
	}

	@Override
	public List<ZoomInfoCandidate> retrieveZoomInfos(
			@NonNull final IZoomSource source,
			@Nullable final AdWindowId targetWindowId)
	{
		//
		// Get the Fact_Acct AD_Window_ID
		final AdWindowId factAcctWindowId = RecordZoomWindowFinder.findAdWindowId(I_Fact_Acct.Table_Name).orElse(null);
		if (factAcctWindowId == null)
		{
			return ImmutableList.of();
		}

		// If not our target window ID, return nothing
		if (targetWindowId != null && !AdWindowId.equals(targetWindowId, factAcctWindowId))
		{
			return ImmutableList.of();
		}

		// Return nothing if source is not Posted
		if (source.hasField(COLUMNNAME_Posted))
		{
			final boolean posted = source.getFieldValueAsBoolean(COLUMNNAME_Posted);
			if (!posted)
			{
				return ImmutableList.of();
			}
		}

		//
		// Build query and check count if needed
		final MQuery query = new MQuery(I_Fact_Acct.Table_Name);
		query.addRestriction(I_Fact_Acct.COLUMNNAME_AD_Table_ID, Operator.EQUAL, source.getAD_Table_ID());
		query.addRestriction(I_Fact_Acct.COLUMNNAME_Record_ID, Operator.EQUAL, source.getRecord_ID());

		final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);
		final ITranslatableString destinationDisplay = adWindowDAO.retrieveWindowName(factAcctWindowId);

		final IntSupplier recordsCountSupplier = createRecordsCountSupplier(source);

		return ImmutableList.of(
				ZoomInfoCandidate.builder()
						.id(ZoomInfoId.ofString(I_Fact_Acct.Table_Name))
						.internalName(I_Fact_Acct.Table_Name)
						.targetWindow(ZoomTargetWindow.ofAdWindowId(factAcctWindowId))
						.priority(zoomInfoPriority)
						.query(query)
						.destinationDisplay(destinationDisplay)
						.recordsCountSupplier(recordsCountSupplier)
						.build());
	}

	private static IntSupplier createRecordsCountSupplier(final IZoomSource source)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final int adTableId = source.getAD_Table_ID();
		final int recordId = source.getRecord_ID();
		return () -> queryBL.createQueryBuilder(I_Fact_Acct.class)
				.addEqualsFilter(I_Fact_Acct.COLUMN_AD_Table_ID, adTableId)
				.addEqualsFilter(I_Fact_Acct.COLUMN_Record_ID, recordId)
				.create()
				.count();
	}

}

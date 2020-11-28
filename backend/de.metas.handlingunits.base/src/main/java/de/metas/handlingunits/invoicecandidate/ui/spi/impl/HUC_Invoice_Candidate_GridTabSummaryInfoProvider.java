/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.invoicecandidate.ui.spi.impl;

import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.impl.InvoiceCandidatesAmtSelectionSummary;
import de.metas.util.Services;
import org.adempiere.ui.api.IGridTabSummaryInfo;
import org.adempiere.ui.spi.IGridTabSummaryInfoProvider;
import org.compiere.model.GridTab;
import org.compiere.model.GridTable;

/**
 * Provides the summary message which is displayed at the window's bottom, when we deal with invoice candidates tab.
 *
 * @author al
 */
public class HUC_Invoice_Candidate_GridTabSummaryInfoProvider implements IGridTabSummaryInfoProvider
{

	final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

	@Override
	public final IGridTabSummaryInfo getSummaryInfo(final GridTab gridTab)
	{
		if (gridTab == null)
		{
			return IGridTabSummaryInfo.NULL;
		}

		final GridTable gridTable = gridTab.getMTable();
		final String icWhereClause = gridTable.getSelectWhereClauseFinal();

		final HUInvoiceCandidatesSelectionSummaryInfo summary = new HUInvoiceCandidatesSelectionSummaryInfo(getInvoiceCandidatesSelectionSummary(icWhereClause));
		return summary;
	}

	public final InvoiceCandidatesAmtSelectionSummary getInvoiceCandidatesSelectionSummary(final String icWhereClause)
	{
		return invoiceCandBL.calculateAmtSelectionSummary(icWhereClause);
	}
}

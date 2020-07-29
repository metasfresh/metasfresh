/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.view;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.TranslatableStrings;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.impl.InvoiceCandidatesAmtSelectionSummary;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.ui.web.view.descriptor.SqlAndParams;
import de.metas.ui.web.view.descriptor.SqlViewRowsWhereClause;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.model.sql.SqlOptions;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderCandidateViewHeaderPropertiesProvider implements ViewHeaderPropertiesProvider
{

	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

	@Override
	public String getAppliesOnlyToTableName()
	{
		return I_C_Invoice_Candidate.Table_Name;
	}

	@Override
	public @NonNull ViewHeaderProperties computeHeaderProperties(@NonNull final IView view)
	{
		return ViewHeaderProperties.builder()
				.groups(
						ImmutableList.<ViewHeaderPropertiesGroup>builder()
								.addAll(computeDummyDataForTesting())
								.addAll(computeRealData(view))
								.build()
				)
				.build();
	}

	private List<ViewHeaderPropertiesGroup> computeRealData(final @NonNull IView view)
	{
		/*
			Implementation detail
			The `view.size()` call is needed because the rows in table `T_WEBUI_ViewSelection` are lazily inserted, on the first view usage.
			If this call is not done, querying from the view will return 0 rows.
		 */
		final long rowsToDisplay = view.size();

		if (rowsToDisplay==0)
		{
			return ImmutableList.of();
		}

		final SqlViewRowsWhereClause sqlWhereClause = view.getSqlWhereClause(DocumentIdsSelection.ALL, SqlOptions.usingTableName(I_C_Invoice_Candidate.Table_Name));
		final SqlAndParams sqlAndParams = SqlAndParams.of(sqlWhereClause.toSqlString());
		final InvoiceCandidatesAmtSelectionSummary summary = invoiceCandBL.calculateSummary(sqlAndParams.getSql());

		return toViewHeaderProperties(summary);
	}

	private List<ViewHeaderPropertiesGroup> toViewHeaderProperties(final InvoiceCandidatesAmtSelectionSummary summary)
	{
		 // TODO tbp: implement this similar to HUInvoiceCandidatesSelectionSummaryInfo.getSummaryMessageTranslated
		return ImmutableList.of();
	}

	@NonNull
	private List<ViewHeaderPropertiesGroup> computeDummyDataForTesting()
	{
		return ImmutableList.of(
				ViewHeaderPropertiesGroup.builder()
						.entry(ViewHeaderProperty.builder()
								.caption(TranslatableStrings.constant("Net (Approved for Invoicing)"))
								.value("123.456 EUR")
								.build())
						.entry(ViewHeaderProperty.builder()
								.caption(TranslatableStrings.constant("Trading Unit"))
								.value("100.456 EUR")
								.build())
						.entry(ViewHeaderProperty.builder()
								.caption(TranslatableStrings.constant("Not Trading Unit"))
								.value("23 EUR")
								.build())
						.build(),
				ViewHeaderPropertiesGroup.builder()
						.entry(ViewHeaderProperty.builder()
								.caption(TranslatableStrings.constant("Net (Ohne Freigabe)"))
								.value("987.654 EUR")
								.build())
						.entry(ViewHeaderProperty.builder()
								.caption(TranslatableStrings.constant("Trading Unit"))
								.value("987 EUR")
								.build())
						.entry(ViewHeaderProperty.builder()
								.caption(TranslatableStrings.constant("Not Trading Unit"))
								.value("0.654 EUR")
								.build())
						.build(),

				ViewHeaderPropertiesGroup.builder()
						.entry(ViewHeaderProperty.builder()
								.caption(TranslatableStrings.constant("Net (Approved for Invoicing)"))
								.value("123.456 CHF")
								.build())
						.entry(ViewHeaderProperty.builder()
								.caption(TranslatableStrings.constant("Trading Unit"))
								.value("100.456 CHF")
								.build())
						.entry(ViewHeaderProperty.builder()
								.caption(TranslatableStrings.constant("Not Trading Unit"))
								.value("23 CHF")
								.build())
						.build(),

				ViewHeaderPropertiesGroup.builder()
						.entry(ViewHeaderProperty.builder()
								.caption(TranslatableStrings.constant("Net (Ohne Freigabe)"))
								.value("987.654 CHF")
								.build())
						.entry(ViewHeaderProperty.builder()
								.caption(TranslatableStrings.constant("Trading Unit"))
								.value("987 CHF")
								.build())
						.entry(ViewHeaderProperty.builder()
								.caption(TranslatableStrings.constant("Not Trading Unit"))
								.value("0.654 CHF")
								.build())
						.build()
		);
	}

}

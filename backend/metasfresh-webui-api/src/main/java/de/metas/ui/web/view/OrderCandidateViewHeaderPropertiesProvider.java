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
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderCandidateViewHeaderPropertiesProvider implements ViewHeaderPropertiesProvider
{
	@Override
	public String getAppliesOnlyToTableName()
	{
		return I_C_Invoice_Candidate.Table_Name;
	}

	@Override
	public @NonNull ViewHeaderProperties computeHeaderProperties(@NonNull final IView view)
	{
		return ViewHeaderProperties.builder()
				.entries(
						ImmutableList.<ViewHeaderProperty>builder()
								.addAll(computeDummyDataForTesting())
								.addAll(computeRealData())
								.build()
				)
				.build();
	}

	private List<ViewHeaderProperty> computeRealData()
	{
		return ImmutableList.of();
	}

	@NonNull
	private List<ViewHeaderProperty> computeDummyDataForTesting()
	{
		return ImmutableList.of(
				ViewHeaderProperty.builder()
						.caption(TranslatableStrings.constant("Net (Approved for Invoicing)"))
						.value("123.456 EUR")
						.build(),
				ViewHeaderProperty.builder()
						.caption(TranslatableStrings.constant("Trading Unit"))
						.value("100.456 EUR")
						.build(),
				ViewHeaderProperty.builder()
						.caption(TranslatableStrings.constant("Not Trading Unit"))
						.value("23 EUR")
						.build(),
				ViewHeaderProperty.builder()
						.caption(TranslatableStrings.constant("||"))
						.value("")
						.build(),
				ViewHeaderProperty.builder()
						.caption(TranslatableStrings.constant("Net (Ohne Freigabe)"))
						.value("987.654 EUR")
						.build(),
				ViewHeaderProperty.builder()
						.caption(TranslatableStrings.constant("Trading Unit"))
						.value("987 EUR")
						.build(),
				ViewHeaderProperty.builder()
						.caption(TranslatableStrings.constant("Not Trading Unit"))
						.value("0.654 EUR")
						.build(),
				ViewHeaderProperty.builder()
						.caption(TranslatableStrings.constant("||"))
						.value("")
						.build()
		);
	}

}

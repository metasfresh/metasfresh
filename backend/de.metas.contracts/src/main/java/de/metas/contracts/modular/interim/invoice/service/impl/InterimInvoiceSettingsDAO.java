/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.modular.interim.invoice.service.impl;

import de.metas.calendar.standard.CalendarId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.interim.invoice.InterimInvoiceSettings;
import de.metas.contracts.modular.interim.invoice.InterimInvoiceSettingsId;
import de.metas.contracts.modular.interim.invoice.service.IInterimInvoiceSettingsDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Interim_Invoice_Settings;

import javax.annotation.Nullable;

public class InterimInvoiceSettingsDAO implements IInterimInvoiceSettingsDAO
{
	IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	@Nullable
	public InterimInvoiceSettings getById(final @NonNull InterimInvoiceSettingsId id)
	{
		final I_C_Interim_Invoice_Settings interimInvoiceSettings = InterfaceWrapperHelper.load(id, I_C_Interim_Invoice_Settings.class);
		return interimInvoiceSettings == null ? null : fromDB(interimInvoiceSettings);
	}

	private InterimInvoiceSettings fromDB(@NonNull final I_C_Interim_Invoice_Settings settings)
	{
		return InterimInvoiceSettings.builder()
				.id(InterimInvoiceSettingsId.ofRepoId(settings.getC_Interim_Invoice_Settings_ID()))
				.harvestingCalendarId(CalendarId.ofRepoId(settings.getC_Harvesting_Calendar_ID()))
				.withholdingProductId(ProductId.ofRepoId(settings.getM_Product_Witholding_ID()))
				.build();
	}

	@Override
	@Nullable
	public InterimInvoiceSettings getForTerm(final @NonNull FlatrateTermId id)
	{
		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID, id)
				.andCollect(I_C_Flatrate_Conditions.COLUMN_C_Flatrate_Conditions_ID, I_C_Flatrate_Conditions.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Conditions.COLUMNNAME_Type_Conditions, TypeConditions.INTERIM_INVOICE.getCode())
				.andCollect(I_C_Flatrate_Conditions.COLUMNNAME_C_Interim_Invoice_Settings_ID, I_C_Interim_Invoice_Settings.class)
				.orderByDescending(I_C_Interim_Invoice_Settings.COLUMN_Created)
				.create()
				.firstOptional()
				.map(this::fromDB)
				.orElse(null);
	}
}

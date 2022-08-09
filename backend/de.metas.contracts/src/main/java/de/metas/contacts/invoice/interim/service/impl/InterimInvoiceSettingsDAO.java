/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.contacts.invoice.interim.service.impl;

import de.metas.calendar.CalendarId;
import de.metas.contacts.invoice.interim.InterimInvoiceSettings;
import de.metas.contacts.invoice.interim.InterimInvoiceSettingsId;
import de.metas.contacts.invoice.interim.service.IInterimInvoiceSettingsDAO;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Interim_Invoice_Settings;

import javax.annotation.Nullable;

public class InterimInvoiceSettingsDAO implements IInterimInvoiceSettingsDAO
{
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
}

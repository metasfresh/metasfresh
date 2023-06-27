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

package de.metas.contacts.invoice.interim.service;

import de.metas.contacts.invoice.interim.InterimInvoiceSettings;
import de.metas.contacts.invoice.interim.InterimInvoiceSettingsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.util.ISingletonService;
import lombok.NonNull;

import javax.annotation.Nullable;

public interface IInterimInvoiceSettingsDAO extends ISingletonService
{
	InterimInvoiceSettings getById(@NonNull final InterimInvoiceSettingsId id);

	@Nullable
	InterimInvoiceSettings getForTerm(@NonNull FlatrateTermId id);
}

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.invoice.acct;

import de.metas.bpartner.BPartnerId;
import de.metas.calendar.standard.CalendarId;
import de.metas.calendar.standard.YearId;
import de.metas.document.DocTypeId;
import de.metas.invoice.InvoiceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import java.util.HashSet;

@Value
@Builder
public class InvoiceSearchQuery
{

	@NonNull HashSet<InvoiceId> invoiceRepoIds;
	@NonNull HashSet<BPartnerId> bpartnerRepoIds;
	@NonNull HashSet<Integer> bpartnerLocationRepoIds;
	@NonNull HashSet<Integer> bpartnerContactRepoIds;
	@NonNull HashSet<DocTypeId> docTypeRepoIds;
	@NonNull HashSet<WarehouseId> warehouseRepoIds;
	@NonNull HashSet<CalendarId> calendarRepoIds;
	@NonNull HashSet<YearId> yearRepoIds;
}

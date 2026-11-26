/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.invoice.vatid;

import de.metas.organization.FiscalRepresentationId;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * The VAT IDs of an invoice. A {@code null} seller VAT ID means the organization's main VAT ID.
 */
@Value
public class InvoiceVATaxIds
{
	@Nullable String buyerVATaxId;
	@Nullable String sellerVATaxId;
	@Nullable FiscalRepresentationId fiscalRepresentationId;
}

package de.metas.contracts.commission.commissioninstance.businesslogic.sales;

/*
 * #%L
 * de.metas.commission
 * %%
 * Copyright (C) 2019 metas GmbH
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

/** Please keep in sync with the respective values of {@code AD_Reference_ID=541042}. */
public enum SalesCommissionState
{
	/** Related to an invoice candidate's open (i.e. not-yet-invoiced) QtyOrdered. */
	FORECASTED,

	/** Related to an invoice candidate's QtyToInvoice. */
	INVOICEABLE,

	/** Related to an invoice candidate's QtyInvoiced. */
	INVOICED;
}

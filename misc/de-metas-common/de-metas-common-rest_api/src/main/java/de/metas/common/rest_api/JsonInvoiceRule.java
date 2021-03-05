/*
 * #%L
 * de-metas-common-rest_api
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.common.rest_api;

import de.pentabyte.springfox.ApiEnum;

public enum JsonInvoiceRule
{
	@ApiEnum("Specifies that only *delivered* quantities will be invoiced")
	AfterDelivery,

	@ApiEnum("Like `AfterDelivery`, but the invoicing date is also set according to the respective bill partner's invoicing schedule (e.g. once per month)")
	CustomerScheduleAfterDelivery,

	@ApiEnum("Specifies that no invoicing should take place until all quantities belonging to the same invoice have been shipped.\nNote: what belongs to one invoice is determined by the respective business partner's aggregation rule.")
	OrderCompletelyDelivered,

	@ApiEnum("Any ordered quantities - delivered or not - can be invoiced right away")
	Immediate;
}

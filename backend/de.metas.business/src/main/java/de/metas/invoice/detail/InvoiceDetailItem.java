/*
 * #%L
 * de.metas.business
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

package de.metas.invoice.detail;

import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@Builder
public class InvoiceDetailItem
{
	Integer seqNo;

	String label;

	String description;

	@Nullable
	LocalDate date;

	@Nullable
	BigDecimal price;

	@Nullable Quantity qty;

	String note;

	OrgId orgId;

	/**
	 * @return true if label and description, date and price are blank/null, false otherwise.
	 */
	public boolean isEmpty()
	{
		return StringUtils.isBlank(label) && (StringUtils.isBlank(description) && price == null && date == null );
	}
}

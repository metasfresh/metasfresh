/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.bpartner.bpartnercomposite;

import de.metas.common.rest_api.v2.JsonInvoiceRule;
import de.metas.order.InvoiceRule;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@UtilityClass
public class BPartnerCompositeRestUtils
{
	@Nullable
	public static InvoiceRule getInvoiceRule(@Nullable final JsonInvoiceRule jsonInvoiceRule)
	{
		if (jsonInvoiceRule == null)
		{
			return null;
		}
		switch (jsonInvoiceRule)
		{
			case AfterDelivery:
				return InvoiceRule.AfterDelivery;
			case CustomerScheduleAfterDelivery:
				return InvoiceRule.CustomerScheduleAfterDelivery;
			case Immediate:
				return InvoiceRule.Immediate;
			case OrderCompletelyDelivered:
				return InvoiceRule.OrderCompletelyDelivered;
			default:
				throw new AdempiereException("Unsupported JsonInvoiceRule " + jsonInvoiceRule);
		}
	}
}

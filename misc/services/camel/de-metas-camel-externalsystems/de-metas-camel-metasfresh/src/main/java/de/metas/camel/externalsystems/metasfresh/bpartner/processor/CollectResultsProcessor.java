/*
 * #%L
 * de-metas-camel-metasfresh
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

package de.metas.camel.externalsystems.metasfresh.bpartner.processor;

import de.metas.common.bpartner.v2.response.JsonResponseBPartnerCompositeUpsert;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import static de.metas.camel.externalsystems.metasfresh.MetasfreshConstants.RESPONSE_ITEMS_NO_PROPERTY;

public class CollectResultsProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange)
	{
		final Integer responseItemsNo = exchange.getProperty(RESPONSE_ITEMS_NO_PROPERTY, Integer.class);
		final JsonResponseBPartnerCompositeUpsert bPartnerUpsertResponseList = exchange.getIn().getBody(JsonResponseBPartnerCompositeUpsert.class);
		final int newItemCount;
		if (responseItemsNo == null)
		{
			newItemCount = bPartnerUpsertResponseList.getResponseItems().size();
		}
		else
		{
			newItemCount = responseItemsNo + bPartnerUpsertResponseList.getResponseItems().size();
		}
		
		exchange.setProperty(RESPONSE_ITEMS_NO_PROPERTY, newItemCount);
	}
}

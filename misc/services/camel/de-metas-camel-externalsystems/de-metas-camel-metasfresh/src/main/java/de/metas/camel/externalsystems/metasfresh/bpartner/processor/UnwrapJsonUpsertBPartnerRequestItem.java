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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import de.metas.camel.externalsystems.metasfresh.bpartner.model.JsonBPartnerItemBody;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.RuntimeCamelException;

import static de.metas.camel.externalsystems.metasfresh.MetasfreshConstants.PARSER_PROPERTY;

public class UnwrapJsonUpsertBPartnerRequestItem implements Processor
{
	/**
	 * Expecting parser source to be {@link de.metas.camel.externalsystems.metasfresh.bpartner.model.JsonBPartnerItemBody}.
	 */
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final JsonParser parser = exchange.getIn().getBody(JsonParser.class);

		try
		{
			if (parser.nextToken() != JsonToken.START_OBJECT)
			{
				parser.close();
				throw new RuntimeCamelException("Error parsing the request: next token should be object");
			}

			parser.nextToken();
			final String fieldName = parser.getCurrentName();
			if (!fieldName.equals(JsonBPartnerItemBody.FIELD_NAME_REQUEST_ITEMS))
			{
				throw new RuntimeCamelException("Error parsing the request: next fieldName shall be " + JsonBPartnerItemBody.FIELD_NAME_REQUEST_ITEMS);
			}

			if (parser.nextToken() == JsonToken.START_ARRAY)
			{
				exchange.setProperty(PARSER_PROPERTY, parser);
			}
			else
			{
				throw new RuntimeCamelException("Error parsing the request: next token shall be " + JsonToken.START_ARRAY);
			}
		}
		catch (final Exception exception)
		{
			throw new RuntimeCamelException("Could not parse the Json!" + exception.getMessage());
		}
	}
}

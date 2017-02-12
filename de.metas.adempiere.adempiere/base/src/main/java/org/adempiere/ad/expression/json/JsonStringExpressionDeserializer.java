package org.adempiere.ad.expression.json;

import java.io.IOException;

import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.util.Services;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class JsonStringExpressionDeserializer extends JsonDeserializer<IStringExpression>
{

	@Override
	public IStringExpression deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException, JsonProcessingException
	{
		final String stringExpressionStr = p.readValueAs(String.class);
		return Services.get(IExpressionFactory.class).compile(stringExpressionStr, IStringExpression.class);
	}

}

/*
 * #%L
 * de.metas.business
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

package de.metas.quantity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class QuantitySerializer extends StdSerializer<Quantity>
{
	private static final long serialVersionUID = -8292209848527230256L;

	public QuantitySerializer()
	{
		super(Quantity.class);
	}

	@Override
	public void serialize(final Quantity value, final JsonGenerator gen, final SerializerProvider provider) throws IOException
	{
		gen.writeStartObject();

		final String qtyStr = value.toBigDecimal().toString();
		final int uomId = value.getUomId().getRepoId();

		gen.writeFieldName("qty");
		gen.writeString(qtyStr);

		gen.writeFieldName("uomId");
		gen.writeNumber(uomId);

		final String sourceQtyStr = value.getSourceQty().toString();
		final int sourceUomId = value.getSourceUomId().getRepoId();

		if (!qtyStr.equals(sourceQtyStr) || uomId != sourceUomId)
		{
			gen.writeFieldName("sourceQty");
			gen.writeString(sourceQtyStr);

			gen.writeFieldName("sourceUomId");
			gen.writeNumber(sourceUomId);
		}
		gen.writeEndObject();
	}

}

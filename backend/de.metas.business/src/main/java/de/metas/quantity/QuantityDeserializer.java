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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import de.metas.uom.UomId;

import java.io.IOException;
import java.math.BigDecimal;

public class QuantityDeserializer extends StdDeserializer<Quantity>
{
	private static final long serialVersionUID = -5406622853902102217L;

	public QuantityDeserializer()
	{
		super(Quantity.class);
	}

	@Override
	public Quantity deserialize(final JsonParser p, final DeserializationContext ctx) throws IOException
	{
		final JsonNode node = p.getCodec().readTree(p);
		final String qtyStr = node.get("qty").asText();
		final int uomRepoId = (Integer)node.get("uomId").numberValue();

		final String sourceQtyStr;
		final int sourceUomRepoId;
		if (node.has("sourceQty"))
		{
			sourceQtyStr = node.get("sourceQty").asText();
			sourceUomRepoId = (Integer)node.get("sourceUomId").numberValue();
		}
		else
		{
			sourceQtyStr = qtyStr;
			sourceUomRepoId = uomRepoId;
		}
		return Quantitys.of(
				new BigDecimal(qtyStr), UomId.ofRepoId(uomRepoId),
				new BigDecimal(sourceQtyStr), UomId.ofRepoId(sourceUomRepoId));
	}
}

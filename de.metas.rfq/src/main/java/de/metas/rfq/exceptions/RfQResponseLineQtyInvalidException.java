package de.metas.rfq.exceptions;

import de.metas.rfq.model.I_C_RfQResponse;
import de.metas.rfq.model.I_C_RfQResponseLine;
import de.metas.rfq.model.I_C_RfQResponseLineQty;

/*
 * #%L
 * de.metas.rfq
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class RfQResponseLineQtyInvalidException extends RfQException
{
	private static final long serialVersionUID = -357274733502388653L;

	public RfQResponseLineQtyInvalidException(final I_C_RfQResponseLineQty rfqResponseLineQty, final String message)
	{
		super(buildMsg(rfqResponseLineQty, message));
	}

	private static String buildMsg(final I_C_RfQResponseLineQty rfqResponseLineQty, final String message)
	{
		final I_C_RfQResponseLine rfqResponseLine = rfqResponseLineQty.getC_RfQResponseLine();
		final I_C_RfQResponse rfqResponse = rfqResponseLine.getC_RfQResponse();

		return new StringBuilder()
				.append("@" + I_C_RfQResponse.COLUMNNAME_C_RfQResponse_ID + "@")
				.append(" '").append(rfqResponse.getName() + "'")
				.append(", @Line@ ").append(rfqResponseLine.getC_RfQLine().getLine())
				.append(", @Qty@=" + rfqResponseLineQty.getC_RfQLineQty().getQty())
				.append(": ").append(message)
				.toString();
	}

}

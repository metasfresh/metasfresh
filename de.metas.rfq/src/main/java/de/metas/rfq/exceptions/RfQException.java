package de.metas.rfq.exceptions;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;

import de.metas.rfq.IRfqBL;
import de.metas.rfq.model.I_C_RfQ;
import de.metas.rfq.model.I_C_RfQLine;
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

/**
 * Base exception class for RfQ module
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class RfQException extends AdempiereException
{
	private static final long serialVersionUID = -8279948147175847410L;

	public RfQException(final String message, final Throwable cause)
	{
		super(message, cause);
	}

	public RfQException(final String message)
	{
		super(message);
	}

	protected static String buildInfoString(final I_C_RfQ rfq)
	{
		return Services.get(IRfqBL.class).getSummary(rfq);
	}

	protected static String buildInfoString(final I_C_RfQLine rfqLine)
	{
		if (rfqLine == null)
		{
			return "?";
		}

		final I_C_RfQ rfq = rfqLine.getC_RfQ();
		return new StringBuilder()
				.append(buildInfoString(rfq))
				.append(", @Line@ ").append(rfqLine.getLine())
				.toString();

	}

	protected static String buildInfoString(final I_C_RfQResponse rfqResponse)
	{
		return Services.get(IRfqBL.class).getSummary(rfqResponse);
	}

	protected static String buildInfoString(final I_C_RfQResponseLine rfqResponseLine)
	{
		if (rfqResponseLine == null)
		{
			return "?";
		}

		return new StringBuilder()
				.append(buildInfoString(rfqResponseLine.getC_RfQResponse()))
				.append(", @Line@ ").append(rfqResponseLine.getLine())
				.toString();
	}

	protected static String buildInfoString(final I_C_RfQResponseLineQty rfqResponseLineQty)
	{
		if (rfqResponseLineQty == null)
		{
			return "?";
		}

		return new StringBuilder()
				.append(buildInfoString(rfqResponseLineQty.getC_RfQResponseLine()))
				.append(", @Qty@=").append(rfqResponseLineQty.getQtyPromised())
				.toString();
	}
}

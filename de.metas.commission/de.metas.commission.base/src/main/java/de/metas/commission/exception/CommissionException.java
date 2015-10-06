package de.metas.commission.exception;

/*
 * #%L
 * de.metas.commission.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.util.Arrays;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import de.metas.commission.util.Messages;

public class CommissionException extends AdempiereException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5809325557657286742L;

	private final String adMessage;
	private final Object[] msgParams;

	public CommissionException(final String adMessage, final Object... msgParams)
	{
		super("");
		this.adMessage = adMessage;
		this.msgParams = msgParams;
	}

	public CommissionException(final String adMessage, final Throwable cause, final Object... msgParams)
	{
		super(cause);
		this.adMessage = adMessage;
		this.msgParams = msgParams;
	}

	public CommissionException(final Throwable cause)
	{
		super(cause);
		adMessage = cause.getMessage();
		msgParams = new Object[0];
	}

	public String getAdMessage()
	{
		return adMessage;
	}

	public Object[] getMsgParams()
	{
		return msgParams;
	}

	@Override
	public String buildMessage()
	{
		String msg = Msg.getMsg(Env.getCtx(), adMessage, msgParams);

		// Our message was not translated
		if (adMessage.equals(msg) && msgParams != null && msgParams.length > 0)
		{
			msg = adMessage + ": " + Arrays.toString(msgParams);
		}

		return msg;
	}

	public static final CommissionException inconsistentData(
			final String description, final Object obj)
	{

		final String objString = obj == null ? "" : obj.toString();

		return new CommissionException(
				Messages.COMMISSION_DATA_INCONSISTENT_2P, new Object[] {
						description, objString });
	}

	public static final CommissionException inconsistentConfig(
			final String description, final Object obj)
	{

		final String objString = obj == null ? "" : obj.toString();

		return new CommissionException(
				Messages.COMMISSION_CONFIG_INCONSISTENT_2P, new Object[] {
						description, objString });
	}
}

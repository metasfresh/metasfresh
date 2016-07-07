package de.metas.email.impl;

import org.adempiere.exceptions.AdempiereException;

import de.metas.email.EMailSentStatus;

/*
 * #%L
 * de.metas.swat.base
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
 * Exception thrown when an email could not be sent.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class EMailSendException extends AdempiereException
{
	private static final long serialVersionUID = -4519372831111638967L;

	private final boolean connectionError;

	public EMailSendException(final EMailSentStatus emailSentStatus)
	{
		super(emailSentStatus.getSentMsg());
		this.connectionError = emailSentStatus.isSentConnectionError();
	}

	/**
	 * @return true if the email could not be sent because there were connection problems
	 */
	public boolean isConnectionError()
	{
		return connectionError;
	}
}

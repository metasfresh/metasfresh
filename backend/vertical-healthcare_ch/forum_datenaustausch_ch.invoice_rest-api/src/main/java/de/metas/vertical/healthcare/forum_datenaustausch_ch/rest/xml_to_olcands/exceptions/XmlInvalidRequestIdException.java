/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_rest-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.vertical.healthcare.forum_datenaustausch_ch.rest.xml_to_olcands.exceptions;

import lombok.Getter;

import javax.annotation.Nullable;

public class XmlInvalidRequestIdException extends RuntimeException
{
	private static final long serialVersionUID = -4688552956794873772L;

	@Getter
	private final String invalidRequestId;

	public XmlInvalidRequestIdException(@Nullable final String invalidRequestId)
	{
		super("Invalid invoice request_id=" + invalidRequestId + "; it has to start with EA_, GM_, KV_ or KT_");
		this.invalidRequestId = invalidRequestId;
	}
}

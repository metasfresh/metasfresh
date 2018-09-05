package de.metas.inbound.mail.config;

import java.util.Map;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.GuavaCollectors;

import de.metas.inbound.mail.model.X_C_InboundMailConfig;
import lombok.Getter;

/*
 * #%L
 * de.metas.inbound.mail
 * %%
 * Copyright (C) 2018 metas GmbH
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

public enum InboundEMailProtocol
{
	IMAP("imap", X_C_InboundMailConfig.PROTOCOL_IMAP), //
	IMAPS("imaps", X_C_InboundMailConfig.PROTOCOL_IMAPS) //
	;

	@Getter
	private String protocolString;
	@Getter
	private String code;

	private InboundEMailProtocol(final String protocolString, final String code)
	{
		this.protocolString = protocolString;
		this.code = code;
	}

	public static InboundEMailProtocol forCode(final String code)
	{
		InboundEMailProtocol type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + InboundEMailProtocol.class + " found for code: " + code);
		}
		return type;
	}

	private static final Map<String, InboundEMailProtocol> typesByCode = Stream.of(values())
			.collect(GuavaCollectors.toImmutableMapByKey(InboundEMailProtocol::getCode));
}

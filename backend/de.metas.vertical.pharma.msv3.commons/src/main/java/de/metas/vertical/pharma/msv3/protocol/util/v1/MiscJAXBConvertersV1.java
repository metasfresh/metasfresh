package de.metas.vertical.pharma.msv3.protocol.util.v1;

import de.metas.vertical.pharma.msv3.protocol.types.FaultInfo;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.Msv3FaultInfo;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.ObjectFactory;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.msv3.commons
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

public class MiscJAXBConvertersV1
{
	private final ObjectFactory jaxbObjectFactory;

	public MiscJAXBConvertersV1()
	{
		this(new ObjectFactory());
	}

	public MiscJAXBConvertersV1(@NonNull final ObjectFactory jaxbObjectFactory)
	{
		this.jaxbObjectFactory = jaxbObjectFactory;
	}

	public static FaultInfo extractFaultInfoOrNull(final Object value)
	{
		if (value instanceof Msv3FaultInfo)
		{
			final Msv3FaultInfo msv3FaultInfo = (Msv3FaultInfo)value;
			return fromJAXB(msv3FaultInfo);
		}
		else
		{
			return null;
		}
	}

	public static final FaultInfo fromJAXB(final Msv3FaultInfo msv3FaultInfo)
	{
		if (msv3FaultInfo == null)
		{
			return null;
		}

		return FaultInfo.builder()
				.errorCode(msv3FaultInfo.getErrorCode())
				.userMessage(msv3FaultInfo.getEndanwenderFehlertext())
				.technicalMessage(msv3FaultInfo.getTechnischerFehlertext())
				.build();
	}

	public final Msv3FaultInfo toJAXB(final FaultInfo faultInfo)
	{
		if (faultInfo == null)
		{
			return null;
		}

		final Msv3FaultInfo soap = jaxbObjectFactory.createMsv3FaultInfo();
		soap.setErrorCode(faultInfo.getErrorCode());
		soap.setEndanwenderFehlertext(faultInfo.getUserMessage());
		soap.setTechnischerFehlertext(faultInfo.getTechnicalMessage());
		return soap;
	}

}

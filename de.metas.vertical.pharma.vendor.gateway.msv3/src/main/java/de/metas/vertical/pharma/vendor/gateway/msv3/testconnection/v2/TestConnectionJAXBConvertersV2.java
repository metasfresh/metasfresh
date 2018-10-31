package de.metas.vertical.pharma.vendor.gateway.msv3.testconnection.v2;

import javax.xml.bind.JAXBElement;

import de.metas.vertical.pharma.msv3.protocol.types.ClientSoftwareId;
import de.metas.vertical.pharma.msv3.protocol.types.FaultInfo;
import de.metas.vertical.pharma.msv3.protocol.util.v2.MiscJAXBConvertersV2;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.ObjectFactory;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.VerbindungTesten;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.VerbindungTestenResponse;
import de.metas.vertical.pharma.vendor.gateway.msv3.testconnection.TestConnectionJAXBConverters;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.msv3
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

public class TestConnectionJAXBConvertersV2 implements TestConnectionJAXBConverters
{
	public static final transient TestConnectionJAXBConvertersV2 instance = new TestConnectionJAXBConvertersV2();

	private final ObjectFactory jaxbObjectFactory = new ObjectFactory();

	private TestConnectionJAXBConvertersV2()
	{
	}

	@Override
	public JAXBElement<?> encodeRequest(@NonNull final ClientSoftwareId clientSoftwareId)
	{

		final VerbindungTesten verbindungTesten = jaxbObjectFactory.createVerbindungTesten();
		verbindungTesten.setClientSoftwareKennung(clientSoftwareId.getValueAsString());

		return jaxbObjectFactory.createVerbindungTesten(verbindungTesten);
	}

	@Override
	public FaultInfo extractFaultInfoOrNull(final Object value)
	{
		return MiscJAXBConvertersV2.extractFaultInfoOrNull(value);
	}

	@Override
	public Class<?> getResponseClass()
	{
		return VerbindungTestenResponse.class;
	}

}

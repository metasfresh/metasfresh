package de.metas.shipper.gateway.go;

import javax.xml.bind.JAXBElement;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.oxm.Marshaller;
import org.springframework.xml.transform.StringResult;

import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * de.metas.shipper.gateway.go
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

@Value
@Builder
public class GOClientLogEvent
{
	int deliveryOrderRepoId;
	GOClientConfig config;

	String action;

	Marshaller jaxbMarshaller;
	JAXBElement<?> requestElement;
	JAXBElement<?> responseElement;
	Exception responseException;
	long durationMillis;

	public String getConfigSummary()
	{
		return config != null ? config.toString() : "";
	}

	public String getRequestAsString()
	{
		return toString(requestElement);
	}

	public String getResponseAsString()
	{
		return toString(responseElement);
	}

	private String toString(final JAXBElement<?> jaxbElement)
	{
		if (jaxbElement == null)
		{
			return null;
		}

		try
		{
			final StringResult result = new StringResult();
			jaxbMarshaller.marshal(jaxbElement, result);
			return result.toString();
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Failed converting " + jaxbElement + " to String", ex);
		}
	}

}

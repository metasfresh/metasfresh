/*
 * #%L
 * de-metas-camel-leichundmehl
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.api.model;

import de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.LeichMehlConstants;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Value
@XmlRootElement(name = LeichMehlConstants.XML_ELEMENT_RI)
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLPluRootElement implements Serializable
{
	@NonNull
	@XmlElement(name = LeichMehlConstants.XML_ELEMENT_RECV_PLU)
	XMLPluElement xmlPluElement;

	@Builder
	public XMLPluRootElement(@NonNull final XMLPluElement xmlPluElement)
	{
		this.xmlPluElement = xmlPluElement;
	}

	public XMLPluRootElement()
	{
		throw new RuntimeException("Here just to please jaxb marshalling!");
	}
}

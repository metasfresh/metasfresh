/*
 * #%L
 * de.metas.postfinance
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.postfinance.customerregistration.util;

import de.metas.postfinance.customerregistration.CustomerRegistrationMessage;
import de.metas.postfinance.jaxb.DownloadFile;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;

@UtilityClass
public class XMLUtil
{
	@NonNull
	public CustomerRegistrationMessage getCustomerRegistrationMessage(@NonNull final DownloadFile downloadFile)
	{
		try
		{
			final JAXBContext jc = JAXBContext.newInstance(CustomerRegistrationMessage.class);
			final Unmarshaller unmarshaller = jc.createUnmarshaller();
			final ByteArrayInputStream inputStream = new ByteArrayInputStream(downloadFile.getData().getValue());

			return (CustomerRegistrationMessage)unmarshaller.unmarshal(inputStream);
		}
		catch (final JAXBException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}


	public boolean isXML(@NonNull final String filename)
	{
		final String name = filename.trim().toLowerCase();
		return name.endsWith(".xml");
	}
}

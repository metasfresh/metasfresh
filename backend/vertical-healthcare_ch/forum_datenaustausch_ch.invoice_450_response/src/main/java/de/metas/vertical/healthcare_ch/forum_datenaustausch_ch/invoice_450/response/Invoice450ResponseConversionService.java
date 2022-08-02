/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_450.response
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.response;

import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.ForumDatenaustauschChConstants;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.XmlVersion;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.CrossVersionResponseConverter;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.JaxbUtil;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.XmlResponse;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBElement;
import java.io.InputStream;

@Service
@Profile(ForumDatenaustauschChConstants.PROFILE)
public class Invoice450ResponseConversionService implements CrossVersionResponseConverter
{
	public static final String INVOICE_450_RESPONSE_XSD = "http://www.forum-datenaustausch.ch/invoice generalInvoiceResponse_450.xsd";

	@Override
	public XmlResponse toCrossVersionResponse(@NonNull final InputStream xmlInput)
	{
		final JAXBElement<ResponseType> jaxbRequest = JaxbUtil.unmarshalToJaxbElement(xmlInput, ResponseType.class);

		return Invoice450ToCrossVersionModelTool.INSTANCE.toCrossVersionModel(jaxbRequest.getValue());
	}

	@Override
	public String getXsdName()
	{
		return INVOICE_450_RESPONSE_XSD;
	}

	@Override
	public XmlVersion getVersion()
	{
		return XmlVersion.v450;
	}

}

/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_450.request
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

package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450;

import com.google.common.annotations.VisibleForTesting;
import de.metas.bpartner.BPartnerId;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.ForumDatenaustauschChConstants;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.XmlVersion;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_450.request.RequestType;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.CrossVersionRequestConverter;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.JaxbUtil;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlRequest;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBElement;
import java.io.InputStream;
import java.io.OutputStream;

@Service
@Profile(ForumDatenaustauschChConstants.PROFILE)
public class Invoice450RequestConversionService implements CrossVersionRequestConverter
{
	public static final String INVOICE_450_REQUEST_XSD = "http://www.forum-datenaustausch.ch/invoice generalInvoiceRequest_450.xsd";

	private boolean usePrettyPrint = false;

	@Override
	public void fromCrossVersionRequest(@NonNull final XmlRequest xRequest, @NonNull final OutputStream outputStream)
	{
		final JAXBElement<RequestType> jaxbType = Invoice450FromCrossVersionModelTool.INSTANCE.fromCrossVersionModel(xRequest);

		JaxbUtil.marshal(
				jaxbType,
				RequestType.class,
				INVOICE_450_REQUEST_XSD,
				outputStream,
				usePrettyPrint);
	}

	@Override
	public XmlRequest toCrossVersionRequest(@NonNull final InputStream xmlInput)
	{
		final JAXBElement<RequestType> jaxbRequest = JaxbUtil.unmarshalToJaxbElement(xmlInput, RequestType.class);

		return Invoice450ToCrossVersionModelTool.INSTANCE.toCrossVersionModel(jaxbRequest.getValue());
	}

	@Override
	public String getXsdName()
	{
		return INVOICE_450_REQUEST_XSD;
	}

	@Override
	public XmlVersion getVersion()
	{
		return XmlVersion.v450;
	}

	public XmlRequest augmentRequest(final XmlRequest xAugmentedRequest, final BPartnerId invoice)
	{
		return Invoice450FromCrossVersionModelTool.INSTANCE.augmentRequest(xAugmentedRequest, invoice);
	}

	@VisibleForTesting
	public void setUsePrettyPrint(final boolean usePrettyPrint)
	{
		this.usePrettyPrint = usePrettyPrint;
	}
}

/*
 * #%L
 * de.metas.xfactura.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.xfactura.base;

import com.google.common.collect.ImmutableList;
import de.metas.attachments.AttachmentEntryCreateRequest;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.AttachmentEntryType;
import de.metas.attachments.AttachmentTags;
import de.metas.document.archive.DocOutboundLogId;
import de.metas.invoice_gateway.spi.model.export.InvoiceToExport;
import de.metas.util.StringUtils;
import de.metas.xfactura.base.en16931.ObjectFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.MimeType;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.ByteArrayOutputStream;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class XFacturaService
{
	private static final ObjectFactory X_FACTURA_OBJECT_FACTORY = new ObjectFactory();
	private static final String X_FACTURA_SCHEMA_LOCATION = "Factur-X_1.07.2_EN16931.xsd";

	@NonNull private final AttachmentEntryService attachmentEntryService;


	public void handleExceptions(@NonNull final DocOutboundLogId docOutboundLogId, @NonNull final AdempiereException ex)
	{
		//TODO add error message and set error status
	}

	public XFacturaResponse prepareData(@NonNull final XFacturaRequest xFacturaRequest, @NonNull final InvoiceToExport invoiceToExport)
	{
		// TODO
		return XFacturaResponse.builder().build();
	}

	public void createAttachment(
			@NonNull final XFacturaResponse xFacturaResponse)
	{
		final byte[] data;
		try
		{
			final JAXBContext contextObj = JAXBContext.newInstance(ObjectFactory.class);

			final SchemaFactory factory = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
			final URL schemaUrl = ObjectFactory.class.getResource(X_FACTURA_SCHEMA_LOCATION);
			final Schema schema = factory.newSchema(schemaUrl);

			final Marshaller marshallerObj = contextObj.createMarshaller();
			marshallerObj.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshallerObj.setProperty(Marshaller.JAXB_FRAGMENT, true);
			marshallerObj.setSchema(schema); //make sure schema is respected and get a proper error if not

			final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			marshallerObj.marshal(xFacturaResponse.getInvoice(), outputStream);
			data = outputStream.toByteArray();
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Error in X-Factura XML", e);
		}

		final AttachmentTags attachmentTags = AttachmentTags.builder()
				.tag(AttachmentTags.TAGNAME_IS_DOCUMENT, StringUtils.ofBoolean(true))
				.tag(AttachmentTags.TAGNAME_SEND_VIA_EMAIL, StringUtils.ofBoolean(true))
				.build();

		attachmentEntryService.createNewAttachment(ImmutableList.of(xFacturaResponse.getDocOutboundLogReference()),
				AttachmentEntryCreateRequest.builder()
						.type(AttachmentEntryType.Data)
						.filename("x_rechnung.xml") //TODO check if compatible with pdf attachment name convention
						.data(data)
						.contentType(MimeType.TYPE_XML)
						.tags(attachmentTags)
						.build());
	}

}

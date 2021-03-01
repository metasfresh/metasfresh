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

package de.metas.vertical.healthcare.forum_datenaustausch_ch.rest;

import de.metas.vertical.healthcare.forum_datenaustausch_ch.rest.xml_to_olcands.exceptions.InvalidXMLException;
import de.metas.vertical.healthcare.forum_datenaustausch_ch.rest.xml_to_olcands.exceptions.XmlInvalidRequestIdException;
import de.metas.vertical.healthcare.forum_datenaustausch_ch.rest.xml_to_olcands.exceptions.XmlInvoiceAttachException;
import de.metas.vertical.healthcare.forum_datenaustausch_ch.rest.xml_to_olcands.exceptions.XmlInvoiceInputStreamException;
import de.metas.vertical.healthcare.forum_datenaustausch_ch.rest.xml_to_olcands.exceptions.XmlInvoiceUnmarshalException;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("de.metas.vertical.healthcare.forum_datenaustausch_ch.rest.exceptions.RestResponseEntityExceptionHandler")
public class XMLInvoiceResponseEntityExceptionHandler
{
	@ExceptionHandler(InvalidXMLException.class)
	public ResponseEntity<String> handleBPartnerInfoNotFoundException(@NonNull final InvalidXMLException e)
	{
		return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(XmlInvalidRequestIdException.class)
	public ResponseEntity<String> handleXmlInvalidRequestIdException(@NonNull final XmlInvalidRequestIdException e)
	{
		return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(XmlInvoiceInputStreamException.class)
	public ResponseEntity<String> handleXmlInvoiceInputStreamException(@NonNull final XmlInvoiceInputStreamException e)
	{
		return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(XmlInvoiceUnmarshalException.class)
	public ResponseEntity<String> handleXmlInvoiceUnmarshalException(@NonNull final XmlInvoiceUnmarshalException e)
	{
		return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(XmlInvoiceAttachException.class)
	public ResponseEntity<String> handleXmlInvoiceAttachException(@NonNull final XmlInvoiceAttachException e)
	{
		return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}

}

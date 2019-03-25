package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.export.invoice;

import static de.metas.util.Check.assume;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.io.ByteStreams;

import de.metas.invoice_gateway.spi.InvoiceImportClient;
import de.metas.invoice_gateway.spi.model.imp.ImportedInvoiceResponse;
import de.metas.util.xml.XmlIntrospectionUtil;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.CrossVersionServiceRegistry;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.CrossVersionRequestConverter;
import lombok.NonNull;

/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class InvoiceImportClientImpl implements InvoiceImportClient
{
	private final CrossVersionServiceRegistry crossVersionServiceRegistry;

	private InvoiceImportClientImpl(@NonNull final CrossVersionServiceRegistry crossVersionServiceRegistry)
	{
		this.crossVersionServiceRegistry = crossVersionServiceRegistry;
	}

	@Override
	public ImportedInvoiceResponse importInvoiceResponse(@NonNull final InputStream xmlInput)
	{
		try
		{
			return import0(xmlInput);
		}
		catch (final IOException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	private ImportedInvoiceResponse import0(final InputStream xmlInput) throws IOException
	{
		final InputStream inputStreamToUse;
		if (xmlInput.markSupported())
		{
			inputStreamToUse = xmlInput;
		}
		else
		{
			final byte[] targetArray = ByteStreams.toByteArray(xmlInput);
			inputStreamToUse = new ByteArrayInputStream(targetArray);
			assume(inputStreamToUse.markSupported(), "ByteArrayInputStreams support mark and reset; inputStream={}", inputStreamToUse);
		}

		inputStreamToUse.mark(Integer.MAX_VALUE);
		final String xsdName = XmlIntrospectionUtil.extractXsdValueOrNull(inputStreamToUse);
		inputStreamToUse.reset();

		final CrossVersionRequestConverter<?> converter = crossVersionServiceRegistry.getConverterForXsdName(xsdName);

		converter.toCrossVersionRequest(inputStreamToUse);

		// TODO Auto-generated method stub
		return null;
	}

}

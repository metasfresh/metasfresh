/*
 * #%L
 * de.metas.report.jasper.client
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

package de.metas.report.jasper.client;

import de.metas.report.server.OutputType;
import de.metas.report.server.ReportResult;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class ReportResultMessageConverter
		extends AbstractHttpMessageConverter<ReportResult>
{

	public ReportResultMessageConverter()
	{
//		super(new MediaType("application", "vnd.ms-excel"));
		super (new MediaType("*", "*"));
	}

	@Override
	protected boolean supports(Class<?> clazz)
	{
		return ReportResult.class.isAssignableFrom(clazz);
	}

	@Override
	protected ReportResult readInternal(Class<? extends ReportResult> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException
	{
		String requestBody = toString(inputMessage.getBody());
		int i = requestBody.indexOf("\n");
		if (i == -1)
		{
			throw new HttpMessageNotReadableException("No first line found");
		}

		String reportName = requestBody.substring(0, i).trim();
		String content = requestBody.substring(i).trim();


		final ReportResult report = ReportResult.builder()
				.reportFilename("TEST_FILENAME")
				.reportContentBase64(requestBody)
				.outputType(OutputType.XLS)
				.build();
		return report;
	}

	@Override
	protected void writeInternal(ReportResult report, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException
	{
		try
		{
			OutputStream outputStream = outputMessage.getBody();
			String body = report.getReportFilename() + "\n" +
					report.getReportContent();
			outputStream.write(body.getBytes());
			outputStream.close();
		}
		catch (Exception e)
		{
		}
	}

	private static String toString(InputStream inputStream)
	{
		Scanner scanner = new Scanner(inputStream, "UTF-8");
		return scanner.useDelimiter("\\A").next();
	}
}

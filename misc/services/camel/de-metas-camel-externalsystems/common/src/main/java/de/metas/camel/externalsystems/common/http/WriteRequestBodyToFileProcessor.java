/*
 * #%L
 * de-metas-camel-externalsystems-common
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

package de.metas.camel.externalsystems.common.http;

import com.google.common.io.ByteStreams;
import de.metas.camel.externalsystems.common.file.WorkFile;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.RuntimeCamelException;

import javax.servlet.http.HttpServletRequestWrapper;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.function.Supplier;

public class WriteRequestBodyToFileProcessor implements Processor
{
	private final String writeFileToLocation;
	private final Supplier<WorkFile> workFile; 

	public WriteRequestBodyToFileProcessor(
			@NonNull final String writeFileToLocation,
			@NonNull final Supplier<WorkFile> workFile)
	{
		this.writeFileToLocation = writeFileToLocation;
		this.workFile = workFile;
	}

	@Override
	public void process(@NonNull final Exchange exchange)
	{
		final HttpServletRequestWrapper camelHttpServletRequest = ((HttpServletRequestWrapper)exchange.getIn()
				.getHeader("CamelHttpServletRequest"));

		final EmptyBodyRequestWrapper emptyBodyRequestWrapper = ((EmptyBodyRequestWrapper)camelHttpServletRequest.getRequest());
		
		final Path path = Path.of(writeFileToLocation, workFile.get().getDownloadInProgressFilename());

		final File downloadingTargetFile = new File(path.toString());

		try (final FileOutputStream outputStream = new FileOutputStream(downloadingTargetFile))
		{
			ByteStreams.copy(emptyBodyRequestWrapper.getRealStream(), outputStream);
		}
		catch (final Exception exception)
		{
			throw new RuntimeCamelException("Could not write to file! " + exception.getMessage());
		}
		
		markFileAsReady(downloadingTargetFile);
	}

	private void markFileAsReady(@NonNull final File downloadingTargetFile)
	{
		final Path readyFilename = Path.of(writeFileToLocation, workFile.get().getReadyFilename());
		final File readyTargetFile = new File(readyFilename.toString());

		final boolean success = downloadingTargetFile.renameTo(readyTargetFile);

		if (!success)
		{
			throw new RuntimeCamelException("File was not successfully renamed!");
		}
	}
}

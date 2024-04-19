/*
 * #%L
 * de-metas-camel-pcm-file-import
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

package de.metas.camel.externalsystems.pcm.purchaseorder;

import de.metas.camel.externalsystems.common.PInstanceLogger;
import de.metas.camel.externalsystems.pcm.config.LocalFileConfig;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.component.file.GenericFile;
import org.apache.camel.component.file.GenericFileEndpoint;
import org.apache.camel.component.file.GenericFileOperations;
import org.apache.camel.component.file.strategy.GenericFileProcessStrategySupport;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

/**
 * Don't process orders files if there are master data files.
 */
@RequiredArgsConstructor
public class GetPurchaseOrderFromFileProcessStrategy extends GenericFileProcessStrategySupport<File>
{
	@NonNull
	LocalFileConfig fileEndpointConfig;

	@NonNull
	PInstanceLogger pInstanceLogger;

	@Override
	public boolean begin(
			final GenericFileOperations operations, 
			final GenericFileEndpoint endpoint, 
			@NonNull final Exchange exchange, 
			@NonNull final GenericFile file) throws Exception
	{
		final boolean defaultResult = super.begin(operations, endpoint, exchange, file);
		if (!defaultResult)
		{
			return false;
		}

		final FileSystem fileSystem = FileSystems.getDefault();
		final PathMatcher bpartnerFileMatcher = fileSystem.getPathMatcher("glob:" + fileEndpointConfig.getFileNamePatternBPartner());
		final PathMatcher warehouseFileMatcher = fileSystem.getPathMatcher("glob:" + fileEndpointConfig.getFileNamePatternWarehouse());
		final PathMatcher productFileMatcher = fileSystem.getPathMatcher("glob:" + fileEndpointConfig.getFileNamePatternProduct());

		try
		{ // we could have the following with less lines, but IMO this way it's easier to debug
			final Path rootLocation = Paths.get(fileEndpointConfig.getRootLocation());
			final EnumSet<FileVisitOption> options = EnumSet.noneOf(FileVisitOption.class);
			final Path[] existingMasterDataFile = new Path[1];
			final SimpleFileVisitor<Path> visitor = new SimpleFileVisitor<>()
			{
				@Override
				public FileVisitResult visitFile(@NonNull final Path currentFile, final BasicFileAttributes attrs)
				{
					if (bpartnerFileMatcher.matches(currentFile.getFileName()))
					{
						existingMasterDataFile[0] = currentFile;
						return FileVisitResult.TERMINATE;
					}
					if (warehouseFileMatcher.matches(currentFile.getFileName()))
					{
						existingMasterDataFile[0] = currentFile;
						return FileVisitResult.TERMINATE;
					}
					if (productFileMatcher.matches(currentFile.getFileName()))
					{
						existingMasterDataFile[0] = currentFile;
						return FileVisitResult.TERMINATE;
					}

					return FileVisitResult.CONTINUE;
				}
			};
			Files.walkFileTree(rootLocation, options, 0, visitor);

			final boolean atLEastOneFileFound = existingMasterDataFile[0] != null;
			if (atLEastOneFileFound)
			{
				pInstanceLogger.logMessage("There is at least the masterdata file " + existingMasterDataFile[0].getFileName() + " which has to be processed first => ignoring orders file " + file.getFileName() + " for now");
				
				return false;
			}

			return true; // the super-class said yes and we did not find any masterdata file
		}
		catch (final IOException e)
		{
			throw new RuntimeCamelException("Caught exception while checking for existing master data files", e);
		}
	}
}

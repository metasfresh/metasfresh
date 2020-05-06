package de.metas.impex.api;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.io.File;
import java.util.List;

/**
 * Generic services for the file handling related to EDI imports. Note: I'm not using a utility class with static
 * methods because they do FS access, which might lead to trouble when trying to unit test users of these methods.
 * 
 * @author tobi42
 * 
 */
public interface IInboundProcessorBL
{

	public final static String LOCAL_FOLDER = "LocalFolder";

	public final static String ARCHIVE_FOLDER = "ArchiveFolder";

	public List<File> getFiles(final String localFolderName);

	/**
	 * 
	 * @param importedFile
	 * @param archiveFolder
	 * @return an error message (see {@link EdiMessages}) or <code>null</code> if the operation did succeed.
	 */
	public String moveToArchive(final File importedFile,
			final String archiveFolder);

}

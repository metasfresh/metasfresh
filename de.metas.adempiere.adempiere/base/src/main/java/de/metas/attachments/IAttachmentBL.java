package de.metas.attachments;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.io.File;
import java.util.Collection;
import java.util.List;

import javax.activation.DataSource;

import org.adempiere.util.ISingletonService;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_AD_Attachment;

/**
 * Record attachments repository.
 * 
 * For all methods which accept <code>Object model</code> as parameter, the model can be:
 * <ul>
 * <li>some actual model (interface)
 * <li>{@link ITableRecordReference}
 * <li>{@link I_AD_Attachment}
 * </ul>
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IAttachmentBL extends ISingletonService
{
	/**
	 * Gets attachment of given model.
	 *
	 * @param model that is referenced by an {@link I_AD_Attachment} via {@code AD_Table_ID} and {@code Record_ID}, or the attachment instance itself.
	 * @return attachment; never return null
	 */
	I_AD_Attachment getAttachment(Object model);

	/**
	 * Add given file as a new entry to given attachment.
	 *
	 * @param model see class documentation
	 * @param file
	 */
	AttachmentEntry addEntry(Object model, File file);

	/**
	 * Attach given byte array to attachment.
	 *
	 * @param model
	 * @param name entry name
	 * @param data
	 */
	AttachmentEntry addEntry(Object model, String name, byte[] data);

	List<AttachmentEntry> addEntriesFromDataSources(Object model, Collection<DataSource> dataSources);

	/**
	 * @param model model (see class documentation)
	 * @param files
	 * @return attached entries
	 */
	List<AttachmentEntry> addEntriesFromFiles(Object model, Collection<File> files);

	/**
	 * Method to attach file to object model.
	 *
	 * @param model model or {@link ITableRecordReference}
	 * @param file
	 * @return the attachment
	 */
	I_AD_Attachment createAttachment(Object model, File file);

	/**
	 * Method to attach file (described by name and data) to object model.
	 *
	 * @param model model or {@link ITableRecordReference}
	 * @param name filename
	 * @param data file content
	 * @return the attachment
	 */
	I_AD_Attachment createAttachment(Object model, String name, byte[] data);

	AttachmentEntry getEntryById(Object model, int attachmentEntryId);

	AttachmentEntry getEntryByFilename(Object model, String filename);

	byte[] getEntryByFilenameAsBytes(Object model, String filename);

	byte[] getEntryByIdAsBytes(Object model, int attachmentEntryId);

	/**
	 * Method to get data as bytes from the first entry
	 *
	 * @param model see class documentation
	 * @return
	 */
	byte[] getFirstEntryAsBytesOrNull(Object model);

	AttachmentEntry getFirstEntry(Object model);

	List<AttachmentEntry> getEntries(Object model);

	boolean hasEntries(Object model);

	/**
	 * @param model see class documentation
	 * @param attachmentEntryId
	 */
	void deleteEntryForModel(Object model, int attachmentEntryId);

	void deleteEntryById(Object model, int attachmentEntryId);

	void deleteAttachment(Object model);

	void setAttachmentText(Object model, String textMsg);
}

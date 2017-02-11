package org.adempiere.service;

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

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_Attachment;

public interface IAttachmentBL extends ISingletonService
{
	/**
	 * Gets attachment of given model.
	 * 
	 * @param model
	 * @return attachment; never return null
	 */
	I_AD_Attachment getAttachment(Object model);

	/**
	 * Add given file as a new entry to given attachment.
	 * 
	 * @param attachment
	 * @param file
	 */
	void addEntry(I_AD_Attachment attachment, File file);

	/**
	 * Attach given byte array to attachment.
	 * 
	 * @param attachment
	 * @param name entry name
	 * @param data
	 */
	void addEntry(I_AD_Attachment attachment, String name, byte[] data);

	/**
	 * Method to attach file to object model.
	 * 
	 * @param model
	 * @param file
	 * @return the attachment
	 */
	I_AD_Attachment createAttachment(Object model, File file);

	/**
	 * Method to attach file (described by name and data) to object model.
	 * 
	 * @param model
	 * @param name filename
	 * @param data file content
	 * @return the attachment
	 */
	I_AD_Attachment createAttachment(Object model, String name, byte[] data);

	byte[] getEntryAsBytes(I_AD_Attachment attachment, String name);

	/**
	 * Method to get data as bytes from the first entry
	 * 
	 * @param attachment
	 * @return
	 */
	byte[] getFirstEntryAsBytesOrNull(I_AD_Attachment attachment);
}

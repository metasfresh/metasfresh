package org.adempiere.service.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.slf4j.Logger;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.impl.AttachmentBL;
import de.metas.logging.LogManager;

public class PlainAttachmentBL extends AttachmentBL
{
	private static final transient Logger logger = LogManager.getLogger(PlainAttachmentBL.class);

	@Override
	public AttachmentEntry addEntry(final Object model, final String name, final byte[] data)
	{
		logger.warn("NOTE: adding entries to attachments is not supported in test mode");
		return null;
	}

}

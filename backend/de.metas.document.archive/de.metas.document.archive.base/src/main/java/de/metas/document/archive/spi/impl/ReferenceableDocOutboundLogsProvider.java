package de.metas.document.archive.spi.impl;

import org.springframework.stereotype.Component;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentTags;
import de.metas.attachments.automaticlinksharing.TableRecordRefProvider;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

/**
 * This implementation's {@link #expand(java.util.Collection)} method returns invoice candidates referencing the records to expand on.
 */
@Component
public class ReferenceableDocOutboundLogsProvider extends TableRecordRefProvider<I_C_Doc_Outbound_Log>
{
	public ReferenceableDocOutboundLogsProvider()
	{
		super(I_C_Doc_Outbound_Log.class);
	}

	@Override
	protected boolean isExpandOnAttachmentEntry(@NonNull final AttachmentEntry attachmentEntry)
	{
		return attachmentEntry.getTags().hasTagSetToTrue(AttachmentTags.TAGNAME_IS_DOCUMENT);
	}
}

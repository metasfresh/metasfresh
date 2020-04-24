/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.attachments.listener;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.listener.AttachmentListenerConstants.ListenerWorkStatus;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Table_AttachmentListener;

/**
 * Listeners subscribed to attaching files to tables should implement this interface.
 * Implementations need to be registered in the table {@link I_AD_Table_AttachmentListener#Table_Name}.
 */
public interface AttachmentListener
{
	ListenerWorkStatus afterRecordLinked(AttachmentEntry attachmentEntry, TableRecordReference tableRecordReference);
}

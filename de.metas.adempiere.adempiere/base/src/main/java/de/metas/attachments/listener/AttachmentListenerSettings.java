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

import de.metas.i18n.AdMessageId;
import de.metas.javaclasses.JavaClassId;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_AD_Table_AttachmentListener;

import javax.annotation.Nullable;

@Value
public class AttachmentListenerSettings
{
	@NonNull
	JavaClassId listenerJavaClassId;

	boolean isSendNotification;

	@Nullable
	AdMessageId adMessageId;

	public static AttachmentListenerSettings of(final I_AD_Table_AttachmentListener record)
	{
		return new AttachmentListenerSettings(JavaClassId.ofRepoId(record.getAD_JavaClass_ID()),
				record.isSendNotification(),
				AdMessageId.ofRepoIdOrNull(record.getAD_Message_ID()) );
	}
}

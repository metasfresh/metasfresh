/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.attachments;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_AD_AttachmentEntry;

import javax.annotation.Nullable;
import java.util.Arrays;

public enum AttachmentEntryType implements ReferenceListAwareEnum
{
	Data(X_AD_AttachmentEntry.TYPE_Data),
	URL(X_AD_AttachmentEntry.TYPE_URL),
	LocalFileURL(X_AD_AttachmentEntry.TYPE_LocalFile_URL);

	@Getter
	private final String code;

	AttachmentEntryType(@NonNull final String code)
	{
		this.code = code;
	}

	@Nullable
	public static AttachmentEntryType ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	public static AttachmentEntryType ofCode(@NonNull final String code)
	{
		final AttachmentEntryType type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + AttachmentEntryType.class + " found for code: " + code);
		}
		return type;
	}
	private static final ImmutableMap<String, AttachmentEntryType> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), AttachmentEntryType::getCode);
}

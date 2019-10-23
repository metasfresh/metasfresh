package de.metas.document.sequence;

import java.util.Optional;

import javax.annotation.Nullable;

import de.metas.document.DocumentSequenceInfo;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public interface ValueSequenceInfoProvider
{
	@Value
	public static class ProviderResult
	{
		public static final ProviderResult EMPTY = new ProviderResult(null);

		public static ProviderResult of(@NonNull final DocumentSequenceInfo documentSequenceInfo)
		{
			return new ProviderResult(documentSequenceInfo);
		}

		Optional<DocumentSequenceInfo> documentSequenceInfo;

		public boolean hasInfo()
		{
			return documentSequenceInfo.isPresent();
		}

		public DocumentSequenceInfo getInfoOrNull()
		{
			return documentSequenceInfo.orElse(null);
		}

		private ProviderResult(@Nullable final DocumentSequenceInfo documentSequenceInfo)
		{
			this.documentSequenceInfo = Optional.ofNullable(documentSequenceInfo);
		}
	}

	ProviderResult computeValueInfo(Object modelrecord);
}

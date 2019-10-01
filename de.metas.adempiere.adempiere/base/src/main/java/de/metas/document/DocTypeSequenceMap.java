package de.metas.document;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import org.adempiere.service.ClientId;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

import de.metas.document.sequence.DocSequenceId;
import de.metas.organization.OrgId;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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
 * Immutable document type sequence map (indexed by AD_Client_ID, AD_Org_ID).
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class DocTypeSequenceMap
{
	public static Builder builder()
	{
		return new Builder();
	}

	public static final DocTypeSequenceMap EMPTY = builder().build();

	private final Map<ArrayKey, DocTypeSequence> docTypeSequences;
	private final DocSequenceId defaultDocNoSequenceId;

	private DocTypeSequenceMap(final Builder builder)
	{
		docTypeSequences = ImmutableMap.copyOf(builder.docTypeSequences);
		defaultDocNoSequenceId = builder.defaultDocNoSequenceId;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("docTypeSequences", docTypeSequences)
				.add("defaultDocNoSequence_ID", defaultDocNoSequenceId)
				.toString();
	}

	public DocSequenceId getDocNoSequenceId(final ClientId adClientId, final OrgId adOrgId)
	{
		if (!docTypeSequences.isEmpty())
		{
			final ArrayKey key = mkKey(adClientId, adOrgId);
			final DocTypeSequence docTypeSequence = docTypeSequences.get(key);
			if (docTypeSequence != null)
			{
				return docTypeSequence.getDocSequenceId();
			}
		}

		return defaultDocNoSequenceId;
	}

	private static ArrayKey mkKey(@NonNull final ClientId adClientId, @NonNull final OrgId adOrgId)
	{
		return Util.mkKey(adClientId, adOrgId);
	}

	public static final class Builder
	{
		private final Map<ArrayKey, DocTypeSequence> docTypeSequences = new HashMap<>();
		private DocSequenceId defaultDocNoSequenceId = null;

		private Builder()
		{
		}

		public DocTypeSequenceMap build()
		{
			return new DocTypeSequenceMap(this);
		}

		public void addDocSequenceId(final ClientId adClientId, final OrgId adOrgId, final DocSequenceId docSequenceId)
		{
			final DocTypeSequence docTypeSequence = new DocTypeSequence(adClientId, adOrgId, docSequenceId);
			final ArrayKey key = mkKey(docTypeSequence.getAdClientId(), docTypeSequence.getAdOrgId());

			docTypeSequences.put(key, docTypeSequence);
		}

		public Builder defaultDocNoSequenceId(final DocSequenceId defaultDocNoSequenceId)
		{
			this.defaultDocNoSequenceId = defaultDocNoSequenceId;
			return this;
		}
	}

	@Value
	private static final class DocTypeSequence
	{
		private final ClientId adClientId;
		private final OrgId adOrgId;
		private final DocSequenceId docSequenceId;

		private DocTypeSequence(
				@Nullable final ClientId adClientId,
				@Nullable final OrgId adOrgId,
				@NonNull final DocSequenceId docSequenceId)
		{
			this.adClientId = adClientId != null ? adClientId : ClientId.SYSTEM;
			this.adOrgId = adOrgId != null ? adOrgId : OrgId.ANY;
			this.docSequenceId = docSequenceId;
		}
	}
}

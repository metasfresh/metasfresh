package de.metas.document;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import de.metas.location.CountryId;
import de.metas.util.lang.SeqNo;
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

	public DocSequenceId getDocNoSequenceId(@NonNull final ClientId adClientId, @NonNull final OrgId adOrgId, @Nullable final CountryId countryId)
	{
		if (!docTypeSequences.isEmpty())
		{
			final SeqNo seqNo = getLowestEligibleSeqNo(adClientId, adOrgId);

			ArrayKey key = mkKey(adClientId, adOrgId, countryId, seqNo);
			DocTypeSequence docTypeSequence = docTypeSequences.get(key);
			if (docTypeSequence == null)
			{
				key = mkKey(adClientId, OrgId.ANY, countryId, seqNo);
				docTypeSequence = docTypeSequences.get(key);
			}
			if (docTypeSequence == null)
			{
				key = mkKey(adClientId, adOrgId, null, seqNo);
				docTypeSequence = docTypeSequences.get(key);
			}
			if (docTypeSequence == null)
			{
				key = mkKey(adClientId, OrgId.ANY, null, seqNo);
				docTypeSequence = docTypeSequences.get(key);
			}
			if (docTypeSequence != null)
			{
				return docTypeSequence.getDocSequenceId();
			}
		}

		return defaultDocNoSequenceId;
	}

	private SeqNo getLowestEligibleSeqNo(@NonNull final ClientId adClientId, @NonNull final OrgId adOrgId)
	{
		SeqNo seqNo = docTypeSequences.entrySet().iterator().next().getValue().getSeqNo();
		for (final Map.Entry<Util.ArrayKey, DocTypeSequence> entry : docTypeSequences.entrySet()) {
			if (entry.getValue().getAdClientId() == adClientId
					&& (entry.getValue().getAdOrgId() == adOrgId
					|| entry.getValue().getAdOrgId() == OrgId.ANY))
			{
				if(entry.getValue().getSeqNo().toInt() < seqNo.toInt())
				{
					seqNo = entry.getValue().getSeqNo();
				}
			}
		}
		return seqNo;
	}

	private static ArrayKey mkKey(@NonNull final ClientId adClientId, @NonNull final OrgId adOrgId, @Nullable final CountryId countryId, @NonNull final SeqNo seqNo)
	{
		return Util.mkKey(adClientId, adOrgId, countryId, seqNo);
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

		public void addDocSequenceId(final ClientId adClientId, final OrgId adOrgId, final DocSequenceId docSequenceId, final CountryId countryId, final SeqNo seqNo)
		{
			final DocTypeSequence docTypeSequence = new DocTypeSequence(adClientId, adOrgId, docSequenceId, countryId, seqNo);
			final ArrayKey key = mkKey(docTypeSequence.getAdClientId(), docTypeSequence.getAdOrgId(), docTypeSequence.getCountryId(), docTypeSequence.getSeqNo());

			docTypeSequences.put(key, docTypeSequence);
		}

		public Builder defaultDocNoSequenceId(final DocSequenceId defaultDocNoSequenceId)
		{
			this.defaultDocNoSequenceId = defaultDocNoSequenceId;
			return this;
		}
	}

	@Value
	private static class DocTypeSequence
	{
		ClientId adClientId;
		OrgId adOrgId;
		DocSequenceId docSequenceId;
		CountryId countryId;
		SeqNo seqNo;

		private DocTypeSequence(
				@Nullable final ClientId adClientId,
				@Nullable final OrgId adOrgId,
				@NonNull final DocSequenceId docSequenceId,
				@Nullable final CountryId countryId,
				@NonNull final SeqNo seqNo)
		{
			this.adClientId = adClientId != null ? adClientId : ClientId.SYSTEM;
			this.adOrgId = adOrgId != null ? adOrgId : OrgId.ANY;
			this.docSequenceId = docSequenceId;
			this.countryId = countryId;
			this.seqNo = seqNo;
		}
	}
}

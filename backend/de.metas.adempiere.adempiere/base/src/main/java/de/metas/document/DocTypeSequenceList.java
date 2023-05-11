package de.metas.document;

import com.google.common.base.MoreObjects;
import de.metas.document.sequence.DocSequenceId;
import de.metas.location.CountryId;
import de.metas.organization.OrgId;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;
import java.util.ArrayList;

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
public final class DocTypeSequenceList
{
	public static Builder builder()
	{
		return new Builder();
	}

	public static final DocTypeSequenceList EMPTY = builder().build();

	private final ArrayList<DocTypeSequence> docTypeSequences;
	private final DocSequenceId defaultDocNoSequenceId;

	private DocTypeSequenceList(@NonNull final Builder builder)
	{
		docTypeSequences = builder.docTypeSequences;
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
			for (final DocTypeSequence docTypeSequence : docTypeSequences)
			{
				if (!docTypeSequence.getAdClientId().equals(adClientId))
				{
					continue;
				}
				if (!(docTypeSequence.getAdOrgId().equals(adOrgId) || docTypeSequence.getAdOrgId().equals(OrgId.ANY)))
				{
					continue;
				}
				if (docTypeSequence.getCountryId().equals(countryId) || docTypeSequence.getCountryId() == null)
				{
					return docTypeSequence.getDocSequenceId();
				}
			}
		}
		return defaultDocNoSequenceId;
	}

	public static final class Builder
	{
		private final ArrayList<DocTypeSequence> docTypeSequences = new ArrayList<>();
		private DocSequenceId defaultDocNoSequenceId = null;

		private Builder()
		{
		}

		public DocTypeSequenceList build()
		{
			return new DocTypeSequenceList(this);
		}

		/**
		 * We assume that this method is called in the right order of SeqNos (ascending)
		 */
		public void addDocSequenceId(final ClientId adClientId, final OrgId adOrgId, final DocSequenceId docSequenceId, final CountryId countryId, final SeqNo seqNo)
		{
			final DocTypeSequence docTypeSequence = new DocTypeSequence(adClientId, adOrgId, docSequenceId, countryId, seqNo);

			docTypeSequences.add(docTypeSequence);
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

package de.metas.document;

import java.util.HashMap;
import java.util.Map;

import org.compiere.util.Env;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

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
	public static final Builder builder()
	{
		return new Builder();
	}

	public static final DocTypeSequenceMap EMPTY = builder().build();

	private final Map<ArrayKey, DocTypeSequence> docTypeSequences;
	private final int defaultDocNoSequence_ID;

	private DocTypeSequenceMap(final Builder builder)
	{
		super();
		docTypeSequences = ImmutableMap.copyOf(builder.docTypeSequences);
		defaultDocNoSequence_ID = builder.defaultDocNoSequence_ID;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("docTypeSequences", docTypeSequences)
				.add("defaultDocNoSequence_ID", defaultDocNoSequence_ID)
				.toString();
	}

	public int getDocNoSequence_ID(final int adClientId, final int adOrgId)
	{
		if (!docTypeSequences.isEmpty())
		{
			final ArrayKey key = mkKey(adClientId, adOrgId);
			final DocTypeSequence docTypeSequence = docTypeSequences.get(key);
			if (docTypeSequence != null)
			{
				return docTypeSequence.getDocNoSequence_ID();
			}
		}

		return defaultDocNoSequence_ID;
	}

	private static final ArrayKey mkKey(final int adClientId, final int adOrgId)
	{
		return Util.mkKey(adClientId, adOrgId);
	}

	public static final class Builder
	{
		private final Map<ArrayKey, DocTypeSequence> docTypeSequences = new HashMap<>();
		private int defaultDocNoSequence_ID = -1;

		private Builder()
		{
			super();
		}

		public DocTypeSequenceMap build()
		{
			return new DocTypeSequenceMap(this);
		}

		public void addDocSequenceId(final int adClientId, final int adOrgId, final int docSequenceId)
		{
			final DocTypeSequence docTypeSequence = DocTypeSequence.of(adClientId, adOrgId, docSequenceId);
			final ArrayKey key = mkKey(docTypeSequence.getAD_Client_ID(), docTypeSequence.getAD_Org_ID());

			docTypeSequences.put(key, docTypeSequence);
		}

		public Builder setDefaultDocNoSequence_ID(final int defaultDocNoSequence_ID)
		{
			this.defaultDocNoSequence_ID = defaultDocNoSequence_ID;
			return this;
		}
	}

	private static final class DocTypeSequence
	{

		public static final DocTypeSequence of(final int adClientId, final int adOrgId, final int docSequenceId)
		{
			return new DocTypeSequence(adClientId, adOrgId, docSequenceId);
		}

		private final int adClientId;
		private final int adOrgId;
		private final int docSequenceId;

		private DocTypeSequence(final int adClientId, final int adOrgId, final int docSequenceId)
		{
			super();
			this.adClientId = adClientId <= 0 ? Env.CTXVALUE_AD_Client_ID_System : adClientId;
			this.adOrgId = adOrgId <= 0 ? Env.CTXVALUE_AD_Org_ID_Any : adOrgId;
			if (docSequenceId <= 0)
			{
				throw new IllegalArgumentException("docSequenceId <= 0");
			}
			this.docSequenceId = docSequenceId;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("AD_Client_ID", adClientId)
					.add("AD_Org_ID", adOrgId)
					.add("DocSequence_ID", docSequenceId)
					.toString();
		}

		public int getAD_Client_ID()
		{
			return adClientId;
		}

		public int getAD_Org_ID()
		{
			return adOrgId;
		}

		public int getDocNoSequence_ID()
		{
			return docSequenceId;
		}
	}
}

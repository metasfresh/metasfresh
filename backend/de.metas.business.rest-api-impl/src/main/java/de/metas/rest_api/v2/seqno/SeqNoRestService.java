/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.rest_api.v2.seqno;

import de.metas.common.rest_api.v2.seqno.JsonSeqNoResponse;
import de.metas.document.sequence.DocSequenceId;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SeqNoRestService
{
	private final IDocumentNoBuilderFactory documentNoFactory;

	public SeqNoRestService(@NonNull final IDocumentNoBuilderFactory documentNoFactory)
	{
		this.documentNoFactory = documentNoFactory;
	}

	@NonNull
	public JsonSeqNoResponse getNextSeqNo(@NonNull final Integer sequenceId)
	{
		final String nextSeqNo = Optional.ofNullable(documentNoFactory.forSequenceId(DocSequenceId.ofRepoId(sequenceId)).build())
				.orElseThrow(() -> new AdempiereException("Failed to compute sequenceId")
						.appendParametersToMessage()
						.setParameter("adSequenceId", sequenceId));

		return JsonSeqNoResponse.builder().seqNo(nextSeqNo).build();
	}
}

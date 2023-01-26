/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.document.sequence;

import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.model.POInfoColumn;

import java.util.Optional;

@UtilityClass
public class SequenceUtil
{
	public String createValueFor(@NonNull final Object modelRecord)
	{
		final IDocumentNoBuilderFactory documentNoFactory = Services.get(IDocumentNoBuilderFactory.class);

		return documentNoFactory.createValueBuilderFor(modelRecord)
				.setFailOnError(true) // backward compatiblity: initially here (in PO.java) an DBException was thrown
				.build();
	}

	@NonNull
	public Optional<String> computeColumnValueBasedOnSequenceIdIfProvided(
			@NonNull final POInfoColumn poInfoColumn,
			final int clientId)
	{
		final DocSequenceId sequenceId = DocSequenceId.ofRepoIdOrNull(poInfoColumn.getAD_Sequence_ID());
		if (sequenceId == null)
		{
			return Optional.empty();
		}

		final IDocumentNoBuilderFactory documentNoFactory = Services.get(IDocumentNoBuilderFactory.class);
		final String computedSeqNo = documentNoFactory.forSequenceId(sequenceId)
				.setClientId(ClientId.ofRepoId(clientId))
				.build();

		if (computedSeqNo == null)
		{
			throw new AdempiereException("Failed to compute sequence!")
					.appendParametersToMessage()
					.setParameter("ColumnName", poInfoColumn.getColumnName())
					.setParameter("sequenceId", sequenceId);
		}

		return Optional.of(computedSeqNo);
	}
}

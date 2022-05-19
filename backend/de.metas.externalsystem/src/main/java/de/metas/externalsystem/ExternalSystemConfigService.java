/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem;

import de.metas.document.sequence.DocSequenceId;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExternalSystemConfigService
{
	public static final int AUDIT_AD_SEQUENCE_ID = 555613;

	private final IDocumentNoBuilderFactory documentNoFactory;

	public ExternalSystemConfigService(@NonNull final IDocumentNoBuilderFactory documentNoFactory)
	{
		this.documentNoFactory = documentNoFactory;
	}

	@NonNull
	public String getTraceId()
	{
		return Optional.ofNullable(documentNoFactory.forSequenceId(DocSequenceId.ofRepoId(AUDIT_AD_SEQUENCE_ID))
										   .setClientId(ClientId.SYSTEM)
										   .build())
				.orElseThrow(() -> new AdempiereException("Failed to compute sequenceId")
						.appendParametersToMessage()
						.setParameter("adSequenceId", AUDIT_AD_SEQUENCE_ID));
	}
}

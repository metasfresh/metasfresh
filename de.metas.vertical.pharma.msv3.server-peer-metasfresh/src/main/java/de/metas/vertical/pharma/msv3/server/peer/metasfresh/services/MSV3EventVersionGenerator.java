package de.metas.vertical.pharma.msv3.server.peer.metasfresh.services;

import org.compiere.Adempiere;
import org.compiere.util.Env;

import de.metas.document.DocumentSequenceInfo;
import de.metas.document.IDocumentSequenceDAO;
import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.util.Services;
import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3EventVersion;
import lombok.NonNull;

/*
 * #%L
 * de.metas.shipper.gateway.derkurier
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

public class MSV3EventVersionGenerator
{
	public static final int NO_AD_SEQUENCE_ID_FOR_TESTING = -99;

	public static final int AD_SEQUENCE_ID = 554782;

	private final IDocumentNoBuilder documentNoBuilder;

	public MSV3EventVersionGenerator(@NonNull final IDocumentNoBuilderFactory documentNoBuilderFactory)
	{
		final int eventVersionAdSequenceId = Adempiere.isUnitTestMode() ? NO_AD_SEQUENCE_ID_FOR_TESTING : AD_SEQUENCE_ID;

		final DocumentSequenceInfo documentSeqInfo = Services.get(IDocumentSequenceDAO.class)
				.retriveDocumentSequenceInfo(eventVersionAdSequenceId);

		this.documentNoBuilder = documentNoBuilderFactory
				.createDocumentNoBuilder()
				.setClientId(Env.getClientId())
				.setDocumentSequenceInfo(documentSeqInfo)
				.setFailOnError(true);
	}

	public MSV3EventVersion getNextEventVersion()
	{
		final String seqNoString = documentNoBuilder.build();
		final int seqNo = Integer.parseInt(seqNoString);
		return MSV3EventVersion.of(seqNo);
	}
}

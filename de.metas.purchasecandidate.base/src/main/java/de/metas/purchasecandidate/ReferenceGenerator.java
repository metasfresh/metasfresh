package de.metas.purchasecandidate;

import org.adempiere.service.ISysConfigBL;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import de.metas.document.DocumentSequenceInfo;
import de.metas.document.IDocumentSequenceDAO;
import de.metas.document.sequence.DocSequenceId;
import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.util.Check;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.purchasecandidate.base
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

@Service
public class ReferenceGenerator
{
	public static final String SYSCONFIG_AD_SEQUENCE_ID = "de.metas.purchasecandidate.DemandReference_AD_Sequence_ID";

	public String getNextDemandReference()
	{
		final DocSequenceId docSequenceId = DocSequenceId.ofRepoIdOrNull(Services.get(ISysConfigBL.class)
				.getIntValue(SYSCONFIG_AD_SEQUENCE_ID,
						-1, // default
						Env.getAD_Client_ID(),
						Env.getAD_Org_ID(Env.getCtx())));

		Check.errorIf(docSequenceId == null, "Unable to retrieve an AD_Sequence_ID from sysconfig; sysconfig key={}", SYSCONFIG_AD_SEQUENCE_ID);

		final DocumentSequenceInfo documentSeqInfo = Services.get(IDocumentSequenceDAO.class)
				.retriveDocumentSequenceInfo(docSequenceId);

		final IDocumentNoBuilder documentNoBuilder = Services.get(IDocumentNoBuilderFactory.class)
				.createDocumentNoBuilder()
				.setClientId(Env.getClientId())
				.setDocumentSequenceInfo(documentSeqInfo)
				.setFailOnError(true);

		final String demandId = documentNoBuilder.build();
		return demandId;
	}
}

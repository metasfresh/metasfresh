/*
 * #%L
 * de.metas.manufacturing.rest-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.rest_api.v2.shipping;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MSequence;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

@Component
public class ReceiptCandidateExportSequenceNumberProvider
{
	public static final String RECEIPT_CANDIDATE_EXPORT_SEQ_NO = "ReceiptCandidate_Export_SeqNo";

	public int provideNextReceiptCandidateSeqNo()
	{
		final int exportSequenceNumber = MSequence.getNextID(Env.CTXVALUE_AD_Client_ID_System, RECEIPT_CANDIDATE_EXPORT_SEQ_NO);
		if (exportSequenceNumber <= 0)
		{
			throw new AdempiereException("Could not retrieve nextID for sequence " + RECEIPT_CANDIDATE_EXPORT_SEQ_NO);
		}
		return exportSequenceNumber;
	}
}

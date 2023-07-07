/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.modular.log;

import de.metas.contracts.model.I_ModCntr_Log;
import de.metas.i18n.AdMessageKey;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

@Service
public class ModularContractLogService
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private static final AdMessageKey MSG_ERROR_DOCUMENT_LINE_DELETION = AdMessageKey.of("documentLineDeletionErrorBecauseOfRelatedModuleContractLog");

	public void throwErrorIfLogExistsForDocumentLine(@NonNull final TableRecordReference tableRecordReference)
	{
		if (existsLogForRecord(tableRecordReference))
		{
			throw new AdempiereException(MSG_ERROR_DOCUMENT_LINE_DELETION);
		}
	}

	private boolean existsLogForRecord(@NonNull final TableRecordReference tableRecordReference)
	{
		return queryBL.createQueryBuilder(I_ModCntr_Log.class)
				.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_AD_Table_ID, tableRecordReference.getAdTableId())
				.addEqualsFilter(I_ModCntr_Log.COLUMNNAME_Record_ID, tableRecordReference.getRecord_ID())
				.firstOptional().isPresent();
	}
}

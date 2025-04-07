/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.record.warning;

import com.google.common.base.Optional;
import de.metas.notification.spi.IRecordTextProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_AD_Record_Warning;

@RequiredArgsConstructor
public class RecordWarningTextProvider implements IRecordTextProvider
{
	@NonNull private final RecordWarningRepository recordWarningRepository;

	@Override
	public Optional<String> getTextMessageIfApplies(final ITableRecordReference referencedRecord)
	{
		if (!I_AD_Record_Warning.Table_Name.equals(referencedRecord.getTableName()))
		{
			return Optional.absent();
		}
		
		final RecordWarningId recordWarningId = RecordWarningId.ofRepoId(referencedRecord.getRecord_ID());
		return Optional.of(recordWarningRepository.getById(recordWarningId).getMsgText());
	}
}

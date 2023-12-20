/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.copy_with_details;

import de.metas.i18n.AdMessageKey;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.PO;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
public class CopyRecordService
{
	@NonNull
	public PO copyRecord(@NonNull final CopyRecordRequest copyRecordRequest)
	{
		final TableRecordReference fromRecordRef = copyRecordRequest.getTableRecordReference();
		final PO fromPO = fromRecordRef.getModel(PlainContextAware.newWithThreadInheritedTrx(), PO.class);
		final String tableName = fromPO.get_TableName();
		if (!CopyRecordFactory.isEnabledForTableName(tableName))
		{
			throw newCloneNotAllowedException(copyRecordRequest.getCustomErrorIfCloneNotAllowed(), tableName);
		}

		return CopyRecordFactory.getCopyRecordSupport(tableName)
				.setAdWindowId(copyRecordRequest.getFromAdWindowId())
				.copyToNew(fromPO)
				.orElseThrow(() -> newCloneNotAllowedException(copyRecordRequest.getCustomErrorIfCloneNotAllowed(), tableName));
	}

	private static AdempiereException newCloneNotAllowedException(@Nullable final AdMessageKey customErrorIfCloneNotAllowed, final @NonNull String tableName)
	{
		if (customErrorIfCloneNotAllowed != null)
		{
			return new AdempiereException(customErrorIfCloneNotAllowed);
		}
		else
		{
			return new AdempiereException("Clone is not enabled for table=" + tableName);
		}
	}
}
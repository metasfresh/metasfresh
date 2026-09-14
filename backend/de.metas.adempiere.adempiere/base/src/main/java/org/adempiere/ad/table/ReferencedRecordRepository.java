package org.adempiere.ad.table;

import com.google.common.annotations.VisibleForTesting;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.springframework.stereotype.Repository;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2026 metas GmbH
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
 * Repository Tables: dynamic - resolved at runtime from the given {@link TableRecordReference}
 * Repository Cluster: -
 */
@Repository
@RequiredArgsConstructor
public class ReferencedRecordRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@VisibleForTesting
	public static ReferencedRecordRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(ReferencedRecordRepository.class, ReferencedRecordRepository::new);
	}

	public boolean exists(@NonNull final TableRecordReference recordRef)
	{
		final String tableName = recordRef.getTableName();

		// NOTE: intentionally not restricted to active records - an inactive record still exists.
		return queryBL.createQueryBuilder(tableName)
				.addEqualsFilter(InterfaceWrapperHelper.getKeyColumnName(tableName), recordRef.getRecord_ID())
				.create()
				.anyMatch();
	}
}

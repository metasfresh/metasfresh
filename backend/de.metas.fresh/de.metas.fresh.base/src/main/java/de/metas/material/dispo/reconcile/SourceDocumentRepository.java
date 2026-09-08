package de.metas.material.dispo.reconcile;

/*
 * #%L
 * de.metas.fresh.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.common.util.IdConstants;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

/**
 * Repository Tables: M_ShipmentSchedule, M_ReceiptSchedule, PP_Order, DD_Order, M_ForecastLine, M_Forecast
 * Repository Cluster: SourceDocumentRepository
 * <p>
 * READ-ONLY, and it owns none of those tables: it never writes, and each one keeps its own DAO as the
 * persistence owner (see the {@link IQueryBL} note below for why those DAOs are not delegated to). The
 * single-member cluster therefore claims no write ownership - if a table above ever gains a declared
 * owner elsewhere, that is the accepted reader/owner split, not the second-writer case the convention
 * exists to catch.
 * <p>
 * Loads the source document behind an {@code MD_Candidate} for
 * {@link SourceDocumentLivenessService}, tolerating a miss.
 * <p>
 * This repository goes through {@link IQueryBL} rather than delegating to each source document's own
 * DAO, deliberately: no null-tolerant by-id lookup exists across all five source documents.
 * {@code ShipmentSchedulePA.getById} and {@code IForecastDAO.getById} throw on a miss (in production, not
 * just in tests); the receipt-schedule, {@code PP_Order} and {@code DD_Order} DAOs are bare
 * {@code InterfaceWrapperHelper.load(...)} calls, which NPE on a miss in unit-test POJO mode; and
 * {@code DD_Order} plus the {@code M_ForecastLine} hop offer no null-tolerant batch alternative either.
 * A candidate referencing a purged document must degrade to
 * {@link de.metas.material.dispo.commons.reconcile.SourceDocumentStatus#NO_SOURCE_DOCUMENT}, never abort
 * the run — that dangling reference is precisely the drifted state this feature exists to find and report.
 */
@Repository
public class SourceDocumentRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * @return the record with the given id, or {@code null} if there is no such record - or if the given id
	 * is unset
	 */
	@Nullable
	public <T> T getByIdOrNull(
			@NonNull final Class<T> modelClass,
			@NonNull final String idColumnName,
			final int recordId)
	{
		// note that an unset id is IdConstants.UNSPECIFIED_REPO_ID, which is positive
		final int repoId = IdConstants.toRepoId(recordId);
		if (repoId <= 0)
		{
			return null;
		}

		return queryBL.createQueryBuilder(modelClass)
				.addEqualsFilter(idColumnName, repoId)
				.create()
				.firstOnly(modelClass);
	}
}

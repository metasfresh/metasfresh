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
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.dispo.model.I_MD_Candidate_Demand_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_Purchase_Detail;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Product;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Repository Tables: M_ShipmentSchedule, M_ReceiptSchedule, PP_Order, DD_Order, M_ForecastLine, M_Forecast,
 * MD_Candidate_Demand_Detail, MD_Candidate_Purchase_Detail
 * Repository Cluster: SourceDocumentRepository
 * <p>
 * READ-ONLY, and owns none of those tables - each keeps its own DAO as persistence owner (see the
 * {@link IQueryBL} note below). It claims no write ownership, so a table above later gaining a declared owner
 * elsewhere is the accepted reader/owner split, not a second-writer conflict. {@code MD_Candidate_Demand_Detail}/
 * {@code MD_Candidate_Purchase_Detail} are read only as an existence check ({@code NOT IN} subquery) in
 * {@link #retrieveOpenShipmentScheduleIdsWithoutCandidate}/{@link #retrieveOpenReceiptScheduleIdsWithoutCandidate}
 * - the owning module's repositories still write them.
 * <p>
 * Loads the source document behind an {@code MD_Candidate} for {@link SourceDocumentLivenessService}, tolerating
 * a miss.
 * <p>
 * Goes through {@link IQueryBL} rather than each source document's own DAO because no null-tolerant by-id lookup
 * exists across all five: {@code ShipmentSchedulePA.getById}/{@code IForecastDAO.getById} throw on a miss, and the
 * receipt-schedule/{@code PP_Order}/{@code DD_Order} DAOs NPE on one in unit-test POJO mode - {@code DD_Order}
 * and the {@code M_ForecastLine} hop offer no null-tolerant batch alternative either. A candidate referencing a
 * purged document must degrade to
 * {@link de.metas.material.dispo.commons.reconcile.SourceDocumentStatus#NO_SOURCE_DOCUMENT}, never abort the run
 * - that dangling reference is precisely the drift this feature exists to find and report.
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

	/**
	 * @return {@code M_ShipmentSchedule_ID} of every open ({@code Processed='N'}, active) shipment schedule that no
	 * active {@code MD_Candidate_Demand_Detail} row references, restricted by the optional
	 * warehouse/product/product-category filter - one page of at most {@code limit} at {@code offset}, ordered by
	 * {@code M_ShipmentSchedule_ID} for stable pagination.
	 * <p>
	 * This is the one drift {@link AtpTargetCalculator}'s recompute cannot close: there is no candidate to correct,
	 * so it has to be surfaced instead of silently absorbed.
	 */
	public List<Integer> retrieveOpenShipmentScheduleIdsWithoutCandidate(
			@Nullable final WarehouseId warehouseId,
			@Nullable final ProductId productId,
			@Nullable final ProductCategoryId productCategoryId,
			final int limit,
			final int offset)
	{
		final IQueryBuilder<I_M_ShipmentSchedule> queryBuilder = queryBL.createQueryBuilder(I_M_ShipmentSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_Processed, false)
				.addNotInSubQueryFilter(
						I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID,
						I_MD_Candidate_Demand_Detail.COLUMNNAME_M_ShipmentSchedule_ID,
						queryBL.createQueryBuilder(I_MD_Candidate_Demand_Detail.class)
								.addOnlyActiveRecordsFilter()
								.create());

		addLocationFilters(queryBuilder,
				I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_ID, I_M_ShipmentSchedule.COLUMNNAME_M_Product_ID,
				warehouseId, productId, productCategoryId);

		return queryBuilder
				.orderBy().addColumnAscending(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID).endOrderBy()
				.create()
				.setLimit(limit, offset)
				.listIds();
	}

	/**
	 * Same as {@link #retrieveOpenShipmentScheduleIdsWithoutCandidate}, for {@code M_ReceiptSchedule} /
	 * {@code MD_Candidate_Purchase_Detail}.
	 */
	public List<Integer> retrieveOpenReceiptScheduleIdsWithoutCandidate(
			@Nullable final WarehouseId warehouseId,
			@Nullable final ProductId productId,
			@Nullable final ProductCategoryId productCategoryId,
			final int limit,
			final int offset)
	{
		final IQueryBuilder<I_M_ReceiptSchedule> queryBuilder = queryBL.createQueryBuilder(I_M_ReceiptSchedule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_Processed, false)
				.addNotInSubQueryFilter(
						I_M_ReceiptSchedule.COLUMNNAME_M_ReceiptSchedule_ID,
						I_MD_Candidate_Purchase_Detail.COLUMNNAME_M_ReceiptSchedule_ID,
						queryBL.createQueryBuilder(I_MD_Candidate_Purchase_Detail.class)
								.addOnlyActiveRecordsFilter()
								.create());

		addLocationFilters(queryBuilder,
				I_M_ReceiptSchedule.COLUMNNAME_M_Warehouse_ID, I_M_ReceiptSchedule.COLUMNNAME_M_Product_ID,
				warehouseId, productId, productCategoryId);

		return queryBuilder
				.orderBy().addColumnAscending(I_M_ReceiptSchedule.COLUMNNAME_M_ReceiptSchedule_ID).endOrderBy()
				.create()
				.setLimit(limit, offset)
				.listIds();
	}

	/**
	 * Applies the shared optional warehouse/product/product-category filter to a source-document query
	 * builder - extracted because {@link #retrieveOpenShipmentScheduleIdsWithoutCandidate} and
	 * {@link #retrieveOpenReceiptScheduleIdsWithoutCandidate} need the identical filter over two different
	 * model classes that happen to share both column names.
	 */
	private <T> void addLocationFilters(
			@NonNull final IQueryBuilder<T> queryBuilder,
			@NonNull final String warehouseColumnName,
			@NonNull final String productColumnName,
			@Nullable final WarehouseId warehouseId,
			@Nullable final ProductId productId,
			@Nullable final ProductCategoryId productCategoryId)
	{
		if (warehouseId != null)
		{
			queryBuilder.addEqualsFilter(warehouseColumnName, warehouseId);
		}
		if (productId != null)
		{
			queryBuilder.addEqualsFilter(productColumnName, productId);
		}
		if (productCategoryId != null)
		{
			queryBuilder.addInSubQueryFilter(
					productColumnName,
					I_M_Product.COLUMNNAME_M_Product_ID,
					queryBL.createQueryBuilder(I_M_Product.class)
							.addEqualsFilter(I_M_Product.COLUMNNAME_M_Product_Category_ID, productCategoryId)
							.create());
		}
	}
}

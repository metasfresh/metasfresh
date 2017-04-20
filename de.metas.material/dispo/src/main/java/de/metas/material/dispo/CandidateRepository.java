package de.metas.material.dispo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;

import de.metas.material.dispo.Candidate.CandidateBuilder;
import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.X_MD_Candidate;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-manufacturing-dispo
 * %%
 * Copyright (C) 2017 metas GmbH
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
public class CandidateRepository
{
	/**
	 * Stores the given {@code candidate}.
	 * If there is already an existing candidate in the store, it is loaded, its fields are updated and the result is saved.
	 *
	 * @param candidate
	 * @return a candidate with
	 *         <ul>
	 *         <li>the {@code id} of the persisted data record
	 *         <li>the quantity <b>delta</b> of the persisted data record before the update was made
	 *         </ul>
	 */
	public Candidate addOrReplace(@NonNull final Candidate candidate)
	{
		final Optional<I_MD_Candidate> oldCandidateRecord = retrieveExact(candidate);
		final BigDecimal oldqty = oldCandidateRecord.isPresent() ? oldCandidateRecord.get().getQty() : BigDecimal.ZERO;
		final BigDecimal qtyDelta = candidate.getQuantity().subtract(oldqty);
		
		final I_MD_Candidate synchedRecord = syncToRecord(oldCandidateRecord, candidate);
		InterfaceWrapperHelper.save(synchedRecord);

		return candidate
				.withId(synchedRecord.getMD_Candidate_ID())
				.withQuantity(qtyDelta);
	}

	public Optional<Candidate> retrieve(@NonNull final Candidate candidate)
	{
		return fromCandidateRecord(retrieveExact(candidate));
	}

	public Candidate retrieve(@NonNull final Integer parentId)
	{
		final I_MD_Candidate candidateRecord = InterfaceWrapperHelper.create(Env.getCtx(), parentId, I_MD_Candidate.class, ITrx.TRXNAME_ThreadInherited);
		return fromCandidateRecord(Optional.of(candidateRecord)).get();
	}

	/**
	 * Retrieves the <b>one</b>record that matches the given candidate's
	 * <ul>
	 * <li>type</li>
	 * <li>warehouse</li>
	 * <li>locator?</li>
	 * <li>product</li>
	 * <li>date</li>
	 * <li>tableId and record?</li>
	 * </ul>
	 *
	 * @param candidate
	 * @return
	 */
	@VisibleForTesting
	/* package */ Optional<I_MD_Candidate> retrieveExact(@NonNull final Candidate candidate)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_MD_Candidate> builder = queryBL
				.createQueryBuilder(I_MD_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Candidate.COLUMN_MD_Candidate_Type, candidate.getType().toString())
				.addEqualsFilter(I_MD_Candidate.COLUMN_M_Warehouse_ID, candidate.getWarehouseId())
				.addEqualsFilter(I_MD_Candidate.COLUMN_M_Product_ID, candidate.getProductId())
				.addEqualsFilter(I_MD_Candidate.COLUMN_DateProjected, candidate.getDate());

		final TableRecordReference referencedRecord = candidate.getReference();
		if (referencedRecord != null)
		{
			builder.addEqualsFilter(I_MD_Candidate.COLUMN_AD_Table_ID, referencedRecord.getAD_Table_ID());
			builder.addEqualsFilter(I_MD_Candidate.COLUMN_Record_ID, referencedRecord.getRecord_ID());
		}

		final I_MD_Candidate candidateRecord = builder
				.create()
				.firstOnly(I_MD_Candidate.class); // note that we have a UC to make sure there is just one

		return Optional.ofNullable(candidateRecord);
	}

	/**
	 *
	 * @param candidateRecord
	 * @param candidate
	 * @return either returns the record contained in the given candidateRecord (but updated) or a new record.
	 */
	private I_MD_Candidate syncToRecord(final Optional<I_MD_Candidate> candidateRecord, final Candidate candidate)
	{
		Preconditions.checkState(
				!candidateRecord.isPresent()
						|| InterfaceWrapperHelper.isNew(candidateRecord.get())
						|| candidate.getId() == null
						|| Objects.equals(candidateRecord.get().getMD_Candidate_ID(), candidate.getId()),
				"Param candidateRecord=%s is not new and its ID is different from the ID of param candidate=%s",
				candidateRecord, candidate);

		final I_MD_Candidate candidateRecordToUse = candidateRecord.orElse(InterfaceWrapperHelper.newInstance(I_MD_Candidate.class));

		candidateRecordToUse.setAD_Org_ID(candidate.getOrgId());
		candidateRecordToUse.setMD_Candidate_Type(candidate.getType().toString());
		candidateRecordToUse.setM_Warehouse_ID(candidate.getWarehouseId());
		candidateRecordToUse.setM_Product_ID(candidate.getProductId());
		candidateRecordToUse.setQty(candidate.getQuantity());
		candidateRecordToUse.setDateProjected(new Timestamp(candidate.getDate().getTime()));

		if (candidate.getSubType() != null)
		{
			candidateRecordToUse.setMD_Candidate_SubType(candidate.getSubType().toString());
		}

		if (candidate.getParentId() != null)
		{
			candidateRecordToUse.setMD_Candidate_Parent_ID(candidate.getParentId());
		}

		final ITableRecordReference referencedRecord = candidate.getReference();
		if (referencedRecord != null)
		{
			candidateRecordToUse.setAD_Table_ID(referencedRecord.getAD_Table_ID());
			candidateRecordToUse.setRecord_ID(referencedRecord.getRecord_ID());
		}
		return candidateRecordToUse;
	}

	private Optional<Candidate> fromCandidateRecord(final Optional<I_MD_Candidate> candidateRecordOpt)
	{
		if (candidateRecordOpt == null
				|| !candidateRecordOpt.isPresent())
		{
			return Optional.empty();
		}

		final I_MD_Candidate candidateRecord = candidateRecordOpt.get();

		if (InterfaceWrapperHelper.isNew(candidateRecord))
		{
			return Optional.empty();
		}

		CandidateBuilder builder = Candidate.builder()
				.id(candidateRecord.getMD_Candidate_ID())
				.orgId(candidateRecord.getAD_Org_ID())
				.parentId(candidateRecord.getMD_Candidate_Parent_ID())
				.productId(candidateRecord.getM_Product_ID())
				.quantity(candidateRecord.getQty())
				.type(Type.valueOf(candidateRecord.getMD_Candidate_Type()))
				.warehouseId(candidateRecord.getM_Warehouse_ID())

				// make sure to add a Date and not a Timestamp to make sure not to confuse Candidate's equals() and hashCode() methods
				.date(new Date(candidateRecord.getDateProjected().getTime()));

		if (candidateRecord.getRecord_ID() > 0)
		{
			builder = builder.reference(TableRecordReference.ofReferenced(candidateRecord));
		}

		return Optional.of(builder.build());
	}

	/**
	 *
	 * @param segment
	 * @return the "oldest" stock candidate that is <b>not</b> after the given {@code segment}'s date.
	 */
	public Optional<Candidate> retrieveStockAt(@NonNull final CandidatesSegment segment)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_MD_Candidate> builder = queryBL.createQueryBuilder(I_MD_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Candidate.COLUMN_MD_Candidate_Type, X_MD_Candidate.MD_CANDIDATE_TYPE_STOCK)
				.addEqualsFilter(I_MD_Candidate.COLUMN_M_Warehouse_ID, segment.getWarehouseId())
				.addEqualsFilter(I_MD_Candidate.COLUMN_M_Product_ID, segment.getProductId())
				.addCompareFilter(I_MD_Candidate.COLUMN_DateProjected, Operator.LESS_OR_EQUAL, segment.getDate());

		final I_MD_Candidate candidateRecord = builder
				.orderBy().addColumn(I_MD_Candidate.COLUMNNAME_DateProjected, false).endOrderBy()
				.create()
				.first();

		return fromCandidateRecord(Optional.ofNullable(candidateRecord));
	}

	/**
	 * Retrieve stock records that match the given segment's warehouse, product etc and have a timestamp later <b>or equal</b>.
	 * 
	 * @param segment
	 * @return
	 */
	public List<Candidate> retrieveStockFrom(@NonNull final CandidatesSegment segment)
	{
		final boolean from = true;
		return retrieveStockFromOrAfter(segment, from);
	}

	/**
	 * Retrieve stock records that match the given segment's warehouse, product etc and have a timestamp later <b>but not equal</b>.
	 * 
	 * @param segment
	 * @return
	 */
	public List<Candidate> retrieveStockAfter(@NonNull final CandidatesSegment segment)
	{
		final boolean from = false;
		return retrieveStockFromOrAfter(segment, from);
	}

	/**
	 *
	 * @param segment
	 * @param from if <code>true</code>, the also records with the same time are included. Otherwise, only records after the given segment's time are included.
	 * @return
	 */
	private List<Candidate> retrieveStockFromOrAfter(@NonNull final CandidatesSegment segment, final boolean from)
	{
		final IQueryBuilder<I_MD_Candidate> builder = Services.get(IQueryBL.class).createQueryBuilder(I_MD_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Candidate.COLUMN_MD_Candidate_Type, X_MD_Candidate.MD_CANDIDATE_TYPE_STOCK)
				.addEqualsFilter(I_MD_Candidate.COLUMN_M_Warehouse_ID, segment.getWarehouseId())
				.addEqualsFilter(I_MD_Candidate.COLUMN_M_Product_ID, segment.getProductId())
				.addCompareFilter(I_MD_Candidate.COLUMN_DateProjected,
						from ? Operator.GREATER_OR_EQUAL : Operator.GREATER,
						segment.getDate());

		final Stream<I_MD_Candidate> candidateRecords = builder
				.orderBy().addColumn(I_MD_Candidate.COLUMNNAME_DateProjected, true).endOrderBy()
				.create()
				.stream();

		return candidateRecords
				.map(record -> fromCandidateRecord(Optional.of(record)).get())
				.collect(Collectors.toList());
	}

	public Optional<Candidate> retrieveSingleStockFor(@NonNull final TableRecordReference reference)
	{

		final I_MD_Candidate candidateRecord = mkReferencedRecordFilter(reference)
				.addEqualsFilter(I_MD_Candidate.COLUMN_MD_Candidate_Type, X_MD_Candidate.MD_CANDIDATE_TYPE_STOCK)
				.create()
				.firstOnly(I_MD_Candidate.class);

		return fromCandidateRecord(Optional.ofNullable(candidateRecord));
	}

	/**
	 * Deletes all records that reference the given {@code referencedRecord}.
	 * 
	 * @param reference
	 */
	public void deleteForReference(@NonNull final TableRecordReference referencedRecord)
	{
		mkReferencedRecordFilter(referencedRecord)
				.create()
				.delete();
	}

	private IQueryBuilder<I_MD_Candidate> mkReferencedRecordFilter(final TableRecordReference reference)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_MD_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Candidate.COLUMN_AD_Table_ID, reference.getAD_Table_ID())
				.addEqualsFilter(I_MD_Candidate.COLUMN_Record_ID, reference.getRecord_ID());
	}
}

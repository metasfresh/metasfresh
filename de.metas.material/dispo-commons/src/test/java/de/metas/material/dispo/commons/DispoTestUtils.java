package de.metas.material.dispo.commons;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;

import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.X_MD_Candidate;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * metasfresh-material-dispo
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
@UtilityClass
public class DispoTestUtils
{
	public List<I_MD_Candidate> filter(@NonNull final CandidateType type)
	{
		final List<I_MD_Candidate> allRecords = retrieveAllRecords();
		return allRecords.stream()
				.filter(r -> type.toString().equals(r.getMD_Candidate_Type()))
				.collect(Collectors.toList());
	}

	public static List<I_MD_Candidate> filterExclStock()
	{
		final List<I_MD_Candidate> allRecords = retrieveAllRecords();
		return allRecords.stream()
				.filter(r -> !r.getMD_Candidate_Type().equals(X_MD_Candidate.MD_CANDIDATE_TYPE_STOCK))
				.collect(Collectors.toList());
	}

	public List<I_MD_Candidate> filter(@NonNull final CandidateType type, @NonNull final Date date)
	{
		return filter(type).stream()
				.filter(r -> date.getTime() == r.getDateProjected().getTime())
				.collect(Collectors.toList());
	}

	public List<I_MD_Candidate> filter(@NonNull final CandidateType type, @NonNull final Date date, final int productId)
	{
		return filter(type, date).stream()
				.filter(r -> r.getM_Product_ID() == productId)
				.collect(Collectors.toList());
	}

	public List<I_MD_Candidate> filter(@NonNull final CandidateType type, @NonNull final Date date, final int productId, final int warehouseId)
	{
		return filter(type, date, productId).stream()
				.filter(r -> r.getM_Warehouse_ID() == warehouseId)
				.collect(Collectors.toList());
	}

	public List<I_MD_Candidate> filter(@NonNull final CandidateType type, final int warehouseId)
	{
		return filter(type).stream()
				.filter(r -> r.getM_Warehouse_ID() == warehouseId)
				.collect(Collectors.toList());
	}

	public List<I_MD_Candidate> retrieveAllRecords()
	{
		final List<I_MD_Candidate> allRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_MD_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.orderBy().addColumn(I_MD_Candidate.COLUMN_MD_Candidate_ID).endOrderBy()
				.create().list();
		return allRecords;
	}

	public List<I_MD_Candidate> sortBySeqNo(@NonNull final List<I_MD_Candidate> candidateRecords)
	{
		final List<I_MD_Candidate> allRecordBySeqNo = DispoTestUtils.retrieveAllRecords().stream()
				.sorted(Comparator.comparing(I_MD_Candidate::getSeqNo))
				.collect(Collectors.toList());
		return allRecordBySeqNo;
	}

	public List<I_MD_Candidate> sortByDateProjected(@NonNull final List<I_MD_Candidate> candidateRecords)
	{
		final List<I_MD_Candidate> allRecordBySeqNo = DispoTestUtils.retrieveAllRecords().stream()
				.sorted(Comparator.comparing(I_MD_Candidate::getDateProjected))
				.collect(Collectors.toList());
		return allRecordBySeqNo;
	}
}

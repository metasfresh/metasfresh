package de.metas.materialtracking.ch.lagerkonf.impl;

/*
 * #%L
 * de.metas.materialtracking
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy;

import de.metas.materialtracking.ch.lagerkonf.IQualityInspLagerKonfDAO;
import de.metas.materialtracking.ch.lagerkonf.model.I_M_QualityInsp_LagerKonf_AdditionalFee;
import de.metas.materialtracking.ch.lagerkonf.model.I_M_QualityInsp_LagerKonf_Month_Adj;
import de.metas.materialtracking.ch.lagerkonf.model.I_M_QualityInsp_LagerKonf_ProcessingFee;
import de.metas.materialtracking.ch.lagerkonf.model.I_M_QualityInsp_LagerKonf_Version;
import de.metas.util.Services;

public class QualityInspLagerKonfDAO implements IQualityInspLagerKonfDAO
{

	@Override
	public List<I_M_QualityInsp_LagerKonf_Month_Adj> retriveMonthAdjustments(final I_M_QualityInsp_LagerKonf_Version qualityInspLagerKonfVersion)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryOrderBy orderBy =
				queryBL.createQueryOrderByBuilder(I_M_QualityInsp_LagerKonf_Month_Adj.class)
				.addColumn(I_M_QualityInsp_LagerKonf_Month_Adj.COLUMN_M_QualityInsp_LagerKonf_Month_Adj_ID)
						.createQueryOrderBy();

		return queryBL.createQueryBuilder(I_M_QualityInsp_LagerKonf_Month_Adj.class, qualityInspLagerKonfVersion)
				.addEqualsFilter(I_M_QualityInsp_LagerKonf_Month_Adj.COLUMN_M_QualityInsp_LagerKonf_Version_ID, qualityInspLagerKonfVersion.getM_QualityInsp_LagerKonf_Version_ID())
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.create()
				.setOrderBy(orderBy)
				.list();
	}

	@Override
	public List<I_M_QualityInsp_LagerKonf_ProcessingFee> retriveProcessingFees(final I_M_QualityInsp_LagerKonf_Version qualityInspLagerKonfVersion)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryOrderBy orderBy =
				queryBL.createQueryOrderByBuilder(I_M_QualityInsp_LagerKonf_ProcessingFee.class)
				.addColumn(I_M_QualityInsp_LagerKonf_ProcessingFee.COLUMN_M_QualityInsp_LagerKonf_ProcessingFee_ID)
						.createQueryOrderBy();

		return queryBL.createQueryBuilder(I_M_QualityInsp_LagerKonf_ProcessingFee.class, qualityInspLagerKonfVersion)
				.addEqualsFilter(I_M_QualityInsp_LagerKonf_ProcessingFee.COLUMN_M_QualityInsp_LagerKonf_Version_ID, qualityInspLagerKonfVersion.getM_QualityInsp_LagerKonf_Version_ID())
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.create()
				.setOrderBy(orderBy)
				.list();
	}

	@Override
	public List<I_M_QualityInsp_LagerKonf_AdditionalFee> retriveAdditionalFees(final I_M_QualityInsp_LagerKonf_Version qualityInspLagerKonfVersion)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryOrderBy orderBy =
				queryBL.createQueryOrderByBuilder(I_M_QualityInsp_LagerKonf_AdditionalFee.class)
				.addColumn(I_M_QualityInsp_LagerKonf_AdditionalFee.COLUMN_SeqNo)
						.createQueryOrderBy();

		return queryBL.createQueryBuilder(I_M_QualityInsp_LagerKonf_AdditionalFee.class, qualityInspLagerKonfVersion)
				.addEqualsFilter(I_M_QualityInsp_LagerKonf_AdditionalFee.COLUMN_M_QualityInsp_LagerKonf_Version_ID, qualityInspLagerKonfVersion.getM_QualityInsp_LagerKonf_Version_ID())
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.create()
				.setOrderBy(orderBy)
				.list();
	}
}

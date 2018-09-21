package de.metas.materialtracking.impl;

import java.util.Iterator;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.eevolution.model.I_PP_Order;

import de.metas.inout.model.I_M_InOutLine;
import de.metas.materialtracking.IMaterialTrackingReportDAO;
import de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report;
import de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report_Line;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_M_Material_Tracking_Ref;
import de.metas.util.Check;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.materialtracking
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class MaterialTrackingReportDAO implements IMaterialTrackingReportDAO
{

	@Override
	public Iterator<I_M_Material_Tracking_Ref> retrieveMaterialTrackingRefsForMaterialTracking(final I_M_Material_Tracking materialTracking)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		Check.assumeNotNull(materialTracking, "materialTracking not null");

		final IQueryBuilder<I_M_Material_Tracking_Ref> queryBuilder = queryBL
				.createQueryBuilder(I_M_Material_Tracking_Ref.class, materialTracking)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Material_Tracking_Ref.COLUMNNAME_M_Material_Tracking_ID, materialTracking.getM_Material_Tracking_ID());

		final ICompositeQueryFilter<I_M_Material_Tracking_Ref> tablesFilter = queryBL.createCompositeQueryFilter(I_M_Material_Tracking_Ref.class)
				.setJoinOr()
				.addEqualsFilter(I_M_Material_Tracking_Ref.COLUMNNAME_AD_Table_ID, InterfaceWrapperHelper.getTableId(I_M_InOutLine.class))

				.addEqualsFilter(I_M_Material_Tracking_Ref.COLUMNNAME_AD_Table_ID, InterfaceWrapperHelper.getTableId(I_PP_Order.class));

		queryBuilder.filter(tablesFilter);

		queryBuilder.orderBy().addColumn(I_M_Material_Tracking_Ref.COLUMNNAME_M_Material_Tracking_Ref_ID);

		return queryBuilder.create()
				.iterate(I_M_Material_Tracking_Ref.class);
	}

	@Override
	public I_M_Material_Tracking_Report_Line retrieveMaterialTrackingReportLineOrNull(final I_M_Material_Tracking_Report report, final String aggregationKey)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_M_Material_Tracking_Report_Line> queryBuilder = queryBL
				.createQueryBuilder(I_M_Material_Tracking_Report_Line.class, report)
				.addEqualsFilter(I_M_Material_Tracking_Report_Line.COLUMNNAME_M_Material_Tracking_Report_ID, report.getM_Material_Tracking_Report_ID())
				.addEqualsFilter(I_M_Material_Tracking_Report_Line.COLUMNNAME_LineAggregationKey, aggregationKey);
		queryBuilder

				.orderBy()
				.addColumn(I_M_Material_Tracking_Report_Line.COLUMNNAME_LineAggregationKey);

		return queryBuilder
				.create()
				.firstOnly(I_M_Material_Tracking_Report_Line.class);

	}
}

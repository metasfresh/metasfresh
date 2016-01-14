package de.metas.materialtracking.impl;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.model.I_M_AttributeSetInstance;

import de.metas.dimension.IDimensionSpecAttributeBL;
import de.metas.dimension.IDimensionspecDAO;
import de.metas.dimension.model.I_DIM_Dimension_Spec;
import de.metas.materialtracking.IMaterialTrackingReportBL;
import de.metas.materialtracking.MaterialTrackingConstants;
import de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report;
import de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report_Line;
import de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report_Line_Alloc;
import de.metas.materialtracking.model.I_M_InOutLine;
import de.metas.materialtracking.model.I_M_Material_Tracking_Ref;
import de.metas.materialtracking.model.I_PP_Order;

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

public class MaterialTrackingReportBL implements IMaterialTrackingReportBL
{

	@Override
	public I_M_Material_Tracking_Report_Line createMaterialTrackingReportLine(final I_M_Material_Tracking_Report report, final I_M_Material_Tracking_Ref ref)
	{

		final I_M_Material_Tracking_Report_Line newLine = InterfaceWrapperHelper.newInstance(I_M_Material_Tracking_Report_Line.class, ref);

		newLine.setM_Material_Tracking_Report(report);

		final org.compiere.model.I_M_Product product = ref.getM_Material_Tracking().getM_Product();

		newLine.setM_Product(product);

		final I_M_AttributeSetInstance asi = createASIFromRef(ref);

		newLine.setM_AttributeSetInstance(asi);

		return newLine;

	}

	private I_M_AttributeSetInstance createASIFromRef(final I_M_Material_Tracking_Ref ref)
	{
		final IDimensionspecDAO dimSpecDAO = Services.get(IDimensionspecDAO.class);

		final IContextAware ctxAware = InterfaceWrapperHelper.getContextAware(ref);
		final String trxName = InterfaceWrapperHelper.getTrxName(ref);

		final int table_ID = ref.getAD_Table_ID();

		// the ASI of the ref's linked record (inoutLine or ppOrder)
		final I_M_AttributeSetInstance refASI;
		if (InterfaceWrapperHelper.getTableId(I_PP_Order.class) == table_ID)
		{
			final I_PP_Order ppOrder = InterfaceWrapperHelper.create(ctxAware.getCtx(), ref.getRecord_ID(), I_PP_Order.class, trxName);

			refASI = ppOrder.getM_AttributeSetInstance();

		}

		else if (InterfaceWrapperHelper.getTableId(I_M_InOutLine.class) == table_ID)
		{
			final I_M_InOutLine iol = InterfaceWrapperHelper.create(ctxAware.getCtx(), ref.getRecord_ID(), I_M_InOutLine.class, trxName);
			refASI = iol.getM_AttributeSetInstance();

		}
		else
		{
			throw new AdempiereException("Not supported for table ID" + table_ID);
		}

		final String internalName = Services.get(ISysConfigBL.class).getValue(MaterialTrackingConstants.SYSCONFIG_M_Material_Tracking_Report_Dimension);

		final I_DIM_Dimension_Spec dimensionSpec = dimSpecDAO.retrieveForInternalName(internalName, ctxAware);

		final I_M_AttributeSetInstance resultASI = Services.get(IDimensionSpecAttributeBL.class).createASIForDimensionSpec(refASI, dimensionSpec);

		return resultASI;
	}

	@Override
	public I_M_Material_Tracking_Report_Line_Alloc createMaterialTrackingReportLineAllocation(final I_M_Material_Tracking_Report_Line line, final I_M_Material_Tracking_Ref ref)
	{
		final I_M_Material_Tracking_Report_Line_Alloc alloc = InterfaceWrapperHelper.newInstance(I_M_Material_Tracking_Report_Line_Alloc.class, ref);

		alloc.setM_Material_Tracking_Report_Line(line);
		alloc.setAD_Table(ref.getAD_Table());
		alloc.setRecord_ID(ref.getRecord_ID());
		alloc.setM_Material_Tracking(ref.getM_Material_Tracking());

		InterfaceWrapperHelper.save(alloc);

		return alloc;
	}

}

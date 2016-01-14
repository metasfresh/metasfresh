package de.metas.materialtracking.process;

import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.adempiere.util.agg.key.IAggregationKeyBuilder;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.util.KeyNamePair;

import de.metas.dimension.IDimensionSpecAttributeBL;
import de.metas.dimension.IDimensionSpecAttributeDAO;
import de.metas.dimension.IDimensionspecDAO;
import de.metas.dimension.model.I_DIM_Dimension_Spec;
import de.metas.materialtracking.MaterialTrackingConstants;
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

public class CreateMaterialTrackingReportLineFromMaterialTrackingRefKeyBuilder implements IAggregationKeyBuilder<I_M_Material_Tracking_Ref>
{

	final IDimensionSpecAttributeDAO dimSpecAttrDAO = Services.get(IDimensionSpecAttributeDAO.class);
	final IDimensionSpecAttributeBL dimSpecAttrBL = Services.get(IDimensionSpecAttributeBL.class);

	final IDimensionspecDAO dimSpecDAO = Services.get(IDimensionspecDAO.class);

	CreateMaterialTrackingReportLineFromMaterialTrackingRefKeyBuilder()
	{

		super();

	}

	@Override
	public String buildKey(final I_M_Material_Tracking_Ref ref)
	{
		final IContextAware ctxAware = InterfaceWrapperHelper.getContextAware(ref);
		
		final String internalName = Services.get(ISysConfigBL.class).getValue(MaterialTrackingConstants.SYSCONFIG_M_Material_Tracking_Report_Dimension);
		
		final I_DIM_Dimension_Spec dimensionSpec = dimSpecDAO.retrieveForInternalName(internalName, ctxAware);
		
		final int table_ID = ref.getAD_Table_ID();

		final Properties ctx = InterfaceWrapperHelper.getCtx(ref);
		final String trxName = InterfaceWrapperHelper.getTrxName(ref);

		final int productID = ref.getM_Material_Tracking().getM_Product_ID();

		final I_M_AttributeSetInstance asi;

		if (InterfaceWrapperHelper.getTableId(I_PP_Order.class) == table_ID)
		{
			final I_PP_Order ppOrder = InterfaceWrapperHelper.create(ctx, ref.getRecord_ID(), I_PP_Order.class, trxName);

			asi = ppOrder.getM_AttributeSetInstance();
		}

		else if (InterfaceWrapperHelper.getTableId(I_M_InOutLine.class) == table_ID)
		{
			final I_M_InOutLine iol = InterfaceWrapperHelper.create(ctx, ref.getRecord_ID(), I_M_InOutLine.class, trxName);

			asi = iol.getM_AttributeSetInstance();
		}
		else
		{
			throw new AdempiereException("Not suppoerted for table ID " + table_ID);
		}

		final StringBuilder keyBuilder = new StringBuilder();

		keyBuilder.append(productID);

		final List<KeyNamePair> attrToValues = dimSpecAttrBL.createAttrToValue(asi, dimensionSpec);

		for (final KeyNamePair attrToValue : attrToValues)
		{
			keyBuilder.append("#")
					.append(attrToValue.getKey())
					.append("-")
					.append(attrToValue.getName());
		}

		return keyBuilder.toString();
	}



	@Override
	public List<String> getDependsOnColumnNames()
	{
		throw new UnsupportedOperationException("getDependsOnColumnNames() is not supported in this applciation");
	}

	@Override
	public boolean isSame(final I_M_Material_Tracking_Ref item1, final I_M_Material_Tracking_Ref item2)
	{
		throw new UnsupportedOperationException("isSame() is not supported in this applciation");
	}

}

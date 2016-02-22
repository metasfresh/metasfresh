package de.metas.materialtracking.process;

import java.util.List;

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

public class CreateMaterialTrackingReportLineFromMaterialTrackingRefKeyBuilder implements IAggregationKeyBuilder<MaterialTrackingReportAgregationItem>
{

	final IDimensionSpecAttributeDAO dimSpecAttrDAO = Services.get(IDimensionSpecAttributeDAO.class);
	final IDimensionSpecAttributeBL dimSpecAttrBL = Services.get(IDimensionSpecAttributeBL.class);

	final IDimensionspecDAO dimSpecDAO = Services.get(IDimensionspecDAO.class);

	CreateMaterialTrackingReportLineFromMaterialTrackingRefKeyBuilder()
	{

		super();

	}

	@Override
	public String buildKey(final MaterialTrackingReportAgregationItem item)
	{
		final I_M_InOutLine iol = item.getInOutLine();

		final IContextAware ctxAware = InterfaceWrapperHelper.getContextAware(iol);
		final String internalName = Services.get(ISysConfigBL.class).getValue(MaterialTrackingConstants.SYSCONFIG_M_Material_Tracking_Report_Dimension);

		final I_DIM_Dimension_Spec dimensionSpec = dimSpecDAO.retrieveForInternalName(internalName, ctxAware);

		final int productID = iol.getM_Product_ID();

		final I_M_AttributeSetInstance asi = iol.getM_AttributeSetInstance();

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
	public boolean isSame(final MaterialTrackingReportAgregationItem item1, final MaterialTrackingReportAgregationItem item2)
	{
		throw new UnsupportedOperationException("isSame() is not supported in this applciation");
	}

}

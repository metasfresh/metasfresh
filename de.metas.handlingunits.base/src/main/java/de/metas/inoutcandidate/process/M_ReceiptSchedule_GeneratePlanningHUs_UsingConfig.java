package de.metas.inoutcandidate.process;

import java.math.BigDecimal;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.Param;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class M_ReceiptSchedule_GeneratePlanningHUs_UsingConfig extends M_ReceiptSchedule_GeneratePlanningHUs_Base implements IProcessDefaultParametersProvider
{
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		switch (parameter.getColumnName())
		{
			case PARAM_M_HU_PI_Item_Product_ID:
				return getCurrentLUTUConfiguration().getM_HU_PI_Item_Product_ID();
			case PARAM_M_LU_HU_PI_ID:
				return getCurrentLUTUConfiguration().getM_LU_HU_PI_ID();
			case PARAM_QtyCU:
				return getCurrentLUTUConfiguration().getQtyCU();
			case PARAM_QtyTU:
				return getCurrentLUTUConfiguration().getQtyTU();
			case PARAM_QtyLU:
				return getCurrentLUTUConfiguration().getQtyLU();
			default:
				return DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	//
	// Parameters
	private static final String PARAM_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";
	@Param(parameterName = PARAM_M_HU_PI_Item_Product_ID)
	private int p_M_HU_PI_Item_Product_ID;
	//
	private static final String PARAM_M_LU_HU_PI_ID = "M_LU_HU_PI_ID";
	@Param(parameterName = PARAM_M_LU_HU_PI_ID)
	private int p_M_LU_HU_PI_ID;
	//
	private static final String PARAM_QtyCU = "QtyCU";
	@Param(parameterName = PARAM_QtyCU)
	private BigDecimal p_QtyCU;
	//
	private static final String PARAM_QtyTU = "QtyTU";
	@Param(parameterName = PARAM_QtyTU)
	private BigDecimal p_QtyTU;
	//
	private static final String PARAM_QtyLU = "QtyLU";
	@Param(parameterName = PARAM_QtyLU)
	private BigDecimal p_QtyLU;

	@Override
	protected I_M_HU_LUTU_Configuration updateM_HU_LUTU_Configuration(final I_M_HU_LUTU_Configuration lutuConfigurationToEdit)
	{
		//
		// CU
		lutuConfigurationToEdit.setQtyCU(p_QtyCU);

		//
		// TU
		final I_M_HU_PI_Item_Product tuPIItemProduct = InterfaceWrapperHelper.create(getCtx(), p_M_HU_PI_Item_Product_ID, I_M_HU_PI_Item_Product.class, ITrx.TRXNAME_None);
		final I_M_HU_PI tuPI = tuPIItemProduct.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI();
		lutuConfigurationToEdit.setM_HU_PI_Item_Product(tuPIItemProduct);
		lutuConfigurationToEdit.setM_TU_HU_PI(tuPI);
		lutuConfigurationToEdit.setQtyTU(p_QtyTU);

		//
		// LU
		if (p_M_LU_HU_PI_ID > 0)
		{
			final I_M_HU_PI luPI = InterfaceWrapperHelper.create(getCtx(), p_M_LU_HU_PI_ID, I_M_HU_PI.class, ITrx.TRXNAME_None);

			final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
			final I_M_HU_PI_Version luPIV = handlingUnitsDAO.retrievePICurrentVersion(luPI);
			final I_M_HU_PI_Item luPI_Item = handlingUnitsDAO.retrieveParentPIItemsForParentPI(tuPI, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit, lutuConfigurationToEdit.getC_BPartner())
					.stream()
					.filter(piItem -> piItem.getM_HU_PI_Version_ID() == luPIV.getM_HU_PI_Version_ID())
					.findFirst()
					.orElseThrow(() -> new AdempiereException("@NotFound@ @M_HU_PI_Version_ID@"));

			lutuConfigurationToEdit.setM_LU_HU_PI(luPI);
			lutuConfigurationToEdit.setM_LU_HU_PI_Item(luPI_Item);
			lutuConfigurationToEdit.setQtyLU(p_QtyLU);
		}
		else
		{
			lutuConfigurationToEdit.setM_LU_HU_PI(null);
			lutuConfigurationToEdit.setM_LU_HU_PI_Item(null);
			lutuConfigurationToEdit.setQtyLU(null);
		}

		InterfaceWrapperHelper.save(lutuConfigurationToEdit);

		return lutuConfigurationToEdit;
	}

}

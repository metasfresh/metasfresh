package de.metas.procurement.base;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.procurement.base.model.I_C_Flatrate_DataEntry;

/*
 * #%L
 * de.metas.procurement.base
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

/**
 * Pricing aware model:
 * <ul>
 * <li>contains all informations to calculate the pricing
 * <li>has setters to set pricing result fields
 * </ul>
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IPMMPricingAware
{
	Properties getCtx();
	
	I_C_BPartner getC_BPartner();

	boolean isContractedProduct();

	I_M_Product getM_Product();

	I_C_UOM getC_UOM();

	I_C_Flatrate_Term getC_Flatrate_Term();

	I_C_Flatrate_DataEntry getC_Flatrate_DataEntry();

	Object getWrappedModel();

	Timestamp getDate();

	BigDecimal getQty();

	void setM_PricingSystem_ID(int M_PricingSystem_ID);

	void setM_PriceList_ID(int M_PriceList_ID);

	void setC_Currency_ID(int C_Currency_ID);

	void setPrice(BigDecimal price);
}

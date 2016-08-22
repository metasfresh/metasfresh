package de.metas.procurement.base.order.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import com.google.common.base.MoreObjects;

import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.procurement.base.IPMMContractsBL;
import de.metas.procurement.base.IPMMPricingAware;
import de.metas.procurement.base.model.I_C_Flatrate_DataEntry;
import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;

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
 * Wraps a {@link I_PMM_PurchaseCandidate} and behaves like an {@link IPMMPricingAware}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
class PMMPricingAware_PurchaseCandidate implements IPMMPricingAware
{
	public static PMMPricingAware_PurchaseCandidate of(final I_PMM_PurchaseCandidate candidate)
	{
		return new PMMPricingAware_PurchaseCandidate(candidate);
	}

	private final I_PMM_PurchaseCandidate candidate;
	
	private PMMPricingAware_PurchaseCandidate(final I_PMM_PurchaseCandidate candidate)
	{
		super();
		Check.assumeNotNull(candidate, "candidate not null");
		this.candidate = candidate;
	}
	
	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("candidate", candidate)
				.toString();
	}

	@Override
	public Properties getCtx()
	{
		return InterfaceWrapperHelper.getCtx(candidate);
	}

	@Override
	public I_C_BPartner getC_BPartner()
	{
		return candidate.getC_BPartner();
	}

	@Override
	public boolean isContractedProduct()
	{
		final I_C_Flatrate_DataEntry flatrateDataEntry = getC_Flatrate_DataEntry();
		if (flatrateDataEntry == null)
		{
			return false;
		}
		
		// Consider that we have a contracted product only if the data entry has the Price or the QtyPlanned set (FRESH-568)
		return Services.get(IPMMContractsBL.class).hasPriceOrQty(flatrateDataEntry);
	}

	@Override
	public I_M_Product getM_Product()
	{
		return candidate.getM_Product();
	}

	@Override
	public I_C_UOM getC_UOM()
	{
		return candidate.getC_UOM();
	}

	@Override
	public I_C_Flatrate_Term getC_Flatrate_Term()
	{
		final I_C_Flatrate_DataEntry flatrateDataEntry = getC_Flatrate_DataEntry();
		if(flatrateDataEntry == null)
		{
			return null;
		}
		return flatrateDataEntry.getC_Flatrate_Term();
	}

	@Override
	public I_C_Flatrate_DataEntry getC_Flatrate_DataEntry()
	{
		return InterfaceWrapperHelper.create(candidate.getC_Flatrate_DataEntry(), I_C_Flatrate_DataEntry.class);
	}

	@Override
	public Object getWrappedModel()
	{
		return candidate;
	}

	@Override
	public Timestamp getDate()
	{
		return candidate.getDatePromised();
	}

	@Override
	public BigDecimal getQty()
	{
		// TODO: shall we use QtyToOrder instead... but that could affect our price (if we have some prices defined on breaks)
		return candidate.getQtyPromised();
	}

	@Override
	public void setM_PricingSystem_ID(int M_PricingSystem_ID)
	{
		candidate.setM_PricingSystem_ID(M_PricingSystem_ID);
	}

	@Override
	public void setM_PriceList_ID(int M_PriceList_ID)
	{
		candidate.setM_PriceList_ID(M_PriceList_ID);
	}

	@Override
	public void setC_Currency_ID(int C_Currency_ID)
	{
		candidate.setC_Currency_ID(C_Currency_ID);
	}

	@Override
	public void setPrice(BigDecimal priceStd)
	{
		candidate.setPrice(priceStd);
	}
}

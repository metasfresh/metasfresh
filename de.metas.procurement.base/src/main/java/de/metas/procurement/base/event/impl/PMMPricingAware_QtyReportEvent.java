package de.metas.procurement.base.event.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import com.google.common.base.MoreObjects;

import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.procurement.base.IPMMPricingAware;
import de.metas.procurement.base.model.I_C_Flatrate_DataEntry;
import de.metas.procurement.base.model.I_PMM_QtyReport_Event;

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
 * Wraps a {@link I_PMM_QtyReport_Event} and behaves like an {@link IPMMPricingAware}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class PMMPricingAware_QtyReportEvent implements IPMMPricingAware
{
	public static final PMMPricingAware_QtyReportEvent of(final I_PMM_QtyReport_Event qtyReportEvent)
	{
		return new PMMPricingAware_QtyReportEvent(qtyReportEvent);
	}

	private final I_PMM_QtyReport_Event qtyReportEvent;

	private PMMPricingAware_QtyReportEvent(final I_PMM_QtyReport_Event qtyReportEvent)
	{
		super();

		Check.assumeNotNull(qtyReportEvent, "qtyReportEvent not null");
		this.qtyReportEvent = qtyReportEvent;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("qtyReportEvent", qtyReportEvent)
				.toString();
	}
	
	@Override
	public Properties getCtx()
	{
		return InterfaceWrapperHelper.getCtx(qtyReportEvent);
	}

	@Override
	public I_C_BPartner getC_BPartner()
	{
		return qtyReportEvent.getC_BPartner();
	}

	@Override
	public boolean isContractedProduct()
	{
		final String contractLine_uuid = qtyReportEvent.getContractLine_UUID();
		return !Check.isEmpty(contractLine_uuid, true);
	}

	@Override
	public I_M_Product getM_Product()
	{
		return qtyReportEvent.getM_Product();
	}

	@Override
	public I_C_UOM getC_UOM()
	{
		return qtyReportEvent.getC_UOM();
	}

	@Override
	public I_C_Flatrate_Term getC_Flatrate_Term()
	{
		return qtyReportEvent.getC_Flatrate_Term();
	}

	@Override
	public I_C_Flatrate_DataEntry getC_Flatrate_DataEntry()
	{
		return InterfaceWrapperHelper.create(qtyReportEvent.getC_Flatrate_DataEntry(), I_C_Flatrate_DataEntry.class);
	}

	@Override
	public Object getWrappedModel()
	{
		return qtyReportEvent;
	}

	@Override
	public Timestamp getDate()
	{
		return qtyReportEvent.getDatePromised();
	}

	@Override
	public BigDecimal getQty()
	{
		return qtyReportEvent.getQtyPromised();
	}

	@Override
	public void setM_PricingSystem_ID(final int M_PricingSystem_ID)
	{
		qtyReportEvent.setM_PricingSystem_ID(M_PricingSystem_ID);
	}

	@Override
	public void setM_PriceList_ID(final int M_PriceList_ID)
	{
		qtyReportEvent.setM_PriceList_ID(M_PriceList_ID);
	}

	@Override
	public void setC_Currency_ID(final int C_Currency_ID)
	{
		qtyReportEvent.setC_Currency_ID(C_Currency_ID);
	}

	@Override
	public void setPrice(final BigDecimal price)
	{
		qtyReportEvent.setPrice(price);
	}
}

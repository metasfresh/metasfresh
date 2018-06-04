package org.eevolution.util;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.I_M_Warehouse;
import org.eevolution.model.I_DD_NetworkDistribution;
import org.eevolution.model.I_DD_NetworkDistributionLine;

public class DDNetworkBuilder
{
	private IContextAware _context;
	private String _name;
	private I_M_Shipper _shipper;

	//
	private I_DD_NetworkDistribution _ddNetwork;

	public DDNetworkBuilder setContext(IContextAware context)
	{
		this._context = context;
		return this;
	}

	private IContextAware getContext()
	{
		Check.assumeNotNull(_context, "_context not null");
		return _context;
	}

	public DDNetworkBuilder name(final String name)
	{
		this._name = name;
		return this;
	}

	private String getName()
	{
		Check.assumeNotEmpty(_name, "name not empty");
		return this._name;
	}

	public DDNetworkBuilder shipper(final I_M_Shipper shipper)
	{
		this._shipper = shipper;
		return this;
	}

	private I_M_Shipper getM_Shipper()
	{
		Check.assumeNotNull(_shipper, "shipper not null");
		return _shipper;
	}

	public I_DD_NetworkDistribution build()
	{
		return getDD_NetworkDistribution();
	}

	/** Gets/Creates the distribution network */
	public I_DD_NetworkDistribution getDD_NetworkDistribution()
	{
		if (_ddNetwork == null)
		{
			this._ddNetwork = create();
		}
		return _ddNetwork;
	}

	/** Gets/Creates the distribution network */
	public <T extends I_DD_NetworkDistribution> T getDD_NetworkDistribution(Class<T> modelClass)
	{
		return InterfaceWrapperHelper.create(getDD_NetworkDistribution(), modelClass);
	}

	private I_DD_NetworkDistribution create()
	{
		final String name = getName();

		final I_DD_NetworkDistribution ddNetwork = InterfaceWrapperHelper.newInstance(I_DD_NetworkDistribution.class, getContext());
		ddNetwork.setValue(name);
		ddNetwork.setName(name);
		ddNetwork.setDocumentNo(name);
		ddNetwork.setValidFrom(null);
		ddNetwork.setValidTo(null);

		InterfaceWrapperHelper.save(ddNetwork);
		return ddNetwork;
	}

	public DDNetworkBuilder createDDNetworkLine(
			final I_M_Warehouse warehouseFrom,
			final I_M_Warehouse warehouseTo
			)
	{
		final I_DD_NetworkDistribution ddNetwork = getDD_NetworkDistribution();
		final I_M_Shipper shipper = getM_Shipper();

		final I_DD_NetworkDistributionLine ddNetworkLine = InterfaceWrapperHelper.newInstance(I_DD_NetworkDistributionLine.class, ddNetwork);
		ddNetworkLine.setDD_NetworkDistribution(ddNetwork);
		ddNetworkLine.setM_WarehouseSource(warehouseFrom);
		ddNetworkLine.setM_Warehouse(warehouseTo);
		ddNetworkLine.setPercent(BigDecimal.valueOf(100));
		ddNetworkLine.setM_Shipper(shipper);
		ddNetworkLine.setTransfertTime(BigDecimal.ZERO);
		ddNetworkLine.setPriorityNo(0);
		ddNetworkLine.setValidFrom(null);
		ddNetworkLine.setValidTo(null);

		InterfaceWrapperHelper.save(ddNetworkLine);

		return this;
	}

}

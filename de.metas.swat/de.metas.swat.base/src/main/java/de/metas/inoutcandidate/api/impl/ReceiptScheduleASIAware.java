package de.metas.inoutcandidate.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

/*
 * #%L
 * de.metas.swat.base
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


import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;

import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Wraps an {@link I_M_ReceiptSchedule} and makes it behave like an {@link IAttributeSetInstanceAware}
 *
 * @author tsa
 *
 */
/* package */class ReceiptScheduleASIAware implements IAttributeSetInstanceAware
{
	private final I_M_ReceiptSchedule rs;

	public ReceiptScheduleASIAware(final I_M_ReceiptSchedule rs)
	{
		super();

		Check.assumeNotNull(rs, "rs not null");
		this.rs = rs;
	}

	@Override
	public I_M_Product getM_Product()
	{
		return loadOutOfTrx(rs.getM_Product_ID(), I_M_Product.class);
	}

	@Override
	public int getM_Product_ID()
	{
		return rs.getM_Product_ID();
	}

	@Override
	public I_M_AttributeSetInstance getM_AttributeSetInstance()
	{
		return Services.get(IReceiptScheduleBL.class).getM_AttributeSetInstance_Effective(rs);
	}

	@Override
	public int getM_AttributeSetInstance_ID()
	{
		return Services.get(IReceiptScheduleBL.class).getM_AttributeSetInstance_Effective_ID(rs);
	}

	@Override
	public void setM_AttributeSetInstance(I_M_AttributeSetInstance asi)
	{
		Services.get(IReceiptScheduleBL.class).setM_AttributeSetInstance_Effective(rs, asi);
	}
}

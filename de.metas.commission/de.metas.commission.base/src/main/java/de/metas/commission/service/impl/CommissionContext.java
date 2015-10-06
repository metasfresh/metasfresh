package de.metas.commission.service.impl;

/*
 * #%L
 * de.metas.commission.base
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


import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Util;

import de.metas.adempiere.model.I_M_Product;
import de.metas.commission.custom.type.ICommissionType;
import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.service.ICommissionContext;

public class CommissionContext implements ICommissionContext
{
	private final Properties ctx;
	private final String trxName;
	private final I_C_Sponsor sponsor;
	private final ICommissionType commissionType;
	private final I_M_Product product;
	private Timestamp date;
	private I_C_AdvComSystem comSystem;

	public CommissionContext(final I_C_Sponsor sponsor, final ICommissionType commissionType, final org.compiere.model.I_M_Product product, final Timestamp date)
	{
		// TODO Finally, the ctx and trxName may always be taken from the product
		if (sponsor != null)
		{
			ctx = InterfaceWrapperHelper.getCtx(sponsor);
			trxName = InterfaceWrapperHelper.getTrxName(sponsor);
		}
		else
		{
			ctx = InterfaceWrapperHelper.getCtx(product);
			trxName = InterfaceWrapperHelper.getTrxName(product);
		}
		this.sponsor = sponsor;

		this.commissionType = commissionType;
		this.product = InterfaceWrapperHelper.create(product, I_M_Product.class);
		this.date = date;
	}

	public CommissionContext(final I_C_Sponsor sponsor, final ICommissionType commissionType, final I_C_AdvComSystem comSystem, final org.compiere.model.I_M_Product product, final Timestamp date)
	{
		if (sponsor != null)
		{
			ctx = InterfaceWrapperHelper.getCtx(sponsor);
			trxName = InterfaceWrapperHelper.getTrxName(sponsor);
		}
		else
		{
			ctx = InterfaceWrapperHelper.getCtx(product);
			trxName = InterfaceWrapperHelper.getTrxName(product);
		}
		this.sponsor = sponsor;

		this.commissionType = commissionType;
		this.comSystem = comSystem;
		this.product = InterfaceWrapperHelper.create(product, I_M_Product.class);
		this.date = date;
	}

	private CommissionContext copy()
	{
		return new CommissionContext(sponsor, commissionType, product, date);
	}

	@Override
	public Properties getCtx()
	{
		return ctx;
	}

	@Override
	public String getTrxName()
	{
		return trxName;
	}

	@Override
	public I_C_Sponsor getC_Sponsor()
	{
		return sponsor;
	}

	@Override
	public Timestamp getDate()
	{
		return date;
	}

	@Override
	public ICommissionContext setDate(final Timestamp date)
	{
		if (Util.equals(this.date, date))
		{
			return this;
		}

		final CommissionContext ctxNew = copy();
		ctxNew.date = date == null ? null : (Timestamp)date.clone();
		return ctxNew;
	}

	@Override
	public ICommissionType getCommissionType()
	{
		return commissionType;
	}

	@Override
	public I_M_Product getM_Product()
	{
		return product;
	}

	@Override
	public I_C_AdvComSystem getC_AdvComSystem()
	{
		return getC_AdvComSystem_Type().getC_AdvComSystem();
	}

	@Override
	public I_C_AdvComSystem_Type getC_AdvComSystem_Type()
	{
		return commissionType.getComSystemType();
	}

	public I_C_AdvComSystem getComSystem()
	{
		return comSystem;
	}

}

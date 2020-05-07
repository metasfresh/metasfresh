package org.compiere.grid.ed;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import java.util.Properties;

import org.adempiere.util.Check;
import org.compiere.model.GridField;
import org.compiere.util.Env;

/**
 * Implementation of {@link IVPAttributeContext} which resolves all values by using window's context.
 *
 * This is the default implementation.
 *
 * @author tsa
 *
 */
/* package */class VPAttributeWindowContext implements IVPAttributeContext
{
	public static final VPAttributeWindowContext of(final Properties ctx, final int windowNo, final int tabNo)
	{
		return new VPAttributeWindowContext(ctx, windowNo, tabNo);
	}

	public static final VPAttributeWindowContext of(final GridField gridField)
	{
		final Properties ctx = gridField.getCtx();
		final int windowNo = gridField.getWindowNo();
		final int tabNo = gridField.getTabNo();
		return new VPAttributeWindowContext(ctx, windowNo, tabNo);
	}

	private final Properties ctx;
	private final int windowNo;
	private final int tabNo;

	private VPAttributeWindowContext(final Properties ctx, final int windowNo, final int tabNo)
	{
		super();

		Check.assumeNotNull(ctx, "ctx not null");
		this.ctx = ctx;

		this.windowNo = windowNo;
		this.tabNo = tabNo;
	}

	private final int getContextAsInt(final String columnName)
	{
		return Env.getContextAsInt(ctx, windowNo, tabNo, columnName);
	}

	@Override
	public final int getWindowNo()
	{
		return windowNo;
	}

	@Override
	public final int getTabNo()
	{
		return tabNo;
	}

	@Override
	public int getC_BPartner_ID()
	{
		return getContextAsInt("C_BPartner_ID");
	}

	@Override
	public int getM_Product_ID()
	{
		return getContextAsInt("M_Product_ID");
	}

	@Override
	public boolean isSOTrx()
	{
		return Env.isSOTrx(ctx, windowNo);
	}

	@Override
	public Boolean getSOTrx()
	{
		return Env.getSOTrxOrNull(ctx, windowNo);
	}

	@Override
	public int getM_Warehouse_ID()
	{
		return getContextAsInt("M_Warehouse_ID");
	}

	@Override
	public int getM_Locator_ID()
	{
		return getContextAsInt("M_Locator_ID");
	}

	@Override
	public int getC_DocType_ID()
	{
		return getContextAsInt("C_DocType_ID");
	}
}

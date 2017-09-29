package de.metas.adempiere.service.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import static org.adempiere.model.I_M_PackagingContainer.COLUMNNAME_M_PackagingContainer_ID;
import static org.adempiere.model.I_M_PackagingContainer.Table_ID;

import java.util.Properties;

import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.Lookup;
import org.compiere.model.MColumn;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MTable;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import de.metas.adempiere.service.IPackagingBL;
import de.metas.shipping.util.Constants;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Transportverpackung_%282009_0022_G61%29'>(2009_0022_G61)</a>"
 */
public class PackagingBL implements IPackagingBL
{
	@Override
	public boolean isDisplayNonItemsEnabled(final Properties ctx)
	{
		final boolean displayNonItemsDefault = false;
		final boolean displayNonItems = Services.get(ISysConfigBL.class).getBooleanValue(
				Constants.SYSCONFIG_SHIPMENT_PACKAGE_NON_ITEMS,
				displayNonItemsDefault,
				Env.getAD_Client_ID(ctx),
				Env.getAD_Org_ID(ctx));

		return displayNonItems;
	}

	@Override
	public Lookup createShipperLookup()
	{
		final MColumn c = MTable.get(Env.getCtx(), I_M_Shipper.Table_ID).getColumn(I_M_Shipper.COLUMNNAME_M_Shipper_ID);

		return createLookup(c);
	}

	private static Lookup createLookup(final I_AD_Column c)
	{
		try
		{
			final Lookup m_bundlesLookup = MLookupFactory.get(Env.getCtx(),
					-1, // WindowNo
					0, // Column_ID,
					DisplayType.Table, // AD_Reference_ID,
					c.getColumnName(), // ColumnName
					c.getAD_Reference_Value_ID(), // AD_Reference_Value_ID,
					false, // IsParent,
					IValidationRule.AD_Val_Rule_ID_Null // ValidationCode // 03271: no validation is required
			);
			return m_bundlesLookup;
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
	}
}

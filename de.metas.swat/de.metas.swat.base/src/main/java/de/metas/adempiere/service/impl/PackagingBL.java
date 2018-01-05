package de.metas.adempiere.service.impl;

import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
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
		final MColumn c = MTable.get(Env.getCtx(), I_M_Shipper.Table_Name).getColumn(I_M_Shipper.COLUMNNAME_M_Shipper_ID);

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
					Services.get(IADTableDAO.class).retrieveTableName(c.getAD_Table_ID()),
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

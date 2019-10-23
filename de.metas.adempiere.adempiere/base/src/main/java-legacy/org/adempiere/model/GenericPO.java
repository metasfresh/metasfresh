/*******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution * Copyright (C)
 * 1999-2006 Adempiere, Inc. All Rights Reserved. * This program is free
 * software; you can redistribute it and/or modify it * under the terms version
 * 2 of the GNU General Public License as published * by the Free Software
 * Foundation. This program is distributed in the hope * that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied * warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. * See the GNU General
 * Public License for more details. * You should have received a copy of the GNU
 * General Public License along * with this program; if not, write to the Free
 * Software Foundation, Inc., * 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307 USA. *
 * 
 * Copyright (C) 2004 Marco LOMBARDO. lombardo@mayking.com 
 * Contributor(s): Low Heng Sin hengsin@avantz.com
 * __________________________________________
 ******************************************************************************/

// ----------------------------------------------------------------------
// Generic PO.
package org.adempiere.model;

// import for GenericPO
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.PO;
import org.compiere.model.POInfo;

/**
 * Generic PO implementation, this can be use together with ModelValidator as alternative to the classic
 * generated model class and extend ( X_ & M_ ) approach.
 * 
 * Originally for used to insert/update data from adempieredata.xml file in 2pack.
 * 
 * @author Marco LOMBARDO
 * @contributor Low Heng Sin
 */
public class GenericPO extends PO
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6558017105997010172L;

	/**
	 * @param tableName
	 * @param ctx
	 * @param ID
	 */
	public GenericPO(String tableName, Properties ctx, int ID)
	{
		super(new PropertiesWrapper(ctx, tableName), ID, null, null);
	}

	/**
	 * @param tableName
	 * @param ctx
	 * @param rs
	 */
	public GenericPO(String tableName, Properties ctx, ResultSet rs)
	{
		super(new PropertiesWrapper(ctx, tableName), 0, null, rs);
	}

	/**
	 * @param tableName
	 * @param ctx
	 * @param ID
	 * @param trxName
	 */
	public GenericPO(String tableName, Properties ctx, int ID, String trxName)
	{
		super(new PropertiesWrapper(ctx, tableName), ID, trxName, null);
	}

	/**
	 * @param tableName
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public GenericPO(String tableName, Properties ctx, ResultSet rs, String trxName)
	{
		super(new PropertiesWrapper(ctx, tableName), 0, trxName, rs);
	}

	private int tableID = -1;
	private String tableName = null;

	/** Load Meta Data */
	@Override
	protected POInfo initPO(final Properties ctx)
	{
		final PropertiesWrapper wrapper = (PropertiesWrapper)ctx;
		setCtx(wrapper.source);
		tableName = wrapper.tableName;
		final POInfo poInfo = POInfo.getPOInfo(tableName);
		this.tableID = poInfo.getAD_Table_ID();
		return poInfo;
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("GenericPO[")
				.append("TableName=").append(get_TableName())
				.append(",ID=").append(get_ID())
				.append("]");
		return sb.toString();
	}

	public static final String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";
	public static final int AD_ORGTRX_ID_AD_Reference_ID = 130;

	/**
	 * Set Trx Organization. Performing or initiating organization
	 */
	public void setAD_OrgTrx_ID(int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID == 0)
			set_Value(COLUMNNAME_AD_OrgTrx_ID, null);
		else
			set_Value(COLUMNNAME_AD_OrgTrx_ID, new Integer(AD_OrgTrx_ID));
	}

	/**
	 * Get Trx Organization. Performing or initiating organization
	 */
	public int getAD_OrgTrx_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgTrx_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	@Override
	protected final GenericPO newInstance()
	{
		return new GenericPO(tableName, getCtx(), ID_NewInstanceNoInit);
	}

	@Override
	public final PO copy()
	{
		final GenericPO po = (GenericPO)super.copy();
		po.tableName = this.tableName;
		po.tableID = this.tableID;
		return po;
	}

} // GenericPO

/**
 * Wrapper class to workaround the limit of PO constructor that doesn't take a tableName or
 * tableID parameter. Note that in the generated class scenario ( X_ ), tableName and tableId
 * is generated as a static field.
 * 
 * @author Low Heng Sin
 *
 */
final class PropertiesWrapper extends Properties
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8887531951501323594L;
	protected Properties source;
	protected String tableName;

	PropertiesWrapper(Properties source, String tableName)
	{
		this.source = source;
		this.tableName = tableName;
	}
}

/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.logging.LogManager;

/**
 * Product Attribute
 *
 * @author Jorg Janke
 * @version $Id: MAttribute.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MAttribute extends X_M_Attribute
{
	/**
	 *
	 */
	private static final long serialVersionUID = 7869800574413317999L;

	/**
	 * Get Attributes Of Client
	 *
	 * @param ctx Properties
	 * @param onlyProductAttributes only Product Attributes
	 * @param onlyListAttributes only List Attributes
	 * @return array of attributes
	 */
	public static MAttribute[] getOfClient(Properties ctx,
			boolean onlyProductAttributes, boolean onlyListAttributes)
	{
		ArrayList<MAttribute> list = new ArrayList<MAttribute>();
		int AD_Client_ID = Env.getAD_Client_ID(ctx);
		String sql = "SELECT * FROM M_Attribute "
				+ "WHERE AD_Client_ID=? AND IsActive='Y'";
		if (onlyProductAttributes)
			sql += " AND IsInstanceAttribute='N'";
		if (onlyListAttributes)
			sql += " AND AttributeValueType='L'";
		sql += " ORDER BY Name";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, AD_Client_ID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MAttribute(ctx, rs, null));
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.error(sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}

		MAttribute[] retValue = new MAttribute[list.size()];
		list.toArray(retValue);
		s_log.debug("AD_Client_ID=" + AD_Client_ID + " - #" + retValue.length);
		return retValue;
	}	// getOfClient

	/** Logger */
	private static Logger s_log = LogManager.getLogger(MAttribute.class);

	/**
	 * Standard Constructor
	 *
	 * @param ctx context
	 * @param M_Attribute_ID id
	 * @param trxName transaction
	 */
	public MAttribute(Properties ctx, int M_Attribute_ID, String trxName)
	{
		super(ctx, M_Attribute_ID, trxName);
		if (M_Attribute_ID == 0)
		{
			setAttributeValueType(ATTRIBUTEVALUETYPE_StringMax40);
			setIsInstanceAttribute(false);
			setIsMandatory(false);
		}
	}	// MAttribute

	/**
	 * Load Constructor
	 *
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MAttribute(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MAttribute

	/** Values */
	private I_M_AttributeValue[] m_values = null;

	/**
	 * Get Values if List
	 *
	 * @return Values or null if not list
	 */
	public I_M_AttributeValue[] getMAttributeValues()
	{
		return getMAttributeValues(null);
	}

	/**
	 * Get Values if List
	 *
	 * @param isSOTrx may be null; if set, then only return values with <code>AvailableTrx = SO</code> (for <code>true</code>) or <code>AvailableTrx = PO</code> (for <code>false</code>).
	 * @return Values or null if not list
	 */
	public I_M_AttributeValue[] getMAttributeValues(Boolean isSOTrx)
	{
		if (m_values == null && ATTRIBUTEVALUETYPE_List.equals(getAttributeValueType()))
		{
			final List<I_M_AttributeValue> list = new ArrayList<I_M_AttributeValue>();
			if (!isMandatory())
				list.add(null);
			//
			list.addAll(Services.get(IAttributeDAO.class).retrieveFilteredAttributeValues(this, isSOTrx));

			m_values = new I_M_AttributeValue[list.size()];
			list.toArray(m_values);
		}
		return m_values;
	}	// getValues

	/**
	 * Get Attribute Instance.
	 *
	 * The attribute instance is loaded using {@link ITrx#TRXNAME_ThreadInherited} transaction.
	 *
	 * @param M_AttributeSetInstance_ID attribute set instance
	 * @return Attribute Instance or null
	 */
	public MAttributeInstance getMAttributeInstance(final int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID <= 0)
		{
			// task 07948
			// note: in fact there is an asi with ID = null (created in 2000), but we want to ignore it,
			// and "not" load it's attribute instances into any uninitialized VPAttributeDialog
			// note that ASI-ID=0 shall not have instances, so we can spare us the DB-access
			return null;
		}

		final I_M_AttributeInstance attributeInstance = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_AttributeInstance.class, Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.addEqualsFilter(I_M_AttributeInstance.COLUMNNAME_M_AttributeSetInstance_ID, M_AttributeSetInstance_ID)
				.addEqualsFilter(I_M_AttributeInstance.COLUMNNAME_M_Attribute_ID, getM_Attribute_ID())
				.addOnlyActiveRecordsFilter() // inactive M_AttributeInstances shouldn't occur, but e.g. we could deactivate certain records via DB-updates under certain situations.
				.create()
				.firstOnly(I_M_AttributeInstance.class);
		return LegacyAdapters.convertToPO(attributeInstance);
	}	// getAttributeInstance

	/**
	 * Set Attribute Instance and saves it.
	 *
	 * The attribute instance is loaded and saved using {@link ITrx#TRXNAME_ThreadInherited} transaction.
	 *
	 * The method makes sure that there is an Attribute Instance for the given <code>M_AttributeSetInstance_ID</code> and that it has the given <code>value</code> value.
	 *
	 * @param M_AttributeSetInstance_ID id
	 * @param attributeValue the value to use. <b>We are using {@link I_M_AttributeValue#getValue()}, not <code>getName</code></b> (task 06907)
	 */
	public void setMAttributeInstance(final int M_AttributeSetInstance_ID, final I_M_AttributeValue attributeValue)
	{
		// task 07948
		// note: in fact there is an asi with ID = null (created in 2000), but we want to ignore it;
		// it's a special one and it shall not have any Attribute instances
		Check.errorIf(M_AttributeSetInstance_ID <= 0, "This method shall not be called with M_AttributeSetInstance_ID=0");

		//
		// Extract M_AttributeValue_ID and Value
		final int attributeValueId;
		final String value;
		if (attributeValue != null)
		{
			attributeValueId = attributeValue.getM_AttributeValue_ID();
			value = attributeValue.getValue(); // 06907: store the value, not the name!
		}
		else
		{
			attributeValueId = 0;
			value = null;
		}

		//
		// Get/Create the attribute instance
		MAttributeInstance instance = getMAttributeInstance(M_AttributeSetInstance_ID);
		if (instance == null)
		{
			instance = new MAttributeInstance(getCtx(), getM_Attribute_ID(),
					M_AttributeSetInstance_ID,
					attributeValueId,
					value,
					ITrx.TRXNAME_ThreadInherited);
		}

		//
		// Update the attribute instance & save
		instance.setM_AttributeValue(attributeValue);
		instance.setValue(value);
		InterfaceWrapperHelper.save(instance);
	}

	/**
	 * Loads or creates an {@link I_M_AttributeInstance}, sets its {@link I_M_AttributeInstance#COLUMN_Value} and saves it.
	 *
	 * The attribute instance is loaded and saved using {@link ITrx#TRXNAME_ThreadInherited} transaction.
	 *
	 * @param M_AttributeSetInstance_ID id
	 * @param value the string value to set.<br>
	 *            <b>Important (gh #487):</b> both the empty string and <code>null</code> is stored as <code>null</code>.<br>
	 *            This is because until some time ago, <i>every</i> empty string was stored as <code>null</code> in the database and we want to preserve this behavior in this particular situation.<br>
	 *            The reason for that is that a number of old jasper reports, and views and functions might still rely on this.
	 *
	 */
	public void setMAttributeInstance(final int M_AttributeSetInstance_ID, final String value)
	{
		// task 07948
		// note: in fact there is an asi with ID = null (created in 2000), but we want to ignore it;
		// it's a special one and it shall not have any Attribute instances
		Check.errorIf(M_AttributeSetInstance_ID <= 0, "This method shall not be called with M_AttributeSetInstance_ID=0");

		final String valueToStore = Check.isEmpty(value, false) ? null : value; // gh #487

		I_M_AttributeInstance instance = getMAttributeInstance(M_AttributeSetInstance_ID);
		if (instance == null)
		{
			instance = new MAttributeInstance(getCtx(), getM_Attribute_ID(), M_AttributeSetInstance_ID, valueToStore, ITrx.TRXNAME_ThreadInherited);
		}
		else
		{
			instance.setValue(valueToStore);
		}

		InterfaceWrapperHelper.save(instance);
	}	// setAttributeInstance

	/**
	 * Set Attribute Instance and saves it.
	 *
	 * The attribute instance is loaded and saved using {@link ITrx#TRXNAME_ThreadInherited} transaction.
	 *
	 * @param M_AttributeSetInstance_ID id
	 * @param value number value
	 */
	public void setMAttributeInstance(int M_AttributeSetInstance_ID, BigDecimal value)
	{
		// task 07948
		// note: in fact there is an asi with ID = null (created in 2000), but we want to ignore it;
		// it's a special one and it shall not have any Attribute instances
		Check.errorIf(M_AttributeSetInstance_ID <= 0, "This method shall not be called with M_AttributeSetInstance_ID=0");

		MAttributeInstance instance = getMAttributeInstance(M_AttributeSetInstance_ID);
		if (instance == null)
			instance = new MAttributeInstance(getCtx(), getM_Attribute_ID(),
					M_AttributeSetInstance_ID, value, ITrx.TRXNAME_ThreadInherited);
		else
			instance.setValueNumber(value);

		InterfaceWrapperHelper.save(instance);
	}	// setAttributeInstance

	/**
	 * Set Attribute Instance and saves it.
	 *
	 * The attribute instance is loaded and saved using {@link ITrx#TRXNAME_ThreadInherited} transaction.
	 *
	 * @param M_AttributeSetInstance_ID id
	 * @param value date value
	 */
	public void setMAttributeInstance(int M_AttributeSetInstance_ID, Timestamp value)
	{
		// task 07948
		// note: in fact there is an asi with ID = null (created in 2000), but we want to ignore it;
		// it's a special one and it shall not have any Attribute instances
		Check.errorIf(M_AttributeSetInstance_ID <= 0, "This method shall not be called with M_AttributeSetInstance_ID=0");

		MAttributeInstance instance = getMAttributeInstance(M_AttributeSetInstance_ID);
		if (instance == null)
		{
			instance = new MAttributeInstance(getCtx(), 0, ITrx.TRXNAME_ThreadInherited);
			instance.setM_Attribute(this);
			instance.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
		}

		instance.setValueDate(value);

		InterfaceWrapperHelper.save(instance);
	}	// setAttributeInstance

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("MAttribute[");
		sb.append(get_ID()).append("-").append(getName())
				.append(",Type=").append(getAttributeValueType())
				.append(",Instance=").append(isInstanceAttribute())
				.append("]");
		return sb.toString();
	}	// toString

	/**
	 * AfterSave
	 *
	 * @param newRecord new
	 * @param success success
	 * @return success
	 */
	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		// Changed to Instance Attribute
		if (!newRecord && is_ValueChanged("IsInstanceAttribute") && isInstanceAttribute())
		{
			String sql = "UPDATE M_AttributeSet mas "
					+ "SET IsInstanceAttribute='Y' "
					+ "WHERE IsInstanceAttribute='N'"
					+ " AND EXISTS (SELECT * FROM M_AttributeUse mau "
					+ "WHERE mas.M_AttributeSet_ID=mau.M_AttributeSet_ID"
					+ " AND mau.M_Attribute_ID=" + getM_Attribute_ID() + ")";
			int no = DB.executeUpdate(sql, get_TrxName());
			log.debug("AttributeSet Instance set #" + no);
		}
		return success;
	}	// afterSave

	/**
	 * Get Record ID/ColumnName
	 *
	 * @return ID/ColumnName pair
	 */
	public org.compiere.util.KeyNamePair getKeyNamePair()
	{
		return new org.compiere.util.KeyNamePair(get_ID(), getName());
	}
}	// MAttribute

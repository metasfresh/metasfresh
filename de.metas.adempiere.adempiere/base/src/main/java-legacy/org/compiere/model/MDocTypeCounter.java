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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.document.IDocTypeDAO;
import de.metas.logging.LogManager;

/**
 * Counter Document Type Model
 *
 * @author Jorg Janke
 * @version $Id: MDocTypeCounter.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MDocTypeCounter extends X_C_DocTypeCounter
{
	/**
	 *
	 */
	private static final long serialVersionUID = 3469046560457430527L;

	/**
	 * Get Counter document for document type
	 * 
	 * @param ctx context
	 * @param C_DocType_ID base document
	 * @return counter document C_DocType_ID or 0 or -1 if no counter doc
	 */
	public static int getCounterDocType_ID(Properties ctx, int C_DocType_ID)
	{
		// Direct Relationship
		MDocTypeCounter dtCounter = getCounterDocType(ctx, C_DocType_ID);
		if (dtCounter != null)
		{
			if (!dtCounter.isCreateCounter() || !dtCounter.isValid())
				return -1;
			return dtCounter.getCounter_C_DocType_ID();
		}

		// task FRESH_417: docBaseType_Counter now has its own table.
		final String trxName = ITrx.TRXNAME_None;

		// Indirect Relationship
		int Counter_C_DocType_ID = 0;

		final I_C_DocType dt = InterfaceWrapperHelper.create(ctx, C_DocType_ID, I_C_DocType.class, trxName);
		if (!dt.isCreateCounter())
		{
			return -1;
		}

		final String cDocBaseType = Services.get(IDocTypeDAO.class).retrieveDocBaseTypeCounter(ctx, dt.getDocBaseType());
		if (cDocBaseType == null)
		{
			return 0;
		}

		MDocType[] counters = MDocType.getOfDocBaseType(ctx, cDocBaseType);
		for (int i = 0; i < counters.length; i++)
		{
			MDocType counter = counters[i];
			if (counter.isDefaultCounterDoc())
			{
				Counter_C_DocType_ID = counter.getC_DocType_ID();
				break;
			}
			if (counter.isDefault())
			{
				Counter_C_DocType_ID = counter.getC_DocType_ID();
			}
			else if (i == 0)
			{
				Counter_C_DocType_ID = counter.getC_DocType_ID();
			}
		}
		return Counter_C_DocType_ID;
	}	// getCounterDocType_ID

	/**
	 * Get (first) valid Counter document for document type
	 * 
	 * @param ctx context
	 * @param C_DocType_ID base document
	 * @return counter document (may be invalid) or null
	 */
	public static MDocTypeCounter getCounterDocType(Properties ctx, int C_DocType_ID)
	{
		Integer key = C_DocType_ID;
		MDocTypeCounter retValue = s_counter.get(key);
		if (retValue != null)
			return retValue;

		// Direct Relationship
		MDocTypeCounter temp = null;
		String sql = "SELECT * FROM C_DocTypeCounter WHERE C_DocType_ID=? AND IsActive='Y'";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_DocType_ID);

			rs = pstmt.executeQuery();

			while (rs.next() && retValue == null)
			{
				retValue = new MDocTypeCounter(ctx, rs, null);
				if (!retValue.isCreateCounter() || !retValue.isValid())
				{
					temp = retValue;
					retValue = null;
				}
			}
		}
		catch (Exception e)
		{
			s_log.error("getCounterDocType", e);
		}

		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		if (retValue != null)      	// valid
		{
			return retValue;
		}
		if (temp != null)      		// invalid
		{
			return temp;
		}
		return null;			// nothing found
	}	// getCounterDocType

	/**
	 * Get MDocTypeCounter from Cache
	 * 
	 * @param ctx context
	 * @param C_DocTypeCounter_ID id
	 * @return MDocTypeCounter
	 * @param trxName transaction
	 */
	public static MDocTypeCounter get(Properties ctx, int C_DocTypeCounter_ID, String trxName)
	{
		Integer key = new Integer(C_DocTypeCounter_ID);
		MDocTypeCounter retValue = s_cache.get(key);
		if (retValue != null)
			return retValue;
		retValue = new MDocTypeCounter(ctx, C_DocTypeCounter_ID, trxName);
		if (retValue.get_ID() != 0)
			s_cache.put(key, retValue);
		return retValue;
	}	// get

	/** Object Cache */
	private static CCache<Integer, MDocTypeCounter> s_cache = new CCache<Integer, MDocTypeCounter>("C_DocTypeCounter", 20);
	/** Counter Relationship Cache */
	private static CCache<Integer, MDocTypeCounter> s_counter = new CCache<Integer, MDocTypeCounter>("C_DocTypeCounter", 20);
	/** Static Logger */
	private static Logger s_log = LogManager.getLogger(MDocTypeCounter.class);

	/**************************************************************************
	 * Standard Constructor
	 * 
	 * @param ctx context
	 * @param C_DocTypeCounter_ID id
	 * @param trxName transaction
	 */
	public MDocTypeCounter(Properties ctx, int C_DocTypeCounter_ID, String trxName)
	{
		super(ctx, C_DocTypeCounter_ID, trxName);
		if (C_DocTypeCounter_ID == 0)
		{
			setIsCreateCounter(true);	// Y
			setIsValid(false);
		}
	}	// MDocTypeCounter

	/**
	 * Load Constructor
	 * 
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MDocTypeCounter(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MDocTypeCounter

	/**
	 * Set C_DocType_ID
	 * 
	 * @param C_DocType_ID id
	 */
	@Override
	public void setC_DocType_ID(int C_DocType_ID)
	{
		super.setC_DocType_ID(C_DocType_ID);
		if (isValid())
			setIsValid(false);
	}	// setC_DocType_ID

	/**
	 * Set Counter C_DocType_ID
	 * 
	 * @param Counter_C_DocType_ID id
	 */
	@Override
	public void setCounter_C_DocType_ID(int Counter_C_DocType_ID)
	{
		super.setCounter_C_DocType_ID(Counter_C_DocType_ID);
		if (isValid())
			setIsValid(false);
	}	// setCounter_C_DocType_ID

	/**
	 * Get Doc Type
	 * 
	 * @return doc type or null if not existing
	 */
	public MDocType getDocType()
	{
		MDocType dt = null;
		if (getC_DocType_ID() > 0)
		{
			dt = MDocType.get(getCtx(), getC_DocType_ID());
			if (dt.get_ID() == 0)
				dt = null;
		}
		return dt;
	}	// getDocType

	/**
	 * Get Counter Doc Type
	 * 
	 * @return counter doc type or null if not existing
	 */
	public MDocType getCounterDocType()
	{
		MDocType dt = null;
		if (getCounter_C_DocType_ID() > 0)
		{
			dt = MDocType.get(getCtx(), getCounter_C_DocType_ID());
			if (dt.get_ID() == 0)
				dt = null;
		}
		return dt;
	}	// getCounterDocType

	/**************************************************************************
	 * Validate Document Type compatability
	 * 
	 * @return Error message or null if valid
	 */
	public String validate()
	{
		MDocType dt = getDocType();
		if (dt == null)
		{
			log.error("No DocType=" + getC_DocType_ID());
			setIsValid(false);
			return "No Document Type";
		}
		MDocType c_dt = getCounterDocType();
		if (c_dt == null)
		{
			log.error("No Counter DocType=" + getCounter_C_DocType_ID());
			setIsValid(false);
			return "No Counter Document Type";
		}
		//
		String dtBT = dt.getDocBaseType();
		String c_dtBT = c_dt.getDocBaseType();
		log.debug(dtBT + " -> " + c_dtBT);

		boolean valid = true;
		final String docBaseTypeCounter = Services.get(IDocTypeDAO.class).retrieveDocBaseTypeCounter(getCtx(), dtBT);

		if (c_dtBT == null)
		{
			valid = false;
		}
		else if (docBaseTypeCounter == null)
		{
			valid = false;
		}

		else if (!c_dtBT.equals(docBaseTypeCounter))
		{
			valid = false;
		}

		setIsValid(valid);

		if (!valid)
		{
			log.warn("NOT - " + dtBT + " -> " + c_dtBT);
			return "Not valid";
		}

		// Counter should have document numbering
		if (!c_dt.isDocNoControlled())
			return "Counter Document Type should be automatically Document Number controlled";
		return null;
	}	// validate

	/**
	 * String Representation
	 * 
	 * @return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("MDocTypeCounter[");
		sb.append(get_ID()).append(",").append(getName())
				.append(",C_DocType_ID=").append(getC_DocType_ID())
				.append(",Counter=").append(getCounter_C_DocType_ID())
				.append(",DocAction=").append(getDocAction())
				.append("]");
		return sb.toString();
	}	// toString

	/**
	 * Before Save
	 * 
	 * @param newRecord new
	 * @return true
	 */
	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if (getAD_Org_ID() != 0)
			setAD_Org_ID(0);

		if (!newRecord
				&& (is_ValueChanged("C_DocType_ID") || is_ValueChanged("Counter_C_DocType_ID")))
			setIsValid(false);

		// try to validate
		if (!isValid())
			validate();
		return true;
	}	// beforeSave

}	// MDocTypeCounter

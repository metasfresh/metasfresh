/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 *	Asset Registration Attribute Value
 *	
 *  @author Jorg Janke
 *  @version $Id: MRegistrationValue.java,v 1.2 2006/07/30 00:51:03 jjanke Exp $
 */
public class MRegistrationValue extends X_A_RegistrationValue
	implements Comparable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3380153548447464552L;

	/**
	 * 	Persistency Constructor
	 *	@param ctx context
	 *	@param ignored ignored
	 */
	public MRegistrationValue (Properties ctx, int ignored, String trxName)
	{
		super(ctx, 0, trxName);
		if (ignored != 0)
			throw new IllegalArgumentException("Multi-Key");
	}	//	MRegistrationValue
	
	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 */
	public MRegistrationValue(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MRegistrationValue
	
	/**
	 * 	Parent Constructor
	 *	@param registration parent
	 *	@param A_RegistrationAttribute_ID attribute
	 *	@param Name value
	 */
	public MRegistrationValue (MRegistration registration, 
		int A_RegistrationAttribute_ID, String Name)
	{
		super(registration.getCtx(), 0, registration.get_TrxName());
		setClientOrg (registration);
		setA_Registration_ID (registration.getA_Registration_ID());
		//
		setA_RegistrationAttribute_ID (A_RegistrationAttribute_ID);
		setName (Name);
	}	//	MRegistrationValue

	/**	Cached Attribute Name				*/
	private String		m_registrationAttribute = null;
	/**	Cached Attribute Description		*/
	private String		m_registrationAttributeDescription = null;
	/**	Cached Attribute Sequence	*/
	private int			m_seqNo = -1;

	/**
	 * 	Get Registration Attribute
	 *	@return name of registration attribute
	 */
	public String getRegistrationAttribute()
	{
		if (m_registrationAttribute == null)
		{
			int A_RegistrationAttribute_ID = getA_RegistrationAttribute_ID();
			MRegistrationAttribute att = MRegistrationAttribute.get (getCtx(), A_RegistrationAttribute_ID, get_TrxName());
			m_registrationAttribute = att.getName();
			m_registrationAttributeDescription = att.getDescription();
			m_seqNo = att.getSeqNo();
		}
		return m_registrationAttribute; 
	}	//	getRegistrationAttribute

	/**
	 * 	Get Registration Attribute Description
	 *	@return Description of registration attribute 
	 */
	public String getRegistrationAttributeDescription()
	{
		if (m_registrationAttributeDescription == null)
			getRegistrationAttribute();
		return m_registrationAttributeDescription; 
	}	//	getRegistrationAttributeDescription

	/**
	 * 	Get Attribute SeqNo
	 *	@return seq no
	 */
	public int getSeqNo()
	{
		if (m_seqNo == -1)
			getRegistrationAttribute();
		return m_seqNo;
	}	//	getSeqNo

	/**
	 * 	Compare To
     *	@param   o the Object to be compared.
     *	@return  a negative integer, zero, or a positive integer as this object
     *		is less than, equal to, or greater than the specified object.
	 */
	public int compareTo (Object o)
	{
		if (o == null)
			return 0;
		MRegistrationValue oo = (MRegistrationValue)o;
		int compare = getSeqNo() - oo.getSeqNo();
		return compare;
	}	//	compareTo

	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(getSeqNo()).append(": ")
			.append(getRegistrationAttribute()).append("=").append(getName());
		return sb.toString();
	}	//	toString
	
}	//	MRegistrationValue

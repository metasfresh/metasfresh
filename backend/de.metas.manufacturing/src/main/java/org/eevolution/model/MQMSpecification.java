/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
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
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *****************************************************************************/
package org.eevolution.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import org.compiere.util.DB;

/**
 *	Forcast Line Model
 *
 *  @author Victor Perez www.e-evolution.com
 *  @version $Id: MQMSpecification.java,v 1.11 2005/05/17 05:29:52 vpj-cd vpj-cd $
 */
public class MQMSpecification extends  X_QM_Specification
{
	/**
	 *
	 */
	private static final long serialVersionUID = 3672559433094289125L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param M_ForecastLine_ID id
	 */
	public MQMSpecification (Properties ctx, int QM_Specification_ID, String trxName)
	{
		super (ctx, QM_Specification_ID, trxName);
		if (QM_Specification_ID == 0)
		{
		}

	}	//	MQMSpecification

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 */
	public MQMSpecification (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MQMSpecification

	/** Lines						*/
	private MQMSpecificationLine[]		m_lines = null;

	/**
	 * 	Get Lines
	 *	@return array of lines
	 */
	public MQMSpecificationLine[] getLines(String where)
	{
		if (m_lines != null)
			return m_lines;

		ArrayList<MQMSpecificationLine> list = new ArrayList<>();
		String sql = "SELECT * FROM QM_SpecificationLine WHERE QM_SpecificationLine_ID=? AND "+ where +" ORDER BY Line";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt (1, getQM_Specification_ID());
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
				list.add (new MQMSpecificationLine(getCtx(), rs, get_TrxName()));
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.error("getLines", e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		m_lines = new MQMSpecificationLine[list.size ()];
		list.toArray (m_lines);
		return m_lines;
	}	//	getLines

	public boolean isValid(int M_AttributeSetInstance_ID)
	{
		// TODO: check/uncomment/reimplement when it will be needed
//		//MAttributeSet mas = MAttributeSet.get(getCtx(), getM_AttributeSet_ID());
//
////		Save Instance Attributes
//
//		MAttributeSetInstance asi = new MAttributeSetInstance(getCtx(),M_AttributeSetInstance_ID, get_TrxName());
//		MAttributeSet 		  as = MAttributeSet.get(AttributeSetId.ofRepoId(asi.getM_AttributeSet_ID()));
//		for (final I_M_Attribute attribute : as.getMAttributes(false))
//		{
//
//			//MAttribute attribute = new MAttribute(getCtx(),0,null);
//			MAttributeInstance instance = attribute.getMAttributeInstance(M_AttributeSetInstance_ID);
//			MQMSpecificationLine[] lines = getLines(" M_Attribute_ID="+attribute.getM_Attribute_ID());
//			for (int s = 0; s < lines.length; i++)
//			{
//				MQMSpecificationLine line = lines[s];
//				if (MAttribute.ATTRIBUTEVALUETYPE_Number.equals(attribute.getAttributeValueType()))
//				{
//				BigDecimal objValue = instance.getValueNumber();
//				if(!line.evaluate(objValue,instance.getValue()));
//				return false;
//				}
//				else
//				{
//				String	objValue = instance.getValue();
//				if(!line.evaluate(objValue,instance.getValue()))
//					return false;
//				}
//				//if(line.evaluate(mas.getValueNumber())
//			}
//		}	//	for all attributes
		return true;
	}

}	//	MQMSpecification

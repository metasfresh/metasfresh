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
package org.compiere.apps;


/**
 *  Application Layout Constraint to indicate grid position (immutable)
 *
 *  @author Jorg Janke
 *  @version  $Id: ALayoutConstraint.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 */
public class ALayoutConstraint implements Comparable
{
	/**
	 *  Layout Constraint to indicate grid position
	 *  @param row row 0..x
	 *  @param col column 0..x
	 */
	public ALayoutConstraint(int row, int col)
	{
		m_row = row;
		m_col = col;
	}  //  ALayoutConstraint

	/**
	 *  Create Next in Row
	 *  @return ALayoutConstraint for additional column in same row
	 */
	public ALayoutConstraint createNext()
	{
		return new ALayoutConstraint(m_row, m_col+1);
	}   //  createNext

	private int m_row;
	private int m_col;

	/**
	 *  Get Row
	 *  @return roe no
	 */
	public int getRow()
	{
		return m_row;
	}   //  getRow

	/**
	 *  Get Column
	 *  @return col no
	 */
	public int getCol()
	{
		return m_col;
	}   //  getCol

	/**
	 * Compares this object with the specified object for order.  Returns a
	 * negative integer, zero, or a positive integer as this object is less
	 * than, equal to, or greater than the specified object.<p>
	 *
	 * @param   o the Object to be compared.
	 * @return  a negative integer if this object is less than the specified object,
	 *          zero if equal,
	 *          or a positive integer if this object is greater than the specified object.
	 */
	public int compareTo(Object o)
	{
		ALayoutConstraint comp = null;
		if (o instanceof ALayoutConstraint)
			comp = (ALayoutConstraint)o;
		if (comp == null)
			return +111;

		//  Row compare
		int rowComp = m_row - comp.getRow();
		if (rowComp != 0)
			return rowComp;
		//  Column compare
		return m_col - comp.getCol();
	}   //  compareTo

	/**
	 *  Is Object Equal
	 *  @param o
	 *  @return true if equal
	 */
	public boolean equals(Object o)
	{
		if (o instanceof ALayoutConstraint)
			return compareTo(o) == 0;
		return false;
	}   //  equal

	/**
	 *  To String
	 *  @return info
	 */
	public String toString()
	{
		return "ALayoutConstraint [Row=" + m_row + ", Col=" + m_col + "]";
	}   //  toString

}   //  ALayoutConstraint

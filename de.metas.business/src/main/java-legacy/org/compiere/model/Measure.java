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

import java.math.BigDecimal;

/**
 *  Performance Key Indicator Interface
 *
 *  @author 	Jorg Janke
 *  @version 	$Id: Measure.java,v 1.2 2006/07/30 00:51:03 jjanke Exp $
 */
public interface Measure
{
	/**
	 * 	Create new Measures for the Performance Goal?
	 * 	@param measure measure
	 * 	@return true if measure not created via MeasureCalc (sql)
	 */
	public boolean isCreateMeasures (MMeasure measure);

	/**
	 * 	Create new Measures for the Performance Goal.
	 * 	Called only if isCreateMeasures is true
	 * 	@param measure measure
	 * 	@return number created
	 */
	public int createMeasures (MMeasure measure);

	/**
	 * 	Calculate Parformance Goal Actual
	 * 	@param measure measure
	 * 	@return new Actual Measure
	 */
	public BigDecimal calculateMeasure (MMeasure measure);

}   //  Measure

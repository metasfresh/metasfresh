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
package org.compiere.util;

import java.lang.management.MemoryUsage;
import java.text.DecimalFormat;

/**
 * 	Memory Usage Info
 *	
 *  @author Jorg Janke
 *  @version $Id: CMemoryUsage.java,v 1.2 2006/07/30 00:54:35 jjanke Exp $
 */
public class CMemoryUsage extends MemoryUsage
{
	/**
	 * 	Detail Constructor
	 *	@param init init
	 *	@param used used
	 *	@param committed committed
	 *	@param max max
	 */
	public CMemoryUsage (long init, long used, long committed, long max)
	{
		super (init, used, committed, max);
	}	//	CMemoryUsage
	
	/**
	 * 	Parent Constructor
	 *	@param usage usage
	 */
	public CMemoryUsage (MemoryUsage usage)
	{
		super (usage.getInit(), usage.getUsed(), usage.getCommitted(), usage.getMax());
	}	//	CMemoryUsage

	/**	Format						*/
	private static DecimalFormat s_format =	DisplayType.getNumberFormat(DisplayType.Integer);

	
	/**
	 * 	Get Free (Committed-Used) Memory
	 *	@return memory
	 */
	public long getFree()
	{
		return getCommitted() - getUsed();
	}	//	getFree
	
	/**
	 * 	Get Free (Committed-Used) Memory Percent
	 *	@return memory
	 */
	public int getFreePercent()
	{
		long base = getCommitted();
		long no = getFree() * 100;
		if (no == 0)
			return 0;
		long percent = no/base;
		return (int)percent;
	}	//	getFree

	/**
	 * 	Get Committed (Max-Committed) Memory Percent
	 *	@return memory
	 */
	public int getCommittedPercent()
	{
		long base = getMax();
		long no = getCommitted() * 100;
		if (no == 0)
			return 0;
		long percent = no/base;
		return (int)percent;
	}	//	getCommittedPercent

	/**
	 * 	Format k/M
	 *	@param info
	 *	@return string info
	 */
	private String format (long info)
	{
		long infoK = info / 1024;
		if (infoK == 0)
			return String.valueOf(info);
		long infoM = infoK / 1024;
		if (infoM == 0)
			return s_format.format(info);
		return s_format.format(infoK) + "k";		
	}	//	format
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ();
		sb.append ("Init=").append(format(getInit()))
			.append (", Used=").append(format(getUsed()))
			.append (", Free=").append(format(getFree()))
				.append(" ").append(getFreePercent())
			.append ("%, Committed=").append(format(getCommitted()))
				.append(" ").append(getCommittedPercent())
			.append ("%, Max=").append (format(getMax()));
		return sb.toString ();
	}	//	toString
	
}	//	CMemoryUsage

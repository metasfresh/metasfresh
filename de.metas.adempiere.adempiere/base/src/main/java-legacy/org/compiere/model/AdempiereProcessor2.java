/******************************************************************************
 * Copyright (C) 2010 Low Heng Sin                                            *
 * Copyright (C) 2010 Idalica Corporation                                     *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.compiere.model;

/**
 * 
 * @author hengsin
 *
 */
public interface AdempiereProcessor2 {
	/**
	 * @return true if previous DateNextRun should be use as base to calculate the new 
	 * DateNextRun value. False to follow the legacy behaviour where current
	 * server time is use as the base to the new DateNextRun value.
	 */
	public boolean isIgnoreProcessingTime();
}

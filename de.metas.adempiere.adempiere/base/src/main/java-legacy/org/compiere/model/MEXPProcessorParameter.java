/**********************************************************************
 * This file is part of Adempiere ERP Bazaar                          * 
 * http://www.adempiere.org                                           * 
 *                                                                    * 
 * Copyright (C) Trifon Trifonov.                                     * 
 * Copyright (C) Contributors                                         * 
 *                                                                    * 
 * This program is free software; you can redistribute it and/or      * 
 * modify it under the terms of the GNU General Public License        * 
 * as published by the Free Software Foundation; either version 2     * 
 * of the License, or (at your option) any later version.             * 
 *                                                                    * 
 * This program is distributed in the hope that it will be useful,    * 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of     * 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the       * 
 * GNU General Public License for more details.                       * 
 *                                                                    * 
 * You should have received a copy of the GNU General Public License  * 
 * along with this program; if not, write to the Free Software        * 
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,         * 
 * MA 02110-1301, USA.                                                * 
 *                                                                    * 
 * Contributors:                                                      * 
 *  - Trifon Trifonov (trifonnt@users.sourceforge.net)                *
 *                                                                    *
 * Sponsors:                                                          *
 *  - e-Evolution (http://www.e-evolution.com/)                       *
 *********************************************************************/

package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.CLogger;

/**
 * @author Trifon N. Trifonov
 */
public class MEXPProcessorParameter extends X_EXP_ProcessorParameter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2633307385854436092L;
	/**	Static Logger	*/
	private static CLogger	s_log	= CLogger.getCLogger (MEXPProcessorParameter.class);
	
	
	public MEXPProcessorParameter(Properties ctx, int EXP_ProcessorParameter_ID, String trxName) {
		super(ctx, EXP_ProcessorParameter_ID, trxName);
	}
	
	public MEXPProcessorParameter(Properties ctx, ResultSet rs, String trxName) {
		super (ctx, rs, trxName);
	}
	
}

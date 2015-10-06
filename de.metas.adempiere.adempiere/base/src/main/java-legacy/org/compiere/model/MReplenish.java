/**********************************************************************
 * This file is part of Adempiere ERP Bazaar                          *
 * http://www.adempiere.org                                           *
 *                                                                    *
 * Copyright (C) Daniel Tamm                                          *
 * Copyright (C) Contributors                                         *
 *                                                                    *
 * This program is free software, you can redistribute it and/or      *
 * modify it under the terms of the GNU General Public License        *
 * as published by the Free Software Foundation, either version 2     *
 * of the License, or (at your option) any later version.             *
 *                                                                    *
 * This program is distributed in the hope that it will be useful,    *
 * but WITHOUT ANY WARRANTY, without even the implied warranty of     *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the       *
 * GNU General Public License for more details.                       *
 *                                                                    *
 * You should have received a copy of the GNU General Public License  *
 * along with this program, if not, write to the Free Software        *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,         *
 * MA 02110-1301, USA.                                                *
 *                                                                    *
 * Contributors:                                                      *
 * - Daniel Tamm     (usrdno@users.sourceforge.net)                   *
 * - Victor Perez    (victor.perez@e-evolution.com)					  *
 *                                                                    *
 * Sponsors:                                                          *
 * - Company (http://www.notima.se)                                   *
 * - Company (http://www.cyberphoto.se)                               *
 *********************************************************************/

package org.compiere.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.util.Env;

/**
 *
 * @author Daniel Tamm
 */
public class MReplenish extends X_M_Replenish {

    /**
	 * 
	 */
	private static final long serialVersionUID = -76806183034687720L;

	/**
     * Standard constructor
     * 
     * @param ctx
     * @param M_Replenish_ID
     * @param trxName
     */
    public MReplenish(Properties ctx, int M_Replenish_ID, String trxName) {
        super(ctx, M_Replenish_ID, trxName);
    }
    
    /**
     * Standard constructor to create a PO from a resultset.
     * 
     * @param ctx
     * @param rs
     * @param trxName
     */
    public MReplenish(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
    
    /**
     * 
     * @param ctx
     * @param M_ProductID
     * @param trxName
     * @return  A list of active replenish lines for given product.
     */
    public static List<MReplenish> getForProduct(Properties ctx, int M_ProductID, String trxName) {
    	String whereClause= "M_Product_ID=? AND AD_Client_ID=? AND AD_Org_ID IN (0, ?) ";         
    	return new Query(ctx, MReplenish.Table_Name, whereClause, trxName)
    	.setParameters(new Object[]{M_ProductID, Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx)})
    	.setOrderBy("AD_Org_ID")
    	.setOnlyActiveRecords(true)
    	.list();
    }
}

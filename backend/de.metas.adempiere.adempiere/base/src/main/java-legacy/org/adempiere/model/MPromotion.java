/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin                                            *
 * Copyright (C) 2009 Idalica Corporation                                     *
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
package org.adempiere.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.X_M_Promotion;

/**
 *
 * @author hengsin
 *
 */
public class MPromotion extends X_M_Promotion {

	private static final long serialVersionUID = 5437777366112957770L;

	public MPromotion(Properties ctx, int M_Promotion_ID, String trxName) {
		super(ctx, M_Promotion_ID, trxName);
	}

	public MPromotion(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

}

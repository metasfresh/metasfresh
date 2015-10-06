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
package org.adempiere.webui.util;

import java.util.Properties;

import org.compiere.model.MPreference;

/**
 *
 * @author hengsin
 *
 */
public class MUserPreference extends MPreference {

	private static final long serialVersionUID = -3424015890197944847L;

	public MUserPreference(Properties ctx, int AD_Preference_ID, String trxName) {
		super(ctx, AD_Preference_ID, trxName);
		setClientOrg(0, 0);
	}
}

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

import org.adempiere.ad.persistence.EntityTypesCache;
import org.adempiere.exceptions.AdempiereException;

import de.metas.cache.CacheMgt;

/**
 * Entity Type Model
 */
@Deprecated
public class MEntityType extends X_AD_EntityType
{
	/**
	 *
	 */
	private static final long serialVersionUID = -2183955192373166750L;

	public MEntityType(final Properties ctx, final int AD_EntityType_ID, final String trxName)
	{
		super(ctx, AD_EntityType_ID, trxName);
	}

	public MEntityType(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		if (!newRecord)
		{
			if (is_ValueChanged(COLUMNNAME_EntityType))
			{
				throw new AdempiereException("You cannot modify EntityType");
			}
		}

		CacheMgt.get().reset(I_AD_EntityType.Table_Name);

		return true;
	}

	@Override
	protected boolean beforeDelete()
	{
		if (EntityTypesCache.instance.isSystemMaintained(getEntityType()))	// all pre-defined
		{
			throw new AdempiereException("You cannot delete a System maintained entity");
		}
		return true;
	}
}

/**
 * 
 */
package de.metas.letters.model;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.exceptions.AdempiereException;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author teo_sarca
 *
 */
public class MADBoilerPlateRef extends X_AD_BoilerPlate_Ref
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 472075266306434709L;

	/**
	 * @param ctx
	 * @param AD_BoilerPlate_Ref_ID
	 * @param trxName
	 */
	public MADBoilerPlateRef(Properties ctx, int AD_BoilerPlate_Ref_ID, String trxName)
	{
		super(ctx, AD_BoilerPlate_Ref_ID, trxName);
	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MADBoilerPlateRef(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	public MADBoilerPlateRef(MADBoilerPlate parent, String refName)
	{
		this(parent.getCtx(), 0, parent.get_TrxName());
		setAD_BoilerPlate_ID(parent.getAD_BoilerPlate_ID());
		
		final int Ref_BoilerPlate_ID = MADBoilerPlate.getIdByName(getCtx(), refName, get_TrxName());
		if (Ref_BoilerPlate_ID <= 0)
		{
			throw new AdempiereException("@NotFound@ @AD_BoilerPlate_ID@ (@Name@:"+refName+")");
		}
		setRef_BoilerPlate_ID(Ref_BoilerPlate_ID);
	}

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if (getAD_BoilerPlate_ID() == getRef_BoilerPlate_ID())
		{
			throw new AdempiereException("@de.metas.letters.AD_BoilerPlate.SelfReferencingError@");
		}
		return true;
	}
}

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_DocType;
import org.compiere.model.MDocType;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

/**
 * Boiler Plate Variable Evaluation Timing
 *
 * @author teo.sarca@gmail.com
 */
public class MADBoilerPlateVarEval extends X_AD_BoilerPlate_Var_Eval
{
	private static final IQueryBL queryBL = Services.get(IQueryBL.class);
	private static final long serialVersionUID = 6355649371361977481L;

	private static final CCache<Integer, ImmutableList<MADBoilerPlateVarEval>> s_cache = new CCache<>(Table_Name + "#DocTiming", 2);

	public static @NotNull List<MADBoilerPlateVarEval> getAll()
	{
		//noinspection DataFlowIssue
		return s_cache.getOrLoad(0, MADBoilerPlateVarEval::retrieveAll);
	}

	private static ImmutableList<MADBoilerPlateVarEval> retrieveAll()
	{
		return queryBL.createQueryBuilder(I_AD_BoilerPlate_Var_Eval.Table_Name)
				.orderBy(I_AD_BoilerPlate_Var_Eval.COLUMNNAME_AD_BoilerPlate_Var_ID)
				.orderBy(I_AD_BoilerPlate_Var_Eval.COLUMNNAME_C_DocType_ID)
				.create()
				.listImmutable(MADBoilerPlateVarEval.class);
	}

	@SuppressWarnings("unused")
	public MADBoilerPlateVarEval(Properties ctx, int AD_BoilerPlate_Var_Eval_ID, String trxName)
	{
		super(ctx, AD_BoilerPlate_Var_Eval_ID, trxName);
	}

	@SuppressWarnings("unused")
	public MADBoilerPlateVarEval(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	public I_AD_BoilerPlate_Var getAD_BoilerPlate_Var() throws RuntimeException
	{
		if (get_TrxName() == null)
			return MADBoilerPlateVar.get(getCtx(), getAD_BoilerPlate_Var_ID());
		else
			return super.getAD_BoilerPlate_Var();
	}

	@Override
	public I_C_DocType getC_DocType() throws RuntimeException
	{
		if (get_TrxName() == null)
			return MDocType.get(getCtx(), getC_DocType_ID());
		else
			return super.getC_DocType();
	}
}

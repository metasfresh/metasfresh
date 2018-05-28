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


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.compiere.model.Query;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.letters.model.MADBoilerPlate.BoilerPlateContext;
import de.metas.letters.model.MADBoilerPlate.SourceDocument;

/**
 * Boiler Plate Variable
 * @author teo.sarca@gmail.com
 */
public class MADBoilerPlateVar extends X_AD_BoilerPlate_Var
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7312020496358784755L;
	
	private static CCache<Integer, MADBoilerPlateVar> s_cache = new CCache<>(Table_Name, 20, 0);
	private static CCache<Integer, Map<String, MADBoilerPlateVar>> s_cacheByValue = new CCache<>(Table_Name+"_Client", 2);

	/**
	 * 
	 * @param ctx
	 * @return Value -> MADBoilerPlateVar map
	 */
	public static Map<String, MADBoilerPlateVar> getAll(Properties ctx)
	{
		final int AD_Client_ID = Env.getAD_Client_ID(ctx);
		Map<String, MADBoilerPlateVar> map = s_cacheByValue.get(AD_Client_ID);
		if (map == null)
		{
			final String whereClause = COLUMNNAME_AD_Client_ID+" IN (0,?)";
			final List<MADBoilerPlateVar> list = new Query (ctx, Table_Name, whereClause, null)
			.setParameters(new Object[]{AD_Client_ID})
			.setOrderBy(COLUMNNAME_Value)
			.list(MADBoilerPlateVar.class);
			
			map = new HashMap<>(list.size());
			for (MADBoilerPlateVar var : list)
			{
				map.put(var.getValue().toUpperCase(), var);
				s_cache.put(var.getAD_BoilerPlate_Var_ID(), var);
			}
		}
		return Collections.unmodifiableMap(map);
	}
	
	public static MADBoilerPlateVar get(Properties ctx, String value)
	{
		return getAll(ctx).get(value.toUpperCase());
	}
	
	public static MADBoilerPlateVar get(Properties ctx, int AD_BoilerPlate_Var_ID)
	{
		MADBoilerPlateVar var = s_cache.get(AD_BoilerPlate_Var_ID);
		if(var == null)
		{
			var = new MADBoilerPlateVar(ctx, AD_BoilerPlate_Var_ID, null);
			if (var.getAD_BoilerPlate_Var_ID() > 0)
				s_cache.put(var.getAD_BoilerPlate_Var_ID(), var);
			else
				var = null;
		}
		return var;
	}
	
	public static boolean exists(Properties ctx, String value)
	{
		return get(ctx, value) != null;
	}
	
	public MADBoilerPlateVar(Properties ctx, int AD_BoilerPlate_Var_ID, String trxName)
	{
		super(ctx, AD_BoilerPlate_Var_ID, trxName);
	}

	public MADBoilerPlateVar(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}
	
	/**
	 * Evaluate variable
	 * @param attributes context attributes
	 * @return String value of evaluated variable
	 */
	public String evaluate(final BoilerPlateContext context)
	{
		final String type = getType();
		if (TYPE_SQL.equals(type))
		{
			return evaluateSQL(context);
		}
		else if (TYPE_RuleEngine.equals(type))
		{
			return evaluateRuleEngine(context);
		}
		else
		{
			throw new AdempiereException("@NotSupported@ @Type@ - "+type);
		}
	}
	
	private String evaluateSQL(final BoilerPlateContext context)
	{
		final Properties ctx = Env.getCtx();
		final String code = getCode();
		if (Check.isEmpty(code, true))
			return "";

		final int windowNo = context.getWindowNo();
		final SourceDocument sourceDocument = context.getSourceDocumentOrNull();
		final List<Object> params = new ArrayList<>();
		final StringBuffer sql = new StringBuffer();

		String inStr = code;
		int i = inStr.indexOf('@');
		while (i != -1)
		{
			sql.append(inStr.substring(0, i));			// up to @
			inStr = inStr.substring(i+1, inStr.length());	// from first @

			int j = inStr.indexOf('@');						// next @
			if (j < 0)
			{
				log.error("No second tag: " + inStr);
				return "";						//	no second tag
			}

			String token = inStr.substring(0, j);

			Object tokenValue = null;
			if (sourceDocument != null)
			{
				if (sourceDocument.hasFieldValue(token))
				{
					tokenValue = sourceDocument.getFieldValue(token);
					if (tokenValue == null)
						tokenValue = "";
				}
			}
			if (tokenValue == null || tokenValue.toString().length() == 0)
			{
				tokenValue = context.get(token);
			}
			if (tokenValue == null || tokenValue.toString().length() == 0)
			{
				// get context
				if(token.endsWith("_ID"))
				{
					// metas-ts; 01950: the token ends with _ID, so we assume an int value 
					tokenValue = Env.getContextAsInt(ctx, windowNo, token, false);	
				}
				else
				{
					tokenValue = Env.getContext(ctx, windowNo, token, false);
				}
			}
			if ((tokenValue == null || tokenValue.toString().length() == 0) && (token.startsWith("#") || token.startsWith("$")) )
			{
				// get global context
				if (token.endsWith("_ID")) 
				{
					// metas-ts; 01950: the token ends with _ID, so we assume an int value
					tokenValue = Env.getContextAsInt(ctx, token);
				} 
				else 
				{
					tokenValue = Env.getContext(ctx, token);
				}
			}
			if (tokenValue == null || tokenValue.toString().length() == 0)
			{
				log.warn("No context value for: " + token);
				return ""; //	token not found
			}
			
			sql.append("?");
			params.add(tokenValue);
			inStr = inStr.substring(j+1, inStr.length());	// from second @
			i = inStr.indexOf('@');
		}
		sql.append(inStr);						// add the rest of the string

		return DB.getSQLValueStringEx(get_TrxName(), sql.toString(), params);
	}
	
	private String evaluateRuleEngine(final BoilerPlateContext context)
	{
		throw new IllegalStateException("Method not implemented"); // TODO
	}
	
	public MADBoilerPlateVarEval[] getEvaluationTimings()
	{
		//new Query(getCtx(), MADBoilerPlateVarEval.Table_Name, whereClause, trxName)
		return new MADBoilerPlateVarEval[0];// TODO
	}

	public String getTagString()
	{
		String value = getValue();
		if (Check.isEmpty(value, true))
			return "";
		return MADBoilerPlate.TagBegin+value.trim()+MADBoilerPlate.TagEnd;
	}
}

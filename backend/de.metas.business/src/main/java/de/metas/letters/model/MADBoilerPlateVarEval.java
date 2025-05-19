/**
 *
 */
package de.metas.letters.model;

import java.io.Serial;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.model.I_C_DocType;
import org.compiere.model.MDocType;
import org.compiere.model.Query;
import org.compiere.util.Env;

import de.metas.cache.CCache;

/**
 * Boiler Plate Variable Evaluation Timing
 *
 * @author teo.sarca@gmail.com
 */
public class MADBoilerPlateVarEval extends X_AD_BoilerPlate_Var_Eval
{
	/**
	 *
	 */
	@Serial
	private static final long serialVersionUID = 6355649371361977481L;

	private static CCache<Integer, MADBoilerPlateVarEval[]> s_cache = new CCache<>(Table_Name + "_Client", 2);

	public static MADBoilerPlateVarEval[] getAll(Properties ctx)
	{
		final int AD_Client_ID = Env.getAD_Client_ID(ctx);
		MADBoilerPlateVarEval[] arr = s_cache.get(AD_Client_ID);
		if (arr == null)
		{
			final String whereClause = I_AD_BoilerPlate_Var_Eval.COLUMNNAME_AD_Client_ID + " IN (0,?)";
			final List<MADBoilerPlateVarEval> list = new Query(ctx, Table_Name, whereClause, null)
					.setParameters(new Object[] { AD_Client_ID })
					.setOrderBy(COLUMNNAME_AD_BoilerPlate_Var_ID + "," + COLUMNNAME_C_DocType_ID)
					.list(MADBoilerPlateVarEval.class);
			arr = list.toArray(new MADBoilerPlateVarEval[list.size()]);
			s_cache.put(AD_Client_ID, arr);
		}
		return arr;
	}

	public MADBoilerPlateVarEval(Properties ctx, int AD_BoilerPlate_Var_Eval_ID, String trxName)
	{
		super(ctx, AD_BoilerPlate_Var_Eval_ID, trxName);
	}

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

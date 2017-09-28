package de.metas.contracts.subscription.impl;

import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.Query;
import org.compiere.util.DB;

import de.metas.contracts.flatrate.api.impl.AbstractSubscriptionDAO;
import de.metas.contracts.flatrate.interfaces.I_C_OLCand;
import de.metas.contracts.model.I_C_Contract_Term_Alloc;
import de.metas.contracts.model.I_C_Flatrate_Term;

public class SubscriptionDAO extends AbstractSubscriptionDAO
{
	public static final String SUBSCRIPTION_NO_SP_AT_DATE_1P = "Subscription_NoSPAtDate_1P";

	@Override
	public List<I_C_Flatrate_Term> retrieveTermsForOLCand(final I_C_OLCand olCand)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(olCand);
		final String trxName = InterfaceWrapperHelper.getTrxName(olCand);

		final String wc = I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID + " IN (\n" +
				"  select " + I_C_Contract_Term_Alloc.COLUMNNAME_C_Flatrate_Term_ID + "\n" +
				"  from " + I_C_Contract_Term_Alloc.Table_Name + " cta \n" +
				"  where \n" +
				"     cta." + I_C_Contract_Term_Alloc.COLUMNNAME_IsActive + "=" + DB.TO_STRING("Y") + " AND \n" +
				"     cta." + I_C_Contract_Term_Alloc.COLUMNNAME_AD_Client_ID + "=" + I_C_Flatrate_Term.Table_Name + "." + I_C_Flatrate_Term.COLUMNNAME_AD_Client_ID + " AND \n" +
				"     cta." + I_C_Contract_Term_Alloc.COLUMNNAME_C_OLCand_ID + "=? \n" +
				")";

		return new Query(ctx, I_C_Flatrate_Term.Table_Name, wc, trxName) //
				.setParameters(olCand.getC_OLCand_ID())
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setOrderBy(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID)
				.list(I_C_Flatrate_Term.class);

	}

	@Override
	public <T extends I_C_OLCand> List<T> retrieveOLCands(final I_C_Flatrate_Term term, final Class<T> clazz)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(term);
		final String trxName = InterfaceWrapperHelper.getTrxName(term);

		final String wc = I_C_OLCand.COLUMNNAME_C_OLCand_ID + " IN (\n" +
				"  select " + I_C_Contract_Term_Alloc.COLUMNNAME_C_OLCand_ID + "\n" +
				"  from " + I_C_Contract_Term_Alloc.Table_Name + " cta \n" +
				"  where \n" +
				"     cta." + I_C_Contract_Term_Alloc.COLUMNNAME_IsActive + "=" + DB.TO_STRING("Y") + " AND \n" +
				"     cta." + I_C_Contract_Term_Alloc.COLUMNNAME_AD_Client_ID + "=" + I_C_OLCand.Table_Name + "." + I_C_OLCand.COLUMNNAME_AD_Client_ID + " AND \n" +
				"     cta." + I_C_Contract_Term_Alloc.COLUMNNAME_C_Flatrate_Term_ID + "=? \n" +
				")";

		return new Query(ctx, I_C_OLCand.Table_Name, wc, trxName) //
				.setParameters(term.getC_Flatrate_Term_ID())
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setOrderBy(I_C_OLCand.COLUMNNAME_C_OLCand_ID)
				.list(clazz);
	}
}

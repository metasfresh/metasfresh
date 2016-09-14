package de.metas.commission.modelvalidator;

/*
 * #%L
 * de.metas.commission.base
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


import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

import de.metas.commission.model.I_C_AdvCommissionFact;
import de.metas.commission.model.I_C_AdvCommissionInstance;
import de.metas.commission.model.MCAdvCommissionFact;
import de.metas.commission.model.X_C_AdvCommissionFact;

/**
 * 
 * @author ts
 * 
 */
public class ProhibitInconsistentFacts implements ModelValidator
{

	private int ad_Client_ID = -1;

	@Override
	public int getAD_Client_ID()
	{
		return ad_Client_ID;
	}

	@Override
	public final void initialize(final ModelValidationEngine engine, final MClient client)
	{
		if (client != null)
		{
			ad_Client_ID = client.getAD_Client_ID();
		}
		engine.addModelChange(I_C_AdvCommissionFact.Table_Name, this);
	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		return null;
	}

	@Override
	public String modelChange(final PO po, final int type)
	{
		if (ModelValidator.TYPE_BEFORE_NEW != type)
		{
			// nothing to do
			return null;
		}
		final I_C_AdvCommissionFact fact = InterfaceWrapperHelper.create(po, I_C_AdvCommissionFact.class);

		checkNotUpdatable(po);

		checkPointsSumChange(fact);

		checkCommissionAmtBase((MCAdvCommissionFact)po);

		return null;
	}

	/**
	 * Makes sure that certain columns can't be updated. This is important because we are relying on the assumption that the values we check for new facts are not altered later on.
	 * 
	 * @param po
	 */
	protected void checkNotUpdatable(final PO po)
	{
		final POInfo info = POInfo.getPOInfo(po.getCtx(), po.get_Table_ID(), po.get_TrxName());

		Check.assume(!info.isColumnUpdateable(info.getColumnIndex(I_C_AdvCommissionFact.COLUMNNAME_CommissionPoints)),
				"Column " + I_C_AdvCommissionFact.COLUMNNAME_CommissionPoints + " is not updatable");
		Check.assume(!info.isColumnUpdateable(info.getColumnIndex(I_C_AdvCommissionFact.COLUMNNAME_CommissionAmtBase)),
				"Column " + I_C_AdvCommissionFact.COLUMNNAME_CommissionAmtBase + " is not updatable");
	}

	private void checkCommissionAmtBase(final MCAdvCommissionFact factPO)
	{
		final Object commissionAmtBase =
				factPO.get_Value(I_C_AdvCommissionFact.COLUMNNAME_CommissionAmtBase);

		final boolean assumptionOk;
		if (commissionAmtBase != null)
		{
			assumptionOk = true;
		}
		else
		{
			if (X_C_AdvCommissionFact.FACTTYPE_VorgangSchliessen.equals(factPO.getFactType()))
			{
				assumptionOk = true;
			}
			else if (!X_C_AdvCommissionFact.STATUS_ZuBerechnen.equals(factPO.getStatus()))
			{
				assumptionOk = true;
			}
			else
			{
				assumptionOk = false;
			}
		}
		Check.assume(
				assumptionOk,
				"If " + factPO + " has status = '" + X_C_AdvCommissionFact.STATUS_ZuBerechnen
						+ "' and FactType != '" + X_C_AdvCommissionFact.FACTTYPE_VorgangSchliessen
						+ "', then CommissionAmtBase must be != null; CommissionAmtBase=" + commissionAmtBase);
	}

	/**
	 * Method makes sure that the commission business logic can't deduct more from a given commission instance that what is there. See {@link ProhibitInconsistentFactsTests} for details.
	 * 
	 * 02219
	 * 
	 * @param fact
	 */
	private void checkPointsSumChange(final I_C_AdvCommissionFact fact)
	{
		if (!fact.isCounterEntry())
		{
			// we only need to check counter entries.
			// counter entries are the facts that are supposed to deduct something from a sum.
			// this means that the sum needs to be less if the counter entry enters into it.
			return;
		}

		if (!X_C_AdvCommissionFact.STATUS_Prognostiziert.equals(fact.getStatus())
				&& !X_C_AdvCommissionFact.STATUS_ZuBerechnen.equals(fact.getStatus()))
		{
			// we only check facts with status PR and CP, because this is where the trouble begins and also where I know
			// how to check ;-)
			return;
		}

		final I_C_AdvCommissionInstance instance = fact.getC_AdvCommissionInstance();

		if (X_C_AdvCommissionFact.STATUS_Prognostiziert.equals(fact.getStatus()))
		{
			checkPointsSumChange(fact, instance.getPoints_Predicted());
		}
		else if (X_C_AdvCommissionFact.STATUS_ZuBerechnen.equals(fact.getStatus()))
		{
			checkPointsSumChange(fact, instance.getPoints_ToCalculate());
		}
	}

	private void checkPointsSumChange(final I_C_AdvCommissionFact fact, final BigDecimal pointsSum)
	{
		// Note:
		// We are comparing absolute values, so basically we want to find out if 'fact' moves the sum closer to zero.
		final BigDecimal pointsSumAbsWithoutNewFact = pointsSum.abs();
		final BigDecimal pointsSumAbsWithNewFact = pointsSum.add(fact.getCommissionPoints()).abs();

		// Unless the fact has zero point, the absolute sum of points must be decreased
		Check.errorUnless(
				fact.getCommissionPoints().signum() == 0
						|| pointsSumAbsWithNewFact.compareTo(pointsSumAbsWithoutNewFact) < 0,
				"Counter-fact " + fact + " does not decrease the absolute amount of points sum"
						+ "; pointsSumAbsWithoutNewFact=" + pointsSumAbsWithoutNewFact
						+ "; pointsSumAbsWithNewFact=" + pointsSumAbsWithNewFact);
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{
		// nothing to do
		return null;
	}
}

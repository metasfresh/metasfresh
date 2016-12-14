package de.metas.flatrate.process;

import java.sql.Timestamp;
import java.util.Iterator;

import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;
import org.apache.commons.collections4.IteratorUtils;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.compiere.util.TrxRunnableAdapter;

import de.metas.flatrate.api.IFlatrateBL;
import de.metas.flatrate.model.I_C_Flatrate_Conditions;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.process.JavaProcess;

/*
 * #%L
 * de.metas.contracts
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

public abstract class C_Flatrate_Term_Create extends JavaProcess
{
	// services
	protected final transient IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

	private Timestamp startDate;
	private Timestamp endDate;
	private I_C_Flatrate_Conditions conditions;
	private I_AD_User userInCharge;
	private I_M_Product product;

	public void setStartDate(Timestamp startDate)
	{
		this.startDate = startDate;
	}

	public void setEndDate(Timestamp endDate)
	{
		this.endDate = endDate;
	}

	public void setUserInCharge(I_AD_User userInCharge)
	{
		this.userInCharge = userInCharge;
	}

	public void setProduct(I_M_Product product)
	{
		this.product = product;
	}

	public void setConditions(I_C_Flatrate_Conditions conditions)
	{
		this.conditions = conditions;
	}

	@Override
	public String doIt() throws Exception
	{

		createTermForBPartners();

		return "@Success@";
	}

	/**
	 * create terms for all the BPartners iterated from the subclass, each of them in its own transaction
	 */
	private void createTermForBPartners()
	{
		for (final I_C_BPartner partner : IteratorUtils.asIterable(getBPartners()))
		{
			// create each term in its own transaction
			trxManager.run(new TrxRunnableAdapter()
			{
				@Override
				public void run(final String localTrxName)
				{
					createTerm(partner);
				}

				@Override
				public boolean doCatch(Throwable ex)
				{
					addLog("@Error@ @C_BPartner_ID@:" + partner.getValue() + "_" + partner.getName() + ": " + ex.getLocalizedMessage());
					log.warn("Failed creating contract for {}", partner, ex);
					return true; // rollback
				}

				@Override
				public void doFinally()
				{
					addLog("@Created@ @C_BPartner_ID@:" + partner.getValue() + "_" + partner.getName());
				}
			});
		}
	}

	private I_C_Flatrate_Term createTerm(final I_C_BPartner partner)
	{
		final IContextAware context = PlainContextAware.newWithThreadInheritedTrx(getCtx());
		final boolean completeIt = true;
		final I_C_Flatrate_Term term = flatrateBL.createTerm(context, partner, conditions, startDate, userInCharge, product, completeIt);
		if (term == null)
		{
			return null;
		}

		if (product != null)
		{
			term.setM_Product_ID(product.getM_Product_ID());
		}

		if (endDate != null)
		{
			term.setEndDate(endDate);
		}

		InterfaceWrapperHelper.save(term);
		return term;
	}

	/**
	 * Implement this method in the subclass to provide all the partners that are about to have terms created.
	 *
	 * @return an iterator of the partners.
	 */
	public abstract Iterator<I_C_BPartner> getBPartners();

}

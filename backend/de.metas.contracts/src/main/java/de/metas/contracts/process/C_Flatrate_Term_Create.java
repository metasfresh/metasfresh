package de.metas.contracts.process;

import java.sql.Timestamp;

import javax.annotation.Nullable;

import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;

import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.process.FlatrateTermCreator.FlatrateTermCreatorBuilder;
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
	private final FlatrateTermCreatorBuilder builder = FlatrateTermCreator.builder();

	public void setStartDate(Timestamp startDate)
	{
		builder.startDate(startDate);
	}

	public void setEndDate(Timestamp endDate)
	{
		builder.endDate(endDate);
	}

	public void setUserInCharge(I_AD_User userInCharge)
	{
		builder.userInCharge(userInCharge);
	}

	public void addProduct(@Nullable final I_M_Product product)
	{
		builder.product(product);
	}

	public void setConditions(I_C_Flatrate_Conditions conditions)
	{
		builder.conditions(conditions);
	}

	@Override
	public String doIt() throws Exception
	{
		builder
				.ctx(getCtx())
				.bPartners(getBPartners())
				.build()
				.createTermsForBPartners();

		return MSG_OK;
	}

	/**
	 * Implement this method in the subclass to provide all the partners that are about to have terms created.
	 *
	 * @return an iterator of the partners.
	 */
	protected abstract Iterable<I_C_BPartner> getBPartners();
}

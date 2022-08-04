/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.contacts.partialpayment.command;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.process.FlatrateTermCreator;
import de.metas.inout.model.validator.M_InOut;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Product;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

@Value
@Builder
public class InterimInvoiceFlatrateTermCreateCommand
{
	@Getter(AccessLevel.PRIVATE)
	Properties ctx;

	@Getter(AccessLevel.PRIVATE)
	BPartnerId bpartnerId;

	@Getter(AccessLevel.PRIVATE)
	ConditionsId conditionsId;

	@Getter(AccessLevel.PRIVATE)
	ProductId productId;

	@Getter(AccessLevel.PRIVATE)
	Timestamp dateFrom;

	@Getter(AccessLevel.PRIVATE)
	Timestamp dateTo;

	ITrxManager trxManager = Services.get(ITrxManager.class);
	IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	IProductDAO productDAO = Services.get(IProductDAO.class);

	public void execute()
	{
		trxManager.runInThreadInheritedTrx(this::createInterimInvoiceFlatrateTerm);
	}

	private void createInterimInvoiceFlatrateTerm()
	{
		final I_C_Flatrate_Term flatrateTerm = createFlatrateTerm();

		final Collection<I_M_InOut> matchingInOuts = findMatchingInOuts(flatrateTerm);

	}

	private I_C_Flatrate_Term createFlatrateTerm()
	{
		final I_C_BPartner bpartner = bpartnerDAO.getById(bpartnerId);
		final I_C_Flatrate_Conditions conditions = flatrateDAO.getConditionsById(conditionsId);
		final I_M_Product product = productDAO.getById(productId);
		return FlatrateTermCreator.builder()
				.bPartners(Collections.singleton(bpartner))
				.conditions(conditions)
				.ctx(ctx)
				.product(product)
				.startDate(dateFrom)
				.endDate(dateTo)
				.build()
				.createTermsForBPartners()
				.stream()
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No flatrate term created")); //TODO replace with ad_message
	}

	private Collection<I_M_InOut> findMatchingInOuts(final I_C_Flatrate_Term flatrateTerm)
	{
		inOut
	}
}

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts.flatrate.process;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermRequest.FlatrateTermPriceRequest;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.TimeUtil;

import java.time.LocalDate;

public class C_Flatrate_Term_Change_Product_for_Partner extends JavaProcess implements IProcessPrecondition
{
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);

	@Param(parameterName = I_C_Flatrate_Term.COLUMNNAME_M_Product_ID, mandatory = true)
	private int p_M_Product_ID;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (context.isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution
				.acceptIf(I_C_BPartner.Table_Name.equals(context.getTableName()));
	}

	@Override
	protected String doIt() throws Exception
	{
		updateFlatrateTermsProductAndPrice();

		return MSG_OK;
	}

	private void updateFlatrateTermsProductAndPrice()
	{
		final ImmutableList<I_C_Flatrate_Term> terms = retrieveFlatrateTerms(retrieveSelectedBPartnerId());

		for (final I_C_Flatrate_Term term : terms)
		{
			updateFlatrateTermProductAndPrice(term);
		}
	}

	private void updateFlatrateTermProductAndPrice(@NonNull final I_C_Flatrate_Term term)
	{
		final LocalDate date = TimeUtil.asLocalDate(term.getStartDate(), orgDAO.getTimeZone(OrgId.ofRepoId(term.getAD_Org_ID())));
		final FlatrateTermPriceRequest request = FlatrateTermPriceRequest.builder()
				.flatrateTerm(term)
				.productId(retrieveSelectedProductId())
				.priceDate(date)
				.build();

		flatrateBL.updateFlatrateTermProductAndPrice(request);
	}

	private ImmutableList<I_C_Flatrate_Term> retrieveFlatrateTerms(@NonNull final BPartnerId bPartnerId)
	{
		return flatrateDAO.retrieveRunningTermsForDropShipPartnerAndProductCategory(bPartnerId, retrieveProductCategoryId());
	}

	private ProductId retrieveSelectedProductId()
	{
		return ProductId.ofRepoId(p_M_Product_ID);
	}

	private ProductCategoryId retrieveProductCategoryId()
	{
		return productDAO.retrieveProductCategoryByProductId(retrieveSelectedProductId());
	}

	private BPartnerId retrieveSelectedBPartnerId()
	{
		return BPartnerId.ofRepoId(getRecord_ID());
	}
}


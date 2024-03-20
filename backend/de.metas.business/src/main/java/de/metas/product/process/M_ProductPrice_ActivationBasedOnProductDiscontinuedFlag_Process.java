/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.product.process;

import de.metas.i18n.AdMessageKey;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.IProductDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

public class M_ProductPrice_ActivationBasedOnProductDiscontinuedFlag_Process extends JavaProcess implements IProcessPrecondition
{
	private static final AdMessageKey ERROR_MISSING_DISCONTINUED_FROM = AdMessageKey.of("ActivationBasedOnProductDiscontinuedFlag_Process.MissingDiscontinuedFrom");

	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

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
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_M_Product product = productDAO.getById(getRecord_ID());
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryFilter<I_M_Product> productFilter = queryBL.createCompositeQueryFilter(I_M_Product.class)
				.addOnlyActiveRecordsFilter()
				.addCompareFilter(
						I_M_Product.COLUMNNAME_M_Product_ID,
						CompareQueryFilter.Operator.EQUAL,
						product.getM_Product_ID());

		if (product.isDiscontinued())
		{
			final ZoneId zoneId = orgDAO.getTimeZone(OrgId.ofRepoId(product.getAD_Org_ID()));
			final Optional<LocalDate> discontinuedFrom = Optional.ofNullable(product.getDiscontinuedFrom())
					.map(discontinuedFromTimestamp -> TimeUtil.asLocalDate(discontinuedFromTimestamp, zoneId));

			if (!discontinuedFrom.isPresent())
			{
				throw new AdempiereException(ERROR_MISSING_DISCONTINUED_FROM)
						.appendParametersToMessage()
						.setParameter("ProductId", product.getM_Product_ID());
			}

			priceListDAO.updateProductPricesIsActive(productFilter, discontinuedFrom.get(), false);
		}
		else
		{
			priceListDAO.updateProductPricesIsActive(productFilter, null, true);
		}

		return MSG_OK;
	}
}

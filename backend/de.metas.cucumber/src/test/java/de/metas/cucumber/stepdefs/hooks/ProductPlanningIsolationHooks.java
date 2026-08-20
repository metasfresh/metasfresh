package de.metas.cucumber.stepdefs.hooks;

import de.metas.util.Services;
import io.cucumber.java.After;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.eevolution.model.I_PP_Product_Planning;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

/**
 * Cross-scenario isolation hooks.
 * <p>
 * A {@link I_PP_Product_Planning} with no {@code M_Product_ID} is generic: planning resolution
 * applies it to EVERY product that has no product-specific planning. Cucumber runs all scenarios of
 * a profile in one JVM against a shared DB with no per-scenario reset, so a single generic planning
 * left active leaks into every later scenario — its products become "purchased", the material
 * disposition auto-advises a SUPPLY/PURCHASE candidate, and MD_Candidate-count assertions fail.
 * <p>
 * Product-specific plannings cannot leak (they are pinned to a scenario's auto-generated product),
 * so only the generic ones are deactivated. Runs in an own committed transaction so the cleanup
 * survives a scenario that fails mid-step (an in-scenario cleanup step would be skipped on failure).
 */
public class ProductPlanningIsolationHooks
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@After
	public void deactivateGenericProductPlannings()
	{
		trxManager.runInNewTrx(() -> {
			final List<I_PP_Product_Planning> genericPlannings = queryBL
					.createQueryBuilder(I_PP_Product_Planning.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_PP_Product_Planning.COLUMNNAME_M_Product_ID, null)
					.create()
					.list();

			for (final I_PP_Product_Planning planning : genericPlannings)
			{
				planning.setIsActive(false);
				saveRecord(planning);
			}
		});
	}
}

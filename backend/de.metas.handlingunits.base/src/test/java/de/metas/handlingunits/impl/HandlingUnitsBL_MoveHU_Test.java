package de.metas.handlingunits.impl;

import static de.metas.business.BusinessTestHelper.createLocator;
import static de.metas.business.BusinessTestHelper.createWarehouse;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.handlingunits.base
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Properties;

import de.metas.organization.ClientAndOrgId;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.TrxRunnable;

import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HUContextDateTrxProvider.ITemporaryDateTrx;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.time.SystemTime;
import org.junit.jupiter.api.Test;

public class HandlingUnitsBL_MoveHU_Test extends AbstractHUTest
{

	private I_M_Locator locator1;
	private I_M_Locator locator2;

	@Override
	protected void initialize()
	{
		final I_M_Warehouse warehouse1 = createWarehouse("TestWH1");
		this.locator1 = createLocator("TestLocator1", warehouse1);
		final I_M_Warehouse warehouse2 = createWarehouse("TestWH2");
		this.locator2 = createLocator("TestLocator1", warehouse2);
	}

	@Override
	protected HUTestHelper createHUTestHelper()
	{
		return new HUTestHelper()
		{
			@Override
			protected String createAndStartTransaction()
			{
				return ITrx.TRXNAME_None;
			}

			@Override
			protected IMutableHUContext createInitialHUContext(final IContextAware contextProvider)
			{
				return createMutableHUContextForProcessing(contextProvider.getTrxName());
			}
		};
	}

	/**
	 * Generate and move an VHU.
	 * Make sure the {@link I_M_HU_Trx_Line} have the DateTrx which we specified.
	 */
	@Test
	public void test_MoveVHU_DateTrxOverride()
	{
		final Properties ctx = Env.getCtx();

		//
		// Create the VHU
		final I_M_HU vhu;
		{
			final IMutableHUContext huContext = Services.get(IHUContextFactory.class).createMutableHUContext(ctx, ClientAndOrgId.ofClientAndOrg(Env.getAD_Client_ID(), Env.getAD_Org_ID(ctx)));

			final List<I_M_HU> vhus = helper.createHUs(
					huContext,
					helper.huDefVirtual,
					helper.pTomatoProductId,
					BigDecimal.ONE, // qtyToLoad,
					uomKg);
			vhu = CollectionUtils.singleElement(vhus);

			vhu.setM_Locator_ID(locator1.getM_Locator_ID());
			InterfaceWrapperHelper.save(vhu);
		}

		//
		// Remove the trx lines so far, because we don't care about those ones.
		// From now on it's important
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_Trx_Line.class, ctx, ITrx.TRXNAME_None)
				.create()
				.deleteDirectly();

		//
		// Start the model interceptor (which is generating the trx lines)
		Services.get(IModelInterceptorRegistry.class)
				.addModelInterceptor(de.metas.handlingunits.hutransaction.interceptor.M_HU.INSTANCE);

		//
		// Change VHU's locator
		// NOTE: we are enforced to change the HU in a transaction
		final ZonedDateTime dateTrx = LocalDate.of(1993, Month.OCTOBER, 10).atStartOfDay(SystemTime.zoneId());
		Services.get(ITrxManager.class).runInNewTrx((TrxRunnable)localTrxName -> {
			InterfaceWrapperHelper.setTrxName(vhu, localTrxName);

			try (final ITemporaryDateTrx dateTrxTmp = IHUContext.DateTrxProvider.temporarySet(dateTrx))
			{
				vhu.setM_Locator_ID(locator2.getM_Locator_ID());
				InterfaceWrapperHelper.save(vhu);
			}
		});

		//
		// Validate generated trx lines
		final List<I_M_HU_Trx_Line> trxLines = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_M_HU_Trx_Line.class)
				.create()
				.list();
		assertThat(trxLines).hasSize(2);
		
		// Assert they have the right DateTrx
		for (final I_M_HU_Trx_Line trxLine : trxLines)
		{
			assertThat(trxLine.getDateTrx()).isEqualTo(TimeUtil.asTimestamp(dateTrx));
		}
	}
}

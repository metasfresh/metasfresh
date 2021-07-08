package de.metas.contracts.inoutcandidate;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import de.metas.common.util.time.SystemTime;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Product;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_SubscriptionProgress;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import de.metas.util.collections.IteratorUtils;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class SubscriptionShipmentScheduleHandler_RetrieveModelsWithMissingCandidates_Tests
{
	private I_C_SubscriptionProgress firstRecord;
	private I_C_SubscriptionProgress secondRecord;
	private I_M_Product secondProduct;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final I_M_Product firstProduct = newInstance(I_M_Product.class);
		firstProduct.setProductType(X_M_Product.PRODUCTTYPE_Item);
		save(firstProduct);

		final I_C_Flatrate_Term firstTerm = newInstance(I_C_Flatrate_Term.class);
		firstTerm.setM_Product_ID(firstProduct.getM_Product_ID());
		save(firstTerm);

		firstRecord = newInstance(I_C_SubscriptionProgress.class);
		firstRecord.setC_Flatrate_Term(firstTerm);
		firstRecord.setEventDate(SystemTime.asTimestamp());
		firstRecord.setEventType(X_C_SubscriptionProgress.EVENTTYPE_Delivery);
		firstRecord.setStatus(X_C_SubscriptionProgress.STATUS_Planned);
		save(firstRecord);

		secondProduct = newInstance(I_M_Product.class);
		secondProduct.setProductType(X_M_Product.PRODUCTTYPE_Item);
		save(secondProduct);

		final I_C_Flatrate_Term secondTerm = newInstance(I_C_Flatrate_Term.class);
		secondTerm.setM_Product_ID(secondProduct.getM_Product_ID());
		save(secondTerm);

		secondRecord = newInstance(I_C_SubscriptionProgress.class);
		secondRecord.setC_Flatrate_Term(secondTerm);
		secondRecord.setEventDate(TimeUtil.addDays(de.metas.common.util.time.SystemTime.asTimestamp(), 4));
		secondRecord.setEventType(X_C_SubscriptionProgress.EVENTTYPE_Delivery);
		secondRecord.setStatus(X_C_SubscriptionProgress.STATUS_Planned);
		save(secondRecord);
	}

	@Test
	public void retrieveModelsWithMissingCandidates_No_SysConfig()
	{
		assertOnlyFirstRecordIsReturned();
	}

	@Test
	public void retrieveModelsWithMissingCandidates_Different_Advance_Days_Values()
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

		// guard
		{
			sysConfigBL.setValue(SubscriptionShipmentScheduleHandler.SYSCONFIG_CREATE_SHIPMENT_SCHEDULES_IN_ADVANCE_DAYS, 3, ClientId.SYSTEM, OrgId.ANY);
			assertOnlyFirstRecordIsReturned();
		}

		sysConfigBL.setValue(SubscriptionShipmentScheduleHandler.SYSCONFIG_CREATE_SHIPMENT_SCHEDULES_IN_ADVANCE_DAYS, 4, ClientId.SYSTEM, OrgId.ANY);
		assertBothRecordsAreReturned();

		sysConfigBL.setValue(SubscriptionShipmentScheduleHandler.SYSCONFIG_CREATE_SHIPMENT_SCHEDULES_IN_ADVANCE_DAYS, 5, ClientId.SYSTEM, OrgId.ANY);
		assertBothRecordsAreReturned();
	}

	/**
	 * Verifies that subscription progress records with service products do not miss any shipment schedule.
	 */
	@Test
	public void retrieveModelsWithMissingCandidates_Service_Product()
	{
		// taken from another test; i don't really care, i just need a setup that returns both records
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		sysConfigBL.setValue(SubscriptionShipmentScheduleHandler.SYSCONFIG_CREATE_SHIPMENT_SCHEDULES_IN_ADVANCE_DAYS, 4, ClientId.SYSTEM, OrgId.ANY);
		assertBothRecordsAreReturned(); // guard

		secondProduct.setProductType(X_M_Product.PRODUCTTYPE_Service);
		save(secondProduct);

		assertOnlyFirstRecordIsReturned();
	}

	private void assertOnlyFirstRecordIsReturned()
	{
		final List<? extends Object> result = IteratorUtils.asList(new SubscriptionShipmentScheduleHandler().retrieveModelsWithMissingCandidates(Env.getCtx(), ITrx.TRXNAME_ThreadInherited));

		assertThat(result).hasSize(1);
		assertThat(result.get(0)).isInstanceOf(I_C_SubscriptionProgress.class);

		final I_C_SubscriptionProgress retrievedProgress = (I_C_SubscriptionProgress)result.get(0);
		assertThat(retrievedProgress.getC_SubscriptionProgress_ID()).isEqualTo(firstRecord.getC_SubscriptionProgress_ID());
	}

	private void assertBothRecordsAreReturned()
	{
		final List<? extends Object> secondResult = IteratorUtils.asList(new SubscriptionShipmentScheduleHandler().retrieveModelsWithMissingCandidates(Env.getCtx(), ITrx.TRXNAME_ThreadInherited));
		assertThat(secondResult).hasSize(2);

		assertThat(secondResult).allSatisfy(r -> {
			assertThat(r).isInstanceOf(I_C_SubscriptionProgress.class);
		});

		final I_C_SubscriptionProgress retrievedProgress = (I_C_SubscriptionProgress)secondResult.get(0);
		assertThat(retrievedProgress.getC_SubscriptionProgress_ID()).isEqualTo(firstRecord.getC_SubscriptionProgress_ID());

		final I_C_SubscriptionProgress retrievedProgressInTheFuture = (I_C_SubscriptionProgress)secondResult.get(1);
		assertThat(retrievedProgressInTheFuture.getC_SubscriptionProgress_ID()).isEqualTo(secondRecord.getC_SubscriptionProgress_ID());
	}

}

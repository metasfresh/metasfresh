package de.metas.contracts.flatrate.impexp;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.Mutable;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_I_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class FlatrateTermImportProcess_SimpleCase_Test
{
	private Properties ctx;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		ctx = Env.getCtx();
	}

	@Ignore
	@Test
	public void testImportActiveFlatrateTerms()
	{
		final Timestamp startDate = SystemTime.asTimestamp();
		final Timestamp masterEndDate = TimeUtil.addDays(startDate, 90);
		
		final I_I_Flatrate_Term iflatrateTerm = prepareImportFlatrateTerm(startDate, masterEndDate);

		final FlatrateTermImportProcess importProcess = new FlatrateTermImportProcess();
		importProcess.setCtx(ctx);
		importProcess.importRecord(new Mutable<>(), iflatrateTerm);
		
		final I_C_Flatrate_Term flatrateTerm = iflatrateTerm.getC_Flatrate_Term();
		assertThat(flatrateTerm).isNotNull();
		assertThat(flatrateTerm.getBill_BPartner()).isNotNull();
		assertThat(flatrateTerm.getBill_Location()).isNotNull();
		assertThat(flatrateTerm.getDropShip_BPartner()).isNotNull();
		assertThat(flatrateTerm.getDropShip_Location()).isNotNull();
		assertThat(flatrateTerm.getBill_User()).isNotNull();
		assertThat(flatrateTerm.getDropShip_User()).isNotNull();
			
		assertThat(flatrateTerm.getStartDate()).isEqualTo(iflatrateTerm.getStartDate());

	}

	private I_I_Flatrate_Term prepareImportFlatrateTerm(final Timestamp startDate, final Timestamp masterEndDate)
	{
		final int bpartnerId= prepareBPartner();

		final I_M_Product product = PrepareImportDataFactory.createProduct("01", "testProduct");
		final I_C_Flatrate_Conditions conditions = PrepareImportDataFactory.flatrateConditionsBuilder()
								.name("Abo")
								.invoiceRule(X_C_Flatrate_Conditions.INVOICERULE_Sofort)
								.typeConditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Subscription)
								.build();
		
		return IFlatrateTermFactory.builder()
				.ctx(ctx)
				.bartnerId(bpartnerId)
				.dropShipBPartnerId(bpartnerId)
				.productId(product.getM_Product_ID())
				.flatrateConditionsId(conditions.getC_Flatrate_Conditions_ID())
				.price(BigDecimal.valueOf(10))
				.qty(BigDecimal.valueOf(1))
				.startDate(startDate)
				.endDate(null)
				.masterStartDate(masterEndDate)
				.masterEndDate(startDate)
				.build();
	}

	private int prepareBPartner()
	{
		final I_C_BPartner bpartner = PrepareImportDataFactory.createBpartner("G0022", true);
		
		PrepareImportDataFactory.bpLocationBuilder()
				.bpartner(bpartner)
				.isBillTo_Default(true)
				.isShipTo_Default(true)
				.build();

		PrepareImportDataFactory.userBuilder()
				.bpartner(bpartner)
				.isBillToContact_Default(true)
				.isShipToContact_Default(true)
				.firstName("FN")
				.lastName("LN")
				.build();
		
		return bpartner.getC_BPartner_ID();
	}
	
}

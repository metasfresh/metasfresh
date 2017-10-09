package de.metas.contracts.flatrate.impexp;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.util.lang.Mutable;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.StartupListener;
import de.metas.contracts.impl.AbstractFlatrateTermTest;
import de.metas.contracts.impl.FlatrateTermDataFactory;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_I_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.inout.invoicecandidate.InOutLinesWithMissingInvoiceCandidate;

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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, InOutLinesWithMissingInvoiceCandidate.class })
public class FlatrateTermImportProcess_SimpleCase_Test extends AbstractFlatrateTermTest
{
	@Test
	public void testImportActiveFlatrateTerms()
	{
		final Timestamp startDate = TimeUtil.parseTimestamp("2017-09-10");
		final Timestamp masterEndDate = TimeUtil.addDays(startDate, 90);
		final Timestamp expectedEndDate = TimeUtil.parseTimestamp("2018-09-09");

		final int bpartnerId = prepareBPartner();

		final I_M_Product product = FlatrateTermDataFactory.productNew()
				.value("01")
				.name("testProduct")
				.build();

		final I_C_Flatrate_Conditions conditions = FlatrateTermDataFactory.flatrateConditionsNew()
				.name("Abo")
				.calendar(getCalendar())
				.invoiceRule(X_C_Flatrate_Conditions.INVOICERULE_Sofort)
				.typeConditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Subscription)
				.build();

		final I_I_Flatrate_Term iflatrateTerm = IFlatrateTermFactory.builder()
				.ctx(helper.getCtx())
				.bartnerId(bpartnerId)
				.dropShipBPartnerId(bpartnerId)
				.productId(product.getM_Product_ID())
				.flatrateConditionsId(conditions.getC_Flatrate_Conditions_ID())
				.price(BigDecimal.valueOf(10))
				.qty(BigDecimal.valueOf(1))
				.startDate(startDate)
				.masterStartDate(startDate)
				.masterEndDate(masterEndDate)
				.build();

		final FlatrateTermImportProcess importProcess = new FlatrateTermImportProcess();
		importProcess.setCtx(helper.getCtx());
		importProcess.importRecord(new Mutable<>(), iflatrateTerm);

		final I_C_Flatrate_Term flatrateTerm = iflatrateTerm.getC_Flatrate_Term();
		assertThat(flatrateTerm).isNotNull();
		
		assertPartnerData(iflatrateTerm);

		assertThat(flatrateTerm.getStartDate()).isEqualTo(iflatrateTerm.getStartDate());
		assertThat(flatrateTerm.getEndDate()).isEqualTo(expectedEndDate);
		assertThat(flatrateTerm.getMasterStartDate()).isEqualTo(startDate);
		assertThat(flatrateTerm.getMasterEndDate()).isEqualTo(masterEndDate);
		
	}
	
	@Test
	public void testImportingInActiveFlatrateTerms()
	{
		final Timestamp startDate = TimeUtil.parseTimestamp("2017-03-10");
		final Timestamp endDate = TimeUtil.parseTimestamp("2017-09-09");
		final Timestamp masterEndDate = TimeUtil.addDays(startDate, 90);

		final int bpartnerId = prepareBPartner();

		final I_M_Product product = FlatrateTermDataFactory.productNew()
				.value("01")
				.name("testProduct")
				.build();

		final I_C_Flatrate_Conditions conditions = FlatrateTermDataFactory.flatrateConditionsNew()
				.name("Abo")
				.calendar(getCalendar())
				.invoiceRule(X_C_Flatrate_Conditions.INVOICERULE_Sofort)
				.typeConditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Subscription)
				.build();

		final I_I_Flatrate_Term iflatrateTerm = IFlatrateTermFactory.builder()
				.ctx(helper.getCtx())
				.bartnerId(bpartnerId)
				.dropShipBPartnerId(bpartnerId)
				.productId(product.getM_Product_ID())
				.flatrateConditionsId(conditions.getC_Flatrate_Conditions_ID())
				.price(BigDecimal.valueOf(10))
				.qty(BigDecimal.valueOf(1))
				.startDate(startDate)
				.endDate(endDate)
				.masterStartDate(startDate)
				.masterEndDate(masterEndDate)
				.build();

		final FlatrateTermImportProcess importProcess = new FlatrateTermImportProcess();
		importProcess.setCtx(helper.getCtx());
		importProcess.importRecord(new Mutable<>(), iflatrateTerm);

		final I_C_Flatrate_Term flatrateTerm = iflatrateTerm.getC_Flatrate_Term();
		assertThat(flatrateTerm).isNotNull();
		
		assertPartnerData(iflatrateTerm);

		assertThat(flatrateTerm.getStartDate()).isEqualTo(iflatrateTerm.getStartDate());
		assertThat(flatrateTerm.getEndDate()).isEqualTo(endDate);
		assertThat(flatrateTerm.getMasterStartDate()).isEqualTo(startDate);
		assertThat(flatrateTerm.getMasterEndDate()).isEqualTo(masterEndDate);
		assertThat(flatrateTerm.getDocAction()).isEqualTo(X_C_Flatrate_Term.DOCACTION_None);
		
	}

	private int prepareBPartner()
	{
		final I_C_BPartner bpartner = FlatrateTermDataFactory.bpartnerNew()
				.bpValue("G0022")
				.isCustomer(true)
				.build();

		FlatrateTermDataFactory.bpLocationNew()
				.bpartner(bpartner)
				.isBillTo_Default(true)
				.isShipTo_Default(true)
				.build();

		FlatrateTermDataFactory.userNew()
				.bpartner(bpartner)
				.isBillToContact_Default(true)
				.isShipToContact_Default(true)
				.firstName("FN")
				.lastName("LN")
				.build();

		return bpartner.getC_BPartner_ID();
	}
	
	private void assertPartnerData(final I_I_Flatrate_Term iflatrateTerm )
	{
		final I_C_Flatrate_Term flatrateTerm = iflatrateTerm.getC_Flatrate_Term();
		assertThat(flatrateTerm.getBill_BPartner()).isNotNull();
		assertThat(flatrateTerm.getBill_BPartner()).isEqualToComparingFieldByField(iflatrateTerm.getDropShip_BPartner());
		assertThat(flatrateTerm.getBill_Location()).isNotNull();
		assertThat(flatrateTerm.getDropShip_BPartner()).isNotNull();
		assertThat(flatrateTerm.getDropShip_BPartner_ID()).isEqualByComparingTo(iflatrateTerm.getDropShip_BPartner_ID());
		assertThat(flatrateTerm.getDropShip_Location()).isNotNull();
		assertThat(flatrateTerm.getBill_User()).isNotNull();
		assertThat(flatrateTerm.getDropShip_User()).isNotNull();
	}

	@Override
	protected void initialize()
	{
	}

}

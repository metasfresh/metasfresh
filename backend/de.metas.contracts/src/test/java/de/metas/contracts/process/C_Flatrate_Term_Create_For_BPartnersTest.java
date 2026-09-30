package de.metas.contracts.process;

/*
 * #%L
 * de.metas.contracts
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

import de.metas.contracts.commission.commissioninstance.services.CommissionProductService;
import de.metas.invoice.service.InvoiceScheduleRepository;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.refund.RefundConfigRepository;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Which terms "Erzeuge Vertrag" creates per partner: one entry per product, where a {@code null} entry
 * stands for one product-less term. No entry means no term is created at all.
 */
public class C_Flatrate_Term_Create_For_BPartnersTest
{
	private C_Flatrate_Term_Create_For_BPartners process;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(new RefundConfigRepository(new InvoiceScheduleRepository()));
		SpringContextHolder.registerJUnitBean(new CommissionProductService());

		process = new C_Flatrate_Term_Create_For_BPartners();
	}

	private I_C_Flatrate_Conditions conditions(final String typeConditions)
	{
		final I_C_Flatrate_Conditions conditions = newInstance(I_C_Flatrate_Conditions.class);
		conditions.setName(typeConditions);
		conditions.setType_Conditions(typeConditions);
		saveRecord(conditions);
		return conditions;
	}

	@Test
	void compensationGroup_oneProductLessTerm()
	{
		final List<I_M_Product> products = process.getTermProducts(conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_CompensationGroup));

		assertThat(products).containsExactly((I_M_Product)null);
	}

	@Test
	void refundable_oneProductLessTerm_unchanged()
	{
		final List<I_M_Product> products = process.getTermProducts(conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refundable));

		assertThat(products).containsExactly((I_M_Product)null);
	}

	@Test
	void subscriptionWithoutMatchings_noTerm_unchanged()
	{
		final List<I_M_Product> products = process.getTermProducts(conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Subscription));

		assertThat(products).isEmpty();
	}
}

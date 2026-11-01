/*
 * #%L
 * de.metas.business
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

package de.metas.promotioncode;

import de.metas.adempiere.model.I_C_Order;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class C_Order_PromotionCodeTest
{
	private C_Order interceptor;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		interceptor = new C_Order();
	}

	@Test
	void sameCode_throws()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setC_PromotionCode_ID(100);
		order.setC_PromotionCode2_ID(100);

		assertThatThrownBy(() -> interceptor.validateNoDuplicatePromotionCode(order))
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> assertThat(((AdempiereException)ex).isUserValidationError()).isTrue());
	}

	@Test
	void differentCodes_passes()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setC_PromotionCode_ID(100);
		order.setC_PromotionCode2_ID(200);

		assertThatCode(() -> interceptor.validateNoDuplicatePromotionCode(order))
				.doesNotThrowAnyException();
	}

	@Test
	void onlyOneCodeSet_passes()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setC_PromotionCode_ID(100);

		assertThatCode(() -> interceptor.validateNoDuplicatePromotionCode(order))
				.doesNotThrowAnyException();
	}

	@Test
	void bothCodesZero_passes()
	{
		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);

		assertThatCode(() -> interceptor.validateNoDuplicatePromotionCode(order))
				.doesNotThrowAnyException();
	}
}

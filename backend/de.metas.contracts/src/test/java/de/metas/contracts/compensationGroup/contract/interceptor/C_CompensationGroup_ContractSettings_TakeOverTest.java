package de.metas.contracts.compensationGroup.contract.interceptor;

import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver;
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequestFactory;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

/**
 * The take-over record's own-line product is appended as a compensation line with an explicit percentage, but the line's
 * type/amt-type come from the product alone; a non-Percent / non-Discount product would silently collapse the line to 0%.
 */
class C_CompensationGroup_ContractSettings_TakeOverTest
{
	private final C_CompensationGroup_ContractSettings_TakeOver interceptor = new C_CompensationGroup_ContractSettings_TakeOver(new GroupCompensationLineCreateRequestFactory());

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	private I_M_Product product(final String compensationType, final String amtType)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setGroupCompensationType(compensationType);
		product.setGroupCompensationAmtType(amtType);
		saveRecord(product);
		return product;
	}

	private I_C_CompensationGroup_ContractSettings_TakeOver recordWith(final I_M_Product product)
	{
		final I_C_CompensationGroup_ContractSettings_TakeOver record = newInstance(I_C_CompensationGroup_ContractSettings_TakeOver.class);
		record.setM_Product_ID(product.getM_Product_ID());
		return record;
	}

	@Test
	void percentDiscountProduct_isAccepted()
	{
		final I_M_Product product = product(X_C_OrderLine.GROUPCOMPENSATIONTYPE_Discount, X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_Percent);
		assertThatCode(() -> interceptor.assertOwnLineProductIsPercentDiscount(recordWith(product))).doesNotThrowAnyException();
	}

	@Test
	void unsetTypes_defaultToPercentDiscount_isAccepted()
	{
		final I_M_Product product = product(null, null);
		assertThatCode(() -> interceptor.assertOwnLineProductIsPercentDiscount(recordWith(product))).doesNotThrowAnyException();
	}

	@Test
	void nonPercentAmtType_isRejected()
	{
		final I_M_Product product = product(X_C_OrderLine.GROUPCOMPENSATIONTYPE_Discount, X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_PriceAndQty);
		assertThatThrownBy(() -> interceptor.assertOwnLineProductIsPercentDiscount(recordWith(product)))
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> assertThat(((AdempiereException)ex).isUserValidationError()).isTrue());
	}

	@Test
	void surchargeProduct_isRejected()
	{
		final I_M_Product product = product(X_C_OrderLine.GROUPCOMPENSATIONTYPE_Surcharge, X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_Percent);
		assertThatThrownBy(() -> interceptor.assertOwnLineProductIsPercentDiscount(recordWith(product)))
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> assertThat(((AdempiereException)ex).isUserValidationError()).isTrue());
	}
}

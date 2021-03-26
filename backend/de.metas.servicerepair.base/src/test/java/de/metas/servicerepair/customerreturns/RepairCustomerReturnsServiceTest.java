/*
 * #%L
 * de.metas.servicerepair.base
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

package de.metas.servicerepair.customerreturns;

import de.metas.handlingunits.inout.returns.ReturnsServiceFacade;
import de.metas.handlingunits.inout.returns.customer.CustomerReturnInOutRecordFactory;
import de.metas.handlingunits.inout.returns.customer.CustomerReturnsWithoutHUsProducer;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Attribute;
import org.compiere.model.X_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class RepairCustomerReturnsServiceTest
{
	private RepairCustomerReturnsService repairCustomerReturnsService;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final CustomerReturnInOutRecordFactory customerReturnRepository = new CustomerReturnInOutRecordFactory();
		final CustomerReturnsWithoutHUsProducer customerReturnsWithoutHUsProducer = new CustomerReturnsWithoutHUsProducer(customerReturnRepository);
		final ReturnsServiceFacade returnsServiceFacade = new ReturnsServiceFacade(customerReturnsWithoutHUsProducer);
		final AlreadyShippedHUsInPreviousSystemRepository alreadyShippedHUsInPreviousSystemRepository = new AlreadyShippedHUsInPreviousSystemRepository();
		repairCustomerReturnsService = new RepairCustomerReturnsService(returnsServiceFacade, alreadyShippedHUsInPreviousSystemRepository);

		createWarrantyStartDateAttribute();
	}

	private void createWarrantyStartDateAttribute()
	{
		final I_M_Attribute attribute = InterfaceWrapperHelper.newInstance(I_M_Attribute.class);
		attribute.setValue(AttributeConstants.WarrantyStartDate.getCode());
		attribute.setName(AttributeConstants.WarrantyStartDate.getCode());
		attribute.setAttributeValueType(X_M_Attribute.ATTRIBUTEVALUETYPE_Date);
		InterfaceWrapperHelper.saveRecord(attribute);
	}

	@SuppressWarnings("SameParameterValue")
	private ProductId createProduct(final String guaranteeMonths)
	{
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setGuaranteeMonths(guaranteeMonths);
		InterfaceWrapperHelper.saveRecord(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	@SuppressWarnings("SameParameterValue")
	private static ImmutableAttributeSet warrantyStartDateAttributes(@NonNull final String date)
	{
		return ImmutableAttributeSet.builder()
				.attributeValue(AttributeConstants.WarrantyStartDate, LocalDate.parse(date))
				.build();
	}

	@Nested
	class isWarrantyCase
	{
		@Test
		void noWarrantyStartDateAttribute()
		{
			final boolean result = repairCustomerReturnsService.isWarrantyCase(
					ProductId.ofRepoId(1),
					ImmutableAttributeSet.EMPTY,
					LocalDate.parse("2021-03-01"));
			Assertions.assertThat(result).isFalse();
		}

		@Test
		void warrantyExpired()
		{
			final boolean result = repairCustomerReturnsService.isWarrantyCase(
					createProduct(X_M_Product.GUARANTEEMONTHS_12),
					warrantyStartDateAttributes("2018-01-01"),
					LocalDate.parse("2021-03-01"));
			Assertions.assertThat(result).isFalse();
		}

		@Test
		void warrantyActive()
		{
			final boolean result = repairCustomerReturnsService.isWarrantyCase(
					createProduct(X_M_Product.GUARANTEEMONTHS_12),
					warrantyStartDateAttributes("2018-01-01"),
					LocalDate.parse("2018-12-31"));
			Assertions.assertThat(result).isTrue();
		}

	}
}
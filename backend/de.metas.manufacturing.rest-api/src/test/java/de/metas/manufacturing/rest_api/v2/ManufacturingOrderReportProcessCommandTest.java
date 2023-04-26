/*
 * #%L
 * de.metas.manufacturing.rest-api
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

package de.metas.manufacturing.rest_api.v2;

import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.manufacturing.v2.JsonRequestHULookup;
import de.metas.common.manufacturing.v2.JsonRequestManufacturingOrdersReport;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.reservation.HUReservationRepository;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.manufacturing.rest_api.ManufacturingOrderReportAuditRepository;
import de.metas.manufacturing.rest_api.v2.ManufacturingOrderReportProcessCommand;
import de.metas.product.ProductId;
import de.metas.util.Check;
import lombok.Builder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.assertj.core.api.Assertions;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

@ExtendWith(AdempiereTestWatcher.class)
class ManufacturingOrderReportProcessCommandTest
{
	private HUTestHelper testHelper;
	private final ProductId productId = ProductId.ofRepoId(1);

	@BeforeEach
	void beforeEach()
	{
		testHelper = HUTestHelper.newInstanceOutOfTrx();
	}

	@Builder(builderMethodName = "hu", builderClassName = "$HUBuilder")
	private HuId createHU(
			@Nullable final String huValue,
			@Nullable final String lotNumber,
			@Nullable final LocalDate bestBeforeDate,
			@Nullable final String serialNo)
	{
		final I_M_HU hu = InterfaceWrapperHelper.newInstance(I_M_HU.class);
		hu.setM_HU_PI_Version_ID(HuPackingInstructionsVersionId.VIRTUAL.getRepoId());
		hu.setHUStatus(X_M_HU.HUSTATUS_Active);
		if (huValue != null && !Check.isBlank(huValue))
		{
			hu.setM_HU_ID(HuId.ofHUValue(huValue).getRepoId());
			hu.setValue(huValue);
		}
		InterfaceWrapperHelper.saveRecord(hu);

		//
		// Storage
		final I_M_HU_Storage huStorage = InterfaceWrapperHelper.newInstance(I_M_HU_Storage.class);
		huStorage.setM_HU_ID(hu.getM_HU_ID());
		huStorage.setM_Product_ID(productId.getRepoId());
		huStorage.setQty(new BigDecimal("100"));
		huStorage.setC_UOM_ID(testHelper.uomEachId.getRepoId());
		InterfaceWrapperHelper.saveRecord(huStorage);

		//
		// Attributes
		if (lotNumber != null && !Check.isBlank(lotNumber))
		{
			createHULotNumberAttribute(hu, lotNumber);
		}
		if (bestBeforeDate != null)
		{
			createHUBestBeforeDateAttribute(hu, bestBeforeDate);
		}
		if (serialNo != null && !Check.isBlank(serialNo))
		{
			createHUSerialNoAttribute(hu, serialNo);
		}

		return HuId.ofRepoId(hu.getM_HU_ID());
	}

	private void createHULotNumberAttribute(final I_M_HU hu, final String lotNumber)
	{
		final I_M_HU_Attribute huAttribute = InterfaceWrapperHelper.newInstance(I_M_HU_Attribute.class);
		huAttribute.setM_HU_ID(hu.getM_HU_ID());
		huAttribute.setM_Attribute_ID(testHelper.attr_LotNumber.getM_Attribute_ID());
		huAttribute.setValue(lotNumber);
		InterfaceWrapperHelper.saveRecord(huAttribute);
	}

	private void createHUBestBeforeDateAttribute(final I_M_HU hu, final LocalDate bestBeforeDate)
	{
		final I_M_HU_Attribute huAttribute = InterfaceWrapperHelper.newInstance(I_M_HU_Attribute.class);
		huAttribute.setM_HU_ID(hu.getM_HU_ID());
		huAttribute.setM_Attribute_ID(testHelper.attr_BestBeforeDate.getM_Attribute_ID());
		huAttribute.setValueDate(TimeUtil.asTimestamp(bestBeforeDate));
		InterfaceWrapperHelper.saveRecord(huAttribute);
	}

	private void createHUSerialNoAttribute(final I_M_HU hu, final String serialNo)
	{
		final I_M_HU_Attribute huAttribute = InterfaceWrapperHelper.newInstance(I_M_HU_Attribute.class);
		huAttribute.setM_HU_ID(hu.getM_HU_ID());
		huAttribute.setM_Attribute_ID(testHelper.attr_SerialNo.getM_Attribute_ID());
		huAttribute.setValue(serialNo);
		InterfaceWrapperHelper.saveRecord(huAttribute);
	}

	@Nested
	public class resolveHUId
	{
		private ManufacturingOrderReportProcessCommand command;

		@BeforeEach
		public void beforeEach()
		{
			command = ManufacturingOrderReportProcessCommand.builder()
					.huReservationService(new HUReservationService(new HUReservationRepository()))
					.auditRepository(new ManufacturingOrderReportAuditRepository())
					.jsonObjectMapper(JsonObjectMapperHolder.newJsonObjectMapper())
					.request(JsonRequestManufacturingOrdersReport.builder()
							.receipts(ImmutableList.of())
							.issues(ImmutableList.of())
							.build())
					.build();
		}

		@Test
		public void huValue()
		{
			final HuId huId = hu().huValue("12345").build();
			final HuId foundHUId = command.resolveHUId(productId, JsonRequestHULookup.builder().huValue("12345").build());
			Assertions.assertThat(foundHUId).isEqualTo(huId);
		}

		@Test
		public void lotNumber()
		{
			final HuId huId = hu().lotNumber("12345").build();
			final HuId foundHUId = command.resolveHUId(productId, JsonRequestHULookup.builder().lotNumber("12345").build());
			Assertions.assertThat(foundHUId).isEqualTo(huId);
		}

		@Test
		public void bestBeforeDate()
		{
			final HuId huId = hu().bestBeforeDate(LocalDate.parse("2020-02-03")).build();
			final HuId foundHUId = command.resolveHUId(productId, JsonRequestHULookup.builder().bestBeforeDate(LocalDate.parse("2020-02-03")).build());
			Assertions.assertThat(foundHUId).isEqualTo(huId);
		}

		@Test
		public void serialNo()
		{
			final HuId huId = hu().serialNo("12345").build();
			final HuId foundHUId = command.resolveHUId(productId, JsonRequestHULookup.builder().serialNo("12345").build());
			Assertions.assertThat(foundHUId).isEqualTo(huId);
		}
	}

}
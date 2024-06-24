/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.handlingunits.qrcodes.service;

import com.google.common.collect.ImmutableList;
import de.metas.business.BusinessTestHelper;
import de.metas.global_qrcodes.service.GlobalQRCodeService;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUnitType;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class HUQRCodesServiceTest
{
	//
	// Services
	private HUTestHelper helper;
	private HUQRCodesService huQRCodesService;

	//
	// Masterdata
	private static final BigDecimal QTY_CUs_per_TU = new BigDecimal("20");
	private static final QtyTU QTY_TUs_per_LU = QtyTU.ofInt(40);
	private ProductId productId;
	private I_M_HU_PI_Item luPIItem;
	private I_M_HU_PI tuPI;
	private I_M_HU_PI_Item_Product tuPIItemProduct;

	@Value
	@Builder
	private static class Attributes
	{
		@Nullable String bestBeforeDate;
		@Nullable String lotNumber;
		@Nullable String weightNet;
	}

	@BeforeEach
	void beforeEach()
	{
		this.helper = HUTestHelper.newInstanceOutOfTrx();
		this.huQRCodesService = new HUQRCodesService(new HUQRCodesRepository(), new GlobalQRCodeService());

		this.productId = BusinessTestHelper.createProductId("MyProduct", helper.uomEach);

		{
			this.tuPI = helper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
			final I_M_HU_PI_Item itemMA = helper.createHU_PI_Item_Material(tuPI);
			this.tuPIItemProduct = helper.assignProduct(itemMA, productId, QTY_CUs_per_TU, helper.uomEach);
		}

		{
			final I_M_HU_PI luPI = helper.createHUDefinition("LU", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
			this.luPIItem = helper.createHU_PI_Item_IncludedHU(luPI, tuPI, QTY_TUs_per_LU.toBigDecimal());
		}
	}

	private void setGenerateQRCodeIfMissing(final boolean generateQRCodeIfMissing)
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		sysConfigBL.setValue(HUQRCodesService.SYSCONFIG_GenerateQRCodeIfMissing, generateQRCodeIfMissing, ClientId.SYSTEM, OrgId.ANY);
	}

	private HuId createLU(final Attributes attributes)
	{
		final List<I_M_HU> hus = helper.newLUs()
				.loadingUnitPIItem(luPIItem)
				.tuPIItemProduct(tuPIItemProduct)
				.totalQtyCU(QTY_TUs_per_LU.toBigDecimal().multiply(QTY_CUs_per_TU))
				.build();
		final I_M_HU hu = CollectionUtils.singleElement(hus);

		setAttributes(hu, attributes);

		return HuId.ofRepoId(hu.getM_HU_ID());
	}

	private HuId createTU(final Attributes attributes)
	{
		final IHUContext huContext = helper.createMutableHUContextForProcessing(ITrx.TRXNAME_None);
		final List<I_M_HU> hus = helper.createHUs(huContext, tuPI, productId, QTY_CUs_per_TU, helper.uomEach);
		final I_M_HU hu = CollectionUtils.singleElement(hus);

		setAttributes(hu, attributes);

		return HuId.ofRepoId(hu.getM_HU_ID());
	}

	private HuId createVHU(final Attributes attributes)
	{
		final I_M_HU vhu = helper.newVHU()
				.productId(productId)
				.qty(Quantity.of("1", helper.uomEach))
				.build();

		setAttributes(vhu, attributes);

		return HuId.ofRepoId(vhu.getM_HU_ID());
	}

	private void setAttributes(@NonNull final I_M_HU hu, final Attributes attributes)
	{
		final IAttributeStorage huAttributes = helper.createMutableHUContext()
				.getHUAttributeStorageFactory()
				.getAttributeStorage(hu);
		huAttributes.setSaveOnChange(true);
		huAttributes.setValue(AttributeConstants.ATTR_BestBeforeDate, attributes.getBestBeforeDate() != null ? LocalDate.parse(attributes.getBestBeforeDate()) : null);
		huAttributes.setValue(AttributeConstants.ATTR_LotNumber, attributes.getLotNumber());
		huAttributes.setValue(Weightables.ATTR_WeightNet, attributes.getWeightNet() != null ? new BigDecimal(attributes.getWeightNet()) : null);
	}

	@Nested
	class generateForExistingHU
	{
		@Test
		void LU()
		{
			final HuId luId = createLU(Attributes.builder()
					.bestBeforeDate("2023-01-01")
					.lotNumber("123")
					.weightNet("45.678")
					.build());

			final HUQRCode qrCode = huQRCodesService.generateForExistingHU(luId).getSingleQRCode(luId);
			System.out.println(qrCode);

			assertThat(qrCode.getPackingInfo().getHuUnitType()).isEqualTo(HUQRCodeUnitType.LU);
			assertThat(qrCode.getPackingInfo().getCaption()).isEqualTo("LU");
			assertThat(qrCode.getProduct().getCode()).isEqualTo("MyProduct");
			assertThat(qrCode.getProduct().getName()).isEqualTo("MyProduct");
			assertThat(qrCode.getAttributeValueAsString(AttributeConstants.ATTR_BestBeforeDate)).contains("2023-01-01");
			assertThat(qrCode.getAttributeValueAsString(AttributeConstants.ATTR_LotNumber)).contains("123");
			assertThat(qrCode.getAttributeValueAsString(Weightables.ATTR_WeightNet)).contains("45.678");
		}

		@Test
		void AggregatedTUs()
		{
			createLU(Attributes.builder()
					.bestBeforeDate("2023-01-01")
					.lotNumber("123")
					.weightNet("45.678")
					.build());

			final HuId huId = helper.retrieveAllHandlingUnits()
					.stream()
					.filter(hu -> helper.handlingUnitsBL().isAggregateHU(hu))
					.map(hu -> HuId.ofRepoId(hu.getM_HU_ID()))
					.findFirst()
					.orElseThrow(() -> new AdempiereException("No aggregated TUs found"));

			final ImmutableList<HUQRCode> qrCodes = huQRCodesService.generateForExistingHU(huId).toList();
			assertThat(qrCodes).hasSize(QTY_TUs_per_LU.toInt());

			for (final HUQRCode qrCode : qrCodes)
			{
				assertThat(qrCode.getPackingInfo().getHuUnitType()).isEqualTo(HUQRCodeUnitType.TU);
				assertThat(qrCode.getPackingInfo().getCaption()).isEqualTo("TU");
				assertThat(qrCode.getProduct().getCode()).isEqualTo("MyProduct");
				assertThat(qrCode.getProduct().getName()).isEqualTo("MyProduct");
				assertThat(qrCode.getAttributeValueAsString(AttributeConstants.ATTR_BestBeforeDate)).contains("2023-01-01");
				assertThat(qrCode.getAttributeValueAsString(AttributeConstants.ATTR_LotNumber)).contains("123");
				assertThat(qrCode.getAttributeValueAsString(Weightables.ATTR_WeightNet)).contains("45.678");
			}
		}

		@Test
		void TU()
		{
			final HuId tuId = createTU(Attributes.builder()
					.bestBeforeDate("2023-01-01")
					.lotNumber("123")
					.weightNet("45.678")
					.build());

			final HUQRCode qrCode = huQRCodesService.generateForExistingHU(tuId).getSingleQRCode(tuId);
			System.out.println(qrCode);

			assertThat(qrCode.getPackingInfo().getHuUnitType()).isEqualTo(HUQRCodeUnitType.TU);
			assertThat(qrCode.getPackingInfo().getCaption()).isEqualTo("TU");
			assertThat(qrCode.getProduct().getCode()).isEqualTo("MyProduct");
			assertThat(qrCode.getProduct().getName()).isEqualTo("MyProduct");
			assertThat(qrCode.getAttributeValueAsString(AttributeConstants.ATTR_BestBeforeDate)).contains("2023-01-01");
			assertThat(qrCode.getAttributeValueAsString(AttributeConstants.ATTR_LotNumber)).contains("123");
			assertThat(qrCode.getAttributeValueAsString(Weightables.ATTR_WeightNet)).contains("45.678");
		}

		@Test
		void VHU()
		{
			final HuId vhuId = createVHU(Attributes.builder()
					.bestBeforeDate("2023-01-01")
					.lotNumber("123")
					.weightNet("45.678")
					.build());

			final HUQRCode qrCode = huQRCodesService.generateForExistingHU(vhuId).getSingleQRCode(vhuId);
			System.out.println(qrCode);

			assertThat(qrCode.getPackingInfo().getHuUnitType()).isEqualTo(HUQRCodeUnitType.VHU);
			assertThat(qrCode.getPackingInfo().getCaption()).isEqualTo("VirtualPI");
			assertThat(qrCode.getProduct().getCode()).isEqualTo("MyProduct");
			assertThat(qrCode.getProduct().getName()).isEqualTo("MyProduct");
			assertThat(qrCode.getAttributeValueAsString(AttributeConstants.ATTR_BestBeforeDate)).contains("2023-01-01");
			assertThat(qrCode.getAttributeValueAsString(AttributeConstants.ATTR_LotNumber)).contains("123");
			assertThat(qrCode.getAttributeValueAsString(Weightables.ATTR_WeightNet)).contains("45.678");
		}
	}

	@Nested
	class getQRCodeByHuId
	{
		@Test
		void generateIfMissing()
		{
			setGenerateQRCodeIfMissing(true);

			final HuId huId = createVHU(Attributes.builder().build());
			final HUQRCode qrCode = huQRCodesService.getQRCodeByHuId(huId);
			assertThat(huQRCodesService.getQRCodeByHuId(huId))
					.isEqualTo(qrCode)
					.isNotSameAs(qrCode);
		}

		@Test
		void doNotGenerateIfMissing()
		{
			setGenerateQRCodeIfMissing(false);

			final HuId huId = createVHU(Attributes.builder().build());

			assertThatThrownBy(() -> huQRCodesService.getQRCodeByHuId(huId))
					.hasMessageStartingWith("No QR Code attached to HU");

			final HUQRCode qrCode = huQRCodesService.generateForExistingHU(huId).getSingleQRCode(huId);
			
			assertThat(huQRCodesService.getQRCodeByHuId(huId))
					.isEqualTo(qrCode)
					.isNotSameAs(qrCode);
		}
	}
}
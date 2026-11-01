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
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.model.I_M_HU;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_QRCode;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import de.metas.handlingunits.qrcodes.ean13.EAN13HUQRCode;
import de.metas.handlingunits.qrcodes.gs1.GS1HUQRCode;
import de.metas.handlingunits.qrcodes.mobile.MobileQRCodeMessages;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodePackingInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUniqueId;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUnitType;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.handlingunits.qrcodes.special.PickOnTheFlyQRCode;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.scannable_code.ScannedCode;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
		this.huQRCodesService = HUQRCodesService.newInstanceForUnitTesting();

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

	private HuId createLU(@Nullable final Attributes attributes)
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

	private HuId createTU() {return createTU(null);}

	private HuId createTU(@Nullable final Attributes attributes)
	{
		final IHUContext huContext = helper.createMutableHUContextForProcessing(ITrx.TRXNAME_None);
		final List<I_M_HU> hus = helper.createHUs(huContext, tuPI, productId, QTY_CUs_per_TU, helper.uomEach);
		final I_M_HU hu = CollectionUtils.singleElement(hus);

		setAttributes(hu, attributes);

		return HuId.ofRepoId(hu.getM_HU_ID());
	}

	private HuId createVHU(@Nullable final Attributes attributes)
	{
		final I_M_HU vhu = helper.newVHU()
				.productId(productId)
				.qty(Quantity.of("1", helper.uomEach))
				.build();

		setAttributes(vhu, attributes);

		return HuId.ofRepoId(vhu.getM_HU_ID());
	}

	private void setAttributes(@NonNull final I_M_HU hu, @Nullable final Attributes attributes)
	{
		if (attributes == null) {return;}
		final IAttributeStorage huAttributes = helper.createMutableHUContext()
				.getHUAttributeStorageFactory()
				.getAttributeStorage(hu);
		huAttributes.setSaveOnChange(true);
		huAttributes.setValue(AttributeConstants.ATTR_BestBeforeDate, attributes.getBestBeforeDate() != null ? LocalDate.parse(attributes.getBestBeforeDate()) : null);
		huAttributes.setValue(AttributeConstants.ATTR_LotNumber, attributes.getLotNumber());
		huAttributes.setValue(Weightables.ATTR_WeightNet, attributes.getWeightNet() != null ? new BigDecimal(attributes.getWeightNet()) : null);
	}

	@Nested
	class getSingleQRCodeByHuIds
	{
		private final HUQRCodesRepository repo = new HUQRCodesRepository();

		private HUQRCode newQRCode()
		{
			return HUQRCode.builder()
					.id(HUQRCodeUniqueId.ofUUID(UUID.randomUUID()))
					.packingInfo(HUQRCodePackingInfo.builder()
							.huUnitType(HUQRCodeUnitType.TU)
							.packingInstructionsId(HuPackingInstructionsId.ofRepoId(123))
							.caption("Some TU")
							.build())
					.attributes(ImmutableList.of())
					.build();
		}

		@Test
		void returnsOnlyHUsWithExactlyOneAssignedQRCode()
		{
			final HuId huSingle = HuId.ofRepoId(701);
			final HuId huMulti = HuId.ofRepoId(702);
			final HuId huNone = HuId.ofRepoId(703);

			final HUQRCode qrSingle = newQRCode();
			repo.createNew(qrSingle, huSingle);
			// two distinct QR codes assigned to the same HU -> ambiguous -> must be omitted
			repo.createNew(newQRCode(), huMulti);
			repo.createNew(newQRCode(), huMulti);
			// huNone has no assigned QR code

			final Map<HuId, HUQRCode> result = huQRCodesService.getSingleQRCodeByHuIds(
					ImmutableList.of(huSingle, huMulti, huNone));

			assertThat(result).containsOnlyKeys(huSingle);
			assertThat(result.get(huSingle).getId()).isEqualTo(qrSingle.getId());
		}
	}

	@Nested
	@SuppressWarnings("OptionalGetWithoutIsPresent")
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
			assertThat(qrCode.getProduct().get().getCode()).isEqualTo("MyProduct");
			assertThat(qrCode.getProduct().get().getName()).isEqualTo("MyProduct");
			assertThat(qrCode.getBestBeforeDate()).contains(LocalDate.parse("2023-01-01"));
			assertThat(qrCode.getLotNumber()).contains("123");
			assertThat(qrCode.getWeightInKg()).contains(new BigDecimal("45.678"));
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
				assertThat(qrCode.getProduct().get().getCode()).isEqualTo("MyProduct");
				assertThat(qrCode.getProduct().get().getName()).isEqualTo("MyProduct");
				assertThat(qrCode.getBestBeforeDate()).contains(LocalDate.parse("2023-01-01"));
				assertThat(qrCode.getLotNumber()).contains("123");
				assertThat(qrCode.getWeightInKg()).contains(new BigDecimal("45.678"));
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
			assertThat(qrCode.getProduct().get().getCode()).isEqualTo("MyProduct");
			assertThat(qrCode.getProduct().get().getName()).isEqualTo("MyProduct");
			assertThat(qrCode.getBestBeforeDate()).contains(LocalDate.parse("2023-01-01"));
			assertThat(qrCode.getLotNumber()).contains("123");
			assertThat(qrCode.getWeightInKg()).contains(new BigDecimal("45.678"));
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
			assertThat(qrCode.getProduct().get().getCode()).isEqualTo("MyProduct");
			assertThat(qrCode.getProduct().get().getName()).isEqualTo("MyProduct");
			assertThat(qrCode.getBestBeforeDate()).contains(LocalDate.parse("2023-01-01"));
			assertThat(qrCode.getLotNumber()).contains("123");
			assertThat(qrCode.getWeightInKg()).contains(new BigDecimal("45.678"));
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

	@Nested
	class getQRCodeByScannedCode
	{
		@Test
		void globalQRCode()
		{
			setGenerateQRCodeIfMissing(true);

			final HuId tuId = createTU();
			final HUQRCode expectedQrCode = huQRCodesService.getQRCodeByHuId(tuId);

			final HUQRCode resolvedQrCode = huQRCodesService.getQRCodeByScannedCode(ScannedCode.ofString(expectedQrCode.toGlobalQRCodeString()));

			assertThat(resolvedQrCode).isEqualTo(expectedQrCode);
		}

		@Test
		void externalBarcode()
		{
			setGenerateQRCodeIfMissing(true);

			final HuId tuId = createTU();
			final HUQRCode expectedQrCode = huQRCodesService.getQRCodeByHuId(tuId);

			final String externalBarcode = "EXT-BARCODE-123";
			final I_M_HU hu = InterfaceWrapperHelper.load(tuId, I_M_HU.class);
			final IAttributeStorage huAttributes = helper.createMutableHUContext()
					.getHUAttributeStorageFactory()
					.getAttributeStorage(hu);
			huAttributes.setSaveOnChange(true);
			huAttributes.setValue(AttributeConstants.ATTR_ExternalBarcode, externalBarcode);

			final HUQRCode resolvedQrCode = huQRCodesService.getQRCodeByScannedCode(ScannedCode.ofString(externalBarcode));

			assertThat(resolvedQrCode).isEqualTo(expectedQrCode);
		}

		@Test
		void codeThatParsesToSomethingOtherThanAnAssignedHU_isRejected()
		{
			// "PICK_ON_THE_FLY" parses successfully - but to a PickOnTheFlyQRCode, which identifies no
			// existing handling unit. It must be rejected rather than resolved onto whichever HU happens
			// to carry that string as its M_HU.Value / ExternalBarcode.
			assertThatThrownBy(() -> huQRCodesService.getQRCodeByScannedCode(ScannedCode.ofString("PICK_ON_THE_FLY")))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("Invalid HU QR code");
		}
	}

	@Nested
	class parse
	{
		HUQRCodesService huQRCodesService;

		@BeforeEach
		void beforeEach()
		{
			huQRCodesService = HUQRCodesService.newInstanceForUnitTesting();
		}

		@Test
		void pickOnFly()
		{
			assertThat(huQRCodesService.parse(PickOnTheFlyQRCode.instance.getAsString())).isSameAs(PickOnTheFlyQRCode.instance);
		}

		@Test
		void gs1()
		{
			final IHUQRCode huQRCode = huQRCodesService.parse("0197311876341811310300752015170809");
			assertThat(huQRCode).isInstanceOf(GS1HUQRCode.class);

			final GS1HUQRCode gs1 = (GS1HUQRCode)huQRCode;
			assertThat(gs1.getWeightInKg()).contains(new BigDecimal("7.520"));
			assertThat(gs1.getBestBeforeDate()).contains(LocalDate.parse("2017-08-09"));
			assertThat(gs1.getLotNumber()).isEmpty();
		}

		@Test
		void ean13()
		{
			final IHUQRCode huQRCode = huQRCodesService.parse("2859414004825");
			assertThat(huQRCode).isInstanceOf(EAN13HUQRCode.class);

			final EAN13HUQRCode ean13 = (EAN13HUQRCode)huQRCode;
			assertThat(ean13.unbox().getPrefix().getAsString()).isEqualTo("28");
			assertThat(ean13.unbox().getProductNo().getAsString()).isEqualTo("59414");
			assertThat(ean13.getWeightInKg()).contains(new BigDecimal("0.482"));
			assertThat(ean13.getBestBeforeDate()).isEmpty();
			assertThat(ean13.getLotNumber()).isEmpty();
		}

		@Test
		void huId_when_hu_missing()
		{
			final IHUQRCode parsedHUQRCode = huQRCodesService.parse("1000066");
			System.out.println("parsedHUQRCode: " + parsedHUQRCode + " (" + parsedHUQRCode.getClass() + ")");
			assertThat(parsedHUQRCode).isNotInstanceOf(HUQRCode.class);
		}

		@Test
		void huId_when_hu_exists()
		{
			final HuId tuId = createTU();
			final IHUQRCode parsedHUQRCode = huQRCodesService.parse(tuId.toHUValue());
			System.out.println("parsedHUQRCode: " + parsedHUQRCode + " (" + parsedHUQRCode.getClass() + ")");
			assertThat(parsedHUQRCode).isInstanceOf(HUQRCode.class);
		}

		@Test
		void locatorQRCode_throwsUserFriendlyError()
		{
			// LOC# prefix is a locator QR code, not an HU QR code
			final String locatorQRCodeString = "LOC#1#{\"warehouseId\":1,\"locatorId\":2,\"caption\":\"Regal-01\"}";
			assertThatThrownBy(() -> huQRCodesService.parse(locatorQRCodeString))
					.isInstanceOf(AdempiereException.class)
					.satisfies(ex -> assertThat(((AdempiereException)ex).isUserValidationError()).isTrue());
		}

		@Test
		void unrecognized_throwsUserFriendlyError()
		{
			final String junkCode = "TOTALLY_UNKNOWN_FORMAT_XYZ";
			assertThatThrownBy(() -> huQRCodesService.parse(junkCode))
					.isInstanceOf(AdempiereException.class)
					.satisfies(ex -> assertThat(((AdempiereException)ex).isUserValidationError()).isTrue());
		}

		@Test
		void truncatedHuQRCodeHead_throwsUserFriendlyError()
		{
			// A long HU QR code can be split mid-stream on a slow scanner device: the head fragment keeps the
			// valid "HU#<version>#" prefix but carries a truncated JSON payload. It must surface the SAME friendly
			// "not recognized" message as any other bad code, not leak the raw "Failed converting payload" error.
			final HuId tuId = createTU();
			final String fullHuQRCode = huQRCodesService.generateForExistingHU(tuId).getSingleQRCode(tuId).getAsString();

			// sanity: the full code is a valid, parseable HU QR code
			assertThat(huQRCodesService.parse(fullHuQRCode)).isInstanceOf(HUQRCode.class);

			// simulate the device split: keep the HU#<version># prefix, drop the tail so the JSON payload is incomplete
			final String truncatedHead = fullHuQRCode.substring(0, fullHuQRCode.length() / 2);
			assertThat(truncatedHead).startsWith("HU#");

			assertThatThrownBy(() -> huQRCodesService.parse(truncatedHead))
					.isInstanceOf(AdempiereException.class)
					.satisfies(ex -> {
						final AdempiereException ae = (AdempiereException)ex;
						assertThat(ae.isUserValidationError()).isTrue();
						assertThat(ae.getErrorCode()).isEqualTo(MobileQRCodeMessages.NOT_RECOGNIZED.toAD_Message());
					});
		}
	}

	@Nested
	class assertQRCodeAssignedToHU
	{
		private I_M_HU_QRCode getQRCodeRecord(@NonNull final HUQRCode qrCode)
		{
			return Services.get(IQueryBL.class)
					.createQueryBuilder(I_M_HU_QRCode.class)
					.addEqualsFilter(I_M_HU_QRCode.COLUMNNAME_UniqueId, qrCode.getId().getAsString())
					.create()
					.firstOnlyNotNull(I_M_HU_QRCode.class);
		}

		@Test
		void assignedToDifferentHU_messageDistinguishesTheCause()
		{
			final HuId assignedHuId = createTU();
			final HuId otherHuId = createTU();

			// generate + assign a QR code to assignedHuId
			final HUQRCode qrCode = huQRCodesService.generateForExistingHU(assignedHuId).getSingleQRCode(assignedHuId);

			// Asserting it against a DIFFERENT HU must fail — and the message must distinguish the cause
			// (the QR is active and assigned, just to another HU), not merely say "not assigned".
			assertThatThrownBy(() -> huQRCodesService.assertQRCodeAssignedToHU(qrCode, otherHuId))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("is not assigned to HU")
					.hasMessageContaining("assigned to HU(s)")
					.hasMessageContaining(String.valueOf(assignedHuId.getRepoId()));
		}

		@Test
		void noActiveAssignment_messageDistinguishesTheCause()
		{
			final HuId huId = createTU();
			final HUQRCode qrCode = huQRCodesService.generateForExistingHU(huId).getSingleQRCode(huId);

			// remove the assignment: the QR row stays active but no active assignment remains
			huQRCodesService.removeAssignment(qrCode, ImmutableSet.of(huId));

			assertThatThrownBy(() -> huQRCodesService.assertQRCodeAssignedToHU(qrCode, huId))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("QR active but has no active assignment");
		}

		@Test
		void inactiveQRCodeRow_messageDistinguishesTheCause()
		{
			final HuId huId = createTU();
			final HUQRCode qrCode = huQRCodesService.generateForExistingHU(huId).getSingleQRCode(huId);

			// deactivate the M_HU_QRCode row itself
			final I_M_HU_QRCode record = getQRCodeRecord(qrCode);
			record.setIsActive(false);
			InterfaceWrapperHelper.save(record);

			assertThatThrownBy(() -> huQRCodesService.assertQRCodeAssignedToHU(qrCode, huId))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("M_HU_QRCode row inactive");
		}

		@Test
		void noQRCodeRow_messageDistinguishesTheCause()
		{
			final HuId huId = createTU();
			final HUQRCode qrCode = huQRCodesService.generateForExistingHU(huId).getSingleQRCode(huId);

			// delete the M_HU_QRCode row entirely
			InterfaceWrapperHelper.delete(getQRCodeRecord(qrCode));

			assertThatThrownBy(() -> huQRCodesService.assertQRCodeAssignedToHU(qrCode, huId))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("no M_HU_QRCode row for UniqueId");
		}
	}
}

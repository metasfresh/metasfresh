/*
 * #%L
 * de.metas.handlingunits.mobileui
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

package de.metas.handlingunits.rest_api;

import de.metas.ad_reference.ADReferenceService;
import de.metas.business.BusinessTestHelper;
import de.metas.common.handlingunits.JsonClearanceStatus;
import de.metas.common.handlingunits.JsonHU;
import de.metas.common.handlingunits.JsonSetClearanceStatusRequest;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.grai.HUGraiService;
import de.metas.handlingunits.impl.HUQtyService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.qrcodes.mobile.MobileQRCodeMessages;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.report.labels.HULabelService;
import de.metas.inventory.InventoryCandidateService;
import de.metas.product.ProductId;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

/**
 * {@link HandlingUnitsService#getHUsByQrCode} and the {@code jsonHuIdentifier.qrCode} resolution inside
 * {@link HandlingUnitsService#setClearanceStatus} must accept a legacy {@code M_HU.Value} /
 * {@code ExternalBarcode} label and resolve it to the same handling unit a metasfresh global QR code
 * would - <b>without ever generating a QR code</b> for an HU that has none (a read/resolution path must
 * not mint master data).
 */
class HandlingUnitsServiceLegacyLabelTest
{
	private static final BigDecimal QTY_CUs_per_TU = new BigDecimal("20");
	private static final QtyTU QTY_TUs_per_LU = QtyTU.ofInt(3);

	private HUTestHelper helper;
	private HUQRCodesService huQRCodesService;
	private HandlingUnitsService handlingUnitsService;

	private ProductId productId;
	private I_M_HU_PI_Item luPIItem;
	private I_M_HU_PI tuPI;
	private I_M_HU_PI_Item_Product tuPIItemProduct;

	@BeforeEach
	void beforeEach()
	{
		this.helper = HUTestHelper.newInstanceOutOfTrx();
		SpringContextHolder.registerJUnitBean(ADReferenceService.newMocked());
		this.huQRCodesService = HUQRCodesService.newInstanceForUnitTesting();
		this.handlingUnitsService = new HandlingUnitsService(
				huQRCodesService,
				mock(HUQtyService.class),
				mock(HULabelService.class),
				mock(InventoryCandidateService.class),
				mock(HUGraiService.class));

		this.productId = BusinessTestHelper.createProductId("MyProduct", helper.uomEach);

		this.tuPI = helper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item itemMA = helper.createHU_PI_Item_Material(tuPI);
		this.tuPIItemProduct = helper.assignProduct(itemMA, productId, QTY_CUs_per_TU, helper.uomEach);

		final I_M_HU_PI luPI = helper.createHUDefinition("LU", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		this.luPIItem = helper.createHU_PI_Item_IncludedHU(luPI, tuPI, QTY_TUs_per_LU.toBigDecimal());
	}

	private HuId createTU()
	{
		final List<I_M_HU> hus = helper.createHUs(
				helper.createMutableHUContextForProcessing(ITrx.TRXNAME_None),
				tuPI,
				productId,
				QTY_CUs_per_TU,
				helper.uomEach);
		final I_M_HU hu = CollectionUtils.singleElement(hus);
		return HuId.ofRepoId(hu.getM_HU_ID());
	}

	private HuId createAggregatedTU()
	{
		helper.newLUs()
				.loadingUnitPIItem(luPIItem)
				.tuPIItemProduct(tuPIItemProduct)
				.totalQtyCU(QTY_TUs_per_LU.toBigDecimal().multiply(QTY_CUs_per_TU))
				.build();

		return helper.retrieveAllHandlingUnits()
				.stream()
				.filter(hu -> helper.handlingUnitsBL().isAggregateHU(hu))
				.map(hu -> HuId.ofRepoId(hu.getM_HU_ID()))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No aggregated TUs found"));
	}

	private void setExternalBarcode(final HuId huId, final String externalBarcode)
	{
		final I_M_HU hu = InterfaceWrapperHelper.load(huId, I_M_HU.class);
		final IAttributeStorage huAttributes = helper.createMutableHUContext()
				.getHUAttributeStorageFactory()
				.getAttributeStorage(hu);
		huAttributes.setSaveOnChange(true);
		huAttributes.setValue(AttributeConstants.ATTR_ExternalBarcode, externalBarcode);
	}

	private void setHULocator(final HuId huId, final I_M_Locator locator)
	{
		final I_M_HU hu = InterfaceWrapperHelper.load(huId, I_M_HU.class);
		hu.setM_Locator_ID(locator.getM_Locator_ID());
		InterfaceWrapperHelper.save(hu);
	}

	@Nested
	class getHUsByQrCode
	{
		@Test
		void externalBarcode_resolvesSameHUAsQRCode()
		{
			final HuId tuId = createTU();
			final HUQRCode expectedQrCode = huQRCodesService.getQRCodeByHuId(tuId);

			final String externalBarcode = "EXT-BARCODE-31540";
			setExternalBarcode(tuId, externalBarcode);

			final List<JsonHU> byQRCode = handlingUnitsService.getHUsByQrCode(
					JsonGetByQRCodeRequest.builder().qrCode(expectedQrCode.toGlobalQRCodeString()).build(),
					"en");
			final List<JsonHU> byLegacyLabel = handlingUnitsService.getHUsByQrCode(
					JsonGetByQRCodeRequest.builder().qrCode(externalBarcode).build(),
					"en");

			assertThat(byQRCode).hasSize(1);
			assertThat(byLegacyLabel).hasSize(1);
			assertThat(byLegacyLabel.get(0).getId()).isEqualTo(byQRCode.get(0).getId());
			assertThat(byLegacyLabel.get(0).getId()).isEqualTo(String.valueOf(tuId.getRepoId()));
		}

		@Test
		void plainHUValue_resolvesSameHU()
		{
			final HuId tuId = createTU();

			final List<JsonHU> result = handlingUnitsService.getHUsByQrCode(
					JsonGetByQRCodeRequest.builder().qrCode(tuId.toHUValue()).build(),
					"en");

			assertThat(result).hasSize(1);
			assertThat(result.get(0).getId()).isEqualTo(String.valueOf(tuId.getRepoId()));
		}

		@Test
		void externalBarcode_doesNotGenerateQRCode()
		{
			final HuId tuId = createTU();
			assertThat(huQRCodesService.getFirstQRCodeByHuIdIfExists(tuId)).isEmpty();

			final String externalBarcode = "EXT-BARCODE-NO-QR";
			setExternalBarcode(tuId, externalBarcode);

			final List<JsonHU> result = handlingUnitsService.getHUsByQrCode(
					JsonGetByQRCodeRequest.builder().qrCode(externalBarcode).build(),
					"en");

			assertThat(result).hasSize(1);
			assertThat(result.get(0).getQrCode()).isNull();
			// the whole point of the design: a read path must never mint a QR code as a side effect
			assertThat(huQRCodesService.getFirstQRCodeByHuIdIfExists(tuId)).isEmpty();
		}

		@Test
		void unmatchedCode_returnsEmptyList()
		{
			final List<JsonHU> result = handlingUnitsService.getHUsByQrCode(
					JsonGetByQRCodeRequest.builder().qrCode("NO-SUCH-LABEL-ANYWHERE").build(),
					"en");

			assertThat(result).isEmpty();
		}

		@Test
		void upperLevelLocatingQrCode_withLegacyLabel_resolvesHU_andDoesNotGenerateQRCode()
		{
			// this combination (locating QR code + legacy label) is not reachable through the shipped mobile UI
			// today, but it is reachable through the public REST endpoint directly - defence-in-depth, not a
			// live bug.
			final HuId tuId = createTU();
			final String externalBarcode = "EXT-BARCODE-LOCATING-NO-QR";
			setExternalBarcode(tuId, externalBarcode);
			assertThat(huQRCodesService.getFirstQRCodeByHuIdIfExists(tuId)).isEmpty();

			final I_M_Warehouse warehouse = BusinessTestHelper.createWarehouse("LocatingWarehouse");
			final I_M_Locator locator = BusinessTestHelper.createLocator("LocatingLocator", warehouse);
			setHULocator(tuId, locator);

			final String locatorQrCode = LocatorQRCode.ofLocator(locator).toGlobalQRCodeJsonString();

			final List<JsonHU> result = handlingUnitsService.getHUsByQrCode(
					JsonGetByQRCodeRequest.builder()
							.qrCode(externalBarcode)
							.upperLevelLocatingQrCode(locatorQrCode)
							.build(),
					"en");

			assertThat(result).hasSize(1);
			assertThat(result.get(0).getId()).isEqualTo(String.valueOf(tuId.getRepoId()));

			// the whole point of the design: a read path must never mint a QR code as a side effect
			assertThat(huQRCodesService.getFirstQRCodeByHuIdIfExists(tuId)).isEmpty();
		}
	}

	@Nested
	class setClearanceStatus_qrCodeIdentifier
	{
		@Test
		void externalBarcode_resolvesAndUpdatesHU()
		{
			final HuId tuId = createTU();
			final String externalBarcode = "EXT-BARCODE-CLEARANCE";
			setExternalBarcode(tuId, externalBarcode);

			handlingUnitsService.setClearanceStatus(JsonSetClearanceStatusRequest.builder()
					.huIdentifier(JsonSetClearanceStatusRequest.JsonHUIdentifier.builder().qrCode(externalBarcode).build())
					.clearanceStatus(JsonClearanceStatus.Locked)
					.build());

			final I_M_HU hu = InterfaceWrapperHelper.load(tuId, I_M_HU.class);
			assertThat(hu.getClearanceStatus()).isEqualTo(X_M_HU.CLEARANCESTATUS_Locked);
		}

		@Test
		void externalBarcode_doesNotGenerateQRCode()
		{
			final HuId tuId = createTU();
			final String externalBarcode = "EXT-BARCODE-CLEARANCE-NO-QR";
			setExternalBarcode(tuId, externalBarcode);
			assertThat(huQRCodesService.getFirstQRCodeByHuIdIfExists(tuId)).isEmpty();

			handlingUnitsService.setClearanceStatus(JsonSetClearanceStatusRequest.builder()
					.huIdentifier(JsonSetClearanceStatusRequest.JsonHUIdentifier.builder().qrCode(externalBarcode).build())
					.clearanceStatus(JsonClearanceStatus.Locked)
					.build());

			assertThat(huQRCodesService.getFirstQRCodeByHuIdIfExists(tuId)).isEmpty();
		}

		@Test
		void aggregateHU_withoutQRCode_isRejected_andDoesNotGenerateOne()
		{
			final HuId aggregatedHuId = createAggregatedTU();
			assertThat(huQRCodesService.getFirstQRCodeByHuIdIfExists(aggregatedHuId)).isEmpty();

			// NOTE: must scan the plain M_HU.Value here, NOT an ExternalBarcode. createAggregatedTU() builds the
			// aggregate TU nested inside an LU (not a top-level HU), and getHUIdByValueOrExternalBarcode's
			// ExternalBarcode branch (HandlingUnitsBL.getByExternalBarcode) applies .setOnlyTopLevelHUs(), so it can
			// never resolve a nested aggregate TU - the lookup would fail with HU_NOT_FOUND before
			// resolveHUIdExtractingIfAggregated's aggregate branch is ever reached. M_HU.Value has no such
			// restriction (HandlingUnitsBL.getHUIdByValue), so it is the only legacy-label mechanism that can
			// actually exercise this branch. Do not "simplify" this back to an ExternalBarcode.
			final String huValue = aggregatedHuId.toHUValue();

			assertThatThrownBy(() -> handlingUnitsService.setClearanceStatus(JsonSetClearanceStatusRequest.builder()
					.huIdentifier(JsonSetClearanceStatusRequest.JsonHUIdentifier.builder().qrCode(huValue).build())
					.clearanceStatus(JsonClearanceStatus.Locked)
					.build()))
					.isInstanceOf(AdempiereException.class)
					// NOTE: in this unit-test context (no AD_Message DB row resolved), AdempiereException#getMessage()
					// returns the raw AdMessageKey rather than the translated text - assert on the key itself so this
					// still pins the specific refusal (HU_CANNOT_SPLIT_NO_QR_CODE), not just "some exception was thrown".
					.hasMessageContaining(MobileQRCodeMessages.HU_CANNOT_SPLIT_NO_QR_CODE.toAD_Message())
					.satisfies(ex -> assertThat(((AdempiereException)ex).isUserValidationError()).isTrue());

			// the rejection must not have generated a QR code as a side effect either
			assertThat(huQRCodesService.getFirstQRCodeByHuIdIfExists(aggregatedHuId)).isEmpty();
		}

		@Test
		void unmatchedCode_throwsOperatorFacingHuNotFound()
		{
			assertThatThrownBy(() -> handlingUnitsService.setClearanceStatus(JsonSetClearanceStatusRequest.builder()
					.huIdentifier(JsonSetClearanceStatusRequest.JsonHUIdentifier.builder().qrCode("NO-SUCH-LABEL").build())
					.clearanceStatus(JsonClearanceStatus.Locked)
					.build()))
					.isInstanceOf(AdempiereException.class);
		}
	}
}

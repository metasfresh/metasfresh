/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.ordercandidates.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.common.bpartner.v2.response.JsonResponseBPartner;
import de.metas.common.bpartner.v2.response.JsonResponseComposite;
import de.metas.common.bpartner.v2.response.JsonResponseLocation;
import de.metas.common.ordercandidates.v2.request.JsonOLCandCreateRequest;
import de.metas.common.ordercandidates.v2.request.JsonRequestBPartnerLocationAndContact;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.externalreference.rest.v2.ExternalReferenceRestControllerService;
import de.metas.externalsystem.ExternalSystemId;
import de.metas.externalsystem.ExternalSystemRepository;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.impex.model.I_AD_InputDataSource;
import de.metas.order.impl.DocTypeService;
import de.metas.organization.OrgId;
import de.metas.organization.StoreCreditCardNumberMode;
import de.metas.promotioncode.PromotionCodeRepository;
import de.metas.rest_api.utils.CurrencyService;
import de.metas.rest_api.v2.bpartner.BPartnerMasterdataProvider;
import de.metas.rest_api.v2.bpartner.BpartnerRestController;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonRetrieverService;
import de.metas.security.permissions2.PermissionService;
import org.adempiere.ad.persistence.custom_columns.CustomColumnService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

/**
 * Proves that {@link JsonConverters#fromJson} passes the ordering partner into the GTIN product lookup,
 * i.e. each partner's request resolves to that partner's own {@code M_HU_PI_Item_Product} (packing instruction).
 */
class JsonConvertersFromJsonTest
{
	private static final String GTIN = "90000000001";

	private OrgId orgId;
	private JsonConverters jsonConverters;
	private MasterdataProvider masterdataProvider;
	private BpartnerRestController bpartnerRestController;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		orgId = createOrg();
		createDataSource("test-source");
		createDataSource("DEST.de.metas.ordercandidate");

		final PermissionService permissionService = Mockito.mock(PermissionService.class);
		Mockito.doReturn(orgId).when(permissionService).getDefaultOrgId();

		final ExternalSystemRepository externalSystemRepository = Mockito.mock(ExternalSystemRepository.class);
		Mockito.doReturn(ExternalSystemId.ofRepoId(1)).when(externalSystemRepository).getIdByType(any());

		final BPartnerMasterdataProvider bPartnerMasterdataProvider = Mockito.mock(BPartnerMasterdataProvider.class);
		bpartnerRestController = Mockito.mock(BpartnerRestController.class);

		masterdataProvider = MasterdataProvider.builder()
				.permissionService(permissionService)
				.bpartnerRestController(bpartnerRestController)
				.bPartnerMasterdataProvider(bPartnerMasterdataProvider)
				.externalReferenceRestControllerService(ExternalReferenceRestControllerService.newInstanceForUnitTesting())
				.jsonRetrieverService(Mockito.mock(JsonRetrieverService.class))
				.externalSystemRepository(externalSystemRepository)
				.build();

		jsonConverters = new JsonConverters(
				externalSystemRepository,
				Mockito.mock(CurrencyService.class),
				Mockito.mock(DocTypeService.class),
				Mockito.mock(CustomColumnService.class),
				Mockito.mock(PromotionCodeRepository.class));
	}

	@Test
	void fromJson_resolves_gtin_to_the_ordering_partners_packing_instruction()
	{
		// given: one product, a carton row with the same GTIN for each of two partners
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setValue("feta-200g");
		product.setIsActive(true);
		product.setC_UOM_ID(createUomId());
		InterfaceWrapperHelper.save(product);

		final BPartnerId partnerA = createBPartner("partnerA");
		final BPartnerId partnerB = createBPartner("partnerB");
		final HUPIItemProductId rowA = createPiip(product, partnerA);
		final HUPIItemProductId rowB = createPiip(product, partnerB);

		// when / then: both go through the same MasterdataProvider (and thus the same product cache)
		assertThat(jsonConverters.fromJson(request(partnerA), masterdataProvider).getHuPIItemProductId())
				.as("partner A").isEqualTo(rowA.getRepoId());
		assertThat(jsonConverters.fromJson(request(partnerB), masterdataProvider).getHuPIItemProductId())
				.as("partner B").isEqualTo(rowB.getRepoId());
	}

	private JsonOLCandCreateRequest request(final BPartnerId bpartnerId)
	{
		final String partnerIdentifier = String.valueOf(bpartnerId.getRepoId());
		mockBPartnerEndpoints(partnerIdentifier, bpartnerId);

		return JsonOLCandCreateRequest.builder()
				.externalLineId("line-1")
				.externalHeaderId("header-1")
				.externalSystemCode("EDI")
				.dataSource("int-test-source")
				.poReference("po-1")
				.dateRequired(LocalDate.of(2026, 7, 5))
				.productIdentifier("gtin-" + GTIN)
				.qty(BigDecimal.ONE)
				.bpartner(JsonRequestBPartnerLocationAndContact.builder()
						.bPartnerIdentifier(partnerIdentifier)
						.bPartnerLocationIdentifier("1")
						.build())
				.build();
	}

	private void mockBPartnerEndpoints(final String partnerIdentifier, final BPartnerId bpartnerId)
	{
		final JsonResponseComposite composite = JsonResponseComposite.builder()
				.bpartner(JsonResponseBPartner.builder().metasfreshId(JsonMetasfreshId.of(bpartnerId.getRepoId())).active(true).name("bp").vendor(false).customer(true).company(true).build())
				.build();
		Mockito.doReturn(ResponseEntity.ok(composite)).when(bpartnerRestController).retrieveBPartner(any(), eq(partnerIdentifier));

		final JsonResponseLocation location = JsonResponseLocation.builder().metasfreshId(JsonMetasfreshId.of(1)).active(true).build();
		Mockito.doReturn(ResponseEntity.ok(location)).when(bpartnerRestController).retrieveBPartnerLocation(any(), eq(partnerIdentifier), any());
	}

	private static int createUomId()
	{
		final I_C_UOM uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		uom.setName("PCE");
		uom.setX12DE355("PCE");
		uom.setIsActive(true);
		InterfaceWrapperHelper.save(uom);
		return uom.getC_UOM_ID();
	}

	private static OrgId createOrg()
	{
		final I_AD_Org org = InterfaceWrapperHelper.newInstance(I_AD_Org.class);
		org.setValue("001");
		InterfaceWrapperHelper.saveRecord(org);

		final I_AD_OrgInfo orgInfo = InterfaceWrapperHelper.newInstance(I_AD_OrgInfo.class);
		orgInfo.setAD_Org_ID(org.getAD_Org_ID());
		orgInfo.setStoreCreditCardData(StoreCreditCardNumberMode.DONT_STORE.getCode());
		orgInfo.setTimeZone("Europe/Berlin");
		InterfaceWrapperHelper.saveRecord(orgInfo);
		return OrgId.ofRepoId(org.getAD_Org_ID());
	}

	private static I_AD_InputDataSource createDataSource(final String internalName)
	{
		final I_AD_InputDataSource record = InterfaceWrapperHelper.newInstance(I_AD_InputDataSource.class);
		record.setInternalName(internalName);
		record.setIsActive(true);
		InterfaceWrapperHelper.save(record);
		return record;
	}

	private static BPartnerId createBPartner(final String value)
	{
		final I_C_BPartner bpartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		bpartner.setValue(value);
		bpartner.setName(value);
		InterfaceWrapperHelper.save(bpartner);
		return BPartnerId.ofRepoId(bpartner.getC_BPartner_ID());
	}

	private static HUPIItemProductId createPiip(final I_M_Product product, final BPartnerId bpartnerId)
	{
		final I_M_HU_PI_Item_Product piip = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class);
		piip.setM_Product_ID(product.getM_Product_ID());
		piip.setGTIN(GTIN);
		piip.setC_BPartner_ID(bpartnerId.getRepoId());
		piip.setValidFrom(TimeUtil.parseLocalDateAsTimestamp("2019-01-01"));
		piip.setIsActive(true);
		InterfaceWrapperHelper.save(piip);
		return HUPIItemProductId.ofRepoId(piip.getM_HU_PI_Item_Product_ID());
	}
}

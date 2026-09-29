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
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.common.bpartner.v2.response.JsonResponseBPartner;
import de.metas.common.bpartner.v2.response.JsonResponseComposite;
import de.metas.common.bpartner.v2.response.JsonResponseLocation;
import de.metas.common.ordercandidates.v2.request.JsonRequestBPartnerLocationAndContact;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.externalreference.rest.v2.ExternalReferenceRestControllerService;
import de.metas.externalsystem.ExternalSystemRepository;
import de.metas.organization.OrgId;
import de.metas.rest_api.v2.bpartner.BPartnerMasterdataProvider;
import de.metas.rest_api.v2.bpartner.BpartnerRestController;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonRetrieverService;
import de.metas.security.permissions2.PermissionService;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

/**
 * Order candidates that reference a partner or location by {@code gln-} identifier resolve it among active partners and active locations only.
 * <p>
 * The {@link BpartnerRestController} is mocked to understand only metasfresh-id identifiers (the form {@link MasterdataProvider} passes on
 * after resolving a GLN); any other identifier is "not found". So a non-resolved GLN surfaces as an error, and a resolved one as the ids asserted here.
 */
class MasterdataProviderGetBPartnerInfoTest
{
	private OrgId orgId;
	private MasterdataProvider masterdataProvider;
	private BpartnerRestController bpartnerRestController;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		final I_AD_Org org = InterfaceWrapperHelper.newInstance(I_AD_Org.class);
		org.setValue("001");
		InterfaceWrapperHelper.saveRecord(org);
		orgId = OrgId.ofRepoId(org.getAD_Org_ID());

		bpartnerRestController = Mockito.mock(BpartnerRestController.class);
		mockBPartnerEndpointsForMetasfreshIdsOnly();

		masterdataProvider = MasterdataProvider.builder()
				.permissionService(Mockito.mock(PermissionService.class))
				.bpartnerRestController(bpartnerRestController)
				.bPartnerMasterdataProvider(Mockito.mock(BPartnerMasterdataProvider.class))
				.externalReferenceRestControllerService(ExternalReferenceRestControllerService.newInstanceForUnitTesting())
				.jsonRetrieverService(Mockito.mock(JsonRetrieverService.class))
				.externalSystemRepository(Mockito.mock(ExternalSystemRepository.class))
				.build();
	}

	@Test
	void partnerGln_sharedByInactiveAndActivePartner_resolvesToActivePartner()
	{
		final BPartnerId inactivePartner = createBPartner("old-partner", false);
		createLocation(inactivePartner, "p-shared", true);
		final BPartnerId activePartner = createBPartner("new-partner", true);
		final BPartnerLocationId activeLocation = createLocation(activePartner, "p-shared", true);

		final BPartnerInfo result = masterdataProvider.getBPartnerInfoNotNull(request("gln-p-shared", "gln-p-shared"), orgId);

		assertThat(result.getBpartnerId()).isEqualTo(activePartner);
		assertThat(result.getBpartnerLocationId()).isEqualTo(activeLocation);
	}

	@Test
	void partnerGln_onlyOnInactivePartner_isNotResolved()
	{
		final BPartnerId inactivePartner = createBPartner("old-partner", false);
		createLocation(inactivePartner, "p-inactive", true);

		assertThatThrownBy(() -> masterdataProvider.getBPartnerInfoNotNull(request("gln-p-inactive", "gln-p-inactive"), orgId))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("No BPartner found");
	}

	@Test
	void locationGln_onInactiveAndActiveLocationOfPartner_resolvesToActiveLocation()
	{
		final BPartnerId partner = createBPartner("partner", true);
		createLocation(partner, "loc-1", false); // created first on purpose
		final BPartnerLocationId activeLocation = createLocation(partner, "loc-1", true);

		final BPartnerInfo result = masterdataProvider.getBPartnerInfoNotNull(request(String.valueOf(partner.getRepoId()), "gln-loc-1"), orgId);

		assertThat(result.getBpartnerId()).isEqualTo(partner);
		assertThat(result.getBpartnerLocationId()).isEqualTo(activeLocation);
	}

	@Test
	void locationGln_onlyOnInactiveLocation_isNotResolved()
	{
		final BPartnerId partner = createBPartner("partner", true);
		createLocation(partner, "loc-2", false);
		createLocation(partner, "loc-other", true);

		assertThatThrownBy(() -> masterdataProvider.getBPartnerInfoNotNull(request(String.valueOf(partner.getRepoId()), "gln-loc-2"), orgId))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("No BPartnerLocation found");
	}

	@Test
	void locationGln_withUnknownNonGlnPartner_failsWithTheUsualBPartnerNotFoundMessage()
	{
		assertThatThrownBy(() -> masterdataProvider.getBPartnerInfoNotNull(request("val-unknown-partner", "gln-loc-4"), orgId))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("No BPartner found for the given identifier!")
				.hasMessageContaining("BPartnerIdentifier")
				.hasMessageContaining("val-unknown-partner");
	}

	@Test
	void nonGlnIdentifiers_arePassedOnUnchanged()
	{
		final BPartnerId partner = createBPartner("partner", true);
		final BPartnerLocationId location = createLocation(partner, "loc-3", true);
		final String partnerIdentifier = String.valueOf(partner.getRepoId());
		final String locationIdentifier = String.valueOf(location.getRepoId());

		final BPartnerInfo result = masterdataProvider.getBPartnerInfoNotNull(request(partnerIdentifier, locationIdentifier), orgId);

		assertThat(result.getBpartnerLocationId()).isEqualTo(location);
		Mockito.verify(bpartnerRestController).retrieveBPartner("001", partnerIdentifier);
		Mockito.verify(bpartnerRestController).retrieveBPartnerLocation("001", partnerIdentifier, locationIdentifier);
	}

	private static JsonRequestBPartnerLocationAndContact request(final String bpartnerIdentifier, final String locationIdentifier)
	{
		return JsonRequestBPartnerLocationAndContact.builder()
				.bPartnerIdentifier(bpartnerIdentifier)
				.bPartnerLocationIdentifier(locationIdentifier)
				.build();
	}

	private void mockBPartnerEndpointsForMetasfreshIdsOnly()
	{
		Mockito.doAnswer(invocation -> {
			final String bpartnerIdentifier = invocation.getArgument(1);
			if (!isMetasfreshId(bpartnerIdentifier))
			{
				return ResponseEntity.notFound().build();
			}
			final JsonResponseComposite composite = JsonResponseComposite.builder()
					.bpartner(JsonResponseBPartner.builder()
							.metasfreshId(JsonMetasfreshId.of(Integer.parseInt(bpartnerIdentifier)))
							.active(true).name("bp").vendor(false).customer(true).company(true)
							.build())
					.build();
			return ResponseEntity.ok(composite);
		}).when(bpartnerRestController).retrieveBPartner(any(), anyString());

		Mockito.doAnswer(invocation -> {
			final String bpartnerIdentifier = invocation.getArgument(1);
			final String locationIdentifier = invocation.getArgument(2);
			if (!isMetasfreshId(bpartnerIdentifier) || !isMetasfreshId(locationIdentifier))
			{
				return ResponseEntity.notFound().build();
			}
			final JsonResponseLocation location = JsonResponseLocation.builder()
					.metasfreshId(JsonMetasfreshId.of(Integer.parseInt(locationIdentifier)))
					.active(true)
					.build();
			return ResponseEntity.ok(location);
		}).when(bpartnerRestController).retrieveBPartnerLocation(any(), anyString(), anyString());
	}

	private static boolean isMetasfreshId(final String identifier)
	{
		return identifier.matches("^\\d+$");
	}

	private static BPartnerId createBPartner(final String value, final boolean active)
	{
		final I_C_BPartner bpartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		bpartner.setValue(value);
		bpartner.setName(value);
		bpartner.setIsActive(active);
		InterfaceWrapperHelper.saveRecord(bpartner);
		return BPartnerId.ofRepoId(bpartner.getC_BPartner_ID());
	}

	private static BPartnerLocationId createLocation(final BPartnerId bpartnerId, final String glnCode, final boolean active)
	{
		final I_C_BPartner_Location location = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class);
		location.setC_BPartner_ID(bpartnerId.getRepoId());
		location.setGLN(glnCode); // stored without the "gln-" identifier prefix
		location.setIsActive(active);
		InterfaceWrapperHelper.saveRecord(location);
		return BPartnerLocationId.ofRepoId(bpartnerId, location.getC_BPartner_Location_ID());
	}
}

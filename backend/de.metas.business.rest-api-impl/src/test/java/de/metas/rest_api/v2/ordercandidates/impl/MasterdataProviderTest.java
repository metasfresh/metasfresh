/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2025 metas GmbH
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

import de.metas.externalreference.rest.v2.ExternalReferenceRestControllerService;
import de.metas.externalsystem.ExternalSystemRepository;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.v2.bpartner.BPartnerMasterdataProvider;
import de.metas.rest_api.v2.bpartner.BpartnerRestController;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonRetrieverService;
import de.metas.security.permissions2.PermissionService;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.util.Services;
import de.metas.util.web.exception.InvalidIdentifierException;
import de.metas.util.web.exception.MissingResourceException;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_TaxCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MasterdataProviderTest
{
	private MasterdataProvider masterdataProvider;

	private TaxCategoryId transportTaxCategoryId;
	private TaxCategoryId inactiveTaxCategoryId;

	private Object parent;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final PermissionService permissionService = Mockito.mock(PermissionService.class);
		final BpartnerRestController bpartnerRestController = Mockito.mock(BpartnerRestController.class);
		final BPartnerMasterdataProvider bPartnerMasterdataProvider = Mockito.mock(BPartnerMasterdataProvider.class);
		final ExternalReferenceRestControllerService externalReferenceRestControllerService = Mockito.mock(ExternalReferenceRestControllerService.class);
		final JsonRetrieverService jsonRetrieverService = Mockito.mock(JsonRetrieverService.class);
		final ExternalSystemRepository externalSystemRepository = Mockito.mock(ExternalSystemRepository.class);

		masterdataProvider = MasterdataProvider.builder()
				.permissionService(permissionService)
				.bpartnerRestController(bpartnerRestController)
				.bPartnerMasterdataProvider(bPartnerMasterdataProvider)
				.externalReferenceRestControllerService(externalReferenceRestControllerService)
				.jsonRetrieverService(jsonRetrieverService)
				.externalSystemRepository(externalSystemRepository)
				.build();

		// Create test tax categories
		final I_C_TaxCategory transportRecord = newInstance(I_C_TaxCategory.class);
		transportRecord.setInternalName("Transport");
		transportRecord.setIsActive(true);
		saveRecord(transportRecord);
		transportTaxCategoryId = TaxCategoryId.ofRepoId(transportRecord.getC_TaxCategory_ID());

		final I_C_TaxCategory inactiveRecord = newInstance(I_C_TaxCategory.class);
		inactiveRecord.setInternalName("Inactive");
		inactiveRecord.setIsActive(false);
		saveRecord(inactiveRecord);
		inactiveTaxCategoryId = TaxCategoryId.ofRepoId(inactiveRecord.getC_TaxCategory_ID());

		parent = new Object();
	}

	@Test
	void getTaxCategoryId_byInternalName()
	{
		assertThat(masterdataProvider.getTaxCategoryId(IdentifierString.of("int-Transport"), parent))
				.isEqualTo(transportTaxCategoryId);
	}

	@Test
	void getTaxCategoryId_byMetasfreshId()
	{
		assertThat(masterdataProvider.getTaxCategoryId(
				IdentifierString.of(String.valueOf(transportTaxCategoryId.getRepoId())), parent))
				.isEqualTo(transportTaxCategoryId);
	}

	@Test
	void getTaxCategoryId_inactiveId_isRejected()
	{
		assertThatThrownBy(() -> masterdataProvider.getTaxCategoryId(
				IdentifierString.of(String.valueOf(inactiveTaxCategoryId.getRepoId())), parent))
				.isInstanceOf(MissingResourceException.class);
	}

	@Test
	void getTaxCategoryId_notFoundSentinelId_isRejected()
	{
		assertThatThrownBy(() -> masterdataProvider.getTaxCategoryId(IdentifierString.of("100"), parent))
				.isInstanceOf(MissingResourceException.class);
	}

	@Test
	void getTaxCategoryId_unsupportedForm_throwsInvalidIdentifier()
	{
		assertThatThrownBy(() -> masterdataProvider.getTaxCategoryId(IdentifierString.of("val-Transport"), parent))
				.isInstanceOf(InvalidIdentifierException.class);
	}
}

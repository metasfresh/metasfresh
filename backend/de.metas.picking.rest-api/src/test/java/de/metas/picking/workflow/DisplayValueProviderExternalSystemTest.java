/*
 * #%L
 * de.metas.picking.rest-api
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

package de.metas.picking.workflow;

import com.google.common.collect.ImmutableList;
import de.metas.document.location.RenderedAddressProvider;
import de.metas.externalsystem.ExternalSystem;
import de.metas.externalsystem.ExternalSystemCreateRequest;
import de.metas.externalsystem.ExternalSystemId;
import de.metas.externalsystem.ExternalSystemRepository;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfile;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.config.mobileui.PickingJobField;
import de.metas.handlingunits.picking.config.mobileui.PickingJobFieldType;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.PickingJobCandidateProducts;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobReference;
import de.metas.handlingunits.picking.job.service.external.bpartner.PickingJobBPartnerService;
import de.metas.organization.IOrgDAO;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Covers the EXTERNAL_SYSTEM picking-launcher field type: the caption shows the external
 * system's {@code Name}, it shows the same value once the job has been STARTED, and an order that came
 * in through no external system degrades to the remaining fields instead of rendering a placeholder.
 */
class DisplayValueProviderExternalSystemTest
{
	private ExternalSystemRepository externalSystemRepository;
	private ExternalSystem shopware;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		externalSystemRepository = ExternalSystemRepository.newInstanceForUnitTesting();
		shopware = externalSystemRepository.create(ExternalSystemCreateRequest.builder()
				.name("Shopware 6")
				.type(ExternalSystemType.Shopware6)
				.build());
	}

	private DisplayValueProvider displayValueProvider(final PickingJobFieldType... fieldTypes)
	{
		final IOrgDAO orgDAO = mock(IOrgDAO.class);
		final PickingJobBPartnerService bpartnerService = mock(PickingJobBPartnerService.class);
		when(bpartnerService.newRenderedAddressProvider()).thenReturn(mock(RenderedAddressProvider.class));

		final ImmutableList.Builder<PickingJobField> fields = ImmutableList.builder();
		int seqNo = 10;
		for (final PickingJobFieldType fieldType : fieldTypes)
		{
			fields.add(PickingJobField.builder().seqNo(seqNo).field(fieldType).isShowInSummary(true).isShowInDetailed(true).build());
			seqNo += 10;
		}

		return DisplayValueProvider.builder()
				.orgDAO(orgDAO)
				.bpartnerService(bpartnerService)
				.externalSystemRepository(externalSystemRepository)
				.profile(MobileUIPickingUserProfile.DEFAULT.toBuilder().fields(fields.build()).build())
				.build();
	}

	private static PickingJobCandidate candidate(@Nullable final ExternalSystemId externalSystemId)
	{
		return PickingJobCandidate.builder()
				.aggregationType(PickingJobAggregationType.SALES_ORDER)
				.salesOrderDocumentNo("SO1")
				.customerName("Acme")
				.externalSystemId(externalSystemId)
				.products(PickingJobCandidateProducts.newInstance())
				.build();
	}

	private static PickingJobReference startedJob(@Nullable final ExternalSystemId externalSystemId)
	{
		return PickingJobReference.builder()
				.pickingJobId(PickingJobId.ofRepoId(1))
				.aggregationType(PickingJobAggregationType.SALES_ORDER)
				.salesOrderDocumentNo("SO1")
				.customerName("Acme")
				.externalSystemId(externalSystemId)
				.products(PickingJobCandidateProducts.newInstance())
				.build();
	}

	/** AC2 — and the Name, not the "Shopware6" Value code. */
	@Test
	void notYetStarted_showsTheExternalSystemName()
	{
		final DisplayValueProvider provider = displayValueProvider(
				PickingJobFieldType.DOCUMENT_NO, PickingJobFieldType.CUSTOMER, PickingJobFieldType.EXTERNAL_SYSTEM);

		assertThat(provider.computeLauncherCaption(candidate(shopware.getId())).translate("en_US"))
				.isEqualTo("SO1 | Acme | Shopware 6");
	}

	/**
	 * AC7 — starting a job must not blank or change the value. The two halves of the launcher list read
	 * from different sources ({@code M_Packageable_V} vs {@code M_Picking_Job}), which is exactly where a
	 * value like this goes missing.
	 */
	@Test
	void alreadyStarted_showsTheSameExternalSystemName()
	{
		final DisplayValueProvider provider = displayValueProvider(
				PickingJobFieldType.DOCUMENT_NO, PickingJobFieldType.CUSTOMER, PickingJobFieldType.EXTERNAL_SYSTEM);

		assertThat(provider.computeLauncherCaption(startedJob(shopware.getId())).translate("en_US"))
				.isEqualTo("SO1 | Acme | Shopware 6");
	}

	/** AC8 — no external system means no text and no placeholder, exactly as other blank fields behave. */
	@Test
	void noExternalSystem_rendersNoPlaceholder()
	{
		final DisplayValueProvider provider = displayValueProvider(
				PickingJobFieldType.DOCUMENT_NO, PickingJobFieldType.CUSTOMER, PickingJobFieldType.EXTERNAL_SYSTEM);

		assertThat(provider.computeLauncherCaption(candidate(null)).translate("en_US"))
				.isEqualTo("SO1 | Acme");
	}

	/** AC9 — a profile that does not enable the field is untouched by it. */
	@Test
	void fieldNotConfigured_captionIsUnchanged()
	{
		final DisplayValueProvider provider = displayValueProvider(
				PickingJobFieldType.DOCUMENT_NO, PickingJobFieldType.CUSTOMER);

		assertThat(provider.computeLauncherCaption(candidate(shopware.getId())).translate("en_US"))
				.isEqualTo("SO1 | Acme");
	}
}

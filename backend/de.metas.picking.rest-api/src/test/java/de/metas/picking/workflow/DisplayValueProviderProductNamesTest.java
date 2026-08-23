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
import de.metas.business.BusinessTestHelper;
import de.metas.document.location.RenderedAddressProvider;
import de.metas.externalsystem.ExternalSystemRepository;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfile;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.config.mobileui.PickingJobField;
import de.metas.handlingunits.picking.config.mobileui.PickingJobFieldType;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.PickingJobCandidateProduct;
import de.metas.handlingunits.picking.job.model.PickingJobCandidateProducts;
import de.metas.handlingunits.picking.job.service.external.bpartner.PickingJobBPartnerService;
import de.metas.i18n.TranslatableStrings;
import de.metas.organization.IOrgDAO;
import de.metas.product.ProductId;
import de.metas.product.ProductValueAndName;
import de.metas.quantity.Quantity;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Covers the {@code PRODUCT_NAMES} launcher field type: a job-list
 * caption listing every product of a multi-product job, joined by {@code ", "}, while the existing
 * {@code PRODUCT_NAME} / {@code QTY_TO_DELIVER} fields stay blank for a multi-product job as before.
 */
class DisplayValueProviderProductNamesTest
{
	private I_C_UOM each;
	private DisplayValueProvider displayValueProvider;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		each = BusinessTestHelper.createUomEach();
	}

	private DisplayValueProvider displayValueProvider(final PickingJobFieldType productFieldType)
	{
		final IOrgDAO orgDAO = mock(IOrgDAO.class);
		final PickingJobBPartnerService bpartnerService = mock(PickingJobBPartnerService.class);
		when(bpartnerService.newRenderedAddressProvider()).thenReturn(mock(RenderedAddressProvider.class));

		final MobileUIPickingUserProfile profile = MobileUIPickingUserProfile.DEFAULT.toBuilder()
				.fields(ImmutableList.of(
						PickingJobField.builder().seqNo(10).field(PickingJobFieldType.DOCUMENT_NO).isShowInSummary(true).isShowInDetailed(true).build(),
						PickingJobField.builder().seqNo(20).field(PickingJobFieldType.CUSTOMER).isShowInSummary(true).isShowInDetailed(true).build(),
						PickingJobField.builder().seqNo(30).field(productFieldType).isShowInSummary(true).isShowInDetailed(true).build(),
						PickingJobField.builder().seqNo(40).field(PickingJobFieldType.QTY_TO_DELIVER).isShowInSummary(true).isShowInDetailed(true).build()
				))
				.build();

		return DisplayValueProvider.builder()
				.orgDAO(orgDAO)
				.bpartnerService(bpartnerService)
				.externalSystemRepository(ExternalSystemRepository.newInstanceForUnitTesting())
				.profile(profile)
				.build();
	}

	private PickingJobCandidateProduct product(final int productRepoId, final String name, final String qtyToDeliver)
	{
		final ProductId productId = ProductId.ofRepoId(productRepoId);
		return PickingJobCandidateProduct.builder()
				.productId(productId)
				.productValueAndName(ProductValueAndName.of("value-" + productRepoId, TranslatableStrings.anyLanguage(name)))
				.qtyToDeliver(qtyToDeliver != null ? Quantity.of(qtyToDeliver, each) : null)
				.build();
	}

	private PickingJobCandidate candidate(final PickingJobCandidateProduct... products)
	{
		return PickingJobCandidate.builder()
				.aggregationType(PickingJobAggregationType.SALES_ORDER)
				.salesOrderDocumentNo("SO1")
				.customerName("Acme")
				.products(PickingJobCandidateProducts.ofList(ImmutableList.copyOf(products)))
				.build();
	}

	@Test
	void multiProduct_productNames_joinsAllNamesInOrder()
	{
		final DisplayValueProvider provider = displayValueProvider(PickingJobFieldType.PRODUCT_NAMES);
		final PickingJobCandidate candidate = candidate(
				product(1, "ProductA", null),
				product(2, "ProductB", null),
				product(3, "ProductC", null));

		final String caption = provider.computeLauncherCaption(candidate).translate("en_US");

		assertThat(caption).isEqualTo("SO1 | Acme | ProductA, ProductB, ProductC");
	}

	@Test
	void singleProduct_productNames_isExactlyThatName()
	{
		final DisplayValueProvider provider = displayValueProvider(PickingJobFieldType.PRODUCT_NAMES);
		final PickingJobCandidate candidate = candidate(product(1, "OnlyProduct", null));

		final String caption = provider.computeLauncherCaption(candidate).translate("en_US");

		assertThat(caption).isEqualTo("SO1 | Acme | OnlyProduct");
	}

	@Test
	void multiProduct_existingProductNameField_staysBlank()
	{
		final DisplayValueProvider provider = displayValueProvider(PickingJobFieldType.PRODUCT_NAME);
		final PickingJobCandidate candidate = candidate(
				product(1, "ProductA", null),
				product(2, "ProductB", null),
				product(3, "ProductC", null));

		final String caption = provider.computeLauncherCaption(candidate).translate("en_US");

		assertThat(caption).isEqualTo("SO1 | Acme");
	}

	@Test
	void multiProduct_qtyToDeliver_staysBlankEvenWhenEachProductHasAQty()
	{
		final DisplayValueProvider provider = displayValueProvider(PickingJobFieldType.PRODUCT_NAMES);
		final PickingJobCandidate candidate = candidate(
				product(1, "ProductA", "5"),
				product(2, "ProductB", "10"),
				product(3, "ProductC", "3"));

		final String caption = provider.computeLauncherCaption(candidate).translate("en_US");

		assertThat(caption).isEqualTo("SO1 | Acme | ProductA, ProductB, ProductC");
	}
}

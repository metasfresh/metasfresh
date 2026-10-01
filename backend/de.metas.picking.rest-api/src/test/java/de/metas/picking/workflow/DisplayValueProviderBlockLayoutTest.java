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
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.business.BusinessTestHelper;
import de.metas.document.location.RenderedAddressProvider;
import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfile;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.config.mobileui.PickingJobField;
import de.metas.handlingunits.picking.config.mobileui.PickingJobFieldType;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.PickingJobCandidateProduct;
import de.metas.handlingunits.picking.job.model.PickingJobCandidateProducts;
import de.metas.handlingunits.picking.job.model.PickingJobDocStatus;
import de.metas.handlingunits.picking.job.model.PickingJobHeader;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.handlingunits.picking.job.model.PickingUnit;
import de.metas.handlingunits.picking.job.service.external.bpartner.PickingJobBPartnerService;
import de.metas.i18n.TranslatableStrings;
import de.metas.inout.ShipmentScheduleId;
import de.metas.order.OrderAndLineId;
import de.metas.organization.IOrgDAO;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.product.ProductValueAndName;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Covers {@code PickingJobField.isBlockLayout()} on the {@code PRODUCT_NAMES} launcher field type: with the
 * switch on, a job-list caption renders each product name on its own line instead of joining them with
 * {@code ", "} — regardless of how many products the job holds; with the switch off, the list caption is
 * unchanged from {@link DisplayValueProviderProductNamesTest}; the job-detail header
 * ({@link DisplayValueProviderProductNamesDetailTest}) keeps {@code ", "} whatever the switch is set to.
 */
class DisplayValueProviderBlockLayoutTest
{
	private I_C_UOM each;
	private int nextRepoId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		each = BusinessTestHelper.createUomEach();
		nextRepoId = 1;
	}

	private DisplayValueProvider displayValueProvider(final boolean blockLayout)
	{
		final IOrgDAO orgDAO = mock(IOrgDAO.class);
		final PickingJobBPartnerService bpartnerService = mock(PickingJobBPartnerService.class);
		when(bpartnerService.newRenderedAddressProvider()).thenReturn(mock(RenderedAddressProvider.class));

		final MobileUIPickingUserProfile profile = MobileUIPickingUserProfile.DEFAULT.toBuilder()
				.fields(ImmutableList.of(
						PickingJobField.builder().seqNo(10).field(PickingJobFieldType.DOCUMENT_NO).isShowInSummary(true).isShowInDetailed(true).build(),
						PickingJobField.builder().seqNo(20).field(PickingJobFieldType.CUSTOMER).isShowInSummary(true).isShowInDetailed(true).build(),
						PickingJobField.builder().seqNo(30).field(PickingJobFieldType.PRODUCT_NAMES).isShowInSummary(true).isShowInDetailed(true).isBlockLayout(blockLayout).build(),
						PickingJobField.builder().seqNo(40).field(PickingJobFieldType.QTY_TO_DELIVER).isShowInSummary(true).isShowInDetailed(true).build()
				))
				.build();

		return DisplayValueProvider.builder()
				.orgDAO(orgDAO)
				.bpartnerService(bpartnerService)
				.profile(profile)
				.build();
	}

	private PickingJobCandidateProduct product(final int productRepoId, final String name)
	{
		final ProductId productId = ProductId.ofRepoId(productRepoId);
		return PickingJobCandidateProduct.builder()
				.productId(productId)
				.productValueAndName(ProductValueAndName.of("value-" + productRepoId, TranslatableStrings.anyLanguage(name)))
				.qtyToDeliver(null)
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
	void blockLayoutOn_multiProduct_list_rendersEachProductOnItsOwnLine()
	{
		final DisplayValueProvider provider = displayValueProvider(true);
		final PickingJobCandidate candidate = candidate(
				product(1, "ProductA"),
				product(2, "ProductB"),
				product(3, "ProductC"));

		final String caption = provider.computeLauncherCaption(candidate).translate("en_US");

		assertThat(caption).isEqualTo("SO1 | Acme\nProductA\nProductB\nProductC");
	}

	@Test
	void blockLayoutOn_singleProduct_list_rendersItsOwnLineToo()
	{
		// deliberately no ">1 product" condition: a one-product job still forms its own block
		final DisplayValueProvider provider = displayValueProvider(true);
		final PickingJobCandidate candidate = candidate(product(1, "OnlyProduct"));

		final String caption = provider.computeLauncherCaption(candidate).translate("en_US");

		assertThat(caption).isEqualTo("SO1 | Acme\nOnlyProduct");
	}

	@Test
	void blockLayoutOff_multiProduct_list_rendersExactlyAsToday()
	{
		final DisplayValueProvider provider = displayValueProvider(false);
		final PickingJobCandidate candidate = candidate(
				product(1, "ProductA"),
				product(2, "ProductB"),
				product(3, "ProductC"));

		final String caption = provider.computeLauncherCaption(candidate).translate("en_US");

		assertThat(caption).isEqualTo("SO1 | Acme | ProductA, ProductB, ProductC");
	}

	@Test
	void blockLayoutOff_singleProduct_list_rendersExactlyAsToday()
	{
		final DisplayValueProvider provider = displayValueProvider(false);
		final PickingJobCandidate candidate = candidate(product(1, "OnlyProduct"));

		final String caption = provider.computeLauncherCaption(candidate).translate("en_US");

		assertThat(caption).isEqualTo("SO1 | Acme | OnlyProduct");
	}

	//
	// Detail path (job header) — stays ", " whatever the switch is set to.
	//

	private PickingJobField productNamesField(final boolean blockLayout)
	{
		return PickingJobField.builder().seqNo(10).field(PickingJobFieldType.PRODUCT_NAMES).isShowInSummary(false).isShowInDetailed(true).isBlockLayout(blockLayout).build();
	}

	private PickingJobLine line(final int productRepoId, final String name)
	{
		final int lineRepoId = nextRepoId++;
		final ProductId productId = ProductId.ofRepoId(productRepoId);

		return PickingJobLine.builder()
				.id(PickingJobLineId.ofRepoId(lineRepoId))
				.caption(TranslatableStrings.anyLanguage("line-" + lineRepoId))
				.productId(productId)
				.productNo("value-" + productRepoId)
				.productCategoryId(ProductCategoryId.ofRepoId(1))
				.productValueAndName(ProductValueAndName.of("value-" + productRepoId, TranslatableStrings.anyLanguage(name)))
				.packingInfo(HUPIItemProduct.builder()
						.id(HUPIItemProductId.VIRTUAL_HU)
						.name(TranslatableStrings.anyLanguage("packing"))
						.piItemId(HuPackingInstructionsItemId.VIRTUAL)
						.build())
				.qtyToPick(de.metas.quantity.Quantity.of("1", each))
				.salesOrderAndLineId(OrderAndLineId.ofRepoIds(1, lineRepoId))
				.salesOrderDocumentNo("SO1")
				.orderLineSeqNo(lineRepoId * 10)
				.deliveryBPLocationId(BPartnerLocationId.ofRepoId(1, 1))
				.scheduleId(ShipmentScheduleAndJobScheduleId.ofShipmentScheduleId(ShipmentScheduleId.ofRepoId(lineRepoId)))
				.steps(ImmutableList.of())
				.pickingUnit(PickingUnit.CU)
				.build();
	}

	private PickingJob job(final PickingJobLine... lines)
	{
		return PickingJob.builder()
				.id(PickingJobId.ofRepoId(1))
				.header(PickingJobHeader.builder()
						.aggregationType(PickingJobAggregationType.SALES_ORDER)
						.salesOrderDocumentNo("SO1")
						.customerName("Acme")
						.build())
				.lines(ImmutableList.copyOf(lines))
				.pickFromAlternatives(ImmutableSet.of())
				.docStatus(PickingJobDocStatus.Drafted)
				.build();
	}

	private DisplayValueProvider displayValueProviderForDetail()
	{
		final IOrgDAO orgDAO = mock(IOrgDAO.class);
		final PickingJobBPartnerService bpartnerService = mock(PickingJobBPartnerService.class);
		when(bpartnerService.newRenderedAddressProvider()).thenReturn(mock(RenderedAddressProvider.class));

		return DisplayValueProvider.builder()
				.orgDAO(orgDAO)
				.bpartnerService(bpartnerService)
				.profile(MobileUIPickingUserProfile.DEFAULT)
				.build();
	}

	@Test
	void blockLayoutOn_detail_stillJoinsWithComma()
	{
		final DisplayValueProvider provider = displayValueProviderForDetail();
		final PickingJobLine lineA = line(1, "ProductA");
		final PickingJobLine lineB = line(2, "ProductB");
		final PickingJob job = job(lineA, lineB);

		final String caption = provider.getDisplayValue(productNamesField(true), job).translate("en_US");

		assertThat(caption).isEqualTo("ProductA, ProductB");
	}
}

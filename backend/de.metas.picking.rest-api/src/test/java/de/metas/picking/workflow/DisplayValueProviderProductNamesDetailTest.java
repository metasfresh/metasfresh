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
import de.metas.externalsystem.ExternalSystemRepository;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfile;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.config.mobileui.PickingJobField;
import de.metas.handlingunits.picking.config.mobileui.PickingJobFieldType;
import de.metas.handlingunits.picking.job.model.PickingJob;
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
import de.metas.quantity.Quantity;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Covers the {@code PRODUCT_NAMES} launcher field type on the DETAIL surfaces: the started-job
 * header names every distinct product of the job (deduplicated by {@code ProductId}, first-occurrence order),
 * while an opened line names only its own product, never the job's others.
 */
class DisplayValueProviderProductNamesDetailTest
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

	private DisplayValueProvider displayValueProvider()
	{
		final IOrgDAO orgDAO = mock(IOrgDAO.class);
		final PickingJobBPartnerService bpartnerService = mock(PickingJobBPartnerService.class);
		when(bpartnerService.newRenderedAddressProvider()).thenReturn(mock(RenderedAddressProvider.class));

		return DisplayValueProvider.builder()
				.orgDAO(orgDAO)
				.bpartnerService(bpartnerService)
				.externalSystemRepository(ExternalSystemRepository.newInstanceForUnitTesting())
				.profile(MobileUIPickingUserProfile.DEFAULT)
				.build();
	}

	private PickingJobField productNamesField()
	{
		return PickingJobField.builder().seqNo(10).field(PickingJobFieldType.PRODUCT_NAMES).isShowInSummary(false).isShowInDetailed(true).build();
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
				.qtyToPick(Quantity.of("1", each))
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

	@Nested
	class JobProductNames
	{
		@Test
		void namesEachDistinctProductOnceInFirstOccurrenceOrder()
		{
			final DisplayValueProvider provider = displayValueProvider();
			final PickingJobLine lineA1 = line(1, "ProductA");
			final PickingJobLine lineB = line(2, "ProductB");
			final PickingJobLine lineA2 = line(1, "ProductA"); // same product again, on a different line
			final PickingJob job = job(lineA1, lineB, lineA2);

			final String caption = provider.getDisplayValue(productNamesField(), job).translate("en_US");

			assertThat(caption).isEqualTo("ProductA, ProductB");
		}

		@Test
		void namesTwoDistinctProductsSharingANameTwice()
		{
			final DisplayValueProvider provider = displayValueProvider();
			// two genuinely different products that happen to carry the same name:
			// deduplication is by ProductId, so neither may swallow the other
			final PickingJobLine lineA = line(1, "Gouda");
			final PickingJobLine lineB = line(2, "Gouda");
			final PickingJob job = job(lineA, lineB);

			final String caption = provider.getDisplayValue(productNamesField(), job).translate("en_US");

			assertThat(caption).isEqualTo("Gouda, Gouda");
		}
	}

	@Test
	void line_productNames_namesOnlyItsOwnProduct()
	{
		final DisplayValueProvider provider = displayValueProvider();
		// a line resolves its own product independently of any job it belongs to,
		// so no job is constructed here
		final PickingJobLine lineA = line(1, "ProductA");
		final PickingJobLine lineB = line(2, "ProductB");

		final String captionA = provider.getDisplayValue(productNamesField(), lineA).translate("en_US");
		final String captionB = provider.getDisplayValue(productNamesField(), lineB).translate("en_US");

		assertThat(captionA).isEqualTo("ProductA");
		assertThat(captionB).isEqualTo("ProductB");
	}

	@Test
	void neitherJobNorLineCallThrows()
	{
		final DisplayValueProvider provider = displayValueProvider();
		final PickingJobLine lineA = line(1, "ProductA");
		final PickingJobLine lineB = line(2, "ProductB");
		final PickingJob job = job(lineA, lineB);

		assertThatCode(() -> provider.getDisplayValue(productNamesField(), job)).doesNotThrowAnyException();
		assertThatCode(() -> provider.getDisplayValue(productNamesField(), lineA)).doesNotThrowAnyException();
	}
}

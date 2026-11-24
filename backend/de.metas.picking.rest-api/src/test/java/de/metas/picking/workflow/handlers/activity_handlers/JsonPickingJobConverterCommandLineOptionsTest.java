package de.metas.picking.workflow.handlers.activity_handlers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.ad_reference.ADRefList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.picking.config.mobileui.AllowedPickToStructures;
import de.metas.handlingunits.picking.config.mobileui.CUQtyEntryMode;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfile;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.config.mobileui.PickingJobOptions;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.model.PickingJobLineId;
import de.metas.handlingunits.picking.job.model.PickingUnit;
import de.metas.handlingunits.picking.job.service.CreateShipmentPolicy;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
import de.metas.handlingunits.picking.job.service.external.product.PickingJobProductService;
import de.metas.i18n.TranslatableStrings;
import de.metas.order.OrderAndLineId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleId;
import de.metas.picking.rest_api.json.JsonPickingJob;
import de.metas.picking.rest_api.json.JsonPickingJobLine;
import de.metas.picking.workflow.DisplayValueProvider;
import de.metas.picking.workflow.DisplayValueProviderService;
import de.metas.picking.workflow.PickingJobRestService;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.product.ProductValueAndName;
import de.metas.quantity.Quantity;
import de.metas.scannable_code.format.ScannableCodeFormatId;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The CU quantity entry mode / label format of a picking job line comes from the options of the line's OWN customer
 * ({@code PickingJobRestService.getPickingJobOptions(line customer)}), not from the job header's options.
 */
class JsonPickingJobConverterCommandLineOptionsTest
{
	private static final BPartnerId HEADER_CUSTOMER = BPartnerId.ofRepoId(1);
	private static final BPartnerId CUSTOMER_A = BPartnerId.ofRepoId(2);
	private static final BPartnerId CUSTOMER_B = BPartnerId.ofRepoId(3);
	private static final ScannableCodeFormatId FORMAT_A = ScannableCodeFormatId.ofRepoId(901);
	private static final ScannableCodeFormatId FORMAT_HEADER = ScannableCodeFormatId.ofRepoId(902);

	private I_C_UOM uom;
	private PickingJobRestService pickingJobRestService;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		uom = newInstance(I_C_UOM.class);
		uom.setUOMSymbol("Stk");
		saveRecord(uom);

		pickingJobRestService = mock(PickingJobRestService.class);
		final MobileUIPickingUserProfile profile = mock(MobileUIPickingUserProfile.class);
		when(profile.getDetailFieldsInOrder()).thenReturn(ImmutableList.of());
		when(pickingJobRestService.getProfile()).thenReturn(profile);
		when(pickingJobRestService.getQtyRejectedReasons()).thenReturn(mock(ADRefList.class));
		when(pickingJobRestService.getPickingJobOptions(HEADER_CUSTOMER)).thenReturn(options(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY, FORMAT_HEADER));
		when(pickingJobRestService.getPickingJobOptions(CUSTOMER_A)).thenReturn(options(CUQtyEntryMode.SCAN_EACH_CU, FORMAT_A));
		when(pickingJobRestService.getPickingJobOptions(CUSTOMER_B)).thenReturn(options(CUQtyEntryMode.MANUAL, null));
	}

	private static PickingJobOptions options(@Nullable final CUQtyEntryMode mode, @Nullable final ScannableCodeFormatId formatId)
	{
		return PickingJobOptions.builder()
				.allowedPickToStructures(AllowedPickToStructures.EMPTY)
				.createShipmentPolicy(CreateShipmentPolicy.DO_NOT_CREATE)
				.cuQtyEntryMode(mode)
				.cuLabelFormatId(formatId)
				.build();
	}

	private PickingJobLine line(final int id, final BPartnerId customerId)
	{
		return PickingJobLine.builder()
				.id(PickingJobLineId.ofRepoId(id))
				.caption(TranslatableStrings.constant("line " + id))
				.productId(ProductId.ofRepoId(10))
				.productNo("P-1")
				.productCategoryId(ProductCategoryId.ofRepoId(11))
				.productValueAndName(ProductValueAndName.of("P-1", TranslatableStrings.constant("product")))
				.packingInfo(HUPIItemProduct.builder()
						.id(HUPIItemProductId.VIRTUAL_HU)
						.name(TranslatableStrings.constant("virtual"))
						.piItemId(HuPackingInstructionsItemId.ofRepoId(540001))
						.build())
				.qtyToPick(Quantity.of(BigDecimal.valueOf(8), uom))
				.salesOrderAndLineId(OrderAndLineId.ofRepoIds(20, 20 + id))
				.salesOrderDocumentNo("SO-" + id)
				.orderLineSeqNo(id)
				.deliveryBPLocationId(BPartnerLocationId.ofRepoId(customerId, 100 + id))
				.scheduleId(ShipmentScheduleAndJobScheduleId.ofRepoIds(40 + id, 0))
				.steps(ImmutableList.of())
				.pickingUnit(PickingUnit.CU)
				.build();
	}

	private JsonPickingJob convert(final PickingJobLine... lines)
	{
		final PickingJob pickingJob = mock(PickingJob.class);
		when(pickingJob.getCustomerId()).thenReturn(HEADER_CUSTOMER);
		when(pickingJob.getAggregationType()).thenReturn(PickingJobAggregationType.PRODUCT);
		when(pickingJob.getProgress()).thenReturn(de.metas.handlingunits.picking.job.model.PickingJobProgress.NOT_STARTED);
		when(pickingJob.getPickFromHU()).thenReturn(Optional.empty());
		when(pickingJob.getLuPickingTarget(any())).thenReturn(Optional.empty());
		when(pickingJob.getTuPickingTarget(any())).thenReturn(Optional.empty());
		when(pickingJob.getPickFromAlternatives()).thenReturn(ImmutableSet.of());
		when(pickingJob.getLines()).thenReturn(ImmutableList.copyOf(lines));
		when(pickingJob.streamLines()).thenAnswer(invocation -> ImmutableList.copyOf(lines).stream());

		final PickingJobProductService productService = mock(PickingJobProductService.class);
		when(productService.getUOMSymbolById(any())).thenReturn(TranslatableStrings.constant("Stk"));

		final DisplayValueProviderService displayValueProviderService = mock(DisplayValueProviderService.class);
		when(displayValueProviderService.newDisplayValueProvider(any())).thenReturn(mock(DisplayValueProvider.class));

		return JsonPickingJobConverterCommand.builder()
				.productService(productService)
				.huService(mock(PickingJobHUService.class))
				.pickingJobRestService(pickingJobRestService)
				.displayValueProviderService(displayValueProviderService)
				.pickingJob(pickingJob)
				.jsonOpts(JsonOpts.ofAdLanguage("en_US"))
				.build()
				.execute();
	}

	@Test
	void eachLineGetsTheModeAndFormatOfItsOwnCustomer_notOfTheJobHeader()
	{
		final JsonPickingJob json = convert(line(1, CUSTOMER_A), line(2, CUSTOMER_B));

		final JsonPickingJobLine lineA = json.getLines().get(0);
		final JsonPickingJobLine lineB = json.getLines().get(1);
		assertThat(lineA.getCuQtyEntryMode()).isEqualTo(CUQtyEntryMode.SCAN_EACH_CU.getCode());
		assertThat(lineA.getCuQtyEntryPolicy().isLabelRequired()).isTrue();
		assertThat(lineA.getCuQtyEntryPolicy().isPackIdRequired()).isTrue();
		assertThat(lineA.getCuQtyEntryPolicy().isScanOnce()).isFalse();
		assertThat(lineA.getCuLabelFormatId()).isEqualTo(FORMAT_A);
		assertThat(lineB.getCuQtyEntryMode()).isEqualTo(CUQtyEntryMode.MANUAL.getCode());
		assertThat(lineB.getCuQtyEntryPolicy().isLabelRequired()).isFalse();
		assertThat(lineB.getCuLabelFormatId()).isNull();
	}
}

package de.metas.handlingunits.picking.job.repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileService;
import de.metas.handlingunits.picking.job.service.PickingJobLockService;
import de.metas.handlingunits.picking.job.service.PickingJobSlotService;
import de.metas.handlingunits.picking.job.service.external.bpartner.PickingJobBPartnerService;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
import de.metas.handlingunits.picking.job.service.external.product.PickingJobProductService;
import de.metas.handlingunits.picking.job.service.external.salesorder.PickingJobSalesOrderService;
import de.metas.handlingunits.picking.job.service.external.warehouse.PickingJobWarehouseService;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodePackingInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUnitType;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUniqueId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Verifies that {@link DefaultPickingJobLoaderSupportingServices} resolves picked-HU QR codes through a single
 * batch warm-up (one round-trip for N HUs) instead of one lookup per HU — the fix for the picking write-path
 * QR-code N+1 (me03 #30802).
 */
@ExtendWith({ AdempiereTestWatcher.class })
class DefaultPickingJobLoaderSupportingServicesTest
{
	private PickingJobHUService huService;
	private DefaultPickingJobLoaderSupportingServices loaderServices;

	private final HuId hu1 = HuId.ofRepoId(101);
	private final HuId hu2 = HuId.ofRepoId(102);
	private final HUQRCode qr1 = newQRCode();
	private final HUQRCode qr2 = newQRCode();

	private static HUQRCode newQRCode()
	{
		return HUQRCode.builder()
				.id(HUQRCodeUniqueId.random())
				.packingInfo(HUQRCodePackingInfo.builder()
						.huUnitType(HUQRCodeUnitType.TU)
						.packingInstructionsId(HuPackingInstructionsId.ofRepoId(540))
						.caption("test")
						.build())
				.attributes(ImmutableList.of())
				.build();
	}

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		huService = mock(PickingJobHUService.class);

		loaderServices = DefaultPickingJobLoaderSupportingServices.builder()
				.orderService(mock(PickingJobSalesOrderService.class))
				.warehouseService(mock(PickingJobWarehouseService.class))
				.bpartnerService(mock(PickingJobBPartnerService.class))
				.productService(mock(PickingJobProductService.class))
				.pickingSlotService(mock(PickingJobSlotService.class))
				.pickingJobLockService(mock(PickingJobLockService.class))
				.huService(huService)
				.profileService(mock(MobileUIPickingUserProfileService.class))
				.build();
	}

	@Test
	void warmUp_then_getQRCode_servesFromCache_withoutPerHuLookup()
	{
		when(huService.getSingleQRCodeByHuIds(any()))
				.thenReturn(ImmutableMap.of(hu1, qr1, hu2, qr2));

		loaderServices.warmUpQRCodesCache(ImmutableSet.of(hu1, hu2));

		assertThat(loaderServices.getQRCodeByHUId(hu1)).isSameAs(qr1);
		assertThat(loaderServices.getQRCodeByHUId(hu2)).isSameAs(qr2);

		// the warm-up must batch-load exactly once ...
		verify(huService, times(1)).getSingleQRCodeByHuIds(any());
		// ... and the per-HU accessor must NOT fall back to a single lookup for already-warmed HUs
		verify(huService, never()).getQRCodeByHuId(any());
	}

	@Test
	void getQRCode_cacheMiss_fallsBackToSingleLookupOnce()
	{
		final HuId huNotWarmed = HuId.ofRepoId(999);
		// the batch omits this HU (as it would for an HU with no / multiple assigned QR codes)
		when(huService.getSingleQRCodeByHuIds(any())).thenReturn(ImmutableMap.of());
		when(huService.getQRCodeByHuId(huNotWarmed)).thenReturn(qr1);

		loaderServices.warmUpQRCodesCache(ImmutableSet.of(huNotWarmed));

		// first access falls back to the single-HU lookup (preserving generate-if-missing semantics) ...
		assertThat(loaderServices.getQRCodeByHUId(huNotWarmed)).isSameAs(qr1);
		// ... and the fallback result is itself cached, so a second access does NOT re-query
		assertThat(loaderServices.getQRCodeByHUId(huNotWarmed)).isSameAs(qr1);
		verify(huService, times(1)).getQRCodeByHuId(huNotWarmed);
	}
}

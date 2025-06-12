package de.metas.frontend_testing.masterdata;

import de.metas.currency.CurrencyRepository;
import de.metas.distribution.config.MobileUIDistributionConfigRepository;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.frontend_testing.expectations.AssertExpectationsCommandServices;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileRepository;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.mobile.MobileConfigService;
import de.metas.product.ProductRepository;
import de.metas.scannable_code.format.service.ScannableCodeFormatService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateMasterdataCommandSupportingServices
{
	@NonNull public final ProductRepository productRepository;
	@NonNull public final MobileConfigService mobileConfigService;
	@NonNull public final MobileUIPickingUserProfileRepository mobilePickingConfigRepository;
	@NonNull public final MobileUIDistributionConfigRepository mobileDistributionConfigRepository;
	@NonNull public final InventoryService inventoryService;
	@NonNull public final HUQRCodesService huQRCodesService;
	@NonNull public final CurrencyRepository currencyRepository;
	@NonNull public final DDOrderService ddOrderService;
	@NonNull public final ScannableCodeFormatService scannableCodeFormatService;
	@NonNull public final AssertExpectationsCommandServices assertExpectationsCommandServices;
}

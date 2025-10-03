package org.adempiere.warehouse.qrcode.resolver.global_qr_code;

import de.metas.global_qrcodes.GlobalQRCode;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.adempiere.warehouse.qrcode.resolver.LocatorGlobalQRCodeResolverKey;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolveContext;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolverResult;
import org.springframework.stereotype.Component;

@Component
public class LocatorQRCodeResolver implements LocatorGlobalQRCodeResolver
{
	private static final LocatorGlobalQRCodeResolverKey RESOLVER_KEY = LocatorGlobalQRCodeResolverKey.ofClass(LocatorQRCodeResolver.class);

	@Override
	public LocatorScannedCodeResolverResult resolve(final @NonNull GlobalQRCode scannedCode, final @NonNull LocatorScannedCodeResolveContext context)
	{
		if (!LocatorQRCode.isTypeMatching(scannedCode))
		{
			return LocatorScannedCodeResolverResult.notFound(RESOLVER_KEY, "Not a locator QR code");
		}

		final LocatorQRCode locatorQRCode = LocatorQRCode.ofGlobalQRCode(scannedCode);
		return resolveLocatorQRCode(locatorQRCode, context);
	}

	private LocatorScannedCodeResolverResult resolveLocatorQRCode(@NonNull final LocatorQRCode locatorQRCode, @NonNull final LocatorScannedCodeResolveContext context)
	{
		final LocatorId locatorId = locatorQRCode.getLocatorId();
		return context.isMatching(locatorId)
				? LocatorScannedCodeResolverResult.found(locatorId)
				: LocatorScannedCodeResolverResult.notFound(RESOLVER_KEY, "Locator is not matching the context warehouses");
	}

}

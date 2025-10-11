package org.adempiere.warehouse.qrcode.resolver.global_qr_code;

import de.metas.global_qrcodes.GlobalQRCode;
import lombok.NonNull;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolveContext;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolverResult;

public interface LocatorGlobalQRCodeResolver
{
	LocatorScannedCodeResolverResult resolve(@NonNull final GlobalQRCode scannedCode, @NonNull final LocatorScannedCodeResolveContext context);
}

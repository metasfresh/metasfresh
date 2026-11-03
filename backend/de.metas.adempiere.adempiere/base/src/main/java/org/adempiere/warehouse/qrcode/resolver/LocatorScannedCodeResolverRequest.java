package org.adempiere.warehouse.qrcode.resolver;

import de.metas.scannable_code.ScannedCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class LocatorScannedCodeResolverRequest
{
	@NonNull ScannedCode scannedCode;
	@NonNull @Builder.Default LocatorScannedCodeResolveContext context = LocatorScannedCodeResolveContext.NO_CONTEXT;

	public static LocatorScannedCodeResolverRequest ofScannedCode(@NonNull final ScannedCode scannedCode)
	{
		return builder().scannedCode(scannedCode).build();
	}
}

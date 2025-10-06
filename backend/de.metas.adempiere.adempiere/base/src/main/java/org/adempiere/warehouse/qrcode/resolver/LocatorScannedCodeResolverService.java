package org.adempiere.warehouse.qrcode.resolver;

import com.google.common.collect.ImmutableList;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.logging.LogManager;
import de.metas.scannable_code.ScannedCode;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.adempiere.warehouse.qrcode.resolver.global_qr_code.LocatorGlobalQRCodeResolver;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LocatorScannedCodeResolverService
{
	@NonNull private static final Logger logger = LogManager.getLogger(LocatorScannedCodeResolverService.class);
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull private final ImmutableList<LocatorGlobalQRCodeResolver> globalQRCodeResolvers;

	@NonNull private static final LocatorGlobalQRCodeResolverKey RESOLVER_KEY_resolveByLocatorValue = LocatorGlobalQRCodeResolverKey.ofString("resolveByLocatorValue");

	public LocatorScannedCodeResolverService(
			@NonNull final Optional<List<LocatorGlobalQRCodeResolver>> globalQRCodeResolvers
	)
	{
		this.globalQRCodeResolvers = globalQRCodeResolvers.map(ImmutableList::copyOf).orElseGet(ImmutableList::of);
		logger.info("Registered global QR code resolvers: {}", this.globalQRCodeResolvers);
	}

	public LocatorScannedCodeResolverResult resolve(@NonNull final ScannedCode scannedCode)
	{
		return resolve(scannedCode, LocatorScannedCodeResolveContext.NO_CONTEXT);
	}

	public LocatorScannedCodeResolverResult resolve(@NonNull final ScannedCode scannedCode, @NonNull final LocatorScannedCodeResolveContext context)
	{
		final ArrayList<LocatorNotResolvedReason> notFoundReasons = new ArrayList<>();

		final GlobalQRCode globalQRCode = scannedCode.toGlobalQRCodeIfMatching().orNullIfError();
		if (globalQRCode != null)
		{
			for (LocatorGlobalQRCodeResolver globalQRCodeResolver : globalQRCodeResolvers)
			{
				final LocatorScannedCodeResolverResult result = globalQRCodeResolver.resolve(globalQRCode, context);
				if (result.isFound())
				{
					return result;
				}
				else
				{
					notFoundReasons.addAll(result.getNotResolvedReasons());
				}
			}
		}

		//
		// Try searching by locator value
		{
			final LocatorScannedCodeResolverResult result = resolveByLocatorValue(scannedCode, context);
			if (result.isFound())
			{
				return result;
			}
			else
			{
				notFoundReasons.addAll(result.getNotResolvedReasons());
			}
		}

		return LocatorScannedCodeResolverResult.notFound(notFoundReasons);
	}

	private LocatorScannedCodeResolverResult resolveByLocatorValue(final @NotNull ScannedCode scannedCode, final @NotNull LocatorScannedCodeResolveContext context)
	{
		final ImmutableList<LocatorQRCode> locatorQRCodes = warehouseBL.getActiveLocatorsByValue(scannedCode.getAsString())
				.stream()
				.map(LocatorQRCode::ofLocator)
				.filter(context::isMatching)
				.distinct()
				.collect(ImmutableList.toImmutableList());

		if (locatorQRCodes.isEmpty())
		{
			return LocatorScannedCodeResolverResult.notFound(RESOLVER_KEY_resolveByLocatorValue, "No locator found for " + scannedCode.getAsString());
		}
		else if (locatorQRCodes.size() > 1)
		{
			return LocatorScannedCodeResolverResult.notFound(RESOLVER_KEY_resolveByLocatorValue, "Multiple locatorQRCodes found for " + scannedCode.getAsString());
		}
		else
		{
			return LocatorScannedCodeResolverResult.found(locatorQRCodes.get(0));
		}
	}
}

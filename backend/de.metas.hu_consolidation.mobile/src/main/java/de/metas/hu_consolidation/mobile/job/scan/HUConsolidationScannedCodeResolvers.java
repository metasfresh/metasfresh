package de.metas.hu_consolidation.mobile.job.scan;

import de.metas.handlingunits.qrcodes.mobile.MobileQRCodeMessages;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * The replaceable chain of {@link HUConsolidationScannedCodeResolver}s: the first one that recognizes the code wins.
 */
@Component
public class HUConsolidationScannedCodeResolvers
{
	private final List<HUConsolidationScannedCodeResolver> resolvers;

	public HUConsolidationScannedCodeResolvers(@NonNull final List<HUConsolidationScannedCodeResolver> resolvers)
	{
		this.resolvers = resolvers;
	}

	@NonNull
	public HUConsolidationScannedCrate resolve(@NonNull final String scannedCode, @NonNull final HUConsolidationJobCrates jobCrates)
	{
		for (final HUConsolidationScannedCodeResolver resolver : resolvers)
		{
			final Optional<HUConsolidationScannedCrate> crate = resolver.resolve(scannedCode, jobCrates);
			if (crate.isPresent())
			{
				return crate.get();
			}
		}

		throw new AdempiereException(MobileQRCodeMessages.INVALID_GRAI_BARCODE, scannedCode);
	}
}

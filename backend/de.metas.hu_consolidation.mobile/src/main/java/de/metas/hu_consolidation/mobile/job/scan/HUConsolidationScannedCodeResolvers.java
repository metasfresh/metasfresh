package de.metas.hu_consolidation.mobile.job.scan;

import de.metas.handlingunits.qrcodes.mobile.MobileQRCodeMessages;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * The replaceable chain of {@link HUConsolidationScannedCodeResolver}s: the first one that recognizes the code wins.
 * The order of the injected resolvers does not matter as long as the code types of the resolvers are disjoint.
 * If no resolver recognizes the code, the message names GRAI because GRAI is the only code type so far.
 */
@Component
@RequiredArgsConstructor
public class HUConsolidationScannedCodeResolvers
{
	@NonNull private final List<HUConsolidationScannedCodeResolver> resolvers;

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

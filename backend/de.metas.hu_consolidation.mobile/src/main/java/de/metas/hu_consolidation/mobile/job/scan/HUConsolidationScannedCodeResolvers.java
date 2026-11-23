package de.metas.hu_consolidation.mobile.job.scan;

import de.metas.handlingunits.picking.slot.PickingSlotQueues;
import de.metas.handlingunits.qrcodes.mobile.MobileQRCodeMessages;
import de.metas.scannable_code.ScannedCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

/**
 * The replaceable chain of {@link HUConsolidationScannedCodeResolver}s: the first one that recognizes the code wins.
 * The order of the injected resolvers does not matter as long as the code types of the resolvers are disjoint.
 * A new code type needs a new {@link HUConsolidationScannedCodeResolver} and its own "not a valid code" message, because the message below names GRAI.
 * If no resolver recognizes the code (or the code is blank, i.e. {@code null}), the message names GRAI because GRAI is the only supported code type.
 */
@Component
@RequiredArgsConstructor
public class HUConsolidationScannedCodeResolvers
{
	@NonNull private final List<HUConsolidationScannedCodeResolver> resolvers;

	@NonNull
	public HUConsolidationScannedCrate resolve(@Nullable final ScannedCode scannedCode, @NonNull final PickingSlotQueues queues)
	{
		if (scannedCode != null)
		{
			for (final HUConsolidationScannedCodeResolver resolver : resolvers)
			{
				final Optional<HUConsolidationScannedCrate> crate = resolver.resolve(scannedCode, queues);
				if (crate.isPresent())
				{
					return crate.get();
				}
			}
		}

		// a blank code and a code of an unsupported type end here alike
		throw new AdempiereException(MobileQRCodeMessages.INVALID_GRAI_BARCODE, scannedCode != null ? scannedCode.getAsString() : "");
	}
}

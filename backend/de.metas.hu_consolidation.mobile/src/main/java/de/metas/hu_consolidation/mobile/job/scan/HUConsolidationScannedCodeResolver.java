package de.metas.hu_consolidation.mobile.job.scan;

import de.metas.scannable_code.ScannedCode;
import lombok.NonNull;

import java.util.Optional;

/**
 * Resolves a scanned code of one code type to a crate among the crates of the job.
 * A new code type = one new implementation (a Spring component); the chain's "unrecognized code" message names the supported types.
 */
public interface HUConsolidationScannedCodeResolver
{
	/**
	 * @return empty if the code is not of this resolver's type ("not my code type"); else the crate.
	 * @throws org.adempiere.exceptions.AdempiereException {@code HU_NOT_FOUND} if no crate of the job matches (also when {@code jobCrates} is empty, without any query),
	 *                                                     {@code HU_AMBIGUOUS} if more than one matches.
	 */
	Optional<HUConsolidationScannedCrate> resolve(@NonNull ScannedCode scannedCode, @NonNull HUConsolidationJobCrates jobCrates);
}

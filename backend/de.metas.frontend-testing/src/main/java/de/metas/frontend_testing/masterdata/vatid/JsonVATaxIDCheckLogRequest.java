package de.metas.frontend_testing.masterdata.vatid;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonVATaxIDCheckLogRequest
{
	/**
	 * Identifier of an already-created {@code bpartners} entry — the check-log row is written for this
	 * business partner.
	 */
	@NonNull Identifier bpartner;

	/**
	 * Identifier of an already-created business-partner location. If null, the row is written without a
	 * {@code C_BPartner_Location_ID} (allowed — see {@link de.metas.vatid.VATaxIDCheckRequest}).
	 */
	@Nullable Identifier bpartnerLocation;

	/**
	 * The VAT-ID string recorded on the row. If null, a fixed placeholder value is used — this command
	 * never calls VIES, so the value only needs to be non-blank.
	 */
	@Nullable String vataxID;
}

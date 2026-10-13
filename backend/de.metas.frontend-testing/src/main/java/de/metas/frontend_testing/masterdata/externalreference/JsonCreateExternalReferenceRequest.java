package de.metas.frontend_testing.masterdata.externalreference;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * Creates one {@code S_ExternalReference} pointing at a business partner.
 * <p>
 * Addition of this release line: there is no counterpart in the later (new_dawn-based) releases.
 */
@Value
@Builder
@Jacksonized
public class JsonCreateExternalReferenceRequest
{
	/** {@code S_ExternalReference.Type} code, e.g. {@code "BPartner"}. */
	@NonNull String type;

	/** {@code S_ExternalReference.ExternalSystem} code, e.g. {@code "Other"}. */
	@NonNull String externalSystem;

	/** {@code S_ExternalReference.ExternalReference}, i.e. the value in the external system. */
	@NonNull String value;

	/** Identifier of the referenced business partner: a key of the {@code bpartners} section or a {@code context} identifier. */
	@NonNull String referencedRecord;
}

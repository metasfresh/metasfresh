package de.metas.frontend_testing.expectations.request;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

/**
 * Asserts (or denies) that a completed {@code M_Movement} exists moving a product FROM one warehouse
 * TO another. Keyed (in {@link JsonExpectations#getMovements()}) by either a product identifier
 * directly, or an HU identifier — resolved to the FIRST packing material product that HU carries
 * (e.g. a packing-instruction-produced TU's crate/pallet product).
 */
@Value
@Builder
@Jacksonized
public class JsonMovementExpectation
{
	@Nullable Boolean isExists;
	@NonNull String fromWarehouse;
	@NonNull String toWarehouse;
}

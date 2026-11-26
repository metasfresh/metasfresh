package de.metas.frontend_testing.masterdata.workplace;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonWorkplaceRequest
{
	@Nullable Identifier warehouse;
	@Nullable Identifier pickingSlot;
	@Nullable Identifier pickFromLocator;
	/** Null ⇒ defaults to a packing place (matches the {@code C_Workplace.IsPackingPlace} DB default 'Y'); set {@code false} for a replenishment workplace. */
	@Nullable Boolean isPackingPlace;
	/** C_Workplace.IsWarnShelfLifeUndercut — when true, a shelf-life undercut warning dialog is shown to the picker */
	@Nullable Boolean warnShelfLifeUndercut;
}

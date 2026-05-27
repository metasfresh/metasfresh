package de.metas.frontend_testing.masterdata.compensation_group;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonCompensationGroupSchemaRequest
{
	/**
	 * Optional explicit schema name. If null, the identifier (map key) is used.
	 */
	@Nullable String name;

	/**
	 * Whether the schema propagates packing instructions to derived order lines.
	 */
	@Nullable Boolean isInheritPackingInstruction;

	/**
	 * Template (regular) lines that will be added to every group instantiated from this schema.
	 */
	@Nullable List<JsonCompensationGroupSchemaTemplateLine> templateLines;
}

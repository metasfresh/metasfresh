package de.metas.handlingunits.report.labels;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuUnitType;
import de.metas.organization.OrgId;
import de.metas.process.AdProcessId;
import de.metas.report.PrintCopies;
import de.metas.util.lang.SeqNo;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * A new HU label config that applies to exactly one HU unit type.
 */
@Value
@Builder
public class HULabelConfigCreateRequest
{
	@NonNull OrgId orgId;
	@NonNull SeqNo seqNo;
	/** If null, the config matches any business partner. */
	@Nullable BPartnerId bpartnerId;
	/** If null, the config matches any source document type. */
	@Nullable HULabelSourceDocType sourceDocType;
	@NonNull HuUnitType huUnitType;
	@NonNull AdProcessId printFormatProcessId;
	boolean autoPrint;
	@Builder.Default @NonNull PrintCopies autoPrintCopies = PrintCopies.ONE;
}

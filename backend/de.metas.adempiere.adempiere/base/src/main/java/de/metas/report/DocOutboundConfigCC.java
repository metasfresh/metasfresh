package de.metas.report;

import de.metas.document.DocTypeId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.column.AdColumnId;

import javax.annotation.Nullable;

@Value
@Builder
public class DocOutboundConfigCC
{
	@NonNull DocOutboundConfigCCId id;
	@NonNull DocOutboundConfigId docOutboundConfigId;
	@NonNull PrintFormatId printFormatId;
	@NonNull AdColumnId columnId;
	@Nullable DocTypeId overrideDocTypeId;
}

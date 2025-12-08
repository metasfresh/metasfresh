package de.metas.attributes_included_tab.descriptor;

import de.metas.i18n.ITranslatableString;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.service.ClientId;

@Value
@Builder
class AttributesIncludedTabUserConfig
{
	@NonNull AttributesIncludedTabId id;
	@NonNull AttributeSetId attributeSetId;
	@NonNull ClientId clientId;
	@NonNull AdTableId parentTableId;
	@NonNull ITranslatableString caption;
	int seqNo;
}

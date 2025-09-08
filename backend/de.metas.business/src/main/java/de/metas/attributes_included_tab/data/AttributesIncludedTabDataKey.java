package de.metas.attributes_included_tab.data;

import de.metas.attributes_included_tab.descriptor.AttributesIncludedTabId;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;

@Value(staticConstructor = "of")
public class AttributesIncludedTabDataKey
{
	@NonNull TableRecordReference recordRef;
	@NonNull AttributesIncludedTabId tabId;
}

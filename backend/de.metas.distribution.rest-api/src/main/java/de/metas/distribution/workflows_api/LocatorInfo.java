package de.metas.distribution.workflows_api;

import de.metas.i18n.ITranslatableString;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorBarcode;
import org.adempiere.warehouse.LocatorId;

@Value
@Builder
public class LocatorInfo
{
	@NonNull LocatorId locatorId;
	@NonNull LocatorBarcode barcode;
	@NonNull String caption;
}

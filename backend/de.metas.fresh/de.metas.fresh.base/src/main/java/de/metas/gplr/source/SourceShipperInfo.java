package de.metas.gplr.source;

import de.metas.i18n.ITranslatableString;
import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SourceShipperInfo
{
	@NonNull ShipperId shipperId;
	@NonNull ITranslatableString name;
}

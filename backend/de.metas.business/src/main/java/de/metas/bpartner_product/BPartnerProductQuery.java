package de.metas.bpartner_product;

import de.metas.ean13.EAN13;
import de.metas.util.InSetPredicate;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class BPartnerProductQuery
{
	@Nullable InSetPredicate<EAN13> cuEANs;
}

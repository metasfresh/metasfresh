package de.metas.camel.ebay.producer;

import javax.annotation.Nullable;

import de.metas.camel.externalsystems.ebay.api.model.Order;
import lombok.NonNull;
import lombok.Value;

@Value
public class BPartnerUpsertRequestProducer {
	
	@NonNull
	Order order;
	
	@Nullable
	String bPartnerLocationIdentifierCustomPath;

	@Nullable
	String bPartnerCustomIdentifier;

}

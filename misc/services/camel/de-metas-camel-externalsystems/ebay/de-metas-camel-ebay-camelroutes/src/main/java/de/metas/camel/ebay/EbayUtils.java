package de.metas.camel.ebay;

import static de.metas.camel.ebay.EbayConstants.EXTERNAL_ID_PREFIX;

import java.time.LocalDate;

import de.metas.camel.externalsystems.ebay.api.model.Order;
import lombok.NonNull;

public class EbayUtils {

	public static LocalDate toLocalDate(@NonNull final String in) {
        LocalDate localDate = LocalDate.parse(in);
        return localDate;
	}

	
	public static String bPartnerIdentifier(Order order) {
		return EXTERNAL_ID_PREFIX + order.getBuyer().getUsername();
	}
	
}

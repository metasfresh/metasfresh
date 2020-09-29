package de.metas.camel.shipping.shipment;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static de.metas.camel.shipping.shipment.AttributeValueType.DATE;
import static de.metas.camel.shipping.shipment.AttributeValueType.STRING;

@AllArgsConstructor
@Getter
public enum AttributeCode
{
	LOT_NUMBER("Lot-Nummer",STRING),
	ARTICLE_FLAVOR("Article_Flavor", STRING),
	BEST_BEFORE_DATE("HU_BestBeforeDate", DATE);

	private final String attributeCode;
	private final AttributeValueType attributeValueType;
}

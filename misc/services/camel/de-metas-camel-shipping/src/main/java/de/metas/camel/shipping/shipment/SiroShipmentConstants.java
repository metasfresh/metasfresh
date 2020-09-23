package de.metas.camel.shipping.shipment;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

public interface SiroShipmentConstants
{
	String SIRO_FTP_PATH = "{{siro.ftp.retrieve.shipments.endpoint}}";
	String CREATE_SHIPMENT_MF_URL = "{{metasfresh.api.baseurl}}/shipment";
	String LOCAL_STORAGE_URL = "{{siro.shipments.local.storage}}";
	String AUTHORIZATION = "Authorization";
	String AUTHORIZATION_TOKEN = "{{metasfresh.api.authtoken}}";
	String SHIPMENT_XML_TO_JSON_PROCESSOR = "shipment-xml-to-json-id";

	String DEFAULT_DELIVERY_RULE_FORCE = "F";

	String DELIVERY_DATE_PATTERN = "dd.MM.yyyy HH.mm.ss";

	String TRACKING_NUMBERS_SEPARATOR = "shipment.tracking.numbers.separator";
	String PACKAGE_WEIGHT_SEPARATOR = "shipment.package.weight.separator";

	String SIRO_LOCALE_PROPERTY = "siro.locale";
	String LOCALE_COUNTRY_LANGUAGE_SEPARATOR = ",";

	Set<String> EXPIRY_DATE_PATTERNS = ImmutableSet.of("yyyy/MM/dd", "yyyy.MM.dd", "dd.MM.yyyy");

	String EMPTY_FIELD = "";
	String SHIPPER_INTERNAL_NAME_SEPARATOR_PROP = "shipper.InternalName.parts.separator";
}

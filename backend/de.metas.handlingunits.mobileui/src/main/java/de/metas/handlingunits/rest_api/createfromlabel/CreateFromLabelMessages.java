package de.metas.handlingunits.rest_api.createfromlabel;

import de.metas.i18n.AdMessageKey;
import lombok.experimental.UtilityClass;

/** AD_Messages of the HU Manager "create HU from a scanned label" flow (migrations {@code 5832640_sys_createFromLabel_AD_Messages.sql}, {@code 5832650_sys_createFromLabel_ProductNotFoundForProductCode_AD_Message.sql}). */
@UtilityClass
public class CreateFromLabelMessages
{
	public static final AdMessageKey LABEL_NOT_RECOGNIZED = AdMessageKey.of("de.metas.handlingunits.createFromLabel.LabelNotRecognized");
	public static final AdMessageKey LABEL_HAS_NO_PRODUCT = AdMessageKey.of("de.metas.handlingunits.createFromLabel.LabelHasNoProduct");
	public static final AdMessageKey PRODUCT_NOT_FOUND_FOR_GTIN = AdMessageKey.of("de.metas.handlingunits.createFromLabel.ProductNotFoundForGTIN");
	public static final AdMessageKey PRODUCT_NOT_FOUND_FOR_PRODUCT_CODE = AdMessageKey.of("de.metas.handlingunits.createFromLabel.ProductNotFoundForProductCode");
	public static final AdMessageKey NOT_A_PRODUCT_LABEL = AdMessageKey.of("de.metas.handlingunits.createFromLabel.NotAProductLabel");
	public static final AdMessageKey WEIGHT_NOT_USED = AdMessageKey.of("de.metas.handlingunits.createFromLabel.WeightNotUsed");
	public static final AdMessageKey BARCODE_FITS_SEVERAL_PRODUCTS = AdMessageKey.of("de.metas.handlingunits.createFromLabel.BarcodeFitsSeveralProducts");
}

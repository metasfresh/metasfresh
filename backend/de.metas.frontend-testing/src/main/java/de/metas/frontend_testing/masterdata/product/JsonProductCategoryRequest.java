package de.metas.frontend_testing.masterdata.product;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

/**
 * Creates a per-run {@code M_Product_Category} and (optionally) a fresh per-run {@code M_AttributeSet} that becomes
 * the category's attribute set ({@code M_Product_Category.M_AttributeSet_ID}).
 * <p>
 * Needed because the mobile Manufacturing receive's editable-attributes feature resolves a product's applicable
 * attribute set from the product's CATEGORY ({@code IProductBL#getAttributeSetId(I_M_Product)} reads
 * {@code M_Product_Category.M_AttributeSet_ID}), NOT from {@code M_Product.M_AttributeSet_ID} (the product's
 * {@code attributeSetName} field feeds a different consumer). A spec that needs an attribute offered on the receive
 * dialog therefore creates its own category here, links the attribute into the category's set (via the
 * {@code attributes} section by name), and points its products at this category
 * ({@link JsonCreateProductRequest#getProductCategory()}).
 * <p>
 * See {@link CreateProductCategoryCommand}.
 */
@Value
@Builder
@Jacksonized
public class JsonProductCategoryRequest
{
	/**
	 * {@code M_Product_Category.Name}/{@code Value}. Uniquified per run (see {@link CreateProductCategoryCommand}),
	 * so the same request label never collides across runs. Defaults to the request's map-key identifier when omitted.
	 */
	@Nullable String name;

	/**
	 * The in-request coupling KEY for this category's attribute set (NOT the stored DB name). A FRESH
	 * (not-mandatory) {@code M_AttributeSet} is always created per run with a unique stored {@code Name} - never
	 * get-or-created by this literal, which would silently share one set across specs/runs. Other {@code attributes}
	 * entries reference this same key via {@code attributeSetNames} to link into THIS per-run set (resolved through
	 * the {@code MasterdataContext}), so the products in this category offer those attributes. When omitted, the
	 * category is created without an attribute set.
	 */
	@Nullable String attributeSetName;
}

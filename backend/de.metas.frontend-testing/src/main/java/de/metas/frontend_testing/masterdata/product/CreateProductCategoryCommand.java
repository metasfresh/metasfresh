package de.metas.frontend_testing.masterdata.product;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.product.ProductCategoryId;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.mm.attributes.AttributeSetMandatoryType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_Product_Category;

/**
 * Creates a per-run {@code M_Product_Category} and (optionally) a fresh per-run {@code M_AttributeSet} as its
 * attribute set. See {@link JsonProductCategoryRequest} for why this exists (the mfg editable-attributes feature
 * reads the attribute set from the product's category, not from the product).
 * <p>
 * No repository owns {@code M_Product_Category} / {@code M_AttributeSet} creation - this mirrors the equivalent
 * test helpers ({@code BusinessTestHelper#createProductCategory}, {@code MaterialReceiptActivityHandlerTest}) and
 * the sibling {@link de.metas.frontend_testing.masterdata.attribute.CreateAttributeCommand}, which write these
 * records the same way via {@link InterfaceWrapperHelper}.
 */
@Builder
public class CreateProductCategoryCommand
{
	@NonNull private final MasterdataContext context;
	@NonNull private final JsonProductCategoryRequest request;
	@NonNull private final Identifier identifier;

	public JsonProductCategoryResponse execute()
	{
		final I_M_Product_Category category = createProductCategory();

		final ProductCategoryId productCategoryId = ProductCategoryId.ofRepoId(category.getM_Product_Category_ID());
		context.putIdentifier(identifier, productCategoryId);

		return JsonProductCategoryResponse.builder()
				.id(productCategoryId)
				.build();
	}

	private I_M_Product_Category createProductCategory()
	{
		// Names/Values must be unique per run (module CLAUDE.md) - never the raw request label.
		final String requestName = StringUtils.trimBlankToNull(request.getName());
		final String value = requestName != null
				? Identifier.ofString(requestName).toUniqueString()
				: identifier.toUniqueString();

		final I_M_Product_Category category = InterfaceWrapperHelper.newInstance(I_M_Product_Category.class);
		category.setAD_Org_ID(MasterdataContext.ORG_ID.getRepoId());
		category.setValue(value);
		category.setName(value);

		final String attributeSetName = StringUtils.trimBlankToNull(request.getAttributeSetName());
		if (attributeSetName != null)
		{
			final AttributeSetId attributeSetId = createAttributeSet(attributeSetName);
			category.setM_AttributeSet_ID(attributeSetId.getRepoId());
			// Register the created set under the request name so the `attributes` section
			// (CreateAttributeCommand, which runs after productCategories) links into THIS per-run set.
			// The request name is only the in-request coupling KEY - the stored DB Name is uniquified below.
			context.putIdentifier(Identifier.ofString(attributeSetName), attributeSetId);
		}

		InterfaceWrapperHelper.saveRecord(category);
		return category;
	}

	/**
	 * Always creates a FRESH {@code M_AttributeSet} with a per-run-unique {@code Name} - never get-or-create by
	 * the literal request name, which would silently SHARE one set across specs/runs on the shared test DB (the
	 * module CLAUDE.md "Names/Values must be unique per run" rule + the {@code e2e/mobile-webui} fresh-fixture
	 * rule). Callers couple to it via the context identifier (see {@link #createProductCategory()}), not the name.
	 */
	private AttributeSetId createAttributeSet(@NonNull final String attributeSetName)
	{
		final I_M_AttributeSet attributeSet = InterfaceWrapperHelper.newInstance(I_M_AttributeSet.class);
		attributeSet.setName(Identifier.ofString(attributeSetName).toUniqueString());
		attributeSet.setMandatoryType(AttributeSetMandatoryType.NotMandatory.getCode());
		InterfaceWrapperHelper.saveRecord(attributeSet);
		return AttributeSetId.ofRepoId(attributeSet.getM_AttributeSet_ID());
	}
}

/*
 * #%L
 * de-metas-common-product
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.common.product.v2.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.common.rest_api.v2.SyncAdvise;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static de.metas.common.product.v2.request.constants.SwaggerDocConstants.PRODUCT_CATEGORY_IDENTIFIER_DOC;
import static de.metas.common.rest_api.v2.SwaggerDocConstants.READ_ONLY_SYNC_ADVISE_DOC;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@ToString
@EqualsAndHashCode
public class JsonRequestProduct
{
	@Schema(description = "Corresponding to `M_Product.Value`")
	private String code;

	@Schema(hidden = true)
	private boolean codeSet;

	@Schema(description = "Corresponding to `M_Product.Name`", requiredMode = REQUIRED)
	private String name;

	@Schema(hidden = true)
	private boolean nameSet;

	@Schema(description = "Corresponding to `M_Product.Type`", requiredMode = REQUIRED)
	private Type type;

	@Schema(hidden = true)
	private boolean typeSet;

	@Schema(description = "Corresponding to `M_Product.C_UOM_ID`", requiredMode = REQUIRED)
	private String uomCode;

	@Schema(hidden = true)
	private boolean uomCodeSet;

	@Schema(description = "Corresponding to `M_Product.UPC`")
	private String ean;

	@Schema(hidden = true)
	private boolean eanSet;

	@Schema(description = "Corresponding to `M_Product.GTIN`")
	private String gtin;

	@Schema(hidden = true)
	private boolean gtinSet;

	@Schema(description = "Corresponding to `M_Product.Description`")
	private String description;

	@Schema(hidden = true)
	private boolean descriptionSet;

	@Schema(description = "Corresponding to `M_Product.isDiscontinued`")
	private Boolean discontinued;

	@Schema(hidden = true)
	private boolean discontinuedSet;

	@Schema(description = "Corresponding to `M_Product.discontinuedFrom`", example = "2021-11-08")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate discontinuedFrom;

	@Schema(hidden = true)
	private boolean discontinuedFromSet;

	@Schema(description = "Corresponding to `M_Product.isActive`")
	private Boolean active;

	@Schema(hidden = true)
	private boolean activeSet;

	@Schema(description = "Corresponding to `M_Product.isStocked`")
	private Boolean stocked;

	@Schema(hidden = true)
	private boolean stockedSet;

	@Schema(description = PRODUCT_CATEGORY_IDENTIFIER_DOC)
	private String productCategoryIdentifier;

	@Schema(hidden = true)
	private boolean productCategoryIdentifierSet;

	@Schema
	private String guaranteeMonths;

	@Schema(hidden = true)
	private boolean guaranteeMonthsSet;

	@Schema
	private String warehouseTemperature;

	@Schema(hidden = true)
	private boolean warehouseTemperatureSet;

	@Schema(description = READ_ONLY_SYNC_ADVISE_DOC)
	private SyncAdvise syncAdvise;

	@Schema
	private List<JsonRequestBPartnerProductUpsert> bpartnerProductItems;

	@Schema
	private String sectionCode;

	@Schema(hidden = true)
	private boolean sectionCodeSet;

	@Schema(description = "Corresponding to `M_Product.IsPurchased`")
	private Boolean purchased;

	@Schema(hidden = true)
	private boolean purchasedSet;

	@Schema(description = "Corresponding to `M_Product.SAP_ProductHierarchy`")
	private String sapProductHierarchy;

	@Schema(hidden = true)
	private boolean sapProductHierarchySet;

	@Schema
	private JsonRequestUpsertProductAllergen productAllergens;

	@Schema
	private JsonRequestUpsertQualityAttribute qualityAttributes;

	@Schema
	private JsonRequestProductWarehouseAssignmentSave warehouseAssignments;

	@Schema
	private List<JsonRequestProductTaxCategoryUpsert> productTaxCategories;

	public void setCode(final @NonNull String code)
	{
		this.code = code;
		this.codeSet = true;
	}

	public void setName(final @NonNull String name)
	{
		this.name = name;
		this.nameSet = true;
	}

	public void setType(final @NonNull Type type)
	{
		this.type = type;
		this.typeSet = true;
	}

	public void setUomCode(final @NonNull String uomCode)
	{
		this.uomCode = uomCode;
		this.uomCodeSet = true;
	}

	public void setEan(final String ean)
	{
		this.ean = ean;
		this.eanSet = true;
	}

	public void setGtin(final String gtin)
	{
		this.gtin = gtin;
		this.gtinSet = true;
	}

	public void setDescription(final String description)
	{
		this.description = description;
		this.descriptionSet = true;
	}

	public void setDiscontinued(final Boolean discontinued)
	{
		this.discontinued = discontinued;
		this.discontinuedSet = true;
	}

	public void setDiscontinuedFrom(final LocalDate discontinuedFrom)
	{
		this.discontinuedFrom = discontinuedFrom;
		this.discontinuedFromSet = true;
	}

	public void setActive(final Boolean active)
	{
		this.active = active;
		this.activeSet = true;
	}

	public void setStocked(final Boolean stocked)
	{
		this.stocked = stocked;
		this.stockedSet = true;
	}

	public void setProductCategoryIdentifier(final String productCategoryIdentifier)
	{
		this.productCategoryIdentifier = productCategoryIdentifier;
		this.productCategoryIdentifierSet = true;
	}

	public void setPurchased(final Boolean purchased)
	{
		this.purchased = purchased;
		this.purchasedSet = true;
	}

	public void setSyncAdvise(final SyncAdvise syncAdvise)
	{
		this.syncAdvise = syncAdvise;
	}

	public void setBpartnerProductItems(final List<JsonRequestBPartnerProductUpsert> bpartnerProductItems)
	{
		this.bpartnerProductItems = bpartnerProductItems;
	}

	public void setSectionCode(final String sectionCode)
	{
		this.sectionCode = sectionCode;
		this.sectionCodeSet = true;
	}

	public void setSAPProductHierarchy(final String sapProductHierarchy)
	{
		this.sapProductHierarchy = sapProductHierarchy;
		this.sapProductHierarchySet = true;
	}

	public void setProductAllergens(@Nullable final JsonRequestUpsertProductAllergen productAllergens)
	{
		this.productAllergens = productAllergens;
	}

	public void setGuaranteeMonths(@Nullable final String guaranteeMonths)
	{
		this.guaranteeMonths = guaranteeMonths;
		this.guaranteeMonthsSet = true;
	}

	public void setWarehouseTemperature(@Nullable final String warehouseTemperature)
	{
		this.warehouseTemperature = warehouseTemperature;
		this.warehouseTemperatureSet = true;
	}

	public void setQualityAttributes(@Nullable final JsonRequestUpsertQualityAttribute qualityAttribute)
	{
		this.qualityAttributes = qualityAttribute;
	}

	public void setWarehouseAssignments(final JsonRequestProductWarehouseAssignmentSave warehouseAssignments)
	{
		this.warehouseAssignments = warehouseAssignments;
	}

	@AllArgsConstructor
	public static enum Type
	{
		ITEM("I"),
		SERVICE("S"),
		RESOURCE("R"),
		EXPENSE_TYPE("E"),
		ONLINE("O"),
		FREIGHT_COST("F"),
		NAHRUNG("N");

		@Getter
		private String code;
		private static final ImmutableMap<String, Type> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), Type::getCode);

		@NonNull
		public static Type ofCode(@NonNull final String code)
		{
			final Type type = typesByCode.get(code);
			if (type == null)
			{
				throw new RuntimeException("No " + Type.class + " found for code: " + code);
			}
			return type;
		}
	}

	public void setProductTaxCategories(final List<JsonRequestProductTaxCategoryUpsert> productTaxCategories)
	{
		this.productTaxCategories = productTaxCategories;
	}
}

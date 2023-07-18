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
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static de.metas.common.product.v2.request.constants.SwaggerDocConstants.PRODUCT_CATEGORY_IDENTIFIER_DOC;
import static de.metas.common.rest_api.v2.SwaggerDocConstants.READ_ONLY_SYNC_ADVISE_DOC;

@Getter
@ToString
@EqualsAndHashCode
public class JsonRequestProduct
{
	@ApiModelProperty(position = 20, value = "Corresponding to `M_Product.Value`")
	private String code;

	@ApiModelProperty(hidden = true)
	private boolean codeSet;

	@ApiModelProperty(position = 30, value = "Corresponding to `M_Product.Name`", required = true)
	private String name;

	@ApiModelProperty(hidden = true)
	private boolean nameSet;

	@ApiModelProperty(position = 40, value = "Corresponding to `M_Product.Type`", required = true)
	private Type type;

	@ApiModelProperty(hidden = true)
	private boolean typeSet;

	@ApiModelProperty(position = 50, value = "Corresponding to `M_Product.C_UOM_ID`", required = true)
	private String uomCode;

	@ApiModelProperty(hidden = true)
	private boolean uomCodeSet;

	@ApiModelProperty(position = 60, value = "Corresponding to `M_Product.UPC`")
	private String ean;

	@ApiModelProperty(hidden = true)
	private boolean eanSet;

	@ApiModelProperty(position = 70, value = "Corresponding to `M_Product.GTIN`")
	private String gtin;

	@ApiModelProperty(hidden = true)
	private boolean gtinSet;

	@ApiModelProperty(position = 80, value = "Corresponding to `M_Product.Description`")
	private String description;

	@ApiModelProperty(hidden = true)
	private boolean descriptionSet;

	@ApiModelProperty(position = 90, value = "Corresponding to `M_Product.isDiscontinued`")
	private Boolean discontinued;

	@ApiModelProperty(hidden = true)
	private boolean discontinuedSet;

	@ApiModelProperty(position = 100, value = "Corresponding to `M_Product.discontinuedFrom`", example = "2021-11-08")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate discontinuedFrom;

	@ApiModelProperty(hidden = true)
	private boolean discontinuedFromSet;

	@ApiModelProperty(position = 110, value = "Corresponding to `M_Product.isActive`")
	private Boolean active;

	@ApiModelProperty(hidden = true)
	private boolean activeSet;

	@ApiModelProperty(position = 120, value = "Corresponding to `M_Product.isStocked`")
	private Boolean stocked;

	@ApiModelProperty(hidden = true)
	private boolean stockedSet;

	@ApiModelProperty(position = 130, value = PRODUCT_CATEGORY_IDENTIFIER_DOC)
	private String productCategoryIdentifier;

	@ApiModelProperty(hidden = true)
	private boolean productCategoryIdentifierSet;

	@ApiModelProperty(position = 140, value = READ_ONLY_SYNC_ADVISE_DOC)
	private SyncAdvise syncAdvise;

	@ApiModelProperty(position = 150)
	private List<JsonRequestBPartnerProductUpsert> bpartnerProductItems;

	@ApiModelProperty(position = 160)
	private String sectionCode;

	@ApiModelProperty(hidden = true)
	private boolean sectionCodeSet;

	@ApiModelProperty(position = 170, value = "Corresponding to `M_Product.IsPurchased`")
	private Boolean purchased;

	@ApiModelProperty(hidden = true)
	private boolean purchasedSet;

	@ApiModelProperty(position = 180, value = "Corresponding to `M_Product.SAP_ProductHierarchy`")
	private String sapProductHierarchy;

	@ApiModelProperty(hidden = true)
	private boolean sapProductHierarchySet;

	@ApiModelProperty(position = 190)
	private JsonRequestProductWarehouseAssignmentSave warehouseAssignments;
	
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
}

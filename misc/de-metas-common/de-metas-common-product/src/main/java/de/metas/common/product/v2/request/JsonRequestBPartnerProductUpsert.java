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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import static de.metas.common.product.v2.request.constants.SwaggerDocConstants.BPARTNER_IDENTIFIER_DOC;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(description = "Contains an external id and the actual bpartner-product-item to insert or update. ")
public class JsonRequestBPartnerProductUpsert
{
	@ApiModelProperty(position = 10, required = true, value = BPARTNER_IDENTIFIER_DOC)
	private String bpartnerIdentifier;

	@ApiModelProperty(hidden = true)
	private boolean bpartnerSet;

	@ApiModelProperty(position = 20, value = "Corresponding to I_C_BPartner_Product.isActive", allowEmptyValue = true)
	private Boolean active;

	@ApiModelProperty(hidden = true)
	private boolean activeSet;

	@ApiModelProperty(position = 30, value = "Corresponding to I_C_BPartner_Product.seqNo", allowEmptyValue = true)
	private Integer seqNo;

	@ApiModelProperty(hidden = true)
	private boolean seqNoSet;

	@ApiModelProperty(position = 40, value = "Corresponding to I_C_BPartner_Product.productNo", allowEmptyValue = true)
	private String productNo;

	@ApiModelProperty(hidden = true)
	private boolean productNoSet;

	@ApiModelProperty(position = 50, value = "Corresponding to I_C_BPartner_Product.Description", allowEmptyValue = true)
	private String description;

	@ApiModelProperty(hidden = true)
	private boolean descriptionSet;

	@ApiModelProperty(position = 60, value = "Corresponding to I_C_BPartner_Product.EAN_CU", allowEmptyValue = true)
	private String cuEAN;

	@ApiModelProperty(hidden = true)
	private boolean cuEANSet;

	@ApiModelProperty(position = 70, value = "Corresponding to I_C_BPartner_Product.GTIN", allowEmptyValue = true)
	private String gtin;

	@ApiModelProperty(hidden = true)
	private boolean gtinSet;

	@ApiModelProperty(position = 80, value = "Corresponding to I_C_BPartner_Product.customerLabelName", allowEmptyValue = true)
	private String customerLabelName;

	@ApiModelProperty(hidden = true)
	private boolean customerLabelNameSet;

	@ApiModelProperty(position = 90, value = "Corresponding to I_C_BPartner_Product.ingredients", allowEmptyValue = true)
	private String ingredients;

	@ApiModelProperty(hidden = true)
	private boolean ingredientsSet;

	@ApiModelProperty(position = 100, value = "Corresponding to I_C_BPartner_Product.isCurrentVendor", allowEmptyValue = true)
	private Boolean currentVendor;

	@ApiModelProperty(hidden = true)
	private boolean currentVendorSet;

	@ApiModelProperty(position = 110, value = "Corresponding to I_C_BPartner_Product.isExcludedFromSales", allowEmptyValue = true)
	private Boolean excludedFromSales;

	@ApiModelProperty(hidden = true)
	private boolean excludedFromSalesSet;

	@ApiModelProperty(position = 120, value = "Corresponding to I_C_BPartner_Product.ExclusionFromSaleReason", allowEmptyValue = true)
	private String exclusionFromSalesReason;

	@ApiModelProperty(hidden = true)
	private boolean exclusionFromSalesReasonSet;

	@ApiModelProperty(position = 130, value = "", allowEmptyValue = true)
	private Boolean dropShip;

	@ApiModelProperty(hidden = true)
	private boolean dropShipSet;

	@ApiModelProperty(position = 140, value = "Corresponding to I_C_BPartner_Product.UsedForVendor", allowEmptyValue = true)
	private Boolean usedForVendor;

	@ApiModelProperty(hidden = true)
	private boolean usedForVendorSet;

	@ApiModelProperty(position = 150, value = "Corresponding to I_C_BPartner_Product.isExcludedFromPurchase", allowEmptyValue = true)
	private Boolean excludedFromPurchase;

	@ApiModelProperty(hidden = true)
	private boolean excludedFromPurchaseSet;

	@ApiModelProperty(position = 160, value = "Corresponding to I_C_BPartner_Product.ExclusionFromPurchaseReason", allowEmptyValue = true)
	private String exclusionFromPurchaseReason;

	@ApiModelProperty(hidden = true)
	private boolean exclusionFromPurchaseReasonSet;

	public void setBpartnerIdentifier(final String bpartnerIdentifier)
	{
		this.bpartnerIdentifier = bpartnerIdentifier;
		this.bpartnerSet = true;
	}

	public void setActive(final Boolean active)
	{
		this.active = active;
		activeSet = true;
	}

	public void setSeqNo(final Integer seqNo)
	{
		this.seqNo = seqNo;
		seqNoSet = true;

	}

	public void setProductNo(final String productNo)
	{
		this.productNo = productNo;
		productNoSet = true;

	}

	public void setDescription(final String description)
	{
		this.description = description;
		descriptionSet = true;

	}

	public void setCuEAN(final String cuEAN)
	{
		this.cuEAN = cuEAN;
		cuEANSet = true;

	}

	public void setGtin(final String gtin)
	{
		this.gtin = gtin;
		gtinSet = true;
	}

	public void setCustomerLabelName(final String customerLabelName)
	{
		this.customerLabelName = customerLabelName;
		customerLabelNameSet = true;
	}

	public void setIngredients(final String ingredients)
	{
		this.ingredients = ingredients;
		ingredientsSet = true;
	}

	public void setCurrentVendor(final Boolean currentVendor)
	{
		this.currentVendor = currentVendor;
		currentVendorSet = true;
	}

	public void setExcludedFromSales(final Boolean excludedFromSales)
	{
		this.excludedFromSales = excludedFromSales;
		excludedFromSalesSet = true;

	}

	public void setExclusionFromSalesReason(final String exclusionFromSalesReason)
	{
		this.exclusionFromSalesReason = exclusionFromSalesReason;
		exclusionFromSalesReasonSet = true;
	}

	public void setDropShip(final Boolean dropShip)
	{
		this.dropShip = dropShip;
		dropShipSet = true;
	}

	public void setUsedForVendor(final Boolean usedForVendor)
	{
		this.usedForVendor = usedForVendor;
		usedForVendorSet = true;
	}

	public void setExcludedFromPurchase(final Boolean excludedFromPurchase)
	{
		this.excludedFromPurchase = excludedFromPurchase;
		excludedFromPurchaseSet = true;
	}

	public void setExclusionFromPurchaseReason(final String exclusionFromPurchaseReason)
	{
		this.exclusionFromPurchaseReason = exclusionFromPurchaseReason;
		exclusionFromPurchaseReasonSet = true;
	}
}

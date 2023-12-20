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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import static de.metas.common.product.v2.request.constants.SwaggerDocConstants.BPARTNER_IDENTIFIER_DOC;

@Getter
@ToString
@EqualsAndHashCode
@Schema(description = "Contains an external id and the actual bpartner-product-item to insert or update. ")
public class JsonRequestBPartnerProductUpsert
{
	@Schema(required = true, description = BPARTNER_IDENTIFIER_DOC)
	private String bpartnerIdentifier;

	@Schema(hidden = true)
	private boolean bpartnerSet;

	@Schema(description = "Corresponding to I_C_BPartner_Product.isActive")
	private Boolean active;

	@Schema(hidden = true)
	private boolean activeSet;

	@Schema(description = "Corresponding to I_C_BPartner_Product.seqNo")
	private Integer seqNo;

	@Schema(hidden = true)
	private boolean seqNoSet;

	@Schema(description = "Corresponding to I_C_BPartner_Product.productNo")
	private String productNo;

	@Schema(hidden = true)
	private boolean productNoSet;

	@Schema(description = "Corresponding to I_C_BPartner_Product.Description")
	private String description;

	@Schema(hidden = true)
	private boolean descriptionSet;

	@Schema(description = "Corresponding to I_C_BPartner_Product.EAN_CU")
	private String cuEAN;

	@Schema(hidden = true)
	private boolean cuEANSet;

	@Schema(description = "Corresponding to I_C_BPartner_Product.GTIN")
	private String gtin;

	@Schema(hidden = true)
	private boolean gtinSet;

	@Schema(description = "Corresponding to I_C_BPartner_Product.customerLabelName")
	private String customerLabelName;

	@Schema(hidden = true)
	private boolean customerLabelNameSet;

	@Schema(description = "Corresponding to I_C_BPartner_Product.ingredients")
	private String ingredients;

	@Schema(hidden = true)
	private boolean ingredientsSet;

	@Schema(description = "Corresponding to I_C_BPartner_Product.isCurrentVendor")
	private Boolean currentVendor;

	@Schema(hidden = true)
	private boolean currentVendorSet;

	@Schema(description = "Corresponding to I_C_BPartner_Product.isExcludedFromSales")
	private Boolean excludedFromSales;

	@Schema(hidden = true)
	private boolean excludedFromSalesSet;

	@Schema(description = "Corresponding to I_C_BPartner_Product.ExclusionFromSaleReason")
	private String exclusionFromSalesReason;

	@Schema(hidden = true)
	private boolean exclusionFromSalesReasonSet;

	@Schema(description = "")
	private Boolean dropShip;

	@Schema(hidden = true)
	private boolean dropShipSet;

	@Schema(description = "Corresponding to I_C_BPartner_Product.UsedForVendor")
	private Boolean usedForVendor;

	@Schema(hidden = true)
	private boolean usedForVendorSet;

	@Schema(description = "Corresponding to I_C_BPartner_Product.isExcludedFromPurchase")
	private Boolean excludedFromPurchase;

	@Schema(hidden = true)
	private boolean excludedFromPurchaseSet;

	@Schema(description = "Corresponding to I_C_BPartner_Product.ExclusionFromPurchaseReason")
	private String exclusionFromPurchaseReason;

	@Schema(hidden = true)
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

package de.metas.ordercandidate.api;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.base.MoreObjects;

import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.pricing.attributebased.IProductPriceAware;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public final class OLCand implements IProductPriceAware
{
	public static OLCand of(final I_C_OLCand candidate)
	{
		final int pricingSystemId = -1;
		final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);
		return new OLCand(candidate, pricingSystemId, olCandEffectiveValuesBL);
	}

	private final IOLCandEffectiveValuesBL olCandEffectiveValuesBL;

	private final I_C_OLCand candidate;

	private final OLCandBPartnerInfo bpartnerInfo;
	private final OLCandBPartnerInfo billBPartnerInfo;
	private final OLCandBPartnerInfo dropShipBPartnerInfo;
	private final OLCandBPartnerInfo handOverBPartnerInfo;
	private final int pricingSystemId;

	@Getter
	private final String externalId;

	@Builder
	private OLCand(
			@NonNull final I_C_OLCand candidate,
			final int pricingSystemId,
			final IOLCandEffectiveValuesBL olCandEffectiveValuesBL)
	{
		this.candidate = candidate;
		this.olCandEffectiveValuesBL = olCandEffectiveValuesBL != null ? olCandEffectiveValuesBL : Services.get(IOLCandEffectiveValuesBL.class);

		bpartnerInfo = OLCandBPartnerInfo.builder()
				.bpartnerId(this.olCandEffectiveValuesBL.getC_BPartner_Effective_ID(candidate))
				.bpartnerLocationId(this.olCandEffectiveValuesBL.getC_BP_Location_Effective_ID(candidate))
				.contactId(this.olCandEffectiveValuesBL.getAD_User_Effective_ID(candidate))
				.build();
		billBPartnerInfo = OLCandBPartnerInfo.builder()
				.bpartnerId(this.olCandEffectiveValuesBL.getBill_BPartner_Effective_ID(candidate))
				.bpartnerLocationId(this.olCandEffectiveValuesBL.getBill_Location_Effective_ID(candidate))
				.contactId(this.olCandEffectiveValuesBL.getBill_User_Effective_ID(candidate))
				.build();
		dropShipBPartnerInfo = OLCandBPartnerInfo.builder()
				.bpartnerId(this.olCandEffectiveValuesBL.getDropShip_BPartner_Effective_ID(candidate))
				.bpartnerLocationId(this.olCandEffectiveValuesBL.getDropShip_Location_Effective_ID(candidate))
				.contactId(this.olCandEffectiveValuesBL.getDropShip_User_Effective_ID(candidate))
				.build();
		handOverBPartnerInfo = OLCandBPartnerInfo.builder()
				.bpartnerId(this.olCandEffectiveValuesBL.getHandOver_Partner_Effective_ID(candidate))
				.bpartnerLocationId(this.olCandEffectiveValuesBL.getHandOver_Location_Effective_ID(candidate))
				// .contactId(this.xolCandEffectiveValuesBL.getHandOver_User_Effective_ID(candidate))
				.build();

		this.pricingSystemId = pricingSystemId;
		
		this.externalId = candidate.getExternalId();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this).addValue(candidate).toString();
	}

	public I_C_OLCand unbox()
	{
		return candidate;
	}

	public int getId()
	{
		return candidate.getC_OLCand_ID();
	}

	public TableRecordReference toTableRecordReference()
	{
		return TableRecordReference.of(I_C_OLCand.Table_Name, getId());
	}

	public int getAD_Client_ID()
	{
		return candidate.getAD_Client_ID();
	}

	public int getAD_Org_ID()
	{
		return candidate.getAD_Org_ID();
	}

	public OLCandBPartnerInfo getBPartnerInfo()
	{
		return bpartnerInfo;
	}

	public OLCandBPartnerInfo getBillBPartnerInfo()
	{
		return billBPartnerInfo;
	}

	public OLCandBPartnerInfo getDropShipBPartnerInfo()
	{
		return dropShipBPartnerInfo;
	}

	public OLCandBPartnerInfo getHandOverBPartnerInfo()
	{
		return handOverBPartnerInfo;
	}

	public int getPricingSystemId()
	{
		return pricingSystemId;
	}

	public int getC_Charge_ID()
	{
		return candidate.getC_Charge_ID();
	}

	public int getM_Product_ID()
	{
		return olCandEffectiveValuesBL.getM_Product_Effective_ID(candidate);
	}

	public int getC_UOM_ID()
	{
		return olCandEffectiveValuesBL.getC_UOM_Effective_ID(candidate);
	}

	public int getM_AttributeSet_ID()
	{
		return candidate.getM_AttributeSet_ID();
	}

	public int getM_AttributeSetInstance_ID()
	{
		return candidate.getM_AttributeSetInstance_ID();
	}

	public int getM_Warehouse_Dest_ID()
	{
		return candidate.getM_Warehouse_Dest_ID();
	}

	public boolean isManualPrice()
	{
		return candidate.isManualPrice();
	}

	public BigDecimal getPriceActual()
	{
		return candidate.getPriceActual();
	}

	public boolean isManualDiscount()
	{
		return candidate.isManualDiscount();
	}

	public BigDecimal getDiscount()
	{
		return candidate.getDiscount();
	}

	public int getC_Currency_ID()
	{
		return candidate.getC_Currency_ID();
	}

	public String getProductDescription()
	{
		return candidate.getProductDescription();
	}

	public int getLine()
	{
		return candidate.getLine();
	}

	public boolean isProcessed()
	{
		return candidate.isProcessed();
	}

	public void setProcessed(final boolean processed)
	{
		candidate.setProcessed(true);
	}

	public boolean isError()
	{
		return candidate.isError();
	}

	public void setError(final String errorMsg, final int adNoteId)
	{
		candidate.setIsError(true);
		candidate.setErrorMsg(errorMsg);
		candidate.setAD_Note_ID(adNoteId);
	}

	public BigDecimal getQty()
	{
		return candidate.getQty();
	}

	public String getPOReference()
	{
		return candidate.getPOReference();
	}

	public Timestamp getDatePromised()
	{
		return olCandEffectiveValuesBL.getDatePromised_Effective(candidate);
	}

	public int getAD_InputDataSource_ID()
	{
		return candidate.getAD_InputDataSource_ID();
	}

	public int getAD_DataDestination_ID()
	{
		return candidate.getAD_DataDestination_ID();
	}

	public boolean isImportedWithIssues()
	{
		final org.adempiere.process.rpl.model.I_C_OLCand rplCandidate = InterfaceWrapperHelper.create(candidate, org.adempiere.process.rpl.model.I_C_OLCand.class);
		return rplCandidate.isImportedWithIssues();
	}

	// FIXME hardcoded (08691)
	public Object getValueByColumn(final OLCandAggregationColumn column)
	{
		final String olCandColumnName = column.getColumnName();

		if (olCandColumnName.equals(I_C_OLCand.COLUMNNAME_Bill_BPartner_ID))
		{
			return getBillBPartnerInfo().getBpartnerId();
		}
		else if (olCandColumnName.equals(I_C_OLCand.COLUMNNAME_Bill_Location_ID))
		{
			return getBillBPartnerInfo().getBpartnerLocationId();
		}
		else if (olCandColumnName.equals(I_C_OLCand.COLUMNNAME_Bill_User_ID))
		{
			return getBillBPartnerInfo().getContactId();
		}
		else if (olCandColumnName.equals(I_C_OLCand.COLUMNNAME_DropShip_BPartner_ID))
		{
			return getDropShipBPartnerInfo().getBpartnerId();
		}
		else if (olCandColumnName.equals(I_C_OLCand.COLUMNNAME_DropShip_Location_ID))
		{
			return getDropShipBPartnerInfo().getBpartnerLocationId();
		}
		else if (olCandColumnName.equals(I_C_OLCand.COLUMNNAME_M_PricingSystem_ID))
		{
			return getPricingSystemId();
		}
		else if (olCandColumnName.equals(I_C_OLCand.COLUMNNAME_DatePromised_Effective))
		{
			return getDatePromised();
		}
		else
		{
			return InterfaceWrapperHelper.getValueByColumnId(candidate, column.getAdColumnId());
		}
	}

	@Override
	public int getM_ProductPrice_ID()
	{
		return candidate.getM_ProductPrice_ID();
	}

	@Override
	public boolean isExplicitProductPriceAttribute()
	{
		return candidate.isExplicitProductPriceAttribute();
	}

	public int getFlatrateConditionsId()
	{
		return candidate.getC_Flatrate_Conditions_ID();
	}

	public int getHUPIProductItemId()
	{
		if (candidate.getM_HU_PI_Item_Product_Override_ID() > 0)
		{
			return candidate.getM_HU_PI_Item_Product_Override_ID();
		}
		return candidate.getM_HU_PI_Item_Product_ID();
	}
}

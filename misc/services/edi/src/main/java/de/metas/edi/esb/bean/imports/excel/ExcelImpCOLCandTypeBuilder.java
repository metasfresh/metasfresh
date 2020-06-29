/*
 * #%L
 * de-metas-edi-esb-camel
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.edi.esb.bean.imports.excel;

import static de.metas.edi.esb.commons.Util.resolveGenericLookup;

import java.math.BigDecimal;
import java.math.BigInteger;

import de.metas.edi.esb.commons.Constants;
import de.metas.edi.esb.commons.Util;
import de.metas.edi.esb.jaxb.metasfresh.COrderDeliveryRuleEnum;
import de.metas.edi.esb.jaxb.metasfresh.COrderDeliveryViaRuleEnum;
import de.metas.edi.esb.jaxb.metasfresh.EDIImpADInputDataSourceLookupINType;
import de.metas.edi.esb.jaxb.metasfresh.EDIImpCCurrencyLookupISOCodeType;
import de.metas.edi.esb.jaxb.metasfresh.EDIImpCUOMLookupUOMSymbolType;
import de.metas.edi.esb.jaxb.metasfresh.ReplicationEventEnum;
import de.metas.edi.esb.jaxb.metasfresh.ReplicationModeEnum;
import de.metas.edi.esb.jaxb.metasfresh.ReplicationTypeEnum;
import de.metas.edi.esb.jaxb.metasfresh.XLSImpCOLCandType;

/**
 * Builds {@link XLSImpCOLCandType}s.
 *
 * @author tsa
 * @task 08839
 */
public class ExcelImpCOLCandTypeBuilder
{
	public static final ExcelImpCOLCandTypeBuilder newBuilder()
	{
		return new ExcelImpCOLCandTypeBuilder();
	}

	private final XLSImpCOLCandType olcand;

	private ExcelImpCOLCandTypeBuilder()
	{
		olcand = Constants.JAXB_ObjectFactory.createXLSImpCOLCandType();

		// Predefined
		olcand.setReplicationEventAttr(ReplicationEventEnum.AfterChange);
		olcand.setReplicationModeAttr(ReplicationModeEnum.Table);
		olcand.setReplicationTypeAttr(ReplicationTypeEnum.Merge);
		olcand.setVersionAttr(Constants.EXP_FORMAT_GENERIC_VERSION);
	}

	public XLSImpCOLCandType build()
	{
		return olcand;
	}

	public ExcelImpCOLCandTypeBuilder setFromContext(final ExcelConfigurationContext ctx)
	{
		// set ReplicationTrx attribute
		olcand.setTrxNameAttr(ctx.getCamelFileName());

		olcand.setADClientValueAttr(ctx.getAD_Client_Value());
		olcand.setADOrgID(toBigIntegerID(ctx.getAD_Org_ID()));

		// AD_DataDestination_ID lookup
		{
			final EDIImpADInputDataSourceLookupINType dataDestinationLookup = resolveGenericLookup(EDIImpADInputDataSourceLookupINType.class,
					Constants.LOOKUP_TEMPLATE_InternalName.createMandatoryValueLookup(ctx.getADInputDataDestination_InternalName()));
			olcand.setADDataDestinationID(dataDestinationLookup);
		}

		olcand.setADInputDataSourceID(ctx.getADInputDataSourceID());
		olcand.setADUserEnteredByID(ctx.getADUserEnteredByID());

		{
			final COrderDeliveryRuleEnum deliveryRule = COrderDeliveryRuleEnum.fromValue(ctx.getDeliveryRule());
			olcand.setDeliveryRule(deliveryRule);
		}

		{
			final COrderDeliveryViaRuleEnum deliveryViaRule = COrderDeliveryViaRuleEnum.fromValue(ctx.getDeliveryViaRule());
			olcand.setDeliveryViaRule(deliveryViaRule);
		}

		olcand.setCCurrencyID(currencyLookup(ctx.getCurrencyISOCode()));

		return this;
	}

	public ExcelImpCOLCandTypeBuilder setFromRow(final Excel_OLCand_Row row)
	{
		olcand.setLine(toBigIntegerOrNull(row.getLineNo()));
		olcand.setPOReference(row.getPOReference());

		//
		// Dates
		olcand.setDateCandidate(Util.toXMLCalendar(row.getDateCandidate()));
		olcand.setDatePromised(Util.toXMLCalendar(row.getDatePromised()));

		//
		// BPartner
		olcand.setCBPartnerID(toBigIntegerID(row.getC_BPartner_ID()));
		olcand.setCBPartnerLocationID(toBigIntegerID(row.getC_BPartner_Location_ID()));

		//
		// Product
		olcand.setMProductID(toBigIntegerID(row.getM_Product_ID()));
		olcand.setProductDescription(row.getProductDescription());
		olcand.setMProductPriceID(toBigIntegerID(row.getM_ProductPrice_ID()));
		olcand.setMHUPIItemProductID(toBigIntegerID(row.getM_HU_PI_Item_Product_ID()));

		// Product Price Attribute
		// NOTE: we shall always use explicit ProductPriceAttribute to make sure we would get the same Product Attributes when C_OLCand is imported
		olcand.setIsExplicitProductPriceAttribute("Y");
		olcand.setMProductPriceAttributeID(toBigIntegerID(row.getM_ProductPrice_Attribute_ID()));

		//
		// UOM
		{
			final String UOM_x12de355 = row.getUOM_x12de355();
			final EDIImpCUOMLookupUOMSymbolType uomLookup = resolveGenericLookup(EDIImpCUOMLookupUOMSymbolType.class,
					Constants.LOOKUP_TEMPLATE_X12DE355.createMandatoryValueLookup(UOM_x12de355));
			olcand.setCUOMID(uomLookup);
		}

		//
		// Quantities
		{
			final BigDecimal qtyCUsPerTU = row.getQtyCUsPerTU();
			olcand.setQtyItemCapacity(qtyCUsPerTU);

			if (row.getM_HU_PI_Item_Product_ID() > 0)
			{
				if (qtyCUsPerTU == null || qtyCUsPerTU.signum() <= 0)
				{
					throw new RuntimeException("LineNo=" + row.getLineNo() + ": if M_HU_PI_Item_Product_ID>0, then QtyCUsPerTU=" + qtyCUsPerTU + " has to be >0: ");
				}
				olcand.setQty(qtyCUsPerTU.multiply(row.getQtyUOM()));
			}
			else
			{
				olcand.setQty(row.getQtyUOM());
			}
		}

		//
		// Prices
		final BigDecimal priceEntered = row.getPrice();
		olcand.setPriceEntered(priceEntered);
		// Note that this price shall not be propagated to the order line, so C_OLCand.
		// IsManual shall not be set to Y.
		// We only need this price in metasfresh in order to detect discrepancies
		olcand.setIsManualPrice("N");

		//
		// Currency
		final String currencyISOCode = row.getCurrencyISOCode();
		if (currencyISOCode != null)
		{
			olcand.setCCurrencyID(currencyLookup(currencyISOCode));
		}

		return this;
	}

	private static final EDIImpCCurrencyLookupISOCodeType currencyLookup(final String currencyISOCode)
	{
		if (currencyISOCode == null)
		{
			return null;
		}

		final EDIImpCCurrencyLookupISOCodeType currencyLookup = new EDIImpCCurrencyLookupISOCodeType();
		currencyLookup.setISOCode(currencyISOCode);

		return currencyLookup;
	}

	private static final BigInteger toBigIntegerOrNull(final Integer valueInteger)
	{
		return valueInteger == null ? null : BigInteger.valueOf(valueInteger);
	}

	private static final BigInteger toBigIntegerID(final int id)
	{
		if (id <= 0)
		{
			return null;
		}
		return BigInteger.valueOf(id);
	}

}

/*
 * #%L
 * de-metas-camel-edi
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.edi.esb.invoicexport.metasfreshinhousev1;

import de.metas.edi.esb.jaxb.metasfreshinhousev1.CCreditMemoReasonEnum;
import de.metas.edi.esb.jaxb.metasfreshinhousev1.InvoicableQtyBasedOnEnum;
import de.metas.edi.esb.jaxb.metasfreshinhousev1.ObjectFactory;
import de.metas.edi.esb.jaxb.metasfreshinhousev1.ReplicationEventEnum;
import de.metas.edi.esb.jaxb.metasfreshinhousev1.ReplicationModeEnum;
import de.metas.edi.esb.jaxb.metasfreshinhousev1.ReplicationTypeEnum;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDICctop000VType;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDICctop111VType;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDICctop119VType;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDICctop120VType;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDICctop140VType;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDICctop901991VType;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDICctopInvoic500VType;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDICctopInvoicVType;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDIExpCUOMType;
import lombok.NonNull;
import org.apache.camel.Exchange;

import java.util.List;

public class MetasfreshInHouseV1XMLInvoicBean
{
	public static final String METHOD_createXMLEDIData = "createXMLEDIData";

	private static final ObjectFactory DESADV_objectFactory = new ObjectFactory();

	public final void createXMLEDIData(@NonNull final Exchange exchange)
	{
		final EDICctopInvoicVType source = exchange.getIn().getBody(EDICctopInvoicVType.class);

		final de.metas.edi.esb.jaxb.metasfreshinhousev1.EDICctopInvoicVType target = DESADV_objectFactory.createEDICctopInvoicVType();

		target.setCBPartnerLocationID(source.getCBPartnerLocationID());
		target.setCInvoiceID(source.getCInvoiceID());

		if (source.getCreditMemoReason() != null)
		{
			target.setCreditMemoReason(CCreditMemoReasonEnum.fromValue(source.getCreditMemoReason().value()));
		}

		target.setDateInvoiced(source.getDateInvoiced());
		target.setDateOrdered(source.getDateOrdered());
		target.setEancomDoctype(source.getEancomDoctype());
		target.setEDICctopInvoicVID(source.getEDICctopInvoicVID());
		target.setGrandTotal(source.getGrandTotal());
		target.setSurchargeAmt(source.getSurchargeAmt());
		target.setTotalLinesWithSurchargeAmt(source.getTotalLinesWithSurchargeAmt());
		target.setInvoiceDocumentno(source.getInvoiceDocumentno());
		target.setISOCode(source.getISOCode());
		target.setMovementDate(source.getMovementDate());
		target.setPOReference(source.getPOReference());
		target.setReceiverGLN(source.getReceivergln());
		target.setSenderGLN(source.getSendergln());
		target.setShipmentDocumentno(source.getShipmentDocumentno());
		target.setEDIDesadvDocumentNo(source.getEDIDesadvDocumentNo());
		target.setTotalLines(source.getTotalLines());
		target.setTotalTaxBaseAmt(source.getTotalTaxBaseAmt());
		target.setTotalVat(source.getTotalVat());

		if (source.getEDICctop000V() != null)
		{
			target.setEDICctop000V(getEDICctop000V(source.getEDICctop000V()));
		}

		if (source.getEDICctop111V() != null)
		{
			target.setEDICctop111V(getEDICctop111VType(source.getEDICctop111V()));
		}

		if (source.getEDICctop119V() != null)
		{
			final List<de.metas.edi.esb.jaxb.metasfreshinhousev1.EDICctop119VType> targetList = target.getEDICctop119V();
			for (final EDICctop119VType pack : source.getEDICctop119V())
			{
				targetList.add(getEDICctop119VType(pack));
			}
		}

		if (source.getEDICctop120V() != null)
		{
			final List<de.metas.edi.esb.jaxb.metasfreshinhousev1.EDICctop120VType> targetList = target.getEDICctop120V();
			for (final EDICctop120VType pack : source.getEDICctop120V())
			{
				targetList.add(getEDICctop120VType(pack));
			}
		}

		if (source.getEDICctop140V() != null)
		{
			final List<de.metas.edi.esb.jaxb.metasfreshinhousev1.EDICctop140VType> targetList = target.getEDICctop140V();
			for (final EDICctop140VType pack : source.getEDICctop140V())
			{
				targetList.add(getEDICctop140VType(pack));
			}
		}

		if (source.getEDICctopInvoic500V() != null)
		{
			final List<de.metas.edi.esb.jaxb.metasfreshinhousev1.EDICctopInvoic500VType> targetList = target.getEDICctopInvoic500V();
			for (final EDICctopInvoic500VType pack : source.getEDICctopInvoic500V())
			{
				targetList.add(getEDICctopInvoic500VType(pack));
			}
		}

		if (source.getEDICctop901991V() != null)
		{
			final List<de.metas.edi.esb.jaxb.metasfreshinhousev1.EDICctop901991VType> targetList = target.getEDICctop901991V();
			for (final EDICctop901991VType pack : source.getEDICctop901991V())
			{
				targetList.add(getEDICctop901991VType(pack));
			}
		}

		target.setCountryCode(source.getCountryCode());
		target.setCountryCode3Digit(source.getCountryCode3Digit());
		target.setCreditMemoReasonText(source.getCreditMemoReasonText());
		target.setVATaxID(source.getVATaxID());

		if (source.getInvoicableQtyBasedOn() != null)
		{
			target.setInvoicableQtyBasedOn(InvoicableQtyBasedOnEnum.fromValue(source.getInvoicableQtyBasedOn().value()));
		}

		target.setTotalVatWithSurchargeAmt(source.getTotalVatWithSurchargeAmt());
		target.setGrandTotalWithSurchargeAmt(source.getGrandTotalWithSurchargeAmt());

		target.setADClientValueAttr(source.getADClientValueAttr());

		if (source.getReplicationEventAttr() != null)
		{
			target.setReplicationEventAttr(ReplicationEventEnum.fromValue(source.getReplicationEventAttr().value()));
		}

		if (source.getReplicationModeAttr() != null)
		{
			target.setReplicationModeAttr(ReplicationModeEnum.fromValue(source.getReplicationModeAttr().value()));
		}

		if (source.getReplicationTypeAttr() != null)
		{
			target.setReplicationTypeAttr(ReplicationTypeEnum.fromValue(source.getReplicationTypeAttr().value()));
		}

		target.setVersionAttr(source.getVersionAttr());
		target.setSequenceNoAttr(source.getSequenceNoAttr());
		target.setTrxNameAttr(source.getTrxNameAttr());
		target.setADSessionIDAttr(source.getADSessionIDAttr());

		exchange.getIn().setBody(target);
	}

	@NonNull
	private de.metas.edi.esb.jaxb.metasfreshinhousev1.EDICctop901991VType getEDICctop901991VType(@NonNull final EDICctop901991VType source)
	{
		final de.metas.edi.esb.jaxb.metasfreshinhousev1.EDICctop901991VType target = DESADV_objectFactory.createEDICctop901991VType();

		target.setCInvoiceID(source.getCInvoiceID());
		target.setEDICctop901991VID(source.getEDICctop901991VID());
		target.setRate(source.getRate());
		target.setTaxAmt(source.getTaxAmt());
		target.setIsTaxExempt(source.getIsTaxExempt());
		target.setTaxBaseAmt(source.getTaxBaseAmt());
		target.setTotalAmt(source.getTotalAmt());
		target.setReferenceNo(source.getReferenceNo());
		target.setSurchargeAmt(source.getSurchargeAmt());
		target.setTaxBaseAmtWithSurchargeAmt(source.getTaxBaseAmtWithSurchargeAmt());
		target.setTaxAmtWithSurchargeAmt(source.getTaxAmtWithSurchargeAmt());
		target.setIsMainVAT(source.getIsMainVAT());

		return target;
	}

	@NonNull
	private de.metas.edi.esb.jaxb.metasfreshinhousev1.EDICctopInvoic500VType getEDICctopInvoic500VType(@NonNull final EDICctopInvoic500VType source)
	{
		final de.metas.edi.esb.jaxb.metasfreshinhousev1.EDICctopInvoic500VType target = DESADV_objectFactory.createEDICctopInvoic500VType();

		target.setCInvoiceID(source.getCInvoiceID());
		target.setEanComUOM(source.getEanComUOM());
		target.setEDICctopInvoic500VID(source.getEDICctopInvoic500VID());
		target.setISOCode(source.getISOCode());
		target.setLine(source.getLine());
		target.setLineNetAmt(source.getLineNetAmt());
		target.setDiscount(source.getDiscount());
		target.setName(source.getName());
		target.setName2(source.getName2());
		target.setPriceActual(source.getPriceActual());
		target.setPriceList(source.getPriceList());
		target.setQtyInvoiced(source.getQtyInvoiced());
		target.setRate(source.getRate());
		target.setTaxfree(source.getTaxfree());
		target.setIsTaxExempt(source.getIsTaxExempt());
		target.setValue(source.getValue());
		target.setCustomerProductNo(source.getCustomerProductNo());
		target.setEDICctopInvoicVID(source.getEDICctopInvoicVID());
		target.setLeergut(source.getLeergut());
		target.setProductDescription(source.getProductDescription());
		target.setOrderPOReference(source.getOrderPOReference());
		target.setOrderLine(source.getOrderLine());
		target.setQtyInvoicedInOrderedUOM(source.getQtyInvoicedInOrderedUOM());
		target.setEanComOrderedUOM(source.getEanComOrderedUOM());
		target.setTaxAmtInfo(source.getTaxAmtInfo());
		target.setEanComPriceUOM(source.getEanComPriceUOM());
		target.setEANCU(source.getEANCU());
		target.setEANTU(source.getEANTU());
		target.setGTIN(source.getGTIN());
		target.setUPCCU(source.getUPCCU());
		target.setUPCTU(source.getUPCTU());
		target.setBuyerGTINTU(source.getBuyerGTINTU());
		target.setBuyerGTINCU(source.getBuyerGTINCU());
		target.setBuyerEANCU(source.getBuyerEANCU());
		target.setSupplierGTINCU(source.getSupplierGTINCU());

		if (source.getInvoicableQtyBasedOn() != null)
		{
			target.setInvoicableQtyBasedOn(InvoicableQtyBasedOnEnum.fromValue(source.getInvoicableQtyBasedOn().value()));
		}

		target.setQtyEnteredInBPartnerUOM(source.getQtyEnteredInBPartnerUOM());
		target.setExternalSeqNo(source.getExternalSeqNo());

		if (source.getCUOMBPartnerID() != null)
		{
			target.setCUOMBPartnerID(getEDIExpCUOMType(source.getCUOMBPartnerID()));
		}

		return target;
	}

	@NonNull
	private de.metas.edi.esb.jaxb.metasfreshinhousev1.EDIExpCUOMType getEDIExpCUOMType(@NonNull final EDIExpCUOMType source)
	{
		final de.metas.edi.esb.jaxb.metasfreshinhousev1.EDIExpCUOMType target = DESADV_objectFactory.createEDIExpCUOMType();

		target.setName(source.getName());
		target.setUOMSymbol(source.getUOMSymbol());
		target.setX12DE355(source.getX12DE355());

		return target;
	}

	@NonNull
	private de.metas.edi.esb.jaxb.metasfreshinhousev1.EDICctop140VType getEDICctop140VType(@NonNull final EDICctop140VType source)
	{
		final de.metas.edi.esb.jaxb.metasfreshinhousev1.EDICctop140VType target = DESADV_objectFactory.createEDICctop140VType();

		target.setCInvoiceID(source.getCInvoiceID());
		target.setDiscount(source.getDiscount());
		target.setDiscountDate(source.getDiscountDate());
		target.setDiscountDays(source.getDiscountDays());
		target.setEDICctop140VID(source.getEDICctop140VID());
		target.setRate(source.getRate());
		target.setName(source.getName());
		target.setDiscountAmt(source.getDiscountAmt());
		target.setDiscountBaseAmt(source.getDiscountBaseAmt());

		return target;
	}

	@NonNull
	private de.metas.edi.esb.jaxb.metasfreshinhousev1.EDICctop120VType getEDICctop120VType(@NonNull final EDICctop120VType source)
	{
		final de.metas.edi.esb.jaxb.metasfreshinhousev1.EDICctop120VType target = DESADV_objectFactory.createEDICctop120VType();

		target.setCInvoiceID(source.getCInvoiceID());
		target.setEDICctop120VID(source.getEDICctop120VID());
		target.setISOCode(source.getISOCode());
		target.setNetdate(source.getNetdate());
		target.setNetDays(source.getNetDays());
		target.setSinglevat(source.getSinglevat());
		target.setTaxfree(source.getTaxfree());

		return target;
	}

	@NonNull
	private de.metas.edi.esb.jaxb.metasfreshinhousev1.EDICctop119VType getEDICctop119VType(@NonNull final EDICctop119VType source)
	{
		final de.metas.edi.esb.jaxb.metasfreshinhousev1.EDICctop119VType target = DESADV_objectFactory.createEDICctop119VType();

		target.setAddress1(source.getAddress1());
		target.setAddress2(source.getAddress2());
		target.setCBPartnerLocationID(source.getCBPartnerLocationID());
		target.setCInvoiceID(source.getCInvoiceID());
		target.setCity(source.getCity());
		target.setCountryCode(source.getCountryCode());
		target.setEancomLocationtype(source.getEancomLocationtype());
		target.setEDICctop119VID(source.getEDICctop119VID());
		target.setFax(source.getFax());
		target.setGLN(source.getGLN());
		target.setMInOutID(source.getMInOutID());
		target.setName(source.getName());
		target.setName2(source.getName2());
		target.setPhone(source.getPhone());
		target.setPostal(source.getPostal());
		target.setValue(source.getValue());
		target.setVATaxID(source.getVATaxID());
		target.setReferenceNo(source.getReferenceNo());
		target.setSetupPlaceNo(source.getSetupPlaceNo());
		target.setSiteName(source.getSiteName());
		target.setContact(source.getContact());

		return target;
	}

	@NonNull
	private de.metas.edi.esb.jaxb.metasfreshinhousev1.EDICctop111VType getEDICctop111VType(@NonNull final EDICctop111VType source)
	{
		final de.metas.edi.esb.jaxb.metasfreshinhousev1.EDICctop111VType target = DESADV_objectFactory.createEDICctop111VType();

		target.setCOrderID(source.getCOrderID());
		target.setDateOrdered(source.getDateOrdered());
		target.setEDICctop111VID(source.getEDICctop111VID());
		target.setMInOutID(source.getMInOutID());
		target.setMovementDate(source.getMovementDate());
		target.setPOReference(source.getPOReference());
		target.setShipmentDocumentno(source.getShipmentDocumentno());

		return target;
	}

	@NonNull
	private de.metas.edi.esb.jaxb.metasfreshinhousev1.EDICctop000VType getEDICctop000V(@NonNull final EDICctop000VType source)
	{
		final de.metas.edi.esb.jaxb.metasfreshinhousev1.EDICctop000VType target = DESADV_objectFactory.createEDICctop000VType();

		target.setCBPartnerLocationID(source.getCBPartnerLocationID());
		target.setEDICctop000VID(source.getEDICctop000VID());
		target.setGLN(source.getGLN());

		return target;
	}
}

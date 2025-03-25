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

package de.metas.edi.esb.desadvexport.metasfreshinhousev1;

import de.metas.edi.esb.jaxb.metasfreshinhousev1.COrderDeliveryViaRuleEnum;
import de.metas.edi.esb.jaxb.metasfreshinhousev1.EDIExportStatusEnum;
import de.metas.edi.esb.jaxb.metasfreshinhousev1.FreshCCOUNTRYLOOKUPCOUNTRYCODEType;
import de.metas.edi.esb.jaxb.metasfreshinhousev1.InvoicableQtyBasedOnEnum;
import de.metas.edi.esb.jaxb.metasfreshinhousev1.ObjectFactory;
import de.metas.edi.esb.jaxb.metasfreshinhousev1.ReplicationEventEnum;
import de.metas.edi.esb.jaxb.metasfreshinhousev1.ReplicationModeEnum;
import de.metas.edi.esb.jaxb.metasfreshinhousev1.ReplicationTypeEnum;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDIExpCBPartnerLocationType;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDIExpCBPartnerType;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDIExpCCurrencyType;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDIExpCLocationType;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDIExpCUOMType;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDIExpDesadvLineType;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDIExpDesadvLineWithNoPackType;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDIExpDesadvPackItemType;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDIExpDesadvPackType;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDIExpDesadvType;
import de.metas.edi.esb.jaxb.metasfreshinhousev2.EDIExpMProductType;
import lombok.NonNull;
import org.apache.camel.Exchange;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class MetasfreshInHouseV1XMLDesadvBean
{
	public static final String METHOD_createXMLEDIData = "createXMLEDIData";

	private static final ObjectFactory DESADV_objectFactory = new ObjectFactory();

	public final void createXMLEDIData(@NonNull final Exchange exchange)
	{
		final EDIExpDesadvType source = exchange.getIn().getBody(EDIExpDesadvType.class);

		final de.metas.edi.esb.jaxb.metasfreshinhousev1.EDIExpDesadvType target = DESADV_objectFactory.createEDIExpDesadvType();
		if (source.getCBPartnerID() != null)
		{
			target.setCBPartnerID(getCBPartnerID(source.getCBPartnerID()));
		}

		if (source.getCBPartnerLocationID() != null)
		{
			target.setCBPartnerLocationID(getEDIExpCBPartnerLocationType(source.getCBPartnerLocationID()));
		}

		target.setDateOrdered(source.getDateOrdered());
		target.setDocumentNo(source.getDocumentNo());
		target.setEDIDesadvID(source.getEDIDesadvID());

		if (source.getEDIExportStatus() != null)
		{
			target.setEDIExportStatus(EDIExportStatusEnum.fromValue(source.getEDIExportStatus().value()));
		}

		target.setMovementDate(source.getMovementDate());
		target.setPOReference(source.getPOReference());

		if (source.getEDIExpDesadvPack() != null)
		{
			final List<de.metas.edi.esb.jaxb.metasfreshinhousev1.EDIExpDesadvPackType> targetList = target.getEDIExpDesadvPack();
			for (final EDIExpDesadvPackType pack : source.getEDIExpDesadvPack())
			{
				targetList.add(getEDIExpDesadvPackType(pack));
			}
		}

		if (source.getBillLocationID() != null)
		{
			target.setBillLocationID(getEDIExpCBPartnerLocationType(source.getBillLocationID()));
		}

		if (source.getCCurrencyID() != null)
		{
			target.setCCurrencyID(getEDIExpCCurrencyType(source.getCCurrencyID()));
		}

		if (source.getHandOverLocationID() != null)
		{
			target.setHandOverLocationID(getEDIExpCBPartnerLocationType(source.getHandOverLocationID()));
		}

		if (source.getDropShipBPartnerID() != null)
		{
			target.setDropShipBPartnerID(getCBPartnerID(source.getDropShipBPartnerID()));
		}

		if (source.getDropShipLocationID() != null)
		{
			target.setDropShipLocationID(getEDIExpCBPartnerLocationType(source.getDropShipLocationID()));
		}

		if (source.getHandOverPartnerID() != null)
		{
			target.setHandOverPartnerID(getCBPartnerID(source.getHandOverPartnerID()));
		}

		if (source.getInvoicableQtyBasedOn() != null)
		{
			target.setInvoicableQtyBasedOn(InvoicableQtyBasedOnEnum.fromValue(source.getInvoicableQtyBasedOn().value()));
		}

		if (source.getDeliveryViaRule() != null)
		{
			target.setDeliveryViaRule(COrderDeliveryViaRuleEnum.fromValue(source.getDeliveryViaRule().value()));
		}

		if (source.getEDIExpDesadvLineWithNoPack() != null)
		{
			final List<de.metas.edi.esb.jaxb.metasfreshinhousev1.EDIExpDesadvLineWithNoPackType> targetLines = target.getEDIExpDesadvLineWithNoPack();
			for (final EDIExpDesadvLineWithNoPackType line : source.getEDIExpDesadvLineWithNoPack())
			{
				targetLines.add(getEDIExpDesadvLineWithNoPackType(line));
			}
		}

		target.setShipmentDocumentno(source.getShipmentDocumentno());
		target.setDatePromised(source.getDatePromised());
		target.setADClientValueAttr(source.getADClientValueAttr());
		target.setReplicationEventAttr(getReplicationEventAttr(source.getReplicationEventAttr()));
		target.setReplicationModeAttr(getReplicationModeAttr(source.getReplicationModeAttr()));
		target.setReplicationTypeAttr(getReplicationTypeAttr(source.getReplicationTypeAttr()));

		target.setVersionAttr(source.getVersionAttr());
		target.setSequenceNoAttr(source.getSequenceNoAttr());
		target.setTrxNameAttr(source.getTrxNameAttr());
		target.setADSessionIDAttr(source.getADSessionIDAttr());

		exchange.getIn().setBody(target);
	}

	@NonNull
	private de.metas.edi.esb.jaxb.metasfreshinhousev1.EDIExpDesadvLineWithNoPackType getEDIExpDesadvLineWithNoPackType(@NonNull final EDIExpDesadvLineWithNoPackType source)
	{
		final de.metas.edi.esb.jaxb.metasfreshinhousev1.EDIExpDesadvLineWithNoPackType target = DESADV_objectFactory.createEDIExpDesadvLineWithNoPackType();

		if (source.getEDIDesadvLineID() != null)
		{
			target.setEDIDesadvLineID(getEDIExpDesadvLineType(source.getEDIDesadvLineID()));
		}

		return target;
	}

	@NonNull
	private de.metas.edi.esb.jaxb.metasfreshinhousev1.EDIExpCCurrencyType getEDIExpCCurrencyType(@NonNull final EDIExpCCurrencyType source)
	{
		final de.metas.edi.esb.jaxb.metasfreshinhousev1.EDIExpCCurrencyType target = DESADV_objectFactory.createEDIExpCCurrencyType();

		target.setCurSymbol(source.getCurSymbol());
		target.setISO4217Numeric(source.getISO4217Numeric());
		target.setISOCode(source.getISOCode());
		target.setADClientValueAttr(source.getADClientValueAttr());

		target.setReplicationEventAttr(getReplicationEventAttr(source.getReplicationEventAttr()));
		target.setReplicationModeAttr(getReplicationModeAttr(source.getReplicationModeAttr()));
		target.setReplicationTypeAttr(getReplicationTypeAttr(source.getReplicationTypeAttr()));

		target.setVersionAttr(getVersionAttr(source.getVersionAttr()));
		target.setSequenceNoAttr(source.getSequenceNoAttr());
		target.setTrxNameAttr(source.getTrxNameAttr());
		target.setADSessionIDAttr(source.getADSessionIDAttr());

		return target;
	}

	@NonNull
	private de.metas.edi.esb.jaxb.metasfreshinhousev1.EDIExpCBPartnerLocationType getEDIExpCBPartnerLocationType(@NonNull final EDIExpCBPartnerLocationType source)
	{
		final de.metas.edi.esb.jaxb.metasfreshinhousev1.EDIExpCBPartnerLocationType target = DESADV_objectFactory.createEDIExpCBPartnerLocationType();

		target.setGLN(source.getGLN());
		target.setName(source.getName());
		target.setSetupPlaceNo(source.getSetupPlaceNo());

		if (source.getCLocationID() != null)
		{
			target.setCLocationID(getEDIExpCLocationType(source.getCLocationID()));
		}

		target.setADClientValueAttr(source.getADClientValueAttr());
		target.setReplicationEventAttr(getReplicationEventAttr(source.getReplicationEventAttr()));
		target.setReplicationModeAttr(getReplicationModeAttr(source.getReplicationModeAttr()));
		target.setReplicationTypeAttr(getReplicationTypeAttr(source.getReplicationTypeAttr()));
		target.setVersionAttr(getVersionAttr(source.getVersionAttr()));
		target.setSequenceNoAttr(source.getSequenceNoAttr());
		target.setTrxNameAttr(source.getTrxNameAttr());

		target.setADSessionIDAttr(source.getADSessionIDAttr());

		return target;
	}

	@NonNull
	private de.metas.edi.esb.jaxb.metasfreshinhousev1.EDIExpDesadvPackType getEDIExpDesadvPackType(@NonNull final EDIExpDesadvPackType source)
	{
		final de.metas.edi.esb.jaxb.metasfreshinhousev1.EDIExpDesadvPackType target = DESADV_objectFactory.createEDIExpDesadvPackType();

		target.setLine(source.getSeqNo());
		target.setGTINLUPackingMaterial(source.getGTINPackingMaterial());
		target.setIPASSCC18(source.getIPASSCC18());
		target.setMHUPackagingCodeLUText(source.getMHUPackagingCodeText());

		if (source.getEDIExpDesadvPackItem() != null)
		{
			final List<de.metas.edi.esb.jaxb.metasfreshinhousev1.EDIExpDesadvPackItemType> targetList = target.getEDIExpDesadvPackItem();
			for (final EDIExpDesadvPackItemType packItem : source.getEDIExpDesadvPackItem())
			{
				targetList.add(getEDIExpDesadvPackItemType(packItem));
			}
		}

		return target;
	}

	@NonNull
	private de.metas.edi.esb.jaxb.metasfreshinhousev1.EDIExpDesadvPackItemType getEDIExpDesadvPackItemType(@NonNull final EDIExpDesadvPackItemType source)
	{
		final de.metas.edi.esb.jaxb.metasfreshinhousev1.EDIExpDesadvPackItemType target = DESADV_objectFactory.createEDIExpDesadvPackItemType();

		target.setBestBeforeDate(source.getBestBeforeDate());
		target.setLotNumber(source.getLotNumber());
		target.setQtyCUsPerTU(source.getQtyCUsPerTU());
		target.setQtyCUsPerLU(source.getQtyCUsPerLU());
		target.setQtyCUsPerLUInInvoiceUOM(source.getQtyCUsPerLUInInvoiceUOM());
		target.setQtyCUsPerTUInInvoiceUOM(source.getQtyCUsPerTUInInvoiceUOM());
		target.setQtyTU(source.getQtyTU());

		if (source.getEDIDesadvLineID() != null)
		{
			target.setEDIDesadvLineID(getEDIExpDesadvLineType(source.getEDIDesadvLineID()));
		}

		target.setGTINTUPackingMaterial(source.getGTINTUPackingMaterial());
		target.setMHUPackagingCodeTUText(source.getMHUPackagingCodeTUText());

		return target;
	}

	@NonNull
	private de.metas.edi.esb.jaxb.metasfreshinhousev1.EDIExpDesadvLineType getEDIExpDesadvLineType(@NonNull final EDIExpDesadvLineType source)
	{
		final de.metas.edi.esb.jaxb.metasfreshinhousev1.EDIExpDesadvLineType target = DESADV_objectFactory.createEDIExpDesadvLineType();

		if (source.getCUOMID() != null)
		{
			target.setCUOMID(getEDIExpCUOMType(source.getCUOMID()));
		}

		target.setEDIDesadvLineID(source.getEDIDesadvLineID());
		target.setLine(source.getLine());

		if (source.getMProductID() != null)
		{
			target.setMProductID(getEDIExpMProductType(source.getMProductID()));
		}

		target.setProductDescription(source.getProductDescription());
		target.setProductNo(source.getProductNo());
		target.setQtyEntered(source.getQtyEntered());
		target.setUPC(source.getUPCCU());
		target.setIsSubsequentDeliveryPlanned(source.getIsSubsequentDeliveryPlanned());
		target.setEANCU(source.getEANCU());
		target.setGTIN(source.getGTINCU());
		target.setEANTU(source.getEANTU());
		target.setPriceActual(source.getPriceActual());
		target.setEanComInvoiceUOM(source.getEanComInvoiceUOM());
		if (source.getInvoicableQtyBasedOn() != null)
		{
			target.setInvoicableQtyBasedOn(InvoicableQtyBasedOnEnum.valueOf(source.getInvoicableQtyBasedOn().value()));
		}

		target.setOrderPOReference(source.getOrderPOReference());
		target.setQtyItemCapacity(source.getQtyItemCapacity());
		target.setQtyDeliveredInInvoiceUOM(source.getQtyDeliveredInInvoiceUOM());
		target.setQtyDeliveredInUOM(source.getQtyDeliveredInUOM());
		target.setOrderLine(source.getOrderLine());
		target.setExternalSeqNo(source.getExternalSeqNo());

		if (source.getCUOMBPartnerID() != null)
		{
			target.setCUOMBPartnerID(getEDIExpCUOMType(source.getCUOMBPartnerID()));
		}

		target.setQtyEnteredInBPartnerUOM(source.getQtyEnteredInBPartnerUOM());
		target.setBPartnerQtyItemCapacity(source.getBPartnerQtyItemCapacity());
		target.setUPCTU(source.getUPCTU());

		return target;
	}

	@NonNull
	private de.metas.edi.esb.jaxb.metasfreshinhousev1.EDIExpMProductType getEDIExpMProductType(@NonNull final EDIExpMProductType source)
	{
		final de.metas.edi.esb.jaxb.metasfreshinhousev1.EDIExpMProductType target = DESADV_objectFactory.createEDIExpMProductType();

		target.setName(source.getName());
		target.setValue(source.getValue());
		target.setVolume(source.getVolume());
		target.setWeight(source.getWeight());
		target.setADClientValueAttr(source.getADClientValueAttr());

		target.setReplicationEventAttr(getReplicationEventAttr(source.getReplicationEventAttr()));
		target.setReplicationModeAttr(getReplicationModeAttr(source.getReplicationModeAttr()));
		target.setReplicationTypeAttr(getReplicationTypeAttr(source.getReplicationTypeAttr()));

		target.setVersionAttr(getVersionAttr(source.getVersionAttr()));
		target.setSequenceNoAttr(source.getSequenceNoAttr());
		target.setTrxNameAttr(source.getTrxNameAttr());
		target.setADSessionIDAttr(source.getADSessionIDAttr());

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
	private de.metas.edi.esb.jaxb.metasfreshinhousev1.EDIExpCBPartnerType getCBPartnerID(@NonNull final EDIExpCBPartnerType source)
	{
		final de.metas.edi.esb.jaxb.metasfreshinhousev1.EDIExpCBPartnerType target = DESADV_objectFactory.createEDIExpCBPartnerType();
		target.setEdiRecipientGLN(source.getEdiRecipientGLN());
		target.setName(source.getName());
		target.setName2(source.getName2());
		target.setValue(source.getValue());
		target.setADClientValueAttr(source.getADClientValueAttr());

		target.setReplicationEventAttr(getReplicationEventAttr(source.getReplicationEventAttr()));
		target.setReplicationModeAttr(getReplicationModeAttr(source.getReplicationModeAttr()));
		target.setReplicationTypeAttr(getReplicationTypeAttr(source.getReplicationTypeAttr()));

		target.setVersionAttr(getVersionAttr(source.getVersionAttr()));
		target.setSequenceNoAttr(source.getSequenceNoAttr());
		target.setTrxNameAttr(source.getTrxNameAttr());
		target.setADSessionIDAttr(source.getADSessionIDAttr());

		return target;
	}

	@NonNull
	private de.metas.edi.esb.jaxb.metasfreshinhousev1.EDIExpCLocationType getEDIExpCLocationType(@NonNull final EDIExpCLocationType source)
	{
		final de.metas.edi.esb.jaxb.metasfreshinhousev1.EDIExpCLocationType target = DESADV_objectFactory.createEDIExpCLocationType();

		target.setAddress1(source.getAddress1());
		target.setAddress2(source.getAddress2());
		target.setAddress3(source.getAddress3());
		target.setAddress4(source.getAddress4());
		if (source.getCCountryID() != null)
		{
			final FreshCCOUNTRYLOOKUPCOUNTRYCODEType countryId = DESADV_objectFactory.createFreshCCOUNTRYLOOKUPCOUNTRYCODEType();

			countryId.setCountryCode(source.getCCountryID().getCountryCode());

			target.setCCountryID(countryId);
		}

		target.setCity(source.getCity());
		target.setPostal(source.getPostal());
		target.setRegionName(source.getRegionName());

		target.setADClientValueAttr(source.getADClientValueAttr());
		target.setReplicationEventAttr(getReplicationEventAttr(source.getReplicationEventAttr()));
		target.setReplicationModeAttr(getReplicationModeAttr(source.getReplicationModeAttr()));
		target.setReplicationTypeAttr(getReplicationTypeAttr(source.getReplicationTypeAttr()));

		target.setVersionAttr(getVersionAttr(source.getVersionAttr()));
		target.setSequenceNoAttr(source.getSequenceNoAttr());
		target.setTrxNameAttr(source.getTrxNameAttr());
		target.setADSessionIDAttr(source.getADSessionIDAttr());

		return target;
	}

	@Nullable
	private String getVersionAttr(@Nullable final String sourceVersionAttr)
	{
		if (sourceVersionAttr == null)
		{
			return null;
		}

		if ("*".equals(sourceVersionAttr))
		{
			return null;
		}

		return sourceVersionAttr;
	}


	@Nullable
	private ReplicationTypeEnum getReplicationTypeAttr(@Nullable final de.metas.edi.esb.jaxb.metasfreshinhousev2.ReplicationTypeEnum replicationType)
	{
		return Optional.ofNullable(replicationType)
				.map(attr -> ReplicationTypeEnum.fromValue(attr.value()))
				.orElse(null);
	}

	@Nullable
	private ReplicationModeEnum getReplicationModeAttr(@Nullable final de.metas.edi.esb.jaxb.metasfreshinhousev2.ReplicationModeEnum replicationModeAttr)
	{
		return Optional.ofNullable(replicationModeAttr)
				.map(attr -> ReplicationModeEnum.fromValue(attr.value()))
				.orElse(null);
	}

	@Nullable
	private ReplicationEventEnum getReplicationEventAttr(@Nullable final de.metas.edi.esb.jaxb.metasfreshinhousev2.ReplicationEventEnum replicationEventAttr)
	{
		return Optional.ofNullable(replicationEventAttr)
				.map(attr -> ReplicationEventEnum.fromValue(attr.value()))
				.orElse(null);
	}
}

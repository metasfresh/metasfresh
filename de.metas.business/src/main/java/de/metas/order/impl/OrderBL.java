package de.metas.order.impl;

import static de.metas.util.lang.CoalesceUtil.coalesce;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryAggregateBuilder;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.LegacyAdapters;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerDAO.BPartnerLocationQuery;
import de.metas.bpartner.service.IBPartnerDAO.BPartnerLocationQuery.Type;
import de.metas.bpartner.service.IBPartnerBL.RetrieveContactRequest;
import de.metas.bpartner.service.IBPartnerBL.RetrieveContactRequest.RetrieveContactRequestBuilder;
import de.metas.currency.CurrencyPrecision;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeBL;
import de.metas.document.IDocTypeDAO;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.ITranslatableString;
import de.metas.interfaces.I_C_BPartner;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.order.BPartnerOrderParams;
import de.metas.order.BPartnerOrderParamsRepository;
import de.metas.order.BPartnerOrderParamsRepository.BPartnerOrderParamsQuery;
import de.metas.order.DeliveryViaRule;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.exceptions.PriceListNotFoundException;
import de.metas.pricing.service.IPriceListBL;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.user.User;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.CoalesceUtil;
import lombok.NonNull;

public class OrderBL implements IOrderBL
{
	private static final transient Logger logger = LogManager.getLogger(OrderBL.class);

	@Override
	public I_C_Order getById(@NonNull final OrderId orderId)
	{
		return Services.get(IOrderDAO.class).getById(orderId);
	}

	@Override
	public void setM_PricingSystem_ID(final I_C_Order order, final boolean overridePricingSystemAndDontThrowExIfNotFound)
	{
		final int previousPricingSystemId = order.getM_PricingSystem_ID();

		final boolean overridePricingSystem = overridePricingSystemAndDontThrowExIfNotFound;
		if (overridePricingSystem || previousPricingSystemId <= 0)
		{
			final BPartnerLocationId bpartnerAndLocation = getShipToLocationIdOrNull(order);
			if (bpartnerAndLocation == null)
			{
				logger.debug("Order {} has no C_BPartner_ID. Doing nothing", order);
				return;
			}

			final IBPartnerDAO bpartnersDAO = Services.get(IBPartnerDAO.class);
			final BPartnerId bpartnerId = bpartnerAndLocation.getBpartnerId();
			final SOTrx soTrx = SOTrx.ofBoolean(order.isSOTrx());
			final PricingSystemId pricingSysId = bpartnersDAO.retrievePricingSystemIdOrNull(bpartnerId, soTrx);

			final boolean throwExIfNotFound = !overridePricingSystemAndDontThrowExIfNotFound;
			if (pricingSysId == null && throwExIfNotFound)
			{
				final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
				final String bpartnerName = bpartnerBL.getBPartnerValueAndName(bpartnerId);
				Check.errorIf(true, "Unable to find pricing system for BPartner {}_{}; SOTrx={}", bpartnerName, soTrx);
			}

			order.setM_PricingSystem_ID(PricingSystemId.toRepoId(pricingSysId));
		}

		//
		// Update the M_PriceList_ID only if:
		// * overridePricingSystem is true => this is also covering the case when pricing system was not changed but for some reason the price list could be a different one (Date changed etc)
		// * pricing system really changed => we need to set to correct price list
		// Cases we want to avoid:
		// * overridePriceSystem is false and M_PricingSystem_ID was not changed: in this case we shall NOT update the price list because it might be that we were called for a completed Order and we don't want to change the data.
		if (overridePricingSystemAndDontThrowExIfNotFound || previousPricingSystemId != order.getM_PricingSystem_ID()
				|| order.getM_PriceList_ID() <= 0 // gh #936: attempt to set the pricelist, if we don't have it yet (i don't understand the error, but this might solve it. going to try it out)
		)
		{
			setPriceList(order);
		}
	}

	@Override
	public void setPriceList(final I_C_Order order)
	{
		final PricingSystemId pricingSystemId = PricingSystemId.ofRepoIdOrNull(order.getM_PricingSystem_ID());
		if (pricingSystemId == null)
		{
			logger.debug("order {} has no M_PricingSystem_ID. Doing nothing", order);
			return;
		}

		final BPartnerLocationId bpartnerAndLocationId = getShipToLocationIdOrNull(order);
		if (bpartnerAndLocationId == null)
		{
			logger.debug("order {} has no C_BPartner_Location_ID. Doing nothing", order);
			return;
		}

		final SOTrx soTrx = SOTrx.ofBoolean(order.isSOTrx());
		final PriceListId priceListId = retrievePriceListIdOrNull(pricingSystemId, bpartnerAndLocationId, soTrx);
		if (priceListId == null)
		{
			// Fail if no price list found
			final String pricingSystemName = Services.get(IPriceListDAO.class).getPricingSystemName(pricingSystemId);
			throw new PriceListNotFoundException(pricingSystemName, soTrx);
		}

		order.setM_PriceList_ID(priceListId.getRepoId());
	}

	@Override
	public void checkForPriceList(final I_C_Order order)
	{
		final PricingSystemId pricingSystemId = PricingSystemId.ofRepoIdOrNull(order.getM_PricingSystem_ID());
		if (pricingSystemId == null)
		{
			return;
		}

		final BPartnerLocationId bpartnerAndLocationId = getShipToLocationIdOrNull(order);
		if (bpartnerAndLocationId == null)
		{
			return;
		}

		final SOTrx soTrx = SOTrx.ofBoolean(order.isSOTrx());
		final PriceListId plId = retrievePriceListIdOrNull(pricingSystemId, bpartnerAndLocationId, soTrx);
		if (plId == null)
		{
			final String pricingSystemName = Services.get(IPriceListDAO.class).getPricingSystemName(pricingSystemId);
			throw new PriceListNotFoundException(pricingSystemName, soTrx);
		}
	}

	@Override
	public PriceListId retrievePriceListId(final I_C_Order order, final PricingSystemId pricingSystemIdOverride)
	{
		final PriceListId orderPriceListId = PriceListId.ofRepoIdOrNull(order.getM_PriceList_ID());
		if (orderPriceListId != null)
		{
			if (pricingSystemIdOverride != null)
			{
				final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
				final I_M_PriceList priceList = priceListDAO.getById(orderPriceListId);
				if (priceList.getM_PricingSystem_ID() == pricingSystemIdOverride.getRepoId())
				{
					return orderPriceListId;
				}
			}
			else
			{
				return orderPriceListId;
			}
		}

		final PricingSystemId pricingSystemId = pricingSystemIdOverride != null ? pricingSystemIdOverride : PricingSystemId.ofRepoIdOrNull(order.getM_PricingSystem_ID());
		final BPartnerLocationId bpartnerAndLocationId = getShipToLocationIdOrNull(order);
		final SOTrx soTrx = SOTrx.ofBoolean(order.isSOTrx());
		return retrievePriceListIdOrNull(pricingSystemId, bpartnerAndLocationId, soTrx);
	}

	private PriceListId retrievePriceListIdOrNull(
			final PricingSystemId pricingSystemId,
			final BPartnerLocationId shipToBPLocationId,
			@NonNull final SOTrx soTrx)
	{
		if (shipToBPLocationId == null)
		{
			return null;
		}

		final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
		return priceListDAO.retrievePriceListIdByPricingSyst(pricingSystemId, shipToBPLocationId, soTrx);
	}

	@Override
	public boolean setBill_User_ID(final org.compiere.model.I_C_Order order)
	{
		// First try: if order and bill partner and location are the same, and the contact is set
		// we can use the same contact
		final BPartnerLocationId billToBPLocationId = BPartnerLocationId.ofRepoIdOrNull(order.getBill_BPartner_ID(), order.getBill_Location_ID());
		final BPartnerLocationId shipToBPLocationId = extractBPartnerLocationOrNull(order);
		final BPartnerContactId shipToContactId = BPartnerContactId.ofRepoIdOrNull(order.getC_BPartner_ID(), order.getAD_User_ID());
		if (BPartnerLocationId.equals(shipToBPLocationId, billToBPLocationId) && shipToContactId != null)
		{
			order.setBill_User_ID(shipToContactId.getRepoId());
			return true;
		}

		final RetrieveContactRequestBuilder retrieveBillContanctRequest = RetrieveContactRequest.builder()
				.bpartnerId(BPartnerId.ofRepoId(order.getBill_BPartner_ID()));
		if (billToBPLocationId != null)
		{
			// Case: Bill Location is set, we can use it to retrieve the contact for that location
			retrieveBillContanctRequest.bPartnerLocationId(billToBPLocationId);

		}
		final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
		final User billContact = bpartnerBL.retrieveContactOrNull(retrieveBillContanctRequest.build());
		if (billContact == null)
		{
			return false;
		}

		order.setBill_User_ID(billContact.getId().getRepoId());
		return true;
	}

	@Override
	public void setDocTypeTargetId(final I_C_Order order)
	{
		if (order.isSOTrx())
		{
			setDocTypeTargetId(order, X_C_DocType.DOCSUBTYPE_StandardOrder);
			return;
		}
		else
		{
			final DocTypeQuery docTypeQuery = DocTypeQuery.builder()
					.docBaseType(X_C_DocType.DOCBASETYPE_PurchaseOrder)
					.docSubType(DocTypeQuery.DOCSUBTYPE_Any)
					.adClientId(order.getAD_Client_ID())
					.adOrgId(order.getAD_Org_ID())
					.build();
			final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

			final DocTypeId docTypeId = docTypeDAO.getDocTypeIdOrNull(docTypeQuery);
			if (docTypeId == null)
			{
				logger.error("No POO found for {}", docTypeQuery);
			}
			else
			{
				logger.debug("(PO) - {}", docTypeId);
				setDocTypeTargetIdAndUpdateDescription(order, docTypeId);
			}
		}
	}

	@Override
	public void setDocTypeTargetId(final I_C_Order order, final String soDocSubType)
	{
		final DocTypeQuery docTypeQuery = DocTypeQuery.builder()
				.docBaseType(X_C_DocType.DOCBASETYPE_SalesOrder)
				.docSubType(soDocSubType)
				.adClientId(order.getAD_Client_ID())
				.adOrgId(order.getAD_Org_ID())
				.build();
		final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

		final DocTypeId docTypeId = docTypeDAO.getDocTypeIdOrNull(docTypeQuery);
		if (docTypeId == null)
		{
			logger.error("Not found for {}", docTypeQuery);
		}
		else
		{
			logger.debug("(SO) - {}", soDocSubType);
			setDocTypeTargetIdAndUpdateDescription(order, docTypeId);
			order.setIsSOTrx(true);
		}
	}

	@Override
	public void setDocTypeTargetIdAndUpdateDescription(@NonNull final I_C_Order order, @NonNull final DocTypeId docTypeId)
	{
		order.setC_DocTypeTarget_ID(docTypeId.getRepoId());
		updateDescriptionFromDocTypeTargetId(order);
	}

	@Override
	public void updateDescriptionFromDocTypeTargetId(final I_C_Order order)
	{
		final int docTypeId = order.getC_DocTypeTarget_ID();
		if (docTypeId <= 0)
		{
			return;
		}

		final int bpartnerId = order.getC_BPartner_ID();

		if (bpartnerId <= 0)
		{
			return;
		}

		final org.compiere.model.I_C_DocType docType = Services.get(IDocTypeDAO.class).getById(docTypeId);

		if (docType == null)
		{
			return;
		}

		if (!docType.isCopyDescriptionToDocument())
		{
			return;
		}

		final String adLanguage = CoalesceUtil.coalesce(
				getBPartner(order).getAD_Language(),
				Env.getAD_Language());

		final IModelTranslationMap docTypeTrl = InterfaceWrapperHelper.getModelTranslationMap(docType);
		final ITranslatableString description = docTypeTrl.getColumnTrl(I_C_DocType.COLUMNNAME_Description, docType.getDescription());
		final ITranslatableString documentNote = docTypeTrl.getColumnTrl(I_C_DocType.COLUMNNAME_DocumentNote, docType.getDocumentNote());

		order.setDescription(description.translate(adLanguage));
		order.setDescriptionBottom(documentNote.translate(adLanguage));
	}

	@Override
	public void updateAddresses(final org.compiere.model.I_C_Order order)
	{
		final de.metas.adempiere.model.I_C_Order orderEx = InterfaceWrapperHelper.create(order, de.metas.adempiere.model.I_C_Order.class);

		for (final I_C_OrderLine line : Services.get(IOrderDAO.class).retrieveOrderLines(orderEx))
		{
			if (orderEx.isDropShip() && orderEx.getDropShip_BPartner_ID() > 0)
			{
				line.setC_BPartner_ID(orderEx.getDropShip_BPartner_ID());
			}
			else
			{
				line.setC_BPartner_ID(orderEx.getC_BPartner_ID());
			}

			if (orderEx.isDropShip() && orderEx.getDropShip_Location_ID() > 0)
			{
				line.setC_BPartner_Location_ID(orderEx.getDropShip_Location_ID());
				line.setBPartnerAddress(orderEx.getDeliveryToAddress());

			}
			else
			{
				line.setC_BPartner_Location_ID(orderEx.getC_BPartner_Location_ID());
				line.setBPartnerAddress(orderEx.getBPartnerAddress());
			}

			if (orderEx.isDropShip() && orderEx.getDropShip_User_ID() > 0)
			{
				line.setAD_User_ID(orderEx.getDropShip_User_ID());
			}
			else
			{
				line.setAD_User_ID(orderEx.getAD_User_ID());
			}

			InterfaceWrapperHelper.save(line);
		}
	}

	@Override
	public DeliveryViaRule evaluateOrderDeliveryViaRule(I_C_Order order)
	{
		final DeliveryViaRule orderDeliveryViaRule = DeliveryViaRule.ofNullableCode(order.getDeliveryViaRule());
		return orderDeliveryViaRule != null
				? orderDeliveryViaRule
				: findDeliveryViaRule(order);
	}

	private DeliveryViaRule findDeliveryViaRule(final I_C_Order orderRecord)
	{
		final BPartnerOrderParams params = retrieveBPartnerParams(orderRecord);
		return params.getDeliveryViaRule().orElse(null);
	}

	private BPartnerOrderParams retrieveBPartnerParams(@NonNull final I_C_Order orderRecord)
	{
		final BPartnerId shipBPartnerId = BPartnerId.ofRepoIdOrNull(orderRecord.getC_BPartner_ID());
		final BPartnerId billBPartnerId = BPartnerId.ofRepoIdOrNull(coalesce(
				orderRecord.getBill_BPartner_ID(),
				orderRecord.getC_BPartner_ID()));

		final SOTrx soTrx = SOTrx.ofBoolean(orderRecord.isSOTrx());

		final BPartnerOrderParamsRepository bpartnerOrderParamsRepository = SpringContextHolder.instance.getBean(BPartnerOrderParamsRepository.class);

		final BPartnerOrderParamsQuery query = BPartnerOrderParamsQuery.builder()
				.shipBPartnerId(shipBPartnerId)
				.billBPartnerId(billBPartnerId)
				.soTrx(soTrx)
				.build();
		final BPartnerOrderParams params = bpartnerOrderParamsRepository.getBy(query);
		return params;
	}

	@Override
	public I_M_PriceList_Version getPriceListVersion(final I_C_Order order)
	{
		if (order == null)
		{
			return null;
		}
		final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);

		final LocalDate orderDate;
		if (order.getDatePromised() != null)
		{
			orderDate = TimeUtil.asLocalDate(order.getDatePromised());
		}
		else
		{
			orderDate = TimeUtil.asLocalDate(order.getDateOrdered());
		}

		final PriceListId priceListId = PriceListId.ofRepoId(order.getM_PriceList_ID());
		final Boolean processedPLVFiltering = null; // task 09533: the user doesn't know about PLV's processed flag, so we can't filter by it
		final I_M_PriceList_Version plv = priceListDAO.retrievePriceListVersionOrNull(priceListId, orderDate, processedPLVFiltering);
		return plv;
	}

	@Override
	public I_C_BPartner getBPartner(@NonNull final I_C_Order order)
	{
		final I_C_BPartner bpartner = getBPartnerOrNull(order);
		if (bpartner == null)
		{
			throw new AdempiereException("No BPartner defined for " + order);
		}
		return bpartner;
	}

	@Override
	public I_C_BPartner getBPartnerOrNull(@NonNull final I_C_Order order)
	{
		final BPartnerId bpartnerId = extractBPartnerIdOrNull(order);
		return bpartnerId != null
				? Services.get(IBPartnerDAO.class).getById(bpartnerId, I_C_BPartner.class)
				: null;
	}

	@Override
	public void setBPartner(final org.compiere.model.I_C_Order order, final org.compiere.model.I_C_BPartner bp)
	{
		// FIXME: keep in sync / merge with org.compiere.model.MOrder.setBPartner(MBPartner)
		if (bp == null)
		{
			return;
		}

		order.setC_BPartner_ID(bp.getC_BPartner_ID());

		final boolean isSOTrx = order.isSOTrx();
		//
		// Defaults Payment Term
		final int paymentTermId;
		if (isSOTrx)
		{
			paymentTermId = bp.getC_PaymentTerm_ID();
		}
		else
		{
			paymentTermId = bp.getPO_PaymentTerm_ID();
		}
		if (paymentTermId > 0)
		{
			order.setC_PaymentTerm_ID(paymentTermId);
		}

		//
		// Default Price List
		final int priceListId;
		if (isSOTrx)
		{
			priceListId = bp.getM_PriceList_ID();
		}
		else
		{
			priceListId = bp.getPO_PriceList_ID();
		}
		if (priceListId > 0)
		{
			order.setM_PriceList_ID(priceListId);
		}

		//
		// Default Delivery
		final String deliveryRule = bp.getDeliveryRule();
		if (deliveryRule != null)
		{
			order.setDeliveryRule(deliveryRule);
		}

		//
		// Default Delivery Via Rule
		final String deliveryViaRule;
		if (isSOTrx)
		{
			deliveryViaRule = bp.getDeliveryViaRule();
		}
		else
		{
			deliveryViaRule = bp.getPO_DeliveryViaRule();
		}
		if (deliveryViaRule != null)
		{
			order.setDeliveryViaRule(deliveryViaRule);
		}

		//
		// Default Invoice/Payment Rule
		final String invoiceRule = bp.getInvoiceRule();
		if (invoiceRule != null)
		{
			order.setInvoiceRule(invoiceRule);
		}
		final String paymentRule = bp.getPaymentRule();
		if (paymentRule != null)
		{
			order.setPaymentRule(paymentRule);
		}

		//
		// Sales Rep
		final int salesRepId = bp.getSalesRep_ID();
		if (salesRepId > 0)
		{
			order.setSalesRep_ID(salesRepId);
		}

		setBPLocation(order, bp);

		// #1056
		// find if the partner doesn't have a bill relation with another partner. In such a case, that partner will have priority.
		setBillLocation(order);

		final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

		// Set Contact
		// final List<I_AD_User> contacts = bPartnerDAO.retrieveContacts(bp.getC_BPartner_ID(), false, null);
		// if (contacts != null && contacts.size() == 1)
		// {
		// order.setAD_User_ID(contacts.get(0).getAD_User_ID());
		// }

		// 08812
		// set the fit contact

		final Properties ctx = InterfaceWrapperHelper.getCtx(order);
		final int bpartnerId = bp.getC_BPartner_ID();

		// keep the trxName null, as it was before
		final String trxName = ITrx.TRXNAME_None;
		final I_AD_User contact = bPartnerDAO.retrieveContact(ctx, bpartnerId, isSOTrx, trxName);

		// keep the functionality as it was. Do not set null user
		if (contact != null)
		{
			order.setAD_User_ID(contact.getAD_User_ID());
		}
	}

	@Override
	public void setBPLocation(final org.compiere.model.I_C_Order order, final org.compiere.model.I_C_BPartner bp)
	{
		final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

		final List<I_C_BPartner_Location> locations = bPartnerDAO.retrieveBPartnerLocations(bp);

		// Set Locations
		final List<I_C_BPartner_Location> shipLocations = new ArrayList<>();
		boolean foundLoc = false;
		for (final I_C_BPartner_Location loc : locations)
		{
			if (loc.isShipTo() && loc.isActive())
			{
				shipLocations.add(loc);
			}

			final org.compiere.model.I_C_BPartner_Location bpLoc = InterfaceWrapperHelper.create(loc, org.compiere.model.I_C_BPartner_Location.class);
			if (bpLoc.isShipToDefault())
			{
				order.setC_BPartner_Location_ID(bpLoc.getC_BPartner_Location_ID());
				foundLoc = true;
			}
		}

		// set first ship location if is not set
		if (!foundLoc)
		{
			if (!shipLocations.isEmpty())
			{
				order.setC_BPartner_Location_ID(shipLocations.get(0).getC_BPartner_Location_ID());
			}
			else if (!locations.isEmpty())
			{
				// set to first
				if (order.getC_BPartner_Location_ID() == 0)
				{
					order.setC_BPartner_Location_ID(locations.get(0)
							.getC_BPartner_Location_ID());
				}
			}
		}

		if (!foundLoc)
		{
			logger.error("MOrder.setBPartner - Has no Ship To Address: {}", bp);
		}
	}

	@Override
	public boolean setBillLocation(final I_C_Order order)
	{
		final BPartnerId bpartnerId = extractBPartnerIdOrNull(order);
		if (bpartnerId == null)
		{
			return false; // nothing to be done
		}

		final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
		final BPartnerLocationQuery query = BPartnerLocationQuery
				.builder()
				.type(Type.BILL_TO)
				.relationBPartnerLocationId(extractBPartnerLocationOrNull(order))
				.bpartnerId(bpartnerId)
				.build();
		final I_C_BPartner_Location billtoLocation = bPartnerDAO.retrieveBPartnerLocation(query);

		if (billtoLocation == null)
		{
			return false;
		}

		order.setBill_BPartner_ID(billtoLocation.getC_BPartner_ID());
		order.setBill_Location_ID(billtoLocation.getC_BPartner_Location_ID());

		return true; // found it
	}

	private BPartnerId extractBPartnerIdOrNull(final I_C_Order order)
	{
		return BPartnerId.ofRepoIdOrNull(order.getC_BPartner_ID());
	}

	private BPartnerLocationId extractBPartnerLocationOrNull(final I_C_Order order)
	{
		return BPartnerLocationId.ofRepoIdOrNull(order.getC_BPartner_ID(), order.getC_BPartner_Location_ID());
	}

	@Override
	public CurrencyPrecision getPricePrecision(final I_C_Order order)
	{
		final PriceListId priceListId = PriceListId.ofRepoIdOrNull(order.getM_PriceList_ID());
		return priceListId != null
				? Services.get(IPriceListBL.class).getPricePrecision(priceListId)
				: CurrencyPrecision.TWO;
	}

	@Override
	public CurrencyPrecision getAmountPrecision(final I_C_Order order)
	{
		final PriceListId priceListId = PriceListId.ofRepoIdOrNull(order.getM_PriceList_ID());
		return priceListId != null
				? Services.get(IPriceListBL.class).getAmountPrecision(priceListId)
				: CurrencyPrecision.TWO;
	}

	@Override
	public CurrencyPrecision getTaxPrecision(final I_C_Order order)
	{
		final PriceListId priceListId = PriceListId.ofRepoIdOrNull(order.getM_PriceList_ID());
		return priceListId != null
				? Services.get(IPriceListBL.class).getTaxPrecision(priceListId)
				: CurrencyPrecision.TWO;
	}

	@Override
	public boolean isTaxIncluded(final org.compiere.model.I_C_Order order, I_C_Tax tax)
	{
		Check.assumeNotNull(order, "order not null");

		if (tax != null && tax.isWholeTax())
		{
			return true;
		}

		return order.isTaxIncluded();
	}

	@Override
	public void reserveStock(@NonNull final I_C_Order order, final org.compiere.model.I_C_OrderLine... orderLines)
	{
		final MOrder orderPO = InterfaceWrapperHelper.getPO(order);
		final List<MOrderLine> orderLinePOs = LegacyAdapters.convertToPOList(Arrays.asList(orderLines));
		orderPO.reserveStock(null, orderLinePOs); // docType=null (i.e. fetch it from order)
	}

	@Override
	public void closeLine(final org.compiere.model.I_C_OrderLine orderLine)
	{
		Check.assumeNotNull(orderLine, "orderLine not null");

		if (orderLine.getQtyDelivered().compareTo(orderLine.getQtyOrdered()) >= 0) // they delivered at least the ordered qty => nothing to do
		{
			return; // Do nothing
		}

		orderLine.setQtyOrdered(orderLine.getQtyDelivered());
		InterfaceWrapperHelper.save(orderLine); // saving, just to be on the save side in case reserveStock() does a refresh or sth

		final I_C_Order order = orderLine.getC_Order();
		reserveStock(order, orderLine); // FIXME: move reserveStock method to an orderBL service
	}

	@Override
	public void reopenLine(@NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

		//
		// Calculate QtyOrdered as QtyEntered converted to stocking UOM
		final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());
		final Quantity qtyEntered = Services.get(IOrderLineBL.class).getQtyEntered(orderLine);
		final Quantity qtyOrdered = uomConversionBL.convertToProductUOM(qtyEntered, productId);

		//
		// Set QtyOrdered
		orderLine.setQtyOrdered(qtyOrdered.toBigDecimal());
		InterfaceWrapperHelper.save(orderLine); // saving, just to be on the save side in case reserveStock() does a refresh or sth

		//
		// Update qty reservation
		final I_C_Order order = orderLine.getC_Order();
		reserveStock(order, orderLine);

	}

	@Override
	public BPartnerLocationId getShipToLocationId(final I_C_Order order)
	{
		final BPartnerLocationId shipToLocationId = getShipToLocationIdOrNull(order);
		if (shipToLocationId == null)
		{
			throw new AdempiereException("No Ship To BP/Location defined: " + order);
		}
		return shipToLocationId;
	}

	private BPartnerLocationId getShipToLocationIdOrNull(final I_C_Order order)
	{
		if (order.isDropShip() && order.getDropShip_BPartner_ID() > 0 && order.getDropShip_Location_ID() > 0)
		{
			return BPartnerLocationId.ofRepoId(order.getDropShip_BPartner_ID(), order.getDropShip_Location_ID());
		}

		return extractBPartnerLocationOrNull(order);
	}

	@Override
	public org.compiere.model.I_AD_User getShipToUser(final I_C_Order order)
	{
		final UserId contactId;
		if (order.isDropShip())
		{
			// check for isDropShip to avoid returning a "stale" dropship-partner
			final UserId dropShipUserId = UserId.ofRepoIdOrNull(order.getDropShip_User_ID());
			contactId = dropShipUserId != null ? dropShipUserId : UserId.ofRepoIdOrNull(order.getAD_User_ID());
		}
		else
		{
			contactId = UserId.ofRepoIdOrNull(order.getAD_User_ID());
		}

		return contactId != null
				? Services.get(IUserDAO.class).getById(contactId)
				: null;
	}

	@Override
	public BPartnerLocationId getBillToLocationIdOrNull(@NonNull final I_C_Order order)
	{
		final BPartnerLocationId billToBPLocationId = BPartnerLocationId.ofRepoIdOrNull(order.getBill_BPartner_ID(), order.getBill_Location_ID());
		return billToBPLocationId != null
				? billToBPLocationId
				: BPartnerLocationId.ofRepoId(order.getC_BPartner_ID(), order.getC_BPartner_Location_ID());
	}

	@Override
	public BPartnerContactId getBillToContactId(@NonNull final I_C_Order order)
	{
		final BPartnerContactId billToContactId = BPartnerContactId.ofRepoIdOrNull(order.getBill_BPartner_ID(), order.getBill_User_ID());
		return billToContactId != null
				? billToContactId
				: BPartnerContactId.ofRepoId(order.getC_BPartner_ID(), order.getAD_User_ID());
	}

	private static final ModelDynAttributeAccessor<org.compiere.model.I_C_Order, BigDecimal> DYNATTR_QtyInvoicedSum = new ModelDynAttributeAccessor<>("QtyInvoicedSum", BigDecimal.class);
	private static final ModelDynAttributeAccessor<org.compiere.model.I_C_Order, BigDecimal> DYNATTR_QtyDeliveredSum = new ModelDynAttributeAccessor<>("QtyDeliveredSum", BigDecimal.class);
	private static final ModelDynAttributeAccessor<org.compiere.model.I_C_Order, BigDecimal> DYNATTR_QtyOrderedSum = new ModelDynAttributeAccessor<>("QtyOrderedSum", BigDecimal.class);

	@Override
	public void updateOrderQtySums(final org.compiere.model.I_C_Order order)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryAggregateBuilder<org.compiere.model.I_C_OrderLine, org.compiere.model.I_C_Order> aggregateOnOrder = queryBL
				.createQueryBuilder(org.compiere.model.I_C_OrderLine.class, order)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(org.compiere.model.I_C_OrderLine.COLUMNNAME_C_Order_ID, order.getC_Order_ID())
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_IsPackagingMaterial, false)
				.aggregateOnColumn(org.compiere.model.I_C_OrderLine.COLUMN_C_Order_ID);

		aggregateOnOrder.sum(DYNATTR_QtyInvoicedSum, org.compiere.model.I_C_OrderLine.COLUMN_QtyInvoiced);
		aggregateOnOrder.sum(DYNATTR_QtyDeliveredSum, org.compiere.model.I_C_OrderLine.COLUMN_QtyDelivered);
		aggregateOnOrder.sum(DYNATTR_QtyOrderedSum, org.compiere.model.I_C_OrderLine.COLUMN_QtyOrdered);

		final de.metas.order.model.I_C_Order fOrder = InterfaceWrapperHelper.create(order, de.metas.order.model.I_C_Order.class);

		final List<org.compiere.model.I_C_Order> queryiedOrders = aggregateOnOrder.aggregate();
		if (queryiedOrders.isEmpty())
		{
			// gh #1855: cover the case that the order has no lines or just packing lines.
			fOrder.setQtyInvoiced(BigDecimal.ZERO);
			fOrder.setQtyMoved(BigDecimal.ZERO);
			fOrder.setQtyOrdered(BigDecimal.ZERO);
		}
		else
		{
			final org.compiere.model.I_C_Order queriedOrder = CollectionUtils.singleElement(queryiedOrders);

			fOrder.setQtyInvoiced(DYNATTR_QtyInvoicedSum.getValue(queriedOrder));
			fOrder.setQtyMoved(DYNATTR_QtyDeliveredSum.getValue(queriedOrder));
			fOrder.setQtyOrdered(DYNATTR_QtyOrderedSum.getValue(queriedOrder));
		}
		InterfaceWrapperHelper.save(fOrder);
	}

	@Override
	public boolean isQuotation(@NonNull final I_C_Order order)
	{
		final boolean isSOTrx = order.isSOTrx();

		if (!isSOTrx)
		{
			// purchase orders are not quotations
			return false;
		}

		final I_C_DocType docType = CoalesceUtil.coalesceSuppliers(
				() -> getDocTypeOrNull(order),
				() -> getDocTypeTargetOrNull(order));
		if (docType == null)
		{
			return false;
		}

		if (!(X_C_DocType.DOCBASETYPE_SalesOrder.equals(docType.getDocBaseType())))
		{
			// Quotation must be of BaseType Sales Order
			return false;
		}

		final String docSubType = docType.getDocSubType();
		if (docSubType == null)
		{
			// Quotation must have a docSubType
			return false;
		}

		return (docSubType.equals(X_C_DocType.DOCSUBTYPE_Proposal) || docSubType.equals(X_C_DocType.DOCSUBTYPE_Quotation));
	}

	@Override
	public boolean isPrepay(@NonNull final OrderId orderId)
	{
		final I_C_Order order = getById(orderId);
		return isPrepay(order);
	}

	@Override
	public boolean isPrepay(@NonNull final I_C_Order order)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(order.getC_DocType_ID());
		if (docTypeId == null)
		{
			return false;
		}

		return Services.get(IDocTypeBL.class).isPrepay(docTypeId);
	}

	@Override
	public I_C_DocType getDocTypeOrNull(@NonNull final I_C_Order order)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(order.getC_DocType_ID());
		return docTypeId != null
				? Services.get(IDocTypeDAO.class).getById(docTypeId)
				: null;
	}

	private I_C_DocType getDocTypeTargetOrNull(@NonNull final I_C_Order order)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(order.getC_DocTypeTarget_ID());
		return docTypeId != null
				? Services.get(IDocTypeDAO.class).getById(docTypeId)
				: null;
	}

	@Override
	public ProjectId getProjectIdOrNull(@NonNull final OrderLineId orderLineId)
	{
		final IOrderDAO ordersRepo = Services.get(IOrderDAO.class);

		final I_C_OrderLine orderLine = ordersRepo.getOrderLineById(orderLineId);
		final ProjectId lineProjectId = ProjectId.ofRepoIdOrNull(orderLine.getC_Project_ID());
		if (lineProjectId != null)
		{
			return lineProjectId;
		}

		final OrderId orderId = OrderId.ofRepoId(orderLine.getC_Order_ID());
		final I_C_Order order = ordersRepo.getById(orderId);
		final ProjectId orderProjectId = ProjectId.ofRepoIdOrNull(order.getC_Project_ID());
		return orderProjectId;
	}

	@Override
	public ZoneId getTimeZone(@NonNull final I_C_Order order)
	{
		final IOrgDAO orgsRepo = Services.get(IOrgDAO.class);

		final OrgId orgId = OrgId.ofRepoIdOrAny(order.getAD_Org_ID());
		return orgsRepo.getTimeZone(orgId);
	}
}

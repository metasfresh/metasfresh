/*
 * #%L
 * de.metas.business
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

package de.metas.order.impl;

import ch.qos.logback.classic.Level;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerBL.RetrieveContactRequest;
import de.metas.bpartner.service.IBPartnerBL.RetrieveContactRequest.RetrieveContactRequestBuilder;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerDAO.BPartnerLocationQuery;
import de.metas.bpartner.service.IBPartnerDAO.BPartnerLocationQuery.Type;
import de.metas.common.util.CoalesceUtil;
import de.metas.currency.CurrencyPrecision;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeBL;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.location.DocumentLocation;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.ITranslatableString;
import de.metas.interfaces.I_C_BPartner;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
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
import de.metas.order.location.adapter.OrderDocumentLocationAdapterFactory;
import de.metas.order.location.adapter.OrderLineDocumentLocationAdapterFactory;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.exceptions.PriceListNotFoundException;
import de.metas.pricing.service.IPriceListBL;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantity;
import de.metas.request.RequestTypeId;
import de.metas.tax.api.Tax;
import de.metas.uom.IUOMConversionBL;
import de.metas.user.User;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryAggregateBuilder;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.LegacyAdapters;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_C_Order;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static de.metas.common.util.CoalesceUtil.coalesce;
import static de.metas.common.util.CoalesceUtil.firstGreaterThanZero;

public class OrderBL implements IOrderBL
{
	private static final Logger logger = LogManager.getLogger(OrderBL.class);
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	private static final String SYS_CONFIG_MAX_HADDEX_AGE_IN_MONTHS = "de.metas.order.MAX_HADDEX_AGE_IN_MONTHS";
	private static final AdMessageKey MSG_HADDEX_CHECK_ERROR = AdMessageKey.of("de.metas.order.CustomerHaddexError");

	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final IUserDAO userDAO = Services.get(IUserDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IPriceListBL priceListBL = Services.get(IPriceListBL.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);

	@Override
	public I_C_Order getById(@NonNull final OrderId orderId)
	{
		return orderDAO.getById(orderId);
	}

	@Override
	public void setM_PricingSystem_ID(final I_C_Order order, final boolean overridePricingSystemAndDontThrowExIfNotFound)
	{
		final int previousPricingSystemId = order.getM_PricingSystem_ID();

		if (overridePricingSystemAndDontThrowExIfNotFound || previousPricingSystemId <= 0)
		{
			final BPartnerLocationAndCaptureId bpartnerAndLocation = extractBPartnerLocation(order).orElse(null);
			if (bpartnerAndLocation == null)
			{
				logger.debug("Order {} has no C_BPartner_ID. Doing nothing", order);
				return;
			}

			final BPartnerId bpartnerId = bpartnerAndLocation.getBpartnerId();
			final SOTrx soTrx = SOTrx.ofBoolean(order.isSOTrx());
			final PricingSystemId pricingSysId = bpartnerDAO.retrievePricingSystemIdOrNull(bpartnerId, soTrx);

			final boolean throwExIfNotFound = !overridePricingSystemAndDontThrowExIfNotFound;
			if (pricingSysId == null && throwExIfNotFound)
			{
				final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
				final String bpartnerName = bpartnerBL.getBPartnerValueAndName(bpartnerId);
				Check.errorIf(true, "Unable to find pricing system for BPartner {}; SOTrx={}", bpartnerName, soTrx);
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

		final BPartnerLocationAndCaptureId bpartnerAndLocationId = extractBPartnerLocation(order).orElse(null);
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
			final String pricingSystemName = priceListDAO.getPricingSystemName(pricingSystemId);
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

		final BPartnerLocationAndCaptureId bpartnerAndLocationId = extractBPartnerLocation(order).orElse(null);
		if (bpartnerAndLocationId == null)
		{
			return;
		}

		final SOTrx soTrx = SOTrx.ofBoolean(order.isSOTrx());
		final PriceListId plId = retrievePriceListIdOrNull(pricingSystemId, bpartnerAndLocationId, soTrx);
		if (plId == null)
		{
			final String pricingSystemName = priceListDAO.getPricingSystemName(pricingSystemId);
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
				final IPriceListDAO priceListDAO = this.priceListDAO;
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
		final BPartnerLocationAndCaptureId bpartnerAndLocationId = extractBPartnerLocation(order).orElse(null);
		final SOTrx soTrx = SOTrx.ofBoolean(order.isSOTrx());
		return retrievePriceListIdOrNull(pricingSystemId, bpartnerAndLocationId, soTrx);
	}

	private PriceListId retrievePriceListIdOrNull(
			final PricingSystemId pricingSystemId,
			@Nullable final BPartnerLocationAndCaptureId shipToBPLocationId,
			@NonNull final SOTrx soTrx)
	{
		if (shipToBPLocationId == null)
		{
			return null;
		}

		final IBPartnerBL partnerBL = Services.get(IBPartnerBL.class); // keep it here as this is spring service
		final CountryId countryId = partnerBL.getCountryId(shipToBPLocationId);

		final IPriceListDAO priceListDAO = this.priceListDAO;
		return priceListDAO.retrievePriceListIdByPricingSyst(pricingSystemId, countryId, soTrx);
	}

	@Override
	public boolean setBill_User_ID(final org.compiere.model.I_C_Order order)
	{
		final BPartnerId billBPartnerId = BPartnerId.ofRepoIdOrNull(order.getBill_BPartner_ID());
		if (billBPartnerId == null)
		{
			return false;
		}

		// First try: if order and bill partner and location are the same, and the contact is set
		// we can use the same contact
		final BPartnerLocationId billToBPLocationId = BPartnerLocationId.ofRepoIdOrNull(billBPartnerId, order.getBill_Location_ID());
		final BPartnerLocationId shipToBPLocationId = extractBPartnerLocation(order)
				.map(BPartnerLocationAndCaptureId::getBpartnerLocationId)
				.orElse(null);
		final BPartnerContactId shipToContactId = BPartnerContactId.ofRepoIdOrNull(order.getC_BPartner_ID(), order.getAD_User_ID());
		if (BPartnerLocationId.equals(shipToBPLocationId, billToBPLocationId) && shipToContactId != null)
		{
			order.setBill_User_ID(shipToContactId.getRepoId());
			return true;
		}

		final RetrieveContactRequestBuilder retrieveBillContactRequest = RetrieveContactRequest.builder()
				.bpartnerId(billBPartnerId);
		if (billToBPLocationId != null)
		{
			// Case: Bill Location is set, we can use it to retrieve the contact for that location
			retrieveBillContactRequest.bPartnerLocationId(billToBPLocationId);

		}
		final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
		final User billContact = bpartnerBL.retrieveContactOrNull(retrieveBillContactRequest.build());
		if (billContact == null)
		{
			return false;
		}

		order.setBill_User_ID(billContact.getId().getRepoId());
		return true;
	}

	@Override
	public void setDefaultDocTypeTargetId(@NonNull final I_C_Order order)
	{
		if (order.isSOTrx())
		{
			this.setSODocTypeTargetId(order, X_C_DocType.DOCSUBTYPE_StandardOrder);
		}
		else
		{
			final DocTypeQuery docTypeQuery = DocTypeQuery.builder()
					.docBaseType(X_C_DocType.DOCBASETYPE_PurchaseOrder)
					.docSubType(DocTypeQuery.DOCSUBTYPE_Any)
					.adClientId(order.getAD_Client_ID())
					.adOrgId(order.getAD_Org_ID())
					.build();

			final DocTypeId docTypeId = docTypeBL.getDocTypeIdOrNull(docTypeQuery);
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

	public void setPODocTypeTargetId(@NonNull final I_C_Order order, @NonNull final String poDocSubType)
	{
		if (order.isSOTrx())
		{
			throw new AdempiereException("Expecting C_Order to have isSOTrx equal to false!")
					.appendParametersToMessage()
					.setParameter("C_Order_ID", order.getC_Order_ID())
					.setParameter("C_Order.isSOTrx", order.isSOTrx());
		}

		final DocTypeQuery docTypeQuery = DocTypeQuery.builder()
				.docBaseType(X_C_DocType.DOCBASETYPE_PurchaseOrder)
				.docSubType(poDocSubType)
				.adClientId(order.getAD_Client_ID())
				.adOrgId(order.getAD_Org_ID())
				.build();

		final DocTypeId docTypeId = docTypeBL.getDocTypeIdOrNull(docTypeQuery);
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

	public void setSODocTypeTargetId(final I_C_Order order, final String soDocSubType)
	{
		if (!order.isSOTrx())
		{
			throw new AdempiereException("Expecting C_Order to have isSOTrx equal to true!")
					.appendParametersToMessage()
					.setParameter("C_Order_ID", order.getC_Order_ID())
					.setParameter("C_Order.isSOTrx", order.isSOTrx());
		}

		final DocTypeQuery docTypeQuery = DocTypeQuery.builder()
				.docBaseType(X_C_DocType.DOCBASETYPE_SalesOrder)
				.docSubType(soDocSubType)
				.adClientId(order.getAD_Client_ID())
				.adOrgId(order.getAD_Org_ID())
				.build();

		final DocTypeId docTypeId = docTypeBL.getDocTypeIdOrNull(docTypeQuery);
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
		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(order.getC_DocTypeTarget_ID());
		if (docTypeId == null)
		{
			return;
		}

		final int bpartnerId = order.getC_BPartner_ID();

		if (bpartnerId <= 0)
		{
			return;
		}

		final I_C_DocType docType = docTypeBL.getById(docTypeId);
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
	public void updateOrderLineAddressesFromOrder(final org.compiere.model.I_C_Order order)
	{
		for (final I_C_OrderLine line : orderDAO.retrieveOrderLines(order))
		{
			OrderLineDocumentLocationAdapterFactory.locationAdapter(line).setFromOrderHeader(order);
			orderDAO.save(line);
		}
	}

	@Override
	public Optional<DeliveryViaRule> findDeliveryViaRule(final I_C_Order orderRecord)
	{
		final BPartnerOrderParams params = retrieveBPartnerParams(orderRecord);
		return params.getDeliveryViaRule();
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

		return bpartnerOrderParamsRepository.getBy(query);
	}

	@Override
	public I_M_PriceList_Version getPriceListVersion(final I_C_Order order)
	{
		if (order == null)
		{
			return null;
		}
		final ZonedDateTime orderDate;
		if (order.getDatePromised() != null)
		{
			orderDate = TimeUtil.asZonedDateTime(order.getDatePromised());
		}
		else
		{
			orderDate = TimeUtil.asZonedDateTime(order.getDateOrdered());
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
				? bpartnerDAO.getById(bpartnerId, I_C_BPartner.class)
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
		final I_AD_User contact = bpartnerDAO.retrieveContact(ctx, bpartnerId, isSOTrx, trxName);

		// keep the functionality as it was. Do not set null user
		if (contact != null)
		{
			order.setAD_User_ID(contact.getAD_User_ID());
		}
	}

	@Override
	public void setBPLocation(final org.compiere.model.I_C_Order order, final org.compiere.model.I_C_BPartner bp)
	{
		final List<I_C_BPartner_Location> locations = bpartnerDAO.retrieveBPartnerLocations(bp);

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
				setBPartnerLocation(order, bpLoc);
				foundLoc = true;
			}
		}

		// set first ship location if is not set
		if (!foundLoc)
		{
			if (!shipLocations.isEmpty())
			{
				setBPartnerLocation(order, shipLocations.get(0));
			}
			//No longer setting any location when no shipping location exists for the bpartner
		}

		if (!foundLoc)
		{
			logger.error("MOrder.setBPartner - Has no Ship To Address: {}", bp);
		}
	}

	public void setBPartnerLocation(@NonNull final I_C_Order order, @Nullable final I_C_BPartner_Location bpartnerLocation)
	{
		BPartnerLocationAndCaptureId bpartnerLocationAndCaptureId = bpartnerLocation != null ? BPartnerLocationAndCaptureId.ofRecord(bpartnerLocation) : null;
		OrderDocumentLocationAdapterFactory.locationAdapter(order).setLocationAndResetRenderedAddress(bpartnerLocationAndCaptureId);
	}

	@Override
	public boolean setBillLocation(final I_C_Order order)
	{
		final BPartnerId bpartnerId = extractBPartnerIdOrNull(order);
		if (bpartnerId == null)
		{
			return false; // nothing to be done
		}

		final BPartnerLocationQuery query = BPartnerLocationQuery
				.builder()
				.type(Type.BILL_TO)
				.relationBPartnerLocationId(extractBPartnerLocation(order).map(BPartnerLocationAndCaptureId::getBpartnerLocationId).orElse(null))
				.bpartnerId(bpartnerId)
				.build();
		final I_C_BPartner_Location billtoLocation = bpartnerDAO.retrieveBPartnerLocation(query);
		if (billtoLocation == null)
		{
			return false;
		}

		final BPartnerId oldBPartnerId = BPartnerId.ofRepoIdOrNull(order.getBill_BPartner_ID());
		final BPartnerLocationAndCaptureId newBPartnerLocationId = BPartnerLocationAndCaptureId.ofRecord(billtoLocation);
		final BPartnerContactId newContactId = BPartnerId.equals(oldBPartnerId, newBPartnerLocationId.getBpartnerId())
				? BPartnerContactId.ofRepoIdOrNull(oldBPartnerId, order.getBill_User_ID())
				: null;

		OrderDocumentLocationAdapterFactory
				.billLocationAdapter(order)
				.setFrom(DocumentLocation.builder()
								 .bpartnerId(newBPartnerLocationId.getBpartnerId())
								 .bpartnerLocationId(newBPartnerLocationId.getBpartnerLocationId())
								 .contactId(newContactId)
								 .build());

		return true; // found it
	}

	private static BPartnerId extractBPartnerIdOrNull(final I_C_Order order)
	{
		return BPartnerId.ofRepoIdOrNull(order.getC_BPartner_ID());
	}

	private static Optional<BPartnerLocationAndCaptureId> extractBPartnerLocation(final I_C_Order order)
	{
		return BPartnerLocationAndCaptureId.optionalOfRepoId(order.getC_BPartner_ID(), order.getC_BPartner_Location_ID(), order.getC_BPartner_Location_Value_ID());
	}

	@Override
	public CurrencyPrecision getPricePrecision(final I_C_Order order)
	{
		final PriceListId priceListId = PriceListId.ofRepoIdOrNull(order.getM_PriceList_ID());
		return priceListId != null
				? priceListBL.getPricePrecision(priceListId)
				: CurrencyPrecision.TWO;
	}

	@Override
	public CurrencyPrecision getAmountPrecision(final I_C_Order order)
	{
		final PriceListId priceListId = PriceListId.ofRepoIdOrNull(order.getM_PriceList_ID());
		return priceListId != null
				? priceListBL.getAmountPrecision(priceListId)
				: CurrencyPrecision.TWO;
	}

	@Override
	public CurrencyPrecision getTaxPrecision(final I_C_Order order)
	{
		final PriceListId priceListId = PriceListId.ofRepoIdOrNull(order.getM_PriceList_ID());
		return priceListId != null
				? priceListBL.getTaxPrecision(priceListId)
				: CurrencyPrecision.TWO;
	}

	@Override
	public boolean isTaxIncluded(@NonNull final org.compiere.model.I_C_Order order, Tax tax)
	{
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
		//
		// Calculate QtyOrdered as QtyEntered converted to stocking UOM
		final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());
		final Quantity qtyEntered = orderLineBL.getQtyEntered(orderLine);
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
	public BPartnerLocationAndCaptureId getShipToLocationId(final I_C_Order order)
	{
		return getShipToLocationIdIfExists(order)
				.orElseThrow(() -> new AdempiereException("No Ship To BP/Location defined: " + order));
	}

	private Optional<BPartnerLocationAndCaptureId> getShipToLocationIdIfExists(final I_C_Order order)
	{
		if (order.isDropShip() && order.getDropShip_BPartner_ID() > 0 && order.getDropShip_Location_ID() > 0)
		{
			return BPartnerLocationAndCaptureId.optionalOfRepoId(order.getDropShip_BPartner_ID(), order.getDropShip_Location_ID(), order.getDropShip_Location_Value_ID());
		}
		else
		{
			return extractBPartnerLocation(order);
		}
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
				? userDAO.getById(contactId)
				: null;
	}

	@Override
	public BPartnerLocationAndCaptureId getBillToLocationId(@NonNull final I_C_Order order)
	{
		final BPartnerLocationAndCaptureId billToBPLocationId = BPartnerLocationAndCaptureId.ofRepoIdOrNull(
				order.getBill_BPartner_ID(),
				order.getBill_Location_ID(),
				order.getBill_Location_Value_ID());

		return billToBPLocationId != null
				? billToBPLocationId
				: BPartnerLocationAndCaptureId.ofRepoId(order.getC_BPartner_ID(), order.getC_BPartner_Location_ID(), order.getC_BPartner_Location_Value_ID());
	}

	@Override
	@Nullable
	public BPartnerId getEffectiveBillPartnerId(@NonNull final I_C_Order orderRecord)
	{
		return BPartnerId.ofRepoIdOrNull(firstGreaterThanZero(
				orderRecord.getBill_BPartner_ID(),
				orderRecord.getC_BPartner_ID()));
	}

	@Override
	@NonNull
	public BPartnerContactId getBillToContactId(@NonNull final I_C_Order order)
	{
		final BPartnerContactId contactIdOrNull = getBillToContactIdOrNull(order);

		if (contactIdOrNull == null)
		{
			throw new AdempiereException("@Invalid@ @Contact_ID@ for Order " + order.getC_Order_ID())
					.appendParametersToMessage()
					.setParameter("getBill_BPartner_ID", order.getBill_BPartner_ID())
					.setParameter("getBill_Location_ID", order.getBill_Location_ID())
					.setParameter("getC_BPartner_ID", order.getC_BPartner_ID())
					.setParameter("getC_BPartner_Location_ID", order.getC_BPartner_Location_ID());
		}

		return contactIdOrNull;
	}

	@Override
	public boolean hasBillToContactId(@NonNull final I_C_Order order)
	{
		return null != getBillToContactIdOrNull(order);
	}

	@Nullable
	private BPartnerContactId getBillToContactIdOrNull(@NonNull final I_C_Order order)
	{
		final BPartnerContactId billToContactId = BPartnerContactId.ofRepoIdOrNull(order.getBill_BPartner_ID(), order.getBill_User_ID());
		return billToContactId != null
				? billToContactId
				: BPartnerContactId.ofRepoIdOrNull(order.getC_BPartner_ID(), order.getAD_User_ID());
	}

	private static final ModelDynAttributeAccessor<org.compiere.model.I_C_Order, BigDecimal> DYNATTR_QtyInvoicedSum = new ModelDynAttributeAccessor<>("QtyInvoicedSum", BigDecimal.class);
	private static final ModelDynAttributeAccessor<org.compiere.model.I_C_Order, BigDecimal> DYNATTR_QtyDeliveredSum = new ModelDynAttributeAccessor<>("QtyDeliveredSum", BigDecimal.class);
	private static final ModelDynAttributeAccessor<org.compiere.model.I_C_Order, BigDecimal> DYNATTR_QtyOrderedSum = new ModelDynAttributeAccessor<>("QtyOrderedSum", BigDecimal.class);

	@Override
	public void updateOrderQtySums(final org.compiere.model.I_C_Order order)
	{
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
	public boolean isSalesProposalOrQuotation(@NonNull final I_C_Order order)
	{
		final SOTrx soTrx = SOTrx.ofBoolean(order.isSOTrx());
		if (!soTrx.isSales())
		{
			// only sales orders can be proposals or quotations
			return false;
		}

		final DocTypeId docTypeId = getDocTypeIdEffectiveOrNull(order);
		return docTypeId != null && docTypeBL.isSalesProposalOrQuotation(docTypeId);
	}

	@Override
	public boolean isRequisition(@NonNull final I_C_Order order)
	{
		final SOTrx soTrx = SOTrx.ofBoolean(order.isSOTrx());
		if (!soTrx.isPurchase())
		{
			// only purchase orders can be requisitions
			return false;
		}

		final DocTypeId docTypeId = getDocTypeIdEffectiveOrNull(order);
		return docTypeId != null && docTypeBL.isRequisition(docTypeId);
	}

	@Override
	public boolean isMediated(@NonNull final I_C_Order order)
	{
		final SOTrx soTrx = SOTrx.ofBoolean(order.isSOTrx());
		if (!soTrx.isPurchase())
		{
			// only purchase orders can be mediated
			return false;
		}

		final DocTypeId docTypeId = getDocTypeIdEffectiveOrNull(order);
		return docTypeId != null && docTypeBL.isMediated(docTypeId);
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

		return docTypeBL.isPrepay(docTypeId);
	}

	@Nullable
	private DocTypeId getDocTypeIdEffectiveOrNull(@NonNull final I_C_Order order)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(order.getC_DocType_ID());
		if (docTypeId != null)
		{
			return docTypeId;
		}

		final DocTypeId docTypeTargetId = DocTypeId.ofRepoIdOrNull(order.getC_DocTypeTarget_ID());
		if (docTypeTargetId != null)
		{
			return docTypeTargetId;
		}

		return null;
	}

	@Override
	public I_C_DocType getDocTypeOrNull(@NonNull final I_C_Order order)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(order.getC_DocType_ID());
		return docTypeId != null
				? docTypeBL.getById(docTypeId)
				: null;
	}

	@Nullable
	private I_C_DocType getDocTypeTargetOrNull(@NonNull final I_C_Order order)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(order.getC_DocTypeTarget_ID());
		return docTypeId != null
				? docTypeBL.getById(docTypeId)
				: null;
	}

	@Override
	public ProjectId getProjectIdOrNull(@NonNull final OrderLineId orderLineId)
	{
		final I_C_OrderLine orderLine = orderDAO.getOrderLineById(orderLineId);
		final ProjectId lineProjectId = ProjectId.ofRepoIdOrNull(orderLine.getC_Project_ID());
		if (lineProjectId != null)
		{
			return lineProjectId;
		}

		final OrderId orderId = OrderId.ofRepoId(orderLine.getC_Order_ID());
		final I_C_Order order = orderDAO.getById(orderId);
		final ProjectId orderProjectId = ProjectId.ofRepoIdOrNull(order.getC_Project_ID());
		return orderProjectId;
	}

	@Override
	public Optional<RequestTypeId> getRequestTypeForCreatingNewRequestsAfterComplete(@NonNull final I_C_Order order)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoId(order.getC_DocType_ID());
		final I_C_DocType docType = docTypeBL.getById(docTypeId);
		if (docType.getR_RequestType_ID() <= 0)
		{
			return Optional.empty();
		}

		return Optional.ofNullable(RequestTypeId.ofRepoIdOrNull(docType.getR_RequestType_ID()));
	}

	@Override
	public ZoneId getTimeZone(@NonNull final I_C_Order order)
	{
		final OrgId orgId = OrgId.ofRepoIdOrAny(order.getAD_Org_ID());
		return orgDAO.getTimeZone(orgId);
	}

	@Override
	public void validateHaddexOrder(final I_C_Order order)
	{
		if (!isHaddexOrder(order))
		{
			return;
		}

		boolean hasHaddexLine = orderDAO.retrieveOrderLines(order)
				.stream()
				.anyMatch(lineId -> productBL.isHaddexProduct(ProductId.ofRepoId(lineId.getM_Product_ID())));

		if (!hasHaddexLine)
		{
			return;
		}

		validateHaddexDate(order);
	}

	@Override
	public void validateHaddexDate(final I_C_Order order)
	{
		final ZoneId timeZone = getTimeZone(order);
		final org.compiere.model.I_C_BPartner partner = partnerDAO.getById(order.getC_BPartner_ID());
		final long differenceBetweenHaddexCheckDateAndPromisedDateInMonths = Math.abs(
				ChronoUnit.MONTHS.between(
						TimeUtil.asZonedDateTime(partner.getDateHaddexCheck(), timeZone),
						TimeUtil.asZonedDateTime(order.getDatePromised(), timeZone)
				));

		if (differenceBetweenHaddexCheckDateAndPromisedDateInMonths > getMaxHaddexAgeInMonths(order.getAD_Client_ID(), order.getAD_Org_ID()))
		{
			throw new AdempiereException(MSG_HADDEX_CHECK_ERROR).markAsUserValidationError();
		}
	}

	@Override
	public boolean isHaddexOrder(final I_C_Order order)
	{
		if (!order.isSOTrx())
		{
			return false;
		}

		final org.compiere.model.I_C_BPartner partner = partnerDAO.getById(order.getC_BPartner_ID());

		if (!partner.isHaddexCheck())
		{
			return false;
		}

		if (partner.getDateHaddexCheck() == null)
		{
			return false;
		}

		return true;
	}

	private int getMaxHaddexAgeInMonths(int clientID, int orgID)
	{
		final int months = sysConfigBL.getIntValue(SYS_CONFIG_MAX_HADDEX_AGE_IN_MONTHS, 24, clientID, orgID);
		if (months > 0)
		{
			return months;
		}
		return Integer.MAX_VALUE;
	}

	public void closeOrder(final OrderId orderId)
	{
		final I_C_Order order = getById(orderId);
		documentBL.processEx(order, X_C_Order.DOCACTION_Close);

		Loggables.withLogger(logger, Level.DEBUG).addLog("Order closed for C_Order_ID={}", order.getC_Order_ID());
	}
}

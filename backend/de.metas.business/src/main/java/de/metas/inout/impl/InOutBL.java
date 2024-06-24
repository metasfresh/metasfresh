package de.metas.inout.impl;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.cache.CacheMgt;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.IDocTypeDAO;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.ITranslatableString;
import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.inout.location.adapter.InOutDocumentLocationAdapterFactory;
import de.metas.interfaces.I_C_BPartner;
import de.metas.invoice.service.IMatchInvDAO;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderLineId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.IPricingBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.request.RequestTypeId;
import de.metas.request.api.IRequestDAO;
import de.metas.request.api.IRequestTypeDAO;
import de.metas.request.api.RequestCandidate;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.comparator.ComparatorChain;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_R_Request;
import org.compiere.model.X_M_InOut;
import org.compiere.model.X_R_Request;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class InOutBL implements IInOutBL
{
	private static final String VIEW_M_Shipment_Statistics_V = "M_Shipment_Statistics_V";

	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final IPricingBL pricingBL = Services.get(IPricingBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IMatchInvDAO matchInvDAO = Services.get(IMatchInvDAO.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IRequestTypeDAO requestTypeDAO = Services.get(IRequestTypeDAO.class);
	private final IRequestDAO requestsRepo = Services.get(IRequestDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	@Override
	public I_M_InOut getById(@NonNull final InOutId inoutId)
	{
		return inOutDAO.getById(inoutId);
	}

	@Override
	public void save(@NonNull final I_M_InOut inout)
	{
		inOutDAO.save(inout);
	}

	@Override
	public List<I_M_InOutLine> getLines(@NonNull final I_M_InOut inout)
	{
		return inOutDAO.retrieveLines(inout);
	}

	@Override
	public List<I_M_InOutLine> getLines(@NonNull final InOutId inoutId)
	{
		final I_M_InOut inout = getById(inoutId);
		return getLines(inout);
	}

	@Override
	public IPricingContext createPricingCtx(@NonNull final org.compiere.model.I_M_InOutLine inOutLine)
	{
		final I_M_InOut inOut = inOutLine.getM_InOut();

		SOTrx soTrx = SOTrx.ofBoolean(inOut.isSOTrx());
		final BPartnerLocationAndCaptureId bpLocationId = InOutDocumentLocationAdapterFactory.locationAdapter(inOut).getBPartnerLocationAndCaptureId();

		final IEditablePricingContext pricingCtx = pricingBL.createInitialContext(
				OrgId.ofRepoIdOrAny(inOutLine.getAD_Org_ID()),
				ProductId.ofRepoId(inOutLine.getM_Product_ID()),
				bpLocationId.getBpartnerId(),
				Quantitys.create(inOutLine.getQtyEntered(), UomId.ofRepoId(inOutLine.getC_UOM_ID())),
				soTrx);

		I_M_PricingSystem pricingSystem = getPricingSystemOrNull(inOut, soTrx);

		if (pricingSystem == null)
		{
			if (isReturnMovementType(inOut.getMovementType()))
			{
				// 08358
				// in case no pricing system was found for the current IsSOTrx AND we are dealing with leergut inouts
				// we are allowed to take the pricing system from the other opposite SOTrx, since the boxes have the same prices
				// either they are bought or sold
				soTrx = soTrx.invert();
				pricingSystem = getPricingSystemOrNull(inOut, soTrx);
			}
		}

		if (pricingSystem == null)
		{
			throw new AdempiereException("@NotFound@ @M_PricingSystem_ID@"
												 + "\n @M_InOut_ID@: " + inOut
												 + "\n @C_BPartner_ID@: " + inOut.getC_BPartner_ID());
		}

		final PricingSystemId pricingSystemId = PricingSystemId.ofRepoId(pricingSystem.getM_PricingSystem_ID());
		Check.assumeNotNull(pricingSystemId, "No pricing system found for M_InOut_ID={}", inOut);

		final PriceListId priceListId = priceListDAO.retrievePriceListIdByPricingSyst(
				pricingSystemId,
				bpLocationId,
				soTrx);
		Check.errorIf(priceListId == null,
					  "No price list found for M_InOutLine_ID {}; M_InOut.M_PricingSystem_ID={}, M_InOut.C_BPartner_Location_ID={}, M_InOut.SOTrx={}",
					  inOutLine.getM_InOutLine_ID(), pricingSystemId, inOut.getC_BPartner_Location_ID(), soTrx);

		pricingCtx.setPricingSystemId(pricingSystemId);
		pricingCtx.setPriceListId(priceListId);
		pricingCtx.setPriceDate(TimeUtil.asLocalDate(inOut.getDateOrdered()));

		pricingCtx.setFailIfNotCalculated();

		// note: the qty was already passed to the pricingCtx upon creation, further up.
		return pricingCtx;
	}

	@Override
	public IPricingResult getProductPrice(final org.compiere.model.I_M_InOutLine inOutLine)
	{
		final IPricingContext pricingCtx = createPricingCtx(inOutLine);
		return pricingBL.calculatePrice(pricingCtx);

	}

	@Override
	public StockQtyAndUOMQty getStockQtyAndCatchQty(@NonNull final I_M_InOutLine inoutLine)
	{
		final UomId catchUomIdOrNull;
		if (inoutLine.getQtyDeliveredCatch().signum() != 0)
		{
			catchUomIdOrNull = UomId.ofRepoIdOrNull(inoutLine.getCatch_UOM_ID());
		}
		else
		{
			catchUomIdOrNull = null;
		}

		final ProductId productId = ProductId.ofRepoId(inoutLine.getM_Product_ID());

		return StockQtyAndUOMQtys.create(
				inoutLine.getMovementQty(),
				productId,
				inoutLine.getQtyDeliveredCatch(),
				catchUomIdOrNull);
	}

	@Override
	public StockQtyAndUOMQty getStockQtyAndQtyInUOM(@NonNull final I_M_InOutLine inoutLine)
	{
		final ProductId productId = ProductId.ofRepoId(inoutLine.getM_Product_ID());
		final UomId uomId = UomId.ofRepoId(inoutLine.getC_UOM_ID());
		return StockQtyAndUOMQtys.create(
				inoutLine.getMovementQty(),
				productId,
				inoutLine.getQtyEntered(),
				uomId);
	}

	@Override
	public I_M_PricingSystem getPricingSystem(final I_M_InOut inOut, final boolean throwEx)
	{
		final I_M_PricingSystem pricingSystem = getPricingSystemOrNull(inOut, SOTrx.ofBoolean(inOut.isSOTrx()));

		if (pricingSystem == null)
		{
			if (throwEx)
			{
				throw new AdempiereException("@NotFound@ @M_PricingSystem_ID@"
													 + "\n @C_BPartner_ID@: " + inOut.getC_BPartner_ID());
			}
		}
		return pricingSystem;
	}

	@NonNull
	public StockQtyAndUOMQty extractInOutLineQty(
			@NonNull final I_M_InOutLine inOutLineRecord,
			@NonNull final InvoicableQtyBasedOn invoicableQtyBasedOn)
	{
		switch (invoicableQtyBasedOn)
		{
			case CatchWeight:
				final StockQtyAndUOMQty stockQtyAndCatchQty = getStockQtyAndCatchQty(inOutLineRecord);
				if (stockQtyAndCatchQty.getUOMQtyOpt().isPresent())
				{
					return stockQtyAndCatchQty;
				}

				// fallback if the given iol simply doesn't have a catch weight (which is a common case)
				return getStockQtyAndQtyInUOM(inOutLineRecord);
			case NominalWeight:
				return getStockQtyAndQtyInUOM(inOutLineRecord);
			default:
				throw new AdempiereException("Unsupported invoicableQtyBasedOn=" + invoicableQtyBasedOn);
		}
	}

	/**
	 * Find the pricing system based on the soTrx. This method will be used in the rare cases when we are not relying upon the SOTrx of the inout, because we need the pricing system for the opposite
	 * SOTrx nature.
	 */
	@Nullable
	private I_M_PricingSystem getPricingSystemOrNull(final I_M_InOut inOut, final SOTrx soTrx)
	{
		if (inOut.getC_Order_ID() > 0 && inOut.getC_Order().getM_PricingSystem_ID() > 0)
		{
			final PricingSystemId pricingSystemId = PricingSystemId.ofRepoId(inOut.getC_Order().getM_PricingSystem_ID());
			return priceListDAO.getPricingSystemById(pricingSystemId);
		}

		final PricingSystemId pricingSystemId = bpartnerDAO.retrievePricingSystemIdOrNull(BPartnerId.ofRepoId(inOut.getC_BPartner_ID()), soTrx);
		if (pricingSystemId == null)
		{
			return null;
		}

		return priceListDAO.getPricingSystemById(pricingSystemId);
	}

	private boolean isReversal(final int recordId, final int recordReversalId)
	{
		if (recordId <= 0)
		{
			// InOut was not already saved.
			// Consider it as NOT reversal
			return false;
		}

		if (recordReversalId <= 0)
		{
			// no reversal was set, so for sure this is not a reversal
			return false;
		}

		Check.assume(recordId != recordReversalId, "record id({}) and reversal record id({}) shall not be the same", recordId, recordReversalId);

		if (recordId < recordReversalId)
		{
			// this document was created before the linked reversal
			// so this is the orginal document and the other one is the actual reversal
			return false;
		}

		// At this point we have: inOutId > reversalInOutId
		// So our document is the actual reversal
		// and the linked reversal is the original document
		return true;
	}

	@Override
	public boolean isReversal(final I_M_InOut inout)
	{
		Check.assumeNotNull(inout, "inout not null");

		final int recordId = inout.getM_InOut_ID();
		final int recordReversalId = inout.getReversal_ID();
		return isReversal(recordId, recordReversalId);
	}

	@Override
	public boolean isReversal(final org.compiere.model.I_M_InOutLine inoutLine)
	{
		Check.assumeNotNull(inoutLine, "inoutLine not null");

		final int recordId = inoutLine.getM_InOutLine_ID();
		final int recordReversalId = inoutLine.getReversalLine_ID();
		return isReversal(recordId, recordReversalId);
	}

	@Override
	public I_M_InOutLine newInOutLine(final I_M_InOut inout)
	{
		return newInOutLine(inout, I_M_InOutLine.class);
	}

	@Override
	public <T extends I_M_InOutLine> T newInOutLine(final I_M_InOut inout, final Class<T> modelClass)
	{
		final T line = InterfaceWrapperHelper.newInstance(modelClass, inout);
		line.setAD_Org_ID(inout.getAD_Org_ID());
		line.setM_InOut(inout);

		final I_M_Warehouse warehouse = InterfaceWrapperHelper.load(inout.getM_Warehouse_ID(), I_M_Warehouse.class);
		final I_M_Locator locator = warehouseBL.getDefaultLocator(warehouse);
		if (locator != null)
		{
			line.setM_Locator_ID(locator.getM_Locator_ID());
		}
		return line;
	}

	@Override
	public boolean getSOTrxFromMovementType(final String movementType)
	{
		if (X_M_InOut.MOVEMENTTYPE_CustomerShipment.equals(movementType)
				|| X_M_InOut.MOVEMENTTYPE_CustomerReturns.equals(movementType))
		{
			return true;
		}
		else if (X_M_InOut.MOVEMENTTYPE_VendorReceipts.equals(movementType)
				|| X_M_InOut.MOVEMENTTYPE_VendorReturns.equals(movementType))
		{
			return false;
		}
		else
		{
			throw new AdempiereException("Unsupported MovementType: " + movementType);
		}
	}

	@Override
	public boolean isReturnMovementType(final String movementType)
	{
		return X_M_InOut.MOVEMENTTYPE_CustomerReturns.equals(movementType)
				|| X_M_InOut.MOVEMENTTYPE_VendorReturns.equals(movementType);
	}

	@Override
	public BigDecimal negateIfReturnMovmenType(
			@NonNull final I_M_InOutLine iol,
			@NonNull final BigDecimal qty)
	{
		final I_M_InOut inoutRecord = InterfaceWrapperHelper.load(iol.getM_InOut_ID(), I_M_InOut.class);
		if (isReturnMovementType(inoutRecord.getMovementType()))
		{
			return qty.negate();
		}
		return qty;
	}

	@Override
	public BigDecimal getEffectiveStorageChange(final I_M_InOutLine iol)
	{

		final String movementType = iol.getM_InOut().getMovementType();
		final BigDecimal multiplier;

		if (X_M_InOut.MOVEMENTTYPE_CustomerReturns.equals(movementType)
				|| X_M_InOut.MOVEMENTTYPE_VendorReceipts.equals(movementType))
		{
			multiplier = BigDecimal.ONE; // storage increase
		}
		else if (X_M_InOut.MOVEMENTTYPE_CustomerShipment.equals(movementType)
				|| X_M_InOut.MOVEMENTTYPE_VendorReturns.equals(movementType))
		{
			multiplier = BigDecimal.ONE.negate(); // storage decrease
		}
		else
		{
			Check.errorIf(true, "iol={} has an M_InOut with Unexpected MovementType={}", iol, movementType);
			return BigDecimal.ZERO; // won't normally be reached.
		}

		return iol.getMovementQty().multiply(multiplier);
	}

	@Override
	public List<I_M_InOutLine> sortLines(final I_M_InOut inOut)
	{
		final HashMap<Integer, Integer> inoutLineId2orderId = new HashMap<>();

		final List<I_M_InOutLine> lines = inOutDAO.retrieveLines(inOut);
		for (int i = 0; i < lines.size(); i++)
		{
			final I_M_InOutLine iol = lines.get(i);

			final int currentOrderID = iol.getC_OrderLine_ID() > 0
					? iol.getC_OrderLine().getC_Order_ID()
					: 0;

			final int currentLineID = iol.getM_InOutLine_ID();

			// if this is not a comment line, then store its C_Order_ID in the map
			if (currentOrderID != 0)
			{
				inoutLineId2orderId.put(currentLineID, currentOrderID);
				continue;
			}

			int valueIdToUse = -1;

			// If this is a comment line, then iterate further, to find the next not-comment line
			for (int j = 1; i + j < lines.size(); j++)
			{
				final I_M_InOutLine nextLine = lines.get(i + j);

				final int nextID = nextLine.getC_OrderLine_ID() > 0
						? nextLine.getC_OrderLine().getC_Order_ID()
						: 0;

				if (nextID != 0)   // If this is a valid ID, put it into the Map.
				{
					valueIdToUse = nextID;
					break;
				}
			}

			inoutLineId2orderId.put(currentLineID, valueIdToUse);
		}

		Check.assume(inoutLineId2orderId.size() == lines.size(), "Every line's id has been added to map '" + inoutLineId2orderId + "'");

		final ComparatorChain<I_M_InOutLine> mainComparator = new ComparatorChain<>();

		//
		// DocLine Sort Preference criteria goes first
		// final Comparator<I_M_InOutLine> docLineSortComparator = getDocLineSortComparator(inOut);
		// mainComparator.addComparator(docLineSortComparator); // commented out: this is only called in one place; if needed, see history / I_C_DocLine_Sort

		//
		// Sort by order lines
		final Comparator<I_M_InOutLine> orderLineComparator = getOrderLineComparator(inoutLineId2orderId);
		mainComparator.addComparator(orderLineComparator);

		lines.sort(mainComparator);

		return lines;
	}

	private static Comparator<I_M_InOutLine> getOrderLineComparator(final HashMap<Integer, Integer> inoutLineId2orderId)
	{
		return (line1, line2) -> {
			// InOut_ID
			final int order_ID1 = inoutLineId2orderId.get(line1.getM_InOutLine_ID());
			final int order_ID2 = inoutLineId2orderId.get(line2.getM_InOutLine_ID());

			if (order_ID1 > order_ID2)
			{
				return 1;
			}
			if (order_ID1 < order_ID2)
			{
				return -1;
			}

			// LineNo
			final int line1No = line1.getLine();
			final int line2No = line2.getLine();

			return Integer.compare(line1No, line2No);
		};
	}

	@Override
	public void deleteMatchInvs(final I_M_InOut inout)
	{
		final List<I_M_MatchInv> matchInvs = matchInvDAO.retrieveForInOut(inout);
		for (final I_M_MatchInv matchInv : matchInvs)
		{
			matchInv.setProcessed(false);
			InterfaceWrapperHelper.delete(matchInv);
		}
	}

	@Override
	public void deleteMatchInvsForInOutLine(final I_M_InOutLine iol)
	{
		//
		// Delete M_MatchInvs (08627)
		for (final I_M_MatchInv matchInv : matchInvDAO.retrieveForInOutLine(iol))
		{
			matchInv.setProcessed(false); // delete it even if it's processed, because all M_MatchInv are processed on save new.
			InterfaceWrapperHelper.delete(matchInv);
		}
	}

	@Override
	public void invalidateStatistics(final I_M_InOut inout)
	{
		if (inout.isSOTrx())
		{
			final InOutId shipmentId = InOutId.ofRepoId(inout.getM_InOut_ID());
			final Set<InOutAndLineId> shipmentAndLineIds = inOutDAO.retrieveLinesForInOutId(shipmentId);
			if (!shipmentAndLineIds.isEmpty())
			{
				CacheMgt.get().reset(CacheInvalidateMultiRequest.rootRecords(
						VIEW_M_Shipment_Statistics_V,
						shipmentAndLineIds,
						InOutAndLineId::getInOutLineId));
			}
		}
	}

	@Override
	public void invalidateStatistics(final I_M_InOutLine inoutLine)
	{
		if (inoutLine.getM_InOut().isSOTrx())
		{
			CacheMgt.get().reset(VIEW_M_Shipment_Statistics_V, inoutLine.getM_InOutLine_ID());
		}
	}

	@Override
	@Nullable
	public I_C_Order getOrderByInOutLine(@NonNull final I_M_InOutLine inOutLine)
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(inOutLine.getC_OrderLine_ID());

		if (orderLineId == null)
		{
			return null;
		}

		final I_C_OrderLine orderLine = orderDAO.getOrderLineById(orderLineId);

		return orderLine.getC_Order();
	}

	private RequestTypeId getRequestTypeId(final SOTrx soTrx)
	{
		return soTrx.isSales()
				? requestTypeDAO.retrieveCustomerRequestTypeId()
				: requestTypeDAO.retrieveVendorRequestTypeId();
	}

	@Override
	public Optional<RequestTypeId> getRequestTypeForCreatingNewRequestsAfterComplete(@NonNull final I_M_InOut inOut)
	{
		final I_C_DocType docType = docTypeDAO.getById(inOut.getC_DocType_ID());

		if (docType.getR_RequestType_ID() <= 0)
		{
			return Optional.empty();
		}

		return Optional.ofNullable(RequestTypeId.ofRepoIdOrNull(docType.getR_RequestType_ID()));
	}

	@Override
	public I_R_Request createRequestFromInOut(@NonNull final I_M_InOut inOut)
	{
		final Optional<RequestTypeId> requestType = getRequestTypeForCreatingNewRequestsAfterComplete(inOut);

		final RequestCandidate requestCandidate = RequestCandidate.builder()
				.summary(inOut.getDescription() != null ? inOut.getDescription() : " ")
				.confidentialType(X_R_Request.CONFIDENTIALTYPE_Internal)
				.orgId(OrgId.ofRepoId(inOut.getAD_Org_ID()))
				.recordRef(TableRecordReference.of(inOut))
				.requestTypeId(requestType.orElseGet(() -> getRequestTypeId(SOTrx.ofBoolean(inOut.isSOTrx()))))
				.partnerId(BPartnerId.ofRepoId(inOut.getC_BPartner_ID()))
				.userId(UserId.ofRepoIdOrNull(inOut.getAD_User_ID()))
				.dateDelivered(TimeUtil.asZonedDateTime(inOut.getMovementDate()))
				.build();

		return requestsRepo.createRequest(requestCandidate);
	}

	@Nullable
	public String getLocationEmail(@NonNull final InOutId inOutId)
	{
		final I_M_InOut inout = inOutDAO.getById(inOutId);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(inout.getC_BPartner_ID());
		final I_C_BPartner_Location bpartnerLocation = bpartnerDAO.getBPartnerLocationByIdInTrx(BPartnerLocationId.ofRepoId(bpartnerId, inout.getC_BPartner_Location_ID()));

		final String locationEmail = bpartnerLocation.getEMail();
		if (!Check.isEmpty(locationEmail))
		{
			return locationEmail;
		}

		final BPartnerContactId contactId = BPartnerContactId.ofRepoIdOrNull(bpartnerId, inout.getAD_User_ID());
		if (contactId == null)
		{
			return null;
		}
		return bpartnerDAO.getContactLocationEmail(contactId);
	}

	@Override
	@NonNull
	public LocalDate retrieveMovementDate(@NonNull final I_M_InOut inOut)
	{
		final OrgId orgId = OrgId.ofRepoId(inOut.getAD_Org_ID());
		final ZoneId timeZone = orgDAO.getTimeZone(orgId);

		return Objects.requireNonNull(TimeUtil.asLocalDate(inOut.getMovementDate(), timeZone));
	}

	@Override
	public void updateDescriptionAndDescriptionBottomFromDocType(@NonNull final I_M_InOut inOut)
	{

		final I_C_DocType docType = docTypeDAO.getById(inOut.getC_DocType_ID());
		if (docType == null)
		{
			return;
		}

		if (!docType.isCopyDescriptionToDocument())
		{
			return;
		}

		final I_C_BPartner bPartner = getBPartnerOrNull(inOut);

		final String adLanguage = CoalesceUtil.coalesce(
				bPartner == null ? null : bPartner.getAD_Language(),
				Env.getAD_Language());

		final IModelTranslationMap docTypeTrl = InterfaceWrapperHelper.getModelTranslationMap(docType);
		final ITranslatableString description = docTypeTrl.getColumnTrl(I_C_DocType.COLUMNNAME_Description, docType.getDescription());
		final ITranslatableString documentNote = docTypeTrl.getColumnTrl(I_C_DocType.COLUMNNAME_DocumentNote, docType.getDocumentNote());

		inOut.setDescription(description.translate(adLanguage));
		inOut.setDescriptionBottom(documentNote.translate(adLanguage));
	}

	private I_C_BPartner getBPartnerOrNull(@NonNull final I_M_InOut inOut)
	{
		final BPartnerId bPartnerId = BPartnerId.ofRepoIdOrNull(inOut.getC_BPartner_ID());

		return bPartnerId != null
				? bpartnerDAO.getById(bPartnerId, I_C_BPartner.class)
				: null;
	}
}

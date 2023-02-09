package de.metas.handlingunits.receiptschedule.impl;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.ClearanceStatus;
import de.metas.handlingunits.ClearanceStatusInfo;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.InstantAndOrgId;
import de.metas.product.IProductDAO;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.List;

class ReceiptScheduleCreatePlanningVHUCommand
{
	private final static AdMessageKey MESSAGE_ClearanceStatusInfo_Receipt = AdMessageKey.of("ClearanceStatusInfo.Receipt");

	// services
	private final IBPartnerOrgBL partnerOrgBL = Services.get(IBPartnerOrgBL.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IHUReceiptScheduleBL huReceiptScheduleBL;
	private final IHUContextFactory huContextFactory;

	// params
	private final I_M_ReceiptSchedule receiptSchedule;
	private final Quantity qtyToReceive;

	@Builder
	private ReceiptScheduleCreatePlanningVHUCommand(
			@NonNull final IHUReceiptScheduleBL huReceiptScheduleBL,
			@NonNull final IHUContextFactory huContextFactory,
			//
			@NonNull final I_M_ReceiptSchedule receiptSchedule,
			@Nullable final Quantity qtyToReceive)
	{
		this.huReceiptScheduleBL = huReceiptScheduleBL;
		this.huContextFactory = huContextFactory;

		this.receiptSchedule = receiptSchedule;
		this.qtyToReceive = qtyToReceive;
	}

	public I_M_HU execute()
	{
		//
		// Create allocation request for the quantity user entered
		final IAllocationRequest allocationRequest = createAllocationRequest();
		if (allocationRequest == null || allocationRequest.isZeroQty())
		{
			return null;
		}

		// task 09717
		// make sure the attributes are initialized in case of multiple row selection, also
		huReceiptScheduleBL.setInitialAttributeValueDefaults(allocationRequest, ImmutableList.of(receiptSchedule));

		//
		// Allocation Destination: HU producer which will create 1 VHU
		final HUProducerDestination huProducer = HUProducerDestination.ofVirtualPI();

		//
		// Transfer Qty
		HULoader.builder()
				.source(createAllocationSource()) // our receipt schedule
				.destination(huProducer)
				.allowPartialUnloads(false)
				.allowPartialLoads(false)
				.load(allocationRequest);

		//
		// Get created VHU and return it
		final List<I_M_HU> hus = huProducer.getCreatedHUs();
		if (hus == null || hus.size() != 1)
		{
			throw new HUException("One and only one VHU was expected but we got: " + hus);
		}
		final I_M_HU vhu = hus.get(0);

		huReceiptScheduleBL.updateHUAttributesFromReceiptSchedule(vhu, receiptSchedule);

		InterfaceWrapperHelper.setTrxName(vhu, ITrx.TRXNAME_None);
		return vhu;
	}

	private IAllocationSource createAllocationSource()
	{
		final IProductStorage productStorage = huReceiptScheduleBL.createProductStorage(receiptSchedule);
		return new GenericAllocationSourceDestination(productStorage, receiptSchedule);
	}

	protected Quantity getEffectiveQtyToReceive()
	{
		if (qtyToReceive != null)
		{
			return qtyToReceive;
		}
		else
		{
			final StockQtyAndUOMQty qty = huReceiptScheduleBL.getQtyToMove(receiptSchedule);
			return qty.getStockQty().toZeroIfNegative();
		}
	}

	@Nullable
	private IAllocationRequest createAllocationRequest()
	{
		// Get Qty
		final Quantity qty = getEffectiveQtyToReceive();
		if (qty.signum() <= 0)
		{
			// nothing to do
			return null;
		}

		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(receiptSchedule.getAD_Client_ID(), receiptSchedule.getAD_Org_ID());
		final IMutableHUContext huContextInitial = huContextFactory.createMutableHUContextForProcessing(Env.getCtx(), clientAndOrgId);
		final I_M_Product product = productDAO.getById(receiptSchedule.getM_Product_ID());
		final ClearanceStatus clearanceStatus = ClearanceStatus.ofNullableCode(product.getHUClearanceStatus());
		final ClearanceStatusInfo clearanceStatusInfo;
		if (clearanceStatus != null)
		{
			final String language = partnerOrgBL.getOrgLanguageOrLoggedInUserLanguage(clientAndOrgId.getOrgId());
			clearanceStatusInfo = ClearanceStatusInfo.builder()
					.clearanceStatus(clearanceStatus)
					.clearanceNote(TranslatableStrings.adMessage(MESSAGE_ClearanceStatusInfo_Receipt).translate(language))
					.clearanceDate(InstantAndOrgId.ofInstant(SystemTime.asInstant(), clientAndOrgId.getOrgId()))
					.build();

		}
		else
		{
			clearanceStatusInfo = null;
		}

		return AllocationUtils.builder()
				.setHUContext(huContextInitial)
				.setDateAsToday()
				.setProduct(product)
				.setQuantity(qty)
				.setFromReferencedModel(receiptSchedule)
				.setForceQtyAllocation(true)
				.setClearanceStatusInfo(clearanceStatusInfo)
				.create();
	}
}

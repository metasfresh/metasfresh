package de.metas.ui.web.handlingunits.process;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.ClearanceStatus;
import de.metas.handlingunits.ClearanceStatusInfo;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.InstantAndOrgId;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.product.IProductDAO;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.springframework.context.annotation.Profile;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

/*
 * #%L
 * metasfresh-webui-api
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

/**
 * Process used to receive HUs for more then one receipt schedule.
 * <p>
 * It creates one VHU for each receipt schedule, using it's remaining quantity to move.
 *
 * @author metas-dev <dev@metasfresh.com>
 * https://github.com/metasfresh/metasfresh-webui/issues/182
 */
@Profile(Profiles.PROFILE_Webui)
public class WEBUI_M_ReceiptSchedule_ReceiveCUs extends ReceiptScheduleBasedProcess
{
	private final static AdMessageKey MESSAGE_ClearanceStatusInfo_Receipt = AdMessageKey.of("ClearanceStatusInfo.Receipt");

	private final transient IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);
	private final transient IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient IBPartnerOrgBL partnerOrgBL = Services.get(IBPartnerOrgBL.class);
	private final transient IProductDAO productDAO = Services.get(IProductDAO.class);

	private boolean allowMultipleReceiptsSchedules = true; // by default we shall allow multiple lines
	private boolean allowNoQuantityAvailable = false; // by default we shall not allow lines which have no quantity available

	protected final void setAllowMultipleReceiptsSchedules(final boolean allowMultipleReceiptsSchedules)
	{
		this.allowMultipleReceiptsSchedules = allowMultipleReceiptsSchedules;
	}

	protected final void setAllowNoQuantityAvailable(final boolean allowNoQuantityAvailable)
	{
		this.allowNoQuantityAvailable = allowNoQuantityAvailable;
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		//
		// Check if we are allowed to select multiple lines
		if (!allowMultipleReceiptsSchedules && context.isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("select only one line");
		}

		//
		// Fetch the receipt schedules which have some qty available for receiving
		final List<I_M_ReceiptSchedule> receiptSchedules = context.streamSelectedModels(I_M_ReceiptSchedule.class)
				.filter(receiptSchedule -> allowNoQuantityAvailable || getDefaultAvailableQtyToReceive(receiptSchedule).signum() > 0)
				.collect(ImmutableList.toImmutableList());
		if (receiptSchedules.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("nothing to receive");
		}

		//
		// Make sure each of them are eligible for receiving
		{
			final ProcessPreconditionsResolution rejectResolution = receiptSchedules.stream()
					.map(WEBUI_M_ReceiptSchedule_ReceiveHUs_Base::checkEligibleForReceivingHUs)
					.filter(resolution -> !resolution.isAccepted())
					.findFirst()
					.orElse(null);
			if (rejectResolution != null)
			{
				return rejectResolution;
			}
		}

		//
		// If more than one line selected, make sure the lines make sense together
		// * enforce same BPartner (task https://github.com/metasfresh/metasfresh-webui/issues/207)
		if (receiptSchedules.size() > 1)
		{
			final long bpartnersCount = receiptSchedules
					.stream()
					.map(receiptScheduleBL::getC_BPartner_Effective_ID)
					.distinct()
					.count();
			if (bpartnersCount != 1)
			{
				return ProcessPreconditionsResolution.rejectWithInternalReason("select only one BPartner");
			}
		}

		//
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected final String doIt()
	{
		final List<I_M_HU> hus = streamReceiptSchedulesToReceive()
				.map(this::createPlanningVHU)
				.filter(Objects::nonNull)
				.collect(GuavaCollectors.toImmutableList());

		openHUsToReceive(hus);

		return MSG_OK;
	}

	protected Stream<I_M_ReceiptSchedule> streamReceiptSchedulesToReceive()
	{
		return retrieveSelectedRecordsQueryBuilder(I_M_ReceiptSchedule.class)
				.create()
				.stream(I_M_ReceiptSchedule.class);
	}

	@Nullable
	private I_M_HU createPlanningVHU(final I_M_ReceiptSchedule receiptSchedule)
	{
		//
		// Create allocation request for the quantity user entered
		final IAllocationRequest allocationRequest = createAllocationRequest(receiptSchedule);
		if (allocationRequest == null || allocationRequest.isZeroQty())
		{
			return null;
		}

		// task 09717
		// make sure the attributes are initialized in case of multiple row selection, also
		huReceiptScheduleBL.setInitialAttributeValueDefaults(allocationRequest, ImmutableList.of(receiptSchedule));

		//
		// Allocation Source: our receipt schedule
		final IAllocationSource allocationSource = huReceiptScheduleBL.createAllocationSource(receiptSchedule);

		//
		// Allocation Destination: HU producer which will create 1 VHU
		final HUProducerDestination huProducer = HUProducerDestination.ofVirtualPI();

		//
		// Transfer Qty
		HULoader.of(allocationSource, huProducer)
				.setAllowPartialUnloads(false)
				.setAllowPartialLoads(false)
				.load(allocationRequest);

		//
		// Get created VHU and return it
		final List<I_M_HU> hus = huProducer.getCreatedHUs();
		if (hus == null || hus.size() != 1)
		{
			throw new HUException("One and only one VHU was expected but we got: " + hus);
		}
		final I_M_HU vhu = hus.get(0);

		updateAttributes(vhu, receiptSchedule);

		InterfaceWrapperHelper.setTrxName(vhu, ITrx.TRXNAME_None);
		return vhu;
	}

	protected final BigDecimal getDefaultAvailableQtyToReceive(@NonNull final I_M_ReceiptSchedule rs)
	{
		final StockQtyAndUOMQty qty = receiptScheduleBL.getQtyToMove(rs);
		return qty == null || qty.signum() <= 0 ? BigDecimal.ZERO : qty.getStockQty().toBigDecimal();
	}

	protected BigDecimal getEffectiveQtyToReceive(final I_M_ReceiptSchedule rs)
	{
		return getDefaultAvailableQtyToReceive(rs);
	}

	@Nullable
	private IAllocationRequest createAllocationRequest(final I_M_ReceiptSchedule rs)
	{
		// Get Qty
		final BigDecimal qty = getEffectiveQtyToReceive(rs);
		if (qty.signum() <= 0)
		{
			// nothing to do
			return null;
		}

		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(rs.getAD_Client_ID(), rs.getAD_Org_ID());
		final IMutableHUContext huContextInitial = Services.get(IHUContextFactory.class).createMutableHUContextForProcessing(getCtx(), clientAndOrgId);
		final I_M_Product product = productDAO.getById(rs.getM_Product_ID());
		final ClearanceStatus clearanceStatus = ClearanceStatus.ofNullableCode(product.getHUClearanceStatus());
		final ClearanceStatusInfo clearanceStatusInfo;
		if (clearanceStatus != null)
		{
			final String language = partnerOrgBL.getOrgLanguageOrLoggedInUserLanguage(clientAndOrgId.getOrgId());
			clearanceStatusInfo = ClearanceStatusInfo.builder()
					.clearanceStatus(clearanceStatus)
					.clearanceNote(msgBL.getMsg(language, MESSAGE_ClearanceStatusInfo_Receipt))
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
				.setQuantity(new Quantity(qty, loadOutOfTrx(rs.getC_UOM_ID(), I_C_UOM.class)))
				.setFromReferencedModel(rs)
				.setForceQtyAllocation(true)
				.setClearanceStatusInfo(clearanceStatusInfo)
				.create();
	}

}

package de.metas.ui.web.handlingunits.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.deliveryplanning.DeliveryPlanningId;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL.CreateReceiptsParameters;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL.CreateReceiptsParameters.CreateReceiptsParametersBuilder;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.inoutcandidate.api.impl.ReceiptMovementDateRule;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.product.ProductRepository;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorRowAttributes;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.handlingunits.process.HUEditorReceiptSources.ReferencedReceiptSource;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.vertical.pharma.securpharm.attribute.SecurPharmAttributesStatus;
import de.metas.vertical.pharma.securpharm.service.SecurPharmService;
import lombok.NonNull;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Delivery_Planning;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

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

public abstract class WEBUI_M_HU_CreateReceipt_Base
		extends WEBUI_M_HU_Receipt_Base
		implements IProcessPrecondition
{
	private static final AdMessageKey MSG_ScanRequired = AdMessageKey.of("securPharm.scanRequiredError");
	private static final AdMessageKey MSG_MissingMandatoryHUAttribute = AdMessageKey.of("WEBUI_Receipt_Missing_Mandatory_HU_Attribute");

	@Autowired
	private IViewsRepository viewsRepo;
	@Autowired
	private DocumentCollection documentsCollection;
	@Autowired
	private SecurPharmService securPharmService;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private DeliveryPlanningService deliveryPlanningService;
	private final transient IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);
	private final transient IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

	/**
	 * Only allows rows whose HU is in the "planning" status.
	 */
	@Override
	protected final ProcessPreconditionsResolution rejectResolutionOrNull(final HUEditorRow document)
	{
		return ProcessPreconditionsResolution.firstRejectOrElseAccept(
				() -> rejectIfNotPlanningHUStatus(document),
				() -> rejectIfMandatoryAttributesAreNotFilled(document),
				() -> rejectIfSecurPharmAttributesAreNotOK(document));
	}

	private static ProcessPreconditionsResolution rejectIfNotPlanningHUStatus(final HUEditorRow document)
	{
		return document.isHUStatusPlanning()
				? ProcessPreconditionsResolution.accept()
				: ProcessPreconditionsResolution.rejectWithInternalReason("Only planning HUs can be received");
	}

	private ProcessPreconditionsResolution rejectIfMandatoryAttributesAreNotFilled(final HUEditorRow document)
	{
		//
		// Make sure all mandatory attributes are filled
		final HUEditorRowAttributes attributes = document.getAttributes();
		for (final AttributeCode mandatoryAttributeCode : attributes.getMandatoryAttributeNames())
		{
			final Object value = attributes.getValue(mandatoryAttributeCode);
			if (Check.isEmpty(value))
			{
				final I_M_Attribute attribute = attributeDAO.retrieveAttributeByValue(mandatoryAttributeCode);
				final I_M_Attribute translatedAttribute = InterfaceWrapperHelper.translate(attribute, I_M_Attribute.class);
				final ITranslatableString msg = msgBL.getTranslatableMsgText(MSG_MissingMandatoryHUAttribute, translatedAttribute.getName());
				return ProcessPreconditionsResolution.reject(msg);
			}
		}

		return ProcessPreconditionsResolution.accept();
	}

	private ProcessPreconditionsResolution rejectIfSecurPharmAttributesAreNotOK(final HUEditorRow document)
	{
		//
		// OK if this is not a Pharma product
		final HUEditorRowAttributes attributes = document.getAttributes();
		if (!attributes.hasAttribute(AttributeConstants.ATTR_SecurPharmScannedStatus))
		{
			return ProcessPreconditionsResolution.accept();
		}

		//
		// NOK if SecurPharm connection is not configured and we deal with a pharma product
		if (!securPharmService.hasConfig())
		{
			return ProcessPreconditionsResolution.reject("SecurPharm not configured");
		}

		//
		// NOK if not scanned and vendor != manufacturer
		final BPartnerId vendorId = document.getBpartnerId();
		final BPartnerId manufacturerId = productRepository
				.getById(document.getProductId())
				.getManufacturerId();
		if (!BPartnerId.equals(vendorId, manufacturerId))
		{
			final SecurPharmAttributesStatus status = SecurPharmAttributesStatus.ofNullableCodeOrKnown(attributes.getValueAsString(AttributeConstants.ATTR_SecurPharmScannedStatus));
			if (status.isUnknown())
			{
				return ProcessPreconditionsResolution.reject(Services.get(IMsgBL.class).getTranslatableMsgText(MSG_ScanRequired));
			}
		}

		//
		// OK
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx // IHUReceiptScheduleBL.processReceiptSchedules creates its own transaction
	protected String doIt()
	{
		final ImmutableList<ReferencedReceiptSource> referencedSources = getReferencedReceiptSources();
		final List<I_M_ReceiptSchedule> receiptSchedules = referencedSources.stream()
				.map(ReferencedReceiptSource::getReceiptSchedule)
				.collect(GuavaCollectors.toImmutableList());
		final Set<HuId> selectedHuIds = retrieveHUsToReceive();

		assertNoReferencedDeliveryPlanningProcessed(referencedSources);

		// Generate material receipts
		final CreateReceiptsParametersBuilder parametersBuilder = CreateReceiptsParameters.builder()
				.commitEachReceiptIndividually(false)
				.movementDateRule(ReceiptMovementDateRule.CURRENT_DATE)
				.ctx(getCtx())
				.destinationLocatorIdOrNull(null) // use receipt schedules' destination-warehouse settings
				.printReceiptLabels(true)
				.receiptSchedules(receiptSchedules)
				.selectedHuIds(selectedHuIds)
				// The provenance the finished receipt has to carry. It travels with the REQUEST because the call
				// below completes the receipt before returning, and an id written afterwards is invisible to the
				// TIMING_AFTER_COMPLETE interceptor that derives the planning's delivered state.
				.deliveryPlanningIdByHuId(HUEditorReceiptSources.deliveryPlanningIdByHuId(referencedSources, selectedHuIds));

		customizeParametersBuilder(parametersBuilder);

		final CreateReceiptsParameters parameters = parametersBuilder.build();

		huReceiptScheduleBL.processReceiptSchedules(parameters);
		// NOTE: at this point, the user was already notified about generated material receipts

		// Reset the view's affected HUs
		getView().invalidateAll();

		// The launching window's rows too, not only the receipt schedules: on the receipt-disposition window a
		// PLANNED row is keyed on its planning, so a grid listening for M_ReceiptSchedule alone would not refresh.
		viewsRepo.notifyRecordsChangedAsync(TableRecordReferenceSet.of(ImmutableSet.<TableRecordReference>builder()
				.addAll(TableRecordReference.ofSet(receiptSchedules))
				.addAll(HUEditorReceiptSources.deliveryPlanningIds(referencedSources).stream()
						.map(deliveryPlanningId -> TableRecordReference.of(I_M_Delivery_Planning.Table_Name, deliveryPlanningId))
						.collect(ImmutableSet.toImmutableSet()))
				.build()));

		return MSG_OK;
	}

	/**
	 * The planning guard of the launching window, fired a SECOND time here - at confirm, and before anything is
	 * produced.
	 * <p>
	 * The gesture is two steps and the editor stays open for as long as the operator needs: between the row's
	 * receive action and this confirm the planning can have been received - or closed - by another path, and a
	 * planning is exactly ONE receipt. The first firing happened when the editor was opened
	 * ({@code ReceiptDispositionDeliveryPlanningReceiveProcess#doIt}) and cannot cover that window of time.
	 * <p>
	 * Does nothing at all when the launch carries no planning, so the receipt-schedule window's confirm is
	 * unchanged.
	 */
	private void assertNoReferencedDeliveryPlanningProcessed(@NonNull final ImmutableList<ReferencedReceiptSource> referencedSources)
	{
		final ImmutableSet<DeliveryPlanningId> deliveryPlanningIds = HUEditorReceiptSources.deliveryPlanningIds(referencedSources);
		if (deliveryPlanningIds.isEmpty())
		{
			return;
		}

		deliveryPlanningService
				.getReceiveRejectionReason(deliveryPlanningService.getProcessedStatePlannings(deliveryPlanningIds))
				.ifPresent(reason -> {
					throw new AdempiereException(reason);
				});
	}

	protected abstract void customizeParametersBuilder(final CreateReceiptsParametersBuilder parametersBuilder);

	@Override
	protected HUEditorView getView()
	{
		return getView(HUEditorView.class);
	}

	protected List<I_M_ReceiptSchedule> getM_ReceiptSchedules()
	{
		return getReferencedReceiptSources().stream()
				.map(ReferencedReceiptSource::getReceiptSchedule)
				.collect(GuavaCollectors.toImmutableList());
	}

	/**
	 * The rows of the view this editor was launched from, resolved to the records they stand for.
	 */
	private ImmutableList<ReferencedReceiptSource> getReferencedReceiptSources()
	{
		return HUEditorReceiptSources.resolve(
				documentsCollection,
				this,
				getView().getReferencingDocumentPaths());
	}

	protected Set<HuId> retrieveHUsToReceive()
	{
		// https://github.com/metasfresh/metasfresh/issues/1863
		// if the queryFilter is empty, then *do not* return everything to avoid an OOME
		final IQueryFilter<I_M_HU> processInfoFilter = getProcessInfo().getQueryFilterOrElse(ConstantQueryFilter.of(false));

		final IQuery<I_M_HU> query = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU.class, this)
				.filter(processInfoFilter)
				.addOnlyActiveRecordsFilter()
				.create();

		final Set<HuId> huIds = query
				.listIds()
				.stream()
				.map(HuId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
		if (huIds.isEmpty())
		{
			throw new AdempiereException("@NoSelection@ @M_HU_ID@")
					.appendParametersToMessage()
					.setParameter("query", query);
		}
		return huIds;
	}
}

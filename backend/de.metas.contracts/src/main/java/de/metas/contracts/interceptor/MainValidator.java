/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.interceptor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.acct.GLCategoryRepository;
import de.metas.ad_reference.ADReferenceService;
import de.metas.contracts.Contracts_Constants;
import de.metas.contracts.bpartner.interceptor.C_BPartner_Location;
import de.metas.contracts.callorder.CallOrderContractService;
import de.metas.contracts.definitive.notification.DefinitiveInvoiceUserNotificationsProducer;
import de.metas.contracts.flatrate.impexp.FlatrateTermImportProcess;
import de.metas.contracts.flatrate.inout.spi.impl.FlatrateMaterialBalanceConfigMatcher;
import de.metas.contracts.inoutcandidate.ShipmentScheduleCallOrderVetoer;
import de.metas.contracts.inoutcandidate.ShipmentScheduleFromSubscriptionOrderLineVetoer;
import de.metas.contracts.inoutcandidate.ShipmentScheduleSubscriptionProcessor;
import de.metas.contracts.inoutcandidate.SubscriptionShipmentScheduleHandler;
import de.metas.contracts.model.I_I_Flatrate_Term;
import de.metas.contracts.modular.interest.InterestComputationNotificationsProducer;
import de.metas.contracts.order.ContractOrderService;
import de.metas.contracts.printing.impl.FlatrateTermPrintingQueueHandler;
import de.metas.contracts.spi.impl.FlatrateTermInvoiceCandidateListener;
import de.metas.contracts.subscription.invoicecandidatehandler.ExcludeSubscriptionOrderLines;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.event.Topic;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.impex.model.I_AD_InputDataSource;
import de.metas.impexp.processing.IImportProcessFactory;
import de.metas.inout.api.IMaterialBalanceConfigBL;
import de.metas.inout.invoicecandidate.InOutLinesWithMissingInvoiceCandidate;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.api.IShipmentScheduleUpdater;
import de.metas.invoicecandidate.api.IInvoiceCandidateListeners;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.compensationGroup.OrderGroupCompensationChangesHandler;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.compiere.util.Ini;

import java.util.List;
import java.util.Set;

public class MainValidator extends AbstractModuleInterceptor
{

	public static final AdMessageKey MSG_FLATRATE_DOC_ACTION_NOT_SUPPORTED_0P = AdMessageKey.of("Flatrate_DocAction_Not_Supported");

	public static final AdMessageKey MSG_FLATRATE_REACTIVATE_DOC_ACTION_NOT_SUPPORTED_0P = AdMessageKey.of("Flatrate_DocAction_Reactivate_Not_Supported");
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ContractOrderService contractOrderService;
	private final IDocumentLocationBL documentLocationBL;
	private final OrderGroupCompensationChangesHandler groupChangesHandler;
	private final InOutLinesWithMissingInvoiceCandidate inoutLinesWithMissingInvoiceCandidateRepo;
	private final CallOrderContractService callOrderContractService;
	private final ADReferenceService adReferenceService;
	private final GLCategoryRepository glCategoryRepository;

	@Deprecated
	public MainValidator()
	{
		this(
				SpringContextHolder.instance.getBean(ContractOrderService.class),
				SpringContextHolder.instance.getBean(IDocumentLocationBL.class),
				SpringContextHolder.instance.getBean(OrderGroupCompensationChangesHandler.class),
				SpringContextHolder.instance.getBean(InOutLinesWithMissingInvoiceCandidate.class),
				SpringContextHolder.instance.getBean(CallOrderContractService.class),
				ADReferenceService.get(),
				GLCategoryRepository.get());
	}

	public MainValidator(
			@NonNull final ContractOrderService contractOrderService,
			@NonNull final IDocumentLocationBL documentLocationBL,
			@NonNull final OrderGroupCompensationChangesHandler groupChangesHandler,
			@NonNull final InOutLinesWithMissingInvoiceCandidate inoutLinesWithMissingInvoiceCandidateRepo,
			@NonNull final CallOrderContractService callOrderContractService,
			@NonNull final ADReferenceService adReferenceService,
			@NonNull final GLCategoryRepository glCategoryRepository)
	{
		this.contractOrderService = contractOrderService;
		this.documentLocationBL = documentLocationBL;
		this.groupChangesHandler = groupChangesHandler;
		this.inoutLinesWithMissingInvoiceCandidateRepo = inoutLinesWithMissingInvoiceCandidateRepo;
		this.callOrderContractService = callOrderContractService;
		this.adReferenceService = adReferenceService;
		this.glCategoryRepository = glCategoryRepository;
	}

	@Override
	protected Set<String> getTableNamesToSkipOnMigrationScriptsLogging()
	{
		return ImmutableSet.of(I_I_Flatrate_Term.Table_Name);
	}

	@Override
	protected void onAfterInit()
	{
		if (!Ini.isSwingClient())
		{
			ensureDataDestExists();
		}

		setupCallouts();

		registerFactories();
	}

	private void ensureDataDestExists()
	{
		final I_AD_InputDataSource dest = Services.get(IInputDataSourceDAO.class).retrieveInputDataSource(Env.getCtx(), Contracts_Constants.DATA_DESTINATION_INTERNAL_NAME, false, ITrx.TRXNAME_None);
		if (dest == null)
		{
			final I_AD_InputDataSource newDest = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_InputDataSource.class, ITrx.TRXNAME_None);
			newDest.setEntityType(Contracts_Constants.ENTITY_TYPE);
			newDest.setInternalName(Contracts_Constants.DATA_DESTINATION_INTERNAL_NAME);
			newDest.setIsDestination(true);
			newDest.setValue(Contracts_Constants.DATA_DESTINATION_INTERNAL_NAME);
			newDest.setName(Services.get(IMsgBL.class).translate(Env.getCtx(), "C_Flatrate_Term_ID"));
			InterfaceWrapperHelper.save(newDest);
		}
	}

	private void setupCallouts()
	{
		final IProgramaticCalloutProvider calloutProvider = Services.get(IProgramaticCalloutProvider.class);
		calloutProvider.registerAnnotatedCallout(new de.metas.contracts.subscription.callout.C_OrderLine());

		calloutProvider.registerAnnotatedCallout(new de.metas.contracts.flatrate.callout.C_Flatrate_Term());
	}

	public void registerFactories()
	{
		Services.get(IShipmentScheduleHandlerBL.class).registerVetoer(new ShipmentScheduleFromSubscriptionOrderLineVetoer(), I_C_OrderLine.Table_Name);
		Services.get(IShipmentScheduleHandlerBL.class).registerVetoer(new ShipmentScheduleCallOrderVetoer(callOrderContractService), I_C_OrderLine.Table_Name);
		Services.get(IShipmentScheduleHandlerBL.class).registerHandler(new SubscriptionShipmentScheduleHandler());

		Services.get(IShipmentScheduleUpdater.class).registerCandidateProcessor(new ShipmentScheduleSubscriptionProcessor());

		// material balance matcher
		Services.get(IMaterialBalanceConfigBL.class).addMaterialBalanceConfigMather(new FlatrateMaterialBalanceConfigMatcher());

		Services.get(IImportProcessFactory.class).registerImportProcess(I_I_Flatrate_Term.class, FlatrateTermImportProcess.class);

		ExcludeSubscriptionOrderLines.registerFilterForInvoiceCandidateCreation();
		registerInOutLinesWithMissingInvoiceCandidateFilter();

		final IInvoiceCandidateListeners invoiceCandidateListeners = Services.get(IInvoiceCandidateListeners.class);
		invoiceCandidateListeners.addListener(FlatrateTermInvoiceCandidateListener.instance);

		Services.get(IPrintingQueueBL.class).registerHandler(FlatrateTermPrintingQueueHandler.instance);
	}

	/**
	 * Make sure that no {@link I_C_Invoice_Candidate}s are created for inout lines that belong to a subscription contract.
	 * Make sure that no {@link I_C_Invoice_Candidate}s are created for inout lines that belong to ProFormaSO.
	 */
	private void registerInOutLinesWithMissingInvoiceCandidateFilter()
	{
		final IQueryFilter<I_M_InOutLine> filter = queryBL
				.createCompositeQueryFilter(I_M_InOutLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(de.metas.contracts.model.I_M_InOutLine.COLUMNNAME_C_SubscriptionProgress_ID, null);

		inoutLinesWithMissingInvoiceCandidateRepo.addAdditionalFilter(filter);

		final IQuery<I_C_DocType> docSubTypeNotProFormaSO = queryBL.createQueryBuilder(I_C_DocType.class)
				.addNotEqualsFilter(I_C_DocType.COLUMNNAME_DocSubType, X_C_DocType.DOCSUBTYPE_ProFormaSO)
				.create();

		final IQuery<I_C_Order> orderQuery = queryBL.createQueryBuilder(I_C_Order.class)
				.addInSubQueryFilter(I_C_Order.COLUMNNAME_C_DocType_ID, I_C_DocType.COLUMNNAME_C_DocType_ID, docSubTypeNotProFormaSO)
				.create();

		final IQuery<I_M_InOut> inOutQuery = queryBL.createQueryBuilder(I_M_InOut.class)
				.addInSubQueryFilter(I_M_InOut.COLUMNNAME_C_Order_ID, I_C_Order.COLUMNNAME_C_Order_ID, orderQuery)
				.create();

		final IQueryFilter<I_M_InOutLine> filterProFormaSO = queryBL.createCompositeQueryFilter(I_M_InOutLine.class)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(I_M_InOutLine.COLUMNNAME_M_InOut_ID, I_M_InOut.COLUMNNAME_M_InOut_ID, inOutQuery);

		inoutLinesWithMissingInvoiceCandidateRepo.addAdditionalFilter(filterProFormaSO);

	}

	@Override
	protected void registerInterceptors(final IModelValidationEngine engine)
	{
		engine.addModelValidator(C_SubscriptionProgress.instance);
		engine.addModelValidator(C_Flatrate_DataEntry.instance);
		engine.addModelValidator(C_Flatrate_Matching.instance);
		engine.addModelValidator(new C_Flatrate_Term(contractOrderService, documentLocationBL, adReferenceService, glCategoryRepository));

		engine.addModelValidator(new C_Invoice_Candidate());
		engine.addModelValidator(new C_Invoice_Clearing_Alloc());
		engine.addModelValidator(new C_Order());
		engine.addModelValidator(new C_OrderLine(groupChangesHandler));
		engine.addModelValidator(new C_Invoice_Rejection_Detail());
		engine.addModelValidator(new C_BPartner_Location());

		// 03742
		engine.addModelValidator(new C_Flatrate_Transition());

		// 04360
		engine.addModelValidator(new C_Period());

		engine.addModelValidator(new M_InOutLine());

		engine.addModelValidator(new M_ShipmentSchedule_QtyPicked());

		// 09869
		engine.addModelValidator(M_ShipmentSchedule.INSTANCE);
	}

	protected List<Topic> getAvailableUserNotificationsTopics()
	{
		return ImmutableList.of(InterestComputationNotificationsProducer.EVENTBUS_TOPIC,
				DefinitiveInvoiceUserNotificationsProducer.EVENTBUS_TOPIC);
	}

}

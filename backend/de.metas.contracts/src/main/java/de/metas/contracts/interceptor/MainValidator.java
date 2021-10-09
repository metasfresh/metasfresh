package de.metas.contracts.interceptor;

/*
 * #%L
 * de.metas.contracts
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

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.Env;
import org.compiere.util.Ini;

import de.metas.contracts.Contracts_Constants;
import de.metas.contracts.flatrate.impexp.FlatrateTermImportProcess;
import de.metas.contracts.flatrate.inout.spi.impl.FlatrateMaterialBalanceConfigMatcher;
import de.metas.contracts.inoutcandidate.ShipmentScheduleFromSubscriptionOrderLineVetoer;
import de.metas.contracts.inoutcandidate.ShipmentScheduleSubscriptionProcessor;
import de.metas.contracts.inoutcandidate.SubscriptionShipmentScheduleHandler;
import de.metas.contracts.model.I_I_Flatrate_Term;
import de.metas.contracts.order.ContractOrderService;
import de.metas.contracts.spi.impl.FlatrateTermInvoiceCandidateListener;
import de.metas.contracts.subscription.invoicecandidatehandler.ExcludeSubscriptionOrderLines;
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
import de.metas.util.Services;
import lombok.NonNull;

public class MainValidator extends AbstractModuleInterceptor
{

	public static final AdMessageKey MSG_FLATRATE_DOC_ACTION_NOT_SUPPORTED_0P = AdMessageKey.of("Flatrate_DocAction_Not_Supported");

	public static final AdMessageKey MSG_FLATRATE_REACTIVATE_DOC_ACTION_NOT_SUPPORTED_0P = AdMessageKey.of("Flatrate_DocAction_Reactivate_Not_Supported");

	private final ContractOrderService contractOrderService;
	private final OrderGroupCompensationChangesHandler groupChangesHandler;
	private final InOutLinesWithMissingInvoiceCandidate inoutLinesWithMissingInvoiceCandidateRepo;

	@Deprecated
	public MainValidator()
	{
		this(
				SpringContextHolder.instance.getBean(ContractOrderService.class),
				SpringContextHolder.instance.getBean(OrderGroupCompensationChangesHandler.class),
				SpringContextHolder.instance.getBean(InOutLinesWithMissingInvoiceCandidate.class));
	}

	public MainValidator(
			@NonNull final ContractOrderService contractOrderService,
			@NonNull final OrderGroupCompensationChangesHandler groupChangesHandler,
			@NonNull final InOutLinesWithMissingInvoiceCandidate inoutLinesWithMissingInvoiceCandidateRepo)
	{
		this.contractOrderService = contractOrderService;
		this.groupChangesHandler = groupChangesHandler;
		this.inoutLinesWithMissingInvoiceCandidateRepo = inoutLinesWithMissingInvoiceCandidateRepo;
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
		Services.get(IShipmentScheduleHandlerBL.class).registerHandler(new SubscriptionShipmentScheduleHandler());

		Services.get(IShipmentScheduleUpdater.class).registerCandidateProcessor(new ShipmentScheduleSubscriptionProcessor());

		// material balance matcher
		Services.get(IMaterialBalanceConfigBL.class).addMaterialBalanceConfigMather(new FlatrateMaterialBalanceConfigMatcher());

		Services.get(IImportProcessFactory.class).registerImportProcess(I_I_Flatrate_Term.class, FlatrateTermImportProcess.class);

		ExcludeSubscriptionOrderLines.registerFilterForInvoiceCandidateCreation();
		registerInOutLinesWithMissingInvoiceCandidateFilter();

		final IInvoiceCandidateListeners invoiceCandidateListeners = Services.get(IInvoiceCandidateListeners.class);
		invoiceCandidateListeners.addListener(FlatrateTermInvoiceCandidateListener.instance);
	}

	/**
	 * Make sure that no {@link I_C_Invoice_Candidate}s are created for inout lines that belong to a subscription contract.
	 */
	private void registerInOutLinesWithMissingInvoiceCandidateFilter()
	{
		final IQueryFilter<I_M_InOutLine> filter = Services.get(IQueryBL.class)
				.createCompositeQueryFilter(I_M_InOutLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(de.metas.contracts.model.I_M_InOutLine.COLUMNNAME_C_SubscriptionProgress_ID, null);

		inoutLinesWithMissingInvoiceCandidateRepo.addAdditionalFilter(filter);
	}

	@Override
	protected void registerInterceptors(final IModelValidationEngine engine)
	{
		engine.addModelValidator(C_Flatrate_Conditions.INSTANCE);
		engine.addModelValidator(C_SubscriptionProgress.instance);
		engine.addModelValidator(C_Flatrate_DataEntry.instance);
		engine.addModelValidator(C_Flatrate_Matching.instance);
		engine.addModelValidator(new C_Flatrate_Term(contractOrderService));

		engine.addModelValidator(new C_Invoice_Candidate());
		engine.addModelValidator(new C_Invoice_Clearing_Alloc());
		engine.addModelValidator(new C_Order());
		engine.addModelValidator(new C_OrderLine(groupChangesHandler));
		engine.addModelValidator(new C_Invoice_Rejection_Detail());

		// 03742
		engine.addModelValidator(new C_Flatrate_Transition());

		// 04360
		engine.addModelValidator(new C_Period());

		engine.addModelValidator(new M_InOutLine());

		engine.addModelValidator(new M_ShipmentSchedule_QtyPicked());

		// 09869
		engine.addModelValidator(M_ShipmentSchedule.INSTANCE);
	}

}

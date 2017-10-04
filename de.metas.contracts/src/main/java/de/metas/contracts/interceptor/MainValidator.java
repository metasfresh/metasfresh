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
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.impexp.IImportProcessFactory;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.compiere.util.Ini;

import de.metas.contracts.Contracts_Constants;
import de.metas.contracts.flatrate.impexp.FlatrateTermImportProcess;
import de.metas.contracts.flatrate.inout.spi.impl.FlatrateMaterialBalanceConfigMatcher;
import de.metas.contracts.flatrate.ordercandidate.spi.FlatrateGroupingProvider;
import de.metas.contracts.flatrate.ordercandidate.spi.FlatrateOLCandListener;
import de.metas.contracts.inoutcandidate.ShipmentScheduleFromSubscriptionOrderLineVetoer;
import de.metas.contracts.inoutcandidate.ShipmentScheduleSubscriptionProcessor;
import de.metas.contracts.inoutcandidate.SubscriptionShipmentScheduleHandler;
import de.metas.contracts.invoicecandidate.ExcludeSubscriptionInOutLines;
import de.metas.contracts.invoicecandidate.ExcludeSubscriptionOrderLines;
import de.metas.contracts.model.I_I_Flatrate_Term;
import de.metas.i18n.IMsgBL;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.impex.model.I_AD_InputDataSource;
import de.metas.inout.api.IMaterialBalanceConfigBL;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.ordercandidate.api.IOLCandBL;

public class MainValidator implements ModelValidator
{

	public static final String MSG_FLATRATE_DOC_ACTION_NOT_SUPPORTED_0P = "Flatrate_DocAction_Not_Supported";

	private int m_AD_Client_ID = -1;

	@Override
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}

	@Override
	public void initialize(final ModelValidationEngine engine, final MClient client)
	{
		if (client != null)
		{
			m_AD_Client_ID = client.getAD_Client_ID();
		}

		Services.get(IShipmentScheduleHandlerBL.class).registerVetoer(new ShipmentScheduleFromSubscriptionOrderLineVetoer(), I_C_OrderLine.Table_Name);
		Services.get(IShipmentScheduleHandlerBL.class).registerHandler(Env.getCtx(), new SubscriptionShipmentScheduleHandler());

		Services.get(IShipmentScheduleBL.class).registerCandidateProcessor(new ShipmentScheduleSubscriptionProcessor());

		Services.get(IOLCandBL.class).registerCustomerGroupingValuesProvider(new FlatrateGroupingProvider());
		Services.get(IOLCandBL.class).registerOLCandListener(new FlatrateOLCandListener());

		engine.addModelValidator(new C_Flatrate_Conditions(), client);
		engine.addModelValidator(C_SubscriptionProgress.instance, client);
		engine.addModelValidator(new C_Flatrate_DataEntry(), client);
		engine.addModelValidator(new C_Flatrate_Matching(), client);
		engine.addModelValidator(new C_Flatrate_Term(), client);

		engine.addModelValidator(new C_Invoice_Candidate(), client);
		engine.addModelValidator(new C_Invoice_Clearing_Alloc(), client);
		engine.addModelValidator(new C_Order(), client);
		engine.addModelValidator(new C_OrderLine(), client);

		// 03742
		engine.addModelValidator(new C_Flatrate_Transition(), client);

		// 04360
		engine.addModelValidator(new C_Period(), client);

		engine.addModelValidator(new M_InOutLine(), client);

		engine.addModelValidator(new M_ShipmentSchedule_QtyPicked(), client);
		
		// 09869
		engine.addModelValidator(new de.metas.contracts.interceptor.M_ShipmentSchedule(), client);

		// material balance matcher
		Services.get(IMaterialBalanceConfigBL.class).addMaterialBalanceConfigMather(new FlatrateMaterialBalanceConfigMatcher());

		if (!Ini.isClient())
		{
			ensureDataDestExists();
		}

		setupCallouts();

		Services.get(IImportProcessFactory.class).registerImportProcess(I_I_Flatrate_Term.class, FlatrateTermImportProcess.class);

		ExcludeSubscriptionOrderLines.registerFilterForInvoiceCandidateCreation();
		ExcludeSubscriptionInOutLines.registerFilterForInvoiceCandidateCreation();
	}

	private void setupCallouts()
	{
		final IProgramaticCalloutProvider calloutProvider = Services.get(IProgramaticCalloutProvider.class);
		calloutProvider.registerAnnotatedCallout(new de.metas.contracts.subscription.callout.C_OrderLine());

		calloutProvider.registerAnnotatedCallout(new de.metas.contracts.flatrate.callout.C_Flatrate_Term());
	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		return null; // nothing to do
	}

	@Override
	public String modelChange(final PO po, final int type) throws Exception
	{
		return null; // nothing to do
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{
		return null; // nothing to do
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
			newDest.setName(Services.get(IMsgBL.class).translate(Env.getCtx(), "@C_Flatrate_Term_ID@"));
			InterfaceWrapperHelper.save(newDest);
		}
	}
}

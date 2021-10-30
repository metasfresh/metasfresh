package de.metas.ordercandidate.modelvalidator;

import de.metas.i18n.IMsgBL;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.impex.model.I_AD_InputDataSource;
import de.metas.ordercandidate.OrderCandidate_Constants;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.spi.impl.OLCandASIAwareFactory;
import de.metas.ordercandidate.spi.impl.OLCandPricingASIListener;
import de.metas.util.Services;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.mm.attributes.api.IModelAttributeSetInstanceListenerService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;
import org.compiere.util.Ini;

/**
 * Main model interceptor of <code>de.metas.ordercandidate</code> module.
 */
public class OrderCandidate extends AbstractModuleInterceptor
{
	@Override
	protected void onAfterInit()
	{
		Services.get(IAttributeSetInstanceAwareFactoryService.class).registerFactoryForTableName(I_C_OLCand.Table_Name, new OLCandASIAwareFactory()); // task 08803

		// task 08803: registering this listener *after* C_OLCand, because C_OLCand can call IOLCandValdiatorBL.validate, and this listener (which is actually a model interceptor) needs to be called
		// after that (if there is any change).
		Services.get(IModelAttributeSetInstanceListenerService.class).registerListener(new OLCandPricingASIListener());

		if (!Ini.isSwingClient())
		{
			ensureDataDestExists();
		}
	}

	private void ensureDataDestExists()
	{
		final I_AD_InputDataSource dest = Services.get(IInputDataSourceDAO.class).retrieveInputDataSource(Env.getCtx(), OrderCandidate_Constants.DATA_DESTINATION_INTERNAL_NAME, false,
																										  ITrx.TRXNAME_None);
		if (dest == null)
		{
			final IMsgBL msgBL = Services.get(IMsgBL.class);

			final I_AD_InputDataSource newDest = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_InputDataSource.class, ITrx.TRXNAME_None);
			newDest.setEntityType(OrderCandidate_Constants.ENTITY_TYPE);
			newDest.setInternalName(OrderCandidate_Constants.DATA_DESTINATION_INTERNAL_NAME);
			newDest.setIsDestination(true);
			newDest.setValue(OrderCandidate_Constants.DATA_DESTINATION_INTERNAL_NAME);
			newDest.setName(msgBL.translate(Env.getCtx(), "C_Order_ID"));
			InterfaceWrapperHelper.save(newDest);
		}
	}
}

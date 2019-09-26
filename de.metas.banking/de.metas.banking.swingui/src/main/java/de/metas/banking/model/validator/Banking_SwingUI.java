package de.metas.banking.model.validator;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;

import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.compiere.Adempiere.RunMode;
import org.compiere.grid.VCreateFromFactory;
import org.compiere.grid.VCreateFromStatementUI;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_C_BankStatement;

/**
 * Banking SwingUI module activator.
 *
 * This activator will be loaded only if running with {@link RunMode#SWING_CLIENT} run mode.
 *
 * NOTE: keep the name in sync with {@link Banking} and keep the suffix.
 *
 * @author tsa
 *
 */
public class Banking_SwingUI extends AbstractModuleInterceptor
{
	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		super.onInit(engine, client);

		VCreateFromFactory.registerClass(getTableId(I_C_BankStatement.class), VCreateFromStatementUI.class);
	}
}

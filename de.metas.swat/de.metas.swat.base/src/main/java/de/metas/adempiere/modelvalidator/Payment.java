package de.metas.adempiere.modelvalidator;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MClient;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MPayment;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Obscure;
import org.compiere.model.PO;
import org.compiere.model.X_AD_Field;

import de.metas.adempiere.model.I_AD_OrgInfo;

/**
 * 
 * @author ts [metas 00036] Modelvalidator makes sure that credit card number and verification code are not stored in
 *         the DB, depending on {@link I_AD_OrgInfo#COLUMNAME_StoreCreditCardData}.
 */
public class Payment implements ModelValidator
{
	private int ad_Client_ID = -1;

	@Override
	public int getAD_Client_ID()
	{
		return ad_Client_ID;
	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		// nothing to do
		return null;
	}

	@Override
	public final void initialize(final ModelValidationEngine engine, final MClient client)
	{

		if (client != null)
		{
			ad_Client_ID = client.getAD_Client_ID();
		}

		engine.addModelChange(I_C_Payment.Table_Name, this);
	}

	@Override
	public String modelChange(final PO po, final int type) throws Exception
	{
		if (type == TYPE_BEFORE_NEW || type == TYPE_BEFORE_CHANGE)
		{
			final MPayment payment = (MPayment)po;

			if (po.is_ValueChanged(I_C_Payment.COLUMNNAME_CreditCardNumber) || po.is_ValueChanged(I_C_Payment.COLUMNNAME_CreditCardVV))
			{
				final I_AD_OrgInfo orgInfo = InterfaceWrapperHelper.create(MOrgInfo.get(po.getCtx(), po.getAD_Org_ID(), null), I_AD_OrgInfo.class);

				final String ccStoreMode = orgInfo.getStoreCreditCardData();

				if (I_AD_OrgInfo.StoreCreditCardData_DONT_STORE.equals(ccStoreMode))
				{
					payment.set_ValueOfColumn(I_C_Payment.COLUMNNAME_CreditCardNumber, payment.getCreditCardNumber().replaceAll(" ", "").replaceAll(".", "*"));
					payment.set_ValueOfColumn(I_C_Payment.COLUMNNAME_CreditCardVV, payment.getCreditCardVV().replaceAll(" ", "").replaceAll(".", "*"));
				}
				else if (I_AD_OrgInfo.StoreCreditCardData_STORE_LAST_4.equals(ccStoreMode))
				{
					final String creditCardNumer = payment.getCreditCardNumber();
					final Obscure obscure = new Obscure(creditCardNumer, X_AD_Field.OBSCURETYPE_ObscureAlphaNumericButLast4);
					payment.set_ValueOfColumn(I_C_Payment.COLUMNNAME_CreditCardNumber, obscure.getObscuredValue());

					payment.set_ValueOfColumn(I_C_Payment.COLUMNNAME_CreditCardVV, "***");
				}
				else
				{
					// nothing to do
				}
			}
		}
		return null;
	}
	
	@Override
	public String docValidate(final PO po, final int timing)
	{
		return null;
	}
}

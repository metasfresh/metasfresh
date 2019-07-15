package de.metas.adempiere.modelvalidator;

import org.compiere.model.I_C_Payment;
import org.compiere.model.MClient;
import org.compiere.model.MPayment;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.Obscure;
import org.compiere.model.PO;
import org.compiere.model.X_AD_Field;

import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfo;
import de.metas.organization.StoreCreditCardNumberMode;
import de.metas.util.Services;

/**
 *
 * @author ts [metas 00036] Modelvalidator makes sure that credit card number and verification code are not stored in
 *         the DB, depending on {@link OrgInfo#getStoreCreditCardNumberMode()}. 
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
				final OrgId orgId = OrgId.ofRepoId(payment.getAD_Org_ID());
				final OrgInfo orgInfo = Services.get(IOrgDAO.class).getOrgInfoById(orgId);
				final StoreCreditCardNumberMode ccStoreMode = orgInfo.getStoreCreditCardNumberMode();

				if (StoreCreditCardNumberMode.DONT_STORE.equals(ccStoreMode))
				{
					payment.set_ValueOfColumn(I_C_Payment.COLUMNNAME_CreditCardNumber, payment.getCreditCardNumber().replaceAll(" ", "").replaceAll(".", "*"));
					payment.set_ValueOfColumn(I_C_Payment.COLUMNNAME_CreditCardVV, payment.getCreditCardVV().replaceAll(" ", "").replaceAll(".", "*"));
				}
				else if (StoreCreditCardNumberMode.LAST_4_DIGITS.equals(ccStoreMode))
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

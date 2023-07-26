package de.metas.acct.model.validator;

import static org.compiere.model.I_C_AcctSchema_Element.COLUMNNAME_C_Activity_ID;
import static org.compiere.model.I_C_AcctSchema_Element.COLUMNNAME_C_BPartner_ID;
import static org.compiere.model.I_C_AcctSchema_Element.COLUMNNAME_C_Campaign_ID;
import static org.compiere.model.I_C_AcctSchema_Element.COLUMNNAME_C_ElementValue_ID;
import static org.compiere.model.I_C_AcctSchema_Element.COLUMNNAME_C_Location_ID;
import static org.compiere.model.I_C_AcctSchema_Element.COLUMNNAME_C_Project_ID;
import static org.compiere.model.I_C_AcctSchema_Element.COLUMNNAME_C_SalesRegion_ID;
import static org.compiere.model.I_C_AcctSchema_Element.COLUMNNAME_IsMandatory;
import static org.compiere.model.I_C_AcctSchema_Element.COLUMNNAME_M_Product_ID;
import static org.compiere.model.I_C_AcctSchema_Element.COLUMNNAME_Org_ID;
import static org.compiere.model.I_C_AcctSchema_Element.COLUMNNAME_SeqNo;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_AcctSchema_Element;
import org.compiere.model.MAccount;
import org.compiere.model.ModelValidator;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.acct.api.AcctSchemaElementType;
import de.metas.organization.OrgId;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Interceptor(I_C_AcctSchema_Element.class)
public class C_AcctSchema_Element
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_C_AcctSchema_Element element)
	{
		element.setAD_Org_ID(OrgId.ANY.getRepoId());

		final AcctSchemaElementType elementType = AcctSchemaElementType.ofCode(element.getElementType());
		if (element.isMandatory() && elementType.isUserDefinedElements())
		{
			element.setIsMandatory(false);
		}

		if (!elementType.isDeletable())
		{
			element.setIsMandatory(true);
			element.setIsActive(true);
		}
		//
		else if (element.isMandatory())
		{
			String errorField = null;
			if (AcctSchemaElementType.Account.equals(elementType) && element.getC_ElementValue_ID() == 0)
			{
				errorField = COLUMNNAME_C_ElementValue_ID;
			}
			else if (AcctSchemaElementType.Activity.equals(elementType) && element.getC_Activity_ID() == 0)
			{
				errorField = COLUMNNAME_C_Activity_ID;
			}
			else if (AcctSchemaElementType.BPartner.equals(elementType) && element.getC_BPartner_ID() == 0)
			{
				errorField = COLUMNNAME_C_BPartner_ID;
			}
			else if (AcctSchemaElementType.Campaign.equals(elementType) && element.getC_Campaign_ID() == 0)
			{
				errorField = COLUMNNAME_C_Campaign_ID;
			}
			else if (AcctSchemaElementType.LocationFrom.equals(elementType) && element.getC_Location_ID() == 0)
			{
				errorField = COLUMNNAME_C_Location_ID;
			}
			else if (AcctSchemaElementType.LocationTo.equals(elementType) && element.getC_Location_ID() == 0)
			{
				errorField = COLUMNNAME_C_Location_ID;
			}
			else if (AcctSchemaElementType.Organization.equals(elementType) && element.getOrg_ID() == 0)
			{
				errorField = COLUMNNAME_Org_ID;
			}
			else if (AcctSchemaElementType.OrgTrx.equals(elementType) && element.getOrg_ID() == 0)
			{
				errorField = COLUMNNAME_Org_ID;
			}
			else if (AcctSchemaElementType.Product.equals(elementType) && element.getM_Product_ID() == 0)
			{
				errorField = COLUMNNAME_M_Product_ID;
			}
			else if (AcctSchemaElementType.Project.equals(elementType) && element.getC_Project_ID() == 0)
			{
				errorField = COLUMNNAME_C_Project_ID;
			}
			else if (AcctSchemaElementType.SalesRegion.equals(elementType) && element.getC_SalesRegion_ID() == 0)
			{
				errorField = COLUMNNAME_C_SalesRegion_ID;
			}
			if (errorField != null)
			{
				throw new AdempiereException("@IsMandatory@: @" + errorField + "@");
			}
		}

		//
		if (element.getAD_Column_ID() <= 0
				&& (AcctSchemaElementType.UserElement1.equals(elementType) || AcctSchemaElementType.UserElement2.equals(elementType)))
		{
			throw new AdempiereException("@IsMandatory@: @AD_Column_ID@");
		}
	}	// beforeSave

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void afterSave(final I_C_AcctSchema_Element element)
	{
		// Default Value
		if (element.isMandatory() && InterfaceWrapperHelper.isValueChanged(element, COLUMNNAME_IsMandatory))
		{
			final AcctSchemaElementType elementType = AcctSchemaElementType.ofCode(element.getElementType());
			if (AcctSchemaElementType.Activity.equals(elementType))
			{
				updateData(COLUMNNAME_C_Activity_ID, element.getC_Activity_ID(), element);
			}
			else if (AcctSchemaElementType.BPartner.equals(elementType))
			{
				updateData(COLUMNNAME_C_BPartner_ID, element.getC_BPartner_ID(), element);
			}
			else if (AcctSchemaElementType.Product.equals(elementType))
			{
				updateData(COLUMNNAME_M_Product_ID, element.getM_Product_ID(), element);
			}
			else if (AcctSchemaElementType.Project.equals(elementType))
			{
				updateData(COLUMNNAME_C_Project_ID, element.getC_Project_ID(), element);
			}
		}

		// Re-sequence
		if (InterfaceWrapperHelper.isNew(element) || InterfaceWrapperHelper.isValueChanged(element, COLUMNNAME_SeqNo))
		{
			MAccount.updateValueDescription(Env.getCtx(), "AD_Client_ID=" + element.getAD_Client_ID(), ITrx.TRXNAME_ThreadInherited);
		}
	}	// afterSave

	/**
	 * Update ValidCombination and Fact with mandatory value
	 *
	 * @param element element
	 * @param id new default
	 */
	private void updateData(final String element, final int id, final I_C_AcctSchema_Element elementRecord)
	{
		MAccount.updateValueDescription(Env.getCtx(), element + "=" + id, ITrx.TRXNAME_ThreadInherited);

		//
		{
			final int clientId = elementRecord.getAD_Client_ID();
			final String sql = "UPDATE C_ValidCombination SET " + element + "=? WHERE " + element + " IS NULL AND AD_Client_ID=?";
			DB.executeUpdateAndThrowExceptionOnFail(sql, new Object[] { id, clientId }, ITrx.TRXNAME_ThreadInherited);
		}
		//
		{
			final int acctSchemaId = elementRecord.getC_AcctSchema_ID();
			final String sql = "UPDATE Fact_Acct SET " + element + "=? WHERE " + element + " IS NULL AND C_AcctSchema_ID=?";
			DB.executeUpdateAndThrowExceptionOnFail(sql, new Object[] { id, acctSchemaId }, ITrx.TRXNAME_ThreadInherited);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void beforeDelete(final I_C_AcctSchema_Element element)
	{
		final AcctSchemaElementType elementType = AcctSchemaElementType.ofCode(element.getElementType());
		if (!elementType.isDeletable())
		{
			throw new AdempiereException("@DeleteError@ @IsMandatory@");
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_DELETE })
	public void afterDelete(final I_C_AcctSchema_Element element)
	{
		MAccount.updateValueDescription(Env.getCtx(), "AD_Client_ID=" + element.getAD_Client_ID(), ITrx.TRXNAME_ThreadInherited);
	}
}

package de.metas.acct.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.acct.api.AccountDimension;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaElement;
import de.metas.acct.api.AcctSchemaElementType;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAccountBL;
import de.metas.acct.api.IAccountDAO;
import de.metas.acct.api.IAccountDimensionValidator;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Campaign;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_SalesRegion;
import org.compiere.model.I_C_SubAcct;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_SectionCode;
import org.compiere.model.MAccount;
import org.compiere.model.Query;
import org.slf4j.Logger;

import java.util.List;
import java.util.Properties;

public class AccountBL implements IAccountBL
{
	private static final Logger log = LogManager.getLogger(AccountBL.class);
	private final IAccountDAO accountDAO = Services.get(IAccountDAO.class);
	private final IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private static final String SEGMENT_COMBINATION_NA = "_";
	private static final String SEGMENT_DESCRIPTION_NA = "_";

	@Override
	public IAccountDimensionValidator createAccountDimensionValidator(final AcctSchema acctSchema)
	{
		return new AccountDimensionValidator(acctSchema);
	}

	@Override
	public void updateValueDescription(final Properties ctx, final String whereClause, final String trxName)
	{
		final List<I_C_ValidCombination> accounts = new Query(ctx, I_C_ValidCombination.Table_Name, whereClause, trxName)
				.setOrderBy(MAccount.COLUMNNAME_C_ValidCombination_ID)
				.list(I_C_ValidCombination.class);

		for (final I_C_ValidCombination account : accounts)
		{
			setValueDescription(account);
			InterfaceWrapperHelper.save(account);
		}
	}    // updateValueDescription

	@Override
	public void setValueDescription(final I_C_ValidCombination account)
	{
		final StringBuilder combination = new StringBuilder();
		final StringBuilder description = new StringBuilder();
		boolean fullyQualified = true;

		final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(account.getC_AcctSchema_ID());
		final AcctSchema acctSchema = acctSchemaDAO.getById(acctSchemaId);
		final String separator = acctSchema.getValidCombinationOptions().getSeparator();

		//
		for (final AcctSchemaElement element : acctSchema.getSchemaElements())
		{
			// Skip those elements which are not displayed in editor (07546)
			if (!element.isDisplayedInEditor())
			{
				continue;
			}

			String segmentCombination = SEGMENT_COMBINATION_NA;        // not defined
			String segmentDescription = SEGMENT_DESCRIPTION_NA;

			final AcctSchemaElementType elementType = element.getElementType();
			Check.assumeNotNull(elementType, "elementType not null"); // shall not happen

			if (AcctSchemaElementType.Organization.equals(elementType))
			{
				final OrgId orgId = OrgId.ofRepoIdOrAny(account.getAD_Org_ID());
				if (orgId.isRegular())
				{
					final I_AD_Org org = orgDAO.getById(orgId);
					segmentCombination = org.getValue();
					segmentDescription = org.getName();
				}
				else
				{
					segmentCombination = "*";
					segmentDescription = "*";
					fullyQualified = false;
				}
			}
			else if (AcctSchemaElementType.Account.equals(elementType))
			{
				if (account.getAccount_ID() > 0)
				{
					final I_C_ElementValue elementValue = account.getAccount();
					segmentCombination = elementValue.getValue();
					segmentDescription = elementValue.getName();
				}
				else if (element.isMandatory())
				{
					log.warn("Mandatory Element missing: Account");
					fullyQualified = false;
				}
			}
			else if (AcctSchemaElementType.SubAccount.equals(elementType))
			{
				if (account.getC_SubAcct_ID() > 0)
				{
					final I_C_SubAcct sa = account.getC_SubAcct();
					segmentCombination = sa.getValue();
					segmentDescription = sa.getName();
				}
			}
			else if (AcctSchemaElementType.Product.equals(elementType))
			{
				if (account.getM_Product_ID() > 0)
				{
					final I_M_Product product = account.getM_Product();
					segmentCombination = product.getValue();
					segmentDescription = product.getName();
				}
				else if (element.isMandatory())
				{
					log.warn("Mandatory Element missing: Product");
					fullyQualified = false;
				}
			}
			else if (AcctSchemaElementType.BPartner.equals(elementType))
			{
				if (account.getC_BPartner_ID() > 0)
				{
					I_C_BPartner partner = account.getC_BPartner();
					segmentCombination = partner.getValue();
					segmentDescription = partner.getName();
				}
				else if (element.isMandatory())
				{
					log.warn("Mandatory Element missing: Business Partner");
					fullyQualified = false;
				}
			}
			else if (AcctSchemaElementType.OrgTrx.equals(elementType))
			{
				if (account.getAD_OrgTrx_ID() > 0)
				{
					I_AD_Org org = account.getAD_OrgTrx();
					segmentCombination = org.getValue();
					segmentDescription = org.getName();
				}
				else if (element.isMandatory())
				{
					log.warn("Mandatory Element missing: Trx Org");
					fullyQualified = false;
				}
			}
			else if (AcctSchemaElementType.LocationFrom.equals(elementType))
			{
				if (account.getC_LocFrom_ID() > 0)
				{
					final I_C_Location loc = account.getC_LocFrom();
					segmentCombination = loc.getPostal();
					segmentDescription = loc.getCity();
				}
				else if (element.isMandatory())
				{
					log.warn("Mandatory Element missing: Location From");
					fullyQualified = false;
				}
			}
			else if (AcctSchemaElementType.LocationTo.equals(elementType))
			{
				if (account.getC_LocTo_ID() > 0)
				{
					final I_C_Location loc = account.getC_LocTo();
					segmentCombination = loc.getPostal();
					segmentDescription = loc.getCity();
				}
				else if (element.isMandatory())
				{
					log.warn("Mandatory Element missing: Location To");
					fullyQualified = false;
				}
			}
			else if (AcctSchemaElementType.SalesRegion.equals(elementType))
			{
				if (account.getC_SalesRegion_ID() > 0)
				{
					final I_C_SalesRegion loc = account.getC_SalesRegion();
					segmentCombination = loc.getValue();
					segmentDescription = loc.getName();
				}
				else if (element.isMandatory())
				{
					log.warn("Mandatory Element missing: SalesRegion");
					fullyQualified = false;
				}
			}
			else if (AcctSchemaElementType.Project.equals(elementType))
			{
				if (account.getC_Project_ID() > 0)
				{
					// final I_C_Project project = account.getC_Project();
					// segmentCombination = project.getValue();
					// segmentDescription = project.getName();
				}
				else if (element.isMandatory())
				{
					log.warn("Mandatory Element missing: Project");
					fullyQualified = false;
				}
			}
			else if (AcctSchemaElementType.Campaign.equals(elementType))
			{
				if (account.getC_Campaign_ID() > 0)
				{
					final I_C_Campaign campaign = account.getC_Campaign();
					segmentCombination = campaign.getValue();
					segmentDescription = campaign.getName();
				}
				else if (element.isMandatory())
				{
					log.warn("Mandatory Element missing: Campaign");
					fullyQualified = false;
				}
			}
			else if (AcctSchemaElementType.Activity.equals(elementType))
			{
				if (account.getC_Activity_ID() > 0)
				{
					final I_C_Activity act = account.getC_Activity();
					segmentCombination = act.getValue();
					segmentDescription = act.getName();
				}
				else if (element.isMandatory())
				{
					log.warn("Mandatory Element missing: Campaign");
					fullyQualified = false;
				}
			}
			else if (AcctSchemaElementType.SalesOrder.equals(elementType))
			{
				if (account.getC_OrderSO_ID() > 0)
				{
					final I_C_Order order = account.getC_OrderSO();
					segmentCombination = order.getDocumentNo();
				}
				else if (element.isMandatory())
				{
					log.warn("Mandatory Element missing: C_Order_ID");
					fullyQualified = false;
				}
			}
			else if (AcctSchemaElementType.SectionCode.equals(elementType))
			{
				if (account.getM_SectionCode_ID() > 0)
				{
					final I_M_SectionCode sectionCode = account.getM_SectionCode();
					segmentCombination = sectionCode.getValue();
					segmentDescription = sectionCode.getName();
				}
				else if (element.isMandatory())
				{
					log.warn("Mandatory Element missing: C_Order_ID");
					fullyQualified = false;
				}
			}
			else if (AcctSchemaElementType.UserList1.equals(elementType))
			{
				if (account.getUser1_ID() > 0)
				{
					final I_C_ElementValue ev = account.getUser1();
					segmentCombination = ev.getValue();
					segmentDescription = ev.getName();
				}
			}
			else if (AcctSchemaElementType.UserList2.equals(elementType))
			{
				if (account.getUser2_ID() > 0)
				{
					final I_C_ElementValue ev = account.getUser2();
					segmentCombination = ev.getValue();
					segmentDescription = ev.getName();
				}
			}
			// TODO: implement
			// else if (AcctSchemaElementType.UserElement1.equals(elementType))
			// {
			// 	// if (acct.getUserElement1_ID() > 0)
			// 	// {
			// 	// }
			// }
			// else if (AcctSchemaElementType.UserElement2.equals(elementType))
			// {
			// 	// if (acct.getUserElement2_ID() > 0)
			// 	// {
			// 	// }
			// }

			//
			// Append segment combination and description
			if (combination.length() > 0)
			{
				combination.append(separator);
				description.append(separator);
			}
			combination.append(segmentCombination);
			description.append(segmentDescription);
		}

		//
		// Set Values
		account.setCombination(combination.toString());
		account.setDescription(description.toString());
		if (fullyQualified != account.isFullyQualified())
		{
			account.setIsFullyQualified(fullyQualified);
		}
	}    // setValueDescription

	@Override
	public void validate(@NonNull final I_C_ValidCombination account)
	{
		// Validate Sub-Account
		if (account.getC_SubAcct_ID() > 0)
		{
			I_C_SubAcct sa = account.getC_SubAcct();
			if (sa != null && sa.getC_ElementValue_ID() != account.getAccount_ID())
			{
				throw new AdempiereException("C_SubAcct.C_ElementValue_ID=" + sa.getC_ElementValue_ID() + "<>Account_ID=" + account.getAccount_ID());
			}
		}
	}

	@Override
	public AccountId getOrCreate(@NonNull final AccountDimension dimension)
	{
		return accountDAO.getOrCreate(dimension);
	}

}

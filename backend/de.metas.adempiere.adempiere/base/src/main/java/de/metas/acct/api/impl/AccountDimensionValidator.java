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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.acct.api.AccountDimension;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaElement;
import de.metas.acct.api.AcctSchemaElementType;
import de.metas.acct.api.AcctSchemaElementsMap;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.AcctSchemaValidCombinationOptions;
import de.metas.acct.api.IAccountDimensionValidator;
import de.metas.i18n.IMsgBL;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_ValidCombination;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/* package */class AccountDimensionValidator implements IAccountDimensionValidator
{
	// Services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	private final Properties _ctx;
	private final AcctSchemaId acctSchemaId;
	private final AcctSchema _acctSchema;
	private AcctSchemaElementsMap acctSchemaElements;

	public AccountDimensionValidator(@NonNull final AcctSchema acctSchema)
	{
		this.acctSchemaId = acctSchema.getId();
		this._acctSchema = acctSchema;
		this._ctx = InterfaceWrapperHelper.getCtx(acctSchema);
	}

	@Override
	public void setAcctSchemaElements(final AcctSchemaElementsMap acctSchemaElements)
	{
		this.acctSchemaElements = acctSchemaElements;
	}

	private Properties getCtx()
	{
		return _ctx;
	}

	@Override
	public AcctSchema getAcctSchema()
	{
		return _acctSchema;
	}

	@Override
	public AcctSchemaElementsMap getAcctSchemaElements()
	{
		if (acctSchemaElements != null)
		{
			return acctSchemaElements;
		}
		else
		{
			return getAcctSchema().getSchemaElements();
		}
	}

	private AcctSchemaValidCombinationOptions getValidCombinationOptions()
	{
		return getAcctSchema().getValidCombinationOptions();
	}

	@Override
	public void validate(final AccountDimension accountDimension)
	{

		final Set<String> mandatoryFieldsNotFilled = new HashSet<>();

		//
		// Validate C_AcctSchema_ID
		if (AcctSchemaId.equals(accountDimension.getAcctSchemaId(), acctSchemaId))
		{
			throw new AdempiereException("C_AcctSchema_ID not matched"
												 + "\n Expected: " + acctSchemaId
												 + "\n Was: " + accountDimension.getAcctSchemaId());
		}

		//
		// Validate Alias
		if (getValidCombinationOptions().isUseAccountAlias())
		{
			final String alias = accountDimension.getAlias();

			if (Check.isEmpty(alias, true))
			{
				mandatoryFieldsNotFilled.add(msgBL.translate(getCtx(), I_C_ValidCombination.COLUMNNAME_Alias));
			}
		}

		//
		// Validate: AD_Client_ID
		if (accountDimension.getAD_Client_ID() <= 0)
		{
			mandatoryFieldsNotFilled.add(msgBL.translate(getCtx(), I_C_ValidCombination.COLUMNNAME_AD_Client_ID));
		}

		//
		// Validate segments
		final AcctSchemaElementsMap elements = getAcctSchemaElements();
		for (final AcctSchemaElement ase : elements.onlyDisplayedInEditor())
		{
			if (!ase.isMandatory())
			{
				continue;
			}
			final AcctSchemaElementType elementType = ase.getElementType();
			final Object segmentValue = getSegmentValue(accountDimension, elementType);

			if (segmentValue instanceof Integer)
			{
				final int segmentId = NumberUtils.asInt(segmentValue, 0);

				if (segmentId > 0)
				{
					continue;
				}
				mandatoryFieldsNotFilled.add(ase.getName());
			}
		}

		//
		// If we found segments not set throw exception
		if (!mandatoryFieldsNotFilled.isEmpty())
		{
			throw new FillMandatoryException(true, mandatoryFieldsNotFilled);
		}

		//
		// C_ElementValue_ID (i.e. the account number) shall be set
		if (accountDimension.getC_ElementValue_ID() <= 0)
		{
			throw new FillMandatoryException(I_C_ValidCombination.COLUMNNAME_Account_ID);
		}
	}

	/**
	 * @return segment's value (ID)
	 */
	private Object getSegmentValue(final AccountDimension accountDimension, @NonNull final AcctSchemaElementType elementType)
	{
		Check.assumeNotNull(elementType, "elementType not null");

		if (elementType.equals(AcctSchemaElementType.Organization))
		{
			return accountDimension.getAD_Org_ID();
		}
		else if (elementType.equals(AcctSchemaElementType.Account))
		{
			return accountDimension.getC_ElementValue_ID();
		}
		else if (elementType.equals(AcctSchemaElementType.SubAccount))
		{
			return accountDimension.getC_SubAcct_ID();
		}
		else if (elementType.equals(AcctSchemaElementType.Product))
		{
			return accountDimension.getM_Product_ID();
		}
		else if (elementType.equals(AcctSchemaElementType.BPartner))
		{
			return accountDimension.getC_BPartner_ID();
		}
		else if (elementType.equals(AcctSchemaElementType.Campaign))
		{
			return accountDimension.getC_Campaign_ID();
		}
		else if (elementType.equals(AcctSchemaElementType.LocationFrom))
		{
			return accountDimension.getC_LocFrom_ID();
		}
		else if (elementType.equals(AcctSchemaElementType.LocationTo))
		{
			return accountDimension.getC_LocTo_ID();
		}
		else if (elementType.equals(AcctSchemaElementType.Project))
		{
			return accountDimension.getC_Project_ID();
		}
		else if (elementType.equals(AcctSchemaElementType.SalesRegion))
		{
			return accountDimension.getC_SalesRegion_ID();
		}
		else if (elementType.equals(AcctSchemaElementType.OrgTrx))
		{
			return accountDimension.getAD_OrgTrx_ID();
		}
		else if (elementType.equals(AcctSchemaElementType.Activity))
		{
			return accountDimension.getC_Activity_ID();
		}
		else if (elementType.equals(AcctSchemaElementType.SalesOrder))
		{
			return accountDimension.getSalesOrderId();
		}
		else if (elementType.equals(AcctSchemaElementType.SectionCode))
		{
			return accountDimension.getM_SectionCode_ID();
		}
		else if (elementType.equals(AcctSchemaElementType.UserList1))
		{
			return accountDimension.getUser1_ID();
		}
		else if (elementType.equals(AcctSchemaElementType.UserList2))
		{
			return accountDimension.getUser2_ID();
		}
		else if (elementType.equals(AcctSchemaElementType.UserElementNumber1))
		{
			return accountDimension.getUserElementNumber1();
		}
		else if (elementType.equals(AcctSchemaElementType.UserElementNumber2))
		{
			return accountDimension.getUserElementNumber2();
		}
		else if (elementType.equals(AcctSchemaElementType.UserElementString1))
		{
			return accountDimension.getUserElementString1();
		}
		else if (elementType.equals(AcctSchemaElementType.UserElementString2))
		{
			return accountDimension.getUserElementString2();
		}
		else if (elementType.equals(AcctSchemaElementType.UserElementString3))
		{
			return accountDimension.getUserElementString3();
		}
		else if (elementType.equals(AcctSchemaElementType.UserElementString4))
		{
			return accountDimension.getUserElementString4();
		}
		else if (elementType.equals(AcctSchemaElementType.UserElementString5))
		{
			return accountDimension.getUserElementString5();
		}
		else if (elementType.equals(AcctSchemaElementType.UserElementString6))
		{
			return accountDimension.getUserElementString6();
		}
		else if (elementType.equals(AcctSchemaElementType.UserElementString7))
		{
			return accountDimension.getUserElementString7();
		}
		else if (elementType.equals(AcctSchemaElementType.UserElementDate1))
		{
			return accountDimension.getUserElementDate1();
		}
		else if (elementType.equals(AcctSchemaElementType.UserElementDate2))
		{
			return accountDimension.getUserElementDate2();
		}
		else if (elementType.equals(AcctSchemaElementType.HarvestingCalendar))
		{
			return accountDimension.getC_Harvesting_Calendar_ID();
		}
		else if (elementType.equals(AcctSchemaElementType.HarvestingYear))
		{
			return accountDimension.getHarvesting_Year_ID();
		}
		else
		{
			// Unknown
			return -1;
		}
	}

}

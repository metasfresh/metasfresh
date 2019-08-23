package de.metas.pricing.process;

import static org.adempiere.model.InterfaceWrapperHelper.copyValues;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.session.ISessionBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.process.JavaProcess;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class M_PricelistVersion_CreateForBasePLV extends JavaProcess
{
	final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);

	@Override
	protected String doIt() throws Exception
	{
		final I_M_PriceList_Version basePriceListVersion = priceListsRepo.getPriceListVersionById(PriceListVersionId.ofRepoId(getRecord_ID()));

		createCustomerPLV(basePriceListVersion);

		return MSG_OK;
	}

	public void createCustomerPLV(final I_M_PriceList_Version newBasePLV)
	{

		final PriceListVersionId basePriceListVersionId = PriceListVersionId.ofRepoId(newBasePLV.getM_Pricelist_Version_Base_ID());
		if (basePriceListVersionId == null)
		{
			// nothing to do
			return;
		}

		I_M_PriceList_Version oldPLV = priceListsRepo.getPriceListVersionById(basePriceListVersionId);

		final List<I_M_PriceList_Version> versionsForOldBase = priceListsRepo.retrieveCustomPLVsForBasePLV(oldPLV);

		for (final I_M_PriceList_Version oldCustPLV : versionsForOldBase)
		{
			// final PricingConditionsId oldCustomerPricingConditionsId = CoalesceUtil.coalesce(
			// PricingConditionsId.ofDiscountSchemaIdOrNull(oldCustPLV.getM_DiscountSchema_ID()),
			// PricingConditionsId.ofDiscountSchemaIdOrNull(oldPLV.getM_DiscountSchema_ID()));
			// TODO: Cleanup

			createNewPLV(oldCustPLV, newBasePLV);
		}

	}

	private void createNewPLV(final I_M_PriceList_Version oldCustomerPLV, final I_M_PriceList_Version newBasePLV)
	{
		final ISessionBL sessionBL = Services.get(ISessionBL.class);

		final I_M_PriceList_Version newCustomerPLV = newInstance(I_M_PriceList_Version.class);

		copyValues(newBasePLV, newCustomerPLV);

		// newInstance(I_M_PriceList_Version.class);
		// newCustomerPLV.setM_Pricelist_Version_Base_ID(newBasePLVId.getRepoId());
		newCustomerPLV.setM_DiscountSchema_ID(oldCustomerPLV.getM_DiscountSchema_ID());
		newCustomerPLV.setM_PriceList_ID(oldCustomerPLV.getM_PriceList_ID());
		newCustomerPLV.setM_Pricelist_Version_Base_ID(newBasePLV.getM_PriceList_Version_ID());
		saveRecord(newCustomerPLV);

		final PriceListVersionId newCustomerPLVId = PriceListVersionId.ofRepoId(newCustomerPLV.getM_PriceList_Version_ID());

		// Disabling change log creation because we might create and then update a huge amount of records.
		// To avoid this huge performance issue we are disabling for this thread (08125)
		sessionBL.setDisableChangeLogsForThread(true);

		try
		{
			DB.executeFunctionCallEx( //
					ITrx.TRXNAME_ThreadInherited //
					, "select M_PriceList_Version_CopyFromBase(p_M_PriceList_Version_ID:=?, p_AD_User_ID:=?)" //
					, new Object[] { newCustomerPLVId, getAD_User_ID() } //
			);

			cloneASIs(newCustomerPLVId);

			// newCustomerPLV.setM_Pricelist_Version_Base_ID(newBasePLV); TODO
		}
		finally
		{
			sessionBL.setDisableChangeLogsForThread(false);
		}

	}

	private void cloneASIs(PriceListVersionId newPLVId)
	{
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ProductPrice.class, Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.addEqualsFilter(I_M_ProductPrice.COLUMN_M_PriceList_Version_ID, newPLVId)
				.addEqualsFilter(I_M_ProductPrice.COLUMN_IsAttributeDependant, true)
				.create()
				.iterateAndStream()
				.forEach(this::cloneASI);
	}

	private void cloneASI(final I_M_ProductPrice productPrice)
	{
		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
		if (!productPrice.isAttributeDependant())
		{
			return;
		}

		// NOTE: we assume the ASI was set when the initial copy function was executed

		final I_M_AttributeSetInstance sourceASI = productPrice.getM_AttributeSetInstance();
		final I_M_AttributeSetInstance targetASI = sourceASI == null ? null : attributeDAO.copy(sourceASI);

		productPrice.setM_AttributeSetInstance(targetASI);
		InterfaceWrapperHelper.save(productPrice);
	}

}

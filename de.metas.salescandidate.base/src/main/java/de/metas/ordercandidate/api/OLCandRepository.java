package de.metas.ordercandidate.api;

import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;

import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.ordercandidate.model.I_C_OLCand;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
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

@Repository
public class OLCandRepository
{
	public OLCandSource getForProcessor(@NonNull OLCandProcessorDescriptor processor)
	{
		return RelationTypeOLCandSource.builder()
				.orderDefaults(processor.getDefaults())
				.olCandProcessorId(processor.getId())
				.build();
	}

	public List<OLCand> create(@NonNull final List<OLCandCreateRequest> requests)
	{
		Check.assumeNotEmpty(requests, "requests is not empty");

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		return trxManager.call(ITrx.TRXNAME_ThreadInherited, () -> requests.stream()
				.map(this::create)
				.collect(ImmutableList.toImmutableList()));
	}

	public OLCand create(@NonNull final OLCandCreateRequest request)
	{
		final I_C_OLCand olCandPO = InterfaceWrapperHelper.newInstance(I_C_OLCand.class);

		{
			final OLCandBPartnerInfo bpartner = request.getBpartner();
			olCandPO.setC_BPartner_ID(bpartner.getBpartnerId());
			olCandPO.setC_BPartner_Location_ID(bpartner.getBpartnerLocationId());
			olCandPO.setAD_User_ID(bpartner.getContactId());
		}

		if (request.getBillBPartner() != null)
		{
			OLCandBPartnerInfo bpartner = request.getBillBPartner();
			olCandPO.setBill_BPartner_ID(bpartner.getBpartnerId());
			olCandPO.setBill_Location_ID(bpartner.getBpartnerLocationId());
			olCandPO.setBill_User_ID(bpartner.getContactId());
		}

		if (request.getDropShipBPartner() != null)
		{
			final OLCandBPartnerInfo bpartner = request.getDropShipBPartner();
			olCandPO.setDropShip_BPartner_ID(bpartner.getBpartnerId());
			olCandPO.setDropShip_Location_ID(bpartner.getBpartnerLocationId());
			// olCandPO.setDropShip_User_ID(bpartner.getContactId());
		}

		if (request.getHandOverBPartner() != null)
		{
			final OLCandBPartnerInfo bpartner = request.getHandOverBPartner();
			olCandPO.setHandOver_Partner_ID(bpartner.getBpartnerId());
			olCandPO.setHandOver_Location_ID(bpartner.getBpartnerLocationId());
			// olCandPO.setHandOver_User_ID(bpartner.getContactId());
		}

		if (!Check.isEmpty(request.getPoReference(), true))
		{
			olCandPO.setPOReference(request.getPoReference());
		}

		olCandPO.setDateCandidate(SystemTime.asDayTimestamp());
		olCandPO.setDatePromised(TimeUtil.asTimestamp(request.getDateRequired()));
		olCandPO.setC_Flatrate_Conditions_ID(request.getFlatrateConditionsId());

		olCandPO.setM_Product_ID(request.getProductId());
		olCandPO.setProductDescription(request.getProductDescription());
		olCandPO.setQty(request.getQty());
		olCandPO.setC_UOM_ID(request.getUomId());
		olCandPO.setM_HU_PI_Item_Product_ID(request.getHuPIItemProductId());

		if (request.getPricingSystemId() > 0)
		{
			olCandPO.setM_PricingSystem_ID(request.getPricingSystemId());
		}
		olCandPO.setPriceEntered(request.getPrice());
		olCandPO.setDiscount(request.getDiscount());

		olCandPO.setAD_User_EnteredBy_ID(Env.getAD_User_ID());
		olCandPO.setAD_InputDataSource_ID(Services.get(IInputDataSourceDAO.class).retrieveInputDataSourceId(request.getAdInputDataSourceInternalName()));
		olCandPO.setExternalId(request.getExternalId());

		InterfaceWrapperHelper.save(olCandPO);

		return OLCand.builder()
				.candidate(olCandPO)
				.build();
	}
}

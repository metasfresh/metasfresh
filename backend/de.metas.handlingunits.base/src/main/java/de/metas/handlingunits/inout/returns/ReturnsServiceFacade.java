/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits.inout.returns;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.inout.returns.customer.CreateCustomerReturnLineReq;
import de.metas.handlingunits.inout.returns.customer.CustomerReturnLineCandidate;
import de.metas.handlingunits.inout.returns.customer.CustomerReturnsWithoutHUsProducer;
import de.metas.handlingunits.inout.returns.customer.MultiCustomerHUReturnsInOutProducer;
import de.metas.handlingunits.inout.returns.customer.MultiCustomerHUReturnsResult;
import de.metas.handlingunits.inout.returns.vendor.MultiVendorHUReturnsInOutProducer;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.inout.InOutId;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

/**
 * Main class for all material returns related services
 */
@Service
public class ReturnsServiceFacade
{
	private final IHUInOutBL huInOutBL = Services.get(IHUInOutBL.class);
	private final CustomerReturnsWithoutHUsProducer customerReturnsWithoutHUsProducer;

	public ReturnsServiceFacade(
			@NonNull final CustomerReturnsWithoutHUsProducer customerReturnsWithoutHUsProducer)
	{
		this.customerReturnsWithoutHUsProducer = customerReturnsWithoutHUsProducer;
	}

	public boolean isCustomerReturn(@NonNull final org.compiere.model.I_M_InOut inout)
	{
		return huInOutBL.isCustomerReturn(inout);
	}

	public boolean isVendorReturn(@NonNull final org.compiere.model.I_M_InOut inout)
	{
		return huInOutBL.isVendorReturn(inout);
	}

	public boolean isServiceRepair(@NonNull final org.compiere.model.I_M_InOut inout)
	{
		return huInOutBL.isServiceRepair(inout);
	}

	public boolean isEmptiesReturn(@NonNull final org.compiere.model.I_M_InOut inout)
	{
		return huInOutBL.isEmptiesReturn(inout);
	}

	public MultiCustomerHUReturnsResult createCustomerReturnInOutForHUs(final Collection<I_M_HU> shippedHUsToReturn)
	{
		return MultiCustomerHUReturnsInOutProducer.builder()
				.shippedHUsToReturn(shippedHUsToReturn)
				.build()
				.create();
	}

	public void createVendorReturnInOutForHUs(final List<I_M_HU> hus, final Timestamp movementDate)
	{
		MultiVendorHUReturnsInOutProducer.newInstance()
				.setMovementDate(movementDate)
				.addHUsToReturn(hus)
				.create();
	}

	public List<InOutId> createCustomerReturnsFromCandidates(@NonNull final List<CustomerReturnLineCandidate> candidates)
	{
		return customerReturnsWithoutHUsProducer.create(candidates);
	}

	public I_M_InOutLine createCustomerReturnLine(@NonNull final CreateCustomerReturnLineReq request)
	{
		return customerReturnsWithoutHUsProducer.createReturnLine(request);
	}

	public void assignHandlingUnitToHeaderAndLine(
			@NonNull final org.compiere.model.I_M_InOutLine customerReturnLine,
			@NonNull final I_M_HU hu)
	{
		final ImmutableList<I_M_HU> hus = ImmutableList.of(hu);
		assignHandlingUnitsToHeaderAndLine(customerReturnLine, hus);

	}

	public void assignHandlingUnitsToHeaderAndLine(
			@NonNull final org.compiere.model.I_M_InOutLine customerReturnLine,
			@NonNull final List<I_M_HU> hus)
	{
		if (hus.isEmpty())
		{
			return;
		}

		final InOutId customerReturnId = InOutId.ofRepoId(customerReturnLine.getM_InOut_ID());
		final I_M_InOut customerReturn = huInOutBL.getById(customerReturnId, I_M_InOut.class);

		huInOutBL.addAssignedHandlingUnits(customerReturn, hus);
		huInOutBL.setAssignedHandlingUnits(customerReturnLine, hus);
	}
}

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

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.inout.returns.customer.CreateCustomerReturnLineReq;
import de.metas.handlingunits.inout.returns.customer.CustomerReturnLineCandidate;
import de.metas.handlingunits.inout.returns.customer.CustomerReturnLineHUGenerator;
import de.metas.handlingunits.inout.returns.customer.CustomerReturnsWithoutHUsProducer;
import de.metas.handlingunits.inout.returns.customer.ManualCustomerReturnInOutProducer;
import de.metas.handlingunits.inout.returns.customer.MultiCustomerHUReturnsInOutProducer;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
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
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
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

	public void createCustomerReturnInOutForHUs(final Collection<I_M_HU> hus)
	{
		MultiCustomerHUReturnsInOutProducer.newInstance()
				.addHUsToReturn(hus)
				.create();
	}

	/**
	 * Create HUs for manual customer return.
	 */
	public void createHUsForCustomerReturn(@NonNull final I_M_InOut customerReturn)
	{
		Check.assume(isCustomerReturn(customerReturn), "Inout {} is not a customer return ", customerReturn);
		final IContextAware ctxAware = InterfaceWrapperHelper.getContextAware(customerReturn);

		final List<I_M_InOutLine> customerReturnLines = inOutDAO.retrieveLines(customerReturn, I_M_InOutLine.class);
		if (customerReturnLines.isEmpty())
		{
			throw new AdempiereException("No customer return lines found");
		}

		final ArrayListMultimap<InOutLineId, I_M_HU> husByLineId = ArrayListMultimap.create();

		// final List<I_M_HU> hus = new ArrayList<>();
		for (final I_M_InOutLine customerReturnLine : customerReturnLines)
		{
			final CustomerReturnLineHUGenerator huGenerator = CustomerReturnLineHUGenerator.newInstance(ctxAware);
			huGenerator.addM_InOutLine(customerReturnLine);
			final List<I_M_HU> currentHUs = huGenerator.generate();
			//hus.addAll(currentHUs);

			husByLineId.putAll(InOutLineId.ofRepoId(customerReturnLine.getM_InOutLine_ID()), currentHUs);
		}

		ManualCustomerReturnInOutProducer.builder()
				.manualCustomerReturn(customerReturn)
				.husByLineId(husByLineId)
				.build()
				.create();

		//return hus;
	}

	public void createVendorReturnInOutForHUs(final List<I_M_HU> hus, final Timestamp movementDate)
	{
		MultiCustomerHUReturnsInOutProducer.newInstance()
				.setMovementDate(movementDate)
				.addHUsToReturn(hus)
				.create();
	}

	public List<InOutId> createCustomerReturnsFromCandidates(@NonNull final List<CustomerReturnLineCandidate> candidates)
	{
		return customerReturnsWithoutHUsProducer.create(candidates);
	}

	@Value
	@Builder
	public static class CloneHUAndCreateCustomerReturnLineRequest
	{
		@NonNull InOutId customerReturnId;
		@NonNull ProductId productId;
		@NonNull HuId cloneFromHuId;
		@NonNull Quantity qtyReturned;
	}

	public void cloneHUAndCreateCustomerReturnLineRequest(@NonNull final CloneHUAndCreateCustomerReturnLineRequest request)
	{
		final I_M_HU clonedPlaningHU = handlingUnitsBL.copyAsPlannedHU(request.getCloneFromHuId());

		final I_M_InOutLine customerReturnLine = customerReturnsWithoutHUsProducer.createReturnLine(
				CreateCustomerReturnLineReq.builder()
						.headerId(request.getCustomerReturnId())
						.productId(request.getProductId())
						.qtyReturned(request.getQtyReturned())
						.build());

		final I_M_InOut customerReturn = inOutDAO.getById(request.getCustomerReturnId(), I_M_InOut.class);

		// Assign the cloned HU
		huInOutBL.addAssignedHandlingUnits(customerReturn, ImmutableList.of(clonedPlaningHU));
		huInOutBL.setAssignedHandlingUnits(customerReturnLine, ImmutableList.of(clonedPlaningHU));
	}
}

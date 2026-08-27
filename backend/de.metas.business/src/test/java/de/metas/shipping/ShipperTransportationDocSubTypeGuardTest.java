/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.shipping;

import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_DocType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.google.common.collect.ImmutableList;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link ShipperTransportationDocSubTypeGuard} works both ways (a delivery instruction is hidden from a
 * transport-order-only process AND vice versa), and a plain transport order is a complete no-op through it -
 * proven with interaction counts on {@link IDocTypeDAO}, not just the outcome, per the "prove the no-op" rule for
 * this process family (this precondition runs on every grid selection change).
 */
class ShipperTransportationDocSubTypeGuardTest
{
	private IDocTypeDAO docTypeDAO;
	private ShipperTransportationDocSubTypeGuard guard;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		// spy the real (table-cached) DocTypeDAO, so the lookup actually happens against the in-memory
		// C_DocType record while the call count stays observable
		docTypeDAO = Mockito.spy(Services.get(IDocTypeDAO.class));
		Services.registerService(IDocTypeDAO.class, docTypeDAO);

		guard = new ShipperTransportationDocSubTypeGuard();
	}

	private I_M_ShipperTransportation shipperTransportation(final int docTypeId)
	{
		final I_M_ShipperTransportation record = InterfaceWrapperHelper.newInstance(I_M_ShipperTransportation.class);
		record.setC_DocType_ID(docTypeId);
		return record;
	}

	private int createDocType(final String docSubType)
	{
		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		docType.setName("docType-" + docSubType);
		docType.setDocBaseType(DocBaseType.ShipperTransportation.getCode());
		docType.setDocSubType(docSubType);
		InterfaceWrapperHelper.save(docType);
		return docType.getC_DocType_ID();
	}

	@Test
	void isDeliveryInstruction_true_forDeliveryInstructionDocType()
	{
		final I_M_ShipperTransportation deliveryInstruction = shipperTransportation(createDocType(DocSubType.DeliveryInstruction.getCode()));

		assertThat(guard.isDeliveryInstruction(deliveryInstruction)).isTrue();
	}

	@Test
	void isDeliveryInstruction_false_forPlainTransportOrder()
	{
		final I_M_ShipperTransportation transportOrder = shipperTransportation(createDocType(null));

		assertThat(guard.isDeliveryInstruction(transportOrder)).isFalse();
	}

	@Test
	void rejectIfDeliveryInstruction_transportOrder_isANoOp()
	{
		final I_M_ShipperTransportation transportOrder = shipperTransportation(createDocType(null));

		final ProcessPreconditionsResolution resolution = guard.rejectIfDeliveryInstruction(transportOrder);

		assertThat(resolution.isAccepted()).isTrue();
		// the no-op proof: exactly one cached lookup, no repeated/hidden work
		Mockito.verify(docTypeDAO, Mockito.times(1)).getById(Mockito.any(DocTypeId.class));
	}

	@Test
	void rejectIfDeliveryInstruction_deliveryInstruction_rejectsInternally()
	{
		final I_M_ShipperTransportation deliveryInstruction = shipperTransportation(createDocType(DocSubType.DeliveryInstruction.getCode()));

		final ProcessPreconditionsResolution resolution = guard.rejectIfDeliveryInstruction(deliveryInstruction);

		assertThat(resolution.isAccepted()).isFalse();
	}

	@Test
	void rejectIfNotDeliveryInstruction_deliveryInstruction_accepts()
	{
		final I_M_ShipperTransportation deliveryInstruction = shipperTransportation(createDocType(DocSubType.DeliveryInstruction.getCode()));

		final ProcessPreconditionsResolution resolution = guard.rejectIfNotDeliveryInstruction(deliveryInstruction);

		assertThat(resolution.isAccepted()).isTrue();
	}

	@Test
	void rejectIfNotDeliveryInstruction_transportOrder_rejectsInternally()
	{
		final I_M_ShipperTransportation transportOrder = shipperTransportation(createDocType(null));

		final ProcessPreconditionsResolution resolution = guard.rejectIfNotDeliveryInstruction(transportOrder);

		assertThat(resolution.isAccepted()).isFalse();
	}

	@Test
	void rejectIfDeliveryInstruction_batchOfTransportOrders_oneLookupPerRow_noExtraWork()
	{
		final int transportOrderDocTypeId = createDocType(null);
		final List<I_M_ShipperTransportation> rows = ImmutableList.of(
				shipperTransportation(transportOrderDocTypeId),
				shipperTransportation(transportOrderDocTypeId),
				shipperTransportation(transportOrderDocTypeId));

		for (final I_M_ShipperTransportation row : rows)
		{
			assertThat(guard.rejectIfDeliveryInstruction(row).isAccepted()).isTrue();
		}

		// linear, not quadratic: exactly one DocType lookup per row, all against the same cached record
		Mockito.verify(docTypeDAO, Mockito.times(rows.size())).getById(Mockito.any(DocTypeId.class));
	}
}

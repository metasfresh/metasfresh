/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.shipping.process;

import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_DocType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * {@code M_ShippingPackage_CreateFromPickingSlots} is a transport-order-only action - it must not be offered
 * on a delivery instruction, and a plain transport order must be completely unaffected (a no-op) by the added
 * guard.
 */
class M_ShippingPackage_CreateFromPickingSlotsTest
{
	private IDocTypeDAO docTypeDAO;
	private M_ShippingPackage_CreateFromPickingSlots process;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		// spy the real (table-cached) DocTypeDAO BEFORE the guard is constructed, so the guard captures the spy
		docTypeDAO = Mockito.spy(Services.get(IDocTypeDAO.class));
		Services.registerService(IDocTypeDAO.class, docTypeDAO);

		SpringContextHolder.registerJUnitBean(new ShipperTransportationDocSubTypeGuard());
		process = new M_ShippingPackage_CreateFromPickingSlots();
	}

	private I_M_ShipperTransportation shipperTransportation(final String docSubType, final boolean processed)
	{
		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		docType.setName("docType-" + docSubType);
		docType.setDocBaseType(DocBaseType.ShipperTransportation.getCode());
		docType.setDocSubType(docSubType);
		InterfaceWrapperHelper.save(docType);

		final I_M_ShipperTransportation record = InterfaceWrapperHelper.newInstance(I_M_ShipperTransportation.class);
		record.setC_DocType_ID(docType.getC_DocType_ID());
		record.setProcessed(processed);
		InterfaceWrapperHelper.save(record);
		return record;
	}

	private IProcessPreconditionsContext contextSelecting(final I_M_ShipperTransportation record)
	{
		final IProcessPreconditionsContext context = mock(IProcessPreconditionsContext.class);
		when(context.getSelectedModel(I_M_ShipperTransportation.class)).thenReturn(record);
		return context;
	}

	@Test
	void unprocessedTransportOrder_isANoOp_stillOffered()
	{
		final I_M_ShipperTransportation transportOrder = shipperTransportation(null, false);

		final ProcessPreconditionsResolution resolution = process.checkPreconditionsApplicable(contextSelecting(transportOrder));

		assertThat(resolution.isAccepted()).isTrue();
		// exactly one C_DocType lookup for the single selected row
		Mockito.verify(docTypeDAO, Mockito.times(1)).getById(Mockito.any(DocTypeId.class));
	}

	@Test
	void deliveryInstruction_isHidden_evenWhenUnprocessed()
	{
		final I_M_ShipperTransportation deliveryInstruction = shipperTransportation(DocSubType.DeliveryInstruction.getCode(), false);

		final ProcessPreconditionsResolution resolution = process.checkPreconditionsApplicable(contextSelecting(deliveryInstruction));

		assertThat(resolution.isAccepted()).isFalse();
	}

	@Test
	void noSelection_isRejected_asBefore()
	{
		final IProcessPreconditionsContext context = mock(IProcessPreconditionsContext.class);
		when(context.getSelectedModel(I_M_ShipperTransportation.class)).thenReturn(null);

		final ProcessPreconditionsResolution resolution = process.checkPreconditionsApplicable(context);

		assertThat(resolution.isAccepted()).isFalse();
	}
}

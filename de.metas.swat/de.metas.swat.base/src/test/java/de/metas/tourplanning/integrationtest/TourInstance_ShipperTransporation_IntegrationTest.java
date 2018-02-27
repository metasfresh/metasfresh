package de.metas.tourplanning.integrationtest;

/*
 * #%L
 * de.metas.swat.base
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


import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.junit.Test;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.engine.impl.PlainDocumentBL;
import de.metas.document.exceptions.DocumentProcessingException;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.tourplanning.TourPlanningTestBase;
import de.metas.tourplanning.model.I_M_Tour;
import de.metas.tourplanning.model.I_M_Tour_Instance;

/**
 * Tests the interaction between {@link I_M_Tour_Instance} and {@link I_M_ShipperTransportation}
 * 
 * @author tsa
 *
 */
public class TourInstance_ShipperTransporation_IntegrationTest extends TourPlanningTestBase
{
	//
	// Services
	private PlainDocumentBL docActionBL;

	//
	// Master data
	private I_M_Tour tour;
	private I_M_ShipperTransportation shipperTransportation;
	private I_M_Tour_Instance tourInstance;

	@Override
	protected void afterInit()
	{
		//
		// Configure and register PlainDocActionBL
		this.docActionBL = new PlainDocumentBL();
		PlainDocumentBL.isDocumentTableResponse = true;
		this.docActionBL.setDefaultProcessInterceptor(PlainDocumentBL.PROCESSINTERCEPTOR_CompleteDirectly);
		Services.registerService(IDocumentBL.class, docActionBL);

		//
		// Master data
		this.tour = createTour("tour01");
		this.shipperTransportation = createShipperTransporation();
		this.tourInstance = createTourInstance(shipperTransportation);
	}

	@Test
	public void test_complete_reActivate_ShipperTransporation()
	{
		assertProcessed(false, tourInstance);

		// Complete
		docActionBL.processEx(shipperTransportation, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

		// Assert Tour Instance is processed
		InterfaceWrapperHelper.refresh(tourInstance);
		assertProcessed(true, tourInstance);

		// ReActivate
		docActionBL.processEx(shipperTransportation, IDocument.ACTION_ReActivate, IDocument.STATUS_InProgress);

		// Assert Tour Instance is NOT processed
		InterfaceWrapperHelper.refresh(tourInstance);
		assertProcessed(false, tourInstance);
	}

	@Test(expected = DocumentProcessingException.class)
	public void test_void_ShipperTransportation_ShallFail()
	{
		// Void it => shall throw exception
		docActionBL.processEx(shipperTransportation, IDocument.ACTION_Void, null);
	}

	@Test(expected = DocumentProcessingException.class)
	public void test_reverse_ShipperTransportation_ShallFail()
	{
		// Reverse it => shall throw exception
		docActionBL.processEx(shipperTransportation, IDocument.ACTION_Reverse_Correct, null);
	}

	private I_M_ShipperTransportation createShipperTransporation()
	{
		final I_M_ShipperTransportation shipperTransportation = InterfaceWrapperHelper.newInstance(I_M_ShipperTransportation.class, contextProvider);
		shipperTransportation.setProcessed(false);
		shipperTransportation.setProcessing(false);
		shipperTransportation.setDocStatus(IDocument.STATUS_Drafted);
		shipperTransportation.setDocAction(IDocument.ACTION_Complete);
		InterfaceWrapperHelper.save(shipperTransportation);

		return shipperTransportation;
	}

	private I_M_Tour_Instance createTourInstance(final I_M_ShipperTransportation shipperTransportation)
	{
		final I_M_Tour_Instance tourInstance = InterfaceWrapperHelper.newInstance(I_M_Tour_Instance.class, contextProvider);
		tourInstance.setM_Tour(tour);
		tourInstance.setProcessed(false);
		tourInstance.setM_ShipperTransportation_ID(shipperTransportation.getM_ShipperTransportation_ID());
		InterfaceWrapperHelper.save(tourInstance);

		return tourInstance;
	}

}

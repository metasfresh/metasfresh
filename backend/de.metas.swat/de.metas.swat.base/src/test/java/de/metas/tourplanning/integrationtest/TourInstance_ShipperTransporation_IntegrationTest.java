package de.metas.tourplanning.integrationtest;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.engine.impl.PlainDocumentBL;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.tourplanning.TourPlanningTestBase;
import de.metas.tourplanning.model.I_M_Tour;
import de.metas.tourplanning.model.I_M_Tour_Instance;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests the interaction between {@link I_M_Tour_Instance} and {@link I_M_ShipperTransportation}
 *
 * @author tsa
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

	@Test
	public void test_void_ShipperTransportation_ShallFail()
	{
		// Void it => shall throw exception
		assertThatThrownBy(() -> docActionBL.processEx(shipperTransportation, IDocument.ACTION_Void, null))
				.isInstanceOf(AdempiereException.class)
				.hasMessageStartingWith("NotAllowed ");
	}

	@Test
	public void test_reverse_ShipperTransportation_ShallFail()
	{
		// Reverse it => shall throw exception
		assertThatThrownBy(() -> docActionBL.processEx(shipperTransportation, IDocument.ACTION_Reverse_Correct, null))
				.isInstanceOf(AdempiereException.class)
				.hasMessageStartingWith("NotAllowed ");
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

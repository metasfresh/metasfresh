package de.metas.inoutcandidate.modelvalidator;

import java.util.Set;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.ModelValidator;

import de.metas.event.Event;
import de.metas.event.Event.Builder;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.inoutcandidate.event.EventUtil;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.manufacturing.event.ShipmentScheduleEventBus;

/**
 * Shipment Schedule module: M_ShipmentSchedule
 *
 * @author tsa
 *
 */
@Validator(I_M_ShipmentSchedule.class)
public class M_ShipmentSchedule_EventDispatcher
{



}

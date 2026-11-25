package de.metas.frontend_testing.masterdata.shipment_schedule;

import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.api.impl.ShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.model.I_M_IolCandHandler;
import de.metas.logging.LogManager;
import de.metas.order.inoutcandidate.OrderLineShipmentScheduleHandler;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.Attribute;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Makes sure that the given attributes are active in {@code M_ShipmentSchedule_AttributeConfig} of the sales-order-line
 * shipment schedule handler, i.e. that shipment lines are split by their values (e.g. {@code Lot-Nummer}). The attributes are given by attribute code ({@code M_Attribute.Value}).
 * <p>
 * An active row that already exists is left untouched (never deactivated, {@code OnlyIfInReferencedASI} never changed).
 * Otherwise a new row with {@code OnlyIfInReferencedASI = N} is inserted; existing inactive rows are not touched.
 * <b>The rows stay after the run (no cleanup) and are not scoped to a test or run:</b> they switch the splitting of
 * shipment lines on for every later shipment of that database, in this run and in later ones. Unlike other e2e masterdata
 * (unique per run), this is a global behaviour switch. Each insert is logged, so a run log shows whether the command
 * changed anything.
 */
@Builder
public class EnsureShipmentScheduleAttributeConfigCommand
{
	private static final Logger logger = LogManager.getLogger(EnsureShipmentScheduleAttributeConfigCommand.class);
	private static final String HANDLER_CLASSNAME = OrderLineShipmentScheduleHandler.class.getName();

	@NonNull private final ShipmentScheduleAttributeConfigRepository repository = new ShipmentScheduleAttributeConfigRepository();
	@NonNull private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

	@Nullable private final List<String> attributeCodes;

	public void execute()
	{
		if (attributeCodes == null || attributeCodes.isEmpty())
		{
			return;
		}

		final int handlerId = getHandlerId();
		attributeCodes.forEach(attributeCode -> ensure(handlerId, getAttributeId(attributeCode)));
	}

	private int getHandlerId()
	{
		// the handler record lookup is only on the implementation, not on the interface
		final IShipmentScheduleHandlerBL handlerBL = Services.get(IShipmentScheduleHandlerBL.class);
		if (!(handlerBL instanceof ShipmentScheduleHandlerBL))
		{
			throw new AdempiereException("Unexpected IShipmentScheduleHandlerBL implementation: " + handlerBL.getClass().getName());
		}
		final I_M_IolCandHandler handler = ((ShipmentScheduleHandlerBL)handlerBL).retrieveHandlerRecordOrNull(HANDLER_CLASSNAME);
		if (handler == null)
		{
			throw new AdempiereException("No active M_IolCandHandler with Classname=" + HANDLER_CLASSNAME);
		}
		return handler.getM_IolCandHandler_ID();
	}

	private AttributeId getAttributeId(@NonNull final String attributeCodeStr)
	{
		// getAttributeByCode also returns inactive attributes
		final Attribute attribute = attributeDAO.getAttributeByCode(AttributeCode.ofString(attributeCodeStr));
		if (!attribute.isActive())
		{
			throw new AdempiereException("M_Attribute with Value=" + attributeCodeStr + " is not active");
		}
		return attribute.getAttributeId();
	}

	private void ensure(final int handlerId, @NonNull final AttributeId attributeId)
	{
		if (repository.existsActive(handlerId, attributeId))
		{
			logger.info("M_ShipmentSchedule_AttributeConfig already active for M_Attribute_ID={}", attributeId.getRepoId());
			return;
		}

		repository.insertActive(handlerId, attributeId);
		logger.info("Inserted M_ShipmentSchedule_AttributeConfig for M_Attribute_ID={}", attributeId.getRepoId());
	}
}

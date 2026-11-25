package de.metas.frontend_testing.masterdata.shipment_schedule;

import de.metas.inoutcandidate.model.I_M_IolCandHandler;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_AttributeConfig;
import de.metas.logging.LogManager;
import de.metas.order.inoutcandidate.OrderLineShipmentScheduleHandler;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Attribute;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Makes sure that the given attributes are active in {@code M_ShipmentSchedule_AttributeConfig} of the sales-order-line
 * shipment schedule handler, i.e. that shipment lines are split by their values (e.g. {@code Lot-Nummer}).
 * <p>
 * An active row that already exists is left untouched (never deactivated, {@code OnlyIfInReferencedASI} never changed).
 * Otherwise a new row with {@code OnlyIfInReferencedASI = N} is inserted; existing inactive rows are not touched.
 * The rows stay after the run (no cleanup), like all other e2e masterdata. Each insert is logged, so a run log shows
 * whether the command changed anything.
 */
@Builder
public class EnsureShipmentScheduleAttributeConfigCommand
{
	private static final Logger logger = LogManager.getLogger(EnsureShipmentScheduleAttributeConfigCommand.class);
	private static final String HANDLER_CLASSNAME = OrderLineShipmentScheduleHandler.class.getName();

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

	@Nullable private final List<String> attributeValues;

	public void execute()
	{
		if (attributeValues == null || attributeValues.isEmpty())
		{
			return;
		}

		final int handlerId = getHandlerId();
		attributeValues.forEach(attributeValue -> ensure(handlerId, getAttributeId(attributeValue)));
	}

	private int getHandlerId()
	{
		final I_M_IolCandHandler handler = queryBL.createQueryBuilder(I_M_IolCandHandler.class)
				.addEqualsFilter(I_M_IolCandHandler.COLUMNNAME_Classname, HANDLER_CLASSNAME)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_M_IolCandHandler.class);
		if (handler == null)
		{
			throw new AdempiereException("No active M_IolCandHandler with Classname=" + HANDLER_CLASSNAME);
		}
		return handler.getM_IolCandHandler_ID();
	}

	private AttributeId getAttributeId(@NonNull final String attributeValue)
	{
		final I_M_Attribute attribute = attributeDAO.retrieveActiveAttributeByValueOrNull(AttributeCode.ofString(attributeValue));
		if (attribute == null)
		{
			throw new AdempiereException("No active M_Attribute with Value=" + attributeValue);
		}
		return AttributeId.ofRepoId(attribute.getM_Attribute_ID());
	}

	private void ensure(final int handlerId, @NonNull final AttributeId attributeId)
	{
		final boolean activeRowExists = queryBL.createQueryBuilder(I_M_ShipmentSchedule_AttributeConfig.class)
				.addEqualsFilter(I_M_ShipmentSchedule_AttributeConfig.COLUMNNAME_M_IolCandHandler_ID, handlerId)
				.addEqualsFilter(I_M_ShipmentSchedule_AttributeConfig.COLUMNNAME_M_Attribute_ID, attributeId)
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch();
		if (activeRowExists)
		{
			logger.info("M_ShipmentSchedule_AttributeConfig already active for M_Attribute_ID={}", attributeId.getRepoId());
			return;
		}

		final I_M_ShipmentSchedule_AttributeConfig record = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule_AttributeConfig.class);
		record.setM_IolCandHandler_ID(handlerId);
		record.setM_Attribute_ID(attributeId.getRepoId());
		record.setOnlyIfInReferencedASI(false);
		record.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(record);
		logger.info("Inserted M_ShipmentSchedule_AttributeConfig for M_Attribute_ID={}", attributeId.getRepoId());
	}
}

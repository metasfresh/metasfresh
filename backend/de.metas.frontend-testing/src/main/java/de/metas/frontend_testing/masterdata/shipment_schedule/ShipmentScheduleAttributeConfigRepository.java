package de.metas.frontend_testing.masterdata.shipment_schedule;

import de.metas.inoutcandidate.model.I_M_IolCandHandler;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_AttributeConfig;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.model.InterfaceWrapperHelper;

/**
 * Access to {@code M_ShipmentSchedule_AttributeConfig} (and the {@code M_IolCandHandler} it belongs to) for the e2e masterdata commands.
 */
class ShipmentScheduleAttributeConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	int getHandlerIdByClassname(@NonNull final String classname)
	{
		final I_M_IolCandHandler handler = queryBL.createQueryBuilder(I_M_IolCandHandler.class)
				.addEqualsFilter(I_M_IolCandHandler.COLUMNNAME_Classname, classname)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_M_IolCandHandler.class);
		if (handler == null)
		{
			throw new AdempiereException("No active M_IolCandHandler with Classname=" + classname);
		}
		return handler.getM_IolCandHandler_ID();
	}

	boolean existsActive(final int handlerId, @NonNull final AttributeId attributeId)
	{
		return queryBL.createQueryBuilder(I_M_ShipmentSchedule_AttributeConfig.class)
				.addEqualsFilter(I_M_ShipmentSchedule_AttributeConfig.COLUMNNAME_M_IolCandHandler_ID, handlerId)
				.addEqualsFilter(I_M_ShipmentSchedule_AttributeConfig.COLUMNNAME_M_Attribute_ID, attributeId)
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch();
	}

	/** Inserts an active row with {@code OnlyIfInReferencedASI = N}. */
	void insertActive(final int handlerId, @NonNull final AttributeId attributeId)
	{
		final I_M_ShipmentSchedule_AttributeConfig record = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule_AttributeConfig.class);
		record.setM_IolCandHandler_ID(handlerId);
		record.setM_Attribute_ID(attributeId.getRepoId());
		record.setOnlyIfInReferencedASI(false);
		record.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(record);
	}
}

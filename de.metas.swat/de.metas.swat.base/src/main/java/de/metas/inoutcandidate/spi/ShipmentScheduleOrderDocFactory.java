package de.metas.inoutcandidate.spi;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.springframework.stereotype.Service;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import lombok.NonNull;



@Service
public class ShipmentScheduleOrderDocFactory
{
	private final Map<Integer, Function<I_M_ShipmentSchedule, ShipmentScheduleOrderDoc>> providers = new HashMap<>();

	public void registerProvider(
			@NonNull final String tableName,
			@NonNull final Function<I_M_ShipmentSchedule, ShipmentScheduleOrderDoc> provider)
	{
		final int tableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);
		providers.put(tableId, provider);
	}

	public ShipmentScheduleOrderDoc createFor(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final Function<I_M_ShipmentSchedule, ShipmentScheduleOrderDoc> provider = providers.get(shipmentSchedule.getAD_Table_ID());
		if (provider == null)
		{
			throw new UnsupportedShipmentScheduleTableId(shipmentSchedule);
		}
		return provider.apply(shipmentSchedule);
	}

	public static final class UnsupportedShipmentScheduleTableId extends AdempiereException
	{
		private static final long serialVersionUID = 3430768273480532505L;

		public UnsupportedShipmentScheduleTableId(final I_M_ShipmentSchedule shipmentSchedule)
		{
			super("M_ShipmentSchedule=" + shipmentSchedule.toString() + " with AD_Table_ID=" + shipmentSchedule.getAD_Table_ID());
		}
	}
}

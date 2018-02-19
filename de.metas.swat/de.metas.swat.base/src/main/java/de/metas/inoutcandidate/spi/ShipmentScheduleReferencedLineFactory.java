package de.metas.inoutcandidate.spi;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import lombok.NonNull;

@Service
public class ShipmentScheduleReferencedLineFactory
{
	private final Map<String, ShipmentScheduleReferencedLineProvider> providers = new HashMap<>();

	@Autowired
	public void registerProviders(@NonNull final Collection<ShipmentScheduleReferencedLineProvider> providers)
	{
		for (final ShipmentScheduleReferencedLineProvider provider : providers)
		{
			this.providers.put(provider.getTableName(), provider);
		}
	}

	/**
	 * May not be {@code null}
	 *
	 * @param shipmentSchedule
	 * @return
	 * @throws UnsupportedShipmentScheduleTableId if no {@link ShipmentScheduleReferencedLineProvider} is available for the given {@code shipmentSchedule}'s {@code AD_Table_ID}.
	 */
	public ShipmentScheduleReferencedLine createFor(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final String tableName = Services.get(IADTableDAO.class).retrieveTableName(shipmentSchedule.getAD_Table_ID());

		final ShipmentScheduleReferencedLineProvider provider = providers.get(tableName);
		if (provider == null)
		{
			throw new UnsupportedShipmentScheduleTableId(shipmentSchedule, tableName);
		}
		return provider.provideFor(shipmentSchedule);
	}

	/**
	 * See {@link ShipmentScheduleReferencedLineFactory#createFor(I_M_ShipmentSchedule)}.
	 *
	 * @author metas-dev <dev@metasfresh.com>
	 *
	 */
	public static final class UnsupportedShipmentScheduleTableId extends AdempiereException
	{
		private static final long serialVersionUID = 3430768273480532505L;

		public UnsupportedShipmentScheduleTableId(final I_M_ShipmentSchedule shipmentSchedule, final String tableName)
		{
			super("Missing ShipmentScheduleOrderDocProvider for tableName='" + tableName + "'; AD_Table_ID=" + shipmentSchedule.getAD_Table_ID() + "; M_ShipmentSchedule=" + shipmentSchedule.toString());
		}
	}

	@VisibleForTesting
	ShipmentScheduleReferencedLineProvider getProviderForTableName(@NonNull final String tableName)
	{
		return providers.get(tableName);
	}
}

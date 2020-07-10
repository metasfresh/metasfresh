package de.metas.inoutcandidate.spi;

import java.util.Collection;
import java.util.Optional;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.ToString;

@Service
@ToString
public class ShipmentScheduleReferencedLineFactory
{
	private final ImmutableMap<String, ShipmentScheduleReferencedLineProvider> providers;

	public ShipmentScheduleReferencedLineFactory(
			@NonNull final Optional<Collection<ShipmentScheduleReferencedLineProvider>> providers)
	{
		this.providers = providers.isPresent()
				? Maps.uniqueIndex(providers.get(), ShipmentScheduleReferencedLineProvider::getTableName)
				: ImmutableMap.of();
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

		final ShipmentScheduleReferencedLineProvider provider = getProviderForTableNameOrNull(tableName);
		if (provider == null)
		{
			throw new UnsupportedShipmentScheduleTableId(shipmentSchedule, tableName);
		}
		return provider.provideFor(shipmentSchedule);
	}

	@VisibleForTesting
	ShipmentScheduleReferencedLineProvider getProviderForTableNameOrNull(@NonNull final String tableName)
	{
		return providers.get(tableName);
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
}

package de.metas.shipping;

import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPartnerId;
import de.metas.i18n.ITranslatableString;
import de.metas.organization.OrgId;
import de.metas.util.ISingletonService;
import org.adempiere.service.ClientId;
import org.compiere.model.I_M_Shipper;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author tsa
 *
 */
public interface IShipperDAO extends ISingletonService
{

	/**
	 * @return shipper; if no shippers found it will return an error message
	 */
	ShipperId getShipperIdByShipperPartnerId(BPartnerId shipperPartnerId);

	ShipperId getDefault(ClientId clientId);

	ITranslatableString getShipperName(ShipperId shipperId);

	I_M_Shipper getById(ShipperId id);

	Optional<ShipperId> getShipperIdByValue(String value, OrgId orgId);

	Map<ShipperId,I_M_Shipper> getByIds(Set<ShipperId> shipperIds);

	ImmutableMap<String,I_M_Shipper> getByInternalName(Set<String> internalNameSet);
}

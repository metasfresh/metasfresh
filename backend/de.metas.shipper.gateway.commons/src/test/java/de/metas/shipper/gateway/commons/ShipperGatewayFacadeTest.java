package de.metas.shipper.gateway.commons;

import de.metas.user.UserId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShipperGatewayFacadeTest
{
	/**
	 * A shipment with no contact has {@code M_Package.AD_User_ID == 0}. The resolved delivery-order
	 * receiver contact id must be {@code null} (i.e. "no contact"), NOT {@link UserId#SYSTEM} — otherwise
	 * the System user's name/phone/email are sent to the carrier as the shipment order's receiver contact.
	 */
	@Test
	void toDeliverToContactId_isNull_whenPackageHasNoContact()
	{
		assertThat(ShipperGatewayFacade.toDeliverToContactId(0)).isNull();
	}

	@Test
	void toDeliverToContactId_isTheUser_whenPackageHasRealContact()
	{
		assertThat(ShipperGatewayFacade.toDeliverToContactId(1234)).isEqualTo(UserId.ofRepoId(1234));
	}
}

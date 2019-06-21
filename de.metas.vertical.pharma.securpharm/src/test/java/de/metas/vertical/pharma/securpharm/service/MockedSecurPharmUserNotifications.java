package de.metas.vertical.pharma.securpharm.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;

import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.vertical.pharma.securpharm.actions.DecommissionResponse;
import de.metas.vertical.pharma.securpharm.actions.UndoDecommissionResponse;
import de.metas.vertical.pharma.securpharm.notifications.SecurPharmUserNotifications;
import de.metas.vertical.pharma.securpharm.product.SecurPharmProduct;
import de.metas.vertical.pharma.securpharm.product.SecurPharmProductId;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.securpharm
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class MockedSecurPharmUserNotifications implements SecurPharmUserNotifications
{
	private final HashMap<SecurPharmProductId, SecurPharmProduct> decodeAndVerifyErrors = new HashMap<>();
	private final HashMap<SecurPharmProductId, DecommissionResponse> decommissionErrors = new HashMap<>();
	private final HashMap<SecurPharmProductId, UndoDecommissionResponse> undoDecommissionErrors = new HashMap<>();

	@Override
	public void notifyProductDecodeAndVerifyError(final UserId responsibleId, final SecurPharmProduct product)
	{
		Check.assumeNotNull(product.getId(), "product.getId() is not null");
		decodeAndVerifyErrors.put(product.getId(), product);
	}

	@Override
	public void notifyDecommissionFailed(final UserId responsibleId, final DecommissionResponse response)
	{
		decommissionErrors.put(response.getProductId(), response);
	}

	@Override
	public void notifyUndoDecommissionFailed(final UserId responsibleId, final UndoDecommissionResponse response)
	{
		undoDecommissionErrors.put(response.getProductId(), response);
	}

	public void assertNoErrors()
	{
		assertThat(decodeAndVerifyErrors).as("no decode & verify errors").isEmpty();
		assertThat(decommissionErrors).as("no decommission errors").isEmpty();
		assertThat(undoDecommissionErrors).as("no undo-decommission errors").isEmpty();
	}

	public void assertProductDecodeAndVerifyError(@NonNull final SecurPharmProductId productId)
	{
		assertThat(decodeAndVerifyErrors)
				.as("" + productId + " was reported with decode & verify errors")
				.containsKey(productId);
	}

	public void assertDecommissionError(@NonNull final SecurPharmProductId productId)
	{
		assertThat(decommissionErrors)
				.as("" + productId + " was reported with decommission errors")
				.containsKey(productId);
	}

	public void assertUndoDecommissionError(@NonNull final SecurPharmProductId productId)
	{
		assertThat(undoDecommissionErrors)
				.as("" + productId + " was reported with undo-decommission errors")
				.containsKey(productId);
	}
}

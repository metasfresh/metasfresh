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
import lombok.Value;

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
	private final HashMap<SecurPharmProductId, ProductVerifyErrorNotification> lastVerifyErrorByProductId = new HashMap<>();
	private final HashMap<SecurPharmProductId, DecommissionErrorNotification> lastDecommissionErrorByProductId = new HashMap<>();
	private final HashMap<SecurPharmProductId, UndoDecommissionErrorNotification> lastUndoDecommissionErrorByProductId = new HashMap<>();

	@Override
	public void notifyProductDecodeAndVerifyError(@NonNull final UserId responsibleId, @NonNull final SecurPharmProduct product)
	{
		final SecurPharmProductId productId = product.getId();
		Check.assumeNotNull(productId, "product shall be saved: {}", product);

		lastVerifyErrorByProductId.put(productId, ProductVerifyErrorNotification.of(responsibleId, product));
	}

	@Override
	public void notifyDecommissionFailed(final UserId responsibleId, final DecommissionResponse response)
	{
		lastDecommissionErrorByProductId.put(response.getProductId(), DecommissionErrorNotification.of(responsibleId, response));
	}

	@Override
	public void notifyUndoDecommissionFailed(final UserId responsibleId, final UndoDecommissionResponse response)
	{
		lastUndoDecommissionErrorByProductId.put(response.getProductId(), UndoDecommissionErrorNotification.of(responsibleId, response));
	}

	public void assertNoErrors()
	{
		assertThat(lastVerifyErrorByProductId).as("no decode & verify errors").isEmpty();
		assertThat(lastDecommissionErrorByProductId).as("no decommission errors").isEmpty();
		assertThat(lastUndoDecommissionErrorByProductId).as("no undo-decommission errors").isEmpty();
	}

	public void assertProductDecodeAndVerifyError(
			@NonNull final SecurPharmProductId productId,
			@NonNull final UserId expectedUserId)
	{
		assertThat(lastVerifyErrorByProductId)
				.as("" + productId + " was reported with decode & verify errors")
				.containsKey(productId);

		assertThat(lastVerifyErrorByProductId.get(productId).getUserId())
				.isEqualTo(expectedUserId);
	}

	public void assertDecommissionError(
			@NonNull final SecurPharmProductId productId,
			@NonNull final UserId expectedUserId)
	{
		assertThat(lastDecommissionErrorByProductId)
				.as("" + productId + " was reported with decommission errors")
				.containsKey(productId);

		assertThat(lastDecommissionErrorByProductId.get(productId).getUserId())
				.isEqualTo(expectedUserId);
	}

	public void assertUndoDecommissionError(
			@NonNull final SecurPharmProductId productId,
			@NonNull final UserId expectedUserId)
	{
		assertThat(lastUndoDecommissionErrorByProductId)
				.as("" + productId + " was reported with undo-decommission errors")
				.containsKey(productId);

		assertThat(lastUndoDecommissionErrorByProductId.get(productId).getUserId())
				.isEqualTo(expectedUserId);
	}

	@Value(staticConstructor = "of")
	private static class ProductVerifyErrorNotification
	{
		@NonNull
		private UserId userId;
		@NonNull
		private SecurPharmProduct product;
	}

	@Value(staticConstructor = "of")
	private static class DecommissionErrorNotification
	{
		@NonNull
		private UserId userId;
		@NonNull
		private DecommissionResponse decommission;
	}

	@Value(staticConstructor = "of")
	private static class UndoDecommissionErrorNotification
	{
		@NonNull
		private UserId userId;
		@NonNull
		private UndoDecommissionResponse undoDecommission;
	}
}

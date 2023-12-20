/*
 * #%L
 * de-metas-camel-edi
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.edi.esb.ordersimport.ecosio;

import com.google.common.collect.ArrayListMultimap;
import de.metas.common.util.Check;
import de.metas.edi.esb.commons.route.notifyreplicationtrx.NotifyReplicationTrxRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Value
@Builder
public class EcosioOrdersRouteContext
{
	@NonNull
	String clientValue;

	@Nullable
	@NonFinal
	@Setter(AccessLevel.NONE)
	String currentReplicationTrxName;

	@NonNull
	ArrayListMultimap<String, TrxImportStatus> importedTrxName2TrxStatus = ArrayListMultimap.create();

	public void setCurrentReplicationTrx(@NonNull final String currentTrxName)
	{
		this.currentReplicationTrxName = currentTrxName;
	}

	public void setCurrentReplicationTrxStatus(@NonNull final EcosioOrdersRouteContext.TrxImportStatus currentTrxStatus)
	{
		importedTrxName2TrxStatus.put(currentReplicationTrxName, currentTrxStatus);
	}

	@NonNull
	public Optional<NotifyReplicationTrxRequest> getStatusRequestFor(@NonNull final String trxName)
	{
		final Collection<TrxImportStatus> progress = importedTrxName2TrxStatus.get(trxName);
		if (Check.isEmpty(progress))
		{
			return Optional.empty();
		}

		final boolean allNotOk = progress.stream().noneMatch(EcosioOrdersRouteContext.TrxImportStatus::isOk);
		if (allNotOk)
		{
			//dev-note: no update is required when there is no C_OLCand imported in this replication trx
			return Optional.empty();
		}

		final boolean allOk = progress.stream().allMatch(EcosioOrdersRouteContext.TrxImportStatus::isOk);
		if (allOk)
		{
			final NotifyReplicationTrxRequest finishedRequest = NotifyReplicationTrxRequest.finished()
					.clientValue(clientValue)
					.trxName(trxName)
					.build();

			return Optional.ofNullable(finishedRequest);

		}

		return Optional.of(NotifyReplicationTrxRequest.error(getErrorMessage(progress))
								   .clientValue(clientValue)
								   .trxName(trxName)
								   .build());
	}

	@NonNull
	private static String getErrorMessage(@NonNull final Collection<TrxImportStatus> progress)
	{
		return progress.stream()
				.map(TrxImportStatus::getErrorMessage)
				.filter(Check::isNotBlank)
				.collect(Collectors.joining("\n"));
	}

	@Value
	@Builder
	public static class TrxImportStatus
	{
		boolean ok;

		@Nullable
		String errorMessage;

		@NonNull
		public static EcosioOrdersRouteContext.TrxImportStatus ok()
		{
			return TrxImportStatus.builder()
					.ok(true)
					.build();
		}

		@NonNull
		public static EcosioOrdersRouteContext.TrxImportStatus error(@NonNull final String errorMessage)
		{
			return TrxImportStatus.builder()
					.ok(false)
					.errorMessage(errorMessage)
					.build();
		}
	}
}

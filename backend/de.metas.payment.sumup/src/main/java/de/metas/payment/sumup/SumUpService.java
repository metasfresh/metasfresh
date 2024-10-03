package de.metas.payment.sumup;

import de.metas.payment.sumup.client.SumUpClient;
import de.metas.payment.sumup.client.json.GetReadersResponse;
import de.metas.payment.sumup.client.json.PairReaderRequest;
import de.metas.payment.sumup.repository.SumUpConfigRepository;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SumUpService
{
	@NonNull private final SumUpClientFactory clientFactory;
	@NonNull private final SumUpConfigRepository configRepository;

	public void updateCardReadersFromRemote(@NonNull SumUpConfigId configId)
	{
		configRepository.updateById(configId, config -> {
			final GetReadersResponse remoteCardReaders = clientFactory.newClient(config).getCardReaders();
			return updateFromRemote(config, remoteCardReaders);
		});
	}

	private static SumUpConfig updateFromRemote(final SumUpConfig config, final GetReadersResponse remoteResponse)
	{
		final HashMap<SumUpCardReaderExternalId, SumUpCardReader> cardReadersByExternalId = config.getCardReaders()
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(SumUpCardReader::getExternalId));

		final ArrayList<SumUpCardReader> newCardReaders = new ArrayList<>();
		for (GetReadersResponse.Item remoteCardReader : remoteResponse.getItems())
		{
			SumUpCardReader cardReader = updateFromRemote(cardReadersByExternalId.get(remoteCardReader.getId()), remoteCardReader);
			newCardReaders.add(cardReader);
		}

		return config.toBuilder()
				.cardReaders(newCardReaders)
				.build();
	}

	private static SumUpCardReader updateFromRemote(@Nullable final SumUpCardReader cardReader, @NonNull final GetReadersResponse.Item remote)
	{
		final SumUpCardReader.SumUpCardReaderBuilder builder = cardReader != null
				? cardReader.toBuilder()
				: SumUpCardReader.builder().isActive(true);

		return builder
				.name(remote.getName())
				.externalId(remote.getId())
				.build();
	}

	public void pairCardReader(@NonNull SumUpConfigId configId, @NonNull String name, @NonNull String pairingCode)
	{
		configRepository.updateById(configId, config -> {
			final SumUpClient client = clientFactory.newClient(config);
			client.pairCardReader(PairReaderRequest.builder()
					.name(name)
					.pairing_code(pairingCode)
					.build());

			final GetReadersResponse remoteCardReaders = client.getCardReaders();
			return updateFromRemote(config, remoteCardReaders);
		});
	}

	public void deleteCardReaders(@NonNull SumUpConfigId configId, @NonNull Set<SumUpCardReaderExternalId> ids)
	{
		if (ids.isEmpty())
		{
			return;
		}

		configRepository.updateById(configId, config -> {
			final SumUpClient client = clientFactory.newClient(config);
			ids.forEach(client::deleteCardReader);

			final GetReadersResponse remoteCardReaders = client.getCardReaders();
			return updateFromRemote(config, remoteCardReaders);
		});
	}
}

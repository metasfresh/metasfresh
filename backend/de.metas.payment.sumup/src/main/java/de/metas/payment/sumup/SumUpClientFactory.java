package de.metas.payment.sumup;

import de.metas.error.IErrorManager;
import de.metas.payment.sumup.client.SumUpClient;
import de.metas.payment.sumup.repository.SumUpLogRepository;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SumUpClientFactory
{
	@NonNull private final IErrorManager errorManager = Services.get(IErrorManager.class);
	@NonNull private final SumUpLogRepository logRepository;

	public SumUpClient newClient(@NonNull final SumUpConfig config)
	{
		return SumUpClient.builder()
				.errorManager(errorManager)
				.logRepository(logRepository)
				.configId(config.getId())
				.apiKey(config.getApiKey())
				.merchantCode(config.getMerchantCode())
				.build();
	}
}

package de.metas.payment.sumup;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.error.IErrorManager;
import de.metas.payment.sumup.client.SumUpClient;
import de.metas.payment.sumup.repository.SumUpLogRepository;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SumUpClientFactory
{
	@NonNull private final IErrorManager errorManager = Services.get(IErrorManager.class);
	@NonNull private final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();
	@NonNull private final SumUpLogRepository logRepository;

	@Value("${sumup.baseUrl:https://api.sumup.com/v0.1}") private String baseUrl;

	public SumUpClient newClient(@NonNull final SumUpConfig config)
	{
		return SumUpClient.builder()
				.errorManager(errorManager)
				.logRepository(logRepository)
				.jsonObjectMapper(jsonObjectMapper)
				.baseUrl(baseUrl)
				//
				.configId(config.getId())
				.apiKey(config.getApiKey())
				.merchantCode(config.getMerchantCode())
				//
				.build();
	}
}

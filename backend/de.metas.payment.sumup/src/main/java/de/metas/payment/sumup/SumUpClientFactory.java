package de.metas.payment.sumup;

import de.metas.payment.sumup.client.SumUpClient;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class SumUpClientFactory
{
	public SumUpClient newClient(@NonNull final SumUpConfig config)
	{
		return SumUpClient.builder()
				.apiKey(config.getApiKey())
				.merchantCode(config.getMerchantCode())
				.build();
	}
}

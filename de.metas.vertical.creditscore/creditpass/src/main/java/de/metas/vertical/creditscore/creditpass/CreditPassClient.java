package de.metas.vertical.creditscore.creditpass;

/*
 * #%L
 * de.metas.vertical.creditscore.creditpass
 * %%
 * Copyright (C) 2018 metas GmbH
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

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
import de.metas.vertical.creditscore.base.spi.CreditScoreClient;
import de.metas.vertical.creditscore.base.spi.model.CreditScore;
import de.metas.vertical.creditscore.base.spi.model.CreditScoreRequestLogData;
import de.metas.vertical.creditscore.base.spi.model.ResultCode;
import de.metas.vertical.creditscore.base.spi.model.TransactionData;
import de.metas.vertical.creditscore.creditpass.mapper.RequestMapper;
import de.metas.vertical.creditscore.creditpass.model.CreditPassConfig;
import de.metas.vertical.creditscore.creditpass.model.CreditPassConfigPaymentRule;
import de.metas.vertical.creditscore.creditpass.model.CreditPassTransactionData;
import de.metas.vertical.creditscore.creditpass.model.schema.Request;
import de.metas.vertical.creditscore.creditpass.model.schema.Response;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

public class CreditPassClient implements CreditScoreClient
{
	private final transient Logger log = LogManager.getLogger(getClass());

	private final RestTemplate restTemplate;

	@Getter
	private final CreditPassConfig creditPassConfig;

	public CreditPassClient(@NonNull final RestTemplate restTemplate,
			@NonNull final CreditPassConfig creditPassConfig)
	{
		this.restTemplate = restTemplate;
		this.creditPassConfig = creditPassConfig;
	}

	@Override
	public CreditScore getCreditScore(@NonNull final TransactionData transactionData, @NonNull final String paymentRule) throws Exception
	{

		Request request = new RequestMapper().mapToRequest((CreditPassTransactionData)transactionData, creditPassConfig, paymentRule);
		Optional<CreditPassConfigPaymentRule> configPaymentRule = creditPassConfig.getCreditPassConfigPaymentRuleList().stream()
				.filter(configPr -> StringUtils.equals(configPr.getPaymentRule(), paymentRule)).findFirst();

		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(ImmutableList.of(MediaType.APPLICATION_XML));
		httpHeaders.setContentType(MediaType.APPLICATION_XML);

		final HttpEntity<Request> entity = new HttpEntity<>(request, httpHeaders);
		CreditScoreRequestLogData requestLogData = new CreditScoreRequestLogData();
		requestLogData.setRequestTime(LocalDateTime.now());
		requestLogData.setCustomerTransactionID(request.getCustomer().getCustomerTransactionId());
		XmlMapper xmlMapper = new XmlMapper();
		requestLogData.setRequestData(xmlMapper.writeValueAsString(request));
		try
		{
			final ResponseEntity<Response> result = restTemplate.exchange("/", HttpMethod.POST, entity, Response.class);
			Response response = result.getBody();
			requestLogData.setTransactionID(response.getProcess().getTransactionID());
			requestLogData.setResponseTime(LocalDateTime.now());
			requestLogData.setResponseData(xmlMapper.writeValueAsString(response));
			return CreditScore.builder()
					.requestLogData(requestLogData)
					.resultCode(ResultCode.fromCode(response.getProcess().getAnswerCode()))
					.resultText(response.getProcess().getAnswerText())
					.resultDetails(response.getProcess().getAnswerDetails())
					.paymentRule(paymentRule)
					.requestPrice(configPaymentRule.map(CreditPassConfigPaymentRule::getRequestPrice).orElse(null))
					.currency(configPaymentRule.map(CreditPassConfigPaymentRule::getRequestPriceCurrency).orElse(null))
					.build();
		}
		catch (final RestClientException e)
		{
			final Throwable cause = AdempiereException.extractCause(e);
			log.error(e.getLocalizedMessage(), cause);
			requestLogData.setResponseTime(LocalDateTime.now());
			return CreditScore.builder()
					.requestLogData(requestLogData)
					.resultCode(ResultCode.E)
					.resultCodeOverride(creditPassConfig.getResultCode())
					.resultText(CreditPassConstants.DEFAULT_RESULT_TEXT)
					.resultDetails(e.getMessage())
					.paymentRule(paymentRule)
					.requestPrice(configPaymentRule.map(CreditPassConfigPaymentRule::getRequestPrice).orElse(null))
					.currency(configPaymentRule.map(CreditPassConfigPaymentRule::getRequestPriceCurrency).orElse(null))
					.build();
		}

	}

}

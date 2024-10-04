package de.metas.payment.sumup.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.error.AdIssueId;
import de.metas.logging.LogManager;
import de.metas.payment.sumup.SumUpLogRequest;
import de.metas.payment.sumup.repository.model.I_SUMUP_API_Log;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

@Repository
public class SumUpLogRepository
{
	@NonNull private final Logger logger = LogManager.getLogger(SumUpLogRepository.class);
	@NonNull private final ObjectMapper jsonMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

	public void createLog(final SumUpLogRequest request)
	{
		try
		{
			final I_SUMUP_API_Log record = InterfaceWrapperHelper.newInstanceOutOfTrx(I_SUMUP_API_Log.class);
			record.setSUMUP_Config_ID(request.getConfigId().getRepoId());
			record.setSUMUP_merchant_code(request.getMerchantCode().getAsString());

			record.setMethod(request.getMethod().name());
			record.setRequestURI(request.getRequestURI());
			record.setRequestBody(toJson(request.getRequestBody()));

			if (request.getResponseCode() != null)
			{
				record.setResponseCode(request.getResponseCode().value());
			}
			record.setResponseBody(toJson(request.getResponseBody()));
			record.setAD_Issue_ID(AdIssueId.toRepoId(request.getAdIssueId()));
			InterfaceWrapperHelper.save(record);
		}
		catch (Exception ex)
		{
			logger.error("Failed saving log {}", request, ex);
		}
	}

	private String toJson(@Nullable final Object obj)
	{
		if (obj == null)
		{
			return null;
		}

		if (obj instanceof String)
		{
			return StringUtils.trimBlankToNull((String)obj);
		}

		try
		{
			return jsonMapper.writeValueAsString(obj);
		}
		catch (JsonProcessingException ex)
		{
			logger.error("Failed converting object to json: {}", obj, ex);
			return obj.toString();
		}
	}
}

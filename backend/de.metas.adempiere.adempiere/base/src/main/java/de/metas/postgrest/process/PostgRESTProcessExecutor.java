/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.postgrest.process;

import de.metas.common.util.FileUtil;
import de.metas.postgrest.client.GetRequest;
import de.metas.postgrest.client.PostgRESTClient;
import de.metas.postgrest.client.PostgRESTResponseFormat;
import de.metas.postgrest.config.PostgRESTConfig;
import de.metas.postgrest.config.PostgRESTConfigId;
import de.metas.postgrest.config.PostgRESTConfigRepository;
import de.metas.process.IADProcessDAO;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfo;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Process;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Makes an invocation to the postgresql-service.
 * Can be used as is or be subclassed if custom behavior is needed.
 * If invoked via WebUI, then the result is sent to the browser, similar to the behavior of a report-process.
 */
public class PostgRESTProcessExecutor extends JavaProcess
{
	private final PostgRESTClient postgRESTClient = SpringContextHolder.instance.getBean(PostgRESTClient.class);
	private final PostgRESTConfigRepository configRepository = SpringContextHolder.instance.getBean(PostgRESTConfigRepository.class);

	private final IExpressionFactory expressionFactory = Services.get(IExpressionFactory.class);
	private final IADProcessDAO processDAO = Services.get(IADProcessDAO.class);

	@Override
	protected final String doIt() throws Exception
	{
		try
		{
			final CustomPostgRESTParameters parameters = beforePostgRESTCall();

			performPostgRESTCallAndSetResult(parameters);

			return afterPostgRESTCall();
		}
		catch (final Exception e)
		{
			throw handleException(e);
		}
	}

	protected CustomPostgRESTParameters beforePostgRESTCall()
	{
		return CustomPostgRESTParameters.builder().storeJsonFile(false).build();
	}

	protected String afterPostgRESTCall()
	{
		return MSG_OK;
	}

	protected Exception handleException(final Exception e) {return e;}

	private void performPostgRESTCallAndSetResult(@NonNull final CustomPostgRESTParameters parameters) throws IOException
	{
		final ProcessInfo processInfo = getProcessInfo();

		final String jsonPathRaw = processInfo.getJsonPath()
				.orElseThrow(() -> new AdempiereException("JSONPath is missing!")
						.appendParametersToMessage()
						.setParameter("processInfo", processInfo));

		final PostgRESTConfig config = configRepository.getConfigFor(processInfo.getOrgId());
		addLog("Using S_PostgREST_Config_ID={}", PostgRESTConfigId.toRepoId(config.getId()));
		addLog("ProcessCalledFrom={}", processInfo.getProcessCalledFrom());

		final I_AD_Process processRecord = processDAO.getById(getProcessInfo().getAdProcessId());
		final PostgRESTResponseFormat responseFormat = PostgRESTResponseFormat.ofCode(processRecord.getPostgrestResponseFormat());

		final IStringExpression jsonPathExpression = expressionFactory.compile(prepareJSONPath(jsonPathRaw), IStringExpression.class);
		final String jsonPath = jsonPathExpression.evaluate(getEvalContext(), IExpressionEvaluator.OnVariableNotFound.Fail);

		final GetRequest getRequest = GetRequest
				.builder()
				.baseURL(createRequestURL(config, jsonPath))
				.responseFormat(responseFormat)
				.build();

		final String fileName = processInfo.getPinstanceId().getRepoId() + responseFormat.getFilenameExtension();
		final String fileNameForReportData = processRecord.getValue() + "_" + fileName;

		final Resource postgRESTResponse = postgRESTClient.performGet(getRequest);
		if (!postgRESTResponse.exists())
		{
			throw new AdempiereException("postgREST response does not exist: " + postgRESTResponse.getDescription())
					.appendParametersToMessage()
					.setParameter("getRequest", getRequest);
		}

		if (parameters.isStoreJsonFile())
		{
			final Path outputFilePath = FileUtil.createAndValidatePath(
					Paths.get(config.getResultDirectory()),
					Path.of(processRecord.getValue(), fileName));

			// store resource to disk
			Files.createDirectories(outputFilePath.getParent());
			try (final InputStream in = postgRESTResponse.getInputStream())
			{
				FileUtil.copy(in, outputFilePath.toFile());
			}
			addLog("Stored postgREST result to {}", outputFilePath);
			processInfo.getResult().setReportData(outputFilePath.toFile(), fileNameForReportData);
		}
		else
		{
			processInfo.getResult().setReportData(postgRESTResponse, fileNameForReportData, responseFormat.getContentType());
		}
	}

	@NonNull
	private static String createRequestURL(
			@NonNull final PostgRESTConfig config,
			@NonNull final String jsonPath)
	{
		final StringBuilder result = new StringBuilder(config.getBaseURL());
		if (!config.getBaseURL().endsWith("/") && !jsonPath.startsWith("/"))
		{
			result.append("/");
		}
		result.append(jsonPath);
		return result.toString();
	}

	private String prepareJSONPath(@NonNull final String rawJSONPath)
	{
		return StringUtils.prependIfNotStartingWith(
				rawJSONPath
						.replace("\n", "")
						.replace("\r", ""),
				"/");
	}

	private Evaluatee getEvalContext()
	{
		final List<Evaluatee> contexts = new ArrayList<>();

		contexts.add(Evaluatees.ofRangeAwareParams(getProcessInfo().getParameterAsIParams()));
		contexts.add(Evaluatees.ofCtx(Env.getCtx()));

		return Evaluatees.compose(contexts);
	}

	@Value
	@Builder
	protected static class CustomPostgRESTParameters
	{
		boolean storeJsonFile;
	}
}

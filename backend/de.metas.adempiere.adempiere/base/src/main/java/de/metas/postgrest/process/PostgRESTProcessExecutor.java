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

import de.metas.postgrest.client.GetRequest;
import de.metas.postgrest.client.PostgRESTClient;
import de.metas.postgrest.client.PostgRESTResponseFormat;
import de.metas.postgrest.config.PostgRESTConfig;
import de.metas.postgrest.config.PostgRESTConfigRepository;
import de.metas.process.IADProcessDAO;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfo;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;

import java.util.ArrayList;
import java.util.List;

public class PostgRESTProcessExecutor extends JavaProcess
{
	private final PostgRESTClient postgRESTClient = SpringContextHolder.instance.getBean(PostgRESTClient.class);
	private final PostgRESTConfigRepository configRepository = SpringContextHolder.instance.getBean(PostgRESTConfigRepository.class);
	private final IExpressionFactory expressionFactory = Services.get(IExpressionFactory.class);
	private final IADProcessDAO processDAO = Services.get(IADProcessDAO.class);

	@Override
	protected String doIt() throws Exception
	{
		final ProcessInfo processInfo = getProcessInfo();

		final boolean jsonPathMissing = !processInfo.getJsonPath().isPresent();

		if (jsonPathMissing)
		{
			throw new AdempiereException("JSONPath is missing!")
					.appendParametersToMessage()
					.setParameter("processInfo", processInfo);
		}

		final PostgRESTConfig config = configRepository.getConfigFor(processInfo.getOrgId());

		final PostgRESTResponseFormat responseFormat = PostgRESTResponseFormat.ofCode(processDAO.getById(getProcessInfo().getAdProcessId()).getPostgrestResponseFormat());

		final String rawJSONPath = processInfo.getJsonPath().get();
		final IStringExpression pathExpression = expressionFactory.compile(prepareJSONPath(rawJSONPath), IStringExpression.class);
		final String path = pathExpression.evaluate(getEvalContext(), IExpressionEvaluator.OnVariableNotFound.Fail);

		final GetRequest getRequest = GetRequest
				.builder()
				.baseURL(config.getBaseURL() + path)
				.responseFormat(responseFormat)
				.build();

		final String postgRESTResponse = postgRESTClient.performGet(getRequest);
		processInfo.getResult().setStringResult(postgRESTResponse, responseFormat.getContentType());

		return MSG_OK;
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

}

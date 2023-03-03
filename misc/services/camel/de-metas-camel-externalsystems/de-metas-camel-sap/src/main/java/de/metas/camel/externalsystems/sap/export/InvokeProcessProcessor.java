/*
 * #%L
 * de-metas-camel-sap
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

package de.metas.camel.externalsystems.sap.export;

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.common.v2.InvokeProcessCamelRequest;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.v2.process.request.JSONProcessParam;
import de.metas.common.rest_api.v2.process.request.RunProcessRequest;
import de.metas.common.util.Check;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class InvokeProcessProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange)
	{
		final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

		final String processValue = request.getParameters().get(ExternalSystemConstants.PARAM_PostGREST_AD_Process_Value);

		if (Check.isBlank(processValue))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_PostGREST_AD_Process_Value);
		}

		final String paramRecordIdName = request.getParameters().get(ExternalSystemConstants.PARAM_PostGREST_AD_Process_Param_Record_ID_NAME);

		if (Check.isBlank(paramRecordIdName))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_PostGREST_AD_Process_Param_Record_ID_NAME);
		}

		final String paramRecordId = request.getParameters().get(ExternalSystemConstants.PARAM_PostGREST_AD_Process_Param_Record_ID_VALUE);

		if (Check.isBlank(paramRecordId))
		{
			throw new RuntimeException("Missing mandatory param: " + ExternalSystemConstants.PARAM_PostGREST_AD_Process_Param_Record_ID_VALUE);
		}

		final InvokeProcessCamelRequest invokeProcessRequest = InvokeProcessCamelRequest.builder()
				.processValue(processValue)
				.runProcessRequest(RunProcessRequest.builder()
										   .processParameters(ImmutableList.of(JSONProcessParam.builder()
																					   .name(paramRecordIdName)
																					   .value(paramRecordId)
																					   .build()))
										   .build())
				.build();

		exchange.getIn().setBody(invokeProcessRequest);
	}
}

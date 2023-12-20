/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.externalsystem.process;

import de.metas.common.util.EmptyUtil;
import de.metas.externalsystem.ExternalSystemParentConfig;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.amazon.ExternalSystemAmazonConfig;
import de.metas.externalsystem.amazon.ExternalSystemAmazonConfigId;
import de.metas.externalsystem.model.I_ExternalSystem_Config_Amazon;
import de.metas.order.impl.DocTypeService;
import de.metas.payment.paymentterm.IPaymentTermRepository;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.Param;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

import java.util.HashMap;
import java.util.Map;

import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_BASE_PATH;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_CHILD_CONFIG_VALUE;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_CLIENT_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_CLIENT_SECRET;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_ORDER_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_ORDER_NO;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_UPDATED_AFTER_OVERRIDE;

public class InvokeAmazonAction extends InvokeExternalSystemProcess
{
	private final InvokeAmazonService invokeAmazonService = SpringContextHolder.instance.getBean(InvokeAmazonService.class);

	private final IPaymentTermRepository paymentTermRepository = Services.get(IPaymentTermRepository.class);
	private final DocTypeService docTypeService = SpringContextHolder.instance.getBean(DocTypeService.class);

	private static final String PARAM_ORDERNO = "OrderNo";
	@Param(parameterName = PARAM_ORDERNO)
	private String orderNo;

	private static final String PARAM_ORDERID = "OrderId";
	@Param(parameterName = PARAM_ORDERID)
	private String orderId;

	@Override
	protected IExternalSystemChildConfigId getExternalChildConfigId()
	{
		final int id;

		if (this.childConfigId > 0)
		{
			id = this.childConfigId;
		}
		else
		{
			id = externalSystemConfigDAO.getChildByParentIdAndType(ExternalSystemParentConfigId.ofRepoId(getRecord_ID()), getExternalSystemType())
					.get().getId().getRepoId();
		}

		return ExternalSystemAmazonConfigId.ofRepoId(id);
	}

	@Override
	protected Map<String, String> extractExternalSystemParameters(@NonNull final ExternalSystemParentConfig externalSystemParentConfig)
	{
		final ExternalSystemAmazonConfig amazonConfig = ExternalSystemAmazonConfig.cast(externalSystemParentConfig.getChildConfig());

		final Map<String, String> parameters = new HashMap<>();
		parameters.put(PARAM_BASE_PATH, amazonConfig.getBasePath());
		parameters.put("AccessKeyId", amazonConfig.getAccessKeyId());
		parameters.put("RefreshToken", amazonConfig.getRefreshToken());
		parameters.put("Region", amazonConfig.getRegionName());
		parameters.put("RoleArn", amazonConfig.getRoleArn());
		parameters.put(PARAM_CLIENT_ID, amazonConfig.getClientId());
		parameters.put(PARAM_CLIENT_SECRET, amazonConfig.getClientSecret());
		parameters.put("LwaEndpoint", amazonConfig.getLwaEndpoint());

		parameters.put("Active", Boolean.toString(amazonConfig.isActive()));
		parameters.put("Debug", Boolean.toString(amazonConfig.isDebugProtocol()));

		parameters.put(PARAM_CHILD_CONFIG_VALUE, amazonConfig.getValue());

		if (getSinceParameterValue() != null)
		{
			parameters.put(PARAM_UPDATED_AFTER_OVERRIDE, getSinceParameterValue().toInstant().toString());
		}

		if (EmptyUtil.isNotBlank(orderNo))
		{
			parameters.put(PARAM_ORDER_NO, orderNo);
		}

		if (EmptyUtil.isNotBlank(orderId))
		{
			parameters.put(PARAM_ORDER_ID, orderId);
		}

		return parameters;
	}

	@NonNull
	protected String getTabName()
	{
		return ExternalSystemType.Amazon.getName();
	}

	@Override
	protected long getSelectedRecordCount(final IProcessPreconditionsContext context)
	{
		return context.getSelectedIncludedRecords()
				.stream()
				.filter(recordRef -> I_ExternalSystem_Config_Amazon.Table_Name.equals(recordRef.getTableName()))
				.count();
	}

	@NonNull
	protected ExternalSystemType getExternalSystemType()
	{
		return ExternalSystemType.Amazon;
	}
}

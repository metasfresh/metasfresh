package de.metas.acct;

import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.api.TaxCorrectionType;
import de.metas.acct.api.impl.AcctSchemaDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_AcctSchema_Default;
import org.compiere.model.I_C_AcctSchema_GL;
import org.junit.Ignore;
import org.slf4j.Logger;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@Ignore
public class AcctSchemaTestHelper
{
	private static final Logger logger = LogManager.getLogger(AcctSchemaTestHelper.class);

	@Builder(builderMethodName = "newAcctSchema", builderClassName = "newAcctSchemaBuilder")
	private static AcctSchemaId createAcctSchema(
			@Nullable final CostingLevel costingLevel,
			@Nullable final CostingMethod costingMethod,
			@Nullable final CostTypeId costTypeId,
			@Nullable final CurrencyId currencyId)
	{
		final CurrencyId currencyIdEffective = currencyId != null
				? currencyId
				: PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_AcctSchema acctSchemaRecord = newInstance(I_C_AcctSchema.class);
		acctSchemaRecord.setName("Test AcctSchema");
		acctSchemaRecord.setC_Currency_ID(currencyId.getRepoId());
		acctSchemaRecord.setM_CostType_ID(costTypeId != null ? costTypeId.getRepoId() : 1);
		acctSchemaRecord.setCostingLevel(CoalesceUtil.coalesce(costingLevel, CostingLevel.Client).getCode());
		acctSchemaRecord.setCostingMethod(CoalesceUtil.coalesce(costingMethod, CostingMethod.StandardCosting).getCode());
		acctSchemaRecord.setSeparator("-");
		acctSchemaRecord.setTaxCorrectionType(TaxCorrectionType.NONE.getCode());
		saveRecord(acctSchemaRecord);

		final I_C_AcctSchema_GL acctSchemaGL = newInstance(I_C_AcctSchema_GL.class);
		acctSchemaGL.setC_AcctSchema_ID(acctSchemaRecord.getC_AcctSchema_ID());
		acctSchemaGL.setIntercompanyDueFrom_Acct(1);
		acctSchemaGL.setIntercompanyDueTo_Acct(1);
		acctSchemaGL.setIncomeSummary_Acct(1);
		acctSchemaGL.setRetainedEarning_Acct(1);
		acctSchemaGL.setPPVOffset_Acct(1);
		saveRecord(acctSchemaGL);

		final I_C_AcctSchema_Default acctSchemaDefault = newInstance(I_C_AcctSchema_Default.class);
		acctSchemaDefault.setC_AcctSchema_ID(acctSchemaRecord.getC_AcctSchema_ID());
		acctSchemaDefault.setRealizedGain_Acct(1);
		acctSchemaDefault.setRealizedLoss_Acct(1);
		acctSchemaDefault.setUnrealizedGain_Acct(1);
		acctSchemaDefault.setUnrealizedLoss_Acct(1);
		saveRecord(acctSchemaDefault);

		return AcctSchemaId.ofRepoId(acctSchemaRecord.getC_AcctSchema_ID());
	}

	public static void registerAcctSchemaDAOWhichAlwaysProvides(@NonNull final AcctSchemaId acctSchemaId)
	{
		if (Services.isAvailable(IAcctSchemaDAO.class))
		{
			final IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);
			throw new AdempiereException("Possible (test)developer error: registering a mocked service is not allowed"
					+ " when an actual service was already registered (any maybe used): " + acctSchemaDAO);
		}

		final AcctSchemaDAO_getAcctSchemaIdByClientAndOrg_ReturnFixedValue acctSchemaDAO = new AcctSchemaDAO_getAcctSchemaIdByClientAndOrg_ReturnFixedValue(acctSchemaId);
		Services.registerService(IAcctSchemaDAO.class, acctSchemaDAO);
		logger.info("Registered {}", acctSchemaDAO);
	}

	@ToString
	private static final class AcctSchemaDAO_getAcctSchemaIdByClientAndOrg_ReturnFixedValue extends AcctSchemaDAO
	{
		private final AcctSchemaId acctSchemaId;

		public AcctSchemaDAO_getAcctSchemaIdByClientAndOrg_ReturnFixedValue(@Nullable final AcctSchemaId acctSchemaId)
		{
			this.acctSchemaId = acctSchemaId;
		}

		@Override
		public AcctSchemaId getAcctSchemaIdByClientAndOrg(
				final ClientId clientId,
				final OrgId orgId)
		{
			logger.debug("Always returning acctSchemaId={} for clientId={} and orgId={}", acctSchemaId, clientId, orgId);
			return acctSchemaId;
		}

	}
}

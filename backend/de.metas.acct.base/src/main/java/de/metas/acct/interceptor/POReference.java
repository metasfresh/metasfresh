package de.metas.acct.interceptor;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.IFactAcctDAO;
import de.metas.acct.doc.AcctDocRegistry;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.AbstractModelInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.POInfo;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class POReference extends AbstractModelInterceptor
{
	// services
	private static final Logger logger = LogManager.getLogger(POReference.class);
	private final IFactAcctDAO factAcctDAO = Services.get(IFactAcctDAO.class);
	private final AcctDocRegistry acctDocRegistry;

	private static final String COLUMNNAME_POReference = I_Fact_Acct.COLUMNNAME_POReference;

	public POReference(final AcctDocRegistry acctDocRegistry) {this.acctDocRegistry = acctDocRegistry;}

	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		final Set<String> tableNamesToWatch = getTableNamesToWatch();
		tableNamesToWatch.forEach(tableName -> engine.addModelChange(tableName, this));
		logger.info("Watching POReference changes on {}", tableNamesToWatch);
	}

	private Set<String> getTableNamesToWatch()
	{
		return acctDocRegistry.getDocTableNames()
				.stream()
				.filter(tableName -> POInfo.getPOInfoNotNull(tableName).hasColumnName(COLUMNNAME_POReference))
				.collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public void onModelChange(final Object model, final ModelChangeType changeType)
	{
		if (changeType.isAfter() && changeType.isChange()
				&& InterfaceWrapperHelper.isValueChanged(model, COLUMNNAME_POReference))
		{
			final TableRecordReference recordRef = TableRecordReference.of(model);
			final String poReference = InterfaceWrapperHelper.getValueOptional(model, COLUMNNAME_POReference)
					.map(Object::toString)
					.orElse(null);

			factAcctDAO.updatePOReference(recordRef, poReference);
		}
	}
}

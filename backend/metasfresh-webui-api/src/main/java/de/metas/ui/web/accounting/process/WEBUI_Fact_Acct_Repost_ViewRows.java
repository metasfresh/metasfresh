package de.metas.ui.web.accounting.process;

import java.util.Set;

import de.metas.ui.web.accounting.filters.FactAcctFilterDescriptorsProviderFactory;
import lombok.NonNull;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.service.ClientId;
import org.compiere.model.I_Fact_Acct;

import com.google.common.collect.ImmutableSet;

import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.accounting.process.FactAcctRepostCommand.DocumentToRepost;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.IViewRow;
import de.metas.util.Services;

/*
 * #%L
 * metasfresh-webui-api
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

public class WEBUI_Fact_Acct_Repost_ViewRows extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	private final IADTableDAO adTablesRepo = Services.get(IADTableDAO.class);

	public static final String TABLENAME_RV_UnPosted = "RV_UnPosted";

	@Param(parameterName = "IsEnforcePosting", mandatory = true)
	private boolean forcePosting;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final Set<DocumentToRepost> documentsToRepost = getDocumentsToRepost();
		if (documentsToRepost.isEmpty())
		{
			return MSG_OK;
		}

		FactAcctRepostCommand.builder()
				.forcePosting(forcePosting)
				.documentsToRepost(documentsToRepost)
				.build()
				.execute();

		return MSG_OK;
	}

	private Set<DocumentToRepost> getDocumentsToRepost()
	{
		return getView().streamByIds(getSelectedRowIds())
				.map(this::extractDocumentToRepost)
				.distinct()
				.collect(ImmutableSet.toImmutableSet());
	}

	private DocumentToRepost extractDocumentToRepost(@NonNull final IViewRow row)
	{
		final String tableName = getTableName();
		if (I_Fact_Acct.Table_Name.equals(tableName)
				|| FactAcctFilterDescriptorsProviderFactory.FACT_ACCT_TRANSACTIONS_VIEW.equals(tableName)
				|| TABLENAME_RV_UnPosted.equals(tableName))
		{
			return extractDocumentToRepostFromTableAndRecordIdRow(row);
		}
		else
		{
			return extractDocumentToRepostFromRegularRow(row);
		}
	}

	private DocumentToRepost extractDocumentToRepostFromTableAndRecordIdRow(final IViewRow row)
	{
		final int adTableId = row.getFieldValueAsInt(I_Fact_Acct.COLUMNNAME_AD_Table_ID, -1);
		final int recordId = row.getFieldValueAsInt(I_Fact_Acct.COLUMNNAME_Record_ID, -1);
		final ClientId adClientId = ClientId.ofRepoId(row.getFieldValueAsInt(I_Fact_Acct.COLUMNNAME_AD_Client_ID, -1));
		return DocumentToRepost.builder()
				.adTableId(adTableId)
				.recordId(recordId)
				.clientId(adClientId)
				.build();
	}

	private DocumentToRepost extractDocumentToRepostFromRegularRow(final IViewRow row)
	{
		final int adTableId = adTablesRepo.retrieveTableId(getTableName());
		final int recordId = row.getId().toInt();

		final ClientId adClientId = ClientId.ofRepoId(row.getFieldValueAsInt(I_Fact_Acct.COLUMNNAME_AD_Client_ID, -1));

		return DocumentToRepost.builder()
				.adTableId(adTableId)
				.recordId(recordId)
				.clientId(adClientId)
				.build();
	}

	@Override
	protected void postProcess(final boolean success)
	{
		getView().invalidateSelection();
	}
}

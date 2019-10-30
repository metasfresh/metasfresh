package de.metas.ui.web.document.process;

import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_Fact_Acct;

import de.metas.acct.api.IFactAcctDAO;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.BooleanWithReason;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.document.process.FactAcctRepostCommand.DocumentToRepost;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.IDocumentFieldView;
import de.metas.util.Services;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class WEBUI_Fact_Acct_Repost_SingleDocument extends JavaProcess implements IProcessPrecondition
{
	private final IADTableDAO adTablesRepo = Services.get(IADTableDAO.class);
	private final IFactAcctDAO factAcctsRepo = Services.get(IFactAcctDAO.class);

	@Param(parameterName = "IsEnforcePosting", mandatory = true)
	private boolean forcePosting;

	private final DocumentCollection documentsCollection = SpringContextHolder.instance.getBean(DocumentCollection.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(IProcessPreconditionsContext context)
	{
		final String recordTableName = context.getTableName();
		if (I_Fact_Acct.Table_Name.equals(recordTableName))
		{
			return ProcessPreconditionsResolution.accept();
		}
		else
		{
			final AdWindowId adWindowId = context.getAdWindowId();
			final int recordId = context.getSingleSelectedRecordId();
			DocumentPath documentPath = DocumentPath.rootDocumentPath(adWindowId, recordId);
			final Document document = documentsCollection.getDocumentReadonly(documentPath);
			final BooleanWithReason allowPosting = checkAllowReposting(document);
			return allowPosting.isTrue()
					? ProcessPreconditionsResolution.accept()
					: ProcessPreconditionsResolution.reject(allowPosting.getReason()).toInternal();
		}
	}

	@Override
	protected String doIt()
	{
		final DocumentToRepost documentToRepost = getDocumentToRepost();
		FactAcctRepostCommand.builder()
				.forcePosting(forcePosting)
				.documentToRepost(documentToRepost)
				.build()
				.execute();

		return MSG_OK;
	}

	private DocumentToRepost getDocumentToRepost()
	{
		final String recordTableName = getTableName();
		if (I_Fact_Acct.Table_Name.equals(recordTableName))
		{
			final int factAcctId = getRecord_ID();
			final I_Fact_Acct factAcctRecord = factAcctsRepo.getById(factAcctId);
			return DocumentToRepost.builder()
					.adTableId(factAcctRecord.getAD_Table_ID())
					.recordId(factAcctRecord.getFact_Acct_ID())
					.clientId(ClientId.ofRepoId(factAcctRecord.getAD_Client_ID()))
					.build();
		}
		else
		{
			final DocumentPath documentPath = DocumentPath.rootDocumentPath(getProcessInfo().getAdWindowId(), getRecord_ID());
			final Document document = documentsCollection.getDocumentReadonly(documentPath);
			return extractDocumentToRepostFromSingleDocumentOrNull(document);
		}
	}

	private DocumentToRepost extractDocumentToRepostFromSingleDocumentOrNull(final Document document)
	{
		final BooleanWithReason allowReposting = checkAllowReposting(document);
		if (allowReposting.isFalse())
		{
			throw new AdempiereException(allowReposting.getReason());
		}

		final String tableName = document.getEntityDescriptor().getTableName();
		final int adTableId = adTablesRepo.retrieveTableId(tableName);
		final int recordId = document.getDocumentIdAsInt();
		final ClientId adClientId = document.getClientId();

		return DocumentToRepost.builder()
				.adTableId(adTableId)
				.recordId(recordId)
				.clientId(adClientId)
				.build();
	}

	private static BooleanWithReason checkAllowReposting(final Document document)
	{
		if (!document.hasField(WindowConstants.FIELDNAME_Posted))
		{
			return BooleanWithReason.falseBecause("document has no Posted field");
		}

		final DocStatus docStatus = getDocStatusOrNull(document);
		if (docStatus != null && !docStatus.isAccountable())
		{
			return BooleanWithReason.falseBecause("DocStatus is not accountable");
		}

		if (!isProcessed(document))
		{
			return BooleanWithReason.falseBecause("not processed");
		}

		return BooleanWithReason.TRUE;
	}

	private static DocStatus getDocStatusOrNull(final Document document)
	{
		final IDocumentFieldView docStatusField = document.getFieldViewOrNull(WindowConstants.FIELDNAME_DocStatus);
		if (docStatusField == null)
		{
			return null;
		}

		final String docStatusStr = docStatusField.getValueAs(String.class);
		return DocStatus.ofNullableCodeOrUnknown(docStatusStr);
	}

	private static boolean isProcessed(final Document document)
	{
		final IDocumentFieldView processedField = document.getFieldViewOrNull(WindowConstants.FIELDNAME_Processed);
		if (processedField == null)
		{
			return false;
		}

		return processedField.getValueAsBoolean();
	}
}

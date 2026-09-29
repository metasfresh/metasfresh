/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.costrevaluation.callout;

import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.IDocumentNoInfo;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.ui.spi.ITabCallout;
import org.adempiere.ad.ui.spi.TabCallout;
import org.compiere.model.I_M_CostRevaluation;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.Objects;

@Component
@Callout(I_M_CostRevaluation.class)
@TabCallout(I_M_CostRevaluation.class)
public class M_CostRevaluation implements ITabCallout
{
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	@PostConstruct
	public void postConstruct()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@Override
	public void onNew(@NonNull final ICalloutRecord calloutRecord)
	{
		final I_M_CostRevaluation costRevaluation = calloutRecord.getModel(I_M_CostRevaluation.class);
		setDocTypeId(costRevaluation);
	}

	@CalloutMethod(columnNames = I_M_CostRevaluation.COLUMNNAME_C_DocType_ID)
	public void onDocTypeChanged(@NonNull final I_M_CostRevaluation costRevaluation)
	{
		setDocumentNo(costRevaluation);
	}

	/**
	 * Keeps a defaulted EvaluationStartDate (i.e. equal to the previous DateAcct, or not set at all) in step when the user changes
	 * DateAcct on a not yet processed document, so that moving the posting date does not silently turn the revaluation retrospective.
	 * An EvaluationStartDate that the user set on its own is left untouched.
	 * <p>
	 * If the document already has lines, the moved EvaluationStartDate is then rejected on save by the model interceptor's
	 * "delete lines first" guard. That is intended: the lines were derived for the old revaluation window.
	 * <p>
	 * "Previous DateAcct" is {@link ICalloutField#getModelBeforeChanges(Class)}, i.e. the last saved value (the WebUI refreshes it
	 * after every save). So if DateAcct is edited more than once while the document cannot be saved yet (e.g. a new header with
	 * mandatory fields still empty), only the first edit moves EvaluationStartDate along; the user sees the value and can adjust it.
	 */
	@CalloutMethod(columnNames = I_M_CostRevaluation.COLUMNNAME_DateAcct)
	public void onDateAcctChanged(@NonNull final I_M_CostRevaluation costRevaluation, @NonNull final ICalloutField field)
	{
		final DocStatus docStatus = DocStatus.ofNullableCode(costRevaluation.getDocStatus());
		if (docStatus != null && !docStatus.isDraftedOrInProgress())
		{
			return;
		}

		final Timestamp dateAcct = costRevaluation.getDateAcct();
		if (dateAcct == null)
		{
			return;
		}

		final Timestamp evaluationStartDate = costRevaluation.getEvaluationStartDate();
		final Timestamp dateAcctOld = field.getModelBeforeChanges(I_M_CostRevaluation.class).getDateAcct();
		if (evaluationStartDate == null || Objects.equals(evaluationStartDate, dateAcctOld))
		{
			costRevaluation.setEvaluationStartDate(dateAcct);
		}
	}

	private void setDocTypeId(final I_M_CostRevaluation costRevaluation)
	{
		final DocTypeId docTypeId = docTypeDAO.getDocTypeIdOrNull(DocTypeQuery.builder()
				.docBaseType(DocBaseType.CostRevaluation)
				.docSubType(DocTypeQuery.DOCSUBTYPE_Any)
				.adClientId(costRevaluation.getAD_Client_ID())
				.adOrgId(costRevaluation.getAD_Org_ID())
				.build());
		if (docTypeId == null)
		{
			return;
		}

		costRevaluation.setC_DocType_ID(docTypeId.getRepoId());
	}

	private void setDocumentNo(final I_M_CostRevaluation costRevaluation)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(costRevaluation.getC_DocType_ID());
		if (docTypeId == null)
		{
			return;
		}

		final IDocumentNoInfo documentNoInfo = Services.get(IDocumentNoBuilderFactory.class)
				.createPreliminaryDocumentNoBuilder()
				.setNewDocType(docTypeDAO.getById(docTypeId))
				.setOldDocumentNo(costRevaluation.getDocumentNo())
				.setDocumentModel(costRevaluation)
				.buildOrNull();
		if (documentNoInfo != null && documentNoInfo.isDocNoControlled())
		{
			costRevaluation.setDocumentNo(documentNoInfo.getDocumentNo());
		}

	}
}



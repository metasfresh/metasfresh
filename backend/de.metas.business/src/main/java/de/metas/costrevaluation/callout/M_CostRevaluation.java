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

import de.metas.acct.api.AcctSchemaId;
import de.metas.costrevaluation.CostRevaluationId;
import de.metas.costrevaluation.CostRevaluationService;
import de.metas.costrevaluation.RevaluationSource;
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
import org.adempiere.service.ClientId;
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
	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	@NonNull private final CostRevaluationService costRevaluationService;

	public M_CostRevaluation(@NonNull final CostRevaluationService costRevaluationService)
	{
		this.costRevaluationService = costRevaluationService;
	}

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
	 * On a UI change of DateAcct in a draft, the EvaluationStartDate follows it. Only a CopyFromCostElement revaluation,
	 * where EvaluationStartDate is the cut-off date, keeps a hand-set value and moves only a defaulted one (unset, or equal to the last saved DateAcct).
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
		final RevaluationSource revaluationSource = RevaluationSource.ofNullableCode(costRevaluation.getRevaluationSource());
		if (revaluationSource == null || revaluationSource.isManual())
		{
			costRevaluation.setEvaluationStartDate(dateAcct);
			return;
		}

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

	/**
	 * On a UI switch of a draft to the Manual source, the EvaluationStartDate follows DateAcct at once (as on save),
	 * because only a CopyFromCostElement revaluation has a cut-off date of its own.
	 */
	@CalloutMethod(columnNames = I_M_CostRevaluation.COLUMNNAME_RevaluationSource)
	public void onRevaluationSourceChanged(@NonNull final I_M_CostRevaluation costRevaluation)
	{
		final DocStatus docStatus = DocStatus.ofNullableCode(costRevaluation.getDocStatus());
		if (docStatus != null && !docStatus.isDraftedOrInProgress())
		{
			return;
		}

		final RevaluationSource revaluationSource = RevaluationSource.ofNullableCode(costRevaluation.getRevaluationSource());
		if (revaluationSource == null || revaluationSource.isManual())
		{
			costRevaluation.setEvaluationStartDate(costRevaluation.getDateAcct());
		}
	}

	/**
	 * On a UI change of the accounting schema of a draft {@code Manual} revaluation without lines, the cost element is preset to the one of the schema's costing method.
	 * A CopyFromCostElement revaluation keeps its target element choice.
	 */
	@CalloutMethod(columnNames = I_M_CostRevaluation.COLUMNNAME_C_AcctSchema_ID)
	public void onAcctSchemaChanged(@NonNull final I_M_CostRevaluation costRevaluation)
	{
		final DocStatus docStatus = DocStatus.ofNullableCode(costRevaluation.getDocStatus());
		if (docStatus != null && !docStatus.isDraftedOrInProgress())
		{
			return;
		}

		final RevaluationSource revaluationSource = RevaluationSource.ofNullableCode(costRevaluation.getRevaluationSource());
		if (revaluationSource != null && !revaluationSource.isManual())
		{
			return;
		}

		final CostRevaluationId costRevaluationId = CostRevaluationId.ofRepoIdOrNull(costRevaluation.getM_CostRevaluation_ID());
		if (costRevaluationId != null && costRevaluationService.hasActiveLines(costRevaluationId))
		{
			return;
		}

		costRevaluationService.findPresetCostElement(ClientId.ofRepoId(costRevaluation.getAD_Client_ID()), AcctSchemaId.ofRepoIdOrNull(costRevaluation.getC_AcctSchema_ID()))
				.ifPresent(costElementId -> costRevaluation.setM_CostElement_ID(costElementId.getRepoId()));
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



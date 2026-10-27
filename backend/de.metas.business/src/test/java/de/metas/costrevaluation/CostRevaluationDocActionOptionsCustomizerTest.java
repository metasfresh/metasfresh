/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.costrevaluation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.ad_reference.ADReferenceService;
import de.metas.costing.CostElementId;
import de.metas.costing.CostElementType;
import de.metas.costing.CostingMethod;
import de.metas.costing.impl.CostDetailRepository;
import de.metas.costing.impl.CostDetailService;
import de.metas.costing.impl.CostElementRepository;
import de.metas.costing.impl.CostingService;
import de.metas.costing.impl.CurrentCostsRepository;
import de.metas.costing.methods.AverageInvoiceCostingMethodHandler;
import de.metas.costing.methods.CostingMethodHandlerUtils;
import de.metas.currency.CurrencyRepository;
import de.metas.document.engine.DocActionOptionsContext;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.impl.DocActionOptionsBL;
import de.metas.lang.SOTrx;
import de.metas.organization.OrgId;
import de.metas.security.RoleId;
import de.metas.security.UserRolePermissionsKey;
import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.ad.validationRule.impl.PlainValidationContext;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_CostDetail;
import org.compiere.model.I_M_CostElement;
import org.compiere.model.I_M_CostRevaluation;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * The status menu of a Completed cost revaluation: Close always; Void only while nothing is booked (Manual source, no revaluation M_CostDetail); never Re-Activate.
 */
@ExtendWith(AdempiereTestWatcher.class)
public class CostRevaluationDocActionOptionsCustomizerTest
{
	private CostRevaluationService costRevaluationService;
	private CostElementId costElementId;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final Properties ctx = Env.getCtx();
		Env.setClientId(ctx, ClientId.METASFRESH);

		final CostElementRepository costElementRepo = new CostElementRepository(ADReferenceService.newMocked());
		final CurrentCostsRepository currentCostsRepo = new CurrentCostsRepository(costElementRepo);
		final CostDetailRepository costDetailsRepo = new CostDetailRepository();
		final CostDetailService costDetailsService = new CostDetailService(costDetailsRepo, costElementRepo);
		final CostingMethodHandlerUtils handlerUtils = new CostingMethodHandlerUtils(new CurrencyRepository(), currentCostsRepo, costDetailsService);
		final CostingService costingService = new CostingService(
				handlerUtils,
				costDetailsService,
				costElementRepo,
				currentCostsRepo,
				ImmutableList.of(new AverageInvoiceCostingMethodHandler(handlerUtils)));

		costRevaluationService = new CostRevaluationService(new CostRevaluationRepository(), currentCostsRepo, costingService, costDetailsRepo, costElementRepo);

		final I_M_CostElement costElement = newInstance(I_M_CostElement.class);
		costElement.setAD_Org_ID(OrgId.ANY.getRepoId());
		costElement.setName("CostElement");
		costElement.setCostElementType(CostElementType.Material.getCode());
		costElement.setCostingMethod(CostingMethod.AveragePO.getCode());
		costElement.setIsCalculated(false);
		saveRecord(costElement);
		costElementId = CostElementId.ofRepoId(costElement.getM_CostElement_ID());
	}

	/** The single place that builds the customizer under test. */
	private CostRevaluationDocActionOptionsCustomizer newCustomizer()
	{
		return new CostRevaluationDocActionOptionsCustomizer(costRevaluationService);
	}

	private static DocActionOptionsContext optionsContext(final String docStatus, final IValidationContext validationContext)
	{
		return DocActionOptionsContext.builder()
				.userRolePermissionsKey(UserRolePermissionsKey.of(RoleId.SYSTEM, UserId.SYSTEM, ClientId.SYSTEM, LocalDate.parse("2024-03-06")))
				.tableName(I_M_CostRevaluation.Table_Name)
				.docStatus(docStatus)
				.soTrx(SOTrx.PURCHASE)
				.docActions(ImmutableSet.of(IDocument.ACTION_ReActivate, IDocument.ACTION_Close))
				.validationContext(validationContext)
				.build();
	}

	private I_M_CostRevaluation createRevaluation(@NonNull final RevaluationSource source)
	{
		final I_M_CostRevaluation record = newInstance(I_M_CostRevaluation.class);
		record.setAD_Org_ID(OrgId.ANY.getRepoId());
		record.setM_CostElement_ID(costElementId.getRepoId());
		record.setRevaluationSource(source.getCode());
		record.setDocStatus(DocStatus.Completed.getCode());
		record.setProcessed(true);
		saveRecord(record);
		return record;
	}

	private void bookRevaluation(@NonNull final I_M_CostRevaluation revaluation)
	{
		final I_M_CostDetail costDetail = newInstance(I_M_CostDetail.class);
		costDetail.setAD_Org_ID(OrgId.ANY.getRepoId());
		costDetail.setM_CostElement_ID(costElementId.getRepoId());
		costDetail.setM_CostRevaluation_ID(revaluation.getM_CostRevaluation_ID());
		saveRecord(costDetail);
	}

	private static PlainValidationContext validationContext(final I_M_CostRevaluation revaluation, final boolean posted)
	{
		final PlainValidationContext validationContext = new PlainValidationContext();
		validationContext.setValue(I_M_CostRevaluation.COLUMNNAME_M_CostRevaluation_ID, String.valueOf(revaluation.getM_CostRevaluation_ID()));
		validationContext.setValue(I_M_CostRevaluation.COLUMNNAME_RevaluationSource, revaluation.getRevaluationSource());
		validationContext.setValue(I_M_CostRevaluation.COLUMNNAME_Posted, posted ? "Y" : "N");
		return validationContext;
	}

	private DocActionOptionsContext customize(final String docStatus, final IValidationContext validationContext)
	{
		final DocActionOptionsContext optionsCtx = optionsContext(docStatus, validationContext);
		newCustomizer().customizeValidActions(optionsCtx);
		return optionsCtx;
	}

	@Test
	public void completed_manual_booked_offersOnlyClose()
	{
		final I_M_CostRevaluation revaluation = createRevaluation(RevaluationSource.Manual);
		bookRevaluation(revaluation);

		final DocActionOptionsContext optionsCtx = customize(IDocument.STATUS_Completed, validationContext(revaluation, true));

		assertThat(optionsCtx.getDocActions()).containsExactlyInAnyOrder(IDocument.ACTION_Close);
	}

	@Test
	public void completed_manual_notBooked_offersCloseAndVoid()
	{
		final I_M_CostRevaluation revaluation = createRevaluation(RevaluationSource.Manual);

		final DocActionOptionsContext optionsCtx = customize(IDocument.STATUS_Completed, validationContext(revaluation, false));

		assertThat(optionsCtx.getDocActions()).containsExactlyInAnyOrder(IDocument.ACTION_Close, IDocument.ACTION_Void);
	}

	@Test
	public void completed_copyFromCostElement_offersOnlyClose()
	{
		final I_M_CostRevaluation revaluation = createRevaluation(RevaluationSource.CopyFromCostElement);

		final DocActionOptionsContext optionsCtx = customize(IDocument.STATUS_Completed, validationContext(revaluation, false));

		assertThat(optionsCtx.getDocActions()).containsExactlyInAnyOrder(IDocument.ACTION_Close);
	}

	@Test
	public void completed_withoutIdAndSource_offersOnlyClose()
	{
		final DocActionOptionsContext optionsCtx = customize(IDocument.STATUS_Completed, new PlainValidationContext());

		assertThat(optionsCtx.getDocActions()).containsExactlyInAnyOrder(IDocument.ACTION_Close);
	}

	@Test
	public void drafted_keepsTheDefaultActions()
	{
		final I_M_CostRevaluation revaluation = createRevaluation(RevaluationSource.Manual);

		final DocActionOptionsContext optionsCtx = customize(IDocument.STATUS_Drafted, validationContext(revaluation, false));

		assertThat(optionsCtx.getDocActions()).containsExactlyInAnyOrder(IDocument.ACTION_ReActivate, IDocument.ACTION_Close);
	}

	@Test
	public void getParameters_containsIdSourceAndPosted()
	{
		assertThat(newCustomizer().getParameters()).containsExactlyInAnyOrder(
				I_M_CostRevaluation.COLUMNNAME_M_CostRevaluation_ID,
				I_M_CostRevaluation.COLUMNNAME_RevaluationSource,
				I_M_CostRevaluation.COLUMNNAME_Posted);
	}

	@Test
	public void docActionOptionsBL_requiresTheCustomizersParameters()
	{
		SpringContextHolder.registerJUnitBean(newCustomizer());

		assertThat(new DocActionOptionsBL().getRequiredParameters(I_M_CostRevaluation.Table_Name)).contains(
				I_M_CostRevaluation.COLUMNNAME_M_CostRevaluation_ID,
				I_M_CostRevaluation.COLUMNNAME_RevaluationSource,
				I_M_CostRevaluation.COLUMNNAME_Posted);
	}
}

/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.servicerepair.project.process;

import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.process.IProcessParametersCallout;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import de.metas.project.ProjectCategory;
import de.metas.project.ProjectId;
import de.metas.project.service.HUProjectService;
import de.metas.project.service.ProjectIssueRequest;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Project;

import java.math.BigDecimal;
import java.util.List;

public class C_Project_IssueSpareParts
		extends ServiceOrRepairProjectBasedProcess
		implements IProcessPrecondition, IProcessParametersCallout
{
	private final HUProjectService projectService = SpringContextHolder.instance.getBean(HUProjectService.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	private static final String PARAM_M_HU_ID = "M_HU_ID";
	@Param(parameterName = PARAM_M_HU_ID, mandatory = true)
	private HuId huId;

	@Param(parameterName = "M_Product_ID", mandatory = true)
	private ProductId productId;

	@Param(parameterName = "Qty", mandatory = true)
	private BigDecimal qtyBD;

	@Param(parameterName = "C_UOM_ID", mandatory = true)
	private UomId qtyUOMId;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		final ProjectId projectId = ProjectId.ofRepoIdOrNull(context.getSingleSelectedRecordId());
		if (projectId == null)
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		return checkCanIssue(projectId);
	}

	private ProcessPreconditionsResolution checkCanIssue(@NonNull final ProjectId projectId)
	{
		final I_C_Project project = projectService.getById(projectId);
		final ProjectCategory projectCategory = ProjectCategory.ofNullableCodeOrGeneral(project.getProjectCategory());
		if (!projectCategory.isServiceOrRepair())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not Service/Repair project");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	public void onParameterChanged(final String parameterName)
	{
		if (PARAM_M_HU_ID.equals(parameterName))
		{
			if (huId != null)
			{
				final I_M_HU hu = handlingUnitsBL.getById(huId);
				final List<IHUProductStorage> productStorages = handlingUnitsBL.getStorageFactory()
						.getStorage(hu)
						.getProductStorages();
				if (productStorages.isEmpty())
				{
					throw new AdempiereException("Empty HU");
				}

				final IHUProductStorage productStorage = productStorages.get(0);

				this.productId = productStorage.getProductId();

				final Quantity qty = productStorage.getQty();
				this.qtyBD = qty.toBigDecimal();
				this.qtyUOMId = qty.getUomId();
			}
		}
	}

	@Override
	protected String doIt()
	{
		final ProjectId projectId = ProjectId.ofRepoId(getRecord_ID());
		checkCanIssue(projectId).throwExceptionIfRejected();

		projectService.createProjectIssue(ProjectIssueRequest.builder()
				.projectId(projectId)
				.date(SystemTime.asZonedDateTime())
				.productId(productId)
				.qty(getQty())
				.huId(huId)
				.build());

		return MSG_OK;
	}

	private Quantity getQty()
	{
		return Quantity.of(qtyBD, uomDAO.getById(qtyUOMId));
	}
}

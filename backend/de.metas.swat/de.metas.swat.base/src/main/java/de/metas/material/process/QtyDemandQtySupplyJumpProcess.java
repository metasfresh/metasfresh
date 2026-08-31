/*
 * #%L
 * metasfresh-material-cockpit
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.material.process;

import de.metas.i18n.AdMessageKey;
import de.metas.material.cockpit.QtyDemandQtySupply;
import de.metas.material.cockpit.QtyDemandQtySupplyId;
import de.metas.material.cockpit.QtyDemandSupplyRepository;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;

import java.util.List;

/**
 * Common base for the Material Cockpit v2 "jump to ..." actions on {@code QtyDemand_QtySupply_V}.
 * <p>
 * Each subclass's {@link #checkPreconditionsApplicable(IProcessPreconditionsContext)} runs the same query as
 * {@link #doIt()}, as a cheap first-match probe, and rejects with {@link #MSG_NO_RELATED_DOCS_FOUND} when the probe
 * finds nothing, instead of silently opening an empty target grid.
 */
public abstract class QtyDemandQtySupplyJumpProcess extends JavaProcess implements IProcessPrecondition
{
	// NOTE: de.metas.swat.base cannot see de.metas.ui.web.view.SqlViewFactory (dependency runs the other
	// way), so the key is declared here rather than imported. Same AD_Message row (545635).
	protected static final AdMessageKey MSG_NO_RELATED_DOCS_FOUND = AdMessageKey.of("NO_RELATED_DOCS_FOUND");

	private final QtyDemandSupplyRepository demandSupplyRepository = SpringContextHolder.instance.getBean(QtyDemandSupplyRepository.class);

	@Override
	public final ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		if (!hasRecordsToOpen(getRow(context.getSingleSelectedRecordId())))
		{
			return ProcessPreconditionsResolution.reject(MSG_NO_RELATED_DOCS_FOUND);
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected final String doIt()
	{
		getResult().setRecordsToOpen(findRecordsToOpen(getRow(getRecord_ID())));
		return MSG_OK;
	}

	protected final QtyDemandQtySupply getRow(final int qtyDemandQtySupplyRepoId)
	{
		return demandSupplyRepository.getById(QtyDemandQtySupplyId.ofRepoId(qtyDemandQtySupplyRepoId));
	}

	/** True when {@link #findRecordsToOpen(QtyDemandQtySupply)} would return at least one record. Must use the SAME query. */
	protected abstract boolean hasRecordsToOpen(@NonNull QtyDemandQtySupply row);

	protected abstract List<TableRecordReference> findRecordsToOpen(@NonNull QtyDemandQtySupply row);
}

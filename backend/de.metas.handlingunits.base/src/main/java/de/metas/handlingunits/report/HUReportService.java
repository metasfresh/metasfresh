package de.metas.handlingunits.report;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuUnitType;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.process.api.HUProcessDescriptor;
import de.metas.handlingunits.process.api.IMHUProcessDAO;
import de.metas.handlingunits.report.labels.HULabelConfig;
import de.metas.handlingunits.report.labels.HULabelConfigQuery;
import de.metas.handlingunits.report.labels.HULabelService;
import de.metas.handlingunits.report.labels.HULabelSourceDocType;
import de.metas.i18n.ExplainedOptional;
import de.metas.logging.LogManager;
import de.metas.process.AdProcessId;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * This service facade offers useful methods around to handling unit related jasper report processed.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Service
public class HUReportService
{
	private static final Logger logger = LogManager.getLogger(HUReportService.class);
	private final HULabelService huLabelService;

	public HUReportService(
			@NonNull final HULabelService huLabelService)
	{
		this.huLabelService = huLabelService;
	}

	/**
	 * @return those HUs from the given {@code huToReport} what match the given {@code adProcessId}'s {@link HUProcessDescriptor}.
	 * If there is no {@link HUProcessDescriptor}, then it returns an empty list.
	 */
	public List<HUToReport> getHUsToProcess(
			@NonNull final HUToReport huToReport,
			@NonNull final AdProcessId adProcessId)
	{
		final IMHUProcessDAO huProcessDAO = Services.get(IMHUProcessDAO.class);
		final HUProcessDescriptor huProcessDescriptor = huProcessDAO.getByProcessIdOrNull(adProcessId);
		if (huProcessDescriptor == null)
		{
			return ImmutableList.of();
		}

		return huToReport.streamRecursively()
				.filter(currentHU -> currentHU.isTopLevel() || !huProcessDescriptor.isAcceptOnlyTopLevelHUs()) // if acceptOnlyTopLevelHUs then only accept topLevel-HUs
				.filter(currentHU -> huProcessDescriptor.appliesToHUUnitType(currentHU.getHUUnitType()))
				.collect(ImmutableList.toImmutableList());
	}

	public List<HUToReport> getHUsToProcess(@NonNull final Set<HUToReport> husToCheck)
	{
		if (husToCheck.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<HUToReport> tuHUs = new ArrayList<>();
		final List<HUToReport> luHUs = new ArrayList<>();
		final List<HUToReport> cuHUs = new ArrayList<>();
		for (final HUToReport hu : husToCheck)
		{
			final HuUnitType huUnitType = hu.getHUUnitType();

			if (HuUnitType.VHU.equals(huUnitType))
			{
				cuHUs.add(hu);
			}
			else if (HuUnitType.LU.equals(huUnitType))
			{
				luHUs.add(hu);
			}
			else if (HuUnitType.TU.equals(huUnitType))
			{
				tuHUs.add(hu);
			}
		}

		final String huUnitTypeToReport;
		if (!tuHUs.isEmpty())
		{
			huUnitTypeToReport = X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit;
		}
		else if (!luHUs.isEmpty())
		{
			huUnitTypeToReport = X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit;
		}
		else
		{
			huUnitTypeToReport = X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI;
		}

		if (!luHUs.isEmpty() || !tuHUs.isEmpty() || !cuHUs.isEmpty())
		{
			return extractHUsToProcess(huUnitTypeToReport, luHUs, tuHUs, cuHUs);
		}
		else
		{
			return ImmutableList.of();
		}
	}

	/**
	 * In case at least one TU was selected, we will deliver the processes for TUs.
	 * <p>
	 * This will happen even though we have, for instance, just 1 TU and some LUs selected.
	 * <p>
	 * The HUs to have the processes applied will be the 1 TU and the included TUs of the selected LUs
	 */
	private static List<HUToReport> extractHUsToProcess(final String huUnitType, final List<HUToReport> luHUs, final List<HUToReport> tuHUs, final List<HUToReport> cuHUs)
	{
		// In case the unit type is Virtual PI we don't have to return anything, since we don't have processes for virtual PIs
		if (X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI.equals(huUnitType))
		{
			return cuHUs;
		}

		// In case the unit type is LU we just have to process the LUs
		else if (X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit.equals(huUnitType))
		{
			return luHUs;
		}

		// In case the unit type is TU we have 2 possibilities:
		else
		{
			// In case there are no selected LUs, simply return the TUs
			if (luHUs.isEmpty())
			{
				return tuHUs;
			}
			// if this point is reached, it means we have both TUs and LUs selected
			else
			{
				final ImmutableList.Builder<HUToReport> husToProcess = ImmutableList.builder();

				// first, add all the selected TUs
				husToProcess.addAll(tuHUs);

				for (final HUToReport lu : luHUs)
				{
					final List<HUToReport> includedHUs = lu.getIncludedHUs();
					husToProcess.addAll(includedHUs);
				}

				return husToProcess.build();
			}
		}
	}

	/**
	 * @param onlyIfAutoPrintIsEnabled if {@code false}, then do the printing even if auto-printing is disabled.
	 */
	public void printPickingLabel(@Nullable final HUToReport huToReport, final boolean onlyIfAutoPrintIsEnabled)
	{
		final ILoggable loggable = Loggables.withLogger(logger, Level.INFO);

		if (huToReport == null)
		{
			loggable.addLog("Param 'huToReport'==null; nothing to do");
			return;
		}

		final HULabelConfig huLabelConfig = huLabelService.getFirstMatching(HULabelConfigQuery.builder()
						.sourceDocType(HULabelSourceDocType.Picking)
						.huUnitType(huToReport.getHUUnitType())
						.bpartnerId(huToReport.getBPartnerId())
						.build())
				.ifAbsent(msg -> loggable.addLog(msg.getDefaultValue()))
				.orElse(null);
		if (huLabelConfig == null)
		{
			return;
		}

		if (onlyIfAutoPrintIsEnabled && !huLabelConfig.isAutoPrint())
		{
			loggable.addLog("Auto printing receipt labels is not enabled via SysConfig; nothing to do");
			return;
		}

		if (!huToReport.isTopLevel())
		{
			loggable.addLog("We only print top level HUs; nothing to do; huToReport={}", huToReport);
			return;
		}

		final AdProcessId adProcessId = huLabelConfig.getPrintFormatProcessId();
		final List<HUToReport> husToProcess = getHUsToProcess(huToReport, adProcessId);
		if (husToProcess.isEmpty())
		{
			loggable.addLog("The selected hu does not match AD_Process_ID={}; nothing to do; huToReport={}", adProcessId, huToReport);
			return;
		}

		final int copies = huLabelConfig.getAutoPrintCopies();

		loggable.addLog("Going to invoke HUReportExecutor to run AD_Process_ID={} with copies={} on husToProcess={}", adProcessId, copies, husToProcess);

		HUReportExecutor.newInstance()
				.numberOfCopies(copies)
				.executeHUReportAfterCommit(adProcessId, husToProcess);
	}

	public ExplainedOptional<HULabelConfig> getLabelConfig(final HULabelConfigQuery query)
	{
		return huLabelService.getFirstMatching(query);
	}
}

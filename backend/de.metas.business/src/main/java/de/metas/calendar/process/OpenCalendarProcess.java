package de.metas.calendar.process;

import de.metas.bpartner.BPartnerId;
import de.metas.calendar.CalendarResourceId;
import de.metas.calendar.simulation.SimulationPlanId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult.CalendarToOpen;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ResourceId;
import de.metas.project.ProjectId;
import de.metas.resource.ResourceGroupId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_C_SimulationPlan;
import org.compiere.model.I_S_Resource;
import org.compiere.model.I_S_Resource_Group;

import java.util.Optional;

public class OpenCalendarProcess extends JavaProcess implements IProcessPrecondition
{
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isSingleSelection())
		{
			final boolean hasCalendarToOpen = getCalendarToOpen(context.getTableName(), context.getSingleSelectedRecordId()).isPresent();
			return hasCalendarToOpen ? ProcessPreconditionsResolution.accept() : ProcessPreconditionsResolution.rejectWithInternalReason("No calendar to open");
		}
		else
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}
	}

	@Override
	protected String doIt()
	{
		final CalendarToOpen calendarToOpen = getCalendarToOpen(getTableName(), getRecord_ID())
				.orElseThrow(() -> new AdempiereException("No calendar found"));

		getResult().setCalendarToOpen(calendarToOpen);

		return MSG_OK;
	}

	private static Optional<CalendarToOpen> getCalendarToOpen(final String tableName, final int recordId)
	{
		if (I_C_SimulationPlan.Table_Name.equals(tableName))
		{
			final SimulationPlanId simulationId = SimulationPlanId.ofRepoIdOrNull(recordId);
			if (simulationId == null)
			{
				return Optional.empty();
			}

			return Optional.of(CalendarToOpen.builder().simulationId(String.valueOf(simulationId.getRepoId())).build());
		}
		else if (I_C_Project.Table_Name.equals(tableName))
		{
			final ProjectId projectId = ProjectId.ofRepoIdOrNull(recordId);
			if (projectId == null)
			{
				return Optional.empty();
			}

			return Optional.of(CalendarToOpen.builder().projectId(String.valueOf(projectId.getRepoId())).build());
		}
		else if (I_S_Resource.Table_Name.equals(tableName))
		{
			final ResourceId resourceId = ResourceId.ofRepoIdOrNull(recordId);
			if (resourceId == null)
			{
				return Optional.empty();
			}

			final CalendarResourceId calendarResourceId = CalendarResourceId.ofRepoId(resourceId);

			return Optional.of(CalendarToOpen.builder().calendarResourceId(calendarResourceId.getAsString()).build());
		}
		else if (I_S_Resource_Group.Table_Name.equals(tableName))
		{
			final ResourceGroupId resourceGroupId = ResourceGroupId.ofRepoIdOrNull(recordId);
			if (resourceGroupId == null)
			{
				return Optional.empty();
			}

			final CalendarResourceId calendarResourceId = CalendarResourceId.ofRepoId(resourceGroupId);

			return Optional.of(CalendarToOpen.builder().calendarResourceId(calendarResourceId.getAsString()).build());
		}
		else if (I_C_BPartner.Table_Name.equals(tableName))
		{
			final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(recordId);
			if (bpartnerId == null)
			{
				return Optional.empty();
			}

			return Optional.of(CalendarToOpen.builder().customerId(bpartnerId).build());
		}
		else
		{
			throw new AdempiereException("Unsupported table: " + tableName);
		}
	}
}

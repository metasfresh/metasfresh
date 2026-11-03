package de.metas.handlingunits.rest_api.createfromlabel;

import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.workplace.Workplace;
import de.metas.workplace.WorkplaceService;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Locator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The target location the "New HU" preview proposes: (1) the user's workplace pick-from location, else (2) the
 * default location of the workplace's warehouse, else (3) the default location of the organisation's default warehouse; none when no
 * rule finds one (OK stays disabled until one is scanned).
 * <p>
 * <b>Read only</b>: the proposal runs on every scan, including scans the user cancels, so it must never create a location.
 * That is why it does not call {@code IWarehouseBL#getOrCreateDefaultLocatorId} or
 * {@code WorkplaceService#getPickFromLocatorIdOrWarehouseDefault} (both can insert an {@code M_Locator}), nor
 * {@code IOrgDAO#getOrgWarehouseId} (throws for an organisation without an {@code AD_OrgInfo} row; {@code IOrgDAO#getOrgWarehouseIdIfExists} is used instead).
 */
@Component
public class LabelTargetLocatorResolver
{
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final WorkplaceService workplaceService;

	public LabelTargetLocatorResolver(@NonNull final WorkplaceService workplaceService)
	{
		this.workplaceService = workplaceService;
	}

	@NonNull
	public Optional<LocatorId> resolve(@NonNull final UserId userId, @NonNull final OrgId orgId)
	{
		final Workplace workplace = workplaceService.getWorkplaceByUserId(userId).orElse(null);
		if (workplace != null)
		{
			if (workplace.getPickFromLocatorId() != null)
			{
				return Optional.of(workplace.getPickFromLocatorId());
			}

			final Optional<LocatorId> workplaceWarehouseLocator = getDefaultLocatorId(workplace.getWarehouseId());
			if (workplaceWarehouseLocator.isPresent())
			{
				return workplaceWarehouseLocator;
			}
		}

		return orgDAO.getOrgWarehouseIdIfExists(orgId).flatMap(this::getDefaultLocatorId);
	}

	/** the active location marked as default, else the first active one, else none (never creates one) */
	private Optional<LocatorId> getDefaultLocatorId(@NonNull final WarehouseId warehouseId)
	{
		final List<I_M_Locator> activeLocators = warehouseDAO.getLocators(warehouseId).stream()
				.filter(I_M_Locator::isActive)
				.collect(Collectors.toList());

		final I_M_Locator locator = activeLocators.stream()
				.filter(I_M_Locator::isDefault)
				.findFirst()
				.orElse(activeLocators.isEmpty() ? null : activeLocators.get(0));
		return Optional.ofNullable(locator != null ? LocatorId.ofRepoId(warehouseId, locator.getM_Locator_ID()) : null);
	}
}

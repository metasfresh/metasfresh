package de.metas.handlingunits.rest_api.createfromlabel;

import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.workplace.Workplace;
import de.metas.workplace.WorkplaceService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Locator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The target location the "New HU" preview proposes: (1) the user's workplace pick-from location, if it is usable (see {@link LabelLocatorRules}), else (2) the
 * default location of the workplace's warehouse, else (3) the default location of the organisation's default warehouse; none when no
 * rule finds one (OK stays disabled until one is scanned).
 * <p>
 * <b>Read only</b>: the proposal runs on every scan, including scans the user cancels, so it must never create a location.
 * That is why it does not call {@code IWarehouseBL#getOrCreateDefaultLocatorId} or
 * {@code WorkplaceService#getPickFromLocatorIdOrWarehouseDefault} (both can insert an {@code M_Locator}), nor
 * {@code IOrgDAO#getOrgWarehouseId} (throws for an organisation without an {@code AD_OrgInfo} row; {@code IOrgDAO#getOrgWarehouseIdIfExists} is used instead).
 */
@RequiredArgsConstructor
public class LabelTargetLocatorResolver
{
	@NonNull private final WorkplaceService workplaceService;
	@NonNull private final IWarehouseDAO warehouseDAO;
	@NonNull private final IOrgDAO orgDAO;

	@NonNull
	public Optional<LocatorId> resolve(@NonNull final UserId userId, @NonNull final OrgId orgId, @NonNull final ClientId clientId)
	{
		final Workplace workplace = workplaceService.getWorkplaceByUserId(userId).orElse(null);
		if (workplace != null)
		{
			// a pick-from location the create step would refuse is not proposed
			final LocatorId pickFromLocatorId = workplace.getPickFromLocatorId();
			if (pickFromLocatorId != null && LabelLocatorRules.isUsable(warehouseDAO.getLocatorById(pickFromLocatorId), pickFromLocatorId, clientId))
			{
				return Optional.of(pickFromLocatorId);
			}

			final Optional<LocatorId> workplaceWarehouseLocator = getDefaultLocatorId(workplace.getWarehouseId(), clientId);
			if (workplaceWarehouseLocator.isPresent())
			{
				return workplaceWarehouseLocator;
			}
		}

		return orgDAO.getOrgWarehouseIdIfExists(orgId).flatMap(warehouseId -> getDefaultLocatorId(warehouseId, clientId));
	}

	/** the usable location (see {@link LabelLocatorRules}) marked as default, else the first usable one, else none (never creates one) */
	private Optional<LocatorId> getDefaultLocatorId(@NonNull final WarehouseId warehouseId, @NonNull final ClientId clientId)
	{
		final List<I_M_Locator> usableLocators = warehouseDAO.getLocators(warehouseId).stream()
				.filter(locator -> LabelLocatorRules.isUsable(locator, LocatorId.ofRepoId(warehouseId, locator.getM_Locator_ID()), clientId))
				.collect(Collectors.toList());

		final I_M_Locator locator = usableLocators.stream()
				.filter(I_M_Locator::isDefault)
				.findFirst()
				.orElse(usableLocators.isEmpty() ? null : usableLocators.get(0));
		return Optional.ofNullable(locator != null ? LocatorId.ofRepoId(warehouseId, locator.getM_Locator_ID()) : null);
	}
}

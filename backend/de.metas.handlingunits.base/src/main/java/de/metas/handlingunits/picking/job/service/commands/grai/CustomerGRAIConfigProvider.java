package de.metas.handlingunits.picking.job.service.commands.grai;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.handlingunits.grai.GRAIRequired;
import lombok.Builder;
import lombok.NonNull;
import org.compiere.model.I_C_BPartner;

import java.util.HashMap;

class CustomerGRAIConfigProvider
{
	@NonNull private final IBPartnerDAO bpartnerDAO;

	private final HashMap<BPartnerId, GRAIRequired> graiRequiredByCustomerId = new HashMap<>();

	@Builder
	private CustomerGRAIConfigProvider(@NonNull final IBPartnerDAO bpartnerDAO)
	{
		this.bpartnerDAO = bpartnerDAO;
	}

	@NonNull
	public GRAIRequired getGRAIRequired(@NonNull final BPartnerId customerId)
	{
		return graiRequiredByCustomerId.computeIfAbsent(customerId, this::computeGRAIRequired);
	}

	@NonNull
	private GRAIRequired computeGRAIRequired(@NonNull final BPartnerId customerId)
	{
		final I_C_BPartner bpartner = bpartnerDAO.getById(customerId);
		return GRAIRequired.optionalOfNullableCode(bpartner.getGRAIRequired()).orElse(GRAIRequired.No);
	}

}

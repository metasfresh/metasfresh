package de.metas.vertical.pharma;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import de.metas.bpartner.BPartnerId;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.product.ProductId;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Service
public class PharmaService
{
	private final static String MSG_NoPrescriptionPermission = "de.metas.vertical.pharma.PharmaService.NoPrescriptionPermission";

	private final PharmaBPartnerRepository pharmaBPartnerRepo;
	private final PharmaProductRepository pharmaProductRepo;

	public PharmaService(@NonNull PharmaBPartnerRepository pharmaBPartnerRepo,
			@NonNull PharmaProductRepository pharmaProductRepo)
	{
		this.pharmaBPartnerRepo = pharmaBPartnerRepo;
		this.pharmaProductRepo = pharmaProductRepo;
	}

	public void evaluatePrescriptionPermission(final BPartnerId bpartnerId, final ProductId productId)
	{

		final PharmaBPartner partner = pharmaBPartnerRepo.getById(bpartnerId);

		final boolean hasPermissions = partner.isHasPermissions();

		if (hasPermissions)
		{
			return;
		}

		final PharmaProduct pharmaProduct = pharmaProductRepo.getById(productId);

		final boolean isPrescription = pharmaProduct.isPrescription();

		if (isPrescription)
		{
			final ITranslatableString noPermissionMessage = Services.get(IMsgBL.class).getTranslatableMsgText(MSG_NoPrescriptionPermission,
					pharmaProduct.getValue(),
					partner.getName(),
					partner.getShipmentPermissionPharma());

			throw new AdempiereException(noPermissionMessage.translate(Env.getAD_Language()));
		}
	}
}

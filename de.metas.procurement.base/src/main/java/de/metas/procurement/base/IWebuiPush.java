package de.metas.procurement.base;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;

import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.procurement.base.model.I_PMM_Product;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
/**
 * Service which allows us to PUSH from metasfresh server to webui server.
 *
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
public interface IWebuiPush extends ISingletonService
{
	/**
	 * @return true if the webui connection is available
	 */
	boolean checkAvailable();

	/**
	 * Push bpartner (without pushing the bpartner's contracts).
	 * 
	 * @param bpartner
	 */
	void pushBPartnerWithoutContracts(I_C_BPartner bpartner);

	void pushBPartnerForContact(I_AD_User contact);

	void pushBPartnerForContract(I_C_Flatrate_Term contract);

	void pushProduct(I_PMM_Product pmmProduct);

}

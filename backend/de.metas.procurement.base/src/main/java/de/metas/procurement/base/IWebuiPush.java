package de.metas.procurement.base;

import de.metas.common.procurement.sync.protocol.dto.SyncRfQ;
import de.metas.common.procurement.sync.protocol.dto.SyncRfQCloseEvent;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.procurement.base.model.I_PMM_Product;
import de.metas.util.ISingletonService;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;

import javax.annotation.Nullable;
import java.util.List;

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
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IWebuiPush extends ISingletonService
{
	IAutoCloseable disable();

	/**
	 * Push bpartner (without pushing the bpartner's contracts).
	 */
	void pushBPartnerAndUsers(I_C_BPartner bpartner);

	void pushBPartnerForContact(I_AD_User contact);

	void pushBPartnerForContract(I_C_Flatrate_Term contract);

	/**
	 * Pushes/synchronizes the given <code>pmmProduct</code> to the procurement webUI.
	 */
	void pushProduct(I_PMM_Product pmmProduct);

	/**
	 * Pushes/synchronizes all currently valid PMM_Products to the procurement webUI.
	 *
	 */
	void pushAllProducts();

	void pushAllInfoMessages();

	/**
	 * Pushes/synchronizes all business partners with their contracts to the procurement webUI.
	 */
	void pushAllBPartners();

	void pushRfQs(@Nullable List<SyncRfQ> syncRfqs);

	void pushRfQCloseEvents(@Nullable List<SyncRfQCloseEvent> syncRfQCloseEvents);
}

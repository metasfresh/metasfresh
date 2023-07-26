package de.metas.purchasecandidate.process;

import com.google.common.collect.ImmutableSet;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.async.C_PurchaseCandidates_GeneratePurchaseOrders;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import de.metas.util.Services;
import org.compiere.SpringContextHolder;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;

/*
 * #%L
 * de.metas.purchasecandidate.base
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

public class C_PurchaseCandiate_Create_Requisitions
		extends C_PurchaseCandiate_Create_PurchaseOrders
{
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final DocTypeId reqDocTypeId = docTypeDAO.getDocTypeId(
			DocTypeQuery.builder()
					.docBaseType(X_C_DocType.DOCBASETYPE_PurchaseOrder)
					.docSubType(X_C_DocType.DOCSUBTYPE_Requisition)
					.adClientId(Env.getClientId().getRepoId())
					.build());

	protected void createPurchaseOrders(final ImmutableSet<PurchaseCandidateId> purchaseCandidateIds)
	{
		C_PurchaseCandidates_GeneratePurchaseOrders.enqueue(purchaseCandidateIds, reqDocTypeId);
	}
}

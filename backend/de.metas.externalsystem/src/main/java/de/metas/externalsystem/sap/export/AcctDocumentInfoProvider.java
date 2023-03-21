/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.externalsystem.sap.export;

import de.metas.acct.gljournal_sap.SAPGLJournal;
import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.acct.gljournal_sap.service.SAPGLJournalRepository;
import de.metas.acct.model.I_SAP_GLJournal;
import de.metas.banking.BankStatementId;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.document.DocTypeId;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.inventory.IInventoryDAO;
import de.metas.inventory.InventoryId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mmovement.MovementId;
import org.adempiere.mmovement.api.IMovementDAO;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BankStatement;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_Movement;
import org.springframework.stereotype.Service;

@Service
public class AcctDocumentInfoProvider
{
	private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
	private final IInventoryDAO inventoryDAO = Services.get(IInventoryDAO.class);
	private final IMovementDAO movementDAO = Services.get(IMovementDAO.class);
	private final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);

	@NonNull
	private final SAPGLJournalRepository sapglJournalRepository;

	public AcctDocumentInfoProvider(final @NonNull SAPGLJournalRepository sapglJournalRepository)
	{
		this.sapglJournalRepository = sapglJournalRepository;
	}

	@NonNull
	public AcctDocumentInfo loadDocumentInfo(@NonNull final TableRecordReference recordReference)
	{
		switch (recordReference.getTableName())
		{
			case I_M_InOut.Table_Name:
				final I_M_InOut inOut = inOutBL.getById(recordReference.getIdAssumingTableName(I_M_InOut.Table_Name, InOutId::ofRepoId));
				return AcctDocumentInfo.builder()
						.docTypeId(DocTypeId.ofRepoId(inOut.getC_DocType_ID()))
						.orgId(OrgId.ofRepoId(inOut.getAD_Org_ID()))
						.build();
			case I_C_Payment.Table_Name:
				final I_C_Payment payment = paymentDAO.getById(recordReference.getIdAssumingTableName(I_C_Payment.Table_Name, PaymentId::ofRepoId));
				return AcctDocumentInfo.builder()
						.docTypeId(DocTypeId.ofRepoId(payment.getC_DocType_ID()))
						.orgId(OrgId.ofRepoId(payment.getAD_Org_ID()))
						.build();
			case I_M_Inventory.Table_Name:
				final I_M_Inventory inventory = inventoryDAO.getById(recordReference.getIdAssumingTableName(I_M_Inventory.Table_Name, InventoryId::ofRepoId));
				return AcctDocumentInfo.builder()
						.docTypeId(DocTypeId.ofRepoId(inventory.getC_DocType_ID()))
						.orgId(OrgId.ofRepoId(inventory.getAD_Org_ID()))
						.build();
			case I_C_BankStatement.Table_Name:
				final I_C_BankStatement bankStatement = bankStatementDAO.getById(recordReference.getIdAssumingTableName(I_C_BankStatement.Table_Name, BankStatementId::ofRepoId));
				return AcctDocumentInfo.builder()
						.docTypeId(DocTypeId.ofRepoIdOrNull(bankStatement.getC_DocType_ID()))
						.orgId(OrgId.ofRepoId(bankStatement.getAD_Org_ID()))
						.build();
			case I_M_Movement.Table_Name:
				final I_M_Movement movement = movementDAO.getById(recordReference.getIdAssumingTableName(I_M_Movement.Table_Name, MovementId::ofRepoId));
				return AcctDocumentInfo.builder()
						.docTypeId(DocTypeId.ofRepoId(movement.getC_DocType_ID()))
						.orgId(OrgId.ofRepoId(movement.getAD_Org_ID()))
						.build();
			case I_SAP_GLJournal.Table_Name:
				final SAPGLJournal sapglJournal = sapglJournalRepository.getById(recordReference.getIdAssumingTableName(I_SAP_GLJournal.Table_Name, SAPGLJournalId::ofRepoId));
				return AcctDocumentInfo.builder()
						.docTypeId(sapglJournal.getDocTypeId())
						.orgId(sapglJournal.getOrgId())
						.build();
			default:
				throw new AdempiereException("Unsupported TableRecordReference!")
						.appendParametersToMessage()
						.setParameter("TableName", recordReference.getTableName())
						.setParameter("Record_Id", recordReference.getRecord_ID());
		}
	}
}

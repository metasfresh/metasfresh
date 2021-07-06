/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.bpartner;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.i18n.AdMessageKey;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.user.UserGroupId;
import de.metas.user.UserGroupRepository;
import de.metas.user.UserGroupUserAssignment;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_BP_SupplierApproval;
import org.compiere.model.X_C_BP_SupplierApproval;
import org.compiere.util.TimeUtil;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

public class BPartnerSupplierApprovalService
{
	private final BPartnerSupplierApprovalRepository repo;
	private final UserGroupRepository userGroupRepository;
	private final IOrgDAO orgDAO;
	private final ISysConfigBL sysConfigBL;
	private final INotificationBL notificationBL;
	private final IBPartnerDAO bpartnerDAO;

	private static final AdMessageKey MSG_Partner_Lacks_SupplierApproval = AdMessageKey.of("C_BPartner_Lacks_Supplier_Approval");
	private static final AdMessageKey MSG_Partner_SupplierApproval_ExpirationDate = AdMessageKey.of("C_BPartner_Supplier_Approval_WillExpire");

	private static final String SYS_CONFIG_C_BP_SupplierApproval_Expiration_Notify_UserGroup_ID = "C_BP_SupplierApproval_Expiration_Notify_UserGroup_ID";

	public BPartnerSupplierApprovalService(@NonNull final BPartnerSupplierApprovalRepository repo, final UserGroupRepository userGroupRepository)
	{
		this.repo = repo;
		this.userGroupRepository = userGroupRepository;
		this.orgDAO = Services.get(IOrgDAO.class);
		this.sysConfigBL = Services.get(ISysConfigBL.class);
		this.notificationBL = Services.get(INotificationBL.class);
		this.bpartnerDAO = Services.get(IBPartnerDAO.class);
	}

	public void validateSupplierApproval(@NonNull final BPartnerId partnerId,
			@NonNull final LocalDate datePromised,
			@NonNull final ImmutableList<String> supplierApprovalNorms)
	{
		if (Check.isEmpty(supplierApprovalNorms))
		{
			// nothing to validate
			return;
		}

		final ImmutableList<I_C_BP_SupplierApproval> supplierApprovals = repo.retrieveBPartnerSupplierApprovals(partnerId);

		for (final String norm : supplierApprovalNorms)
		{
			final Optional<I_C_BP_SupplierApproval> foundNorm = supplierApprovals.stream()
					.filter(supplierApproval -> norm.equals(supplierApproval.getSupplierApproval_Norm()))
					.findFirst();

			if (!foundNorm.isPresent())
			{
				throw new AdempiereException(MSG_Partner_Lacks_SupplierApproval, partnerId, norm); // TODO: message
			}

			final I_C_BP_SupplierApproval bPartnerSupplierApproval = foundNorm.get();

			final String supplierApproval = bPartnerSupplierApproval.getSupplierApproval();

			final Timestamp supplierApprovalDate = bPartnerSupplierApproval.getSupplierApproval_Date();

			if (supplierApprovalDate == null)
			{
				// the vendor doesn't have a supplier approval
				throw new AdempiereException(MSG_Partner_Lacks_SupplierApproval, partnerId, norm); // TODO: message
			}
			final LocalDate supplierApprovalDateToUse = TimeUtil.asLocalDate(supplierApprovalDate,
																			 orgDAO.getTimeZone(OrgId.ofRepoId(bPartnerSupplierApproval.getAD_Org_ID())));

			final int numberOfYears;

			switch (supplierApproval)
			{
				case X_C_BP_SupplierApproval.SUPPLIERAPPROVAL_1Year:
					numberOfYears = 1;
					break;

				case X_C_BP_SupplierApproval.SUPPLIERAPPROVAL_2Years:
					numberOfYears = 2;
					break;

				case X_C_BP_SupplierApproval.SUPPLIERAPPROVAL_3Years:
					numberOfYears = 3;
					break;

				default:
					// the vendor doesn't have a supplier approval
					throw new AdempiereException(MSG_Partner_Lacks_SupplierApproval, partnerId, norm); // TODO: message
			}

			if (supplierApprovalExpired(supplierApprovalDateToUse, datePromised, numberOfYears))
			{
				throw new AdempiereException(MSG_Partner_Lacks_SupplierApproval, partnerId, norm); // TODO: message
			}
		}

	}

	private boolean supplierApprovalExpired(final LocalDate supplierApprovalDate, final LocalDate datePromised, final int numberOfYears)
	{
		return datePromised.minusYears(numberOfYears).isAfter(supplierApprovalDate);
	}

	public void notifyUserGroupAboutSupplierApprovalExpiration()
	{
		final ImmutableList<I_C_BP_SupplierApproval> bpSupplierApprovals = repo.retrieveBPSupplierApprovalsAboutToExpire();

		for (final I_C_BP_SupplierApproval supplierApprovalRecord : bpSupplierApprovals)
		{
			notifyUserGroupAboutSupplierApprovalExpiration(supplierApprovalRecord);
		}

	}

	private void notifyUserGroupAboutSupplierApprovalExpiration(final I_C_BP_SupplierApproval supplierApprovalRecord)
	{
		final UserNotificationRequest.TargetRecordAction targetRecordAction = UserNotificationRequest
				.TargetRecordAction
				.of(I_C_BP_SupplierApproval.Table_Name, supplierApprovalRecord.getC_BP_SupplierApproval_ID());

		final int userGroupRecordId = sysConfigBL.getIntValue(SYS_CONFIG_C_BP_SupplierApproval_Expiration_Notify_UserGroup_ID,
															  -1,
															  supplierApprovalRecord.getAD_Client_ID(),
															  supplierApprovalRecord.getAD_Org_ID());

		final UserGroupId userGroupId = UserGroupId.ofRepoIdOrNull(userGroupRecordId);

		if (userGroupId == null)
		{
			// nobody to notify
			return;
		}

		final String partnerName = bpartnerDAO.getBPartnerNameById(BPartnerId.ofRepoId(supplierApprovalRecord.getC_BPartner_ID()));
		final String supplierApprovalNorm = supplierApprovalRecord.getSupplierApproval_Norm();
		final LocalDate expirationDate = LocalDate.now(); // TODO FIX THIS

		userGroupRepository
				.getByUserGroupId(userGroupId)
				.streamAssignmentsFor(userGroupId, Instant.now())
				.map(UserGroupUserAssignment::getUserId)
				.map(userId -> UserNotificationRequest.builder()
						.recipientUserId(userId)
						.contentADMessage(MSG_Partner_SupplierApproval_ExpirationDate) // TODo message
						.contentADMessageParam(partnerName)
						.contentADMessageParam(supplierApprovalNorm)
						.contentADMessageParam(expirationDate)
						.targetAction(targetRecordAction)
						.build())
				.forEach(notificationBL::send);
	}

}

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
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
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
import org.compiere.model.I_C_BPartner;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class BPartnerSupplierApprovalService
{
	private final BPartnerSupplierApprovalRepository repo;
	private final UserGroupRepository userGroupRepository;
	private final IOrgDAO orgDAO;
	private final ISysConfigBL sysConfigBL;
	private final INotificationBL notificationBL;
	private final IBPartnerDAO bpartnerDAO;
	private final IMsgBL msgBL;

	private static final AdMessageKey MSG_Partner_Lacks_SupplierApproval = AdMessageKey.of("C_BPartner_Lacks_Supplier_Approval");
	private static final AdMessageKey MSG_Partner_SupplierApproval_ExpirationDate = AdMessageKey.of("C_BPartner_Supplier_Approval_WillExpire");

	private static final String SYS_CONFIG_C_BP_SupplierApproval_Expiration_Notify_UserGroup_ID = "C_BP_SupplierApproval_Expiration_Notify_UserGroup_ID";

	private static final int MAX_MONTHS_UNTIL_EXPIRATION_DATE = 3;

	public BPartnerSupplierApprovalService(@NonNull final BPartnerSupplierApprovalRepository repo, final UserGroupRepository userGroupRepository)
	{
		this.repo = repo;
		this.userGroupRepository = userGroupRepository;
		this.orgDAO = Services.get(IOrgDAO.class);
		this.sysConfigBL = Services.get(ISysConfigBL.class);
		this.notificationBL = Services.get(INotificationBL.class);
		this.bpartnerDAO = Services.get(IBPartnerDAO.class);
		this.msgBL = Services.get(IMsgBL.class);
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

		final String partnerName = bpartnerDAO.getBPartnerNameById(partnerId);

		final ImmutableList<I_C_BP_SupplierApproval> supplierApprovals = repo.retrieveBPartnerSupplierApprovals(partnerId);

		for (final String norm : supplierApprovalNorms)
		{
			final Optional<I_C_BP_SupplierApproval> foundNorm = supplierApprovals.stream()
					.filter(supplierApproval -> norm.equals(supplierApproval.getSupplierApproval_Norm()))
					.findFirst();

			if (!foundNorm.isPresent())
			{
				partnerLacksSupplierApproval(partnerName, norm);
			}

			final I_C_BP_SupplierApproval bPartnerSupplierApproval = foundNorm.get();

			final Timestamp supplierApprovalDate = bPartnerSupplierApproval.getSupplierApproval_Date();

			if (supplierApprovalDate == null)
			{
				// the vendor doesn't have a supplier approval
				partnerLacksSupplierApproval(partnerName, norm);
			}
			final LocalDate supplierApprovalDateToUse = TimeUtil.asLocalDate(supplierApprovalDate,
																			 orgDAO.getTimeZone(OrgId.ofRepoId(bPartnerSupplierApproval.getAD_Org_ID())));

			final int numberOfYearsForApproval = getNumberOfYearsForApproval(bPartnerSupplierApproval);

			if (numberOfYearsForApproval == 0)
			{
				partnerLacksSupplierApproval(partnerName, norm);
			}

			if (supplierApprovalDateToUse.isAfter(datePromised))
			{
				partnerLacksSupplierApproval(partnerName, norm);
				return;
			}
			if (supplierApprovalExpired(supplierApprovalDateToUse, datePromised, numberOfYearsForApproval))
			{
				partnerLacksSupplierApproval(partnerName, norm);
				return;
			}
		}

	}

	private void partnerLacksSupplierApproval(final String partnerName, final String norm)
	{
		final ITranslatableString msg = msgBL.getTranslatableMsgText(MSG_Partner_Lacks_SupplierApproval,
																	 partnerName,
																	 norm);
		throw new AdempiereException(msg).markAsUserValidationError();
	}

	private int getNumberOfYearsForApproval(final I_C_BP_SupplierApproval bPartnerSupplierApproval)
	{
		final SupplierApproval supplierApproval = SupplierApproval.ofNullableCode(bPartnerSupplierApproval.getSupplierApproval());

		if (supplierApproval == null)
		{
			// the vendor doesn't have a supplier approval
			return 0;
		}
		if (supplierApproval.isOneYear())
		{
			return 1;
		}
		else if (supplierApproval.isTwoYears())
		{
			return 2;
		}
		else if (supplierApproval.isThreeYears())
		{
			return 3;
		}

		// should not happen
		// the vendor doesn't have a supplier approval
		return 0;

	}

	private boolean supplierApprovalExpired(final LocalDate supplierApprovalDate, final LocalDate datePromised, final int numberOfYears)
	{
		return datePromised.minusYears(numberOfYears).isAfter(supplierApprovalDate);
	}

	public void notifyUserGroupAboutSupplierApprovalExpiration()
	{
		final ImmutableList<I_C_BP_SupplierApproval> bpSupplierApprovals = repo.retrieveBPSupplierApprovalsAboutToExpire(MAX_MONTHS_UNTIL_EXPIRATION_DATE);

		for (final I_C_BP_SupplierApproval supplierApprovalRecord : bpSupplierApprovals)
		{
			notifyUserGroupAboutSupplierApprovalExpiration(supplierApprovalRecord);
		}

	}

	private void notifyUserGroupAboutSupplierApprovalExpiration(@NonNull final I_C_BP_SupplierApproval supplierApprovalRecord)
	{
		final UserNotificationRequest.TargetRecordAction targetRecordAction = UserNotificationRequest
				.TargetRecordAction
				.of(I_C_BPartner.Table_Name, supplierApprovalRecord.getC_BPartner_ID());

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

		final ZoneId timeZone = orgDAO.getTimeZone(OrgId.ofRepoId(supplierApprovalRecord.getAD_Org_ID()));

		final LocalDate expirationDate = TimeUtil.asLocalDate(supplierApprovalRecord.getSupplierApproval_Date(), timeZone)
				.plusYears(getNumberOfYearsForApproval(supplierApprovalRecord));

		userGroupRepository
				.getByUserGroupId(userGroupId)
				.streamAssignmentsFor(userGroupId, Instant.now())
				.map(UserGroupUserAssignment::getUserId)
				.map(userId -> UserNotificationRequest.builder()
						.recipientUserId(userId)
						.contentADMessage(MSG_Partner_SupplierApproval_ExpirationDate)
						.contentADMessageParam(partnerName)
						.contentADMessageParam(supplierApprovalNorm)
						.contentADMessageParam(expirationDate)
						.targetAction(targetRecordAction)
						.build())
				.forEach(notificationBL::send);
	}

}

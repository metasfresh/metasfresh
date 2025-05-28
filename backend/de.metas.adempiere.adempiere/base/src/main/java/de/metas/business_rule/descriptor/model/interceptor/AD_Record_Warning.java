/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.business_rule.descriptor.model.interceptor;

import de.metas.notification.INotificationRepository;
import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Record_Warning;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_AD_Record_Warning.class)
@Component
public class AD_Record_Warning
{
	final INotificationRepository notificationRepository = SpringContextHolder.instance.getBean(INotificationRepository.class);

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_AD_Record_Warning.COLUMNNAME_IsAcknowledged)
	public void deleteNotificationOnAknowledgement(@NonNull final I_AD_Record_Warning warning)
	{
		if (warning.isAcknowledged())
		{
			notificationRepository.deleteByUserAndTableRecordRef(UserId.ofRepoId(warning.getAD_User_ID()), TableRecordReference.of(warning));
		}
	}
}

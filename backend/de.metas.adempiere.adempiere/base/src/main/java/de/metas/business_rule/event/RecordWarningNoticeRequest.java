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

package de.metas.business_rule.event;

import de.metas.i18n.AdMessageKey;
import de.metas.notification.impl.NotificationSeverity;
import de.metas.record.warning.RecordWarningId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Builder
@Value
public class RecordWarningNoticeRequest
{

	// TODO IMPROVE THIS as per review https://github.com/metasfresh/metasfresh/pull/20871#discussion_r2107362428
	@NonNull UserId userId;
	@NonNull RecordWarningId recordWarningId;
	@NonNull NotificationSeverity notificationSeverity;
	@NonNull String message;
}

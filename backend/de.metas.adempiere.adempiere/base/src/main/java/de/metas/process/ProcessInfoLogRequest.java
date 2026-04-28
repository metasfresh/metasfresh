/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.process;

import de.metas.error.AdIssueId;
import lombok.Builder;
import lombok.Value;
import org.adempiere.util.lang.ITableRecordReference;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Value
@Builder
public class ProcessInfoLogRequest
{
	int log_ID;
	@Nullable
	BigDecimal p_Number;
	@Nullable
	String p_Msg;

	@Nullable
	Timestamp pDate;

	@Nullable
	ITableRecordReference tableRecordReference;
	@Nullable
	AdIssueId ad_Issue_ID;
	@Nullable
	String trxName;

	@Nullable
	List<String> warningMessages;
}

package de.metas.ui.web.bankstatement_reconciliation;

import java.time.LocalDate;
import java.util.Set;

import javax.annotation.Nullable;

import de.metas.banking.BankStatementLineId;
import de.metas.currency.Amount;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValuesHolder;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.WidgetSize;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class BankStatementLineRow implements IViewRow
{
	@ViewColumn(seqNo = 10, widgetType = DocumentFieldWidgetType.Integer, widgetSize = WidgetSize.Small, captionKey = "Line")
	private final int lineNo;

	@ViewColumn(seqNo = 20, widgetType = DocumentFieldWidgetType.LocalDate, widgetSize = WidgetSize.Small, captionKey = "DateAcct")
	private final LocalDate dateAcct;

	@ViewColumn(seqNo = 30, widgetType = DocumentFieldWidgetType.Amount, widgetSize = WidgetSize.Small, captionKey = "StmtAmt")
	@Getter
	private final Amount statementLineAmt;

	@ViewColumn(seqNo = 40, widgetType = DocumentFieldWidgetType.Text, widgetSize = WidgetSize.Small, captionKey = "C_Currency_ID")
	private final String currencyCode;

	@ViewColumn(seqNo = 50, widgetType = DocumentFieldWidgetType.Text, widgetSize = WidgetSize.Large, captionKey = "Description")
	private final String description;

	//
	@Getter
	private final BankStatementLineId bankStatementLineId;
	private final DocumentId rowId;
	@Getter
	private final boolean reconciled;
	private final ViewRowFieldNameAndJsonValuesHolder<BankStatementLineRow> values;

	@Builder
	private BankStatementLineRow(
			@NonNull final BankStatementLineId bankStatementLineId,
			final int lineNo,
			@NonNull final LocalDate dateAcct,
			@NonNull final Amount statementLineAmt,
			@Nullable final String description,
			final boolean reconciled)
	{
		this.lineNo = lineNo;
		this.dateAcct = dateAcct;
		this.statementLineAmt = statementLineAmt;
		this.currencyCode = statementLineAmt.getCurrencyCode().toThreeLetterCode();
		this.description = StringUtils.trimBlankToNull(description);

		this.bankStatementLineId = bankStatementLineId;
		this.rowId = convertBankStatementLineIdToDocumentId(bankStatementLineId);
		this.reconciled = reconciled;
		values = ViewRowFieldNameAndJsonValuesHolder.newInstance(BankStatementLineRow.class);

	}

	@Override
	public DocumentId getId()
	{
		return rowId;
	}

	@Override
	public boolean isProcessed()
	{
		return isReconciled();
	}

	@Override
	public DocumentPath getDocumentPath()
	{
		return null;
	}

	@Override
	public Set<String> getFieldNames()
	{
		return values.getFieldNames();
	}

	@Override
	public ViewRowFieldNameAndJsonValues getFieldNameAndJsonValues()
	{
		return values.get(this);
	}

	static DocumentId convertBankStatementLineIdToDocumentId(@NonNull final BankStatementLineId bankStatementLineId)
	{
		return DocumentId.of(bankStatementLineId);
	}

	static BankStatementLineId convertDocumentIdToBankStatementLineId(@NonNull final DocumentId rowId)
	{
		return rowId.toId(BankStatementLineId::ofRepoId);
	}
}

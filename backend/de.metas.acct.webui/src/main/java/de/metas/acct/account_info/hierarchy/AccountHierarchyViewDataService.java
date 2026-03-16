package de.metas.acct.account_info.hierarchy;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.ui.web.document.filter.DocumentFilter;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

class AccountHierarchyViewDataService
{
	private final AcctSchemaId acctSchemaId;

	@Builder
	private AccountHierarchyViewDataService(@NonNull final AcctSchemaId acctSchemaId)
	{
		this.acctSchemaId = acctSchemaId;
	}

	ImmutableList<AccountHierarchyRow> loadRows(@Nullable final DocumentFilter filter)
	{
		final LocalDate dateAcct = extractDateAcct(filter);
		final Integer adOrgId = extractAdOrgId(filter);

		final List<FlatAccountRecord> flatRecords = queryFlatRecords(dateAcct, adOrgId);
		return buildTree(flatRecords);
	}

	@Nullable
	private static LocalDate extractDateAcct(@Nullable final DocumentFilter filter)
	{
		if (filter == null)
		{
			return LocalDate.now();
		}
		final java.time.Instant instant = filter.getParameterValueAsInstantOrNull(AccountHierarchyFilterHelper.PARAM_DateAcct);
		if (instant == null)
		{
			return LocalDate.now();
		}
		return instant.atZone(java.time.ZoneId.systemDefault()).toLocalDate();
	}

	@Nullable
	private static Integer extractAdOrgId(@Nullable final DocumentFilter filter)
	{
		if (filter == null)
		{
			return null;
		}
		return filter.getParameterValueAsInt(AccountHierarchyFilterHelper.PARAM_AD_Org_ID, -1) > 0
				? filter.getParameterValueAsInt(AccountHierarchyFilterHelper.PARAM_AD_Org_ID, -1)
				: null;
	}

	private List<FlatAccountRecord> queryFlatRecords(@NonNull final LocalDate dateAcct, @Nullable final Integer adOrgId)
	{
		final String sql = "SELECT " +
				" ev.C_ElementValue_ID, ev.Value, ev.Name, ev.IsSummary, " +
				" tn.Parent_ID, " +
				" COALESCE((de_metas_acct.acctBalanceToDate(ev.C_ElementValue_ID, ?, ?::date, COALESCE(?, 0), 'N', 'N')).Balance, 0) AS balance, " +
				" COALESCE((de_metas_acct.acctBalanceToDate(ev.C_ElementValue_ID, ?, ?::date, COALESCE(?, 0), 'N', 'N')).Debit, 0) AS dr_ytd, " +
				" COALESCE((de_metas_acct.acctBalanceToDate(ev.C_ElementValue_ID, ?, ?::date, COALESCE(?, 0), 'N', 'N')).Credit, 0) AS cr_ytd " +
				" FROM C_ElementValue ev " +
				" JOIN C_Element e ON e.C_Element_ID = ev.C_Element_ID AND e.isActive = 'Y' " +
				" JOIN AD_TreeNode tn ON tn.AD_Tree_ID = e.AD_Tree_ID AND tn.Node_ID = ev.C_ElementValue_ID " +
				" WHERE ev.isActive = 'Y' " +
				" ORDER BY ev.Value";

		final List<FlatAccountRecord> records = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			int idx = 1;
			// balance params
			pstmt.setInt(idx++, acctSchemaId.getRepoId());
			pstmt.setObject(idx++, dateAcct);
			pstmt.setObject(idx++, adOrgId);
			// dr_ytd params
			pstmt.setInt(idx++, acctSchemaId.getRepoId());
			pstmt.setObject(idx++, dateAcct);
			pstmt.setObject(idx++, adOrgId);
			// cr_ytd params
			pstmt.setInt(idx++, acctSchemaId.getRepoId());
			pstmt.setObject(idx++, dateAcct);
			pstmt.setObject(idx++, adOrgId);

			rs = pstmt.executeQuery();
			while (rs.next())
			{
				records.add(FlatAccountRecord.builder()
						.elementValueId(ElementValueId.ofRepoId(rs.getInt("C_ElementValue_ID")))
						.value(rs.getString("Value"))
						.name(rs.getString("Name"))
						.isSummary("Y".equals(rs.getString("IsSummary")))
						.parentElementValueId(ElementValueId.ofRepoIdOrNull(rs.getInt("Parent_ID")))
						.balance(rs.getBigDecimal("balance"))
						.drYTD(rs.getBigDecimal("dr_ytd"))
						.crYTD(rs.getBigDecimal("cr_ytd"))
						.build());
			}
		}
		catch (final SQLException e)
		{
			throw new org.adempiere.exceptions.DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		return records;
	}

	@VisibleForTesting
	static ImmutableList<AccountHierarchyRow> buildTree(@NonNull final List<FlatAccountRecord> flatRecords)
	{
		// Group by parent
		final ImmutableListMultimap<Optional<ElementValueId>, FlatAccountRecord> byParent = Multimaps.index(
				flatRecords,
				record -> Optional.ofNullable(record.getParentElementValueId()));

		// Build leaf rows first, then assemble tree bottom-up via recursion
		return buildChildrenOf(Optional.empty(), byParent);
	}

	private static ImmutableList<AccountHierarchyRow> buildChildrenOf(
			@NonNull final Optional<ElementValueId> parentId,
			@NonNull final ImmutableListMultimap<Optional<ElementValueId>, FlatAccountRecord> byParent)
	{
		final ImmutableList<FlatAccountRecord> children = byParent.get(parentId);
		if (children.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableList.Builder<AccountHierarchyRow> result = ImmutableList.builder();
		for (final FlatAccountRecord record : children)
		{
			final ImmutableList<AccountHierarchyRow> grandChildren = buildChildrenOf(
					Optional.of(record.getElementValueId()),
					byParent);

			AccountHierarchyRow row = AccountHierarchyRow.builder()
					.elementValueId(record.getElementValueId())
					.accountValue(record.getValue())
					.accountName(record.getName())
					.balance(record.getBalance())
					.debitYTD(record.getDrYTD())
					.creditYTD(record.getCrYTD())
					.rowType(record.isSummary() ? guessLevelType(parentId, record) : AccountHierarchyRowType.ACCOUNT)
					.parentElementValueId(record.getParentElementValueId())
					.includedRows(grandChildren)
					.build();

			// Aggregate balances from children if this is a summary account
			if (record.isSummary() && !grandChildren.isEmpty())
			{
				BigDecimal totalBalance = BigDecimal.ZERO;
				BigDecimal totalDr = BigDecimal.ZERO;
				BigDecimal totalCr = BigDecimal.ZERO;
				for (final AccountHierarchyRow child : grandChildren)
				{
					totalBalance = totalBalance.add(child.getBalance());
					totalDr = totalDr.add(child.getDebitYTD());
					totalCr = totalCr.add(child.getCreditYTD());
				}
				row = row.withAggregatedBalances(totalBalance, totalDr, totalCr);
			}

			result.add(row);
		}

		return result.build()
				.stream()
				.sorted(Comparator.comparing(row -> row.getId().toJson()))
				.collect(ImmutableList.toImmutableList());
	}

	private static AccountHierarchyRowType guessLevelType(
			@NonNull final Optional<ElementValueId> parentId,
			@NonNull final FlatAccountRecord record)
	{
		// Root nodes (no parent) are L1
		if (!parentId.isPresent() || record.getParentElementValueId() == null)
		{
			return AccountHierarchyRowType.L1;
		}
		// The actual tree depth is hard to determine without walking up,
		// so we use a simple heuristic based on account value length
		final String value = record.getValue();
		if (value.length() <= 1) return AccountHierarchyRowType.L1;
		if (value.length() <= 2) return AccountHierarchyRowType.L2;
		if (value.length() <= 3) return AccountHierarchyRowType.L3;
		if (value.length() <= 4) return AccountHierarchyRowType.L4;
		return AccountHierarchyRowType.L5;
	}

	@Value
	@Builder
	static class FlatAccountRecord
	{
		@NonNull ElementValueId elementValueId;
		@NonNull String value;
		@NonNull String name;
		boolean isSummary;
		@Nullable ElementValueId parentElementValueId;
		@NonNull BigDecimal balance;
		@NonNull BigDecimal drYTD;
		@NonNull BigDecimal crYTD;
	}
}

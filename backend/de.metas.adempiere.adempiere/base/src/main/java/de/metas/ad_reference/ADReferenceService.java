package de.metas.ad_reference;

import de.metas.adempiere.service.impl.TooltipType;
import de.metas.cache.CCache;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.Check;
import de.metas.util.ColorId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.service.impl.LookupException;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_C_ValidCombination;
import org.compiere.model.MQuery;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Properties;

@Service
public class ADReferenceService
{
	/**
	 * !! Don't invoke this getter during startup !!
	 * It is intended to be used only:
	 * <li>If you are not within a spring component and can't inject it in any other way.</li>
	 * <li>If your code is not invoked during startup.</li>
	 */
	public static ADReferenceService get() {return SpringContextHolder.instance.getBean(ADReferenceService.class);}

	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
	private final AdRefListRepository adRefListRepository;
	private final AdRefTableRepository adRefTableRepository;
	private final CCache<String, ADRefTable> tableDirectInfoByColumnName = CCache.<String, ADRefTable>builder().build();

	public ADReferenceService(
			@NonNull final AdRefListRepository adRefListRepository,
			@NonNull final AdRefTableRepository adRefTableRepository
	)
	{
		this.adRefListRepository = adRefListRepository;
		this.adRefTableRepository = adRefTableRepository;
	}

	public static ADReferenceService newMocked()
	{
		final AdRefListRepositoryMocked adRefListRepository = new AdRefListRepositoryMocked();
		adRefListRepository.setAutoCreateOnDemand(true);

		return new ADReferenceService(
				adRefListRepository,
				new AdRefTableRepositoryMocked());
	}

	public void saveRefList(final ADRefListItemCreateRequest request)
	{
		adRefListRepository.createRefListItem(request);
	}

	public ADRefList getRefListById(@NonNull final ReferenceId adReferenceId)
	{
		return adRefListRepository.getById(adReferenceId);
	}

	public Collection<ADRefListItem> retrieveListItems(final ReferenceId adReferenceId)
	{
		return getRefListById(adReferenceId).getItems();
	}

	public Collection<ADRefListItem> retrieveListItems(final int adReferenceId)
	{
		return retrieveListItems(ReferenceId.ofRepoId(adReferenceId));
	}

	public String retrieveListNameTrl(final Properties ctx, final ReferenceId adReferenceId, final String value)
	{
		final ADRefListItem item = getRefListById(adReferenceId).getItemByValue(value).orElse(null);
		return item != null
				? item.getName().translate(Env.getADLanguageOrBaseLanguage(ctx))
				: value;
	}

	@Deprecated
	public String retrieveListNameTrl(final Properties ctx, final int adReferenceId, final String value)
	{
		return retrieveListNameTrl(ctx, ReferenceId.ofRepoId(adReferenceId), value);
	}

	@Deprecated
	public String retrieveListNameTrl(final ReferenceId adReferenceId, final String value)
	{
		return retrieveListNameTrl(Env.getCtx(), adReferenceId, value);
	}

	@Deprecated
	public String retrieveListNameTrl(final int adReferenceId, final String value)
	{
		return retrieveListNameTrl(Env.getCtx(), ReferenceId.ofRepoId(adReferenceId), value);
	}

	@Nullable
	public ADRefListItem retrieveListItemOrNull(@NonNull final ReferenceId adReferenceId, @Nullable final String value)
	{
		return getRefListById(adReferenceId).getItemByValue(value).orElse(null);
	}

	@Nullable
	public ADRefListItem retrieveListItemOrNull(final int adReferenceId, final String value)
	{
		return retrieveListItemOrNull(ReferenceId.ofRepoId(adReferenceId), value);
	}

	public ITranslatableString retrieveListNameTranslatableString(@NonNull final ReferenceId adReferenceId, @NonNull final String value)
	{
		Check.assumeNotEmpty(value, "value is not empty");

		// NOTE: we are wrapping everything in a forwarding translatable string,
		// because we want this code to be called each time, just in case of a cache reset,
		// or the translation changes.
		return TranslatableStrings.forwardingTo(() -> {
			final ADRefListItem item = retrieveListItemOrNull(adReferenceId, value);
			return item != null ? item.getName() : TranslatableStrings.constant(value);
		});
	}

	public ITranslatableString retrieveListNameTranslatableString(final int adReferenceId, final String value)
	{
		return retrieveListNameTranslatableString(ReferenceId.ofRepoId(adReferenceId), value);
	}

	public ExplainedOptional<ADRefTable> getTableRefInfo(final ReferenceId referenceId)
	{
		return adRefTableRepository.getById(referenceId);
	}

	@Nullable
	public ADRefTable retrieveTableRefInfo(@NonNull final ReferenceId referenceId)
	{
		final ExplainedOptional<ADRefTable> tableRefInfo = adRefTableRepository.getById(referenceId);
		if (tableRefInfo.isPresent())
		{
			return tableRefInfo.get();
		}
		else
		{
			// NOTE: don't use logger.error because that call ErrorManager which will call POInfo which called this method.
			System.err.println("Cannot retrieve tableRefInfo for " + referenceId + " because " + tableRefInfo.getExplanation().getDefaultValue()
					+ ". Returning null.");
			// logger.error("Cannot retrieve tableRefInfo for {}. Returning null.", referenceId);
			return null;
		}
	}

	public boolean isTableReference(@Nullable final ReferenceId referenceId)
	{
		return adRefTableRepository.hasTableReference(referenceId);
	}

	public ADRefTable getTableDirectRefInfo(@NonNull final String columnName)
	{
		return tableDirectInfoByColumnName.getOrLoad(columnName, this::retrieveTableDirectRefInfo);
	}

	private ADRefTable retrieveTableDirectRefInfo(@NonNull final String columnName)
	{
		Check.assumeNotEmpty(columnName, "ColumnName not empty");

		if (!columnName.endsWith("_ID"))
		{
			throw new LookupException("Key does not end with '_ID': " + columnName);
		}

		String keyColumn = MQuery.getZoomColumnName(columnName);
		String tableName = MQuery.getZoomTableName(columnName);

		// Case: ColumnName is something like "hu_pip.M_HU_PI_Item_Product_ID". We need to get rid of table alias prefix
		if (adTableDAO.retrieveTableId(tableName) <= 0 // table name does not exist
				&& keyColumn.indexOf(".") > 0)
		{
			keyColumn = keyColumn.substring(keyColumn.indexOf(".") + 1);
			tableName = MQuery.getZoomTableName(keyColumn);
		}

		final boolean autoComplete;
		final I_AD_Table table = adTableDAO.retrieveTableOrNull(tableName);
		if (table == null)
		{
			autoComplete = false;
		}
		else
		{
			autoComplete = table.isAutocomplete();
		}

		final TooltipType tooltipType = adTableDAO.getTooltipTypeByTableName(tableName);

		return ADRefTable.builder()
				.identifier("Direct[FromColumn" + columnName + ",To=" + tableName + "." + columnName + "]")
				.tableName(tableName)
				.keyColumn(keyColumn)
				.autoComplete(autoComplete)
				.tooltipType(tooltipType)
				.build();
	}

	public ADRefTable retrieveAccountTableRefInfo()
	{
		return ADRefTable.builder()
				.identifier("Account - C_ValidCombination_ID")
				.tableName(I_C_ValidCombination.Table_Name)
				.keyColumn(I_C_ValidCombination.COLUMNNAME_C_ValidCombination_ID)
				.autoComplete(true)
				.tooltipType(adTableDAO.getTooltipTypeByTableName(I_C_ValidCombination.Table_Name))
				.build();
	}

	@Nullable
	public ColorId getColorId(@NonNull final Object model, @NonNull final String columnName, @Nullable final String refListValue)
	{
		if (Check.isBlank(refListValue))
		{
			return null;
		}

		final ReferenceId referenceId = InterfaceWrapperHelper.getPO(model).getPOInfo().getColumnReferenceValueId(columnName);
		if (referenceId == null)
		{
			return null;
		}
		final ADRefListItem refListItem = retrieveListItemOrNull(referenceId, refListValue);

		return refListItem != null ? refListItem.getColorId() : null;
	}
}


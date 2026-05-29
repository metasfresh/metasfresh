package de.metas.ui.web.window.model.sql.save;

import com.google.common.collect.ImmutableList;
import de.metas.cache.model.POCacheSourceModel;
import de.metas.logging.LogManager;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.DataTypes;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.DocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.IDocumentFieldView;
import de.metas.ui.web.window.model.lookup.LabelsLookup;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.GridTabVO;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static de.metas.ui.web.window.model.sql.SqlValueConverters.convertToPOValue;

class DefaultSaveHandler implements SaveHandler
{
	@NonNull private static final Logger logger = LogManager.getLogger(DefaultSaveHandler.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	@NonNull
	public Set<String> getHandledTableName() {throw new IllegalStateException("Calling getHandledTableName() for " + this + " is not allowed");}

	@Override
	public boolean isReadonly(@NonNull GridTabVO gridTabVO)
	{
		return gridTabVO.isView();
	}

	@Override
	public SaveResult save(final @NotNull Document document)
	{
		// Runnables to be executed after the PO is saved
		final List<Runnable> afterSaveRunnables = new ArrayList<>();

		//
		// Load the PO / Create new PO instance
		final PO po = retrieveOrCreatePO(document);

		//
		// Set values to PO
		final boolean isNew = document.isNew();
		boolean changes = false;
		for (final IDocumentFieldView documentField : document.getFieldViews())
		{
			if (!isNew && !documentField.hasChangesToSave())
			{
				logger.trace("Skip setting PO value because document field has no changes: {}", documentField);
				continue;
			}

			if (DocumentFieldWidgetType.Labels == documentField.getWidgetType())
			{
				// save labels after PO is saved because we want to make sure it's not new (so we can link to it)
				afterSaveRunnables.add(() -> saveLabels(document, documentField));
			}

			if (setPOValue(po, documentField))
			{
				changes = true;
			}
		}

		//
		// Save the PO
		boolean needsRefresh = false;
		if (changes)
		{
			//
			// Actual save
			// TODO: advice the PO to not reload after save.
			InterfaceWrapperHelper.save(po);
			document.markAsSaved();
			needsRefresh = true;
		}
		else
		{
			logger.trace("Skip saving {} because there was no actual change", po);
		}

		//
		// Execute after save runnables
		if (!afterSaveRunnables.isEmpty())
		{
			afterSaveRunnables.forEach(Runnable::run);
			needsRefresh = true;
		}

		final DocumentId idNew;
		if (needsRefresh)
		{
			final SqlDocumentEntityDataBindingDescriptor dataBinding = document.getEntityDescriptor().getDataBinding(SqlDocumentEntityDataBindingDescriptor.class);
			idNew = extractDocumentId(po, dataBinding);
		}
		else
		{
			idNew = document.getDocumentId();
		}

		return SaveResult.builder()
				.needsRefresh(needsRefresh)
				.idNew(idNew)
				.build();
	}

	@Override
	public void delete(@NonNull final Document document)
	{
		final PO po = retrieveOrCreatePO(document);
		InterfaceWrapperHelper.delete(po);
	}

	private PO retrieveOrCreatePO(final Document document)
	{
		final SqlDocumentEntityDataBindingDescriptor dataBinding = SqlDocumentEntityDataBindingDescriptor.cast(document.getEntityDescriptor().getDataBinding());
		final String sqlTableName = dataBinding.getTableName();

		//
		// Load the PO / Create new PO instance
		final PO po;
		if (document.isNew())
		{
			po = TableModelLoader.instance.newPO(document.getCtx(), sqlTableName, ITrx.TRXNAME_ThreadInherited);
		}
		else if (dataBinding.isSingleKey())
		{
			final boolean checkCache = false;
			po = TableModelLoader.instance.getPO(document.getCtx(), sqlTableName, document.getDocumentIdAsInt(), checkCache, ITrx.TRXNAME_ThreadInherited);
		}
		else
		{
			po = toQueryBuilder(dataBinding, document.getDocumentId())
					.create()
					.firstOnly(PO.class);
		}

		if (po == null)
		{
			throw new DBException("No PO found for " + document);
		}

		//
		//
		po.set_ManualUserAction(document.getWindowNo());
		InterfaceWrapperHelper.ATTR_ReadOnlyColumnCheckDisabled.setValue(po, true); // allow changing any columns

		//
		final TableRecordReference rootRecordReference = extractRootRecordReference(document);
		POCacheSourceModel.setRootRecordReference(po, rootRecordReference);

		return po;
	}

	private static void saveLabels(final Document document, final IDocumentFieldView documentField)
	{
		final LabelsLookup lookup = LabelsLookup.cast(documentField.getDescriptor().getLookupDescriptor().orElse(null));

		final int linkId = document.getFieldView(lookup.getLinkColumnName()).getValueAsInt(-1);
		final Set<String> listValuesInDatabase = lookup.retrieveExistingValuesByLinkId(linkId).getKeysAsString();

		final LookupValuesList lookupValuesList = documentField.getValueAs(LookupValuesList.class);
		final HashSet<String> listValuesToSave = lookupValuesList != null ? new HashSet<>(lookupValuesList.getKeysAsString()) : new HashSet<>();

		//
		// Delete removed labels
		{
			final HashSet<String> listValuesToDelete = new HashSet<>(listValuesInDatabase);
			listValuesToDelete.removeAll(listValuesToSave);
			if (!listValuesToDelete.isEmpty())
			{
				final int countDeleted = lookup.queryValueRecordsByLinkId(linkId)
						.addInArrayFilter(lookup.getLabelsValueColumnName(), lookup.normalizeStringIds(listValuesToDelete))
						.create()
						.delete();
				if (countDeleted != listValuesToDelete.size())
				{
					logger.warn("Possible issue while deleting labels for linkId={}: listValuesToDelete={}, countDeleted={}", linkId, listValuesToDelete, countDeleted);
				}
			}
		}

		//
		// Create new labels
		{
			final Set<String> listValuesToSaveEffective = new HashSet<>(listValuesToSave);
			listValuesToSaveEffective.removeAll(listValuesInDatabase);
			listValuesToSaveEffective.forEach(listValueToSave -> createLabelPORecord(listValueToSave, linkId, lookup));
		}
	}

	private static void createLabelPORecord(
			@NonNull final String listValue,
			final int linkId,
			@NonNull final LabelsLookup lookup)
	{
		final PO labelPO = TableModelLoader.instance.newPO(lookup.getLabelsTableName());
		labelPO.set_ValueNoCheck(lookup.getLabelsLinkColumnName(), linkId);
		labelPO.set_ValueNoCheck(lookup.getLabelsValueColumnName(), listValue);
		InterfaceWrapperHelper.save(labelPO);
	}

	/**
	 * Sets PO's value from given <code>documentField</code>.
	 *
	 * @return true if value was set and really changed
	 */
	private static boolean setPOValue(final PO po, final IDocumentFieldView documentField)
	{
		final DocumentFieldDataBindingDescriptor dataBinding = documentField.getDescriptor().getDataBinding().orElse(null);
		if (dataBinding == null)
		{
			logger.trace("Skip setting PO's column because it has no databinding: {}", documentField);
			return false;
		}

		final POInfo poInfo = po.getPOInfo();
		final String columnName = dataBinding.getColumnName();

		final int poColumnIndex = poInfo.getColumnIndex(columnName);
		if (poColumnIndex < 0)
		{
			logger.trace("Skip setting PO's column because it's missing: {} -- PO={}", columnName, po);
			return false;
		}

		//
		// Virtual column => skip setting it
		if (poInfo.isVirtualColumn(poColumnIndex))
		{
			logger.trace("Skip setting PO's virtual column: {} -- PO={}", columnName, po);
			return false; // no change
		}
		//
		// ID
		else if (poInfo.isKey(poColumnIndex))
		{
			final int id = documentField.getValueAsInt(-1);
			if (id >= 0)
			{
				final int idOld = po.get_ValueAsInt(poColumnIndex);
				if (id == idOld)
				{
					logger.trace("Skip setting PO's key column because it's the same as the old value: {} (old={}), PO={}", columnName, idOld, po);
					return false; // no change
				}

				final boolean idSet = po.set_ValueNoCheck(columnName, id);
				if (!idSet)
				{
					throw new AdempiereException("Failed setting ID=" + id + " to " + po);
				}

				logger.trace("Setting PO ID: {}={} -- PO={}", columnName, id, po);
				return true;
			}
			else
			{
				logger.trace("Skip setting PO's key column: {} -- PO={}", columnName, po);
				return false; // no change
			}
		}
		//
		// Created/Updated columns
		else if (WindowConstants.FIELDNAMES_CreatedUpdated.contains(columnName))
		{
			logger.trace("Skip setting PO's created/updated column: {} -- PO={}", columnName, po);
			return false; // no change
		}
		//
		// Regular column
		else
		{
			//
			// Check if value was changed, compared with PO's current value
			final Object poValue = po.get_Value(poColumnIndex);
			final Class<?> poValueClass = poInfo.getColumnClass(poColumnIndex);
			final Object fieldValueConv = convertToPOValue(documentField.getValue(), columnName, documentField.getWidgetType(), poValueClass);
			if (poFieldValueEqual(fieldValueConv, poValue))
			{
				logger.trace("Skip setting PO's column because it was not changed: {}={} (old={}) -- PO={}", columnName, fieldValueConv, poValue, po);
				return false; // no change
			}

			//
			// Check if the field value was changed from when we last queried it
			if (!po.is_new())
			{
				final Object fieldInitialValueConv = convertToPOValue(documentField.getInitialValue(), columnName, documentField.getWidgetType(), poValueClass);
				if (!poFieldValueEqual(fieldInitialValueConv, poValue))
				{
					throw new AdempiereException("Document's field was changed from when we last queried it. Please re-query."
							+ "\n Document field initial value: " + fieldInitialValueConv
							+ "\n PO value: " + poValue
							+ "\n Document field: " + documentField
							+ "\n PO: " + po);
				}
			}

			// TODO: handle not updatable columns... i think we shall set them only if the PO is new

			// NOTE: at this point we shall not do any other validations like "mandatory but null", value min/max range check,
			// because we shall rely completely on Document level validations and not duplicate the logic here.

			//
			// Try setting the value
			final boolean valueSet = po.set_ValueOfColumn(columnName, fieldValueConv);
			if (!valueSet)
			{
				logger.warn("Failed setting PO's column: {}={} (old={}) -- PO={}", columnName, fieldValueConv, poValue, po);
				return false; // no change
			}

			logger.trace("Setting PO value: {}={} (old={}) -- PO={}", columnName, fieldValueConv, poValue, po);
			return true;
		}
	}

	/**
	 * @return true if PO field's values can be considered the same
	 */
	private static boolean poFieldValueEqual(final Object value1, final Object value2)
	{
		if (value1 == value2)
		{
			return true;
		}

		// If both values are empty we can consider they are equal
		// (see task https://github.com/metasfresh/metasfresh-webui-api/issues/276)
		if (isEmptyPOFieldValue(value1) && isEmptyPOFieldValue(value2))
		{
			return true;
		}

		return DataTypes.equals(value1, value2);
	}

	private static boolean isEmptyPOFieldValue(final Object value)
	{
		if (value == null)
		{
			return true;
		}
		else if (value instanceof String)
		{
			return ((String)value).isEmpty();
		}
		else
		{
			return false;
		}
	}

	private IQueryBuilder<Object> toQueryBuilder(final SqlDocumentEntityDataBindingDescriptor dataBinding, final DocumentId documentId)
	{
		final String tableName = dataBinding.getTableName();

		final List<SqlDocumentFieldDataBindingDescriptor> keyFields = dataBinding.getKeyFields();
		final int keyFieldsCount = keyFields.size();
		if (keyFieldsCount == 0)
		{
			throw new AdempiereException("No primary key defined for " + tableName);
		}
		final List<Object> keyParts = documentId.toComposedKeyParts();
		if (keyFieldsCount != keyParts.size())
		{
			throw new AdempiereException("Invalid documentId '" + documentId + "'. It shall have " + keyFieldsCount + " parts but it has " + keyParts.size());
		}

		final IQueryBuilder<Object> queryBuilder = queryBL.createQueryBuilder(tableName, PlainContextAware.newWithThreadInheritedTrx());
		for (int i = 0; i < keyFieldsCount; i++)
		{
			final SqlDocumentFieldDataBindingDescriptor keyField = keyFields.get(i);
			final String keyColumnName = keyField.getColumnName();
			final Object keyValue = convertToPOValue(keyParts.get(i), keyColumnName, keyField.getWidgetType(), keyField.getSqlValueClass());
			queryBuilder.addEqualsFilter(keyColumnName, keyValue);
		}

		return queryBuilder;
	}

	private static TableRecordReference extractRootRecordReference(final Document includedDocument)
	{
		if (includedDocument.isRootDocument())
		{
			return null;
		}

		final Document rootDocument = includedDocument.getRootDocument();
		final String rootTableName = rootDocument.getEntityDescriptor().getTableNameOrNull();
		if (rootTableName == null)
		{
			return null;
		}

		final int rootRecordId = rootDocument.getDocumentId().toIntOr(-1);
		if (rootRecordId < 0)
		{
			return null;
		}

		return TableRecordReference.of(rootTableName, rootRecordId);
	}

	private static DocumentId extractDocumentId(final PO po, SqlDocumentEntityDataBindingDescriptor dataBinding)
	{
		if (dataBinding.isSingleKey())
		{
			return DocumentId.of(InterfaceWrapperHelper.getId(po));
		}
		else
		{
			final List<SqlDocumentFieldDataBindingDescriptor> keyFields = dataBinding.getKeyFields();
			if (keyFields.isEmpty())
			{
				throw new AdempiereException("No primary key defined for " + dataBinding.getTableName());
			}

			final List<Object> keyParts = keyFields.stream()
					.map(keyField -> po.get_Value(keyField.getColumnName()))
					.collect(ImmutableList.toImmutableList());

			return DocumentId.ofComposedKeyParts(keyParts);
		}
	}
}

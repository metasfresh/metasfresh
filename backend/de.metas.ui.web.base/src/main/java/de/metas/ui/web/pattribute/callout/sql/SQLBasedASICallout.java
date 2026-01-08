package de.metas.ui.web.pattribute.callout.sql;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMap;
import de.metas.logging.LogManager;
import de.metas.ui.web.pattribute.ASIDescriptor;
import de.metas.ui.web.pattribute.ASIDocument;
import de.metas.ui.web.pattribute.ASIEventType;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.descriptor.ILambdaDocumentFieldCallout;
import de.metas.ui.web.window.model.Document;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.DB;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Map;

@Builder
public class SQLBasedASICallout implements ILambdaDocumentFieldCallout
{
	@NonNull private static final Logger logger = LogManager.getLogger(SQLBasedASICallout.class);
	@NonNull private final ObjectMapper jsonObjectMapper;
	@NonNull private final SQLCalloutFunctionsRepository sqlCalloutFunctionsRepository;

	private static final String DYNATTR_IsRunning = "SQLBasedASICallout.IsRunning";

	@Override
	public void execute(final ICalloutField field) throws Exception
	{
		if (isRunning(field))
		{
			logger.debug("Skipping SQL-based callout because it is already running {}", field);
			return;
		}

		try (IAutoCloseable ignored = temporarySetRunning(field))
		{
			final ASIEventType eventType = ASIDocument.extractASIEventType(field);
			if (eventType.isInitializing())
			{
				// avoid calling while initializing because we might wrongly change the data that was just copied
				logger.debug("Skipping SQL-based callout because it is called while initializing {}", field);
				return;
			}

			final SQLCalloutFunctionList sqlFunctionNames = sqlCalloutFunctionsRepository.getAllFunctions();
			if (sqlFunctionNames.isEmpty())
			{
				logger.debug("Skipping SQL-based callout because there are no SQL functions configured for {}", field);
				return;
			}

			for (final SQLCalloutFunction sqlFunctionName : sqlFunctionNames)
			{
				Stopwatch stopwatch = Stopwatch.createStarted();
				try
				{
					executeCallout(sqlFunctionName, field);
				}
				catch (Exception ex)
				{
					throw AdempiereException.wrapIfNeeded(ex)
							.setParameter("sqlFunctionName", sqlFunctionName)
							.setParameter("field", field.getColumnName());
				}
				finally
				{
					stopwatch.stop();
					logger.debug("Executed {} in {} ms", sqlFunctionName, stopwatch);
				}
			}
		}
	}

	private void executeCallout(@NonNull final SQLCalloutFunction sqlFunctionName, @NonNull final ICalloutField field) throws Exception
	{
		final JsonSQLCalloutFunctionRequest param = toSQLFunctionRequest(field);
		final JsonSQLCalloutFunctionResponse response = executeFunction(sqlFunctionName, param);
		if (response == null)
		{
			return;
		}

		updateASIDocumentFromResponse(field, response);
	}

	private boolean isRunning(@NonNull final ICalloutField field)
	{
		final Document document = field.getModel(Document.class);
		return StringUtils.toBoolean(document.getDynAttribute(DYNATTR_IsRunning));
	}

	private IAutoCloseable temporarySetRunning(@NonNull final ICalloutField field)
	{
		final Document document = field.getModel(Document.class);
		final boolean runningPrev = StringUtils.toBoolean(document.getDynAttribute(DYNATTR_IsRunning));

		document.setDynAttribute(DYNATTR_IsRunning, true);
		return () -> document.setDynAttribute(DYNATTR_IsRunning, runningPrev);
	}

	@Nullable
	private JsonSQLCalloutFunctionResponse executeFunction(@NonNull final SQLCalloutFunction sqlFunctionName, @NonNull final JsonSQLCalloutFunctionRequest param) throws Exception
	{
		final String paramAsJson = jsonObjectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(param);
		final String responseAsJson = StringUtils.trimBlankToNull(
				DB.getSQLValueStringEx(
						ITrx.TRXNAME_ThreadInherited,
						"SELECT " + sqlFunctionName.getNameFQ() + "(?::jsonb)",
						paramAsJson
				)
		);
		if (responseAsJson == null)
		{
			return null;
		}

		return jsonObjectMapper.readValue(responseAsJson, JsonSQLCalloutFunctionResponse.class);
	}

	private static void updateASIDocumentFromResponse(
			@NonNull final ICalloutField field,
			@NonNull final JsonSQLCalloutFunctionResponse from)
	{
		final Map<String, Object> attributes = from.getAttributes();
		if (attributes == null || attributes.isEmpty())
		{
			return;
		}

		final Document document = field.getModel(Document.class);
		final ICalloutRecord calloutRecord = field.getCalloutRecord();
		attributes.forEach((attributeCode, value) -> {
			if (document.hasField(attributeCode))
			{
				calloutRecord.setValue(attributeCode, value);
			}
		});
	}

	private static JsonSQLCalloutFunctionRequest toSQLFunctionRequest(final ICalloutField field)
	{
		final ASIDescriptor asiDescriptor = ASIDocument.extractASIDescriptor(field);
		final String triggeringFieldName = field.getColumnName();
		final Document document = field.getModel(Document.class);
		final JSONOptions jsonOptions = JSONOptions.newInstance();

		return JsonSQLCalloutFunctionRequest.builder()
				.contextTableName(asiDescriptor.getContextTableName())
				.contextRecordId(asiDescriptor.getContextRecordId())
				.contextColumnName(asiDescriptor.getContextColumnName())
				.attributeId(asiDescriptor.getAttributeId(triggeringFieldName))
				.attributeCode(asiDescriptor.getAttributeCode(triggeringFieldName))
				.attributes(document.getFieldNames()
						.stream()
						.filter(asiDescriptor::isAttributeField)
						.map(fieldName -> JsonSQLCalloutFunctionRequest.Attribute.builder()
								.attributeId(asiDescriptor.getAttributeId(fieldName))
								.attributeCode(asiDescriptor.getAttributeCode(fieldName))
								.value(extractAttributeValueAsJson(document, fieldName, jsonOptions))
								.build())
						.collect(ImmutableMap.toImmutableMap(
								attribute -> attribute.getAttributeCode().getCode(),
								attribute -> attribute))
				)
				.build();
	}

	@Nullable
	private static Object extractAttributeValueAsJson(final Document document, final String fieldName, final JSONOptions jsonOptions)
	{
		Object value = document.getFieldView(fieldName).getValueAsJsonObject(jsonOptions);
		if (value instanceof JSONLookupValue)
		{
			value = ((JSONLookupValue)value).getKey();
		}
		return value;
	}
}

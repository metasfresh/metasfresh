package de.metas.quickinput.config;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@EqualsAndHashCode
@ToString
public class QuickInputConfigLayout
{
	private final ImmutableList<Field> fields;
	private final ImmutableMap<String, Field> fieldsByFieldName;
	@Getter private final ImmutableList<String> fieldNamesInOrder;

	@Builder
	private QuickInputConfigLayout(@NonNull @Singular final ImmutableList<Field> fields)
	{
		this.fields = fields;
		this.fieldsByFieldName = Maps.uniqueIndex(this.fields, Field::getFieldName);
		this.fieldNamesInOrder = this.fields.stream().map(Field::getFieldName).collect(ImmutableList.toImmutableList());
	}

	public static Optional<QuickInputConfigLayout> parse(@Nullable final String layoutStringParam)
	{
		final String layoutStringNorm = StringUtils.trimBlankToNull(layoutStringParam);

		if (layoutStringNorm == null || "-".equals(layoutStringNorm))
		{
			return Optional.empty();
		}

		final ArrayList<Field> fields = new ArrayList<>();
		for (final String fieldLayoutString : Splitter.on(",").splitToList(layoutStringNorm))
		{
			final String fieldLayoutStringNorm = StringUtils.trimBlankToNull(fieldLayoutString);
			if (fieldLayoutStringNorm == null)
			{
				throw new AdempiereException("Empty field name not allowed");
			}

			final String fieldName;
			final boolean mandatory;
			if (fieldLayoutStringNorm.endsWith("?"))
			{
				mandatory = false;
				fieldName = fieldLayoutStringNorm.substring(0, fieldLayoutStringNorm.length() - 1);
			}
			else
			{
				mandatory = true;
				fieldName = fieldLayoutStringNorm;
			}

			if (fieldName.isEmpty())
			{
				throw new AdempiereException("Empty field name not allowed");
			}

			fields.add(QuickInputConfigLayout.Field.builder()
					.fieldName(fieldName)
					.mandatory(mandatory)
					.build());
		}

		if (fields.isEmpty())
		{
			throw new AdempiereException("No fields");
		}

		return Optional.of(
				QuickInputConfigLayout.builder()
						.fields(ImmutableList.copyOf(fields))
						.build());
	}


	public void assertFieldExistsAndIsMandatory(final String fieldName)
	{
		final Field field = fieldsByFieldName.get(fieldName);
		if (field == null)
		{
			throw new AdempiereException("Field `" + fieldName + "` is missing from layout config");
		}
		if (!field.isMandatory())
		{
			throw new AdempiereException("Field `" + fieldName + "` shall be mandatory");
		}
	}

	public void assertFieldNamesEligible(@NonNull final Set<String> eligibleFieldNames)
	{
		fields.forEach(field -> field.assertFieldNamesEligible(eligibleFieldNames));
	}

	public boolean isMandatory(@NonNull final String fieldName)
	{
		final Field field = fieldsByFieldName.get(fieldName);
		return field != null && field.isMandatory();
	}

	@Value
	@Builder
	public static class Field
	{
		@NonNull String fieldName;
		boolean mandatory;

		public void assertFieldNamesEligible(@NonNull final Set<String> eligibleFieldNames)
		{
			// shall not happen
			if (eligibleFieldNames.isEmpty())
			{
				throw new AdempiereException("Cannot validate when eligible field names list is empty");
			}

			if (!eligibleFieldNames.contains(fieldName))
			{
				throw new AdempiereException("Field `" + fieldName + "` is not eligible. Eligible field names are: " + eligibleFieldNames);
			}
		}
	}
}

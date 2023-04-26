package de.metas.dao;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * @apiNote Helper class to be used in Query POJOs.
 */
@EqualsAndHashCode
@ToString
public final class ValueRestriction<T>
{
	public static <T> ValueRestriction<T> any()
	{
		//noinspection unchecked
		return (ValueRestriction<T>)ANY;
	}

	public static <T> ValueRestriction<T> isNull()
	{
		//noinspection unchecked
		return (ValueRestriction<T>)IS_NULL;
	}

	public static <T> ValueRestriction<T> notNull()
	{
		//noinspection unchecked
		return (ValueRestriction<T>)NOT_NULL;
	}

	public static <T> ValueRestriction<T> equalsTo(@NonNull final T onlyValue)
	{
		return new ValueRestriction<>(Type.EQUALS_TO, onlyValue);
	}

	public static <T> ValueRestriction<T> equalsToOrNull(@Nullable final T onlyValue)
	{
		return onlyValue != null
				? new ValueRestriction<>(Type.EQUALS_TO_OR_NULL, onlyValue)
				: isNull();
	}

	private enum Type
	{
		ANY, IS_NULL, NOT_NULL, EQUALS_TO, EQUALS_TO_OR_NULL
	}

	private static final ValueRestriction<Object> ANY = new ValueRestriction<>(Type.ANY, null);
	private static final ValueRestriction<Object> IS_NULL = new ValueRestriction<>(Type.IS_NULL, null);
	private static final ValueRestriction<Object> NOT_NULL = new ValueRestriction<>(Type.NOT_NULL, null);

	private final @NonNull Type type;
	private final @Nullable T onlyValue;

	private ValueRestriction(final @NonNull Type type, final @Nullable T onlyValue)
	{
		this.type = type;
		this.onlyValue = onlyValue;
	}

	public interface CaseMappingFunction<T, R>
	{
		R anyValue();

		R valueIsNull();

		R valueIsNotNull();

		R valueEqualsTo(@NonNull T value);

		R valueEqualsToOrNull(@NonNull T value);
	}

	public <R> R map(@NonNull final CaseMappingFunction<T, R> mappingFunction)
	{
		switch (type)
		{
			case ANY:
				return mappingFunction.anyValue();
			case IS_NULL:
				return mappingFunction.valueIsNull();
			case NOT_NULL:
				return mappingFunction.valueIsNotNull();
			case EQUALS_TO:
				return mappingFunction.valueEqualsTo(Objects.requireNonNull(onlyValue));
			case EQUALS_TO_OR_NULL:
				return mappingFunction.valueEqualsToOrNull(Objects.requireNonNull(onlyValue));
			default:
				throw new AdempiereException("Unhandled type: " + type); // shall not happen
		}
	}

	public <RecordType> void appendFilter(@NonNull final IQueryBuilder<RecordType> queryBuilder, @NonNull final String columnName)
	{
		map(new CaseMappingFunction<T, Void>()
		{

			@Override
			public Void anyValue()
			{
				// do nothing
				return null;
			}

			@Override
			public Void valueIsNull()
			{
				queryBuilder.addEqualsFilter(columnName, null);
				return null;
			}

			@Override
			public Void valueIsNotNull()
			{
				queryBuilder.addNotNull(columnName);
				return null;
			}

			@Override
			public Void valueEqualsTo(@NonNull final T value)
			{
				queryBuilder.addEqualsFilter(columnName, value);
				return null;
			}

			@Override
			public Void valueEqualsToOrNull(@NonNull final T value)
			{
				//noinspection unchecked
				queryBuilder.addInArrayFilter(columnName, null, value);
				return null;
			}
		});
	}
}
